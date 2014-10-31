package com.wellsoft.pt.ldx.web.lmsWeb;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.wellsoft.pt.core.web.BaseController;
import com.wellsoft.pt.ldx.model.lmsModel.CostCenter;
import com.wellsoft.pt.ldx.model.lmsModel.LabFile;
import com.wellsoft.pt.ldx.model.lmsModel.TstType;
import com.wellsoft.pt.ldx.service.lmsService.LMSService;
import com.wellsoft.pt.ldx.util.StringUtils;

@Controller
@Scope("prototype")
@RequestMapping("/lms")
public class LmsController extends BaseController{
	@Autowired
	private LMSService lmsService;
	/**
	 *固定资产/低值易耗品报废申请/不同主体固资/计量器具/委外校准申请/资产调拨
	 * @param labfileNo
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/search/labfile", method = RequestMethod.POST)
	public void searchLabfile(@RequestParam(value = "labfileNo")String labfileNo
			, HttpServletRequest request, HttpServletResponse response){
		
		List<LabFile> list = lmsService.findLabFileById(labfileNo);
		List<CostCenter> costList = new ArrayList<CostCenter>();
		if(null!=list&&list.size()>0){
			for(LabFile labfile:list){
				if(StringUtils.isNotEmpty(labfile.getLabfileCostno())){
					costList = lmsService.findCostCenter(labfile.getLabfileCostno());
				}
				String supName = "";
				if(StringUtils.isNotEmpty(labfile.getLabfileSup())){
					List suplist = lmsService.getSupfile(labfile.getLabfileSup());
					supName = (String)suplist.get(0);
				}
				labfile.setCostName(costList.size()>0?costList.get(0).getCostcenter_name():"");
				labfile.setSupName(supName);
			}
		}
		JSONArray json = new JSONArray().fromObject(list);
		try {
			response.getWriter().print(json.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	@RequestMapping(value = "/search/test")
	public String test(){
		/*Calendar cal = Calendar.getInstance();
		cal.setTime(new Date(System.currentTimeMillis()));
		int week = cal.get(Calendar.DAY_OF_WEEK);
		List<LabFile> list = lmsService.findLabfileByHavedate(getNextMonday(9-week), getNextSunday(9-week));
		System.out.println("testestsestsetst: "+list.size());*/
		List suplist = lmsService.queryForFinishedList("C1413623");
		System.out.println("查询条数:"+suplist.get(0));
		return forward("/pt/ldx/sso/changePassword");
	}
	/**
	 * 计量器具领用
	 * @param labfileNo
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/search/meter", method = RequestMethod.POST)
	public void searchMeter(@RequestParam(value = "labfileNo")String labfileNo
			, HttpServletRequest request, HttpServletResponse response){
		List<LabFile> list = lmsService.findMeterAccess(labfileNo);
		JSONArray json = new JSONArray().fromObject(list);
		try {
			response.getWriter().print(json.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	 public String getNextMonday(int count) {  
         
	        Calendar strDate = Calendar.getInstance();         
	        strDate.add(strDate.DATE,count);  
	          
	        //System.out.println(strDate.getTime());  
	        GregorianCalendar currentDate = new GregorianCalendar();  
	        currentDate.set(strDate.get(Calendar.YEAR), strDate.get(Calendar.MONTH),strDate.get(Calendar.DATE));  
	        Date monday = currentDate.getTime();  
	        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");  
	        String preMonday = df.format(monday);  
	        return preMonday;  
	    }  
	 public String getNextSunday(int count)   
	    {    
	        GregorianCalendar currentDate = new GregorianCalendar();  
	        Calendar strDate = Calendar.getInstance();         
	        strDate.add(strDate.DATE,count);  
//	        System.out.println("=="+strDate.getTime());  
	        currentDate.set(strDate.get(Calendar.YEAR), strDate.get(Calendar.MONTH),strDate.get(Calendar.DATE));  
	        currentDate.add(GregorianCalendar.DATE, 6);  
	        Date monday = currentDate.getTime();  
	        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");  
	        String preMonday = df.format(monday);  
	        return preMonday;  
	    } 
	/**
	 * 校准计划通知单查询
	 * @param labfile_havedate
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/search/jzjh", method = RequestMethod.POST)
	public void searchJZJH(HttpServletRequest request, HttpServletResponse response){
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date(System.currentTimeMillis()));
		int week = cal.get(Calendar.DAY_OF_WEEK);
		
		List<LabFile> list = lmsService.findLabfileByHavedate(getNextMonday(9-week),getNextSunday(9-week));
		List<CostCenter> costList = new ArrayList<CostCenter>();
		if(null!=list){
			for(LabFile labfile:list){
				if(StringUtils.isNotEmpty(labfile.getLabfileCostno())){
					costList = lmsService.findCostCenter(labfile.getLabfileCostno());
				}
				String supName = "";
				if(StringUtils.isNotEmpty(labfile.getLabfileSup())){
					List suplist = lmsService.getSupfile(labfile.getLabfileSup());
					supName = (String)suplist.get(0);
				}
				labfile.setCostName(costList.size()>0?costList.get(0).getCostcenter_name():"");
				labfile.setSupName(supName);
			}
		}
		JSONArray json = new JSONArray().fromObject(list);
		try {
			response.getWriter().print(json.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 获取测试类别、外部委托测试
	 */
	@RequestMapping(value = "/search/tstType", method = RequestMethod.POST)
	public void searchTstType(@RequestParam(value = "tsttype_name")String tsttype_name
			, HttpServletRequest request, HttpServletResponse response){
		List<TstType> tstList = lmsService.findTSTType(tsttype_name,"");
		JSONArray json = new JSONArray().fromObject(tstList);
		try {
			response.getWriter().print(json.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 获取成本中心数据
	 * @param costNO
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/search/costCenter", method = RequestMethod.POST)
	public void searchCostcenter(@RequestParam(value = "costNO")String costNO
			, HttpServletRequest request, HttpServletResponse response){
		List<CostCenter> list = lmsService.findCostCenter(costNO);
		JSONArray json = new JSONArray().fromObject(list);
		try {
			response.getWriter().print(json.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 计量器具送检（只做插入操作）
	 * @param labfile
	 */
	@RequestMapping(value = "/search/insert", method = RequestMethod.POST)
	public void insertLabfile(@ModelAttribute("labfile")LabFile labfile){
		lmsService.insertMeter(labfile);
	}
	/**
	 * 测试异常通知查询
	 * @param tstorder_no
	 * @param tstorder2_clsfrom
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/search/error", method = RequestMethod.POST)
	public void searchTest(@RequestParam(value = "tstorder_no")String tstorder_no,@RequestParam(value = "tstorder2_clsfrom")String tstorder2_clsfrom
			, HttpServletRequest request, HttpServletResponse response){
		List list = lmsService.findTstorderByTest(tstorder_no, tstorder2_clsfrom);
		
		JSONArray json = new JSONArray().fromObject(list);
		try {
			response.getWriter().print(json.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * EP&PP测试进度通知,维护一
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/search/eppp1", method = RequestMethod.POST)
	public void searchEPPP1(HttpServletRequest request, HttpServletResponse response){
		List list = lmsService.findTstorderByEpPp_1();
		JSONArray json = new JSONArray().fromObject(list);
		try {
			response.getWriter().print(json.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * EP&PP测试进度通知,维护二
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/search/eppp2", method = RequestMethod.POST)
	public void searchEPPP2(@RequestParam(value = "tstorder_no")String tstorder_no,@RequestParam(value = "tstorder2_clsfrom")String tstorder2_clsfrom
			,HttpServletRequest request, HttpServletResponse response){
		List list = lmsService.findTstorderByEpPp_2(tstorder_no, tstorder2_clsfrom);
		JSONArray json = new JSONArray().fromObject(list);
		try {
			response.getWriter().print(json.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 成品例行试验&RoHS测试&寿命跟踪计划表 
	 */
	@RequestMapping(value = "/search/queryOrderByNO", method = RequestMethod.POST)
	public void searchOrderByNO(@RequestParam(value = "tstorder_no")String tstorder_no
			,HttpServletRequest request, HttpServletResponse response){
		List list = lmsService.queryOrderByNO(tstorder_no);
		if(list.size()!=0){
			Object[] obj = (Object[]) list.get(0);
			String str = obj[5].toString();
			if("1".equals(str)){
				obj[5]="P";
			}else if(StringUtils.isNotEmpty(str)){
				obj[5]="F";
			}
		}
		JSONArray json = new JSONArray().fromObject(list);
		try {
			response.getWriter().print(json.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 成品例行试验&RoHS测试异常一览表 V1.2
	 */
	@RequestMapping(value = "/search/queryOrderByError", method = RequestMethod.POST)
	public void searchOrderByError(@RequestParam(value = "tstorder_no")String tstorder_no
			,HttpServletRequest request, HttpServletResponse response){
		List list = lmsService.queryOrderByError(tstorder_no);
		JSONArray json = new JSONArray().fromObject(list);
		try {
			response.getWriter().print(json.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 根据委托号获取测试项目
	 */
	@RequestMapping(value = "/search/queryTsttypeName", method = RequestMethod.POST)
	public void getTstProject(@RequestParam(value = "tstorder_no")String tstorder_no
			,HttpServletRequest request, HttpServletResponse response){
		List tst4List = lmsService.queryTstorder4ByFromno(tstorder_no);
		String typeId = "(";
		for(int i=0;i<tst4List.size();i++){
			Object obj = (Object) tst4List.get(i);
			typeId += obj.toString();
			if(tst4List.size()-1>i)typeId += ",";
		}
		typeId += ")";
		List<TstType> typeList = lmsService.findTSTTypeMoer("", typeId);
		JSONArray json = new JSONArray().fromObject(typeList);
		try {
			response.getWriter().print(json.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 *  成品例行试验一览表取值接口
	 * @param tstorder_no
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/search/getFinishedList")
	public void getFinishedList(@RequestParam(value = "tstorder_no")String tstorder_no
			,HttpServletRequest request, HttpServletResponse response){
		List list = lmsService.queryForFinishedList(tstorder_no);
		JSONArray json = new JSONArray().fromObject(list);
		try {
			response.getWriter().print(json.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
