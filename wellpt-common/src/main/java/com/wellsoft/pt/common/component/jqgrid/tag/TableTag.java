package com.wellsoft.pt.common.component.jqgrid.tag;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import com.google.common.collect.Lists;
import com.wellsoft.pt.core.context.ApplicationContextHolder;
import com.wellsoft.pt.dytable.dao.FieldDefinitionDao;
import com.wellsoft.pt.dytable.entity.FieldDefinition;
import com.wellsoft.pt.org.dao.UserDao;
import com.wellsoft.pt.utils.encode.JsonBinder;

/**
 * Description: 如何描述该类
 * 
 * @author lilin
 * @date 2013-2-5
 * @version 1.0
 * 
 *          <pre>
 * 修改记录:
 * 修改后版本	修改人		修改日期			修改内容
 * 2013-2-5.1	lilin		2013-2-5		Create
 * </pre>
 * 
 */
public class TableTag extends SimpleTagSupport {
	private static final String RN = "\r\n";
	private String id;

	// options
	private String ajaxGridOptions;
	private String ajaxSelectOptions;
	private String altclass;
	private String altRows;
	// autoencode=true 则使用innerText= "<a href="..">a</a>"
	// autoencode=false则使用innerHTML
	private String autoencode = "false";
	// 与父容器同宽度？ 默认为false
	private String autowidth = "false";
	// 标题
	private String caption;
	private String cellLayout;
	private String cellEdit;
	private String cellsubmit;
	private String cellurl;
	private String cmTemplate;
	private String colModel;
	private String colNames;
	private String data;
	private String datastr;
	private String datatype = "local";
	private String deepempty;
	private String deselectAfterSort;
	private String direction;
	private String editurl;
	private String emptyrecords;
	private String ExpandColClick;
	private String ExpandColumn;
	private String footerrow;
	private String forceFit;
	private String gridstate;
	private String gridview;
	private String grouping;
	private String headertitles;
	private String height;
	private String hiddengrid;
	private String hidegrid;
	private String hoverrows;
	private String idPrefix;
	private String ignoreCase;
	private String inlineData;
	private String jsonReader;
	private String lastpage;
	private String lastsort;
	// jqgrid的loadonce如果设置为true，则会一次性把数据都加载到客户端,以后的排序、分页都是在客户端进行了。如果数据量不多，一次性加载到客户端后的操作就快多了，不用每次ajax去取，挺好的。但是如果这时候想reload
	// jqgrid，发现reload不了了。因为loadonce为true时候，在第一次加载完成之后，程序会自动把datatype设置为local，之后的操作都变为在本地进行。如果要让loadonce:true和reload同时使用，就要在reload之前把datatype重新还原成json或xml，就能正常reload了。
	// $("#list").setGridParam({datatype:'json', page:1}).trigger('reloadGrid');
	private String loadonce = "false";
	private String loadtext;
	private String loadui;
	private String mtype;
	private String multikey;
	private String multiboxonly;
	// 是否能多选(其实就是加了复选框
	private String multiselect = "false";
	private String multiselectWidth;
	private String page;
	// 分页部分显示位置
	private String pager;
	private String pagerpos;
	private String pgbuttons;
	private String pginput;
	private String pgtext;
	private String prmNames;
	private String postData;
	private String reccount;
	// 记录条数显示的位置
	private String recordpos = "right";
	private String records;
	private String recordtext;
	private String resizeclass;
	// 可选每页显示几条,例如 [10,20,30] 可供选择
	private String rowList = "[10,20,30]";
	private String rownumbers;
	// 默认每页显示几条，例如:10
	private String rowNum = "10";
	private String rowTotal;
	private String rownumWidth;
	private String savedRow;
	private String searchdata;
	private String scroll;
	private String scrollOffset;
	private String scrollTimeout;
	private String scrollrows;
	private String selarrrow;
	private String selrow;
	private String shrinkToFit;
	private String sortable;
	// 默认排序字段
	private String sortname;
	// 排序方式
	private String sortorder = "asc";
	private String subGrid;
	private String subGridOptions;
	private String subGridModel;
	private String subGridType;
	private String subGridUrl;
	private String subGridWidth;
	private String toolbar;
	private String toppager;
	private String totaltime;
	private String treedatatype;
	private String treeGrid;
	private String treeGridModel;
	private String treeIcons;
	private String treeReader;
	private String tree_root_level;
	private String url;
	private String userData;
	private String userDataOnFooter;
	// 是否显示记录条数，即是否显示:"1 - 9 条"
	private String viewrecords = "true";
	private String viewsortcols;
	private String width;
	private String xmlReader;

