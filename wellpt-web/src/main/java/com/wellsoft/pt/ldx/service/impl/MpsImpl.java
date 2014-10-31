package com.wellsoft.pt.ldx.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.hibernate.SQLQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wellsoft.pt.basicdata.datasource.entity.DataSourceColumn;
import com.wellsoft.pt.core.dao.UniversalDao;
import com.wellsoft.pt.core.service.impl.BaseServiceImpl;
import com.wellsoft.pt.core.support.PagingInfo;
import com.wellsoft.pt.core.support.QueryItem;
import com.wellsoft.pt.core.support.QueryItemResultTransformer;
import com.wellsoft.pt.ldx.service.DqbsService;

@Service
@Transactional
public class MpsImpl extends BaseServiceImpl  {
	protected UniversalDao mpsDao;
	public MpsImpl() {
		mpsDao = this.getDao("mpsSessionFactory");
	}
	
}
