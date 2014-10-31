package com.wellsoft.pt.basicdata.exceltemplate.service.impl;

import java.beans.PropertyDescriptor;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wellsoft.pt.basicdata.exceltemplate.bean.ExcelColumnDefinitionBean;
import com.wellsoft.pt.basicdata.exceltemplate.bean.ExcelImportRuleBean;
import com.wellsoft.pt.basicdata.exceltemplate.dao.ExcelColumnDefinitionDao;
import com.wellsoft.pt.basicdata.exceltemplate.dao.ExcelImportRuleDao;
import com.wellsoft.pt.basicdata.exceltemplate.entity.ExcelColumnDefinition;
import com.wellsoft.pt.basicdata.exceltemplate.entity.ExcelImportRule;
import com.wellsoft.pt.basicdata.exceltemplate.service.ExcelImportRuleService;
import com.wellsoft.pt.common.component.jqgrid.JqGridQueryData;
import com.wellsoft.pt.common.component.jqgrid.JqGridQueryInfo;
import com.wellsoft.pt.core.dao.Page;
import com.wellsoft.pt.dyform.facade.DyFormApiFacade;
import com.wellsoft.pt.dyform.support.FieldDefinition;
import com.wellsoft.pt.utils.bean.BeanUtils;

/**
 * 
 * Description: Excel导入规则服务层实现类
 *  
 * @author zhouyq
 * @date 2013-4-22
 * @version 1.0
 * 
 * <pre>
 * 修改记录:
 * 修改后版本	修改人		修改日期			修改内容
 * 2013-4-24.1	zhouyq		2013-4-22		Create
 * </pre>
 *
 */
@Service
@Transactional
public class ExcelImportRuleServiceImpl implements ExcelImportRuleService {
	@Autowired
	private ExcelImportRuleDao excelImportRuleDao;
	@Autowired
	private ExcelColumnDefinitionDao excelColumnDefinitionDao;
	@Autowired
	private DyFormApiFacade dyFormApiFacade;

	/**
	 * 
	 * 获得所有Excel导入规则
	 * 
	 * @return
	 */
	@Override
	public List<ExcelImportRule> findAll() {
		return excelImportRuleDao.getAll();
	}

	/**
	 * 通过uuid获取Excel导入规则
	 * 
	 * @param id
	 * @return
	 */
	@Override
	public ExcelImportRule get(String uuid) {
		return excelImportRuleDao.get(uuid);
	}

	/** 
	 * 保存Excel导入规则bean
	 */
	@Override
	public void saveBean(ExcelImportRuleBean bean) {
		//1.保存父表的数据
		ExcelImportRule excelImportRule = new ExcelImportRule();
		// 保存新ExcelImportRule 设置id值
		if (StringUtils.isBlank(bean.getUuid())) {
			bean.setUuid(null);

			//先保存父表,然后再保存子表
			BeanUtils.copyProperties(bean, excelImportRule);
			this.excelImportRuleDao.save(excelImportRule);

			Set<ExcelColumnDefinitionBean> changeColumnDefinitions = bean.getChangeColumnDefinitions();
			for (ExcelColumnDefinitionBean changeColumnDefinition : changeColumnDefinitions) {
				ExcelColumnDefinition columnDefinition = new ExcelColumnDefinition();
				columnDefinition.setColumnNum(changeColumnDefinition.getColumnNum());
				columnDefinition.setAttributeName(changeColumnDefinition.getAttributeName());
				//多方保存时得将实体类也保存而不是bean
				columnDefinition.setExcelImportRule(excelImportRule);
				excelColumnDefinitionDao.save(columnDefinition);
			}
		} else {
			excelImportRule = this.excelImportRuleDao.get(bean.getUuid());
			BeanUtils.copyProperties(bean, excelImportRule);
			this.excelImportRuleDao.save(excelImportRule);
			Set<ExcelColumnDefinitionBean> changeColumnDefinitions = bean.getChangeColumnDefinitions();
			for (ExcelColumnDefinitionBean changeColumnDefinition : changeColumnDefinitions) {
				ExcelColumnDefinition columnDefinition = new ExcelColumnDefinition();
				if (changeColumnDefinition.getUuid() != null && !changeColumnDefinition.getUuid().equals("")) {
					columnDefinition = excelColumnDefinitionDao.get(changeColumnDefinition.getUuid());
				} else {
					columnDefinition.setExcelImportRule(excelImportRule);
				}
				columnDefinition.setColumnNum(changeColumnDefinition.getColumnNum());
				columnDefinition.setAttributeName(changeColumnDefinition.getAttributeName());
				excelColumnDefinitionDao.save(columnDefinition);
			}
			Set<ExcelColumnDefinitionBean> deletedExcelRows = bean.getDeletedExcelRows();
			for (ExcelColumnDefinitionBean changeColumnDefinition : deletedExcelRows) {
				ExcelColumnDefinition columnDefinition = new ExcelColumnDefinition();
				columnDefinition = excelColumnDefinitionDao.get(changeColumnDefinition.getUuid());
				excelColumnDefinitionDao.delete(columnDefinition);
			}
		}
		/*//保存子表修改的数据
		Set<ExcelColumnDefinitionBean> changeColumnDefinitions = bean.getChangeColumnDefinitions();
		for (ExcelColumnDefinitionBean changeColumnDefinition : changeColumnDefinitions) {
			ExcelColumnDefinition excelColumnDefinition = excelColumnDefinitionDao
					.get(changeColumnDefinition.getUuid());
			BeanUtils.copyProperties(changeColumnDefinition, excelColumnDefinition);
			this.excelColumnDefinitionDao.save(excelColumnDefinition);
		}*/
	}

