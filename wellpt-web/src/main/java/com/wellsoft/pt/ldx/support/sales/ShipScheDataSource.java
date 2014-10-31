package com.wellsoft.pt.ldx.support.sales;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.wellsoft.pt.basicdata.datasource.entity.DataSourceColumn;
import com.wellsoft.pt.basicdata.datasource.provider.AbstractDataSourceProvider;
import com.wellsoft.pt.basicdata.dyview.provider.ViewColumnType;
import com.wellsoft.pt.basicdata.view.support.CondSelectAskInfoNew;
import com.wellsoft.pt.basicdata.view.support.DyViewQueryInfoNew;
import com.wellsoft.pt.common.enums.ModuleID;
import com.wellsoft.pt.core.support.PagingInfo;
import com.wellsoft.pt.core.support.QueryItem;
import com.wellsoft.pt.file.dao.FmFileDao;
import com.wellsoft.pt.file.entity.FmFile;
import com.wellsoft.pt.ldx.service.ISapService;
import com.wellsoft.pt.ldx.util.DateUtils;
import com.wellsoft.pt.utils.security.SpringSecurityUtils;

@Component
public class ShipScheDataSource extends AbstractDataSourceProvider {

	@Autowired
	private ISapService sapService;

	@Autowired
	private FmFileDao fmFileDao;

	@Override
	public Set<DataSourceColumn> getAllDataSourceColumns() {
		Set<DataSourceColumn> viewColumns = new HashSet<DataSourceColumn>();
		viewColumns.add(generateCol("zsdt0016.vbeln", "vbeln", "外向交货单号",
				ViewColumnType.STRING));
		viewColumns.add(generateCol("zsdt0016.zgbh", "zgbh", "装柜编号",
				ViewColumnType.STRING));
		viewColumns.add(generateCol("zsdt0016.bukrs", "bukrs", "出口单位",
				ViewColumnType.STRING));
		viewColumns.add(generateCol("zsdt0016.sortl", "sortl", "客户代码",
				ViewColumnType.STRING));
		viewColumns.add(generateCol("likp.wadat", "wadat", "订单交期",
				ViewColumnType.STRING));
		viewColumns.add(generateCol("zsdt0036.zpzfzr", "zpzfzr", "排载负责人",
				ViewColumnType.STRING));
		return viewColumns;
	}

	private DataSourceColumn generateCol(String attributeName,
			String columnAlias, String columnName, ViewColumnType viewcolumntype) {
		DataSourceColumn column = new DataSourceColumn();
		column.setFieldName(attributeName);
		column.setColumnAliase(columnAlias);
		column.setColumnName(columnName);
		column.setColumnDataType(viewcolumntype == null ? ViewColumnType.STRING
				.name() : viewcolumntype.name());
		return column;
	}

	@Override
	public String getModuleId() {
		return ModuleID.WORKFLOW.getValue();
	}

	@Override
	public String getModuleName() {
		return "出货排载维护";
	}

	@Override
	public List<QueryItem> query(Set<DataSourceColumn> viewColumns,
			String whereHql, Map<String, Object> queryParams, String orderBy,
			PagingInfo pagingInfo) {
		String sql = " zsdt0016 zsdt0016 inner join LIKP likp on zsdt0016.VBELN = likp.vbeln and zsdt0016.mandt=likp.mandt left join zsdt0036 zsdt0036 on zsdt0016.Vbeln= zsdt0036.vbeln and zsdt0016.mandt=zsdt0036.mandt where "
				+ whereHql + getWhereLike();
		List<QueryItem> results = sapService.queryForItemBySql(viewColumns,
				sql, queryParams, orderBy, pagingInfo, "zsdt0016.mandt", true);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		for (QueryItem queryItem : results) {
			queryItem.put("vbeln", queryItem.getString("vbeln").substring(2));
			if (!"00000000".equals(queryItem.getString("wadat"))) {
				try {
					queryItem.put(
							"wadat",
							formatter.format(DateUtils.parseDate(
									queryItem.getString("wadat"), "yyyyMMdd")));
				} catch (ParseException e) {
					e.printStackTrace();
				}
			} else {
				queryItem.put("wadat", null);
			}
		}
		return results;
	}

	@Override
	public String getWhereSql(DyViewQueryInfoNew dyviewqueryinfonew) {
		List<CondSelectAskInfoNew> whereSqlList = dyviewqueryinfonew
				.getCondSelectList();
		String str = "1=1";
		for (CondSelectAskInfoNew condSelectAskInfoNew : whereSqlList) {
			if ("zgbh".equals(condSelectAskInfoNew.getSearchField())
					|| "sortl".equals(condSelectAskInfoNew.getSearchField())) {
				str += " and zsdt0016." + condSelectAskInfoNew.getSearchField()
						+ " like '%" + condSelectAskInfoNew.getSearchValue()
						+ "%'";
			} else if ("vbeln".equals(condSelectAskInfoNew.getSearchField())
					&& StringUtils.isNotEmpty(condSelectAskInfoNew
							.getSearchValue())) {
				String[] arrayStr = condSelectAskInfoNew.getSearchValue()
						.split(",");
				for (int i = 0; i < arrayStr.length; i++) {
					arrayStr[i] = StringUtils.leftPad(arrayStr[i], 10, '0');
				}
				str += " and zsdt0016." + condSelectAskInfoNew.getSearchField()
						+ " in ('" + StringUtils.join(arrayStr, "','") + "')";
			} else if ("vbeln_first".equals(condSelectAskInfoNew
					.getSearchField())
					&& StringUtils.isNotEmpty(condSelectAskInfoNew
							.getSearchValue())) {
				String[] arrayStr = condSelectAskInfoNew.getSearchValue()
						.split("\\|");
				if (StringUtils.isNotEmpty(arrayStr[0])) {
					str += " and zsdt0016.vbeln >='"
							+ StringUtils.leftPad(arrayStr[0], 10, '0') + "'";
				}
				if (StringUtils.isNotEmpty(arrayStr[1])) {
					str += " and zsdt0016.vbeln <='"
							+ StringUtils.leftPad(arrayStr[1], 10, '0') + "'";
				}
			}
		}
		return str;
	}

	@SuppressWarnings("deprecation")
	private String getWhereLike() {
		String str = "";
		List<FmFile> fmFiles = fmFileDao.find(
				"from FmFile where status=2 and fmFolder.uuid=? and reservedText7 like '%"
						+ SpringSecurityUtils.getCurrentUserId() + "%'",
				"78f6f286-d4a2-4a74-a749-f7c07b89aac0");
		List<String> likeStr = new ArrayList<String>();
		for (FmFile fmFile : fmFiles) {
			likeStr.add("zsdt0016.sortl like '" + fmFile.getTitle().trim()
					+ "%'");
		}
		if (!likeStr.isEmpty()) {
			str = " and (" + StringUtils.join(likeStr, " or ") + ") ";
		}
		return str;
	}
}
