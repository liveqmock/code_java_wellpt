package com.wellsoft.pt.ldx.web;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.wellsoft.pt.core.web.BaseController;
import com.wellsoft.pt.ldx.model.cooling.CoolingModel;
import com.wellsoft.pt.ldx.util.StringUtils;

@Controller
@Scope("prototype")
@RequestMapping("/plm/coolingArea")
public class CoolingAreaController extends BaseController{
	private static final long serialVersionUID = 1L;
	@RequestMapping("/init")
	public String init() throws Exception {

		return forward("/pt/cooling/coolingArea");
	}
	/**
	 * 计算散热面积及换热系数
	 * @throws IOException 
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value = "/coolingArea", method = RequestMethod.POST)
	protected void calculateCoolingArea(HttpServletRequest request, HttpServletResponse response
			,@RequestParam(value = "coolType")String coolType,@RequestParam(value = "hi")String hi,@RequestParam(value = "tc")String tc
			,@RequestParam(value = "wi")String wi,@RequestParam(value = "yi")String yi,@RequestParam(value = "diMin")String diMin
			,@RequestParam(value = "diMax")String diMax) throws UnsupportedEncodingException, IOException{
		Map<String, Object> map = new HashMap<String, Object>();
		String error = checkParam(hi,tc,wi,yi, diMin, diMax, coolType);
		if(StringUtils.isNotBlank(error)){
			map.put("result", "fail");
			map.put("error", error);
		}else{
			CoolingModel model = new CoolingModel();
			model.setCoolType(Integer.parseInt(coolType));
			model.setHi(Double.parseDouble(hi));
			model.setTc(Double.parseDouble(tc));
			model.setWi(Double.parseDouble(wi));
			model.setYi(Double.parseDouble(yi));
			if("2".equals(StringUtils.nullToString(coolType))){
				model.setDiMax(Double.parseDouble(diMax));
				model.setDiMin(Double.parseDouble(diMin));
			}
			map.put("result", "success");
			map.put("error", "");
			map.put("h2",model.getH2());
			map.put("tak",model.getTak());
			map.put("t1",model.getT1());
			map.put("tm",model.getTm());
			map.put("t1k",model.getT1k());
			if("2".equals(StringUtils.nullToString(coolType))){
				map.put("s1",model.getS1());
			}
			map.put("pf",model.getPf());
			map.put("fc",model.getFc());
			map.put("fcFinal",model.getFcFinal());
			map.put("fa",model.getFa());
			map.put("faFinal",model.getFaFinal());
		}
		JSONArray ja = JSONArray.fromObject(map);
		response.getWriter().print(ja.toString());
	}
	
	/**
	 * 校验参数
	 * @return
	 */
	private String checkParam(String hi,String tc,String wi,String yi,String diMin
			,String diMax,String coolType){
		try {
			Double.parseDouble(hi);
		} catch (Exception e) {
			return "散热件高度格式错误!";
		}
		try {
			Double.parseDouble(tc);
		} catch (Exception e) {
			return "基板温度格式错误!";
		}
		try {
			Double.parseDouble(wi);
		} catch (Exception e) {
			return "输入功率格式错误!";
		}
		try {
			Double.parseDouble(yi);
		} catch (Exception e) {
			return "驱动效率格式错误!";
		}
		if("2".equals(StringUtils.nullToString(coolType))){
			try {
				Double.parseDouble(diMin);
			} catch (Exception e) {
				return "最小外径格式错误!";
			}
			try {
				Double.parseDouble(diMax);
			} catch (Exception e) {
				return "最大外径格式错误!";
			}
		}
		return null;
	}
	
	/**
	 * 计算预估寿命
	 * @throws UnsupportedEncodingException
	 * @throws IOException
	 */
	@RequestMapping(value = "/lifeTime", method = RequestMethod.POST)
	protected void calculateLifeTime(HttpServletRequest request, HttpServletResponse response
			,@RequestParam(value = "ti")String ti,@RequestParam(value = "gi")String gi) throws UnsupportedEncodingException, IOException{
		Map<String, Object> map = new HashMap<String, Object>();
		String error = checkLiftTimeParam(ti,gi);
		if(StringUtils.isNotBlank(error)){
			map.put("result", "fail");
			map.put("error", error);
		}else{
			Double timeIn = Double.parseDouble(ti);
			Double gtIn = Double.parseDouble(gi);
			try {
				//衰减系数=LN(光通维持率)/跟踪时间*10^5
				Double so = Math.log(gtIn) * 1E5 / timeIn;
				//LM70时间=LN(0.7)/衰减系数*10
				Double lo = Math.log(0.7D) * 10 / so;
				BigDecimal soFinal = new BigDecimal(so);
				BigDecimal loFinal = new BigDecimal(lo);
				BigDecimal one = new BigDecimal(1);
				map.put("so",soFinal.divide(one, 4, BigDecimal.ROUND_HALF_UP).doubleValue());
				map.put("lo",loFinal.divide(one, 2, BigDecimal.ROUND_HALF_UP).doubleValue());
				map.put("result", "success");
				map.put("error", "");
			} catch (Exception e) {
				map.put("result", "fail");
				map.put("error", "当前参数无法计算出正确结果!");
			}
		}
		JSONArray ja = JSONArray.fromObject(map);
		response.getWriter().print(ja.toString());
	}
	
	public String checkLiftTimeParam(String ti,String gi){
		try {
			Double.parseDouble(ti);
		} catch (Exception e) {
			return "跟踪时间格式错误!";
		}
		try {
			Double.parseDouble(gi);
		} catch (Exception e) {
			return "光通维持率格式错误!";
		}
		return null;
	}

}
