package com.wellsoft.pt.ldx.service.impl.ficoManage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wellsoft.pt.core.service.impl.BaseServiceImpl;
import com.wellsoft.pt.ldx.dao.common.MailAddrDao;
import com.wellsoft.pt.ldx.model.ficoManage.MailAddr;
import com.wellsoft.pt.ldx.service.ficoManage.IFicoDataSynchronizeService;
import com.wellsoft.pt.ldx.util.StringUtils;

/**
 * 
 * Description: 后台数据同步服务类
 *  
 * @author HeShi
 * @date 2014-10-3
 * @version 1.0
 * 
 * <pre>
 * 修改记录:
 * 修改后版本	修改人		修改日期			修改内容
 * 2014-10-3.1	HeShi		2014-10-3		Create
 * </pre>
 *
 */
@Service
@Transactional
public class FicoDataSynchronizeServiceImpl extends BaseServiceImpl implements IFicoDataSynchronizeService{
	@Autowired
	private MailAddrDao mailAddrDao;

	@Override
	public void saveMailAddr() {
		// TODO Auto-generated method stub
		String sql = "select name,addr,type,sendType,funCode,orderNum,userId from c_mail_addr";
		Connection conn = null;
		PreparedStatement psStatement = null;
		ResultSet rSet = null;
		try{
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").newInstance();
			String url = "jdbc:sqlserver://192.168.0.134:1433;databasename=dataportal_prod";
			conn = DriverManager.getConnection(url, "sa", "123456");
			psStatement = conn.prepareStatement(sql);
			rSet = psStatement.executeQuery();
			while (rSet.next()) {
				String name = StringUtils.nullToString(rSet.getObject("name"));//姓名
				String addr = StringUtils.nullToString(rSet.getObject("addr"));//邮件地址
				String type = StringUtils.nullToString(rSet.getObject("type"));//类型
				String sendType = StringUtils.nullToString(rSet.getObject("sendType"));//发送类型
				String funCode = StringUtils.nullToString(rSet.getObject("funCode"));//功能代码
				String orderNum = StringUtils.nullToString(rSet.getObject("orderNum"));//排序号
				String userId = StringUtils.nullToString(rSet.getObject("userId"));//工号
				//System.out.println("**//** name:"+name+"  addr:"+addr+"  sendType:"+sendType+"  funCode:"+funCode+"  orderNum:"+orderNum+"  userId:"+userId);
				@SuppressWarnings("deprecation")
				List<MailAddr> list = mailAddrDao.find("from MailAddr where type=? and funcode=? and sendtype=? and addr=?",type,funCode,sendType,addr);
				if(list != null && !list.isEmpty())
					continue;
				MailAddr ma = new MailAddr();
				ma.setAddr(addr);
				ma.setSendtype(sendType);
				ma.setName(name);
				ma.setType(type);
				ma.setFuncode(funCode);
				ma.setOrdernum(orderNum);
				ma.setUserid(userId);
				mailAddrDao.save(ma);
			}
		}catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}finally{
			try {
				rSet.close();
				psStatement.close();
				conn.close();
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
	}

}
