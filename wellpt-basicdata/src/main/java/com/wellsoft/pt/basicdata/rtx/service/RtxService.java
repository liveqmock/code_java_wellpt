package com.wellsoft.pt.basicdata.rtx.service;

import java.util.List;

import com.wellsoft.pt.basicdata.rtx.bean.RtxBean;
import com.wellsoft.pt.basicdata.rtx.entity.Rtx;
import com.wellsoft.pt.message.support.Message;

/**
 * 
 * Description: Rtx设置服务层接口
 *  
 * @author zhouyq
 * @date 2013-6-17
 * @version 1.0
 * 
 * <pre>
 * 修改记录:
 * 修改后版本	修改人		修改日期			修改内容
 * 2013-6-17.1	zhouyq		2013-6-17		Create
 * </pre>
 *
 */

public interface RtxService {
	public static final String ACL_SID = "ROLE_RTX";

	/**
	 * 
	 * 判断是否启用rtx
	 * 
	 * @return
	 */
	public Boolean isRtxEnable();

	/**
	 * 通过uuid获取Rtx设置
	 * 
	 * @param uuid
	 * @return
	 */
	public Rtx get(String uuid);

	/**
	 * 通过uuid获取Rtx设置VO对象
	 * 
	 * @param uuid
	 * @return
	 */
	public RtxBean getBeanByUuid(String uuid);

	/**
	 * 保存Rtx设置
	 * 
	 * @param uuid
	 * @return
	 */
	public void saveBean(RtxBean bean);

	/**
	 * 通过UUID删除Rtx设置
	 * 
	 * @param uuid
	 * @return
	 */
	public void remove(String uuid);

	/**
	 * 获取全部的Rtx设置
	 * 
	 * @return
	 */
	List<Rtx> getAll();

	/**
	 * 
	 * 同步组织
	 *
	 */
	public void synchronizedOrganization(RtxBean bean);

	/**
	 * 
	 * 向rtx发送在线消息
	 * 
	 * @param msg
	 */
	public void sendMessage(Message msg);

	/**
	 * 
	 * 单点登录
	 * 
	 * @return
	 */
	public String singleSignOn(RtxBean bean);

}
