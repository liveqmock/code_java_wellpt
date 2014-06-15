<%@page import="java.net.URLEncoder"%>
<%@page import="com.wellsoft.pt.utils.security.SpringSecurityUtils"%>
<%@ page pageEncoding="utf-8"%>
<%@page import="org.springframework.context.i18n.LocaleContextHolder"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="security"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://ckeditor.com" prefix="ckeditor"%>
<%@ taglib uri="/wellpt/tags/privilege" prefix="privilege"%>
<%@ taglib uri="/wellpt/tags/table" prefix="t"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<%
	// 目前language设到cookie中，以后直接从session获取
	String contextPath = request.getContextPath();
	Cookie cookie = new Cookie("language", URLEncoder.encode(LocaleContextHolder.getLocale().toString(),
			"UTF-8"));
	cookie.setPath(contextPath.length() > 0 ? contextPath : "/");
	response.addCookie(cookie);
	Cookie ctx = new Cookie("ctx", contextPath);
	response.addCookie(ctx);
	Cookie currentUsername = new Cookie("cookie.current.username", URLEncoder.encode(
			SpringSecurityUtils.getCurrentLoginName(), "UTF-8"));
	currentUsername.setPath(contextPath.length() > 0 ? contextPath : "/");
	currentUsername.setMaxAge(30 * 60);
	response.addCookie(currentUsername);
%>
