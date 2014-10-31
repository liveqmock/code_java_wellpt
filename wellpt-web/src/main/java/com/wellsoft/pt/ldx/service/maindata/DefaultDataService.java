package com.wellsoft.pt.ldx.service.maindata;

import org.apache.poi.ss.usermodel.Sheet;

import com.wellsoft.pt.core.service.BaseService;

public interface DefaultDataService extends BaseService {

	public abstract void importQm(Sheet sh);

	public abstract void importPlan(Sheet sh);

	public abstract void importPp(Sheet sh);

	public abstract void importFactory(Sheet sh);

	public abstract void importFico(Sheet sh);

	public abstract void importSd(Sheet sh);

	public abstract void importMm(Sheet sh);

	public abstract void importBaoguan(Sheet sh);
}