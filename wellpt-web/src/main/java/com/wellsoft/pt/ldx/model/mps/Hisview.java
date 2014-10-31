package com.wellsoft.pt.ldx.model.mps;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.wellsoft.pt.core.entity.IdEntity;

@Entity
@Table(name = "HISZHDENG")
@DynamicUpdate
@DynamicInsert
public class Hisview extends IdEntity{
    private static final long serialVersionUID = 1L;
    
    private String  showdate;//日期
    private String sailorder ;//销售订单号
    private String lineitem ;
    private String tlineitem ;
    private String porder ;
    private String border ;
    private String  sapid ;
    private String longdec ;
    private String autoorder;
    private String zdtype ;
    private String zddia  ;
    private String  packway ;
    private String comp   ;
    private String sutcode ;
    private int ordernum ;
    private String zlqid ;
    private String zlqidinf ;
    private String zlqcheck ;
    private String supplier1; 
    private String dgid;
    private String dginf  ;
    private String dgcheck ;
    private String supplier2 ;
    private String upoverflow ;
    private String downflow ;
    private String  struinf   ;
    private String strureinf ;
    private String buyreinf  ;
    private Date sapredate  ;
    private int sapnum;//SAP系统总装完成量
    private String bzcheck ;//包装入库量
    private int renum;// 剩余排产计划量
    private String checkdate; //
    private Date syupdate;//系统上线日期
    private Date indate;//验货
    private Date chdates;//更变日期
    private Date orderdate;//订单日期
    private Date comdate;//完工日期
    private Date t821;//8-21上线
    private Date t828;//8-28上线
    private String remark;//备注
    private int tnum;//数量

    public Hisview(){
    	
    }
    
    
    
    public String getShowdate() {
		return showdate;
	}
	public void setShowdate(String showdate) {
		this.showdate = showdate;
	}

	public String getSailorder() {
		return sailorder;
	}

	public void setSailorder(String sailorder) {
		this.sailorder = sailorder;
	}

	public String getLineitem() {
		return lineitem;
	}

	public void setLineitem(String lineitem) {
		this.lineitem = lineitem;
	}

	public String getTlineitem() {
		return tlineitem;
	}

	public void setTlineitem(String tlineitem) {
		this.tlineitem = tlineitem;
	}

	public String getPorder() {
		return porder;
	}

	public void setPorder(String porder) {
		this.porder = porder;
	}

	public String getBorder() {
		return border;
	}

	public void setBorder(String border) {
		this.border = border;
	}

	public String getSapid() {
		return sapid;
	}

	public void setSapid(String sapid) {
		this.sapid = sapid;
	}

	public String getLongdec() {
		return longdec;
	}

	public void setLongdec(String longdec) {
		this.longdec = longdec;
	}

	public String getAutoorder() {
		return autoorder;
	}

	public void setAutoorder(String autoorder) {
		this.autoorder = autoorder;
	}

	public String getZdtype() {
		return zdtype;
	}

	public void setZdtype(String zdtype) {
		this.zdtype = zdtype;
	}

	public String getZddia() {
		return zddia;
	}

	public void setZddia(String zddia) {
		this.zddia = zddia;
	}

	public String getPackway() {
		return packway;
	}

	public void setPackway(String packway) {
		this.packway = packway;
	}

	public String getComp() {
		return comp;
	}

	public void setComp(String comp) {
		this.comp = comp;
	}

	public String getSutcode() {
		return sutcode;
	}

	public void setSutcode(String sutcode) {
		this.sutcode = sutcode;
	}

	public int getOrdernum() {
		return ordernum;
	}

	public void setOrdernum(int ordernum) {
		this.ordernum = ordernum;
	}

	public String getZlqid() {
		return zlqid;
	}

	public void setZlqid(String zlqid) {
		this.zlqid = zlqid;
	}

	public String getZlqidinf() {
		return zlqidinf;
	}

	public void setZlqidinf(String zlqidinf) {
		this.zlqidinf = zlqidinf;
	}

	public String getZlqcheck() {
		return zlqcheck;
	}

	public void setZlqcheck(String zlqcheck) {
		this.zlqcheck = zlqcheck;
	}

	public String getSupplier1() {
		return supplier1;
	}

	public void setSupplier1(String supplier1) {
		this.supplier1 = supplier1;
	}

	public String getDgid() {
		return dgid;
	}

	public void setDgid(String dgid) {
		this.dgid = dgid;
	}

	public String getDginf() {
		return dginf;
	}

	public void setDginf(String dginf) {
		this.dginf = dginf;
	}

	public String getDgcheck() {
		return dgcheck;
	}

	public void setDgcheck(String dgcheck) {
		this.dgcheck = dgcheck;
	}

	public String getSupplier2() {
		return supplier2;
	}

	public void setSupplier2(String supplier2) {
		this.supplier2 = supplier2;
	}

	public String getUpoverflow() {
		return upoverflow;
	}

	public void setUpoverflow(String upoverflow) {
		this.upoverflow = upoverflow;
	}

	public String getDownflow() {
		return downflow;
	}

	public void setDownflow(String downflow) {
		this.downflow = downflow;
	}

	public String getStruinf() {
		return struinf;
	}

	public void setStruinf(String struinf) {
		this.struinf = struinf;
	}

	public String getStrureinf() {
		return strureinf;
	}

	public void setStrureinf(String strureinf) {
		this.strureinf = strureinf;
	}

	public String getBuyreinf() {
		return buyreinf;
	}

	public void setBuyreinf(String buyreinf) {
		this.buyreinf = buyreinf;
	}

	public Date getSapredate() {
		return sapredate;
	}

	public void setSapredate(Date sapredate) {
		this.sapredate = sapredate;
	}

	public String getBzcheck() {
		return bzcheck;
	}

	public void setBzcheck(String bzcheck) {
		this.bzcheck = bzcheck;
	}
	public int getSapnum() {
		return sapnum;
	}
	public void setSapnum(int sapnum) {
		this.sapnum = sapnum;
	}
	public int getRenum() {
		return renum;
	}
	public void setRenum(int renum) {
		this.renum = renum;
	}
	public String getCheckdate() {
		return checkdate;
	}
	public void setCheckdate(String checkdate) {
		this.checkdate = checkdate;
	}
	public Date getSyupdate() {
		return syupdate;
	}
	public void setSyupdate(Date syupdate) {
		this.syupdate = syupdate;
	}
	public Date getIndate() {
		return indate;
	}
	public void setIndate(Date indate) {
		this.indate = indate;
	}
	public Date getChdates() {
		return chdates;
	}
	public void setChdates(Date chdates) {
		this.chdates = chdates;
	}
	public Date getOrderdate() {
		return orderdate;
	}
	public void setOrderdate(Date orderdate) {
		this.orderdate = orderdate;
	}
	public Date getComdate() {
		return comdate;
	}
	public void setComdate(Date comdate) {
		this.comdate = comdate;
	}
	public Date getT821() {
		return t821;
	}
	public void setT821(Date t821) {
		this.t821 = t821;
	}
	public Date getT828() {
		return t828;
	}
	public void setT828(Date t828) {
		this.t828 = t828;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public int getTnum() {
		return tnum;
	}
	public void setTnum(int tnum) {
		this.tnum = tnum;
	}
    
	
}
