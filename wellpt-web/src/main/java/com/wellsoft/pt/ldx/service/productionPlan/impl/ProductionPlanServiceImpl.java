package com.wellsoft.pt.ldx.service.productionPlan.impl;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.hibernate.SQLQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wellsoft.pt.basicdata.sap.service.SAPRfcService;
import com.wellsoft.pt.basicdata.sap.util.SAPRfcJson;
import com.wellsoft.pt.core.dao.UniversalDao;
import com.wellsoft.pt.core.support.ExtendedPropertyPlaceholderConfigurer;
import com.wellsoft.pt.core.support.PagingInfo;
import com.wellsoft.pt.ldx.model.productionPlan.PlanShare;
import com.wellsoft.pt.ldx.model.productionPlan.ZPPT0030;
import com.wellsoft.pt.ldx.model.productionPlan.ZPPT0031;
import com.wellsoft.pt.ldx.model.productionPlan.ZPPT0033;
import com.wellsoft.pt.ldx.model.productionPlan.ZPPT0068;
import com.wellsoft.pt.ldx.service.impl.SapServiceImpl;
import com.wellsoft.pt.ldx.service.productionPlan.IProductionPlanService;
import com.wellsoft.pt.ldx.util.DateUtils;
import com.wellsoft.pt.ldx.util.StringUtils;
import com.wellsoft.pt.org.entity.User;

/**
 * 
 * Description: 生产计划平台service
 *  
 * @author heshi
 * @date 2014-7-29
 * @version 1.0
 * 
 * <pre>
 * 修改记录:
 * 修改后版本	修改人		修改日期			修改内容
 * 2014-7-29.1	heshi		2014-7-29		Create
 * </pre>
 *
 */
@Service
@Transactional
public class ProductionPlanServiceImpl extends SapServiceImpl implements IProductionPlanService {
	@Autowired
	private SAPRfcService saprfcservice;
	@Autowired
	private UniversalDao universalDao;
	@Autowired
	private ExtendedPropertyPlaceholderConfigurer propertyConfigurer;
	
	private static Logger logger = LoggerFactory.getLogger(ProductionPlanServiceImpl.class);
	
