package com.wellsoft.pt.ldx.web.pdm;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.wellsoft.pt.bpm.engine.exception.WorkFlowException;
import com.wellsoft.pt.core.web.BaseController;
import com.wellsoft.pt.dyform.entity.DyFormDefinition;
import com.wellsoft.pt.dyform.facade.DyFormApiFacade;
import com.wellsoft.pt.dyform.service.DyFormDataService;
import com.wellsoft.pt.dyform.support.DyFormData;
import com.wellsoft.pt.dyform.support.SubformDefinition;
import com.wellsoft.pt.ldx.service.pdm.DefaultValueService;
import com.wellsoft.pt.ldx.util.DateUtils;
import com.wellsoft.pt.ldx.util.StringUtils;
import com.wellsoft.pt.utils.security.SpringSecurityUtils;
import com.wellsoft.pt.workflow.work.bean.WorkBean;
import com.wellsoft.pt.workflow.work.service.WorkService;

@Controller
@Scope("prototype")
@RequestMapping("/versionUp")
public class VersionUpgradeController extends BaseController{
	@Autowired
	private DefaultValueService dvService;
	@Autowired
	private DyFormApiFacade dyformApiFacade;
	@Autowired
	private WorkService workService;
	/**
	 * 灯具项目申请数据升版
	 * @param model
	 * @param bh
	 * @param bb
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/uv_djxmsqdsplc", method = RequestMethod.GET)
	public String UV_DJXMSQDSPLC(Model model,@RequestParam(value = "bh")String bh,@RequestParam(value = "bb")String bb){
		String workFlowId = "0944a0f4-7125-483d-97e8-e2cef9187604";//流程id
		String mainId = "e1959f17-33d2-4a66-b034-057e20423bbd";//灯具主表id
		String tsdChileId_1 = "52aec00f-c5ad-4b71-ba96-d7807c742343";//筒射灯从表1ID
		String tsdChileId_2 = "92dbd612-ff55-42da-835a-704a92da65e1";//筒射灯从表2ID
		String mbdChileId_1 = "22b102fa-22a3-4ec7-b4dc-ac062a6142d4";//面板灯从表1ID
		String mbdChileId_2 = "e7d857c9-33da-4447-8ced-eaaabb544bd2";//面板灯从表2ID
		String xddChileId_1 = "951547e4-e914-4f01-b141-4916371c1b90";//吸顶灯从表1ID
		String xddChileId_2 = "0911af0f-2e7e-40b0-a60b-331effe744aa";//吸顶灯从表2ID
		
		String findMainSql = "select t.uuid,t.cpdl,t.bb,t.xm from (select uuid,cpdl,bb,xm from UF_LEEDARSON_DJXMSQDFC where bh='"+bh+"' order by rq desc)" +
				" t where rownum=1";
		
		List mainlist = dvService.queryBySql(findMainSql);
		Object[] object = (Object[]) mainlist.get(0);
		String olduuid = object[0].toString();
		String cpdl = object[1].toString();
		String bbStr =  object[2].toString();
		String cjr = object[3].toString();
		
		String userName = SpringSecurityUtils.getCurrentUserName();
		if(!"系统管理员".equals(userName)&&!cjr.equals(userName))return "不是该数据创建人或管理员，无法对数据升版！";
		
		if(bbStr.equals(bb)){
			String interviewUuid = dyformApiFacade.copyFormData(mainId, olduuid, mainId);
			//修改版本
			Double newBB = Double.parseDouble(bbStr)+0.1;
			 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String nowDate = sdf.format(new Date());
			int a = dvService.update("UF_LEEDARSON_DJXMSQDFC", "bb='"+String.format("%.1f", newBB)+"',zt='00',xm='"+userName
					+"',rq=to_date('"+nowDate+"','yyyy-mm-dd hh24:mi:ss')","uuid='"+interviewUuid+"'");
			
		    List chileUUidList_1 = new ArrayList();
		    List chileUUidList_2 = new ArrayList();
			//获取不同从表数据
			if(cpdl.indexOf("1")>=0){//筒射灯
				//copySubFormData(关联的主表formuuid,关联主表的datauuid,本次引用的主表formuuid,本次引用的主表的datauuid,从表formuuid,从表数据的查询条件,)
				chileUUidList_1 = dyformApiFacade.copySubFormData(mainId, olduuid, mainId, interviewUuid
						, tsdChileId_1, " 1=1", null);
				chileUUidList_2 = dyformApiFacade.copySubFormData(mainId, olduuid, mainId, interviewUuid
						, tsdChileId_2, " 1=1", null);
			}
			if(cpdl.indexOf("2")>=0){//吸顶灯
				
				chileUUidList_1 = dyformApiFacade.copySubFormData(mainId, olduuid, mainId, interviewUuid
						, xddChileId_1, " 1=1", null);
				chileUUidList_2 = dyformApiFacade.copySubFormData(mainId, olduuid, mainId, interviewUuid
						, xddChileId_2, " 1=1", null);
			}
			if(cpdl.indexOf("3")>=0){//面板灯
				
				chileUUidList_1 = dyformApiFacade.copySubFormData(mainId, olduuid, mainId, interviewUuid
						, mbdChileId_1, " 1=1", null);
				chileUUidList_2 = dyformApiFacade.copySubFormData(mainId, olduuid, mainId, interviewUuid
						, mbdChileId_2, " 1=1", null);
			}
	
			//新建流程实例
			WorkBean localWorkBean = this.workService.newWork(workFlowId);
		    if (StringUtils.isNotBlank(mainId))
		    {
		      localWorkBean.setFormUuid(mainId);
		      if (StringUtils.isNotBlank(interviewUuid))
		        localWorkBean.setDataUuid(interviewUuid);
		    }
		    model.addAttribute(localWorkBean);
		    //修改上一个版本数据状态为归档
//		    dvService.update("UF_LEEDARSON_DJXMSQDFC", "zt='12'", "uuid='"+olduuid+"'");
			return forward("/workflow/work/work_view");
		}else{
			return "该数据非最高版本！";
		}
	}

/**
 * 灯具pdcp评审数据升版
 * @param model
 * @param bh
 * @param bb
 * @return
 */
	@RequestMapping(value = "/uv_djpdcp", method = RequestMethod.GET)
	public String UV_DJPDCPSHBSPLC(Model model,@RequestParam(value = "bh")String bh,@RequestParam(value = "bb")String bb
			,@RequestParam(value = "form_uuid")String form_uuid){
		String workFlowId = "12a8cb2c-23d3-4032-a6a9-9fe869fefbff";//流程id
		String mainId = "afbcc569-d80d-4403-b55b-8b47bf47d53b";//PDCP主表id
		String ChileId = "b373bfbf-2a70-494d-a1d4-682d50c12e85";//PDCP从表ID
		
		String findMainSql = "select t.uuid,t.bb,t.xm from (select uuid,bb,xm from UF_LEEDARSON_DJPDCPSHB where bh='"+bh+"' order by rq desc)" +
		" t where rownum=1";

		List mainlist = dvService.queryBySql(findMainSql);
		Object[] object = (Object[]) mainlist.get(0);
		String olduuid = object[0].toString();
		String bbStr = object[1].toString();
		String cjr = object[2].toString();
		
		String userName = SpringSecurityUtils.getCurrentUserName();
		if(!"系统管理员".equals(userName)&&!cjr.equals(userName))return "不是该数据创建人或管理员，无法对数据升版！";

		if(bbStr.equals(bb)){
			String interviewUuid = dyformApiFacade.copyFormData(mainId, olduuid, mainId);
			//修改版本
			Double newBB = Double.parseDouble(bbStr)+0.1;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String nowDate = sdf.format(new Date());
			int a = dvService.update("UF_LEEDARSON_DJPDCPSHB", "bb='"+String.format("%.1f", newBB)+"',zt='00',xm='"+userName
					+"',rq=to_date('"+nowDate+"','yyyy-mm-dd hh24:mi:ss')","uuid='"+interviewUuid+"'");
			
			dyformApiFacade.copySubFormData(mainId, olduuid, mainId, interviewUuid, ChileId, " 1=1", null);

		  //修改上一个版本数据状态为归档
//		    dvService.update("UF_LEEDARSON_DJPDCPSHB", "zt='12'", "uuid='"+olduuid+"'");
		    
			WorkBean localWorkBean = this.workService.newWork(workFlowId);
		    if (StringUtils.isNotBlank(mainId))
		    {
		      localWorkBean.setFormUuid(mainId);
		      if (StringUtils.isNotBlank(interviewUuid))
		        localWorkBean.setDataUuid(interviewUuid);
		    }
		    model.addAttribute(localWorkBean);
			return forward("/workflow/work/work_view");
		}else{
			return "该数据非最高版本！";
		}
	}
	/**
	 * 灯具可行性报告数据升版
	 * @param model
	 * @param bh
	 * @param bb
	 * @return
	 */
	@RequestMapping(value = "/uv_djkxxfxbg", method = RequestMethod.GET)
	public String UV_DJKXXFXBG(Model model,@RequestParam(value = "bh")String bh,@RequestParam(value = "bb")String bb
			,@RequestParam(value = "form_uuid")String form_uuid){
		String workFlowId = "18ed9ef7-4a91-4144-b862-858b7bfde7db";//流程id
		String mainId = "487c2629-88d0-4470-b82c-bdba84d2dcfb";//主表id
		
		String findMainSql = "select t.uuid,t.bb,t.xm from (select uuid,bb,xm from uf_djcpkxxfxbg where bh='"+bh+"' order by rq desc)" +
		" t where rownum=1";

		List mainlist = dvService.queryBySql(findMainSql);
		Object[] object = (Object[]) mainlist.get(0);
		String olduuid = object[0].toString();
		String bbStr = object[1].toString();
		String cjr = object[2].toString();
		
		String userName = SpringSecurityUtils.getCurrentUserName();
		if(!"系统管理员".equals(userName)&&!cjr.equals(userName))return "不是该数据创建人或管理员，无法对数据升版！";
		
		if(bbStr.equals(bb)){
			String interviewUuid = dyformApiFacade.copyFormData(mainId, olduuid, mainId);
			//修改版本
			Double newBB = Double.parseDouble(bbStr)+0.1;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String nowDate = sdf.format(new Date());
			int a = dvService.update("uf_djcpkxxfxbg", "bb='"+String.format("%.1f", newBB)+"',zt='00',xm='"+userName
					+"',rq=to_date('"+nowDate+"','yyyy-mm-dd hh24:mi:ss')","uuid='"+interviewUuid+"'");
			
			WorkBean localWorkBean = this.workService.newWork(workFlowId);
		    if (StringUtils.isNotBlank(mainId))
		    {
		      localWorkBean.setFormUuid(mainId);
		      if (StringUtils.isNotBlank(interviewUuid))
		        localWorkBean.setDataUuid(interviewUuid);
		    }
		    model.addAttribute(localWorkBean);
		  //修改上一个版本数据状态为归档
//		    dvService.update("uf_djcpkxxfxbg", "zt='12'", "uuid='"+olduuid+"'");
			return forward("/workflow/work/work_view");
		}else{
			return "该数据非最高版本！";
		}
	}
	
/**
 * 工艺评审表审批数据升版
 * @param model
 * @param bh
 * @param bb
 * @return
 */
	@RequestMapping(value = "/uv_epgypsbsplc", method = RequestMethod.GET)
	public String UV_EPGYPSBSPLC(Model model,@RequestParam(value = "bh")String bh,@RequestParam(value = "bb")String bb
			,@RequestParam(value = "form_uuid")String form_uuid){
		String workFlowId = "2e2361ee-6b91-4fdc-9b82-485978527449";//流程id
		String mainId = "63b92838-2312-4063-a3d6-b638fce13719";//主表id
		String subId = "14cead70-038f-4f4a-b0b1-4f833e1ba0d8";//从表id
		
		String findMainSql = "select t.uuid,t.bb,t.xm from (select uuid,bb,xm from uf_leedarson_thdepgypsb where bh='"+bh+"' order by rq desc)" +
		" t where rownum=1";
		
		List mainlist = dvService.queryBySql(findMainSql);
		Object[] object = (Object[]) mainlist.get(0);
		String olduuid = object[0].toString();
		String bbStr = object[1].toString();
		String cjr = object[2].toString();
		
		String userName = SpringSecurityUtils.getCurrentUserName();
		if(!"系统管理员".equals(userName)&&!cjr.equals(userName))return "不是该数据创建人或管理员，无法对数据升版！";
		
		
		if(bbStr.equals(bb)){
			String interviewUuid = dyformApiFacade.copyFormData(mainId, olduuid, mainId);
			//修改版本
			Double newBB = Double.parseDouble(bbStr)+0.1;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String nowDate = sdf.format(new Date());
			int a = dvService.update("uf_leedarson_thdepgypsb", "bb='"+String.format("%.1f", newBB)+"',zt='00',xm='"+userName
					+"',rq=to_date('"+nowDate+"','yyyy-mm-dd hh24:mi:ss')","uuid='"+interviewUuid+"'");
			//copy从表数据
			List list = dyformApiFacade.copySubFormData(mainId, olduuid, mainId, interviewUuid, subId, " 1=1", null);
			System.out.println("ssssssssssssssss："+list.size());
			WorkBean localWorkBean = this.workService.newWork(workFlowId);
		    if (StringUtils.isNotBlank(mainId))
		    {
		      localWorkBean.setFormUuid(mainId);
		      if (StringUtils.isNotBlank(interviewUuid))
		        localWorkBean.setDataUuid(interviewUuid);
		    }
		    model.addAttribute(localWorkBean);
		  //修改上一个版本数据状态为归档
//		    dvService.update("uf_leedarson_thdepgypsb", "zt='12'", "uuid='"+olduuid+"'");
			
			return forward("/workflow/work/work_view");
		}else{
			return "该数据非最高版本！";
		}
	}
	/**
	 * LED替换灯项目申请单数据升版
	 * @param model
	 * @param bh
	 * @param bb
	 * @return
	 */
	@RequestMapping(value = "/uv_ledthdxmsqd", method = RequestMethod.GET)
	public String UV_LEDTHDXMSQD(Model model,@RequestParam(value = "bh")String bh,@RequestParam(value = "bb")String bb
			,@RequestParam(value = "form_uuid")String form_uuid){
		String workFlowId = "1cb4bf4b-c284-4b4a-887a-9d7e8ed5b420";//流程id
		String mainId = "b0a31004-681e-450a-9ce1-419d93247d22";//主表id
		String subId = "573614e4-8ede-4557-9e86-c2b621e98b21";//从表id
		
		String findMainSql = "select t.uuid,t.bb,t.xm from (select uuid,bb,xm from uf_leedarson_ledthdxmrws where bh='"+bh+"' order by rq desc)" +
		" t where rownum=1";
		
		List mainlist = dvService.queryBySql(findMainSql);
		Object[] object = (Object[]) mainlist.get(0);
		String olduuid = object[0].toString();
		String bbStr = object[1].toString();
		String cjr = object[2].toString();
		
		String userName = SpringSecurityUtils.getCurrentUserName();
		if(!"系统管理员".equals(userName)&&!cjr.equals(userName))return "不是该数据创建人或管理员，无法对数据升版！";
		
		if(bbStr.equals(bb)){
			String interviewUuid = dyformApiFacade.copyFormData(mainId, olduuid, mainId);
			//修改版本
			Double newBB = Double.parseDouble(bbStr)+0.1;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String nowDate = sdf.format(new Date());
			int a = dvService.update("uf_leedarson_ledthdxmrws", "bb='"+String.format("%.1f", newBB)+"',zt='00',xm='"+userName
					+"',rq=to_date('"+nowDate+"','yyyy-mm-dd hh24:mi:ss')","uuid='"+interviewUuid+"'");
			//copy从表数据
			dyformApiFacade.copySubFormData(mainId, olduuid, mainId, interviewUuid, subId, " 1=1", null);
			WorkBean localWorkBean = this.workService.newWork(workFlowId);
		    if (StringUtils.isNotBlank(mainId))
		    {
		      localWorkBean.setFormUuid(mainId);
		      if (StringUtils.isNotBlank(interviewUuid))
		        localWorkBean.setDataUuid(interviewUuid);
		    }
		    model.addAttribute(localWorkBean);
		  //修改上一个版本数据状态为归档
//		    dvService.update("uf_leedarson_ledthdxmrws", "zt='12'", "uuid='"+olduuid+"'");
			
			return forward("/workflow/work/work_view");
		}else{
			return "该数据非最高版本！";
		}
	}
	/**
	 * 灯具技术规格书数据升版
	 * @param model
	 * @param bh
	 * @param bb
	 * @return
	 */
	@RequestMapping(value = "/uv_djjsggssplc", method = RequestMethod.GET)
	public String UV_DJJSGGSSPLC(Model model,@RequestParam(value = "bh")String bh,@RequestParam(value = "bb")String bb
			,@RequestParam(value = "form_uuid")String form_uuid){
		String workFlowId = "b925d462-38e9-466f-8df1-a4277c47c15f";//流程id
		String mainId = form_uuid;
//			"e2cff892-1847-4e25-8754-f0e43797c19e";//主表id
//		String subId = "bfa8dc17-b68b-454a-8121-75e9f7be6e3b";//从表id1
//		subId += ";"+"5081ebeb-629d-482f-ae66-3fd99687dc74";//从表id2
//		subId += ";"+"2ceb872d-0c55-40e3-924d-a1e3e5365665";//从表id3
//		subId += ";"+"aac087b5-294a-44b8-bb70-8007983bd119";//从表id4
		
		Map<String , Object> map = upVersionFormData(workFlowId, mainId, bh, bb);
		
		String userName = SpringSecurityUtils.getCurrentUserName();
		if(!"系统管理员".equals(userName)&&!map.get("cjr").equals(userName))return "不是该数据创建人或管理员，无法对数据升版！";
		
		if(null != map.get("workBean")){
		    model.addAttribute(map.get("workBean"));
			
			return forward("/workflow/work/work_view");
		}else{
			return "该数据非最高版本！";
		}
		
	}
	/**
	 * 灯具设计评审数据升版
	 * @param model
	 * @param bh
	 * @param bb
	 * @return
	 */
	@RequestMapping(value = "/uv_djsjpsbsplc", method = RequestMethod.GET)
	public String UV_DJSJPSBSPLC(Model model,@RequestParam(value = "bh")String bh,@RequestParam(value = "bb")String bb
			,@RequestParam(value = "form_uuid")String form_uuid){
		String workFlowId = "006c7b7d-8fa4-461b-8b43-4002dec2036d";//流程id
		String mainId = form_uuid;
//			"2808c49b-8c77-4c10-86d5-ef48626187b4";//主表id
//		String subId = "73ab0664-40d5-4000-bbe9-dad2048d4840";//从表id1
//		subId += ";"+"a1b3a969-bfe5-4af8-917a-e664a76ccc1d";//从表id2
//		subId += ";"+"681ee1da-cf5b-4dc7-9121-2909b47069c7";//从表id3
//		subId += ";"+"bd9983ff-49e2-454d-b203-515a17bdf527";//从表id4
//		subId += ";"+"8841ea24-6185-4be4-8011-4344b5070672";//从表id5
		
		Map<String , Object> map = upVersionFormData(workFlowId, mainId, bh, bb);
		
		String userName = SpringSecurityUtils.getCurrentUserName();
		if(!"系统管理员".equals(userName)&&!map.get("cjr").equals(userName))return "不是该数据创建人或管理员，无法对数据升版！";
		
		if(null != map.get("workBean")){
		    model.addAttribute(map.get("workBean"));
			
			return forward("/workflow/work/work_view");
		}else{
			return "该数据非最高版本！";
		}
	}
	
