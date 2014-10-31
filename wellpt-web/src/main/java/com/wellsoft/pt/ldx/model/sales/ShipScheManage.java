package com.wellsoft.pt.ldx.model.sales;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;

public class ShipScheManage implements Serializable {
	// serialVersionUID
	private static final long serialVersionUID = 1L;
	private String vbeln;
	private String zgbh;
	private String bukrs;
	private String sortl;
	private String zbstkd;
	private String wadat;
	private String z103;
	private String z104;
	private String z108;
	private String land1;
	private String cny;
	private String usd;
	private String zccrq;
	private String zaa;
	private String zgxsj;
	private String zggr;
	private String zwadat;
	private String zpzfzr;
	private String zyjcq;
	private String zbl;
	private String zmt;
	private String zcd;
	private String zhd;
	private String zhdc;
	private String zhdcw;
	private String zsbd;
	private String zbz;
	private String zdgrq;
	private String zappr;

	public String getVbeln() {
		return vbeln;
	}

	public void setVbeln(String vbeln) {
		this.vbeln = vbeln;
	}

	public String getZgbh() {
		return zgbh;
	}

	public void setZgbh(String zgbh) {
		this.zgbh = zgbh;
	}

	public String getBukrs() {
		return bukrs;
	}

	public void setBukrs(String bukrs) {
		this.bukrs = bukrs;
	}

	public String getSortl() {
		return sortl;
	}

	public void setSortl(String sortl) {
		this.sortl = sortl;
	}

	public String getZbstkd() {
		return zbstkd;
	}

	public void setZbstkd(String zbstkd) {
		this.zbstkd = zbstkd;
	}

	public String getWadat() {
		return wadat;
	}

	public void setWadat(String wadat) {
		this.wadat = wadat;
	}

	public String getZ103() {
		return z103;
	}

	public void setZ103(String z103) {
		this.z103 = z103;
	}

	public String getZ104() {
		return z104;
	}

	public void setZ104(String z104) {
		this.z104 = z104;
	}

	public String getZ108() {
		return z108;
	}

	public void setZ108(String z108) {
		this.z108 = z108;
	}

	public String getLand1() {
		return land1;
	}

	public void setLand1(String land1) {
		this.land1 = land1;
	}

	public String getCny() {
		return cny;
	}

	public void setCny(String cny) {
		this.cny = cny;
	}

	public String getUsd() {
		return usd;
	}

	public void setUsd(String usd) {
		this.usd = usd;
	}

	public String getZccrq() {
		return zccrq;
	}

	public void setZccrq(String zccrq) {
		this.zccrq = zccrq;
	}

	public String getZaa() {
		return zaa;
	}

	public void setZaa(String zaa) {
		this.zaa = zaa;
	}

	public String getZgxsj() {
		return zgxsj;
	}
	
	public String getZgxsj1() {
		if (StringUtils.isEmpty(zgxsj)) {
			zgxsj = "00000000";
		}
		return zgxsj;
	}

	public void setZgxsj(String zgxsj) {
		this.zgxsj = zgxsj;
	}

	public String getZggr() {
		if (StringUtils.isEmpty(zggr)) {
			zggr = " ";
		}
		return zggr;
	}

	public void setZggr(String zggr) {
		this.zggr = zggr;
	}

	public String getZwadat() {
		return zwadat;
	}
	
	public String getZwadat1() {
		if (StringUtils.isEmpty(zwadat)) {
			zwadat = "00000000";
		}
		return zwadat;
	}

	public void setZwadat(String zwadat) {
		this.zwadat = zwadat;
	}

	public String getZpzfzr() {
		if (StringUtils.isEmpty(zpzfzr)) {
			zpzfzr = " ";
		}
		return zpzfzr;
	}

	public void setZpzfzr(String zpzfzr) {
		this.zpzfzr = zpzfzr;
	}

	public String getZyjcq() {
		return zyjcq;
	}
	
	public String getZyjcq1() {
		if (StringUtils.isEmpty(zyjcq)) {
			zyjcq = "00000000";
		}
		return zyjcq;
	}

	public void setZyjcq(String zyjcq) {
		this.zyjcq = zyjcq;
	}

	public String getZbl() {
		if (StringUtils.isEmpty(zbl)) {
			zbl = " ";
		}
		return zbl;
	}

	public void setZbl(String zbl) {
		this.zbl = zbl;
	}

	public String getZmt() {
		if (StringUtils.isEmpty(zmt)) {
			zmt = " ";
		}
		return zmt;
	}

	public void setZmt(String zmt) {
		this.zmt = zmt;
	}

	public String getZcd() {
		if (StringUtils.isEmpty(zcd)) {
			zcd = " ";
		}
		return zcd;
	}

	public void setZcd(String zcd) {
		this.zcd = zcd;
	}

	public String getZhd() {
		if (StringUtils.isEmpty(zhd)) {
			zhd = " ";
		}
		return zhd;
	}

	public void setZhd(String zhd) {
		this.zhd = zhd;
	}

	public String getZhdc() {
		if (StringUtils.isEmpty(zhdc)) {
			zhdc = " ";
		}
		return zhdc;
	}

	public void setZhdc(String zhdc) {
		this.zhdc = zhdc;
	}

	public String getZhdcw() {
		if (StringUtils.isEmpty(zhdcw)) {
			zhdcw = " ";
		}
		return zhdcw;
	}

	public void setZhdcw(String zhdcw) {
		this.zhdcw = zhdcw;
	}

	public String getZsbd() {
		return zsbd;
	}
	
	public String getZsbd1() {
		if (StringUtils.isEmpty(zsbd)) {
			zsbd = "00000000";
		}
		return zsbd;
	}

	public void setZsbd(String zsbd) {
		this.zsbd = zsbd;
	}

	public String getZbz() {
		if (StringUtils.isEmpty(zbz)) {
			zbz = " ";
		}
		return zbz;
	}

	public void setZbz(String zbz) {
		this.zbz = zbz;
	}

	public String getZdgrq() {
		return zdgrq;
	}
	
	public String getZdgrq1() {
		if (StringUtils.isEmpty(zdgrq)) {
			zdgrq = "00000000";
		}
		return zdgrq;
	}

	public void setZdgrq(String zdgrq) {
		this.zdgrq = zdgrq;
	}

	public String getZappr() {
		if (StringUtils.isEmpty(zappr)) {
			zappr = "N";
		}
		return zappr;
	}

	public void setZappr(String zappr) {
		this.zappr = zappr;
	}

}
