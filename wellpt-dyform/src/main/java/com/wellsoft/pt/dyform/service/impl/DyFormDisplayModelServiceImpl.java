package com.wellsoft.pt.dyform.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wellsoft.pt.common.component.jqgrid.JqGridQueryInfo;
import com.wellsoft.pt.common.component.jqgrid.JqTreeGridNode;
import com.wellsoft.pt.common.component.tree.TreeNode;
import com.wellsoft.pt.core.dao.PropertyFilter;
import com.wellsoft.pt.core.service.impl.BaseServiceImpl;
import com.wellsoft.pt.core.support.QueryData;
import com.wellsoft.pt.core.support.QueryInfo;
import com.wellsoft.pt.core.support.QueryItem;
import com.wellsoft.pt.dyform.entity.BeanCopyUtils;
import com.wellsoft.pt.dyform.entity.DyFormDisplayModel;
import com.wellsoft.pt.dyform.service.DyFormDisplayModelService;
import com.wellsoft.pt.dytable.utils.DynamicUtils;
import com.wellsoft.pt.utils.bean.BeanUtils;

@Service
@Transactional
public class DyFormDisplayModelServiceImpl extends BaseServiceImpl implements DyFormDisplayModelService {

	@Override
	public void save(DyFormDisplayModel model) throws IllegalAccessException, InvocationTargetException {

		if (model == null) {
			return;
		}

		String uuid = model.getUuid();
		DyFormDisplayModel dbModel = null;
		if (!StringUtils.isBlank(uuid) && uuid.trim().length() > 0) {
			dbModel = this.get(model.getUuid());
		} else {//调用方没有传入uuid
			uuid = DynamicUtils.getRandomUUID();
		}

		model.setUuid(uuid);

		if (dbModel == null) {
			dbModel = model;
			dbModel.doBindCreateTimeAsNow();
			dbModel.doBindCreatorAsCurrentUser();
			dbModel.doBindModifierAsCurrentUser();
			dbModel.doBindModifyTimeAsNow();
		} else {
			BeanCopyUtils copyUtils = new BeanCopyUtils();
			copyUtils.copyProperties(dbModel, model);
		}

		this.dao.save(dbModel);

	}

	@Override
	public DyFormDisplayModel get(String uuid) {
		DyFormDisplayModel dbModel = this.dao.get(DyFormDisplayModel.class, uuid);
		return dbModel;
	}

	public DyFormDisplayModel getByModelId(String modelId) {
		DyFormDisplayModel dbModel = this.dao.findUniqueBy(DyFormDisplayModel.class, "outerId", modelId);
		return dbModel;
	}

	@Override
	public QueryData getForPageAsTree(JqGridQueryInfo jqGridQueryInfo, QueryInfo queryInfo) {
		// 设置查询字段条件
		Map<String, Object> values = PropertyFilter.convertToMap(queryInfo.getPropertyFilters());
		// 查询父节点为null的部门
		List<QueryItem> results = null;
		results = this.dao.namedQuery("topDyFormDefinitionTreeQuery2", values, QueryItem.class,
				queryInfo.getPagingInfo());

		// results = pageData.getResult();
		List<JqTreeGridNode> retResults = new ArrayList<JqTreeGridNode>();

		int level = jqGridQueryInfo.getN_level() == null ? 0 : jqGridQueryInfo.getN_level() + 1;
		String parentId = jqGridQueryInfo.getNodeid() == null ? "null" : jqGridQueryInfo.getNodeid();
		for (int index = 0; index < results.size(); index++) {
			QueryItem item = results.get(index);
			JqTreeGridNode node = new JqTreeGridNode();
			node.setId(item.get("outerId").toString());// id
			List<Object> cell = node.getCell();
			cell.add(item.get("uuid"));// UUID
			cell.add(item.get("outerId"));// version 
			cell.add(item.get("displayName"));
			cell.add(item.get("referredFormId"));
			cell.add(item.get("preview"));
			// level field
			cell.add(level);
			// parent id field
			cell.add(parentId);
			// leaf field

			cell.add(true);

			retResults.add(node);
		}
		QueryData queryData = new QueryData();
		queryData.setDataList(retResults);
		queryData.setPagingInfo(queryInfo.getPagingInfo());
		return queryData;
	}

	@Override
	public void deleteDisplayModel(String uuid) {
		this.dao.deleteByPk(DyFormDisplayModel.class, uuid);
	}

	/**
	 * 
	 * 获得显示单据的表单
	 * 
	 * @param s
	 * @return
	 */
	@Override
	public List<TreeNode> getModels(String modelId) {
		List<TreeNode> treeNodes = new ArrayList<TreeNode>();
		List<DyFormDisplayModel> fds = new ArrayList<DyFormDisplayModel>();
		fds = this.dao.getAll(DyFormDisplayModel.class);
		for (Iterator iterator2 = fds.iterator(); iterator2.hasNext();) {
			TreeNode treeNode = new TreeNode();
			treeNode.setId("-1");
			DyFormDisplayModel fd = (DyFormDisplayModel) iterator2.next();
			DyFormDisplayModel fdNew = new DyFormDisplayModel();
			BeanUtils.copyProperties(fd, fdNew);
			treeNode.setName(fdNew.getDisplayName());
			treeNode.setId(fdNew.getOuterId());
			treeNode.setData(fdNew);
			treeNode.setChecked(fdNew.getOuterId().equals(modelId));
			treeNodes.add(treeNode);

		}
		return treeNodes;
	}
}
