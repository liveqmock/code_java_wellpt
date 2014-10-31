package com.wellsoft.pt.ldx.service.ficoManage.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wellsoft.pt.core.support.ExtendedPropertyPlaceholderConfigurer;
import com.wellsoft.pt.ldx.dao.common.MailAddrDao;
import com.wellsoft.pt.ldx.model.ficoManage.MailAddr;
import com.wellsoft.pt.ldx.service.ficoManage.IFicoReportService;
import com.wellsoft.pt.ldx.service.impl.SapServiceImpl;
import com.wellsoft.pt.ldx.util.DateUtils;
import com.wellsoft.pt.ldx.util.MailUtil;
import com.wellsoft.pt.ldx.util.StringUtils;

/**
 * 
 * Description: 财务应收报表服务
 *  
 * @author HeShi
 * @date 2014-10-3
 * @version 1.0
 * 
 * <pre>
 * 修改记录:
 * 修改后版本	修改人		修改日期			修改内容
 * 2014-10-3.1	HeShi		2014-10-3		Create
 * </pre>
 *
 */
@Service
@Transactional
public class FicoReportServiceImpl extends SapServiceImpl implements IFicoReportService {
	@Autowired
	private MailAddrDao mailAddrDao;
	@Autowired
	private ExtendedPropertyPlaceholderConfigurer propertyConfigurer;
	//功能代码:资金管理异常提醒邮件
	private static final String FC_DAILY_ERROR ="dailyError";
	//功能代码:发票异常邮件
	private static final String FC_INVOICE_ERROR ="invoiceError";
	//功能代码:未知客户提醒邮件
	private static final String FC_UNKNOWN_KUNNR ="unknownKunnr";
	//功能代码:业务员日报
	private static final String FC_DAILY_BALANCE ="dailyBalance";
	//功能代码:业务员汇总日报
	private static final String FC_DAILY_BALANCE_ALL ="dailyBalanceAll";
	//邮件发送类型:主送
	private static final String SENDTYPE_TO ="to";
	//邮件发送类型:抄送
	private static final String SENDTYPE_CC ="cc";
	//邮件发送类型:密送
	private static final String SENDTYPE_BC ="bc";
	
	private static Logger logger = LoggerFactory.getLogger(FicoReportServiceImpl.class);

	@Override
	public void sendEmptyKunnrEmail() throws Exception {
		String queryPP = " with temp as ( "
				+ " select a.zsname,a.kunnr,a.budat,a.sortl,a.zbelnr,' ' as bstkd,a.bukrs,a.waers,a.zdoip,a.zcamount,a.zdrs,a.zcirs,a.sgtxt,nvl(a.zetime,to_char(sysdate,'yyyy-MM-dd hh24:mi:ss')) as zetime,a.ztext "
				+ " from zfmt0003 a"
				+ " inner join zfmt0007 b on a.bukrs=b.bukrs and a.kunnr=b.kunnr "
				+ " where a.zdrs='P' and a.zcirs='P'"
				+ " and (a.fksta!='N' or a.fksta is null)"
				+ " and a.mandt="
				+ getClient()
				+ " and a.kunnr='...'"
				+ " and a.bukrs in (1000,7000,7200,7300)"
				+ " and a.zsname=b.zom"
				+ " )"
				+ " select * from ("
				+ " select zsname,kunnr,budat,sortl,zbelnr,bstkd,bukrs,waers,zdoip,zcamount,zdrs,zcirs,sgtxt,zetime,ztext from temp"
				+ " union all"
				+ " select 'Z',kunnr,'','','','','',waers,'',sum(zcamount),'','','','','' from temp group by kunnr,waers"
				+ " )order by kunnr asc,waers asc,zsname asc ";
		String queryPC = " with temp as ( "
				+ " select b.zsname,b.kunnr,b.budat,b.sortl,b.zbelnr,a.bstkd,b.bukrs,a.waers,b.zdoip,a.zcamount,a.zdrs,a.zcirs,b.sgtxt,b.ztext"
				+ " from zfmt0004 a"
				+ " inner join zfmt0003 b on a.zbelnr = b.zbelnr"
				+ " where a.zrbl='A'"
				+ " and a.zdrs='P'"
				+ " and a.zcirs='C'"
				+ " and (b.fksta!='N' or b.fksta is null)"
				+ " and b.kunnr='...'"
				+ " and b.bukrs in (1000,7000,7200,7300)"
				+ " )"
				+ " select * from ("
				+ " select zsname,kunnr,budat,sortl,zbelnr,bstkd,bukrs,waers,zdoip,zcamount,zdrs,zcirs,sgtxt,ztext from temp"
				+ " union all"
				+ " select 'Z',kunnr,'','','','','',waers,'',sum(zcamount),'','','','' from temp group by kunnr,waers"
				+ " )order by kunnr asc,waers asc,zsname asc ";
		String title = "<h4>此邮件为系统自动发送,请勿回复.</h4>" + "<h4>如果确认是您负责的客户,请将客户编号修改正确.</h4>";
		StringBuffer contString = new StringBuffer();
		// 邮件标题
		String subjectString = "";
		// 待登记信息
		List<Object> listPP = findListBySql(queryPP);
		if (listPP != null && listPP.size() > 0) {
			if (StringUtils.isNotBlank(title)) {
				contString.append(title);
				title = null;
			}
			if (StringUtils.isBlank(subjectString)) {
				subjectString = "今天未知客户信息登记,";
			}
			contString.append("<h4>待登记到账信息:</h4>").append(generateTableForEmptyKunnr(listPP, null, "1", "yw"))
					.append("<br/>");
		}
		// 待分解信息
		List<Object> listPC = findListBySql(queryPC);
		if (listPC != null && listPC.size() > 0) {
			if (StringUtils.isNotBlank(title)) {
				contString.append(title);
				title = null;
			}
			if (StringUtils.isBlank(subjectString)) {
				subjectString = "今天未知客户预收待分解提醒,";
			}
			contString.append("<h4>预收待分解信息:</h4>").append(generateTableForEmptyKunnr(listPC, null, "2", "yw"));
		}
		if (contString.length() == 0) {
			contString.append("<h4>今天没有未知客户待处理工作.</h4>");
		}
		if (contString.length() > 0) {
			boolean success = false;
			String date = DateUtils.formatDate(new Date());
			String to = findEmail(FC_UNKNOWN_KUNNR, SENDTYPE_TO);
			String cc = findEmail(FC_UNKNOWN_KUNNR, SENDTYPE_CC);
			String bc = findEmail(FC_UNKNOWN_KUNNR, SENDTYPE_BC);
			try {
				MailUtil mailUtil = new MailUtil();
				mailUtil.setHost("mail.leedarson.com");
				mailUtil.setUsername("IT@leedarson.com");
				mailUtil.setPassword("it612345");
				mailUtil.setFrom("IT@leedarson.com");
				mailUtil.setTo(to);
				mailUtil.setTocc(cc);
				mailUtil.setTobcc(bc);
				mailUtil.setSubject(subjectString + date);
				mailUtil.setContent(contString.toString());
				success = mailUtil.send();
			} catch (Exception e) {
				//			LOGGER.error("发送到账提醒邮件失败:" + e.getMessage());
			}
			if (success) {
				String update = "update zfmt0003 set zetime = to_char(sysdate,'yyyy-MM-dd hh24:mi:ss') "
						+ " where zetime =' ' " + " and kunnr = '...'";
				this.execSql(update);
			} else {
				MailUtil mailUtil = new MailUtil();
				String notice = "<h3 style='color:red;'>今日到账系统邮件发送给销售人员Email:"
						+ to
						+ "失败,以下为原邮件内容</h3><br/>"
						+ "============================================引用原邮件============================================<br/>";
				mailUtil.sendMail(null, findEmail(FC_DAILY_ERROR, SENDTYPE_TO), "到款提醒邮件发送失败:" + date, notice
						+ contString.toString());
			}
		}
	}
	
	/**
	 * 生成邮件表格(未知客户)
	 * @param list 数据列表
	 * @param userId 用户id
	 * @param type 提醒类型
	 * @param from 访问者,yw,ae
	 * @return
	 */
	private String generateTableForEmptyKunnr(List<Object> list,String userId,String type,String from){
		if(null==list || list.size()==0)
			return null;
		StringBuffer table = new StringBuffer();
		table.append("<table style='border-collapse: collapse;border-spacing: 2px;border-color: gray;'>");
		table.append("<thead>");
		table.append("<tr>");
		table.append("<th style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;background-color:#f2f3f3;border:1px solid #cccccc'>").append("业务员").append("</th>");
		table.append("<th style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;background-color:#f2f3f3;border:1px solid #cccccc'>").append("操作").append("</th>");
		table.append("<th style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;background-color:#f2f3f3;border:1px solid #cccccc'>").append("公司代码").append("</th>");
		table.append("<th style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;background-color:#f2f3f3;border:1px solid #cccccc'>").append("客户编号").append("</th>");
		table.append("<th style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;background-color:#f2f3f3;border:1px solid #cccccc'>").append("客户简称").append("</th>");
		table.append("<th style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;background-color:#f2f3f3;border:1px solid #cccccc'>").append("凭证编号（流水号）").append("</th>");
		table.append("<th style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;background-color:#f2f3f3;border:1px solid #cccccc'>").append("国际收支申报单号").append("</th>");
		table.append("<th style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;background-color:#f2f3f3;border:1px solid #cccccc'>").append("收款日期").append("</th>");
		if("1".equals(type)){//到款登记+提醒日期
			table.append("<th style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;background-color:#f2f3f3;border:1px solid #cccccc'>").append("提醒日期").append("</th>");
		}
		table.append("<th style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;background-color:#f2f3f3;border:1px solid #cccccc'>").append("收款金额").append("</th>");
		table.append("<th style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;background-color:#f2f3f3;border:1px solid #cccccc'>").append("收款币别").append("</th>");
		table.append("<th style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;background-color:#f2f3f3;border:1px solid #cccccc'>").append("合同号").append("</th>");
		table.append("<th style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;background-color:#f2f3f3;border:1px solid #cccccc'>").append("业务对象（摘要）").append("</th>");
		table.append("<th style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;background-color:#f2f3f3;border:1px solid #cccccc'>").append("备注").append("</th>");
		table.append("</tr>");
		table.append("</thead>");
		table.append("<tbody>");
		for(Object ob:list){
			Object[] obj = (Object[]) ob;
			table.append("<tr");
			if("Z".equals(obj[0])){
				table.append(" style='background-color:#FFFFBF;'");
			}
			table.append(">");
			table.append("<td style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;border:1px solid #cccccc;padding-left:4px;'>").append("Z".equals(obj[0])?"合计":obj[0]).append("</td>");
			table.append("<td style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;border:1px solid #cccccc;padding-left:4px;'>");
			if(!"Z".equals(obj[0])){
				table.append("<a href='").append(StringUtils.nullToString(propertyConfigurer.getProperty("server.url"))).append("/ficoManage/ficoRoute?flowNum=").append(obj[4]).append("&from=").append(from).append("&mail=y&user=").append(StringUtils.isBlank(userId)?StringUtils.nullToString(obj[0]):userId).append("'>");
				if("ae".equals(from)){
					table.append("查看");
				}else{
					if("1".equals(type)){
						table.append("登记");
					}else{
						table.append("分解");
					}
				}
				table.append("</a>");
			}
			table.append("</td>");
			table.append("<td style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;border:1px solid #cccccc;padding-left:4px;'>").append(StringUtils.nullToString(obj[6])).append("</td>");
			table.append("<td style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;border:1px solid #cccccc;padding-left:4px;'>").append(StringUtils.nullToString(obj[1])).append("</td>");
			table.append("<td style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;border:1px solid #cccccc;padding-left:4px;'>").append(StringUtils.nullToString(obj[3])).append("</td>");
			table.append("<td style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;border:1px solid #cccccc;padding-left:4px;'>").append(StringUtils.nullToString(obj[4])).append("</td>");
			table.append("<td style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;border:1px solid #cccccc;padding-left:4px;'>").append(StringUtils.nullToString(obj[8])).append("</td>");
			table.append("<td style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;border:1px solid #cccccc;padding-left:4px;'>").append(StringUtils.nullToString(obj[2])).append("</td>");
			if("1".equals(type)){//到款登记+提醒日期
				table.append("<td style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;border:1px solid #cccccc;padding-left:4px;'>").append(StringUtils.nullToString(obj[13])).append("</td>");
			}
			table.append("<td style='font-size:12px; ");
			if(!"Z".equals(obj[0])){
				table.append("background-color:#FFE1E1; ");
			}
			table.append("white-space:nowrap; text-align: right; display: table-cell;vertical-align: inherit;border:1px solid #cccccc;padding-left:4px;'>").append(StringUtils.nullToString(obj[9])).append("</td>");
			table.append("<td style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;border:1px solid #cccccc;padding-left:4px;'>").append(StringUtils.nullToString(obj[7])).append("</td>");
			table.append("<td style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;border:1px solid #cccccc;padding-left:4px;'>").append(StringUtils.nullToString(obj[5])).append("</td>");
			table.append("<td style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;border:1px solid #cccccc;padding-left:4px;'>").append(StringUtils.nullToString(obj[12])).append("</td>");
			table.append("<td style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;border:1px solid #cccccc;padding-left:4px;'>").append(StringUtils.nullToString(obj[14])).append("</td>");
			table.append("</tr>");
		}
		table.append("</tbody>");
		table.append("</table>");
		
		return table.toString();
	}
	
	/**
	 * 查询邮件接收人
	 * @param funcode 功能代码
	 * @param sendType 发送类型:to主送,cc抄送,bc密送
	 * @return
	 */
	@SuppressWarnings({ "deprecation" })
	private String findEmail(String funCode,String sendType){
		List<MailAddr> list = mailAddrDao.find("from MailAddr where type=? and funcode=? and sendtype=? order by ordernum","fico",funCode,sendType);
		if(list == null || list.isEmpty())
			return null;
		StringBuffer sb = new StringBuffer();
		for(MailAddr addr:list){
			sb.append(StringUtils.nullToString(addr.getAddr())).append(",");
		}
		return StringUtils.removeLastComma(sb.toString());
	}

	@Override
	public void sendErrorMessageToAdmin(String to, String cc) throws Exception {
		logger.error("发送邮件给资金管理相关管理员========start");
		int index = 1;
		StringBuffer content = new StringBuffer("<h4>此邮件为系统自动发送,请勿回复!</h4>");
		content
			.append(getDailyErrorMessage(index++))        //日记账信息未及时保存
			.append(getUserErrorMessage(index++))         //客户对应表维护中用户已经离职
			.append(getReceivedWithoutCustomerRegist(index++))         //到款信息未维护客户对应表
			.append(getCustErrorMessage(index++))         //客户对应表维护中客户编号在系统中不存在
			.append(getKunnrNoOmMessage(index++))         //客户信息未在客户对应表中未维护相应OM
			.append(getShipErrorMessage(index++))         //未及时维护预计船期(到款信息)
			.append(getShipErrorMessageAll(index++))      //未及时维护预计船期(所有外销的外向交货单)
			.append(getVbelnErrorMessage(index++))        //到账明细信息中的外向交货单在系统中查找不到对应记录
			.append(getTodoWorkAfterOmSubmit(index++))    //到款登记后财务待办工作
			.append(getTodoWorkAfterOmSeprate(index++))   //到款分解后财务待办工作
			.append(getNonRecCheckFail(index++))          //未收汇信息中以外向交货单对账金额不符
			.append(getPreReceiveError(index++))          //SAP预收未清金额与OA不符
			.append(getZitemHasNotText(index++))          //未收汇信息付款条件代码没有维护相应说明
			;
		if(StringUtils.isBlank(content.toString())){
			content.append("<h4>今天未发现异常信息!</h4>");
		}
		MailUtil mailUtil = new MailUtil();
		String date = DateUtils.formatDate(new Date(),"yyyy/MM/dd");
		mailUtil.setHost("mail.leedarson.com");
		mailUtil.setUsername("IT@leedarson.com");
		mailUtil.setPassword("it612345");
		mailUtil.setFrom("IT@leedarson.com");
		mailUtil.setSubject("今天资金管理异常信息,发送日期:" + date+",版本号:20140514");
		mailUtil.setContent(content.toString());
		if(StringUtils.isNotBlank(to)){
			mailUtil.setTo(to);
		}else{
			mailUtil.setTo(findEmail(FC_DAILY_ERROR,SENDTYPE_TO));
		}
		if(StringUtils.isNotBlank(cc)){
			mailUtil.setTocc(cc);
		}else{
			mailUtil.setTocc(findEmail(FC_DAILY_ERROR,SENDTYPE_CC));
		}
		boolean result = mailUtil.send();
		logger.error("发送邮件给资金管理相关管理员,success=="+result);
	}
	
	/**
	 * 获取到账处理状态异常信息
	 * @return
	 */
	public String getDailyErrorMessage(int index) {
		StringBuffer table = new StringBuffer();
		StringBuffer sql = new StringBuffer();
		sql.append(" select bukrs,kunnr,zbelnr from zfmt0001 where mandt=").append(getClient()).append(" and zbl='01' and zpc=' '");
		List<Object> list = findListBySql(sql.toString());
		if(null!=list && list.size()>0){
			table.append("<h4>").append(index).append(".日记账信息未及时保存:(郑治国课长负责)</h4>");
			table.append("<table style='border-collapse: collapse;border-spacing: 2px;border-color: gray;'>");
			table.append("<thead>");
			table.append("<tr>");
			table.append("<th style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;background-color:#f2f3f3;border:1px solid #cccccc'>").append("公司代码").append("</th>");
			table.append("<th style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;background-color:#f2f3f3;border:1px solid #cccccc'>").append("客户编号").append("</th>");
			table.append("<th style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;background-color:#f2f3f3;border:1px solid #cccccc'>").append("凭证编号(流水号)").append("</th>");
			table.append("</tr>");
			table.append("</thead>");
			table.append("<tbody>");
			for(Object ob:list){
				Object[] obj = (Object[])ob;
				table.append("<tr>");
				table.append("<td style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;border:1px solid #cccccc;padding-left:4px;'>").append(StringUtils.nullToString(obj[0])).append("</td>");
				table.append("<td style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;border:1px solid #cccccc;padding-left:4px;'>").append(StringUtils.nullToString(obj[1])).append("</td>");
				table.append("<td style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;border:1px solid #cccccc;padding-left:4px;'>").append(StringUtils.nullToString(obj[2])).append("</td>");
				table.append("</tr>");
			}
			table.append("</tbody>");
			table.append("</table>");
		}else{
			table.append("<h4>").append(index).append(".日记账信息未及时保存:(郑治国课长负责)</h4>");
			table.append("<span>今天无异常</span>");
		}
		return table.toString();
	}
	
	/**
	 * 获取人员异常信息
	 * @return
	 */
	public String getUserErrorMessage(int index){
		StringBuffer table = new StringBuffer();
		StringBuffer sql = new StringBuffer();
		sql.append(" select tp.bukrs,tp.kunnr,k.sortl,tp.usrid,tp.type from(")
			.append(" select bukrs,kunnr,zrsm as usrid,'RSM' as type from zfmt0007 where mandt=").append(getClient()).append(" and zrsm!=' '")
			.append(" union all")
			.append(" select bukrs,kunnr,zom as usrid,'OM' as type from zfmt0007 where mandt=").append(getClient()).append(" and zom!=' '")
			.append(" union all")
			.append(" select bukrs,kunnr,zaa as usrid,'AA' as type from zfmt0007 where mandt=").append(getClient()).append(" and zaa!=' '")
			.append(" union all")
			.append(" select bukrs,kunnr,zae as usrid,'AE' as type from zfmt0007 where mandt=").append(getClient()).append(" and zae!=' '")
			.append(" union all")
			.append(" select bukrs,kunnr,zrname as usrid,'AR' as type from zfmt0007 where mandt=").append(getClient()).append(" and zrname!=' '")
			.append(" ) tp ")
			.append(" left join kna1 k on tp.kunnr=k.kunnr")
			.append(" where exists (select 1 from pa0000 l where tp.usrid = l.pernr and l.massn='Z3' )")
			.append(" and not exists (select 1 from kna1 k where tp.kunnr=k.kunnr and k.ktokd in('Z003','Z011'))");
		List<Object> list = findListBySql(sql.toString());
		if(null!=list && list.size()>0){
			table.append("<h4>").append(index).append(".客户对应表维护中,有以下用户已经离职,请及时维护:(周娜,林艺婷负责)</h4>");
			table.append("<table style='border-collapse: collapse;border-spacing: 2px;border-color: gray;'>");
			table.append("<thead>");
			table.append("<tr>");
			table.append("<th style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;background-color:#f2f3f3;border:1px solid #cccccc'>").append("公司代码").append("</th>");
			table.append("<th style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;background-color:#f2f3f3;border:1px solid #cccccc'>").append("客户编号").append("</th>");
			table.append("<th style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;background-color:#f2f3f3;border:1px solid #cccccc'>").append("客户简称").append("</th>");
			table.append("<th style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;background-color:#f2f3f3;border:1px solid #cccccc'>").append("用户编号").append("</th>");
			table.append("<th style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;background-color:#f2f3f3;border:1px solid #cccccc'>").append("用户角色").append("</th>");
			table.append("</tr>");
			table.append("</thead>");
			table.append("<tbody>");
			for(Object ob:list){
				Object[] obj = (Object[])ob;
				table.append("<tr>");
				table.append("<td style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;border:1px solid #cccccc;padding-left:4px;'>").append(StringUtils.nullToString(obj[0])).append("</td>");
				table.append("<td style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;border:1px solid #cccccc;padding-left:4px;'>").append(StringUtils.nullToString(obj[1])).append("</td>");
				table.append("<td style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;border:1px solid #cccccc;padding-left:4px;'>").append(StringUtils.nullToString(obj[2])).append("</td>");
				table.append("<td style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;border:1px solid #cccccc;padding-left:4px;'>").append(StringUtils.nullToString(obj[3])).append("</td>");
				table.append("<td style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;border:1px solid #cccccc;padding-left:4px;'>").append(StringUtils.nullToString(obj[4])).append("</td>");
				table.append("</tr>");
			}
			table.append("</tbody>");
			table.append("</table>");
		}else{
			table.append("<h4>").append(index).append(".客户对应表维护中,有以下用户已经离职,请及时维护:(周娜,林艺婷负责)</h4>");
			table.append("<span>今天无异常</span>");
		}
		return table.toString();
	}
	
	/**
	 * 到款信息没有维护客户对应表记录
	 * @return
	 */
	public String getReceivedWithoutCustomerRegist(int index){
		StringBuffer table = new StringBuffer();
		StringBuffer sql = new StringBuffer();
		sql.append(" select a.zbelnr,a.bukrs,a.kunnr,b.sortl,a.zcamount||' '||a.waers,a.bldat from zfmt0003 a")
			.append(" left join kna1 b on a.kunnr=b.kunnr")
			.append(" where not exists (select 1 from zfmt0007 c where a.bukrs=c.bukrs and a.kunnr=c.kunnr)")
			.append(" and a.zdrs!='C' and a.bukrs in (1000,7000,7200,7300)")
			.append(" and a.mandt=").append(getClient());
		List<Object> list = findListBySql(sql.toString());
		if(null!=list && list.size()>0){
			table.append("<h4>").append(index).append(".以下到款信息未维护客户对应表:(周娜,林艺婷负责)</h4>");
			table.append("<table style='border-collapse: collapse;border-spacing: 2px;border-color: gray;'>");
			table.append("<thead>");
			table.append("<tr>");
			table.append("<th style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;background-color:#f2f3f3;border:1px solid #cccccc'>").append("流水号").append("</th>");
			table.append("<th style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;background-color:#f2f3f3;border:1px solid #cccccc'>").append("公司代码").append("</th>");
			table.append("<th style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;background-color:#f2f3f3;border:1px solid #cccccc'>").append("客户编号").append("</th>");
			table.append("<th style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;background-color:#f2f3f3;border:1px solid #cccccc'>").append("客户简称").append("</th>");
			table.append("<th style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;background-color:#f2f3f3;border:1px solid #cccccc'>").append("到款金额").append("</th>");
			table.append("<th style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;background-color:#f2f3f3;border:1px solid #cccccc'>").append("到款日期").append("</th>");
			table.append("</tr>");
			table.append("</thead>");
			table.append("<tbody>");
			for(Object ob:list){
				Object[] obj=(Object[])ob;
				table.append("<tr>");
				table.append("<td style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;border:1px solid #cccccc;padding-left:4px;'>").append(StringUtils.nullToString(obj[0])).append("</td>");
				table.append("<td style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;border:1px solid #cccccc;padding-left:4px;'>").append(StringUtils.nullToString(obj[1])).append("</td>");
				table.append("<td style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;border:1px solid #cccccc;padding-left:4px;'>").append(StringUtils.nullToString(obj[2])).append("</td>");
				table.append("<td style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;border:1px solid #cccccc;padding-left:4px;'>").append(StringUtils.nullToString(obj[3])).append("</td>");
				table.append("<td style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;border:1px solid #cccccc;padding-left:4px;text-align: right;'>").append(StringUtils.nullToString(obj[4])).append("</td>");
				table.append("<td style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;border:1px solid #cccccc;padding-left:4px;'>").append(StringUtils.nullToString(obj[5])).append("</td>");
				table.append("</tr>");
			}
			table.append("</tbody>");
			table.append("</table>");
		}else{
			table.append("<h4>").append(index).append(".以下到款信息未维护客户对应表:(周娜,林艺婷负责)</h4>");
			table.append("<span>今天无异常</span>");
		}
		return table.toString();
	}
	
	/**
	 * 获取客户编号异常信息
	 * @return
	 */
	public String getCustErrorMessage(int index){
		StringBuffer table = new StringBuffer();
		StringBuffer sql = new StringBuffer();
		sql.append(" select bukrs,kunnr,sortl from zfmt0007 a ")
			.append(" where mandt=").append(getClient())
			.append(" and not exists (select 1 from kna1 b where a.mandt = b.mandt and b.kunnr = a.kunnr)")
			.append(" and kunnr!='...'");
		List<Object> list = findListBySql(sql.toString());
		if(null!=list && list.size()>0){
			table.append("<h4>").append(index).append(".客户对应表维护中,有以下客户编号在系统中查询不到相应客户信息:(周娜,林艺婷负责)</h4>");
			table.append("<table style='border-collapse: collapse;border-spacing: 2px;border-color: gray;'>");
			table.append("<thead>");
			table.append("<tr>");
			table.append("<th style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;background-color:#f2f3f3;border:1px solid #cccccc'>").append("公司代码").append("</th>");
			table.append("<th style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;background-color:#f2f3f3;border:1px solid #cccccc'>").append("客户编号").append("</th>");
			table.append("<th style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;background-color:#f2f3f3;border:1px solid #cccccc'>").append("客户简称").append("</th>");
			table.append("</tr>");
			table.append("</thead>");
			table.append("<tbody>");
			for(Object ob:list){
				Object[] obj = (Object[])ob;
				table.append("<tr>");
				table.append("<td style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;border:1px solid #cccccc;padding-left:4px;'>").append(StringUtils.nullToString(obj[0])).append("</td>");
				table.append("<td style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;border:1px solid #cccccc;padding-left:4px;'>").append(StringUtils.nullToString(obj[1])).append("</td>");
				table.append("<td style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;border:1px solid #cccccc;padding-left:4px;'>").append(StringUtils.nullToString(obj[2])).append("</td>");
				table.append("</tr>");
			}
			table.append("</tbody>");
			table.append("</table>");
		}else{
			table.append("<h4>").append(index).append(".客户对应表维护中,有以下客户编号在系统中查询不到相应客户信息:(周娜,林艺婷负责)</h4>");
			table.append("<span>今天无异常</span>");
		}
		return table.toString();
	}
	
