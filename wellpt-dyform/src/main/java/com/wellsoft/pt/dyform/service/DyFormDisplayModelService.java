package com.wellsoft.pt.dyform.service;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import com.wellsoft.pt.common.component.jqgrid.JqGridQueryInfo;
import com.wellsoft.pt.common.component.tree.TreeNode;
import com.wellsoft.pt.core.service.BaseService;
import com.wellsoft.pt.core.support.QueryData;
import com.wellsoft.pt.core.support.QueryInfo;
import com.wellsoft.pt.dyform.entity.DyFormDisplayModel;

public interface DyFormDisplayModelService extends BaseService {

	void save(DyFormDisplayModel model) throws IllegalAccessException, InvocationTargetException;

	DyFormDisplayModel get(String uuid);

	QueryData getForPageAsTree(JqGridQueryInfo jqGridQueryInfo, QueryInfo queryInfo);

	void deleteDisplayModel(String uuid);

	public List<TreeNode> getModels(String modelId);

	public DyFormDisplayModel getByModelId(String modelId);
}
