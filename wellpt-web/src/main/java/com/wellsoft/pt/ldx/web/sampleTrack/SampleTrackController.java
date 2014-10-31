package com.wellsoft.pt.ldx.web.sampleTrack;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.wellsoft.pt.core.web.BaseController;
import com.wellsoft.pt.ldx.model.sampleTrack.SampleTrack;
import com.wellsoft.pt.ldx.model.sampleTrack.SampleTrackSearckParam;
import com.wellsoft.pt.ldx.service.sampleTrack.SampleTrackService;
import com.wellsoft.pt.ldx.util.DateUtils;
import com.wellsoft.pt.ldx.util.StringUtils;

@Controller
@Scope("prototype")
@RequestMapping("/sampleTrack")
public class SampleTrackController extends BaseController{
	@Autowired
	private SampleTrackService sampleTrackService;
	/**
	 * 样品费用结算
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/sampleFeeSettle/init")
	public ModelAndView initSample() throws Exception {
		ModelAndView orderContext = new ModelAndView();
		orderContext.setViewName("/sampleTrack/sampleFeeSettle");
		List<Object[]> list = sampleTrackService.findSampleFeeSettleListByPage("部门名称", "2014", "我司", "1");
		orderContext.addObject("groupCondition", "部门名称");
		for(int j =0;j<list.size();j++){
			Object[] object = (Object[]) list.get(j);
			for(int i=0;i<object.length;i++){
				if(null==object[i])object[i]=0;
			}
		}
		orderContext.addObject("list", list);
		return orderContext;
	}
	/**
	 * 样品费用结算查询
	 * @param groupCondition
	 * @param year
	 * @param sampleCharge1
	 * @param productMode1
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/sampleFeeSettle/search", method = RequestMethod.POST)
	public void search(@RequestParam(value = "groupCondition")String groupCondition,@RequestParam(value = "year")String year
			,@RequestParam(value = "sampleCharge1")String sampleCharge1,@RequestParam(value = "productMode1")String productMode1,
			HttpServletRequest request, HttpServletResponse response) throws IOException{
		
		List list = sampleTrackService.findSampleFeeSettleListByPage(groupCondition, year, sampleCharge1, productMode1);
		for(int j =0;j<list.size();j++){
			Object[] object = (Object[]) list.get(j);
			for(int i=0;i<object.length;i++){
				if(null==object[i])object[i]=0;
			}
		}
		JSONArray json = new JSONArray().fromObject(list);
		response.getWriter().print(json.toString());
	}
	/**
	 * 样品跟踪
	 * @param sample
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sampleTrack")
	public ModelAndView searchSampleTrack(@ModelAttribute("sample") SampleTrackSearckParam sample
			, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView orderContext = new ModelAndView();
		orderContext.setViewName("/sampleTrack/sampleTrack");
		Map map = sampleTrackService.findSample(sample);
		orderContext.addObject("sample", map.get("sample"));
		
		List resultList = (List) map.get("list");
		if(resultList!=null&&resultList.size()>0){
			Object[] objects = (Object[])resultList.get(0);	
			orderContext.addObject("dep", String.valueOf(objects[0]!=null?objects[0]:"0"));
			orderContext.addObject("yep", String.valueOf(objects[1]!=null?objects[1]:"0"));
			orderContext.addObject("dzz", String.valueOf(objects[2]!=null?objects[2]:"0"));
			orderContext.addObject("sumStockNumber", String.valueOf(objects[3]!=null?objects[3]:"0"));
			orderContext.addObject("ywc", String.valueOf(objects[4]!=null?objects[4]:"0"));
			orderContext.addObject("yqx", String.valueOf(objects[5]!=null?objects[5]:"0"));
			orderContext.addObject("sampleCostTemp", String.valueOf(objects[6]!=null?objects[6]:"0"));
			orderContext.addObject("PIMoneyUSDTemp", String.valueOf(objects[7]!=null?objects[7]:"0"));
			orderContext.addObject("PIMoneyRMBTemp", String.valueOf(objects[8]!=null?objects[8]:"0"));
		}
		return orderContext;
	}
	/**
	 * 显示样品跟踪维护页面
	 * @param lineItemId
	 * @return
	 */
	@RequestMapping(value = "/sampleTrack/update")
	public ModelAndView updateSampleTrack(@RequestParam(value = "lineItemId")String lineItemId){
		List list= sampleTrackService.findByEntity(lineItemId);
		ModelAndView orderContext = new ModelAndView();
		orderContext.setViewName("/sampleTrack/sampleTrackUpdate");
		orderContext.addObject("sample", list.get(0));
		return orderContext;
	}
	/**
	 * 样品跟踪修改保存
	 * @param sampleTrack
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/sampleTrack/save")
	public void saveSampleTrack(@ModelAttribute("sampleTrack")SampleTrack sampleTrack
			, HttpServletRequest request, HttpServletResponse response){
		StringBuffer sql = new StringBuffer("update USERFORM_SAMPLE_TRACK set ")
			.append(StringUtils.isNotEmpty(sampleTrack.getProductid())?"productid='"+sampleTrack.getProductid()+"'":"")
			.append(StringUtils.isNotEmpty(sampleTrack.getProductname())?",productname='"+sampleTrack.getProductname()+"'":"")
			.append(StringUtils.isNotEmpty(sampleTrack.getCompletedate())?",completedate=to_date('"+sampleTrack.getCompletedate()+"','yyyy-mm-dd hh24:mi:ss')":"")
			.append(StringUtils.isNotEmpty(sampleTrack.getTechoutgroupreplydate())?",techoutgroupreplydate=to_date('"+sampleTrack.getTechoutgroupreplydate()+"','yyyy-mm-dd hh24:mi:ss')":"")
			.append(StringUtils.isNotEmpty(sampleTrack.getSampledeliveryperiod())?",sampledeliveryperiod=to_date('"+sampleTrack.getSampledeliveryperiod()+"','yyyy-mm-dd hh24:mi:ss')":"")
			.append(StringUtils.isNotEmpty(sampleTrack.getCustomerid())?",customerid='"+sampleTrack.getCustomerid()+"'":"")
			.append(StringUtils.isNotEmpty(sampleTrack.getApplydept())?",applydept='"+sampleTrack.getApplydept()+"'":"")
			.append(StringUtils.isNotEmpty(sampleTrack.getMemo())?",memo='"+sampleTrack.getMemo()+"'":"")
			.append(StringUtils.isNotEmpty(sampleTrack.getLedsamplememo())?",ledsamplememo='"+sampleTrack.getLedsamplememo()+"'":"")
			.append(StringUtils.isNotEmpty(sampleTrack.getUnitprice())?",unitprice='"+sampleTrack.getUnitprice()+"'":"")
			.append(StringUtils.isNotEmpty(sampleTrack.getBomcost())?",bomcost='"+sampleTrack.getBomcost()+"'":"")
			.append(StringUtils.isNotEmpty(sampleTrack.getLabourcost())?",labourcost='"+sampleTrack.getLabourcost()+"'":"")
			.append(StringUtils.isNotEmpty(sampleTrack.getPackcost())?",packcost='"+sampleTrack.getPackcost()+"'":"")
			.append(StringUtils.isNotEmpty(sampleTrack.getPimoney())?",pimoney='"+sampleTrack.getPimoney()+"'":"")
			.append(StringUtils.isNotEmpty(sampleTrack.getPimoneyunit())?",pimoneyunit='"+sampleTrack.getPimoneyunit()+"'":"")
			.append(" where lineitemid="+sampleTrack.getLineitemid());
		
		sampleTrackService.sampleUpdate(sql.toString());
	}
	/**
	 * 导出报表
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sampleTrack/export", method = RequestMethod.POST)
	public void exportExcel(@ModelAttribute("sample") SampleTrackSearckParam sample,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		OutputStream os = response.getOutputStream();

		response.reset();
		String fname = new SimpleDateFormat("yyyyMMdd_HHmmssSSS")
				.format(new Date()) + "_sample.xlsx";
		response.setHeader("Content-disposition",
				"attachment; filename=" + fname);
		response.setContentType("application/msexcel");

		String path = request.getSession().getServletContext()
				.getRealPath("/");
		String pathname = path+"\\pt\\sampleTrack\\default_sample1.xlsx";
		/*if("1".equals(power)||"2".equals(power)){//查看全部内容
			 pathname = path.concat(FILE_SEPARATOR).concat("excel")
			.concat(FILE_SEPARATOR).concat("default_sample1.xlsx");
		}else{//power="3"//查看部分内容
			 pathname = path.concat(FILE_SEPARATOR).concat("excel")
				.concat(FILE_SEPARATOR).concat("default_sample2.xlsx");
		}*/
		File inputFile = new File(pathname);
		