	/**
	 * 获取未维护客户对应表的客户信息
	 * @return
	 */
	public String getKunnrNoOmMessage(int index){
		StringBuffer table = new StringBuffer();
		StringBuffer sql = new StringBuffer();
		sql.append("select distinct d.bukrs,d.kunnr,decode(n.sortl,' ',n.name1,n.sortl) from bsid d ")
			.append(" left join kna1 n on n.kunnr=d.kunnr and n.mandt=d.mandt ")
			.append(" where d.mandt=").append(getClient())
			.append(" and d.umsks in (' ','A','3') and d.bukrs in (1000,7000,7200,7300)")
			.append(" and not exists (select * from zfmt0007 s where s.kunnr = d.kunnr and s.bukrs = d.bukrs and s.zom !=' ')")
			.append(" and not exists (select 1 from kna1 k where d.kunnr=k.kunnr and k.ktokd in('Z003','Z011'))")
			.append(" order by d.bukrs,d.kunnr");
		List<Object> list = findListBySql(sql.toString());
		if(null!=list && list.size()>0){
			table.append("<h4>").append(index).append(".以下客户信息未在客户对应表中维护相应OM:(周娜,林艺婷负责)</h4>");
			table.append("<table style='border-collapse: collapse;border-spacing: 2px;border-color: gray;'>");
			table.append("<thead>");
			table.append("<tr>");
			table.append("<th style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;background-color:#f2f3f3;border:1px solid #cccccc'>").append("公司代码").append("</th>");
			table.append("<th style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;background-color:#f2f3f3;border:1px solid #cccccc'>").append("客户编号").append("</th>");
			table.append("<th style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;background-color:#f2f3f3;border:1px solid #cccccc'>").append("客户简称").append("</th>");
			table.append("</tr>");
			table.append("</thead>");
			table.append("<tbody>");
			for(Object ob:list){
				Object[] obj=(Object[])ob;
				table.append("<tr>");
				table.append("<td style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;border:1px solid #cccccc;padding-left:4px;'>").append(StringUtils.nullToString(obj[0])).append("</td>");
				table.append("<td style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;border:1px solid #cccccc;padding-left:4px;'>").append(StringUtils.nullToString(obj[1])).append("</td>");
				table.append("<td style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;border:1px solid #cccccc;padding-left:4px;'>").append(StringUtils.nullToString(obj[2])).append("</td>");
				table.append("</tr>");
			}
			table.append("</tbody>");
			table.append("</table>");
		}else{
			table.append("<h4>").append(index).append(".以下客户信息未在客户对应表中维护相应OM:(周娜,林艺婷负责)</h4>");
			table.append("<span>今天无异常</span>");
		}
		return table.toString();
	}
	
	/**
	 * 获取船运异常信息
	 * @return
	 */
	public String getShipErrorMessage(int index){
		StringBuffer table = new StringBuffer();
		StringBuffer sql = new StringBuffer();
		sql.append(" select distinct a.zbelnr,a.vbeln,e.sortl from zfmt0004 a ")
			.append(" left join kna1 e on a.kunnr=e.kunnr and a.mandt=e.mandt ")
			.append(" where a.mandt=").append(getClient())
			.append(" and a.vbeln!=' '")
			.append(" and a.vbeln not like '201%'")
			.append(" and not exists (select * from zsdt0036 b where a.vbeln=b.vbeln and zyjcq!='00000000')")
			//过滤国内发货单,币种为人民币
			.append(" and not exists (select 1 from zfmt0003 c where a.zbelnr=c.zbelnr and c.waers='CNY' )")
			//add by HeShi 20140109 外向交货单每日异常需验证已经开具发票
			.append(" and exists (select 1 from vbrp p where p.vgbel=a.vbeln)")
			//add by HeShi 20140305 过滤S001-1客户
			.append(" and e.sortl not in ('S001-1','S001-2')");
		List<Object> list = findListBySql(sql.toString());
		
		
		if(null!=list && list.size()>0){
			//查找出货排载负责人
			Map<String,String> fzrMap = new HashMap<String,String>();
			//TODO 查找出货排载负责人from LCP
//			try {
//				List<CShipschedef> fzrList = portalDao
//						.getAll(CShipschedef.class);
//				if (fzrList != null && fzrList.size() > 0) {
//					for (CShipschedef def : fzrList) {
//						fzrMap.put(def.getContr().trim().toUpperCase(),
//								def.getUsername());
//					}
//				}
//			} catch (Exception e) {
//				LOGGER.error("load CShipschedef error when getShipErrorMessage:"+e.getMessage());
//			}
			table.append("<h4>").append(index).append(".以下到账明细信息中的外向交货单,共").append(list.size()).append("条记录,请及时维护预计船期:(江艺君课长负责)</h4>");
			table.append("<table style='border-collapse: collapse;border-spacing: 2px;border-color: gray;'>");
			table.append("<thead>");
			table.append("<tr>");
			table.append("<th style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;background-color:#f2f3f3;border:1px solid #cccccc'>").append("凭证编号(流水号)").append("</th>");
			table.append("<th style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;background-color:#f2f3f3;border:1px solid #cccccc'>").append("外向交货单").append("</th>");
			table.append("<th style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;background-color:#f2f3f3;border:1px solid #cccccc'>").append("客户简称").append("</th>");
			table.append("<th style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;background-color:#f2f3f3;border:1px solid #cccccc'>").append("排载负责人").append("</th>");
			table.append("</tr>");
			table.append("</thead>");
			table.append("<tbody>");
			for(Object ob:list){
				Object[] obj=(Object[])ob;
				table.append("<tr>");
				table.append("<td style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;border:1px solid #cccccc;padding-left:4px;'>").append(StringUtils.nullToString(obj[0])).append("</td>");
				table.append("<td style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;border:1px solid #cccccc;padding-left:4px;'>").append(StringUtils.removeLeftZero(StringUtils.nullToString(obj[1]))).append("</td>");
				table.append("<td style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;border:1px solid #cccccc;padding-left:4px;'>").append(StringUtils.nullToString(obj[2])).append("</td>");
				table.append("<td style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;border:1px solid #cccccc;padding-left:4px;'>");
				if(StringUtils.isNotBlank(StringUtils.nullToString(obj[2]))){
					String str = StringUtils.nullToString(obj[2]).substring(0,1).toUpperCase();
					table.append(StringUtils.nullToString(fzrMap.get(str)));
				}
				table.append("</td>");
				table.append("</tr>");
			}
			table.append("</tbody>");
			table.append("</table>");
		}else{
			table.append("<h4>").append(index).append(".以下到账明细信息中的外向交货单,未及时维护预计船期:(江艺君课长负责)</h4>");
			table.append("<span>今天无异常</span>");
		}
		return table.toString();
	}
	
	/**
	 * 获取船运异常信息
	 * @return
	 */
	public String getShipErrorMessageAll(int index){
		StringBuffer table = new StringBuffer();
		StringBuffer sql = new StringBuffer();
		sql.append("select distinct substr(ta.vbeln,3),k.kunnr,k.sortl from lips ta")
			.append(" inner join vbap tb on ta.vgbel=tb.vbeln and ta.vgpos = tb.posnr")
			.append(" inner join vbak tc on tb.vbeln=tc.vbeln")
			.append(" inner join kna1 k on tc.kunnr=k.kunnr")
			.append(" where ta.vbeln like '008%'")
			.append(" and exists (select 1 from vbrp b,vbrk c,bsid d where ta.vbeln=b.vgbel and b.vbeln=c.vbeln and b.vbeln=d.zuonr and c.ktgrd='Z4' )")
			.append(" and not exists (select 1 from zsdt0036 e where e.vbeln=ta.vbeln and e.zyjcq !='00000000')")
			.append(" and k.sortl not in ('S001','S001-1','S001-2')");
		List<Object> list = findListBySql(sql.toString());
		
		if(null!=list && list.size()>0){
			table.append("<h4>").append(index).append(".以下未收汇外向交货单,共").append(list.size()).append("条记录,请及时维护预计船期:(江艺君课长负责)</h4>");
			table.append("<table style='border-collapse: collapse;border-spacing: 2px;border-color: gray;'>");
			table.append("<thead>");
			table.append("<tr>");
			table.append("<th style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;background-color:#f2f3f3;border:1px solid #cccccc'>").append("外向交货单").append("</th>");
			table.append("<th style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;background-color:#f2f3f3;border:1px solid #cccccc'>").append("客户编号").append("</th>");
			table.append("<th style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;background-color:#f2f3f3;border:1px solid #cccccc'>").append("客户简称").append("</th>");
			table.append("</tr>");
			table.append("</thead>");
			table.append("<tbody>");
			for(Object ob:list){
				Object[] obj=(Object[])ob;
				table.append("<tr>");
				table.append("<td style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;border:1px solid #cccccc;padding-left:4px;'>").append(StringUtils.nullToString(obj[0])).append("</td>");
				table.append("<td style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;border:1px solid #cccccc;padding-left:4px;'>").append(StringUtils.nullToString(obj[1])).append("</td>");
				table.append("<td style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;border:1px solid #cccccc;padding-left:4px;'>").append(StringUtils.nullToString(obj[2])).append("</td>");
				table.append("</tr>");
			}
			table.append("</tbody>");
			table.append("</table>");
		}else{
			table.append("<h4>").append(index).append(".未收汇外向交货单,未及时维护预计船期:(江艺君课长负责)</h4>");
			table.append("<span>今天无异常</span>");
		}
		return table.toString();
	}
	
	/**
	 * 获取外向交货单异常信息
	 * @return
	 */
	public String getVbelnErrorMessage(int index) {
		StringBuffer table = new StringBuffer();
		StringBuffer sql = new StringBuffer();
		sql.append("with usr as(select distinct pernr,ename from pa0001) ")
			.append(" select a.zbelnr,a.zposnr,a.vbeln,d.ename,e.ename from zfmt0004 a ")
			.append(" inner join zfmt0003 b on a.zbelnr = b.zbelnr ")
			.append(" left join zfmt0007 c on c.kunnr = b.kunnr and c.bukrs = b.bukrs")
			.append(" left join usr d on c.zrname = d.pernr")
			.append(" left join usr e on c.zom = e.pernr")
			.append(" where mandt=").append(getClient())
			.append(" and vbeln!=' '")
			.append(" and vbeln not like '201%'")
			.append(" and not exists (select 1 from likp b where b.vbeln=a.vbeln)");
		List<Object> list = findListBySql(sql.toString());
		if(null!=list && list.size()>0){
			table.append("<h4>").append(index).append(".以下到账明细信息中的外向交货单,在系统中查找不到对应的交货单记录,请核对:(AR人员负责)</h4>");
			table.append("<table style='border-collapse: collapse;border-spacing: 2px;border-color: gray;'>");
			table.append("<thead>");
			table.append("<tr>");
			table.append("<th style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;background-color:#f2f3f3;border:1px solid #cccccc'>").append("凭证编号(流水号)").append("</th>");
			table.append("<th style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;background-color:#f2f3f3;border:1px solid #cccccc'>").append("行项").append("</th>");
			table.append("<th style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;background-color:#f2f3f3;border:1px solid #cccccc'>").append("外向交货单").append("</th>");
			table.append("<th style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;background-color:#f2f3f3;border:1px solid #cccccc'>").append("AR").append("</th>");
			table.append("<th style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;background-color:#f2f3f3;border:1px solid #cccccc'>").append("OM").append("</th>");
			table.append("</tr>");
			table.append("</thead>");
			table.append("<tbody>");
			for(Object ob:list){
				Object[] obj=(Object[])ob;
				table.append("<tr>");
				table.append("<td style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;border:1px solid #cccccc;padding-left:4px;'>").append(StringUtils.nullToString(obj[0])).append("</td>");
				table.append("<td style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;border:1px solid #cccccc;padding-left:4px;'>").append(StringUtils.nullToString(obj[1])).append("</td>");
				table.append("<td style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;border:1px solid #cccccc;padding-left:4px;'>").append(StringUtils.nullToString(obj[2])).append("</td>");
				table.append("<td style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;border:1px solid #cccccc;padding-left:4px;'>").append(StringUtils.nullToString(obj[3])).append("</td>");
				table.append("<td style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;border:1px solid #cccccc;padding-left:4px;'>").append(StringUtils.nullToString(obj[4])).append("</td>");
				table.append("</tr>");
			}
			table.append("</tbody>");
			table.append("</table>");
		}else{
			table.append("<h4>").append(index).append(".以下到账明细信息中的外向交货单,在系统中查找不到对应的交货单记录,请核对:(AR人员负责)</h4>");
			table.append("<span>今天无异常</span>");
		}
		return table.toString();
	}
	
	/**
	 * 获取登记后财务待办工作
	 * @return
	 */
	public String getTodoWorkAfterOmSubmit(int index){
		StringBuffer table = new StringBuffer();
		StringBuffer sql = new StringBuffer();
		sql.append("with usr as(")
			.append(" select distinct pernr,ename from pa0001 where mandt=").append(getClient())
			.append(" )")
			.append(" select a.bukrs,a.kunnr,a.sortl,a.zbelnr,c.ename as yw,d.ename as om,e.ename as ar,f.ename as rsm from zfmt0003 a")
			.append(" left join zfmt0007 b on a.mandt = b.mandt and a.bukrs = b.bukrs and a.kunnr = b.kunnr")
			.append(" left join usr c on a.zsname=c.pernr")
			.append(" left join usr d on b.zom = d.pernr")
			.append(" left join usr e on b.zrname = e.pernr")
			.append(" left join usr f on b.zrsm = f.pernr")
			.append(" where a.mandt=").append(getClient())
			.append(" and a.zcirs='F'")
			.append(" and a.bukrs in (1000,7000,7200,7300)")
			.append(" order by a.zbelnr");
		List<Object> list = findListBySql(sql.toString());
		if(null!=list && list.size()>0){
			table.append("<h4>").append(index).append(".到款登记后财务待办工作(杨晨负责):</h4>");
			table.append("<table style='border-collapse: collapse;border-spacing: 2px;border-color: gray;'>");
			table.append("<thead>");
			table.append("<tr>");
			table.append("<th style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;background-color:#f2f3f3;border:1px solid #cccccc'>").append("公司代码").append("</th>");
			table.append("<th style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;background-color:#f2f3f3;border:1px solid #cccccc'>").append("客户编号").append("</th>");
			table.append("<th style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;background-color:#f2f3f3;border:1px solid #cccccc'>").append("客户简称").append("</th>");
			table.append("<th style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;background-color:#f2f3f3;border:1px solid #cccccc'>").append("凭证编号(流水号)").append("</th>");
			table.append("<th style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;background-color:#f2f3f3;border:1px solid #cccccc'>").append("业务员").append("</th>");
			table.append("<th style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;background-color:#f2f3f3;border:1px solid #cccccc'>").append("OM").append("</th>");
			table.append("<th style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;background-color:#f2f3f3;border:1px solid #cccccc'>").append("AR").append("</th>");
			table.append("<th style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;background-color:#f2f3f3;border:1px solid #cccccc'>").append("RSM").append("</th>");
			table.append("</tr>");
			table.append("</thead>");
			table.append("<tbody>");
			for(Object ob:list){
				Object[] obj=(Object[])ob;
				table.append("<tr>");
				table.append("<td style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;border:1px solid #cccccc;padding-left:4px;'>").append(StringUtils.nullToString(obj[0])).append("</td>");
				table.append("<td style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;border:1px solid #cccccc;padding-left:4px;'>").append(StringUtils.nullToString(obj[1])).append("</td>");
				table.append("<td style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;border:1px solid #cccccc;padding-left:4px;'>").append(StringUtils.nullToString(obj[2])).append("</td>");
				table.append("<td style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;border:1px solid #cccccc;padding-left:4px;'>").append(StringUtils.nullToString(obj[3])).append("</td>");
				table.append("<td style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;border:1px solid #cccccc;padding-left:4px;'>").append(StringUtils.nullToString(obj[4])).append("</td>");
				table.append("<td style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;border:1px solid #cccccc;padding-left:4px;'>").append(StringUtils.nullToString(obj[5])).append("</td>");
				table.append("<td style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;border:1px solid #cccccc;padding-left:4px;'>").append(StringUtils.nullToString(obj[6])).append("</td>");
				table.append("<td style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;border:1px solid #cccccc;padding-left:4px;'>").append(StringUtils.nullToString(obj[7])).append("</td>");
				table.append("</tr>");
			}
			table.append("</tbody>");
			table.append("</table>");
		}else{
			table.append("<h4>").append(index).append(".到款登记后财务待办工作(杨晨负责):</h4>");
			table.append("<span>今天无待办工作</span>");
		}
		return table.toString();
	}
	
	/**
	 * 获取到款分解后财务待办工作
	 * @return
	 */
	public String getTodoWorkAfterOmSeprate(int index){
		StringBuffer table = new StringBuffer();
		StringBuffer sql = new StringBuffer();
		sql.append("with usr as(")
		.append(" select distinct pernr,ename from pa0001 where mandt=").append(getClient())
		.append(" )")
		.append(" select a.bukrs,a.kunnr,a.sortl,a.zbelnr,c.ename as yw,d.ename as om,e.ename as ar,f.ename as rsm from zfmt0003 a")
		.append(" left join zfmt0007 b on a.mandt = b.mandt and a.bukrs = b.bukrs and a.kunnr = b.kunnr")
		.append(" left join usr c on a.zsname=c.pernr")
		.append(" left join usr d on b.zom = d.pernr")
		.append(" left join usr e on b.zrname = e.pernr")
		.append(" left join usr f on b.zrsm = f.pernr")
		.append(" where a.mandt=").append(getClient())
		.append(" and exists (select 1 from zfmt0004 g where g.zbelnr=a.zbelnr and g.zdrs='F' and g.zcirs='C')")
		.append(" and a.bukrs in (1000,7000,7200,7300)")
		.append(" order by a.zbelnr");
		List<Object> list = findListBySql(sql.toString());
		if(null!=list && list.size()>0){
			table.append("<h4>").append(index).append(".到款分解后财务待办工作(杨晨负责):</h4>");
			table.append("<table style='border-collapse: collapse;border-spacing: 2px;border-color: gray;'>");
			table.append("<thead>");
			table.append("<tr>");
			table.append("<th style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;background-color:#f2f3f3;border:1px solid #cccccc'>").append("公司代码").append("</th>");
			table.append("<th style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;background-color:#f2f3f3;border:1px solid #cccccc'>").append("客户编号").append("</th>");
			table.append("<th style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;background-color:#f2f3f3;border:1px solid #cccccc'>").append("客户简称").append("</th>");
			table.append("<th style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;background-color:#f2f3f3;border:1px solid #cccccc'>").append("凭证编号(流水号)").append("</th>");
			table.append("<th style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;background-color:#f2f3f3;border:1px solid #cccccc'>").append("业务员").append("</th>");
			table.append("<th style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;background-color:#f2f3f3;border:1px solid #cccccc'>").append("OM").append("</th>");
			table.append("<th style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;background-color:#f2f3f3;border:1px solid #cccccc'>").append("AR").append("</th>");
			table.append("<th style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;background-color:#f2f3f3;border:1px solid #cccccc'>").append("RSM").append("</th>");
			table.append("</tr>");
			table.append("</thead>");
			table.append("<tbody>");
			for(Object ob:list){
				Object[] obj=(Object[])ob;
				table.append("<tr>");
				table.append("<td style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;border:1px solid #cccccc;padding-left:4px;'>").append(StringUtils.nullToString(obj[0])).append("</td>");
				table.append("<td style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;border:1px solid #cccccc;padding-left:4px;'>").append(StringUtils.nullToString(obj[1])).append("</td>");
				table.append("<td style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;border:1px solid #cccccc;padding-left:4px;'>").append(StringUtils.nullToString(obj[2])).append("</td>");
				table.append("<td style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;border:1px solid #cccccc;padding-left:4px;'>").append(StringUtils.nullToString(obj[3])).append("</td>");
				table.append("<td style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;border:1px solid #cccccc;padding-left:4px;'>").append(StringUtils.nullToString(obj[4])).append("</td>");
				table.append("<td style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;border:1px solid #cccccc;padding-left:4px;'>").append(StringUtils.nullToString(obj[5])).append("</td>");
				table.append("<td style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;border:1px solid #cccccc;padding-left:4px;'>").append(StringUtils.nullToString(obj[6])).append("</td>");
				table.append("<td style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;border:1px solid #cccccc;padding-left:4px;'>").append(StringUtils.nullToString(obj[7])).append("</td>");
				table.append("</tr>");
			}
			table.append("</tbody>");
			table.append("</table>");
		}else{
			table.append("<h4>").append(index).append(".到款分解后财务待办工作(杨晨负责):</h4>");
			table.append("<span>今天无待办工作</span>");
		}
		return table.toString();
	}
	
	/**
	 * 获取未收汇金额不符交货单信息
	 * @return
	 */
	public String getNonRecCheckFail(int index){
		StringBuffer table = new StringBuffer();
		//@formatter:off
		String sql = 
			 "with temp as                                                              "
			+" (select vbeln,                                                           "
			+"         listagg(budat, '|') within group(order by budat) as budat,       "
			+"         listagg(zcamount, '|') within group(order by budat) as zcamount, "
			+"         sum(zcamount) as amt,                                            "
			+"         sum(zsum) as zsum,                                               "
			+"         sum(zhc) as zhc                                                  "
			+"    from (select na.vbeln,                                                "
			+"                 nb.budat,                                                "
			+"                 sum(na.zcamount - na.zhc) as zcamount,                   "
			+"                 sum(na.zcamount) as zsum,                                "
			+"                 sum(na.zhc) as zhc                                       "
			+"            from zfmt0004 na, zfmt0003 nb                                 "
			+"           where na.mandt = nb.mandt                                      "
			+"             and (na.zrbl = 'B' or                                        "
			+"                 (na.zrbl = 'A' and not exists                            "
			+"                  (select 1                                               "
			+"                      from zfmt0004 te                                    "
			+"                     where te.zbelnr = na.zbelnr                          "
			+"                       and te.zposnr_s = na.zposnr)))                     "
			+"             and na.zdrs = 'C'                                            "
			+"             and na.zcirs = 'C'                                           "
			+"             and na.zbelnr = nb.zbelnr                                    "
			+"             and na.mandt =                                               " +getClient()
			+"             and na.vbeln != ' '                                          "
			+"           group by na.vbeln, nb.budat, nb.zbelnr)                        "
			+"   group by vbeln),                                                       "
			+"bsidtep as                                                                "
			+" (select p.vgbel as vbeln,                                                "
			+"         nvl(sum(case                                                     "
			+"                   when bschl > '09' then                                 "
			+"                    -wrbtr                                                "
			+"                   else                                                   "
			+"                    wrbtr                                                 "
			+"                 end),                                                    "
			+"             0) as unclear                                                "
			+"    from bsid d                                                           "
			+"   inner join (select distinct vgbel, vbeln from vbrp) p                  "
			+"      on p.vbeln = d.zuonr                                                "
			+"   where umskz = ' '                                                      "
			+"     and d.mandt =                                                        " +getClient()
			+"   group by p.vgbel)                                                      "
			+"select substr(a.vbeln, 3) 外向交货单,                                      "
			+"       a.bukrs 公司代码,                                                  "
			+"       a.kunnr 客户ID,                                                    "
			+"       a.sortl 客户简称,                                                  "
			+"       a.zdamt 发货金额,                                                  "
			+"       a.ziamt 开票金额,                                                  "
			+"       nvl(t.amt, 0) 已收金额,                                            "
			+"       nvl(case                                                           "
			+"             when a.ziamt=0 then                                          "
			+"              a.zdamt                                                     "
			+"             else                                                         "
			+"              a.ziamt                                                     "
			+"           end,                                                           "
			+"           0) + nvl(b.zcamt, 0) - nvl(t.amt, 0) - nvl(t.zhc, 0) 应收款余额,"
			+"       nvl(t.zhc, 0) 调整金额,                                             "
			+"       nvl(b.zcamt, 0) 扣款金额,                                           "
			+"       nvl(v.unclear, 0) 未清金额,                                         "
			+"       nvl(case when a.ziamt=0 then a.zdamt else a.ziamt end,0) + nvl(b.zcamt, 0) - nvl(t.amt, 0) - nvl(t.zhc, 0) -         "
			+"       nvl(v.unclear, 0) 不符金额,                                        "
			+"       y.ename 应收人员                                                      								"
			+"  from zfmt0016 a                                                         "
			+"  left join zfmt0017 b                                                    "
			+"    on a.madat = b.mandt                                                  "
			+"   and a.vbeln = b.vbeln                                                  "
			+"  left join zfmt0007 d                                                    "
			+"    on a.kunnr = d.kunnr                                                  "
			+"   and a.bukrs = d.bukrs                                                  "
			+"  left join temp t                                                        "
			+"    on a.vbeln = t.vbeln                                                  "
			+"  left join bsidtep v                                                     "
			+"    on a.vbeln = v.vbeln                                                  "
			+"  left join zfmt0007 x                                                    "
			+"    on a.kunnr = x.kunnr                                                  "
			+"   and a.bukrs = x.bukrs                                                  "
			+"  left join (select distinct pernr, ename from pa0001) y                  "
			+"    on x.zrname = y.pernr                                                 "
			+" where a.madat =                                                          " +getClient()
			+"   and (nvl(case when a.ziamt=0 then a.zdamt else a.ziamt end,0) + nvl(b.zcamt, 0) - nvl(t.amt, 0) - nvl(t.zhc, 0) -        "
			+"       nvl(v.unclear, 0) < -1 or                                          "
			+"       nvl(case when a.ziamt=0 then a.zdamt else a.ziamt end,0) + nvl(b.zcamt, 0) - nvl(t.amt, 0) - nvl(t.zhc, 0) -        "
			+"       nvl(v.unclear, 0) > 1)                                             "
			+"   and a.fkdat != '00000000'                                              "
			+"   and a.ziamt > 0                                                        "
			+" order by a.bukrs, a.kunnr                                                ";
		//@formatter:on
		List<Object> list = findListBySql(sql);
		if(null!=list && list.size()>0){
			table.append("<h4>").append(index).append(".未收汇信息中以下外向交货单对账金额不符(杨晨负责):</h4>");
			table.append("<h4>注:不符金额=开票金额+扣款金额-已收金额-调整金额(手续费)-sap未清金额:</h4>");
			table.append("<table style='border-collapse: collapse;border-spacing: 2px;border-color: gray;'>");
			table.append("<thead>");
			table.append("<tr>");
			table.append("<th style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;background-color:#f2f3f3;border:1px solid #cccccc'>").append("外向交货单").append("</th>");
			table.append("<th style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;background-color:#f2f3f3;border:1px solid #cccccc'>").append("公司代码").append("</th>");
			table.append("<th style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;background-color:#f2f3f3;border:1px solid #cccccc'>").append("客户ID").append("</th>");
			table.append("<th style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;background-color:#f2f3f3;border:1px solid #cccccc'>").append("客户简称").append("</th>");
			table.append("<th style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;background-color:#f2f3f3;border:1px solid #cccccc'>").append("发货金额").append("</th>");
			table.append("<th style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;background-color:#f2f3f3;border:1px solid #cccccc'>").append("开票金额").append("</th>");
			table.append("<th style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;background-color:#f2f3f3;border:1px solid #cccccc'>").append("已收金额").append("</th>");
			table.append("<th style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;background-color:#f2f3f3;border:1px solid #cccccc'>").append("应收款余额").append("</th>");
			table.append("<th style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;background-color:#f2f3f3;border:1px solid #cccccc'>").append("调整金额(手续费)").append("</th>");
			table.append("<th style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;background-color:#f2f3f3;border:1px solid #cccccc'>").append("扣款金额").append("</th>");
			table.append("<th style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;background-color:#f2f3f3;border:1px solid #cccccc'>").append("sap未清金额").append("</th>");
			table.append("<th style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;background-color:#f2f3f3;border:1px solid #cccccc'>").append("不符金额").append("</th>");
			table.append("<th style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;background-color:#f2f3f3;border:1px solid #cccccc'>").append("应收人员").append("</th>");
			table.append("</tr>");
			table.append("</thead>");
			table.append("<tbody>");
			for(Object ob:list){
				Object[] obj=(Object[])ob;
				table.append("<tr>");
				table.append("<td style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;border:1px solid #cccccc;padding-left:4px;'>").append(StringUtils.nullToString(obj[0])).append("</td>");
				table.append("<td style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;border:1px solid #cccccc;padding-left:4px;'>").append(StringUtils.nullToString(obj[1])).append("</td>");
				table.append("<td style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;border:1px solid #cccccc;padding-left:4px;'>").append(StringUtils.nullToString(obj[2])).append("</td>");
				table.append("<td style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;border:1px solid #cccccc;padding-left:4px;'>").append(StringUtils.nullToString(obj[3])).append("</td>");
				table.append("<td style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;border:1px solid #cccccc;padding-left:4px;text-align: right;'>").append(StringUtils.nullToString(obj[4])).append("</td>");
				table.append("<td style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;border:1px solid #cccccc;padding-left:4px;text-align: right;'>").append(StringUtils.nullToString(obj[5])).append("</td>");
				table.append("<td style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;border:1px solid #cccccc;padding-left:4px;text-align: right;'>").append(StringUtils.nullToString(obj[6])).append("</td>");
				table.append("<td style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;border:1px solid #cccccc;padding-left:4px;text-align: right;'>").append(StringUtils.nullToString(obj[7])).append("</td>");
				table.append("<td style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;border:1px solid #cccccc;padding-left:4px;text-align: right;'>").append(StringUtils.nullToString(obj[8])).append("</td>");
				table.append("<td style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;border:1px solid #cccccc;padding-left:4px;text-align: right;'>").append(StringUtils.nullToString(obj[9])).append("</td>");
				table.append("<td style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;border:1px solid #cccccc;padding-left:4px;text-align: right;'>").append(StringUtils.nullToString(obj[10])).append("</td>");
				table.append("<td style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;border:1px solid #cccccc;padding-left:4px;text-align: right;'>").append(StringUtils.nullToString(obj[11])).append("</td>");
				table.append("<td style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;border:1px solid #cccccc;padding-left:4px;text-align: center;'>").append(StringUtils.nullToString(obj[12])).append("</td>");
				table.append("</tr>");
			}
			table.append("</tbody>");
			table.append("</table>");
		}else{
			table.append("<h4>").append(index).append(".未收汇信息中以下外向交货单对账金额不符(杨晨负责):</h4>");
			table.append("<span>今天无异常</span>");
		}
		return table.toString();
	}
	