	// Event
	private String afterInsertRow;
	private String beforeProcessing;
	private String beforeRequest;
	private String beforeSelectRow;
	private String gridComplete;
	private String loadBeforeSend;
	private String loadComplete;
	private String loadError;
	private String onCellSelect;
	private String ondblClickRow;
	private String onHeaderClick;
	private String onPaging;
	private String onRightClickRow;
	private String onSelectAll;
	private String onSelectRow;
	private String onSortCol;
	private String resizeStart;
	private String resizeStop;
	private String serializeGridData;

	// extendsion
	private String edit = "false";
	private String add = "false";
	private String del = "false";

	private String moduleName;

	@Override
	public void doTag() throws JspException, IOException {
		PageContext pageContext = (PageContext) this.getJspBody()
				.getJspContext();
		HttpServletRequest request = (HttpServletRequest) pageContext
				.getRequest();
		JspWriter out = pageContext.getOut();
		out.write("<table id=\"" + id + "\"></table>");
		out.newLine();
		out.write("<div id=\"" + id + "Pager\"></div>");
		out.newLine();

		// StringWriter sw = new StringWriter();
		// // 调用TableTag中的子标签 将执行结果输出到指定流sw中。
		// getJspBody().invoke(sw);
		// StringBuffer tableInnerData = sw.getBuffer();
		// int i = tableInnerData.indexOf("colNames");
		// int j = tableInnerData.indexOf("colModel");
		// int k = tableInnerData.indexOf("€");
		// if (k == -1) {
		// k = tableInnerData.length();
		// }
		this.initCol();
		String innerData = getData();
		// innerData = innerData.replaceAll("€\\{", "{")
		// .replaceAll(",\\s+\\{", ",{").replaceAll(",$", "");
		StringBuffer datas = new StringBuffer("var ___datas = ");
		datas.append(innerData);
		// datas.append("];");
		out.write(getJavaScript(datas));
	}

	private void initCol() {
		if (moduleName == null) {
			if (moduleName.equals("")) {
				return;
			}
		}
		FieldDefinitionDao dao = ApplicationContextHolder.getBean(
				"fieldDefinitionDao", FieldDefinitionDao.class);
		List<FieldDefinition> fields = dao.getFiledByEntityName(moduleName);
		List<String> colnames = Lists.newArrayList();
		List<ColModel> colmodels = Lists.newArrayList();
		colnames.add("uuid");
		colmodels.add(new ColModel("uuid", "uuid", "180", true));
		for (FieldDefinition field : fields) {
			if (field.isShowed()) {
				colnames.add(field.getDescname());
				colmodels.add(new ColModel(field.getDescname(), field
						.getFieldName(), "180", false));
			}
		}
		this.colNames = JsonBinder.buildNonDefaultBinder().toJson(colnames);
		this.colModel = JsonBinder.buildNonDefaultBinder().toJson(colmodels);
	}

