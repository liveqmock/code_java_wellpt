package com.wellsoft.pt.ldx.service.mps;

import java.util.List;
import java.util.Map;

import com.wellsoft.pt.core.service.BaseService;
import com.wellsoft.pt.core.support.PagingInfo;
import com.wellsoft.pt.core.support.QueryItem;
import com.wellsoft.pt.ldx.model.mps.Zdview;
public interface ZdService extends BaseService {
	
	public List<QueryItem> queryQueryItemData(String hql,
			Map<String, Object> queryParams, PagingInfo pagingInfo);
	         @SuppressWarnings("rawtypes")
	         public Map saveZdview(Zdview objectview);//保存数据
	         @SuppressWarnings("rawtypes")
	     	 public void FindAndDelete();
	         public Map saveZdviews(List<Zdview> objectviewlist);//保存数据
	         public List<Zdview> getZdviews(String xs,String jh,String sc,String sd);
      
}
