package com.wellsoft.pt.ldx.service.sales;

import java.util.List;

import com.wellsoft.pt.core.service.BaseService;
import com.wellsoft.pt.file.entity.FmFile;

public interface OutBoundService extends BaseService {

	public List<FmFile> findFmFile(String wl);

}
