package com.wellsoft.pt.dytable.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wellsoft.pt.common.bean.LabelValueBean;
import com.wellsoft.pt.dytable.dao.FieldDefinitionDao;
import com.wellsoft.pt.dytable.entity.FieldDefinition;
import com.wellsoft.pt.dytable.service.FieldDefinitionService;

/**
 * Description: 表单字段定义service实现类
 *  
 * @author jiangmb
 * @date 2012-12-21
 * @version 1.0
 * 
 * <pre>
 * 修改记录:
 * 修改后版本		修改人		修改日期			修改内容
 * 2012-12-21.1	jiangmb		2012-12-21		Create
 * </pre>
 *
 */
@Service
@Transactional
public class FieldDefinitionServiceImpl implements FieldDefinitionService {
	@Autowired
	private FieldDefinitionDao fieldDao;

	@Override
	public FieldDefinition getFieldById(String id) {
		return fieldDao.get(id);
	}

	@Override
	public List<FieldDefinition> getAllField() {
		return null;
	}

	@Override
	public void saveField(FieldDefinition entity) {
		fieldDao.save(entity);
	}

	@Override
	public void deleteField(String id) {
		fieldDao.delete(id);
	}

	@Override
	public List<FieldDefinition> getAllFieldByTable(String entityName) {
		return fieldDao.findBy("entityName", entityName);
	}

	/**
	 * 根据表单UUID查找对应的字段字义信息列表
	 * 
	 * @param uid
	 * @return
	 */
	public List<FieldDefinition> getFieldByForm(String uid) {
		return fieldDao.findBy("formDefinition.uuid", uid);
	}

	/**
	 * 根据指定的表单实体名和版本号查找对应的字段定义信息列表
	 * 
	 * @param entityName
	 * @param version
	 * @return
	 */
	public List<FieldDefinition> getField(String entityName, String version) {
		return fieldDao.getListByForm(entityName, version);
	}

	/** 
	 * 如何描述该方法
	 * 
	 * (non-Javadoc)
	 * @see com.wellsoft.pt.dytable.service.FieldDefinitionService#getFieldLavelValueBean(java.lang.String)
	 */
	@Override
	public List<LabelValueBean> getFieldLavelValueBean(String formUuid) {
		List<FieldDefinition> fieldList = getFieldByForm(formUuid);
		List<LabelValueBean> list = new ArrayList<LabelValueBean>();
		if (null != fieldList && fieldList.size() > 0) {
			for (FieldDefinition field : fieldList) {
				if (field.getDescname() == null || field.getDescname() == "") {
					if (field.getFieldName().equals("body_col")) {
						list.add(new LabelValueBean(field.getUuid(), "正文"));
					} else if (field.getFieldName().equals("signature_")) {
						list.add(new LabelValueBean(field.getUuid(), "签名字段"));
					} else {
						list.add(new LabelValueBean(field.getUuid(), field.getFieldName()));
					}
				} else {
					list.add(new LabelValueBean(field.getUuid(), field.getDescname()));
				}
			}
		}
		return list;
	}

	/** 
	 * 如何描述该方法
	 * 
	 * (non-Javadoc)
	 * @see com.wellsoft.pt.dytable.service.FieldDefinitionService#getFieldNameAndValueLavelValueBean(java.lang.String)
	 */
	@Override
	public List<LabelValueBean> getFieldNameAndValueLavelValueBean(String formUuid) {
		List<FieldDefinition> fieldList = getFieldByForm(formUuid);
		List<LabelValueBean> list = new ArrayList<LabelValueBean>();
		if (null != fieldList && fieldList.size() > 0) {
			for (FieldDefinition field : fieldList) {
				if (field.getDescname() == null || field.getDescname() == "") {
					if (field.getFieldName().equals("body_col")) {
						list.add(new LabelValueBean(field.getFieldName(), "正文"));
					} else if (field.getFieldName().equals("signature_")) {
						list.add(new LabelValueBean(field.getFieldName(), "签名字段"));
					} else {
						list.add(new LabelValueBean(field.getFieldName(), field.getFieldName()));
					}
				} else {
					list.add(new LabelValueBean(field.getFieldName(), field.getDescname()));
				}
			}
		}
		return list;
	}
}