	/**
	 * LED替换灯技术规格书审批流程
	 * @param model
	 * @param bh
	 * @param bb
	 * @param form_uuid
	 * @return
	 */
	@RequestMapping(value = "/uv_ledthdjsggsplc",method = RequestMethod.GET)
	public String UV_LEDTHDJSGGSPLC(Model model,@RequestParam(value="bh")String bh,@RequestParam(value="bb")String bb,
            @RequestParam(value="form_uuid")String form_uuid){
		String workFlowId = "011e0a2b-00c4-499a-b6ab-a152452af35a";//流程id
		String mainId = form_uuid;
		
		Map<String , Object> map = upVersionFormData(workFlowId, mainId, bh, bb);
		
		String userName = SpringSecurityUtils.getCurrentUserName();
		if(!"系统管理员".equals(userName)&&!map.get("cjr").equals(userName))return "不是该数据创建人或管理员，无法对数据升版！";
		
		if(null != map.get("workBean")){
			model.addAttribute(map.get("workBean"));
		
			return forward("/workflow/work/work_view");
		}else{
			return "该数据非最高版本！";
		}
    }
	
	/**
	 * LED智能灯技术规格书审批流程
	 * @param model
	 * @param bh
	 * @param bb
	 * @param form_uuid
	 * @return
	 */
	@RequestMapping(value = "/uv_ledzndjsggsplc",method = RequestMethod.GET)
	public String UV_LEDZNDJSGGSSPLC(Model model,@RequestParam(value="bh")String bh,@RequestParam(value="bb")String bb,
            @RequestParam(value="form_uuid")String form_uuid){
		String workFlowId = "1ba36b73-8147-4cce-b7e0-693c8906cc9d";//流程id
		String mainId = form_uuid;
		
		Map<String , Object> map = upVersionFormData(workFlowId, mainId, bh, bb);
		
		String userName = SpringSecurityUtils.getCurrentUserName();
		if(!"系统管理员".equals(userName)&&!map.get("cjr").equals(userName))return "不是该数据创建人或管理员，无法对数据升版！";
		
		if(null != map.get("workBean")){
			model.addAttribute(map.get("workBean"));
		
			return forward("/workflow/work/work_view");
		}else{
			return "该数据非最高版本！";
		}
    }
	
