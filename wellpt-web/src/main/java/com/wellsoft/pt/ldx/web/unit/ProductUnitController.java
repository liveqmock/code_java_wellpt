package com.wellsoft.pt.ldx.web.unit;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wellsoft.pt.basicdata.sap.config.SAPDbConfig;
import com.wellsoft.pt.basicdata.sap.service.SAPRfcService;
import com.wellsoft.pt.basicdata.sap.util.SAPRfcJson;
import com.wellsoft.pt.ldx.service.ISapService;
import com.wellsoft.pt.ldx.util.StringUtils;
import com.wellsoft.pt.org.unit.service.UnitTreeService;

@Controller
@RequestMapping("/ldx/unit/product")
public class ProductUnitController {
	//产品类型选择
	private final String[] types = {"LED","CFL"};
	@Resource(name="sapConnectConfig")
	private SAPDbConfig sapConfig;
	@Autowired
	private UnitTreeService unitTreeService;
	@Autowired
	private ISapService sapService;
	@Autowired
	private SAPRfcService saprfcservice;

	@RequestMapping({ "/search/xml" })
	@ResponseBody
	public String searchXml(@RequestParam("optionType") String paramString1, @RequestParam("all") String paramString2,
			@RequestParam("searchValue") String paramString4,@RequestParam("jsonpCallback") String paramString5) {
		String str1 = "";
		try {
			String str2 = URLDecoder.decode(paramString4, "UTF-8");
			str1 = builtUnit(paramString1,str2).asXML();
		} catch (UnsupportedEncodingException localUnsupportedEncodingException) {
			localUnsupportedEncodingException.printStackTrace();
		}
		return convertToJsonp(paramString5, str1);
	}

	@RequestMapping({ "/type/xml" })
	@ResponseBody
	public String type(@RequestParam("jsonpCallback") String paramString) {
		String str = "<types></types>";
		try {
			str = parserType().asXML();
		} catch (Exception localException) {
			localException.printStackTrace();
		}
		return convertToJsonp(paramString, str);
	}

	@RequestMapping({ "/unit/xml" })
	@ResponseBody
	public String xml(@RequestParam("optionType") String paramString1, @RequestParam("all") String paramString2,
			@RequestParam("jsonpCallback") String paramString4) {
		String str = builtUnit(paramString1,"").asXML();
		return convertToJsonp(paramString4, str);
	}

	@RequestMapping({ "/leaf/unit/xml" })
	@ResponseBody
	public String leaf(@RequestParam("optionType") String paramString1, @RequestParam("id") String paramString2,
			@RequestParam("type") String paramString3, @RequestParam("login") String paramString4,
			@RequestParam("jsonpCallback") String paramString5) {
		String str = "";
		Document localDocument = this.unitTreeService.leafUnit(paramString1, paramString2, paramString3, paramString4);
		if (localDocument != null)
			str = localDocument.asXML();
		return convertToJsonp(paramString5, str);
	}
	
	@RequestMapping("/search/getLondDesc")
	public void getLondDesc(@RequestParam("id") String id,HttpServletResponse response) throws UnsupportedEncodingException, IOException{
		StringBuffer desc = new StringBuffer();
		String[] ids = id.split(";");
		for(String matnr:ids){
			desc.append(";").append(getDescById(matnr));
		}
		response.getOutputStream().write(desc.toString().replaceFirst(";","").getBytes("UTF-8"));
	}

	private String convertToJsonp(String paramString1, String paramString2) {
		JSONObject localJSONObject = new JSONObject();
		localJSONObject.put("xml", paramString2);
		return paramString1 + "(" + localJSONObject.toString().replace("'", "\"") + ");";
	}
	
	private Document parserType(){
	    Document localDocument = DocumentHelper.createDocument();
	    Element localElement1 = localDocument.addElement("types");
	    for(String type:types){
	    	Element localElement2 = localElement1.addElement("type");
	    	localElement2.addAttribute("id", type);
	        localElement2.addText(type);
	    }
	    return localDocument;
	  }
	
	private Document builtUnit(String type,String conditions){
		 Document localDocument = DocumentHelper.createDocument();
		    Element localElement1 = localDocument.addElement("units");
		    List<Object> list = findProductList(type,conditions);
		    for(Object obj:list){
		    	Object[] objects = (Object[])obj;
		    	Element localElement2 = localElement1.addElement("unit");
		    	localElement2.addAttribute("type", "1");
		    	localElement2.addAttribute("isLeaf", "1");
		    	localElement2.addAttribute("id", StringUtils.nullToString(objects[0]));
		    	localElement2.addAttribute("name", StringUtils.nullToString(objects[1]));
		    }
		    return localDocument;
	}
	
	private List<Object> findProductList(String type,String conditions){
		StringBuffer query = new StringBuffer();
		query.append("select substr(matnr,9) as matnr,maktx from makt where spras='1' and matnr like '000000001%' ")
			.append(" and maktx like '%"+type.toUpperCase()+"%' and mandt=").append(sapConfig.getClient());
		boolean isMatnr=true;
		try {
			Long.parseLong(conditions.trim());
			query.append(" and matnr like '%").append(conditions.trim()).append("%'");
		} catch (Exception e) {
			isMatnr=false;
		}
		if(StringUtils.isNotBlank(conditions)&&!isMatnr){
			String[] condArray = conditions.split(" ");
			for(String cond:condArray){
				if(StringUtils.isNotBlank(cond)){
					query.append(" and maktx like '%").append(cond.trim()).append("%'");
				}
			}
		}
		query.append(" and rownum<100");
		return sapService.findListBySql(query.toString());
	}
	
	/**
	 * 
	 * 根据单个ID获取长描述
	 * 
	 * @param id
	 * @return
	 */
	private String getDescById(String id){
		JSONObject jsonObject = new JSONObject();
		jsonObject.element("ID","GRUN");
		jsonObject.element("LANGUAGE","1");
		jsonObject.element("OBJECT","MATERIAL");
		jsonObject.element("NAME",StringUtils.addLeftZero(id.trim(),18));
		try {
			SAPRfcJson rfcjson = saprfcservice.RFC("sapConnectConfig","ZREAD_TEXT1", jsonObject.toString(), 1, -1, null);
			JSONObject linesJsonObject = rfcjson.getRecord("LINES");
			if(null!=linesJsonObject){
				JSONArray rows = linesJsonObject.getJSONArray("row");
				if(rows.size()>0){
					JSONObject obj = (JSONObject) rows.get(0);
					return obj.get("TDLINE").toString();
				}
			}
		} catch (Exception e) {
		}
		return "";
	}
}