	// private String getColModel() {
	// String colModel =
	// "[ {name : 'uuid',index : 'uuid',width : '180',hidden : true,}, {name : 'loginName',index : 'loginName',width : '180'}, "
	// + "{name : 'userName',index : 'userName',"
	// +
	// "	width : '180'}, {name : 'password',index : 'password',width : '180',hidden : true}, {name : 'sex',index : 'sex',width : '180'"
	// + "}, {name : 'enabled',index : 'enabled',width : '180'"
	// + "}, {name : 'issys',index : 'issys',width : '180',hidden : true"
	// + "}, {name : 'remark',index : 'remark',width : '180'} ]";
	// return colModel;
	// }

	private String getData() {
		UserDao dao = ApplicationContextHolder
				.getBean("userDao", UserDao.class);
		return JsonBinder.buildNonDefaultBinder().toJson(dao.getAll());
	}

	public String getJavaScript(CharSequence datas) {
		StringBuffer script = new StringBuffer();
		String jsonProperties = TagTools.KeyValuePair2Json("id:" + this.id
				+ ":false", "ajaxGridOptions:" + this.ajaxGridOptions
				+ ":false", "ajaxSelectOptions:" + this.ajaxSelectOptions
				+ ":false", "altclass:" + this.altclass + ":false", "altRows:"
				+ this.altRows + ":false", "autoencode:" + this.autoencode
				+ ":true", "autowidth:" + this.autowidth + ":true", "caption:"
				+ this.caption + ":false", "cellLayout:" + this.cellLayout
				+ ":false", "cellEdit:" + this.cellEdit + ":false",
				"cellsubmit:" + this.cellsubmit + ":false", "cellurl:"
						+ this.cellurl + ":false", "cmTemplate:"
						+ this.cmTemplate + ":false", "data:" + this.data
						+ ":false", "datastr:" + this.datastr + ":false",
				"datatype:" + this.datatype + ":false", "deepempty:"
						+ this.deepempty + ":false", "deselectAfterSort:"
						+ this.deselectAfterSort + ":false", "direction:"
						+ this.direction + ":false", "editurl:" + this.editurl
						+ ":false", "emptyrecords:" + this.emptyrecords
						+ ":false", "ExpandColClick:" + this.ExpandColClick
						+ ":false", "ExpandColumn:" + this.ExpandColumn
						+ ":false", "footerrow:" + this.footerrow + ":false",
				"forceFit:" + this.forceFit + ":false", "gridstate:"
						+ this.gridstate + ":false", "gridview:"
						+ this.gridview + ":false", "grouping:" + this.grouping
						+ ":false", "headertitles:" + this.headertitles
						+ ":false", "height:" + this.height + ":false",
				"hiddengrid:" + this.hiddengrid + ":false", "hidegrid:"
						+ this.hidegrid + ":false", "hoverrows:"
						+ this.hoverrows + ":false", "idPrefix:"
						+ this.idPrefix + ":false", "ignoreCase:"
						+ this.ignoreCase + ":false", "inlineData:"
						+ this.inlineData + ":false", "jsonReader:"
						+ this.jsonReader + ":false", "lastpage:"
						+ this.lastpage + ":false", "lastsort:" + this.lastsort
						+ ":false", "loadonce:" + this.loadonce + ":false",
				"loadtext:" + this.loadtext + ":false", "loadui:" + this.loadui
						+ ":false", "mtype:" + this.mtype + ":false",
				"multikey:" + this.multikey + ":false", "multiboxonly:"
						+ this.multiboxonly + ":false", "multiselect:"
						+ this.multiselect + ":true", "multiselectWidth:"
						+ this.multiselectWidth + ":false", "page:" + this.page
						+ ":false", "pager:" + this.pager + ":false",
				"pagerpos:" + this.pagerpos + ":false", "pgbuttons:"
						+ this.pgbuttons + ":false", "pginput:" + this.pginput
						+ ":false", "pgtext:" + this.pgtext + ":false",
				"prmNames:" + this.prmNames + ":false", "postData:"
						+ this.postData + ":false", "reccount:" + this.reccount
						+ ":false", "recordpos:" + this.recordpos + ":false",
				"records:" + this.records + ":false", "recordtext:"
						+ this.recordtext + ":false", "resizeclass:"
						+ this.resizeclass + ":false", "rowList:"
						+ this.rowList + ":true", "rownumbers:"
						+ this.rownumbers + ":false", "rowNum:" + this.rowNum
						+ ":false", "rowTotal:" + this.rowTotal + ":false",
				"rownumWidth:" + this.rownumWidth + ":false", "savedRow:"
						+ this.savedRow + ":false", "searchdata:"
						+ this.searchdata + ":false", "scroll:" + this.scroll
						+ ":false", "scrollOffset:" + this.scrollOffset
						+ ":false", "scrollTimeout:" + this.scrollTimeout
						+ ":false", "scrollrows:" + this.scrollrows + ":false",
				"selarrrow:" + this.selarrrow + ":false", "selrow:"
						+ this.selrow + ":false", "shrinkToFit:"
						+ this.shrinkToFit + ":false", "sortable:"
						+ this.sortable + ":false", "sortname:" + this.sortname
						+ ":false", "sortorder:" + this.sortorder + ":false",
				"subGrid:" + this.subGrid + ":false", "subGridOptions:"
						+ this.subGridOptions + ":false", "subGridModel:"
						+ this.subGridModel + ":false", "subGridType:"
						+ this.subGridType + ":false", "subGridUrl:"
						+ this.subGridUrl + ":false", "subGridWidth:"
						+ this.subGridWidth + ":false", "toolbar:"
						+ this.toolbar + ":false", "toppager:" + this.toppager
						+ ":false", "totaltime:" + this.totaltime + ":false",
				"treedatatype:" + this.treedatatype + ":false", "treeGrid:"
						+ this.treeGrid + ":false", "treeGridModel:"
						+ this.treeGridModel + ":false", "treeIcons:"
						+ this.treeIcons + ":false", "treeReader:"
						+ this.treeReader + ":false", "tree_root_level:"
						+ this.tree_root_level + ":false", "url:" + this.url
						+ ":false", "userData:" + this.userData + ":false",
				"userDataOnFooter:" + this.userDataOnFooter + ":false",
				"viewrecords:" + this.viewrecords + ":true", "viewsortcols:"
						+ this.viewsortcols + ":false", "width:" + this.width
						+ ":false", "xmlReader:" + this.xmlReader + ":false",
				"edit:" + this.edit + ":false", "add:" + this.add + ":false",
				"del:" + this.del + ":false", "afterInsertRow:"
						+ this.afterInsertRow + ":true", "beforeProcessing:"
						+ this.beforeProcessing + ":true", "beforeRequest:"
						+ this.beforeRequest + ":true", "beforeSelectRow:"
						+ this.beforeSelectRow + ":true", "gridComplete:"
						+ this.gridComplete + ":true", "loadBeforeSend:"
						+ this.loadBeforeSend + ":true", "loadComplete:"
						+ this.loadComplete + ":true", "loadError:"
						+ this.loadError + ":true", "onCellSelect:"
						+ this.onCellSelect + ":true", "ondblClickRow:"
						+ this.ondblClickRow + ":true", "onHeaderClick:"
						+ this.onHeaderClick + ":true", "onPaging:"
						+ this.onPaging + ":true", "onRightClickRow:"
						+ this.onRightClickRow + ":true", "onSelectAll:"
						+ this.onSelectAll + ":true", "onSelectRow:"
						+ this.onSelectRow + ":true", "onSortCol:"
						+ this.onSortCol + ":true", "resizeStart:"
						+ this.resizeStart + ":true", "resizeStop:"
						+ this.resizeStop + ":true", "serializeGridData:"
						+ this.serializeGridData + ":true");
		script.append("<script>" + RN);
		script.append("	 $(document).ready(function(){" + RN);
		script.append("		$(\"#" + id + "\").jqGrid({" + RN);
		script.append("			" + jsonProperties + "," + RN);
		script.append("			datatype : \"json\"," + RN);
		script.append("			colNames : " + colNames + "," + RN);
		script.append("			colModel : " + colModel + RN);
		script.append("		});");

		// page部分
		script.append("		$(\"#" + id + "\").jqGrid(" + RN);
		script.append(" \"navGrid\","
				+ "\"#"
				+ id
				+ "Pager\",{"
				+ TagTools.KeyValuePair2Json("edit:" + edit + ":true", "add:"
						+ add + ":true", "del:" + del + ":true") + "});" + RN);
		// 结束
		script.append("})");
		// script.append(".navGrid(\"#"
		// + id
		// + "Pager\",{"
		// + TagTools.KeyValuePair2Json("edit:" + edit + ":true", "add:"
		// + add + ":true", "del:" + del + ":true") + "});" + RN);
		// script.append("	    " + datas + RN);
		// script.append("		jQuery(\"#" + id +
		// "\").jqGrid('setFrozenColumns'); "
		// + RN);
		// script.append("		for(var i=0;i<=___datas.length;i++)" + RN);
		// script.append("		        jQuery(\"#" + id
		// + "\").jqGrid(\"addRowData\",i+1,___datas[i]);" + RN);
		// script.append("		});" + RN);
		// script.append("		jQuery(\"#"
		// + id
		// +
		// "\").jqGrid('setGridParam',{\"page\":1,}).trigger(\"reloadGrid\"); "
		// + RN);
		script.append("</script>" + RN);
		return script.toString();
	}

