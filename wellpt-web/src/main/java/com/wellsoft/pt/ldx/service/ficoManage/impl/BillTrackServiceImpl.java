package com.wellsoft.pt.ldx.service.ficoManage.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.wellsoft.pt.ldx.service.ficoManage.IBillTrackService;
import com.wellsoft.pt.ldx.service.impl.SapServiceImpl;
import com.wellsoft.pt.ldx.util.DateUtils;
import com.wellsoft.pt.ldx.util.MailUtil;
import com.wellsoft.pt.ldx.util.StringUtils;

/**
 * 
 * Description: 发票跟踪service
 *  
 * @author HeShi
 * @date 2014-8-27
 * @version 1.0
 * 
 * <pre>
 * 修改记录:
 * 修改后版本	修改人		修改日期			修改内容
 * 2014-8-27 	HeShi		2014-8-27		Create
 * </pre>
 *
 */
@Service
@Transactional
public class BillTrackServiceImpl extends SapServiceImpl implements IBillTrackService {

	@Override
	public Map<?, ?> billConfirm(String params,String type) {
		Map<String,String> map = new HashMap<String,String>();
		if(StringUtils.isBlank(params)){
			map.put("res","fail");
			map.put("msg","请选择确认开票的数据!");			
			return map;
		}
		String[] array = params.split(";");
		if(null==array||array.length==0){
			map.put("res","fail");
			map.put("msg","请选择确认开票的数据!");			
			return map;
		}
		for (String congigs: array) {
			String[] conifg = congigs.split(",");
			confirmSingleBill(StringUtils.nullToString(conifg[0]),StringUtils.nullToString(conifg[1]),type);
		}
		map.put("res","success");
		map.put("msg","操作成功!");			
		return map;
	}

	@Override
	public void confirmSingleBill(String zbelnr, String vbeln,String type) {
		updateBillStatus(zbelnr,vbeln,type);
		boolean done = checkAllIsDone(zbelnr);
		if(done){
			sendEmailAfterBill(zbelnr,vbeln,"Y");
		}else if("N".equals(type)&&!done){
			sendEmailAfterBill(zbelnr,vbeln,"N");
		}
	}
	
	/**
	 * 
	 * 确定当前流水号是否全部开票
	 * 如果全部开票,则更新zfmt0003表开票状态,并发邮件通知
	 * 
	 * @param zbelnr
	 */
	private boolean checkAllIsDone(String zbelnr) {
		String sql = "select 1 from zfmt0015 where zbelnr='"+zbelnr+"' and fksta='N' and mandt="+getClient();
		List<Object> list = findListBySql(sql);
		if(null==list||list.size()==0){
			String update =  "update zfmt0003 set fksta = ' ' where zbelnr='"+zbelnr+"' and mandt="+getClient();
			execSql(update);
			return true;
		}else{
			String update =  "update zfmt0003 set fksta = 'N' where zbelnr='"+zbelnr+"' and mandt="+getClient();
			execSql(update);
			return false;
		}
	}

	/**
	 * 
	 * 根据流水号及外向交货单修改开票状态
	 * 
	 * @param zbelnr
	 * @param vbeln
	 */
	private void updateBillStatus(String zbelnr, String vbeln,String type){
		String update = "update zfmt0015 set fksta = '"+("N".equals(type)?"N":" ")+"' where zbelnr = '"+zbelnr+"' and vbeln='"+StringUtils.addLeftZero(vbeln,10)+"' and mandt="+getClient();
		execSql(update);
	}

