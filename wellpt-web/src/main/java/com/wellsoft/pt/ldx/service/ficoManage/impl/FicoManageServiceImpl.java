package com.wellsoft.pt.ldx.service.ficoManage.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wellsoft.pt.basicdata.sap.service.SAPRfcService;
import com.wellsoft.pt.basicdata.sap.util.SAPRfcJson;
import com.wellsoft.pt.core.support.ExtendedPropertyPlaceholderConfigurer;
import com.wellsoft.pt.ldx.dao.common.MailAddrDao;
import com.wellsoft.pt.ldx.model.ficoManage.MailAddr;
import com.wellsoft.pt.ldx.model.ficoManage.Zfmt0003;
import com.wellsoft.pt.ldx.model.ficoManage.Zfmt0004;
import com.wellsoft.pt.ldx.service.ficoManage.IFicoManageService;
import com.wellsoft.pt.ldx.service.impl.SapServiceImpl;
import com.wellsoft.pt.ldx.util.DateUtils;
import com.wellsoft.pt.ldx.util.MailUtil;
import com.wellsoft.pt.ldx.util.StringUtils;
import com.wellsoft.pt.security.core.userdetails.UserDetails;
import com.wellsoft.pt.utils.security.SpringSecurityUtils;

/**
 * 
 * Description: 未收汇手工资料service
 *  
 * @author HeShi
 * @date 2014-8-25
 * @version 1.0
 * 
 * <pre>
 * 修改记录:
 * 修改后版本	修改人		修改日期			修改内容
 * 2014-8-25 	HeShi		2014-8-25		Create
 * </pre>
 *
 */
@Service
@Transactional
public class FicoManageServiceImpl extends SapServiceImpl implements IFicoManageService{
	@Autowired
	private SAPRfcService saprfcservice;
	@Autowired
	private ExtendedPropertyPlaceholderConfigurer propertyConfigurer;
	@Autowired
	private MailAddrDao mailAddrDao;
	
	private static Logger logger = LoggerFactory.getLogger(FicoManageServiceImpl.class);
	
	//功能代码:资金管理异常提醒邮件
	private static final String FC_DAILY_ERROR ="dailyError";
	//邮件发送类型:主送
	private static final String SENDTYPE_TO ="to";
	
	public void execSqlList(List<String> sqls) throws Exception {
		if(null==sqls || sqls.size()==0){
			return;
		}
		String curSql = "";
		try {
			for(String sql:sqls){
				curSql = sql;
				execSql(sql);
			}
		} catch (Exception e) {
			logger.error("sql error:"+e.getMessage());
			logger.error("====ERROR SQL====");
			logger.error(curSql);
			logger.error("====ERROR SQL====");
			throw e;
		}
	}

	@Override
	public Zfmt0003 findAcctByFlowNum(String flowNum) {
		Zfmt0003 acct = null;
		String sql = "select bukrs,zbelnr,zsname,kunnr,sortl,waers,sgtxt,hkont,zcamount,zdrs,zcirs,zdoip,zremind,zetime " 
			+ " ,zcheck,zds,bldat,budat,zcrem,zpost,zrdate,ztext "
			+ " from zfmt0003 a "
			+ " where mandt ="+getClient()+" and  a.zbelnr ='"+flowNum.trim()+"'";
		List<Object> list = findListBySql(sql);
		if(null!=list && list.size()>0){
			Object[] ob =(Object[]) list.get(0);
			acct = new Zfmt0003();
			acct.setBukrs(StringUtils.nullToString(ob[0]));
			acct.setZbelnr(StringUtils.nullToString(ob[1]));
			acct.setZsname(StringUtils.nullToString(ob[2]));
			acct.setKunnr(StringUtils.nullToString(ob[3]));
			acct.setSortl(StringUtils.nullToString(ob[4]));
			acct.setWaers(StringUtils.nullToString(ob[5]));
			acct.setSgtxt(StringUtils.nullToString(ob[6]));
			acct.setHkont(StringUtils.nullToString(ob[7]));
			acct.setZcamount(StringUtils.getCurrencyStr(ob[8].toString()));
			acct.setZdrs(StringUtils.nullToString(ob[9]));
			acct.setZcirs(StringUtils.nullToString(ob[10]));
			acct.setZdoip(StringUtils.nullToString(ob[11]));
			acct.setZremind(StringUtils.nullToString(ob[12]));
			acct.setZetime(StringUtils.nullToString(ob[13]));
			acct.setZcheck(StringUtils.nullToString(ob[14]));
			acct.setZds(StringUtils.nullToString(ob[15]));
			acct.setBldat(StringUtils.nullToString(ob[16]));
			acct.setBudat(StringUtils.nullToString(ob[17]));
			acct.setZcrem(StringUtils.nullToString(ob[18]));
			acct.setZpost(StringUtils.nullToString(ob[19]));
			acct.setZrdate(StringUtils.nullToString(ob[20]));
			acct.setZtext(StringUtils.nullToString(ob[21]));
		}
		return acct;
	}

	@Override
	public String findUserNameByCode(String zsname) {
		String sql = "select distinct ename from pa0001 where pernr='"+zsname+"' and mandt="+getClient();
		List<Object> list = findListBySql(sql);
		if(null!=list&&!list.isEmpty()){
			return  StringUtils.nullToString(list.get(0));
		}
		return zsname;
	}

	@Override
	public List<Zfmt0004> findSepAcctsByFlowNum(String flowNum) {
		List<Zfmt0004> result = new ArrayList<Zfmt0004>();
		//@formatter:off
		String sql = 
			  "select a.mandt,a.zbelnr,to_number(a.zposnr),a.bstkd,a.vbeln,a.zcamount,a.zwoamt,a.zrbl,a.zpodat,a.zbamt,a.waers,a.kursf,a.zanote,a.zsmc,a.zhc,a.belnr,a.umskz,a.rstgr,a.ztnum,a.zdrs,a.zcirs,a.zuonr,a.stblg,a.mwskz,a.zposnr_s "
			+ ",b.zpamount-b.zdwoamt,a.zstblg,a.zp_zstblg,a.spart,a.zwodat,a.kunnr,c.sortl,a.bukrs,a.zpeinh,a.belnr2,a.belnr3 "
			+ " from zfmt0004 a "
			+ " inner join zfmt0003 m on a.zbelnr = m.zbelnr"
			+ " left join zfmt0006 b on a.bstkd=b.bstkd and a.zrbl='A' and b.zover = '否' and b.waers=a.waers "
			+ " left join kna1 c on a.mandt=c.mandt and a.kunnr=c.kunnr"
			+ " where a.mandt=" + getClient()
			+ " and a.zbelnr = '"+flowNum+"'"
			+ " order by a.zposnr";
		//@formatter:on
		List<Object> list = findListBySql(sql);
		if(null!=list && list.size()>0){
			Zfmt0004 sepAcct;
			for(Object object : list){
				Object[] ob = (Object[]) object;
				sepAcct = new Zfmt0004();
				sepAcct.setMandt(StringUtils.nullToString(ob[0]));
				sepAcct.setZbelnr(StringUtils.nullToString(ob[1]));
				sepAcct.setZposnr(StringUtils.nullToString(ob[2]));
				sepAcct.setBstkd(StringUtils.nullToString(ob[3]));
				sepAcct.setVbeln(StringUtils.nullToString(ob[4]));
				sepAcct.setZcamount(StringUtils.nullToString(ob[5]));
				sepAcct.setZwoamt(StringUtils.nullToString(ob[6]));
				sepAcct.setZrbl(StringUtils.nullToString(ob[7]));
				sepAcct.setZpodat(StringUtils.nullToString(ob[8]));
				sepAcct.setZbamt(StringUtils.nullToString(ob[9]));
				sepAcct.setWaers(StringUtils.nullToString(ob[10]));
				sepAcct.setKursf(StringUtils.nullToString(ob[11]));
				sepAcct.setZanote(StringUtils.nullToString(ob[12]));
				sepAcct.setZsmc(StringUtils.nullToString(ob[13]));
				sepAcct.setZhc(StringUtils.nullToString(ob[14]));
				sepAcct.setBelnr(StringUtils.nullToString(ob[15]));
				sepAcct.setUmskz(StringUtils.nullToString(ob[16]));
				sepAcct.setRstgr(StringUtils.nullToString(ob[17]));
				sepAcct.setZtnum(StringUtils.nullToString(ob[18]));
				sepAcct.setZdrs(StringUtils.nullToString(ob[19]));
				sepAcct.setZcirs(StringUtils.nullToString(ob[20]));
				sepAcct.setZuonr(StringUtils.nullToString(ob[21]));
				sepAcct.setStblg(StringUtils.nullToString(ob[22]));
				sepAcct.setMwskz(StringUtils.nullToString(ob[23]));
				sepAcct.setZposnrSup(StringUtils.nullToString(ob[24]));
				sepAcct.setAeamt(StringUtils.nullToString(ob[25]));
				sepAcct.setZstblg(StringUtils.nullToString(ob[26]));//红冲凭证号
				sepAcct.setZpStblg(StringUtils.nullToString(ob[27]));//红冲预收凭证
				sepAcct.setSpart(StringUtils.nullToString(ob[28]));//产品组
				sepAcct.setZwodat(StringUtils.nullToString(ob[29]));//冲销预收过账日期
				sepAcct.setKunnr(StringUtils.nullToString(ob[30]));//客户编号
				sepAcct.setSortl(StringUtils.nullToString(ob[31]));//客户简称
				sepAcct.setBukrs(StringUtils.nullToString(ob[32]));//公司代码
				sepAcct.setZpeinh(StringUtils.nullToString(ob[33]));//单位换算
				sepAcct.setBelnr2(StringUtils.nullToString(ob[34]));//凭证2
				sepAcct.setBelnr3(StringUtils.nullToString(ob[35]));//凭证3
				result.add(sepAcct);
			}
		}
		return result;
	}

	@Override
	public void saveLog(String user, String type, String record) {
		if(StringUtils.isBlank(user)){
			//用户信息为空时获取当前登录用户工号
			user = getCurrentUserCode();
		}
		user = user.length()>20?user.substring(0,19):user;
		type = type.length()>20?type.substring(0,19):type;
		record = record.length()>500?record.substring(0,499):record;
		record = record.replaceAll("'","|");
		
		String save = "insert into zfmt0005(mandt,zuser,zoptime,zoptype,zoprecord) "
				+ "select "
				+ getClient()
				+ ",'"
				+ user
				+ "',to_char(sysdate,'yyyyMMdd hh24:mi:ss'),'"
				+ type
				+ "','"
				+ record + "'  from dual";
		try {
			this.execSql(save);
		} catch (Exception e) {
//			LOGGER.error("error when save log :" + e.getMessage());
		}
		
	}
	
	private String getCurrentUserCode() {
		UserDetails userDetail = SpringSecurityUtils.getCurrentUser();
		return userDetail.getEmployeeNumber(); 
	}

	@Override
	public void checkAcctBalance(String flowNum) throws Exception {
		//@formatter:off
		//更新到账信息预收状态、流转状态、对账状态
		String query = "select nvl(sum(amt),0) from("
			+ " select nvl(zcamount,0) as amt from zfmt0003 where zbelnr='"+flowNum+"'"
			+ " union all"
			+ " select -sum(to_number(to_char(nvl(nvl(zcamount,0)*nvl(kursf,1)/nvl(zpeinh,1)-zhc,0),'FM9999999999.99'))) from zfmt0004 where zbelnr='"+flowNum+"' and (zposnr_s is null or zposnr_s=' ' or zposnr_s='00000')"
			+ " )";
		//@formatter:on
		Object object = findListBySql(query).get(0);
		Double result = Double.valueOf(StringUtils.nullToString(object));
		if(result.compareTo(new Double(0))!=0){
			throw new Exception("应收金额合计与到款金额不符!");
		}
	}

	@Override
	public void submitAcctYw(String flowNum) throws Exception {
		checkUnknowKunnrSubmit(flowNum);
		//@formatter:off
		// 更新到账信息预收状态、流转状态、对账状态
		String updateSeprate = "update zfmt0004 set "
			+ " zdrs=case when zdrs='P' and vbeln!=' ' and bstkd!=' ' and zwoamt=0 then 'F' when zrbl='A' and zcamount=zwoamt and zwoamt<>0 then 'C' when zrbl='A' and vbeln=' ' and zcamount<>zwoamt and zcamount<>0 then 'P' when zdrs='P' and (zrbl='B' or zrbl='C' or zrbl='D' or zrbl='E') then 'F' else zdrs end,"
			+ " zcirs=case when zcirs='P' then 'F' when zrbl='A' and zcamount=zwoamt and zcamount<>0 then 'C' else zcirs end "
			+ " where zbelnr = '"+flowNum+"'";
		// 更新回复时间
		String updateZrdate = "update zfmt0003 set zrdate=to_char(sysdate,'yyyyMMdd')"
			+ " where zbelnr = '"+flowNum+"'";
		//@formatter:on
		this.execSql(updateSeprate);
		this.execSql(updateZrdate);
		// 更新到账记录状态
		updateAcctStatusByFlowNum(flowNum);
	}
	
	/**
	 * 未知客户登记提交时,校验当前业务员不可以是未知客户默认OM
	 * add by HeShi 2014/05/23 Do not ask me why !
	 * @param zbelnr
	 * @throws LdxPortalException
	 */
	@SuppressWarnings("rawtypes")
	private void checkUnknowKunnrSubmit(String zbelnr) throws Exception {
		//@formatter:off
		String checkSql = "select 1 from zfmt0003 a "
			+ " inner join zfmt0007 b on a.bukrs=b.bukrs and a.kunnr=b.kunnr and a.zsname=b.zom"
			+ " where a.kunnr ='...'"
			+ " and a.zbelnr='"+zbelnr+"'";
		//@formatter:on
		List checkList = findListBySql(checkSql);
		if(null!=checkList && checkList.size()>0){
			throw new Exception("未知客户到款登记不能由您来提交,请分配给其他业务员!");
		}
	}