	public String addRow() {
		StringBuffer script = new StringBuffer();
		script.append("<script type=\"text/javascript\">" + RN);
		script.append("function addRow(datarow){" + RN);
		script.append("		var flag = jQuery(\"" + id
				+ "\").jqGrid('addRowData',99,datarow);" + RN);
		script.append("}" + RN);
		script.append("</script>" + RN);
		return script.toString();
	}

	public String setRow() {
		StringBuffer script = new StringBuffer();
		script.append("<script type=\"text/javascript\">" + RN);
		script.append("function setRow(datarow){" + RN);
		script.append("		var flag = jQuery(\"" + id
				+ "\").jqGrid('setRowData',99,datarow);" + RN);
		script.append("}" + RN);
		script.append("</script>" + RN);
		return script.toString();
	}

	public String delRow() {
		StringBuffer script = new StringBuffer();
		script.append("<script type=\"text/javascript\">" + RN);
		script.append("function delRow(){" + RN);
		script.append("		var id = jQuery(\"" + id
				+ "\").jqGrid('getGridParam','selrow');" + RN);
		script.append("		if (id) {" + RN);
		script.append("			jQuery(\"" + id + "\").jqGrid('delRowData',id);" + RN);
		script.append("		} else {" + RN);
		script.append("			alert(\"Please select row\");" + RN);
		script.append("		}" + RN);
		script.append("}" + RN);
		script.append("</script>" + RN);
		return script.toString();
	}

