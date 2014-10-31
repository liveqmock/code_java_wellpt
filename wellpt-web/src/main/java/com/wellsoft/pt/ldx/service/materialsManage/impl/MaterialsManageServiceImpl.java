package com.wellsoft.pt.ldx.service.materialsManage.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.hibernate.SQLQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wellsoft.pt.basicdata.datasource.entity.DataSourceColumn;
import com.wellsoft.pt.core.service.impl.BaseServiceImpl;
import com.wellsoft.pt.core.support.PagingInfo;
import com.wellsoft.pt.core.support.QueryItem;
import com.wellsoft.pt.core.support.QueryItemResultTransformer;
import com.wellsoft.pt.ldx.service.materialsManage.MaterialsManageService;
import com.wellsoft.pt.ldx.util.StringUtils;
@Service
@Transactional
@SuppressWarnings("rawtypes")
public class MaterialsManageServiceImpl extends BaseServiceImpl implements MaterialsManageService{

	@Override
	public List queryMaterial(Collection<DataSourceColumn> dataSourceColumn,
			String whereHql, PagingInfo pagingInfo){
		StringBuffer sql = new StringBuffer("select ");
		boolean start = true;
		for (DataSourceColumn col : dataSourceColumn) {
			if (!start) {
				sql.append(",");
			} else {
				start = false;
			}
//			if (col.getFieldName().indexOf(".") == -1) {
//				sql.append("a.");
//			}
			sql.append(StringUtils.isNotEmpty(col.getFieldName())?col.getFieldName():"''").append(" as ")
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
							.append("("+sql+")").toString());
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
	public int add_clkfglmkbd(String gysdm,String gysms,String jhwsdj,String poxdsj,String poxdsl,String cgddbh,String jhgysjfsj
			,String gyssjjfsj,String mbyzwcsj,String jcbh,String sjbh,String sjsqrq,String mbyzwcrq,String zt){
		
		StringBuffer sql = new StringBuffer("insert into uf_clkfgl_clkfglmkbd(gysdm,gysms,jhwsdj,poxdsj,poxdsl,cgddbh,jhgysjfsj")
			.append("gyssjjfsj,mbyzwcsj,jcbh,sjbh,sjsqrq,mbyzwcrq,zt) ")
			.append("values(")
			.append("'"+gysdm+"',")
			.append("'"+gysms+"',")
			.append("'"+jhwsdj+"',")
			.append("to_date('"+poxdsj+"','yyyy-mm-dd hh:mi:ss'),")
			.append("'"+poxdsl+"',")
			.append("'"+cgddbh+"',")
			.append("to_date('"+jhgysjfsj+"','yyyy-mm-dd hh:mi:ss'),")
			.append("to_date('"+gyssjjfsj+"','yyyy-mm-dd hh:mi:ss'),")
			.append("to_date('"+mbyzwcsj+"','yyyy-mm-dd hh:mi:ss'),")
			.append("'"+jcbh+"',")
			.append("'"+sjbh+"',")
			.append("to_date('"+sjsqrq+"','yyyy-mm-dd hh:mi:ss'),")
			.append("to_date('"+mbyzwcrq+"','yyyy-mm-dd hh:mi:ss'),")
			.append("'"+zt+"'");
		return this.dao.getSession().createSQLQuery(sql.toString()).executeUpdate();
		
	}
	@Override
	public List queryMaterialCollect(String wlid,String sqrq,String jcbh){
		
		StringBuffer sql = new StringBuffer("select gysdm,poxdsj,cgddbh,jhgysjfsj,gyssjjfsj,mbyzwcrq from UF_CLKFGL_CLKFGLMKBD")
		.append(" where 1=1")
		.append(" and jcbh liek '%"+jcbh+"%' ")
		.append(" ");
		
		return this.dao.getSession().createSQLQuery(sql.toString()).list();
	}
	@Override
	public List queryMaterialBySqdh(String sqdh){
		StringBuffer sql = new StringBuffer("select ypyt,wlzms,clms,wlid,IDzt,thao,bc,t.sfsj,wsmbdj,xqsl,xqsj,cpID,cpdl,cpmc,ICgy,srjxh,dbcc,bgyy,qy from ")
		.append("uf_ldx_clkfgl_clkfsqd,UF_LDX_CLKFGL_BZCLKFSQDDTBG t where ")
		.append("sqdh='"+sqdh+"' ")
		.append(" or t.sqdh='"+sqdh+"'");
		
		return this.dao.getSession().createSQLQuery(sql.toString()).list();
	}
	
	@Override
	public List queryCollectByJcbh(String jcbh){
		StringBuffer sql = new StringBuffer("select gysdm,gysms,jhwsdj,poxdsj,poxdsl,cgddbh,jhgysjfsj,gyssjjfsj,mbyzwcsj,jcbh,sjbh,sjsqrq,mbyzwcrq,zt")
		.append(",b.sqlx,b.jcmd,b.csjg1,b.csjg3,b.yzwcsj1,b.")
		.append(" from uf_clkfgl_clkfglmkbd , uf_ldx_clkfgl_clcssqd b where ")
		.append(" jcbh='"+jcbh+"'")
		.append(" and b.jcbh='"+jcbh+"'");
		
		return this.dao.getSession().createSQLQuery(sql.toString()).list();
	}
}