		List<SampleTrack> sampleTracks = sampleTrackService.exportSampleTrack(sample);//样品跟踪列表

		
		FileInputStream in = new FileInputStream(inputFile);

		XSSFWorkbook workbook = new XSSFWorkbook(in);
		XSSFSheet sheet = workbook.getSheetAt(0);
		XSSFRow row = null;
		XSSFCell cell = null;

		int columns = sheet.getRow((short) 0).getPhysicalNumberOfCells();

		// 创建样式数组
		XSSFCellStyle styleArray[] = new XSSFCellStyle[columns];

		// 一次性创建所有列的样式放在数组里
		for (int s = 0; s < columns; s++) {
			// 得到数组实例
			styleArray[s] = workbook.createCellStyle();
		}

		XSSFCellStyle style = null;
		for (int i = 0; i < sampleTracks.size(); i++) {

			int j = 0;
			int k = 0;
			SampleTrack model = sampleTracks.get(i);
			row = sheet.createRow(1 + i);		
			//样品单状态
			cell = row.createCell((short) j++);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			style = getStyle(styleArray[k++]);
			style.setLocked(false);
			cell.setCellStyle(style);
			Integer sampleOrderStatus = model.getSampleorderstatus();
			if(null==sampleOrderStatus){
				cell.setCellValue("");
			}else if(1==sampleOrderStatus){
				cell.setCellValue("待制作");
			}else if(2==sampleOrderStatus){
				cell.setCellValue("待邮寄");
			}else if(3==sampleOrderStatus){
				cell.setCellValue("已完成");
			}else if(4==sampleOrderStatus){
				cell.setCellValue("已取消");
			}					
			//项目状态
			cell = row.createCell((short) j++);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			style = getStyle(styleArray[k++]);
			style.setLocked(false);
			cell.setCellStyle(style);
			Integer projectStatus = model.getProjectstatus();
			if(null==projectStatus){
				cell.setCellValue("");
			}else if(0==projectStatus){
				cell.setCellValue("待EP定型");
			}else if(1==projectStatus){
				cell.setCellValue("已EP定型");
			}			
			//样品类型
			cell = row.createCell((short) j++);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			style = getStyle(styleArray[k++]);
			style.setLocked(false);
			cell.setCellStyle(style);
			String sampelType = model.getSampleorderid().substring(0,1);
			if("8".equals(sampelType)){
				cell.setCellValue("CFL");
			}else if("9".equals(sampelType)){
				cell.setCellValue("LED");
			}else if("7".equals(sampelType)){
				cell.setCellValue("LF");
			}
			//样品单号
			cell = row.createCell((short) j++);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			style = getStyle(styleArray[k++]);
			style.setLocked(false);
			cell.setCellStyle(style);
			cell.setCellValue(Double.valueOf(model.getSampleorderid()));//excel中输出为常规类型
			//样品型号已改为：行项目
			cell = row.createCell((short) j++);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			style = getStyle(styleArray[k++]);
			style.setLocked(false);
			cell.setCellStyle(style);
			cell.setCellValue(Double.valueOf(model.getSampleid().substring(0, 8)));//excel中输出为常规类型
			//单支样品序号
			cell = row.createCell((short) j++);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			style = getStyle(styleArray[k++]);
			style.setLocked(false);
			cell.setCellStyle(style);
			cell.setCellValue(Double.valueOf(model.getSampleid()));	//excel中输出为常规类型
			//产品ID
			cell = row.createCell((short) j++);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			style = getStyle(styleArray[k++]);
			style.setLocked(false);
			cell.setCellStyle(style);
			cell.setCellValue(model.getProductid());
			//产品名称
			cell = row.createCell((short) j++);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			style = getStyle(styleArray[k++]);
			style.setLocked(false);
			cell.setCellStyle(style);
			cell.setCellValue(model.getProductname());
			//样品数量
			cell = row.createCell((short) j++);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			style = getStyle(styleArray[k++]);
			style.setLocked(false);
			cell.setCellStyle(style);
			cell.setCellValue(Double.valueOf(model.getSamplenum()));//excel中输出为常规类型
			//申请部门
			cell = row.createCell((short) j++);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			style = getStyle(styleArray[k++]);
			style.setLocked(false);
			cell.setCellStyle(style);
			cell.setCellValue(model.getApplydept());
			//申请人员
			cell = row.createCell((short) j++);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			style = getStyle(styleArray[k++]);
			style.setLocked(false);
			cell.setCellStyle(style);
			cell.setCellValue(model.getApplyuser());
			//跟单人员
			cell = row.createCell((short) j++);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			style = getStyle(styleArray[k++]);
			style.setLocked(false);
			cell.setCellStyle(style);
			cell.setCellValue(model.getDocumentaryuser());
			//客户代码
			cell = row.createCell((short) j++);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			style = getStyle(styleArray[k++]);
			style.setLocked(false);
			cell.setCellStyle(style);
			cell.setCellValue(model.getCustomerid());			
			//客户潜力级别
			cell = row.createCell((short) j++);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			style = getStyle(styleArray[k++]);
			style.setLocked(false);
			cell.setCellStyle(style);
			cell.setCellValue(model.getCustomerpotentialrank());
			//是否报关
			cell = row.createCell((short) j++);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			style = getStyle(styleArray[k++]);
			style.setLocked(false);
			cell.setCellStyle(style);
			Integer isCustoms = model.getIscustoms();
			if(null==isCustoms){
				cell.setCellValue("");
			}else if(0==isCustoms){
				cell.setCellValue("否");
			}else if(1==isCustoms){
				cell.setCellValue("是");
			}
			// 是否开票
			cell = row.createCell((short) j++);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			style = getStyle(styleArray[k++]);
			style.setLocked(false);
			cell.setCellStyle(style);
			Integer isInvoice = model.getIsinvoice();
			if(null==isInvoice){
				cell.setCellValue("");
			}else if(0==isInvoice){
				cell.setCellValue("否");
			}else if(1==isInvoice){
				cell.setCellValue("是");
			}
			//样品费承担方
			cell = row.createCell((short) j++);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			style = getStyle(styleArray[k++]);
			style.setLocked(false);
			cell.setCellStyle(style);
			cell.setCellValue(model.getSamplecharge());
			//运费承担方
			cell = row.createCell((short) j++);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			style = getStyle(styleArray[k++]);
			style.setLocked(false);
			cell.setCellStyle(style);
			cell.setCellValue(model.getFreight());
			//模具费承担方
			cell = row.createCell((short) j++);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			style = getStyle(styleArray[k++]);
			style.setLocked(false);
			cell.setCellStyle(style);
			cell.setCellValue(model.getMouldcharge());
			//付款方式
			cell = row.createCell((short) j++);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			style = getStyle(styleArray[k++]);
			style.setLocked(false);
			cell.setCellStyle(style);
			cell.setCellValue(model.getPaymode());
			//送样目的
			cell = row.createCell((short) j++);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			style = getStyle(styleArray[k++]);
			style.setLocked(false);
			cell.setCellStyle(style);
			cell.setCellValue(model.getSampleaim());
			//技术要求
			cell = row.createCell((short) j++);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			style = getStyle(styleArray[k++]);
			style.setLocked(false);
			cell.setCellStyle(style);
			cell.setCellValue(model.getTechrequire());		
			//测试类型
			cell = row.createCell((short) j++);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			style = getStyle(styleArray[k++]);
			style.setLocked(false);
			cell.setCellStyle(style);
			cell.setCellValue(model.getTestmode());		
			//测试标准
			cell = row.createCell((short) j++);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			style = getStyle(styleArray[k++]);
			style.setLocked(false);
			cell.setCellStyle(style);
			cell.setCellValue(model.getTeststandard());
			//生产方式
			cell = row.createCell((short) j++);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			style = getStyle(styleArray[k++]);
			style.setLocked(false);
			cell.setCellStyle(style);
			Integer productMode = model.getProductmode();
			if(null==productMode){
				cell.setCellValue("");
			}else if(1==productMode){
				cell.setCellValue("自制");
			}else if(2==productMode){
				cell.setCellValue("外购");
			}else if(3==productMode){
				cell.setCellValue("外购&自制");
			}	
			//包装方式
			cell = row.createCell((short) j++);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			style = getStyle(styleArray[k++]);
			style.setLocked(false);
			cell.setCellStyle(style);
			cell.setCellValue(model.getPackmode());
			//标签要求
			cell = row.createCell((short) j++);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			style = getStyle(styleArray[k++]);
			style.setLocked(false);
			cell.setCellStyle(style);
			cell.setCellValue(model.getLabelrequire());			
			//申请日期
			cell = row.createCell((short) j++);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			style = getStyle(styleArray[k++]);
			style.setLocked(false);
			cell.setCellStyle(style);
			cell.setCellValue(DateUtils.formatDate(model.getApplydate(), "yyyy-MM-dd"));
			//RSM审批日期
			cell = row.createCell((short) j++);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			style = getStyle(styleArray[k++]);
			style.setLocked(false);
			cell.setCellStyle(style);
			cell.setCellValue(DateUtils.formatDate(model.getRsmauditingdate(), "yyyy-MM-dd"));
			//PM审批日期
			cell = row.createCell((short) j++);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			style = getStyle(styleArray[k++]);
			style.setLocked(false);
			cell.setCellStyle(style);
			cell.setCellValue(DateUtils.formatDate(model.getPmauditingdate(), "yyyy-MM-dd"));
			//CS审批日期(new)客户服务部样品管理组审批日期(old)
			cell = row.createCell((short) j++);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			style = getStyle(styleArray[k++]);
			style.setLocked(false);
			cell.setCellStyle(style);
			cell.setCellValue(DateUtils.formatDate(model.getCustomergroupauditingdate(), "yyyy-MM-dd"));
			//样品课及外购组回复日期(new)技术中心/外购组回复日期(old)
			cell = row.createCell((short) j++);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			style = getStyle(styleArray[k++]);
			style.setLocked(false);
			cell.setCellStyle(style);
			cell.setCellValue(model.getTechoutgroupreplydate());
			//AE确认交期
			cell = row.createCell((short) j++);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			style = getStyle(styleArray[k++]);
			style.setLocked(false);
			cell.setCellStyle(style);
			cell.setCellValue(DateUtils.formatDate(model.getAesuredelivery(), "yyyy-MM-dd"));
			//相关人员确认日期(new)AE确认日期(old)
			cell = row.createCell((short) j++);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			style = getStyle(styleArray[k++]);
			style.setLocked(false);
			cell.setCellStyle(style);
			cell.setCellValue(DateUtils.formatDate(model.getAesuredate(), "yyyy-MM-dd"));
			//签发Week
			cell = row.createCell((short) j++);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			style = getStyle(styleArray[k++]);
			style.setLocked(false);
			cell.setCellStyle(style);
			cell.setCellValue(model.getSignweek());	
			//目标交期
			cell = row.createCell((short) j++);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			style = getStyle(styleArray[k++]);
			style.setLocked(false);
			cell.setCellStyle(style);
			cell.setCellValue(DateUtils.formatDate(model.getAimdate(), "yyyy-MM-dd"));
			//样品预计交期
			cell = row.createCell((short) j++);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			style = getStyle(styleArray[k++]);
			style.setLocked(false);
			cell.setCellStyle(style);
			cell.setCellValue(DateUtils.formatDate(model.getSamplepredictddate(), "yyyy-MM-dd"));		
			//预计交期Week
			cell = row.createCell((short) j++);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			style = getStyle(styleArray[k++]);
			style.setLocked(false);
			cell.setCellStyle(style);
			cell.setCellValue(model.getSamplepredictddateweek());				
			//样品交货绝期
			cell = row.createCell((short) j++);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			style = getStyle(styleArray[k++]);
			style.setLocked(false);
			cell.setCellStyle(style);
			cell.setCellValue(model.getSampledeliveryperiod());				
			//完工日期
			cell = row.createCell((short) j++);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			style = getStyle(styleArray[k++]);
			style.setLocked(false);
			cell.setCellStyle(style); 
			cell.setCellValue(model.getCompletedate());
			//生产时间
			cell = row.createCell((short) j++);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			style = getStyle(styleArray[k++]);
			style.setLocked(false);
			cell.setCellStyle(style);
			if(StringUtils.isNotBlank(model.getProdcosttime())){
				cell.setCellValue(model.getProdcosttime()+"小时");
			}
			//生产状态
			cell = row.createCell((short) j++);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			style = getStyle(styleArray[k++]);
			style.setLocked(false);
			cell.setCellStyle(style);
			Integer productStatus = model.getProdstatus();
			if(null==productStatus){
				cell.setCellValue("");
			}else if(1==productStatus){
				cell.setCellValue("物料调拨");
			}else if(2==productStatus){
				cell.setCellValue("物料采购");
			}else if(3==productStatus){
				cell.setCellValue("生产中");
			}else if(4==productStatus){
				cell.setCellValue("已完成");
			}
			//生产状态说明
			cell = row.createCell((short) j++);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			style = getStyle(styleArray[k++]);
			style.setLocked(false);
			cell.setCellStyle(style);
			cell.setCellValue(model.getProdstatusmemo());
			//领样Week
			cell = row.createCell((short) j++);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			style = getStyle(styleArray[k++]);
			style.setLocked(false);
			cell.setCellStyle(style);
			cell.setCellValue(model.getWeek());
			//预计送样目的地
			cell = row.createCell((short) j++);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			style = getStyle(styleArray[k++]);
			style.setLocked(false);
			cell.setCellStyle(style);
			cell.setCellValue(model.getPresampledestination());
			//实际送样目的地
			cell = row.createCell((short) j++);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			style = getStyle(styleArray[k++]);
			style.setLocked(false);
			cell.setCellStyle(style);
			cell.setCellValue(model.getSampledestination());
			//送样时间
			cell = row.createCell((short) j++);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			style = getStyle(styleArray[k++]);
			style.setLocked(false);
			cell.setCellStyle(style);
			cell.setCellValue(DateUtils.formatDate(model.getSampledate(), "yyyy-MM-dd"));	
			//快递单号
			cell = row.createCell((short) j++);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			style = getStyle(styleArray[k++]);
			style.setLocked(false);
			cell.setCellStyle(style);
			cell.setCellValue(model.getExpressagenum());	
			//品保判定结果（1OK;2NG;3放行;4特采出货）
			cell = row.createCell((short) j++);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			style = getStyle(styleArray[k++]);
			style.setLocked(false);
			cell.setCellStyle(style);
			Integer qcCheckResult = model.getQccheckresult();
			if(null == qcCheckResult){
				cell.setCellValue("");
			}else if(1==qcCheckResult){
				cell.setCellValue("OK");
			}else if(2==qcCheckResult){
				cell.setCellValue("NG");
			}else if(3==qcCheckResult){
				cell.setCellValue("放行");
			}else if(4==qcCheckResult){
				cell.setCellValue("特采出货");
			}
			//品保二次判定结果（1OK;2NG;3放行;4特采出货）
			cell = row.createCell((short) j++);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			style = getStyle(styleArray[k++]);
			style.setLocked(false);
			cell.setCellStyle(style);
			Integer qcSecondResult = model.getQcsecondresult();
			if(null == qcSecondResult){
				cell.setCellValue("");
			}else if(1==qcSecondResult){
				cell.setCellValue("OK");
			}else if(2==qcSecondResult){
				cell.setCellValue("NG");
			}else if(3==qcSecondResult){
				cell.setCellValue("放行");
			}else if(4==qcSecondResult){
				cell.setCellValue("特采出货");
			}
			//检验人员
			cell = row.createCell((short) j++);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			style = getStyle(styleArray[k++]);
			style.setLocked(false);
			cell.setCellStyle(style);
			cell.setCellValue(model.getQcname());
			//品保检验项目
			cell = row.createCell((short) j++);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			style = getStyle(styleArray[k++]);
			style.setLocked(false);
			cell.setCellStyle(style);
			cell.setCellValue(model.getQccheckitem());
			//品保检验描述
			cell = row.createCell((short) j++);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			style = getStyle(styleArray[k++]);
			style.setLocked(false);
			cell.setCellStyle(style);
			cell.setCellValue(model.getQccheckmemo());
			//品质异常原因
			cell = row.createCell((short) j++);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			style = getStyle(styleArray[k++]);
			style.setLocked(false);
			cell.setCellStyle(style);
			cell.setCellValue(model.getQcexceptcause());
			//品保检验时间
			cell = row.createCell((short) j++);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			style = getStyle(styleArray[k++]);
			style.setLocked(false);
			cell.setCellStyle(style);
			if(StringUtils.isNotBlank(model.getQccosttime())){
				cell.setCellValue(model.getQccosttime()+"小时");
			}
			//客户反馈结果
			cell = row.createCell((short) j++);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			style = getStyle(styleArray[k++]);
			style.setLocked(false);
			cell.setCellStyle(style);
			cell.setCellValue(model.getCustomerresult());
			//品质异常责任部门反馈
			cell = row.createCell((short) j++);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			style = getStyle(styleArray[k++]);
			style.setLocked(false);
			cell.setCellStyle(style);
			cell.setCellValue(model.getQcexceptreply());
			//交期异常责任部门反馈
			cell = row.createCell((short) j++);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			style = getStyle(styleArray[k++]);
			style.setLocked(false);
			cell.setCellStyle(style);
			cell.setCellValue(model.getProdexceptreply());
			//库存数量(Integer)
			cell = row.createCell((short) j++);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			style = getStyle(styleArray[k++]);
			style.setLocked(false);
			cell.setCellStyle(style);
			cell.setCellValue(model.getStocknubmer());//0,1 //excel中输出为常规类型
//			cell.setCellValue((model.getStockNubmer()==null?"":String.valueOf(model.getStockNubmer())));
			//行项目备注说明
			cell = row.createCell((short) j++);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			style = getStyle(styleArray[k++]);
			style.setLocked(false);
			cell.setCellStyle(style);
			cell.setCellValue(model.getMemo());
			//抬头备注信息
			cell = row.createCell((short) j++);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			style = getStyle(styleArray[k++]);
			style.setLocked(false);
			cell.setCellStyle(style);
			cell.setCellValue(model.getTopmemo());		
			//领样说明
			cell = row.createCell((short) j++);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			style = getStyle(styleArray[k++]);
			style.setLocked(false);
			cell.setCellStyle(style);
			cell.setCellValue(model.getLedsamplememo());
			//单价(RMB)
			cell = row.createCell((short) j++);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			style = getStyle(styleArray[k++]);
			style.setLocked(false);
			cell.setCellStyle(style);
			if(StringUtils.isNotBlank(model.getUnitprice())){
				cell.setCellValue(Double.valueOf(model.getUnitprice()));//excel中输出为常规类型
			}	
			
			
//			if("1".equals(power)||"2".equals(power)){//查看全部内容				
			//材料成本(RMB)
			cell = row.createCell((short) j++);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			style = getStyle(styleArray[k++]);
			style.setLocked(false);
			cell.setCellStyle(style);
			cell.setCellValue(Double.valueOf(model.getBomcost()));//excel中输出为常规类型
			//人工成本(RMB)
			cell = row.createCell((short) j++);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			style = getStyle(styleArray[k++]);
			style.setLocked(false);
			cell.setCellStyle(style);
			cell.setCellValue(Double.valueOf(model.getLabourcost()));//excel中输出为常规类型
			//包装成本(RMB)
			cell = row.createCell((short) j++);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			style = getStyle(styleArray[k++]);
			style.setLocked(false);
			cell.setCellStyle(style);
			cell.setCellValue(Double.valueOf(model.getPackcost()));//excel中输出为常规类型
			//样品成本(RMB)
			cell = row.createCell((short) j++);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			style = getStyle(styleArray[k++]);
			style.setLocked(false);
			cell.setCellStyle(style);
			cell.setCellValue(Double.valueOf(model.getSamplecost()!=null?model.getSamplecost():""));//excel中输出为常规类型
			//PI金额
			cell = row.createCell((short) j++);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			style = getStyle(styleArray[k++]);
			style.setLocked(false);
			cell.setCellStyle(style);
			cell.setCellValue(Double.valueOf(StringUtils.isNotEmpty(model.getPimoney())?model.getPimoney():""));//excel中输出为常规类型
			//PI金额单位
			cell = row.createCell((short) j++);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			style = getStyle(styleArray[k++]);
			style.setLocked(false);
			cell.setCellStyle(style);
			cell.setCellValue(model.getPimoneyunit());
//			}
			//距离交期天数(Integer)
			cell = row.createCell((short) j++);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			style = getStyle(styleArray[k++]);
			style.setLocked(false);
			cell.setCellStyle(style);
			String day = "";
			if(null != model.getSamplepredictddate()){
				 day = daysOfTwo(new Date(), model.getSamplepredictddate())+"";//要重新计算
			}
			cell.setCellValue(day);		
		}

