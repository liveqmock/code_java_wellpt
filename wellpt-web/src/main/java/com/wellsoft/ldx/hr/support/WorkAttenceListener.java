package com.wellsoft.ldx.hr.support;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fr.stable.StringUtils;
import com.wellsoft.ldx.hr.utils.SqlServerUtils;
import com.wellsoft.pt.bpm.engine.context.event.Event;
import com.wellsoft.pt.bpm.engine.context.listener.DirectionListener;
import com.wellsoft.pt.bpm.engine.context.listener.TaskListener;
import com.wellsoft.pt.bpm.engine.exception.WorkFlowException;
import com.wellsoft.pt.dyform.facade.DyFormApiFacade;
import com.wellsoft.pt.dyform.support.DyFormData;

@Service
@Transactional
public class WorkAttenceListener implements TaskListener {

	@Autowired
	private DyFormApiFacade dyFormApiFacade;
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "考勤补卡单(长泰)";
	}

	@Override
	public int getOrder() {
		return 0;
	}

	@Override
	public void onCompleted(Event event) throws WorkFlowException {
		//获得SqlServer连接
		Connection conn = SqlServerUtils.getConnection();
		CallableStatement proc = null;
		
		// 获得流程表单的数据
		DyFormData dyformdata =dyFormApiFacade.getDyFormData(event.getFormUuid(), event.getDataUuid());
		String kqkh = (String)dyformdata.getFieldValue("kqkh");
		// 通过从表id获得从表数据//uf_scgl_bqxx(1.0)
		List<Map<String, Object>> childList = dyformdata.getFormDatasById("uf_scgl_bqxx");//	uf_scgl_bqxx
		if(childList != null && childList.size() >0){
			for(Map<String ,Object> child:childList){
				String bkrq = (String)child.get("bkrq");//补卡时间
				String ldksj = (String)child.get("ldksj");//漏打卡时间
				
				String bkrq_ldksj = bkrq + " " +  ldksj;
				
				String bklx = (String)child.get("bklx");//补卡类型
				int bklxInt = 1000;
				if(StringUtils.isNotBlank(bklx)){
					String relBklx = DyFormApiFacade.getRealValue(bklx);
					bklxInt = Integer.parseInt(relBklx);//补卡类型真实值 (注意：参数请传类型ID 备注：因公漏刷:1000;个人漏刷:1001;忘卡漏刷:1002 )
				}
				String bkyy = (String)child.get("bkyy");//补卡原因
				String bkyyRel = null;
				if(StringUtils.isNotBlank(bkyy)){
					bkyyRel = DyFormApiFacade.getDisplayValue(bkyy);
				}
			   try {
					proc =  conn.prepareCall("{call PRO_OA_kq_IC_Input(?,?,?,?,?,?,?)}");
//					@is_td_no char(50), --考勤卡号
//					@idt_td_time datetime, --补卡时间
//					@ii_td_type int , --补卡类型 (注意：参数请传类型ID 备注：因公漏刷:1000;个人漏刷:1001;忘卡漏刷:1002 )
//					@is_td_reason varchar(255),--补卡原因 (注意：参数传补卡原因ID)
//					@is_sysuser char(12) ,--维护人
//					@idt_sysdate datetime, --维护时间
//					@ii_rt int output --返回值
//					AS  
//					--declare @ii_rt int --返回值1/0
					proc.setString(1, kqkh);//PI_PERNR----工号 
					Date startDate = DateUtils.parseDate(bkrq_ldksj, new String[]{"yyyy-MM-dd HH:mm"});//--补卡时间
					proc.setTimestamp(2, new java.sql.Timestamp(startDate.getTime()));
					proc.setInt(3, bklxInt);//--补卡类型 
					proc.setString(4, bkyyRel);//--补卡原因
					proc.setString(5, "LCP");//"OA"--维护人
					proc.setTimestamp(6, new java.sql.Timestamp(new Date().getTime()));//--维护时间
					proc.setString(7, "");//""--返回值1/0/-1
					boolean isSuccess = proc.execute();
					System.out.println(isSuccess);
				}catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}
		
	}

	@Override
	public void onCreated(Event arg0) throws WorkFlowException {
		
	}

}
