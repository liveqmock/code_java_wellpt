package com.wellsoft.pt.ldx.service.lmsService;

import java.util.List;

import com.wellsoft.pt.core.service.BaseService;
import com.wellsoft.pt.ldx.model.lmsModel.CostCenter;
import com.wellsoft.pt.ldx.model.lmsModel.LabFile;
import com.wellsoft.pt.ldx.model.lmsModel.LabMove;
import com.wellsoft.pt.ldx.model.lmsModel.TstType;
@SuppressWarnings("rawtypes")
public interface LMSService extends BaseService{
	public List<LabFile> findLabFileById(String labfileNo);
	public List<LabFile> findMeterAccess(String labfileNo);
	public List<LabFile> findLabfileByHavedate(String sdate,String edate);
	public List<CostCenter> findCostCenter(String costNO);
	public List<TstType> findTSTType(String tsttype_name,String tsttype_id);
	public List<TstType> findTSTTypeMoer(String tsttype_names,String tsttype_ids);
	public String updateMeterAccess(String labfileNo,String labfileAddress,String labfileClaim);
	public String updateLabfileBTZT(String labfile_no,String labfile_assetsno,String labfile_cls,String labfile_address
			,String labfile_claim,String labfile_mainhave,String labfile_costno,String labfile_usr,String labfile_interusr);
	public String updateLabfile(String labfileNo,String labfileState,String labfileScrappedno);
	public String insertMeter(LabFile labfile);
	public List findTstorderByTest(String tstorder_no,String tstorder2_clsfrom);
	public List findTstorderByEpPp_1();
	public List findTstorderByEpPp_2(String tstorder_no,String tstorder2_clsfrom);
	public List getSupfile(String supfileNo);
	public List queryOrderByNO(String tstorder_no);
	public List queryOrderByError(String tstorder_no);
	public List queryTstorder4ByFromno(String tstorder_no);
	public List queryForFinishedList(String tstorder_no);
	public String insertLabmove(LabMove labMove);
}
