package com.wellsoft.pt.ldx.web.sales;

import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.wellsoft.pt.basicdata.sap.config.SAPDbConfig;
import com.wellsoft.pt.basicdata.sap.service.SAPRfcService;
import com.wellsoft.pt.basicdata.sap.util.SAPRfcJson;
import com.wellsoft.pt.core.web.BaseController;
import com.wellsoft.pt.ldx.model.sales.ShipScheManage;
import com.wellsoft.pt.ldx.service.ISapService;

@Controller
@Scope("prototype")
@RequestMapping("/sales")
public class ShipScheController extends BaseController {

	@Autowired
	private ISapService sapService;

	@Resource(name="sapConnectConfig")
	private SAPDbConfig sapConfig;

	@Autowired
	private SAPRfcService saprfcservice;

	@RequestMapping("/preAddShipSche")
	public String search(@RequestParam(value = "vbeln") String vbeln,
			Model model) {
		List<Object> list = sapService
				.findListBySql("select distinct substr(zsdt0016.VBELN,3), zsdt0016.ZGBH, zsdt0016.BUKRS, zsdt0016.SORTL,'' as s1,DECODE(LIKP.WADAT,'00000000','',to_char(to_date(LIKP.WADAT,'YYYY-MM-DD'),'yyyy-MM-dd')),'' as s2,'' as s3,'' as s4, kna1.land1,DECODE(likp.WADAT_IST,'00000000','',to_char(to_date(likp.WADAT_IST,'YYYY-MM-DD'),'yyyy-MM-dd')) as t3,tvv2t.bezei, (select sum(zfct.dmbtr) from ZFCT000702 zfct where zfct.vbeln=likp.VBELN and zfct.waerk='CNY' and zfct.mandt=zsdt0016.mandt) as cny, (select sum(zfct.dmbtr) from ZFCT000702 zfct where zfct.vbeln=likp.VBELN and zfct.waerk='USD' and zfct.mandt=zsdt0016.mandt) as usd, zsdt0036.zggr,DECODE(zsdt0036.Zgxsj,'00000000','',to_char(to_date(zsdt0036.Zgxsj,'YYYY-MM-DD'),'yyyy-MM-dd')) as t4, zsdt0036.zpzfzr,zsdt0036.zbl,zsdt0036.zmt,zsdt0036.zcd,zsdt0036.zhd,zsdt0036.zhdc,zsdt0036.zhdcw,zsdt0036.zbz, DECODE(zsdt0036.zwadat,'00000000','',to_char(to_date(zsdt0036.zwadat,'YYYY-MM-DD'),'yyyy-MM-dd')) as t5, DECODE(zsdt0036.zyjcq,'00000000','',to_char(to_date(zsdt0036.zyjcq,'YYYY-MM-DD'),'yyyy-MM-dd')) as t6, DECODE(zsdt0036.zsbd,'00000000','',to_char(to_date(zsdt0036.zsbd,'YYYY-MM-DD'),'yyyy-MM-dd')) as t7, DECODE(zsdt0036.zdgrq,'00000000','',to_char(to_date(zsdt0036.zdgrq,'YYYY-MM-DD'),'yyyy-MM-dd')) as t8, zsdt0036.zappr from zsdt0016 zsdt0016 inner join LIKP likp on zsdt0016.VBELN = likp.vbeln and zsdt0016.mandt=likp.mandt left join zsdt0036 zsdt0036 on zsdt0016.Vbeln= zsdt0036.vbeln and zsdt0016.mandt=zsdt0036.mandt left join kna1 kna1 on likp.kunnr=kna1.kunnr and zsdt0016.mandt=kna1.MANDT left join knvv knvv on likp.vkorg=knvv.vkorg and likp.KUNAG=knvv.Kunnr and zsdt0016.mandt=knvv.MANDT left join TVV2T tvv2t on knvv.kvgr2=tvv2t.kvgr2 and zsdt0016.mandt=tvv2t.MANDT where zsdt0016.MANDT='"
						+ sapConfig.getClient()
						+ "' AND zsdt0016.vbeln='00"
						+ vbeln + "'");
		ShipScheManage manage = new ShipScheManage();
		if (!list.isEmpty()) {
			Object object = list.get(0);
			Object[] oArr = (Object[]) object;
			manage.setVbeln(object2String(oArr[0]));
			manage.setZgbh(object2String(oArr[1]));
			manage.setBukrs(object2String(oArr[2]));
			manage.setSortl(object2String(oArr[3]));
			manage.setWadat(object2String(oArr[5]));
			manage.setLand1(object2String(oArr[9]));
			manage.setZccrq(object2String(oArr[10]));
			manage.setZaa(object2String(oArr[11]));
			manage.setCny(object2String(oArr[12]));
			manage.setUsd(object2String(oArr[13]));
			manage.setZggr(object2String(oArr[14]));
			manage.setZgxsj(object2String(oArr[15]));
			manage.setZpzfzr(object2String(oArr[16]));
			manage.setZbl(object2String(oArr[17]));
			manage.setZmt(object2String(oArr[18]));
			manage.setZcd(object2String(oArr[19]));
			manage.setZhd(object2String(oArr[20]));
			manage.setZhdc(object2String(oArr[21]));
			manage.setZhdcw(object2String(oArr[22]));
			manage.setZbz(object2String(oArr[23]));
			manage.setZwadat(object2String(oArr[24]));
			manage.setZyjcq(object2String(oArr[25]));
			manage.setZsbd(object2String(oArr[26]));
			manage.setZdgrq(object2String(oArr[27]));
			manage.setZappr(object2String(oArr[28]));
			SAPRfcJson util = new SAPRfcJson("");
			JSONObject rowObject = new JSONObject();
			JSONArray jObjects = new JSONArray();
			JSONObject jObject = new JSONObject();
			jObject.element("VBELN", "00" + manage.getVbeln());
			jObjects.add(jObject);
			rowObject.element("row", jObjects);
			util.setRecord("PT_INPUT", rowObject.toString());
			SAPRfcJson rfcjson = saprfcservice.RFC("sapConnectConfig",
					"ZSDI0010", util.getRfcJson(), 1, -1, null);
			JSONObject o = rfcjson.getRecord("PT_OUTPUT");
			JSONArray jObs = o.getJSONArray("row");
			for (Object ob : jObs) {
				JSONObject returnObject = (JSONObject) ob;
				if (manage.getVbeln().equals(
						returnObject.getString("VBELN").substring(2))) {
					manage.setZbstkd(returnObject.getString("ZBSTKD"));
					manage.setZ103(returnObject.getString("Z103"));
					manage.setZ104(returnObject.getString("Z104"));
					manage.setZ108(returnObject.getString("Z108"));

				}
			}
			model.addAttribute("entity", manage);
		}
		return forward("/ldx/sales/addShipSche");
	}

	private String object2String(Object object) {
		String s = "";
		if (null != object)
			s = object.toString();
		return s;
	}
}
