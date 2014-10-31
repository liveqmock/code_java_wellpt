package com.wellsoft.pt.ldx.model.orderManage;

import java.io.Serializable;
/**
 * 订单行项目明细自建表
 * @author shangguanzc
 *
 */
public class Zsdt0049 implements Serializable{
	private static final long serialVersionUID = 1L;
	/**
	 * 客户端
	 */
	private String mandt;
	/**
	 * 销售凭证：SAP订单号
	 */
	private String vbeln;
	/**
	 * 销售凭证项目：行项目号
	 */
	private String posnr;
	/**
	 * 回复客人交期（手动维护，可批改）
	 */
	private String zhfkr;
	/**
	 * 预计入库时间（SAP同步过来）
	 */
	private String zyjrk;
	/**
	 * 验货日期（手动维护）
	 */
	private String zyhrq;
	/**
	 * 验货结果（手动维护）
	 */
	private String zyhjg;
	/**
	 * 验货备注（手动维护）
	 */
	private String zyhbz;
	/**
	 * 尾数预计出货数量（手动维护）
	 */
	private String zchsl;
	/**
	 * 尾数预计出货日期（手动维护）
	 */
	private String zchrq;
	/**
	 * 是否逾期（公式：如果行项目zsdt0037-EDATU<today()，则是否逾期="是",否则"否"）/SAP同步计算
	 */
	private String zsfyq;
	/**
	 * 逾期责任部门（手动维护）
	 */
	private String zyqbm;
	/**
	 * 逾期异常大类（手动维护）
	 */
	private String zycdl;
	/**
	 * 逾期备注（手动维护）
	 */
	private String zyqbz;
	/**
	 * 订单状态（SAP程序每半天运行一次计算状态）
	 */
	private String zddzt;
	/**
	 * 	OM提供出货计划日期（手动维护，可批改）
	 */
	private String omchjh;
	//评审状态(SAP定时任务更新)
	private String zddps;
	//物料号（SAP同步）
	private String matnr;
	//物料描述（SAP同步）
	private String maktx;
	//订单数量（SAP同步）
	private String kwmeng;
	//订单数量单位（SAP同步）
	private String vrkme;
	//金额（SAP同步）
	private String netpr;
	//客户物料编码（SAP同步）
	private String kdmat;
	//加工厂（SAP同步）
	private String werks;
	//订单签发日期（SAP同步）
	private String zpsdat3;
	//订单交期（SAP同步）
	private String edatu;
	//最终交期（SAP同步）
	private String zdats;
	//各项目对应表提供日期（SAP同步）
	private String zdate;
	//生产订单数量（SAP同步）
	private String psmng;
	//最后一次出货日期（SAP同步）
	private String wadat_ist;
	//出货尾数（SAP同步）
	private String lfimg;
	//冲销标识（SAP同步）
	private String zcxbs;
	//装柜日期(查询用)
	private String zgrq;
	//船交期(查询用)
	private String cjq;
	
