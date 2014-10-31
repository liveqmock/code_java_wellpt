package com.wellsoft.pt.ldx.service.impl;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wellsoft.pt.basicdata.datasource.entity.DataSourceColumn;
import com.wellsoft.pt.basicdata.dyview.provider.ViewColumn;
import com.wellsoft.pt.common.enums.Separator;
import com.wellsoft.pt.core.support.PagingInfo;
import com.wellsoft.pt.core.support.QueryItem;
import com.wellsoft.pt.core.support.QueryItemResultTransformer;
import com.wellsoft.pt.ldx.service.OrderManageService;
import com.wellsoft.pt.ldx.util.StringUtils;

@Service
@Transactional
@SuppressWarnings("rawtypes")
public class orderManageServiceImpl extends SapServiceImpl implements
		OrderManageService {

	@Override
	public List getListForSQL(String sql) {
		// TODO Auto-generated method stub
		return sapDao.getSession().createSQLQuery(sql).list();
	}

	@Override
	public List getListByPage(String sql, PagingInfo pagingInfo) {
		SQLQuery localQuery = this.sapDao.getSession().createSQLQuery(
				sql.toString());
		// if (pagingInfo.getCurrentPage() != -1)
		// {
		// localQuery.setFirstResult(pagingInfo.getFirst());
		// localQuery.setMaxResults(pagingInfo.getPageSize());
		// }
		// localQuery.setResultTransformer(QueryItemResultTransformer.INSTANCE);
		return localQuery.list();
	}

	@Override
	public List orderLineItemDetail(Collection<DataSourceColumn> dataSourceColumn,
			String whereHql, PagingInfo pagingInfo) {

		StringBuffer sql = new StringBuffer("select ");
		boolean start = true;
		for (DataSourceColumn col : dataSourceColumn) {
			if (!start) {
				sql.append(",");
			} else {
				start = false;
			}
			if (col.getFieldName().indexOf(".") == -1) {
				sql.append("a.");
			}
			sql.append(col.getFieldName()).append(" as ")
					.append(col.getColumnAliase());
		}

		sql.append(" from").append(whereHql);
		SQLQuery localQuery = this.sapDao.getSession().createSQLQuery(
				sql.toString());
		if (pagingInfo.getCurrentPage() != -1) {
			localQuery.setFirstResult(pagingInfo.getFirst());
			localQuery.setMaxResults(pagingInfo.getPageSize());
		}
		localQuery.setResultTransformer(QueryItemResultTransformer.INSTANCE);
		List<QueryItem> localList = localQuery.list();

		if (pagingInfo.isAutoCount()) {
			localQuery = this.sapDao.getSession().createSQLQuery(
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

	@Override
	public List productionSearch(Collection<DataSourceColumn> dataSourceColumn,
			String whereHql, PagingInfo pagingInfo) {
		StringBuffer sql = new StringBuffer();
		sql.append(" with cust as(")
				.append(" select distinct ta.vbeln,tb.sortl,tb.kunnr from vbak ta,kna1 tb where ta.mandt=")
				.append(this.getClient())
				.append(" and ta.mandt=tb.mandt and ta.kunnr=tb.kunnr")
				.append(" ),ckjq as(")
				.append(" select vbeln,posnr,max(etenr) as etenr from vbep where mandt=")
				.append(this.getClient()).append(" group by vbeln,posnr")
				.append(" )").append(" select ");
		boolean start = true;
		for (DataSourceColumn col : dataSourceColumn) {
			if (!start) {
				sql.append(",");
			} else {
				start = false;
			}

			if (col.getFieldName().indexOf(".") == -1) {
				sql.append("a.");
			}
			sql.append(col.getFieldName()).append(" as ")
					.append(col.getColumnAliase());
		}

		sql.append(" from")
				.append(" vbap a")
				.append(" left join cust b on a.vbeln=b.vbeln ")
				.append(" left join zsdt0047 c on a.mandt=c.mandt and a.vbeln=c.vbeln and a.posnr=c.posnr")
				.append(" left join makt d on a.mandt=d.mandt and a.matnr=d.matnr")
				.append(" left join zsdt0033 e on a.mandt=e.mandt and a.vbeln=e.vbeln and a.posnr=e.posnr")
				.append(" left join ckjq f on a.vbeln = f.vbeln and a.posnr=f.posnr")
				.append(" left join vbep g on a.mandt=g.mandt and a.vbeln=g.vbeln and a.posnr=g.posnr and g.etenr=f.etenr")
				.append(" inner join plaf h on a.mandt=h.mandt and a.vbeln=h.kdauf and a.posnr=h.kdpos")
				.append(" left join afpo i on a.mandt=i.mandt and a.vbeln=i.kdauf and a.posnr=i.kdpos")
				.append(" left join afko j on a.mandt=j.mandt and i.aufnr=j.aufnr")
				.append(" inner join zsdt0038 k on a.mandt=k.mandt and a.vbeln=k.vbeln and a.posnr=k.posnr and k.zitem='实际' and substr(k.zstatus,0,1) in ('3','4','5','6')")
				.append(" and a.mandt=").append(this.getClient())
				.append(" where "+whereHql);
		SQLQuery localQuery = this.sapDao.getSession().createSQLQuery(
				sql.toString());
		if (pagingInfo.getCurrentPage() != -1) {
			localQuery.setFirstResult(pagingInfo.getFirst());
			localQuery.setMaxResults(pagingInfo.getPageSize());
		}
		localQuery.setResultTransformer(QueryItemResultTransformer.INSTANCE);
		List<QueryItem> localList = localQuery.list();
		// 查询总条数
		StringBuffer hql = new StringBuffer();
		hql.append(" with cust as(")
				.append(" select distinct ta.vbeln,tb.sortl,tb.kunnr from vbak ta,kna1 tb where ta.mandt=")
				.append(this.getClient())
				.append(" and ta.mandt=tb.mandt and ta.kunnr=tb.kunnr")
				.append(" )")
				.append(" select count(1)")
				.append(" from vbap a")
				.append(" left join cust b on a.vbeln=b.vbeln ")
				.append(" left join zsdt0047 c on a.mandt=c.mandt and a.vbeln=c.vbeln and a.posnr=c.posnr")
				.append(" left join makt d on a.mandt=d.mandt and a.matnr=d.matnr")
				.append(" left join zsdt0033 e on a.mandt=e.mandt and a.vbeln=e.vbeln and a.posnr=e.posnr")
				.append(" inner join plaf h on a.mandt=h.mandt and a.vbeln=h.kdauf and a.posnr=h.kdpos")
				.append(" left join afpo i on a.mandt=i.mandt and a.vbeln=i.kdauf and a.posnr=i.kdpos")
				.append(" left join afko j on a.mandt=j.mandt and i.aufnr=j.aufnr")
				.append(" inner join zsdt0038 k on a.mandt=k.mandt and a.vbeln=k.vbeln and a.posnr=k.posnr and k.zitem='实际' and substr(k.zstatus,0,1) in ('3','4','5','6')")
				.append(" and a.mandt=").append(this.getClient())
				.append(" where "+whereHql);
		if (pagingInfo.isAutoCount()) {
			localQuery = this.sapDao.getSession()
					.createSQLQuery(hql.toString());
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

	@Override
	public Map updateOrderLine(String vbeln, String posnr, String zhfkr,
			String zyhrq, String zyhjg, String zyhbz, String zchsl,
			String zchrq, String zyqbm, String zycdl, String zyqbz,
			String omchjh) {
		Map<String, String> map = new HashMap<String, String>();
		try {
			String sql = "update zsdt0049 set";
			sql += " zhfkr='" + zhfkr + "',";
			sql += " zyhrq='" + zyhrq + "',";
			sql += " zyhjg='" + zyhjg + "',";
			sql += " zyhbz='" + zyhbz + "',";
			sql += " zchsl='" + zchsl + "',";
			sql += " zchrq='" + zchrq + "',";
			sql += " zyqbm='" + zyqbm + "',";
			sql += " zycdl='" + zycdl + "',";
			sql += " zyqbz='" + zyqbz + "',";
			sql += " omchjh='" + omchjh + "'";
			sql += " where vbeln='" + vbeln + "' and posnr='" + posnr + "'";
			execSql(sql);
			map.put("res","success");
			map.put("msg","保存成功!");
		} catch (Exception e) {
			// TODO: handle exception
			map.put("res","fail");
			map.put("msg","线别报表显示不能为空值!");
			e.printStackTrace();
		}
		return map;
	}
	@Override
	public List shipManage(Collection<DataSourceColumn> dataSourceColumn, String whereHql,
			PagingInfo pagingInfo) {
		StringBuffer sql = new StringBuffer()
		.append(" with zsdt16 as(")
		.append(" select distinct a.vbeln,a.vgbel,b.zgbh from lips a ")
		.append(" inner join zsdt0016 b on a.mandt = b.mandt and a.vbeln = b.vbeln and a.posnr = b.posnr")
		.append(" where a.mandt =").append(this.getClient())
		.append(" ),lip as (")
		.append(" select l.vbeln,l.vgbel,sum(l.lfimg) as lfimg from lips l,vbuk m where l.vbeln like '008%' and l.mandt=").append(this.getClient())
		.append(" and l.mandt=m.mandt and l.vbeln=m.vbeln and m.wbstk='C' group by l.vbeln,l.vgbel ")
		.append(" )")
		.append(" select ");
		boolean start = true;
		for(DataSourceColumn col:dataSourceColumn){
			if(!start){
				sql.append(",");
			}else{
				start = false;
			}
//			if(col.getAttributeName().indexOf(".")==-1){
//				sql.append("a.");
//			}
			sql.append(col.getFieldName()).append(" as ").append(col.getColumnAliase());
		}
		sql.append(" from zsdt0048 a")
		.append(" left join zsdt16 c on a.vgbel = c.vgbel and a.vbeln = c.vbeln")
		.append(" left join lip d on a.vbeln=d.vbeln and a.vgbel = d.vgbel")
		.append(" left join zsdt0050 e on a.vgbel = e.vbeln and a.mandt = e.mandt ")
		.append(" where a.mandt = ").append(this.getClient())
		.append(" and "+whereHql);
		SQLQuery localQuery = this.sapDao.getSession().createSQLQuery(sql.toString());
	    if (pagingInfo.getCurrentPage() != -1)
	    {
	      localQuery.setFirstResult(pagingInfo.getFirst());
	      localQuery.setMaxResults(pagingInfo.getPageSize());
	    }
	    localQuery.setResultTransformer(QueryItemResultTransformer.INSTANCE);
	    List<QueryItem> localList = localQuery.list();
	    if (pagingInfo.isAutoCount())
	    {
	      localQuery = this.sapDao.getSession().createSQLQuery("select count(1) from ("+sql.toString()+") z");
	      pagingInfo.setTotalCount(((BigDecimal)localQuery.uniqueResult()).longValue());
	    }
	    List<QueryItem> items = new ArrayList<QueryItem>();
	    if(null!=localList&&localList.size()>0){
	    	for(QueryItem item:localList){
	    		QueryItem itemres = new QueryItem();
	    		for(String key:item.keySet()){
	    			itemres.put(key.toLowerCase(),item.get(key));
	    		}
	    		items.add(itemres);
	    	}
	    }
	    return items;
	}

	@Override
	public List orderStatusOm(Collection<DataSourceColumn> dataSourceColumn,
			String whereHql, PagingInfo pagingInfo) {
		StringBuffer sql = new StringBuffer().append("select ");
		/*.append("select sortl,")
			.append(" sum(case when zddzt='待包装' then 1 else 0 end),")
			.append(" sum(case when zddzt='待包装-预警' then 1 else 0 end),")
			.append(" sum(case when zddzt='待包装-delay' then 1 else 0 end),")
			.append(" sum(case when zddzt='待出货' then 1 else 0 end),")
			.append(" sum(case when zddzt='待出货-预警' then 1 else 0 end),")
			.append(" sum(case when zddzt='待出货-delay' then 1 else 0 end),")
			.append(" sum(case when zddzt='尾数待出货' then 1 else 0 end),")
			.append(" sum(case when zddzt='待订舱' then 1 else 0 end),")
			.append(" sum(case when zddzt='待订舱-预警' then 1 else 0 end),")
			.append(" sum(case when zddzt='待订舱-delay' then 1 else 0 end),")
			.append(" sum(case when zddzt='已完全出货' then 1 else 0 end)")*/
		boolean start = true;
		for(DataSourceColumn col:dataSourceColumn){
			if(!start){
				sql.append(",");
			}else{
				start = false;
			}
			sql.append(col.getFieldName()).append(" as ").append(col.getColumnAliase());
		}
		sql.append(" from zsdt0050 ")
		.append(" where mandt =").append(getClient())
		.append(" and sortl != ' '");
		//权限与查询
		/*if(!checkIsOrderAdmin()){
			System.out.println("Current User isnot Admin of OrderManage");
			queryString.append(" and (khzr='").append(getUserCnName()).append("'")
				.append(" or khzl='").append(getUserCnName()).append("'")
				.append(")");
		}
		if(StringUtils.isNotBlank(sortl)){
			queryString.append(" and sortl='").append(sortl.trim()).append("'");
		}*/
		sql.append(" group by sortl order by sortl");
		SQLQuery localQuery = this.sapDao.getSession().createSQLQuery(sql.toString());
	    if (pagingInfo.getCurrentPage() != -1)
	    {
	      localQuery.setFirstResult(pagingInfo.getFirst());
	      localQuery.setMaxResults(pagingInfo.getPageSize());
	    }
	    localQuery.setResultTransformer(QueryItemResultTransformer.INSTANCE);
	    List<QueryItem> localList = localQuery.list();
	    if (pagingInfo.isAutoCount())
	    {
	    	//查询总条数
			StringBuffer counrBuffer = new StringBuffer();
			counrBuffer.append(" select count(1) from(")
				.append(" select sortl")
				.append(" from zsdt0050")
				.append(" where mandt =").append(getClient())
				.append(" and "+whereHql);
			/*if(!checkIsOrderAdmin()){
				System.out.println("Current User isnot Admin of OrderManage");
				counrBuffer.append(" and (khzr='").append(getUserCnName()).append("'")
					.append(" or khzl='").append(getUserCnName()).append("'")
					.append(")");
			}
			if(StringUtils.isNotBlank(sortl)){
				counrBuffer.append(" and sortl='").append(sortl.trim()).append("'");
			}*/
			counrBuffer.append(" group by sortl)");
	      localQuery = this.sapDao.getSession().createSQLQuery(counrBuffer.toString());
	      pagingInfo.setTotalCount(((BigDecimal)localQuery.uniqueResult()).longValue());
	    }
	    List<QueryItem> items = new ArrayList<QueryItem>();
	    if(null!=localList&&localList.size()>0){
	    	for(QueryItem item:localList){
	    		QueryItem itemres = new QueryItem();
	    		for(String key:item.keySet()){
	    			itemres.put(key.toLowerCase(),item.get(key));
	    		}
	    		items.add(itemres);
	    	}
	    }
	    return items;
	}
	@Override
	public List getOrderListbyNativePage(Collection<DataSourceColumn> dataSourceColumn,
			String whereHql, PagingInfo pagingInfo) {
		StringBuffer sql = new StringBuffer("select ");
				
		boolean start = true;
		for(DataSourceColumn col:dataSourceColumn){
			if(!start){
				sql.append(",");
			}else{
				start = false;
			}
			sql.append(col.getFieldName()).append(" as ").append(col.getColumnAliase());
		}
		sql.append(" from zsdt0050 a")
		.append(" left join (select vbeln,max(zdats) as zzjq from zsdt0033 where mandt=")
		.append(getClient())
		.append(" group by vbeln) b")
		.append(" on a.vbeln = b.vbeln")
		.append(" left join (select vbeln,max(zbpdate) as zbpdate,max(gltrs) as gltrs from zsdt0011 group by vbeln) c")
		.append(" on a.vbeln = c.vbeln")
		.append(" where a.mandt = '").append(getClient()+"' and ")
		.append(whereHql)
		.append(" order by vbeln desc");
		SQLQuery localQuery = this.sapDao.getSession().createSQLQuery(sql.toString());
	    if (pagingInfo.getCurrentPage() != -1)
	    {
	      localQuery.setFirstResult(pagingInfo.getFirst());
	      localQuery.setMaxResults(pagingInfo.getPageSize());
	    }
	    localQuery.setResultTransformer(QueryItemResultTransformer.INSTANCE);
	    List<QueryItem> localList = localQuery.list();
	    if (pagingInfo.isAutoCount())
	    {
	      localQuery = this.sapDao.getSession().createSQLQuery("select count(1) from ("+sql.toString()+") z");
	      pagingInfo.setTotalCount(((BigDecimal)localQuery.uniqueResult()).longValue());
	    }
	    List<QueryItem> items = new ArrayList<QueryItem>();
	    if(null!=localList&&localList.size()>0){
	    	for(QueryItem item:localList){
	    		QueryItem itemres = new QueryItem();
	    		for(String key:item.keySet()){
	    			itemres.put(key.toLowerCase(),item.get(key));
	    		}
	    		items.add(itemres);
	    	}
	    }
	    return items;
	}
}
