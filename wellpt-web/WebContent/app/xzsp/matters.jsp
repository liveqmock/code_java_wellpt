<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ include file="/pt/common/taglibs.jsp"%>
<link href="${ctx}/resources/theme/css/urlPage.css" rel="stylesheet">
<style>
.ui-accordion .ui-accordion-content {
	border-right-color: #fff;
}

a.belonging-unit {
	text-decoration: none;
	cursor: pointer;
}

a.belonging-unit:hover {
	color: #FF8F05;
}

.parallel-matters-label {
	display: inline;
	font-size: 12px;
}

#matters_accordion ul {
	padding-left: 0px;
	font-size: 13.2px;
	color: #222;
	font-family: Lucida Grande, ​Lucida Sans, ​Arial, ​sans-serif;
}
</style>
<div>
	<div id="matters_accordion" style="width: 220px; float: left;">
		<h3>串联</h3>
		<div>
			<ul>
				<c:forEach items="${unitMap}" var="entry" varStatus="indexStatus">
					<c:set var="belongingUnit" value="${entry.key}" />
					<li><a name="${belongingUnit.id}" class="belonging-unit"><c:out
								value="${belongingUnit.name}" /></a></li>
					<%-- 				<c:out value="${entry.value}" /> --%>
				</c:forEach>
			</ul>
		</div>
		<h3>并联</h3>
		<div>
			<ul>
				<c:forEach items="${parallelMattersMap}" var="entry">
					<c:set var="matters" value="${entry.key}" />
					<li><a name="${matters.belongingUnitId}"
						mattersUuid="${matters.uuid}"
						class="belonging-unit parallel-matters"><c:out
								value="${matters.name}" /></a></li>
				</c:forEach>
			</ul>
		</div>
	</div>

	<div class="viewContent" style="width: 300px; float: left;">
		<!-- 串联事项 -->
		<c:forEach items="${unitMap}" var="entry" varStatus="indexStatus">
			<c:set var="belongingUnit" value="${entry.key}" />
			<c:set var="unitMatters" value="${entry.value}" />
			<c:choose>
				<c:when test="${indexStatus.index == 0}">
					<div class="con unit_${belongingUnit.id} unit-matters current_unit">
				</c:when>
				<c:otherwise>
					<div class="con unit_${belongingUnit.id} unit-matters"
						style="display: none;">
				</c:otherwise>
			</c:choose>
			<div class="gl_con_r">
				<div class="gl_con2">
					<div class="gl_con2_l">
						<ul>
							<c:forEach items="${unitMatters}" var="matters">
								<li class="flowItem" style="width: 530px;"><span
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
			<c:if test="${empty unitMatters}">
				<div style="margin-top: 15px;">没有可选的办理事项</div>
			</c:if>
	</div>
	</c:forEach>

	<!-- 并联事项 -->
	<c:forEach items="${parallelMattersMap}" var="entry">
		<c:set var="matters" value="${entry.key}" />
		<c:set var="parallelMatters" value="${entry.value}" />
		<div class="con unit_${matters.belongingUnitId}"
			style="display: none;">
			<div class="gl_con_r">
				<div class="gl_con2">
					<div class="gl_con2_l">
						<ul>
							<c:forEach items="${parallelMatters}" var="matters">
								<li class="flowItem" style="width: 530px;"><span
									style="display: none;"><img
										src="${ctx}/resources/pt/images/cms/gl_icon3.jpg" />流程图</span> <input
									type="checkbox" id="cb_${matters.uuid}" name="parallelMatters"
									value="${matters.uuid}"
									style="margin-bottom: 0.5em; margin-right: 0.5em;"><label
									for="cb_${matters.uuid}" class="parallel-matters-label">${matters.name}</label></li>
							</c:forEach>
						</ul>
					</div>
					<div class="clear"></div>
				</div>
			</div>
			<div class="clear"></div>
			<c:if test="${empty parallelMatters}">
				<div style="margin-top: 15px;">没有可选的办理事项</div>
			</c:if>
		</div>
	</c:forEach>
</div>
<div style="clear: both;"></div>
</div>
