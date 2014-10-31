package com.wellsoft.pt.ldx.service.impl;

import java.util.List;

import com.wellsoft.pt.ldx.util.StringUtils;


public class IorderManageTimesImpl extends SapServiceImpl{
	public void saveOrderDetailFromSystem() {
		//@formatter:off
		// 1.创建自建表不存在的订单号记录
		String insert = 
			"insert into zsdt0050(mandt,vbeln)"
			+ " select a.mandt,a.vbeln"
			+ " from vbak a where a.mandt = "
			+ this.getClient()
			+ " and not exists (select 1 from zsdt0050 e where e.mandt=a.mandt and e.vbeln = a.vbeln)"
			+ " and exists (select 1 from kna1 b where a.kunnr = b.kunnr and a.mandt=b.mandt and b.ktokd='Z001')";
		execSql(insert);
		// 2.删除被用户删掉的订单信息
		String delete = 
			"delete from zsdt0050 a"
			+ " where not exists (select 1 from vbak b where b.mandt = a.mandt and b.vbeln = a.vbeln)"
			+ " and a.mandt ="+this.getClient();
		execSql(delete);
		// 3.从系统表中查找相关订单信息并更新至自建表中
		String update = 
			"declare"
			+ " cursor crs_zhch is"
			+ " select vbeln,abrvw,sortl,werks,zprodtype,bstkd,kwmneg,zpsdat3,edatu,zdate,gltrp,wadat_ist,chws,om,ae,zegdate from ("
			+ " with werks as("
			+ " select listagg(werks,'|') within group (order by vbeln) werks,vbeln from (select distinct vbeln,werks from vbap where mandt="+this.getClient()+" and werks!=' ') group by vbeln "
			+ " ),zsdt11 as("
			+ " select vbeln,max(zprodtype) as zprodtype,max(zpsdat3) as zpsdat3,max(zegdate) as zegdate from zsdt0011 where mandt="+this.getClient()+" group by vbeln" 
			+ " ),zsdt47 as("
			+ " select vbeln,max(zdate) as zdate from zsdt0047 where mandt="+this.getClient()+" group by vbeln" 
			+ " ),contract as(" 
			+ " select vbeln,count(vbeln) as con from vbap where mandt="+this.getClient()+" group by vbeln" 
			+ " ),vbkdtemp as(" 
			+ " select distinct vbeln,bstkd from vbkd where mandt="+this.getClient()+" and posnr='000000'" 
			+ " ),kemeng as(" 
			+ " select vbeln,sum(kwmeng) as kwmneg from vbap where mandt="+this.getClient()+" group by vbeln" 
			+ " ),zsdt37 as(" 
			+ " select vbeln,min(edatu) as edatu from zsdt0037 where mandt="+this.getClient()+" group by vbeln" 
			+ " ),zsdt23 as(" 
			+ " select vbeln,max(zcdat) as zdate from zsdt0023 where mandt="+this.getClient()+" group by vbeln" 
			+ " ),afp as(" 
			+ " select afpo.kdauf,max(afko.gltrp) as gltrp from afpo,afko where afpo.mandt="+this.getClient()+" and afpo.mandt = afko.mandt and afpo.aufnr = afko.aufnr group by afpo.kdauf" 
			+ " ),lik as(" 
			+ " select lips.vgbel,max(likp.wadat_ist) as wadat_ist from lips,likp where lips.mandt="+this.getClient()+" and lips.mandt = likp.mandt and lips.vbeln=likp.vbeln group by lips.vgbel" 
			+ " ),chws as(" 
			+ " select vbap.vbeln, sum(case when vbap.abgru!=' ' then 0 else nvl(vbap.kwmeng,0) - nvl(case when vbup.wbsta='C' then nvl(lips.lfimg,0) else 0 end,0) end)  as chws" 
			+ " from vbap left join lips on vbap.mandt = lips.mandt and vbap.vbeln = lips.vgbel and vbap.posnr = lips.vgpos  left join vbup on vbup.mandt = lips.mandt and vbup.vbeln = lips.vbeln and vbup.posnr = lips.posnr "
		    + " where vbap.mandt ='"+this.getClient()+"' group by vbap.vbeln"
			+ " ),tvvt as("
			+ " select kvgr2,max(bezei) as bezei from tvv2t where mandt="+this.getClient()+" and spras='1' group by kvgr2"
			+ " ),tvgr as("
			+ " select vkgrp,max(bezei) as bezei from tvgrt where mandt="+this.getClient()+" and spras='1' group by vkgrp"
			+ " ),khzr as("
			+ " select knvv.kunnr,max(tvvt.bezei) as om from knvv,tvvt where knvv.mandt="+this.getClient()+" and knvv.aufsd!='01' and knvv.kvgr2=tvvt.kvgr2 group by knvv.kunnr"
			+ " ),khzl as("
			+ " select knvv.kunnr,max(tvgr.bezei) as ae from knvv,tvgr where knvv.mandt="+this.getClient()+" and knvv.aufsd!='01' and knvv.vkgrp=tvgr.vkgrp group by knvv.kunnr"
			+ " )"
			+ " select a.vbeln,a.abrvw,nvl(trim(b.sortl),' ') as sortl,nvl(c.werks,' ') as werks,nvl(d.zprodtype,' ') as zprodtype,nvl(f.bstkd,' ') as bstkd,nvl(g.kwmneg,0) as kwmneg," 
			+ " nvl(case when e.zdate is not null and e.zdate!='00000000' then e.zdate else d.zpsdat3 end,'00000000') as zpsdat3,nvl(h.edatu,'00000000') as edatu,nvl(i.zdate,'00000000') as zdate,nvl(j.gltrp,'00000000') as gltrp," 
			+ " nvl(l.wadat_ist,'00000000') as wadat_ist,nvl(m.chws,'0') as chws,nvl(n.om,' ') as om,nvl(o.ae,' ') as ae,nvl(d.zegdate,'00000000') as zegdate "
			+ " from vbak a" 
			+ " left join kna1 b on a.mandt = b.mandt and a.kunnr = b.kunnr" 
			+ " left join werks c on a.vbeln = c.vbeln" 
			+ " left join zsdt11 d on a.vbeln = d.vbeln" 
			+ " left join zsdt47 e on a.vbeln = e.vbeln" 
			+ " left join vbkdtemp f on a.vbeln = f.vbeln" 
			+ " left join kemeng g on a.vbeln = g.vbeln" 
			+ " left join zsdt37 h on a.vbeln = h.vbeln" 
			+ " left join zsdt23 i on a.vbeln = i.vbeln" 
			+ " left join afp j    on a.vbeln = j.kdauf" 
			+ " left join lik l    on a.vbeln = l.vgbel" 
			+ " left join chws m   on a.vbeln = m.vbeln" 
			+ " left join khzr n   on a.kunnr = n.kunnr"
			+ " left join khzl o   on a.kunnr = o.kunnr"
			+ " where a.mandt="+this.getClient()
			+ " )temp;" 
			+ " c_row crs_zhch%rowtype;" 
			+ " begin" 
			+ " for c_row in crs_zhch loop" 
			+ " update zsdt0050 e"
			+ " set abrvw = c_row.abrvw"
			+ " ,sortl = c_row.sortl"
			+ " ,werks = c_row.werks"
			+ " ,zprodtype = c_row.zprodtype"
			+ " ,bstkd = c_row.bstkd"
			+ " ,kwmeng = c_row.kwmneg"
			+ " ,zpsdat3 = c_row.zpsdat3"
			+ " ,edatu = c_row.edatu"
			+ " ,xmdyb = case when xmdyb!='00000000' then xmdyb else c_row.zdate end"
			+ " ,zyhrq = case when zyhrq!='00000000' then zyhrq else c_row.zegdate end"
			+ " ,zyjrk = c_row.gltrp"
			+ " ,zhychh = c_row.wadat_ist"
			+ " ,zchws = c_row.chws"
			+ " ,khzr  = c_row.ae"
			+ " ,khzl  = c_row.om"
			+ " where e.vbeln = c_row.vbeln;"
			+ " end loop;"
			+ " end;";
		execSql(update);
		//更新冲销标识
		String updateCxbs = "update zsdt0050 set zcxbs = case when zddzt='已完全出货' and zchws>0 then '1' else ' ' end";
		execSql(updateCxbs);
		//@formatter:on
	}

	
	//TODO 因此任务需要从ZSDT0050表中抽数,需要先执行任务"saveOrderDetailFromSystem"
	public void saveShipInfoFromSystem() {
		//@formatter:off
		// 1.创建自建表不存在的订单号记录
		String insert = 
			"insert into zsdt0048(mandt,vbeln,vgbel)"
			+ " with base as("
			+ " select distinct mandt,vbeln,vgbel from lips"
			+ " where vbeln like '008%'"
			+ " and mandt="
			+ this.getClient()
			+ " )"
			+ " select mandt,vbeln,vgbel from base a"
			+ " where not exists (select 1 from zsdt0048 t where t.mandt=a.mandt and t.vbeln=a.vbeln and t.vgbel=a.vgbel)"
			+ " and exists (select 1 from vbak b,kna1 c where a.vgbel = b.vbeln and a.mandt = b.mandt and b.kunnr=c.kunnr and b.mandt=c.mandt and c.ktokd='Z001' )";
		execSql(insert);
		// 2.删除被用户删掉的订单信息
		String delete = 
			"delete from zsdt0048 a"
			+ " where not exists (select 1 from lips b where a.mandt = b.mandt and a.vbeln = b.vbeln and a.vgbel = b.vgbel)"
			+ " and a.mandt ="+this.getClient();
		execSql(delete);
		// 3.从系统表中查找相关订单信息并更新至自建表中
		String update = 
			"declare"
			+ " cursor crs_chxx is"
			+ " select vbeln,vgbel,lfimg,omchjh,wadat_ist,zyjcq,edatu from("
			+ " with lip as("
			+ " select l.vbeln,l.vgbel,sum(l.lfimg) as lfimg from lips l "
			+ " inner join vbuk m on l.vbeln=m.vbeln"
			+ " where l.vbeln like '008%' and m.wbstk='C' and l.mandt="+this.getClient()+" group by l.vbeln,l.vgbel)"
			+ " select a.vbeln,a.vgbel,nvl(b.lfimg,0) as lfimg,nvl(c.omchjh,'00000000') as omchjh,nvl(d.wadat_ist,'00000000') as wadat_ist,nvl(e.zyjcq,'00000000') as zyjcq,nvl(c.edatu,'00000000') as edatu"
			+ " from zsdt0048 a"
			+ " left join lip b on a.vbeln = b.vbeln and a.vgbel = b.vgbel"
			+ " left join zsdt0050 c on a.vgbel = c.vbeln and a.mandt=c.mandt"
			+ " left join likp d on a.vbeln = d.vbeln and a.mandt =d.mandt"
			+ " left join zsdt0036 e on a.vbeln = e.vbeln and a.mandt=e.mandt"
			+ " where a.mandt="+this.getClient()
			+ " );"
			+ " c_row crs_chxx%rowtype;"
			+ " begin"
			+ " for c_row in crs_chxx loop"
			+ " update zsdt0048 e"
			+ " set lfimg  = c_row.lfimg,"
			+ " omchjh = c_row.omchjh,"
			+ " zzgrq  = c_row.wadat_ist,"
			+ " zchq   = c_row.zyjcq,"
			+ " edatu  = c_row.edatu"
			+ " where e.vbeln = c_row.vbeln"
			+ " and e.vgbel = c_row.vgbel;"
			+ " end loop;"
			+ " end;";
		execSql(update);
		//@formatter:on
	}
	
