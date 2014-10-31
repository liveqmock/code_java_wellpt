package com.wellsoft.pt.ldx.service.order.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wellsoft.pt.ldx.model.orderManage.Zsdt0050;
import com.wellsoft.pt.ldx.service.impl.SapServiceImpl;
import com.wellsoft.pt.ldx.service.order.OrderSearchService;
import com.wellsoft.pt.ldx.util.StringUtils;

@Service
@Transactional
@SuppressWarnings("rawtypes")
public class OrderSearchServiceImpl extends SapServiceImpl implements OrderSearchService{
	@Override
	public void updateZsdt0050(Zsdt0050 param){
		if(null==param || StringUtils.isBlank(param.getVbeln()))return;
		StringBuffer update = new StringBuffer("update zsdt0050 set vbeln=vbeln");
		update.append(",zpono = '").append(StringUtils.isBlank(param.getZpono())?" ":param.getZpono().trim()).append("'");//PONO
		update.append(",zhfkr = '").append(StringUtils.isBlank(param.getZhfkr())?"00000000":param.getZhfkr().trim().replaceAll("-","")).append("'");//回复客人交期
		update.append(",krpch = '").append(StringUtils.isBlank(param.getKrpch())?" ":param.getKrpch().trim()).append("'");//客人批次号
		update.append(",zsjtg = '").append(StringUtils.isBlank(param.getZsjtg())?"00000000":param.getZsjtg().trim().replaceAll("-","")).append("'");//塑件提供日期
		update.append(",zbzzd = '").append(StringUtils.isBlank(param.getZbzzd())?"00000000":param.getZbzzd().trim().replaceAll("-","")).append("'");//包装指导提供日期
		update.append(",zyhrq = '").append(StringUtils.isBlank(param.getZyhrq())?"00000000":param.getZyhrq().trim().replaceAll("-","")).append("'");//验货日期
		update.append(",zyhjg = '").append(StringUtils.isBlank(param.getZyhjg())?" ":param.getZyhjg().trim()).append("'");//验货结果
		update.append(",zyhbz = '").append(StringUtils.isBlank(param.getZyhbz())?" ":param.getZyhbz().trim()).append("'");//验货备注
		update.append(",chwsbz= '").append(StringUtils.isBlank(param.getChwsbz())?" ":param.getChwsbz().trim()).append("'");//出货尾数备注
		update.append(",zchsl = '").append(StringUtils.isBlank(param.getZchsl())?" ":param.getZchsl().trim()).append("'");//尾数预计出货数量
		update.append(",zchrq = '").append(StringUtils.isBlank(param.getZchrq())?"00000000":param.getZchrq().trim().replaceAll("-","")).append("'");//尾数预计出货日期
		update.append(",zzbgd = '").append(StringUtils.isBlank(param.getZzbgd())?" ":param.getZzbgd().trim()).append("'");//报关单
		update.append(",zbz   = '").append(StringUtils.isBlank(param.getZbz())?" ":param.getZbz().trim()).append("'");//备注
		update.append(",omchjh= '").append(StringUtils.isBlank(param.getOmchjh())?"00000000":param.getOmchjh().trim().replaceAll("-","")).append("'");//OM出货计划提供日期
		update.append(",zyqbm = '").append(StringUtils.isBlank(param.getZyqbm())?" ":param.getZyqbm().trim()).append("'");//逾期责任部门
		update.append(",zycdl = '").append(StringUtils.isBlank(param.getZycdl())?" ":param.getZycdl().trim()).append("'");//逾期异常大类
		update.append(",zyqbz = '").append(StringUtils.isBlank(param.getZyqbz())?" ":param.getZyqbz().trim()).append("'");//逾期备注
		update.append(",xmdyb = '").append(StringUtils.isBlank(param.getXmdyb())?"00000000":param.getXmdyb().trim().replaceAll("-","")).append("'");//各项目对应表提供日期
		update.append(" where mandt=").append(getClient()).append(" and vbeln='").append(StringUtils.addLeftZero(param.getVbeln(),10)).append("'");
		execSql(update.toString());
	}
	
