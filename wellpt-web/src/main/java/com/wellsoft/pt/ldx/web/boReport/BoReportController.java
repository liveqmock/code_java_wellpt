package com.wellsoft.pt.ldx.web.boReport;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.crystaldecisions.celib.properties.URLEncoder;
import com.crystaldecisions.sdk.exception.SDKException;
import com.crystaldecisions.sdk.framework.CrystalEnterprise;
import com.crystaldecisions.sdk.framework.IEnterpriseSession;
import com.crystaldecisions.sdk.occa.security.ILogonTokenMgr;
import com.wellsoft.pt.core.support.ExtendedPropertyPlaceholderConfigurer;
import com.wellsoft.pt.core.web.BaseController;
import com.wellsoft.pt.org.entity.User;
import com.wellsoft.pt.org.service.UserService;
import com.wellsoft.pt.security.core.userdetails.UserDetails;
import com.wellsoft.pt.utils.security.SpringSecurityUtils;

@Controller
@Scope("prototype")
@RequestMapping("/boReport")
public class BoReportController extends BaseController {
	private String reportId;
	@Autowired
	private UserService userService;
	@Autowired
	private ExtendedPropertyPlaceholderConfigurer propertyConfigurer;

	public String getReportId() {
		return reportId;
	}

	public void setReportId(String reportId) {
		this.reportId = reportId;
	}

	public String getDestUrl() {
		return destUrl;
	}

	public void setDestUrl(String destUrl) {
		this.destUrl = destUrl;
	}

	private String destUrl;

	@RequestMapping("/toBoReport")
	private ModelAndView createDestUrl(
			@RequestParam(value = "iDocID") String iDocID,
			@RequestParam(value = "height") String height) throws Exception {
		ModelAndView orderContext = new ModelAndView();
		orderContext.setViewName("/boReport/boReport");
		String cms = propertyConfigurer.getProperty("bw.cms").toString();// 192.168.0.106//192.168.0.104
		// 认证的安全类型，类型是以上几种，如果是SAP，则类型字符为secSAPR3
		String url = propertyConfigurer.getProperty("bw.url").toString();
		// url="http://192.168.0.104:8080/BOE/Xcelsius/opendoc/documentDownload";
		String authentication = "secEnterprise"; // "secEnterprise";
		authentication = "secSAPR3"; // "secEnterprise";//Enterprise//secSAPR3
		String username = "";// administrator//fangdl//ldxit
		String password = ""; // Sapbod2012//bw1234//ldx2013
		UserDetails userDetail = SpringSecurityUtils.getCurrentUser();
		User u = userService.get(userDetail.getUserUuid());
		if (StringUtils.isNotEmpty(u.getBoUser()))
			username = "BWP~800/" + u.getBoUser().toUpperCase();
		password = u.getBoPwd();
		IEnterpriseSession session = CrystalEnterprise.getSessionMgr().logon(
				username, password, cms, authentication);
		ILogonTokenMgr mgr = session.getLogonTokenMgr();
		String defaultToken = mgr.createLogonToken("", 120, 100);
		String tokenEnc = URLEncoder.encode(defaultToken);
		StringBuffer urlBuffer = new StringBuffer();
		urlBuffer.append(url)
				.append("?sIDType=CUID")
				// .append("&sKind=Flash")
				// .append("&sRefresh=Y")
				.append("&iDocID=").append(iDocID).append("&token=")
				.append(tokenEnc);
		System.out.println(urlBuffer.toString());

		// AUO7gf5LtIFHsYax0NQDYiY

		// AWo6OTsbdnJElwZ5Kwg7Hk0

		// AfLszPh82z9BoCcUCT0iklE

		// AbnCvqkTWo5ImtzHIjptQq0

		// String serSession = session.getSerializedSession();
		// String serSessEnc = URLEncoder.encode(serSession);
		// System.out.println(serSession);
		// System.out.println(serSessEnc);
		orderContext.addObject("url", urlBuffer.toString());
		orderContext.addObject("height", height);
		return orderContext;
	}
}
