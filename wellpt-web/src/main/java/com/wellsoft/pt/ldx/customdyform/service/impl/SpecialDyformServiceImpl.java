package com.wellsoft.pt.ldx.customdyform.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fr.third.org.apache.poi.hssf.usermodel.HSSFCell;
import com.fr.third.org.apache.poi.hssf.usermodel.HSSFCellStyle;
import com.fr.third.org.apache.poi.hssf.usermodel.HSSFRow;
import com.fr.third.org.apache.poi.hssf.usermodel.HSSFSheet;
import com.fr.third.org.apache.poi.hssf.usermodel.HSSFWorkbook;
import com.wellsoft.pt.basicdata.datasource.bean.DataSourceProfileBean;
import com.wellsoft.pt.basicdata.datasource.service.DataSourceProfileService;
import com.wellsoft.pt.basicdata.datasource.support.JdbcConnection;
import com.wellsoft.pt.core.support.QueryItem;
import com.wellsoft.pt.dyform.facade.DyFormApiFacade;
import com.wellsoft.pt.dyform.support.DyFormData;
import com.wellsoft.pt.ldx.customdyform.dao.SpecialDyformDao;
import com.wellsoft.pt.ldx.customdyform.service.SpecialDyformService;
import com.wellsoft.pt.org.entity.Employee;
import com.wellsoft.pt.org.entity.User;
import com.wellsoft.pt.org.facade.OrgApiFacade;


@Service
@Transactional
public class SpecialDyformServiceImpl implements SpecialDyformService{
	
	@Autowired
	private SpecialDyformDao specialDyformDao;
	@Autowired
	private DyFormApiFacade dyformApiFacade;
	@Autowired
	private DataSourceProfileService dataSourceProfileService;
	@Autowired
	private OrgApiFacade orgApiFacade;

	private SimpleDateFormat yyyyMMdd = new SimpleDateFormat("yyyyMMdd");
	
	public void getCheckMeterBuy(String formId,String dataUuid){
		
		String formUuid = dyformApiFacade.getFormUuidById(formId);
		
		dyformApiFacade.getFormData(formUuid, dataUuid);
	}
	
