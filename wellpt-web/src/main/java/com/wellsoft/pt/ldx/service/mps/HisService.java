package com.wellsoft.pt.ldx.service.mps;
import java.util.List;
import java.util.Map;
import com.wellsoft.pt.core.service.BaseService;
import com.wellsoft.pt.core.support.PagingInfo;
import com.wellsoft.pt.core.support.QueryItem;
import com.wellsoft.pt.ldx.model.mps.Hisview;;
public interface HisService extends BaseService {
	
	
	public List<QueryItem> queryQueryItemData(String hql,
			 Map<String, Object> queryParams, PagingInfo pagingInfo);
	         @SuppressWarnings("rawtypes")
	         public Map saveHisview(Hisview objectview);//保存数据
	         public Map saveHisviews(List<Hisview> objectviewlist);
}
