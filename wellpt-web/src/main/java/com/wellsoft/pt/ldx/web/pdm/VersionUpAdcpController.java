package com.wellsoft.pt.ldx.web.pdm;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.wellsoft.pt.core.web.BaseController;
import com.wellsoft.pt.dyform.entity.DyFormDefinition;
import com.wellsoft.pt.dyform.facade.DyFormApiFacade;
import com.wellsoft.pt.ldx.service.pdm.DefaultValueService;
import com.wellsoft.pt.ldx.util.StringUtils;
import com.wellsoft.pt.workflow.work.bean.WorkBean;
import com.wellsoft.pt.workflow.work.service.WorkService;

/**
 * 
 * @author qiulb
 * @theme  灯具 ADCP 审核表升版操作 
 * version      date        author
 *  1.0        20141022      QLB 
 */

@Controller
@Scope("prototype")
@RequestMapping("/versionUpgrade")
public class VersionUpAdcpController extends BaseController{
	@Autowired
	private DefaultValueService dvService;        //获取主表数据service
	@Autowired
	private DyFormApiFacade dyFormApiFacade;
	@Autowired
	private WorkService workService;              //流程发起
	

	/**
	 * ADCP-根据编号和版本号获取当前版本数据
	 * @param bh
	 * @param bb
	 * @return
	 */
	@RequestMapping(value = "/uv_djadcp",method = RequestMethod.GET)
	public String adcpVersionUp(Model model,@RequestParam(value = "bh")String bh,@RequestParam(value = "bb")String bb){
		
		String adcpFlowUuid = "b654e4c1-4322-4023-9ae2-3fa59f3af78f";   //adcp流程uuid
		String adcpMainUuid = "7626fb53-2834-4f1f-9f15-a3f442a2198f";   //adcp表单主表uuid
		String adcpSubUuid  = "a2354d60-abb5-4018-867d-90a0da6c3977";   //adcp表单从表uuid		
		
		
		String findMainSql = "select t.uuid,t.xmdh,t.xlm,t.cpxh,t.cpms,t.cpdl,t.bb from (select uuid,xmdh,xlm,cpxh,cpms,cpdl,bb from UF_LEEDARSON_DJADCPSHB where bh = '" 
			                 + bh + "' order by create_time desc)" + " t where rownum=1";
		
		List mainlist = dvService.queryBySql(findMainSql);              //根据编号获取ADCP的抬头数据
		
		Object[] object = (Object[]) mainlist.get(0);                   //获取第一条数据，并转换为object类型
		String bbStr = object[6].toString();                            //获取版本号
		Double newBB = Double.parseDouble(bbStr)+0.1;                   //版本升级
		
		//创建一条新版本的灯具ADCP审核表主表数据
		String interUuid = dyFormApiFacade.copyFormData(adcpMainUuid, object[0].toString(), adcpMainUuid);
		int a = dvService.update("UF_LEEDARSON_DJADCPSHB", "bb='"+String.format("%.1f", newBB)+"'", "uuid='"+interUuid+"'");
		
		//创建流程实例
		WorkBean localWorkBean = this.workService.newWork(adcpFlowUuid);
	    if (StringUtils.isNotBlank(adcpMainUuid))
	    {
	      localWorkBean.setFormUuid(adcpMainUuid);
	      if (StringUtils.isNotBlank(interUuid)){
	    	  localWorkBean.setDataUuid(interUuid);
	      }	        
	    }
	    
	    //copy 从表
	    List childUuidList = new ArrayList();
	    
	    //源uuid,源data_uuid,目标uuid,目标data_uuid,从表表单uuid
	    childUuidList = dyFormApiFacade.copySubFormData(adcpMainUuid, object[0].toString(), adcpMainUuid, interUuid, 
	    		                                        adcpSubUuid, " 1=1", null);
	    
	    System.out.println("childUuidList:"+childUuidList.size());
	    
        model.addAttribute(localWorkBean);
		
		return forward("/workflow/work/work_view");		
	}

	
	/**
	 * 表单归档	
	 * @param uuid
	 * @param bh
	 * @param bb
	 * @param formuuid
	 * @param res
	 * @throws IOException
	 */
	@RequestMapping(value = "/archiveForm",method = RequestMethod.POST)
	public void archiveForm(@RequestParam(value = "uuid")String uuid,@RequestParam(value = "bh")String bh,
                            @RequestParam(value = "bb")String bb,    @RequestParam(value = "form_uuid")String formuuid,
                            HttpServletResponse res) throws IOException{
		int a = 0,         //存放更新表的返回值
		    b = 0;         //返回值的累加
		String mess = "";  //返回消息
		
		if(StringUtils.isBlank(uuid))
			return;
		String[] array1 = uuid.split(";");    //将输入的uuid串转为string数组
		String[] array2 = bh.split(";");      //将输入的bh串转为string数组
		String[] array3 = bb.split(";");      //将输入的bb串转为string数组
		
		if(null==array1||array1.length==0)
			return;
		
		//根据form_uuid 获取主表名称
		DyFormDefinition dyFormDefinition = dyFormApiFacade.getFormDefinition(formuuid);
		
		for (int i = 0;i<array1.length;i++) {             //通过uuid到数据表中更新状态，将状态转为归档状态 12
		     a = dvService.update(dyFormDefinition.getName(), "zt='" + "12" +"'", "uuid='" + array1[i] + "'");	
		     if(a != 1){ //若返回值不为1，则说明更新失败
					mess = "项目编号为：" + array2[i] + ",版本为：" + array3[i] + ",归档失败！停止后续归档操作！";
			 }else{
					b += a;
			 }
		}
		if(b==array1.length){   //成功的记录数 = 选中的记录数
			mess = "归档成功！";
		}
		res.getWriter().print(mess);
	}
	
	
    /**
     * 表单恢复
     * @param uuid
     * @param bh
     * @param bb
     * @param formuuid
     * @param res
     * @throws IOException
     */
	@RequestMapping(value = "/recoverForm",method = RequestMethod.POST)
	public void recoverForm(@RequestParam(value = "uuid")String uuid,@RequestParam(value = "bh")String bh,
			                @RequestParam(value = "bb")String bb,    @RequestParam(value = "form_uuid")String formuuid,
			                HttpServletResponse res) throws IOException{
		int a = 0,             //存放更新表的返回值
		    b = 0;             //返回值的累加
		String mess = "";      //返回消息
		
		if(StringUtils.isBlank(uuid))
			return;
		String[] array1 = uuid.split(";");    //将输入的uuid串转为string数组
		String[] array2 = bh.split(";");      //将输入的bh串转为string数组
		String[] array3 = bb.split(";");      //将输入的bb串转为string数组
		
		if(null==array1||array1.length==0)    
			return;
		
		//根据form_uuid 获取主表名称
		DyFormDefinition dyFormDefinition = dyFormApiFacade.getFormDefinition(formuuid);
		
		for (int i=0;i<array1.length;i++) {             //通过uuid到数据表中更新状态，将状态转为发布状态 11
			//到表中根据输入的编号获取最高版本
			StringBuffer sql1 = new StringBuffer("select t.bh,t.bb from (select bh,bb from ")
			                    .append(dyFormDefinition.getName())
			                    .append(" where bh = '")
			                    .append(array2[i])
			                    .append("' order by create_time desc)").append(" t where rownum=1");
			List list1 = dvService.queryBySql(sql1.toString());
			Object[] object1 = (Object[]) list1.get(0);
			String bbTop = object1[1].toString();      //获取到最高版本
			
			if(!bbTop.equals(array3[i])){                  //如果恢复的不是最高版本，则报错
				mess = "只能对最高版本进行恢复操作！";
			}else{
				//再到表中获取是否存在发布状态的记录
				StringBuffer sql2 = new StringBuffer("select t.bh,t.bb from ")
				                        .append(dyFormDefinition.getName())
                                        .append(" t where t.bh='")
                                        .append(array2[i]).append("' and t.zt='11'");
				List list2 = dvService.queryBySql(sql2.toString());
					
				if(!list2.isEmpty()){     //说明存在发布状态的记录，则报错
					mess = "该项目编号存在发布状态的记录，不允许恢复！";
				}else{
					a = dvService.update(dyFormDefinition.getName(), "zt='" + "11" +"'", "uuid='" + array1[i] + "'");
					
					if(a != 1){ //若返回值不为1，则说明更新失败
						mess = "项目编号为：" + array2[i] + ",版本为：" + array3[i] + ",更新失败！更新后续恢复操作！";
					}else{
						b += a;
					}
				}
			}									
		}
		if(b==array1.length){   //成功的记录数 = 选中的记录数
			mess = "恢复成功！";
		}
		res.getWriter().print(mess);
	}
	

	
	/**
	 * 判断是否为最高版本
	 * @param bh
	 * @param bb
	 * @param formuuid
	 * @param res
	 * @throws IOException
	 */
	@RequestMapping(value = "/checkVersion",method = RequestMethod.POST)
	public void checkVersion(@RequestParam(value = "bh")String bh,@RequestParam(value = "bb")String bb,
			                 @RequestParam(value = "form_uuid")String formuuid,HttpServletResponse res) throws IOException{
		String mess = "";   //返回消息
		
		//根据form_uuid 获取主表名称
		DyFormDefinition dyFormDefinition = dyFormApiFacade.getFormDefinition(formuuid);
		
		//根据所选的记录到表中获取对应的项目最高版本
		StringBuffer sql = new StringBuffer("select t.uuid,t.bb from (select uuid,bb from ")
		                       .append(dyFormDefinition.getName())
		                       .append(" where bh = '")
		                       .append(bh)
		                       .append("' order by create_time desc)").append(" t where rownum=1");
		List list = dvService.queryBySql(sql.toString());
		Object[] object = (Object[]) list.get(0);
		String bbTop = object[1].toString();  //获取到最高版本
		
		if(!bbTop.equals(bb)){
			mess = "升版操作只能针对最高发布状态的记录进行升版，请重新选择！";
		}else{
			mess = "成功";
		}
		
		res.getWriter().print(mess);
	}
	
	
	