	/**
	 * 获取预收信息不平记录
	 * @return
	 */
	public String getPreReceiveError(int index){
		StringBuffer table = new StringBuffer();
		String sql = ""
			+"with acct as                                                                 "
			+" (select bukrs, kunnr, waers, sum(pre) as pre                                "
			+"    from (select bukrs,                                                      "
			+"                 kunnr,                                                      "
			+"                 waers,                                                      "
			+"                 case                                                        "
			+"                   when umskz = 'A' and bschl > '09' then                    "
			+"                    -wrbtr                                                   "
			+"                   when umskz = 'A' and bschl < '10' then                    "
			+"                    wrbtr                                                    "
			+"                   else                                                      "
			+"                    0                                                        "
			+"                 end as pre                                                  "
			+"            from bsid                                                        "
			+"           where umskz in ('A', ' ', '3'))                                   "
			+"   group by bukrs, kunnr, waers),                                            "
			+"usr as                                                                       "
			+" (select distinct pernr, ename from pa0001),                                 "
			+"cust as                                                                      "
			+" (select distinct kunnr, sortl, ktokd from kna1 where mandt = 800),          "
			+"prer as                                                                      "
			+" (select a.bukrs,                                                            "
			+"         a.kunnr,                                                            "
			+"         a.waers,                                                            "
			+"         sum(a.zcamount - case                                               "
			+"               when a.vbeln = ' ' then                                       "
			+"                nvl(b.amt, 0)                                                "
			+"               else                                                          "
			+"                a.zwoamt                                                     "
			+"             end) as yswq                                                    "
			+"    from zfmt0004 a                                                          "
			+"    left join (select t.zbelnr,                                              "
			+"                     t.zposnr_s,                                             "
			+"                     sum(to_number(to_char(nvl(t.zcamount * nvl(t.kursf, 1) /"
			+"                                               nvl(t.zpeinh, 1),             "
			+"                                               0),                           "
			+"                                           'FM9999999999.99'))) as amt       "
			+"                from zfmt0004 t                                              "
			+"               where t.zposnr_s != ' '                                       "
			+"                 and t.zposnr is not null                                    "
			+"                 and t.zposnr_s != '00000'                                   "
			+"                 and t.zrbl != 'A'                                           "
			+"                 and t.zdrs = 'C'                                            "
			+"                 and t.zcirs = 'C'                                           "
			+"               group by t.zbelnr, t.zposnr_s) b                              "
			+"      on a.zbelnr = b.zbelnr                                                 "
			+"     and a.zposnr = b.zposnr_s                                               "
			+"   where a.zrbl = 'A'                                                        "
			+"     and a.zcirs = 'C'                                                       "
			+"   group by a.bukrs, a.kunnr, a.waers)                                       "
			+"select acct.bukrs,                                                           "
			+"       acct.kunnr,                                                           "
			+"       cust.sortl,                                                           "
			+"       zae.ename as ae,                                                      "
			+"       zaa.ename as aa,                                                      "
			+"       zr.ename as zr,                                                       "
			+"       acct.waers,                                                           "
			+"       acct.pre,                                                             "
			+"       nvl(-prer.yswq, 0) as yswq,                                           "
			+"       acct.pre - nvl(-prer.yswq, 0) as buje                                 "
			+"  from acct                                                                  "
			+"  left join cust                                                             "
			+"    on acct.kunnr = cust.kunnr                                               "
			+"  left join zfmt0007 b                                                       "
			+"    on acct.bukrs = b.bukrs                                                  "
			+"   and acct.kunnr = b.kunnr                                                  "
			+"  left join usr zae                                                          "
			+"    on b.zae = zae.pernr                                                     "
			+"  left join usr zaa                                                          "
			+"    on b.zaa = zaa.pernr                                                     "
			+"  left join usr zr                                                           "
			+"    on b.zrname = zr.pernr                                                   "
			+"  left join prer                                                             "
			+"    on acct.bukrs = prer.bukrs                                               "
			+"   and acct.kunnr = prer.kunnr                                               "
			+"   and acct.waers = prer.waers                                               "
			+" where acct.bukrs in (1000, 7000, 7200, 7300)                                "
			+"  and (acct.pre - nvl(-prer.yswq, 0) < -1 or acct.pre - nvl(-prer.yswq, 0)>0)"
			+"   and cust.ktokd not in ('Z003', 'Z011')                                    "
			+" order by acct.bukrs, acct.kunnr, acct.waers                                 ";
		List<Object> list = findListBySql(sql.toString());
		if(null!=list && list.size()>0){
			table.append("<h4>").append(index).append(".以下客户SAP预收未清金额与OA不符,请确认:(杨晨负责)</h4>");
			table.append("<table style='border-collapse: collapse;border-spacing: 2px;border-color: gray;'>");
			table.append("<thead>");
			table.append("<tr>");
			table.append("<th style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;background-color:#f2f3f3;border:1px solid #cccccc'>").append("公司代码").append("</th>");
			table.append("<th style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;background-color:#f2f3f3;border:1px solid #cccccc'>").append("客户编号").append("</th>");
			table.append("<th style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;background-color:#f2f3f3;border:1px solid #cccccc'>").append("客户简称").append("</th>");
			table.append("<th style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;background-color:#f2f3f3;border:1px solid #cccccc'>").append("AE").append("</th>");
			table.append("<th style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;background-color:#f2f3f3;border:1px solid #cccccc'>").append("AA").append("</th>");
			table.append("<th style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;background-color:#f2f3f3;border:1px solid #cccccc'>").append("应收人员").append("</th>");
			table.append("<th style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;background-color:#f2f3f3;border:1px solid #cccccc'>").append("币种").append("</th>");
			table.append("<th style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;background-color:#f2f3f3;border:1px solid #cccccc'>").append("SAP预收未清").append("</th>");
			table.append("<th style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;background-color:#f2f3f3;border:1px solid #cccccc'>").append("OA预收未清").append("</th>");
			table.append("<th style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;background-color:#f2f3f3;border:1px solid #cccccc'>").append("不符金额").append("</th>");
			table.append("</tr>");
			table.append("</thead>");
			table.append("<tbody>");
			for(Object ob:list){
				Object[] obj=(Object[])ob;
				table.append("<tr>");
				table.append("<td style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;border:1px solid #cccccc;padding-left:4px;'>").append(StringUtils.nullToString(obj[0])).append("</td>");
				table.append("<td style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;border:1px solid #cccccc;padding-left:4px;'>").append(StringUtils.nullToString(obj[1])).append("</td>");
				table.append("<td style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;border:1px solid #cccccc;padding-left:4px;'>").append(StringUtils.nullToString(obj[2])).append("</td>");
				table.append("<td style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;border:1px solid #cccccc;padding-left:4px;'>").append(StringUtils.nullToString(obj[3])).append("</td>");
				table.append("<td style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;border:1px solid #cccccc;padding-left:4px;'>").append(StringUtils.nullToString(obj[4])).append("</td>");
				table.append("<td style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;border:1px solid #cccccc;padding-left:4px;'>").append(StringUtils.nullToString(obj[5])).append("</td>");
				table.append("<td style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;border:1px solid #cccccc;padding-left:4px;'>").append(StringUtils.nullToString(obj[6])).append("</td>");
				table.append("<td style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;border:1px solid #cccccc;padding-left:4px;text-align: right;'>").append(StringUtils.nullToString(obj[7])).append("</td>");
				table.append("<td style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;border:1px solid #cccccc;padding-left:4px;text-align: right;'>").append(StringUtils.nullToString(obj[8])).append("</td>");
				table.append("<td style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;border:1px solid #cccccc;padding-left:4px;text-align: right;'>").append(StringUtils.nullToString(obj[9])).append("</td>");
				table.append("</tr>");
			}
			table.append("</tbody>");
			table.append("</table>");
		}else{
			table.append("<h4>").append(index).append(".以下客户SAP预收未清金额与OA不符,请确认:(杨晨负责)</h4>");
			table.append("<span>今天无异常</span>");
		}
		return table.toString();
	}
	
	/**
	 * 获取付款条件未维护记录
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getZitemHasNotText(int index){
		StringBuffer table = new StringBuffer();
		StringBuffer sql = new StringBuffer();
		sql.append("select distinct kunnr,sortl,zlterm from zfmt0016 a where zlvtext =' ' and zlterm != ' ' order by zlterm");
		List<Object> list = findByHql(sql.toString());
		if(null!=list && list.size()>0){
			table.append("<h4>").append(index).append(".未收汇信息中以下付款条件代码没有维护相应说明:(林晓桦负责)</h4>");
			table.append("<table style='border-collapse: collapse;border-spacing: 2px;border-color: gray;'>");
			table.append("<thead>");
			table.append("<tr>");
			table.append("<th style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;background-color:#f2f3f3;border:1px solid #cccccc'>").append("客户编号").append("</th>");
			table.append("<th style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;background-color:#f2f3f3;border:1px solid #cccccc'>").append("客户简称").append("</th>");
			table.append("<th style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;background-color:#f2f3f3;border:1px solid #cccccc'>").append("付款条件代码").append("</th>");
			table.append("</tr>");
			table.append("</thead>");
			table.append("<tbody>");
			for(Object ob:list){
				Object[] obj=(Object[])ob;
				table.append("<tr>");
				table.append("<td style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;border:1px solid #cccccc;padding-left:4px;'>").append(StringUtils.nullToString(obj[0])).append("</td>");
				table.append("<td style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;border:1px solid #cccccc;padding-left:4px;'>").append(StringUtils.nullToString(obj[1])).append("</td>");
				table.append("<td style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;border:1px solid #cccccc;padding-left:4px;'>").append(StringUtils.nullToString(obj[2])).append("</td>");
				table.append("</tr>");
			}
			table.append("</tbody>");
			table.append("</table>");
		}else{
			table.append("<h4>").append(index).append(".未收汇信息中以下付款条件代码没有维护相应说明:(林晓桦负责)</h4>");
			table.append("<span>今天无异常</span>");
		}
		return table.toString();
	}

	@Override
	public void sendNoInvoiceEmail(String to, String cc) {
		logger.error("发送未开票提醒邮件给财务人员========start");
		int index = 1;
		StringBuffer content = new StringBuffer("<h4>此邮件为系统自动发送,请勿回复!</h4>");
		content
			.append(generateNoInvoiceEmail(index++)) // 发货后超过20天未开票
			.append(generateNoInvoiceGroupByCustomer(index++)) // 客户未开票金额汇总
			.append(generateSentWithoutInvoice(index++)) // 已发货未开票(分内外销)
			.append(generateMinusInvoice(index++)) // 应收未清汇总金额为负
			.append(generateZeroUnclearInvoice(index++)) // 外向交货单相同,正负金额相同,需手工对清
			.append(getARsUncompletedWork(index++)) // 到款信息状态不是CC状态，当前经办人是AR
			;
		MailUtil mailUtil = new MailUtil();
		String date = DateUtils.formatDate(new Date(),"yyyy/MM/dd");
		mailUtil.setHost("mail.leedarson.com");
		mailUtil.setUsername("IT@leedarson.com");
		mailUtil.setPassword("it612345");
		mailUtil.setFrom("IT@leedarson.com");
		mailUtil.setSubject("财务应收内部异常报告,发送日期:" + date+",版本号:20140603");
		mailUtil.setContent(content.toString());
		if(StringUtils.isNotBlank(cc)){
			mailUtil.setTocc(cc);
		}else{
			mailUtil.setTocc(findEmail(FC_INVOICE_ERROR,SENDTYPE_CC));
		}
		if(StringUtils.isNotBlank(to)){
			mailUtil.setTo(to);
		}else{
			mailUtil.setTo(findEmail(FC_INVOICE_ERROR,SENDTYPE_TO));
		}
		boolean result = mailUtil.send();
		logger.error("发送财务应收内部异常报告给财务人员,success=="+result);
	}
	
	/**
	 * 
	 * 20天开票提醒
	 * 
	 * @param index
	 * @return
	 */
	public String generateNoInvoiceEmail(int index){
		StringBuffer table = new StringBuffer();
		String sql = ""                                                                    
			+"with fpsl as                                                                 "
			+" (select tv1.vgbel,                                                          "
			+"         max(waerk) as waerk,                                                "
			+"         sum(case                                                            "
			+"               when tv2.fkart = 'ZF2' then                                   "
			+"                tv1.kzwi1                                                    "
			+"               else                                                          "
			+"                -tv1.kzwi1                                                   "
			+"             end) as amtb,                                                   "
			+"         sum(case                                                            "
			+"               when tv2.fkart = 'ZF2' then                                   "
			+"                tv1.fklmg                                                    "
			+"               else                                                          "
			+"                -tv1.fklmg                                                   "
			+"             end) as sumb                                                    "
			+"    from vbrp tv1                                                            "
			+"   inner join vbrk tv2                                                       "
			+"      on tv2.mandt = tv1.mandt                                               "
			+"     and tv2.vbeln = tv1.vbeln                                               "
			+"     and tv2.fkart in ('ZF2', 'S1')                                          "
			+"   where tv1.mandt =                                                         " + getClient()
			+"     and tv1.vgbel like '008%'                                               "
			+"   group by vgbel),                                                          "
			+"fhje as                                                                      "
			+" (select ta.vbeln,                                                           "
			+"         sum(tb.netpr * ta.lfimg / decode(tb.kpein, 0, 1, tb.kpein)) as amta,"
			+"         max(tb.waerk) as waerk,                                             "
			+"         max(tc.kunnr) as kunnr                                              "
			+"    from lips ta, vbap tb,vbak tc                                            "
			+"   where ta.mandt = tb.mandt                                                 "
			+"     and ta.vgbel = tb.vbeln                                                 "
			+"     and ta.vgpos = tb.posnr                                                 "
			+"     and tb.vbeln = tc.vbeln                                                 "
			+"     and ta.mandt =                                                          " + getClient()
			+"     and ta.vbeln like '008%'                                                "
			+"   group by ta.vbeln),                                                       "
			+"khlx as                                                                      "
			+" (select distinct a.vbeln                                                    "
			+"    from lips a                                                              "
			+"   inner join vbkd b                                                         "
			+"      on a.vgbel = b.vbeln                                                   "
			+"     and b.posnr = '000000'                                                  "
			+"   where b.ktgrd = 'Z4')                                                     "
			+"select a.vbeln,                                                              "
			+"       a.vkorg,                                                              "
			+"       a.wadat_ist,                                                          "
			+"       to_char(d.amta, '9999999999.99') || ' ' || d.waerk,                   "
			+"       d.kunnr,                                                              "
			+"       e.sortl                                                               "
			+"  from likp a                                                                "
			+"  left join fpsl b                                                           "
			+"    on a.vbeln = b.vgbel                                                     "
			+" inner join vbuk c                                                           "
			+"    on c.vbeln = a.vbeln                                                     "
			+"  left join fhje d                                                           "
			+"    on a.vbeln = d.vbeln                                                     "
			+"  left join kna1 e                                                           "
			+"    on d.kunnr = e.kunnr                                                     "
			+" inner join khlx f                                                           "
			+"    on a.vbeln = f.vbeln                                                     "
			+" where a.vbeln like '008%'                                                   "
			+"   and nvl(b.amtb, 0) = 0                                                    "
			+"   and c.wbstk = 'C'                                                         "
			+"   and d.amta > 0                                                            "
			+"   and a.vbeln not like '0084%'                                              "
			+"   and vkorg in (1000, 7000, 7200,7300)                                           "
			+"   and e.ktokd not in ('Z003', 'Z011')                                       "
			+"   and a.wadat_ist < to_char(sysdate - 20, 'yyyyMMdd')                       "
			+" order by a.wadat_ist asc                                                    ";
		List<Object> list = findListBySql(sql.toString());
		if(null!=list && list.size()>0){
			table.append("<h4>").append(index).append(".发货后超过20天未开票(杨晨负责):</h4>");
			table.append("<table style='border-collapse: collapse;border-spacing: 2px;border-color: gray;'>");
			table.append("<thead>");
			table.append("<tr>");
			table.append("<th style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;background-color:#f2f3f3;border:1px solid #cccccc'>").append("外向交货单").append("</th>");
			table.append("<th style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;background-color:#f2f3f3;border:1px solid #cccccc'>").append("出口单位").append("</th>");
			table.append("<th style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;background-color:#f2f3f3;border:1px solid #cccccc'>").append("出运日期").append("</th>");
			table.append("<th style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;background-color:#f2f3f3;border:1px solid #cccccc'>").append("客户编号").append("</th>");
			table.append("<th style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;background-color:#f2f3f3;border:1px solid #cccccc'>").append("客户简称").append("</th>");
			table.append("<th style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;background-color:#f2f3f3;border:1px solid #cccccc'>").append("发货金额").append("</th>");
			table.append("</tr>");
			table.append("</thead>");
			table.append("<tbody>");
			for(Object ob:list){
				Object[] obj=(Object[])ob;
				table.append("<tr>");
				table.append("<td style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;border:1px solid #cccccc;padding-left:4px;'>").append(StringUtils.nullToString(obj[0])).append("</td>");
				table.append("<td style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;border:1px solid #cccccc;padding-left:4px;'>").append(StringUtils.nullToString(obj[1])).append("</td>");
				table.append("<td style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;border:1px solid #cccccc;padding-left:4px;'>").append(StringUtils.nullToString(obj[2])).append("</td>");
				table.append("<td style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;border:1px solid #cccccc;padding-left:4px;'>").append(StringUtils.nullToString(obj[4])).append("</td>");
				table.append("<td style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;border:1px solid #cccccc;padding-left:4px;'>").append(StringUtils.nullToString(obj[5])).append("</td>");
				table.append("<td style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;border:1px solid #cccccc;padding-left:4px;text-align: right;'>").append(StringUtils.nullToString(obj[3])).append("</td>");
				table.append("</tr>");
			}
			table.append("</tbody>");
			table.append("</table>");
		}else{
			table.append("<h4>").append(index).append(".发货后超过20天未开票(杨晨负责):</h4>");
			table.append("<span>无未开票记录</span>");
		}
		return table.toString();
	}
	
	/**
	 * 
	 * 按客户汇总未开票金额
	 * 
	 * @param index
	 * @return
	 */
	public String generateNoInvoiceGroupByCustomer(int index){
		StringBuffer table = new StringBuffer();
		//@formatter:off
		String sql =
				  "with fpsl as                            "
				+ " (select tv1.vgbel,                     "
				+ "         max(waerk) as waerk,           "
				+ "         sum(case                       "
				+ "               when tv2.fkart = 'ZF2' then      "
				+ "                tv1.kzwi1               "
				+ "               else                     "
				+ "                -tv1.kzwi1              "
				+ "             end) as amtb,              "
				+ "         sum(case                       "
				+ "               when tv2.fkart = 'ZF2' then      "
				+ "                tv1.fklmg               "
				+ "               else                     "
				+ "                -tv1.fklmg              "
				+ "             end) as sumb               "
				+ "    from vbrp tv1                       "
				+ "   inner join vbrk tv2                  "
				+ "      on tv2.mandt = tv1.mandt          "
				+ "     and tv2.vbeln = tv1.vbeln          "
				+ "     and tv2.fkart in ('ZF2', 'S1')     "
				+ "   where tv1.mandt = "
				+ getClient()
				+ "     and tv1.vgbel like '008%'          "
				+ "   group by vgbel),                     "
				+ "fhje as    "
				+ " (select ta.vbeln,                      "
				+ "         td.kunnr,                      "
				+ "         to_number(to_char(tb.netpr * ta.lfimg / decode(tb.kpein, 0, 1, tb.kpein),'9999999999.99')) as amta,                   "
				+ "         to_number(to_char(tb.netpr * ta.lfimg*nvl(tc.kursk,0) / decode(tb.kpein, 0, 1, tb.kpein),'9999999999.99')) as amtb,   "
				+ "         tb.waerk                       "
				+ "    from lips ta, vbap tb,vbkd tc ,vbak td     "
				+ "   where ta.mandt = tb.mandt            "
				+ "     and ta.vgbel = tb.vbeln            "
				+ "     and ta.vgpos = tb.posnr            "
				+ "     and tb.vbeln = tc.vbeln and tc.posnr = '000000' and tb.vbeln=td.vbeln    "
				+ "     and ta.mandt = "
				+ getClient()
				+ "     and ta.vbeln like '008%'),         "
				+ "detail as( "
				+ "select c.vkorg,a.kunnr,b.sortl,a.vbeln,a.amta,a.waerk,a.amtb from fhje a    "
				+ "inner join kna1 b on a.kunnr=b.kunnr    "
				+ "inner join likp c on a.vbeln=c.vbeln    "
				+ "inner join vbuk d on a.vbeln=d.vbeln    "
				+ "left join fpsl e on a.vbeln =e.vgbel    "
				+ "           "
				+ "where b.ktokd not in('Z003','Z011')     "
				+ "and c.vkorg in ('1000','7000','7200','7300')   "
				+ "and d.wbstk = 'C'                       "
				+ "and a.amta >0                           "
				+ "and a.vbeln not like '0084%'            "
				+ "and nvl(e.amtb, 0) = 0                  "
				+ ")          "
				+ "select vkorg,kunnr,sortl,sum(amta)||'  '||waerk from detail          "
				+ "group by vkorg, kunnr, sortl,waerk      "
				+ "union all  "
				+ "select '合计','','',sum(amtb)||'  CNY' from detail                   "
				+ "order by vkorg,kunnr,sortl              ";
		//@formatter:on
		List<Object> list = findListBySql(sql);
		if(null!=list && list.size()>0){
			table.append("<h4>").append(index).append(".客户未开票金额汇总(杨晨负责):</h4>");
			table.append("<table style='border-collapse: collapse;border-spacing: 2px;border-color: gray;'>");
			table.append("<thead>");
			table.append("<tr>");
			table.append("<th style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;background-color:#f2f3f3;border:1px solid #cccccc'>").append("公司代码").append("</th>");
			table.append("<th style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;background-color:#f2f3f3;border:1px solid #cccccc'>").append("客户编号").append("</th>");
			table.append("<th style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;background-color:#f2f3f3;border:1px solid #cccccc'>").append("客户简称").append("</th>");
			table.append("<th style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;background-color:#f2f3f3;border:1px solid #cccccc'>").append("发货金额").append("</th>");
			table.append("</tr>");
			table.append("</thead>");
			table.append("<tbody>");
			for(Object ob:list){
				Object[] obj=(Object[])ob;
				table.append("<tr>");
				if("合计".equals(StringUtils.nullToString(obj[0]))){
					table.append("<td style='font-size:12px;font-weight:bolder; white-space:nowrap; display: table-cell;vertical-align: inherit;border:1px solid #cccccc;padding-left:4px;'>").append(StringUtils.nullToString(obj[0])).append("</td>");
					table.append("<td colspan='3' style='font-size:12px;font-weight:bolder; white-space:nowrap; display: table-cell;vertical-align: inherit;border:1px solid #cccccc;padding-left:4px;text-align: right;'>").append(StringUtils.nullToString(obj[3])).append("</td>");
				}else{
					table.append("<td style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;border:1px solid #cccccc;padding-left:4px;'>").append(StringUtils.nullToString(obj[0])).append("</td>");
					table.append("<td style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;border:1px solid #cccccc;padding-left:4px;'>").append(StringUtils.nullToString(obj[1])).append("</td>");
					table.append("<td style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;border:1px solid #cccccc;padding-left:4px;'>").append(StringUtils.nullToString(obj[2])).append("</td>");
					table.append("<td style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;border:1px solid #cccccc;padding-left:4px;text-align: right;'>").append(StringUtils.nullToString(obj[3])).append("</td>");
				}
				table.append("</tr>");
			}
			table.append("</tbody>");
			table.append("</table>");
		}else{
			table.append("<h4>").append(index).append(".客户未开票金额汇总:</h4>");
			table.append("<span>暂无未开票记录</span>");
		}
		return table.toString();
	}
	
