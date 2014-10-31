package com.wellsoft.pt.ldx.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wellsoft.pt.core.web.BaseController;
import com.wellsoft.pt.file.entity.FmFile;
import com.wellsoft.pt.ldx.service.PackagePageService;

@Controller
@Scope("prototype")
@RequestMapping("/sales")
public class PackagePageController extends BaseController {
	@Autowired
	private PackagePageService packagePageService;

	@RequestMapping("/package/page/preGenerate")
	public String preGenerate(Model model) {
		List<FmFile> customers = packagePageService
				.getFiles("cdedc58a-752f-4dad-b2e1-2b9e690afa4b");
		List<FmFile> materials = packagePageService
				.getFiles("02346d95-b793-46d1-82bd-c3677cf431b8");
		model.addAttribute("customers", customers);
		model.addAttribute("materials", materials);
		return forward("/sales/packagePageNumberGenerate");
	}

}
