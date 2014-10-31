package com.wellsoft.pt.ldx.service.lmsService.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wellsoft.pt.ldx.model.lmsModel.CostCenter;
import com.wellsoft.pt.ldx.model.lmsModel.LabFile;
import com.wellsoft.pt.ldx.model.lmsModel.LabMove;
import com.wellsoft.pt.ldx.model.lmsModel.TstType;
import com.wellsoft.pt.ldx.service.impl.LmsManageImpl;
import com.wellsoft.pt.ldx.service.lmsService.LMSService;
import com.wellsoft.pt.ldx.util.StringUtils;

@Service
@Transactional
@SuppressWarnings("rawtypes")
public class LMSServiceImpl  extends LmsManageImpl implements LMSService{
	/**
	 * 固定资产/低值易耗品报废申请 LMS查询
	 * @param labfileNo
	 * @return
	 */
	@Override
	public List<LabFile> findLabFileById(String labfileNo){
		StringBuffer sql = new StringBuffer("from LabFile where 1=1 ")
			.append(" and labfileNo='"+labfileNo+"'");
		List<LabFile> list = this.lmsDao.getSession().createQuery(sql.toString()).list();
		return list;
	}
	/**
	 * 固定资产/低值易耗品报废申请 LMS数据修改
	 * @param labfileNo
	 * @param labfileState
	 * @param labfileScrappedno
	 */
	@Override
	public String updateLabfile(String labfileNo,String labfileState,String labfileScrappedno){
		StringBuffer sql = new StringBuffer("update LABFILE set ")
			.append(" LABFILE_STATE="+labfileState+",")
			.append("LABFILE_SCRAPPEDNO='"+labfileScrappedno+"' ")
			.append(" where LABFILE_NO='"+labfileNo+"'");
		int num = this.lmsDao.getSession().createSQLQuery(sql.toString()).executeUpdate();
		return num<=0?"写入失败":"";
	}
	/**
	 * 不同主体固定资产出售申请/资产调拨——修改
	 * @param labfile_no
	 * @param labfile_assetsno
	 * @param labfile_cls
	 * @param labfile_address
	 */
	@Override
	public String updateLabfileBTZT(String labfile_no,String labfile_assetsno,String labfile_cls,String labfile_address
			,String labfile_claim,String labfile_mainhave,String labfile_costno,String labfile_usr,String labfile_interusr){
		StringBuffer sql = new StringBuffer("update LABFILE set ")
		.append(" labfile_assetsno='"+labfile_assetsno+"',")
		.append("labfile_claim='"+labfile_claim+"',")
		.append(StringUtils.isNotEmpty(labfile_mainhave)?"labfile_mainhave="+labfile_mainhave+",":"labfile_mainhave=null,")
		.append("labfile_costno='"+labfile_costno+"',")
		.append("labfile_usr='"+labfile_usr+"',")
		.append("labfile_interusr='"+labfile_interusr+"',")
		.append("labfile_cls='"+labfile_cls+"',")
		.append("labfile_address='"+labfile_address+"' ")
		.append(" where LABFILE_NO='"+labfile_no+"'");
		int num = this.lmsDao.getSession().createSQLQuery(sql.toString()).executeUpdate();
		return num<=0?"写入失败":"";
	}
	/**
	 * 计量器具领用
	 * @param labfileNo
	 * @return
	 */
	@Override
	public List<LabFile> findMeterAccess(String labfileNo){
		String sql = "select a.LABFILE_NAME,a.labfile_no,a.LABFILE_SPEC,c.supfile_name,a.LABFILE_DEFNO,a.LABFILE_ASSETSNO"
			+",a.LABFILE_MAINHAVE,b.COSTCENTER_NAME from LABFILE a left join COSTCENTER b on(b.COSTCENTER_NO=a.LABFILE_COSTNO)"
			+" left join SUPFILE c on(a.labfile_sup=c.supfile_no) "
			+" where a.LABFILE_NO='"+labfileNo+"'";
		List<LabFile> list = this.lmsDao.getSession().createSQLQuery(sql).list();
		return list;
	}
	/**
	 * 计量器具领用
	 * @param labfileNo
	 * @param labfileAddress
	 * @param labfileClaim
	 */
	@Override
	public String updateMeterAccess(String labfileNo,String labfileAddress,String labfileClaim){
		StringBuffer sql = new StringBuffer("update LABFILE set ")
		.append(" LABFILE_ADDRESS='"+labfileAddress+"',")
		.append("LABFILE_CLAIM='"+labfileClaim+"' ")
		.append(" where LABFILE_NO='"+labfileNo+"'");
		int num = this.lmsDao.getSession().createSQLQuery(sql.toString()).executeUpdate();
		return num<=0?"写入失败":"";
	}
	/**
	 * 校准计划通知单查询
	 * @param labfile_havedate
	 * @return
	 */
	@Override
	public List<LabFile> findLabfileByHavedate(String sdate,String edate){
		StringBuffer sql = new StringBuffer("from LabFile where 1=1 ")
			.append(" and labfileHavedate between to_date('"+sdate+"','yyyy/mm/dd') and to_date('"+edate+"','yyyy/mm/dd')");
		List<LabFile> list = this.lmsDao.openSession().createQuery(sql.toString()).list();
		return list;
	}
	/**
	 * 计量器具送检（只做插入操作）
	 * @param labfile
	 */
	@Override
	public String insertMeter(LabFile labfile){
		StringBuffer sql = new StringBuffer("insert into LABFILE ")
			.append("(labfile_no,labfile_sup,labfile_name,labfile_defno,labfile_spec,labfile_usr,labfile_assetsno,")
			.append("labfile_cls,labfile_mainhave,labfile_assetscls,labfile_usrdate,labfile_saleunit,labfile_salenm,labfile_saletel,labfile_costno,labfile_interusr)")
			.append(" values (");
			if(StringUtils.isNotEmpty(labfile.getLabfileNo())){
				sql.append("'"+labfile.getLabfileNo()+"',");
			}else{
				return "管理编号为空，写入失败";
			}
			List list = this.findLabFileById(labfile.getLabfileNo());
			if(list.size()>0||null!=list){
				return "管理编号已存在!";
			}
			sql.append("'"+labfile.getLabfileSup()+"',")
			.append("'"+labfile.getLabfileName()+"',")
			.append("'"+labfile.getLabfileDefno()+"',")
			.append("'"+labfile.getLabfileSpec()+"',")
			.append("'"+labfile.getLabfileUsr()+"',")
			.append("'"+labfile.getLabfileAssetsno()+"',")
			.append(StringUtils.isNotEmpty(labfile.getLabfileCls())?labfile.getLabfileCls()+",":"null,")
			.append(labfile.getLabfileMainhave()+",")
			.append(labfile.getLabfileAssetscls()+",")
			.append(StringUtils.isNotEmpty(labfile.getLabfileUsrdate())?"to_date('"+labfile.getLabfileUsrdate()+"','yyyy-mm-dd'),":"null,")
			.append("'"+labfile.getLabfileSaleunit()+"',")
			.append("'"+labfile.getLabfileSalenm()+"',")
			.append("'"+labfile.getLabfileSaletel()+"',")
			.append("'"+labfile.getLabfileCostno()+"',")
			.append("'"+labfile.getLabfileInterusr()+"')")
			;
		int num = this.lmsDao.getSession().createSQLQuery(sql.toString()).executeUpdate();
		return num<=0?"写入失败":"";
	}
	/**
	 * 获取成本中心数据
	 * @param costNO
	 * @return
	 */
	@Override
	public List<CostCenter> findCostCenter(String costNO){
		StringBuffer sql = new StringBuffer("from CostCenter where 1=1 ")
		.append(StringUtils.isNotEmpty(costNO)?" and costcenter_cbno='"+costNO+"'":"");
		List<CostCenter> list = this.lmsDao.openSession().createQuery(sql.toString()).list();
		return list;
	}
	/**
	 * 获取测试类别、报价单
	 * @param costNO
	 * @return
	 */
	@Override
	public List<TstType> findTSTType(String tsttype_name,String tsttype_id){
		StringBuffer sql = new StringBuffer("from TstType where 1=1 ")
		.append(StringUtils.isNotEmpty(tsttype_id)?" and tsttype_id='"+tsttype_id+"' ":"")
		.append(StringUtils.isNotEmpty(tsttype_name)?" and tsttype_name='"+tsttype_name+"'":"");
		List<TstType> list = this.lmsDao.openSession().createQuery(sql.toString()).list();
		return list;
	}
	/**
	 * 获取多个测试类别、报价单
	 * @param tsttype_names
	 * @param tsttype_ids
	 * @return
	 */
	@Override
	public List<TstType> findTSTTypeMoer(String tsttype_names,String tsttype_ids){
		StringBuffer sql = new StringBuffer("from TstType where 1=1 ")
		.append(StringUtils.isNotEmpty(tsttype_ids)?" and tsttype_id in "+tsttype_ids+" ":"")
		.append(StringUtils.isNotEmpty(tsttype_names)?" and tsttype_name in "+tsttype_names+" ":"");
		List<TstType> list = this.lmsDao.openSession().createQuery(sql.toString()).list();
		return list;
	}
	/**
	 * 测试异常通知单 查询
	 * @param tstorder_no
	 * @param tstorder2_clsfrom
	 * @return
	 */
	@Override
	public List findTstorderByTest(String tstorder_no,String tstorder2_clsfrom){
		StringBuffer sql = new StringBuffer("select t.tstorder_orderno,a.tstorder2_no,a.tstorder2_spec,t.tstorder_pur,t.tstorder_cipusrno,t.tstorder_date from ")
			.append("TSTORDER t left join tstorder2 a on(t.tstorder_no=a.tstorder2_fromno) where 1=1")
			.append(" and t.tstorder_no='"+tstorder_no+"'")
			.append(" and a.tstorder2_clsfrom='"+tstorder2_clsfrom+"'");
		return this.lmsDao.openSession().createSQLQuery(sql.toString()).list();
	}
	/**
	 * EP&PP项目测试进度，维护一
	 * @return
	 */
	@Override
	public List findTstorderByEpPp_1(){
		StringBuffer sql = new StringBuffer("select a.tstorder_pur,a.tstorder_no,b.tstorder2_clsfrom,c.tsttype_name,b.tstorder2_spec,a.tstorder_date,")
			.append("b.tstorder2_sdate,b.tstorder2_edate,a.tstorder_cipusrno from ")
			.append("TSTORDER a left join tstorder2 b on(a.tstorder_no=b.tstorder2_fromno) left join TSTTYPE c on(a.tstorder_fittypeid=c.tsttype_id) ")
			.append(" where a.tstorder_state=3 and a.tstorder_pur in (7,8,16) order by a.tstorder_no");
		return this.lmsDao.openSession().createSQLQuery(sql.toString()).list();
	}
	/**
	 * EP&PP项目测试进度，维护二
	 * @param tstorder_no
	 * @param tstorder2_clsfrom
	 * @return
	 */
	@Override
	public List findTstorderByEpPp_2(String tstorder_no,String tstorder2_clsfrom){
		StringBuffer sql = new StringBuffer("select t.tstorder_date,a.tstorder2_edate from ")
		.append("TSTORDER t left join tstorder2 a on(t.tstorder_no=a.tstorder2_fromno) where 1=1")
		.append(" t.tstorder_no='"+tstorder_no+"'")
		.append(" and a.tstorder2_clsfrom='"+tstorder2_clsfrom+"'");
		return this.lmsDao.openSession().createSQLQuery(sql.toString()).list();
	}
	/**
	 * 获取供应商，生产厂家
	 * @param supfileNo
	 * @return
	 */
	@Override
	public List getSupfile(String supfileNo){
		StringBuffer sql = new StringBuffer("select supfile_name from SUPFILE where 1=1")
			.append(" and supfile_no='"+supfileNo+"'");
		return this.lmsDao.getSession().createSQLQuery(sql.toString()).list();
	}
	/**
	 * 成品例行试验&RoHS测试&寿命跟踪计划表 
	 */
	@Override
	public List queryOrderByNO(String tstorder_no){
		StringBuffer sql = new StringBuffer("select t.tstorder_no,t.tstorder_date,r.tstorder2_voltage,y.supfile_name,v.comlist_name,i.tstreport_conok from ")
		.append("TSTORDER t left join TSTORDER2 r on(t.tstorder_no=r.tstorder2_fromno) ")
		.append("left join supfile y on(r.tstorder2_sup=y.supfile_no) ")
		.append("left join TSTREPORT i on(t.tstorder_no=i.tstreport_fromno)")
		.append(",COMLIST v ")
		.append(" where v.comlist_cls=1 and v.comlist_id=t.tstorder_pur and t.tstorder_no='")
		.append(tstorder_no).append("'");
		
		return this.lmsDao.getSession().createSQLQuery(sql.toString()).list();
	}
	/**
	 * 成品例行试验&RoHS测试异常一览表 V1.2
	 * @param tstorder_no
	 * @return
	 */
	@Override
	public List queryOrderByError(String tstorder_no){
		StringBuffer sql = new StringBuffer("select t.tstorder_no,t.tstorder_extcstno,t.tstorder_orderno,r.tstorder2_no,r.tstorder2_spec,v.comlist_name ")
		.append("from TSTORDER t left join TSTORDER2 r on(t.tstorder_no=r.tstorder2_fromno),COMLIST v ")
		.append(" where v.comlist_cls=1 and v.comlist_id=t.tstorder_pur and t.tstorder_no='").append(tstorder_no).append("'");
		
		return this.lmsDao.getSession().createSQLQuery(sql.toString()).list();
	}
	/**
	 * 测试异常通知单:测试项目
	 * @param tstorder_no
	 * @return
	 */
	@Override
	public List queryTstorder4ByFromno(String tstorder_no){
		StringBuffer sql = new StringBuffer("select tstorder4_tsttype from TSTORDER4 where ")
		.append(" tstorder4_fromno='")
		.append(tstorder_no).append("'");
		
		return this.lmsDao.getSession().createSQLQuery(sql.toString()).list();
	}
	/**
	 * 成品例行试验一览表取值
	 */
	@Override
	public List queryForFinishedList(String tstorder_no){
		StringBuffer sql = new StringBuffer("select i.tstorder_date,y.tsttype_name,u.comlist_name from ")
		.append("TSTREPORT t left join TSTTYPE y on (t.tstreport_tsttype=y.tsttype_id) ")
		.append("left join COMLIST u on (u.comlist_cls='10' and t.tstreport_conok=u.comlist_id) ")
		.append("left join TSTORDER i on (t.tstreport_fromno=i.tstorder_no) ")
		.append("where t.tstreport_fromno='").append(tstorder_no).append("' order by y.tsttype_name");
		return this.lmsDao.getSession().createSQLQuery(sql.toString()).list();
	}
	/**
	 * 添加设备转移记录
	 */
	@Override
	public String insertLabmove(LabMove labMove){
		if(StringUtils.isNotEmpty(labMove.getLabmoveType())){
			return "设备类型字段为空";
		}else if(StringUtils.isNotEmpty(labMove.getLabmoveNo())){
			return "设备编号字段为空";
		}
		
		StringBuffer sql = new StringBuffer("select t.labmove_id from labmove t ")
//		.append(labMove.getLabmoveId()>0?" where t.labmove_id = "+labMove.getLabmoveId():"")
		.append(" order by t.labmove_id desc");
		
		List list = this.lmsDao.getSession().createSQLQuery(sql.toString()).list();
		int id = (Integer) list.get(0);
		labMove.setLabmoveId(id+1);
		try {
			this.lmsDao.save(labMove);
		} catch (Exception e) {
			e.printStackTrace();
			return "写入失败";
		}
		return "";
	}
}
