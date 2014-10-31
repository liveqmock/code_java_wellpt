package com.wellsoft.pt.ldx.service.sampleTrack.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Query;

import org.hibernate.SQLQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wellsoft.pt.basicdata.datasource.entity.DataSourceColumn;
import com.wellsoft.pt.basicdata.dyview.provider.ViewColumn;
import com.wellsoft.pt.core.support.PagingInfo;
import com.wellsoft.pt.core.support.QueryItem;
import com.wellsoft.pt.core.support.QueryItemResultTransformer;
import com.wellsoft.pt.ldx.dao.sampleTrack.SampleDao;
import com.wellsoft.pt.ldx.model.sampleTrack.SampleTrack;
import com.wellsoft.pt.ldx.model.sampleTrack.SampleTrackSearckParam;
import com.wellsoft.pt.ldx.service.impl.SapServiceImpl;
import com.wellsoft.pt.ldx.service.sampleTrack.SampleTrackService;
import com.wellsoft.pt.ldx.util.StringUtils;

@Service
@Transactional
@SuppressWarnings("rawtypes")
public class SampleTrackServiceImpl  extends SapServiceImpl implements SampleTrackService{
	@Autowired
	private SampleDao sampleDao;
	@Override
	public List<Object[]> findSampleFeeSettleListByPage(String groupCondition,String year,String sampleCharge1
			,String productMode1){
		String oldYear = Integer.parseInt(year)-1+"";
		String currentYear = year;
		String[] day = {"26","26"};
		StringBuffer sql = new StringBuffer("with de as ( ");
		if("部门名称".equals(groupCondition)){
			sql.append(" select applyDept, ");
		}else if("客户代码".equals(groupCondition)){
			sql.append(" select customerId, ");
		}		
		sql.append(" SUM(to_number(case when completeDate >='"+oldYear+"-12-"+day[0]+"' and completeDate <'"+currentYear+"-01-"+day[1]+"' then sampleCost else '0' end)) as A1, ");
		sql.append(" SUM(to_number(case when completeDate >='"+oldYear+"-12-"+day[0]+"' and completeDate <'"+currentYear+"-01-"+day[1]+"' then piMoney else '0' end)) as B1, ");
		sql.append(" SUM(to_number(case when completeDate >='"+currentYear+"-01-"+day[0]+"' and completeDate <'"+currentYear+"-02-"+day[1]+"' then sampleCost else '0' end)) as A2, ");
		sql.append(" SUM(to_number(case when completeDate >='"+currentYear+"-01-"+day[0]+"' and completeDate <'"+currentYear+"-02-"+day[1]+"' then piMoney else '0' end)) as B2, ");
		sql.append(" SUM(to_number(case when completeDate >='"+currentYear+"-02-"+day[0]+"' and completeDate <'"+currentYear+"-03-"+day[1]+"' then sampleCost else '0' end)) as A3, ");
		sql.append(" SUM(to_number(case when completeDate >='"+currentYear+"-02-"+day[0]+"' and completeDate <'"+currentYear+"-03-"+day[1]+"' then piMoney else '0' end)) as B3, ");
		sql.append(" SUM(to_number(case when completeDate >='"+currentYear+"-03-"+day[0]+"' and completeDate <'"+currentYear+"-04-"+day[1]+"' then sampleCost else '0' end)) as A4, ");
		sql.append(" SUM(to_number(case when completeDate >='"+currentYear+"-03-"+day[0]+"' and completeDate <'"+currentYear+"-04-"+day[1]+"' then piMoney else '0' end)) as B4, ");
		sql.append(" SUM(to_number(case when completeDate >='"+currentYear+"-04-"+day[0]+"' and completeDate <'"+currentYear+"-05-"+day[1]+"' then sampleCost else '0' end)) as A5, ");
		sql.append(" SUM(to_number(case when completeDate >='"+currentYear+"-04-"+day[0]+"' and completeDate <'"+currentYear+"-05-"+day[1]+"' then piMoney else '0' end)) as B5, ");
		sql.append(" SUM(to_number(case when completeDate >='"+currentYear+"-05-"+day[0]+"' and completeDate <'"+currentYear+"-06-"+day[1]+"' then sampleCost else '0' end)) as A6, ");
		sql.append(" SUM(to_number(case when completeDate >='"+currentYear+"-05-"+day[0]+"' and completeDate <'"+currentYear+"-06-"+day[1]+"' then piMoney else '0' end)) as B6, ");
		sql.append(" SUM(to_number(case when completeDate >='"+currentYear+"-06-"+day[0]+"' and completeDate <'"+currentYear+"-07-"+day[1]+"' then sampleCost else '0' end)) as A7, ");
		sql.append(" SUM(to_number(case when completeDate >='"+currentYear+"-06-"+day[0]+"' and completeDate <'"+currentYear+"-07-"+day[1]+"' then piMoney else '0' end)) as B7, ");
		sql.append(" SUM(to_number(case when completeDate >='"+currentYear+"-07-"+day[0]+"' and completeDate <'"+currentYear+"-08-"+day[1]+"' then sampleCost else '0' end)) as A8, ");
		sql.append(" SUM(to_number(case when completeDate >='"+currentYear+"-07-"+day[0]+"' and completeDate <'"+currentYear+"-08-"+day[1]+"' then piMoney else '0' end)) as B8, ");
		sql.append(" SUM(to_number(case when completeDate >='"+currentYear+"-08-"+day[0]+"' and completeDate <'"+currentYear+"-09-"+day[1]+"' then sampleCost else '0' end)) as A9, ");
		sql.append(" SUM(to_number(case when completeDate >='"+currentYear+"-08-"+day[0]+"' and completeDate <'"+currentYear+"-09-"+day[1]+"' then piMoney else '0' end)) as B9, ");
		sql.append(" SUM(to_number(case when completeDate >='"+currentYear+"-09-"+day[0]+"' and completeDate <'"+currentYear+"-10-"+day[1]+"' then sampleCost else '0' end)) as A10, ");
		sql.append(" SUM(to_number(case when completeDate >='"+currentYear+"-09-"+day[0]+"' and completeDate <'"+currentYear+"-10-"+day[1]+"' then piMoney else '0' end)) as B10, ");
		sql.append(" SUM(to_number(case when completeDate >='"+currentYear+"-10-"+day[0]+"' and completeDate <'"+currentYear+"-11-"+day[1]+"' then sampleCost else '0' end)) as A11, ");
		sql.append(" SUM(to_number(case when completeDate >='"+currentYear+"-10-"+day[0]+"' and completeDate <'"+currentYear+"-11-"+day[1]+"' then piMoney else '0' end)) as B11, ");
		sql.append(" SUM(to_number(case when completeDate >='"+currentYear+"-11-"+day[0]+"' and completeDate <'"+currentYear+"-12-"+day[1]+"' then sampleCost else '0' end)) as A12, ");
		sql.append(" SUM(to_number(case when completeDate >='"+currentYear+"-11-"+day[0]+"' and completeDate <'"+currentYear+"-12-"+day[1]+"' then piMoney else '0' end)) as B12 ");
		sql.append(" from USERFORM_SAMPLE_TRACK ");
		sql.append(" where 1=1 and completeDate is not null ");
		sql.append(" and sampleCharge = '"+sampleCharge1+"'");//费用承担方
		sql.append(" and productMode = '"+productMode1+"'");//生产方式		
		if("部门名称".equals(groupCondition)){
			sql.append(" group by applyDept ");
		}else if("客户代码".equals(groupCondition)){
			sql.append(" group by customerId ");
		}
		sql.append(" )");
		sql.append(" select * from de ");
		sql.append(" union all ");
		sql.append(" select '总计',SUM(A1),SUM(B1),SUM(A2),SUM(B2),SUM(A3),SUM(B3),SUM(A4),SUM(B4),SUM(A5),SUM(B5),SUM(A6),SUM(B6),SUM(A7),SUM(B7),SUM(A8),SUM(B8),SUM(A9),SUM(B9),SUM(A10),SUM(B10),SUM(A11),SUM(B11),SUM(A12),SUM(B12) from de ");		
		/*if("部门名称".equals(groupCondition)){
			sql.append(" order by applyDept ");
		}else if("客户代码".equals(groupCondition)){
			sql.append(" order by customerId ");
		}	*/
		List list = this.dao.getSession().createSQLQuery(sql.toString()).list();
		List<Object[]> resultList = list;
		return resultList;
	}
	/**
	 * 样品课返回结果查询
	 */
	@Override
	public List findData(Set<DataSourceColumn> dataSourceColumn,
			String whereHql, PagingInfo pagingInfo) {

		StringBuffer sql = new StringBuffer("select ");
		boolean start = true;
		for (DataSourceColumn col : dataSourceColumn) {
			if (!start) {
				sql.append(",");
			} else {
				start = false;
			}
//			if (col.getAttributeName().indexOf(".") == -1) {
//				sql.append("a.");
//			}
			sql.append(col.getFieldName()).append(" as ")
					.append(col.getColumnAliase());
		}

		sql.append(" from ").append(whereHql);
		SQLQuery localQuery = this.dao.getSession().createSQLQuery(
				sql.toString());
		if (pagingInfo.getCurrentPage() != -1) {
			localQuery.setFirstResult(pagingInfo.getFirst());
			localQuery.setMaxResults(pagingInfo.getPageSize());
		}
		localQuery.setResultTransformer(QueryItemResultTransformer.INSTANCE);
		List<QueryItem> localList = localQuery.list();

		if (pagingInfo.isAutoCount()) {
			localQuery = this.dao.getSession().createSQLQuery(
					new StringBuilder().append("select count(1) from ")
							.append(whereHql).toString());
			pagingInfo.setTotalCount(((BigDecimal) localQuery.uniqueResult())
					.longValue());
		}
		List<QueryItem> items = new ArrayList<QueryItem>();
		if (null != localList && localList.size() > 0) {
			for (QueryItem item : localList) {
				QueryItem itemres = new QueryItem();
				for (String key : item.keySet()) {
					itemres.put(key.toLowerCase(), item.get(key));
				}
				items.add(itemres);
			}
		}
		return items;
	}
	/**
	 * 根据行号查询样品记录
	 */
	@Override
	public List findByEntity(String lineItemId){
		StringBuffer hql = new StringBuffer("from SampleTrack  where 1 = 1 ")
		.append(" and lineitemid="+lineItemId);
		List<SampleTrack> list = this.dao.getSession().createQuery(hql.toString()).list();
		
		return list;
	}
/**
 * 查询单个样品
 */
	@Override
	public SampleTrack findEntityById(String lineItemId,String type) throws ParseException{
//		List<SampleTrack> list = sampleDao.find("from SampleTrack where lineItemId=?", lineItemId);
		String sql = "select lineItemId,planStart,planEnd,prodStatus,prodStatusMemo,ledSampleMemo,unitPrice from USERFORM_SAMPLE_TRACK where lineItemId="+lineItemId;
		String sql1 = "select lineItemId,sampleDestination,sampleDate,expressageNum,customerResult,qcExceptReply,prodExceptReply from USERFORM_SAMPLE_TRACK where lineItemId="+lineItemId;
		String sql2 = "select lineItemId,qcCheckDate,qcFinishDate,qcCheckResult,qcSecondResult,qcCheckItem,qcCheckMemo,qcExceptCause from USERFORM_SAMPLE_TRACK where lineItemId="+lineItemId;
		
		SampleTrack sampleTrack = new SampleTrack();
		
//			sampleTrack = list.get(0);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-M-dd HH:mm:ss");
		if("样品课反馈结果".equals(type)){
			List list = this.dao.getSession().createSQLQuery(sql).list();
			if(list!=null&&list.size()>0){
				Object[] obj = (Object[]) list.get(0);
				sampleTrack.setLineitemid(obj[0]!=null?obj[0].toString():"");
				if(null!=obj[1])sampleTrack.setPlanstart(format.parse(obj[1].toString()));
				if(null!=obj[2])sampleTrack.setPlanend(format.parse(obj[2].toString()));
				if(null!=obj[3])sampleTrack.setProdstatus(Integer.parseInt(obj[3].toString()));
				sampleTrack.setProdstatusmemo(obj[4]!=null?obj[4].toString():"");
				sampleTrack.setLedsamplememo(obj[5]!=null?obj[5].toString():"");
				sampleTrack.setUnitprice(obj[6]!=null?obj[6].toString():"");
			}
		}else if("客户反馈结果".equals(type)){
			List list = this.dao.getSession().createSQLQuery(sql1).list();
			if(list!=null&&list.size()>0){
				Object[] obj = (Object[]) list.get(0);
				sampleTrack.setLineitemid(obj[0]!=null?obj[0].toString():"");
				sampleTrack.setSampledestination(obj[1]!=null?obj[1].toString():"");
				if(null!=obj[2])sampleTrack.setSampledate(format.parse(obj[2].toString()));
				sampleTrack.setExpressagenum(obj[3]!=null?obj[3].toString():"");
				sampleTrack.setCustomerresult(obj[4]!=null?obj[4].toString():"");
				sampleTrack.setQcexceptreply(obj[5]!=null?obj[5].toString():"");
				sampleTrack.setProdexceptreply(obj[6]!=null?obj[6].toString():"");
			}
		}else if("品保检验结果".equals(type)){
			List list = this.dao.getSession().createSQLQuery(sql2).list();
			if(list!=null&&list.size()>0){
				Object[] obj = (Object[]) list.get(0);
				if(null!=obj[0])sampleTrack.setLineitemid(obj[0].toString());
				if(null!=obj[1])sampleTrack.setQccheckdate(format.parse(obj[1].toString()));
				if(null!=obj[2])sampleTrack.setQcfinishdate(format.parse(obj[2].toString()));
				if(null!=obj[3])sampleTrack.setQccheckresult(Integer.parseInt(obj[3].toString()));
				if(null!=obj[4])sampleTrack.setQcsecondresult(Integer.parseInt(obj[4].toString()));
				sampleTrack.setQccheckitem(obj[5]!=null?obj[5].toString():"");
				sampleTrack.setQccheckmemo(obj[6]!=null?obj[6].toString():"");
				sampleTrack.setQcexceptcause(obj[7]!=null?obj[7].toString():"");
			}
		}
		return sampleTrack;
	}
	/**
	 * 样品跟踪查询
	 */
	@Override
	public Map findSample(SampleTrackSearckParam paramSearch){
		Map map = new HashMap();
		SampleTrack sampleTrack = new SampleTrack();
		StringBuffer hql = new StringBuffer("from SampleTrack  where 1 = 1 ");
		StringBuffer sql = new StringBuffer("select count(1) from USERFORM_SAMPLE_TRACK  where 1 = 1 ");
		StringBuffer sql2 = new StringBuffer("select sum(case when projectStatus='0' then 1 else 0 end) as Q1,sum(case when projectStatus='1' then 1 else 0 end) as Q2,sum(case when sampleOrderStatus='1' then 1 else 0 end) as Q3,sum(stockNubmer) as Q4,sum(case when sampleOrderStatus='3' then 1 else 0 end) as Q5,sum(case when sampleOrderStatus='4' then 1 else 0 end) as Q6,sum(to_number(case when completeDate !='取消' then sampleCost else '0' end)) as Q7,sum(to_number(case when completeDate !='取消' and piMoneyUnit = 'USD' then piMoney else '0' end)) as Q8,sum(to_number(case when completeDate !='取消' and piMoneyUnit = 'RMB' then piMoney else '0' end)) as Q9 from USERFORM_SAMPLE_TRACK  where 1 = 1 ");
		queryCondition(paramSearch,hql,sql,sql2);
		
		List<SampleTrack> result = this.dao.getSession().createQuery(hql.toString()).list();	
		for (Object object : result) {
			sampleTrack = (SampleTrack)object;
			if(sampleTrack.getSamplepredictddate()!=null){
				int day = daysOfTwo(new Date(), sampleTrack.getSamplepredictddate());
				sampleTrack.setDay(new Integer(day));
			}		
		}
		map.put("sample", result);
		
		List<Object[]> list = this.dao.getSession().createSQLQuery(sql2.toString()).list();
		
		map.put("list", list);
		
		return map;
		
	}
	@Override
	public List<SampleTrack> exportSampleTrack(SampleTrackSearckParam paramSearch){
		
		StringBuffer hql = new StringBuffer("from SampleTrack  where 1 = 1 ");
		StringBuffer sql = new StringBuffer("select count(1) from SampleTrack  where 1 = 1 ");
		StringBuffer sql2 = new StringBuffer("select sum(stockNubmer) from c_sample_track  where 1 = 1 ");
		queryCondition(paramSearch,hql,sql,sql2);
		List<SampleTrack> result = this.dao.getSession().createQuery(hql.toString()).list();
		return result;
	}
	/**
	 * 样品课反馈结果维护
	 */
	@Override
	public void prodResultUpdate(String planStart, String planEnd, String prodStatus,
			String prodStatusMemo, String ledSampleMemo, String unitPrice,String lineItemId){
		StringBuffer sql = new StringBuffer("update USERFORM_SAMPLE_TRACK t set ")
			.append("t.planStart=to_date('").append(planStart).append("','yyyy-mm-dd'),")
			.append("t.planEnd=to_date('").append(planEnd).append("','yyyy-mm-dd'),");
			if(StringUtils.isNotEmpty(prodStatus))sql.append("t.prodStatus=").append(prodStatus).append(",");
			sql.append("t.prodStatusMemo='").append(prodStatusMemo).append("',")
			.append("t.ledSampleMemo='").append(ledSampleMemo).append("',")
			.append("t.unitPrice='").append(unitPrice).append("'")
			.append(" where t.lineItemId='").append(lineItemId).append("'");
		this.dao.getSession().createSQLQuery(sql.toString()).executeUpdate();
	}
	/**
	 * 客户反馈结果维护
	 * @param sampleDestination
	 * @param sampleDate
	 * @param expressageNum
	 * @param customerResult
	 * @param qcExceptReply
	 * @param prodExceptReply
	 * @param lineItemId
	 */
	public void customerUpdate(String sampleDestination,String sampleDate,String expressageNum,String customerResult
			,String qcExceptReply,String prodExceptReply,String lineItemId){
		StringBuffer sql = new StringBuffer("update USERFORM_SAMPLE_TRACK set ")
			.append("sampleDestination='").append(sampleDestination).append("',")
			.append("sampleDate=to_date('").append(sampleDate).append("','yyyy-mm-dd'),")
			.append("expressageNum='").append(expressageNum).append("',")
			.append("customerResult='").append(customerResult).append("',")
			.append("qcExceptReply='").append(qcExceptReply).append("',")
			.append("prodExceptReply='").append(prodExceptReply).append("'")
			.append(" where lineItemId='").append(lineItemId).append("'");
		this.dao.getSession().createSQLQuery(sql.toString()).executeUpdate();
	}
	/**
	 * 品保检验结果
	 * @param qcCheckDate
	 * @param qcFinishDate
	 * @param qcSecondResult
	 * @param qcCheckResult
	 * @param qcCheckItem
	 * @param qcCheckMemo
	 * @param qcExceptCause
	 * @param lineItemId
	 */
	public void qcCheckUpdate(String qcCheckDate,String qcFinishDate,String qcSecondResult,String qcCheckResult
			,String qcCheckItem,String qcCheckMemo,String qcExceptCause,String lineItemId){
		StringBuffer sql = new StringBuffer("update USERFORM_SAMPLE_TRACK t set ")
			.append("t.qcCheckDate=to_date('").append(qcCheckDate).append("','yyyy-mm-dd'),")
			.append("t.qcFinishDate=to_date('").append(qcFinishDate).append("','yyyy-mm-dd'),");
			if(StringUtils.isNotEmpty(qcCheckResult))sql.append("t.qcCheckResult=").append(qcCheckResult).append(",");
			if(StringUtils.isNotEmpty(qcSecondResult))sql.append("t.qcSecondResult=").append(qcSecondResult).append(",");
			sql.append("qcCheckMemo='").append(qcCheckMemo).append("',")
			.append("qcCheckItem='").append(qcCheckItem).append("',")
			.append("qcExceptCause='").append(qcExceptCause).append("'")
			.append(" where lineItemId='").append(lineItemId).append("'");
		this.dao.getSession().createSQLQuery(sql.toString()).executeUpdate();
	}
	@Override
	public void sampleUpdate(String sql){
		this.dao.getSession().createSQLQuery(sql).executeUpdate();
	}
	/**
	 * 查询条件
	 * @param paramSearch
	 */
	public static void queryCondition(SampleTrackSearckParam paramSearch,StringBuffer hql,StringBuffer sql,StringBuffer sql2){
		if(StringUtils.isNotEmpty(paramSearch.getFormSampleOrderId())){
			if(StringUtils.isNotEmpty(paramSearch.getToSampleOrderId())){
				hql.append(" and sampleOrderId >= '"+paramSearch.getFormSampleOrderId()+"'");
				sql.append(" and sampleOrderId >= '"+paramSearch.getFormSampleOrderId()+"'");
				sql2.append(" and sampleOrderId >= '"+paramSearch.getFormSampleOrderId()+"'");
			}else{
				hql.append(" and sampleOrderId = '"+paramSearch.getFormSampleOrderId()+"'");
				sql.append(" and sampleOrderId = '"+paramSearch.getFormSampleOrderId()+"'");
				sql2.append(" and sampleOrderId = '"+paramSearch.getFormSampleOrderId()+"'");
			}		
		}
		if(StringUtils.isNotEmpty(paramSearch.getToSampleOrderId())){
			hql.append(" and sampleOrderId <= '"+paramSearch.getToSampleOrderId()+"'");
			sql.append(" and sampleOrderId <= '"+paramSearch.getToSampleOrderId()+"'");
			sql2.append(" and sampleOrderId <= '"+paramSearch.getToSampleOrderId()+"'");
		}
		if(null!=paramSearch.getSampleOrderStatusList()){
			String paramTemp = "";
			 for (int i = 0; i < paramSearch.getSampleOrderStatusList().size(); i++) {
				 paramTemp += paramSearch.getSampleOrderStatusList().get(i)+",";					
			 }	 
			hql.append(" and sampleOrderStatus in ("+paramTemp.substring(0, paramTemp.length()-1)+")");
			sql.append(" and sampleOrderStatus in ("+paramTemp.substring(0, paramTemp.length()-1)+")");
			sql2.append(" and sampleOrderStatus in ("+paramTemp.substring(0, paramTemp.length()-1)+")");
		}
		if(StringUtils.isNotEmpty(paramSearch.getSampleTypeNo())){
			hql.append(" and SUBSTR(sampleId,1,8) = '"+paramSearch.getSampleTypeNo()+"'");
			sql.append(" and SUBSTR(sampleId,1,8) = '"+paramSearch.getSampleTypeNo()+"'");
			sql2.append(" and SUBSTR(sampleId,1,8) = '"+paramSearch.getSampleTypeNo()+"'");
		}		
		if(StringUtils.isNotEmpty(paramSearch.getProjectStatus())){
			hql.append(" and projectStatus = '"+paramSearch.getProjectStatus()+"'");
			sql.append(" and projectStatus = '"+paramSearch.getProjectStatus()+"'");
			sql2.append(" and projectStatus = '"+paramSearch.getProjectStatus()+"'");
		}
		if(StringUtils.isNotEmpty(paramSearch.getQcExceptStatus())){
			hql.append(" and qcExceptStatus = '"+paramSearch.getQcExceptStatus()+"'");
			sql.append(" and qcExceptStatus = '"+paramSearch.getQcExceptStatus()+"'");
			sql2.append(" and qcExceptStatus = '"+paramSearch.getQcExceptStatus()+"'");
		}
		if(StringUtils.isNotEmpty(paramSearch.getProdExceptStatus())){
			hql.append(" and prodExceptStatus = '"+paramSearch.getProdExceptStatus()+"'");
			sql.append(" and prodExceptStatus = '"+paramSearch.getProdExceptStatus()+"'");
			sql2.append(" and prodExceptStatus = '"+paramSearch.getProdExceptStatus()+"'");
		}
		if(StringUtils.isNotEmpty(paramSearch.getFormSampleId())){
			if(StringUtils.isNotEmpty(paramSearch.getToSampleId())){
				hql.append(" and sampleId >= '"+paramSearch.getFormSampleId()+"'");
				sql.append(" and sampleId >= '"+paramSearch.getFormSampleId()+"'");
				sql2.append(" and sampleId >= '"+paramSearch.getFormSampleId()+"'");
			}else{
				hql.append(" and sampleId = '"+paramSearch.getFormSampleId()+"'");
				sql.append(" and sampleId = '"+paramSearch.getFormSampleId()+"'");
				sql2.append(" and sampleId = '"+paramSearch.getFormSampleId()+"'");
			}			
		}
		if(StringUtils.isNotEmpty(paramSearch.getToSampleId())){
			hql.append(" and sampleId <= '"+paramSearch.getToSampleId()+"'");
			sql.append(" and sampleId <= '"+paramSearch.getToSampleId()+"'");
			sql2.append(" and sampleId <= '"+paramSearch.getToSampleId()+"'");
		}
		if(StringUtils.isNotEmpty(paramSearch.getApplyUser())){
			hql.append(" and applyUser like '%"+paramSearch.getApplyUser()+"%'");
			sql.append(" and applyUser like '%"+paramSearch.getApplyUser()+"%'");
			sql2.append(" and applyUser like '%"+paramSearch.getApplyUser()+"%'");
		}
		if(StringUtils.isNotEmpty(paramSearch.getApplyDept())){
			hql.append(" and applyDept like '%"+paramSearch.getApplyDept()+"%'");
			sql.append(" and applyDept like '%"+paramSearch.getApplyDept()+"%'");
			sql2.append(" and applyDept like '%"+paramSearch.getApplyDept()+"%'");
		}
		if(StringUtils.isNotEmpty(paramSearch.getFromApplyDate())){
			if(StringUtils.isNotEmpty(paramSearch.getToApplyDate())){
				hql.append(" and applyDate >= to_date('"+paramSearch.getFromApplyDate()+"','yyyy-mm-dd')");
				sql.append(" and applyDate >= to_date('"+paramSearch.getFromApplyDate()+"','yyyy-mm-dd')");
				sql2.append(" and applyDate >= to_date('"+paramSearch.getFromApplyDate()+"','yyyy-mm-dd')");
			}else{
				hql.append(" and applyDate = to_date('"+paramSearch.getFromApplyDate()+"','yyyy-mm-dd')");
				sql.append(" and applyDate = to_date('"+paramSearch.getFromApplyDate()+"','yyyy-mm-dd')");
				sql2.append(" and applyDate = to_date('"+paramSearch.getFromApplyDate()+"','yyyy-mm-dd')");
			}			
		}
		if(StringUtils.isNotEmpty(paramSearch.getToApplyDate())){
			hql.append(" and applyDate <= to_date('"+paramSearch.getToApplyDate()+"','yyyy-mm-dd')");
			sql.append(" and applyDate <= to_date('"+paramSearch.getToApplyDate()+"','yyyy-mm-dd')");
			sql2.append(" and applyDate <= to_date('"+paramSearch.getToApplyDate()+"','yyyy-mm-dd')");
		}
		if(StringUtils.isNotEmpty(paramSearch.getProductName())){
			hql.append(" and productName like '%"+paramSearch.getProductName()+"%'");
			sql.append(" and productName like '%"+paramSearch.getProductName()+"%'");
			sql2.append(" and productName like '%"+paramSearch.getProductName()+"%'");
		}
		if(StringUtils.isNotEmpty(paramSearch.getSamplePredictDDateWeek())){
			hql.append(" and samplePredictddateWeek = '"+paramSearch.getSamplePredictDDateWeek()+"'");
			sql.append(" and samplePredictddateWeek = '"+paramSearch.getSamplePredictDDateWeek()+"'");
			sql2.append(" and samplePredictddateWeek = '"+paramSearch.getSamplePredictDDateWeek()+"'");
		}
		if(StringUtils.isNotEmpty(paramSearch.getFormCompleteDate())){
			if(StringUtils.isNotEmpty(paramSearch.getToCompleteDate())){
				hql.append(" and completeDate >= to_date('"+paramSearch.getFormCompleteDate()+"','yyyy-mm-dd hh:mi:ss')");
				sql.append(" and completeDate >= to_date('"+paramSearch.getFormCompleteDate()+"','yyyy-mm-dd hh:mi:ss')");
				sql2.append(" and completeDate >= to_date('"+paramSearch.getFormCompleteDate()+"','yyyy-mm-dd hh:mi:ss')");
			}else{
				hql.append(" and completeDate = to_date('"+paramSearch.getFormCompleteDate()+"','yyyy-mm-dd hh:mi:ss')");
				sql.append(" and completeDate = to_date('"+paramSearch.getFormCompleteDate()+"','yyyy-mm-dd hh:mi:ss')");
				sql2.append(" and completeDate = to_date('"+paramSearch.getFormCompleteDate()+"','yyyy-mm-dd hh:mi:ss')");
			}	
		}
		if(StringUtils.isNotEmpty(paramSearch.getToCompleteDate())){
			hql.append(" and completeDate <= to_date('"+paramSearch.getToCompleteDate()+"','yyyy-mm-dd hh:mi:ss')");
			sql.append(" and completeDate <= to_date('"+paramSearch.getToCompleteDate()+"','yyyy-mm-dd hh:mi:ss')");
			sql2.append(" and completeDate <= to_date('"+paramSearch.getToCompleteDate()+"','yyyy-mm-dd hh:mi:ss')");
		}
		
		if(null!=paramSearch.getProductModeList()){
			String paramTemp = "";
			 for (int i = 0; i < paramSearch.getProductModeList().size(); i++) {
				 paramTemp += paramSearch.getProductModeList().get(i)+",";					
			 }	 
			hql.append(" and productMode in ("+paramTemp.substring(0, paramTemp.length()-1)+")");
			sql.append(" and productMode in ("+paramTemp.substring(0, paramTemp.length()-1)+")");
			sql2.append(" and productMode in ("+paramTemp.substring(0, paramTemp.length()-1)+")");
		}		
		if(StringUtils.isNotEmpty(paramSearch.getProductMode())){
			hql.append(" and productMode = '"+paramSearch.getProductMode()+"'");
			sql.append(" and productMode = '"+paramSearch.getProductMode()+"'");
			sql2.append(" and productMode = '"+paramSearch.getProductMode()+"'");
		}
		if(StringUtils.isNotEmpty(paramSearch.getCustomerId())){
			hql.append(" and customerId = '"+paramSearch.getCustomerId()+"'");
			sql.append(" and customerId = '"+paramSearch.getCustomerId()+"'");
			sql2.append(" and customerId = '"+paramSearch.getCustomerId()+"'");
		}
		//距离交期天数实时更新
		if(StringUtils.isNotEmpty(paramSearch.getFormDay())){
			if(StringUtils.isNotEmpty(paramSearch.getToDay())){
				hql.append(" and datediff(day,GETDATE(),samplePredictDDate) >= '"+paramSearch.getFormDay()+"'");
				sql.append(" and datediff(day,GETDATE(),samplePredictDDate) >= '"+paramSearch.getFormDay()+"'");
				sql2.append(" and datediff(day,GETDATE(),samplePredictDDate) >= '"+paramSearch.getFormDay()+"'");
			}else{
				hql.append(" and datediff(day,GETDATE(),samplePredictDDate) = '"+paramSearch.getFormDay()+"'");
				sql.append(" and datediff(day,GETDATE(),samplePredictDDate) = '"+paramSearch.getFormDay()+"'");
				sql2.append(" and datediff(day,GETDATE(),samplePredictDDate) = '"+paramSearch.getFormDay()+"'");
			}		
		}
		if(StringUtils.isNotEmpty(paramSearch.getToDay())){
			hql.append(" and datediff(day,GETDATE(),samplePredictDDate) <= '"+paramSearch.getToDay()+"'");
			sql.append(" and datediff(day,GETDATE(),samplePredictDDate) <= '"+paramSearch.getToDay()+"'");
			sql2.append(" and datediff(day,GETDATE(),samplePredictDDate) <= '"+paramSearch.getToDay()+"'");
		}
		if(StringUtils.isNotEmpty(paramSearch.getCustomerResult())){
			if("1".equals(paramSearch.getCustomerResult())){
				hql.append(" and customerResult is not null and customerResult != ''");
				sql.append(" and customerResult is not null and customerResult != ''");
				sql2.append(" and customerResult is not null and customerResult != ''");
			}else{//"0"
				hql.append(" and (customerResult is  null or customerResult = '')");
				sql.append(" and (customerResult is  null or customerResult = '')");
				sql2.append(" and (customerResult is  null or customerResult = '')");
			}
		}		
		if(StringUtils.isNotEmpty(paramSearch.getProductId())){
			hql.append(" and productId like '%"+paramSearch.getProductId()+"%'");
			sql.append(" and productId like '%"+paramSearch.getProductId()+"%'");
			sql2.append(" and productId like '%"+paramSearch.getProductId()+"%'");
		}
		if(StringUtils.isNotEmpty(paramSearch.getBomCost())){//sampleTrack2.ftl没有这个条件
			hql.append(" and bomCost = '"+paramSearch.getBomCost()+"'");
			sql.append(" and bomCost = '"+paramSearch.getBomCost()+"'");
			sql2.append(" and bomCost = '"+paramSearch.getBomCost()+"'");
		}
		hql.append(" order by sampleId");
	}
	/**
	 * 两个时间相距的天数
	 * @param fDate
	 * @param oDate
	 * @return
	 */
	public static int daysOfTwo(Date fDate, Date oDate) {
	       Calendar aCalendar = Calendar.getInstance();
	       aCalendar.setTime(fDate);
	       int day1 = aCalendar.get(Calendar.DAY_OF_YEAR);
	       aCalendar.setTime(oDate);
	       int day2 = aCalendar.get(Calendar.DAY_OF_YEAR);
	       return  day2-day1;
	    }
}
