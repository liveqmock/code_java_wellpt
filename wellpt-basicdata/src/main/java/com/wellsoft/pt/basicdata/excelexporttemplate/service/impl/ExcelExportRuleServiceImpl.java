/*
 * @(#)2014-6-17 V1.0
 * 
 * Copyright 2014 WELL-SOFT, Inc. All rights reserved.
 */
package com.wellsoft.pt.basicdata.excelexporttemplate.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fr.third.org.apache.poi.hssf.usermodel.HSSFCell;
import com.fr.third.org.apache.poi.hssf.usermodel.HSSFCellStyle;
import com.fr.third.org.apache.poi.hssf.usermodel.HSSFRow;
import com.fr.third.org.apache.poi.hssf.usermodel.HSSFSheet;
import com.fr.third.org.apache.poi.hssf.usermodel.HSSFWorkbook;
import com.wellsoft.pt.basicdata.dyview.bean.ViewDefinitionBean;
import com.wellsoft.pt.basicdata.dyview.entity.ColumnDefinition;
import com.wellsoft.pt.basicdata.dyview.service.ViewDefinitionService;
import com.wellsoft.pt.basicdata.excelexporttemplate.bean.ExcelExportColumnDefinitionBean;
import com.wellsoft.pt.basicdata.excelexporttemplate.bean.ExcelExportDefinitionBean;
import com.wellsoft.pt.basicdata.excelexporttemplate.dao.ExcelExportColumnDefinitionDao;
import com.wellsoft.pt.basicdata.excelexporttemplate.dao.ExcelExportDefinitionDao;
import com.wellsoft.pt.basicdata.excelexporttemplate.entity.ExcelExportColumnDefinition;
import com.wellsoft.pt.basicdata.excelexporttemplate.entity.ExcelExportDefinition;
import com.wellsoft.pt.basicdata.excelexporttemplate.service.ExcelExportRuleService;
import com.wellsoft.pt.basicdata.facade.BasicDataApiFacade;
import com.wellsoft.pt.common.component.jqgrid.JqGridQueryData;
import com.wellsoft.pt.common.component.jqgrid.JqGridQueryInfo;
import com.wellsoft.pt.core.dao.Page;
import com.wellsoft.pt.core.support.QueryItem;
import com.wellsoft.pt.repository.entity.mongo.file.MongoFileEntity;
import com.wellsoft.pt.repository.service.MongoFileService;
import com.wellsoft.pt.utils.bean.BeanUtils;

/**
 * Description: 如何描述该类
 *  
 * @author wubin
 * @date 2014-6-17
 * @version 1.0
 * 
 * <pre>
 * 修改记录:
 * 修改后版本	修改人		修改日期			修改内容
 * 2014-6-17.1	wubin		2014-6-17		Create
 * </pre>
 *
 */
@Service
@Transactional
public class ExcelExportRuleServiceImpl implements ExcelExportRuleService {

	@Autowired
	private ExcelExportDefinitionDao excelExportDefinitionDao;
	@Autowired
	private ExcelExportColumnDefinitionDao excelExportColumnDefinitionDao;
	@Autowired
	private ViewDefinitionService viewDefinitionService;
	@Autowired
	private BasicDataApiFacade basicDataApiFacade;
	@Autowired
	private MongoFileService mongoFileService;

	/** 
	 * 通过uuid获取Excel导出规则bean
	 * 
	 * (non-Javadoc)
	 * @see com.wellsoft.pt.basicdata.excelexporttemplate.service.ExcelExportRuleService#getBeanByUuid(java.lang.String)
	 */
	@Override
	public ExcelExportDefinitionBean getBeanByUuid(String uuid) {
		ExcelExportDefinition excelExportDefinition = this.excelExportDefinitionDao.get(uuid);
		ExcelExportDefinitionBean bean = new ExcelExportDefinitionBean();
		BeanUtils.copyProperties(excelExportDefinition, bean);
		bean.setExcelExportColumnDefinition(BeanUtils.convertCollection(
				excelExportDefinition.getExcelExportColumnDefinition(), ExcelExportColumnDefinition.class));
		return bean;
	}

