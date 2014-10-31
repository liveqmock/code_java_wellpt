package com.wellsoft.pt.ldx.service.materialsManage;

import java.util.Collection;
import java.util.List;

import com.wellsoft.pt.basicdata.datasource.entity.DataSourceColumn;
import com.wellsoft.pt.core.service.BaseService;
import com.wellsoft.pt.core.support.PagingInfo;
@SuppressWarnings("rawtypes")
public interface MaterialsManageService extends BaseService{
	public List queryMaterial(Collection<DataSourceColumn> dataSourceColumn,
			String whereHql, PagingInfo pagingInfo);
	public int add_clkfglmkbd(String gysdm,String gysms,String jhwsdj,String poxdsj,String poxdsl,String cgddbh,String jhgysjfsj
			,String gyssjjfsj,String mbyzwcsj,String jcbh,String sjbh,String sjsqrq,String mbyzwcrq,String zt);
	public List queryMaterialCollect(String wlid,String sqrq,String jcbh);
	public List queryMaterialBySqdh(String sqdh);
	public List queryCollectByJcbh(String jcbh);
}