	@Override
	public List<QueryItem> dataSourceQuery(String dataSourceId,String sql){
		
		List<QueryItem> queryItems = new ArrayList<QueryItem>();
		// 得到外部数据源配�?
		DataSourceProfileBean dspb = dataSourceProfileService.getBeanByUuid("53665141-ccff-4685-a84c-35286450acde");
		if("1".equals(dspb.getOutDataSourceType())&&"1".equals(dspb.getDatabaseType())){
			JdbcConnection jc = new JdbcConnection();
			// 拼接数据库连接url
			String urlStr = "jdbc:oracle:thin:@"+dspb.getHost()+":"+dspb.getPort()+":"+dspb.getDatabaseSid();
			try {
				PreparedStatement pState = jc
						.createConnection("1", dspb.getUserName() , dspb.getPassWord(), urlStr)
						.prepareStatement(sql);
				ResultSet rs = pState.executeQuery();
				ResultSetMetaData rsmd = rs.getMetaData();
				
				while (rs.next()) {
					QueryItem map = new QueryItem();
					for(int i=1;i<=rsmd.getColumnCount();i++){
						map.put(rsmd.getColumnName(i), rs.getObject(i));
					}
					queryItems.add(map);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return queryItems;
	}
	
	@Override
	public void saveDataToForm(String formUuid,String dataUuid){
		// 获得源表单的数据
		DyFormData dyformdata = dyformApiFacade.getDyFormData(formUuid, dataUuid);
		// 获得源表单数�?
		Map<String,Object> dataMap = dyformdata.getFormDataByFormUuidAndDataUuid(formUuid, dataUuid);
		// 目标表单的uuid
		String targetFromUuid = dyformApiFacade.getFormUuidById("uf_pbgl_cplxsyyrohscsysmgz");
		// 目标表单的数�?
		DyFormData targetDataForm =dyformApiFacade.getDyFormData(
				targetFromUuid, dataUuid);
		Map<String,Object> targetData = targetDataForm.getFormDataByFormUuidAndDataUuid(
				targetFromUuid, dataUuid);
		// 若目标表单尚无相应数据，则新增一�?
		if(targetData==null){
			String parentFormUuid = dyformApiFacade.getFormUuidById("userform_ywgl_ddpsyqfsqb");
			String connFormId = dyformApiFacade.getFormIdByFormUuid(formUuid) + "_rl";
			// 中间表查询条件为主表的formUuid 及 dataUuid 
			String connSql = "select uuid,data_uuid,mainform_data_uuid,mainform_form_uuid from " 
					+ connFormId +" where data_uuid = '" + dataUuid + "' and mainform_form_uuid ='" + parentFormUuid +"'";
			// 查询出符合条件的中间表数据列表
			List<Map<String,Object>> connList = new ArrayList<Map<String,Object>>();
			Map<String,Object> parentMap = new HashMap<String,Object>();
			try {
				connList = specialDyformDao.sqlQuery(connSql);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if(connList.size()==1){
				Map<String,Object> connMap = connList.get(0);
				DyFormData pdyformData = dyformApiFacade.getDyFormData(parentFormUuid, (String) (connMap.get("mainform_data_uuid")));
				parentMap = pdyformData.getFormDataByFormUuidAndDataUuid(parentFormUuid, (String) (connMap.get("mainform_data_uuid")));
			}
			targetData = new HashMap<String,Object>();
			targetData.put("uuid",dataMap.get("uuid"));
			targetData.put("xsddh",parentMap.get("xsddh"));
			targetData.put("hxm",dataMap.get("hxmh"));
			// 抓取 线别
			String sql = "select distinct b.zxh from zppt0030 a"+ 
					" inner join zppt0033 b on a.aufnr=b.aufnr"+
					" where a.kdauf='"+ fillStr((String)parentMap.get("xsddh"),10,"0") +
					"' and a.kdpos='"+ fillStr((String)dataMap.get("hxmh"),6,"0") +"'";
			List<QueryItem> zxhs = dataSourceQuery("SAP", sql);
			String zxh = "";
			for(QueryItem qi:zxhs){
				zxh += qi.getString("zxh") + ",";
			}
			if(zxh.length()>0){
				zxh = zxh.substring(0, zxh.length()-1);
			}
			targetData.put("xb", zxh);
			targetData.put("kh",parentMap.get("khjc"));
			targetData.put("cpid",dataMap.get("cpid"));
			targetData.put("ggms",dataMap.get("cp"));
			// 订单评审提交时间
			targetData.put("ddpstjrq",dataMap.get("wgrq"));
			// 抓取 首次入库时间
			sql = "select c.budat from afpo a, mseg b, mkpf c "+
					  " where a.aufnr = b.aufnr and b.mblnr = c.mblnr " +
					  " and a.kdauf='"+ fillStr((String)parentMap.get("xsddh"),10,"0") +
					"' and a.kdpos='"+ fillStr((String)dataMap.get("hxmh"),6,"0") +"' and rownum<2 order by c.budat ";
			List<QueryItem> scrkrqs = dataSourceQuery("SAP", sql);
			if(scrkrqs.size()==1){
				String scrkrq = (String) scrkrqs.get(0).get("budat");
				try {
					targetData.put("scrkrq", yyyyMMdd.parse(scrkrq));
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
			targetData.put("scgc",dataMap.get("gc"));
			targetData.put("lysllxsy",dataMap.get("lysllxsy"));
			targetData.put("lyslrohs",dataMap.get("lyslrohs"));
			targetData.put("lyslsmgz",dataMap.get("lyslsmgz"));
			
			
			Map<String,List<Map<String,Object>>> map = targetDataForm.getFormDatas();
			List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
			list.add(targetData);
			map.put(targetFromUuid, list);
			dyformApiFacade.saveFormData(targetDataForm);
		}
		
	}

	@Override
	public List<QueryItem> formDataQuery(String formId, String columns, String condition) {
		String[] columnArr = null;
		String formUuid = dyformApiFacade.getFormUuidById(formId);
		if(StringUtils.isNotEmpty(columns)){
			columnArr = columns.split(",");
		}
		if(StringUtils.isEmpty(condition)){
			condition = null;
		}
		return dyformApiFacade.query(formUuid, columnArr,condition, null,"","","",0,Integer.MAX_VALUE);
		
	}

	@Override
	public void addAccount(String formUuid,String dataUuid){
		// 获得表单的信息 
		DyFormData dyformdata = dyformApiFacade.getDyFormData(formUuid,dataUuid);
		// 通过从表id获得从表数据 
		List<Map<String, Object>> childList = dyformdata.getFormDatasById("uf_leedarson_xxgl_xxfwdxx");
		for(Map<String,Object> child:childList){
			// 
			DyFormData account = dyformApiFacade.createDyformData(dyformApiFacade.getFormUuidById("uf_doc_account"));
			
			//通过姓名获得userid
			String userid = "";
			//通过姓名获得用户id
			String xm = (String) child.get("xm");
			List<Employee> lists = orgApiFacade.getEmployeesByName(xm);
			if(lists != null && lists.size() >0){
				userid = lists.get(0).getId();
			}
			account.setFieldValue("userid",xm);
			account.setFieldValue("code", child.get("gh"));
			String xtlxz = (String)child.get("xtlxz");
			if(StringUtils.isNotBlank(xtlxz)){
				xtlxz = DyFormApiFacade.getDisplayValue(xtlxz);
			}else{
				xtlxz = "";
			}
			account.setFieldValue("systype",xtlxz );
			account.setFieldValue("account", child.get("xzdzh"));
			account.setFieldValue("stats", "1");
			dyformApiFacade.saveFormData(account);
		}
	}
	
	@Override
	public List<QueryItem> formDataQueryAll(String formId,String columns,String condition,String childFormId,  String childColumns) throws Exception {
		// 主表查询字段数组
		String[] columnArr = null;
		// 主表Uuid
		String formUuid = dyformApiFacade.getFormUuidById(formId);
		if(StringUtils.isNotEmpty(columns)){
			columnArr = columns.split(",");
		}
		if(StringUtils.isEmpty(condition)){
			condition = null;
		}
		// 根据条件查询出主表数据列表
		List<QueryItem> list = dyformApiFacade.query(formUuid, columnArr,condition, null,"","","",0,Integer.MAX_VALUE);
		// 连接主表与从表的中间表
		String connFormId = childFormId + "_rl";
		// 中间表Uuid 
		//String connFormUuid = dyformApiFacade.getFormUuidById(connFormId);
		// 中间表查询出的字段数组 
		//String[] connColumnArr = {"uuid","data_uuid","mainform_data_uuid","mainform_form_uuid"};
		// 查询从表数据 
		for(QueryItem qi:list){
			// 中间表查询条件为主表的formUuid 及 dataUuid 
			String connSql = "select uuid,data_uuid,mainform_data_uuid,mainform_form_uuid from " 
					+ connFormId +" where mainform_data_uuid = '" + qi.getString("uuid") + "' and mainform_form_uuid ='" + formUuid +"'";
			// 查询出符合条件的中间表数据列表
			List<Map<String,Object>> connList = specialDyformDao.sqlQuery(connSql);
			// 从表数据的uuid
			StringBuffer childUuids = new StringBuffer();
			for(Map<String,Object> m:connList){
				childUuids.append("'");
				childUuids.append((String)m.get("data_uuid"));
				childUuids.append("',");
			}
			String childUuid = childUuids.toString();
			// 根据从表数据的uuid查出从表的数据列表
			if(StringUtils.isNotBlank(childUuid)){
				childUuid = childUuid.substring(0,childUuid.length()-1);
				String childCondition = "uuid in (" + childUuid + ")";
				qi.put("child_all_list", formDataQuery(childFormId,childColumns,childCondition));
			}else{
				qi.put("child_all_list", null);
			}
		}
		return list;
	}
	
	@Override
	public List<Map<String,Object>> standbyImport(InputStream is, String sheetName) throws IOException{
		// 解析excel放入List
		HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);
		// 第一个工作表
		HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(0);
		// 如果excel页签名不为空，则取指定的页签.
		if (StringUtils.isNotEmpty(sheetName)) {
			hssfSheet = hssfWorkbook.getSheet(sheetName);
			if (hssfSheet == null) {
				return new ArrayList<Map<String,Object>>();
			}
		}
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		// 循环行Row   
		for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
			Map map = new HashMap(); 
			HSSFRow hssfRow = hssfSheet.getRow(rowNum);
			if (hssfRow == null) {
				continue;
			} 
			// 循环列Cell   
			for (int cellNum = 0; cellNum <= hssfRow.getLastCellNum(); cellNum++) {
				HSSFCell hssfCell = hssfRow.getCell(cellNum);
				if (hssfCell == null) {
					map.put(cellNum, "");
					continue;
				}else{
					map.put(cellNum, getValue(hssfCell));
				}
				System.out.print("    " + getValue(hssfCell));
			}
			list.add(map);
			System.out.println();
		}
		return null;
	}
	
	@Override
	public File standbyExport(String formUuid,String dataUuid) {
		// 表头
		String[] titleNameArray = new String[]{"行项目号","备库原因","整灯备库计划号","客户代码","物料ID","物料描述",
				"申请数量","消耗数量","未消耗数量","预计SAP转正时间","预计订单交期","转正后销售订单号-行项目","未消耗处理对策"};
		
		//获得要导出的所有数据
		List<String[]> dataList = new ArrayList<String[]>();
		
		// 获得流程表单的数据
		DyFormData dyformdata = dyformApiFacade.getDyFormData(formUuid, dataUuid);
		// 获得主表表单数据
		//Map<String,Object> dataMap = dyformdata.getFormDataByFormUuidAndDataUuid(event.getFormUuid(), event.getDataUuid());
		// 通过从表id获得从表数据
		List<Map<String, Object>> childList = dyformdata.getFormDatasById("uf_scgl_fxbksqdxx");
		for(Map<String,Object> child:childList){
			String [] lineData = {(String)child.get("hxmh"),(String)child.get("bkyy"),
					(String)child.get("zdbkjhh"),(String)child.get("khdm"),
					(String)child.get("wlid"),(String)child.get("wlms"),
					child.get("sqsl").toString()  ,child.get("xhsl").toString(),
					child.get("wxhsl").toString(),(String)child.get("yjsapzzsj"),
					(String)child.get("yjddjq"),(String)child.get("zzhxsddh"),
					(String)child.get("wxhcldc")};
			dataList.add(lineData);
		}
		
		File file = generateXmlFile(dataList, titleNameArray,"风险备库单");
		return file;
	}
	

	public File generateXmlFile(List<String[]> dataList, String[] titleArray,String title) {
		// 第一步，创建一个webbook，对应一个Excel文件
		HSSFWorkbook wb = new HSSFWorkbook();
		// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
		HSSFSheet sheet = wb.createSheet("Sheet1");
		// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
		HSSFRow row = sheet.createRow(0);
		// 第四步，创建单元格，并设置值表头 设置表头居中
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式

		HSSFCell cell = row.createCell((short) 0);
		for (int i = 0; i < titleArray.length; i++) {
			cell.setCellValue(titleArray[i]);
			cell.setCellStyle(style);
			cell = row.createCell((short) (i + 1));
		}

		// 第五步，写入实体数据 实际应用中这些数据从数据库得到，

		for (int i = 0; i < dataList.size(); i++) {
			row = sheet.createRow(i + 1);
			String[] dataArray = dataList.get(i);
			//			Map<String, String> map = dataList.get(i);
			// 第四步，创建单元格，并设置值
			for (int j = 0; j < dataArray.length; j++) {
				row.createCell((short) j).setCellValue(dataArray[j]);
			}
			//			for (String key : map.keySet()) {
			//				row.createCell((short) i).setCellValue(map.get(key));
			//			}
		}
		// 第六步，将文件存到指定位置
		String path = "/" + title + yyyyMMdd.format(new Date()) + ".xls";
		try {
			FileOutputStream fout = new FileOutputStream(path);
			wb.write(fout);
			fout.close();
			return new File(path);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	private String getValue(HSSFCell hssfCell) {
		if (hssfCell.getCellType() == HSSFCell.CELL_TYPE_BOOLEAN) {
			return String.valueOf(hssfCell.getBooleanCellValue());
		} else if (hssfCell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
			return String.valueOf(hssfCell.getNumericCellValue());
		} else {
			return String.valueOf(hssfCell.getStringCellValue());
		}
	}
	
	/**
	 * 调整字符串为固定长度(字符串超过指定长度则返回原字符串,否则用指定字符填充使其达到指定长度)
	 */
	public String fillStr(String sourceStr,int length,String filler){
		int len = sourceStr.length();
		if(len>length){
			return sourceStr;
		}else{
			for(int i=0;i<length-len;i++){
				sourceStr = filler + sourceStr;
			}
			return sourceStr;
		}
	}
}