	/** 
	 * 保存导出规则bean
	 * 
	 * (non-Javadoc)
	 * @see com.wellsoft.pt.basicdata.excelexporttemplate.service.ExcelExportRuleService#SaveBean(com.wellsoft.pt.basicdata.excelexporttemplate.bean.ExcelExportDefinitionBean)
	 */
	@Override
	public void saveBean(ExcelExportDefinitionBean bean) {
		ExcelExportDefinition excelExportDefinition = new ExcelExportDefinition();
		// 保存新ExcelImportRule 设置id值
		if (StringUtils.isBlank(bean.getUuid())) {
			bean.setUuid(null);
			//先保存父表,然后再保存子表
			BeanUtils.copyProperties(bean, excelExportDefinition);
			this.excelExportDefinitionDao.save(excelExportDefinition);

			Set<ExcelExportColumnDefinitionBean> changeColumnDefinitions = bean.getChangeColumnDefinitions();
			for (ExcelExportColumnDefinitionBean changeColumnDefinition : changeColumnDefinitions) {
				ExcelExportColumnDefinition columnDefinition = new ExcelExportColumnDefinition();
				columnDefinition.setColumnNum(changeColumnDefinition.getColumnNum());
				columnDefinition.setAttributeName(changeColumnDefinition.getAttributeName());
				columnDefinition.setValueType(changeColumnDefinition.getValueType());
				columnDefinition.setTitleName(changeColumnDefinition.getTitleName());
				columnDefinition.setEntityName(changeColumnDefinition.getEntityName());
				columnDefinition.setColumnAliase(changeColumnDefinition.getColumnAliase());
				columnDefinition.setColumnDataType(changeColumnDefinition.getColumnDataType());
				//多方保存时得将实体类也保存而不是bean
				columnDefinition.setExcelExportDefinition(excelExportDefinition);
				excelExportColumnDefinitionDao.save(columnDefinition);
			}
		} else {
			excelExportDefinition = this.excelExportDefinitionDao.get(bean.getUuid());
			BeanUtils.copyProperties(bean, excelExportDefinition);
			this.excelExportDefinitionDao.save(excelExportDefinition);
			Set<ExcelExportColumnDefinitionBean> changeColumnDefinitions = bean.getChangeColumnDefinitions();
			for (ExcelExportColumnDefinitionBean changeColumnDefinition : changeColumnDefinitions) {
				ExcelExportColumnDefinition columnDefinition = new ExcelExportColumnDefinition();
				if (StringUtils.isNotBlank(changeColumnDefinition.getUuid())) {
					columnDefinition = excelExportColumnDefinitionDao.get(changeColumnDefinition.getUuid());
				} else {
					columnDefinition.setExcelExportDefinition(excelExportDefinition);
				}
				columnDefinition.setColumnNum(changeColumnDefinition.getColumnNum());
				columnDefinition.setAttributeName(changeColumnDefinition.getAttributeName());
				columnDefinition.setValueType(changeColumnDefinition.getValueType());
				columnDefinition.setTitleName(changeColumnDefinition.getTitleName());
				columnDefinition.setEntityName(changeColumnDefinition.getEntityName());
				columnDefinition.setColumnAliase(changeColumnDefinition.getColumnAliase());
				columnDefinition.setColumnDataType(changeColumnDefinition.getColumnDataType());
				excelExportColumnDefinitionDao.save(columnDefinition);
			}
			Set<ExcelExportColumnDefinitionBean> deletedExcelRows = bean.getDeletedExcelRows();
			for (ExcelExportColumnDefinitionBean deletedExcelRow : deletedExcelRows) {
				ExcelExportColumnDefinition columnDefinition = new ExcelExportColumnDefinition();
				columnDefinition = excelExportColumnDefinitionDao.get(deletedExcelRow.getUuid());
				excelExportColumnDefinitionDao.delete(columnDefinition);
			}
		}
	}

