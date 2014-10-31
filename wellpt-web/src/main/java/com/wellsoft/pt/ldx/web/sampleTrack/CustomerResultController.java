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
@RequestMapping("/customerResult")
public class CustomerResultController extends BaseController {
	@Autowired
	private SampleTrackService sampleTrackService;
	
	@RequestMapping("/show")
	public ModelAndView showAndUpdate(@RequestParam(value = "lineItemId")String lineItemId){
		ModelAndView orderContext = new ModelAndView();
		orderContext.setViewName("/sampleTrack/customerResultUpdate");
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SampleTrack sampleTrack = new SampleTrack();
		try {
			sampleTrack = sampleTrackService.findEntityById(lineItemId,"客户反馈结果");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		orderContext.addObject("lineItemId", lineItemId);
		orderContext.addObject("sampleDestination", sampleTrack.getSampledestination());
		orderContext.addObject("sampleDate", sampleTrack.getSampledate()!=null?formatter.format(sampleTrack.getSampledate()):"");
		orderContext.addObject("expressageNum", sampleTrack.getExpressagenum());
		orderContext.addObject("customerResult", sampleTrack.getCustomerresult());
		orderContext.addObject("qcExceptReply", sampleTrack.getQcexceptreply());
		orderContext.addObject("prodExceptReply", sampleTrack.getProdexceptreply());
		return orderContext;
	}
}
