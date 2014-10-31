package com.wellsoft.pt.ldx.job.ficomanage;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.wellsoft.pt.core.context.ApplicationContextHolder;
import com.wellsoft.pt.ldx.service.ficoManage.IFicoReportService;

/**
 * 
 * Description: 资金管理未知客户邮件提醒后台任务
 *  
 * @author HeShi
 * @date 2014-10-3
 * @version 1.0
 * 
 * <pre>
 * 修改记录:
 * 修改后版本	修改人		修改日期			修改内容
 * 2014-10-3.1	HeShi		2014-10-3		Create
 * </pre>
 *
 */
public class FicoReportEmptyKunnrEmailJob implements Job {

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
	    IFicoReportService ficoReportService = (IFicoReportService) ApplicationContextHolder
			.getBean("ficoReportService");
		try {
			ficoReportService.sendEmptyKunnrEmail();
		} catch (Exception exception) {
			exception.printStackTrace();
			throw new RuntimeException(exception);
		}
	}

}
