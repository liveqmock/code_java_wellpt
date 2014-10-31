package com.wellsoft.ldx.hr.support;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;
import javax.xml.rpc.holders.BigDecimalHolder;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.wellsoft.ldx.hr.utils.SqlServerUtils;
import com.wellsoft.pt.bpm.engine.context.event.Event;
import com.wellsoft.pt.bpm.engine.context.listener.DirectionListener;
import com.wellsoft.pt.bpm.engine.context.listener.TaskListener;
import com.wellsoft.pt.bpm.engine.exception.WorkFlowException;
import com.wellsoft.pt.dyform.facade.DyFormApiFacade;
import com.wellsoft.pt.dyform.support.DyFormData;

/**
 * 描述：出差申请单(长泰)流程事件处理
 * @author huangwy
 *
 */
@Service
@Transactional
public class EhrTravelApplicationListener implements TaskListener{

	@Autowired
	private DyFormApiFacade dyFormApiFacade;

	@Override
	public String getName() {
		return "出差申请(长泰)";
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
		// 通过从表id获得从表数据
		List<Map<String, Object>> childList = dyformdata.getFormDatasById("uf_scgl_ccxx");
		if(childList != null && childList.size() >0){
			
			for(Map<String ,Object> child:childList){
				String ygbh = (String) child.get("ygbh");//员工编号
				String xm = (String) child.get("xm");//姓名
				String bm = (String) child.get("bm");//部门
				String gw = (String) child.get("gw");//岗位
				String nkssj = (String) child.get("nkssj");//开始时间
				String njssj = (String) child.get("njssj");//结束时间
				BigDecimal xss = (BigDecimal) child.get("xss");//小时数
				BigDecimal ts = (BigDecimal) child.get("ts");//天数
				String zwdlr = (String) child.get("zwdlr");//职务代理人
				String ccdd = (String) child.get("ccdd");//出差地点
				String ccsy = (String) child.get("ccsy");//出差是由
				String uuid = (String) child.get("uuid");//流水单号
				
			    try {
					proc =  conn.prepareCall("{call pro_OA_kq_leave_apply(?,?,?,?,?,?,?,?,?,?,?,?)}");
//					@is_empid char(12), --工号 
//					@is_nbr char(50), --流水单号 
//					@idt_leambeg datetime, --请假开始时间
//					@idt_leamend datetime, --请假结束时间
//					@ii_leas_id int , --假别ID (注意：参数请传假别ID)
//					@idc_hour decimal(18,2), --本次请假时数
//					@idc_day decimal(18,2), --本次请假天数
//					@is_reason varchar(255),--申请事由
//					@is_leam_user char(12) ,--维护人
//					@idt_leam_time datetime,--维护时间
//					@is_apply_nbr  char(50),--申请单号
//					@ii_rt varchar(100) output --返回值1/0/-1
					proc.setString(1, ygbh);//PI_PERNR----工号 
					proc.setString(2, uuid);//OASQDH --流水单号
					Date startDate = DateUtils.parseDate(nkssj, new String[]{"yyyy-MM-dd HH:mm"});//--请假开始时间
					Date endDate =   DateUtils.parseDate(njssj, new String[]{"yyyy-MM-dd HH:mm"});//--请假结束时间
					proc.setTimestamp(3, new java.sql.Timestamp(startDate.getTime()));
					proc.setTimestamp(4, new java.sql.Timestamp(endDate.getTime()));
				
					//proc.setd
					proc.setInt(5, 12);//--假别ID (注意：参数请传假别ID)
					proc.setBigDecimal(6, xss);//--本次请假时数
					proc.setBigDecimal(7, ts);//--本次请假天数
					proc.setString(8, ccsy);//--申请事由
					proc.setString(9, "LCP");//"OA"--维护人
					proc.setTimestamp(10, new java.sql.Timestamp(new Date().getTime()));//--维护时间
					proc.setString(11, "");//"" --申请单号
					proc.setString(12, "");//""--返回值1/0/-1
					boolean isSuccess = proc.execute();
					
					System.out.println(isSuccess);
				}catch (Exception e1) {
					e1.printStackTrace();
				}
				
			}
		}
	}

	@Override
	public void onCreated(Event event) throws WorkFlowException {
		
	}
	
}
