/*
 * @(#)2012-11-15 V1.0
 * 
 * Copyright 2012 WELL-SOFT, Inc. All rights reserved.
 */
package com.wellsoft.pt.basicdata.datadic;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;

import com.wellsoft.pt.basicdata.datadict.entity.DataDictionary;
import com.wellsoft.pt.basicdata.datadict.service.DataDictionaryService;
import com.wellsoft.pt.basicdata.facade.BasicDataApiFacade;
import com.wellsoft.pt.core.test.spring.SpringTxTestCase;
import com.wellsoft.pt.security.acl.service.AclService;

/**
 * Description: 如何描述该类
 *  
 * @author zhulh
 * @date 2012-11-15
 * @version 1.0
 * 
 * <pre>
 * 修改记录:
 * 修改后版本		修改人		修改日期			修改内容
 * 2012-11-15.1	zhulh		2012-11-15		Create
 * </pre>
 *
 */
@ContextConfiguration(locations = { "/applicationContext-core.xml" })
public class DataDictionaryServiceTest extends SpringTxTestCase {

	@Autowired
	private DataDictionaryService dataDictionaryService;

	@Autowired
	public AclService aclService;

	@Autowired
	private BasicDataApiFacade basicDataApiFacade;

	@Before
	public void testBefore() {
		Authentication authRequest = new UsernamePasswordAuthenticationToken("zhulh", "0",
				AuthorityUtils.createAuthorityList("ROLE_IGNORED"));
		SecurityContextHolder.getContext().setAuthentication(authRequest);
	}

	@After
	public void testAfter() {
		SecurityContextHolder.clearContext();
	}

	@Test
	@Rollback(false)
	public void testSaveByParent() {
		DataDictionary dataDictionary1 = new DataDictionary();
		dataDictionary1.setName("未出车");
		dataDictionary1.setCode("01");
		dataDictionary1.setType("010101");

		DataDictionary dataDictionary2 = new DataDictionary();
		dataDictionary2.setName("已出车");
		dataDictionary2.setCode("02");
		dataDictionary2.setType("010102");

		DataDictionary parent = dataDictionaryService.get("402880b53b890e43013b890e51320000");

		dataDictionary1.setParent(parent);
		dataDictionaryService.saveAcl(dataDictionary1);

		dataDictionary2.setParent(parent);
		dataDictionaryService.saveAcl(dataDictionary2);
	}

	@Test
	@Rollback(false)
	public void testSave() {
		DataDictionary dataDictionary = new DataDictionary();
		dataDictionary.setName("正常|1");
		dataDictionary.setCode("VV002");
		dataDictionary.setType("0101");

		dataDictionaryService.saveAcl(dataDictionary);
	}

	@Test
	@Rollback(false)
	public void testRemove() {
		dataDictionaryService.removeByPk("402880b53b023c23013b023c2b640000");
	}

	@Test
	@Rollback(false)
	public void testGetById() {
		DataDictionary dataDictionary = dataDictionaryService.getByType("010101");
		System.out.println(dataDictionary);

		dataDictionary = dataDictionaryService.getByType("0101");
		List<DataDictionary> list = dataDictionaryService.getDataDictionariesByType("0101");
		System.out.println(list);
	}

	@Test
	@Rollback(false)
	public void testGetByCategory() {
		DataDictionary dataDictionary = dataDictionaryService.getByType("车辆管理\\车辆状态");
		System.out.println(dataDictionary);

		List<DataDictionary> list = dataDictionaryService.getDataDictionariesByType("车辆管理\\车辆状态");
		System.out.println(list);
	}

	@Test
	@Rollback(false)
	public void testChangeOwner() {
		DataDictionary dataDictionary = new DataDictionary();
		dataDictionary.setUuid("402880b53b0312ca013b0312dafa0000");
		aclService.changeOwner(DataDictionary.class, dataDictionary.getUuid(), "O_USER");
	}

	@Test
	@Rollback(false)
	public void testGetByType() {
		//		List<DataDictionary> list = dataDictionaryService.getDataDictionariesByType("车辆管理\\车辆状态");
		//		System.out.println(list);
		List<DataDictionary> list = basicDataApiFacade.getDataDictionariesByType("PT");
		System.out.println(list);
	}
}
