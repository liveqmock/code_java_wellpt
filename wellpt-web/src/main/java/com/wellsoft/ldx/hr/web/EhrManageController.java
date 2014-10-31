package com.wellsoft.ldx.hr.web;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.xml.rpc.holders.BigDecimalHolder;

import org.apache.axis.AxisFault;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.tempuri.Service1;
import org.tempuri.Service1Locator;
import org.tempuri.Service1SoapStub;

import addr.AddrBindingStub;
import addr.AddrService;
import addr.AddrServiceLocator;

import com.wellsoft.ldx.hr.utils.SqlServerUtils;
import com.wellsoft.pt.bpm.engine.exception.WorkFlowException;
import com.wellsoft.pt.core.web.BaseController;
import com.wellsoft.pt.dyform.facade.DyFormApiFacade;
import com.wellsoft.pt.dyform.support.DyFormData;

@Controller
@RequestMapping("/ehr")
public class EhrManageController extends BaseController{

	@Autowired
	private DyFormApiFacade dyFormApiFacade;
	
	@RequestMapping(value = "/validate", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String[] showPage(
			@RequestParam(value="nkssj",required=false) String nkssj,
    		@RequestParam(value="njssj", required=false) String njssj,
    		@RequestParam(value="ygbh", required=false) String ygbh,
    		@RequestParam(value="psCode", required=false) String psCode,
    		@RequestParam(value="psBigType", required=false) String psBigType,
    		@RequestParam(value="psType", required=false) String psType
			){
		
		String[] ret = {"-1","",""};
		
		BigDecimal xss = new BigDecimal("0");
		BigDecimal ts = new BigDecimal("0");
		//小时数
		BigDecimalHolder BigDecimalHolderbdhRetHour = new BigDecimalHolder(xss);
		//天数
		BigDecimalHolder BigDecimalHolderbdhRetDay = new BigDecimalHolder(ts);
		
		int psCodeInt = 0;
		if(StringUtils.isNotBlank(psCode)){
			psCodeInt = Integer.parseInt(psCode);
		}
		
		int _psType = 1;
		
		if(StringUtils.isNotBlank(psType)){
			_psType = Integer.parseInt(psType);
		}
		
		/** 
		 * @description 获取请假或加班EHR接口的信息
		 * @param {String} psURL 字符串 调用webservice服务URL地址
		 * @param {String} psCode 字符串 "1"请假,"0"加班 
		 * @param {String} psEmpid 字符串 工号
		 * @param {String} psDate 字符串 操作时间
		 * @param {String} psBeg 字符串 开始时间
		 * @param {String} psEnd 字符串 结束时间
		 * @param {String} psNbr 字符串 单号
		 * @param {String} psBigType 字符串 请假大类别具体分为请假，出差，公出
		 * @param {String} psType 字符串 请假或加班的具体类别
		 * @param {String} psUserid 字符串 登陆的用户名
		 * @return {String} 字符串 校验正确返回“ok”,否则返回错误信息；如果为“-1”，则表示调用函数出错
		 */
//		<s:sequence>
//		<s:element name="ls" type="s:int" maxOccurs="1" minOccurs="1"/>
//		<s:element name="ls_emp_id" type="s:string" maxOccurs="1" minOccurs="0"/>
//		<s:element name="dt_date" type="s:string" maxOccurs="1" minOccurs="0"/> 
//		<s:element name="dt_beg" type="s:string" maxOccurs="1" minOccurs="0"/>
//		<s:element name="dt_end" type="s:string" maxOccurs="1" minOccurs="0"/> 
//		<s:element name="ls_nbr" type="s:string" maxOccurs="1" minOccurs="0"/> 
//		<s:element name="ls_bigtype" type="s:string" maxOccurs="1" minOccurs="0"/> 
//		<s:element name="ls_type" type="s:int" maxOccurs="1" minOccurs="1"/>
//		<s:element name="userid" type="s:string" maxOccurs="1" minOccurs="0"/>
//		<s:element name="ls_hour" type="s:decimal" maxOccurs="1" minOccurs="1"/>
//		<s:element name="ls_day" type="s:decimal" maxOccurs="1" minOccurs="1"/> 
//		</s:sequence>
		String strURL = "http://192.168.0.56:87/Service1.asmx?wsdl";
		Service1 service = null;
	    Service1SoapStub stup = null;
	    service = new Service1Locator(); 
	    String[] strs = {"a","b","c"};
	    String rtnStr = null;
		try {
			stup = new Service1SoapStub(new java.net.URL(strURL),service);
			rtnStr = stup.oa_interface(psCodeInt, ygbh, nkssj,
						nkssj,njssj,"","",_psType,ygbh,
						BigDecimalHolderbdhRetHour, BigDecimalHolderbdhRetDay);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String hour = BigDecimalHolderbdhRetHour.value.toString();
		String day =  BigDecimalHolderbdhRetDay.value.toString();
		if(rtnStr.equals("ok")){
			ret[0] = rtnStr;
			ret[1] = day;
			ret[2] = hour;
		}else{
			ret[0] = rtnStr;
		}
		
		return ret;
	}
	/**
	 * 调休定额查询方法
	 * @param controlGhValue
	 * @return
	 */
	@RequestMapping(value = "/txdecx", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String getTxdecx(@RequestParam(value="controlGhValue",required=false) String controlGhValue){
		
		//获得SqlServer连接
		Connection conn = SqlServerUtils.getConnection();
		CallableStatement proc = null;
		// 获得流程表单的数据
		String PI_PERNR = controlGhValue;//工号
		String DAY_NOW = "";//当前时间
		String rtnValue = "";//返回值
		try {
			  proc =  conn.prepareCall("{call hr_kq_get_tiaoxiu(?,?,?)}");
			  proc.setString(1, PI_PERNR);//PI_PERNR----工号 
			  proc.setTimestamp(2, new java.sql.Timestamp(new Date().getTime()));//--维护时间
			  proc.registerOutParameter(3,java.sql.Types.VARCHAR);
			  boolean isSuccess = proc.execute();
			  rtnValue = proc.getString(3);
			  System.out.println(rtnValue);
		}catch (Exception e1) {
			  e1.printStackTrace();
		}
		if(StringUtils.isBlank(rtnValue)){
			rtnValue = "";
		}
		
		return rtnValue;
	}
	
	/**
	 * 考勤补卡单
	 * @param attendenceNumber
	 * @return
	 */
	@RequestMapping(value = "/kqbkh", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String getKqbuh(@RequestParam(value="attendenceNumber",required=false) String attendenceNumber){
		
		//获得SqlServer连接
		Connection conn = SqlServerUtils.getConnection();
		PreparedStatement psStatement = null;
		ResultSet rSet = null;
		String sql = "select emp_ic from h_emp_mstr where emp_id='" + attendenceNumber + "'";
		String kqkh = "";
		try {
			psStatement = conn.prepareStatement(sql);
			rSet = psStatement.executeQuery();
			if(rSet.next()){
				kqkh = rSet.getString(1);//考勤卡号
				if(StringUtils.isNotBlank(kqkh)){
					kqkh = kqkh.trim();//去空格
				}else{
					kqkh = "";
				}
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return kqkh;
	}
	
	/**
	 * 对外邮箱申请
	 * @param email
	 * @return
	 */
	@RequestMapping(value = "/dwyx", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String getEmailValidate(
			@RequestParam(value="email",required=false) String email
			){
		
		//用户地址
		String mailAddress = "";
		mailAddress = email;
		BigInteger returnValue = null;
		BigInteger validateValue = null;
		String strURL = "http://util-dev.olerp.net/addr/addr.php?wsdl";
		AddrService addrService = null;
		AddrBindingStub addrBindingStub = null;
		addrService = new AddrServiceLocator();
		try {
			addrBindingStub = new AddrBindingStub(new java.net.URL(strURL),addrService);
			validateValue = addrBindingStub.verifyAddr(mailAddress);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return validateValue.toString();
	}
	
}