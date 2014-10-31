package com.wellsoft.ldx.hr.support;

import java.math.BigInteger;
import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.apache.axis.AxisFault;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import addr.AddrBindingStub;
import addr.AddrPortType;
import addr.AddrPortTypeProxy;
import addr.AddrService;
import addr.AddrServiceLocator;

import com.wellsoft.pt.basicdata.sap.service.SAPRfcService;
import com.wellsoft.pt.bpm.engine.context.event.Event;
import com.wellsoft.pt.bpm.engine.context.listener.TaskListener;
import com.wellsoft.pt.bpm.engine.exception.WorkFlowException;
import com.wellsoft.pt.dyform.facade.DyFormApiFacade;
import com.wellsoft.pt.dyform.support.DyFormData;
import com.wellsoft.pt.security.core.userdetails.UserDetails;
import com.wellsoft.pt.utils.security.SpringSecurityUtils;

/**
 * 
 * @author huangwy
 * 描述：对外邮件地址申请
 */
@Service
@Transactional
public class ForeignMailListener implements TaskListener{

	@Autowired
	private DyFormApiFacade dyFormApiFacade;
	
	@Autowired 
	SAPRfcService saprfcservice;
	
	@Override
	public String getName() {
		return "对外邮件地址申请";
	}

	@Override
	public int getOrder() {
		return 0;
	}

	@Override
	public void onCompleted(Event event) throws WorkFlowException {
		//用户地址
		String mailAddress = "";
		//用户工号
		String currentUserId = "";
		//验证用户
		String verifyUser = "foreignmail";
		//验证密码
		String verifyPsword = "foreignmailoa2014";
		// 获得流程表单的信息 
		DyFormData dyformdata =dyFormApiFacade.getDyFormData(event.getFormUuid(), event.getDataUuid());
		// 获得主表表单数据
		Map<String,Object> dataMap = dyformdata.getFormDataByFormUuidAndDataUuid(event.getFormUuid(), event.getDataUuid());
		
		UserDetails userDetails = SpringSecurityUtils.getCurrentUser();//当前用户id
		currentUserId = userDetails.getEmployeeNumber();//用户工号
		
		// 通过从表id获得从表数据//uf_xxgldwyjdzsq_dtbd(1.0)
		List<Map<String, Object>> childList = dyformdata.getFormDatasById("uf_xxgldwyjdzsq_dtbd");//uf_xxgldwyjdzsq_dtbd
		for(Map<String,Object> child : childList){
			mailAddress = (String)child.get("yjdz");//邮件地址
			String strURL = "http://util-dev.olerp.net/addr/addr.php?wsdl";
			AddrService addrService = null;
			AddrBindingStub addrBindingStub = null;
			addrService = new AddrServiceLocator();
			try {
				addrBindingStub = new AddrBindingStub(new java.net.URL(strURL),addrService);
				BigInteger returnValue =  addrBindingStub.addAddr(mailAddress, currentUserId, verifyUser, verifyPsword);
				
				if(returnValue.toString().equals("0")){
					
				}else if(returnValue.toString().equals("5")){
					throw new WorkFlowException("参数为空!");
				}else if(returnValue.toString().equals("6")){
					throw new WorkFlowException("添加失败!");
				}else if(returnValue.toString().equals("7")){
					throw new WorkFlowException("验证失败!");
				}
			} catch (AxisFault e1) {
				e1.printStackTrace();
			}catch (MalformedURLException e) {
				e.printStackTrace();
			}catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void onCreated(Event event) throws WorkFlowException {
		
	}

}
