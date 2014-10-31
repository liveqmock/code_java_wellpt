package com.wellsoft.pt.purchase.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.wellsoft.pt.core.context.ApplicationContextHolder;
import com.wellsoft.pt.ldx.service.purchase.FetchPurchaseService;

public class FetchPurchaseJob1 implements Job {

	public FetchPurchaseJob1() {
	}

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		FetchPurchaseService fetchPurchaseService = (FetchPurchaseService) ApplicationContextHolder
				.getBean("fetchPurchaseService");
		try {
			fetchPurchaseService.fetchPurchaseData1();
		} catch (Exception exception) {
			exception.printStackTrace();
			throw new RuntimeException(exception);
		}
	}
}