	private static final int ROUND = 100;// 排产精度范围

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> findProductionPlanByPage(PagingInfo pageInfo, Map<String, String> map,String order) {
		if(StringUtils.isBlank(order)){
			//默认按照SD交期排序
			order = "edatu";
		}
		String planType = map.get("planType");//生产订单号
		String aufnr = map.get("aufnr");//生产订单号
		String kdauf = map.get("kdauf");//销售单号 add by HeShi 20130924
		String sortl = map.get("sortl");//客户编号 add by HeShi 20130924
		String matnr = map.get("matnr");//物料ID add by HeShi 20130924
		String zgrun = map.get("zgrun");//物料描述 add by HeShi 20130924
		String zqt = map.get("zqt");//备注 add by HeShi 20131008
		String zsg = map.get("zsg");//生管
		//String zkz = map.get("zkz");//课长
		String zxh = map.get("zxh");//线号
		String dwerks = map.get("dwerks");//厂别
		String zclass = map.get("class");//课别
		String department =  map.get("department");//部别
		String zzt = map.get("zzt");//状态
		String edatu = map.get("edatufrom");//SD交期
		String gstrp = map.get("gstrpfrom");//上线日期
		String gltrp = map.get("gltrpfrom");//完工日期
		String scrq = map.get("scrqfrom");//生产日期
		String mpsdate = map.get("mpsdatefrom");//MPS上线日期
		String yjcq = map.get("yjcqfrom");//预计船期
		//add by heshi 20130929 添加日期条件的到期日 begin
		String edatuTo = map.get("edatuto");//SD交期
		String gstrpTo = map.get("gstrpto");//上线日期
		String gltrpTo = map.get("gltrpto");//完工日期
		String scrqTo = map.get("scrqto");//生产日期
		String mpsdateTo = map.get("mpsdateto");//MPS上线日期
		String yjcqTo = map.get("yjcqto");//预计船期
		//add by heshi 20130929 添加日期条件的到期日 end
		String client = map.get("client");//客户端
		String sgdd = map.get("sgdd");//手工订单(交期为空)
		StringBuffer sql = new StringBuffer(
				"select a.aufnr,a.verid,a.dwerk,a.sortl,substr(a.kdauf,3),substr(a.kdpos,4),a.edatu,substr(a.matnr,9),a.zgrun,a.gamng,a.gstrp,a.gltrp,a.vgw01,a.vgw02,a.igmng,a.zyjcq,a.zudate,b.dwerks,b.zsg,b.zxhscrq,b.zypcl,b.zzt,b.zqt,b.zyxj,b.zwgpd,b.zqlzt,b.zwlzk,b.zsxrq,b.zwcl,b.zwwcl,b.zdqpcl,b.zlevel,b.zkpcl from zppt0030 a,zppt0031 b ")
				.append("where a.aufnr = b.aufnr ");
		if(StringUtils.isNotBlank(client)){
			sql.append("and a.mandt = '").append(client).append("' ");
		}
		if(StringUtils.isNotBlank(aufnr)){
			sql.append("and a.aufnr = '").append(aufnr).append("' ");
		}
		if(StringUtils.isNotBlank(kdauf)){
			sql.append("and a.kdauf = '").append(kdauf).append("' ");
		}
		if(StringUtils.isNotBlank(sortl)){
			sql.append("and a.sortl = '").append(sortl).append("' ");
		}
		if(StringUtils.isNotBlank(matnr)){
			sql.append("and a.matnr = '").append(matnr).append("' ");
		}
		if(StringUtils.isNotBlank(zgrun)){
			sql.append("and a.zgrun like '%").append(zgrun).append("%' ");
		}
		if(StringUtils.isNotBlank(zqt)){
			sql.append("and b.zqt like '%").append(zqt).append("%' ");
		}
		if(StringUtils.isNotBlank(planType)&&"0".equals(planType)){
			sql.append("and b.zsg like 'A%' ");
		}else{
			sql.append("and b.zsg not like 'A%' ");
		}
		if(StringUtils.isNotBlank(edatu)){
			sql.append("and a.edatu >= '").append(edatu).append("' ");
		}
		if(StringUtils.isNotBlank(edatuTo)){
			sql.append("and a.edatu <= '").append(edatuTo).append("' ");
		}
		if(StringUtils.isNotBlank(gstrp)){
			sql.append("and a.gstrp >= '").append(gstrp).append("' ");
		}
		if(StringUtils.isNotBlank(gstrpTo)){
			sql.append("and a.gstrp <= '").append(gstrpTo).append("' ");
		}
		if(StringUtils.isNotBlank(gltrp)){
			sql.append("and a.gltrp >= '").append(gltrp).append("' ");
		}
		if(StringUtils.isNotBlank(gltrpTo)){
			sql.append("and a.gltrp <= '").append(gltrpTo).append("' ");
		}
		if(StringUtils.isNotBlank(yjcq)){
			sql.append("and a.zyjcq >= '").append(yjcq).append("' ");
		}
		if(StringUtils.isNotBlank(yjcqTo)){
			sql.append("and a.zyjcq <= '").append(yjcqTo).append("' ");
		}
		if(StringUtils.isNotBlank(mpsdate)){
			sql.append("and b.zsxrq >= '").append(mpsdate).append("' ");
		}
		if(StringUtils.isNotBlank(mpsdateTo)){
			sql.append("and b.zsxrq <= '").append(mpsdateTo).append("' ");
		}
		if(StringUtils.isBlank(sgdd)){
			sql.append("and a.edatu <> '00000000' ");
		}
		if (StringUtils.isNotBlank(zzt)) {
			if ("-1".equals(zzt)) {
				//-1  未分配生管
				sql.append("and b.aufnr is null ");
			} else if("0".equals(zzt)){ 
				//0.未安排
				sql.append("and (b.zzt = '0' or b.zzt=' ') and b.zwgpd = '0' ");
			} else if ("1".equals(zzt)) {
				//1.未排满,
				sql.append("and b.zzt = '1' and b.zwgpd = '0' ");
			} else if ("2".equals(zzt)){
				//2.已排满、正常
				sql.append("and b.zzt = '2' and b.zqlzt !='1' and b.zwgpd = '0' ");
			} else if ("3".equals(zzt)){
				//3.已排满、欠料
				sql.append("and b.zzt > '2' and b.zqlzt ='1' and b.zwgpd = '0' ");
			} else if ("4".equals(zzt)){
				//4.已排满、超计划量
				sql.append("and b.zzt = '3' and b.zwgpd = '0' ");
			} else if ("5".equals(zzt)){
				//5.已排满、超交期
				sql.append("and b.zzt = '5' and b.zwgpd = '0' ");
			} else if ("99".equals(zzt)){
				//99.完工
				sql.append("and b.zwgpd ='1' ");
			} else if ("100".equals(zzt)){
				//100.归档
				sql.append("and b.zwgpd ='2' ");
			} 
		}else{
			//默认查找未完工记录
			sql.append("and b.zwgpd = '0' ");
		}
		if (StringUtils.isNotBlank(zsg)){
			//生管
			sql.append("and b.zsg = '").append(zsg).append("' ");
		}
		if (StringUtils.isNotBlank(dwerks)){
			//工厂
			sql.append("and exists (select 1 from zppt0034 d,zppt0033 e where b.aufnr = e.aufnr and d.zxh=e.zxh and d.dwerks = '").append(dwerks).append("') ");
		}
		if (StringUtils.isNotBlank(zclass)){
			//课别
			sql.append("and exists (select 1 from zppt0034 d,zppt0033 e where b.aufnr = e.aufnr and d.zxh=e.zxh and d.class = '").append(zclass).append("') ");
		}
		if(StringUtils.isNotBlank(dwerks)||StringUtils.isNotBlank(zclass)||StringUtils.isNotBlank(department)){
			sql.append("and exists (select 1 from zppt0034 d,zppt0033 e where b.aufnr = e.aufnr and d.zxh=e.zxh ");
			if(StringUtils.isNotBlank(dwerks)){
				sql.append("and d.dwerks = '").append(dwerks).append("' ");
			}
			if(StringUtils.isNotBlank(zclass)){
				sql.append("and d.class = '").append(zclass).append("' ");
			}
			if(StringUtils.isNotBlank(department)){
				sql.append("and d.department = '").append(department).append("' ");
			}
			sql.append(") ");
		}
		if (StringUtils.isNotBlank(scrq)||StringUtils.isNotBlank(scrqTo)||StringUtils.isNotBlank(zxh)){
			//生产日期
			sql.append("and exists (select 1 from zppt0033 c where b.aufnr = c.aufnr ");
			if(StringUtils.isNotBlank(scrq)||StringUtils.isNotBlank(scrqTo)){
				if(StringUtils.isNotBlank(scrq)){
					sql.append("and c.gstrp>='").append(scrq).append("' ");
				}
				if(StringUtils.isNotBlank(scrqTo)){
					sql.append("and c.gstrp<='").append(scrqTo).append("' ");
				}
			}
			if (StringUtils.isNotBlank(zxh)){
				sql.append("and c.zxh='").append(zxh).append("'");
			}
			sql.append(") ");
		}
		sql.append(" order by "+order);
		
		String query = "select * from (select row_.*, rownum rownum_ from ("+sql.toString()+") row_) where rownum_ >"+pageInfo.getFirst()+" and rownum_ <="+(pageInfo.getCurrentPage()*pageInfo.getPageSize());
		SQLQuery localQuery = this.sapDao.getSession().createSQLQuery(query);
	    List<Object[]> localList = localQuery.list();
	    if (pageInfo.isAutoCount())
	    {
	      localQuery = this.sapDao.getSession().createSQLQuery(new StringBuilder().append("select count(1) from (").append(sql.toString()).append(")").toString());
	      pageInfo.setTotalCount(((BigDecimal)localQuery.uniqueResult()).longValue());
	    }
	    return localList;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Map<?, ?> findPlanDetail(String aufnr) {
		Map map = new HashMap();
		String sql = "select aufnr,zscrwd,zyxj,zxh,ltxa1,gstrp,gamng01,gamng02,gamng03,zwgl from zppt0033 where mandt="+getClient()+" and aufnr='"+StringUtils.addLeftZero(aufnr,12)+"' order by zxh,gstrp";
		List<Object> list = findListBySql(sql);
		if(null!=list&&list.size()>0){
			map.put("data",list);
			map.put("res","success");
		}else{
			map.put("data","");
			map.put("res","none");
		}
		return map;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Map<?, ?> searchPlanPage(String type1, String value1, String type2, String value2, String type3,
			String value3From, String value3To, String type4, String value4, String sgdd, String sgNum,
			String currPage, String pageSize, String order,String planType) {
		Map<String, String> map = new HashMap<String, String>();
		Map result = new HashMap();
		if (StringUtils.isNotBlank(value1)) {
			map.put(type1, value1);
		}
		if (StringUtils.isNotBlank(value2)) {
			map.put(type2, value2);
		}
		if (StringUtils.isNotBlank(value3From)) {
			map.put(type3.concat("from"), value3From.replaceAll("-", ""));
		}
		if (StringUtils.isNotBlank(value3To)) {
			map.put(type3.concat("to"), value3To.replaceAll("-", ""));
		}
		if (StringUtils.isNotBlank(value4)) {
			String temp;
			if("aufnr".equals(type4)){
				//生产订单号补足12位
				temp = StringUtils.addLeftZero(value4,12);
			}else if("kdauf".equals(type4)){
				//销售订单号补足10位
				temp = StringUtils.addLeftZero(value4,10);
			}else if("matnr".equals(type4)){
				//物料ID补足18位
				temp = StringUtils.addLeftZero(value4,18);
			}else{
				temp=value4;
			}
			map.put(type4, temp);
		}
		if (StringUtils.isNotBlank(sgdd)) {
			map.put("sgdd", sgdd);
		}
		if (StringUtils.isNotBlank(sgNum)) {
			map.put("zsg", sgNum);
		}
		map.put("planType",planType);
		map.put("client", getClient());
		PagingInfo pageInfo = new PagingInfo();
		pageInfo.setCurrentPage(Integer.valueOf(currPage));
		pageInfo.setPageSize(Integer.valueOf(pageSize));
		List<Object[]> list = findProductionPlanByPage(pageInfo,map,order);
		result.put("data",list);
		result.put("res","success");
		result.put("pageInfo",pageInfo);
		return result;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Map<?, ?> getSelectItemByParent(String parentType, String parentValue, String childType) {
		Map result = new HashMap();
		StringBuffer data = new StringBuffer("<option selected value=''></option>");

		if ("zzt".equals(childType)) {// 状态为固定值
			data.append("<option value='-1'>未分配生管</option>");
			data.append("<option value='0'>未安排</option>");
			data.append("<option value='1'>未排满</option>");
			data.append("<option value='2'>已排满、正常</option>");
			data.append("<option value='3'>已排满、欠料</option>");
			data.append("<option value='4'>已排满、超计划量</option>");
			data.append("<option value='5'>已排满、超交期</option>");
			data.append("<option value='99'>完工</option>");
			data.append("<option value='100'>归档</option>");
		} else {
			StringBuffer sql;
			if("zsg".equals(childType)){
				sql = new StringBuffer("select distinct zsg from zppt0035 order by zsg");
			}else{
				sql = new StringBuffer("select distinct ").append(
						childType).append(" from zppt0034 where 1=1");
				if (StringUtils.isNotBlank(parentType)
						&& StringUtils.isNotBlank(parentValue)) {
					sql.append("and ").append(parentType).append(" ='")
							.append(parentValue).append("' ");
				}
				sql.append(" and zrq='20130101' order by ").append(childType);
			}
			List list = findListBySql(sql.toString());
			if (list != null && list.size() > 0) {
				for (Object obj : list) {
					data.append("<option value='")
							.append(StringUtils.nullToString(obj)).append("'>");
					data.append(StringUtils.nullToString(obj)).append("</option>");
				}
			}
		}
		result.put("data",data.toString());
		result.put("res","success");
		return result;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Map<?, ?> searchLoads(String startDay,String endDay,String gxb,String loadShare,String zxh,String userType,String sgNum) {
		Map result = new HashMap();
		if (StringUtils.isBlank(startDay)) {
			startDay = DateUtils.formatDate(new Date(), "yyyy-MM-dd");
		}
		if (StringUtils.isBlank(endDay)) {
			endDay = DateUtils.formatDate(DateUtils.addDate(new Date(), 9),
					"yyyy-MM-dd");
		}
		String sql = "select a.*,case when su>110 or su<100 then 'red' else 'black' end from (select zxh,to_char(to_date(gstrp,'yyyy-MM-dd'),'yyyy-MM-dd') as gstrp,ltxa1,sum(gamng01),sum(gamng02),sum(gamng03),sum(case when trim(zscfh) is null then 0 else to_number(zscfh) end) su from ZPPT0033 group by zxh,gstrp,ltxa1  having gstrp >= '"
				+ startDay.replaceAll("-", "")
				+ "' and gstrp <='"
				+ endDay.replaceAll("-", "") + "' "
				+ "union all select zxh,to_char(to_date(zrq, 'yyyy-MM-dd'), 'yyyy-MM-dd') as gstrp,ltxa1,0,0,0,0 "
				+ "from zppt0034 z where not exists (select 1 from zppt0033 x where x.zxh = z.zxh and x.gstrp = z.zrq) "
				+ "and z.zrq >='"
				+ startDay.replaceAll("-", "")
				+ "' and z.zrq <= '"
				+ endDay.replaceAll("-", "")
				+"')a where 1=1";
		if (StringUtils.isNotBlank(gxb)) {
			String typeTemp = "";
			if ("all".equals(gxb)) {
				typeTemp = "总装+包装";
			} else if ("zz".equals(gxb)) {
				typeTemp = "总装";
			} else if ("bz".equals(gxb)) {
				typeTemp = "包装";
			}
			sql = sql + " and ltxa1 ='" + typeTemp + "' ";
		}
		if (StringUtils.isNotBlank(zxh)) {
			sql = sql + " and zxh ='" + zxh + "' ";
		}
		if ("0".equals(userType) || "3".equals(userType)) {
			sql = sql
					+ " and exists (select 1 from zppt0034 b where b.zxh =a.zxh and (b.zsg ='"
					+ sgNum + "'";
			if ("true".equals(loadShare)) {//启用本组资源共享功能
				sql = sql
						+ "or b.zsg in (select zsg from zppt0035 where zzz in (select zzz from zppt0035 where zsg ='"
						+ sgNum + "')) ";
			}
			sql = sql + "))";
		}
		sql += " order by gstrp,zxh ";
		List<Object> xbfh = findListBySql(sql);
		result.put("data",xbfh);
		result.put("res","success");
		return result;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Map<?,?> getRemainAmt(String aufnr) {
		Map result = new HashMap();
		StringBuffer sql = new StringBuffer("select nvl(nvl(gamng, 0) - nvl(tb.sm, 0), 0) from zppt0030 a,(select sum(gamng01 + gamng02 + gamng03) as sm, aufnr from zppt0033 b ");
		sql.append(" where b.aufnr = '"+StringUtils.addLeftZero(aufnr,12)+"' and b.mandt ="+getClient()+" group by aufnr) tb")
			.append(" where a.aufnr = '"+StringUtils.addLeftZero(aufnr,12)+"' and a.mandt ="+getClient()+" and a.aufnr = tb.aufnr(+)");
		List<Object> list = findListBySql(sql.toString());
		Integer amt = 0;
		if(null!=list && list.size()>0){
			amt = Integer.valueOf(StringUtils.nullToString(list.get(0)));
		}
		if(amt.compareTo(0)>0){
			result.put("data",amt);
			result.put("res","success");
		}else{
			result.put("data",amt);
			result.put("res","当前生产订单已排满!");
		}
		return result;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Map<?, ?> generateAdvice(String aufnr, String startDay, String endDay, String gxb, String zxh,
			String leftAmt, String loadShare, String gsType, String zz, String bz) {
		Map result = new HashMap();
		// 查找当前订单数量及标准工时
		String sql = "select a.gamng,a.vgw01,a.vgw02,b.zsg,b.zyxj,b.zlevel from zppt0030 a "
			+ " left join zppt0031 b on a.mandt=b.mandt and a.aufnr=b.aufnr"
			+ " where a.aufnr = '"+aufnr+"' and a.mandt="+getClient();
		Object[] temp0 = (Object[]) findListBySql(sql).get(0);
		Double productAmtZz = Double.parseDouble(StringUtils.nullToString(temp0[0]));// 待排产数量总装
		Double productAmtBz = Double.parseDouble(StringUtils.nullToString(temp0[0]));// 待排产数量包装
		Double zzgs = Double.parseDouble(StringUtils.nullToString(temp0[1]));// 总装工时
		Double bzgs = Double.parseDouble(StringUtils.nullToString(temp0[2]));// 包装工时
		String zsg = StringUtils.nullToString(temp0[3]);// 生管
		String zyxj = StringUtils.nullToString(temp0[4]);// 优先级
		Double lv = Double.parseDouble(StringUtils.nullToString(temp0[5]));//难易度
		// 默认标准工时配置
		if ("1".endsWith(gsType)) {
			zzgs = Double.parseDouble(String.valueOf(zz));
			bzgs = Double.parseDouble(String.valueOf(bz));
		} else if ("0".equals(gsType)) {
			if (zzgs.compareTo(0D) <= 0) {
				zzgs = Double.parseDouble(String.valueOf(zz));
			}
			if (bzgs.compareTo(0D) <= 0) {
				bzgs = Double.parseDouble(String.valueOf(bz));
			}
		}
		if(zzgs.compareTo(0D)==0 && bzgs.compareTo(0D)==0){
			//总装工时及包装工时都为0不生成建议，由生管手动安排
			result.put("res","fail");
			result.put("msg","总装工时及包装工时都为0,无法生成排产建议");
			return result;
		}
		//难易度
		Double level = 1D;
		if(lv!=null && lv.compareTo(0D)>0){
			level = lv;
		}
		zzgs = zzgs/level;
		bzgs = bzgs/level;
		// 计算待排产数量
		String subPlanQuary = "select gamng01+gamng02+gamng03,ltxa1 from zppt0033 where aufnr='"+aufnr+"' and mandt="+getClient();
		List currPlanList = findListBySql(subPlanQuary);
		if(null!=currPlanList && currPlanList.size()>0){
			for(Object temp:currPlanList){
				Object[] planTemp = (Object[])temp;
				Double amtTemp = Double.parseDouble(StringUtils.nullToString(planTemp[0]));
				String ltxa1 = StringUtils.nullToString(planTemp[1]);
				if(ltxa1.indexOf("总装") > -1){
					productAmtZz -= amtTemp;
				}
				if(ltxa1.indexOf("包装") > -1){
					productAmtBz -= amtTemp;
				}
			}
		}
		if(StringUtils.isNotBlank(leftAmt)){//根据页面剩排产量参数计算
			Double left = Double.parseDouble(leftAmt);
			if(left.compareTo(productAmtZz)<0){
				productAmtZz = left;
			}
			if(left.compareTo(productAmtBz)<0){
				productAmtBz = left;
			}
		}
		// 针对当前生产负荷栏所选起始日期及工序类别，查找日期最早的一条线别，总装+包装的工序别优先于总装工序，暂不查找包装类别的工序
		// 2013-9-4添加包装线建议代码
		if(zzgs.compareTo(0D)==0){//总装工时为0,默认查找包装线
			gxb = "bz";
		}
		if(bzgs.compareTo(0D)==0){//包装工时为0,默认查找总装线
			gxb = "zz";
		}
		StringBuffer sqlA = new StringBuffer(
				"select zrq,zxh,zsg,ltxa1,gamng1,gamng2,gamng3 from zppt0034 a ");
		sqlA.append(" where (a.zsg = '").append(zsg).append("'");
		if ("true".equals(loadShare)) {
			sqlA.append(
					" or a.zsg in (select zsg from zppt0035 where zzz in (select zzz from zppt0035 where zsg = '")
					.append(zsg).append("'))");
		}
		sqlA.append(") ")
				.append(" and a.zrq >= '").append(startDay.replaceAll("-", ""))
				.append("' ").append(" and a.zrq <= '")
				.append(endDay.replaceAll("-", "")).append("' ");
		if (StringUtils.isNotBlank(gxb)) {
			String type = "";
			if ("all".equals(gxb)) {
				type = "总装+包装";
			} else if ("zz".equals(gxb)) {
				type = "总装";
			} else if ("bz".equals(gxb)) {
				type = "包装";
			}
			sqlA.append(" and a.ltxa1 = '").append(type).append("' ");
		}
		if (StringUtils.isNotBlank(zxh)) {
			sqlA.append(" and a.zxh = '").append(zxh).append("' ");
		}
		sqlA.append(
				" and not exists (select 1 from ZPPT0033 b  ")
				.append(" where b.gstrp >= '").append(startDay.replaceAll("-", "")).append("' ")
				.append(" and b.gstrp <= '").append(endDay.replaceAll("-", "")).append("' ")
				.append(" group by zxh, gstrp, ltxa1")
				.append(" having b.gstrp = a.zrq and b.zxh = a.zxh and sum(to_number(zscfh)) > 97)")
				.append(" order by zrq asc, ltxa1 desc, zxh asc");
		List listA = findListBySql(sqlA.toString());
		if (null == listA || listA.size() == 0) {
			result.put("res","fail");
			result.put("msg","当前所选时间及工序范围内查找不到可用产能");
			return result;
		}
		Object temp = listA.get(0);
		Object[] objA = (Object[]) temp;
		String zrq = String.valueOf(objA[0]);// 日期
		zxh = String.valueOf(objA[1]);// 线号
		String ltxa1 = String.valueOf(objA[3]);// 工序别
		Double cnA = Double.parseDouble(String.valueOf(objA[4]));// 产能A
		Double cnB = Double.parseDouble(String.valueOf(objA[5]));// 产能B
		Double cnC = Double.parseDouble(String.valueOf(objA[6]));// 产能C
		Double cnAll = cnA + cnB + cnC;
		listA = null;
		// 查询当天此线别已排产记录
		String sqlB = "select a.gamng01,a.gamng02,a.gamng03,b.vgw01,b.vgw02 from zppt0033 a,zppt0030 b where a.aufnr=b.aufnr and  a.zxh = '"
				+ zxh + "' and a.gstrp = '" + zrq + "'";
		List listB = findListBySql(sqlB.toString());
		// 计算当天产能剩余值
		if (null != listB && listB.size() > 0) {
			for (Object obj : listB) {
				Object[] tempB = (Object[]) obj;
				Double pcA = Double.parseDouble(String.valueOf(tempB[0]));// A时段已排产
				Double pcB = Double.parseDouble(String.valueOf(tempB[1]));// B时段已排产
				Double pcC = Double.parseDouble(String.valueOf(tempB[2]));// C时段已排产
				Double zzTemp = Double.parseDouble(String.valueOf(tempB[3]));// 总装标准工时
				Double bzTemp = Double.parseDouble(String.valueOf(tempB[4]));// 包装标准工时
				// 默认标准工时配置
				if ("1".endsWith(gsType)) {
					zzTemp = Double.parseDouble(String.valueOf(zz));
					bzTemp = Double.parseDouble(String.valueOf(bz));
				} else if ("0".equals(gsType)) {
					if (zzTemp.compareTo(new Double(0)) <= 0) {
						zzTemp = Double.parseDouble(String.valueOf(zz));
					}
					if (bzTemp.compareTo(new Double(0)) <= 0) {
						bzTemp = Double.parseDouble(String.valueOf(bz));
					}
				}
				if (ltxa1.indexOf("总装") > -1) {
					cnA = cnA - pcA * zzTemp;
					cnB = cnB - pcB * zzTemp;
					cnC = cnC - pcC * zzTemp;
				}
				if (ltxa1.indexOf("包装") > -1) {
					cnA = cnA - pcA * bzTemp;
					cnB = cnB - pcB * bzTemp;
					cnC = cnC - pcC * bzTemp;
				}
			}
		}
		listB = null;
		if (cnA.compareTo(0D) < 0) {
			cnA = new Double(0);
		}
		if (cnB.compareTo(0D) < 0) {
			cnB = new Double(0);
		}
		if (cnC.compareTo(0D) < 0) {
			cnC = new Double(0);
		}
		// 计算当前线别标准工时
		Double zgs = new Double(0);
		if (ltxa1.indexOf("总装") > -1) {
			zgs = zgs + zzgs;
		}
		if (ltxa1.indexOf("包装") > -1) {
			zgs = zgs + bzgs;
		}
		int index = 1;// 临时ID
		List<ZPPT0033> advice = new ArrayList<ZPPT0033>();
		Double productAmt = "bz".equals(gxb) ? productAmtBz : productAmtZz;
		ZPPT0033 firstPlan = getSinglePlan(zrq, zgs, cnA, cnB, cnC, productAmt,
				cnAll);
		firstPlan.setAufnr(aufnr);
		firstPlan.setLtxa1(ltxa1);
		firstPlan.setMandt(getClient());
		firstPlan.setZxh(zxh);
		firstPlan.setZyxj(zyxj);
		firstPlan.setZscrwd(String.valueOf(index));
		Double todayAll = firstPlan.getGamng01() + firstPlan.getGamng02()
				+ firstPlan.getGamng03();
		productAmt = productAmt - todayAll;
		index++;
		if (todayAll.compareTo(0D) > 0) {
			advice.add(firstPlan);
		}
		if (productAmt.compareTo(0D) > 0) {
			String sqlC = "select zrq,gamng1,gamng2,gamng3,gamng1+gamng2+gamng3 from zppt0034 where zxh = '"
					+ zxh + "' and zrq>'" + zrq + "' order by zrq";
			List listC = findListBySql(sqlC);
			if (null != listC && listC.size() > 0) {
				for (Object obj : listC) {
					Object[] tempC = (Object[]) obj;
					if (productAmt.compareTo(0D) > 0) {
						String dateTemp = String.valueOf(tempC[0]);
						Double cnATemp = Double.parseDouble(String
								.valueOf(tempC[1]));// 产能A
						Double cnBTemp = Double.parseDouble(String
								.valueOf(tempC[2]));// 产能B
						Double cnCTemp = Double.parseDouble(String
								.valueOf(tempC[3]));// 产能C
						Double cnAllTemp = Double.parseDouble(String
								.valueOf(tempC[4]));// 总产能
						ZPPT0033 subPlan = getSinglePlan(dateTemp, zgs,
								cnATemp, cnBTemp, cnCTemp, productAmt,
								cnAllTemp);
						productAmt = productAmt - subPlan.getGamng01()
								- subPlan.getGamng02() - subPlan.getGamng03();
						subPlan.setAufnr(aufnr);
						subPlan.setLtxa1(ltxa1);
						subPlan.setMandt(getClient());
						subPlan.setZxh(zxh);
						subPlan.setZyxj(zyxj);
						subPlan.setZscrwd(String.valueOf(index));
						index++;
						advice.add(subPlan);
					} else {
						break;
					}
				}
			}
		}
		result.put("res","success");
		result.put("data",advice);
		return result;
	}
	
	private ZPPT0033 getSinglePlan(String date, Double zgs, Double cnA,
			Double cnB, Double cnC, Double productAmt, Double cnAll){
		ZPPT0033 subPlan = new ZPPT0033();
		subPlan.setGstrp(date);
		Double planA = new Double(0);
		Double planB = new Double(0);
		Double planC = new Double(0);
		// 计算当天剩余排产数量
		Double amtA = getRound(cnA / zgs, ROUND);
		Double amtB = getRound(cnB / zgs, ROUND);
		Double amtC = getRound(cnC / zgs, ROUND);
		if (productAmt.compareTo(amtA) < 0) {
			planA = productAmt;
		} else if (productAmt.compareTo(amtA) >= 0
				&& productAmt.compareTo(amtA + amtB) < 0) {
			planA = amtA;
			planB = productAmt - planA;
		} else if (productAmt.compareTo(amtA + amtB) >= 0
				&& productAmt.compareTo(amtA + amtB + amtC) < 0) {
			planA = amtA;
			planB = amtB;
			planC = productAmt - planA - planB;
		} else {
			planA = amtA;
			planB = amtB;
			planC = amtC;
		}
		subPlan.setGamng01(planA);
		subPlan.setGamng02(planB);
		subPlan.setGamng03(planC);
		Double amtToday = planA + planB + planC;
		Double scfhToday = (zgs * amtToday / cnAll) * 100; // 负荷=标准工时×排产量/总产能
		subPlan.setZscfh(String.valueOf(roundHalfUp(scfhToday, 1)));// 保留小数点后一位,四舍五入
		return subPlan;
	}
	
	private static Double roundHalfUp(Double value, int scale) {
		BigDecimal b = new BigDecimal(value.toString());
		BigDecimal one = new BigDecimal("1");
		return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}
	
	private static Double getRound(Double value, int round) {
		Double result = roundUp(value / round, 0) * round;
		return result > 0 ? result : (new Double(0));
	}
	
	private static Double roundUp(Double value, int scale) {
		BigDecimal b = new BigDecimal(value.toString());
		BigDecimal one = new BigDecimal("1");
		return b.divide(one, scale, BigDecimal.ROUND_UP).doubleValue();
	}
	
	private static String formateInt(String num) {
		if ("0".equals(num) || StringUtils.isBlank(num)) {
			return "0";
		} else {
			while (StringUtils.isNotBlank(num) && num.length() > 1
					&& num.startsWith("0")) {
				num = num.replaceFirst("0", "");
			}
			try {
				int t = Integer.parseInt(num);
				if (t < 0) {
					return "e";
				}
			} catch (Exception e) {
				return "e";
			}
			return num;
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Map<?, ?> saveSubPlan(String zscrwd, String aufnr, String zxh, String ltxa1, String gstrp, String gamng01,
			String gamng02, String gamng03, String gsType, String zzDefalt, String bzDefalt) {
		Map result = new HashMap();
		String error = "";
		Integer zid = Integer.parseInt(zscrwd);
		boolean isAdd = zid.compareTo(10000) < 0;
		if (StringUtils.isBlank(zxh)) {
			result.put("res", "fail");
			result.put("msg", "请填写线号!");
			return result;
		}
		if (StringUtils.isBlank(gstrp)) {
			result.put("res", "fail");
			result.put("msg", "请填写生产日期!");
			return result;
		}
		String gamng1 = formateInt(gamng01);
		String gamng2 = formateInt(gamng02);
		String gamng3 = formateInt(gamng03);
		if ("e".equals(gamng1) || "e".equals(gamng2) || "e".equals(gamng3)) {
			error = "排产数量格式错误";
		}else{
			Double all = Double.parseDouble(gamng01)+Double.parseDouble(gamng02)+Double.parseDouble(gamng03);
			error = checkIsOverPlan(aufnr,zscrwd,all,gsType,zzDefalt,bzDefalt);
		}
		if (StringUtils.isNotBlank(error)) {
			result.put("res", "fail");
			result.put("msg", error);
			return result;
		}
		result.put("a", gamng1);
		result.put("b", gamng2);
		result.put("c", gamng3);
		// 查询线号对应的工序别
		String sqla = "select distinct a.ltxa1 from ZPPT0034 a where a.zxh ='"
				+ zxh.trim() + "' and zrq = '20130101'";
		List<Object> list = findListBySql(sqla);
		if (null != list && list.size() > 0) {
			ltxa1 = list.get(0).toString();
		}
		// 查询任务优先级
		String sqlb = "select zyxj from ZPPT0031 where aufnr = '"
				+ aufnr.trim() + "'";
		Object obj = findListBySql(sqlb).get(0);
		String yxj = "100";
		if (null != obj) {
			yxj = obj.toString();
		}
		ZPPT0033 subPlan = new ZPPT0033();
		if (isAdd) {
			// 新增
			subPlan = new ZPPT0033();
		} else {
			// 修改
			subPlan = new ZPPT0033();
			List li = findListBySql("select zscrwd from zppt0033 where zscrwd="+zscrwd+" and mandt="+getClient());
			if (null == li||li.size()==0) {
				error = "查找不到当前记录:" + zscrwd;
			}else{
				subPlan.setZscrwd(StringUtils.nullToString(li.get(0)));
			}
		}
		//result.put("wgl",String.valueOf(subPlan.getZwgl()==null?"0":subPlan.getZwgl().intValue()));
		result.put("ltxa1", ltxa1);
		subPlan.setAufnr(aufnr);
		subPlan.setMandt(getClient());// client
		subPlan.setZscrwd(zscrwd);
		subPlan.setLtxa1(ltxa1);
		subPlan.setZxh(zxh);
		subPlan.setZyxj(yxj);
		subPlan.setGstrp(gstrp.replaceAll("-",""));
//		try {// 生产日期
//			subPlan.setGstrp(DateUtils.formatDate(
//					DateUtils.parseDate(gstrp, "yyyy-MM-dd"), "yyyyMMdd"));
//		} catch (ParseException e) {
//			error = "日期格式错误!";
//		}
		subPlan.setGamng01(Double.parseDouble(gamng01));
		subPlan.setGamng02(Double.parseDouble(gamng02));
		subPlan.setGamng03(Double.parseDouble(gamng03));
		//subPlan.setZwgl(subPlan.getZwgl()==null?new Double(0):subPlan.getZwgl());
		Double amt = subPlan.getGamng01() + subPlan.getGamng02()
				+ subPlan.getGamng03();

		// 查找当前订单所需工时
		Double zzgs = 0D;// 总装工时
		Double bzgs = 0D;// 包装工时
		//启用默认工时
		if (StringUtils.isNotBlank(gsType)&&"1".equals(gsType)) {
			zzgs = Double.parseDouble(zzDefalt);
			bzgs = Double.parseDouble(bzDefalt);
		}else{
			String gsSql = "select vgw01,vgw02 from ZPPT0030 where aufnr = '" + aufnr.trim() + "' and mandt="+getClient();
			Object[] gstemp = (Object[])findListBySql(gsSql).get(0);
			zzgs = Double.parseDouble(String.valueOf(gstemp[0]));// 总装工时
			bzgs = Double.parseDouble(String.valueOf(gstemp[1]));// 包装工时
		}
		//难易度
		Double level = 1D;
		String levelSql = "select zlevel from ZPPT0031 where aufnr = '"+ aufnr.trim() + "' and mandt="+getClient();
		try{
			Object lvTemp = findListBySql(levelSql).get(0);
			if(null!=lvTemp){
				Double lv = Double.parseDouble(lvTemp.toString());
				if(lv.compareTo(new Double(0))>0){
					level = lv;
				}
			}
		}catch (Exception e){
		}
		zzgs = zzgs/level;
		bzgs = bzgs/level;
		
		Double zgs = new Double(0);// 总工时

		String cnSql = "select ltxa1,gamng1+gamng2+gamng3 from ZPPT0034 where zrq ='"
				+ gstrp.replaceAll("-", "") + "' and zxh = '" + zxh.trim() + "'";
		List cnList = findListBySql(cnSql);
		if(null==cnList || cnList.isEmpty()){
			result.put("res", "fail");
			result.put("msg", "当前日期("+gstrp+")没有可用产能!");
			return result;
		}
		Object[] cntemp = (Object[]) cnList.get(0);
		String gxb = String.valueOf(cntemp[0]);
		Double zcn = Double.parseDouble(String.valueOf(cntemp[1]));// 总产能
		if (gxb.indexOf("总装") > -1) {
			zgs = zgs + amt * zzgs;
		}
		if (gxb.indexOf("包装") > -1) {
			zgs = zgs + amt * bzgs;
		}

		Double zfh = (zgs / zcn) * 100; // 负荷=总工时/总产能
		subPlan.setZscfh(String.valueOf(roundHalfUp(zfh, 1)));

		if (StringUtils.isBlank(error)) {
			if (isAdd) {
				zscrwd = addSubPlan(subPlan);
			} else {
				updateSubPlan(subPlan);
			}
		}else{
			result.put("res", "fail");
			result.put("msg", error);
			return result;
		}
		// 查询当前线别总生产负荷传回前台
		String fhsql = "select sum(gamng01)||','||sum(gamng02)||','||sum(gamng03)||','||sum(zscfh) from zppt0033 where zxh = '"
				+ zxh.trim() + "' and gstrp = '" + subPlan.getGstrp() + "'";
		String zscfh = StringUtils.nullToString(findListBySql(fhsql).get(0));
		result.put("zscfh", zscfh);
		result.put("zscrwd", zscrwd);
		// 更新静态表中已排产数量、线别生产日期、状态等字段
		String sqlc = "select ltxa1,gamng01+gamng02+gamng03,zxh,to_char(to_date(gstrp,'yyyy-MM-dd'),'yyyy-MM-dd'),nvl(zwgl,0) from ZPPT0033 a where aufnr = '"
			+ aufnr.trim() + "' order by gstrp,zxh";
		List<Object> listc = findListBySql(sqlc);
		Integer zz = 0;// 总装已排产数量(全部排产记录)
		Integer bz = 0;// 包装已排产数量(全部排产记录)
		Integer zzdq = 0;// 总装当前排产数量(今天之后排产记录)
		Integer bzdq = 0;// 包装当前排产数量(今天之后排产记录)
		Integer zzwg = 0;// 总装完工量
		Integer bzwg = 0;// 包装完工量
		List<String> xhlist = new ArrayList<String>();
		Map<String, String> xbrq = new HashMap<String, String>();
		if (null != listc && listc.size() > 0) {
			for (Object object : listc) {
				Object[] temp = (Object[]) object;
				String gx = String.valueOf(temp[0]);
				Integer cl = Integer.parseInt(String.valueOf(temp[1]));
				String xh = String.valueOf(temp[2]);
				String rq = String.valueOf(temp[3]);
				Integer wgl = Integer.parseInt(String.valueOf(temp[4]));
				if (gx.indexOf("总装") > -1) {
					zz += cl;
					zzwg += wgl;
					if (wgl.compareTo(0)==0) {
						zzdq += cl;
					}
				}
				if (gx.indexOf("包装") > -1) {
					bz += cl;
					bzwg += wgl;
					if (wgl.compareTo(0)==0) {
						bzdq += cl;
					}
				}
				if (!xhlist.contains(xh)) {
					xhlist.add(xh);
				}
				if (!xbrq.containsKey(xh)) {
					xbrq.put(xh, rq);
				} else {
					String tempdate = xbrq.get(xh);
					xbrq.put(xh, tempdate + "," + rq);
				}
			}
		}

		String sqld = "select gamng from zppt0030 where aufnr = '"
				+ aufnr.trim() + "'";
		Object jhcl = findListBySql(sqld).get(0);
		String jhcltemp = jhcl.toString();
		if (jhcltemp.indexOf(".") > 0) {
			jhcltemp = jhcltemp.substring(0, jhcltemp.indexOf("."));
		}
		Integer compare = Integer.parseInt(jhcltemp);
		Integer zzwwc = 0;// 总装未完成量
		Integer bzwwc = 0;// 包装未完成量
		if (zzgs.compareTo(new Double(0)) != 0) {
			zzwwc = compare - zzwg;
		}
		if (bzgs.compareTo(new Double(0)) != 0) {
			bzwwc = compare - bzwg;
		}
		ZPPT0031 planDetail = new ZPPT0031();
		planDetail.setAufnr(aufnr);
		String ypcl = zz.toString() + "|" + bz.toString();
		// 当前排产量
		planDetail.setZdqpcl(zzdq.toString() + "|" + bzdq.toString());
		// 完成量
		planDetail.setZwcl(zzwg.toString() + "|" + bzwg.toString());
		// 未完成量
		planDetail.setZwwcl(zzwwc.toString() + "|" + bzwwc.toString());
		// 当前排产量
		planDetail.setZdqpcl(zzdq.toString() + "|" + bzdq.toString());
		// 累计排产量
		planDetail.setZypcl(ypcl);
		result.put("ypcl", ypcl);//累计排产
		result.put("zwcl", zzwg.toString() + "|" + bzwg.toString());
		result.put("zwwcl", zzwwc.toString() + "|" + bzwwc.toString());
		result.put("zdqpcl", zzdq.toString() + "|" + bzdq.toString());
		if (zz.compareTo(compare) == 0 && bz.compareTo(compare) == 0) {
			planDetail.setZzt("2");// 已排满
			result.put("zzt", "2");
		} else if (zz.compareTo(new Integer(0)) == 0
				&& bz.compareTo(new Integer(0)) == 0) {
			planDetail.setZzt("0");// 未安排
			result.put("zzt", "0");
		}else if (zz.compareTo(compare) < 0 || bz.compareTo(compare) < 0) {
			planDetail.setZzt("1");// 未排满
			result.put("zzt", "1");
		} else if (zz.compareTo(compare) > 0 || bz.compareTo(compare) > 0) {
			planDetail.setZzt("3");// 排产超量
			result.put("zzt", "3");
		} 
		StringBuffer xbrqResult = new StringBuffer();
		for (String xbtemp : xhlist) {
			xbrqResult.append(xbtemp + ":");
			xbrqResult.append(xbrq.get(xbtemp));
			xbrqResult.append(";");
		}
		String zxhscrq=gstrp+":";
		if(!"0".equals(gamng01)){
			zxhscrq = zxhscrq + "A,"+gamng01+";";
		}
		if(!"0".equals(gamng02)){
			zxhscrq = zxhscrq + "B,"+gamng02+";";
		}
		if(!"0".equals(gamng03)){
			zxhscrq = zxhscrq + "C,"+gamng03+";";
		}
		planDetail.setZxhscrq(zxhscrq);
		result.put("zxhscrq", planDetail.getZxhscrq());
		String sqlE = "select 1 from zppt0030 m where m.aufnr = '"
				+ aufnr
				+ "' and exists (select 1 from zppt0033 s where s.aufnr = m.aufnr and s.gstrp>m.edatu)";
		List<Object> tempE = findListBySql(sqlE);
		if ((null != tempE && tempE.size() > 0)
				&&(zz.compareTo(compare) >= 0 && bz.compareTo(compare) >= 0)) {// 排产超交期,modify 20130917 未排满的不参与计算
			planDetail.setZzt("5");
			result.put("zzt", "5");
		}
		updatePlanDetail(planDetail);
		result.put("res","success");
		return result;
	}
	
	private void updateSubPlan(ZPPT0033 subPlan) {
		if(null==subPlan||StringUtils.isBlank(subPlan.getZscrwd()))
			return;
		StringBuffer update = new StringBuffer("update zppt0033 set mandt=mandt");
		if(StringUtils.isNotBlank(subPlan.getZxh()))
			update.append(" ,zxh='"+subPlan.getZxh()+"'");
		if(StringUtils.isNotBlank(subPlan.getLtxa1()))
			update.append(" ,ltxa1='"+subPlan.getLtxa1()+"'");
		if(StringUtils.isNotBlank(subPlan.getGstrp()))
			update.append(" ,gstrp='"+subPlan.getGstrp()+"'");
		if(subPlan.getGamng01()!=null)
			update.append(" ,gamng01='"+subPlan.getGamng01()+"'");
		if(subPlan.getGamng02()!=null)
			update.append(" ,gamng02='"+subPlan.getGamng02()+"'");
		if(subPlan.getGamng03()!=null)
			update.append(" ,gamng03='"+subPlan.getGamng03()+"'");
		if(StringUtils.isNotBlank(subPlan.getZscfh()))
			update.append(" ,zscfh='"+subPlan.getZscfh()+"'");
		if(null!=subPlan.getZwgl())
			update.append(" ,zwgl='"+subPlan.getZwgl()+"'");
		update.append(" where zscrwd='"+subPlan.getZscrwd()+"' and mandt="+getClient());
		execSql(update.toString());
	}

	@Override
	public String addSubPlan(ZPPT0033 subPlan) {
		if(null==subPlan)
			return null;
		String zscrwd = getSubPlanNextId();
		StringBuffer insert = new StringBuffer("insert into zppt0033(mandt,aufnr,zscrwd,zyxj,zxh,ltxa1,gstrp,gamng01,gamng02,gamng03,zscfh,zwgl) values(");
		insert.append(getClient())
			.append(",'"+subPlan.getAufnr()+"'")
			.append(",'"+zscrwd+"'")
			.append(",'"+subPlan.getZyxj()+"'")
			.append(",'"+subPlan.getZxh()+"'")
			.append(",'"+subPlan.getLtxa1()+"'")
			.append(",'"+subPlan.getGstrp()+"'")
			.append(",'"+subPlan.getGamng01()+"'")
			.append(",'"+subPlan.getGamng02()+"'")
			.append(",'"+subPlan.getGamng03()+"'")
			.append(",'"+subPlan.getZscfh()+"'")
			.append(",0)");
		try {
			execSql(insert.toString());
			return zscrwd;
		} catch (Exception e) {
		}
		return null;
	}
	
	private String getSubPlanNextId() {
		int current = 1000000000;
		Object max = findListBySql("select max(zscrwd) from ZPPT0033 where mandt="+getClient()).get(0);
		if (null != max && StringUtils.isNotBlank(max.toString())) {
			current = Integer.parseInt(max.toString());
		}
		return String.valueOf(current+1);
	}

	@Override
	public void updatePlanDetail(ZPPT0031 plan) {
		if(plan==null || StringUtils.isBlank(plan.getAufnr()))
			return;
		StringBuffer update = new StringBuffer("update zppt0031 set mandt=mandt");
		if(StringUtils.isNotBlank(plan.getZdqpcl()))
			update.append(" ,zdqpcl='"+plan.getZdqpcl()+"'");
		if(StringUtils.isNotBlank(plan.getZwcl()))
			update.append(" ,zwcl='"+plan.getZwcl()+"'");
		if(StringUtils.isNotBlank(plan.getZwwcl()))
			update.append(" ,zwwcl='"+plan.getZwwcl()+"'");
		if(StringUtils.isNotBlank(plan.getZypcl()))
			update.append(" ,zypcl='"+plan.getZypcl()+"'");
		if(null!=plan.getZxhscrq()&&!"".equals(plan.getZxhscrq()))
			update.append(" ,zxhscrq='"+plan.getZxhscrq()+"'");
		if(StringUtils.isNotBlank(plan.getZzt()))
			update.append(" ,zzt='"+plan.getZzt()+"'");
		if(StringUtils.isNotBlank(plan.getZqt()))
			update.append(" ,zqt='"+plan.getZqt()+"'");
		if(StringUtils.isNotBlank(plan.getZlevel()))
			update.append(" ,zlevel='"+plan.getZlevel()+"'");
		if(StringUtils.isNotBlank(plan.getZwgpd()))
			update.append(" ,zwgpd='"+plan.getZwgpd()+"'");
		if(StringUtils.isNotBlank(plan.getZsg()))
			update.append(" ,zsg='"+plan.getZsg()+"'");
		if(StringUtils.isNotBlank(plan.getZyxj()))
			update.append(" ,zyxj='"+StringUtils.addLeftZero(plan.getZyxj(),4)+"'");
		update.append(" where aufnr='"+plan.getAufnr()+"' and mandt="+getClient());
		execSql(update.toString());
	}

	@SuppressWarnings("rawtypes")
	private String checkIsOverPlan(String aufnr, String zscrwd, Double amt, String gsType, String zzDefalt, String bzDefalt) {
		String sqlB = "select vgw01,vgw02,gamng from zppt0030 where aufnr='"
				+ aufnr + "'";
		String sqlC = "select nvl(sum(case when ltxa1 like '%总装%' then gamng01+gamng02+gamng03 else 0 end),0) as zz,nvl(sum(case when ltxa1 like '%包装%' then gamng01+gamng02+gamng03 else 0 end),0) as bz from zppt0033 where aufnr = '"
				+ aufnr + "' and zscrwd <>'" + zscrwd + "' ";
		Object plan = findListBySql(sqlB).get(0);
		Object[] planAmt = (Object[]) plan;
		Double zzgs = Double.parseDouble(String.valueOf(planAmt[0]));//总装工时
		Double bzgs = Double.parseDouble(String.valueOf(planAmt[1]));//包装工时
		//启用默认工时
		if (StringUtils.isNotBlank(gsType)) {
			if ("1".equals(gsType) || (zzgs.compareTo(new Double(0)) <= 0)) {
				zzgs = Double.parseDouble(zzDefalt);
			}
			if ("1".equals(gsType) || (bzgs.compareTo(new Double(0)) <= 0)) {
				bzgs = Double.parseDouble(bzDefalt);
			}
		}
		
		Double zzAmt = new Double(0);
		if(zzgs.compareTo(new Double(0))>0){
			zzAmt = Double.parseDouble(String.valueOf(planAmt[2]));// 总装计划
		}
		Double bzAmt = new Double(0);
		if(bzgs.compareTo(new Double(0))>0){
			bzAmt = Double.parseDouble(String.valueOf(planAmt[2]));// 包装计划
		}
		List actList = findListBySql(sqlC);
		if (null != actList && actList.size() > 0) {
			for (Object o : actList) {
				Object[] acts = (Object[]) o;
				zzAmt = zzAmt
						- Double.parseDouble(String
								.valueOf(acts[0] == null ? "0" : acts[0]));
				bzAmt = bzAmt
						- Double.parseDouble(String
								.valueOf(acts[1] == null ? "0" : acts[1]));
			}
		}
		if (zzAmt.compareTo(amt) < 0 && bzAmt.compareTo(amt) < 0) {
			return "排产量不能超出订单计划量!";
		} else {
			return "";
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Map<?, ?> deleteSubPlan(String zscrwd, String aufnr, String gsType, String zzDefalt, String bzDefalt) {
		Map result = new HashMap();
		// 查找当前订单所需工时
		Double zzgs = 0D;// 总装工时
		Double bzgs = 0D;// 包装工时
		//启用默认工时
		if (StringUtils.isNotBlank(gsType)&&"1".equals(gsType)) {
			zzgs = Double.parseDouble(zzDefalt);
			bzgs = Double.parseDouble(bzDefalt);
		}else{
			String gsSql = "select vgw01,vgw02 from ZPPT0030 where aufnr = '" + aufnr.trim() + "' and mandt="+getClient();
			Object[] gstemp = (Object[])findListBySql(gsSql).get(0);
			zzgs = Double.parseDouble(String.valueOf(gstemp[0]));// 总装工时
			bzgs = Double.parseDouble(String.valueOf(gstemp[1]));// 包装工时
		}
		//难易度
		Double level = 1D;
		String levelSql = "select zlevel from ZPPT0031 where aufnr = '"+ aufnr.trim() + "' and mandt="+getClient();
		try{
			Object lvTemp = findListBySql(levelSql).get(0);
			if(null!=lvTemp){
				Double lv = Double.parseDouble(lvTemp.toString());
				if(lv.compareTo(new Double(0))>0){
					level = lv;
				}
			}
		}catch (Exception e){
		}
		zzgs = zzgs/level;
		bzgs = bzgs/level;
		// 查找需要删除的排产记录
		ZPPT0033 subPlanDel = findSubPlanById(zscrwd);
		if (null == subPlanDel) {
			result.put("res","fail");
			result.put("msg","根据生产任务单查找不到排产记录,请重新刷新页面!");
			return result;
		} else {
			String zxh = subPlanDel.getZxh();
			String gstrp = subPlanDel.getGstrp();
			// 1.删除排产记录
			deletePlanDetail(subPlanDel);
			// 查询当前线别总生产负荷传回前台
			String fhsql = "select sum(gamng01)||','||sum(gamng02)||','||sum(gamng03)||','||sum(zscfh) from zppt0033 where zxh = '"
					+ zxh.trim() + "' and gstrp = '" + gstrp + "'";
			String zscfh = StringUtils.nullToString(findListBySql(fhsql).get(0));
			result.put("zscfh", zscfh);
			result.put("zscrwd", zscrwd);
			// 更新静态表中已排产数量、线别生产日期、状态等字段
			String sqlc = "select ltxa1,gamng01+gamng02+gamng03,zxh,to_char(to_date(gstrp,'yyyy-MM-dd'),'yyyy-MM-dd'),nvl(zwgl,0) from ZPPT0033 a where aufnr = '"
				+ aufnr.trim() + "' order by gstrp,zxh";
			List<Object> listc = findListBySql(sqlc);
			Integer zz = 0;// 总装已排产数量(全部排产记录)
			Integer bz = 0;// 包装已排产数量(全部排产记录)
			Integer zzdq = 0;// 总装当前排产数量(今天之后排产记录)
			Integer bzdq = 0;// 包装当前排产数量(今天之后排产记录)
			Integer zzwg = 0;// 总装完工量
			Integer bzwg = 0;// 包装完工量
			List<String> xhlist = new ArrayList<String>();
			Map<String, String> xbrq = new HashMap<String, String>();
			if (null != listc && listc.size() > 0) {
				for (Object object : listc) {
					Object[] temp = (Object[]) object;
					String gx = String.valueOf(temp[0]);
					Integer cl = Integer.parseInt(String.valueOf(temp[1]));
					String xh = String.valueOf(temp[2]);
					String rq = String.valueOf(temp[3]);
					Integer wgl = Integer.parseInt(String.valueOf(temp[4]));
					if (gx.indexOf("总装") > -1) {
						zz += cl;
						zzwg += wgl;
						if (wgl.compareTo(0)==0) {
							zzdq += cl;
						}
					}
					if (gx.indexOf("包装") > -1) {
						bz += cl;
						bzwg += wgl;
						if (wgl.compareTo(0)==0) {
							bzdq += cl;
						}
					}
					if (!xhlist.contains(xh)) {
						xhlist.add(xh);
					}
					if (!xbrq.containsKey(xh)) {
						xbrq.put(xh, rq);
					} else {
						String tempdate = xbrq.get(xh);
						xbrq.put(xh, tempdate + "," + rq);
					}
				}
			}

			String sqld = "select gamng from zppt0030 where aufnr = '"
					+ aufnr.trim() + "'";
			Object jhcl = findListBySql(sqld).get(0);
			String jhcltemp = jhcl.toString();
			if (jhcltemp.indexOf(".") > 0) {
				jhcltemp = jhcltemp.substring(0, jhcltemp.indexOf("."));
			}
			Integer compare = Integer.parseInt(jhcltemp);
			Integer zzwwc = 0;// 总装未完成量
			Integer bzwwc = 0;// 包装未完成量
			if (zzgs.compareTo(new Double(0)) != 0) {
				zzwwc = compare - zzwg;
			}
			if (bzgs.compareTo(new Double(0)) != 0) {
				bzwwc = compare - bzwg;
			}
			ZPPT0031 planDetail = new ZPPT0031();
			planDetail.setAufnr(aufnr);
			String ypcl = zz.toString() + "|" + bz.toString();
			// 当前排产量
			planDetail.setZdqpcl(zzdq.toString() + "|" + bzdq.toString());
			// 完成量
			planDetail.setZwcl(zzwg.toString() + "|" + bzwg.toString());
			// 未完成量
			planDetail.setZwwcl(zzwwc.toString() + "|" + bzwwc.toString());
			// 当前排产量
			planDetail.setZdqpcl(zzdq.toString() + "|" + bzdq.toString());
			// 累计排产量
			planDetail.setZypcl(ypcl);
			result.put("ypcl", ypcl);//累计排产
			result.put("zwcl", zzwg.toString() + "|" + bzwg.toString());
			result.put("zwwcl", zzwwc.toString() + "|" + bzwwc.toString());
			result.put("zdqpcl", zzdq.toString() + "|" + bzdq.toString());
			if (zz.compareTo(compare) == 0 && bz.compareTo(compare) == 0) {
				planDetail.setZzt("2");// 已排满
				result.put("zzt", "2");
			} else if (zz.compareTo(new Integer(0)) == 0
					&& bz.compareTo(new Integer(0)) == 0) {
				planDetail.setZzt("0");// 未安排
				result.put("zzt", "0");
			} else if (zz.compareTo(compare) < 0 || bz.compareTo(compare) < 0) {
				planDetail.setZzt("1");// 未排满
				result.put("zzt", "1");
			} else if (zz.compareTo(compare) > 0 || bz.compareTo(compare) > 0) {
				planDetail.setZzt("3");// 排产超量
				result.put("zzt", "3");
			} 
			StringBuffer xbrqResult = new StringBuffer();
			if(null!=xhlist && xhlist.size()>0){
				for (String xbtemp : xhlist) {
					xbrqResult.append(xbtemp + ":");
					xbrqResult.append(xbrq.get(xbtemp));
					xbrqResult.append(";");
				}
				planDetail.setZxhscrq(xbrqResult.substring(0,
						xbrqResult.length() - 1));
			}else{
				planDetail.setZxhscrq(" ");
			}
			result.put("zxhscrq", planDetail.getZxhscrq());
			String sqlE = "select 1 from zppt0030 m where m.aufnr = '"
					+ aufnr
					+ "' and exists (select 1 from zppt0033 s where s.aufnr = m.aufnr and s.gstrp>m.edatu)";
			List<Object> tempE = findListBySql(sqlE);
			if ((null != tempE && tempE.size() > 0)
					&&(zz.compareTo(compare) >= 0 && bz.compareTo(compare) >= 0)) {// 排产超交期,modify 20130917 未排满的不参与计算
				planDetail.setZzt("5");
				result.put("zzt", "5");
			}
			updatePlanDetail(planDetail);
			result.put("res","success");
		}
		return result;
	}

	private void deletePlanDetail(ZPPT0033 plan) {
		if(null==plan || StringUtils.isBlank(plan.getZscrwd()))
			return;
		String delete = "delete from zppt0033 where zscrwd='"+plan.getZscrwd()+"' and mandt="+getClient();
		execSql(delete);
	}

	@Override
	public ZPPT0033 findSubPlanById(String zscrwd) {
		String sql = "select mandt,aufnr,zscrwd,zyxj,zxh,ltxa1,gstrp,gamng01,gamng02,gamng03,zscfh from zppt0033 where zscrwd='"+zscrwd+"' and mandt="+getClient();
		List<Object> list = findListBySql(sql);
		if(null==list||list.size()==0)
			return null;
		Object[] temp = (Object[])list.get(0);
		ZPPT0033 plan = new ZPPT0033();
		plan.setMandt(StringUtils.nullToString(temp[0]));
		plan.setAufnr(StringUtils.nullToString(temp[1]));
		plan.setZscrwd(StringUtils.nullToString(temp[2]));
		plan.setZyxj(StringUtils.nullToString(temp[3]));
		plan.setZxh(StringUtils.nullToString(temp[4]));
		plan.setLtxa1(StringUtils.nullToString(temp[5]));
		plan.setGstrp(StringUtils.nullToString(temp[6]));
		plan.setGamng01(Double.valueOf(StringUtils.nullToString(temp[7])));
		plan.setGamng02(Double.valueOf(StringUtils.nullToString(temp[8])));
		plan.setGamng03(Double.valueOf(StringUtils.nullToString(temp[9])));
		plan.setZscfh(StringUtils.nullToString(temp[10]));
		return plan;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Map<?, ?> planGather(String zxh, String start) {
		Map result = new HashMap();
		String finisPlan = checkExistFinishPlan(zxh,start,null);
		if(StringUtils.isNotBlank(finisPlan)){
			String message = "订单"+finisPlan+"已完工，或在选定时间内有报工记录，请修改!";
			result.put("res", "fail");
			result.put("msg", message);
			return result;
		}
		//操作前查询当前线别排产总量
		Map<String,String> compareMap = new HashMap<String,String>();
		String comSql = "select aufnr,sum(gamng01+gamng02+gamng03) from zppt0033 where zxh = '"+zxh+"' and gstrp >= '"+start.replaceAll("-", "")+"' group by aufnr";
		List compList = findListBySql(comSql);
		if(null!=compList && compList.size()>0){
			for(Object ob:compList){
				Object[] temp = (Object[]) ob;
				compareMap.put(StringUtils.nullToString(temp[0]), StringUtils.nullToString(temp[1]));
			}
		}
		
		String orderStr = "";
		//查询当前线别起始日期之后的排产记录（不包含完工记录）
		String sql = "select a.aufnr from zppt0033 a,zppt0030 b,zppt0031 c,"
				+ " (select max(gstrp) gstrp,zxh,aufnr from zppt0033 d "
				+ " where d.zxh='"
				+ zxh
				+ "' and d.gstrp<='"
				+ start.replaceAll("-", "")
				+ "' and d.zwgl>0 group by d.zxh,d.aufnr) tb"
				+ " where a.zxh='"
				+ zxh
				+ "' and a.gstrp>='"
				+ start.replaceAll("-", "")
				+ "' and nvl(a.zwgl,0)=0 "
				+ " and a.aufnr = tb.aufnr(+)"
				+ " and a.aufnr = b.aufnr"
				+ " and a.aufnr = c.aufnr"
				+ " order by a.gstrp asc,case"
				+ " when a.gamng02 = 0 and a.gamng03 = 0 then 0"
				+ " when a.gamng02 = 0 and a.gamng03 > 0 then 3"
				+ " when a.gamng02 > 0 and a.gamng03 > 0 then 2"
				+ " when a.gamng02 = 0 and a.gamng03 > 0 then 1"
				+ " else 0 end asc,case"
				+ " when a.gamng01 = 0 and a.gamng02 = 0 then 0"
				+ " when a.gamng01 = 0 and a.gamng02 > 0 then 3"
				+ " when a.gamng01 > 0 and a.gamng02 > 0 then 2"
				+ " when a.gamng01 = 0 and a.gamng02 > 0 then 1"
				+ " else 0 end asc,"
				+ " nvl(tb.gstrp,'00000000') desc,c.zyxj asc,b.edatu asc,b.aufnr asc";
		List<Object> li =findListBySql(sql);
		//按顺序组装订单列表
		List<String> aufnrs = new ArrayList<String>();
		if(null!=li && li.size()>0){
			for(Object o:li){
				if(!aufnrs.contains(o.toString())){
					aufnrs.add(o.toString());
					orderStr = orderStr + "," + o.toString();
				}
			}
		}
		//查询当前线别可排产日期列表
		String dayQuery = "select zrq,ltxa1 from zppt0034 where zxh='"+zxh+"' and zrq>='"+start.replaceAll("-", "")+"' order by zrq";
		List days = findListBySql(dayQuery);
		
		if(days==null||days.size()<aufnrs.size()){
			result.put("res", "fail");
			result.put("msg", "标准产能可用剩余天数不足以进行汇总操作，请维护!");
			return result;
		}
		int dayIndex=0;
		if(aufnrs.size()>0){
			for (String aufnr : aufnrs) {
				//查询当前订单总排产量
				String query = "select nvl(sum(gamng01+gamng02+gamng03),0) from zppt0033 where zxh='"
						+ zxh
						+ "' and gstrp>='"
						+ start.replaceAll("-", "")
						+ "' and nvl(zwgl,0)=0 and aufnr='" 
						+ aufnr + "'";
				Object temp = findListBySql(query).get(0);
				Double amt = Double.parseDouble(temp.toString());
				//删除当前订单排产数据
				String delete = "delete from zppt0033 where zxh='"
						+ zxh
						+ "' and gstrp>='"
						+ start.replaceAll("-", "")
						+ "' and nvl(zwgl,0)=0 and aufnr='" 
						+ aufnr + "'";
				execSql(delete);
				if(amt.compareTo(new Double(0))>0){
					String day = StringUtils.nullToString(((Object[])days.get(dayIndex))[0]);
					ZPPT0033 add = new ZPPT0033();
					add.setAufnr(aufnr);
					add.setGamng02(amt);
					add.setGamng01(new Double(0));
					add.setGamng03(new Double(0));
					add.setGstrp(day);
					add.setLtxa1(StringUtils.nullToString(((Object[])days.get(dayIndex))[1]));
					add.setMandt(getClient());
					add.setZscfh(caculateLoad(aufnr,day,zxh,amt,"1","0.5","0.5"));
					add.setZwgl(new Double(0));
					add.setZxh(zxh);
					add.setZyxj("0100");
					addSubPlan(add);
					updatePlanDetailStatus(aufnr,day,add.getGamng01(),add.getGamng02(),add.getGamng03());
					dayIndex++;
				}
			}
		}
		//对比结果
		compList = findListBySql(comSql);
		String error = "";
		if(null!=compList && compList.size()>0){
			for(Object ob:compList){
				Object[] temp = (Object[]) ob;
				if(null==compareMap.get(temp[0].toString())
						||!temp[1].toString().equals(compareMap.get(temp[0].toString()))){
					error+="订单"+StringUtils.removeLeftZero(temp[0].toString())+"排产量有误;";
				}
			}
		}
		result.put("res", StringUtils.isBlank(error)?"success":"fail");
		error = StringUtils.isBlank(error)?"操作成功":error+"请手工修正!";
		result.put("msg", error);
		return result;
	}
	
	/**
	 * 
	 * 计算当前排产负荷占比
	 * 
	 * @param aufnr
	 * @param gstrp
	 * @param zxh
	 * @param amt
	 * @param gsType
	 * @param zzDefalt
	 * @param bzDefalt
	 * @return
	 */
	private String caculateLoad(String aufnr,String gstrp,String zxh,Double amt,String gsType,
			String zzDefalt, String bzDefalt){
		try{
			// 查找当前订单所需工时
			String gsSql = "select a.vgw01,a.vgw02,b.zlevel from ZPPT0030 a,zppt0031 b where a.aufnr=b.aufnr and a.aufnr = '"
				+ aufnr.trim() + "'";
			Double zzgs = new Double(0.5);// 总装工时
			Double bzgs = new Double(0.5);// 包装工时
			Double zlevel = new Double(1);// 包装工时
			Object[] gstemp = (Object[]) findListBySql(gsSql).get(0);
			zzgs = Double.parseDouble(StringUtils.nullToString(gstemp[0]));// 总装工时
			bzgs = Double.parseDouble(StringUtils.nullToString(gstemp[1]));// 包装工时
			zlevel = Double.parseDouble(StringUtils.nullToString(gstemp[2]));// 包装工时
			if("1".equals(gsType)){
				zzgs = Double.parseDouble(zzDefalt);
				bzgs = Double.parseDouble(bzDefalt);
			}
			Double zgs = new Double(0);// 总工时
			
			String cnSql = "select ltxa1,gamng1+gamng2+gamng3 from ZPPT0034 where zrq ='"
				+ gstrp + "' and zxh = '" + zxh.trim() + "'";
			Object[] cntemp = (Object[]) findListBySql(cnSql).get(0);
			String gxb = String.valueOf(cntemp[0]);
			Double zcn = Double.parseDouble(String.valueOf(cntemp[1]));// 总产能
			if (gxb.indexOf("总装") > -1) {
				zgs = zgs + amt * zzgs/zlevel;
			}
			if (gxb.indexOf("包装") > -1) {
				zgs = zgs + amt * bzgs/zlevel;
			}
			
			Double zfh = (zgs / zcn) * 100; // 负荷=总工时/总产能
			return String.valueOf(roundHalfUp(zfh, 1));
		}catch(Exception e){
			return "0";
		}
	}
	
	/**
	 * 
	 * 更新排产状态
	 * 
	 * @param aufnr
	 * @param zrq
	 * @param a
	 * @param b
	 * @param c
	 */
	private void updatePlanDetailStatus(String aufnr,String zrq,Double a,Double b,Double c) {
		String gsSql = "select vgw01,vgw02 from ZPPT0030 where aufnr = '"
				+ aufnr.trim() + "'";
		Object[] gstemp = (Object[]) findListBySql(gsSql).get(0);
		Double zzgs = Double.parseDouble(String.valueOf(gstemp[0]));// 总装工时
		Double bzgs = Double.parseDouble(String.valueOf(gstemp[1]));// 包装工时
		// 更新静态表中已排产数量、线别生产日期、状态等字段
		String sqlc = "select ltxa1,gamng01+gamng02+gamng03,zxh,to_char(to_date(gstrp,'yyyy-MM-dd'),'yyyy-MM-dd'),nvl(zwgl,0) from ZPPT0033 a where aufnr = '"
				+ aufnr.trim() + "' order by gstrp,zxh";
		List<Object> listc = findListBySql(sqlc);
		Integer zz = 0;// 总装已排产数量(全部排产记录)
		Integer bz = 0;// 包装已排产数量(全部排产记录)
		Integer zzdq = 0;// 总装当前排产数量(今天之后排产记录)
		Integer bzdq = 0;// 包装当前排产数量(今天之后排产记录)
		Integer zzwg = 0;// 总装完工量
		Integer bzwg = 0;// 包装完工量

		List<String> xhlist = new ArrayList<String>();
		Map<String, String> xbrq = new HashMap<String, String>();
		if (null != listc && listc.size() > 0) {
			for (Object object : listc) {
				Object[] temp = (Object[]) object;
				String gx = String.valueOf(temp[0]);
				Integer cl = Integer.parseInt(String.valueOf(temp[1]));
				String xh = String.valueOf(temp[2]);
				String rq = String.valueOf(temp[3]);
				Integer wgl = Integer.parseInt(String.valueOf(temp[4]));
				if (gx.indexOf("总装") > -1) {
					zz += cl;
					zzwg += wgl;
					if (wgl.compareTo(0)==0) {
						zzdq += cl;
					}
				}
				if (gx.indexOf("包装") > -1) {
					bz += cl;
					bzwg += wgl;
					if (wgl.compareTo(0)==0) {
						bzdq += cl;
					}
				}
				if (!xhlist.contains(xh)) {
					xhlist.add(xh);
				}
				if (!xbrq.containsKey(xh)) {
					xbrq.put(xh, rq);
				} else {
					String tempdate = xbrq.get(xh);
					xbrq.put(xh, tempdate + "," + rq);
				}
			}
		}

		String sqld = "select gamng from zppt0030 where aufnr = '"
				+ aufnr.trim() + "'";
		Object jhcl = findListBySql(sqld).get(0);
		String jhcltemp = jhcl.toString();
		if (jhcltemp.indexOf(".") > 0) {
			jhcltemp = jhcltemp.substring(0, jhcltemp.indexOf("."));
		}
		Integer compare = Integer.parseInt(jhcltemp);
		Integer zzwwc = 0;// 总装未完成量
		Integer bzwwc = 0;// 包装未完成量
		if (zzgs.compareTo(new Double(0)) != 0) {
			zzwwc = compare - zzwg;
		}
		if (bzgs.compareTo(new Double(0)) != 0) {
			bzwwc = compare - bzwg;
		}

		ZPPT0031 planDetail = new ZPPT0031(); 
		planDetail.setAufnr(aufnr.trim());
		String ypcl = zz.toString() + "|" + bz.toString();
		// 当前排产量
		planDetail.setZdqpcl(zzdq.toString() + "|" + bzdq.toString());
		// 完成量
		planDetail.setZwcl(zzwg.toString() + "|" + bzwg.toString());
		// 未完成量
		planDetail.setZwwcl(zzwwc.toString() + "|" + bzwwc.toString());
		// 当前排产量
		planDetail.setZdqpcl(zzdq.toString() + "|" + bzdq.toString());
		// 累计排产量
		planDetail.setZypcl(ypcl);
		if (zz.compareTo(compare) == 0 && bz.compareTo(compare) == 0) {
			planDetail.setZzt("2");// 已排满
		} else if (zz.compareTo(compare) < 0 || bz.compareTo(compare) < 0) {
			planDetail.setZzt("1");// 未排满
		} else if (zz.compareTo(compare) > 0 || bz.compareTo(compare) > 0) {
			planDetail.setZzt("3");// 排产超量
		} else if (zz.compareTo(new Integer(0)) == 0
				&& bz.compareTo(new Integer(0)) == 0) {
			planDetail.setZzt("0");// 未安排
		}
		StringBuffer xbrqResult = new StringBuffer();
		if (null != xhlist && xhlist.size() > 0) {
			for (String xbtemp : xhlist) {
				xbrqResult.append(xbtemp + ":");
				xbrqResult.append(xbrq.get(xbtemp));
				xbrqResult.append(";");
			}
			planDetail.setZxhscrq(xbrqResult.substring(0,
					xbrqResult.length() - 1));
		}else{
			planDetail.setZxhscrq(" ");
		}
		if(zrq!=null){
			try {
				String xhscrq = DateUtils.formatDate(DateUtils.parseDate(zrq, "yyyyMMdd"), "yyyy-MM-dd");
				xhscrq+=":";
				if(a>0){
					xhscrq= xhscrq + "A,"+a.intValue()+";";
				}
				if(b>0){
					xhscrq= xhscrq + "B,"+b.intValue()+";";
				}
				if(c>0){
					xhscrq= xhscrq + "C,"+c.intValue()+";";
				}
				planDetail.setZxhscrq(xhscrq);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		String sqlE = "select 1 from zppt0030 m where m.aufnr = '"
				+ aufnr
				+ "' and exists (select 1 from zppt0033 s where s.aufnr = m.aufnr and s.gstrp>m.edatu)";
		List<Object> tempE = findListBySql(sqlE);
		if ((null != tempE && tempE.size() > 0)
				&& (zz.compareTo(compare) >= 0 && bz.compareTo(compare) >= 0)) {
			// 排产超交期,modify20130917	未排满的不参与计算
			planDetail.setZzt("5");
		}
		updatePlanDetail(planDetail);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Map<?, ?> planSeprate(String zxh, String start, String gsType, String zzDefalt, String bzDefalt) {
		long begin = System.currentTimeMillis();
		Map result = new HashMap();
		String finisPlan = checkExistFinishPlan(zxh,start,null);
		if(StringUtils.isNotBlank(finisPlan)){
			String message = "订单"+finisPlan+"已完工，或在选定时间内有报工记录，请修改!";
			result.put("res", "fail");
			result.put("msg", message);
			return result;
		}
		//操作前查询当前线别排产总量
		Map<String,String> compareMap = new HashMap<String,String>();
		String comSql = "select aufnr,sum(gamng01+gamng02+gamng03) from zppt0033 where zxh = '"+zxh+"' and gstrp >= '"+start.replaceAll("-", "")+"' group by aufnr";
		List<Object> compList = findListBySql(comSql);
		if(null!=compList && compList.size()>0){
			for(Object ob:compList){
				Object[] temp = (Object[]) ob;
				compareMap.put(StringUtils.nullToString(temp[0]), StringUtils.nullToString(temp[1]));
			}
		}
		// 查找当前线别起始日期之后所有已排产订单列表
		// 优先级：排产日期>>订单优先级>>ABC班>>完工量>>订单号
		String sqlA = "select temp.*,m.vgw01,m.vgw02,s.zyxj from ("
				+ " select b.gstrp,b.aufnr,b.gamng01 amt,b.zscrwd,'A' as piriod from zppt0033 b where nvl(b.zwgl,0)=0 and zxh='"
				+ zxh
				+ "' and gstrp>='"
				+ start.replaceAll("-", "")
				+ "'"
				+ " union all"
				+ " select b.gstrp,b.aufnr,b.gamng02,b.zscrwd,'B' from zppt0033 b where nvl(b.zwgl,0)=0 and zxh='"
				+ zxh
				+ "' and gstrp>='"
				+ start.replaceAll("-", "")
				+ "'"
				+ " union all"
				+ " select b.gstrp,b.aufnr,b.gamng03,b.zscrwd,'C' from zppt0033 b where nvl(b.zwgl,0)=0 and zxh='"
				+ zxh
				+ "' and gstrp>='"
				+ start.replaceAll("-", "")
				+ "') temp,"
				+ " zppt0030 m,zppt0031 s,"
				+ "(select max(gstrp) gstrp,zxh,aufnr from zppt0033 d where d.zxh='"
				+ zxh
				+ "'and d.gstrp<='"
				+ start.replaceAll("-", "")
				+ "' and d.zwgl>0 group by d.zxh,d.aufnr) tb,"
				+ " zppt0030 ma,zppt0031 mb"
				+ " where temp.aufnr = m.aufnr"
				+ " and temp.aufnr = s.aufnr and temp.amt>0"
				+ " and tb.aufnr(+) = temp.aufnr"
				+ " and temp.aufnr = ma.aufnr"
				+ " and temp.aufnr = mb.aufnr"
				+ " order by temp.gstrp asc,temp.piriod asc,nvl(tb.gstrp,'00000000') desc," 
				+ " mb.zyxj asc,ma.edatu asc,temp.aufnr asc";
		List listA = findListBySql(sqlA);
		Map<String, Double[]> orderMap = new HashMap<String, Double[]>();// 订单标准产能配置
		Map<String, String> yxjMap = new HashMap<String, String>();// 优先级配置
		List<Object[]> orders = new ArrayList<Object[]>();// 订单列表，按照优先级排序
		String orderStr = "";
		List<String> scrwds = new ArrayList<String>();// 生产任务单列表，用于删除
		if (null != listA && listA.size() > 0) {
			String ddTemp = "";//订单号临时变量
			Double amtTemp = new Double(0);//产量临时变量
			for (int i=0;i<listA.size();i++) {
				Object obj = listA.get(i);
				Object[] temp = (Object[]) obj;
				String aufnr = String.valueOf(temp[1]);// 生产单号
				if(i==0){
					ddTemp = aufnr;
				}
				String zyxj = String.valueOf(temp[7]);// 优先级
				Double zzgs = Double.parseDouble(String.valueOf(temp[5]));// 总装工时
				Double bzgs = Double.parseDouble(String.valueOf(temp[6]));// 包装工时
				// 默认标准工时配置
				if (StringUtils.isNotBlank(gsType)) {
					if ("1".equals(gsType)
							|| zzgs.compareTo(new Double(0)) == 0) {
						zzgs = Double.parseDouble(zzDefalt);
					}
					if ("1".equals(gsType)
							|| bzgs.compareTo(new Double(0)) == 0) {
						bzgs = Double.parseDouble(bzDefalt);
					}
				}
				//难易度
				Double level = new Double(1);
				String levelSql = "select zlevel from ZPPT0031 where aufnr = '"+ aufnr.trim() + "'";
				try{
					Object lvTemp = findListBySql(levelSql).get(0);
					if(null!=lvTemp){
						Double lv = Double.parseDouble(StringUtils.nullToString(lvTemp));
						if(lv.compareTo(new Double(0))>0){
							level = lv;
						}
					}
				}catch (Exception e){
				}
				zzgs = zzgs/level;
				bzgs = bzgs/level;
				
				Double amt = Double.parseDouble(String.valueOf(temp[2]));// 当天排产量
				if(!scrwds.contains(String.valueOf(temp[3]))){
					scrwds.add(String.valueOf(temp[3]));// 生产任务单
				}
				yxjMap.put(aufnr, zyxj);
				orderMap.put(aufnr, new Double[] { zzgs, bzgs });
				if (!aufnr.equals(ddTemp)) {
					//订单号变化，将上一笔订单放入订单列表中,临时产能重置
					orders.add(new Object[]{ddTemp,amtTemp});
					orderStr = orderStr + aufnr + ",";
					amtTemp = amt;
				}else{
					//订单号未发生变化
					amtTemp+=amt;
				}
				if(i==listA.size()-1){
					//已经到最后一条记录时，将当前订单放入列表
					orders.add(new Object[]{aufnr,amtTemp});
				}
				ddTemp = aufnr;
			}
			listA = null;
		}
		List<ZPPT0033> subPlan = new ArrayList<ZPPT0033>();// 排产任务列表
		List<ZPPT0033> loads = new ArrayList<ZPPT0033>();// 生产负荷列表
		if (orders.size() > 0) {
			String sqlB = "select zrq,ltxa1,gamng1,gamng2,gamng3,gamng1+gamng2+gamng3 from zppt0034 where zxh='"
					+ zxh
					+ "' and zrq>='"
					+ start.replaceAll("-", "")
					+ "' order by zrq";
			List listB = findListBySql(sqlB);
			if (null != listB && listB.size() > 0) {
				for (Object objB : listB) {
					ZPPT0033 load = new ZPPT0033();
					Object[] tempB = (Object[]) objB;
					load.setGstrp(String.valueOf(tempB[0]));
					load.setLtxa1(String.valueOf(tempB[1]));
					load.setGamng01(Double.parseDouble(String.valueOf(tempB[2])));
					load.setGamng02(Double.parseDouble(String.valueOf(tempB[3])));
					load.setGamng03(Double.parseDouble(String.valueOf(tempB[4])));
					load.setZscfh(String.valueOf(tempB[5]));
					loads.add(load);
				}
				listB = null;
			}
		}
		// 按照订单优先级进行排产
		for (Object[] order : orders) {
			Double[] gs = orderMap.get(order[0]);
			Double amt = (Double) order[1];
			String yxj = yxjMap.get(order[0]);
			singleLineArrange(order[0].toString(), amt, gs, loads, subPlan, zxh, yxj);
		}
		//合并同天记录再新增
		Map<String,ZPPT0033> plans = new HashMap<String,ZPPT0033>();
		for (ZPPT0033 plan : subPlan) {
			if(null==plans.get(plan.getAufnr()+plan.getGstrp())){
				plans.put(plan.getAufnr()+plan.getGstrp(), plan);
			}else{
				ZPPT0033 planTemp = plans.get(plan.getAufnr()+plan.getGstrp());
				planTemp.setGamng01(plan.getGamng01()+planTemp.getGamng01());
				planTemp.setGamng02(plan.getGamng02()+planTemp.getGamng02());
				planTemp.setGamng03(plan.getGamng03()+planTemp.getGamng03());
				Double fhTemp = Double.parseDouble(planTemp.getZscfh());
				Double fh = Double.parseDouble(plan.getZscfh());
				planTemp.setZscfh(String.valueOf(roundHalfUp(fhTemp+fh, 1)));
				plans.put(plan.getAufnr()+plan.getGstrp(), planTemp);
			}
		}
		for(String planKey:plans.keySet()){
			addSubPlan(plans.get(planKey));
		}
		//删除原始记录
		try {
			deleteSubPlanBatch(scrwds);
			updateYxjByXbRq(zxh, start);
		} catch (Exception e) {
		}
		for(Object[] order : orders){
			updateOrderDetailStatus(StringUtils.nullToString(order[0]));
		}
		//对比结果
		compList = findListBySql(comSql);
		String error = "";
		if(null!=compList && compList.size()>0){
			for(Object ob:compList){
				Object[] temp = (Object[]) ob;
				if(null==compareMap.get(StringUtils.nullToString(temp[0]))
						||!StringUtils.nullToString(temp[1]).equals(compareMap.get(StringUtils.nullToString(temp[0])))){
					error+="订单"+StringUtils.removeLeftZero(StringUtils.nullToString(temp[0]))+"排产量有误;";
				}
			}
		}
		result.put("res", StringUtils.isBlank(error)?"success":"fail");
		error = StringUtils.isBlank(error)?"操作成功":error+"请手工修正!";
		result.put("msg", error);
		long end = System.currentTimeMillis();
		if(logger.isDebugEnabled()){
			logger.debug("产线汇总数据整理,耗时:"+(end-begin)/1000+" s");
		}
		return result;
	}
	
	/**
	 * 
	 * 查找当前线号在时间范围内包含的已报工记录
	 * 
	 * @param zxh
	 * @param start
	 * @param end
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	private String checkExistFinishPlan(String zxh,String start,String end){
		String plan = "";
		String sql = "select b.aufnr from zppt0031 a,zppt0033 b " +
				" where a.aufnr = b.aufnr and (a.zwgpd=1 or nvl(b.zwgl,0)>0) and b.zxh = '"
				+ zxh
				+ "' and b.gstrp>='"
				+ start.replaceAll("-", "")
				+ "' ";
		if(StringUtils.isNotBlank(end)){
			sql+=" and b.gstrp<='"
				+ end.replaceAll("-", "")
				+ "' ";
		}
		List list = findListBySql(sql);
		if(list!=null && list.size()>0){
			return StringUtils.removeLeftZero(StringUtils.nullToString(list.get(0)));
		}
		return plan;
	}
	
	/**
	 * 对单个订单进行自动排产
	 * 
	 * @param orderNo
	 *            订单号
	 * @param amt
	 *            待排产数量
	 * @param gs
	 *            标准工时
	 * @param loads
	 *            产能列表
	 * @param subPlan
	 *            排产列表
	 * @param zxh
	 *            线号
	 * @param yxj
	 *            优先级
	 * @return
	 */
	private List<ZPPT0033> singleLineArrange(String orderNo, Double amt,
			Double[] gs, List<ZPPT0033> loads, List<ZPPT0033> subPlan,
			String zxh, String yxj) {
		List<ZPPT0033> tempList = new ArrayList<ZPPT0033>();
		for (ZPPT0033 load : loads) {
			if (load.getGamng01().compareTo(0D) > 0
					|| load.getGamng02().compareTo(0D) > 0
					|| load.getGamng03().compareTo(0D) > 0) {
				Double zgs = new Double(0);// 总工时
				if (load.getLtxa1().indexOf("总装") > -1) {
					zgs = zgs + gs[0];
				}
				if (load.getLtxa1().indexOf("包装") > -1) {
					zgs = zgs + gs[1];
				}
				ZPPT0033 plan = new ZPPT0033();
				// 当天排产数量
				Double planA = new Double(0);
				Double planB = new Double(0);
				Double planC = new Double(0);
				// 计算当天剩余排产数量
				Double amtA = getRound(load.getGamng01() / zgs, ROUND);
				Double amtB = getRound(load.getGamng02() / zgs, ROUND);
				Double amtC = getRound(load.getGamng03() / zgs, ROUND);
				if (amt.compareTo(amtA) < 0) {
					planA = amt;
				} else if (amt.compareTo(amtA) >= 0
						&& amt.compareTo(amtA + amtB) < 0) {
					planA = amtA;
					planB = amt - planA;
				} else if (amt.compareTo(amtA + amtB) >= 0
						&& amt.compareTo(amtA + amtB + amtC) < 0) {
					planA = amtA;
					planB = amtB;
					planC = amt - planA - planB;
				} else {
					planA = amtA;
					planB = amtB;
					planC = amtC;
				}
				plan.setGamng01(planA);
				plan.setGamng02(planB);
				plan.setGamng03(planC);
				plan.setGstrp(load.getGstrp());
				plan.setLtxa1(load.getLtxa1());
				plan.setMandt(getClient());
				plan.setZxh(zxh);
				plan.setZyxj(yxj);
				load.setGamng01(load.getGamng01() - planA * zgs);
				load.setGamng02(load.getGamng02() - planB * zgs);
				load.setGamng03(load.getGamng03() - planC * zgs);
				Double amtToday = planA + planB + planC;
				Double cnAll = Double.parseDouble(load.getZscfh()); // 当天总产能
				Double scfhToday = (zgs * amtToday / cnAll) * 100; // 负荷=标准工时×排产量/总产能
				plan.setZscfh(String.valueOf(roundHalfUp(scfhToday, 1)));// 保留小数点后一位,四舍五入
				plan.setZwgl(0D);
				plan.setAufnr(orderNo);
				amt = amt - amtToday;
				subPlan.add(plan);
			}
			if (amt.compareTo(0D) == 0) {
				break;
			}
		}
		return tempList;
	}
	
	/**
	 * 批量删除生产排程信息
	 * @param scrwds
	 */
	private void deleteSubPlanBatch(List<String> scrwds) throws Exception{
		if(null==scrwds || scrwds.size()==0){
			return;
		}
		StringBuffer sql = new StringBuffer("delete from zppt0033 where zscrwd in (");
		for(int i=0;i<scrwds.size();i++){
			sql.append(scrwds.get(i));
			if(i<scrwds.size()-1){
				sql.append(",");
			}
		}
		sql.append(")");
		execSql(sql.toString());
	}
	
	/**
	 * 根据线别和日期自动更新当天订单优先级
	 * 有A班排产记录设置优先级为70
	 * 有B班排产记录设置优先级为80
	 * 有C班排产记录设置优先级为90
	 * @param zxh
	 * @param zrq
	 */
	private void updateYxjByXbRq(String zxh, String zrq) {
		String condition = " and b.zxh='" + zxh + "' and b.gstrp='"
				+ zrq.replaceAll("-", "") + "' ";
		String updateA = "update zppt0031 set zyxj='0070' where exists (select 1 from zppt0033 b where b.aufnr=zppt0031.aufnr "
				+ condition + " and b.gamng01>0 )";
		String updateB = "update zppt0031 set zyxj='0080' where exists (select 1 from zppt0033 b where b.aufnr=zppt0031.aufnr "
				+ condition + " and b.gamng02>0 )";
		String updateC = "update zppt0031 set zyxj='0090' where exists (select 1 from zppt0033 b where b.aufnr=zppt0031.aufnr "
				+ condition + " and b.gamng03>0 )";
		execSql(updateA);
		execSql(updateB);
		execSql(updateC);
	}
	
	/**
	 * 更新单个生产订单状态
	 * @param aufnr
	 */
	private void updateOrderDetailStatus(String aufnr) {
		String sql = "select b.zypcl,b.zzt,a.gamng from zppt0030 a left join zppt0031 b on a.mandt=b.mandt and a.aufnr=b.aufnr where a.aufnr='"+StringUtils.addLeftZero(aufnr,12)+"' and a.mandt="+getClient();
		Object[] plan = (Object[])findListBySql(sql).get(0);
		if (StringUtils.isNotBlank(StringUtils.nullToString(plan[0]))) {
			String[] ypcl = StringUtils.nullToString(plan[0]).split("\\|");
			if (ypcl != null && ypcl.length == 2) {
				try {
					Double zz = Double.parseDouble(ypcl[0]);
					Double bz = Double.parseDouble(ypcl[1]);
					if (zz.compareTo(Double.valueOf(StringUtils.nullToString(plan[2]))) >= 0
							&& bz.compareTo(Double.valueOf(StringUtils.nullToString(plan[2]))) >= 0
							&& !"5".equals(StringUtils.nullToString(plan[1]))) {// 排满才判断是否超交期
						String sqlE = "select 1 from zppt0030 m where m.aufnr = '"
								+ aufnr
								+ "' and exists (select 1 from zppt0033 s where s.aufnr = m.aufnr and s.gstrp>m.edatu)";
						List<Object> tempE = findListBySql(sqlE);
						if(null!=tempE && tempE.size()>0){
							ZPPT0031 planDetail = new ZPPT0031();
							planDetail.setZzt("5");//排产超SD交期
							updatePlanDetail(planDetail);
						}
					}
				} catch (Exception e) {
				}
			}
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Map<?, ?> autoArrangeXbPlan(String zxh, String start, String end, String gsType, String zzDefalt,
			String bzDefalt) {
		Map result = new HashMap();
		String finisPlan = checkExistFinishPlan(zxh,start,end);
		if(StringUtils.isNotBlank(finisPlan)){
			String message = "订单"+finisPlan+"已完工，或在选定时间内有报工记录，请修改!";
			result.put("res", "fail");
			result.put("msg", message);
			return result;
		}
		//操作前查询当前线别排产总量
		Map<String,String> compareMap = new HashMap<String,String>();
		String comSql = "select aufnr,sum(gamng01+gamng02+gamng03) from zppt0033 where zxh = '"+zxh+"' and gstrp >= '"+start.replaceAll("-", "")+"' group by aufnr";
		List compList = findListBySql(comSql);
		if(null!=compList && compList.size()>0){
			for(Object ob:compList){
				compareMap.put(StringUtils.nullToString(((Object[])ob)[0]), StringUtils.nullToString(((Object[])ob)[1]));
			}
		}
		// 删除'已完工'数据
		deleteCompleteOrderPlans(zxh);
		// 查找当前线别起始日期之后所有已排产订单列表
		// 优先级：排产日期>>订单优先级>>SD交期>>订单号
		String sqlA = "select ta.* from (select b.gstrp,a.zyxj,c.edatu,b.aufnr,c.vgw01,c.vgw02,b.gamng01+b.gamng02+b.gamng03,b.zscrwd,b.gamng01,b.gamng02,b.gamng03 from zppt0031 a,zppt0033 b,zppt0030 c " +
				"where a.aufnr = b.aufnr and b.aufnr = c.aufnr and nvl(b.zwgl,0)=0 and b.zxh = '"
				+ zxh
				+ "' and b.gstrp>='"
				+ start.replaceAll("-", "")
				+ "' and b.gstrp<='"
				+ end.replaceAll("-", "")
				+ "' and b.gamng01 + b.gamng02 + b.gamng03 >0 ) ta"
				+ " left join (select max(gstrp) gstrp,zxh,aufnr from zppt0033 d where d.zxh = '"
				+ zxh
				+ "' and d.gstrp<='"
				+ start.replaceAll("-", "")
				+ "' and d.zwgl>0 group by d.zxh,d.aufnr) tb on tb.aufnr = ta.aufnr"
				+ " order by ta.gstrp asc,case"
				+ " when ta.gamng02 = 0 and ta.gamng03 = 0 then 0"
				+ " when ta.gamng02 = 0 and ta.gamng03 > 0 then 3"
				+ " when ta.gamng02 > 0 and ta.gamng03 > 0 then 2"
				+ " when ta.gamng02 = 0 and ta.gamng03 > 0 then 1"
				+ " else 0 end asc,case"
				+ " when ta.gamng01 = 0 and ta.gamng02 = 0 then 0"
				+ " when ta.gamng01 = 0 and ta.gamng02 > 0 then 3"
				+ " when ta.gamng01 > 0 and ta.gamng02 > 0 then 2"
				+ " when ta.gamng01 = 0 and ta.gamng02 > 0 then 1"
				+ " else 0 end asc,"
				+ " nvl(tb.gstrp,'00000000') desc, ta.zyxj asc,ta.edatu asc, ta.aufnr asc";
		List listA = findListBySql(sqlA);
		Map<String, Double[]> orderMap = new HashMap<String, Double[]>();// 订单标准产能配置
		Map<String, Double> orderAmt = new HashMap<String, Double>();// 订单待排产数量
		Map<String, String> yxjMap = new HashMap<String, String>();// 优先级配置
		List<String> orders = new ArrayList<String>();// 订单列表，按照优先级排序
		String orderStr = "";
		List<String> scrwds = new ArrayList<String>();// 生产任务单列表，用于删除
		if (null != listA && listA.size() > 0) {
			for (Object obj : listA) {
				Object[] temp = (Object[]) obj;
				String aufnr = String.valueOf(temp[3]);// 生产单号
				String zyxj = String.valueOf(temp[1]);// 优先级
				Double zzgs = Double.parseDouble(String.valueOf(temp[4]));// 总装工时
				Double bzgs = Double.parseDouble(String.valueOf(temp[5]));// 包装工时
				// 默认标准工时配置
				if (StringUtils.isNotBlank(gsType)) {
					if ("1".equals(gsType)
							|| zzgs.compareTo(new Double(0)) == 0) {
						zzgs = Double.parseDouble(zzDefalt);
					}
					if ("1".equals(gsType)
							|| bzgs.compareTo(new Double(0)) == 0) {
						bzgs = Double.parseDouble(bzDefalt);
					}
				}
				//难易度
				Double level = new Double(1);
				String levelSql = "select zlevel from ZPPT0031 where aufnr = '"+ aufnr.trim() + "'";
				try{
					Object lvTemp = findListBySql(levelSql).get(0);
					if(null!=lvTemp){
						Double lv = Double.parseDouble(lvTemp.toString());
						if(lv.compareTo(new Double(0))>0){
							level = lv;
						}
					}
				}catch (Exception e){
				}
				zzgs = zzgs/level;
				bzgs = bzgs/level;
				
				Double amt = Double.parseDouble(String.valueOf(temp[6]));// 当天排产量
				scrwds.add(String.valueOf(temp[7]));// 生产任务单
				yxjMap.put(aufnr, zyxj);
				if (!orders.contains(aufnr)) {
					orders.add(aufnr);
					orderMap.put(aufnr, new Double[] { zzgs, bzgs });
					orderStr = orderStr + aufnr + ",";
				}
				if (orderAmt.containsKey(aufnr)) {
					Double alreadyAmt = orderAmt.get(aufnr);
					orderAmt.put(aufnr, alreadyAmt + amt);
				} else {
					orderAmt.put(aufnr, amt);
				}
			}
			listA = null;
		}
		List<ZPPT0033> subPlan = new ArrayList<ZPPT0033>();// 排产任务列表
		List<ZPPT0033> loads = new ArrayList<ZPPT0033>();// 生产负荷列表
		if (orders.size() > 0) {
			String sqlB = "select zrq,ltxa1,gamng1,gamng2,gamng3,gamng1+gamng2+gamng3 from zppt0034 where zxh='"
					+ zxh
					+ "' and zrq>='"
					+ start.replaceAll("-", "")
					+ "' order by zrq";
			List listB = findListBySql(sqlB);
			if (null != listB && listB.size() > 0) {
				for (Object objB : listB) {
					ZPPT0033 load = new ZPPT0033();
					Object[] tempB = (Object[]) objB;
					load.setGstrp(String.valueOf(tempB[0]));
					load.setLtxa1(String.valueOf(tempB[1]));
					load.setGamng01(Double.parseDouble(String.valueOf(tempB[2])));
					load.setGamng02(Double.parseDouble(String.valueOf(tempB[3])));
					load.setGamng03(Double.parseDouble(String.valueOf(tempB[4])));
					load.setZscfh(String.valueOf(tempB[5]));
					loads.add(load);
				}
				listB = null;
			}
		}
		// 按照订单优先级进行排产
		for (String orderNo : orders) {
			Double[] gs = orderMap.get(orderNo);
			Double amt = orderAmt.get(orderNo);
			String yxj = yxjMap.get(orderNo);
			singleLineArrange(orderNo, amt, gs, loads, subPlan, zxh, yxj);
		}

		for (ZPPT0033 plan : subPlan) {
			addSubPlan(plan);
		}
		try {
			deleteSubPlanBatch(scrwds);
			updateYxjByXbRq(zxh, start);
		} catch (Exception e) {
		}
		for(String aufnr:orders){
			updateOrderDetailStatus(aufnr);
		}
		//对比结果
		compList = findListBySql(comSql);
		String error = "";
		if(null!=compList && compList.size()>0){
			for(Object object:compList){
				Object[] ob = (Object[]) object;
				if(null==compareMap.get(StringUtils.nullToString(ob[0]))
						||!StringUtils.nullToString(ob[1]).equals(compareMap.get(StringUtils.nullToString(ob[0])))){
					error+="订单"+StringUtils.removeLeftZero(ob[0].toString())+"排产量有误;";
				}
			}
		}
		result.put("res", StringUtils.isBlank(error)?"success":"fail");
		error = StringUtils.isBlank(error)?"操作成功":error+"请手工修正!";
		result.put("msg", error);
		return result;
	}
	
	/**
	 * 删除当前线别中已完工排产数据(zppt0030表记录不存在)
	 */
	private void deleteCompleteOrderPlans(String zxh){
		try {
			Object lock = findListBySql(
					"select nvl(max(zlock),0) from zppt0032 where zname = 'ZPPR0027'")
					.get(0);
			if (!"1".equals(lock.toString())) {
				String delete = "delete from zppt0033 a where not exists (select 1 from zppt0030 b where b.aufnr = a.aufnr) and nvl(zwgl,0)=0 and zxh = '"
						+ zxh + "'";
				execSql(delete);
			}
		}catch(Exception e){
			
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Map<?, ?> getEndDateInit(String start, String add) {
		Map result = new HashMap();
		String end = DateUtils.formatDate(new Date(), "yyyy-MM-dd");
		try {
			int ad = Integer.parseInt(add);
			Date st = DateUtils.parseDate(start, "yyyy-MM-dd");
			end = DateUtils.formatDate(DateUtils.addDate(st, ad), "yyyy-MM-dd");
			result.put("res", "success");
			result.put("data", end);
		} catch (Exception e) {
			result.put("res", "fail");
			result.put("msg", e.getMessage());
		}
		return result;
	}

	@SuppressWarnings("unused")
	@Override
	public void saveTest(String aufnr,String value) {
		ZPPT0030 plan = sapDao.get(ZPPT0030.class,aufnr);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void updateCompletePlans(String[] wg, String gsType, String zzDefalt, String bzDefalt) throws Exception {
		String bgxb = wg[0];// 本次线别
		String bgrq = wg[1];// 本次日期
		Double bgamt = Double.valueOf(wg[2]);// 本次报工
		String aufnr = wg[3];// 源订单
		String zxh = wg[4];// 源线别c
		String gstrp = wg[5];// 源日期
		Double amt = Double.valueOf(wg[6]);// 源数量
		//查询下一个工作日
		String sql0 = "select nvl(min(zrq),to_char(sysdate,'yyyyMMdd')) from zppt0034 where zxh='"+bgxb+"' and zrq>'"+bgrq+"'";
		Object da = findListBySql(sql0).get(0);
		String bgrqNext = String.valueOf(da);
		// 0查询源排产量
		String sqla = "select gamng01+gamng02+gamng03,zscrwd from zppt0033 where aufnr = '"
				+ aufnr + "' and zxh='" + zxh + "' and gstrp = '" + gstrp + "'";
		List list = findListBySql(sqla);
		Object[] o = new Object[2];
		if(null!=list && list.size()>0){
			o = (Object[]) list.get(0);
			if(list.size()>1){
				//报工当天有大于一条的记录，则合并处理
				List<String> delList = new ArrayList<String>();
				for(int i=1;i<list.size();i++){
					Object[] delObject = (Object[])list.get(i);
					o[0] = Double.parseDouble(o[0].toString())+Double.parseDouble(delObject[0].toString());
					delList.add(delObject[1].toString());
				}
				deleteSubPlanBatch(delList);
				String sqlUpdate = "update zppt0033 set gamng01="+o[0]+",gamng02=0,gamng03=0 where zscrwd='"+o[1]+"'";
				execSql(sqlUpdate);
			}
		}
		Double gamng = Double.parseDouble(o[0].toString());

		if (bgxb.equals(zxh) && bgrq.equals(gstrp)) {
			// 1本次线别、本次日期与源线别、源日期相等
			String zscrwd = String.valueOf(o[1]);
			ZPPT0033 subPlan = findSubPlanById(zscrwd);
			if(bgamt.compareTo(gamng)==0){
				//1.1报工量和排产量相等,更新完工量即可
				subPlan.setZwgl(bgamt);
				updateSubPlan(subPlan);
				updatePlanDetailStatus(aufnr,null,null,null,null);
				return;
			}
			if(bgamt.compareTo(gamng)<0){
				//1.2报工量小于排产量
				//1.2.1更新源日期的产量
				subPlan.setGamng01(bgamt);
				subPlan.setGamng02(new Double(0));
				subPlan.setGamng03(new Double(0));
				subPlan.setZwgl(bgamt);
				subPlan.setZscfh(caculateLoad(aufnr,bgrq,bgxb,bgamt,gsType,zzDefalt,bzDefalt));
				updateSubPlan(subPlan);
				//1.2.2将剩余产量排到次日
				String sqlb = "select gamng01+gamng02+gamng03,zscrwd from zppt0033 where aufnr = '"
					+ aufnr + "' and zxh='" + bgxb + "' and gstrp = '" + bgrqNext + "'";
				List listb = findListBySql(sqlb);
				if(listb!=null&&listb.size()>0){
					//次日有排产记录,更新第一条
					Object[] ob = (Object[])listb.get(0);
					Double gamngB = Double.parseDouble(ob[0].toString());
					String zscrwdB = String.valueOf(ob[1]);
					ZPPT0033 subPlanB = findSubPlanById(zscrwdB);
					subPlanB.setGamng01(gamngB+(gamng-bgamt));
					subPlanB.setGamng02(new Double(0));
					subPlanB.setGamng03(new Double(0));
					subPlanB.setZscfh(caculateLoad(aufnr,bgrqNext,bgxb,subPlanB.getGamng01(),gsType,zzDefalt,bzDefalt));
					updateSubPlan(subPlanB);
				}else{
					//次日无排产记录,新增一条
					ZPPT0033 subPlanB = new ZPPT0033();
					subPlanB.setAufnr(aufnr);
					subPlanB.setGstrp(bgrqNext);
					subPlanB.setLtxa1(StringUtils.nullToString(findListBySql(
									"select max(ltxa1) from ZPPT0034 where zxh = '"
											+ bgxb + "'").get(0)));
					subPlanB.setGamng01(gamng-bgamt);
					subPlanB.setGamng02(new Double(0));
					subPlanB.setGamng03(new Double(0));
					subPlanB.setZwgl(new Double(0));
					subPlanB.setZxh(bgxb);
					subPlanB.setZyxj("0100");
					subPlanB.setMandt(getClient());
					subPlanB.setZscfh(caculateLoad(aufnr,bgrqNext,bgxb,subPlanB.getGamng01(),gsType,zzDefalt,bzDefalt));
					addSubPlan(subPlanB);
				}
				updatePlanDetailStatus(aufnr,null,null,null,null);
				return;
			}
			if(bgamt.compareTo(gamng)>0){
				//1.3报工量大于排产量
				Double left = bgamt-gamng;
				//1.3.1检查报工日期后的排产记录，是否有足够的排产量分给当前报工记录
				String sql1 = "select nvl(sum(gamng01+gamng02+gamng03),0) from zppt0033 where aufnr='"+aufnr+"' and zxh='"+bgxb+"' and gstrp>'"+bgrq+"'";
				Object sum1 = findListBySql(sql1).get(0);
				Double su = Double.parseDouble(sum1.toString());
				if(su.compareTo(left)<0){
					throw new Exception("订单完工数量大于当前累计排产量,订单号:"+aufnr+" 日期:"+bgrq +" 线号:"+bgxb+" 报工量:"+bgamt);
				}
				String sql2 = "select gamng01+gamng02+gamng03,zscrwd from zppt0033 where aufnr='"+aufnr+"' and zxh='"+bgxb+"' and gstrp>'"+bgrq+"' order by gstrp";
				List list2 = findListBySql(sql2);
				if(null==list2 || list2.size()==0){
					throw new Exception("订单完工数量大于当前累计排产量,订单号:"+aufnr+" 日期:"+bgrq +" 线号:"+bgxb+" 报工量:"+bgamt);
				}
				//1.3.2对后续排产记录扣除完工多余部分
				for(Object objTemp : list2){
					Object[] obj2 = (Object[]) objTemp;
					Double gamng2 = Double.parseDouble(obj2[0].toString());
					String zscrwd2 = String.valueOf(obj2[1]);
					ZPPT0033 subPlan2 = findSubPlanById(zscrwd2);
					if(left.compareTo(gamng2)>=0){
						left = left - gamng2;
						deletePlanDetail(subPlan2);
					}else{
						subPlan2.setGamng01(gamng2-left);
						subPlan2.setGamng02(new Double(0));
						subPlan2.setGamng03(new Double(0));
						updateSubPlan(subPlan2);
						left = new Double(0);
						break;
					}
				}
				//1.3.3更新完工记录
				subPlan.setGamng01(bgamt);
				subPlan.setGamng02(new Double(0));
				subPlan.setGamng03(new Double(0));
				subPlan.setZwgl(bgamt);
				subPlan.setZscfh(caculateLoad(aufnr,bgrq,bgxb,bgamt,gsType,zzDefalt,bzDefalt));
				updateSubPlan(subPlan);
				updatePlanDetailStatus(aufnr,null,null,null,null);
				return;
			}
		}else{
			// 2换日期或者换线操作
			String zscrwd = String.valueOf(o[1]);
			ZPPT0033 subPlan = findSubPlanById(zscrwd);
			// 2.1如果源数量等于当天排产量,则删除当天排产记录，否则当天排产记录扣除源数量
			if(amt.compareTo(gamng)==0){
				//2.1.1源数量等于当天排产量，删除当天排产数据
				deletePlanDetail(subPlan);
			}else if(amt.compareTo(gamng)<0){
				//2.1.2源数量小于于当天排产量，当天排产记录扣除源数量
				subPlan.setGamng01(gamng-amt);
				subPlan.setGamng02(new Double(0));
				subPlan.setGamng03(new Double(0));
				subPlan.setZscfh(caculateLoad(aufnr,gstrp,zxh,subPlan.getGamng01(),gsType,zzDefalt,bzDefalt));
				updateSubPlan(subPlan);
			}else{
				//throw new Exception("源数量大于当天排产量,订单号:"+aufnr+" 日期:"+gstrp +" 线号:"+zxh+" 源数量:"+amt+" 排产量:"+gamng);
				//2.1.3源数量大于当天排产量
				Double left = amt;
				//2.1.3.1检查报工日期后的排产记录(包含源排产记录)，是否有足够的排产量分给当前报工记录
				String sql1 = "select nvl(sum(gamng01+gamng02+gamng03),0) from zppt0033 where aufnr='"+aufnr+"' and zxh='"+zxh+"' and gstrp>='"+gstrp+"'";
				Object sum2 = findListBySql(sql1).get(0);
				Double su = Double.parseDouble(sum2.toString());
				if(su.compareTo(left)<0){
					throw new Exception("订单完工数量大于当前累计排产量,订单号:"+aufnr+" 日期:"+gstrp +" 线号:"+zxh+" 源数量:"+bgamt);
				}
				String sql2 = "select gamng01+gamng02+gamng03,zscrwd from zppt0033 where aufnr='"+aufnr+"' and zxh='"+zxh+"' and gstrp>='"+gstrp+"' order by gstrp";
				List list2 = findListBySql(sql2);
				if(null==list2 || list2.size()==0){
					throw new Exception("订单完工数量大于当前累计排产量,订单号:"+aufnr+" 日期:"+bgrq +" 线号:"+zxh+" 报工量:"+bgamt);
				}
				//2.1.3.2对后续排产记录(包含源记录)扣除完工多余部分
				for(Object obj2Temp : list2){
					Object[] obj2 = (Object[]) obj2Temp;
					Double gamng2 = Double.parseDouble(obj2[0].toString());
					String zscrwd2 = String.valueOf(obj2[1]);
					ZPPT0033 subPlan2 = findSubPlanById(zscrwd2);
					if(left.compareTo(gamng2)>=0){
						left = left - gamng2;
						deletePlanDetail(subPlan2);
					}else{
						subPlan2.setGamng01(gamng2-left);
						subPlan2.setGamng02(new Double(0));
						subPlan2.setGamng03(new Double(0));
						updateSubPlan(subPlan2);
						left = new Double(0);
						break;
					}
				}
			}
			
			// 2.2更新或新增本次线别、日期的排产量和报工量
			String sqlc = "select gamng01+gamng02+gamng03,zscrwd from zppt0033 where aufnr = '"
					+ aufnr
					+ "' and zxh='"
					+ bgxb
					+ "' and gstrp = '"
					+ bgrq + "'";
			List listc = findListBySql(sqlc);
			// 查询本次报工线别、日期的排产记录，有则合并或新增
			if(listc!=null&&listc.size()>0){
				Object[] ob = (Object[])listc.get(0);
				Double gamngC = Double.parseDouble(ob[0].toString());
				String zscrwdC = String.valueOf(ob[1]);
				ZPPT0033 subPlanC = findSubPlanById(zscrwdC);
				subPlanC.setGamng01(bgamt+gamngC);
				subPlanC.setGamng02(new Double(0));
				subPlanC.setGamng03(new Double(0));
				subPlanC.setZwgl((subPlanC.getZwgl()==null?new Double(0):subPlanC.getZwgl())+bgamt);
				subPlanC.setZscfh(caculateLoad(aufnr,bgrq,bgxb,subPlanC.getGamng01(),gsType,zzDefalt,bzDefalt));
				updateSubPlan(subPlanC);
			}else{
				ZPPT0033 subPlanC = new ZPPT0033();
				subPlanC.setAufnr(aufnr);
				subPlanC.setGstrp(bgrq);
				subPlanC.setLtxa1(StringUtils.nullToString(findListBySql(
								"select max(ltxa1) from ZPPT0034 where zxh = '"
										+ bgxb + "'").get(0)));
				subPlanC.setGamng01(bgamt);
				subPlanC.setGamng02(new Double(0));
				subPlanC.setGamng03(new Double(0));
				subPlanC.setZwgl(bgamt);
				subPlanC.setZxh(bgxb);
				subPlanC.setZyxj("0100");
				subPlanC.setMandt(getClient());
				subPlanC.setZscfh(caculateLoad(aufnr,bgrq,bgxb,subPlanC.getGamng01(),gsType,zzDefalt,bzDefalt));
				addSubPlan(subPlanC);
			}
			
			// 2.3如果本次报工量小于源数量，则将剩余产量排产至报工日期第二天
			if (bgamt.compareTo(amt) < 0) {
				String sqld = "select gamng01+gamng02+gamng03,zscrwd from zppt0033 where aufnr = '"
						+ aufnr
						+ "' and zxh='"
						+ bgxb
						+ "' and gstrp = '"
						+ bgrqNext + "'";
				List listd = findListBySql(sqld);
				// 查询本次报工线别、第二天的排产记录，有则合并无则新增
				if(listd!=null&&listd.size()>0){
					Object[] ob = (Object[])listd.get(0);
					Double gamngD = Double.parseDouble(ob[0].toString());
					String zscrwdD = String.valueOf(ob[1]);
					ZPPT0033 subPlanD = findSubPlanById(zscrwdD);
					subPlanD.setGamng01(gamngD+(amt-bgamt));
					subPlanD.setGamng02(new Double(0));
					subPlanD.setGamng03(new Double(0));
					subPlanD.setZscfh(caculateLoad(aufnr,bgrqNext,bgxb,subPlanD.getGamng01(),gsType,zzDefalt,bzDefalt));
					updateSubPlan(subPlanD);
				}else{
					//次日无排产记录,新增一条
					ZPPT0033 subPlanD = new ZPPT0033();
					subPlanD.setAufnr(aufnr);
					subPlanD.setGstrp(bgrqNext);
					subPlanD.setLtxa1(StringUtils.nullToString(findListBySql(
							"select max(ltxa1) from ZPPT0034 where zxh = '"
							+ bgxb + "'").get(0)));
					subPlanD.setGamng01(amt-bgamt);
					subPlanD.setGamng02(new Double(0));
					subPlanD.setGamng03(new Double(0));
					subPlanD.setZwgl(new Double(0));
					subPlanD.setZxh(bgxb);
					subPlanD.setZyxj("0100");
					subPlanD.setMandt(getClient());
					subPlanD.setZscfh(caculateLoad(aufnr,bgrqNext,bgxb,subPlanD.getGamng01(),gsType,zzDefalt,bzDefalt));
					addSubPlan(subPlanD);
				}
			}
			updatePlanDetailStatus(aufnr,null,null,null,null);
			return;
		}
	}

	@Override
	public void deleteZeroPlan(String aufnr) {
		if(StringUtils.isNotBlank(aufnr)){
			String delete = "delete from zppt0033 where gamng01=0 and gamng02=0 and gamng03=0 and zwgl=0 and aufnr='"+aufnr+"'";
			execSql(delete);
		}
	}
	
	@Override
	public Map<?,?> manualRefresh(String type) {
		if(logger.isDebugEnabled()){
			logger.debug("=====welcome to manualRefresh's world =====you're "+type);
		}
		HashMap<String,String> hashMap = new HashMap<String,String>();
		JSONObject jsonObject = new JSONObject();
		if(StringUtils.isBlank(type)){
			hashMap.put("res","fail");
			hashMap.put("err","刷新类型为空!");
			return hashMap;
		}
		if("1".equals(type)){//欠料
			jsonObject.element("PI_TCODE","ZPPR0047");
		}else if("2".equals(type)){//mps
			jsonObject.element("PI_TCODE","ZPPR0049");
		}else if("3".equals(type)){//工单追加
			jsonObject.element("PI_TCODE","ZPPR0051");
		}
		SAPRfcJson rfcjson = saprfcservice.RFC("sapConnectConfig","ZPPI0016", jsonObject.toString(), 1, -1, null);
		JSONObject jObject = rfcjson.getRecord("PT_RETURN");
		JSONArray jObs = jObject.getJSONArray("row");
		for (int i = 0; i < jObs.size(); i++) {
			if ("E".equals(((JSONObject) jObs.get(i)).get("TYPE").toString())) {
				hashMap.put("res","fail");
				hashMap.put("err", ((JSONObject)jObs.get(i)).get("MESSAGE").toString());
				return hashMap;
			}
		}
		hashMap.put("res","success");
		if(logger.isDebugEnabled()){
			logger.debug("=====manualRefresh's saying goodbye to you =====");
		}
		return hashMap;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Map<?, ?> publishAllPlan(String zsg) {
		HashMap<String,String> hashMap = new HashMap<String,String>();
		if(StringUtils.isBlank(zsg)){
			hashMap.put("res","fail");
			hashMap.put("msg", "发布功能现在只能由生管来操作!");
			return hashMap;
		}
		String today = DateUtils.formatDate(new Date(), "yyyyMMdd");
		//查询此生管在发布日期前是否有未报工计划
		String checkFinish = "select a.aufnr,a.zxh,a.gstrp from zppt0033 a inner join zppt0031 b on a.aufnr=b.aufnr and a.mandt=b.mandt"
			+ " where a.gamng01+a.gamng02+a.gamng03>a.zwgl"
			+ " and b.zwgpd ='0'"
			+ " and a.gstrp<'"+today+"'"
			+ " and b.zsg='"+zsg+"'";
		List unFinishPlanList = findListBySql(checkFinish);
		if(null!=unFinishPlanList&&unFinishPlanList.size()>0){
			Object[] plan = (Object[])unFinishPlanList.get(0);
			String error = "您有未报工的生产任务单!订单号("+StringUtils.nullToString(plan[0])+")线号("+StringUtils.nullToString(plan[1])+")生产日期("+StringUtils.nullToString(plan[2])+")";
			hashMap.put("res","fail");
			hashMap.put("msg", error);
			return hashMap;
		}
		
		//查找上一版本发布的计划+当前版本计划+未完成工单中的计划
		String woQuery = " select distinct a.aufnr, a.zxh from zppt0044 a"
			+ " inner join zppt0031 b on a.mandt = b.mandt and a.aufnr = b.aufnr"
			+ " where a.zversion in (select max(zversion) from zppt0043 where zversion not like '"+today+"%' "
			+ " and zsg = '"+zsg+"') and b.zwgpd = '0' and b.zsg = '"+zsg+"'"
			+ " union"
			+ " select distinct c.aufnr, c.zxh from zppt0033 c"
			+ " inner join zppt0031 d on c.mandt = d.mandt and c.aufnr = d.aufnr"
			+ " where d.zwgpd = '0' and d.zsg = '"+zsg+"' and c.gstrp >= '"+today+"'"
			+ " union"
			+ " select distinct e.aufnr,e.zxh from zppt0068 e"
			+ " inner join zppt0031 f on e.mandt=f.mandt and e.aufnr=f.aufnr"
			+ " where f.zsg='"+zsg+"' and e.zzt in ('R','M')";
		List<Object> woList = findListBySql(woQuery);
		String datetime = DateUtils.formatDate(new Date(), "yyyyMMdd_HHmmss");
		String delA = "delete from zppt0044 a where a.zversion like '"+today+"%' and exists (select 1 from zppt0043 b where a.aufnr=b.aufnr and a.zversion=b.zversion and b.zsg='"+zsg+"')";
		String delB = "delete from zppt0043 where zversion like '"+today+"%' and zsg='"+zsg+"'";
		String delC = "delete from zppt0042 a where a.zversion like '"+today+"%' and exists (select 1 from zppt0043 b where a.aufnr=b.aufnr and a.zversion=b.zversion and b.zsg='"+zsg+"')";
		String insertA = "insert into zppt0042(mandt,aufnr,sortl,kdauf,kdpos,edatu,matnr,zgrun,gamng,gstrp,gltrp,vgw01,vgw02,igmng,zwwcl,zversion) select a.mandt,a.aufnr,a.sortl,a.kdauf,a.kdpos,a.edatu,a.matnr,a.zgrun,a.gamng,a.gstrp,a.gltrp,a.vgw01,a.vgw02,a.igmng,a.zwwcl,'"
			+ datetime
			+ "' from zppt0030 a inner join zppt0031 c on a.aufnr=c.aufnr" 
			+ " where exists (select 1 from zppt0033 b where b.aufnr = a.aufnr and b.gstrp >= to_char(sysdate,'yyyyMMdd'))"
			+ " and c.zsg='"+zsg+"'";
		String insertB = "insert into zppt0043(mandt,aufnr,zsg,dwerks,zxhscrq,gltrp,zypcl,zzt,zqt,zyxj,zwgpd,zqlzt,zwlzk,zversion) select a.mandt,a.aufnr,a.zsg,case when a.dwerks is null then ' ' else a.dwerks end,"
			+ "a.zxhscrq,case when a.gltrp is null then ' ' else a.gltrp end,a.zypcl,a.zzt,a.zqt,a.zyxj,a.zwgpd,a.zqlzt,a.zwlzk,'"
			+ datetime
			+ "' from zppt0031 a where exists (select 1 from zppt0033 b where b.aufnr = a.aufnr and b.gstrp >= to_char(sysdate, 'yyyyMMdd')) "
			+ " and a.zsg='"+zsg+"'";
		String insertC = "insert into zppt0044(mandt,aufnr,zscrwd,zyxj,zxh,ltxa1,gstrp,gamng01,gamng02,gamng03,zscfh,zversion) select a.mandt,a.aufnr,a.zscrwd,a.zyxj,a.zxh,a.ltxa1,a.gstrp,a.gamng01,a.gamng02,a.gamng03,a.zscfh,'"
			+ datetime
			+ "' from zppt0033 a inner join zppt0031 c on a.aufnr=c.aufnr" 
			+ " where a.gstrp >= to_char(sysdate,'yyyyMMdd')"
			+ " and c.zsg='"+zsg+"'";
		try {
			execSql(delA);
			execSql(delB);
			execSql(delC);
			execSql(insertA);
			execSql(insertB);
			execSql(insertC);
			//修改工单
			List<String> xhList = new ArrayList<String>();
			for(Object object:woList){
				Object[] plan = (Object[]) object;
				compareWorkOrder(StringUtils.nullToString(plan[0]), StringUtils.nullToString(plan[1]));
				if(!xhList.contains(StringUtils.nullToString(plan[1]))){
					xhList.add(StringUtils.nullToString(plan[1]));
				}
			}
			for(String xh : xhList){
				//更新当前线别工单顺序
				updateOrderNumberForWorkOrder(StringUtils.nullToString(xh));
			}
		} catch (Exception e) {
			hashMap.put("res","fail");
			hashMap.put("msg",e.getMessage());
			if(logger.isDebugEnabled()){
				logger.debug("=====publish "+zsg+"'s Plan failed===="+e.getMessage());
			}
		}
		hashMap.put("res","success");
		return hashMap;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<PlanShare> getCurrentPlan(Map<String, Object> map) {
		List days = (List) map.get("days");
		String aufnr = (String) map.get("aufnr");// 生产订单号
		String kdauf = (String) map.get("kdauf");//销售单号 add by HeShi 20130924
		String sortl = (String) map.get("sortl");//客户编号 add by HeShi 20130924
		String matnr = (String) map.get("matnr");//物料ID add by HeShi 20130924
		String zgrun = (String) map.get("zgrun");//物料描述 add by HeShi 20130924
		String zsg = (String) map.get("zsg");// 生管
		String zxh = (String) map.get("zxh");// 线号
		String dwerks = (String) map.get("dwerks");// 厂别
		String zclass = (String) map.get("class");// 课别
		String department = (String) map.get("department");// 部别
		String zzt = (String) map.get("zzt");// 状态
		String edatu = (String) map.get("edatufrom");//SD交期
		String gstrp = (String) map.get("gstrpfrom");//上线日期
		String gltrp = (String) map.get("gltrpfrom");//完工日期
		String scrq = (String) map.get("scrqfrom");//生产日期
		String mpsdate = (String) map.get("mpsdatefrom");//MPS上线日期
		String yjcq = (String) map.get("yjcqfrom");//预计船期
		//add by heshi 20130929 添加日期条件的到期日 begin
		String edatuTo = (String) map.get("edatuto");//SD交期
		String gstrpTo = (String) map.get("gstrpto");//上线日期
		String gltrpTo = (String) map.get("gltrpto");//完工日期
		String scrqTo = (String) map.get("scrqto");//生产日期
		String mpsdateTo = (String) map.get("mpsdateto");//MPS上线日期
		String yjcqTo = (String) map.get("yjcqto");//预计船期
		//add by heshi 20130929 添加日期条件的到期日 end
		int dayCount = days.size();
		int k = 1;
		StringBuffer sqlB = new StringBuffer();
		sqlB.append("select z.*,rownum from (select d.zxbbbxs,a.sortl,a.aufnr,a.kdauf,a.kdpos,a.matnr,a.zgrun,to_char(a.gamng),a.edatu,b.zsxrq,a.vgw01,a.vgw02,to_char(e.sw),to_char(a.zwwcl),b.zwlzk,b.zqt,b.zzt,b.zqlzt,b.zwgpd,e.sm ");
		for (Object obj : days) {
			String daystr = String.valueOf(obj);
			for (int i = 1; i < 4; i++) {
				sqlB.append(",decode(to_char(t").append(daystr)
						.append(".gamng0").append(i).append("),null,'0',t")
						.append(daystr).append(".gamng0").append(i)
						.append(") a").append(k++);
			}
		}
		sqlB.append(
				" from (select c.zxh,c.aufnr from zppt0033 c where 1=1");
		if (StringUtils.isNotBlank(zxh)) {// 线别
			sqlB.append(" and c.zxh = '").append(zxh).append("'");
		}
		if (StringUtils.isNotBlank(scrq)||StringUtils.isNotBlank(scrqTo)) {
			// 生产日期
			sqlB.append(" and exists (select 1 from zppt0033 g where g.aufnr = c.aufnr and g.zxh = c.zxh ");
			if(StringUtils.isNotBlank(scrq)){
				sqlB.append(" and g.gstrp>='").append(scrq).append("' ");
			}
			if(StringUtils.isNotBlank(scrqTo)){
				sqlB.append(" and g.gstrp<='").append(scrqTo).append("' ");
			}
			sqlB.append(") ");
		}
		sqlB.append(" group by c.zxh,c.aufnr) temp1");
		sqlB.append(" inner join zppt0030 a on a.aufnr = temp1.aufnr ");
		if (StringUtils.isNotBlank(edatu)) {// sd交期
			sqlB.append(" and a.edatu >= '").append(edatu).append("' ");
		}
		if (StringUtils.isNotBlank(edatuTo)) {// sd交期
			sqlB.append(" and a.edatu <= '").append(edatuTo).append("' ");
		}
		if(StringUtils.isNotBlank(kdauf)){// 销售单号
			sqlB.append(" and a.kdauf = '").append(kdauf).append("' ");
		}
		if(StringUtils.isNotBlank(sortl)){// 客户编号
			sqlB.append(" and a.sortl = '").append(sortl).append("' ");
		}
		if(StringUtils.isNotBlank(matnr)){// 物料ID 
			sqlB.append(" and a.matnr = '").append(matnr).append("' ");
		}
		if(StringUtils.isNotBlank(zgrun)){// 物料描述
			sqlB.append(" and a.zgrun like '%").append(zgrun).append("%' ");
		}
		if (StringUtils.isNotBlank(gstrp)) {// 上线日期
			sqlB.append(" and a.gstrp >= '").append(gstrp).append("' ");
		}
		if (StringUtils.isNotBlank(gstrpTo)) {// 上线日期
			sqlB.append(" and a.gstrp <= '").append(gstrpTo).append("' ");
		}
		if (StringUtils.isNotBlank(gltrp)) {// 完工日期
			sqlB.append(" and a.gltrp >= '").append(gltrp).append("' ");
		}
		if (StringUtils.isNotBlank(gltrpTo)) {// 完工日期
			sqlB.append(" and a.gltrp <= '").append(gltrpTo).append("' ");
		}
		if (StringUtils.isNotBlank(yjcq)) {// 预计船期
			sqlB.append(" and a.zyjcq >= '").append(yjcq).append("' ");
		}
		if (StringUtils.isNotBlank(yjcqTo)) {// 预计船期
			sqlB.append(" and a.zyjcq <= '").append(yjcqTo).append("' ");
		}
		if (StringUtils.isNotBlank(aufnr)) {// 生产订单
			sqlB.append(" and a.aufnr = '").append(aufnr).append("' ");
		}
		sqlB.append(" inner join zppt0031 b on b.aufnr = temp1.aufnr ");
		if (StringUtils.isNotBlank(zsg)) {// 生管
			sqlB.append(" and b.zsg = '").append(zsg).append("' ");
		}
		if(StringUtils.isNotBlank(mpsdate)){
			sqlB.append(" and b.zsxrq >= '").append(mpsdate).append("' ");
		}
		if(StringUtils.isNotBlank(mpsdateTo)){
			sqlB.append(" and b.zsxrq <= '").append(mpsdateTo).append("' ");
		}
		if (StringUtils.isNotBlank(zzt)) {
			if ("-1".equals(zzt)) {
				//-1  未分配生管
				sqlB.append(" and b.aufnr is null ");
			} else if("0".equals(zzt)){ 
				//0.未安排
				sqlB.append(" and (b.zzt = '0' or b.zzt=' ') and b.zwgpd = '0' ");
			} else if ("1".equals(zzt)) {
				//1.未排满,
				sqlB.append(" and b.zzt = '1' and b.zwgpd = '0' ");
			} else if ("2".equals(zzt)){
				//2.已排满、正常
				sqlB.append(" and b.zzt = '2' and b.zqlzt !='1' and b.zwgpd = '0' ");
			} else if ("3".equals(zzt)){
				//3.已排满、欠料
				sqlB.append(" and b.zzt > '2' and b.zqlzt ='1' and b.zwgpd = '0' ");
			} else if ("4".equals(zzt)){
				//4.已排满、超计划量
				sqlB.append(" and b.zzt = '3' and b.zwgpd = '0' ");
			} else if ("5".equals(zzt)){
				//5.已排满、超交期
				sqlB.append(" and b.zzt = '5' and b.zwgpd = '0' ");
			} else if ("99".equals(zzt)){
				//99.完工
				sqlB.append(" and b.zwgpd ='1' ");
			} else if ("100".equals(zzt)){
				//100.归档
				sqlB.append(" and b.zwgpd ='2' ");
			} 
		}else{//未选择状态时默认查询非完工状态数据
			sqlB.append(" and b.zwgpd ='0' ");
		}
		if (StringUtils.isNotBlank(zclass) || StringUtils.isNotBlank(dwerks)
				|| StringUtils.isNotBlank(department)) {
			sqlB.append(" and exists (select 1 from zppt0034 d where d.zxh=temp1.zxh");
			if (StringUtils.isNotBlank(zclass)) {
				sqlB.append(" and d.class='").append(zclass).append("' ");
			}
			if (StringUtils.isNotBlank(dwerks)) {
				sqlB.append(" and d.dwerks='").append(dwerks).append("' ");
			}
			if (StringUtils.isNotBlank(department)) {
				sqlB.append(" and d.department='").append(department)
						.append("' ");
			}
			sqlB.append(" )");
		}
		sqlB.append(" inner join (select distinct zxh,zxbbbxs from zppt0034 where zrq='20130101') d on temp1.zxh = d.zxh");
		sqlB.append(" inner join (select aufnr,zxh,sum(gamng01+gamng02+gamng03) sm,sum(nvl(zwgl,0)) sw from zppt0033 group by aufnr,zxh)e on temp1.aufnr=e.aufnr and temp1.zxh = e.zxh ");
		sqlB.append(" left join (select max(gstrp) gstrp,zxh,aufnr from zppt0033 d where d.gstrp<='")
			.append(days.get(0))
			.append("' and d.zwgl>0 group by d.zxh,d.aufnr) f ")
			.append(" on temp1.aufnr = f.aufnr")
			.append(" and temp1.zxh = f.zxh");
		for (Object obj : days) {
			String daystr = String.valueOf(obj);
			sqlB.append(" left join (select zxh,gstrp,aufnr,sum(gamng01) gamng01,sum(gamng02) gamng02,sum(gamng03) gamng03 from zppt0033 group by zxh,gstrp,aufnr) t").append(daystr).append(" on t")
					.append(daystr).append(".aufnr = temp1.aufnr and t")
					.append(daystr).append(".zxh = temp1.zxh ").append("and t")
					.append(daystr).append(".gstrp = '").append(daystr)
					.append("'");
		}
		sqlB.append(" order by temp1.zxh asc");
		for (k = (dayCount*3-1); k > 0; k--) {
			sqlB.append(",case when a").append(k).append("='0' and a")
					.append(k + 1).append("='0' then 0 ");
			sqlB.append("when a").append(k).append("='0' and a").append(k + 1)
					.append("!='0' then 3 ");
			sqlB.append("when a").append(k).append("!='0' and a").append(k + 1)
					.append("!='0' then 2 ");
			sqlB.append("when a").append(k).append("!='0' and a").append(k + 1)
					.append("='0' then 1 ");
			sqlB.append("else 0 end asc ");
		}
		sqlB.append(" ,nvl(f.gstrp,'00000000') desc,to_number(b.zyxj) asc ,a.edatu asc,a.aufnr asc");
		sqlB.append(" ) z");
		sqlB.append(" where ");
		for (k = dayCount*3; k > 0; k--) {
			sqlB.append("a").append(k);
			if(k>1){
				sqlB.append("+");
			}
		}
		sqlB.append(">0");
		//System.out.println(sqlB.toString());
		List li = findListBySql(sqlB.toString());
		List<PlanShare> result = new ArrayList<PlanShare>();
		if (li != null && li.size() > 0) {
			for (Object ob : li) {
				Object[] o = (Object[]) ob;
				PlanShare share = new PlanShare();
				int i = 0;
				share.setLineNo(String.valueOf(o[i++]));// 线号
				share.setCustNo(String.valueOf(o[i++]));// 客户
				share.setProductOrder(String.valueOf(o[i++]));// 生产订单
				share.setSaleOrder(String.valueOf(o[i++]));// 销售订单
				share.setOrderLineNo(String.valueOf(o[i++]));// 销售订单行号
				share.setWlId(String.valueOf(o[i++]));// 物料ID
				share.setWlDesc(String.valueOf(o[i++]));// 物料描述
				share.setOrderAmt(String.valueOf(o[i++]));// 计划产量
				share.setSdExpDate(String.valueOf(o[i++]));// SD交期
				share.setMpsDate(String.valueOf(o[i++]));// MPS上线日期
				share.setZzHours(String.valueOf(o[i++]));// 总装标准工时
				share.setBzHours(String.valueOf(o[i++]));// 包装标准工时
				share.setCompleteAmt(String.valueOf(o[i++]));// 完成量
				share.setLeftAmt(String.valueOf(o[i++]));// 未完成量
				share.setWlStatus(String.valueOf(o[i++]));// 物料状况
				share.setDesc(String.valueOf(o[i++]));// 备注-其他
				String zt1 = String.valueOf(o[i++]);// 静态表状态
				String zt2 = String.valueOf(o[i++]);// 缺料状态
				String wgpd = String.valueOf(o[i++]);// 完工判断
				if ("1".equals(zt2)) {// 缺料
					share.setColor("red");
					if("1".equals(wgpd)){
						share.setStatus("完工");
						share.setColor("blue");
					} else if ("1".equals(zt1)) {
						share.setStatus("未排满");
						share.setColor("black");
					} else if ("2".equals(zt1)) {
						share.setStatus("已排满、欠料");
					} else if ("3".equals(zt1)) {
						share.setStatus("已排满、欠料");
					} else if ("5".equals(zt1)) {
						share.setStatus("已排满、欠料");
					}
				} else {// 不缺料
					if ("1".equals(wgpd)){
						share.setStatus("完工");
						share.setColor("blue");
					} else if ("0".equals(zt1)) {
						share.setStatus("未安排");
						share.setColor("black");
					} else if ("1".equals(zt1)) {
						share.setStatus("未排满");
						share.setColor("black");
					} else if ("2".equals(zt1)) {
						share.setStatus("已排满、正常");
						share.setColor("green");
					} else if ("3".equals(zt1)) {
						share.setStatus("已排满、超计划量");
						share.setColor("red");
					} else if ("5".equals(zt1)) {
						share.setStatus("已排满、超交期");
						share.setColor("red");
					}
				}
				share.setPlanAmt(String.valueOf(o[i++]));// 计划量
				if (dayCount > 0) {
					share.setDay1A(String.valueOf(o[i++]));
					share.setDay1B(String.valueOf(o[i++]));
					share.setDay1C(String.valueOf(o[i++]));
				}
				if (dayCount > 1) {
					share.setDay2A(String.valueOf(o[i++]));
					share.setDay2B(String.valueOf(o[i++]));
					share.setDay2C(String.valueOf(o[i++]));
				}
				if (dayCount > 2) {
					share.setDay3A(String.valueOf(o[i++]));
					share.setDay3B(String.valueOf(o[i++]));
					share.setDay3C(String.valueOf(o[i++]));
				}
				if (dayCount > 3) {
					share.setDay4A(String.valueOf(o[i++]));
					share.setDay4B(String.valueOf(o[i++]));
					share.setDay4C(String.valueOf(o[i++]));
				}
				if (dayCount > 4) {
					share.setDay5A(String.valueOf(o[i++]));
					share.setDay5B(String.valueOf(o[i++]));
					share.setDay5C(String.valueOf(o[i++]));
				}
				if (dayCount > 5) {
					share.setDay6A(String.valueOf(o[i++]));
					share.setDay6B(String.valueOf(o[i++]));
					share.setDay6C(String.valueOf(o[i++]));
				}
				if (dayCount > 6) {
					share.setDay7A(String.valueOf(o[i++]));
					share.setDay7B(String.valueOf(o[i++]));
					share.setDay7C(String.valueOf(o[i++]));
				}
				if (dayCount > 7) {
					share.setDay8A(String.valueOf(o[i++]));
					share.setDay8B(String.valueOf(o[i++]));
					share.setDay8C(String.valueOf(o[i++]));
				}
				if (dayCount > 8) {
					share.setDay9A(String.valueOf(o[i++]));
					share.setDay9B(String.valueOf(o[i++]));
					share.setDay9C(String.valueOf(o[i++]));
				}
				if (dayCount > 9) {
					share.setDay0A(String.valueOf(o[i++]));
					share.setDay0B(String.valueOf(o[i++]));
					share.setDay0C(String.valueOf(o[i++]));
				}
				result.add(share);
			}
		}
		return result;
	}

	@Override
	public ZPPT0031 get(String aufnr) {
		return sapDao.get(ZPPT0031.class,StringUtils.addLeftZero(aufnr,12));
	}

	@SuppressWarnings("rawtypes")
	@Override
	public String updateMpsDate(String client, String vbeln, String posnr, String date, String desc, String zsg,
			String aufnr) {
		String type = "";
		String query = "select 1 from zppt0045 where vbeln='"+vbeln+"' and posnr='"+posnr+"'";
		if(StringUtils.isNotBlank(aufnr)){
			query = query + " and aufnr='"+aufnr+"'";
		}else{
			query = query + " and aufnr=' '";
		}
		List li = findListBySql(query);
		String update = "";
		if(li!=null && li.size()>0){
			update = "update zppt0045 set zmpstime='"+date+"'";
			if(StringUtils.isNotBlank(desc)){
				update = update + ",remark='"+desc+"' ";
			}
			if(StringUtils.isNotBlank(zsg)){
				update = update + ",zsg='"+zsg+"' ";
			}
			update = update + " where vbeln='"+vbeln+"' and posnr='"+posnr+"'";
			if(StringUtils.isNotBlank(aufnr)){
				query = query + " and aufnr='"+aufnr+"'";
			}
			type = "update";
		}else{
			if(StringUtils.isBlank(desc)){
				desc = " ";
			}
			if(StringUtils.isBlank(zsg)){
				zsg = " ";
			}
			if(StringUtils.isBlank(aufnr)){
				aufnr = " ";
			}
			update = "insert into zppt0045 values('"+client+"','"+vbeln+"','"+posnr+"','"+date+"','"+desc+"','"+aufnr+"','"+zsg+"')";
			type = "insert";
		}
		try {
			execSql(update);
		} catch (Exception e) {
			type = type+"error";
		}
		return type;
	}
	
	@SuppressWarnings("unchecked")
	public void compareWorkOrder(String aufnr,String zxh){
		Double gdAmt = findGdAmtByAufnr(aufnr);
		String sql = "select nvl(sum(gamng01+gamng02+gamng03),0),nvl(sum(zwgl),0) from zppt0033 "
			+ " where aufnr = '"+aufnr+"' and zxh='"+zxh+"' and mandt="+getClient();
		List<Object> planList = findListBySql(sql);
		if(null==planList||planList.size()==0)
			return;
		//排产总量
		Double planAmt = Double.parseDouble(StringUtils.nullToString(((Object[])planList.get(0))[0]));
		//完工总量
		Double doneAmt = Double.parseDouble(StringUtils.nullToString(((Object[])planList.get(0))[1]));
		List<ZPPT0068> woList =(List<ZPPT0068>) sapDao.getSession().createQuery("from ZPPT0068 where aufnr='"+aufnr+"' and zxh='"+zxh+"' order by zgdh").list();
		String today = DateUtils.formatDate(new Date(), "yyyyMMdd");//当前日期
		Double woPlan = 0D;//工单计划量
		Double woDone = 0D;//工单完成量
		if(null!=woList&&woList.size()>0){
			//有历史工单
			for(ZPPT0068 wo:woList){
				woPlan+=wo.getZjrjhl();
				woDone+=wo.getZwczt();
			}
		}
		if(planAmt.compareTo(woPlan)==0){
			//计划量未变
			updateWorkOrderPublishDate(woList);
		}else if(planAmt.compareTo(woPlan)>0){
			//计划量增加
			addWorkOrders(aufnr,zxh,planAmt-woPlan,gdAmt,today);
		}else{
			//计划量减少
			deleteWorkOrders(aufnr,zxh,woPlan-planAmt,today);
		}
		//更新工单顺序号
		//updateOrderNumberForWorkOrder(zxh);
		//更新完工量
		if(doneAmt.compareTo(woDone)>0){
			updateCompleteCountForWorkOrder(aufnr,zxh,doneAmt);
		}
		updateWorkOrderStartAndPublishDay(aufnr,zxh,today);
	}
	
	/**
	 * 
	 * 更新工单昨日计划量及发布日期
	 * 
	 * @param woList
	 */
	private void updateWorkOrderPublishDate(List<ZPPT0068> woList){
		for(ZPPT0068 wo:woList){
			wo.setZzrjhl(wo.getZjrjhl());
			wo.setZfbdat(DateUtils.formatDate(new Date(), "yyyyMMdd"));
			sapDao.save(wo);
		}
	}

	/**
	 * 
	 * 根据生产订单号查询工单计划量
	 * 
	 * @param aufnr
	 * @return
	 */
	private Double findGdAmtByAufnr(String aufnr) {
		Double result = 10000D;
		String sql = "select zjhl from (select zjhl,1 as rn from zppt0069 where aufnr='"+StringUtils.addLeftZero(aufnr,12)+"' and mandt="+getClient()
		 + " union "
		 + " select a.zjhl,2 from zppt0069 a inner join zppt0031 b on 'SG_'||b.zsg =a.aufnr and a.mandt=b.mandt where b.aufnr= '"
		 + StringUtils.addLeftZero(aufnr,12)+"' and b.mandt="+getClient()+" )order by rn";
		List<Object> list = findListBySql(sql);
		if(list!=null&&list.size()>0){
			try {
				result = Double.parseDouble(StringUtils.nullToString(list.get(0)));
			} catch (Exception e) {
			}
		}
		return result;
	}

	/**
	 * 
	 * 更新当前工单的开始日期及发布日期
	 * 
	 * @param aufnr
	 * @param zxh
	 * @param today
	 */
	@SuppressWarnings("unchecked")
	private void updateWorkOrderStartAndPublishDay(String aufnr, String zxh, String today) {
		String sql = "select gstrp,sum(gamng01+gamng02+gamng03-nvl(zwgl,0)) from zppt0033 "
			+ " where aufnr = '"+aufnr+"' and zxh='"+zxh+"'"
			+ " group by gstrp order by gstrp asc";
		//倒序查询当前排产记录
		List<Object> planDescList = findListBySql(sql);
		List<ZPPT0068> woList = (List<ZPPT0068>) sapDao.getSession().createQuery("from ZPPT0068 where zxh='"+zxh+"' and aufnr='"+aufnr+"' and zzt not in ('C','X') order by zgdh asc").list();
		double amt = 0D;
		for(ZPPT0068 wo : woList){
			amt += wo.getZjrjhl()-wo.getZwczt();
			wo.setZksdat(getStartDate(planDescList,amt));
			wo.setZfbdat(today);
			if(wo.getZjrjhl().compareTo(wo.getZwczt())<=0){
				wo.setZzt("C");//状态为完工
			}
			sapDao.save(wo);
		}
	}

	/**
	 * 
	 * 自动更新当前线号所有正常工单的序号
	 * 
	 * @param zxh
	 */
	@SuppressWarnings("unchecked")
	private void updateOrderNumberForWorkOrder(String zxh) {
		//1.查找当前线别工单列表
		List<ZPPT0068> woList = (List<ZPPT0068>) sapDao.getSession().createQuery("from ZPPT0068 where zxh='"+zxh+"' and zzt not in ('C','X') order by zgdh asc").list();
		// 开始日期
		String beginDay = DateUtils.formatDate(new Date(), "yyyyMMdd");
		// 查询接下来十天内的可排产日期
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("zxh",zxh);
		String sqlA = "select zrq from(select zrq, rownum from (select distinct zrq from zppt0034 where zrq >= '"
				+ beginDay + "'"
				+ " and zxh='"+zxh+"'"
				+ " order by zrq) where rownum <= 10)";
		List<Object> tempList = findListBySql(sqlA);
		List<String> days = new ArrayList<String>();
		for(Object temp:tempList){
			days.add(StringUtils.nullToString(temp));
		}
		map.put("days", days);
		//2.查找当前线别排产记录列表(排序后)
		List<PlanShare> planList = getCurrentPlan(map);
		//3.根据当前计划列表的顺序,对工单列表进行排序并更新顺序号
		List<ZPPT0068> newWoList = new ArrayList<ZPPT0068>();
		for(PlanShare plan:planList){
			for(ZPPT0068 wo:woList){
				if(plan.getProductOrder().equals(wo.getAufnr())){
					newWoList.add(wo);
				}
			}
		}
		int index = 1;
		for(ZPPT0068 wo:newWoList){
			wo.setZsxh(StringUtils.addLeftZero(StringUtils.nullToString(index++),3));
			sapDao.getSession().update(wo);
		}
		String update = "update zppt0068 set zsxh = ' ' where zzt in ('C','X') and zsxh!=' ' and zxh='"+zxh+"'";
		execSql(update);
	}

	/**
	 * 
	 * 根据调整数量创建相应数量工单
	 * 
	 * @param aufnr 生产订单
	 * @param zxh 线号
	 * @param change 调整数量
	 * @param gdAmt 工单标准数量
	 * @param publishDate 发布日期
	 */
	private void addWorkOrders(String aufnr, String zxh, double change, double gdAmt, String publishDate) {
		//倒序查询排产计划列表,用于计算工单开始时间
		String sql = "select gstrp,sum(gamng01+gamng02+gamng03) from zppt0033 "
			+ " where aufnr = '"+aufnr+"' and zxh='"+zxh+"'"
			+ " group by gstrp order by gstrp asc";
		List<Object> planDescList = findListBySql(sql);
		Integer index = generateWorkOrderNum(aufnr);
		while(change>0D){
			if(change>gdAmt){//调整数量大于工单标准数量
				createWorkOrder(aufnr,zxh,gdAmt,publishDate,getStartDate(planDescList,change),index);
				change-=gdAmt; 
			}else{
				createWorkOrder(aufnr,zxh,change,publishDate,getStartDate(planDescList,change),index);
				change=0D;
			}
			index++;
		}
	}
	
	
	/**
	 * 
	 * 根据工单数量及排产计划列表,查找工单开始日期
	 * 
	 * @param dates
	 * @param map
	 * @param gdAmt
	 * @return
	 */
	private String getStartDate(List<Object> planDescList, double gdAmt) {
		Map<String,Double> map = new HashMap<String,Double>();
		List<String> dates = new ArrayList<String>();
		for(Object object:planDescList){
			Object[] temp = (Object[])object;
			map.put(StringUtils.nullToString(temp[0]),Double.valueOf(StringUtils.nullToString(temp[1])));
			dates.add(StringUtils.nullToString(temp[0]));
		}
		for(String date:dates){
			Double dateAmt = map.get(date);
			if(dateAmt.compareTo(gdAmt)>=0){
				map.put(date,dateAmt-gdAmt);
				return date;
			}else{
				gdAmt-=dateAmt;
				map.put(date,0D);
			}
		}
		return DateUtils.formatDate(new Date(), "yyyyMMdd");
	}

	/**
	 * 
	 * 创建新工单
	 * 
	 * @param aufnr
	 * @param zxh
	 * @param gdAmt
	 * @param publishDate
	 */
	@Override
	public void createWorkOrder(String aufnr, String zxh, double gdAmt, String publishDate,String startDay,Integer index) {
		for(int time=0;time<3;time++){
			try {
				String zgdh = StringUtils.removeLeftZero(aufnr).concat(StringUtils.addLeftZero(StringUtils.nullToString(index),2));
				ZPPT0030 zppt0030 = sapDao.get(ZPPT0030.class, aufnr);
				ZPPT0068 wo = new ZPPT0068();
				wo.setAufnr(aufnr);//生产订单
				wo.setKdauf(zppt0030.getKdauf());//销售订单
				wo.setKdpos(zppt0030.getKdpos());//销售订单行项
				wo.setMandt(getClient());//客户端
				wo.setMatnr(zppt0030.getMatnr());//物料号
				wo.setRemark(" ");//备注
				wo.setSortl(zppt0030.getSortl());//客户简称
				wo.setZfbdat(publishDate);//发布日期
				wo.setZgdh(zgdh);//工单号
				wo.setZgrun(zppt0030.getZgrun());//物料描述
				wo.setZjrjhl(gdAmt);//今天计划量
				wo.setZksdat((StringUtils.isBlank(startDay)||"00000000".equals(startDay))?publishDate:startDay);//开始日期
				wo.setZsxh(" ");//顺序号
				wo.setZwczt(0D);//完成量
				wo.setZxh(zxh);//线号
				wo.setZzrjhl(0D);//昨日计划量
				wo.setZzt("R");//状态正常
				sapDao.save(wo);
				return;
			} catch (Exception e) {
				if(logger.isDebugEnabled()){
					logger.debug("======="+aufnr+"========"+index);
				}
				continue;
			}
		}
	}

	/**
	 * 
	 * 生成工单号
	 * 
	 * @param aufnr 生产订单
	 * @return 生产订单号+2位序号
	 */
	private Integer generateWorkOrderNum(String aufnr) {
		String sql = "select nvl(max(substr(zgdh,11)),'00') from zppt0068 where aufnr='"+aufnr+"' and mandt="+getClient();
		String max = StringUtils.removeLeftZero(StringUtils.nullToString(findListBySql(sql).get(0)));
		int index = 1;
		if(StringUtils.isNotBlank(max)){
			index = Integer.valueOf(max)+1;
		}
		return index;
		//return StringUtils.removeLeftZero(aufnr).concat(StringUtils.addLeftZero(StringUtils.nullToString(index),2));
	}

	/**
	 * 
	 * 删除多余工单数量
	 * 
	 * @param aufnr 生产订单
	 * @param zxh 线号
	 * @param change 调整数量
	 * @param date 当前日期
	 */
	@SuppressWarnings("unchecked")
	private void deleteWorkOrders(String aufnr, String zxh, double change,String date) {
		List<ZPPT0068> woList =(List<ZPPT0068>) sapDao.getSession().createQuery("from ZPPT0068 where aufnr='"+aufnr+"' and zxh='"+zxh+"' order by zgdh desc").list();
		for(ZPPT0068 wo:woList){
			if(wo.getZjrjhl().compareTo(0D)>0){
				if(wo.getZjrjhl().compareTo(change)>0){
					//当前工单计划量大于调整数量,修改工单数量
					wo.setZzrjhl(wo.getZjrjhl());//昨日计划量
					wo.setZzt("M");//修改状态
					wo.setZjrjhl(wo.getZjrjhl()-change);//今日计划量
					wo.setZfbdat(date);
					change=0D;
				}else{
					change-=wo.getZjrjhl();
					wo.setZzrjhl(wo.getZjrjhl());//昨日计划量
					wo.setZjrjhl(0D);//今日计划量
					wo.setZzt("X");//取消状态
					wo.setZfbdat(date);
				}
				sapDao.getSession().update(wo);
			}
			if(change==0D){
				break;
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void updateCompleteCountForWorkOrder(String aufnr, String zxh, double wgAmt) {
		List<ZPPT0068> woList =(List<ZPPT0068>) sapDao.getSession().createQuery("from ZPPT0068 where aufnr='"+aufnr+"' and zxh='"+zxh+"' order by zgdh").list();
		for(ZPPT0068 wo:woList){
			Double minus = wo.getZjrjhl();//-wo.getZwczt()
			if(minus.compareTo(0D)>0){//工单完工量小于计划量
				if(minus.compareTo(wgAmt)>0){//计划量减去完工量大于总完工数量
					wo.setZwczt(wgAmt);
					wo.setZzt("R");//状态为正常
					wgAmt=0D;
				}else{
					wo.setZwczt(wo.getZjrjhl());
					wo.setZzt("C");//状态为完工
					wgAmt-=minus;
				}
				sapDao.getSession().update(wo);
			}
			if(wgAmt==0D){
				break;
			}
		}
	}

	@Override
	public User getCurrentUser(String userId) {
		return universalDao.findUniqueBy(User.class,"id",userId);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<PlanShare> findSharingPlanByPage(Map<String, Object> map) {
		String version = (String) map.get("version");
		List days = (List) map.get("days");
		String aufnr = (String) map.get("aufnr");// 生产订单号
		String zsg = (String) map.get("zsg");// 生管
		String zxh = (String) map.get("zxh");// 线号
		String dwerks = (String) map.get("dwerks");// 厂别
		String zclass = (String) map.get("class");// 课别
		String department = (String) map.get("department");// 部别
		String zzt = (String) map.get("zzt");// 状态
		String edatu = (String) map.get("edatu");// SD交期
		String gstrp = (String) map.get("gstrp");// 上线日期
		String gltrp = (String) map.get("gltrp");// 完工日期
		String scrq = (String) map.get("scrq");// 生产日期
		int dayCount = days.size();
		int k = 1;
		StringBuffer sqlB = new StringBuffer();
		sqlB.append("select z.*,rownum from (select d.zxbbbxs,a.sortl,a.aufnr,a.kdauf,a.kdpos,a.matnr,a.zgrun,to_char(a.gamng),a.edatu,m.zsxrq,a.vgw01,a.vgw02,to_char(e.sw),to_char(a.zwwcl),b.zwlzk,b.zqt,b.zzt,b.zqlzt,b.zwgpd,e.sm ");
		for (Object obj : days) {
			String daystr = String.valueOf(obj);
			for (int i = 1; i < 4; i++) {
				sqlB.append(",decode(to_char(t").append(daystr)
						.append(".gamng0").append(i).append("),null,'0',t")
						.append(daystr).append(".gamng0").append(i)
						.append(") a").append(k++);
			}
		}
		sqlB.append(
				" from (select c.zxh,c.aufnr,c.zversion from zppt0044 c where c.zversion like '")
				.append(version).append("%' ");
		if (StringUtils.isNotBlank(zxh)) {// 线别
			sqlB.append(" and c.zxh = '").append(zxh).append("' ");
		}
		if (StringUtils.isNotBlank(scrq)) {
			// 生产日期
			sqlB.append(
					"and exists (select 1 from zppt0044 g where g.aufnr = c.aufnr and c.zversion = g.zversion and g.zxh = c.zxh and g.gstrp='")
					.append(scrq).append("') ");
		}
		sqlB.append(" group by c.zxh,c.aufnr,c.zversion) temp1 ");
		sqlB.append(" inner join zppt0042 a on a.aufnr = temp1.aufnr and a.zversion = temp1.zversion ");
		if (StringUtils.isNotBlank(edatu)) {// sd交期
			sqlB.append(" and a.edatu = '").append(edatu).append("' ");
		}
		if (StringUtils.isNotBlank(gstrp)) {// 上线日期
			sqlB.append(" and a.gstrp = '").append(gstrp).append("' ");
		}
		if (StringUtils.isNotBlank(gltrp)) {// 完工日期
			sqlB.append(" and a.gltrp = '").append(gltrp).append("' ");
		}
		if (StringUtils.isNotBlank(aufnr)) {// 生产订单
			sqlB.append(" and a.aufnr = '").append(aufnr).append("' ");
		}
		sqlB.append(" inner join zppt0043 b on b.aufnr = temp1.aufnr and b.zversion = temp1.zversion ");
		if (StringUtils.isNotBlank(zsg)) {// 生管
			sqlB.append(" and b.zsg = '").append(zsg).append("' ");
		}
		sqlB.append(" left join zppt0031 m on a.aufnr = m.aufnr");
		if (StringUtils.isNotBlank(zzt)) {
			if ("-1".equals(zzt)) {
				//-1  未分配生管
				sqlB.append("and b.aufnr is null ");
			} else if("0".equals(zzt)){ 
				//0.未安排
				sqlB.append("and (b.zzt = '0' or b.zzt=' ') and b.zwgpd = '0' ");
			} else if ("1".equals(zzt)) {
				//1.未排满,
				sqlB.append("and b.zzt = '1' and b.zwgpd = '0' ");
			} else if ("2".equals(zzt)){
				//2.已排满、正常
				sqlB.append("and b.zzt = '2' and b.zqlzt !='1' and b.zwgpd = '0' ");
			} else if ("3".equals(zzt)){
				//3.已排满、欠料
				sqlB.append("and b.zzt > '2' and b.zqlzt ='1' and b.zwgpd = '0' ");
			} else if ("4".equals(zzt)){
				//4.已排满、超计划量
				sqlB.append("and b.zzt = '3' and b.zwgpd = '0' ");
			} else if ("5".equals(zzt)){
				//5.已排满、超交期
				sqlB.append("and b.zzt = '5' and b.zwgpd = '0' ");
			} else if ("99".equals(zzt)){
				//99.完工
				sqlB.append("and b.zwgpd ='1' ");
			} else if ("100".equals(zzt)){
				//100.归档
				sqlB.append("and b.zwgpd ='2' ");
			}
		}else{
			sqlB.append(" and b.zwgpd ='0' ");
		}
		if (StringUtils.isNotBlank(zclass) || StringUtils.isNotBlank(dwerks)
				|| StringUtils.isNotBlank(department)) {
			sqlB.append(" and exists (select 1 from zppt0034 d where d.zxh=temp1.zxh ");
			if (StringUtils.isNotBlank(zclass)) {
				sqlB.append(" and d.class='").append(zclass).append("' ");
			}
			if (StringUtils.isNotBlank(dwerks)) {
				sqlB.append(" and d.dwerks='").append(dwerks).append("' ");
			}
			if (StringUtils.isNotBlank(department)) {
				sqlB.append(" and d.department='").append(department)
						.append("' ");
			}
			sqlB.append(" )");
		}
		sqlB.append(" inner join (select distinct zxh,zxbbbxs from zppt0034 where zrq='20130101') d on temp1.zxh = d.zxh ");
		sqlB.append(" left join (select aufnr,zxh,sum(gamng01+gamng02+gamng03) sm,sum(nvl(zwgl,0)) sw from zppt0033 group by aufnr,zxh)e on temp1.aufnr=e.aufnr and temp1.zxh = e.zxh ");
		sqlB.append(" left join (select max(gstrp) gstrp,zxh,aufnr from zppt0033 d where d.gstrp<='")
			.append(days.get(0))
			.append("' and d.zwgl>0 group by d.zxh,d.aufnr) f ")
			.append(" on temp1.aufnr = f.aufnr") 
			.append(" and temp1.zxh = f.zxh");
		for (Object obj : days) {
			String daystr = String.valueOf(obj);
			sqlB.append(" left join (select zxh,gstrp,aufnr,zversion,sum(gamng01) gamng01,sum(gamng02) gamng02,sum(gamng03) gamng03 from zppt0044 group by zxh,gstrp,aufnr,zversion) t").append(daystr).append(" on t")
					.append(daystr).append(".aufnr = temp1.aufnr and t")
					.append(daystr).append(".zversion = temp1.zversion ")
					.append("and t").append(daystr).append(".zxh = temp1.zxh ")
					.append("and t").append(daystr).append(".gstrp = '")
					.append(daystr).append("'");
		}
		sqlB.append(" order by temp1.zxh asc");
		for (k = (dayCount*3-1); k > 0; k--) {
			sqlB.append(",case when a").append(k).append("='0' and a")
					.append(k + 1).append("='0' then 0 ");
			sqlB.append("when a").append(k).append("='0' and a").append(k + 1)
					.append("!='0' then 3 ");
			sqlB.append("when a").append(k).append("!='0' and a").append(k + 1)
					.append("!='0' then 2 ");
			sqlB.append("when a").append(k).append("!='0' and a").append(k + 1)
					.append("='0' then 1 ");
			sqlB.append("else 0 end asc ");
		}
		sqlB.append(" ,nvl(f.gstrp,'00000000') desc,to_number(b.zyxj) asc ,a.edatu asc,a.aufnr asc");
		sqlB.append(" ) z");
		sqlB.append(" where ");
		for (k = dayCount*3; k > 0; k--) {
			sqlB.append("a").append(k);
			if(k>1){
				sqlB.append("+");
			}
		}
		sqlB.append(">0");
		//System.out.println(sqlB.toString());
		List li = findListBySql(sqlB.toString());
		List<PlanShare> result = new ArrayList<PlanShare>();
		if (li != null && li.size() > 0) {
			for (Object ob : li) {
				Object[] o = (Object[]) ob;
				PlanShare share = new PlanShare();
				int i = 0;
				share.setLineNo(String.valueOf(o[i++]));// 线号
				share.setCustNo(String.valueOf(o[i++]));// 客户
				share.setProductOrder(StringUtils.removeLeftZero(String.valueOf(o[i++])));// 生产订单
				share.setSaleOrder(StringUtils.removeLeftZero(String.valueOf(o[i++])));// 销售订单
				share.setOrderLineNo(StringUtils.removeLeftZero(String.valueOf(o[i++])));// 销售订单行号
				share.setWlId(StringUtils.removeLeftZero(String.valueOf(o[i++])));// 物料ID
				share.setWlDesc(String.valueOf(o[i++]));// 物料描述
				share.setOrderAmt(String.valueOf(o[i++]));// 计划产量
				share.setSdExpDate(String.valueOf(o[i++]));// SD交期
				share.setMpsDate(String.valueOf(o[i++]));// MPS上线日期
				share.setZzHours(String.valueOf(o[i++]));// 总装标准工时
				share.setBzHours(String.valueOf(o[i++]));// 包装标准工时
				share.setCompleteAmt(String.valueOf(o[i++]));// 完成量
				share.setLeftAmt(String.valueOf(o[i++]));// 未完成量
				share.setWlStatus(String.valueOf(o[i++]));// 物料状况
				share.setDesc(String.valueOf(o[i++]));// 备注-其他
				String zt1 = String.valueOf(o[i++]);// 静态表状态
				String zt2 = String.valueOf(o[i++]);// 缺料状态
				String wgpd = String.valueOf(o[i++]);// 完工判断
				if ("1".equals(zt2)) {// 缺料
					share.setColor("red");
					if("1".equals(wgpd)){
						share.setStatus("完工");
						share.setColor("blue");
					} else if ("1".equals(zt1)) {
						share.setStatus("未排满");
						share.setColor("black");
					} else if ("2".equals(zt1)) {
						share.setStatus("已排满、欠料");
					} else if ("3".equals(zt1)) {
						share.setStatus("已排满、欠料");
					} else if ("5".equals(zt1)) {
						share.setStatus("已排满、欠料");
					}
				} else {// 不缺料
					if ("1".equals(wgpd)){
						share.setStatus("完工");
						share.setColor("blue");
					} else if ("0".equals(zt1)) {
						share.setStatus("未安排");
						share.setColor("black");
					} else if ("1".equals(zt1)) {
						share.setStatus("未排满");
						share.setColor("black");
					} else if ("2".equals(zt1)) {
						share.setStatus("已排满、正常");
						share.setColor("green");
					} else if ("3".equals(zt1)) {
						share.setStatus("已排满、超计划量");
						share.setColor("red");
					} else if ("5".equals(zt1)) {
						share.setStatus("已排满、超交期");
						share.setColor("red");
					}
				}
				share.setPlanAmt(String.valueOf(o[i++]));// 计划量
				if (dayCount > 0) {
					share.setDay1A(String.valueOf(o[i++]));
					share.setDay1B(String.valueOf(o[i++]));
					share.setDay1C(String.valueOf(o[i++]));
				}
				if (dayCount > 1) {
					share.setDay2A(String.valueOf(o[i++]));
					share.setDay2B(String.valueOf(o[i++]));
					share.setDay2C(String.valueOf(o[i++]));
				}
				if (dayCount > 2) {
					share.setDay3A(String.valueOf(o[i++]));
					share.setDay3B(String.valueOf(o[i++]));
					share.setDay3C(String.valueOf(o[i++]));
				}
				if (dayCount > 3) {
					share.setDay4A(String.valueOf(o[i++]));
					share.setDay4B(String.valueOf(o[i++]));
					share.setDay4C(String.valueOf(o[i++]));
				}
				if (dayCount > 4) {
					share.setDay5A(String.valueOf(o[i++]));
					share.setDay5B(String.valueOf(o[i++]));
					share.setDay5C(String.valueOf(o[i++]));
				}
				if (dayCount > 5) {
					share.setDay6A(String.valueOf(o[i++]));
					share.setDay6B(String.valueOf(o[i++]));
					share.setDay6C(String.valueOf(o[i++]));
				}
				if (dayCount > 6) {
					share.setDay7A(String.valueOf(o[i++]));
					share.setDay7B(String.valueOf(o[i++]));
					share.setDay7C(String.valueOf(o[i++]));
				}
				if (dayCount > 7) {
					share.setDay8A(String.valueOf(o[i++]));
					share.setDay8B(String.valueOf(o[i++]));
					share.setDay8C(String.valueOf(o[i++]));
				}
				if (dayCount > 8) {
					share.setDay9A(String.valueOf(o[i++]));
					share.setDay9B(String.valueOf(o[i++]));
					share.setDay9C(String.valueOf(o[i++]));
				}
				if (dayCount > 9) {
					share.setDay0A(String.valueOf(o[i++]));
					share.setDay0B(String.valueOf(o[i++]));
					share.setDay0C(String.valueOf(o[i++]));
				}
				result.add(share);
			}
		}
		return result;
	}
	
	/**
	 * 
	 * 发布后调用MES存储过程,读取工单信息
	 *
	 */
	@Override
	public void callMesProcedureAfterPublish(){
		String url = StringUtils.nullToString(propertyConfigurer.getProperty("multi.tenancy.sap.url"));
		String user = StringUtils.nullToString(propertyConfigurer.getProperty("multi.tenancy.sap.username"));
		String pwd = StringUtils.nullToString(propertyConfigurer.getProperty("multi.tenancy.sap.password"));
		String procedure = "{call mes_wo_interface_pck.p_mes_work_order}";
		Connection conn = null;
		CallableStatement callStatement = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver").newInstance();
			conn = DriverManager.getConnection(url, user, pwd);
			long begin = System.currentTimeMillis();
			callStatement = conn.prepareCall(procedure);
			callStatement.executeUpdate();
			long end = System.currentTimeMillis();
			System.out.println("发布后调用MES存储过程耗时:"+(end-begin)+" ms");
		} catch (Exception e) {
			System.out.println("发布后调用MES存储过程出错:"+e.getMessage());
		}finally{
			try {
				callStatement.close();
				conn.close();
			} catch (Exception e2) {
			}
		}
	}
	
	@Override
	public List<Object[]> findMesFinishData(String zsg, String zrq) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public static void main(String[] args) {
		String url = "jdbc:oracle:thin:@192.168.0.103:1527:qas";
		String user = "rdo_sap";
		String pwd = "rdosap";
		String procedure = "{call mes_wo_interface_pck.p_mes_work_order}";
		Connection conn = null;
		CallableStatement callStatement = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver").newInstance();
			conn = DriverManager.getConnection(url, user, pwd);
			long begin = System.currentTimeMillis();
			callStatement = conn.prepareCall(procedure);
			callStatement.executeUpdate();
			long end = System.currentTimeMillis();
			System.out.println("发布后调用MES存储过程共:"+(end-begin)+" ms");
		} catch (Exception e) {
			System.out.println("发布后调用MES存储过程出错:"+e.getMessage());
		}finally{
			try {
				callStatement.close();
				conn.close();
			} catch (Exception e2) {
			}
		}
	}
}