	public String getMandt() {
		return mandt;
	}
	public void setMandt(String mandt) {
		this.mandt = mandt;
	}
	public String getVbeln() {
		return vbeln;
	}
	public void setVbeln(String vbeln) {
		this.vbeln = vbeln;
	}
	public String getPosnr() {
		return posnr;
	}
	public void setPosnr(String posnr) {
		this.posnr = posnr;
	}
	public String getZhfkr() {
		return zhfkr;
	}
	public void setZhfkr(String zhfkr) {
		this.zhfkr = zhfkr;
	}
	public String getZyjrk() {
		return zyjrk;
	}
	public void setZyjrk(String zyjrk) {
		this.zyjrk = zyjrk;
	}
	public String getZyhrq() {
		return zyhrq;
	}
	public void setZyhrq(String zyhrq) {
		this.zyhrq = zyhrq;
	}
	public String getZyhjg() {
		return zyhjg;
	}
	public void setZyhjg(String zyhjg) {
		this.zyhjg = zyhjg;
	}
	public String getZyhbz() {
		return zyhbz;
	}
	public void setZyhbz(String zyhbz) {
		this.zyhbz = zyhbz;
	}
	public String getZchsl() {
		return zchsl;
	}
	public void setZchsl(String zchsl) {
		this.zchsl = zchsl;
	}
	public String getZchrq() {
		return zchrq;
	}
	public void setZchrq(String zchrq) {
		this.zchrq = zchrq;
	}
	public String getZsfyq() {
		return zsfyq;
	}
	public void setZsfyq(String zsfyq) {
		this.zsfyq = zsfyq;
	}
	public String getZyqbm() {
		return zyqbm;
	}
	public void setZyqbm(String zyqbm) {
		this.zyqbm = zyqbm;
	}
	public String getZycdl() {
		return zycdl;
	}
	public void setZycdl(String zycdl) {
		this.zycdl = zycdl;
	}
	public String getZyqbz() {
		return zyqbz;
	}
	public void setZyqbz(String zyqbz) {
		this.zyqbz = zyqbz;
	}
	public String getZddzt() {
		return zddzt;
	}
	public void setZddzt(String zddzt) {
		this.zddzt = zddzt;
	}
	public String getOmchjh() {
		return omchjh;
	}
	public void setOmchjh(String omchjh) {
		this.omchjh = omchjh;
	}
	public String getZddps() {
		return zddps;
	}
	public void setZddps(String zddps) {
		this.zddps = zddps;
	}
	public String getMatnr() {
		return matnr;
	}
	public void setMatnr(String matnr) {
		this.matnr = matnr;
	}
	public String getMaktx() {
		return maktx;
	}
	public void setMaktx(String maktx) {
		this.maktx = maktx;
	}
	public String getKwmeng() {
		return kwmeng;
	}
	public void setKwmeng(String kwmeng) {
		this.kwmeng = kwmeng;
	}
	public String getVrkme() {
		return vrkme;
	}
	public void setVrkme(String vrkme) {
		this.vrkme = vrkme;
	}
	public String getNetpr() {
		return netpr;
	}
	public void setNetpr(String netpr) {
		this.netpr = netpr;
	}
	public String getKdmat() {
		return kdmat;
	}
	public void setKdmat(String kdmat) {
		this.kdmat = kdmat;
	}
	public String getWerks() {
		return werks;
	}
	public void setWerks(String werks) {
		this.werks = werks;
	}
	public String getZpsdat3() {
		return zpsdat3;
	}
	public void setZpsdat3(String zpsdat3) {
		this.zpsdat3 = zpsdat3;
	}
	public String getEdatu() {
		return edatu;
	}
	public void setEdatu(String edatu) {
		this.edatu = edatu;
	}
	public String getZdats() {
		return zdats;
	}
	public void setZdats(String zdats) {
		this.zdats = zdats;
	}
	public String getZdate() {
		return zdate;
	}
	public void setZdate(String zdate) {
		this.zdate = zdate;
	}
	public String getPsmng() {
		return psmng;
	}
	public void setPsmng(String psmng) {
		this.psmng = psmng;
	}
	public String getWadat_ist() {
		return wadat_ist;
	}
	public void setWadat_ist(String wadat_ist) {
		this.wadat_ist = wadat_ist;
	}
	public String getLfimg() {
		return lfimg;
	}
	public void setLfimg(String lfimg) {
		this.lfimg = lfimg;
	}
	public String getZcxbs() {
		return zcxbs;
	}
	public void setZcxbs(String zcxbs) {
		this.zcxbs = zcxbs;
	}
	public String getZgrq() {
		return zgrq;
	}
	public void setZgrq(String zgrq) {
		this.zgrq = zgrq;
	}
	public String getCjq() {
		return cjq;
	}
	public void setCjq(String cjq) {
		this.cjq = cjq;
	}
	
}