	/** 
	 * 删除Excel导入规则
	 */
	@Override
	public void remove(String uuid) {
		this.excelImportRuleDao.delete(uuid);
	}

	@Override
	public void removeAll(String[] uuids) {
		for (int i = 0; i < uuids.length; i++) {
			this.excelImportRuleDao.delete(uuids[i]);
		}
	}

	/** 
	 * JQgridExcel导入规则列表查询
	 */
	@Override
	public JqGridQueryData query(JqGridQueryInfo queryInfo) {
		Page<ExcelImportRule> pageData = new Page<ExcelImportRule>();
		pageData.setPageNo(queryInfo.getPage());
		pageData.setPageSize(queryInfo.getRows());
		this.excelImportRuleDao.findPage(pageData);
		List<ExcelImportRule> excelImportRules = pageData.getResult();
		List<ExcelImportRule> jqUsers = new ArrayList<ExcelImportRule>();
		for (ExcelImportRule excelImportRule : excelImportRules) {
			ExcelImportRule jqExcelImportRule = new ExcelImportRule();
			BeanUtils.copyProperties(excelImportRule, jqExcelImportRule);
			jqUsers.add(jqExcelImportRule);
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
	 * 
	 * 通过uuid获取Excel导入规则VO对象
	 * 
	 * (non-Javadoc)
	 * @see com.wellsoft.pt.basicdata.exceltemplate.service.ExcelImportRuleService#getBeanByUuid(java.lang.String)
	 */
	@Override
	public ExcelImportRuleBean getBeanByUuid(String uuid) {
		ExcelImportRule excelImportRule = this.excelImportRuleDao.get(uuid);
		ExcelImportRuleBean bean = new ExcelImportRuleBean();
		BeanUtils.copyProperties(excelImportRule, bean);
		bean.setExcelColumnDefinitions(BeanUtils.convertCollection(excelImportRule.getExcelColumnDefinitions(),
				ExcelColumnDefinition.class));
		return bean;
	}

	/**
	 * 
	 * 列对应列表查询
	 * 
	 * (non-Javadoc)
	 * @see com.wellsoft.pt.basicdata.exceltemplate.service.ExcelImportRuleService#queryExcelList(com.wellsoft.pt.common.component.jqgrid.JqGridQueryInfo)
	 */
	@Override
	public JqGridQueryData queryExcelList(JqGridQueryInfo queryInfo) {
		Page<ExcelColumnDefinition> pageData = new Page<ExcelColumnDefinition>();
		pageData.setPageNo(queryInfo.getPage());
		pageData.setPageSize(queryInfo.getRows());
		this.excelColumnDefinitionDao.findPage(pageData);
		List<ExcelColumnDefinition> excelColumnDefinitions = pageData.getResult();
		List<ExcelColumnDefinition> jqUsers = new ArrayList<ExcelColumnDefinition>();
		for (ExcelColumnDefinition excelColumnDefinition : excelColumnDefinitions) {
			ExcelColumnDefinition jqExcelColumnDefinition = new ExcelColumnDefinition();
			BeanUtils.copyProperties(excelColumnDefinition, jqExcelColumnDefinition);
			jqUsers.add(jqExcelColumnDefinition);
		}
		JqGridQueryData queryData = new JqGridQueryData();
		queryData.setCurrentPage(queryInfo.getPage());
		queryData.setDataList(jqUsers);
		queryData.setRepeatitems(false);
		queryData.setTotalPages(pageData.getTotalPages());
		queryData.setTotalRows(pageData.getTotalCount());
		return queryData;

	}

	@Override
	public ExcelImportRule getExcelImportRuleObj(String id) {
		return excelImportRuleDao.findUniqueBy("id", id);
	}

	@Override
	public List getListFromExcel(String id, InputStream is) throws Exception {
		return getListFromExcel(id, is, "");
	}

	/**
	 * 
	 * 解析excel
	 * 
	 * @param code
	 * @param is
	 * @return
	 */
	@Override
	public List getListFromExcel(String id, InputStream is, String sheetName) throws Exception {

		ExcelImportRule excelImportRule = excelImportRuleDao.findUniqueBy("id", id);
		//解析excel放入List
		HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);
		//第一个工作表
		HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(0);

		//20140819 add by zky 如果excel页签名不为空，则取指定的页签.
		if (!StringUtils.isEmpty(sheetName)) {
			hssfSheet = hssfWorkbook.getSheet(sheetName);
			if (hssfSheet == null) {
				return new ArrayList();
			}
		}

		if (excelImportRule.getType().equals("formdefinition")) {
			//列的集合
			List<FieldDefinition> fieldDefinitionList = dyFormApiFacade.getFormDefinition(excelImportRule.getEntity())
					.getFieldDefintions();
			//entityMap构造动态表单的map
			Map entityMap = new HashMap();
			for (FieldDefinition f : fieldDefinitionList) {
				if (f.getDbDataType().equals("2")) {
					entityMap.put(f.getName(), f.getDbDataType());
				} else {
					entityMap.put(f.getName(), null);
				}
			}
			List list = new ArrayList();
			Set<ExcelColumnDefinition> aList = excelImportRule.getExcelColumnDefinitions();
			int hang = excelImportRule.getStartRow();
			Map tmap = new HashMap();
			for (Iterator<ExcelColumnDefinition> iter = aList.iterator(); iter.hasNext();) {
				ExcelColumnDefinition e = iter.next();
				tmap.put(e.getColumnNum(), e.getAttributeName());
			}

			// 循环行Row   
			for (int rowNum = 0; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
				if (rowNum + 1 >= hang) {
					Map map = new HashMap();
					map.putAll(entityMap);
					HSSFRow hssfRow = hssfSheet.getRow(rowNum);
					if (hssfRow == null) {
						continue;
					}
					// 循环列Cell   
					for (int cellNum = 0; cellNum <= hssfRow.getLastCellNum(); cellNum++) {
						HSSFCell hssfCell = hssfRow.getCell(cellNum);
						if (hssfCell == null) {
							if (map.get(tmap.get(cellNum + 1)) != null && map.get(tmap.get(cellNum + 1)).equals("2")) {
								map.put(tmap.get(cellNum + 1), null);
							}
							continue;
						}
						if (tmap.get(cellNum + 1) != null) {
							if (map.get(tmap.get(cellNum + 1)) != null && map.get(tmap.get(cellNum + 1)).equals("2")) {
								map.put(tmap.get(cellNum + 1), hssfCell.getDateCellValue());
							} else {
								map.put(tmap.get(cellNum + 1), getValue(hssfCell));
							}
						}
						System.out.print("    " + getValue(hssfCell));
					}
					list.add(map);
					System.out.println();
				}
			}
			return list;
		} else if (excelImportRule.getType().equals("entity")) {
			JqGridQueryData queryData = new JqGridQueryData();
			queryData.setRepeatitems(false);
			String entity = excelImportRule.getEntity();
			BeanWrapper wrapperB = new BeanWrapperImpl(Class.forName(entity).newInstance());
			PropertyDescriptor[] propertyDescriptor = wrapperB.getPropertyDescriptors();
			//开始行
			int hang = excelImportRule.getStartRow();
			List list = new ArrayList();
			Set<ExcelColumnDefinition> aList = excelImportRule.getExcelColumnDefinitions();

			Map tmap = new HashMap();
			for (Iterator<ExcelColumnDefinition> iter = aList.iterator(); iter.hasNext();) {
				ExcelColumnDefinition e = iter.next();
				tmap.put(e.getColumnNum(), e.getAttributeName());
			}
			// 循环行Row   
			for (int rowNum = 0; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
				if (rowNum + 1 >= hang) {
					BeanWrapper wrapper = new BeanWrapperImpl(Class.forName(entity).newInstance());
					HSSFRow hssfRow = hssfSheet.getRow(rowNum);
					if (hssfRow == null) {
						continue;
					}
					// 循环列Cell   
					for (int cellNum = 0; cellNum <= hssfRow.getLastCellNum(); cellNum++) {
						HSSFCell hssfCell = hssfRow.getCell(cellNum);
						if (hssfCell == null) {
							continue;
						}
						//保证定义的列存在
						if (tmap.get(cellNum + 1) != null) {
							String str = tmap.get(cellNum + 1).toString();
							//做相应的数据类型转换
							String type = wrapper.getPropertyDescriptor(str).getPropertyType().toString();
							if (type.equals("class java.lang.Integer")) {
								wrapper.setPropertyValue(str, hssfCell.getNumericCellValue());
							} else if (type.equals("class java.lang.Double")) {
								wrapper.setPropertyValue(str, hssfCell.getNumericCellValue());
							} else if (type.equals("class java.util.Date")) {
								Date date = hssfCell.getDateCellValue();
								wrapper.setPropertyValue(str, date);
							} else {
								wrapper.setPropertyValue(str, getValue(hssfCell));
							}
							System.out.print("    " + getValue(hssfCell));
						}
					}
					list.add(wrapper.getWrappedInstance());
					System.out.println();
				}
			}
			queryData.setDataList(list);
			return list;
		}
		return null;
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

	@Override
	public ExcelImportRule getNameByFileUuid(String fileUuid) {
		return excelImportRuleDao.findUniqueBy("fileUuid", fileUuid);
	}

	@Override
	public List<ExcelImportRule> getExcelImportRule() {
		String hql = "from ExcelImportRule";
		return excelImportRuleDao.find(hql, new HashMap());
	}

	@Override
	public ExcelImportRule getExcelImportRule(String uuid) {
		return excelImportRuleDao.get(uuid);
	}
}