	@Override
	public void sendEmailAfterBill(String zbelnr, String vbeln, String noticeType) {
		//@formatter:off
		String query = "select a.zsname,a.zcirs from zfmt0003 a"
			+ " where a.mandt="+getClient()
			+ " and (a.fksta!='N' or a.fksta is null)"
			+ " and (a.zcirs='P' or (a.zdrs='P' and a.zcirs='C'))"
			+ " and a.zbelnr='"+zbelnr+"'";
		List<Object> checkList = findListBySql(query);
		if(null==checkList || checkList.size()==0)
			return;
		//用户ID
		String userId = StringUtils.nullToString(((Object[])checkList.get(0))[0]);
		//流转状态
		String zcirs = StringUtils.nullToString(((Object[])checkList.get(0))[1]);
		//邮件标题
		String subjectString = "财务开票提醒";
		//邮件内容
		StringBuffer contString = new StringBuffer();
		//邮件是否有内容
		boolean hasCont = false;
		if("P".equals(zcirs)){
			//到款登记
			String ppSearch = " select a.zsname,a.kunnr,a.budat,a.sortl,a.zbelnr,' ' as bstkd,a.bukrs,a.waers,a.zdoip,a.zcamount,a.zdrs,a.zcirs,a.sgtxt,nvl(a.zetime,to_char(sysdate,'yyyy-MM-dd hh24:mi:ss')) as zetime"
				+ " from zfmt0003 a" 
				+ " where a.mandt="+getClient()
				+ " and a.zbelnr='"+zbelnr+"'";
			List<Object> listPP = findListBySql(ppSearch);
			if(listPP!=null && listPP.size()>0){
				hasCont = true;
				if("N".equals(noticeType)){
					contString.append("<h4>财务不能开具外向交货单("+vbeln+")相应发票,请先以预收款类型进行到账登记.</h4>");
				}else{
					contString.append("<h4>财务已开具外向交货单("+vbeln+")相应发票,请及时进行到账登记.</h4>");
				}
				subjectString = "财务开票提醒:请及时登记,";
				contString.append("<h4>待登记到账信息:</h4>")
					.append(generateTable(listPP,userId,"1","yw"))
					.append("<br/>");
			}
		}else if("C".equals(zcirs)&&"Y".equals(noticeType)){
			//到账分解
			String pcSearch = " select b.zsname,b.kunnr,b.budat,b.sortl,b.zbelnr,a.bstkd,b.bukrs,a.waers,b.zdoip,a.zcamount,a.zdrs,a.zcirs,b.sgtxt"
				+ " from zfmt0004 a,zfmt0003 b"
			    + " where a.zbelnr = b.zbelnr"
				+ " and a.zrbl='A'"
				+ " and a.zdrs='P'"
				+ " and a.zcirs='C'"
				+ " and b.zbelnr='"+zbelnr+"'";
			List<Object> listPC = findListBySql(pcSearch);
			if(listPC!=null && listPC.size()>0){
				hasCont = true;
				contString.append("<h4>财务已开具外向交货单("+vbeln+")相应发票,请及时进行到账登记.</h4>");
				subjectString = "财务开票提醒:请及时分解,";
				contString.append("<h4>待分解到账信息:</h4>")
					.append(generateTable(listPC,userId,"2","yw"))
					.append("<br/>");
			}
		}
		if(hasCont){
			String date = DateUtils.formatDate(new Date());
			String to = "";
			String mailQuery = "select distinct usrid from PA0105 a where a.USRTY = 'MAIL' and a.pernr ='"+userId+"'"
				+ " and not exists (select 1 from PA0105 b where b.pernr = a.pernr and b.USRTY=a.USRTY and b.begda>a.begda)";
			List<Object> list = findListBySql(mailQuery);
			if(null!=list && list.size()>0){
				to = StringUtils.nullToString(list.get(0));
				try {
					MailUtil mailUtil = new MailUtil();
					mailUtil.setHost("mail.leedarson.com");
					mailUtil.setUsername("IT@leedarson.com");
					mailUtil.setPassword("it612345");
					mailUtil.setFrom("IT@leedarson.com");
					mailUtil.setTo(to);
					mailUtil.setSubject(subjectString + date);
					mailUtil.setContent(contString.toString());
					mailUtil.send();
				} catch (Exception e) {
					System.out.println("发送财务开票提醒邮件失败:"+e.getMessage());
				}
			}
		}
		//@formatter:on
	}
	
	/**
	 * 生成邮件表格
	 * @param list 数据列表
	 * @param userId 用户id
	 * @param type 提醒类型
	 * @param from 访问者,yw,ae
	 * @return
	 */
	private String generateTable(List<Object> list,String userId,String type,String from){
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
		table.append("<th style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;background-color:#f2f3f3;border:1px solid #cccccc'>").append("预收状态").append("</th>");
		table.append("<th style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;background-color:#f2f3f3;border:1px solid #cccccc'>").append("流转状态").append("</th>");
		table.append("</tr>");
		table.append("</thead>");
		table.append("<tbody>");
		for(Object object:list){
			Object[] obj = (Object[])object;
			table.append("<tr");
			if("Z".equals(obj[0])){
				table.append(" style='background-color:#FFFFBF;'");
			}
			table.append(">");
			table.append("<td style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;border:1px solid #cccccc;padding-left:4px;'>").append("Z".equals(obj[0])?"合计":obj[0]).append("</td>");
			table.append("<td style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;border:1px solid #cccccc;padding-left:4px;'>");
			if(!"Z".equals(obj[0])){
				table.append("<a href='").append("1111111111111/").append("other/ficomanage/ficoRoute!doRoute.do?flowNum=").append(obj[4]).append("&from=").append(from).append("&mail=y&user=").append(StringUtils.isBlank(userId)?StringUtils.nullToString(obj[0]):userId).append("'>");
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
			table.append("<td style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;border:1px solid #cccccc;padding-left:4px;'>").append("Z".equals(obj[0])?"":"未分解").append("</td>");
			table.append("<td style='font-size:12px; white-space:nowrap; display: table-cell;vertical-align: inherit;border:1px solid #cccccc;padding-left:4px;'>");
			if(!"Z".equals(obj[0])){
				table.append("P".equals(obj[11])?"维护":"已生成凭证");
			}
			table.append("</td>");
			table.append("</tr>");
		}
		table.append("</tbody>");
		table.append("</table>");
		
		return table.toString();
	}
	
}
