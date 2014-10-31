package com.wellsoft.pt.ldx.service.sales.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wellsoft.pt.basicdata.sap.config.SAPDbConfig;
import com.wellsoft.pt.ldx.model.sales.ShipScheManage;
import com.wellsoft.pt.ldx.service.impl.SapServiceImpl;
import com.wellsoft.pt.ldx.service.sales.ShipScheService;
import com.wellsoft.pt.utils.security.SpringSecurityUtils;

@Service
@Transactional
public class ShipScheServiceImpl extends SapServiceImpl implements
		ShipScheService {

	@Resource(name="sapConnectConfig")
	private SAPDbConfig sapConfig;

	@SuppressWarnings("rawtypes")
	@Override
	public Map saveShipSche(ShipScheManage entity) {
		HashMap hashmap = new HashMap();
		List list = findListBySql("select * from Zsdt0036 where vbeln='00"
				+ entity.getVbeln() + "'");
		String u = SpringSecurityUtils.getCurrentUserName();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		if (list != null && list.size() > 0) {
			String sql = "update Zsdt0036 set ZWADAT='"
					+ entity.getZwadat1().replaceAll("-", "") + "',ZPZFZR='"
					+ entity.getZpzfzr() + "',ZYJCQ='"
					+ entity.getZyjcq1().replaceAll("-", "") + "',ZBL='"
					+ entity.getZbl() + "',ZMT='" + entity.getZmt() + "',ZCD='"
					+ entity.getZcd() + "',ZHD='" + entity.getZhd()
					+ "',ZHDC='" + entity.getZhdc() + "',ZHDCW='"
					+ entity.getZhdcw() + "',ZSBD='"
					+ entity.getZsbd1().replaceAll("-", "") + "',ZBZ='"
					+ entity.getZbz() + "',zggr='" + u + "',zgxsj='"
					+ sdf.format(new Date()) + "',ZDGRQ='"
					+ entity.getZdgrq1().replaceAll("-", "")
					+ "' where mandt='" + sapConfig.getClient()
					+ "' and VBELN='00" + entity.getVbeln() + "'";
			execSql(sql);
		} else {
			String sql = "insert into Zsdt0036(mandt,VBELN,ZWADAT,ZPZFZR,ZYJCQ,ZBL,ZMT,ZCD,ZHD,ZHDC,ZHDCW,ZSBD,ZBZ,zggr,zgxsj,ZDGRQ,zappr) values('"
					+ sapConfig.getClient()
					+ "','00"
					+ entity.getVbeln()
					+ "','"
					+ entity.getZwadat1().replaceAll("-", "")
					+ "','"
					+ entity.getZpzfzr()
					+ "','"
					+ entity.getZyjcq1().replaceAll("-", "")
					+ "','"
					+ entity.getZbl()
					+ "','"
					+ entity.getZmt()
					+ "','"
					+ entity.getZcd()
					+ "','"
					+ entity.getZhd()
					+ "','"
					+ entity.getZhdc()
					+ "','"
					+ entity.getZhdcw()
					+ "','"
					+ entity.getZsbd1().replaceAll("-", "")
					+ "','"
					+ entity.getZbz()
					+ "','"
					+ u
					+ "','"
					+ sdf.format(new Date())
					+ "','"
					+ entity.getZdgrq1().replaceAll("-", "") + "','N')";
			execSql(sql);
		}
		return hashmap;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Map updateObjects(String s) {
		HashMap hashmap = new HashMap();
		String as[] = s.split(";");
		for (int i = 0; i < as.length; i++) {
			String sql = "update Zsdt0036 set zappr='Y' where mandt='"
					+ sapConfig.getClient() + "' and VBELN='00" + as[i].trim()
					+ "'";
			execSql(sql);
		}
		hashmap.put("state", "1");
		return hashmap;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Map findShipSche(String vbeln) {
		HashMap hashmap = new HashMap();
		List list = findListBySql("select zpzfzr,zbl,zmt,zcd,zhd,zhdc,zhdcw,zbz,DECODE(zwadat,'00000000','',to_char(to_date(zwadat,'YYYY-MM-DD'),'yyyy-MM-dd')) as zwadat,DECODE(zyjcq,'00000000','',to_char(to_date(zyjcq,'YYYY-MM-DD'),'yyyy-MM-dd')) as zyjcq,DECODE(zsbd,'00000000','',to_char(to_date(zsbd,'YYYY-MM-DD'),'yyyy-MM-dd')) as zsbd,DECODE(zdgrq,'00000000','',to_char(to_date(zdgrq,'YYYY-MM-DD'),'yyyy-MM-dd')) as zdgrq from Zsdt0036 where mandt='"
				+ sapConfig.getClient() + "' and vbeln='00" + vbeln + "'");
		if (list.isEmpty()) {
			hashmap.put("type", "E");
			hashmap.put("message", "此外向交货单不存在数据！");
		} else {
			hashmap.put("type", "S");
			Object[] arr = (Object[]) list.get(0);
			hashmap.put("zpzfzr", arr[0].toString());
			hashmap.put("zbl", arr[1].toString());
			hashmap.put("zmt", arr[2].toString());
			hashmap.put("zcd", arr[3].toString());
			hashmap.put("zhd", arr[4].toString());
			hashmap.put("zhdc", arr[5].toString());
			hashmap.put("zhdcw", arr[6].toString());
			hashmap.put("zbz", arr[7].toString());
			hashmap.put("zwadat", arr[8].toString());
			hashmap.put("zyjcq", arr[9].toString());
			hashmap.put("zsbd", arr[10].toString());
			hashmap.put("zdgrq", arr[11].toString());
		}
		return hashmap;
	}
}
