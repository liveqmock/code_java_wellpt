package com.wellsoft.pt.ldx.web.maindata;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.wellsoft.pt.core.web.BaseController;
import com.wellsoft.pt.ldx.service.purchase.TransferDataService;

@Controller
@Scope("prototype")
@RequestMapping("/transferData")
public class TransferDataController extends BaseController {
	@Resource(name = "copyTemplete")
	private JdbcTemplate copyTemplete;
	@Autowired
	private TransferDataService transferDataService;

	@RequestMapping("/fetchData")
	public String fetchData(@RequestParam(value = "objectUuid") String objectUuid,
			Model model) {
		transferData1();
		transferData2();
		transferData3();
		transferData4();
		transferData5();
		transferData6();
		return forward("/maindata/addBaoGuan");
	}

	@SuppressWarnings("rawtypes")
	private void transferData1() {
		List list = copyTemplete
				.queryForList("select code,name,create_date from c_pstruct");
		Iterator iterator = list.iterator();
		while (iterator.hasNext()) {
			Map mapList = (Map) iterator.next();
			transferDataService.transferData1(mapList);
		}
	}

	@SuppressWarnings("rawtypes")
	private void transferData2() {
		List list = copyTemplete
				.queryForList("select coding,pstructcode,secuenceno,version,create_date from c_coding");
		Iterator iterator = list.iterator();
		while (iterator.hasNext()) {
			Map mapList = (Map) iterator.next();
			transferDataService.transferData2(mapList);
		}
	}

	@SuppressWarnings("rawtypes")
	private void transferData3() {
		List list = copyTemplete
				.queryForList("select code,name,create_date from c_baccount");
		Iterator iterator = list.iterator();
		while (iterator.hasNext()) {
			Map mapList = (Map) iterator.next();
			transferDataService.transferData3(mapList);
		}
	}

	@SuppressWarnings("rawtypes")
	private void transferData4() {
		List list = copyTemplete
				.queryForList("select code,name,create_date from c_bmaterials");
		Iterator iterator = list.iterator();
		while (iterator.hasNext()) {
			Map mapList = (Map) iterator.next();
			transferDataService.transferData4(mapList);
		}
	}

	@SuppressWarnings("rawtypes")
	private void transferData5() {
		List list = copyTemplete
				.queryForList("select coding,baccountcode,bmaterialscode,secuenceno,version,create_date from c_bcoding");
		Iterator iterator = list.iterator();
		while (iterator.hasNext()) {
			Map mapList = (Map) iterator.next();
			transferDataService.transferData5(mapList);
		}
	}

	@SuppressWarnings("rawtypes")
	private void transferData6() {
		List list = copyTemplete
				.queryForList("select coding,baccountcode,bmaterialscode,secuenceno,version,create_date from sys_bcoding where codeType='sujian'");
		Iterator iterator = list.iterator();
		while (iterator.hasNext()) {
			Map mapList = (Map) iterator.next();
			transferDataService.transferData6(mapList);
		}
	}

}