	public void saveOrderLinesFromSapSystem() {
		//@formatter:off
		String insert = "insert into zsdt0049(mandt,vbeln,posnr)"
			+ " select a.mandt,a.vbeln,a.posnr from vbap a"
			+ " where a.mandt = " + this.getClient()
			+ " and not exists (select 1 from zsdt0049 b where a.mandt=b.mandt and a.vbeln=b.vbeln and a.posnr=b.posnr)"
			+ " and exists (select 1 from vbak b,kna1 c where a.vbeln = b.vbeln and a.mandt = b.mandt and b.kunnr=c.kunnr and b.mandt=c.mandt and c.ktokd='Z001')";
		execSql(insert);
		String delete = "delete from zsdt0049 a "
			+ " where not exists (select 1 from vbap b where a.mandt=b.mandt and a.vbeln=b.vbeln and a.posnr=b.posnr)"
			+ " and a.mandt = " + this.getClient();
		execSql(delete);
		String update = ""
			+"declare                                                                            "
			+"  cursor crs_zhch is                                                               "
			+"    select distinct t1.vbeln as vbeln,                                             "
			+"                    t1.posnr as posnr,                                             "
			+"                    nvl(t1.matnr, ' ') as matnr,                                   "
			+"                    nvl(t2.MAKTX, ' ') as maktx,                                   "
			+"                    nvl(t1.KWMENG, 0) as kwmeng,                                   "
			+"                    nvl(t1.VRKME, ' ') as vrkme,                                   "
			+"                    nvl(t1.NETPR, 0) as netpr,                                     "
			+"                    nvl(t1.KDMAT, ' ') as kdmat,                                   "
			+"                    nvl(t1.werks, ' ') as werks,                                   "
			+"                    nvl(case                                                       "
			+"                          when t5.zdate is not null and t5.zdate != '00000000' then"
			+"                           t5.zdate                                                "
			+"                          else                                                     "
			+"                           t3.ZPSDAT3                                              "
			+"                        end,                                                       "
			+"                        '00000000') as zpsdat3,                                    "
			+"                    nvl(t4.EDATU, '00000000') as edatu,                            "
			+"                    nvl(t6.ZDATS, '00000000') as zdats,                            "
			+"                    nvl(t7.ZCDAT, '00000000') as zdate,                            "
			+"                    nvl(t3.zegdate, '00000000') as zegdate,                        "
			+"                    nvl(t9.PSMNG, 0) as psmng,                                     "
			+"                    nvl(t10.GLTRP, '00000000') as zyjrk,                           "
			+"                    nvl(t12.wadat_ist, '00000000') as wadat_ist,                   "
			+"                    case                                                           "
			+"                      when t1.abgru != ' ' then                                    "
			+"                       0                                                           "
			+"                      else                                                         "
			+"                       nvl(t1.KWMENG, 0) - nvl(case                                "
			+"                                                 when t13.wbsta = 'C' then         "
			+"                                                  nvl(t11.LFIMG, 0)                "
			+"                                                 else                              "
			+"                                                  0                                "
			+"                                               end,                                "
			+"                                               0)                                  "
			+"                    end as lfimg                                                   "
			+"      from vbap t1                                                                 "
			+"      left join makt t2                                                            "
			+"        on t1.matnr = t2.matnr                                                     "
			+"       and t1.mandt = t2.mandt                                                     "
			+"      left join zsdt0011 t3                                                        "
			+"        on t1.vbeln = t3.vbeln                                                     "
			+"       and t1.posnr = t3.posnr                                                     "
			+"       and t1.mandt = t3.mandt                                                     "
			+"      left join zsdt0037 t4                                                        "
			+"        on t1.vbeln = t4.vbeln                                                     "
			+"       and t1.posnr = t4.posnr                                                     "
			+"       and t1.mandt = t4.mandt                                                     "
			+"      left join zsdt0047 t5                                                        "
			+"        on t1.vbeln = t5.vbeln                                                     "
			+"       and t1.posnr = t5.posnr                                                     "
			+"       and t1.mandt = t5.mandt                                                     "
			+"      left join (select vbeln, posnr, max(zdats) as zdats                          "
			+"                   from zsdt0033                                                   "
			+"                  where mandt = '"+this.getClient()+"'                                       "
			+"                  group by vbeln, posnr) t6                                        "
			+"        on t1.vbeln = t6.vbeln                                                     "
			+"       and t1.posnr = t6.posnr                                                     "
			+"      left join zsdt0023 t7                                                        "
			+"        on t1.vbeln = t7.vbeln                                                     "
			+"       and t1.posnr = t7.posnr                                                     "
			+"       and t1.mandt = t7.mandt                                                     "
			+"      left join afpo t9                                                            "
			+"        on t1.vbeln = t9.KDAUF                                                     "
			+"       and t1.posnr = t9.KDPOS                                                     "
			+"       and t1.mandt = t9.mandt                                                     "
			+"      left join afko t10                                                           "
			+"        on t9.AUFNR = t10.AUFNR                                                    "
			+"       and t1.mandt = t10.mandt                                                    "
			+"      left join lips t11                                                           "
			+"        on t1.vbeln = t11.VGBEL                                                    "
			+"       and t1.posnr = t11.VGPOS                                                    "
			+"       and t1.mandt = t11.mandt                                                    "
			+"      left join vbup t13                                                           "
			+"        on t11.vbeln = t13.vbeln                                                   "
			+"       and t11.posnr = t13.posnr                                                   "
			+"      left join (select vbeln, max(wadat_ist) as wadat_ist                         "
			+"                   from likp                                                       "
			+"                  where mandt = '"+this.getClient()+"'                                       "
			+"                  group by vbeln) t12                                              "
			+"        on t11.vbeln = t12.vbeln                                                   "
			+"     where t1.mandt = '"+this.getClient()+"'                                                 "
			+"     order by t1.vbeln, t1.posnr;                                                  "
			+"  c_row crs_zhch%rowtype;                                                          "
			+"begin                                                                              "
			+"  for c_row in crs_zhch loop                                                       "
			+"    update zsdt0049 e                                                              "
			+"       set zyjrk     = c_row.zyjrk,                                                "
			+"           matnr     = c_row.matnr,                                                "
			+"           maktx     = c_row.maktx,                                                "
			+"           kwmeng    = c_row.kwmeng,                                               "
			+"           vrkme     = c_row.vrkme,                                                "
			+"           netpr     = c_row.netpr,                                                "
			+"           kdmat     = c_row.kdmat,                                                "
			+"           werks     = c_row.werks,                                                "
			+"           zpsdat3   = c_row.zpsdat3,                                              "
			+"           edatu     = c_row.edatu,                                                "
			+"           zdats     = c_row.zdats,                                                "
			+"           zdate     = case when zdate!='00000000' then zdate else c_row.zdate end, "
			+"           zyhrq     = case when zyhrq!='00000000' then zyhrq else c_row.zegdate end, "
			+"           psmng     = c_row.psmng,                                                "
			+"           wadat_ist = c_row.wadat_ist,                                            "
			+"           lfimg     = c_row.lfimg                                                 "
			+"     where e.vbeln = c_row.vbeln                                                   "
			+"       and e.posnr = c_row.posnr;                                                  "
			+"  end loop;                                                                        "
			+"end;                                                                               "
			;
		execSql(update);
		//更新冲销标识
		String updateCxbs = "update zsdt0049 set zcxbs = case when zddzt='已完全出货' and lfimg>0 then '1' else ' ' end";
		execSql(updateCxbs);
		//@formatter:on
	}
	
	/*public void savePortAndContainerToShipInfo() {
		String query = "select distinct vbeln from zsdt0048 where mandt="+this.getClient();
		List<Object> vbelns = findListBySql(query);
		//返回列表String[]{外向交货单,抵运港口,柜型}
		List<String[]> result = queryPortAndContainer(vbelns);
		if(result!=null && result.size()>0){
			for (String[] info : result) {
				if (info == null
						|| StringUtils.isBlank(info[0])
						|| (StringUtils.isBlank(info[1]) && StringUtils
								.isBlank(info[2]))) {
					continue;
				}
				String update = 
					"update zsdt0048 set zdygk='" 
					+ (StringUtils.isBlank(info[1])?" ":StringUtils.getLimitLength(info[1].replaceAll("'","`"),35))
					+ "',zgx='"
					+ (StringUtils.isBlank(info[2])?" ":StringUtils.getLimitLength(info[2].replaceAll("'","`"),35))
					+ "' where mandt="
					+ getClient()
					+ " and vbeln='"
					+ StringUtils.addLeftZero(info[0],10)
					+ "'";
				execSql(update);
			}
		}
	}*/
	
	
}