	/** 
	 *  删除Excel导出规则
	 * 
	 * (non-Javadoc)
	 * @see com.wellsoft.pt.basicdata.excelexporttemplate.service.ExcelExportRuleService#remove(java.lang.String)
	 */
	@Override
	public void remove(String uuid) {
		this.excelExportDefinitionDao.delete(uuid);
	}

	/** 
	 * 如何描述该方法
	 * 
	 * (non-Javadoc)
	 * @see com.wellsoft.pt.basicdata.excelexporttemplate.service.ExcelExportRuleService#removeAll(java.lang.String[])
	 */
	@Override
	public void removeAll(String[] uuids) {
		for (int i = 0; i < uuids.length; i++) {
			this.excelExportDefinitionDao.delete(uuids[i]);
		}
	}

	/** 
	 * JQgridExcel导入规则列表查询
	 * 
	 * (non-Javadoc)
	 * @see com.wellsoft.pt.basicdata.excelexporttemplate.service.ExcelExportRuleService#query(com.wellsoft.pt.common.component.jqgrid.JqGridQueryInfo)
	 */
	@Override
	public JqGridQueryData query(JqGridQueryInfo queryInfo) {
		Page<ExcelExportDefinition> pageData = new Page<ExcelExportDefinition>();
		pageData.setPageNo(queryInfo.getPage());
		pageData.setPageSize(queryInfo.getRows());
		this.excelExportDefinitionDao.findPage(pageData);
		List<ExcelExportDefinition> excelExportDefinitions = pageData.getResult();
		List<ExcelExportDefinition> jqUsers = new ArrayList<ExcelExportDefinition>();
		for (ExcelExportDefinition excelExportDefinition : excelExportDefinitions) {
			ExcelExportDefinition jqExcelExportDefinition = new ExcelExportDefinition();
			BeanUtils.copyProperties(excelExportDefinition, jqExcelExportDefinition);
			jqUsers.add(jqExcelExportDefinition);
		}
		JqGridQueryData queryData = new JqGridQueryData();
		queryData.setCurrentPage(queryInfo.getPage());
		queryData.setDataList(jqUsers);
		queryData.setRepeatitems(false);
		queryData.setTotalPages(pageData.getTotalPages());
		queryData.setTotalRows(pageData.getTotalCount());
		return queryData;
	}

	/** 
	 * 导出excel的模版
	 * 
	 * (non-Javadoc)
	 * @see com.wellsoft.pt.basicdata.excelexporttemplate.service.ExcelExportRuleService#generateExcelFile(java.lang.String)
	 */
	@Override
	public File generateExcelFile(String id, String[] dataArray) {

		//根据传入的id获得视图的bean
		ViewDefinitionBean viewDefinitionBean = viewDefinitionService.getBeanByUuid(id);
		//根据视图的配置信息获取视图配置的导出模版的id
		String excelUuid = viewDefinitionBean.getDataModuleId();

		//获得要导出的所有数据
		List<String[]> dataList = new ArrayList<String[]>();

		List<QueryItem> queryItems = basicDataApiFacade.getViewColumnData(id, dataArray);
		for (QueryItem queryItem : queryItems) {
			StringBuilder tempStr = new StringBuilder();
			for (String key : queryItem.keySet()) {
				String value = (String) queryItem.get(key);
				tempStr.append(";" + value);
			}
			String[] rowDataArray = tempStr.toString().replaceFirst(";", "").split(";");
			dataList.add(rowDataArray);
		}
		String[] titleNameArray = new String[200];
		if (StringUtils.isNotBlank(excelUuid)) {
			ExcelExportDefinition excelExportDefinition = this.excelExportDefinitionDao.get(excelUuid);
			String fileUuid = excelExportDefinition.getFileUuid();
			//		MongoFileEntity fileEntity = mongoFileService.getFile(fileUuid);
			List<MongoFileEntity> files = mongoFileService.getFilesFromFolder(excelExportDefinition.getFileUuid(),
					"attach");
			MongoFileEntity mongoFileEntity = files.get(0);
			if (mongoFileEntity != null) {
				titleNameArray = getExcelExportTitle(mongoFileEntity.getInputstream());
			}
		} else {
			StringBuilder tempStr = new StringBuilder();
			Set<ColumnDefinition> columnDefinitions = viewDefinitionBean.getColumnDefinitions();
			for (ColumnDefinition columnDefinition : columnDefinitions) {
				String titleName = columnDefinition.getOtherName();
				tempStr.append(";").append(titleName);
			}
			titleNameArray = tempStr.toString().replaceFirst(";", "").split(";");
		}
		File file = generateXmlFile(dataList, titleNameArray);
		return file;
	}