	public String getRow() {
		StringBuffer script = new StringBuffer();
		script.append("<script type=\"text/javascript\">" + RN);
		script.append("function getRow(){" + RN);
		script.append("		var id = jQuery(\"" + id
				+ "\").jqGrid('getGridParam','selrow');" + RN);
		script.append("		if (id) {" + RN);
		script.append("			var record = jQuery(\"" + id
				+ "\").jqGrid('getRowData',id);" + RN);
		script.append("			alert(record.username);" + RN);
		script.append("		} else {" + RN);
		script.append("			alert(\"Please select row\");" + RN);
		script.append("		}" + RN);
		script.append("}" + RN);
		script.append("</script>" + RN);
		return script.toString();
	}

	public void setId(String id) {
		this.id = id;
		this.pager = this.id + "Pager";
	}

	public void setAjaxGridOptions(String ajaxGridOptions) {
		this.ajaxGridOptions = ajaxGridOptions;
	}

	public void setAjaxSelectOptions(String ajaxSelectOptions) {
		this.ajaxSelectOptions = ajaxSelectOptions;
	}

	public void setAltclass(String altclass) {
		this.altclass = altclass;
	}

	public void setAltRows(String altRows) {
		this.altRows = altRows;
	}

	public void setAutoencode(String autoencode) {
		this.autoencode = autoencode;
	}