	/**
	 * CFL 灯管规格书审批流程
	 * @param model
	 * @param bh
	 * @param bb
	 * @param form_uuid
	 * @return
	 */
	@RequestMapping(value = "/uv_cfldgggdsplc",method = RequestMethod.GET)
	public String UV_CFLDGGGSSPLC(Model model,@RequestParam(value="bh")String bh,@RequestParam(value="bb")String bb,
			                     @RequestParam(value="form_uuid")String form_uuid){
		String workFlowId = "3c872203-ffe9-4125-b4c7-1b22e01fe338";//流程id
		String mainId = form_uuid;
		
		Map<String , Object> map = upVersionFormData(workFlowId, mainId, bh, bb);
		
		String userName = SpringSecurityUtils.getCurrentUserName();
		if(!"系统管理员".equals(userName)&&!map.get("cjr").equals(userName))return "不是该数据创建人或管理员，无法对数据升版！";
		
		if(null != map.get("workBean")){
		    model.addAttribute(map.get("workBean"));
			
			return forward("/workflow/work/work_view");
		}else{
			return "该数据非最高版本！";
		}
	}
	
	public Map<String, Object> upVersionFormData(String workFlowId,String mainId,String bh,String bb){
		Map map = new HashMap();
		DyFormDefinition dyFormDefinition = dyformApiFacade.getFormDefinition(mainId);
		List<SubformDefinition> subList = dyformApiFacade.getSubformDefinitions(mainId);
		
		String findMainSql = "select t.uuid,t.bb,t.xm from (select uuid,bb,xm from "+dyFormDefinition.getName()+" where bh='"+bh+"' order by rq desc)" +
		" t where rownum=1";
		
		List mainlist = dvService.queryBySql(findMainSql);
		Object[] object = (Object[]) mainlist.get(0);
		String olduuid = object[0].toString();
		String bbStr = object[1].toString();
		String cjr = object[2].toString();
		map.put("cjr", cjr);
		
		if(bbStr.equals(bb)){
			String interviewUuid = dyformApiFacade.copyFormData(mainId, olduuid, mainId);
			//修改版本
			Double newBB = Double.parseDouble(bbStr)+0.1;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String nowDate = sdf.format(new Date());
			int a = dvService.update(dyFormDefinition.getName(), "bb='"+String.format("%.1f", newBB)+"',zt='00',xm='"+SpringSecurityUtils.getCurrentUserName()
					+"',rq=to_date('"+nowDate+"','yyyy-mm-dd hh24:mi:ss')","uuid='"+interviewUuid+"'");
			//copy从表数据
			for(SubformDefinition sub : subList){
				dyformApiFacade.copySubFormData(mainId, olduuid, mainId, interviewUuid, sub.getFormUuid(), " 1=1", null);
			}
			WorkBean localWorkBean = this.workService.newWork(workFlowId);
		    if (StringUtils.isNotBlank(mainId))
		    {
		      localWorkBean.setFormUuid(mainId);
		      if (StringUtils.isNotBlank(interviewUuid))
		        localWorkBean.setDataUuid(interviewUuid);
		    }
			map.put("workBean", localWorkBean);
		}
	    return map;
	}
	/**
	 * 删除数据
	 * @param formuuid
	 * @param uuid
	 * @param res
	 * @throws IOException
	 */
	@RequestMapping(value = "/dropTest", method = RequestMethod.POST)
	public void dropAllFormData(@RequestParam(value = "form_uuid")String formuuid,@RequestParam(value = "uuid")String uuid,
			                    HttpServletResponse res) throws IOException{
		DyFormDefinition dyFormDefinition = dyformApiFacade.getFormDefinition(formuuid);
		
		System.out.println("主表名：" + dyFormDefinition.getName());
		
		List<SubformDefinition> subList = dyformApiFacade.getSubformDefinitions(formuuid);
		
		System.out.println("从表数：" + subList.size());
		
		//将uuid转为数组型
		String[] data_uuid = uuid.split(";");
		int a = 0,b = 0,c=0;
		
		for(SubformDefinition sub : subList){

			String delRlId = "";
			String delSubId = "";
			for(int i=0;i<data_uuid.length;i++){
				StringBuffer sql_rl = new StringBuffer("select data_uuid from ")
				.append(sub.getName()+"_rl ").append("where mainform_data_uuid='")
				.append(data_uuid[i]).append("'");
				List list = dvService.queryBySql(sql_rl.toString());
				//先删除从表及关联表数据
				for(int j=0;j<list.size();j++){				
					delSubId += ("'"+list.get(j)+"'");
					if(j < list.size()-1)delSubId += ",";			
				}	
				delRlId += ("'"+data_uuid[i]+"'");
				if(i < data_uuid.length-1)delRlId += ",";
			}
			a = dvService.delete(sub.getName(), "uuid in ("+("" != delSubId?delSubId:"''")+")");
			b = dvService.delete(sub.getName()+"_rl ", "mainform_data_uuid in ("+("" != delRlId?delRlId:"''")+")");
		}
		
		//再删除主表数据
//		if(b > 0 && subList.size() != b){
//			res.getWriter().print("数据删除出错！");
//		}else {
			String delId = "";
			for(int k=0;k<data_uuid.length;k++){
				delId += ("'"+data_uuid[k]+"'");
				if(k < data_uuid.length-1)delId += ",";
			}
			c = dvService.delete(dyFormDefinition.getName(), "uuid in ("+delId+")");
			if(c == data_uuid.length){
				res.getWriter().print("删除成功！");
			}else{
				res.getWriter().print(dyFormDefinition.getName()+"主数据删除出错！");
			}
//		}
					
	}
	
}