	/**
	 * 
	 * 已发货未开票数据
	 * 
	 * @param index
	 * @return
	 */
	public String generateSentWithoutInvoice(int index){
		StringBuffer table = new StringBuffer();
		//@formatter:off
		String nxsql = ""
			+"with fpsl as                                                                 "
			+" (select tv1.vgbel,                                                          "
			+"         max(waerk) as waerk,                                                "
			+"         sum(case                                                            "
			+"               when tv2.fkart = 'ZF2' then                                   "
			+"                tv1.kzwi1                                                    "
			+"               else                                                          "
			+"                -tv1.kzwi1                                                   "
			+"             end) as amtb,                                                   "
			+"         sum(case                                                            "
			+"               when tv2.fkart = 'ZF2' then                                   "
			+"                tv1.fklmg                                                    "
			+"               else                                                          "
			+"                -tv1.fklmg                                                   "
			+"             end) as sumb                                                    "
			+"    from vbrp tv1                                                            "
			+"   inner join vbrk tv2                                                       "
			+"      on tv2.mandt = tv1.mandt                                               "
			+"     and tv2.vbeln = tv1.vbeln                                               "
			+"     and tv2.fkart in ('ZF2', 'S1')                                          "
			+"     and tv1.vgbel like '008%'                                               "
			+"   group by vgbel),                                                          "
			+"fhje as                                                                      "
			+" (select ta.vbeln,                                                           "
			+"         sum(tb.netpr * ta.lfimg / decode(tb.kpein, 0, 1, tb.kpein)) as amta,"
			+"         max(tb.waerk) as waerk,                                             "
			+"         max(tc.kunnr) as kunnr                                              "
			+"    from lips ta, vbap tb, vbak tc                                           "
			+"   where ta.mandt = tb.mandt                                                 "
			+"     and ta.vgbel = tb.vbeln                                                 "
			+"     and ta.vgpos = tb.posnr                                                 "
			+"     and tb.vbeln = tc.vbeln                                                 "
			+"     and ta.vbeln like '008%'                                                "
			+"   group by ta.vbeln),                                                       "
			+"khlx as                                                                      "
			+" (select distinct a.vbeln, b.ktgrd                                           "
			+"    from lips a                                                              "
			+"   inner join vbkd b                                                         "
			+"      on a.vgbel = b.vbeln                                                   "
			+"     and b.posnr = '000000')                                                 "
			+"select a.vbeln,                                                              "
			+"       a.wadat_ist,                                                          "
			+"       a.vkorg,                                                              "
			+"       d.kunnr,                                                              "
			+"       e.sortl,                                                              "
			+"       to_char(d.amta, '9999999999.99') || ' ' || d.waerk                    "
			+"  from likp a                                                                "
			+"  left join fpsl b                                                           "
			+"    on a.vbeln = b.vgbel                                                     "
			+" inner join vbuk c                                                           "
			+"    on c.vbeln = a.vbeln                                                     "
			+"  left join fhje d                                                           "
			+"    on a.vbeln = d.vbeln                                                     "
			+"  left join kna1 e                                                           "
			+"    on d.kunnr = e.kunnr                                                     "
			+" inner join khlx f                                                           "
			+"    on a.vbeln = f.vbeln                                                     "
			+" where a.vbeln like '008%'                                                   "
			+"   and nvl(b.amtb, 0) = 0                                                    "
			+"   and c.wbstk = 'C'                                                         "
			+"   and d.amta > 0                                                            "
			+"   and a.vbeln not like '0084%'                                              "
			+"   and vkorg in (1000, 7000, 7200,7300)                                           "
			+"   and e.ktokd not in ('Z003', 'Z011')                                       "
			+"   and f.ktgrd != 'Z4'                                                       "
			+"   and substr(a.wadat_ist, 0, 6) < to_char(sysdate, 'yyyyMM')                "
			+" order by a.wadat_ist asc                                                    ";
		String wxsql = ""
			+"with fpsl as                                                                 "
		    +" (select tv1.vgbel,                                                          "
		    +"         max(waerk) as waerk,                                                "
		    +"         sum(case                                                            "
		    +"               when tv2.fkart = 'ZF2' then                                   "
		    +"                tv1.kzwi1                                                    "
		    +"               else                                                          "
		    +"                -tv1.kzwi1                                                   "
		    +"             end) as amtb,                                                   "
		    +"         sum(case                                                            "
		    +"               when tv2.fkart = 'ZF2' then                                   "
		    +"                tv1.fklmg                                                    "
		    +"               else                                                          "
		    +"                -tv1.fklmg                                                   "
		    +"             end) as sumb                                                    "
		    +"    from vbrp tv1                                                            "
		    +"   inner join vbrk tv2                                                       "
		    +"      on tv2.mandt = tv1.mandt                                               "
		    +"     and tv2.vbeln = tv1.vbeln                                               "
		    +"     and tv2.fkart in ('ZF2', 'S1')                                          "
		    +"     and tv1.vgbel like '008%'                                               "
		    +"   group by vgbel),                                                          "
		    +"fhje as                                                                      "
		    +" (select ta.vbeln,                                                           "
		    +"         sum(tb.netpr * ta.lfimg / decode(tb.kpein, 0, 1, tb.kpein)) as amta,"
		    +"         max(tb.waerk) as waerk,                                             "
		    +"         max(tc.kunnr) as kunnr                                              "
		    +"    from lips ta, vbap tb, vbak tc                                           "
		    +"   where ta.mandt = tb.mandt                                                 "
		    +"     and ta.vgbel = tb.vbeln                                                 "
		    +"     and ta.vgpos = tb.posnr                                                 "
		    +"     and tb.vbeln = tc.vbeln                                                 "
		    +"     and ta.vbeln like '008%'                                                "
		    +"   group by ta.vbeln),                                                       "
		    +"khlx as                                                                      "
		    +" (select distinct a.vbeln, b.ktgrd                                           "
		    +"    from lips a                                                              "
		    +"   inner join vbkd b                                                         "
		    +"      on a.vgbel = b.vbeln                                                   "
		    +"     and b.posnr = '000000')                                                 "
		    +"select a.vbeln,                                                              "
		    +"       nvl(g.zyjcq, '99991231'),                                             "
		    +"       a.vkorg,                                                              "
		    +"       d.kunnr,                                                              "
		    +"       e.sortl,                                                              "
		    +"       to_char(d.amta, '9999999999.99') || ' ' || d.waerk                    "
		    +"  from likp a                                                                "
		    +"  left join fpsl b                                                           "
		    +"    on a.vbeln = b.vgbel                                                     "
		    +" inner join vbuk c                                                           "
		    +"    on c.vbeln = a.vbeln                                                     "
		    +"  left join fhje d                                                           "
		    +"    on a.vbeln = d.vbeln                                                     "
		    +"  left join kna1 e                                                           "
		    +"    on d.kunnr = e.kunnr                                                     "
		    +" inner join khlx f                                                           "
		    +"    on a.vbeln = f.vbeln                                                     "
		    +"  inner join zsdt0036 g                                                      "
		    +"    on a.vbeln = g.vbeln                                                     "
		    +" where a.vbeln like '008%'                                                   "
		    +"   and nvl(b.amtb, 0) = 0                                                    "
		    +"   and c.wbstk = 'C'                                                         "
		    +"   and d.amta > 0                                                            "
		    +"   and a.vbeln not like '0084%'                                              "
		    +"   and vkorg in (1000, 7000, 7200,7300)                                           "
		    +"   and e.ktokd not in ('Z003', 'Z011')                                       "
		    +"   and f.ktgrd = 'Z4'                                                        "
		    +"   and nvl(g.zyjcq, '99991231')<to_char(sysdate, 'yyyyMMdd')                 "
		    +" order by nvl(g.zyjcq, '99991231') asc                                       ";
		//@formatter:on
		table.append("<h4>").append(index).append(".已发货未开票(杨晨负责):</h4>");
		//内销
		List<Object> listnx = findListBySql(nxsql);
		if(null!=listnx && listnx.size()>0){
			table.append("<h4>内销：跨月SAP未开票:</h4>");
			table.append("<table style='border-collapse: collapse;border-spacing: 2px;border-color: gray;'>");
			table.append("<thead>");
			table.append("<tr>");
			table.append("<th style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;background-color:#f2f3f3;border:1px solid #cccccc'>").append("外向交货单").append("</th>");
			table.append("<th style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;background-color:#f2f3f3;border:1px solid #cccccc'>").append("发货日期").append("</th>");
			table.append("<th style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;background-color:#f2f3f3;border:1px solid #cccccc'>").append("公司代码").append("</th>");
			table.append("<th style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;background-color:#f2f3f3;border:1px solid #cccccc'>").append("客户编号").append("</th>");
			table.append("<th style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;background-color:#f2f3f3;border:1px solid #cccccc'>").append("客户简称").append("</th>");
			table.append("<th style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;background-color:#f2f3f3;border:1px solid #cccccc'>").append("发货金额").append("</th>");
			table.append("</tr>");
			table.append("</thead>");
			table.append("<tbody>");
			for(Object ob:listnx){
				Object[] obj=(Object[])ob;
				table.append("<tr>");
				table.append("<td style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;border:1px solid #cccccc;padding-left:4px;'>").append(StringUtils.nullToString(obj[0])).append("</td>");
				table.append("<td style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;border:1px solid #cccccc;padding-left:4px;'>").append(StringUtils.nullToString(obj[1])).append("</td>");
				table.append("<td style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;border:1px solid #cccccc;padding-left:4px;'>").append(StringUtils.nullToString(obj[2])).append("</td>");
				table.append("<td style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;border:1px solid #cccccc;padding-left:4px;'>").append(StringUtils.nullToString(obj[3])).append("</td>");
				table.append("<td style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;border:1px solid #cccccc;padding-left:4px;'>").append(StringUtils.nullToString(obj[4])).append("</td>");
				table.append("<td style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;border:1px solid #cccccc;padding-left:4px;text-align: right;'>").append(StringUtils.nullToString(obj[5])).append("</td>");
				table.append("</tr>");
			}
			table.append("</tbody>");
			table.append("</table>");
		}else{
			table.append("<span>暂无内销未开票记录</span>");
		}
		//外销
		List<Object> listwx = findListBySql(wxsql);
		if(null!=listwx && listwx.size()>0){
			table.append("<h4>外销：早于船期SAP未开票:</h4>");
			table.append("<table style='border-collapse: collapse;border-spacing: 2px;border-color: gray;'>");
			table.append("<thead>");
			table.append("<tr>");
			table.append("<th style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;background-color:#f2f3f3;border:1px solid #cccccc'>").append("外向交货单").append("</th>");
			table.append("<th style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;background-color:#f2f3f3;border:1px solid #cccccc'>").append("发货日期").append("</th>");
			table.append("<th style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;background-color:#f2f3f3;border:1px solid #cccccc'>").append("公司代码").append("</th>");
			table.append("<th style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;background-color:#f2f3f3;border:1px solid #cccccc'>").append("客户编号").append("</th>");
			table.append("<th style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;background-color:#f2f3f3;border:1px solid #cccccc'>").append("客户简称").append("</th>");
			table.append("<th style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;background-color:#f2f3f3;border:1px solid #cccccc'>").append("发货金额").append("</th>");
			table.append("</tr>");
			table.append("</thead>");
			table.append("<tbody>");
			for(Object ob:listwx){
				Object[] obj=(Object[])ob;
				table.append("<tr>");
				table.append("<td style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;border:1px solid #cccccc;padding-left:4px;'>").append(StringUtils.nullToString(obj[0])).append("</td>");
				table.append("<td style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;border:1px solid #cccccc;padding-left:4px;'>").append(StringUtils.nullToString(obj[1])).append("</td>");
				table.append("<td style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;border:1px solid #cccccc;padding-left:4px;'>").append(StringUtils.nullToString(obj[2])).append("</td>");
				table.append("<td style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;border:1px solid #cccccc;padding-left:4px;'>").append(StringUtils.nullToString(obj[3])).append("</td>");
				table.append("<td style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;border:1px solid #cccccc;padding-left:4px;'>").append(StringUtils.nullToString(obj[4])).append("</td>");
				table.append("<td style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;border:1px solid #cccccc;padding-left:4px;text-align: right;'>").append(StringUtils.nullToString(obj[5])).append("</td>");
				table.append("</tr>");
			}
			table.append("</tbody>");
			table.append("</table>");
		}else{
			table.append("<span>暂无外销未开票记录</span>");
		}
		return table.toString();
	}
	
	/**
	 * 应收账款为负的数据
	 * DP-255 
	 * @return
	 */
	public String generateMinusInvoice(int index){
		StringBuffer table = new StringBuffer();
		String sql = ""
			+"with base as                                                                "
			+" (select p.vgbel,                                                           "
			+"         sum(case                                                           "
			+"               when bschl > '09' then                                       "
			+"                -wrbtr                                                      "
			+"               else                                                         "
			+"                wrbtr                                                       "
			+"             end) as wrbtr,                                                 "
			+"         sum(case                                                           "
			+"               when bschl > '09' then                                       "
			+"                -wrbtr                                                      "
			+"               else                                                         "
			+"                0                                                           "
			+"             end) as amt,                                                   "
			+"         d.zuonr,                                                           "
			+"         d.waers                                                            "
			+"    from bsid d                                                             "
			+"   inner join (select distinct vgbel, vbeln from vbrp) p                    "
			+"      on p.vbeln = d.zuonr                                                  "
			+"   where d.mandt ="+getClient()
			+"     and d.umskz = ' '                                                      "
			+"   group by vgbel, waers, zuonr),                                           "
			+"grp as                                                                      "
			+" (select vgbel,                                                             "
			+"         sum(wrbtr) as wrbtr,                                               "
			+"         listagg(zuonr, ',') within group(order by zuonr) as zuonr,         "
			+"         waers                                                              "
			+"    from base                                                               "
			+"   where wrbtr < 0                                                          "
			+"   group by vgbel, waers)                                                   "
			+"select tf.ename,                                                            "
			+"       ta.vgbel,                                                            "
			+"       ta.zuonr,                                                            "
			+"       to_char(to_date(tb.wadat_ist, 'yyyyMMdd'), 'yyyy/MM/dd'),            "
			+"       tb.vkorg,                                                            "
			+"       tc.kunnr,                                                            "
			+"       td.sortl,                                                            "
			+"       trim(to_char(ta.wrbtr, '999999990.99')) || ' ' || ta.waers           "
			+"  from grp ta                                                               "
			+"  left join likp tb                                                         "
			+"    on ta.vgbel = tb.vbeln                                                  "
			+"  left join (select distinct ta.vbeln, tc.kunnr                             "
			+"               from lips ta, vbap tb, vbak tc                               "
			+"              where ta.mandt = tb.mandt                                     "
			+"                and tb.vbeln = tc.vbeln                                     "
			+"                and ta.vgbel = tb.vbeln                                     "
			+"                and ta.vgpos = tb.posnr                                     "
			+"                and ta.mandt = 800                                          "
			+"                and ta.vbeln like '008%') tc                                "
			+"    on ta.vgbel = tc.vbeln                                                  "
			+"  left join kna1 td                                                         "
			+"    on tc.kunnr = td.kunnr                                                  "
			+"  left join zfmt0007 te                                                     "
			+"    on te.kunnr = tc.kunnr                                                  "
			+"   and te.bukrs = tb.vkorg                                                  "
			+"  left join (select distinct pernr, ename from pa0001) tf                   "
			+"    on te.zrname = tf.pernr                                                 "
			+" where tb.kunnr != 'WL1000020'                                              "
			+"   and tb.vkorg in (1000, 7000, 7200,7300)                                       "
			+" order by ename, tb.wadat_ist                                               ";
		List<Object> list = findListBySql(sql);
		if(null!=list && list.size()>0){
			table.append("<h4>").append(index).append(".应收未清汇总金额为负,请财务处理:</h4>");
			table.append("<table style='border-collapse: collapse;border-spacing: 2px;border-color: gray;'>");
			table.append("<thead>");
			table.append("<tr>");
			table.append("<th style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;background-color:#f2f3f3;border:1px solid #cccccc'>").append("应收人员").append("</th>");
			table.append("<th style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;background-color:#f2f3f3;border:1px solid #cccccc'>").append("外向交货单").append("</th>");
			table.append("<th style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;background-color:#f2f3f3;border:1px solid #cccccc'>").append("发票号").append("</th>");
			table.append("<th style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;background-color:#f2f3f3;border:1px solid #cccccc'>").append("发货日期").append("</th>");
			table.append("<th style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;background-color:#f2f3f3;border:1px solid #cccccc'>").append("公司代码").append("</th>");
			table.append("<th style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;background-color:#f2f3f3;border:1px solid #cccccc'>").append("客户编号").append("</th>");
			table.append("<th style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;background-color:#f2f3f3;border:1px solid #cccccc'>").append("客户简称").append("</th>");
			table.append("<th style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;background-color:#f2f3f3;border:1px solid #cccccc'>").append("未清金额").append("</th>");
			table.append("</tr>");
			table.append("</thead>");
			table.append("<tbody>");
			for(Object ob:list){
				Object[] obj=(Object[])ob;
				table.append("<tr>");
				table.append("<td style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;border:1px solid #cccccc;padding-left:4px;'>").append(StringUtils.nullToString(obj[0])).append("</td>");
				table.append("<td style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;border:1px solid #cccccc;padding-left:4px;'>").append(StringUtils.nullToString(obj[1])).append("</td>");
				table.append("<td style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;border:1px solid #cccccc;padding-left:4px;'>").append(StringUtils.nullToString(obj[2])).append("</td>");
				table.append("<td style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;border:1px solid #cccccc;padding-left:4px;'>").append(StringUtils.nullToString(obj[3])).append("</td>");
				table.append("<td style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;border:1px solid #cccccc;padding-left:4px;'>").append(StringUtils.nullToString(obj[4])).append("</td>");
				table.append("<td style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;border:1px solid #cccccc;padding-left:4px;'>").append(StringUtils.nullToString(obj[5])).append("</td>");
				table.append("<td style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;border:1px solid #cccccc;padding-left:4px;'>").append(StringUtils.nullToString(obj[6])).append("</td>");
				table.append("<td style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;border:1px solid #cccccc;padding-left:4px;text-align: right;'>").append(StringUtils.nullToString(obj[7])).append("</td>");
				table.append("</tr>");
			}
			table.append("</tbody>");
			table.append("</table>");
		}else{
			table.append("<h4>").append(index).append(".应收未清汇总金额为负,请财务处理:</h4>");
			table.append("<span>暂无异常</span>");
		}
		return table.toString();
	}
	
	/**
	 * 无法自动对清的数据
	 * DP-255 
	 * @return
	 */
	public String generateZeroUnclearInvoice(int index){
		StringBuffer table = new StringBuffer();
		String sql = ""
			+"with base as                                                                "
			+" (select p.vgbel,                                                           "
			+"         sum(case                                                           "
			+"               when bschl > '09' then                                       "
			+"                -wrbtr                                                      "
			+"               else                                                         "
			+"                wrbtr                                                       "
			+"             end) as wrbtr,                                                 "
			+"         d.zuonr,                                                           "
			+"         d.waers                                                            "
			+"    from bsid d                                                             "
			+"   inner join (select distinct vgbel, vbeln from vbrp) p                    "
			+"      on p.vbeln = d.zuonr                                                  "
			+"   where d.mandt = 800                                                      "
			+"     and d.umskz = ' '                                                      "
			+"   group by vgbel, waers, zuonr),                                           "
			+"grp as                                                                      "
			+" (select vgbel,                                                             "
			+"         sum(wrbtr) as wrbtr,                                               "
			+"         listagg(zuonr, ',') within group(order by zuonr) as zuonr,         "
			+"         waers                                                              "
			+"    from base                                                               "
			+"   where wrbtr = 0                                                          "
			+"   group by vgbel, waers)                                                   "
			+"select tf.ename,                                                            "
			+"       ta.vgbel,                                                            "
			+"       ta.zuonr,                                                            "
			+"       to_char(to_date(tb.wadat_ist, 'yyyyMMdd'), 'yyyy/MM/dd'),            "
			+"       tb.vkorg,                                                            "
			+"       tc.kunnr,                                                            "
			+"       td.sortl,                                                            "
			+"       trim(to_char(ta.wrbtr, '999999990.99')) || ' ' || ta.waers           "
			+"  from grp ta                                                               "
			+"  left join likp tb                                                         "
			+"    on ta.vgbel = tb.vbeln                                                  "
			+"  left join (select distinct ta.vbeln, tc.kunnr                             "
			+"               from lips ta, vbap tb, vbak tc                               "
			+"              where ta.mandt = tb.mandt                                     "
			+"                and tb.vbeln = tc.vbeln                                     "
			+"                and ta.vgbel = tb.vbeln                                     "
			+"                and ta.vgpos = tb.posnr                                     "
			+"                and ta.mandt = 800                                          "
			+"                and ta.vbeln like '008%') tc                                "
			+"    on ta.vgbel = tc.vbeln                                                  "
			+"  left join kna1 td                                                         "
			+"    on tc.kunnr = td.kunnr                                                  "
			+"  left join zfmt0007 te                                                     "
			+"    on te.kunnr = tc.kunnr                                                  "
			+"   and te.bukrs = tb.vkorg                                                  "
			+"  left join (select distinct pernr, ename from pa0001) tf                   "
			+"    on te.zrname = tf.pernr                                                 "
			+" where tb.kunnr != 'WL1000020'                                              "
			+"   and tb.vkorg in (1000, 7000, 7200,7300)                                       "
			+" order by ename, tb.wadat_ist                                               ";
		List<Object> list = findListBySql(sql);
		if(null!=list && list.size()>0){
			table.append("<h4>").append(index).append(".外向交货单相同,正负金额相同,需手工对清:</h4>");
			table.append("<table style='border-collapse: collapse;border-spacing: 2px;border-color: gray;'>");
			table.append("<thead>");
			table.append("<tr>");
			table.append("<th style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;background-color:#f2f3f3;border:1px solid #cccccc'>").append("应收人员").append("</th>");
			table.append("<th style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;background-color:#f2f3f3;border:1px solid #cccccc'>").append("外向交货单").append("</th>");
			table.append("<th style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;background-color:#f2f3f3;border:1px solid #cccccc'>").append("发票号").append("</th>");
			table.append("<th style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;background-color:#f2f3f3;border:1px solid #cccccc'>").append("发货日期").append("</th>");
			table.append("<th style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;background-color:#f2f3f3;border:1px solid #cccccc'>").append("公司代码").append("</th>");
			table.append("<th style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;background-color:#f2f3f3;border:1px solid #cccccc'>").append("客户编号").append("</th>");
			table.append("<th style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;background-color:#f2f3f3;border:1px solid #cccccc'>").append("客户简称").append("</th>");
			table.append("</tr>");
			table.append("</thead>");
			table.append("<tbody>");
			for(Object ob:list){
				Object[] obj=(Object[])ob;
				table.append("<tr>");
				table.append("<td style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;border:1px solid #cccccc;padding-left:4px;'>").append(StringUtils.nullToString(obj[0])).append("</td>");
				table.append("<td style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;border:1px solid #cccccc;padding-left:4px;'>").append(StringUtils.nullToString(obj[1])).append("</td>");
				table.append("<td style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;border:1px solid #cccccc;padding-left:4px;'>").append(StringUtils.nullToString(obj[2])).append("</td>");
				table.append("<td style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;border:1px solid #cccccc;padding-left:4px;'>").append(StringUtils.nullToString(obj[3])).append("</td>");
				table.append("<td style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;border:1px solid #cccccc;padding-left:4px;'>").append(StringUtils.nullToString(obj[4])).append("</td>");
				table.append("<td style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;border:1px solid #cccccc;padding-left:4px;'>").append(StringUtils.nullToString(obj[5])).append("</td>");
				table.append("<td style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;border:1px solid #cccccc;padding-left:4px;'>").append(StringUtils.nullToString(obj[6])).append("</td>");
				table.append("</tr>");
			}
			table.append("</tbody>");
			table.append("</table>");
		}else{
			table.append("<h4>").append(index).append(".外向交货单相同,正负金额相同,需手工对清:</h4>");
			table.append("<span>暂无异常</span>");
		}
		return table.toString();
	}
	