	public void update(String vbeln,String pono,String xmdyb,String zhfkr,String krpch,String zsjtg,String zbzzd,String zyhrq,String zyhjg,
			String zyhbz,String zbz,String zzbgd,String zchsl,String zchrq,String chwsbz,String omchjh,String zyqbm,String zycdl,String zyqbz){
		Map<String,String> map = new HashMap<String,String>();
		Zsdt0050 param = new Zsdt0050();
		param.setVbeln(vbeln);
		param.setZpono(pono);
		param.setZhfkr(zhfkr);
		param.setKrpch(krpch);
		param.setZsjtg(zsjtg);
		param.setZbzzd(zbzzd);
		param.setZyhrq(zyhrq);
		param.setZyhjg(zyhjg);
		param.setZyhbz(zyhbz);
		param.setChwsbz(chwsbz);
		param.setZchsl(zchsl);
		param.setZchrq(zchrq);
		param.setZzbgd(zzbgd);
		param.setZbz(zbz);
		param.setOmchjh(omchjh);
		param.setZyqbm(zyqbm);
		param.setZycdl(zycdl);
		param.setXmdyb(xmdyb);
		String message = "保存成功";
		try {
			boolean updatePosnrs = false;
			updateZsdt0050(param);
			String updateOrderPosnr = "update zsdt0049 set mandt=mandt";
			if(StringUtils.isNotBlank(zhfkr)){
				updateOrderPosnr += " ,zhfkr='"+(StringUtils.isBlank(param.getZhfkr())?"00000000":param.getZhfkr().trim().replaceAll("-",""))+"'";
				updatePosnrs = true;
			}
			if(StringUtils.isNotBlank(omchjh)){
				updateOrderPosnr += " ,omchjh='"+(StringUtils.isBlank(param.getOmchjh())?"00000000":param.getOmchjh().trim().replaceAll("-",""))+"'";
				updatePosnrs = true;
			}
			if(StringUtils.isNotBlank(zyhrq)){
				updateOrderPosnr += " ,zyhrq='"+(StringUtils.isBlank(param.getZyhrq())?"00000000":param.getZyhrq().trim().replaceAll("-",""))+"'";
				updatePosnrs = true;
			}
			if(StringUtils.isNotBlank(zyhjg)){
				updateOrderPosnr += " ,zyhjg='"+(StringUtils.isBlank(param.getZyhjg())?" ":param.getZyhjg().trim())+"'";
				updatePosnrs = true;
			}
			if(StringUtils.isNotBlank(zyqbm)){
				updateOrderPosnr += " ,zyqbm='"+(StringUtils.isBlank(param.getZyqbm())?" ":param.getZyqbm().trim())+"'";
				updatePosnrs = true;
			}
			if(StringUtils.isNotBlank(zycdl)){
				updateOrderPosnr += " ,zycdl='"+(StringUtils.isBlank(param.getZycdl())?" ":param.getZycdl().trim())+"'";
				updatePosnrs = true;
			}
			if(StringUtils.isNotBlank(zyqbz)){
				updateOrderPosnr += " ,zyqbz='"+(StringUtils.isBlank(param.getZyqbz())?" ":param.getZyqbz().trim())+"'";
				updatePosnrs = true;
			}
			if(StringUtils.isNotBlank(xmdyb)){
				updateOrderPosnr += " ,zdate='"+(StringUtils.isBlank(param.getXmdyb())?"00000000":param.getXmdyb().trim().replaceAll("-",""))+"'";
				updatePosnrs = true;
			}
			updateOrderPosnr += " where mandt="+getClient()+" and vbeln='"+param.getVbeln()+"'";
			if(updatePosnrs){
				execSql(updateOrderPosnr);
				map.put("res","success");
				map.put("msg",message);
			}
		} catch (Exception e) {
			message = "保存失败" + e.getMessage();
			map.put("res","fail");
			map.put("msg",message);
		}
		
	}
}
