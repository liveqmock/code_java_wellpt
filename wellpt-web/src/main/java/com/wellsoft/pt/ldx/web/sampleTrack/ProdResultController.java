package com.wellsoft.pt.ldx.web.sampleTrack;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.wellsoft.pt.core.web.BaseController;
import com.wellsoft.pt.ldx.model.sampleTrack.SampleTrack;
import com.wellsoft.pt.ldx.service.sampleTrack.SampleTrackService;

@Controller
@Scope("prototype")
@RequestMapping("/prodResult")
public class ProdResultController extends BaseController{
	@Autowired
	private SampleTrackService sampleTrackService;
	
	@RequestMapping("/show")
	public ModelAndView showAndUpdate(@RequestParam(value = "lineItemId")String lineItemId){
		ModelAndView orderContext = new ModelAndView();
		orderContext.setViewName("/sampleTrack/prodResultUpdate");
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SampleTrack sampleTrack = new SampleTrack();
		try {
			sampleTrack = sampleTrackService.findEntityById(lineItemId,"样品课反馈结果");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		orderContext.addObject("lineItemId", lineItemId);
		orderContext.addObject("planStart", formatter.format(sampleTrack.getPlanstart()));
		orderContext.addObject("planEnd", formatter.format(sampleTrack.getPlanend()));
		orderContext.addObject("prodStatus", sampleTrack.getProdstatus());
		orderContext.addObject("prodStatusMemo", sampleTrack.getProdstatusmemo());
		orderContext.addObject("ledSampleMemo", sampleTrack.getLedsamplememo());
		orderContext.addObject("unitPrice", sampleTrack.getUnitprice());
		return orderContext;
	}
}