	/**
	 * 获取不是CC状态，业务员是AR的记录
	 * @since DP-272 : 未CC的，业务员是AR的，提醒出来
	 * @return
	 */
	public String getARsUncompletedWork(int index){
		StringBuffer table = new StringBuffer();
		StringBuffer sql = new StringBuffer();
		sql.append("select c.ename,a.bukrs,a.zbelnr,a.kunnr,a.sortl,a.zcamount||' '||a.waers,to_char(to_date(a.bldat,'yyyyMMdd'),'yyyy/MM/dd'),a.zdrs||a.zcirs,a.ztext from zfmt0003 a ")
			.append(" inner join (select distinct zrname from zfmt0007 where zrname!=' ')b on a.zsname=b.zrname")
			.append(" inner join (select distinct pernr,ename from pa0001) c on a.zsname=c.pernr")
			.append(" where a.zdrs!='C'")
			.append(" and a.bukrs in ('1000','7000','7200','7300')")
			.append(" order by c.ename,a.bldat");
		List<Object> list = findListBySql(sql.toString());
		if(null!=list && list.size()>0){
			List<Object[]> listFinal = new ArrayList<Object[]>();
			Object[] temp = null;
			for(Object ob:list){
				Object[] obj = (Object[])ob;
				int length = obj.length;
				temp = new Object[length];
				for(int i=0;i<length;i++){
					if(i==7){//状态
						if("PP".equals(StringUtils.nullToString(obj[i]))){
							temp[i]="待登记";
						}else if("PF".equals(StringUtils.nullToString(obj[i]))){
							temp[i]="待生成凭证";
						}else if("FF".equals(StringUtils.nullToString(obj[i]))){
							temp[i]="待生成凭证";
						}else if("PC".equals(StringUtils.nullToString(obj[i]))){
							temp[i]="待分解";
						}else{
							temp[i]="";
						}
					}else{
						temp[i]=obj[i];
					}
				}
				listFinal.add(temp);
			}
			
			table.append("<h4>").append(index).append(".应收人员经办工作提醒:</h4>");
			table.append("<table style='border-collapse: collapse;border-spacing: 2px;border-color: gray;'>");
			table.append("<thead>");
			table.append("<tr>");
			table.append("<th style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;background-color:#f2f3f3;border:1px solid #cccccc'>").append("应收人员").append("</th>");
			table.append("<th style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;background-color:#f2f3f3;border:1px solid #cccccc'>").append("公司代码").append("</th>");
			table.append("<th style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;background-color:#f2f3f3;border:1px solid #cccccc'>").append("流水号").append("</th>");
			table.append("<th style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;background-color:#f2f3f3;border:1px solid #cccccc'>").append("客户编号").append("</th>");
			table.append("<th style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;background-color:#f2f3f3;border:1px solid #cccccc'>").append("客户简称").append("</th>");
			table.append("<th style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;background-color:#f2f3f3;border:1px solid #cccccc'>").append("到款金额").append("</th>");
			table.append("<th style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;background-color:#f2f3f3;border:1px solid #cccccc'>").append("到款日期").append("</th>");
			table.append("<th style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;background-color:#f2f3f3;border:1px solid #cccccc'>").append("状态").append("</th>");
			table.append("<th style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;background-color:#f2f3f3;border:1px solid #cccccc'>").append("备注").append("</th>");
			table.append("</tr>");
			table.append("</thead>");
			table.append("<tbody>");
			for(Object[] obj:listFinal){
				table.append("<tr>");
				table.append("<td style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;border:1px solid #cccccc;padding-left:4px;'>").append(StringUtils.nullToString(obj[0])).append("</td>");
				table.append("<td style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;border:1px solid #cccccc;padding-left:4px;'>").append(StringUtils.nullToString(obj[1])).append("</td>");
				table.append("<td style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;border:1px solid #cccccc;padding-left:4px;'>").append(StringUtils.nullToString(obj[2])).append("</td>");
				table.append("<td style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;border:1px solid #cccccc;padding-left:4px;'>").append(StringUtils.nullToString(obj[3])).append("</td>");
				table.append("<td style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;border:1px solid #cccccc;padding-left:4px;'>").append(StringUtils.nullToString(obj[4])).append("</td>");
				table.append("<td style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;border:1px solid #cccccc;padding-left:4px;text-align: right;'>").append(StringUtils.nullToString(obj[5])).append("</td>");
				table.append("<td style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;border:1px solid #cccccc;padding-left:4px;'>").append(StringUtils.nullToString(obj[6])).append("</td>");
				table.append("<td style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;border:1px solid #cccccc;padding-left:4px;'>").append(StringUtils.nullToString(obj[7])).append("</td>");
				table.append("<td style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;border:1px solid #cccccc;padding-left:4px;'>").append(StringUtils.nullToString(obj[8])).append("</td>");
				table.append("</tr>");
			}
			table.append("</tbody>");
			table.append("</table>");
		}else{
			table.append("<h4>").append(index).append(".应收人员经办工作提醒:</h4>");
			table.append("<span>今天无异常</span>");
		}
		return table.toString();
	}
	
	/**
	 * 生成表格HTML
	 * @param title
	 * @param header
	 * @param datas
	 * @return
	 */
	private String generateTableHtml(String title,String[] header,List<?> datas,String[] supTitle,Map<Integer,String> map,Map<Integer,String> specialColor,Map<Integer,String> lineColor){
		StringBuffer html = new StringBuffer();
		if(StringUtils.isNotBlank(title)){
			html.append("<h4>").append(title.trim()).append("</h4>");
		}
		html.append("<table style='border-collapse: collapse;border-spacing: 2px;border-color: gray;'>");
		html.append("<thead>");
		if(null!=supTitle && supTitle.length>0){
			html.append("<tr>");
			String preTitle = null;
			int colSpan = 1;
			for(int i=0;i<supTitle.length;i++){
				if(preTitle==null){
					//第一个标题
					html.append("<th style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;background-color:#0C6DAF;border:1px solid #cccccc;color:#FFFFFF;' colspan='");
				}else{
					//不是第一个标题
					if(supTitle[i].equals(preTitle)){
						//与前一个标题一致
						colSpan++;
						if(i==supTitle.length-1){
							//最后一个标题
							html.append(colSpan).append("'>").append(preTitle).append("</th>");
						}
					}else{
						//与前一个标题不一致
						html.append(colSpan).append("'>").append(preTitle).append("</th>");
						if(i<supTitle.length-1){
							//不是最后一个标题
							html.append("<th style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;background-color:#0C6DAF;border:1px solid #cccccc;color:#FFFFFF;' colspan='");
						}
						colSpan=1;
					}
				}
				preTitle = supTitle[i];
			}
			html.append("</tr>");
		}
		html.append("<tr>");
		for (String head : header) {
			html.append(
					"<th style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;background-color:#0C6DAF;border:1px solid #cccccc;color:#FFFFFF;'>")
					.append(head).append("</th>");
		}
		html.append("</tr>");
		html.append("</thead>");
		html.append("<tbody>");
		int line = 1;
		for(Object ojbect:datas){
			html.append("<tr>");
			boolean red = (null!=map&&null!=map.get(line-1)&&"1".equals(map.get(line-1)))?true:false;
			String backgroundColor = null;
			if(ojbect instanceof Object[]){
				Object[] das =(Object[]) ojbect;
				for(int i=0;i<header.length;i++){
					if(specialColor!=null){
						backgroundColor = specialColor.get(i);
					}
					if(lineColor!=null && lineColor.get(line-1)!=null){
						backgroundColor = lineColor.get(line-1);
					}
					html.append(generateSingleDataCell(das[i],line%2==0,red,backgroundColor,1,null));
				}
			}else{
				if(specialColor!=null){
					backgroundColor = specialColor.get(0);
				}
				if(lineColor!=null && lineColor.get(line-1)!=null){
					backgroundColor = lineColor.get(line-1);
				}
				html.append(generateSingleDataCell(ojbect,line%2==0,red,backgroundColor,1,null));
			}
			html.append("</tr>");
			line++;
		}
		html.append("</tbody>");
		html.append("</table>");
		return html.toString();
	}
	
	/**
	 * 生成单元格HTML
	 * @param o
	 * @param changeColor 变换底色
	 * @param redFont 是否标红
	 * @param specialColor 自定义背景色
	 * @param colspan 合并单元格
	 * @param padding 字体位置
	 * @return
	 */
	private String generateSingleDataCell(Object o,boolean changeColor,boolean redFont,String specialColor,int colspan,String padding){
		StringBuffer sb = new StringBuffer("<td style='font-size:12px;white-space:nowrap;display:table-cell;vertical-align:inherit;border:1px solid #cccccc;padding-left:4px;");
		if(StringUtils.isNotBlank(padding)){
			sb.append("text-align:").append(padding).append(";");
		}else if(isDoubleValue(o)){
			sb.append("text-align: right;");
		}
		if(StringUtils.isNotBlank(specialColor)){
			sb.append("background-color:").append(specialColor).append(";");
		}else if(changeColor){
			sb.append("background-color:#F8FBFC;");
		}
		if(redFont){
			sb.append("color:#FF0000;");
		}
		sb.append("'");
		if(colspan>1){
			sb.append(" colspan='").append(colspan).append("'");
		}
		sb.append(">");
		//if(!StringUtils.nullToString(o).equals("0.00") && !StringUtils.nullToString(o).equals("0")){
			sb.append(StringUtils.nullToString(o));
		//}
		sb.append("</td>");
		return sb.toString();
	}
	