		workbook.write(os);
		response.flushBuffer();
		os.close();
	}
	public static int daysOfTwo(Date fDate, Date oDate) {
	       Calendar aCalendar = Calendar.getInstance();
	       aCalendar.setTime(fDate);
	       int day1 = aCalendar.get(Calendar.DAY_OF_YEAR);
	       aCalendar.setTime(oDate);
	       int day2 = aCalendar.get(Calendar.DAY_OF_YEAR);
	       return  day2-day1;
	    }
	public XSSFCellStyle getStyle(XSSFCellStyle style) {
//		// 设置底边框;
//		style.setBorderBottom(XSSFCellStyle.BORDER_THIN);
//		// 设置底边框颜色;
//		style.setBottomBorderColor(HSSFColor.LIGHT_CORNFLOWER_BLUE.index);
//		// 设置左边框;
//		style.setBorderLeft(XSSFCellStyle.BORDER_THIN);
//		// 设置左边框颜色;
//		style.setLeftBorderColor(HSSFColor.LIGHT_CORNFLOWER_BLUE.index);
//		// 设置右边框;
//		style.setBorderRight(XSSFCellStyle.BORDER_THIN);
//		// 设置右边框颜色;
//		style.setRightBorderColor(HSSFColor.LIGHT_CORNFLOWER_BLUE.index);
//		// 设置顶边框;
//		style.setBorderTop(XSSFCellStyle.BORDER_THIN);
//		// 设置顶边框颜色;
//		style.setTopBorderColor(HSSFColor.LIGHT_CORNFLOWER_BLUE.index);
//		// 在样式用应用设置的字体;
//		// 设置自动换行;
//		style.setWrapText(false);
//		// 设置水平对齐的样式为居中对齐;
//		style.setAlignment(XSSFCellStyle.ALIGN_RIGHT);
//		// 设置垂直对齐的样式为居中对齐;
//		style.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
		return style;
	}
}
