package com.wellsoft.ldx.hr.support;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
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
 * 描述：加班申请单(长泰)流程事件处理
 * @author huangwy
 *
 */
@Service
@Transactional
public class OverTimeApplicationListener implements TaskListener{

	@Autowired
	private DyFormApiFacade dyFormApiFacade;
	
	@Override
	public String getName() {
		return "加班申请单(长泰)";
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
				List<Map<String, Object>> childList = dyformdata.getFormDatasById("uf_scgl_jbxx");
				if(childList != null && childList.size() >0){
					
					for(Map<String ,Object> child:childList){
						String ygbh = (String) child.get("ygbh");//员工编号
						String xm = (String) child.get("xm");//姓名
						String bm = (String) child.get("bm");//部门
						String gw = (String) child.get("gw");//岗位
						String jhjbkssj = (String) child.get("jhjbkssj");//开始时间
						String jhjbjssj = (String) child.get("jhjbjssj");//结束时间
						String xss = (String) child.get("xss");//小时数
						BigDecimal xssBD = null;
						if(StringUtils.isNotBlank(xss)){
							xssBD = new BigDecimal(xss);
						}
						String jblx = (String) child.get("jblx");//加班类型
						String jblxRealValue = DyFormApiFacade.getRealValue(jblx);//加班类型真实值
						int jblxInt = 0;
						if(StringUtils.isNotBlank(jblxRealValue)){
							jblxInt = Integer.parseInt(jblxRealValue);
						}
						
						String jbsy = (String) child.get("jbsy");//--申请加班事由
						String fftx = (String) child.get("fftx");//---补偿类型
						String fftxRealValue = DyFormApiFacade.getRealValue(fftx);
						int fftxInt = 0;
						if(StringUtils.isNotBlank(fftxRealValue)){
							fftxInt = Integer.parseInt(fftxRealValue);
						}
						
						String uuid = (String)child.get("uuid");//--流水单号
						
					    try {
							proc =  conn.prepareCall("{call PRO_OA_kq_over_apply(?,?,?,?,?,?,?,?,?,?,?,?,?)}");
//							@is_empid char(12), --工号   1  
//							@is_ov_nbr char(50), --申请加班单号    2
//							@idt_ov_date datetime ,--申请日期    3
//							@idt_ov_beg datetime, --加班开始时间   4
//							@idt_ov_end datetime, --加班结束时间    5
//							@ii_ov_type int , --加班类型 (注意：参数传类型ID：0正常加班，1提前加班，2中午加班，3厂外加班，4下午连班)  6
//							@is_ov_newclass char(12),--当日班次ID  7
//							@idc_ov_hour decimal(18,2), --本次加班时数   8
//							@is_ov_reason varchar(255),--申请加班事由  9
//							@is_ov_user char(12) ,--维护人   10
//							@idt_ov_time datetime,--维护时间   11
//							@ii_rt int output, --返回值1/0/-1   12
//							@ii_ov_usetype int--补偿类型（1-调休，0-付费） 13
							proc.setString(1, ygbh);//PI_PERNR----工号 
							proc.setString(2, uuid);//OASQDH --流水单号
							Date startDate = DateUtils.parseDate(jhjbkssj, new String[]{"yyyy-MM-dd HH:mm"});//--加班开始时间
							Date endDate =   DateUtils.parseDate(jhjbjssj, new String[]{"yyyy-MM-dd HH:mm"});//--加班结束时间
							//申请日期的加班的开始时间   立达信需求确认的
							proc.setTimestamp(3, new java.sql.Timestamp(startDate.getTime()));
							proc.setTimestamp(4, new java.sql.Timestamp(startDate.getTime()));
							proc.setTimestamp(5, new java.sql.Timestamp(endDate.getTime()));
							proc.setInt(6, jblxInt);//--加班类型 (注意：参数请传假别ID)
							proc.setString(7, "");
							proc.setBigDecimal(8, xssBD);//----本次加班时数
							proc.setString(9, jbsy);//--申请事由
							proc.setString(10, "LCP");//"OA"--维护人  //维护人改为LCP
							proc.setTimestamp(11, new java.sql.Timestamp(new Date().getTime()));//--维护时间
							proc.setString(12, "");
							proc.setInt(13, fftxInt);//"" --申请单号
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
