package com.wellsoft.pt.ldx.support;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
import com.wellsoft.pt.ldx.service.DqbsService;
@Component
public class DqbViewDataSource extends AbstractDataSourceProvider {
	@Autowired
	private  DqbsService  dqbsService;
	@Override
	public Set<DataSourceColumn> getAllDataSourceColumns() {
		Set<DataSourceColumn> viewColumns = new HashSet<DataSourceColumn>();
		viewColumns
				.add(generateCol("question_id", "question_id", "主键", ViewColumnType.STRING));
		viewColumns.add(generateCol("level_1", "level_1", "题级1", ViewColumnType.STRING));
		viewColumns.add(generateCol("level_2", "level_2", "题级2",
				ViewColumnType.STRING));
		viewColumns.add(generateCol("level_3", "level_3", "题级3",
				ViewColumnType.STRING));
		viewColumns.add(generateCol("question_text", "question_text", "题目内容",
				ViewColumnType.STRING));
		viewColumns.add(generateCol("question_type", "question_type", "题目类型",
				ViewColumnType.STRING));
		viewColumns.add(generateCol("operation_seq", "operation_seq", "工序",
				ViewColumnType.STRING));
		viewColumns.add(generateCol("is_key", "is_key", "是否关键工序",
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
		return "首件巡检题库维护";
	}

	@Override
	public List<QueryItem> query(Set<DataSourceColumn> dataSourceColumn,
			String whereHql, Map<String, Object> queryParams, String orderBy,
			PagingInfo pagingInfo) {
		
		//
		
		
			String querySql = " droid_question_bank  where  " + whereHql;
			List<QueryItem> list = dqbsService.queryForItemBySql(dataSourceColumn, querySql, queryParams, orderBy, pagingInfo, false);
			return list;
		
	
	}
	
	
	@Override
	public String getWhereSql(DyViewQueryInfoNew dyviewqueryinfonew) {
		List<CondSelectAskInfoNew> whereSqlList = dyviewqueryinfonew
				.getCondSelectList();
		for (CondSelectAskInfoNew object : whereSqlList) {
			System.out.println("======" + object.getSearchField());
		}
		return "";
	}
	
	
	
	
}
