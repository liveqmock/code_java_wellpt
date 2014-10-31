package com.wellsoft.pt.ldx.service.sampleTrack;

import java.text.ParseException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.wellsoft.pt.basicdata.datasource.entity.DataSourceColumn;
import com.wellsoft.pt.basicdata.dyview.provider.ViewColumn;
import com.wellsoft.pt.core.service.BaseService;
import com.wellsoft.pt.core.support.PagingInfo;
import com.wellsoft.pt.ldx.model.sampleTrack.SampleTrack;
import com.wellsoft.pt.ldx.model.sampleTrack.SampleTrackSearckParam;

@SuppressWarnings("rawtypes")
public interface SampleTrackService extends BaseService{
	public List<Object[]> findSampleFeeSettleListByPage(String groupCondition,String year,String sampleCharge1
			,String productMode1);
	public List findData(Set<DataSourceColumn> dataSourceColumn,
			String whereHql, PagingInfo pagingInfo);
	public SampleTrack findEntityById(String lineItemId,String type)throws ParseException;
	public Map findSample(SampleTrackSearckParam paramSearch);
	public void prodResultUpdate(String planStart, String planEnd, String prodStatus,
			String prodStatusMemo, String ledSampleMemo, String unitPrice,String lineItemId);
	public List findByEntity(String lineItemId);
	public void sampleUpdate(String sql);
	public List<SampleTrack> exportSampleTrack(SampleTrackSearckParam paramSearch);
}
