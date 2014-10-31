package com.wellsoft.pt.dyform.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.wellsoft.pt.basicdata.datasource.entity.DataSourceDefinition;
import com.wellsoft.pt.basicdata.datasource.facade.DataSourceApiFacade;
import com.wellsoft.pt.dyform.service.DyFormDataSourceService;
import com.wellsoft.pt.dytable.bean.TreeNodeBean;

@Service
public class DyFormDataSourceServiceImpl implements DyFormDataSourceService {
	@Autowired
	DataSourceApiFacade dataSourceApiFacade;

	@Override
	public List<DataSourceDefinition> getAllDataSource() {
		return this.dataSourceApiFacade.getAll();
	}

	@Override
	public List<Map<String, String>> getAllDataSourceNameAndId() {
		List<DataSourceDefinition> dses = this.dataSourceApiFacade.getAll();
		if (dses == null || dses.size() == 0) {
			return null;
		}
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		Map<String, String> map = new HashMap<String, String>();
		for (DataSourceDefinition ds : dses) {
			map.put(ds.getDataSourceId(), ds.getDataSourceName());
			list.add(map);
		}
		return list;
	}

	@Override
	public List<TreeNodeBean> getAllDataSourceId() {
		List<TreeNodeBean> list = Lists.newArrayList();
		List<DataSourceDefinition> dses = this.getAllDataSource();
		if (dses == null) {
			return list;
		}

		for (DataSourceDefinition dsd : dses) {
			TreeNodeBean node = new TreeNodeBean(dsd.getDataSourceId(), dsd.getDataSourceName());
			list.add(node);
		}
		return list;
	}
}