	public File generateXmlFile(List<String[]> dataList, String[] titleArray) {

		// 第一步，创建一个webbook，对应一个Excel文件
		HSSFWorkbook wb = new HSSFWorkbook();
		// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
		HSSFSheet sheet = wb.createSheet("Sheet1");
		// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
		HSSFRow row = sheet.createRow((int) 0);
		// 第四步，创建单元格，并设置值表头 设置表头居中
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式

		HSSFCell cell = row.createCell((short) 0);
		for (int i = 0; i < titleArray.length; i++) {
			cell.setCellValue(titleArray[i]);
			cell.setCellStyle(style);
			cell = row.createCell((short) ((short) i + 1));
		}

		// 第五步，写入实体数据 实际应用中这些数据从数据库得到，

		for (int i = 0; i < dataList.size(); i++) {
			row = sheet.createRow((int) i + 1);
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
		try {
			FileOutputStream fout = new FileOutputStream("E:/temp.xls");
			wb.write(fout);
			fout.close();
			return new File("E:/temp.xls");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	/**
	 * 
	 * 根据文件流获取excel导出模版的标题
	 * 
	 * @param is
	 * @return
	 * @throws Exception
	 */
	public String[] getExcelExportTitle(InputStream is) {

		//解析excel放入List
		HSSFWorkbook hssfWorkbook = null;
		try {
			hssfWorkbook = new HSSFWorkbook(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
		//第一个工作表
		HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(0);
		// 获取第一行row，即标题行
		HSSFRow hssfRow = hssfSheet.getRow(0);
		StringBuilder tempStr = new StringBuilder();
		for (int cellNum = 0; cellNum <= hssfRow.getLastCellNum(); cellNum++) {
			HSSFCell hssfCell = hssfRow.getCell(cellNum);
			if (hssfCell != null) {
				String titleName = String.valueOf(hssfCell.getStringCellValue());
				if (titleName != null) {
					System.out.print("    " + titleName);
					tempStr.append(";" + titleName);
				}
			}
		}
		String[] titleNames = tempStr.toString().replaceFirst(";", "").split(";");
		return titleNames;
	}

	/** 
	 * 获取所有的导出规则
	 * 
	 * (non-Javadoc)
	 * @see com.wellsoft.pt.basicdata.excelexporttemplate.service.ExcelExportRuleService#getExcelExportRule()
	 */
	@Override
	public List<ExcelExportDefinition> getExcelExportRule() {
		String hql = "from ExcelExportDefinition";
		return excelExportDefinitionDao.find(hql, new HashMap());
	}

	/** 
	 * 如何描述该方法
	 * 
	 * (non-Javadoc)
	 * @see com.wellsoft.pt.basicdata.excelexporttemplate.service.ExcelExportRuleService#getExcelExportDefinition(java.lang.String)
	 */
	@Override
	public ExcelExportDefinition getExcelExportDefinition(String uuid) {
		return excelExportDefinitionDao.get(uuid);
	}
}
