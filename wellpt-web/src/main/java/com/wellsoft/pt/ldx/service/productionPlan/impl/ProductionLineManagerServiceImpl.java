package com.wellsoft.pt.ldx.service.productionPlan.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wellsoft.pt.ldx.service.impl.SapServiceImpl;
import com.wellsoft.pt.ldx.service.productionPlan.IProductionLineManagerService;
import com.wellsoft.pt.ldx.util.StringUtils;

/**
 * 
 * Description: 生管库存service
 *  
 * @author heshi
 * @date 2014-8-1
 * @version 1.0
 * 
 * <pre>
 * 修改记录:
 * 修改后版本	修改人		修改日期			修改内容
 * 2014-8-1.1	heshi		2014-8-1		Create
 * </pre>
 *
 */
@Service
@Transactional
public class ProductionLineManagerServiceImpl extends SapServiceImpl implements IProductionLineManagerService {

	@Override
	public List<String[]> findLineManagerByInfo(String zsg, String zoauser) {
		StringBuffer sql = new StringBuffer("select zsg,zoauser,zkz,verid,matkl,atwrt,dwerks,zzz from zppt0035 where mandt='" + getClient() + "'");
		if(StringUtils.isNotBlank(zsg)){
			sql.append(" and zsg='" + zsg + "'");
		}
		if(StringUtils.isNotBlank(zoauser)){
			sql.append(" and zoauser='" + zoauser + "'");
		}
		List<Object> list = findListBySql(sql.toString());
		if(null==list||list.size()==0)
			return null;
		List<String[]> resultList = new ArrayList<String[]>(); 
		for(Object obj:list){
			Object[] objects = (Object[]) obj;
			String[] line = new String[objects.length];
			for(int i=0;i<objects.length;i++){
				line[i]=StringUtils.nullToString(objects[i]);
			}
			resultList.add(line);
		}
		return resultList;
	}

	@Override
	public void deleteLineManager(String params) {
		if(StringUtils.isBlank(params))
			return;
		String[] array = params.split(";");
		if(null==array||array.length==0)
			return;
		for (String map: array) {
			String[] conifg = map.split(",");
			String delete = "delete from zppt0035 where zsg='" +conifg[0] + "' and zoauser='"
					+ conifg[1] + "' and mandt=" +getClient() ;
			execSql(delete);
		}
	}

	@Override
	public Map<?, ?> saveLineManager(String zsg, String zoauser, String zkz, String verid, String matkl, String atwrt,
			String dwerks, String zzz) {
		Map<String,String> map = new HashMap<String,String>();
		if(StringUtils.isBlank(zsg)||StringUtils.isBlank(zoauser)){
			map.put("res","fail");
			map.put("msg","生管及OA用户不能为空值!");
			return map;
		}
		if(StringUtils.isBlank(zkz)){
			map.put("res","fail");
			map.put("msg","课长不能为空值!");
			return map;
		}
		if(StringUtils.isBlank(dwerks)){
			map.put("res","fail");
			map.put("msg","工厂不能为空值!");
			return map;
		}
		if(StringUtils.isBlank(zzz)){
			map.put("res","fail");
			map.put("msg","组长不能为空值!");
			return map;
		}
		List<String[]> list = findLineManagerByInfo(zsg,zoauser);
		if(list!=null && list.size()>0){
			map.put("res","fail");
			map.put("msg","当前用户已配置,请勿重复添加!");
			return map;
		}
		if(StringUtils.isBlank(verid)){
			verid = " ";
		}
		if(StringUtils.isBlank(matkl)){
			matkl = " ";
		}
		String sql = "insert into ZPPT0035(mandt,zsg,zoauser,zkz,verid,matkl,atwrt,dwerks,zzz) values('" + getClient()
				+ "','" + zsg + "','" + zoauser + "','" + zkz + "','" + verid + "','" + matkl + "','" + atwrt + "','"
				+ dwerks + "','" + zzz + "')";
		execSql(sql);
		map.put("res","success");
		map.put("msg","保存成功!");
		return map;
	}

	@Override
	public Map<?, ?> updateLineManager(String zsg, String zoauser, String zkz, String verid, String matkl, String atwrt,
			String dwerks, String zzz) {
		Map<String,String> map = new HashMap<String,String>();
		if(StringUtils.isBlank(zkz)){
			map.put("res","fail");
			map.put("msg","课长不能为空值!");
			return map;
		}
		if(StringUtils.isBlank(dwerks)){
			map.put("res","fail");
			map.put("msg","工厂不能为空值!");
			return map;
		}
		if(StringUtils.isBlank(zzz)){
			map.put("res","fail");
			map.put("msg","组长不能为空值!");
			return map;
		}
		if(StringUtils.isBlank(verid)){
			verid = " ";
		}
		if(StringUtils.isBlank(matkl)){
			matkl = " ";
		}
		String sql = "update ZPPT0035 set zkz='" + zkz + "',verid='" + verid + "',matkl='" + matkl + "',atwrt='"
				+ atwrt + "',dwerks='" + dwerks + "',zzz='" + zzz + "' where zsg='" + zsg + "' and zoauser='" + zoauser
				+ "' and mandt=" + getClient();
		execSql(sql);
		map.put("res","success");
		map.put("msg","保存成功!");
		return map;
	}
	
}