	/**
	 * 判断当前字符是否为数值格式
	 * @param object
	 * @return
	 */
	private boolean isDoubleValue(Object object){
		try {
			Double.parseDouble(StringUtils.nullToString(object).replaceAll(",","").replaceAll("%",""));
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	/**
	 * 获取当前日期是第几周
	 * @param date 格式为yyyyMMdd
	 * @return
	 */
	public static String weekOfYear(String date){
		return DateUtils.getWeekOfYear(date.substring(0,4)+"-"+date.substring(4,6)+"-"+date.substring(6,8));
	}

	@Override
	public void sendAccountSummaryEmail(String to, String cc) {
		StringBuffer html = new StringBuffer();
		html.append(findAcctsBalance())//当天余额
			.append(findReceivaLatestMonth());//30天收款明细
		//send Email begin
		MailUtil mailUtil = new MailUtil();
		mailUtil.setHost("mail.leedarson.com");
		mailUtil.setUsername("IT@leedarson.com");
		mailUtil.setPassword("it612345");
		mailUtil.setFrom("IT@leedarson.com");
		String date = DateUtils.formatDate(new Date(),"yyyy/MM/dd");
		mailUtil.setSubject("资金管理日报: "+date);
		mailUtil.setTo(StringUtils.isBlank(to)?findEmail(FC_DAILY_BALANCE,SENDTYPE_TO):to);
		mailUtil.setTocc(StringUtils.isBlank(cc)?findEmail(FC_DAILY_BALANCE,SENDTYPE_CC):cc);
		mailUtil.setTobcc(findEmail(FC_DAILY_BALANCE,SENDTYPE_BC));
		mailUtil.setContent(html.toString());
		boolean res=mailUtil.send();
		logger.error("资金管理报表发送"+(res?"成功!":"失败!!!")+date);
		//send Email end
	}
	
	/**
	 * 查询账户余额汇总
	 * @return
	 */
	public String findAcctsBalance(){
		String sql = ""
			+"with curr as                                                                          "
			+" (select dat, fcurr, ukurs                                                            "
			+"    from (select 99999999 - gdatu as dat,                                             "
			+"                 fcurr,                                                               "
			+"                 ukurs,                                                               "
			+"                 row_number() over(partition by fcurr order by gdatu asc) as rn       "
			+"            from tcurr                                                                "
			+"           where tcurr = 'CNY'                                                        "
			+"             and fcurr in ('EUR', 'USD')                                              "
			+"             and kurst = 'M') t                                                       "
			+"   where rn = 1),                                                                     "
			+"comps as                                                                              "
			+" (select distinct bukrs, waers                                                        "
			+"    from zfmt0001                                                                     "
			+"  union                                                                               "
			+"  select distinct bukrs, waers from zfmt0013),                                        "
			+"bal as                                                                                "
			+" (select b.bukrs,                                                                     "
			+"         b.waers,                                                                     "
			+"         sum(case                                                                     "
			+"               when a.ryear = '2014' and a.rpmax = '000' then                         "
			+"                a.zmacc                                                               "
			+"               else                                                                   "
			+"                0                                                                     "
			+"             end) as amt,                                                             "
			+"         sum(case                                                                     "
			+"               when a.ryear = '2014' and a.rpmax = '000' and a.hkont like '1012%' then"
			+"                a.zmacc                                                               "
			+"               else                                                                   "
			+"                0                                                                     "
			+"             end) as guamt                                                            "
			+"    from comps b                                                                      "
			+"    left join zfmt0013 a                                                              "
			+"      on a.bukrs = b.bukrs                                                            "
			+"     and a.waers = b.waers                                                            "
			+"   group by b.bukrs, b.waers),                                                        "
			+"app as                                                                                "
			+" (select bukrs,                                                                       "
			+"         waers,                                                                       "
			+"         sum(zdmbtr - zcrbtr) as amt,                                                 "
			+"         sum(case                                                                     "
			+"               when hkont like '1012%' then                                           "
			+"                zdmbtr - zcrbtr                                                       "
			+"               else                                                                   "
			+"                0                                                                     "
			+"             end) as guamt                                                            "
			+"    from zfmt0001                                                                     "
			+"   where bldat > '20140100'                                                           "
			+"   group by bukrs, waers),                                                            "
			+"summary as                                                                            "
			+" (select ta.bukrs,                                                                    "
			+"         ta.waers,                                                                    "
			+"         nvl(ta.amt, 0) + nvl(tb.amt, 0) as amt,                                      "
			+"         to_number(to_char((nvl(ta.amt, 0) + nvl(tb.amt, 0)) *                        "
			+"                           nvl(tc.ukurs, 1),                                          "
			+"                           '999999999990.99')) amt_s,                                 "
			+"         case                                                                         "
			+"           when ta.waers = 'CNY' then                                                 "
			+"            nvl(ta.guamt, 0) + nvl(tb.guamt, 0)                                       "
			+"           else                                                                       "
			+"            0                                                                         "
			+"         end as gu_cny,                                                               "
			+"         case                                                                         "
			+"           when ta.waers = 'USD' then                                                 "
			+"            nvl(ta.guamt, 0) + nvl(tb.guamt, 0)                                       "
			+"           else                                                                       "
			+"            0                                                                         "
			+"         end as gu_usd,                                                               "
			+"         to_number(to_char((nvl(ta.guamt, 0) + nvl(tb.guamt, 0)) *                    "
			+"                           nvl(tc.ukurs, 1),                                          "
			+"                           '999999999990.99')) guamt                                  "
			+"    from bal ta                                                                       "
			+"    left join app tb                                                                  "
			+"      on ta.bukrs = tb.bukrs                                                          "
			+"     and ta.waers = tb.waers                                                          "
			+"    left join curr tc                                                                 "
			+"      on ta.waers = tc.fcurr),                                                        "
			+"grp as                                                                                "
			+" (select bukrs,                                                                       "
			+"         sum(decode(waers, 'CNY', amt, 0)) as cny,                                    "
			+"         sum(decode(waers, 'USD', amt, 0)) as usd,                                    "
			+"         sum(decode(waers, 'USD', amt_s, 0)) as usd_s,                                "
			+"         sum(decode(waers, 'EUR', amt, 0)) as eur,                                    "
			+"         sum(decode(waers, 'EUR', amt_s, 0)) as eur_s,                                "
			+"         sum(gu_cny) as gu_cny,                                                       "
			+"         sum(gu_usd) as gu_usd,                                                       "
			+"         sum(guamt) as guamt_s                                                        "
			+"    from summary                                                                      "
			+"   group by bukrs)                                                                    "
			+"select to_char(sysdate, 'yyyy/MM/dd'),                                                "
			+"       ta.bukrs,                                                                      "
			+"       tb.butxt,                                                                      "
			+"       trim(to_char(ta.cny, '999,999,999,990.99')) as cny,                            "
			+"       trim(to_char(ta.usd, '999,999,999,990.99')) as usd,                            "
			+"       usd.ukurs as usduk,                                                            "
			+"       trim(to_char(ta.usd_s, '999,999,999,990.99')) as usd_s,                        "
			+"       trim(to_char(ta.eur, '999,999,999,990.99')) as eur,                            "
			+"       eur.ukurs as euruk,                                                            "
			+"       trim(to_char(ta.eur_s, '999,999,999,990.99')) as eur_s,                        "
			+"       trim(to_char(ta.usd_s + ta.eur_s + ta.cny, '999,999,999,990.99')) as amt_all,  "
			+"       trim(to_char(ta.gu_usd, '999,999,999,990.99')) as gu_usd,                      "
			+"       trim(to_char(ta.gu_cny, '999,999,999,990.99')) as gu_cny,                      "
			+"       trim(to_char(ta.usd_s + ta.eur_s + ta.cny - ta.guamt_s,                        "
			+"                    '999,999,999,990.99')) as amt_bal,                                "
			+"       ta.usd_s + ta.eur_s + ta.cny - ta.guamt_s as balUpper                          "
			+"  from grp ta                                                                         "
			+"  left join (select bukrs,butxt from t001 union all select '9999','智造绿能' from dual) tb "
			+"    on ta.bukrs = tb.bukrs                                                            "
			+"  left join curr usd                                                                  "
			+"    on usd.fcurr = 'USD'                                                              "
			+"  left join curr eur                                                                  "
			+"    on eur.fcurr = 'EUR'                                                              "
			+" where ta.bukrs != '3000'                                                             "
			+"union all                                                                             "
			+"select '合计',                                                                          "
			+"       '',                                                                            "
			+"       '',                                                                            "
			+"       trim(to_char(sum(ta.cny), '999,999,999,990.99')),                              "
			+"       trim(to_char(sum(ta.usd), '999,999,999,990.99')),                              "
			+"       max(usd.ukurs),                                                                "
			+"       trim(to_char(sum(ta.usd_s), '999,999,999,990.99')),                            "
			+"       trim(to_char(sum(ta.eur), '999,999,999,990.99')),                              "
			+"       max(eur.ukurs),                                                                "
			+"       trim(to_char(sum(ta.eur_s), '999,999,999,990.99')),                            "
			+"       trim(to_char(sum(ta.usd_s + ta.eur_s + ta.cny), '999,999,999,990.99')),        "
			+"       trim(to_char(sum(ta.gu_usd), '999,999,999,990.99')),                           "
			+"       trim(to_char(sum(ta.gu_cny), '999,999,999,990.99')),                           "
			+"       trim(to_char(sum(ta.usd_s + ta.eur_s + ta.cny - ta.guamt_s),                   "
			+"                    '999,999,999,990.99')),                                           "
			+"       sum(ta.usd_s + ta.eur_s + ta.cny - ta.guamt_s)                                 "
			+"  from grp ta                                                                         "
			+"  left join curr usd                                                                  "
			+"    on usd.fcurr = 'USD'                                                              "
			+"  left join curr eur                                                                  "
			+"    on eur.fcurr = 'EUR'                                                              "
			+" where ta.bukrs != '3000'                                                             "
			+"  order by bukrs ";
		List<Object> list =findListBySql(sql);
		if(list==null||list.size()==0){
			return "<h3>1.今天各账户余额:无</h3>";
		}
		for(Object ob:list){
			Object[] obj = (Object[])ob;
			obj[obj.length-1]="<div style='width:100%;'><div style='width:100px;height:100%;float:right;text-align: right;'>"+StringUtils.amountToChinese(Double.valueOf(StringUtils.nullToString(obj[obj.length-1])))+"</div></div>";
			obj[obj.length-2]="<div style='width:100%;'><div style='width:100px;height:100%;float:right;text-align: right;'>"+StringUtils.nullToString(obj[obj.length-2]).replaceAll("\\.","．")+"</div></div>";
		}
		Object[] sum = (Object[])list.get(list.size()-1);
		sum[sum.length-1]="<div style='width:100%;color:red;'>"+StringUtils.nullToString(sum[sum.length-1])+"</div>";
		sum[sum.length-2]="<div style='width:100%;'><div style='width:100px;height:100%;color:red;float:right;text-align: right;'>"+StringUtils.nullToString(sum[sum.length-2]).replaceAll("\\.","．")+"</div></div>";
		String[] title = {"日期","代码","公司名称","人民币","美元","美元汇率","美元折成人民币","欧元","欧元汇率","欧元折成人民币","总额(折人民币)","定存(美元)","定存(人民币)","可用余额(人民币)"};
		Map<Integer,String> lineColor = new HashMap<Integer,String>();
		lineColor.put(list.size()-1,"#8BB3E5");//最后一行颜色
		Map<Integer,String> colColor = new HashMap<Integer,String>();
		colColor.put(3,"#EAF3FD");
		colColor.put(6,"#EAF3FD");
		colColor.put(9,"#EAF3FD");
		colColor.put(13,"#EAF3FD");
		colColor.put(14,"#EAF3FD");
		return generateTableHtml("1.今天各账户余额:",title,list,null,null,colColor,lineColor);
	}
	
	/**
	 * 查本月内到款记录
	 * @return
	 */
	public String findReceivaLatestMonth() {
		String[] title = { "日期", "客户简称","业务对象(摘要)", "金额", "币种", "代码", "公司名称", "国家汇款人" };
		StringBuffer html = new StringBuffer();
		html.append("<h4>2.本月到款明细:</h4>");
		html.append("<table style='border-collapse: collapse;border-spacing: 2px;border-color: gray;'>");
		html.append("<thead>");
		html.append("<tr>");
		for (String head : title) {
			html.append(
					"<th style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;background-color:#0C6DAF;border:1px solid #cccccc;color:#FFFFFF;'>")
					.append(head).append("</th>");
		}
		html.append("</tr>");
		html.append("</thead>");
		html.append("<tbody>");
		Date today = new Date();
		Date cusorDate = today;
		String todayString = DateUtils.formatDate(today, "yyyyMMdd");
		String fisrDayString = todayString.substring(0,6)+"01";
		try {
			cusorDate = DateUtils.parseDate(fisrDayString,"yyyyMMdd");
		} catch (ParseException e) {
		}
		String tempDate = fisrDayString;
		String monday = fisrDayString;
		do {// 未跨月
			String sql = ""
				+"with curr as                                                                   "
				+" (select dat, fcurr, ukurs                                                     "
				+"    from (select 99999999 - gdatu as dat,                                      "
				+"                 fcurr,                                                        "
				+"                 ukurs,                                                        "
				+"                 row_number() over(partition by fcurr order by gdatu asc) as rn"
				+"            from tcurr                                                         "
				+"           where tcurr = 'CNY'                                                 "
				+"             and fcurr in ('EUR', 'USD')                                       "
				+"             and kurst = 'M') t                                                "
				+"   where rn = 1),                                                              "
				+"summary as                                                                     "
				+" (select to_char(to_date(a.bldat, 'yyyyMMdd'), 'yyyy-MM-dd') as bldat,         "
				+"         c.sortl,                                                          		 "
				+"         trim(a.sgtxt) as sgtxt,                                               "
				+"         sum(a.zdmbtr) as zdmbtr,                                              "
				+"         a.waers,                                                              "
				+"         b.butxt,                                                              "
				+"         a.zcrem,                                                              "
				+"         a.bukrs                                                               "
				+"    from zfmt0001 a                                                            "
				+"    left join (select bukrs,butxt from t001 union all select '9999','智造绿能' from dual) b                                                           "
				+"      on a.bukrs = b.bukrs                                                     "
				+"    left join kna1 c                                                           "
				+"      on a.kunnr = c.kunnr                                                     "
				+"   where a.bldat = '"+tempDate+"'                                              "
				+"     and a.zdmbtr > 0                                                          "
				+"     and zbl = '01'                                                            "
				+"   group by trim(a.sgtxt), c.sortl,a.bldat, a.waers, a.bukrs, b.butxt, a.zcrem "
				+"                                                                               "
				+"  )                                                                            "
				+"select bldat,                                                                  "
				+"       sortl,                                                                  "
				+"       sgtxt,                                                                  "
				+"       trim(to_char(zdmbtr, '999,999,999,990.99')),                            "
				+"       waers,                                                                  "
				+"       bukrs,                                                                  "
				+"       butxt,                                                                  "
				+"       zcrem                                                                   "
				+"  from summary ta                                                              "
				+"union all                                                                      "
				+"select 'S',                                                                    "
				+"       '美元:' || trim(to_char(sum(decode(ta.waers, 'USD', ta.zdmbtr, 0)),     "
				+"                              '999,999,999,990.99')) || ' 人民币:' ||          "
				+"       trim(to_char(sum(decode(ta.waers, 'CNY', ta.zdmbtr, 0)),                "
				+"                    '999,999,999,990.99')) || ' 欧元:' ||                      "
				+"       trim(to_char(sum(decode(ta.waers, 'EUR', ta.zdmbtr, 0)),                "
				+"                    '999,999,999,990.99')) ,  'REDS' ||                        "
				+"       trim(to_char(sum(to_number(to_char(ta.zdmbtr * nvl(tb.ukurs, 1),        "
				+"                                          '999999999990.99'))),                "
				+"                    '999,999,999,990.99')) || 'REDE CNY',                      "
				+"       '',                                                                     "
				+"       '',                                                                     "
				+"       '',                                                                     "
				+"       '',                                                                     "
				+"       ''                                                                      "
				+"  from summary ta                                                              "
				+"  left join curr tb                                                            "
				+"    on ta.waers = tb.fcurr                                                     "
				+" group by ta.bldat                                                             "
				+" order by bldat asc,waers desc,sgtxt asc                                       ";
			List<Object> list = findListBySql(sql);
			int line = 1;//行号
			if(list!=null && list.size()>0){
				for(Object obj:list){
					Object[] das=(Object[])obj;
					html.append("<tr>");
					if(!"S".equals(StringUtils.nullToString(das[0]))){
						//非合计行
						for(int i=0;i<title.length;i++){
							html.append(generateSingleDataCell(das[i],line%2==0,false,null,1,null));
						}
					}else{
						//合计行
						html.append(generateSingleDataCell("当天合计:",line%2==0,false,"#CFDDF1",1,"right"));
						html.append(generateSingleDataCell(
								StringUtils
										.nullToString(das[1])
										.replaceFirst("REDS",
												"<span style='color:red'>")
										.replaceAll("REDE", "</span>"),
								line % 2 == 0, false, "#CFDDF1", 5,"left"));
						//html.append(generateSingleDataCell("合计:",line%2==0,false,"#B4C9E9",1));
						html.append(generateSingleDataCell(
								"合计:"+StringUtils
										.nullToString(das[2])
										.replaceFirst("REDS",
												"<span style='color:red;text-align: right;'>")
										.replaceAll("REDE", "</span>"),
								line % 2 == 0, false, "#CFDDF1", 2,"right"));
					}
					html.append("</tr>");
					line++;
				}
			}
			//add by HeShi 20140519 添加周合计栏
			if("星期一".equals(DateUtils.getWeekOfDate(cusorDate))){
				monday = tempDate;
			}
			if(("星期日".equals(DateUtils.getWeekOfDate(cusorDate))
					||tempDate.equals(todayString))&&!fisrDayString.equals(tempDate)){
				//每周日或统计的最后一天,做周合计
				String weekSql = ""
					+"with curr as                                                                   "
					+" (select dat, fcurr, ukurs                                                     "
					+"    from (select 99999999 - gdatu as dat,                                      "
					+"                 fcurr,                                                        "
					+"                 ukurs,                                                        "
					+"                 row_number() over(partition by fcurr order by gdatu asc) as rn"
					+"            from tcurr                                                         "
					+"           where tcurr = 'CNY'                                                 "
					+"             and fcurr in ('EUR', 'USD')                                       "
					+"             and kurst = 'M') t                                                "
					+"   where rn = 1),                                                              "
					+"summary as                                                                     "
					+" (select to_char(to_date(a.bldat, 'yyyyMMdd'), 'yyyy-MM-dd') as bldat,         "
					+"         trim(a.sgtxt) as sgtxt,                                               "
					+"         sum(a.zdmbtr) as zdmbtr,                                              "
					+"         a.waers,                                                              "
					+"         b.butxt,                                                              "
					+"         a.zcrem,                                                              "
					+"         a.bukrs                                                               "
					+"    from zfmt0001 a                                                            "
					+"    left join (select bukrs,butxt from t001 union all select '9999','智造绿能' from dual) b     "
					+"      on a.bukrs = b.bukrs                                                     "
					+"   where a.bldat between '"+monday+"' and '"+tempDate+"'"
					+"     and a.zdmbtr > 0                                                          "
					+"     and zbl = '01'                                                            "
					+"   group by trim(a.sgtxt), a.bldat, a.waers, a.bukrs, b.butxt, a.zcrem         "
					+"  )                                                                            "
					+"select '美元:' || trim(to_char(sum(decode(ta.waers, 'USD', ta.zdmbtr, 0)),     "
					+"                              '999,999,999,990.99')) || ' 人民币:' ||          "
					+"       trim(to_char(sum(decode(ta.waers, 'CNY', ta.zdmbtr, 0)),                "
					+"                    '999,999,999,990.99')) || ' 欧元:' ||                      "
					+"       trim(to_char(sum(decode(ta.waers, 'EUR', ta.zdmbtr, 0)),                "
					+"                    '999,999,999,990.99')) ,  ' REDS' ||                       "
					+"       trim(to_char(sum(to_number(to_char(ta.zdmbtr * nvl(tb.ukurs, 1),        "
					+"                                          '999999999990.99'))),                "
					+"                    '999,999,999,990.99')) || 'REDE CNY'                       "
					+"  from summary ta                                                              "
					+"  left join curr tb                                                            "
					+"    on ta.waers = tb.fcurr                                                     "
					;
				List<Object> listweek = findListBySql(weekSql);//查询当周数据合计
				if(null!=listweek && listweek.size()>0){
					html.append("<tr>");
					html.append(generateSingleDataCell(weekOfYear(monday)+"周合计:", true, false, "#B8CDEB",
							1,"right"));
					html.append(generateSingleDataCell(
							StringUtils.nullToString(((Object[])listweek.get(0))[0])
									.replaceFirst("REDS", "<span style='color:red'>")
									.replaceAll("REDE", "</span>"), true, false,
							"#B8CDEB", 5,"left"));
					//html.append(generateSingleDataCell("合计:", true, false, "#8BB3E5",1));
					html.append(generateSingleDataCell(
							"合计:"+StringUtils.nullToString(((Object[])listweek.get(0))[1])
									.replaceFirst("REDS", "<span style='color:red;text-align: right;'>")
									.replaceAll("REDE", "</span>"), true, false,
							"#B8CDEB", 2,"right"));
					html.append("</tr>");
				}
			}
			
			cusorDate = DateUtils.addDate(cusorDate, 1);//日期递增
			tempDate = DateUtils.formatDate(cusorDate, "yyyyMMdd");
		}while(tempDate.compareTo(todayString)<1);
		//本月合计行
		String sql = ""
			+"with curr as                                                                   "
			+" (select dat, fcurr, ukurs                                                     "
			+"    from (select 99999999 - gdatu as dat,                                      "
			+"                 fcurr,                                                        "
			+"                 ukurs,                                                        "
			+"                 row_number() over(partition by fcurr order by gdatu asc) as rn"
			+"            from tcurr                                                         "
			+"           where tcurr = 'CNY'                                                 "
			+"             and fcurr in ('EUR', 'USD')                                       "
			+"             and kurst = 'M') t                                                "
			+"   where rn = 1),                                                              "
			+"summary as                                                                     "
			+" (select to_char(to_date(a.bldat, 'yyyyMMdd'), 'yyyy-MM-dd') as bldat,         "
			+"         trim(a.sgtxt) as sgtxt,                                               "
			+"         sum(a.zdmbtr) as zdmbtr,                                              "
			+"         a.waers,                                                              "
			+"         b.butxt,                                                              "
			+"         a.zcrem,                                                              "
			+"         a.bukrs                                                               "
			+"    from zfmt0001 a                                                            "
			+"    left join (select bukrs,butxt from t001 union all select '9999','智造绿能' from dual) b   "
			+"      on a.bukrs = b.bukrs                                                     "
			+"   where a.bldat > to_char(sysdate, 'yyyyMM') || '00'                          "
			+"     and a.zdmbtr > 0                                                          "
			+"     and zbl = '01'                                                            "
			+"   group by trim(a.sgtxt), a.bldat, a.waers, a.bukrs, b.butxt, a.zcrem         "
			+"                                                                               "
			+"  )                                                                            "
			+"select '美元:' || trim(to_char(sum(decode(ta.waers, 'USD', ta.zdmbtr, 0)),     "
			+"                              '999,999,999,990.99')) || ' 人民币:' ||          "
			+"       trim(to_char(sum(decode(ta.waers, 'CNY', ta.zdmbtr, 0)),                "
			+"                    '999,999,999,990.99')) || ' 欧元:' ||                      "
			+"       trim(to_char(sum(decode(ta.waers, 'EUR', ta.zdmbtr, 0)),                "
			+"                    '999,999,999,990.99')) ,  ' REDS' ||                       "
			+"       trim(to_char(sum(to_number(to_char(ta.zdmbtr * nvl(tb.ukurs, 1),        "
			+"                                          '999999999990.99'))),                "
			+"                    '999,999,999,990.99')) || 'REDE CNY'                       "
			+"  from summary ta                                                              "
			+"  left join curr tb                                                            "
			+"    on ta.waers = tb.fcurr                                                     "
			;
		List<Object> list =findListBySql(sql);//查询当月数据合计
		if(null!=list && list.size()>0){
			html.append("<tr>");
			html.append(generateSingleDataCell("本月合计:", true, false, "#8BB3E5",
					1,"right"));
			html.append(generateSingleDataCell(
					StringUtils.nullToString(((Object[])list.get(0))[0])
							.replaceFirst("REDS", "<span style='color:red'>")
							.replaceAll("REDE", "</span>"), true, false,
					"#8BB3E5", 5,"left"));
			//html.append(generateSingleDataCell("合计:", true, false, "#8BB3E5",1));
			html.append(generateSingleDataCell(
					"合计:"+StringUtils.nullToString(((Object[])list.get(0))[1])
							.replaceFirst("REDS", "<span style='color:red;text-align: right;'>")
							.replaceAll("REDE", "</span>"), true, false,
					"#8BB3E5", 2,"right"));
			html.append("</tr>");
		}
		
		html.append("</tbody>");
		html.append("</table>");
		return html.toString();
	}

	@Override
	public boolean sendReportToSingleAEOrAA(String userId, String mail, String type) {
		boolean result=false;
		StringBuffer html = new StringBuffer();
		html.append("<h4>此邮件为系统自动发送,请勿回复.</h4>")
			.append("<h4>不是您自己负责的客户,请将客户编号修改为'...';</h4>")
			.append(generateLinks(userId,type))//相关链接
			.append(findReceivebleInfo(userId,type,""))//客户汇总
			.append(generateAcctEditLink(userId,type,""))//到账登记
			.append(generateAcctSeprateLink(userId,type,""))//预收分解
			.append(findReceivableNextMonth(userId,type,""))//未来30天收款
			.append(findDelayList(userId,type,""))//逾期列表
			;
		
		if(StringUtils.isBlank(html.toString()))
			return true;
		//send Email begin
		MailUtil mailUtil = new MailUtil();
		mailUtil.setHost("mail.leedarson.com");
		mailUtil.setUsername("IT@leedarson.com");
		mailUtil.setPassword("it612345");
		mailUtil.setFrom("IT@leedarson.com");
		String date = DateUtils.formatDate(new Date(),"yyyy/MM/dd");
		mailUtil.setSubject("业务员收款日报: "+date);
		mailUtil.setTo(mail);
		//mailUtil.setTobcc("hes@leedarson.com");
		mailUtil.setContent(html.toString());
		result = mailUtil.send();
		//send Email end
		return result;
	}
	
	/**
	 * 生成链接
	 * @param userId
	 * @param type
	 * @return
	 */
	public String generateLinks(String userId,String type){
		//TODO 链接配置未完成
		String conditionString = "";
		if("1".equals(type)){
			conditionString = "zae='"+userId+"'";
		}else if("2".equals(type)){
			conditionString = "zaa='"+userId+"'";
		}else if("3".equals(type)){
			conditionString = "zrsm='"+userId+"'";
		}else if("4".equals(type)){
			conditionString = "zrname='"+userId+"'";
		}
		StringBuffer html = new StringBuffer();
		html.append("<a href='")
			.append(StringUtils.nullToString(propertyConfigurer.getProperty("server.url")))
			.append("/ficoManage/financeDayCash!search.do?param.fromBldat=::param.")
			.append(conditionString)
			.append("'>到款资料列表链接</a><br/>");
		html.append("<a href='")
			.append(StringUtils.nullToString(propertyConfigurer.getProperty("server.url")))
			.append("&url=other/ficomanage/financeDayCash!search.do?param.fromBldat=::param.zrbl=A::param.")
			.append(conditionString)
			.append("'>预收款资料列表链接</a><br/>");
		html.append("<a href='")
			.append(StringUtils.nullToString(propertyConfigurer.getProperty("server.url")))
			.append("&url=other/ficomanage/acctCompare!search.do")
			.append("'>客户账款汇总链接</a><br/>");
		html.append("<a href='")
			.append(StringUtils.nullToString(propertyConfigurer.getProperty("server.url")))
			.append("&url=other/ficomanage/receiveSearch!search.do")
			.append("'>客户账款明细链接</a><br/>");
		html.append("<a href='")
			.append(StringUtils.nullToString(propertyConfigurer.getProperty("server.url")))
			.append("&url=other/ficomanage/otherAcctSearch!search.do")
			.append("'>客户其他应收款明细链接</a><br/>");
		html.append("<a href='")
			.append(StringUtils.nullToString(propertyConfigurer.getProperty("server.url")))
			.append("&url=other/ficomanage/nonRecExport!initReport.do")
			.append("'>EXCEL未收汇链接</a><br/>");
		return html.toString();
	}
	
	/**
	 * 客户收款信息汇总
	 */
	@SuppressWarnings("rawtypes")
	public String findReceivebleInfo(String userId,String type,String custGroup){
		String conditionString = "";
		String deptConditon = "";
		if("1".equals(type)){
			conditionString = " and u.zae='"+userId+"'";
		}else if("2".equals(type)){
			conditionString = " and u.zaa='"+userId+"'";
		}else if("3".equals(type)){
			conditionString = " and u.zrsm='"+userId+"'";
		}else if("4".equals(type)){
			conditionString = " and u.zrname='"+userId+"'";
		}else if(StringUtils.isNotBlank(type)){
			deptConditon = " and ta.vkbur='"+type+"'";
		}
		String custGroupString = "";
		if(StringUtils.isNotBlank(custGroup)){
			custGroupString = " and tb.zkdgp ='"+custGroup.toUpperCase().trim()+"' ";
		}
		String sql = ""                                                                                  
			+"with curr as                                                                               "
			+" (select fcurr, tcurr, substr(to_char(99999999 - gdatu), 0, 6) as mon, ukurs               "
			+"    from tcurr                                                                             "
			+"   where kurst = 'Z' and tcurr='CNY'),                                                     "
			+"pre as                                                                                     "
			+" (select a.zbelnr,                                                                         "
			+"         sum(a.zcamount - nvl(b.unBelnr, 0)) as zcamount,                                  "
			+"         sum(nvl(b.belnr, 0)) as zcamountDo                                                "
			+"    from zfmt0004 a                                                                        "
			+"    left join (select zposnr_s,                                                            "
			+"                     zbelnr,                                                               "
			+"                     sum(case                                                              "
			+"                           when zdrs = 'F' and zcirs = 'C' then                            "
			+"                            zcamount                                                       "
			+"                           else                                                            "
			+"                            0                                                              "
			+"                         end) as belnr,                                                    "
			+"                     sum(case                                                              "
			+"                           when zcirs = 'C' then                                           "
			+"                            zcamount                                                       "
			+"                           else                                                            "
			+"                            0                                                              "
			+"                         end) as unBelnr                                                   "
			+"                from zfmt0004                                                              "
			+"               where zposnr_s != '00000'                                                   "
			+"                 and zposnr_s != ' '                                                       "
			+"               group by zposnr_s, zbelnr) b                                                "
			+"      on a.zbelnr = b.zbelnr                                                               "
			+"     and a.zposnr = b.zposnr_s                                                             "
			+"   where a.zrbl = 'A'                                                                      "
			+"     and a.zdrs != 'C'                                                                     "
			+"     and a.zcirs = 'C'                                                                     "
			+"   group by a.zbelnr),                                                                     "
			+"presum as                                                                                  "
			+" (select ta.kunnr,                                                                         "
			+"         sum(to_number(to_char((case                                                       "
			+"                                 when ta.zdrs = 'P' and ta.zcirs = 'P' then                "
			+"                                  ta.zcamount                                              "
			+"                                 else                                                      "
			+"                                  0                                                        "
			+"                               end) *                                                      "
			+"                               nvl(decode(ta.waers, 'CNY', 1, tb.ukurs), 1),               "
			+"                               '99999999990.00'))) as rec,                                 "
			+"         sum(to_number(to_char((case                                                       "
			+"                                 when ta.zdrs != 'C' and ta.zcirs = 'F' then               "
			+"                                  ta.zcamount                                              "
			+"                                 else                                                      "
			+"                                  0                                                        "
			+"                               end) *                                                      "
			+"                               nvl(decode(ta.waers, 'CNY', 1, tb.ukurs), 1),               "
			+"                               '99999999990.00'))) as recDo,                               "
			+"         sum(to_number(to_char(nvl(tc.zcamount, 0) *                                       "
			+"                               nvl(decode(ta.waers, 'CNY', 1, tb.ukurs), 1),               "
			+"                               '99999999990.00'))) as pre,                                 "
			+"         sum(to_number(to_char(nvl(tc.zcamountDo, 0) *                                     "
			+"                               nvl(decode(ta.waers, 'CNY', 1, tb.ukurs), 1),               "
			+"                               '99999999990.00'))) as preDo                                "
			+"    from zfmt0003 ta                                                                       "
			+"    left join curr tb                                                                      "
			+"      on ta.waers = tb.fcurr                                                               "
			+"     and to_char(add_months(to_date(ta.bldat, 'yyyyMMdd'), -1), 'yyyyMM') =                "
			+"         tb.mon                                                                            "
			+"    left join pre tc                                                                       "
			+"      on ta.zbelnr = tc.zbelnr                                                             "
			+"   inner join zfmt0007 u                                                                   "
			+"      on ta.kunnr = u.kunnr                                                                "
			+"     and ta.bukrs = u.bukrs                                                                "
			+"   where ta.kunnr != '...'                                                                 "
			+ conditionString
			+"   group by ta.kunnr),                                                                     "
			+"delay as                                                                                   "
			+" (select a.vkbur,                                                                          "
			+"         a.kunnr,                                                                          "
			+"         a.sortl,                                                                          "
			+"         a.zlwaers,                                                                        "
			+"         case                                                                              "
			+"           when a.zddate >= to_char(sysdate, 'yyyyMMdd') and  "
			+"                a.zddate < to_char(sysdate + 30, 'yyyyMMdd') then "
			+"            (case when nvl(a.ziamt,0)=0 then a.zdamt+ nvl((select bb.zcamt from zfmt0017 bb where a.vbeln = bb.vbeln),0)-a.zhc-a.zgamt else a.ziamt+ nvl((select bb.zcamt from zfmt0017 bb where a.vbeln = bb.vbeln),0)-a.zhc-a.zgamt end) * nvl(a.ukurs, 1)                                                      "
			+"           else                                                                            "
			+"            0                                                                              "
			+"         end as dmon,                                                                      "
			+"         case                                                                              "
			+"           when a.zddate >= to_char(sysdate, 'yyyyMMdd') and  "
			+"                a.zddate < to_char(sysdate + 15, 'yyyyMMdd') then "
			+"            (case when nvl(a.ziamt,0)=0 then a.zdamt+ nvl((select bb.zcamt from zfmt0017 bb where a.vbeln = bb.vbeln),0)-a.zhc-a.zgamt else a.ziamt+ nvl((select bb.zcamt from zfmt0017 bb where a.vbeln = bb.vbeln),0)-a.zhc-a.zgamt end) * nvl(a.ukurs, 1)                                                      "
			+"           else                                                                            "
			+"            0                                                                              "
			+"         end as d14,                                                                       "
			+"         case                                                                              "
			+"           when a.zddate < to_char(sysdate, 'yyyyMMdd') and                                "
			+"                a.zddate >= to_char(sysdate - 30, 'yyyyMMdd') then                         "
			+"            (case when nvl(a.ziamt,0)=0 then a.zdamt+ nvl((select bb.zcamt from zfmt0017 bb where a.vbeln = bb.vbeln),0)-a.zhc-a.zgamt else a.ziamt+ nvl((select bb.zcamt from zfmt0017 bb where a.vbeln = bb.vbeln),0)-a.zhc-a.zgamt end) * nvl(a.ukurs, 1)                                                      "
			+"           else                                                                            "
			+"            0                                                                              "
			+"         end as d30,                                                                       "
			+"         case                                                                              "
			+"           when a.zddate < to_char(sysdate - 30, 'yyyyMMdd') and                           "
			+"                a.zddate >= to_char(sysdate - 60, 'yyyyMMdd') then                         "
			+"            (case when nvl(a.ziamt,0)=0 then a.zdamt+ nvl((select bb.zcamt from zfmt0017 bb where a.vbeln = bb.vbeln),0)-a.zhc-a.zgamt else a.ziamt+ nvl((select bb.zcamt from zfmt0017 bb where a.vbeln = bb.vbeln),0)-a.zhc-a.zgamt end) * nvl(a.ukurs, 1)                                                      "
			+"           else                                                                            "
			+"            0                                                                              "
			+"         end as d60,                                                                       "
			+"         case                                                                              "
			+"           when a.zddate < to_char(sysdate - 60, 'yyyyMMdd') and                           "
			+"                a.zddate >= to_char(sysdate - 90, 'yyyyMMdd') then                         "
			+"            (case when nvl(a.ziamt,0)=0 then a.zdamt+ nvl((select bb.zcamt from zfmt0017 bb where a.vbeln = bb.vbeln),0)-a.zhc-a.zgamt else a.ziamt+ nvl((select bb.zcamt from zfmt0017 bb where a.vbeln = bb.vbeln),0)-a.zhc-a.zgamt end) * nvl(a.ukurs, 1)                                                      "
			+"           else                                                                            "
			+"            0                                                                              "
			+"         end as d90,                                                                       "
			+"         case                                                                              "
			+"           when a.zddate < to_char(sysdate - 90, 'yyyyMMdd') and                           "
			+"                a.zddate >= to_char(sysdate - 180, 'yyyyMMdd') then                        "
			+"            (case when nvl(a.ziamt,0)=0 then a.zdamt+ nvl((select bb.zcamt from zfmt0017 bb where a.vbeln = bb.vbeln),0)-a.zhc-a.zgamt else a.ziamt+ nvl((select bb.zcamt from zfmt0017 bb where a.vbeln = bb.vbeln),0)-a.zhc-a.zgamt end) * nvl(a.ukurs, 1)                                                      "
			+"           else                                                                            "
			+"            0                                                                              "
			+"         end as d180,                                                                      "
			+"         case                                                                              "
			+"           when a.zddate < to_char(sysdate - 180, 'yyyyMMdd') then                         "
			+"            (case when nvl(a.ziamt,0)=0 then a.zdamt+ nvl((select bb.zcamt from zfmt0017 bb where a.vbeln = bb.vbeln),0)-a.zhc-a.zgamt else a.ziamt+ nvl((select bb.zcamt from zfmt0017 bb where a.vbeln = bb.vbeln),0)-a.zhc-a.zgamt end) * nvl(a.ukurs, 1)                                                      "
			+"           else                                                                            "
			+"            0                                                                              "
			+"         end as d360,                                                                      "
			+"         case                                                                              "
			+"           when a.zddate < to_char(sysdate, 'yyyyMMdd') and                                "
			+"                a.zddate != '00000000' then                                                "
			+"            (case when nvl(a.ziamt,0)=0 then a.zdamt+ nvl((select bb.zcamt from zfmt0017 bb where a.vbeln = bb.vbeln),0)-a.zhc-a.zgamt else a.ziamt+ nvl((select bb.zcamt from zfmt0017 bb where a.vbeln = bb.vbeln),0)-a.zhc-a.zgamt end) * nvl(a.ukurs, 1)                                                      "
			+"           else                                                                            "
			+"            0                                                                              "
			+"         end as delay,                                                                     "
			+"         (case when nvl(a.ziamt,0)=0 then a.zdamt+ nvl((select bb.zcamt from zfmt0017 bb where a.vbeln = bb.vbeln),0)-a.zhc-a.zgamt else a.ziamt+ nvl((select bb.zcamt from zfmt0017 bb where a.vbeln = bb.vbeln),0)-a.zhc-a.zgamt end) * nvl(a.ukurs, 1) as zbamt,                                               "
			+"         to_number(to_char((case when nvl(a.ziamt,0)=0 then a.zdamt+ nvl((select bb.zcamt from zfmt0017 bb where a.vbeln = bb.vbeln),0)-a.zhc-a.zgamt else a.ziamt+ nvl((select bb.zcamt from zfmt0017 bb where a.vbeln = bb.vbeln),0)-a.zhc-a.zgamt end) * nvl(a.ukurs, 1), '999999999990.99')) as balcny        "
			+"    from zfmt0016 a                                                                        "
			+"   inner join zfmt0007 u                                                                   "
			+"      on a.kunnr = u.kunnr                                                                 "
			+"     and a.bukrs = u.bukrs                                                                 "
			+"   where a.kunnr != ' '                                                                    "
			+ conditionString
			+"     and a.fkdat != '00000000'                                                                       "
			+"     and a.ziamt > 0),                                                                     "
			+"cust as                                                                                    "
			+" (select kunnr, zbemt, zysmt, zwqmt, klimk, ztendency,zkdgp                                "
			+"    from (select kunnr,                                                                    "
			+"                 a.zbemt,                                                                  "
			+"                 a.zysmt,                                                                  "
			+"                 a.zwqmt,                                                                  "
			+"                 a.klimk,                                                                  "
			+"                 a.ztendency,                                                              "
			+"                 a.zkdgp,                                                                  "
			+"                 row_number() over(partition by kunnr order by kunnr) as rn                "
			+"            from zfmt0016 a                                                                "
			+"           where kunnr != ' ')                                                             "
			+"   where rn = 1),                                                                          "
			+"summary as                                                                                 "
			+" (select vkbur,                                                                            "
			+"         kunnr,                                                                            "
			+"         sortl,                                                                            "
			+"         zlwaers,                                                                          "
			+"         decode(sum(dmon),                                                                 "
			+"                0,                                                                         "
			+"                '',                                                                        "
			+"                trim(to_char(sum(dmon), '999,999,999,990.99'))) as dmon,                   "
			+"         decode(sum(d14),                                                                  "
			+"                0,                                                                         "
			+"                '',                                                                        "
			+"                trim(to_char(sum(d14), '999,999,999,990.99'))) as d14,                     "
			+"         decode(sum(d30),                                                                  "
			+"                0,                                                                         "
			+"                '',                                                                        "
			+"                trim(to_char(sum(d30), '999,999,999,990.99'))) as d30,                     "
			+"         decode(sum(d60),                                                                  "
			+"                0,                                                                         "
			+"                '',                                                                        "
			+"                trim(to_char(sum(d60), '999,999,999,990.99'))) as d60,                     "
			+"         decode(sum(d90),                                                                  "
			+"                0,                                                                         "
			+"                '',                                                                        "
			+"                trim(to_char(sum(d90), '999,999,999,990.99'))) as d90,                     "
			+"         decode(sum(d180),                                                                 "
			+"                0,                                                                         "
			+"                '',                                                                        "
			+"                trim(to_char(sum(d180), '999,999,999,990.99'))) as d180,                   "
			+"         decode(sum(d360),                                                                 "
			+"                0,                                                                         "
			+"                '',                                                                        "
			+"                trim(to_char(sum(d360), '999,999,999,990.99'))) as d360,                   "
			+"         trim(to_char(sum(delay), '999,999,999,990.99')) delay,                            "
			+"         trim(to_char(sum(zbamt), '999,999,999,990.99')) zbamt,                            "
			+"         trim(to_char(decode(sum(zbamt),0,0,sum(delay) * 100 / sum(zbamt)), '99990.99')) || '%' depct,  "
			+"         sum(balcny) as balcny                                                             "
			+"    from delay                                                                             "
			+"   group by vkbur, kunnr, sortl, zlwaers)                                                  "
			+"select ta.vkbur,                                                                           "
			+" ta.sortl, /*ta.zlwaers,*/                                                                 "
			+" case when tc.rec>0 then trim(to_char(tc.rec, '999,999,999,990.99')) else '' end 未登记,    "
			+" case when tc.recDo>0 then trim(to_char(tc.recDo, '999,999,999,990.99')) else '' end  登记未记账,"
			+" case when tc.pre>0 then trim(to_char(tc.pre, '999,999,999,990.99')) else '' end 预收未分解,"
			+" case when tc.preDo>0 then trim(to_char(tc.preDo, '999,999,999,990.99')) else '' end 分解后未记账,"
			+" ta.dmon 未来30天收款,                                                                      "
			+" ta.d14 未来两周收款,                                                                       "
			+" trim(to_char(tb.zwqmt, '999,999,999,990.99')) 发货未清金额,                                "
			+" ta.zbamt 应收合计,                                                                         "
			+" ta.d30 逾期30天,                                                                           "
			+" ta.d60 逾期60天,                                                                           "
			+" ta.d90 逾期90天,                                                                           "
			+" ta.d180 逾期180天,                                                                         "
			+" ta.d360 逾期超半年,                                                                        "
			+" ta.delay 逾期合计,                                                                        "
			+" ta.depct 逾期比,                                                                          "
			+" trim(to_char(tb.klimk, '999,999,999,990.99')) SAP授信额度,                                     "
			+" decode(tb.klimk,                                                                          "
			+"        0,                                                                                 "
			+"        '0.00%',                                                                           "
			+"        trim(to_char(decode(tb.klimk,0,0,(ta.balcny + tb.zwqmt + tb.zysmt) * 100 / tb.klimk), "
			+"                     '99990.99')) || '%') 信用占比,                                        "
			+" trim(to_char(tb.zbemt, '999,999,999,990.99')) 信保额度,                                       "
			+" decode(tb.zbemt,                                                                          "
			+"        0,                                                                                 "
			+"        '未投保',                                                                           "
			+"        trim(to_char(decode(tb.zbemt,0,0,(ta.balcny + tb.zysmt) * 100 / tb.zbemt), '99990.99')) || '%') 信保额度占比,"
			+" tb.ztendency 趋势,                                                                        "
			+" ta.balcny 应收款,                                                                         "
			+" tb.zysmt 预收未清金额                                                                                        									 "
			+"  from summary ta                                                                          "
			+"  left join cust tb                                                                        "
			+"    on ta.kunnr = tb.kunnr                                                                 "
			+"  left join presum tc                                                                      "
			+"    on ta.kunnr = tc.kunnr                                                                 "
			+"  where 1=1                                                                                "
			+ deptConditon
			+ custGroupString
			+" order by ta.vkbur,ta.sortl                                                                "
			;
		//System.out.println(sql);
		List list = findListBySql(sql);
		if(list==null||list.size()==0){
			return "<h3>客户应收统计(币种:CNY):无</h3>";
		}
		String[] title = {"部门","客户简称","1.未登记","财务未记账","2.预收未分解","财务未记账","未来30天收款","未来两周收款","发货未开票","应收合计","逾期30天","逾期60天","逾期90天","逾期180天","逾期180天以上","逾期合计","逾期比","SAP授信额度","额度使用率","信保额度","额度使用率","趋势"};
		String[] supTitle = {"","","收款情况","收款情况","收款情况","收款情况","收款情况","收款情况","收款情况","收款情况","4.逾期情况","4.逾期情况","4.逾期情况","4.逾期情况","4.逾期情况","4.逾期情况","统计","统计","统计","统计","统计","统计"};
		Map<Integer,String> map = new HashMap<Integer,String>();
		map.put(9,"#BFDCF9");
		map.put(15,"#BFDCF9");
		return generateTableHtml("客户应收统计(币种:CNY):",title,list,supTitle,null,map,null);
	}
	
	/**
	 * 到款登记提醒
	 * @param userId
	 * @param type
	 * @return
	 */
	public String generateAcctEditLink(String userId,String type,String custGroup){
		String conditionString = "";
		String deptConditon = "";
		if("1".equals(type)){
			conditionString = " and (c.zae='"+userId+"' or a.zsname='"+userId+"')";
		}else if("2".equals(type)){
			conditionString = " and a.zsname='"+userId+"'";
		}else if("3".equals(type)){
			conditionString = " and (c.zrsm='"+userId+"' or a.zsname='"+userId+"')";
		}else if("4".equals(type)){
			conditionString = " and (c.zrname='"+userId+"' or a.zsname='"+userId+"')";
		}else if(StringUtils.isNotBlank(type)){
			deptConditon = " and kv.vkbur = '"+type+"'";
		}
		String custGroupCond = "";
		if(StringUtils.isNotBlank(custGroup)){
			custGroupCond = " and kv.kdgrp='"+custGroup.toUpperCase().trim()+"' ";
		}
		String sql = "select nvl(kv.vkbur,' ') as vkbur,b.ename,case when nvl(d.sn,0)>0 then '开票中' when nvl(d.sn,0)=0 and nvl(d.con,0)>0 then '已开票' else '' end as invStatus,a.bukrs,a.kunnr,a.sortl,a.zbelnr,a.zdoip,to_char(to_date(a.budat,'yyyyMMdd'),'yyyy/MM/dd')"
			+ " ,a.zetime,trim(to_char(a.zcamount,'999,999,999,990.99')),a.waers,a.ztext,a.zsname"
			+ " from zfmt0003 a "
			+ " left join knvv kv on a.kunnr=kv.kunnr and a.bukrs=kv.vkorg "
			+ " left join (select distinct pernr,ename from pa0001) b on a.zsname=b.pernr"
			+ " left join zfmt0007 c on a.bukrs=c.bukrs and a.kunnr = c.kunnr"
			+ " left join (select zbelnr,sum(1) as con,sum(decode(fksta, 'N', 1, 0)) as sn from zfmt0015 group by zbelnr) d "
			+ "  on a.zbelnr=d.zbelnr"
			+ " left join knvv kv on a.kunnr=kv.kunnr and a.bukrs=kv.vkorg"
			+ " where a.zcirs='P'"
			+ custGroupCond
			+ " and a.zdrs='P'"
			+ " and a.bukrs in (1000,7000,7200,7300)"
			+ conditionString
			+ deptConditon
			+ " order by vkbur,a.sortl,a.bldat";
		List<Object> list =findListBySql(sql);
		if(list==null||list.size()==0){
			return "<h3>1.到款登记提醒:无</h3>";
		}
		List<Object[]> newList = new ArrayList<Object[]>();
		for(Object ob:list){
			Object[] obj = (Object[])ob;
			Object[] newObjects = new Object[obj.length+3];
			newObjects[0] = obj[0];
			newObjects[1] = obj[1];
			boolean canEdit = false;
			if("2".equals(type)||StringUtils.nullToString(obj[13]).equals(StringUtils.nullToString(userId))){
				canEdit = true;
			}
			newObjects[2] = "<a href='" + StringUtils.nullToString(propertyConfigurer.getProperty("server.url"))
					+ "/ficoManage/ficoRoute?flowNum="
					+ StringUtils.nullToString(obj[6]) + "&from="
					+ (canEdit ? "yw" : "ae") + "&mail=y&user="
					+ userId + "'>" + (canEdit ? "登记":"查看")
					+ "</a>";
			for(int i=2;i<obj.length;i++){
				newObjects[i+1]=obj[i];
			}
			newObjects[obj.length+1]="未分解";
			newObjects[obj.length+2]="维护";
			newList.add(newObjects);
		}
		String[] title = {"部门","业务员","操作","开票状态","接单主体","客户编号","客户简称","流水号","国际收支申报单号","收款日期","提醒日期","收款金额","币种","业务对象（摘要）","预收状态","流转状态"};
		return generateTableHtml("1.到款登记提醒:",title,newList,null,null,null,null);
	}
	
	/**
	 * 到款分解提醒
	 * @param userId
	 * @param type
	 * @return
	 */
	public String generateAcctSeprateLink(String userId,String type,String custGroup){
		String conditionString = "";
		String deptConditon = "";
		if("1".equals(type)){
			conditionString = " and (d.zae='"+userId+"' or b.zsname='"+userId+"')";
		}else if("2".equals(type)){
			conditionString = " and b.zsname='"+userId+"'";
		}else if("3".equals(type)){
			conditionString = " and (d.zrsm='"+userId+"' or b.zsname='"+userId+"')";
		}else if("4".equals(type)){
			conditionString = " and (d.zrname='"+userId+"' or b.zsname='"+userId+"')";
		}else if(StringUtils.isNotBlank(type)){
			deptConditon = " and kv.vkbur = '"+type+"'";
		}
		String custGroupCond = "";
		if(StringUtils.isNotBlank(custGroup)){
			custGroupCond = " and kv.kdgrp='"+custGroup.toUpperCase().trim()+"' ";
		}
		String sql = "select nvl(kv.vkbur,' ') as vkbur,c.ename,case when nvl(e.sn,0)>0 then '开票中' when nvl(e.sn,0)=0 and nvl(e.con,0)>0 then '已开票' else '' end as invStatus,b.bukrs,b.kunnr,b.sortl,b.zbelnr,b.zdoip,to_char(to_date(b.budat,'yyyyMMdd'),'yyyy/MM/dd')"
			+ " ,trim(to_char(a.zcamount,'999,999,999,990.99')),a.waers,a.bstkd,b.sgtxt,b.zsname"
			+ " from zfmt0004 a"
			+ " inner join zfmt0003 b on a.zbelnr = b.zbelnr"
			+ " left join (select distinct pernr,ename from pa0001) c on b.zsname=c.pernr"
			+ " left join zfmt0007 d on a.bukrs=d.bukrs and a.kunnr = d.kunnr"
			+ " left join (select zbelnr,sum(1) as con,sum(decode(fksta, 'N', 1, 0)) as sn from zfmt0015 group by zbelnr) e "
			+ " on a.zbelnr=e.zbelnr"
			+ " left join knvv kv on b.kunnr=kv.kunnr and b.bukrs=kv.vkorg"
			+ " where a.zrbl='A'"
			+ " and a.zdrs='P'"
			+ " and a.zcirs='C'"
			+ conditionString
			+ custGroupCond
			+ deptConditon
			+ " order by vkbur,sortl,budat";
		List<Object> list =findListBySql(sql);
		if(list==null||list.size()==0){
			return "<h3>2.预收款分解提醒:无</h3>";
		}
		List<Object[]> newList = new ArrayList<Object[]>();
		for(Object ob:list){
			Object[] obj = (Object[])ob;
			Object[] newObjects = new Object[obj.length+3];
			newObjects[0] = obj[0];
			newObjects[1] = obj[1];
			boolean canEdit = false;
			if("2".equals(type)||StringUtils.nullToString(obj[13]).equals(StringUtils.nullToString(userId))){
				canEdit = true;
			}
			newObjects[2] = "<a href='" + StringUtils.nullToString(propertyConfigurer.getProperty("server.url"))
					+ "/ficoManage/ficoRoute?flowNum="
					+ StringUtils.nullToString(obj[6]) + "&from="
					+ (canEdit ? "yw" : "ae") + "&mail=y&user="
					+ userId + "'>" + (canEdit ?  "分解":"查看")
					+ "</a>";
			for(int i=2;i<obj.length;i++){
				newObjects[i+1]=obj[i];
			}
			newObjects[obj.length+1]="未分解";
			newObjects[obj.length+2]="已生成凭证";
			newList.add(newObjects);
		}
		String[] title = {"部门","业务员","操作","开票状态","接单主体","客户编号","客户简称","流水号","国际收支申报单号","收款日期","收款金额","币种","合同号","业务对象（摘要）","预收状态","流转状态"};
		return generateTableHtml("2.预收款分解提醒:",title,newList,null,null,null,null);
	}
	
	/**
	 * 查找未来两周应收款
	 * @param userId
	 * @param type
	 * @return
	 */
	public String findReceivableNextMonth(String userId,String type,String custGroup){
		String conditionString = "";
		String deptConditon = "";
		if("1".equals(type)){
			conditionString = " and b.zae='"+userId+"'";
		}else if("2".equals(type)){
			conditionString = " and b.zaa='"+userId+"'";
		}else if("3".equals(type)){
			conditionString = " and b.zrsm='"+userId+"'";
		}else if("4".equals(type)&&StringUtils.isBlank(custGroup)){
			conditionString = " and b.zrname='"+userId+"'";
		}else if(StringUtils.isNotBlank(type)){
			deptConditon = " and a.vkbur='"+type+"'";
		}
		String custGroupCond = "";
		if(StringUtils.isNotBlank(custGroup)){
			custGroupCond = " and a.zkdgp='"+custGroup.toUpperCase().trim()+"' ";
		}
		
		String sql = ""
			+"with base as(                                                  "
			+" select distinct a.vbeln,d.bstnk_vf from zfmt0016 a            "
			+" left join lips b on a.vbeln=b.vbeln                           "
			+" left join vbrp c on c.vgbel=b.vbeln and c.vgpos=b.posnr       "
			+" left join vbrk d on c.vbeln=d.vbeln                           "
			+" where a.vbeln like '008%'                                     "
			+" and d.fkart in ('ZF2','S1')                                   "
			+" ),sc as (                                                     "
			+" select vbeln,listagg(bstnk_vf,',') within group(order by bstnk_vf) as bstnk_vf from ( "
			+" select distinct vbeln,bstnk_vf from base )                    "
			+" group by vbeln                                                "
			+")                                                              "
			+"select a.vkbur,                                                "
			+"       a.vbeln,                                                "
			+"       a.bukrs,                                                "
			+"       a.maktx,                                                "
			+"       a.znation,                                              "
			+"       a.kunnr,                                                "
			+"       a.sortl,                                                "
			+"       trim(to_char(a.zdamt, '999,999,999,990.99')),           "
			+"       trim(to_char(a.ziamt, '999,999,999,990.99')),           "
			+"       trim(to_char(a.zgamt, '999,999,999,990.99')),           "
			+"       trim(to_char((case when nvl(a.ziamt,0)=0 then a.zdamt+ nvl((select bb.zcamt from zfmt0017 bb where a.vbeln = bb.vbeln),0)-a.zhc-a.zgamt else a.ziamt+ nvl((select bb.zcamt from zfmt0017 bb where a.vbeln = bb.vbeln),0)-a.zhc-a.zgamt end), '999,999,999,990.99')),           "
			+"       a.zlwaers,                                              "
			+"       to_char(to_date(a.zddate, 'yyyyMMdd'), 'yyyy/MM/dd'),   ";
		if("4".equals(type)){
			sql += "       to_char(to_date(a.zkdate, 'yyyyMMdd'), 'yyyy/MM/dd'),   ";
		}
		sql +="       sc.bstnk_vf,   										 "
			+"       case                                                    "
			+"         when a.zddate < to_char(sysdate + 15, 'yyyyMMdd') then"
			+"          1                                                    "
			+"         else                                                  "
			+"          0                                                    "
			+"       end as red                                              "
			+"  from zfmt0016 a                                              "
			+" inner join zfmt0007 b                                         "
			+"    on a.bukrs = b.bukrs                                       "
			+"   and a.kunnr = b.kunnr                                       "
			+" left join sc on a.vbeln=sc.vbeln                              "
			+" where a.zddate between to_char(sysdate, 'yyyyMMdd') and  "
			+"       to_char(sysdate + 30, 'yyyyMMdd')                       "
			+"   and (case when nvl(a.ziamt,0)=0 then a.zdamt+ nvl((select bb.zcamt from zfmt0017 bb where a.vbeln = bb.vbeln),0)-a.zhc-a.zgamt else a.ziamt+ nvl((select bb.zcamt from zfmt0017 bb where a.vbeln = bb.vbeln),0)-a.zhc-a.zgamt end) > 0                                               "
			+conditionString
			+custGroupCond
			+deptConditon
			+"  order by a.zddate,a.vkbur,a.vbeln";
		List<Object> list = findListBySql(sql);
		if(list==null||list.size()==0){
			return "<h3>3.未来30天应收款明细:无</h3>";
		}
		Map<Integer,String> map = new HashMap<Integer,String>();
		for(int i=0;i<list.size();i++){
			map.put(i,StringUtils.nullToString(((Object[])list.get(i))[((Object[])list.get(i)).length-1]));
		}
		String[] titleZr = {"部门","外向交货单","接单主体","品名","出口国家","客户编号","客户简称","发货金额","开票金额","已收金额","应收余额","币种","到期日","到期日(放宽后)","合同号"};
		String[] title = {"部门","外向交货单","接单主体","品名","出口国家","客户编号","客户简称","发货金额","开票金额","已收金额","应收余额","币种","到期日","合同号"};
		return generateTableHtml("3.未来30天应收款明细:","4".equals(type)?titleZr:title,list,null,map,null,null);
	}
	
	/**
	 * 查找逾期款项列表
	 * @param userId
	 * @param type
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public String findDelayList(String userId,String type,String custGroup){
		String conditionString = "";
		String deptConditon = "";
		if("1".equals(type)){
			conditionString = " and u.zae='"+userId+"'";
		}else if("2".equals(type)){
			conditionString = " and u.zaa='"+userId+"'";
		}else if("3".equals(type)){
			conditionString = " and u.zrsm='"+userId+"'";
		}else if("4".equals(type)&&StringUtils.isBlank(custGroup)){
			conditionString = " and u.zrname='"+userId+"'";
		}else if(StringUtils.isNotBlank(type)){
			//销售部门
			deptConditon = " and a.vkbur='"+type+"'";
		}
		String custGroupCond = "";
		if(StringUtils.isNotBlank(custGroup)){
			custGroupCond = " and a.zkdgp='"+custGroup.toUpperCase().trim()+"' ";
		}
		String sql = ""
			+"with base as(                                                 "
			+" select distinct a.vbeln,d.bstnk_vf from zfmt0016 a           "
			+" left join lips b on a.vbeln=b.vbeln                          "
			+" left join vbrp c on c.vgbel=b.vbeln and c.vgpos=b.posnr      "
			+" left join vbrk d on c.vbeln=d.vbeln                          "
			+" where a.vbeln like '008%'                                    "
			+" and d.fkart in ('ZF2','S1')                                  "
			+" ),sc as (                                                    "
			+" select vbeln,listagg(bstnk_vf,',') within group(order by bstnk_vf) as bstnk_vf from ( "
			+" select distinct vbeln,bstnk_vf from base )                   "
			+" group by vbeln                                               "
			+")                                                             "
			+"select a.vkbur,a.sortl,a.bukrs,substr(a.vbeln,3),to_char(to_date(a.zddate,'yyyyMMdd'),'yyyy/MM/dd'), ";
		if("4".equals(type)){
			//发给AA/AE/RSM 隐藏宽限日
			sql+="to_char(to_date(a.zkdate,'yyyyMMdd'),'yyyy/MM/dd'),";
		}
		sql +="trunc(sysdate-to_date(a.zddate,'yyyyMMdd')),                  "
			+"to_char((case when nvl(a.ziamt,0)=0 then a.zdamt+ nvl((select bb.zcamt from zfmt0017 bb where a.vbeln = bb.vbeln),0)-a.zhc-a.zgamt else a.ziamt+ nvl((select bb.zcamt from zfmt0017 bb where a.vbeln = bb.vbeln),0)-a.zhc-a.zgamt end),'999,999,999,990.99'),a.zlwaers,sc.bstnk_vf   "
			+"from zfmt0016 a                                               "
			+"inner join zfmt0007 u on a.kunnr=u.kunnr and a.bukrs=u.bukrs  "
			+"left join sc on a.vbeln=sc.vbeln                              "
			+"where (case when nvl(a.ziamt,0)=0 then a.zdamt+ nvl((select bb.zcamt from zfmt0017 bb where a.vbeln = bb.vbeln),0)-a.zhc-a.zgamt else a.ziamt+ nvl((select bb.zcamt from zfmt0017 bb where a.vbeln = bb.vbeln),0)-a.zhc-a.zgamt end)>0                                               "
			+"and a.zddate<to_char(sysdate,'yyyyMMdd') "
			+conditionString
			+custGroupCond
			+deptConditon
			+"and a.ziamt >0                                                "
			+"and a.fkdat !='00000000'                                      "
			+"and a.zkdate!='00000000'                                      "
			+"order by a.zddate asc,a.vkbur asc,a.zbamt desc ";
		List list = findListBySql(sql);
		if(list==null||list.size()==0){
			return "<h3>4.单笔逾期明细:无</h3>";
		}
		String[] titleZr = {"部门","客户简称","接单主体","外向交货单","到期日","到期日(放宽后)","逾期天数","逾期金额","币种","合同号"};
		String[] title = {"部门","客户简称","接单主体","外向交货单","到期日","逾期天数","逾期金额","币种","合同号"};
		return generateTableHtml("4.单笔逾期明细(按逾期天数排序):","4".equals(type)?titleZr:title,list,null,null,null,null);
	}

	@Override
	public void queryAllAeAndAAToSendReoprt() {
		String sql = ""
			+"with cust as (                                                                     "
			+"select distinct kunnr,bukrs from zfmt0016                                          "
			+"),usr as (                                                                         "
			+"select distinct tp,usr,kunnr,bukrs from (                                          "
			+"select distinct 1 as tp,zae as usr,kunnr,bukrs from zfmt0007                       "
			+"union                                                                              "
			+"select distinct 2,zaa,kunnr,bukrs from zfmt0007                                    "
			+"union                                                                              "
			+"select distinct 3,zrsm,kunnr,bukrs from zfmt0007                                   "
			+"union                                                                              "
			+"select distinct 4,zrname,kunnr,bukrs from zfmt0007                                 "
			+")),mail as(                                                                        "
			+"select distinct pernr,usrid from (                                                 "
			+"select pernr,usrid,row_number() over(partition by pernr order by begda desc) as rn " 
			+"from pa0105 where usrty='MAIL' and usrid like '%LEEDARSON.COM'                     "
			+")where rn=1                                                                        "
			+")                                                                                  "
			+"select distinct a.pernr,trim(a.usrid),b.tp from pa0105 a                           "
			+"inner join usr b on a.pernr = b.usr                                                "
			+"where a.usrty='MAIL' and a.usrid like '%LEEDARSON.COM'                             "
			;
		List<Object> list =findListBySql(sql);
		//单独发送X客户信息给许海燕
		sendXCustomerReportToZr();
		for (Object ob : list) {
			Object[] objects = (Object[])ob;
			//向AA、AE、RSM、应收人员发送日报表
			boolean flag= sendReportToSingleAEOrAA(StringUtils.nullToString(objects[0]),
					StringUtils.nullToString(objects[1]),
					StringUtils.nullToString(objects[2]));
			if(!flag){
				logger.error("向"+StringUtils.nullToString(objects[0])+"("+StringUtils.nullToString(objects[1])+")发送业务员报表失败");
			}
		}
	}
	
	public boolean sendXCustomerReportToZr(){
		boolean result=false;
		StringBuffer html = new StringBuffer();
		html.append("<h4>此邮件为系统自动发送,请勿回复.</h4>")
			.append(generateLinks("11000557",""))//相关链接
			.append(findReceivebleInfo("11000557","","X"))//客户汇总
			.append(generateAcctEditLink("11000557","","X"))//到账登记
			.append(generateAcctSeprateLink("11000557","","X"))//预收分解
			.append(findReceivableNextMonth("11000557","4","X"))//未来30天收款
			.append(findDelayList("11000557","4","X"))//逾期列表
			;
		if(StringUtils.isBlank(html.toString()))
			return true;
		//send Email begin
		MailUtil mailUtil = new MailUtil();
		mailUtil.setHost("mail.leedarson.com");
		mailUtil.setUsername("IT@leedarson.com");
		mailUtil.setPassword("it612345");
		mailUtil.setFrom("IT@leedarson.com");
		String date = DateUtils.formatDate(new Date(),"yyyy/MM/dd");
		mailUtil.setSubject("X客户收款日报: "+date);
		mailUtil.setTo("xuhy@leedarson.com");
		mailUtil.setTobcc("hes@leedarson.com");
		mailUtil.setContent(html.toString());
		result = mailUtil.send();
		//send Email end
		return result;
	}

	@Override
	public void sendDailyReoprtToSpecialDep(String dept) {
		String sql = "select distinct a.zrsm from zfmt0007 a inner join knvv b on a.kunnr=b.kunnr and a.bukrs=b.vkorg where b.vkbur = '"+dept+"'";
		List<Object> list = findListBySql(sql);
		String userId = "";
		if(null!=list && list.size()>0){
			userId = StringUtils.nullToString(list.get(0));
		}
		boolean result=false;
		StringBuffer html = new StringBuffer();
		html.append("<h4>此邮件为系统自动发送,请勿回复.</h4>")
			.append(generateLinks(userId,dept))//相关链接
			.append(findReceivebleInfo(userId,dept,""))//客户汇总
			.append(generateAcctEditLink(userId,dept,""))//到账登记
			.append(generateAcctSeprateLink(userId,dept,""))//预收分解
			.append(findReceivableNextMonth(userId,dept,""))//未来30天收款
			.append(findDelayList(userId,dept,""))//逾期列表
			;
		//send Email begin
		MailUtil mailUtil = new MailUtil();
		mailUtil.setHost("mail.leedarson.com");
		mailUtil.setUsername("IT@leedarson.com");
		mailUtil.setPassword("it612345");
		mailUtil.setFrom("IT@leedarson.com");
		String date = DateUtils.formatDate(new Date(),"yyyy/MM/dd");
		mailUtil.setSubject(dept+"收款日报: "+date);
		mailUtil.setTo(findEmail("daily"+dept,SENDTYPE_TO));
		mailUtil.setTobcc("hes@leedarson.com");
		mailUtil.setContent(html.toString());
		result = mailUtil.send();
		//send Email end
		if(!result){
			logger.error("发送"+dept+"业务员报表失败");
		}
	}

	@Override
	public void sendReoprtToSellManagers(String to, String cc) {
		StringBuffer html = new StringBuffer();
		html
			.append(findReceivableSummary())//客户汇总
			.append(generateAcctEditLink("","",""))//登记提醒
			;
		//send Email begin
		MailUtil mailUtil = new MailUtil();
		mailUtil.setHost("mail.leedarson.com");
		mailUtil.setUsername("IT@leedarson.com");
		mailUtil.setPassword("it612345");
		mailUtil.setFrom("IT@leedarson.com");
		String date = DateUtils.formatDate(new Date(),"yyyy/MM/dd");
		mailUtil.setSubject("业务员收款日报: "+date);
		mailUtil.setTo(StringUtils.isBlank(to)?findEmail(FC_DAILY_BALANCE_ALL,SENDTYPE_TO):to);
		mailUtil.setTocc(StringUtils.isBlank(cc)?findEmail(FC_DAILY_BALANCE_ALL,SENDTYPE_CC):cc);
		mailUtil.setTobcc("hes@leedarson.com");
		mailUtil.setContent(html.toString());
		boolean res=mailUtil.send();
		logger.error("收款日报表发送"+(res?"成功!":"失败!!!")+date);
		//send Email end
	}
	
	/**
	 * 查询公司各销售部门营收汇总信息
	 * @return
	 */
	public String findReceivableSummary() {
		String sql = ""                                                                                  
			+"with curr as                                                                              "
			+" (select fcurr, tcurr, substr(to_char(99999999 - gdatu), 0, 6) as mon, ukurs              "
			+"    from tcurr                                                                            "
			+"   where kurst = 'Z' and tcurr='CNY'),                                                    "
			+"pre as                                                                                    "
			+" (select a.zbelnr,                                                                        "
			+"         sum(a.zcamount - nvl(b.unBelnr, 0)) as zcamount,                                 "
			+"         sum(nvl(b.belnr, 0)) as zcamountDo                                               "
			+"    from zfmt0004 a                                                                       "
			+"    left join (select zposnr_s,                                                           "
			+"                     zbelnr,                                                              "
			+"                     sum(case                                                             "
			+"                           when zdrs = 'F' and zcirs = 'C' then                           "
			+"                            zcamount                                                      "
			+"                           else                                                           "
			+"                            0                                                             "
			+"                         end) as belnr,                                                   "
			+"                     sum(case                                                             "
			+"                           when zcirs = 'C' then                                          "
			+"                            zcamount                                                      "
			+"                           else                                                           "
			+"                            0                                                             "
			+"                         end) as unBelnr                                                  "
			+"                from zfmt0004                                                             "
			+"               where zposnr_s != '00000'                                                  "
			+"                 and zposnr_s != ' '                                                      "
			+"               group by zposnr_s, zbelnr) b                                               "
			+"      on a.zbelnr = b.zbelnr                                                              "
			+"     and a.zposnr = b.zposnr_s                                                            "
			+"   where a.zrbl = 'A'                                                                     "
			+"     and a.zdrs != 'C'                                                                    "
			+"     and a.zcirs = 'C'                                                                    "
			+"   group by a.zbelnr),                                                                    "
			+"presum as                                                                                 "
			+" (select ta.kunnr,                                                                        "
			+"         sum(to_number(to_char((case                                                      "
			+"                                 when ta.zdrs = 'P' and ta.zcirs = 'P' then               "
			+"                                  ta.zcamount                                             "
			+"                                 else                                                     "
			+"                                  0                                                       "
			+"                               end) *                                                     "
			+"                               nvl(decode(ta.waers, 'CNY', 1, tb.ukurs), 1),              "
			+"                               '99999999990.00'))) as rec,                                "
			+"         sum(to_number(to_char((case                                                      "
			+"                                 when ta.zdrs != 'C' and ta.zcirs = 'F' then              "
			+"                                  ta.zcamount                                             "
			+"                                 else                                                     "
			+"                                  0                                                       "
			+"                               end) *                                                     "
			+"                               nvl(decode(ta.waers, 'CNY', 1, tb.ukurs), 1),              "
			+"                               '99999999990.00'))) as recDo,                              "
			+"         sum(to_number(to_char(nvl(tc.zcamount, 0) *                                      "
			+"                               nvl(decode(ta.waers, 'CNY', 1, tb.ukurs), 1),              "
			+"                               '99999999990.00'))) as pre,                                "
			+"         sum(to_number(to_char(nvl(tc.zcamountDo, 0) *                                    "
			+"                               nvl(decode(ta.waers, 'CNY', 1, tb.ukurs), 1),              "
			+"                               '99999999990.00'))) as preDo                               "
			+"    from zfmt0003 ta                                                                      "
			+"    left join curr tb                                                                     "
			+"      on ta.waers = tb.fcurr                                                              "
			+"     and to_char(add_months(to_date(ta.bldat, 'yyyyMMdd'), -1), 'yyyyMM') =               "
			+"         tb.mon                                                                           "
			+"    left join pre tc                                                                      "
			+"      on ta.zbelnr = tc.zbelnr                                                            "
			+"   inner join zfmt0007 u                                                                  "
			+"      on ta.kunnr = u.kunnr                                                               "
			+"     and ta.bukrs = u.bukrs                                                               "
			+"   where ta.kunnr != '...'                                                                "
			+"   group by ta.kunnr),                                                                    "
			+"delay as                                                                                  "
			+" (select a.vkbur,                                                                         "
			+"         a.kunnr,                                                                         "
			+"         a.sortl,                                                                         "
			+"         a.zlwaers,                                                                       "
			+"         case                                                                             "
			+"           when a.zddate >=                               "
			+"                to_char(sysdate, 'yyyyMMdd') and                                          "
			+"                a.zddate <                                "
			+"                to_char(sysdate + 30, 'yyyyMMdd') then                                    "
			+"            (case when nvl(a.ziamt,0)=0 then a.zdamt+ nvl((select bb.zcamt from zfmt0017 bb where a.vbeln = bb.vbeln),0)-a.zhc-a.zgamt else a.ziamt+ nvl((select bb.zcamt from zfmt0017 bb where a.vbeln = bb.vbeln),0)-a.zhc-a.zgamt end) * nvl(a.ukurs, 1)                                                     "
			+"           else                                                                           "
			+"            0                                                                             "
			+"         end as dmon,                                                                     "
			+"         case                                                                             "
			+"           when a.zddate >=                               "
			+"                to_char(sysdate, 'yyyyMMdd') and                                          "
			+"                a.zddate <                                "
			+"                to_char(sysdate + 15, 'yyyyMMdd') then                                    "
			+"            (case when nvl(a.ziamt,0)=0 then a.zdamt+ nvl((select bb.zcamt from zfmt0017 bb where a.vbeln = bb.vbeln),0)-a.zhc-a.zgamt else a.ziamt+ nvl((select bb.zcamt from zfmt0017 bb where a.vbeln = bb.vbeln),0)-a.zhc-a.zgamt end) * nvl(a.ukurs, 1)                                                     "
			+"           else                                                                           "
			+"            0                                                                             "
			+"         end as d14,                                                                      "
			+"         case                                                                             "
			+"           when a.zddate <                                "
			+"                to_char(sysdate, 'yyyyMMdd') and                                          "
			+"                a.zddate >=                               "
			+"                to_char(sysdate - 30, 'yyyyMMdd') then                                    "
			+"            (case when nvl(a.ziamt,0)=0 then a.zdamt+ nvl((select bb.zcamt from zfmt0017 bb where a.vbeln = bb.vbeln),0)-a.zhc-a.zgamt else a.ziamt+ nvl((select bb.zcamt from zfmt0017 bb where a.vbeln = bb.vbeln),0)-a.zhc-a.zgamt end) * nvl(a.ukurs, 1)                                                     "
			+"           else                                                                           "
			+"            0                                                                             "
			+"         end as d30,                                                                      "
			+"         case                                                                             "
			+"           when a.zddate <                                "
			+"                to_char(sysdate - 30, 'yyyyMMdd') and                                     "
			+"                a.zddate >=                               "
			+"                to_char(sysdate - 60, 'yyyyMMdd') then                                    "
			+"            (case when nvl(a.ziamt,0)=0 then a.zdamt+ nvl((select bb.zcamt from zfmt0017 bb where a.vbeln = bb.vbeln),0)-a.zhc-a.zgamt else a.ziamt+ nvl((select bb.zcamt from zfmt0017 bb where a.vbeln = bb.vbeln),0)-a.zhc-a.zgamt end) * nvl(a.ukurs, 1)                                                     "
			+"           else                                                                           "
			+"            0                                                                             "
			+"         end as d60,                                                                      "
			+"         case                                                                             "
			+"           when a.zddate <                                "
			+"                to_char(sysdate - 60, 'yyyyMMdd') and                                     "
			+"                a.zddate >=                               "
			+"                to_char(sysdate - 90, 'yyyyMMdd') then                                    "
			+"            (case when nvl(a.ziamt,0)=0 then a.zdamt+ nvl((select bb.zcamt from zfmt0017 bb where a.vbeln = bb.vbeln),0)-a.zhc-a.zgamt else a.ziamt+ nvl((select bb.zcamt from zfmt0017 bb where a.vbeln = bb.vbeln),0)-a.zhc-a.zgamt end) * nvl(a.ukurs, 1)                                                     "
			+"           else                                                                           "
			+"            0                                                                             "
			+"         end as d90,                                                                      "
			+"         case                                                                             "
			+"           when a.zddate <                                "
			+"                to_char(sysdate - 90, 'yyyyMMdd') and                                     "
			+"                a.zddate >=                               "
			+"                to_char(sysdate - 180, 'yyyyMMdd') then                                   "
			+"            (case when nvl(a.ziamt,0)=0 then a.zdamt+ nvl((select bb.zcamt from zfmt0017 bb where a.vbeln = bb.vbeln),0)-a.zhc-a.zgamt else a.ziamt+ nvl((select bb.zcamt from zfmt0017 bb where a.vbeln = bb.vbeln),0)-a.zhc-a.zgamt end) * nvl(a.ukurs, 1)                                                     "
			+"           else                                                                           "
			+"            0                                                                             "
			+"         end as d180,                                                                     "
			+"         case                                                                             "
			+"           when a.zddate <                                "
			+"                to_char(sysdate - 180, 'yyyyMMdd') then                                   "
			+"            (case when nvl(a.ziamt,0)=0 then a.zdamt+ nvl((select bb.zcamt from zfmt0017 bb where a.vbeln = bb.vbeln),0)-a.zhc-a.zgamt else a.ziamt+ nvl((select bb.zcamt from zfmt0017 bb where a.vbeln = bb.vbeln),0)-a.zhc-a.zgamt end) * nvl(a.ukurs, 1)                                                     "
			+"           else                                                                           "
			+"            0                                                                             "
			+"         end as d360,                                                                     "
			+"         case                                                                             "
			+"           when a.zddate <                                "
			+"                to_char(sysdate, 'yyyyMMdd') and                                          "
			+"                a.zddate != '00000000' then               "
			+"            (case when nvl(a.ziamt,0)=0 then a.zdamt+ nvl((select bb.zcamt from zfmt0017 bb where a.vbeln = bb.vbeln),0)-a.zhc-a.zgamt else a.ziamt+ nvl((select bb.zcamt from zfmt0017 bb where a.vbeln = bb.vbeln),0)-a.zhc-a.zgamt end) * nvl(a.ukurs, 1)                                                     "
			+"           else                                                                           "
			+"            0                                                                             "
			+"         end as delay,                                                                    "
			+"         (case when nvl(a.ziamt,0)=0 then a.zdamt+ nvl((select bb.zcamt from zfmt0017 bb where a.vbeln = bb.vbeln),0)-a.zhc-a.zgamt else a.ziamt+ nvl((select bb.zcamt from zfmt0017 bb where a.vbeln = bb.vbeln),0)-a.zhc-a.zgamt end) * nvl(a.ukurs, 1) as zbamt,                                              "
			+"         to_number(to_char((case when nvl(a.ziamt,0)=0 then a.zdamt+ nvl((select bb.zcamt from zfmt0017 bb where a.vbeln = bb.vbeln),0)-a.zhc-a.zgamt else a.ziamt+ nvl((select bb.zcamt from zfmt0017 bb where a.vbeln = bb.vbeln),0)-a.zhc-a.zgamt end) * nvl(a.ukurs, 1), '999999999990.99')) as balcny       "
			+"    from zfmt0016 a                                                                       "
			+"   inner join zfmt0007 u                                                                  "
			+"      on a.kunnr = u.kunnr                                                                "
			+"     and a.bukrs = u.bukrs                                                                "
			+"   where a.kunnr != ' '                                                                   "
			+"     and a.fkdat != '00000000'                                                            "
			+"     and a.ziamt > 0),                                                                    "
			+"cust as                                                                                   "
			+" (select kunnr, zbemt, zysmt, zwqmt, klimk, ztendency                                     "
			+"    from (select kunnr,                                                                   "
			+"                 a.zbemt,                                                                 "
			+"                 a.zysmt,                                                                 "
			+"                 a.zwqmt,                                                                 "
			+"                 a.klimk,                                                                 "
			+"                 a.ztendency,                                                             "
			+"                 row_number() over(partition by kunnr order by kunnr) as rn               "
			+"            from zfmt0016 a                                                               "
			+"           where kunnr != ' ')                                                            "
			+"   where rn = 1),                                                                         "
			+"summary as                                                                                "
			+" (select vkbur,                                                                           "
			+"         kunnr,                                                                           "
			+"         sortl,                                                                           "
			+"         zlwaers,                                                                         "
			+"         decode(sum(dmon),                                                                "
			+"                0,                                                                        "
			+"                '',                                                                       "
			+"                trim(to_char(sum(dmon), '999,999,999,990.99'))) as dmon,                  "
			+"         sum(dmon) as dmonsum,                                                            "
			+"         decode(sum(d14),                                                                 "
			+"                0,                                                                        "
			+"                '',                                                                       "
			+"                trim(to_char(sum(d14), '999,999,999,990.99'))) as d14,                    "
			+"         sum(d14) as d14sum,                                                              "
			+"         decode(sum(d30),                                                                 "
			+"                0,                                                                        "
			+"                '',                                                                       "
			+"                trim(to_char(sum(d30), '999,999,999,990.99'))) as d30,                    "
			+"         sum(d30) as d30sum,                                                              "
			+"         decode(sum(d60),                                                                 "
			+"                0,                                                                        "
			+"                '',                                                                       "
			+"                trim(to_char(sum(d60), '999,999,999,990.99'))) as d60,                    "
			+"         sum(d60) as d60sum,                                                              "
			+"         decode(sum(d90),                                                                 "
			+"                0,                                                                        "
			+"                '',                                                                       "
			+"                trim(to_char(sum(d90), '999,999,999,990.99'))) as d90,                    "
			+"         sum(d90) as d90sum,                                                              "
			+"         decode(sum(d180),                                                                "
			+"                0,                                                                        "
			+"                '',                                                                       "
			+"                trim(to_char(sum(d180), '999,999,999,990.99'))) as d180,                  "
			+"         sum(d180) as d180sum,                                                            "
			+"         decode(sum(d360),                                                                "
			+"                0,                                                                        "
			+"                '',                                                                       "
			+"                trim(to_char(sum(d360), '999,999,999,990.99'))) as d360,                  "
			+"         sum(d360) as d360sum,                                                            "
			+"         trim(to_char(sum(delay), '999,999,999,990.99')) delay,                           "
			+"         sum(delay) as delaysum,                                                          "
			+"         trim(to_char(sum(zbamt), '999,999,999,990.99')) zbamt,                           "
			+"         sum(zbamt) as zbamtsum,                                                          "
			+"         trim(to_char(decode(sum(zbamt),0,0,sum(delay) * 100 / sum(zbamt)), '99990.99')) || '%' depct,  "
			+"         sum(balcny) as balcny                                                            "
			+"    from delay                                                                            "
			+"   group by vkbur, kunnr, sortl, zlwaers)                                                 "
			+"select ta.vkbur as vkbur,                                                                 "
			+"       ta.sortl as sortl,                                                                 "
			+"       case                                                                               "
			+"         when tc.rec > 0 then                                                             "
			+"          trim(to_char(tc.rec, '999,999,999,990.99'))                                     "
			+"         else                                                                             "
			+"          ''                                                                              "
			+"       end 未登记,                                                                           "
			+"       case                                                                               "
			+"         when tc.recDo > 0 then                                                           "
			+"          trim(to_char(tc.recDo, '999,999,999,990.99'))                                   "
			+"         else                                                                             "
			+"          ''                                                                              "
			+"       end 登记未记账,                                                                         "
			+"       case                                                                               "
			+"         when tc.pre > 0 then                                                             "
			+"          trim(to_char(tc.pre, '999,999,999,990.99'))                                     "
			+"         else                                                                             "
			+"          ''                                                                              "
			+"       end 预收未分解,                                                                         "
			+"       case                                                                               "
			+"         when tc.preDo > 0 then                                                           "
			+"          trim(to_char(tc.preDo, '999,999,999,990.99'))                                   "
			+"         else                                                                             "
			+"          ''                                                                              "
			+"       end 分解后未记账,                                                                        "
			+"       ta.dmon 未来30天收款,                                                                   "
			+"       ta.d14 未来两周收款,                                                                     "
			+"       trim(to_char(tb.zwqmt, '999,999,999,990.99')) 发货未清金额,                              "
			+"       ta.zbamt 应收合计,                                                                     "
			+"       ta.d30 逾期30天,                                                                      "
			+"       ta.d60 逾期60天,                                                                      "
			+"       ta.d90 逾期90天,                                                                      "
			+"       ta.d180 逾期180天,                                                                    "
			+"       ta.d360 逾期超半年,                                                                     "
			+"       ta.delay 逾期合计,                                                                     "
			+"       ta.depct 逾期比,                                                                      "
			+"       trim(to_char(tb.klimk, '999,999,999,990.99')) SAP授信额度,                                 "
			+"       decode(tb.klimk,                                                                   "
			+"              0,                                                                          "
			+"              '0.00%',                                                                    "
			+"              trim(to_char((ta.balcny + tb.zwqmt + tb.zysmt) * 100 /                      "
			+"                           tb.klimk,                                                      "
			+"                           '99990.99')) || '%') 信用占比,                                     "
			+"       trim(to_char(tb.zbemt, '999,999,999,990.99')) 信保额度,                                  "
			+"       decode(tb.zbemt,                                                                   "
			+"              0,                                                                          "
			+"              '未投保',                                                                      "
			+"              trim(to_char((ta.balcny + tb.zysmt) * 100 / tb.zbemt,                       "
			+"                           '99990.99')) || '%') 信保额度占比,                                     "
			+"       tb.ztendency 趋势,                                                                   "
			+"       ta.balcny 应收款,                                                                     "
			+"       tb.zysmt 预收未清金额                                                                    "
			+"  from summary ta                                                                         "
			+"  left join cust tb                                                                       "
			+"    on ta.kunnr = tb.kunnr                                                                "
			+"  left join presum tc                                                                     "
			+"    on ta.kunnr = tc.kunnr                                                                "
			+"union                                                                                     "
			+"select ta.vkbur || ' 合计' as vkbur,                                                        "
			+"       '' as sortl,                                                                       "
			+"       case                                                                               "
			+"         when sum(tc.rec) > 0 then                                                        "
			+"          trim(to_char(sum(tc.rec), '999,999,999,990.99'))                                "
			+"         else                                                                             "
			+"          ''                                                                              "
			+"       end 未登记,                                                                           "
			+"       case                                                                               "
			+"         when sum(tc.recDo) > 0 then                                                      "
			+"          trim(to_char(sum(tc.recDo), '999,999,999,990.99'))                              "
			+"         else                                                                             "
			+"          ''                                                                              "
			+"       end 登记未记账,                                                                         "
			+"       case                                                                               "
			+"         when sum(tc.pre) > 0 then                                                        "
			+"          trim(to_char(sum(tc.pre), '999,999,999,990.99'))                                "
			+"         else                                                                             "
			+"          ''                                                                              "
			+"       end 预收未分解,                                                                         "
			+"       case                                                                               "
			+"         when sum(tc.preDo) > 0 then                                                      "
			+"          trim(to_char(sum(tc.preDo), '999,999,999,990.99'))                              "
			+"         else                                                                             "
			+"          ''                                                                              "
			+"       end 分解后未记账,                                                                        "
			+"       trim(to_char(sum(ta.dmonsum), '999,999,999,990.99')) 未来30天收款,                      "
			+"       trim(to_char(sum(ta.d14sum), '999,999,999,990.99')) 未来两周收款,                        "
			+"       trim(to_char(sum(tb.zwqmt), '999,999,999,990.99')) 发货未清金额,                         "
			+"       trim(to_char(sum(ta.zbamtsum), '999,999,999,990.99')) 应收合计,                        "
			+"       trim(to_char(sum(ta.d30sum), '999,999,999,990.99')) 逾期30天,                         "
			+"       trim(to_char(sum(ta.d60sum), '999,999,999,990.99')) 逾期60天,                         "
			+"       trim(to_char(sum(ta.d90sum), '999,999,999,990.99')) 逾期90天,                         "
			+"       trim(to_char(sum(ta.d180sum), '999,999,999,990.99')) 逾期180天,                       "
			+"       trim(to_char(sum(ta.d360sum), '999,999,999,990.99')) 逾期超半年,                        "
			+"       trim(to_char(sum(ta.delaysum), '999,999,999,990.99')) 逾期合计,                        "
			+"       trim(to_char(sum(ta.delaysum) * 100 / sum(ta.zbamtsum), '99990.99')) || '%' 逾期比,   "
			+"       trim(to_char(sum(tb.klimk), '999,999,999,990.99')) SAP授信额度,                            "
			+"       decode(sum(tb.klimk),                                                              "
			+"              0,                                                                          "
			+"              '0.00%',                                                                    "
			+"              trim(to_char(sum(ta.balcny + tb.zwqmt + tb.zysmt) * 100 /                   "
			+"                           sum(tb.klimk),                                                 "
			+"                           '99990.99')) || '%') 信用占比,                                     "
			+"       trim(to_char(sum(tb.zbemt), '999,999,999,990.99')) 信保额度,                             "
			+"       decode(sum(tb.zbemt),                                                              "
			+"              0,                                                                          "
			+"              '未投保',                                                                      "
			+"              trim(to_char(sum(ta.balcny + tb.zysmt) * 100 / sum(tb.zbemt),               "
			+"                           '99990.99')) || '%') 信保额度占比,                                     "
			+"       ' ' 趋势,                                                                            "
			+"       sum(ta.balcny) 应收款,                                                                "
			+"       sum(tb.zysmt) 预收未清金额                                                               "
			+"  from summary ta                                                                         "
			+"  left join cust tb                                                                       "
			+"    on ta.kunnr = tb.kunnr                                                                "
			+"  left join presum tc                                                                     "
			+"    on ta.kunnr = tc.kunnr                                                                "
			+" group by ta.vkbur                                                                        "
			+"union                                                                                     "
			+"select '公司合计' as vkbur,                                                                   "
			+"       '' as sortl,                                                                       "
			+"       case                                                                               "
			+"         when sum(tc.rec) > 0 then                                                        "
			+"          trim(to_char(sum(tc.rec), '999,999,999,990.99'))                                "
			+"         else                                                                             "
			+"          ''                                                                              "
			+"       end 未登记,                                                                           "
			+"       case                                                                               "
			+"         when sum(tc.recDo) > 0 then                                                      "
			+"          trim(to_char(sum(tc.recDo), '999,999,999,990.99'))                              "
			+"         else                                                                             "
			+"          ''                                                                              "
			+"       end 登记未记账,                                                                         "
			+"       case                                                                               "
			+"         when sum(tc.pre) > 0 then                                                        "
			+"          trim(to_char(sum(tc.pre), '999,999,999,990.99'))                                "
			+"         else                                                                             "
			+"          ''                                                                              "
			+"       end 预收未分解,                                                                         "
			+"       case                                                                               "
			+"         when sum(tc.preDo) > 0 then                                                      "
			+"          trim(to_char(sum(tc.preDo), '999,999,999,990.99'))                              "
			+"         else                                                                             "
			+"          ''                                                                              "
			+"       end 分解后未记账,                                                                        "
			+"       trim(to_char(sum(ta.dmonsum), '999,999,999,990.99')) 未来30天收款,                      "
			+"       trim(to_char(sum(ta.d14sum), '999,999,999,990.99')) 未来两周收款,                        "
			+"       trim(to_char(sum(tb.zwqmt), '999,999,999,990.99')) 发货未清金额,                         "
			+"       trim(to_char(sum(ta.zbamtsum), '999,999,999,990.99')) 应收合计,                        "
			+"       trim(to_char(sum(ta.d30sum), '999,999,999,990.99')) 逾期30天,                         "
			+"       trim(to_char(sum(ta.d60sum), '999,999,999,990.99')) 逾期60天,                         "
			+"       trim(to_char(sum(ta.d90sum), '999,999,999,990.99')) 逾期90天,                         "
			+"       trim(to_char(sum(ta.d180sum), '999,999,999,990.99')) 逾期180天,                       "
			+"       trim(to_char(sum(ta.d360sum), '999,999,999,990.99')) 逾期超半年,                        "
			+"       trim(to_char(sum(ta.delaysum), '999,999,999,990.99')) 逾期合计,                        "
			+"       trim(to_char(sum(ta.delaysum) * 100 / sum(ta.zbamtsum), '99990.99')) || '%' 逾期比,   "
			+"       trim(to_char(sum(tb.klimk), '999,999,999,990.99')) SAP授信额度,                            "
			+"       decode(sum(tb.klimk),                                                              "
			+"              0,                                                                          "
			+"              '0.00%',                                                                    "
			+"              trim(to_char(sum(ta.balcny + tb.zwqmt + tb.zysmt) * 100 /                   "
			+"                           sum(tb.klimk),                                                 "
			+"                           '99990.99')) || '%') 信用占比,                                     "
			+"       trim(to_char(sum(tb.zbemt), '999,999,999,990.99')) 信保额度,                             "
			+"       decode(sum(tb.zbemt),                                                              "
			+"              0,                                                                          "
			+"              '未投保',                                                                      "
			+"              trim(to_char(sum(ta.balcny + tb.zysmt) * 100 / sum(tb.zbemt),               "
			+"                           '99990.99')) || '%') 信保额度占比,                                     "
			+"       ' ' 趋势,                                                                            "
			+"       sum(ta.balcny) 应收款,                                                                "
			+"       sum(tb.zysmt) 预收未清金额                                                               "
			+"  from summary ta                                                                         "
			+"  left join cust tb                                                                       "
			+"    on ta.kunnr = tb.kunnr                                                                "
			+"  left join presum tc                                                                     "
			+"    on ta.kunnr = tc.kunnr                                                                "
            +"                                                                                          "
			+" order by vkbur, sortl                                                                    "
			;
		List<Object> list = findListBySql(sql);
		if(list==null||list.size()==0){
			return "<h3>客户应收统计(币种:CNY):无</h3>";
		}
		Map<Integer,String> lineColor = new HashMap<Integer,String>(); 
		for(int i=0;i<list.size();i++){
			Object[] obj = (Object[])list.get(i);
			if(StringUtils.nullToString(obj[0]).indexOf("合计")>-1){
				lineColor.put(i, "#B8CDEB");
				if(StringUtils.nullToString(obj[0]).indexOf("公司合计")>-1){
					lineColor.put(i, "#8BB3E5");
				}
				obj[16]="<div style='width:100%;'><div style='width:100px;height:100%;color:red;float:right;text-align: right;'>"+StringUtils.nullToString(obj[16])+"</div></div>";
			}
		}
		String[] title = {"部门","客户简称","1.未登记","财务未记账","2.预收未分解","财务未记账","未来30天收款","未来两周收款","发货未开票","应收合计","逾期30天","逾期60天","逾期90天","逾期180天","逾期180天以上","逾期合计","逾期比","SAP授信额度","额度使用率","信保额度","额度使用率","趋势"};
		String[] supTitle = {"","","收款情况","收款情况","收款情况","收款情况","收款情况","收款情况","收款情况","收款情况","4.逾期情况","4.逾期情况","4.逾期情况","4.逾期情况","4.逾期情况","4.逾期情况","统计","统计","统计","统计","统计","统计"};
		Map<Integer,String> map = new HashMap<Integer,String>();
		map.put(9,"#BFDCF9");
		map.put(15,"#BFDCF9");
		return generateTableHtml("客户应收统计(币种:CNY):",title,list,supTitle,null,map,lineColor);
	}
	
}