	/**
	 * EP申请单-根据编号和版本号获取当前版本数据
	 * QLB  20141026
	 * @param model
	 * @param bh
	 * @param bb
	 * @return
	 */
	@RequestMapping("/uv_epsqd")
	public String epsqdVersionUp(Model model,@RequestParam(value = "bh")String bh,@RequestParam(value = "bb")String bb){
		
		String epFlowUuid = "5ae52aa1-af5f-4403-a767-7939ecbcb859";     //ep流程uuid
		String epMainUuid = "de26c003-7be4-4cdd-8495-7a9a7495e268";     //ep主表uuid
		String epSubUuid  = "932957d1-225a-472c-af1c-8bfd5685f705";     //ep从表uuid
		
		String findMainSql = "select t.uuid,t.fcbm,t.jsbm,t.fcr,t.zbr,t.mbwcsj,t.bb from (select uuid,fcbm,jsbm,fcr,zbr,mbwcsj,bb from UF_LEEDARSON_EPSQD where bh = '" 
			                 + bh + "' order by create_time desc)" + " t where rownum=1";
		
        List mainlist = dvService.queryBySql(findMainSql);              //根据编号获取EP申请单的抬头数据
		
		Object[] object = (Object[]) mainlist.get(0);                   //获取第一条数据，并转换为object类型
		String bbStr = object[6].toString();                            //获取版本号
		Double newBB = Double.parseDouble(bbStr)+0.1;                   //版本升级
		
		//创建一条新版本的EP申请单主表数据
		String interUuid = dyFormApiFacade.copyFormData(epMainUuid, object[0].toString(), epMainUuid);
		int a = dvService.update("UF_LEEDARSON_EPSQD", "bb='"+String.format("%.1f", newBB)+"'", "uuid='"+interUuid+"'");
		
		//创建流程实例
		WorkBean localWorkBean = this.workService.newWork(epFlowUuid);
	    if (StringUtils.isNotBlank(epMainUuid))
	    {
	      localWorkBean.setFormUuid(epMainUuid);
	      if (StringUtils.isNotBlank(interUuid)){
	    	  localWorkBean.setDataUuid(interUuid);
	      }	        
	    }
	    
	    //copy 从表
	    List childUuidList = new ArrayList();
	    
	    //源uuid,源data_uuid,目标uuid,目标data_uuid,从表表单uuid
	    childUuidList = dyFormApiFacade.copySubFormData(epMainUuid, object[0].toString(), epMainUuid, interUuid, 
	    		                                        epSubUuid, " 1=1", null);
	    
	    System.out.println("childUuidList:"+childUuidList.size());
	    
        model.addAttribute(localWorkBean);
        
		return forward("/workflow/work/work_view");	
	}

}