	@Override
	public void updateAcctStatusByFlowNum(String flowNum) throws Exception {
		if(StringUtils.isBlank(flowNum)){
			return ;
		}
		//@formatter:off
		//更新到账信息预收状态、流转状态、对账状态
		String update = " update zfmt0003 set (zdrs,zcirs) = (select nvl(max(zdrs),'P'),nvl(max(zcirs),'P') from zfmt0004 where zfmt0003.zbelnr=zfmt0004.zbelnr) "
			+ " where zbelnr = '"+flowNum+"'";
		//@formatter:on
		this.execSql(update);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void checkStatusBeforEdit(String zbelnr, String zposnr) throws Exception {
		String query = "select 1 from zfmt0004 where zbelnr = '"+ zbelnr +"' and zposnr='"+StringUtils.addLeftZero(zposnr,5)+"' and zdrs!='P' ";
		List list = findListBySql(query);
		if(null!=list && list.size()>0){
			throw new Exception("预收信息已经提交,不能进行当前操作");
		}
	}

	@Override
	public void deleteZfmt0004(Zfmt0004 zfmt0004) throws Exception {
		if(StringUtils.isBlank(zfmt0004.getZbelnr())
				||StringUtils.isBlank(zfmt0004.getZposnr())){
			return;
		}
		//@formatter:off
		String delete = 
			  "delete from zfmt0004 " 
			+ " where zbelnr = '" + zfmt0004.getZbelnr() + "'"
			+ " and   zposnr = '" + StringUtils.addLeftZero(zfmt0004.getZposnr(),5) + "'";
		//@formatter:on
		this.execSql(delete);
		this.updateParentAcct(zfmt0004.getZbelnr(),zfmt0004.getZposnr());
	}

	@Override
	public void updateParentAcct(String zbelnr, String zpsonr) throws Exception {
		String update = "update zfmt0004 set zwoamt = ( select nvl(sum(to_number(to_char(zcamount*nvl(kursf,1)/nvl(zpeinh,1),'FM9999999999.99'))),0) from zfmt0004 b where b.zposnr_s = zfmt0004.zposnr and b.zbelnr = zfmt0004.zbelnr)"
			+ " where zbelnr = '"+zbelnr+"'"
			+ " and (zposnr_s is null or zposnr_s=' ' or zposnr_s='00000')";
		this.execSql(update);
	}

	@Override
	public String saveZfmt0004(Zfmt0004 zfmt0004) throws Exception {
		if(StringUtils.isBlank(zfmt0004.getZbelnr())){
			return null;
		}
		String posnr = getNextPosnr(zfmt0004.getZbelnr());
		String kunnr = zfmt0004.getKunnr();
		String bukrs = zfmt0004.getBukrs();
		if(StringUtils.isBlank(kunnr)||StringUtils.isBlank(bukrs)){
			Object[] object = (Object[]) findListBySql("select bukrs,kunnr from zfmt0003 where zbelnr='"+zfmt0004.getZbelnr()+"' and mandt="+getClient()).get(0);
			bukrs = (String) object[0];
			kunnr = (String) object[1];
		}
		//@formatter:off
		String insert = 
			      "insert into zfmt0004"
				+ "(mandt,zbelnr,zposnr,bstkd,vbeln,"
				+ "zcamount,zwoamt,zrbl,zpodat,zbamt,"
				+ "waers,kursf,zanote,zsmc,zhc,"
				+ "belnr,umskz,rstgr,ztnum,zdrs,"
				+ "zcirs,zuonr,stblg,mwskz,zposnr_s,"
				+ "zstblg,zp_zstblg,spart,zwodat,kunnr," 
				+ "bukrs,zpeinh,belnr2,belnr3)"
				+ "values ("
				+ (StringUtils.isBlank(zfmt0004.getMandt())?"000":zfmt0004.getMandt())
				+ ",'"
				+ (StringUtils.isBlank(zfmt0004.getZbelnr())?" ":zfmt0004.getZbelnr())
				+ "','"
				+ (StringUtils.isBlank(posnr)?" ":StringUtils.addLeftZero(posnr,5))
				+ "','"
				+ (StringUtils.isBlank(zfmt0004.getBstkd())?" ":zfmt0004.getBstkd())
				+ "','"
				+ (StringUtils.isBlank(zfmt0004.getVbeln())?" ":StringUtils.addLeftZero(zfmt0004.getVbeln(),10))
				+ "','"
				+ (StringUtils.isBlank(zfmt0004.getZcamount())?"0":zfmt0004.getZcamount())
				+ "','"
				+ (StringUtils.isBlank(zfmt0004.getZwoamt())?"0":zfmt0004.getZwoamt())
				+ "','"
				+ (StringUtils.isBlank(zfmt0004.getZrbl())?" ":zfmt0004.getZrbl())
				+ "','"
				+ (StringUtils.isBlank(zfmt0004.getZpodat())?"00000000":zfmt0004.getZpodat().replaceAll("-",""))
				+ "','"
				+ (StringUtils.isBlank(zfmt0004.getZbamt())?"0":zfmt0004.getZbamt())
				+ "','"
				+ (StringUtils.isBlank(zfmt0004.getWaers())?" ":zfmt0004.getWaers())
				+ "','"
				+ (StringUtils.isBlank(zfmt0004.getKursf())?"1":zfmt0004.getKursf())
				+ "','"
				+ (StringUtils.isBlank(zfmt0004.getZanote())?" ":zfmt0004.getZanote())
				+ "','"
				+ (StringUtils.isBlank(zfmt0004.getZsmc())?" ":zfmt0004.getZsmc())
				+ "','"
				+ (StringUtils.isBlank(zfmt0004.getZhc())?"0":zfmt0004.getZhc())
				+ "','"
				+ (StringUtils.isBlank(zfmt0004.getBelnr())?" ":StringUtils.addLeftZero(zfmt0004.getBelnr(),10))
				+ "','"
				+ (StringUtils.isBlank(zfmt0004.getUmskz())?" ":zfmt0004.getUmskz())
				+ "','"
				+ (StringUtils.isBlank(zfmt0004.getRstgr())?" ":zfmt0004.getRstgr())
				+ "','"
				+ (StringUtils.isBlank(zfmt0004.getZtnum())?" ":zfmt0004.getZtnum())
				+ "','"
				+ (StringUtils.isBlank(zfmt0004.getZdrs())?"P":zfmt0004.getZdrs())
				+ "','"
				+ (StringUtils.isBlank(zfmt0004.getZcirs())?"P":zfmt0004.getZcirs())
				+ "','"
				+ (StringUtils.isBlank(zfmt0004.getZuonr())?" ":zfmt0004.getZuonr())
				+ "','"
				+ (StringUtils.isBlank(zfmt0004.getStblg())?" ":StringUtils.addLeftZero(zfmt0004.getStblg(),10))
				+ "','"
				+ (StringUtils.isBlank(zfmt0004.getMwskz())?" ":zfmt0004.getMwskz())
				+ "','"
				+ (StringUtils.isBlank(zfmt0004.getZposnrSup())?" ":StringUtils.addLeftZero(zfmt0004.getZposnrSup(),5))
				+ "','"
				+ (StringUtils.isBlank(zfmt0004.getZstblg())?" ":zfmt0004.getZstblg())
				+ "','"
				+ (StringUtils.isBlank(zfmt0004.getZpStblg())?" ":zfmt0004.getZpStblg())
				+ "','"
				+ (StringUtils.isBlank(zfmt0004.getSpart())?" ":zfmt0004.getSpart())
				+ "','"
				+ (StringUtils.isBlank(zfmt0004.getZwodat())?"00000000":zfmt0004.getZwodat().replaceAll("-",""))
				+ "','"
				+ (StringUtils.isBlank(kunnr)?" ":kunnr.trim())
				+ "','"
				+ (StringUtils.isBlank(zfmt0004.getBukrs())?" ":zfmt0004.getBukrs().trim())
				+ "','"
				+ (StringUtils.isBlank(zfmt0004.getZpeinh())?"1":zfmt0004.getZpeinh().trim())
				+ "','"
				+ (StringUtils.isBlank(zfmt0004.getBelnr2())?" ":StringUtils.addLeftZero(zfmt0004.getBelnr2(),10))
				+ "','"
				+ (StringUtils.isBlank(zfmt0004.getBelnr3())?" ":StringUtils.addLeftZero(zfmt0004.getBelnr3(),10))
				+ "')";
		//@formatter:on
		this.execSql(insert);
		this.updateParentAcct(zfmt0004.getZbelnr(),zfmt0004.getZposnr());
		return posnr;
	}
	
	/**
	 * 根据凭证编号(流水号)获取下一个行项值
	 * @param flowNum
	 * @return
	 */
	public String getNextPosnr(String flowNum) {
		int current = 0;
		String query = "select nvl(max(to_number(zposnr)),'0') from zfmt0004 where zbelnr ='"+flowNum+"'";
		Object max = findListBySql(query).get(0);
		if (null != max && StringUtils.isNotBlank(max.toString())) {
			current = Integer.parseInt(max.toString());
		}
		return String.valueOf(current + 10);
	}

	@Override
	public void updateZfmt0004(Zfmt0004 zfmt0004) throws Exception {
		if(StringUtils.isBlank(zfmt0004.getZbelnr())
				||StringUtils.isBlank(zfmt0004.getZposnr())){
			return;
		}
		//@formatter:off
		StringBuffer update = new StringBuffer("update zfmt0004 set zposnr=zposnr");
		if(needReset(zfmt0004.getBstkd()))
			update.append(",bstkd    = '").append(StringUtils.isBlank(zfmt0004.getBstkd())?" ":zfmt0004.getBstkd()).append("'");
		if(needReset(zfmt0004.getVbeln()))
			update.append(",vbeln    = '").append(StringUtils.isBlank(zfmt0004.getVbeln())?" ":StringUtils.addLeftZero(zfmt0004.getVbeln(),10)).append("'");
		if(needReset(zfmt0004.getZcamount()))
			update.append(",zcamount = '").append(StringUtils.isBlank(zfmt0004.getZcamount())?"0":zfmt0004.getZcamount()).append("'");
		if(needReset(zfmt0004.getZwoamt()))
			update.append(",zwoamt   = '").append(StringUtils.isBlank(zfmt0004.getZwoamt())?"0":zfmt0004.getZwoamt()).append("'");
		if(needReset(zfmt0004.getZrbl()))
			update.append(",zrbl     = '").append(StringUtils.isBlank(zfmt0004.getZrbl())?" ":zfmt0004.getZrbl()).append("'");
		if(needReset(zfmt0004.getZpodat()))
			update.append(",zpodat   = '").append(StringUtils.isBlank(zfmt0004.getZpodat())?"00000000":zfmt0004.getZpodat().replaceAll("-","")).append("'");
		if(needReset(zfmt0004.getZbamt()))
			update.append(",zbamt    = '").append(StringUtils.isBlank(zfmt0004.getZbamt())?"0":zfmt0004.getZbamt()).append("'");
		if(needReset(zfmt0004.getWaers()))
			update.append(",waers    = '").append(StringUtils.isBlank(zfmt0004.getWaers())?" ":zfmt0004.getWaers()).append("'");
		if(needReset(zfmt0004.getKursf()))
			update.append(",kursf    = '").append(StringUtils.isBlank(zfmt0004.getKursf())?"1":zfmt0004.getKursf()).append("'");
		if(needReset(zfmt0004.getZanote()))
			update.append(",zanote   = '").append(StringUtils.isBlank(zfmt0004.getZanote())?" ":zfmt0004.getZanote()).append("'");
		if(needReset(zfmt0004.getZsmc()))
			update.append(",zsmc     = '").append(StringUtils.isBlank(zfmt0004.getZsmc())?" ":zfmt0004.getZsmc()).append("'");
		if(needReset(zfmt0004.getZhc()))
			update.append(",zhc      = '").append(StringUtils.isBlank(zfmt0004.getZhc())?"0":zfmt0004.getZhc()).append("'");
		if(needReset(zfmt0004.getBelnr()))
			update.append(",belnr    = '").append(StringUtils.isBlank(zfmt0004.getBelnr())?" ":StringUtils.addLeftZero(zfmt0004.getBelnr(),10)).append("'");
		if(needReset(zfmt0004.getUmskz()))
			update.append(",umskz    = '").append(StringUtils.isBlank(zfmt0004.getUmskz())?" ":zfmt0004.getUmskz()).append("'");
		if(needReset(zfmt0004.getRstgr()))
			update.append(",rstgr    = '").append(StringUtils.isBlank(zfmt0004.getRstgr())?" ":zfmt0004.getRstgr()).append("'");
		if(needReset(zfmt0004.getZtnum()))
			update.append(",ztnum    = '").append(StringUtils.isBlank(zfmt0004.getZtnum())?" ":zfmt0004.getZtnum()).append("'");
		if(needReset(zfmt0004.getZdrs()))
			update.append(",zdrs     = '").append(StringUtils.isBlank(zfmt0004.getZdrs())?"P":zfmt0004.getZdrs()).append("'");
		if(needReset(zfmt0004.getZcirs()))
			update.append(",zcirs    = '").append(StringUtils.isBlank(zfmt0004.getZcirs())?"P":zfmt0004.getZcirs()).append("'");
		if(needReset(zfmt0004.getZuonr()))
			update.append(",zuonr    = '").append(StringUtils.isBlank(zfmt0004.getZuonr())?" ":zfmt0004.getZuonr()).append("'");
		if(needReset(zfmt0004.getStblg()))
			update.append(",stblg    = '").append(StringUtils.isBlank(zfmt0004.getStblg())?" ":StringUtils.addLeftZero(zfmt0004.getStblg(),10)).append("'");
		if(needReset(zfmt0004.getMwskz()))
			update.append(",mwskz    = '").append(StringUtils.isBlank(zfmt0004.getMwskz())?" ":zfmt0004.getMwskz()).append("'");
		if(needReset(zfmt0004.getZposnrSup()))
			update.append(",zposnr_s = '").append(StringUtils.isBlank(zfmt0004.getZposnrSup())?" ":zfmt0004.getZposnrSup()).append("'");
		if(needReset(zfmt0004.getZstblg()))
			update.append(",zstblg   = '").append(StringUtils.isBlank(zfmt0004.getZstblg())?" ":zfmt0004.getZstblg()).append("'");
		if(needReset(zfmt0004.getZpStblg()))
			update.append(",zp_zstblg= '").append(StringUtils.isBlank(zfmt0004.getZpStblg())?" ":zfmt0004.getZpStblg()).append("'");
		if(needReset(zfmt0004.getSpart()))
			update.append(",spart    = '").append(StringUtils.isBlank(zfmt0004.getSpart())?" ":zfmt0004.getSpart()).append("'");
		if(needReset(zfmt0004.getZwodat()))
			update.append(",zwodat    = '").append(StringUtils.isBlank(zfmt0004.getZwodat())?"00000000":zfmt0004.getZwodat().replaceAll("-","")).append("'");
		if(needReset(zfmt0004.getZpeinh()))
			update.append(",zpeinh    = '").append(StringUtils.isBlank(zfmt0004.getZpeinh())?"1":zfmt0004.getZpeinh().trim()).append("'");
		if(needReset(zfmt0004.getBelnr2()))
			update.append(",belnr2    = '").append(StringUtils.isBlank(zfmt0004.getBelnr2())?" ":StringUtils.addLeftZero(zfmt0004.getBelnr2(),10)).append("'");
		if(needReset(zfmt0004.getBelnr3()))
			update.append(",belnr3    = '").append(StringUtils.isBlank(zfmt0004.getBelnr3())?" ":StringUtils.addLeftZero(zfmt0004.getBelnr3(),10)).append("'");
		update.append(" where zbelnr = '").append(zfmt0004.getZbelnr()).append("'");
		update.append(" and   zposnr = '").append(StringUtils.addLeftZero(zfmt0004.getZposnr(),5)).append("'");
		//@formatter:on
		this.execSql(update.toString());
		this.updateParentAcct(zfmt0004.getZbelnr(),zfmt0004.getZposnr());
	}
	
	/**
	 * 私有方法，判断当前字段是否需要更新
	 * @param param
	 * @return
	 */
	private boolean needReset(String param){
		if(null == param)
			return false;
		if(StringUtils.isBlank(param))
			return true;
		return true;
	}

	@Override
	public void saveZounrNotExistInfo(String zbelnr, String vbeln, boolean deleteZetime) throws Exception {
		//1 校验是否已经登记
		boolean exists = checkZounrInfoExist(zbelnr,vbeln);
		List<String> sqls = new ArrayList<String>();
		//2如果已经存在,则将开票标识重新置为N,否则新增一条通知记录 
		//@formatter:off
		if(exists){
			String save = "update zfmt0015 set fksta = 'N' "
				+ " where mandt="+getClient()
				+ " and zbelnr ='"+zbelnr.trim() + "'"
				+ " and vbeln  ='"+StringUtils.addLeftZero(vbeln.trim(), 10)+ "'";
			sqls.add(save);
		}else{
			String save = "insert into zfmt0015(mandt,zbelnr,vbeln,zndate,fksta) "
				+ " select " + getClient() + ",'" + zbelnr.trim() + "','"
				+ StringUtils.addLeftZero(vbeln.trim(), 10)
				+ "',to_char(sysdate,'yyyyMMdd'),'N' from dual";
			sqls.add(save);
		}
		//@formatter:on
		//3更新到账信息状态为未开票
		String update = "update zfmt0003 set fksta = 'N'";
		if(deleteZetime){
			update += ",zetime=' '";
		}
		update +=" where zbelnr = '"+zbelnr.trim() + "'";
		sqls.add(update);
		execSqlList(sqls);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public boolean checkZounrInfoExist(String zbelnr, String vbeln) throws Exception {
		if(StringUtils.isBlank(zbelnr)||StringUtils.isBlank(vbeln)){
			throw new Exception("流水号或外向交货单号不能为空!");
		}
		String check = "select 1 from zfmt0015 where mandt="+getClient()+" and zbelnr = '"+zbelnr+"' and vbeln = '"+StringUtils.addLeftZero(vbeln.trim(),10)+"'";
		List list = findListBySql(check);
		if(null!=list && list.size()>0){
			return true;
		}
		return false;
	}

	@Override
	public String uploadSingleReceive(String sortl, String zbelnr, String ecis, String vbeln, Double zhc,
			Double zcamount, String kunnr, String bukrs) {
		String result = "success";// 返回结果
		String vbelnTemp = "";// 外向交货单
		// 1.查找外向交货单
		if (StringUtils.isNotBlank(vbeln)) {
			vbelnTemp = StringUtils.addLeftZero(vbeln.trim(), 10);
		} else {
			List<Object> templList = findListBySql("select distinct vbeln from likp where xabln='"+ecis+"'");
			if(templList==null|| templList.isEmpty() ||templList.size()>1){
				result = "根据ECIS号查找不到或找到多个外向交货单!";
				return result;
			}else{
				vbelnTemp = StringUtils.nullToString(templList.get(0));
			}
		}
		if(StringUtils.isBlank(vbelnTemp)){
			return "根据ECIS号查找不到外向交货单!";
		}
		// 2.根据校验当前上传外向单对应客户编号与当前编号是否相符
		String checkKunnr = "select 1 from bsid d"
			+ " inner join (select distinct vgbel,vbeln from vbrp) p on p.vbeln = d.zuonr "
			+ " inner join zfmt0003 e on e.bukrs = d.bukrs and e.kunnr = d.kunnr "
			+ " where umskz=' '"
			+ " and p.vgbel='"+vbelnTemp+"'"
			+ " and e.zbelnr ='"+zbelnr+"'"
			+ " and d.mandt ="+getClient();
		List<Object> cklList = findListBySql(checkKunnr);
		if(null==cklList || cklList.size()==0){
			result = "ECIS号或外向交货单号与当前客户不符!";
			return result;
		}
		// 3.根据外向交货单校验bsid表中应收款汇总金额是否与上传金额相等
		String checkSql = "select nvl(sum(case when bschl >'09' then -wrbtr else wrbtr end),0) as amt from bsid d"
			+ " inner join (select distinct vgbel,vbeln from vbrp) p on p.vbeln = d.zuonr "
			+ " where umskz=' '"
			+ " and d.mandt ="
			+ getClient()
			+ " and p.vgbel='"+vbelnTemp+"'";
		List<Object> temp = findListBySql(checkSql);
		if(null==temp || temp.size()==0){
			result = "根据外向交货单"+vbelnTemp+"查找不到应收款!";
			return result;
		}else{
			String object = temp.get(0).toString();
			Double amt = Double.valueOf(StringUtils.isBlank(object)?"0":object.toString());
			if(Double.compare(amt,zcamount)!=0){
				result = "金额不符,该外向交货单应收款金额合计为"+object;
				return result;
			}
 		}
		// 4.校验通过后,根据外向交货单查询应收款明细记录
		// 金额|币种|合同编号|外向交货单|发票号
		String querySql = "select sum(case when bschl >'09' then -wrbtr else wrbtr end) as wrbtr,waers,k.bstnk_vf,p.vgbel,d.zuonr from bsid d"
			+ " inner join vbrk k on  k.vbeln = d.zuonr and k.mandt = d.mandt"
			+ " inner join (select distinct vgbel,vbeln from vbrp) p on p.vbeln = d.zuonr "
			+ " where umskz=' '"
			+ " and d.mandt ="
			+ getClient()
			+ " and p.vgbel='"+vbelnTemp+"'"
			+ " group by waers,k.bstnk_vf,p.vgbel,d.zuonr"
			+ " order by k.bstnk_vf";
		try {
			List<Object> list = findListBySql(querySql);
			if (null != list && list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					Object[] add =(Object[]) list.get(i);
					Zfmt0004 sep = new Zfmt0004();
					sep.setZcamount(StringUtils.nullToString(add[0]));
					sep.setWaers(StringUtils.nullToString(add[1]));
					sep.setBstkd(StringUtils.nullToString(add[2]));
					sep.setVbeln(StringUtils.nullToString(add[3]));
					sep.setZuonr(StringUtils.nullToString(add[4]));
					sep.setMandt(getClient());
					sep.setZbelnr(zbelnr);
					sep.setKunnr(kunnr);//客户编号
					sep.setBukrs(bukrs);//公司代码
					sep.setZposnr(null);
					sep.setZwoamt("0");
					sep.setZrbl("B");
					sep.setZbamt("0");
					sep.setKursf("1");
					sep.setZanote("收" + StringUtils.nullToString(sortl) + "款" + StringUtils.nullToString(add[2])+",");
					if(i==0){
						sep.setZhc(zhc.toString());
					}else{
						sep.setZhc("0");
					}
					sep.setZdrs("P");
					sep.setZcirs("P");
					saveZfmt0004(sep);
				}
			}
		} catch (Exception e) {
			result = "程序处理异常:"+e.getMessage();
		}
		return result;
	}

	@Override
	public Zfmt0004 findSepAcctByZbelnrAndZposnr(String zbelnr, String zposnr) {
		Zfmt0004 sepAcct = null;
		//@formatter:off
		String sql = 
			  "select a.mandt,a.zbelnr,to_number(zposnr),a.bstkd,a.vbeln,a.zcamount,a.zwoamt,a.zrbl,a.zpodat,a.zbamt,a.waers,a.kursf,a.zanote,a.zsmc," 
			+ "a.zhc,a.belnr,a.umskz,a.rstgr,a.ztnum,a.zdrs,a.zcirs,a.zuonr,a.stblg,a.mwskz,a.zposnr_s,a.zstblg,a.zp_zstblg,a.spart,a.zwodat,a.kunnr,b.sortl, "
			+ "a.bukrs,a.zpeinh,a.belnr2,a.belnr3 "
			+ " from zfmt0004 a"
			+ " left join kna1 b on a.mandt=b.mandt and a.kunnr=b.kunnr"
			+ " where a.mandt=" + getClient()
			+ " and a.zbelnr = '"+zbelnr+"'"
			+ " and a.zposnr = '"+StringUtils.addLeftZero(zposnr,5)+"'"
			+ " order by a.zposnr";
		//@formatter:on
		List<Object> list = findListBySql(sql);
		if(null!=list && list.size()>0){
			Object[] ob =(Object[]) list.get(0);
			sepAcct = new Zfmt0004();
			sepAcct.setMandt(StringUtils.nullToString(ob[0]));
			sepAcct.setZbelnr(StringUtils.nullToString(ob[1]));
			sepAcct.setZposnr(StringUtils.nullToString(ob[2]));
			sepAcct.setBstkd(StringUtils.nullToString(ob[3]));
			sepAcct.setVbeln(StringUtils.nullToString(ob[4]));
			sepAcct.setZcamount(StringUtils.nullToString(ob[5]));
			sepAcct.setZwoamt(StringUtils.nullToString(ob[6]));
			sepAcct.setZrbl(StringUtils.nullToString(ob[7]));
			sepAcct.setZpodat(StringUtils.nullToString(ob[8]));
			sepAcct.setZbamt(StringUtils.nullToString(ob[9]));
			sepAcct.setWaers(StringUtils.nullToString(ob[10]));
			sepAcct.setKursf(StringUtils.nullToString(ob[11]));
			sepAcct.setZanote(StringUtils.nullToString(ob[12]));
			sepAcct.setZsmc(StringUtils.nullToString(ob[13]));
			sepAcct.setZhc(StringUtils.nullToString(ob[14]));
			sepAcct.setBelnr(StringUtils.nullToString(ob[15]));
			sepAcct.setUmskz(StringUtils.nullToString(ob[16]));
			sepAcct.setRstgr(StringUtils.nullToString(ob[17]));
			sepAcct.setZtnum(StringUtils.nullToString(ob[18]));
			sepAcct.setZdrs(StringUtils.nullToString(ob[19]));
			sepAcct.setZcirs(StringUtils.nullToString(ob[20]));
			sepAcct.setZuonr(StringUtils.nullToString(ob[21]));
			sepAcct.setStblg(StringUtils.nullToString(ob[22]));
			sepAcct.setMwskz(StringUtils.nullToString(ob[23]));
			sepAcct.setZposnrSup(StringUtils.nullToString(ob[24]));
			sepAcct.setZstblg(StringUtils.nullToString(ob[25]));//红冲凭证号
			sepAcct.setZpStblg(StringUtils.nullToString(ob[26]));//红冲预收凭证
			sepAcct.setSpart(StringUtils.nullToString(ob[27]));//产品组
			sepAcct.setZwodat(StringUtils.nullToString(ob[28]));//冲销预收过账日期
			sepAcct.setKunnr(StringUtils.nullToString(ob[29]));//客户编号
			sepAcct.setSortl(StringUtils.nullToString(ob[30]));//客户简称
			sepAcct.setBukrs(StringUtils.nullToString(ob[31]));//公司代码
			sepAcct.setZpeinh(StringUtils.nullToString(ob[32]));//单位换算
			sepAcct.setBelnr2(StringUtils.nullToString(ob[33]));//凭证2
			sepAcct.setBelnr3(StringUtils.nullToString(ob[34]));//凭证3
		}
		return sepAcct;
	}

	@Override
	public void checkParentAcctBalance(String zbelnr, String zpsonr) throws Exception {
		String query = "select 1 from zfmt0004 ta  "
			+ " inner join zfmt0004 tb on ta.zbelnr=tb.zbelnr and ta.zposnr = tb.zposnr_s and ta.mandt = tb.mandt"
			+ " where ta.zwoamt>ta.zcamount"
			+ " and ta.mandt ="+getClient()
			+ " and ta.zbelnr = '"+zbelnr+"'"
			+ " and tb.zposnr = '"+StringUtils.addLeftZero(zpsonr,5)+"'";
		List<Object> temp = findListBySql(query);
		if(temp!=null&&temp.size()>0){
			throw new Exception("预收款冲销金额大于本次收款金额,请确认.");
		}
	}

	@Override
	public void submitAcctSep(String flowNum) throws Exception {
		//@formatter:off
		// 更新到账信息预收状态、流转状态、对账状态
		String updateSeprate = "update zfmt0004 set "
			+ " zdrs=case when zdrs='P' and vbeln!=' ' and bstkd!=' ' and zwoamt=0 then 'F' when zrbl='A' and zcamount=zwoamt and zwoamt<>0 then 'C' when zrbl='A' and vbeln=' ' and zcamount<>zwoamt and zcamount<>0 then 'P' when zdrs='P' and (zrbl='B' or zrbl='C' or zrbl='D' or zrbl='E') then 'F' else zdrs end,"
			+ " zcirs=case when zcirs='P' then 'F' when zrbl='A' and zcamount=zwoamt and zcamount<>0 then 'C' else zcirs end "
			+ " where zbelnr = '"+flowNum+"'";
		this.execSql(updateSeprate);
		// 更新到账记录状态
		updateAcctStatusByFlowNum(flowNum);
	}
	
	@Override
	public Object[] getBelnrDetail(String belnr, String bukrs, String gjahr){
		Object[] result = new Object[3];
		JSONObject jsonObject = new JSONObject();
		jsonObject.element("PI_BELNR",belnr);
		jsonObject.element("PI_BUKRS",bukrs);
		jsonObject.element("PI_GJAHR",gjahr);
		SAPRfcJson rfcjson = saprfcservice.RFC("sapConnectConfig","ZFMI0002", jsonObject.toString(), 1, -1, null);
		JSONObject headTable = rfcjson.getRecord("PT_HEAD");
		JSONArray headObs = headTable.getJSONArray("row");
		if(headObs.size()>0){
			JSONObject obj = (JSONObject) headObs.get(0);
			String[] head = new String[9];
			head[0] = obj.get("BUKRS").toString();//公司代码
			head[1] = obj.get("BELNR").toString();//会计凭证编号
			head[2] = obj.get("GJAHR").toString();//会计年度
			head[3] = obj.get("BLART").toString();//凭证类型
			head[4] = obj.get("BLDAT").toString();//凭证日期
			head[5] = obj.get("BUDAT").toString();//过帐日期
			head[6] = obj.get("MONAT").toString();//会计期间
			head[7] = obj.get("XBLNR").toString();//参考凭证号
			head[8] = obj.get("WAERS").toString();//货币码
			result[1] = head;
		}
		JSONObject itemTable = rfcjson.getRecord("PT_ITEM");
		JSONArray itemObs = itemTable.getJSONArray("row");
		if(itemObs.size()>0){
			List<String[]> list = new ArrayList<String[]>();
			for(int i = 0; i < itemObs.size(); i++){
				JSONObject itemObj = (JSONObject) itemObs.get(i);
				String[] item = new String[17];
				item[0] = itemObj.get("BUKRS").toString();//公司代码
				item[1] = itemObj.get("BELNR").toString();//会计凭证编号
				item[2] = itemObj.get("GJAHR").toString();//会计年度
				item[3] = StringUtils.removeLeftZero(itemObj.get("BUZEI").toString());//会计凭证中的行项目数
				item[4] = itemObj.get("BSCHL").toString();//记帐代码
				item[5] = itemObj.get("WRBTR").toString();//金额
				item[6] = itemObj.get("DMBTR").toString();//本位币金额
				item[7] = itemObj.get("VALUT").toString();//起息日
				item[8] = itemObj.get("ZUONR").toString();//发票号
				item[9] = itemObj.get("SGTXT").toString();//项目文本
				item[10] = itemObj.get("HKONT").toString();//科目
				item[11] = itemObj.get("TXT50").toString();//科目说明
				item[12] = itemObj.get("XREF1").toString();//外向交货单
				item[13] = itemObj.get("XREF2").toString();//合同号
				item[14] = itemObj.get("XREF3").toString();//合同号
				item[15] = itemObj.get("WAERS").toString();//货币
				item[16] = itemObj.get("ZWAERS").toString();//本位币
				list.add(item);
			}
			result[2]=list;
		}
		result[0]="success";
		return result;
	}

	@Override
	public void checkManualAcct(String zbelnr) throws Exception {
		try {
			Long.parseLong(zbelnr);
		} catch (NumberFormatException e) {
			throw new Exception("到账信息为手工初始化记录,不能进行当前操作!");
		}
	}

	@Override
	public void rejectAcct(String flowNum) throws Exception {
		//@formatter:off
		String updateSeprate = "update zfmt0004 set "
			+ " zdrs=case when zcirs='F' then 'P' else zdrs end,"
			+ " zcirs=case when zcirs='F' then 'P' else zcirs end "
			+ " where zbelnr = '"+flowNum+"'";
		//@formatter:on
		this.execSql(updateSeprate);
		//更新到账记录状态
		updateAcctStatusByFlowNum(flowNum);
	}

	@Override
	public void updateZPnameByZbelnr(String flowNum) throws Exception {
		String code = getCurrentUserCode();
		updateZfmt0003(flowNum,"zpname",StringUtils.isBlank(code)?" ":code);
	}

	@Override
	public void updateZfmt0003(String zbelnr, String type, String value) throws Exception {
		if(StringUtils.isBlank(zbelnr)||StringUtils.isBlank(type)
				||value == null){
			return;
		}
		String sql = "update zfmt0003 set "
			+ type
			+ "='"
			+ value
			+ "' "
			+ " where zbelnr ='"
			+ zbelnr
			+ "'";
		this.execSql(sql);
	}
	
	@Override
	public void checkWaersBeforeCert1(String zbelnr) throws Exception {
		String query = "select distinct * from ("
			+ " select waers from zfmt0003 where zbelnr = '"+ zbelnr + "'"
			+ " union all"
			+ " select waers from zfmt0004 where zbelnr = '"+ zbelnr +"'"
			+ " )";
		List<Object> list = findListBySql(query);
		if(null!=list && list.size()>1){
			throw new Exception("到账信息币种与明细项币种不一致,请手工生成凭证!");
		}
	}
	
	@Override
	public void checkBukrsBeforeCert1(String zbelnr) throws Exception {
		//1.到账分解中同时包含7000公司和其他公司时不能自动记账
		String query = 
			"select bukrs from zfmt0001 where zbelnr ='"+ zbelnr + "'"
			+ " union"
			+ " select distinct bukrs from zfmt0004 where zbelnr = '"+ zbelnr +"'";
		List<Object> list = findListBySql(query);
		if(null!=list && list.size()>1){
			for(Object obj:list){
				if("7000".equals(StringUtils.nullToString(obj))){
					throw new Exception("到款登记中有多个公司并且包含7000公司,请手工生成凭证!");
				}
			}
		}
		//2.智造绿能(9999)分解为多个公司时不能自动记账
		String checkSql = "select distinct a.bukrs from zfmt0004 a"
			+ " inner join zfmt0001 b on a.zbelnr=b.zbelnr"
			+ " where b.zbelnr = '"+zbelnr+"'"
			+ " and b.bukrs='7300'";;
		list = findListBySql(checkSql);
		if(null!=list && list.size()>1){
			throw new Exception("智造绿能到款登记中包含多个公司,请手工生成凭证!");
		}
	}

	@Override
	public void certOne(String zbelnr) throws Exception {
		//校验币种
		checkWaersBeforeCert1(zbelnr);
		//校验公司
		checkBukrsBeforeCert1(zbelnr);
		//调用SAP接口生成凭证
		generateCert(zbelnr,null);
		//后续流程同手工记账
		certManual(zbelnr,null);
	}

	@Override
	public List<String> generateCert(String zbelnr, String zpsonr) throws Exception {
		List<String> belnrResult = new ArrayList<String>();
		JSONObject jsonObject = new JSONObject();
		jsonObject.element("PI_ZBELNR",zbelnr);
		if(StringUtils.isNotBlank(zpsonr)){
			jsonObject.element("PI_ZPSONR",zpsonr);
		}
		SAPRfcJson rfcjson = saprfcservice.RFC("sapConnectConfig","ZFMI0001", jsonObject.toString(), 1, -1, null);
		JSONObject retTable = rfcjson.getRecord("PT_RETURN");
		JSONArray retObs = retTable.getJSONArray("row");
		if(retObs.size()>0){
			JSONObject obj = (JSONObject) retObs.get(0);
			String type = obj.get("TYPE").toString();
			if("E".equals(type)){
				throw new Exception("E"+obj.get("MESSAGE").toString());
			}else if("S".equals(type)){
				belnrResult.add(obj.get("BELNR").toString());
			}
		}
		return belnrResult;
	}

	@Override
	public void certManual(String zbelnr, List<String> belnr) throws Exception {
		//@formatter:off
		String update = "update zfmt0004 set "
			+ " zdrs=case when zdrs='F' and (zrbl='B' or zrbl='C' or zrbl='D' or zrbl='E') then 'C' else zdrs end,"
			+ " zcirs=case when zcirs='F' then 'C' else zcirs end";
		if(null!=belnr && belnr.size()>0){
			if(belnr.size()>0){
				update += " ,belnr='"+belnr.get(0).trim()+"'";
			}
			if(belnr.size()>1){
				update += " ,belnr2='"+belnr.get(1).trim()+"'";
			}
			if(belnr.size()>2){
				update += " ,belnr3='"+belnr.get(2).trim()+"'";
			}
		}
		update += " where zbelnr = '"+zbelnr+"'";
		this.execSql(update);
		String updateDay = "update zfmt0001 a set "
			+ " a.belnr=(select max(belnr) from zfmt0004 b where a.mandt=b.mandt and a.zbelnr=b.zbelnr)"
			+ " where a.zbelnr = '"+zbelnr+"'";
		//@formatter:on
		this.execSql(updateDay);
		// 更新AE预收沖銷金額
		String select = "select a.bstkd,a.vbeln,a.waers,a.zcamount,b.kunnr from zfmt0004 a,zfmt0003 b where zrbl='A' and a.zbelnr = b.zbelnr and a.zbelnr = '"+zbelnr+"' ";
		List<Object> list = findListBySql(select);
		if(null!=list && list.size()>0){
			for(Object obj : list){
				Object[] ojObjects = (Object[])obj;
				String updString = "update zfmt0006 set zdwoamt = to_number(zdwoamt)+to_number("+ojObjects[3].toString()+")"
				+ " where zover = '否' and bstkd ='"+ojObjects[0].toString()+"'"
				+ " and waers='"+ojObjects[2].toString()+"'"
				+ " and kunnr='"+ojObjects[4].toString()+"'";
			this.execSql(updString);
			}
		}
		//@formatter:off
		//记账时向未知客户登记表插入数据
		String updZfmt0019 = "merge into zfmt0019 a "
			+ " using (select mandt,trim(sgtxt) as sgtxt,kunnr,zcrem from zfmt0003 where zbelnr='"+zbelnr+"') b"
			+ " on (trim(a.sgtxt)=upper(trim(b.sgtxt)) or (trim(a.sgtxt) is null and trim(b.sgtxt) is null))"
			+ " when matched then"
			+ " update set a.kunnr=b.kunnr,a.zcrem=b.zcrem where trim(a.sgtxt)=upper(trim(b.sgtxt))"
			+ " when not matched then"
			+ " insert values (b.mandt,nvl(upper(trim(b.sgtxt)),' '),b.kunnr,b.zcrem)";
		this.execSql(updZfmt0019);
		//@formatter:on
		// 更新到账记录状态
		updateAcctStatusByFlowNum(zbelnr);
	}

	@Override
	public String certTwo(String zbelnr, String zposnr) throws Exception {
		//校验币种
		checkWaersBeforeCert2(zbelnr,zposnr);
		//校验类型
		checkZrblBeforeCert2(zbelnr,zposnr);
		//生成凭证接口
		String belnr = this.generateCert(zbelnr,zposnr).get(0);
		// 更新预收分解表冲销凭证号字段
		//@formatter:off
		String update = "update zfmt0004 set "
			+ " zdrs=case when zdrs='F' then 'C' else zdrs end,"
			+ " zcirs=case when zcirs='F' then 'C' else zcirs end,"
			+ " stblg='"+belnr+"',"
			+ " zwoamt=zcamount,"
			+ " zwodat=case when zwodat='00000000' then to_char(sysdate,'yyyyMMdd') else zwodat end "
			+ " where zbelnr = '"+zbelnr+"'"
			+ " and zposnr = '"+StringUtils.addLeftZero(zposnr,5)+"'";
		//@formatter:on
		this.execSql(update);
		// 更新到账记录状态
		updateAcctStatusByFlowNum(zbelnr);
		return belnr;
	}

	@Override
	public void checkWaersBeforeCert2(String zbelnr, String zposnr) throws Exception {
		String query = "select distinct * from ("
			+ " select waers from zfmt0003 where zbelnr = '"+ zbelnr + "'"
			+ " union all"
			+ " select waers from zfmt0004 where zbelnr = '"+ zbelnr +"' and zposnr='"+StringUtils.addLeftZero(zposnr,5)+"'" 
			+ " )";
		List<Object> list = findListBySql(query);
		if(null!=list && list.size()>1){
			throw new Exception("到账信息币种与当前收款币种不一致,请手工生成收款冲销凭证!");
		}
	}
	
	public void checkZrblBeforeCert2(String zbelnr, String zposnr) throws Exception {
		String query = "select 1 from zfmt0004 where zbelnr='"+zbelnr+"' and (zrbl='B' or zrbl='A') and zposnr='"+StringUtils.addLeftZero(zposnr,5)+"'";
		List<Object> list = findListBySql(query);
		if (null == list || list.size() ==0) {
			throw new Exception("当前到账分解信息不是应收款或预收款,请手工生成凭证!");
		}
	}

	@Override
	public void saveReceiveFlushManual(String zbelnr, String belnr, String zposnr) throws Exception {
		// 更新预收分解表冲销凭证号字段
		//@formatter:off
		String update = "update zfmt0004 set "
			+ " zdrs='C',"
			+ " zcirs=case when zcirs='F' then 'C' else zcirs end,"
			+ " stblg='"+belnr+"',"
			+ " zwoamt=zcamount,"
			+ " zwodat=case when zwodat='00000000' then to_char(sysdate,'yyyyMMdd') else zwodat end "
			+ " where zbelnr = '"+zbelnr+"'"
			+ " and zposnr = '"+StringUtils.addLeftZero(zposnr,5)+"'";
		//@formatter:on
		this.execSql(update);
		// 更新到账记录状态
		updateAcctStatusByFlowNum(zbelnr);
	}

	@Override
	public void sepratePushBack(String zbelnr) throws Exception {
		String updateSeprate = "update zfmt0004 set "
			+ " zdrs=case when (zposnr_s!=' ' and zposnr_s!='00000' and zdrs='F') or (zposnr_s=' ' and zrbl='A')  then 'P' " 
			+ " else zdrs end "
			+ " where zbelnr = '"+zbelnr+"'";
		//@formatter:on
		this.execSql(updateSeprate);
		//更新到账记录状态
		updateAcctStatusByFlowNum(zbelnr);
	}

	@Override
	public List<Zfmt0004> findReceivedForCert(String flowNum) {
		List<Zfmt0004> result = new ArrayList<Zfmt0004>();
		//@formatter:off
		String sql = 
			  "select a.mandt,a.zbelnr,to_number(a.zposnr),a.bstkd,a.vbeln,a.zcamount,a.zwoamt,a.zrbl,a.zpodat,a.zbamt,a.waers,a.kursf,a.zanote,a.zsmc,a.zhc,"
			+ "a.belnr,a.umskz,a.rstgr,a.ztnum,a.zdrs,a.zcirs,a.zuonr,a.stblg,a.mwskz,a.zposnr_s,a.zstblg,a.zp_zstblg,a.spart,a.zwodat,a.kunnr,b.sortl,a.bukrs,a.zpeinh,a.belnr2,a.belnr3 "
			+ " from zfmt0004 a"
			+ " left join kna1 b on a.mandt=b.mandt and a.kunnr=b.kunnr"
			+ " where a.mandt=" + getClient()
			//+ " and zcamount<>zwoamt "
			//+ " and zposnr_s != ' '"
			//+ " and zposnr_s != '00000'"
			+ " and a.zbelnr = '"+flowNum+"'"
			+ " order by zposnr";
		//@formatter:on
		List<Object> list = findListBySql(sql);
		if(null!=list && list.size()>0){
			Zfmt0004 sepAcct;
			for(Object obj : list){
				Object[] ob = (Object[])obj;
				sepAcct = new Zfmt0004();
				sepAcct.setMandt(StringUtils.nullToString(ob[0]));
				sepAcct.setZbelnr(StringUtils.nullToString(ob[1]));
				sepAcct.setZposnr(StringUtils.nullToString(ob[2]));
				sepAcct.setBstkd(StringUtils.nullToString(ob[3]));
				sepAcct.setVbeln(StringUtils.nullToString(ob[4]));
				sepAcct.setZcamount(StringUtils.nullToString(ob[5]));
				sepAcct.setZwoamt(StringUtils.nullToString(ob[6]));
				sepAcct.setZrbl(StringUtils.nullToString(ob[7]));
				sepAcct.setZpodat(StringUtils.nullToString(ob[8]));
				sepAcct.setZbamt(StringUtils.nullToString(ob[9]));
				sepAcct.setWaers(StringUtils.nullToString(ob[10]));
				sepAcct.setKursf(StringUtils.nullToString(ob[11]));
				sepAcct.setZanote(StringUtils.nullToString(ob[12]));
				sepAcct.setZsmc(StringUtils.nullToString(ob[13]));
				sepAcct.setZhc(StringUtils.nullToString(ob[14]));
				sepAcct.setBelnr(StringUtils.nullToString(ob[15]));
				sepAcct.setUmskz(StringUtils.nullToString(ob[16]));
				sepAcct.setRstgr(StringUtils.nullToString(ob[17]));
				sepAcct.setZtnum(StringUtils.nullToString(ob[18]));
				sepAcct.setZdrs(StringUtils.nullToString(ob[19]));
				sepAcct.setZcirs(StringUtils.nullToString(ob[20]));
				sepAcct.setZuonr(StringUtils.nullToString(ob[21]));
				sepAcct.setStblg(StringUtils.nullToString(ob[22]));
				sepAcct.setMwskz(StringUtils.nullToString(ob[23]));
				sepAcct.setZposnrSup(StringUtils.nullToString(ob[24]));
				sepAcct.setZstblg(StringUtils.nullToString(ob[25]));//红冲凭证号
				sepAcct.setZpStblg(StringUtils.nullToString(ob[26]));//红冲预收凭证
				sepAcct.setSpart(StringUtils.nullToString(ob[27]));//产品组
				sepAcct.setZwodat(StringUtils.nullToString(ob[28]));//冲销预收过账日期
				sepAcct.setKunnr(StringUtils.nullToString(ob[29]));//客户编号
				sepAcct.setSortl(StringUtils.nullToString(ob[30]));//客户简称
				sepAcct.setBukrs(StringUtils.nullToString(ob[31]));//公司代码
				sepAcct.setZpeinh(StringUtils.nullToString(ob[32]));//单位换算
				sepAcct.setBelnr2(StringUtils.nullToString(ob[33]));//凭证2
				sepAcct.setBelnr3(StringUtils.nullToString(ob[34]));//凭证3
				result.add(sepAcct);
			}
		}
		return result;
	}

	@Override
	public void checkReceiveBelnrFlush(String zbelnr, String zposnr) throws Exception {
		// 根据当前到账信息中的公司代码,预收冲销日期中的年份及凭证编号,查询凭证抬头信息bkpf中的冲销原因stgrd字段是否为空
		//@formatter:off
		String checkSql = "select 1 from bkpf a,zfmt0003 b,zfmt0004 c "
			+ " where a.belnr = c.stblg"
			+ " and a.bukrs = b.bukrs"
			+ " and a.gjahr = substr(c.zwodat,0,4)"
			+ " and b.zbelnr = c.zbelnr"
			+ " and a.stgrd!=' '"
			+ " and c.zbelnr='"+zbelnr+"'"
			+ " and c.zposnr='"+StringUtils.addLeftZero(zposnr,5)+"'";
		//@formatter:on
		List<Object> checkList = findListBySql(checkSql);
		if(null==checkList || checkList.size()==0){
			throw new Exception("当前凭证尚未冲销,请确认!");
		}
	}

	@Override
	public void updateBackFlushReceive(String zbelnr, String zposnr) throws Exception {
		//2.校验当前预收款是否已经冲销
		String check = "select 1 from zfmt0004 where stblg !=' ' and zbelnr ='"+zbelnr+"' and zposnr='"+StringUtils.addLeftZero(zposnr,5)+"'";
		List<Object> checkList = findListBySql(check);
		if(null==checkList || checkList.size()==0){
			throw new Exception("当前预收款未经冲销,无法回冲预收!");
		}
		//3.更新当前预收分解信息,已冲销金额置为0,状态置为PC,冲销凭证号置为空
		String update = "update zfmt0004 set stblg=' ',zdrs='P',zcirs='C',zwoamt=0 " 
				+ "where zbelnr='"+ zbelnr+"' " 
				+ "and zposnr='"+StringUtils.addLeftZero(zposnr,5)+"'";
		this.execSql(update);
		//@formatter:on
		//4.更新到账记录状态
		updateAcctStatusByFlowNum(zbelnr);
	}

	@Override
	public void checkFicoBelnrFlush(String zbelnr) throws Exception {
		// 根据当前到账信息中的公司代码,过账日期中的年份及凭证编号,查询凭证抬头信息bkpf中的冲销原因stgrd字段是否为空
		//@formatter:off
		String checkSql = "select 1 from bkpf a,zfmt0003 b,zfmt0004 c,zfmt0001 d "
			+ " where a.belnr = c.belnr"
			+ " and a.bukrs  = c.bukrs"
			+ " and a.gjahr  = substr(b.budat,0,4)"
			+ " and b.zbelnr = c.zbelnr"
			+ " and b.zbelnr = d.zbelnr"
			+ " and a.stgrd != ' '"
			+ " and b.zbelnr='"+zbelnr+"'";
		//@formatter:on
		List<Object> checkList = findListBySql(checkSql);
		if(null==checkList || checkList.size()==0){
			throw new Exception("当前凭证尚未冲销,请确认!");
		}
	}

	@Override
	public void updateBackFlushBelnr(String zbelnr) throws Exception {
		//2.校验是否当前到账信息下有预收分解信息没有做回冲预收
		String check1 = "select 1 from zfmt0004 where stblg !=' ' and zrbl='A' and zbelnr ='"+zbelnr+"'";
		List<Object> check1List = findListBySql(check1);
		if(null!=check1List && check1List.size()>0){
			throw new Exception("当前到账信息中包含已经冲销的预收款,请先进行回冲预收!");
		}
		//3.校验当前到账信息是否已经生成凭证
		String check2 = "select 1 from zfmt0004 where belnr !=' ' and zbelnr ='"+zbelnr+"'";
		List<Object> check2List = findListBySql(check2);
		if(null==check2List || check2List.size()==0){
			throw new Exception("当前到账信息未生成凭证,无法进行回冲凭证!");
		}
		//4.更新AE预收沖銷金額
		List<String> sqls = new ArrayList<String>();
		String select = "select a.bstkd,a.vbeln,a.waers,a.zcamount,b.kunnr from zfmt0004 a,zfmt0003 b where zrbl='A' and a.zbelnr = b.zbelnr and a.zbelnr = '"+zbelnr+"' ";
		List<Object> list = findListBySql(select);
		if(null!=list && list.size()>0){
			for(Object obj : list){
				Object[] ojObjects = (Object[])obj;
				String updString ="update zfmt0006 set zdwoamt = to_number(zdwoamt)-to_number("+ojObjects[3].toString()+")"
								+ " where bstkd ='"+ojObjects[0].toString()+"'"
								+ " and waers='"+ojObjects[2].toString()+"'"
								+ " and kunnr='"+ojObjects[4].toString()+"'";;
				sqls.add(updString);
			}
		}
		//5.更新到账分解信息:状态改为PP,凭证号置空
		String updateSub = "update zfmt0004 set "
			+ " zdrs ='P',"
			+ " zcirs='P',"
			+ " belnr=' ',"
			+ " belnr2=' ',"
			+ " belnr3=' '"
			+ " where zbelnr = '"+zbelnr+"'";
		sqls.add(updateSub);
		//6.更新日记账信息:凭证号置空
		String updateDaily = "update zfmt0001 set "
			+ " belnr=' '"
			+ " where zbelnr = '"+zbelnr+"'";
		sqls.add(updateDaily);
		//@formatter:on
		this.execSqlList(sqls);
		//7.更新到账记录状态
		updateAcctStatusByFlowNum(zbelnr);
	}

	@Override
	public void updateBackFlushAndDoDelete(String zbelnr) throws Exception {
		//@formatter:off
		//2.校验是否当前到账信息下有预收分解信息没有做回冲预收
		String check1 = "select 1 from zfmt0004 where stblg !=' ' and zrbl='A' and zbelnr ='"+zbelnr+"'";
		List<Object> check1List = findListBySql(check1);
		if(null!=check1List && check1List.size()>0){
			throw new Exception("当前到账信息中包含已经冲销的预收款,请先进行回冲预收!");
		}
		//3.校验当前到账信息是否已经生成凭证
		String check2 = "select 1 from zfmt0004 where belnr !=' ' and zbelnr ='"+zbelnr+"'";
		List<Object> check2List = findListBySql(check2);
		if(null==check2List || check2List.size()==0){
			throw new Exception("当前到账信息未生成凭证,无法进行回冲凭证!");
		}
		//4.更新AE预收沖銷金額
		List<String> sqls = new ArrayList<String>();
		String select = "select a.bstkd,a.vbeln,a.waers,a.zcamount,b.kunnr from zfmt0004 a,zfmt0003 b where zrbl='A' and a.zbelnr = b.zbelnr and a.zbelnr = '"+zbelnr+"' ";
		List<Object> list = findListBySql(select);
		if(null!=list && list.size()>0){
			for(Object obj : list){
				Object[] ojObjects = (Object[])obj;
				String updString ="update zfmt0006 set zdwoamt = to_number(zdwoamt)-to_number("+ojObjects[3].toString()+")"
								+ " where bstkd ='"+ojObjects[0].toString()+"'"
								+ " and waers='"+ojObjects[2].toString()+"'"
								+ " and kunnr='"+ojObjects[4].toString()+"'";;
				sqls.add(updString);
			}
		}
		//5.删除到账分解信息
		String deleteSub = "delete from zfmt0004 where zbelnr='"+zbelnr+"'";
		sqls.add(deleteSub);
		//6.删除到账信息
		String deleteMain = "delete from zfmt0003 where zbelnr='"+zbelnr+"'";
		sqls.add(deleteMain);
		//7.更新日记账信息中处理锁字段
		String updateDaily = "update zfmt0001 set zpc=' ',belnr=' ' where zbelnr='"+zbelnr+"'";
		sqls.add(updateDaily);
		//@formatter:on
		this.execSqlList(sqls);
	}

	@Override
	public void saveRemindInfo() throws Exception {
		//@formatter:off
		String update = "update zfmt0003 ta set zremind = "
			+ " case when "
			+ " exists (select 1 from zfmt0004 a,bsid b,vbrk k,vbrp p where a.zbelnr = ta.zbelnr and k.vbeln = b.zuonr and p.vbeln = b.zuonr and trim(k.bstnk_vf)=a.bstkd and p.vgbel=a.vbeln and (a.zrbl='A' or a.zrbl='B') and a.zdrs='F' and a.zcirs='C')"
			+ " then 'Y'"
			+ " else ' '"
			+ " end"
			+ " where zcirs='C' and zdrs in ('P','F')"
			+ " and exists (select 1 from zfmt0007 tb where ta.kunnr=tb.kunnr and ta.bukrs=tb.bukrs and ta.mandt=tb.mandt and tb.zrname='"+getCurrentUserCode()+"')";
		//@formatter:on
		//更新提醒标识
		this.execSql(update);
		//@formatter:off
		String updZuorn = "update zfmt0004 set (zuonr,zwodat)="
			+ " (select nvl(max(b.zuonr),to_char(sysdate,'yyyyMMdd')),to_char(sysdate,'yyyyMMdd') from bsid b,vbrk k,vbrp p where b.zuonr=p.vbeln and p.vgbel=zfmt0004.vbeln and trim(k.bstnk_vf)=zfmt0004.bstkd )"
			+ " where (zrbl='A' or zrbl='B')"
			+ " and bstkd!= ' '"
			+ " and vbeln!=' '"
			+ " and zdrs='F' "
			+ " and zcirs='C'"
			+ " and exists (select 1 from bsid b,vbrk k,vbrp p where b.zuonr=p.vbeln and p.vgbel=zfmt0004.vbeln and trim(k.bstnk_vf)=zfmt0004.bstkd)"
			+ " and exists (select 1 from zfmt0007 tb,zfmt0003 ta where ta.mandt=zfmt0004.mandt and ta.zbelnr=zfmt0004.zbelnr and ta.kunnr=tb.kunnr and ta.bukrs=tb.bukrs and ta.mandt=tb.mandt and tb.zrname='"+getCurrentUserCode()+"')";
		//@formatter:on
		this.execSql(updZuorn);
		//更新冲销日期
		//@formatter:off
		String updZwodat = "update zfmt0004 set zwodat=to_char(sysdate,'yyyyMMdd')"
			+ " where ((zdrs='F' and zcirs='C')" 
			+ " or(zdrs='P' and zcirs='C' and (zrbl='A' or zrbl='B') and vbeln=' '))"
			+ " and zposnr_s!=' '"
			+ " and zposnr_s!='00000'"
			+ " and exists (select 1 from zfmt0007 tb,zfmt0003 ta where ta.mandt=zfmt0004.mandt and ta.zbelnr=zfmt0004.zbelnr and ta.kunnr=tb.kunnr and ta.bukrs=tb.bukrs and ta.mandt=tb.mandt and tb.zrname='"+getCurrentUserCode()+"')";
		//@formatter:on
		this.execSql(updZwodat);
	}

	@Override
	public void sendNoticeEmailToAe(String[] zbelnrs, String cc) throws Exception {
		//查找待发送到账信息中用户列表
		String condition = "";
		if(null!=zbelnrs && zbelnrs.length>0){
			StringBuffer sb = new StringBuffer(" and zbelnr in (");
			for(String zbelnr:zbelnrs){
				sb.append(",'").append(zbelnr).append("'");
			}
			sb.append(")");
			condition = sb.toString().replaceFirst(",","");
		}
		String sqlString = "select distinct zsname from zfmt0003 a where a.mandt="+getClient()+condition
			+" and a.zdrs='P' and (a.zcirs='P' or a.zcirs='C') ";
		List<Object> objects = findListBySql(sqlString);
		if(null==objects || objects.size()==0){
			throw new Exception("到账信息没有对应的业务员!");
		}
		StringBuffer users = new StringBuffer("<br/>发送失败列表:");
		for(Object object : objects){
			if(null!=object && StringUtils.isNotBlank(object.toString())){
				try {
					sendNoticeToSingleAe(object.toString().trim(), zbelnrs, cc,
							"", "yw", true);
				} catch (Exception e) {
					users.append("(AA)").append(object.toString()).append(";");
				}
			}
		}
		MailUtil mailUtil = new MailUtil();
		mailUtil.setHost("mail.leedarson.com");
		mailUtil.setUsername("IT@leedarson.com");
		mailUtil.setPassword("it612345");
		mailUtil.setFrom("IT@leedarson.com");
		mailUtil.setTo("hes@leedarson.com");
		mailUtil.setSubject("发送到账提醒邮件完成");
		mailUtil.setContent("完成时间:"+DateUtils.formatDateTime()+users.toString());
		mailUtil.send();
	}

	@Override
	public void sendNoticeToSingleAe(String userId, String[] zbelnrs, String cc, String type, String from,
			boolean update) throws Exception {
		if(StringUtils.isBlank(userId))
			return;
		// 查找当前用户所有或指定流水号范围内所有需提醒的到账信息
		//@formatter:off
		// 0.拼接流水号查询条件
		String condition = "";
		if(null!=zbelnrs && zbelnrs.length>0){
			StringBuffer sb = new StringBuffer(" and a.zbelnr in (");
			for(String zbelnr:zbelnrs){
				sb.append(",'").append(zbelnr).append("'");
			}
			sb.append(")");
			condition = sb.toString().replaceFirst(",","");
		}
		// 1.查找待登记状态的到账信息列表zfmt0003
		String queryPP = " with temp as ( " 
			+ " select a.zsname,a.kunnr,a.budat,a.sortl,a.zbelnr,' ' as bstkd,a.bukrs,a.waers,a.zdoip,a.zcamount,a.zdrs,a.zcirs,a.sgtxt,nvl(a.zetime,to_char(sysdate,'yyyy-MM-dd hh24:mi:ss')) as zetime"
			+ " from zfmt0003 a";
		if("ae".equals(from)){
			//发送给AE时关联客户对应表
			queryPP+=" inner join zfmt0007 b on a.bukrs = b.bukrs and a.kunnr = b.kunnr and b.zae='"+userId.trim()+"'";
		}
		queryPP+= " where a.zdrs='P' and a.zcirs='P'"
			+ " and (a.fksta!='N' or a.fksta is null)"
			+ " and a.mandt="+getClient()
			+ " and a.kunnr!='...'";
		if("yw".equals(from)){
			queryPP+= " and a.zsname ='"+userId.trim()+"'";
		}
		if(StringUtils.isNotBlank(condition)){
			queryPP += condition;
		}
		queryPP += " )"
			+ " select * from ("
			+ " select zsname,kunnr,budat,sortl,zbelnr,bstkd,bukrs,waers,zdoip,zcamount,zdrs,zcirs,sgtxt,zetime from temp"
			+ " union all"
			+ " select 'Z',kunnr,'','','','','',waers,'',sum(zcamount),'','','','' from temp group by kunnr,waers"
			+ " )order by kunnr,waers,zsname ";
		// 2.查找待分解状态的到账信息列表zfmt0004
		String queryPC = " with temp as ( "
			+ " select b.zsname,b.kunnr,b.budat,b.sortl,b.zbelnr,a.bstkd,b.bukrs,a.waers,b.zdoip,a.zcamount,a.zdrs,a.zcirs,b.sgtxt"
			+ " from zfmt0004 a,zfmt0003 b";
		if("ae".equals(from)){
			queryPC+=",zfmt0007 c";
		}
		queryPC+= " where a.zbelnr = b.zbelnr"
			+ " and a.zrbl='A'"
			+ " and a.zdrs='P'"
			+ " and a.zcirs='C'"
			+ " and (b.fksta!='N' or b.fksta is null)"
			+ " and b.kunnr!='...'";
		if("ae".equals(from)){
			queryPC+=" and c.bukrs = b.bukrs and c.kunnr = b.kunnr and c.zae='"+userId.trim()+"'";
		}
		queryPC+= " and a.mandt="+getClient();
		if("yw".equals(from)){
			queryPC+= " and b.zsname ='"+userId.trim()+"'";
		}
		if(StringUtils.isNotBlank(condition)){
			queryPC += condition;
		}
		queryPC += " )"
			+ " select * from ("
			+ " select zsname,kunnr,budat,sortl,zbelnr,bstkd,bukrs,waers,zdoip,zcamount,zdrs,zcirs,sgtxt from temp"
			+ " union all"
			+ " select 'Z',kunnr,'','','','','',waers,'',sum(zcamount),'','','' from temp group by kunnr,waers"
			+ " )order by kunnr,waers,zsname ";
		//@formatter:on
		
		List<String> zbelnrList = new ArrayList<String>();
		String title = "<h4>此邮件为系统自动发送,请勿回复.</h4>"
			+"<h4>不是您自己负责的客户,请将客户编号修改为'...';</h4>";
		if("ae".equals(from)){
			title="<h4>系统向业务员发送提醒时自动抄送给您,到款信息由客户对应的业务员负责登记或维护,您无需进行操作!</h4>";
		}
		StringBuffer contString = new StringBuffer();
		//邮件标题
		String subjectString = "";
		//待登记信息FC_DAILY_ERROR
		if(StringUtils.isBlank(type)||"PP".equals(type)){
			List<Object> listPP = findListBySql(queryPP);
			if(listPP!=null && listPP.size()>0){
				if(StringUtils.isNotBlank(title)){
					contString.append(title);
					title = null;
				}
				if(StringUtils.isBlank(subjectString)){
					subjectString = "今天到款信息登记,";
				}
				contString.append("<h4>待登记到账信息:</h4>")
				.append(generateTable(listPP,userId,"1",from))
				.append("<br/>");
				for(Object object : listPP){
					Object[] objects = (Object[]) object;
					if(!zbelnrList.contains(StringUtils.nullToString(objects[4]))){
						zbelnrList.add(StringUtils.nullToString(objects[4]));
					}
				}
			}
		}
		//待分解信息
		if(StringUtils.isBlank(type)||"PC".equals(type)){
			List<Object> listPC = findListBySql(queryPC);
			if(listPC!=null && listPC.size()>0){
				if(StringUtils.isNotBlank(title)){
					contString.append(title);
					title = null;
				}
				if(StringUtils.isBlank(subjectString)){
					subjectString = "今天预收待分解提醒,";
				}
				contString.append("<h4>预收待分解信息:</h4>")
				.append(generateTable(listPC,userId,"2",from));
				for(Object object : listPC){
					Object[] objects = (Object[]) object;
					if(!zbelnrList.contains(StringUtils.nullToString(objects[4]))){
						zbelnrList.add(StringUtils.nullToString(objects[4]));
					}
				}
			}
		}
		if(contString.length()>0){
			boolean success = false;
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
					mailUtil.setTocc(cc);
					//mailUtil.setTobcc("hes@leedarson.com");
					mailUtil.setSubject(subjectString + date);
					mailUtil.setContent(contString.toString());
					success = mailUtil.send();
				} catch (Exception e) {
//					LOGGER.error("发送到账提醒邮件失败:"+e.getMessage());
				}
			}
			if(success){
				if(update){
					updateSendDate(zbelnrList);
				}
			}else{
				MailUtil mailUtil = new MailUtil();
				String notice = "<h3 style='color:red;'>今日到账提醒邮件发送给用户"+userId+",Email:"+to+"失败,以下为原邮件内容</h3><br/>"
				+"============================================引用原邮件============================================<br/>";
				String[] mails = findEmail(FC_DAILY_ERROR,SENDTYPE_TO).split(",");
				for(String admin:mails){
					mailUtil.sendMail(null, admin,
							"到款提醒邮件发送失败:" + date, notice+contString.toString());
				}
			}
		}
	}
	
	/**
	 * 发送邮件后更新最早通知时间
	 * @param zbelnrList
	 * @throws Exception
	 */
	private void updateSendDate(List<String> zbelnrList) throws Exception{
		if(null==zbelnrList || zbelnrList.size()==0)
			return;
		StringBuffer condition = new StringBuffer(" zbelnr in (");
		for(String zbelnr:zbelnrList){
			condition.append(",'").append(zbelnr).append("'");
		}
		condition.append(")");
		
		String update = "update zfmt0003 set zetime = to_char(sysdate,'yyyy-MM-dd hh24:mi:ss') "
			+ " where zetime =' ' "
			+ " and "
			+ condition.toString().replaceFirst(",","");
		execSql(update);
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
		for(Object ob:list){
			Object[] obj =(Object[])ob;
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
	public String saveZfmt0016FromSapSys() {
		String errorMsg = "";
		//@formatter:off
		//0更新已记账应收款手续费
		String updateZhc = "update zfmt0004 a"
			+ " set a.zhc=(select c.zhc from zfmt0004 c where c.zbelnr=a.zbelnr and c.zposnr=a.zposnr_s)"
			+ " where a.zdrs='C'"
			+ " and a.zcirs='C'"
			+ " and a.zbelnr like '1%'"
			+ " and a.zrbl='B'"
			+ " and a.zposnr_s !=' '"
			+ " and not exists (select 1 from zfmt0004 b where a.zbelnr=b.zbelnr and a.zposnr_s=b.zposnr_s and b.zcamount>a.zcamount)"
			+ " and exists (select 1 from zfmt0003 d where d.zbelnr=a.zbelnr and d.zdrs='C' and d.zcirs='C')";
		
		//1 删除数据
		String delete = "delete from zfmt0016 a where not exists (select 1 from likp b where a.madat = b.mandt and a.vbeln = b.vbeln) and a.madat ="+getClient();
		//2插入数据
		String insertA = 
			  "insert into zfmt0016(madat,vbeln)                                                                                             "
			 +"select distinct a.mandt,a.vbeln from likp a                                                                                   "
			 +"inner join vbuk c on c.vbeln=a.vbeln                                                                                          "
			 +"inner join lips b on a.vbeln=b.vbeln                                                                                          "
			 +"inner join vbap ta on ta.vbeln=b.vgbel                                                                                        "
			 +"inner join vbak tb on ta.vbeln=tb.vbeln                                                                                       "
			 +"inner join kna1 tc on tb.kunnr=tc.kunnr                                                                                       "
			 +"where not exists (select 1 from zfmt0016 b where a.mandt=b.madat and a.vbeln=b.vbeln ) and a.vbeln like '008%' and a.mandt="+getClient()
			 +"and c.wbstk='C'                                                                                                               "
			 +"and tc.ktokd not in ('Z003','Z011')                                                                                           "
			 +"and tb.kunnr != 'WL1000020'                                         															 "
			 +"and a.wadat_ist > '20131231'                                                                                                  "
			 +"and a.vkorg in (1000,7000,7200,7300)                                                                                               ";
		String insertB = 
			  "insert into zfmt0016(madat,vbeln)                                                     "
			+ "select distinct a.mandt,a.vbeln from likp a                                           "
			+ "inner join vbrp b on a.vbeln=b.vgbel                                                  "
			+ "inner join bsid d on b.vbeln=d.zuonr                                                  "
			+ "inner join vbuk c on c.vbeln=a.vbeln                                                  "
			+ "inner join lips t on a.vbeln=t.vbeln                                                  "
			+ "inner join vbap ta on ta.vbeln=t.vgbel                                                "
			+ "inner join vbak tb on ta.vbeln=tb.vbeln                                               "
			+ "inner join kna1 tc on tb.kunnr=tc.kunnr                                               "
			+ "where not exists (select 1 from zfmt0016 b where a.mandt=b.madat and a.vbeln=b.vbeln )"
			+ "and a.vbeln like '008%' and a.mandt="+getClient()
			+ "and c.wbstk='C'                                                                       "
			+ "and tc.ktokd not in ('Z003','Z011')                                                   "
			+ "and a.vkorg in (1000,7000,7200,7300)                                                       ";
		//3.更新数据
		String update = ""
			+ "declare"
			+ " cursor crs_wsh is"
			+ " select vbeln,bukrs,zodate,zdamt,ziamt,maktx,menge,werks,zinvoice,znation,kunnr,sortl,zrsm,zae,zaa,zeway,zlbelnr,zlwaers,zlterm,zlvtext,zgamt,zbamt,zrdate,zramt,zhc,zdays,zddate,zdmonth,fkdat,klimk,cashc,kursk,vkbur,kdgrp from"
			+ " ("
			+ " with tempc as("
			+ " select ta.vbeln,sum(to_number(to_char(tb.netpr*ta.lfimg/decode(tb.kpein,0,1,tb.kpein),9999999999.99))) as amta,max(tb.werks) as werks,max(tc.kunnr) as kunnr,max(tc.waerk) as waerk from lips ta,vbap tb,vbak tc where ta.mandt=tb.mandt and tb.vbeln=tc.vbeln and ta.vgbel=tb.vbeln and ta.vgpos=tb.posnr and ta.mandt="+getClient()+" and ta.vbeln like '008%' group by ta.vbeln"
			+ " ),tempd as("
			+ " select tv1.vgbel,max(waerk) as waerk,sum(to_number(to_char(case when tv2.fkart='ZF2' then tv1.kzwi1 else -tv1.kzwi1 end,9999999999.99))) as amtb, sum(case when tv2.fkart='ZF2' then tv1.fklmg else -tv1.fklmg end) as sumb"
			+ " from vbrp tv1 inner join vbrk tv2 on tv2.mandt = tv1.mandt and tv2.vbeln = tv1.vbeln and tv2.fkart in ('ZF2','S1')"
			+ " where tv1.mandt ="+getClient()
			+ " and tv1.vgbel like '008%' group by vgbel"
			+ "  ),tempe as("
			+ " select vbeln,max(case "
			+ " when matnr like '00000000101%' or matnr like '00000000103%' or matnr like '00000000104%' or matnr like '00000000121%' or matnr like '00000000123%' then 'CFL'"
			+ " when matnr like '00000000102%' or matnr like '00000000122%' or matnr like '00000000132%' or matnr like '00000000137%' then 'LED'"
			+ " when matnr like '00000000105%' or matnr like '00000000107%' or matnr like '00000000109%' or matnr like '00000000110%' or matnr like '00000000111%' then '灯具'"
			+ " when matnr like '00000000106%' or matnr like '00000000109%' then '智能照明系统'"
			+ " when matnr like '0000000029998%' or matnr like '0000000029999%' then '其他'"
			+ " else '散件'"
			+ " end) as ty from lips ta where ta.mandt="+getClient()+" and vbeln like '008%' group by vbeln"
			+ " ),tempf as("
			+ " select vgbel,listagg(bstnk_vf,',') within group (order by vgbel) as vbeln, max(vbeln) as invoice,ktgrd,fkdat"
			+ " from (select distinct fa.vgbel,fb.bstnk_vf,fb.ktgrd,fb.fkdat,fb.vbeln from vbrp fa,vbrk fb where fa.mandt="+getClient()+" and fb.vbeln=fa.vbeln and fb.fkart='ZF2' and fa.vgbel like '008%') "
			+ " group by vgbel,ktgrd,fkdat"
			+ " ),tempg as("
			+ " select ea.kunnr,ea.sortl,eb.landx from kna1 ea,t005t eb where ea.mandt=eb.mandt and ea.land1 = eb.land1 and eb.spras='1' and ea.mandt="+getClient()
			+ " ),usr as("
			+ " select distinct pernr,ename from pa0001 where mandt="+getClient()
			+ " ),temph as("
			+ " select bukrs,kunnr,zrsm,zae,zaa,ua.ename as rsm,ub.ename as ae,uc.ename as aa,ha.zday from zfmt0007 ha "
			+ " left join usr ua on ua.pernr = ha.zrsm"
			+ " left join usr ub on ub.pernr = ha.zae"
			+ " left join usr uc on uc.pernr = ha.zaa"
			+ " where ha.mandt="+getClient()
			+ " ),tempi as("
			+ " select vgbel,listagg(vtext,',') within group (order by vgbel) as vtext"
			+ " from (select distinct fa.vgbel,fc.vtext from vbrp fa,vbrk fb,tvzbt fc where fa.mandt=fb.mandt and fa.vbeln=fb.vbeln and fb.mandt=fc.mandt and fb.zterm=fc.zterm and fa.mandt="+getClient()+" and fa.vgbel like '008%') "
			+ " group by vgbel"
			+ " ),tempk as("
			+ " select vbeln,listagg(zbelnr,',') within group(order by vbeln) as zbelnrs from"
			+ " (select distinct vbeln,zbelnr,waers from zfmt0004 where mandt="+getClient()+" and vbeln!=' ')"
			+ " group by vbeln"
			+ " ),templ as("
			+ " select vbeln,listagg(waers,',') within group(order by vbeln) as waers,sum(amt) as amtl,sum(zhc) as zhc from"
			+ " (select vbeln,waers,sum(zcamount-zhc) as amt,sum(zhc) as zhc from zfmt0004 na where mandt="+getClient()+" and (na.zrbl='B' or (na.zrbl='A' and not exists(select 1 from zfmt0004 te where te.zbelnr=na.zbelnr and te.zposnr_s=na.zposnr))) and na.zdrs='C' and na.zcirs='C' and vbeln!=' ' group by vbeln,waers)"
			+ " group by vbeln"
			+ " ),tempn as("
			+ " select vbeln,listagg(budat,'|') within group(order by budat) as budat,listagg(zcamount,'|') within group(order by budat) as zcamount from ("
			+ " select na.vbeln, nb.budat, sum(na.zcamount-na.zhc) as zcamount from zfmt0004 na,zfmt0003 nb where na.mandt=nb.mandt and (na.zrbl='B' or (na.zrbl='A' and not exists(select 1 from zfmt0004 te where te.zbelnr=na.zbelnr and te.zposnr_s=na.zposnr))) and na.zdrs='C' and na.zcirs='C' and na.zbelnr=nb.zbelnr and na.mandt="+getClient()+" and na.vbeln!=' ' group by na.vbeln, nb.budat,nb.zbelnr"
			+ " )group by vbeln"
			+ " ),tempo as("
			+ " select max(kursf) as kursf,rbeln from ce11000 where vrgar='F' group by rbeln "
			+ " )"
			+ " select a.mandt,a.vbeln,a.vkorg as bukrs,nvl(a.wadat_ist,'00000000') as zodate,"
			+ " nvl(c.amta,0) as zdamt,nvl(d.amtb,0) as ziamt,nvl(e.ty,' ') as maktx,nvl(d.sumb,0) as menge,"
			+ " nvl(c.werks,' ') as werks,nvl(f.vbeln,' ') as zinvoice,nvl(g.landx,' ') as znation,nvl(c.kunnr,' ') as kunnr,"
			+ " nvl(g.sortl,' ') as sortl,nvl(h.rsm,' ') as zrsm,nvl(h.ae,' ') as zae,nvl(h.aa,' ') as zaa,nvl(i.vtext,' ') as zeway,"
			+ " nvl(k.zbelnrs,' ') as zlbelnr,nvl(c.waerk,' ') as zlwaers,"
			+ " nvl(kv.zterm,' ') as zlterm,nvl(m.zvtext,' ') as zlvtext,nvl(l.amtl,'0') as zgamt,nvl(((case when nvl(d.amtb,0)=0 then nvl(c.amta,0) else nvl(d.amtb,0) end) + nvl(p.zcamt,0) - nvl(l.amtl, '0') - nvl(l.zhc, '0')),0) as zbamt,nvl(n.budat,' ') zrdate,"
			+ " nvl(n.zcamount,' ') as zramt,nvl(l.zhc,0) zhc,nvl(m.zdeadline,0) as zdays,"
			+ " nvl((case when (nvl(case when f.ktgrd='Z4' then nvl(b.zyjcq,'00000000') else f.fkdat end,'00000000')='00000000') then '' else to_char(to_date(nvl(case when f.ktgrd='Z4' then nvl(b.zyjcq,'00000000') else f.fkdat end,'00000000'),'yyyyMMdd')+nvl(m.zdeadline,0),'yyyyMMdd') end),'00000000') as zddate,"
			+ " nvl((case when (nvl(case when f.ktgrd='Z4' then nvl(b.zyjcq,'00000000') else f.fkdat end,'00000000')='00000000') then '' else to_char(to_date(nvl(case when f.ktgrd='Z4' then nvl(b.zyjcq,'00000000') else f.fkdat end,'00000000'),'yyyyMMdd')+nvl(m.zdeadline,0),'MM') end),'000000') as zdmonth,"
			+ " nvl(case when f.ktgrd='Z4' then nvl(b.zyjcq,'00000000') else f.fkdat end,'00000000') as fkdat,nvl(kk.klimk,0) as klimk,nvl(kk.cashc,' ') as cashc,nvl(o.kursf,0) as kursk,nvl(kv.vkbur,' ') as vkbur,decode(kv.kdgrp,' ',substr(g.sortl,0,decode(INSTR(g.sortl,'-',1,1),0,length(g.sortl),INSTR(g.sortl,'-',1,1)-1)) ,kv.kdgrp) as kdgrp "
			+ " from likp a"
			+ " left join zsdt0036 b on a.mandt = b.mandt and a.vbeln = b.vbeln"
			+ " left join tempc c on a.vbeln = c.vbeln"
			+ " left join tempd d on a.vbeln = d.vgbel"
			+ " left join tempe e on a.vbeln = e.vbeln"
			+ " left join tempf f on a.vbeln = f.vgbel"
			+ " left join tempg g on c.kunnr = g.kunnr"
			+ " left join temph h on a.vkorg = h.bukrs and c.kunnr = h.kunnr"
			+ " left join tempi i on a.vbeln = i.vgbel"
			+ " left join tempk k on a.vbeln = k.vbeln"
			+ " left join templ l on a.vbeln = l.vbeln"
			+ " left join tempn n on a.vbeln = n.vbeln"
			+ " left join knvv kv on c.kunnr=kv.kunnr and a.vkorg=kv.vkorg"
			+ " left join knkk kk on c.kunnr=kk.kunnr"
			+ " left join zfmt0008 m on kv.zterm=m.zterm"
			+ " left join tempo o on f.invoice = o.rbeln"
			+ " left join zfmt0017 p on a.vbeln=p.vbeln"
			+ " where a.mandt ="+getClient()
			+ " and a.vbeln like '008%'"
			+ " and kk.kkber='1000'"
			+ " )temp;"
			+ " c_row crs_wsh%rowtype;"
			+ " begin"
			+ " for c_row in crs_wsh loop"
			+ " update zfmt0016 e"
			+ " set" 
			+ " bukrs = c_row.bukrs,"
			+ " zodate= c_row.zodate,"
			+ " zdamt = c_row.zdamt,"
			+ " ziamt = c_row.ziamt,"
			+ " maktx = c_row.maktx,"
			+ " menge = c_row.menge,"
			+ " werks = c_row.werks,"
			+ " zinvoice = c_row.zinvoice,"
			+ " znation = c_row.znation,"
			+ " kunnr = c_row.kunnr,"
			+ " sortl = c_row.sortl,"
			+ " zrsm = c_row.zrsm,"
			+ " zae = c_row.zae,"
			+ " zaa = c_row.zaa,"
			+ " zeway = c_row.zeway,"
			+ " zlbelnr = c_row.zlbelnr,"
			+ " zlwaers = c_row.zlwaers,"
			+ " zlterm = c_row.zlterm,"
			+ " zlvtext = c_row.zlvtext,"
			+ " zgamt = c_row.zgamt,"
			+ " zbamt = case when c_row.zbamt<0 then 0 else c_row.zbamt end,"
			+ " zrdate = c_row.zrdate,"
			+ " zramt = c_row.zramt,"
			+ " zhc = c_row.zhc,"
			+ " zdays = c_row.zdays,"
			+ " zddate = c_row.zddate,"
			+ " zdmonth = c_row.zdmonth,"
			+ " fkdat = c_row.fkdat,"
			+ " meins = c_row.fkdat,"//暂用此字段包保存开票日期
			+ " klimk = c_row.klimk,"
			+ " fcurr = c_row.cashc,"
			+ " ukurs = c_row.kursk,"
			+ " vkbur = c_row.vkbur,"
			+ " zkdgp = c_row.kdgrp"
			+ " where e.vbeln = c_row.vbeln;"
			+ " end loop;"
			+ " end;";
		//更新手工调整的发票金额，同时更新余额
		String updateInvoiceAmtAndBlance = ""
			+"declare                                                             "
			+"  cursor crs_zkd is                                                 "
			+"    select vbeln, wrbtr                                             "
			+"      from (with base as (select a.vbeln, b.bschl, wrbtr            "
			+"                            from zfmt0016 a                         "
			+"                           inner join bsad b                        "
			+"                              on a.vbeln = b.zuonr                  "
			+"                          union all                                 "
			+"                          select a.vbeln, b.bschl, wrbtr            "
			+"                            from zfmt0016 a                         "
			+"                           inner join bsid b                        "
			+"                              on a.vbeln = b.zuonr)                 "
			+"             select vbeln,                                          "
			+"                    sum(case                                        "
			+"                          when bschl > '09' then                    "
			+"                           -wrbtr                                   "
			+"                          else                                      "
			+"                           wrbtr                                    "
			+"                        end) as wrbtr                               "
			+"               from base                                            "
			+"              group by vbeln) temp;                                 "
            +"                                                                    "
            +"                                                                    "
			+"  c_row crs_zkd%rowtype;                                            "
			+"begin                                                               "
			+"  for c_row in crs_zkd loop                                         "
			+"    update zfmt0016 e                                               "
			+"       set e.ziamt = e.ziamt + c_row.wrbtr,                         "
			+"           e.zbamt = case                                           "
			+"                       when e.ziamt + c_row.wrbtr - e.zgamt > 0 then"
			+"                        e.ziamt + c_row.wrbtr - e.zgamt             "
			+"                       else                                         "
			+"                        0                                           "
			+"                     end                                            "
			+"     where e.vbeln = c_row.vbeln;                                   "
			+"  end loop;                                                         "
			+"end;                                                                ";
		//更新客户保额,预收未清,未清发货额字段
		String updateCustBeAndWq = ""
			+"declare												  "
			+"  cursor crs_zkd is                                     "
			+"    select bukrs,kunnr,zbemt,zwqmt,zysmt                "
			+"      from (                                            "
			+"   with cust as                                                            "
			+"	 (select distinct bukrs, kunnr from zfmt0016 where kunnr != ' '),            "
			+"	 yswq as                                                                     "
			+"	  (select d.bukrs,                                                           "
			+"	          d.kunnr,                                                           "
			+"	          nvl(sum(case                                                       "
			+"	                    when bschl > '09' then                                   "
			+"	                     -dmbtr                                                  "
			+"	                    else                                                     "
			+"	                     dmbtr                                                   "
			+"	                  end),                                                      "
			+"	              0) as unclear                                                  "
			+"	     from bsid d                                                             "
			+"	    where umskz = 'A'                                                        "
			+"	    group by d.bukrs, d.kunnr),                                              "
			+"	 curr as                                                                     "
			+"	  (select fcurr, ukurs                                                       "
			+"	     from (select row_number() over(partition by fcurr order by gdatu) as rn,"
			+"	                  fcurr,                                                     "
			+"	                  ukurs                                                      "
			+"	             from tcurr                                                      "
			+"	            where kurst = 'Z'                                                "
			+"	              and tcurr = 'CNY')                                             "
			+"	    where rn = 1)                                                            "
			+"	 select a.bukrs,                                                             "
			+"	        a.kunnr,                                                             "
			+"	        nvl(case                                                             "
			+"	              when b.waers = 'CNY' then                                      "
			+"	               b.zbemt                                                       "
			+"	              else                                                           "
			+"	               nvl(b.zbemt, 0) * nvl(cr.ukurs, 1)                            "
			+"	            end,                                                             "
			+"	            0) as zbemt,                                                     "
			+"	        nvl(c.olikw, 0) as zwqmt,                                            "
			+"	        nvl(d.unclear, 0) as zysmt                                           "
			+"	   from cust a                                                               "
			+"	   left join zfmt0016_be b                                                   "
			+"	     on a.kunnr = b.kunnr                                                    "
			+"	   left join curr cr                                                         "
			+"	     on b.waers = cr.fcurr                                                   "
			+"	   left join s067 c                                                          "
			+"	     on a.kunnr = c.knkli                                                    "
			+"	    and c.kkber = 1000                                                       "
			+"	   left join yswq d                                                          "
			+"	     on a.kunnr = d.kunnr                                                    "
			+"	    and a.bukrs = d.bukrs                                                    "
			+"            ) temp;                                     "
            +"                                                        "
			+"  c_row crs_zkd%rowtype;                                "
			+"begin                                                   "
			+"  for c_row in crs_zkd loop                             "
			+"    update zfmt0016 e                                   "
			+"       set e.zbemt = c_row.zbemt,                       "
			+"           e.zwqmt = c_row.zwqmt,                       "
			+"           e.zysmt = c_row.zysmt                        "
			+"     where e.bukrs = c_row.bukrs                        "
			+"       and e.kunnr = c_row.kunnr;                       "
			+"  end loop;                                             "
			+"end;                                                    ";
		//更新已开票但预计船期为空的记录
		String updateEmptyFkdate = ""
			+"declare                                                        "
			+"  cursor crs_zkd is                                            "
			+"    select vbeln, fkdat                                        "
			+"      from (with tempf as (select vgbel, ktgrd, fkdat          "
			+"                             from (select distinct fa.vgbel,   "
			+"                                                   fa.vbeln,   "
			+"                                                   fb.ktgrd,   "
			+"                                                   fb.fkdat    "
			+"                                     from vbrp fa, vbrk fb     "
			+"                                    where fb.vbeln = fa.vbeln  "
			+"                                      and fb.fkart = 'ZF2'     "
			+"                                      and fa.vgbel like '008%')"
			+"                            group by vgbel, ktgrd, fkdat)      "
			+"             select a.vbeln, f.fkdat                           "
			+"               from zfmt0016 a                                 "
			+"              inner join tempf f                               "
			+"                 on a.vbeln = f.vgbel                          "
			+"              where a.fkdat = '00000000') temp;                "
			+"  c_row crs_zkd%rowtype;                                       "
			+"begin                                                          "
			+"  for c_row in crs_zkd loop                                    "
			+"    update zfmt0016 e                                          "
			+"       set e.fkdat = c_row.fkdat                               "
			+"     where e.vbeln = c_row.vbeln;                              "
			+"  end loop;                                                    "
			+"end;                                                           ";
		//删除今天客户未收汇余额汇总表记录
		String deleteTodayCustSummary = "delete from zfmt0018 where zdate = to_char(sysdate,'yyyyMMdd')";
		//插入今天客户未收汇余额信息
		String insertTodayCustSummary = "insert into zfmt0018(mandt,kunnr,zdate,zbamt)"
			+ " select "+getClient()+",kunnr,to_char(sysdate,'yyyyMMdd'),sum(nvl(zbamt,0)*nvl(ukurs,0)) from zfmt0016 group by kunnr";
		//更新未收汇表客户欠款趋势
		String updateCustStatus = ""
			+ "declare "
			+ " cursor crs_cust is"
			+ " select kunnr,status from("
			+ " with hisory as("
			+ " select kunnr,avg(zbamt) as his from zfmt0018 where zdate between to_char(sysdate-8,'yyyyMMdd') and to_char(sysdate-1,'yyyyMMdd') group by kunnr"
			+ " ),today as("
			+ " select kunnr,zbamt as tod from zfmt0018 where zdate=to_char(sysdate,'yyyyMMdd')"
			+ " ),cust as("
			+ " select distinct kunnr from zfmt0018"
			+ " )"
			+ " select a.kunnr,case when nvl(b.tod,0)>nvl(c.his,0) then '上升' when nvl(b.tod,0)=nvl(c.his,0) then '持平' else '下降' end status from cust a"
		    + " left join today  b on a.kunnr = b.kunnr"
		    + " left join hisory c on a.kunnr = c.kunnr"
		    + " );"
			+ " c_row crs_cust%rowtype;"
			+ " begin "
			+ " for c_row in crs_cust loop"
			+ " update zfmt0016"
			+ " set ztendency = c_row.status"
			+ " where kunnr = c_row.kunnr;"
			+ " end loop;"
			+ " end;";
		
	 	//三星客户起算日期=发货日期+4天
		String updateSumsung = ""
			+"declare                                                                        "
			+"  cursor crs_zkd is                                                            "
			+"    select vbeln, fkdat                                                        "
			+"      from (select a.vbeln,                                                    "
			+"                   case                                                        "
			+"                     when a.zodate = '00000000' then                           "
			+"                      '00000000'                                               "
			+"                     else                                                      "
			+"                      to_char((to_date(a.zodate, 'yyyyMMdd') + 4), 'yyyyMMdd') "
			+"                   end as fkdat                                                "
			+"              from zfmt0016 a                                                  "
			+"             where a.kunnr in ('0850010000', '0850050000', '0011000238')) temp;"
			+"  c_row crs_zkd%rowtype;                                                       "
			+"begin                                                                          "
			+"  for c_row in crs_zkd loop                                                    "
			+"    update zfmt0016 e                                                          "
			+"       set e.fkdat = c_row.fkdat                                               "
			+"     where e.vbeln = c_row.vbeln;                                              "
			+"  end loop;                                                                    "
			+"end;                                                                           ";
		//V客户组除了VC-3,VC-4,VE-AT外,起算日=开票日期+7天
		String updateVGroupCustomer = ""
			+"declare                                                                      "
			+"  cursor crs_zkd is                                                          "
			+"    select vbeln, fkdat                                                      "
			+"      from (select a.vbeln,                                                  "
			+"                   case                                                      "
			+"                     when a.fkdat = '00000000' then                          "
			+"                      '00000000'                                             "
			+"                     else                                                    "
			+"                      to_char((to_date(a.fkdat, 'yyyyMMdd') + 7), 'yyyyMMdd')"
			+"                   end as fkdat                                              "
			+"              from zfmt0016 a                                                "
			+"             where a.sortl not in ('VC-3', 'VC-4', 'VE-AT')                  "
			+"               and a.zkdgp = 'V') temp;                                      "
			+"  c_row crs_zkd%rowtype;                                                     "
			+"begin                                                                        "
			+"  for c_row in crs_zkd loop                                                  "
			+"    update zfmt0016 e                                                        "
			+"       set e.fkdat = c_row.fkdat                                             "
			+"     where e.vbeln = c_row.vbeln;                                            "
			+"  end loop;                                                                  "
			+"end;                                                                         ";
			
		//C045及C081起算日+12天
		String updateSpecialCust = ""
			+"declare                                                                       "
			+"  cursor crs_zkd is                                                           "
			+"    select vbeln, fkdat                                                       "
			+"      from (select a.vbeln,                                                   "
			+"                   case                                                       "
			+"                     when a.fkdat = '00000000' then                           "
			+"                      '00000000'                                              "
			+"                     else                                                     "
			+"                      to_char((to_date(a.fkdat, 'yyyyMMdd') + 12), 'yyyyMMdd')"
			+"                   end as fkdat                                               "
			+"              from zfmt0016 a                                                 "
			+"             where a.sortl in ('C045', 'C081')) temp;                         "
			+"  c_row crs_zkd%rowtype;                                                      "
			+"begin                                                                         "
			+"  for c_row in crs_zkd loop                                                   "
			+"    update zfmt0016 e                                                         "
			+"       set e.fkdat = c_row.fkdat                                              "
			+"     where e.vbeln = c_row.vbeln;                                             "
			+"  end loop;                                                                   "
			+"end;                                                                          "
			;
		
		//L开头客户起算日+30天
		String updateLCustomer = ""
			+"declare                                                                       "
			+"  cursor crs_zkd is                                                           "
			+"    select vbeln, fkdat                                                       "
			+"      from (select a.vbeln,                                                   "
			+"                   case                                                       "
			+"                     when a.fkdat = '00000000' then                           "
			+"                      '00000000'                                              "
			+"                     else                                                     "
			+"                      to_char((to_date(a.fkdat, 'yyyyMMdd') + 30), 'yyyyMMdd')"
			+"                   end as fkdat                                               "
			+"              from zfmt0016 a                                                 "
			+"             where a.sortl like 'L%') temp;                                   "
			+"  c_row crs_zkd%rowtype;                                                      "
			+"begin                                                                         "
			+"  for c_row in crs_zkd loop                                                   "
			+"    update zfmt0016 e                                                         "
			+"       set e.fkdat = c_row.fkdat                                              "
			+"     where e.vbeln = c_row.vbeln;                                             "
			+"  end loop;                                                                   "
			+"end;                                                                          ";
		
		//付款条件代码在C201~C204区间内及C207的客户，起算日放宽至次月1号,期限-1天
		String specialPayConditionUpdate = ""
			+"declare                                                                   "
			+"  cursor crs_zkd is                                                       "
			+"    select vbeln, fkdat, zdays                                            "
			+"      from (select a.vbeln,                                               "
			+"                   case                                                   "
			+"                     when a.fkdat = '00000000' then                       "
			+"                      '00000000'                                          "
			+"                     else                                                 "
			+"                      to_char(add_months(to_date(a.fkdat, 'yyyyMMdd'), 1),"
			+"                              'yyyyMM') || '01'                           "
			+"                   end as fkdat,                                          "
			+"                   nvl(a.zdays, 0) - 1 as zdays                           "
			+"              from zfmt0016 a                                             "
			+"             where a.zlterm in ('C201','C202','C203','C204','C207')) temp;"
			+"  c_row crs_zkd%rowtype;                                                  "
			+"begin                                                                     "
			+"  for c_row in crs_zkd loop                                               "
			+"    update zfmt0016 e                                                     "
			+"       set e.fkdat = c_row.fkdat, e.zdays = c_row.zdays                   "
			+"     where e.vbeln = c_row.vbeln                                          "
			+"       and e.zlterm in ('C201','C202','C203','C204','C207');              "
			+"  end loop;                                                               "
			+"end;                                                                      ";
		
		//更新到期日\到期月份\放宽期限字段
		String updateZkdateAndZddate = ""
			+"declare                                                                       "
			+"  cursor crs_zkd is                                                           "
			+"    select vbeln, zkdate, zddate, zdmonth                                     "
			+"      from (select a.vbeln,                                                   "
			+"                   a.fkdat,                                                   "
			+"                   a.zdays,                                                   "
			+"                   case                                                       "
			+"                     when a.fkdat = '00000000' then                           "
			+"                      '00000000'                                              "
			+"                     else                                                     "
			+"                      to_char((add_months(to_date(a.fkdat, 'yyyyMMdd'),       "
			+"                                          floor(nvl(trim(a.zdays), 0) / 30)) +"
			+"                              nvl(trim(a.zdays), 0) -                         "
			+"                              floor(nvl(trim(a.zdays), 0) / 30) * 30),        "
			+"                              'yyyyMMdd')                                     "
			+"                   end as zddate,                                             "
			+"                   case                                                       "
			+"                     when a.fkdat = '00000000' then                           "
			+"                      ' '                                                     "
			+"                     else                                                     "
			+"                      to_char((add_months(to_date(a.fkdat, 'yyyyMMdd'),       "
			+"                                          floor(nvl(trim(a.zdays), 0) / 30)) +"
			+"                              nvl(trim(a.zdays), 0) -                         "
			+"                              floor(nvl(trim(a.zdays), 0) / 30) * 30),        "
			+"                              'MM')                                           "
			+"                   end as zdmonth,                                            "
			+"                   case                                                       "
			+"                     when a.fkdat = '00000000' then                           "
			+"                      '00000000'                                              "
			+"                     else                                                     "
			+"                      to_char((add_months(to_date(a.fkdat, 'yyyyMMdd'),       "
			+"                                          floor((nvl(trim(a.zdays), 0) +      "
			+"                                                nvl(trim(b.zday), 0)) / 30)) +"
			+"                              nvl(trim(a.zdays), 0) + nvl(trim(b.zday), 0) -  "
			+"                              floor((nvl(trim(a.zdays), 0) +                  "
			+"                                     nvl(trim(b.zday), 0)) / 30) * 30),       "
			+"                              'yyyyMMdd')                                     "
			+"                   end as zkdate                                              "
			+"              from zfmt0016 a                                                 "
			+"              left join zfmt0007 b                                            "
			+"                on a.kunnr = b.kunnr                                          "
			+"               and a.bukrs = b.bukrs                                          "
			+"             where a.zlterm != 'C205') temp;                                  "
			+"  c_row crs_zkd%rowtype;                                                      "
			+"begin                                                                         "
			+"  for c_row in crs_zkd loop                                                   "
			+"    update zfmt0016 e                                                         "
			+"       set e.zddate  = c_row.zddate,                                          "
			+"           e.zkdate  = c_row.zkdate,                                          "
			+"           e.zdmonth = c_row.zdmonth                                          "
			+"     where e.vbeln = c_row.vbeln;                                             "
			+"  end loop;                                                                   "
			+"end;                                                                          ";

		
		//更新付款条件为C205的到期日为开票当月最后一天
		String updateC205PayCondition = ""
			+"declare                                                                      "
			+"  cursor crs_zkd is                                                          "
			+"    select vbeln, zddate, zdmonth, zkdate                                    "
			+"      from (select a.vbeln,                                                  "
			+"                   a.fkdat,                                                  "
			+"                   case                                                      "
			+"                     when a.fkdat = '00000000' then                          "
			+"                      '00000000'                                             "
			+"                     else                                                    "
			+"                      to_char(to_date(to_char(add_months(to_date(a.fkdat,    "
			+"                                                                 'yyyyMMdd'),"
			+"                                                         1),                 "
			+"                                              'yyyyMM') || '01',             "
			+"                                      'yyyyMMdd') - 1,                       "
			+"                              'yyyyMMdd')                                    "
			+"                   end as zddate,                                            "
			+"                   case                                                      "
			+"                     when a.zddate = '00000000' then                         "
			+"                      ' '                                                    "
			+"                     else                                                    "
			+"                      to_char(to_date(a.fkdat, 'yyyyMMdd'), 'MM')            "
			+"                   end as zdmonth,                                           "
			+"                   case                                                      "
			+"                     when a.fkdat = '00000000' then                          "
			+"                      '00000000'                                             "
			+"                     else                                                    "
			+"                      to_char(to_date(to_char(add_months(to_date(a.fkdat,    "
			+"                                                                 'yyyyMMdd'),"
			+"                                                         1),                 "
			+"                                              'yyyyMM') || '01',             "
			+"                                      'yyyyMMdd') - 1 + nvl(trim(b.zday), 0),"
			+"                              'yyyyMMdd')                                    "
			+"                   end as zkdate                                             "
			+"              from zfmt0016 a                                                "
			+"              left join zfmt0007 b                                           "
			+"                on a.kunnr = b.kunnr                                         "
			+"               and a.bukrs = b.bukrs                                         "
			+"             where a.zlterm = 'C205') temp;                                  "
			+"  c_row crs_zkd%rowtype;                                                     "
			+"begin                                                                        "
			+"  for c_row in crs_zkd loop                                                  "
			+"    update zfmt0016 e                                                        "
			+"       set e.zddate  = c_row.zddate,                                         "
			+"           e.zkdate  = c_row.zkdate,                                         "
			+"           e.zdmonth = c_row.zdmonth                                         "
			+"     where e.vbeln = c_row.vbeln;                                            "
			+"  end loop;                                                                  "
			+"end;                                                                         ";
		
		//7200公司默认本位币为美元,取销售合同对应的财务汇率
		String update7200BukrsUkurs = ""
			+"update zfmt0016 sa                                                                          "
			+"set ukurs = (select ukurs from (                                                            "
			+"select vbeln,ukurs from (                                                                   "
			+"with sellInfo as(                                                                           "
			+"select vbeln,to_char(ADD_MONTHS(to_date(erdat,'yyyyMMdd'),-1),'yyyyMM') da,waerk from (     "
			+"select a.vbeln,c.erdat,c.waerk,row_number() over(partition by a.vbeln order by c.erdat desc)" 
			+"rn from zfmt0016 a                                                                          "
			+"inner join lips b on a.vbeln=b.vbeln                                                        "
			+"inner join vbak c on b.vgbel=c.vbeln                                                        "
			+"where a.bukrs='7200' )                                                                      "
			+"where rn=1                                                                                  "
			+"),curr as(                                                                                  "
			+"select to_char(to_date(to_char(99999999-gdatu),'yyyyMMdd'),'yyyyMM') da,ukurs,fcurr         "
			+"from tcurr where kurst='Z' and tcurr='CNY' and fcurr='USD'                                  "
			+")                                                                                           "
			+"select ta.vbeln,tb.ukurs from sellInfo ta                                                   "
			+"inner join curr tb on ta.waerk=tb.fcurr and ta.da=tb.da                                     "
			+"and ta.waerk != 'CNY')                                                                      "
			+")temp where sa.vbeln=temp.vbeln                                                             "
			+")                                                                                           "
			+"where bukrs='7200'                                                                          ";
		
		//YE,YM,YN 三个客户付款条件为C208时起算日期及开票日期取财务开票日期
		String updateStartDateForSpecialYCustomer = ""
			+"declare                                                "
			+"  cursor crs_zkd is                                    "
			+"    select distinct fa.vgbel, fb.fkdat, fc.sortl       "
			+"      from vbrp fa, vbrk fb, zfmt0016 fc               "
			+"     where fb.vbeln = fa.vbeln                         "
			+"       and fa.vgbel = fc.vbeln                         "
			+"       and fc.zlterm = 'C208'                          "
			+"       and fc.sortl in ('YE', 'YM', 'YN');             "
			+"  c_row crs_zkd%rowtype;                               "
			+"begin                                                  "
			+"  for c_row in crs_zkd loop                            "
			+"    update zfmt0016 e                                  "
			+"       set e.fkdat = c_row.fkdat, e.meins = c_row.fkdat"
			+"     where e.vbeln = c_row.vgbel;                      "
			+"  end loop;                                            "
			+"end;                                                   ";
		
		//Y客户更新放宽期限至5号(如果当前期限日期在5号前,则更新至当月5号,否则更新至次月5号)
		String updateFkdatForYCustomer = ""
			+"declare                                                                         "
			+"  cursor crs_zkd is                                                             "
			+"    select vbeln,                                                               "
			+"           fkdat,                                                               "
			+"           zddate,                                                              "
			+"           case                                                                 "
			+"             when zddate = '00000000' then                                      "
			+"              ' '                                                               "
			+"             else                                                               "
			+"              to_char((to_date(zddate, 'yyyyMMdd')), 'MM')                      "
			+"           end as zdmonth,                                                      "
			+"           case                                                                 "
			+"             when zddate = '00000000' then                                      "
			+"              '00000000'                                                        "
			+"             else                                                               "
			+"              to_char((to_date(zddate, 'yyyyMMdd') + nvl(trim(zday), 0)),       "
			+"                      'yyyyMMdd')                                               "
			+"           end as zkdate                                                        "
			+"      from (select a.vbeln,                                                     "
			+"                   a.fkdat,                                                     "
			+"                   case                                                         "
			+"                     when a.zddate = '00000000' then                            "
			+"                      '00000000'                                                "
			+"                     when substr(a.zddate, 7) <= '05' then                      "
			+"                      substr(a.zddate, 0, 6) || '05'                            "
			+"                     else                                                       "
			+"                      to_char(add_months(to_date(substr(a.zddate, 0, 6) || '05',"
			+"                                                 'yyyyMMdd'),                   "
			+"                                         1),                                    "
			+"                              'yyyyMMdd')                                       "
			+"                   end as zddate,                                               "
			+"                   b.zday                                                       "
			+"              from (select vbeln,                                               "
			+"                           fkdat,                                               "
			+"                           zdays,                                               "
			+"                           kunnr,                                               "
			+"                           bukrs,                                               "
			+"                           case                                                 "
			+"                             when fkdat = '00000000' then                       "
			+"                              '00000000'                                        "
			+"                             else                                               "
			+"                              to_char((to_date(fkdat, 'yyyyMMdd') +             "
			+"                                      nvl(trim(zdays), 0)),                     "
			+"                                      'yyyyMMdd')                               "
			+"                           end as zddate                                        "
			+"                      from zfmt0016 a                                           "
			+"                     where zlterm != 'C205'                                     "
			+"                       and zkdgp = 'Y') a                                       "
			+"              left join zfmt0007 b                                              "
			+"                on a.kunnr = b.kunnr                                            "
			+"               and a.bukrs = b.bukrs);                                          "
			+"  c_row crs_zkd%rowtype;                                                        "
			+"begin                                                                           "
			+"  for c_row in crs_zkd loop                                                     "
			+"    update zfmt0016 e                                                           "
			+"       set e.zddate  = c_row.zddate,                                            "
			+"           e.zkdate  = c_row.zkdate,                                            "
			+"           e.zdmonth = c_row.zdmonth                                            "
			+"     where e.vbeln = c_row.vbeln;                                               "
			+"  end loop;                                                                     "
			+"end;                                                                            ";
		//@formatter:on
		try {
			execSqlNoException(updateZhc);
			execSqlNoException(delete);
			execSqlNoException(insertA);
			execSqlNoException(insertB);
			execSqlNoException(update);
			execSqlNoException(updateInvoiceAmtAndBlance);
			execSqlNoException(updateCustBeAndWq);
			execSqlNoException(updateEmptyFkdate);
			execSqlNoException(deleteTodayCustSummary);
			execSqlNoException(insertTodayCustSummary);
			execSqlNoException(updateCustStatus);
			execSqlNoException(updateSumsung);
			execSqlNoException(updateVGroupCustomer);
			execSqlNoException(updateSpecialCust);
			execSqlNoException(updateLCustomer);
			execSqlNoException(specialPayConditionUpdate);
			execSqlNoException(updateZkdateAndZddate);
			execSqlNoException(updateC205PayCondition);
			execSqlNoException(update7200BukrsUkurs);
			execSqlNoException(updateStartDateForSpecialYCustomer);
			execSqlNoException(updateFkdatForYCustomer);
//			saveZfmt0016LogWeekly();
//			saveZfmt0016LogMonthly();
		} catch (Exception e) {
			errorMsg = e.getMessage();
		}
		return errorMsg;
	}
	
	public void execSqlNoException(String sql){
		try {
			execSql(sql);
		} catch (Exception e) {
			logger.error("execSqlNoException sql error:"+e.getMessage());
			logger.error("====ERROR SQL====");
			logger.error(sql);
			logger.error("====ERROR SQL====");
		}
	}
	
	@Override
	public void saveZfmt0016LogWeekly() throws Exception{
		String weekDay = DateUtils.getWeekOfDate(new Date());
		if("星期四".equals(weekDay)){
			saveZfmt0016Log(0);
		}
	}
	
	@Override
	public void saveZfmt0016LogMonthly() throws Exception{
		if(DateUtils.isLastDayOfMonth(new Date())){
			saveZfmt0016Log(1);
		}
	}
	
	
	/**
	 * 保存未收汇副本信息
	 * @param type(0每周保存,1每月底保存)
	 * @throws LdxPortalException
	 */
	public void saveZfmt0016Log(int type) throws Exception{
		String dateString = DateUtils.formatDate(new Date(),type==0?"yyyyMMdd":"yyyyMM");
		String delete = "delete from zfmt0016_log where zzbdat = '"+dateString+"'";
		String insert = "INSERT INTO ZFMT0016_LOG("
		   +"madat,                             "
		   +"vbeln,                             "
		   +"zzbdat,                            "
		   +"bukrs,                             "
		   +"zodate,                            "
		   +"zdamt,                             "
		   +"ziamt,                             "
		   +"maktx,                             "
		   +"menge,                             "
		   +"werks,                             "
		   +"zinvoice,                          "
		   +"znation,                           "
		   +"kunnr,                             "
		   +"sortl,                             "
		   +"zrsm,                              "
		   +"zae,                               "
		   +"zaa,                               "
		   +"zeway,                             "
		   +"zlbelnr,                           "
		   +"zlwaers,                           "
		   +"zlterm,                            "
		   +"zlvtext,                           "
		   +"zgamt,                             "
		   +"zbamt,                             "
		   +"zrdate,                            "
		   +"zramt,                             "
		   +"zhc,                               "
		   +"zdays,                             "
		   +"zddate,                            "
		   +"zdmonth,                           "
		   +"waers,                             "
		   +"meins,                             "
		   +"ukurs,                             "
		   +"klimk,                             "
		   +"fcurr,                             "
		   +"ztendency,                         "
		   +"zunamt,                            "
		   +"fkdat,                             "
		   +"vkbur,                             "
		   +"kdgrp,                             "
		   +"zkdgp,                             "
		   +"zkdate,                            "
		   +"zbemt,                             "
		   +"zysmt,                             "
		   +"zwqmt)                             "
		   +"select madat,                      "
		   +"      vbeln,                       "
		   +       dateString+",                "
		   +"      bukrs,                       "
		   +"      zodate,                      "
		   +"      zdamt,                       "
		   +"      ziamt,                       "
		   +"      maktx,                       "
		   +"      menge,                       "
		   +"      werks,                       "
		   +"      zinvoice,                    "
		   +"      znation,                     "
		   +"      kunnr,                       "
		   +"      sortl,                       "
		   +"      zrsm,                        "
		   +"      zae,                         "
		   +"      zaa,                         "
		   +"      zeway,                       "
		   +"      zlbelnr,                     "
		   +"      zlwaers,                     "
		   +"      zlterm,                      "
		   +"      zlvtext,                     "
		   +"      zgamt,                       "
		   +"      zbamt,                       "
		   +"      zrdate,                      "
		   +"      zramt,                       "
		   +"      zhc,                         "
		   +"      zdays,                       "
		   +"      zddate,                      "
		   +"      zdmonth,                     "
		   +"      waers,                       "
		   +"      meins,                       "
		   +"      ukurs,                       "
		   +"      klimk,                       "
		   +"      fcurr,                       "
		   +"      nvl(ztendency,' '),          "
		   +"      nvl(zunamt,0),               "
		   +"      nvl(fkdat,'00000000'),       "
		   +"      nvl(vkbur,' '),              "
		   +"      nvl(kdgrp,' '),              "
		   +"      nvl(zkdgp,' '),              "
		   +"      nvl(zkdate,'00000000'),      "
		   +"      nvl(zbemt,0),                "
		   +"      nvl(zysmt,0),                "
		   +"      nvl(zwqmt,0)                 "
		   +" from zfmt0016                     ";
		execSql(delete);
		execSql(insert);
	}
	
	
	/**
	 * 定期发送未收汇报表
	 * 
	 * @throws Exception
	 */
	@Override
	public void sendNoneReceivedReport() throws Exception {
		List<Object> result = findNoneRecList();
		InputStream is = this.getClass().getResourceAsStream("noneReceiveProceeds.xls");
		HSSFWorkbook workbook = new HSSFWorkbook(new POIFSFileSystem(is));
		int resize = result.size();
		HSSFSheet sheet = workbook.getSheetAt(0);
		HSSFRow row = null;
		HSSFCell cell = null;
		// 创建样式数组
		HSSFDataFormat dateformat = workbook.createDataFormat();
		HSSFCellStyle stringStyle = getStyleForStringCell(workbook.createCellStyle());
		HSSFCellStyle dateStyle = getStyleForDateCell(workbook.createCellStyle(),dateformat);
		HSSFCellStyle currencyStyle = getStyleForNumCell(workbook.createCellStyle());
		Object[] objects;
		for(int i=0;i<resize;i++){
			objects = (Object[])result.get(i);
			int j = 0;
			row = sheet.createRow(1 + i);
			//1外向交货单
			cell = row.createCell(j);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellStyle(stringStyle);
			cell.setCellValue(StringUtils.nullToString(objects[0]));
			//2.出口单位
			j++;
			cell = row.createCell(j);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellStyle(stringStyle);
			cell.setCellValue(StringUtils.nullToString(objects[1]));
			//3.申报日期
			j++;
			cell = row.createCell(j);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellStyle(dateStyle);
			if(null!=getDateValue(objects[2]))
				cell.setCellValue(getDateValue(objects[2]));
			//4.出运日期
			j++;
			cell = row.createCell(j);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellStyle(dateStyle);
			if(null!=getDateValue(objects[3]))
				cell.setCellValue(getDateValue(objects[3]));
			//4.0开票日期  add by HeShi 20140626
			j++;
			cell = row.createCell(j);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellStyle(dateStyle);
			if(null!=getDateValue(objects[50]))
				cell.setCellValue(getDateValue(objects[50]));
			//4.1起算日期  add by HeShi 20140314
			j++;
			cell = row.createCell(j);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellStyle(dateStyle);
			if(null!=getDateValue(objects[38]))
				cell.setCellValue(getDateValue(objects[38]));
			//5.报关金额
			j++;
			cell = row.createCell(j);
			cell.setCellStyle(currencyStyle);
			cell.setCellValue(getCurrencyValue(objects[4]));
			//6.发货金额
			j++;
			cell = row.createCell(j);
			cell.setCellStyle(currencyStyle);
			cell.setCellValue(getCurrencyValue(objects[5]));
			//7.开票金额
			Double invoiceAmt = getCurrencyValue(objects[6]);
			j++;
			cell = row.createCell(j);
			cell.setCellStyle(currencyStyle);
			cell.setCellValue(invoiceAmt);
			//8.品名
			j++;
			cell = row.createCell(j);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellStyle(stringStyle);
			cell.setCellValue(StringUtils.nullToString(objects[7]));
			//9.报关数量
			j++;
			cell = row.createCell(j);
			cell.setCellStyle(currencyStyle);
			cell.setCellValue(getCurrencyValue(objects[8]));
			//10实际数量
			j++;
			cell = row.createCell(j);
			cell.setCellStyle(currencyStyle);
			cell.setCellValue(getCurrencyValue(objects[9]));
			//11折算数量
			j++;
			cell = row.createCell(j);
			cell.setCellStyle(currencyStyle);
			cell.setCellValue(getCurrencyValue(objects[10]));
			//12生产工厂
			j++;
			cell = row.createCell(j);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellStyle(stringStyle);
			cell.setCellValue(StringUtils.nullToString(objects[11]));
			//13发票号（外合同）
			j++;
			cell = row.createCell(j);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellStyle(stringStyle);
			cell.setCellValue(StringUtils.nullToString(objects[12]));
			//14出口国家
			j++;
			cell = row.createCell(j);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellStyle(stringStyle);
			cell.setCellValue(StringUtils.nullToString(objects[13]));
			//15客户ID
			j++;
			cell = row.createCell(j);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellStyle(stringStyle);
			cell.setCellValue(StringUtils.nullToString(objects[14]));
			//16客户简称
			j++;
			cell = row.createCell(j);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellStyle(stringStyle);
			cell.setCellValue(StringUtils.nullToString(objects[15]));
			//16.1销售部门
			j++;
			cell = row.createCell(j);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellStyle(stringStyle);
			cell.setCellValue(StringUtils.nullToString(objects[49]));
			//17RSM
			j++;
			cell = row.createCell(j);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellStyle(stringStyle);
			cell.setCellValue(StringUtils.nullToString(objects[16]));
			//18AE
			j++;
			cell = row.createCell(j);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellStyle(stringStyle);
			cell.setCellValue(StringUtils.nullToString(objects[17]));
			//19AA
			j++;
			cell = row.createCell(j);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellStyle(stringStyle);
			cell.setCellValue(StringUtils.nullToString(objects[18]));
			//20收汇方式
			j++;
			cell = row.createCell(j);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellStyle(stringStyle);
			cell.setCellValue(StringUtils.nullToString(objects[19]));
			//21报关海运费
			j++;
			cell = row.createCell(j);
			cell.setCellStyle(currencyStyle);
			cell.setCellValue(getCurrencyValue(objects[20]));
			//22报关单号
			j++;
			cell = row.createCell(j);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellStyle(stringStyle);
			cell.setCellValue(StringUtils.nullToString(objects[21]));
			//23柜型
			j++;
			cell = row.createCell(j);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellStyle(stringStyle);
			cell.setCellValue(StringUtils.nullToString(objects[22]));
			//24收款凭证号
			j++;
			cell = row.createCell(j);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellStyle(stringStyle);
			cell.setCellValue(StringUtils.nullToString(objects[23]));
			//25币种
			j++;
			cell = row.createCell(j);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellStyle(stringStyle);
			cell.setCellValue(StringUtils.nullToString(objects[24]));
			//26付款条件代码
			j++;
			cell = row.createCell(j);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellStyle(stringStyle);
			cell.setCellValue(StringUtils.nullToString(objects[25]));
			//27付款条件
			j++;
			cell = row.createCell(j);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellStyle(stringStyle);
			cell.setCellValue(StringUtils.nullToString(objects[26]));
			//28已收金额
			j++;
			cell = row.createCell(j);
			cell.setCellStyle(currencyStyle);
			cell.setCellValue(getCurrencyValue(objects[27]));
			//29应收款余额
			j++;
			cell = row.createCell(j);
			cell.setCellStyle(currencyStyle);
			Double receiveBlance = getCurrencyValue(objects[28]).compareTo(0D)>0?getCurrencyValue(objects[28]):0D;
			cell.setCellValue(receiveBlance);
			//最后一次到款日期
			Date finalRecDate;
			//30第一次到款日期
			j++;
			cell = row.createCell(j);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellStyle(dateStyle);
			Date dateFirst = getReceiveDateIndex(objects[29],0);
			finalRecDate = dateFirst;
			if(null!=dateFirst)
				cell.setCellValue(dateFirst);
			//31第一次到款金额
			j++;
			cell = row.createCell(j);
			cell.setCellStyle(currencyStyle);
			Double amtFirst = getReceiveAmtIndex(objects[30],0);
			if(amtFirst!=null)
				cell.setCellValue(amtFirst);
			//32第二次到款日期
			j++;
			cell = row.createCell(j);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellStyle(dateStyle);
			Date dateSecond = getReceiveDateIndex(objects[29],1);
			if(null!=dateSecond){
				if(dateSecond.after(finalRecDate)){
					finalRecDate=dateSecond;
				}
				cell.setCellValue(dateSecond);
			}
			//33第二次到款金额
			j++;
			cell = row.createCell(j);
			cell.setCellStyle(currencyStyle);
			Double amtSecond = getReceiveAmtIndex(objects[30],1);
			if(amtSecond!=null)
				cell.setCellValue(amtSecond);
			//34第三次到款日期
			j++;
			cell = row.createCell(j);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellStyle(dateStyle);
			Date dateThird = getReceiveDateIndex(objects[29],2);
			if(null!=dateThird){
				if(dateThird.after(finalRecDate)){
					finalRecDate=dateThird;
				}
				cell.setCellValue(dateThird);
			}
			//35第三次到款金额
			j++;
			cell = row.createCell(j);
			cell.setCellStyle(currencyStyle);
			Double amtThird = getReceiveAmtIndex(objects[30],2);
			if(amtThird!=null)
				cell.setCellValue(amtThird);
			//36第四次到款日期
			j++;
			cell = row.createCell(j);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellStyle(dateStyle);
			Date dateFourth = getReceiveDateIndex(objects[29],3);
			if(null!=dateFourth){
				if(dateFourth.after(finalRecDate)){
					finalRecDate=dateFourth;
				}
				cell.setCellValue(dateFourth);
			}
			//37第四次到款金额
			j++;
			cell = row.createCell(j);
			cell.setCellStyle(currencyStyle);
			Double amtFourth = getReceiveAmtIndex(objects[30],3);
			if(amtFourth!=null)
				cell.setCellValue(amtFourth);
			//37-1第五次到款日期
			j++;
			cell = row.createCell(j);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellStyle(dateStyle);
			Date dateFifth = getReceiveDateIndex(objects[29],4);
			if(null!=dateFifth){
				if(dateFifth.after(finalRecDate)){
					finalRecDate=dateFifth;
				}
				cell.setCellValue(dateFifth);
			}
			//37-2第五次到款金额
			j++;
			cell = row.createCell(j);
			cell.setCellStyle(currencyStyle);
			Double amtFifth = getReceiveAmtIndex(objects[30],4);
			if(amtFifth!=null)
				cell.setCellValue(amtFifth);
			//37-2第六次到款日期
			j++;
			cell = row.createCell(j);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellStyle(stringStyle);
			StringBuffer datas = new StringBuffer();
			int indexDate = 5;
			Date temp = null;
			do {
				temp = getReceiveDateIndex(objects[29],indexDate);
				if(null!=temp){
					if(temp.after(finalRecDate)){
						finalRecDate=temp;
					}
					datas.append(DateUtils.formatDate(temp,"yyyy/MM/dd")).append(";");
				}
				indexDate++;
			} while (temp!=null);
			if(StringUtils.isNotBlank(datas.toString())){
				cell.setCellValue(datas.toString());
			}
//			Date dateSixth = getReceiveDateIndex(objects[29],5);
//			if(null!=dateSixth){
//				if(dateSixth.after(finalRecDate)){
//					finalRecDate=dateSixth;
//				}
//				cell.setCellValue(dateSixth);
//			}
			//37-2第六次到款金额
			j++;
			cell = row.createCell(j);
			cell.setCellStyle(currencyStyle);
//			Double amtSixth = getReceiveAmtIndex(objects[30],5);
//			if(amtSixth!=null && amtSixth.compareTo(0D)>0)
//				cell.setCellValue(amtSixth);
			StringBuffer amts = new StringBuffer();
			int indexAmt = 5;
			Double tempAmt = null;
			do {
				tempAmt = getReceiveAmtIndex(objects[30],indexAmt);
				if(null!=tempAmt){
					amts.append(tempAmt).append("; ");
				}
				indexAmt++;
			} while (tempAmt!=null);
			if(StringUtils.isNotBlank(amts.toString())){
				cell.setCellValue(amts.toString());
			}
			//38调整金额（手续费）
			j++;
			cell = row.createCell(j);
			cell.setCellStyle(currencyStyle);
			cell.setCellValue(getCurrencyValue(objects[31]));
			//39扣款类型
			j++;
			cell = row.createCell(j);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellStyle(stringStyle);
			cell.setCellValue(StringUtils.nullToString(objects[32]));
			//40扣款金额
			j++;
			cell = row.createCell(j);
			cell.setCellStyle(currencyStyle);
			cell.setCellValue(getCurrencyValue(objects[33]));
			//41备注
			j++;
			cell = row.createCell(j);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellStyle(stringStyle);
			cell.setCellValue(StringUtils.nullToString(objects[34]));
			//42期限
			j++;
			cell = row.createCell(j);
			cell.setCellStyle(currencyStyle);
			cell.setCellValue(getCurrencyValue(objects[35]));
			//43到期日
			j++;
			cell = row.createCell(j);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellStyle(dateStyle);
			Date endDate = getDateValue(objects[36]);
			if(null!=endDate)
				cell.setCellValue(endDate);
			//44到期月份
			j++;
			cell = row.createCell(j);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellStyle(currencyStyle);
			String monthString=StringUtils.nullToString(objects[37]);
			if(StringUtils.isNotBlank(monthString)&&monthString.indexOf("0000")<0)
				cell.setCellValue(monthString);
			//44.1 放宽后到期日
			j++;
			cell = row.createCell(j);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellStyle(dateStyle);
			Date fdDate = getDateValue(objects[47]);
			if(null!=fdDate)
				cell.setCellValue(fdDate);
			//宽限期
			int zday = Integer.valueOf(StringUtils.nullToString(objects[46]));
			if(zday>0&&null!=endDate){
				//宽限期大于0,则将到期日延后
				endDate = org.apache.commons.lang.time.DateUtils.addDays(endDate,zday);
			}
			//45逾期天数(应收余额>0?当前日期-到期日:NULL)
			j++;
			cell = row.createCell(j);
			cell.setCellStyle(currencyStyle);
			if(receiveBlance.compareTo(0D)>0){
				if(null!=endDate && DateUtils.beforeToday(endDate)){
					cell.setCellValue(DateUtils.dateDiff(DateUtils.now(),endDate));
				}
			}
			//  逾期条件
			j++;
			cell = row.createCell(j);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellStyle(stringStyle);
			if(receiveBlance.compareTo(0D)>0){
				if(null!=endDate && DateUtils.beforeToday(endDate)){
					cell.setCellValue(getExpCondition(DateUtils.dateDiff(DateUtils.now(),endDate)));
				}
			}
			//46逾期金额((到期日<当前日期&&应收余额>0)?应收余额:0)
			j++;
			cell = row.createCell(j);
			cell.setCellStyle(currencyStyle);
			if(receiveBlance.compareTo(0D)>0){
				if(null!=endDate && DateUtils.beforeToday(endDate)){
					cell.setCellValue(receiveBlance);
				}
			}
			//单笔到期已收=∑(第N次到款日期<到期日?第N次到款金额:0)
			//Double singleRecAmtBeforeEnd = getSingleReceiveBeforeEndDate(endDate,objects[29],objects[30]);
			//逾期天数
			Integer expDays=0;
			if (receiveBlance.compareTo(0D) > 0) {
				// 应收余额>0:取今天-到期日
				if (null != fdDate && DateUtils.beforeToday(fdDate)) {
					expDays = DateUtils.dateDiff(fdDate, DateUtils.now());
				}
			} else {
				// 应收余额小于0且最后次到款日期>到期日:取最后一次到款日期-到期日
				if (null != fdDate && null != finalRecDate
						&& finalRecDate.after(fdDate)) {
					expDays = DateUtils.dateDiff(fdDate, finalRecDate);
				}
			}
			
			//单笔AR逾期金额
			Double singleExpAmts = getSingleReceiveAfterEndDate(fdDate,objects[29], objects[30])
					+ ((fdDate!=null && DateUtils.beforeToday(fdDate)) ? receiveBlance : 0D);
			//单笔AR欠款余额
			Double singleBalance = receiveBlance.compareTo(0D)>0?receiveBlance:0D;
			//51单笔逾期天数
			j++;
			cell = row.createCell(j);
			cell.setCellStyle(currencyStyle);
			int singleExpDays = 0;
			if(singleExpAmts.compareTo(0D)>0){
				if(singleBalance.compareTo(1D)>0 && DateUtils.beforeToday(fdDate)){
					//单笔AR欠款余额>1：今天-到期日
					singleExpDays = DateUtils.dateDiff(fdDate,DateUtils.now());
				}else{
					//最后一次到款日期-到期日
					if(null!=endDate && null!=finalRecDate && finalRecDate.after(fdDate)){
						singleExpDays = DateUtils.dateDiff(fdDate, finalRecDate);
					}
				}
			}else{
				singleExpDays = 0;
			}
			cell.setCellValue(singleExpDays);
			//47单笔AR欠款余额=7开票金额-48单笔到期已收
			j++;
			cell = row.createCell(j);
			cell.setCellStyle(currencyStyle);
			cell.setCellValue(singleBalance);
			//48单笔到期已收--delete on 20140318
//			j++;
//			cell = row.createCell(j);
//			cell.setCellStyle(currencyStyle);
//			cell.setCellValue(singleRecAmtBeforeEnd);
			//49单笔AR逾期金额 =单笔逾期金额合计+应收余额
			
			j++;
			cell = row.createCell(j);
			cell.setCellStyle(currencyStyle);
			if(expDays>0){
				cell.setCellValue(singleExpAmts);
			}
			//50最后次到款日期
//			j++;
//			cell = row.createCell(j);
//			cell.setCellStyle(dateStyle);
//			if(finalRecDate!=null){
//				cell.setCellValue(finalRecDate);
//			}
			//51逾期天数(29应收款余额>0?(43到期日-今天):(50最后次到款日期-43到期日))
//			j++;
//			cell = row.createCell(j);
//			cell.setCellStyle(currencyStyle);
//			cell.setCellValue(expDays);
			//52逾期利息金额(49单笔AR逾期金额 * 51逾期天数 * 0.006 / 30)
//			Double expInterests = roundHalfUp((singleExpAmts*expDays*0.006D/30),2);
//			j++;
//			cell = row.createCell(j);
//			cell.setCellStyle(currencyStyle);
//			cell.setCellValue(expInterests);
			//53汇率
//			j++;
//			cell = row.createCell(j);
//			cell.setCellStyle(currencyStyle);
//			cell.setCellValue(getCurrencyValue(objects[39]));
			//54信用额
//			j++;
//			cell = row.createCell(j);
//			cell.setCellStyle(currencyStyle);
//			cell.setCellValue(getCurrencyValue(objects[40]));
			//54信用币种
//			j++;
//			cell = row.createCell(j);
//			cell.setCellStyle(stringStyle);
//			cell.setCellValue(StringUtils.nullToString(objects[41]));
			//55趋势
//			j++;
//			cell = row.createCell(j);
//			cell.setCellStyle(stringStyle);
//			cell.setCellValue(StringUtils.nullToString(objects[42]));
			//52 逾期利息金额
			j++;
			cell = row.createCell(j);
			cell.setCellStyle(currencyStyle);
			cell.setCellValue(calculateExpInterest(fdDate,objects[29], objects[30], receiveBlance));
			//53 30天逾期日利率
			j++;
			cell = row.createCell(j);
			cell.setCellStyle(currencyStyle);
			cell.setCellValue(new BigDecimal(INTEREST_CONS).setScale(6, BigDecimal.ROUND_HALF_UP).toString());
			//54 31天逾期日利率
			j++;
			cell = row.createCell(j);
			cell.setCellStyle(currencyStyle);
			cell.setCellValue(new BigDecimal(INTEREST_CONS*2).setScale(6, BigDecimal.ROUND_HALF_UP).toString());
			//55开票汇率
			j++;
			cell = row.createCell(j);
			cell.setCellStyle(currencyStyle);
			cell.setCellValue(getCurrencyValue(objects[48]));
			//56未清金额
			j++;
			cell = row.createCell(j);
			cell.setCellStyle(currencyStyle);
			cell.setCellValue(getCurrencyValue(objects[43]));
			//57是否未清
			j++;
			cell = row.createCell(j);
			cell.setCellStyle(stringStyle);
			cell.setCellValue(StringUtils.nullToString(objects[44]));
			//58不符金额
			j++;
			cell = row.createCell(j);
			cell.setCellStyle(currencyStyle);
			cell.setCellValue(getCurrencyValue(objects[45]));
		}
		String dateStr = DateUtils.formatDate();
		File file = new File("d://未收汇月结报表"+dateStr+".xls");
		OutputStream os = new FileOutputStream(file);
		workbook.write(os);
		os.close();
		MailUtil mailUtil = new MailUtil();
		String emails = "hes@leedarson.com,linxh1@leedarson.com,zhaoq@leedarson.com,xuhy@leedarson.com";
		String[] es = emails.split(",");
		for (int i = 0; i < es.length; i++) {
			try {
				logger.error("===未收汇月结报表--->"+ es[i]+ "===" + mailUtil.sendMail(file, es[i], "未收汇月结报表 "+dateStr, "请查收附件"));
			} catch (Exception e) {
			}
		}
	}
	private static Double INTEREST_CONS = 0.06D/360;
	
	//未收汇报表30天汇率
	private static Double INTEREST_ONE_MONTH = new BigDecimal(INTEREST_CONS).setScale(6, BigDecimal.ROUND_HALF_UP).doubleValue();
	
	//未收汇报表31天汇率
	private static Double INTEREST_DOUBLE_MONTH = new BigDecimal(INTEREST_CONS*2).setScale(6, BigDecimal.ROUND_HALF_UP).doubleValue();
	
	private List<Object> findNoneRecList(){
		StringBuffer cond = new StringBuffer();
		cond.append("with temp as(")
			.append(" select vbeln,listagg(budat,'|') within group(order by budat) as budat,listagg(zcamount,'|') within group(order by budat) as zcamount,sum(zcamount) as amt,sum(zsum) as zsum,sum(zhc) as zhc from (")
			.append(" select na.vbeln, nb.budat, sum(na.zcamount-na.zhc) as zcamount,sum(na.zcamount) as zsum,sum(na.zhc) as zhc from zfmt0004 na,zfmt0003 nb where na.mandt=nb.mandt and (na.zrbl='B' or (na.zrbl='A' and not exists(select 1 from zfmt0004 te where te.zbelnr=na.zbelnr and te.zposnr_s=na.zposnr))) and na.zdrs='C' and na.zcirs='C' and na.zbelnr=nb.zbelnr and na.mandt=").append(getClient())
			.append(" and na.vbeln!=' ' group by na.vbeln, nb.budat,nb.zbelnr")
			.append(" )group by vbeln")
			.append(" ),bsidtep as(")
			.append(" select p.vgbel as vbeln,nvl(sum(case when bschl >'09' then -wrbtr else wrbtr end),0) as unclear from bsid d")
			.append(" inner join (select distinct vgbel,vbeln from vbrp) p on p.vbeln = d.zuonr")
			.append(" where umskz=' '")
			.append(" and d.mandt =").append(getClient())
			.append(" group by p.vgbel")
			.append(" )")
			.append(" select")
			.append(" a.vbeln 外向交货单,a.bukrs 出口单位,b.zddate 申报日期,a.zodate 出运日期,b.zeamt 报关金额,")
			.append(" a.zdamt 发货金额,a.ziamt 开票金额,a.maktx 品名,b.lfimg 报关数量,a.menge 实际数量,")
			.append(" b.zclfimg 折算数量,a.werks 生产工厂,a.zinvoice 发票号,a.znation 出口国家,a.kunnr 客户ID,")
			.append(" a.sortl 客户简称,a.zrsm RSM,a.zae AE,a.zaa AA,a.zeway 收汇方式,")
			.append(" b.zcost 报关海运费,b.vbelv 报关单号,b.zmodel 柜型,a.zlbelnr 收款凭证号,a.zlwaers 币种,")
			.append(" a.zlterm 付款条件代码,a.zlvtext 付款条件,nvl(t.amt,0) 已收金额,nvl(case when a.ziamt=0 then a.zdamt else a.ziamt end,0)+nvl(b.zcamt,0)-nvl(t.amt,0)-nvl(t.zhc,0) 应收款余额,")
			.append(" t.budat 到款日期,t.zcamount 到款金额,nvl(t.zhc,0) 调整金额,b.zvtext 扣款类型,b.zcamt 扣款金额,")
			.append(" b.znote 备注,a.zdays 期限,a.zddate 到期日,a.zdmonth 到期月份,a.fkdat 起算日期,a.ukurs 汇率,")
			.append(" a.klimk 信用额,a.fcurr 信用币种,a.ztendency 趋势,nvl(v.unclear,0) 未清金额,")
			.append(" case when nvl(case when a.ziamt=0 then a.zdamt else a.ziamt end,0)+nvl(b.zcamt,0)-nvl(t.amt,0)-nvl(t.zhc,0)=nvl(v.unclear,0) then '' else '是' end 是否不符,")
			.append(" nvl(case when a.ziamt=0 then a.zdamt else a.ziamt end,0)+nvl(b.zcamt,0)-nvl(t.amt,0)-nvl(t.zhc,0)-nvl(v.unclear,0) 不符金额,")
			.append(" nvl(d.zday,0) 宽限期,zkdate 放宽到期日,a.ukurs 开票汇率,a.vkbur 销售部门 ,a.meins 开票日期")
			.append(" from zfmt0016 a")
			.append(" left join zfmt0017 b on a.madat=b.mandt and a.vbeln=b.vbeln")
			.append(" left join zfmt0007 d on a.kunnr=d.kunnr and a.bukrs=d.bukrs")
			.append(" left join temp t on a.vbeln=t.vbeln")
			.append(" left join bsidtep v on a.vbeln=v.vbeln");
		cond.append(" where a.madat=").append(getClient());
		cond.append(" and a.fkdat!='00000000' and a.ziamt>0");
		cond.append(" order by a.vbeln");
		String query = cond.toString();
		List<Object> list = findListBySql(query);
		return list;
	}
	
	private HSSFCellStyle getStyleForStringCell(HSSFCellStyle style) {
		// 设置底边框;
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		// 设置底边框颜色;
		style.setBottomBorderColor(HSSFColor.BLACK.index);
		// 设置右边框;
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		// 设置右边框颜色;
		style.setRightBorderColor(HSSFColor.BLACK.index);
		// 在样式用应用设置的字体;
		// 设置自动换行;
		style.setWrapText(false);
		// 设置水平对齐的样式为右侧对齐;
		style.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		// 设置垂直对齐的样式为居中对齐;
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		return style;
	}
	private HSSFCellStyle getStyleForDateCell(HSSFCellStyle style,
			HSSFDataFormat format) {
		// 设置底边框;
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		// 设置底边框颜色;
		style.setBottomBorderColor(HSSFColor.BLACK.index);
		// 设置右边框;
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		// 设置右边框颜色;
		style.setRightBorderColor(HSSFColor.BLACK.index);
		// 在样式用应用设置的字体;
		// 设置自动换行;
		style.setWrapText(false);
		// 设置水平对齐的样式为左侧对齐;
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		// 设置垂直对齐的样式为居中对齐;
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		style.setDataFormat(format.getFormat("yyyy-m-d"));
		return style;
	}
	private HSSFCellStyle getStyleForNumCell(HSSFCellStyle style) {
		// 设置底边框;
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		// 设置底边框颜色;
		style.setBottomBorderColor(HSSFColor.BLACK.index);
		// 设置右边框;
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		// 设置右边框颜色;
		style.setRightBorderColor(HSSFColor.BLACK.index);
		// 在样式用应用设置的字体;
		// 设置自动换行;
		style.setWrapText(false);
		// 设置水平对齐的样式为右侧对齐;
		style.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
		// 设置垂直对齐的样式为居中对齐;
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		return style;
	}
	private Date getDateValue(Object from){
		String value = StringUtils.nullToString(from);
		if(StringUtils.isBlank(value)
				||"00000000".equals(value)){
			return null;
		}
		try{
			Date current = DateUtils.parseDate(value,"yyyyMMdd");
			return current;
		}catch (Exception e) {
		}
		return null;
	}
	private Double getCurrencyValue(Object from){
		Double result = 0D;
		try{
			result = Double.valueOf(StringUtils.nullToString(from));
		}catch (Exception e) {
			result = 0D;
		}
		return result;
	}
	private Date getReceiveDateIndex(Object from,int index){
		String value = StringUtils.nullToString(from);
		if(StringUtils.isBlank(value)||index<0)
			return null;
		String[] values = value.split("\\|");
		if(index>values.length-1)
			return null;
		return getDateValue(values[index]);
	}
	private Double getReceiveAmtIndex(Object from,int index){
		String value = StringUtils.nullToString(from);
		if(StringUtils.isBlank(value)||index<0)
			return null;
		String[] values = value.split("\\|");
		if(index>values.length-1)
			return null;
		return getCurrencyValue(values[index]);
	}
	private String getExpCondition(int expDays){
		if(expDays<=30){
			return "0-30天";
		}else if(expDays<=60){
			return "30-60天";
		}else if(expDays<=90){
			return "60-90天";
		}else if(expDays<=180){
			return "90-180天";
		}else{
			return "180天以上";
		}
	}
	private Double getSingleReceiveAfterEndDate(Date endDate,Object dates,Object amts){
		Double result = 0D;
		if(endDate==null)
			return 0D;
		Date recDate;
		int i=0;
		do{
			recDate = getReceiveDateIndex(dates,i);
			Double recAmt= getReceiveAmtIndex(amts,i);
			if(null!=recDate && null!=recAmt && recDate.after(endDate)){
				result+=recAmt;
			}
			i++;
		}while(recDate!=null);
		return result;
	}
	public Double calculateExpInterest(Date endDate,Object dates,Object amts,Double balance){ 
		Double result = 0D;
		if(endDate==null)
			return 0D;
		Date recDate;
		int i=0;
		do{
			recDate = getReceiveDateIndex(dates,i);
			Double recAmt= getReceiveAmtIndex(amts,i);
			if(null!=recDate && null!=recAmt && recDate.after(endDate)){
				int dateDiff = DateUtils.dateDiff(endDate, recDate);
				int in30Day = dateDiff>30?30:dateDiff;
				int out30Day = dateDiff>30?(dateDiff-30):0;
				result+=recAmt*(in30Day*INTEREST_ONE_MONTH+out30Day*INTEREST_DOUBLE_MONTH);
			}
			i++;
		}while(recDate!=null);
		
		Date today = new Date();
		if(today.after(endDate)&&balance.compareTo(0D)>0){
			int dateDiff = DateUtils.dateDiff(endDate, today);
			int in30Day = dateDiff>30?30:dateDiff;
			int out30Day = dateDiff>30?(dateDiff-30):0;
			result+=balance*(in30Day*INTEREST_ONE_MONTH+out30Day*INTEREST_DOUBLE_MONTH);
		}
		return new BigDecimal(result).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	}
	
	/**
	 * 同步客户对应表，数据来自sap最新维护的
	 * 
	 * @throws Exception
	 */
	@Override
	public void updateCustomerTableFromSap() throws Exception {
		// 删除数据门户客户对应表的内容
		String deleteSql = "delete from c_customTable";
		mailAddrDao.getSession().createSQLQuery(deleteSql).executeUpdate();
		// 从sap中获取最新客户对应表资料
		String gainDataFromSapSql = "select t1.mandt,t1.vkorg,t1.kunnr,nvl((select kna1.sortl from kna1 where kna1.kunnr = t1.kunnr and kna1.mandt = '"
				+ getClient()
				+ "' and rownum=1),' ') as sortl,(select t2.bezei from tvkbt t2 where t2.vkbur=t1.vkbur and t2.mandt=t1.mandt) as ZRSM,(select t3.bezei from tvgrt t3 where t3.vkgrp=t1.vkgrp and t3.mandt=t1.mandt) as ZAE,(select t4.bezei from tvv2t t4 where t4.kvgr2=t1.kvgr2 and t4.mandt=t1.mandt) as ZAA from knvv t1 where t1.mandt = '"
				+ getClient()
				+ "'  and t1.vkorg in (select distinct(bukrs) from zfmt0007 where mandt='"
				+ getClient()
				+ "') and t1.vkorg||t1.kunnr in (select distinct(bukrs||kunnr) from zfmt0007 where mandt='"
				+ getClient() + "')";
		List<Object> list = findListBySql(gainDataFromSapSql);
		for (Object object: list) {
			Object[] objects = (Object[])object;
			String insertSql = "insert into c_customTable(mandt,bukrs,kunnr,sortl,zrsm,zae,zaa,addTime) values ( ";// 把sap获取的最新客户对应表资料同步到数据门户客户对应表c_customTable
			insertSql += "'" + objects[0] + "',";
			insertSql += "'" + objects[1] + "',";
			insertSql += "'" + objects[2] + "',";
			insertSql += "'" + objects[3] + "',";
			if (objects[4] != null
					&& StringUtils.isNotBlank(objects[4].toString())
					&& (objects[4].toString().contains("（") || objects[4]
							.toString().contains("("))) {// 过滤zrsm为"陈佳虹（SD6）代"情况
				String zrsmTemp = "";
				if (objects[4].toString().contains("（")) {
					zrsmTemp = objects[4].toString().substring(0,
							objects[4].toString().indexOf("（"));
				} else {
					zrsmTemp = objects[4].toString().substring(0,
							objects[4].toString().indexOf("("));
				}
				insertSql += "'" + zrsmTemp + "',";
			} else {
				insertSql += "'" + objects[4] + "',";
			}
			insertSql += "'" + objects[5] + "',";
			insertSql += "'" + objects[6] + "',";
			insertSql += "sysdate";
			insertSql += ")";
			mailAddrDao.getSession().createSQLQuery(insertSql).executeUpdate();
		}
		// 从数据门户表c_customTable获取客户信息
		String gainCustomerSql = ""
		+"select t1.mandt,                                     "
		+"     t1.bukrs,                                       "
		+"     t1.kunnr,                                       "
		+"     t1.sortl,                                       "
		+"     (select employee_number                                      "
		+"        from ORG_USER                                "
		+"       where user_name = t1.zrsm                     "
		+"         and major_job_name like '%营销%') as zrsmId,  "
		+"     (select employee_number                                      "
		+"        from ORG_USER                                "
		+"       where user_name = t1.zae                      "
		+"         and major_job_name like '%营销%') as zaeId,   "
		+"     (select employee_number                                      "
		+"        from ORG_USER                                "
		+"       where user_name = t1.zaa                      "
		+"         and major_job_name like '%营销%') as zaaId    "
		+"from c_customTable t1                                ";
		@SuppressWarnings("unchecked")
		List<Object> listCustomer = mailAddrDao.getSession().createSQLQuery(gainCustomerSql).list();
		for (Object object : listCustomer) {
			Object[] objects = (Object[]) object;
			// 更新zfmt0007客户对应表，取sap最新数据
			StringBuffer updateCustomerSql = new StringBuffer(
					"update ZFMT0007 set mandt = '" + getClient() + "' ");
			if (objects[3] != null
					&& StringUtils.isNotBlank(objects[3].toString())) {
				updateCustomerSql.append(" ,sortl='" + objects[3] + "'");
			}
			if (objects[4] != null
					&& StringUtils.isNotBlank(objects[4].toString())) {
				updateCustomerSql.append(" ,zrsm='" + objects[4] + "'");
			}
			if (objects[5] != null
					&& StringUtils.isNotBlank(objects[5].toString())) {
				updateCustomerSql.append(" ,zae='" + objects[5] + "'");
			}
			if (objects[6] != null
					&& StringUtils.isNotBlank(objects[6].toString())) {
				updateCustomerSql.append(" ,zaa='" + objects[6] + "'");
			}
			updateCustomerSql.append(" where mandt='" + getClient()
					+ "' and bukrs ='" + objects[1] + "' and kunnr='"
					+ objects[2] + "'");
			execSql(updateCustomerSql.toString());
		}
		// 3.定时从客户表同步客户简称至到账信息表(zfmt0003)
		String updateSortl = "update zfmt0003 a set sortl="
				+ " ( with cust as(select trim(kunnr) as kunnr,trim(sortl) as sortl from kna1)"
				+ " select max(sortl) from cust c where c.kunnr = a.kunnr)"
				+ " where zdrs!='C' " + " and zcirs!='C' "
				+ " and exists (select 1 from kna1 k where k.kunnr=a.kunnr)";
		execSql(updateSortl);
		// 4.更新客户编号为...的简称为空
		String updSortlString = "update zfmt0003 set sortl=' ' where kunnr = '...'";
		execSql(updSortlString);
	}
	
}