	public void setAutowidth(String autowidth) {
		this.autowidth = autowidth;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public void setCellLayout(String cellLayout) {
		this.cellLayout = cellLayout;
	}

	public void setCellEdit(String cellEdit) {
		this.cellEdit = cellEdit;
	}

	public void setCellsubmit(String cellsubmit) {
		this.cellsubmit = cellsubmit;
	}

	public void setCellurl(String cellurl) {
		this.cellurl = cellurl;
	}

	public void setCmTemplate(String cmTemplate) {
		this.cmTemplate = cmTemplate;
	}

	public void setColModel(String colModel) {
		this.colModel = colModel;
	}

	public void setColNames(String colNames) {
		this.colNames = colNames;
	}

	public void setData(String data) {
		this.data = data;
	}

	public void setDatastr(String datastr) {
		this.datastr = datastr;
	}

	public void setDatatype(String datatype) {
		this.datatype = datatype;
	}

	public void setDeepempty(String deepempty) {
		this.deepempty = deepempty;
	}

	public void setDeselectAfterSort(String deselectAfterSort) {
		this.deselectAfterSort = deselectAfterSort;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public void setEditurl(String editurl) {
		this.editurl = editurl;
	}

	public void setEmptyrecords(String emptyrecords) {
		this.emptyrecords = emptyrecords;
	}

	public void setExpandColClick(String expandColClick) {
		ExpandColClick = expandColClick;
	}

	public void setExpandColumn(String expandColumn) {
		ExpandColumn = expandColumn;
	}

	public void setFooterrow(String footerrow) {
		this.footerrow = footerrow;
	}

	public void setForceFit(String forceFit) {
		this.forceFit = forceFit;
	}

	public void setGridstate(String gridstate) {
		this.gridstate = gridstate;
	}

	public void setGridview(String gridview) {
		this.gridview = gridview;
	}

	public void setGrouping(String grouping) {
		this.grouping = grouping;
	}

	public void setHeadertitles(String headertitles) {
		this.headertitles = headertitles;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public void setHiddengrid(String hiddengrid) {
		this.hiddengrid = hiddengrid;
	}

	public void setHidegrid(String hidegrid) {
		this.hidegrid = hidegrid;
	}

	public void setHoverrows(String hoverrows) {
		this.hoverrows = hoverrows;
	}

	public void setIdPrefix(String idPrefix) {
		this.idPrefix = idPrefix;
	}

	public void setIgnoreCase(String ignoreCase) {
		this.ignoreCase = ignoreCase;
	}

	public void setInlineData(String inlineData) {
		this.inlineData = inlineData;
	}

	public void setJsonReader(String jsonReader) {
		this.jsonReader = jsonReader;
	}

	public void setLastpage(String lastpage) {
		this.lastpage = lastpage;
	}

	public void setLastsort(String lastsort) {
		this.lastsort = lastsort;
	}

	public void setLoadonce(String loadonce) {
		this.loadonce = loadonce;
	}

	public void setLoadtext(String loadtext) {
		this.loadtext = loadtext;
	}

	public void setLoadui(String loadui) {
		this.loadui = loadui;
	}

	public void setMtype(String mtype) {
		this.mtype = mtype;
	}

	public void setMultikey(String multikey) {
		this.multikey = multikey;
	}

	public void setMultiboxonly(String multiboxonly) {
		this.multiboxonly = multiboxonly;
	}

	public void setMultiselect(String multiselect) {
		this.multiselect = multiselect;
	}

	public void setMultiselectWidth(String multiselectWidth) {
		this.multiselectWidth = multiselectWidth;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public void setPager(String pager) {
		this.pager = pager;
	}

	public void setPagerpos(String pagerpos) {
		this.pagerpos = pagerpos;
	}

	public void setPgbuttons(String pgbuttons) {
		this.pgbuttons = pgbuttons;
	}

	public void setPginput(String pginput) {
		this.pginput = pginput;
	}

	public void setPgtext(String pgtext) {
		this.pgtext = pgtext;
	}

	public void setPrmNames(String prmNames) {
		this.prmNames = prmNames;
	}

	public void setPostData(String postData) {
		this.postData = postData;
	}

	public void setReccount(String reccount) {
		this.reccount = reccount;
	}

	public void setRecordpos(String recordpos) {
		this.recordpos = recordpos;
	}

	public void setRecords(String records) {
		this.records = records;
	}

	public void setRecordtext(String recordtext) {
		this.recordtext = recordtext;
	}

	public void setResizeclass(String resizeclass) {
		this.resizeclass = resizeclass;
	}

	public void setRowList(String rowList) {
		this.rowList = rowList;
	}

	public void setRownumbers(String rownumbers) {
		this.rownumbers = rownumbers;
	}

	public void setRowNum(String rowNum) {
		this.rowNum = rowNum;
	}

	public void setRowTotal(String rowTotal) {
		this.rowTotal = rowTotal;
	}

	public void setRownumWidth(String rownumWidth) {
		this.rownumWidth = rownumWidth;
	}

	public void setSavedRow(String savedRow) {
		this.savedRow = savedRow;
	}

	public void setSearchdata(String searchdata) {
		this.searchdata = searchdata;
	}

	public void setScroll(String scroll) {
		this.scroll = scroll;
	}

	public void setScrollOffset(String scrollOffset) {
		this.scrollOffset = scrollOffset;
	}

	public void setScrollTimeout(String scrollTimeout) {
		this.scrollTimeout = scrollTimeout;
	}

	public void setScrollrows(String scrollrows) {
		this.scrollrows = scrollrows;
	}

	public void setSelarrrow(String selarrrow) {
		this.selarrrow = selarrrow;
	}

	public void setSelrow(String selrow) {
		this.selrow = selrow;
	}

	public void setShrinkToFit(String shrinkToFit) {
		this.shrinkToFit = shrinkToFit;
	}

	public void setSortable(String sortable) {
		this.sortable = sortable;
	}

	public void setSortname(String sortname) {
		this.sortname = sortname;
	}

	public void setSortorder(String sortorder) {
		this.sortorder = sortorder;
	}

	public void setSubGrid(String subGrid) {
		this.subGrid = subGrid;
	}

	public void setSubGridOptions(String subGridOptions) {
		this.subGridOptions = subGridOptions;
	}

	public void setSubGridModel(String subGridModel) {
		this.subGridModel = subGridModel;
	}

	public void setSubGridType(String subGridType) {
		this.subGridType = subGridType;
	}

	public void setSubGridUrl(String subGridUrl) {
		this.subGridUrl = subGridUrl;
	}

	public void setSubGridWidth(String subGridWidth) {
		this.subGridWidth = subGridWidth;
	}

	public void setToolbar(String toolbar) {
		this.toolbar = toolbar;
	}

	public void setToppager(String toppager) {
		this.toppager = toppager;
	}

	public void setTotaltime(String totaltime) {
		this.totaltime = totaltime;
	}

	public void setTreedatatype(String treedatatype) {
		this.treedatatype = treedatatype;
	}

	public void setTreeGrid(String treeGrid) {
		this.treeGrid = treeGrid;
	}

	public void setTreeGridModel(String treeGridModel) {
		this.treeGridModel = treeGridModel;
	}

	public void setTreeIcons(String treeIcons) {
		this.treeIcons = treeIcons;
	}

	public void setTreeReader(String treeReader) {
		this.treeReader = treeReader;
	}

	public void setTree_root_level(String tree_root_level) {
		this.tree_root_level = tree_root_level;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setUserData(String userData) {
		this.userData = userData;
	}

	public void setUserDataOnFooter(String userDataOnFooter) {
		this.userDataOnFooter = userDataOnFooter;
	}

	public void setViewrecords(String viewrecords) {
		this.viewrecords = viewrecords;
	}

	public void setViewsortcols(String viewsortcols) {
		this.viewsortcols = viewsortcols;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public void setXmlReader(String xmlReader) {
		this.xmlReader = xmlReader;
	}

	public void setAfterInsertRow(String afterInsertRow) {
		this.afterInsertRow = afterInsertRow;
	}

	public void setBeforeProcessing(String beforeProcessing) {
		this.beforeProcessing = beforeProcessing;
	}

	public void setBeforeRequest(String beforeRequest) {
		this.beforeRequest = beforeRequest;
	}

	public void setBeforeSelectRow(String beforeSelectRow) {
		this.beforeSelectRow = beforeSelectRow;
	}

	public void setGridComplete(String gridComplete) {
		this.gridComplete = gridComplete;
	}

	public void setLoadBeforeSend(String loadBeforeSend) {
		this.loadBeforeSend = loadBeforeSend;
	}

	public void setLoadComplete(String loadComplete) {
		this.loadComplete = loadComplete;
	}

	public void setLoadError(String loadError) {
		this.loadError = loadError;
	}

	public void setOnCellSelect(String onCellSelect) {
		this.onCellSelect = onCellSelect;
	}

	public void setOndblClickRow(String ondblClickRow) {
		this.ondblClickRow = ondblClickRow;
	}

	public void setOnHeaderClick(String onHeaderClick) {
		this.onHeaderClick = onHeaderClick;
	}

	public void setOnPaging(String onPaging) {
		this.onPaging = onPaging;
	}

	public void setOnRightClickRow(String onRightClickRow) {
		this.onRightClickRow = onRightClickRow;
	}

	public void setOnSelectAll(String onSelectAll) {
		this.onSelectAll = onSelectAll;
	}

	public void setOnSelectRow(String onSelectRow) {
		this.onSelectRow = onSelectRow;
	}

	public void setOnSortCol(String onSortCol) {
		this.onSortCol = onSortCol;
	}

	public void setResizeStart(String resizeStart) {
		this.resizeStart = resizeStart;
	}

	public void setResizeStop(String resizeStop) {
		this.resizeStop = resizeStop;
	}

	public void setSerializeGridData(String serializeGridData) {
		this.serializeGridData = serializeGridData;
	}

	public void setEdit(String edit) {
		this.edit = edit;
	}

	public void setAdd(String add) {
		this.add = add;
	}

	public void setDel(String del) {
		this.del = del;
	}

	/**
	 * @return the moduleName
	 */
	public String getModuleName() {
		return moduleName;
	}

	/**
	 * @param moduleName
	 *            the moduleName to set
	 */
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

}

class ColModel {
	public String name;

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the index
	 */
	public String getIndex() {
		return index;
	}

	/**
	 * @param index
	 *            the index to set
	 */
	public void setIndex(String index) {
		this.index = index;
	}

	/**
	 * @return the width
	 */
	public String getWidth() {
		return width;
	}

	/**
	 * @param width
	 *            the width to set
	 */
	public void setWidth(String width) {
		this.width = width;
	}

	/**
	 * @return the hidden
	 */
	public boolean getHidden() {
		return hidden;
	}

	/**
	 * @param hidden
	 *            the hidden to set
	 */
	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}

	public String index;
	public String width;
	public boolean hidden;

	public ColModel() {

	}

	public ColModel(String name, String index, String width, boolean hidden) {

		this.name = name;
		this.index = index;
		this.width = width;
		this.hidden = hidden;
	}
}
