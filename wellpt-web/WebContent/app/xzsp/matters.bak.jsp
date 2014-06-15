<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ include file="/pt/common/taglibs.jsp"%>

<link href="${ctx}/resources/theme/css/urlPage.css" rel="stylesheet">
<div class="viewContent">
	<div class="con">
		<div class="gl_con_r">
			<div class="gl_tit4"></div>
			<div class="gl_con2">
				<div class="gl_con2_l">
					<ul>
						<c:forEach items="${matterses}" var="matters">
							<li class="flowItem" style="width: 263px;"><span
								style="display: none;"><img
									src="${ctx}/resources/pt/images/cms/gl_icon3.jpg" />流程图</span><a
								src="${ctx}/xzsp/matters/new?mattersUuid=${matters.uuid}&ep_mattersUuid=${matters.uuid}"
								json='${matters.json}' href="#">${matters.name}</a></li>
						</c:forEach>
					</ul>
				</div>
				<div class="clear"></div>
			</div>
		</div>
		<div class="clear"></div>
		<c:if test="${empty matterses}">
			<div style="margin-top: 15px;">没有可选的办理事项</div>
		</c:if>
	</div>
</div>
