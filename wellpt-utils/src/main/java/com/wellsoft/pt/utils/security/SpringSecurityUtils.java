package com.wellsoft.pt.utils.security;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

import com.wellsoft.pt.security.support.IgnoreLoginUtils;

/**
 * SpringSecurity的工具类.
 * 
 * 注意. 本类只支持SpringSecurity 3.0.x.
 * 
 * @author lilin
 */
public class SpringSecurityUtils {
	/**
	 * 取得当前用户, 返回值为SpringSecurity的User类或其子类, 如果当前用户未登录则返回null.
	 */
	@SuppressWarnings("unchecked")
	public static <T extends User> T getCurrentUser() {
		if (IgnoreLoginUtils.isIgnoreLogin()) {
			return (T) IgnoreLoginUtils.getUserDetails();
		}
		Authentication authentication = getAuthentication();

		if (authentication == null) {
			return null;
		}

		Object principal = authentication.getPrincipal();
		if (!(principal instanceof User)) {
			return null;
		}

		return (T) principal;
	}

	/**
	 * 取得当前用户的登录名, 如果当前用户未登录则返回空字符串.
	 */
	public static String getCurrentUserName() {
		Authentication authentication = getAuthentication();

		if (authentication == null || authentication.getPrincipal() == null) {
			return "";
		}
		if ("anonymousUser".equals(authentication.getName())) {
			return authentication.getName();
		}

		return ((com.wellsoft.pt.security.core.userdetails.UserDetails) authentication.getPrincipal()).getUsername();
	}

	/**
	 * 取得当前用户的登录名, 如果当前用户未登录则返回空字符串.
	 */
	public static String getCurrentLoginName() {
		Authentication authentication = getAuthentication();

		if (authentication == null || authentication.getPrincipal() == null) {
			return "";
		}
		if ("anonymousUser".equals(authentication.getName())) {
			return authentication.getName();
		}

		return ((com.wellsoft.pt.security.core.userdetails.UserDetails) authentication.getPrincipal()).getLoginName();
	}

	/**
	 * 取得当前用户的岗位名称, 如果当前用户未登录则返回空字符串.
	 */
	public static String getCurrentUserJobName() {
		Authentication authentication = getAuthentication();

		if (authentication == null || authentication.getPrincipal() == null) {
			return "";
		}
		if ("anonymousUser".equals(authentication.getName())) {
			return "";
		}

		return ((com.wellsoft.pt.security.core.userdetails.UserDetails) authentication.getPrincipal()).getJobName();
	}

	/**
	 * 取得当前用户的主部门, 如果当前用户未登录则返回空字符串.
	 */
	public static String getCurrentUserDepartmentId() {
		Authentication authentication = getAuthentication();

		if (authentication == null || authentication.getPrincipal() == null) {
			return "";
		}
		if ("anonymousUser".equals(authentication.getName())) {
			return "";
		}

		return ((com.wellsoft.pt.security.core.userdetails.UserDetails) authentication.getPrincipal())
				.getDepartmentId();
	}

	/**
	 * 取得当前用户的主部门, 如果当前用户未登录则返回空字符串.
	 */
	public static String getCurrentUserDepartmentName() {
		Authentication authentication = getAuthentication();

		if (authentication == null || authentication.getPrincipal() == null) {
			return "";
		}
		if ("anonymousUser".equals(authentication.getName())) {
			return "";
		}

		return ((com.wellsoft.pt.security.core.userdetails.UserDetails) authentication.getPrincipal())
				.getDepartmentName();
	}

	/**
	 * 取得当前用户的主部门路径, 如果当前用户未登录则返回空字符串.
	 */
	public static String getCurrentUserDepartmentPath() {
		Authentication authentication = getAuthentication();

		if (authentication == null || authentication.getPrincipal() == null) {
			return "";
		}
		if ("anonymousUser".equals(authentication.getName())) {
			return "";
		}

		return ((com.wellsoft.pt.security.core.userdetails.UserDetails) authentication.getPrincipal())
				.getDepartmentPath();
	}

	/**
	 * 取得当前用户的租户ID, 如果当前用户未登录则返回空字符串.
	 */
	public static String getCurrentTenantId() {
		if (IgnoreLoginUtils.isIgnoreLogin()) {
			return IgnoreLoginUtils.getUserDetails().getTenantId();
		}
		return ((com.wellsoft.pt.security.core.userdetails.UserDetails) getCurrentUser()).getTenantId();
	}

	/**
	 * 取得当前用户的登录名, 如果当前用户未登录则返回空字符串.
	 */
	public static String getCurrentUserId() {
		if (IgnoreLoginUtils.isIgnoreLogin()) {
			return IgnoreLoginUtils.getUserDetails().getUserId();
		}
		return ((com.wellsoft.pt.security.core.userdetails.UserDetails) getCurrentUser()).getUserId();
	}

	/**
	 * 取得当前用户登录IP, 如果当前用户未登录则返回空字符串.
	 */
	public static String getCurrentUserIp() {
		Authentication authentication = getAuthentication();

		if (authentication == null) {
			return "";
		}

		Object details = authentication.getDetails();
		if (!(details instanceof WebAuthenticationDetails)) {
			return "";
		}

		WebAuthenticationDetails webDetails = (WebAuthenticationDetails) details;
		return webDetails.getRemoteAddress();
	}

	/**
	 * 判断用户是否拥有角色, 如果用户拥有参数中的任意一个角色则返回true.
	 */
	public static boolean hasAnyRole(String... roles) {
		Authentication authentication = getAuthentication();

		if (authentication == null) {
			return false;
		}

		Collection<? extends GrantedAuthority> grantedAuthorityList = authentication.getAuthorities();
		for (String role : roles) {
			for (GrantedAuthority authority : grantedAuthorityList) {
				if (role.equals(authority.getAuthority())) {
					return true;
				}
			}
		}

		return false;
	}

	/**
	 * 将UserDetails保存到Security Context.
	 * 
	 * @param userDetails 已初始化好的用户信息.
	 * @param request 用于获取用户IP地址信息,可为Null.
	 */
	public static void saveUserDetailsToContext(UserDetails userDetails, HttpServletRequest request) {
		PreAuthenticatedAuthenticationToken authentication = new PreAuthenticatedAuthenticationToken(userDetails,
				userDetails.getPassword(), userDetails.getAuthorities());

		if (request != null) {
			authentication.setDetails(new WebAuthenticationDetails(request));
		}

		SecurityContextHolder.getContext().setAuthentication(authentication);
	}

	/**
	 * 取得Authentication, 如当前SecurityContext为空时返回null.
	 */
	public static Authentication getAuthentication() {
		SecurityContext context = SecurityContextHolder.getContext();

		if (context == null) {
			return null;
		}

		return context.getAuthentication();
	}

	/**
	 * 如何描述该方法
	 * 
	 * @return
	 */
	public static Collection<SimpleGrantedAuthority> getCurrentUserAuthorities() {
		Collection<SimpleGrantedAuthority> grantedAuthorities = new ArrayList<SimpleGrantedAuthority>(0);
		UserDetails user = SpringSecurityUtils.getCurrentUser();
		if (user != null) {
			for (GrantedAuthority authority : user.getAuthorities()) {
				grantedAuthorities.add(new SimpleGrantedAuthority(authority.getAuthority()));
			}
		}
		return grantedAuthorities;
	}
}
