<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html lang="en">
<head>
<%@ include file="/pt/common/taglibs.jsp"%>
<%@ include file="/pt/common/meta.jsp"%>
<%@ include file="/pt/dytable/form_css.jsp"%>	
<title>主体注册</title>
</head>
<body>
	<div class="div_body">
		<div class="div_body_content">
			<div class="form_header">
				<!--标题-->
				<div class="form_title">
					<h2>主体注册信息</h2>
				</div>
				<div id="toolbar" class="form_toolbar"
					style="padding: 8px 0 10px 0px;">
					<div id="mini_wf_opinion" style="margin-bottom: 2px;">
					</div>
					<div class="form_operate" id="btns" style="display: none;">
							<button id="C001010" name="C001010">审核通过</button>
							<button id="C001011" name="C001011">审核不通过</button>
					</div>
				</div>
			</div>
			<div class="form_content" style="width: 875px; margin: 0 15px 0 25px;"></div>
			
			
		<!-- 动态表单 -->
		<div id="dyform">
			<div class="dytable_form">
			   <div class="post-sign">	
				<div class="post-detail">
				<!-- 表类型(tableType):1主表,2从表 -->
				<table width="100%"  tableType="1" id="005" editType="1">
				<tbody>
				  <tr class="title">
				    <td class="Label" colspan="5" id="tab">${object.mc}</td>
				  </tr>
				  <tr>
					 <td class="Label">注册号</td>
				    <td class="value" colspan="4">
				    	${object.zch}
				    </td>
				  </tr>
				  <tr class="odd">
					<td class="Label">名称</td>
				    <td class="value">
						${object.mc}
				    </td>
				    <td></td>
					<td class="Label">法人姓名</td>
				    <td class="value">
				  		${object.frxm}
				    </td>
				  </tr>
				  <tr>
					 <td class="Label">身份证号码</td>
				    <td class="value" colspan="4">
				    	${object.frid}
				    </td>
				  </tr>
				  <tr class="odd">
					<td class="Label">手机</td>
				    <td class="value">
						${object.mp}
				    </td>
				    <td></td>
					<td class="Label">邮件</td>
				    <td class="value">
				  		${object.email}
				    </td>
				  </tr>		
				  <tr>
					 <td class="Label">提交时间</td>
				    <td class="value" colspan="4">
				    	<fmt:formatDate value="${object.submitTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				    </td>
				  </tr>
				  <tr id="msg" style="display: none;" class="odd">
					<td class="Label">回复信息</td>
				    <td class="value" colspan="4">
						<textarea name="replyMsg" id="replyMsg" disabled="disabled" rows="5" cols="100">${object.replyMsg}</textarea>
				    </td>
				  </tr>	
				   <tr id="verifyMsg" style="display: none;">
					<td class="Label">是否通过审核</td>
				    <td class="value" colspan="4">
				    	<c:if test="${object.isVerify eq '1'}">通过审核</c:if>
				    	<c:if test="${object.isVerify eq '-1'}">未通过审核</c:if>
				    </td>
				  </tr>
				  <tr id="reMsg" style="display: none;" class="odd">
					<td class="Label">审核时间</td>
				    <td class="value" colspan="4">
				    	<fmt:formatDate value="${object.verifyTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				    </td>
				  </tr>
				   <tr>
					    <td class="Label"></td>
					    <td class="value"></td>
					</tr>
				 <%--  <tr id="reMsg" style="display: none;" class="odd">
					<td class="Label" id="user">审核部门</td>
				    <td class="value">
						${replyUnitName}
				    </td>
				    <td></td>
					<td class="Label" id="time">回复时间</td>
				    <td class="value">
						<fmt:formatDate value="${object.verifyTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				    </td>
				  </tr>		 --%>
				  </tbody>
				</table>
				</div>
			  </div>
			</div>
		</div>
		
			<input type="hidden" id="uuid" name="uuid" value="${object.uuid}">
			<input type="hidden" id="isVerify" name="isVerify" value="${object.isVerify}">
			
		</div>
	</div>
	<div class="body_foot"></div>
	
	<!-- Project -->
	<%@ include file="/pt/dytable/form_js.jsp"%>
	<script type="text/javascript" src="${ctx}/resources/pt/js/global.js"></script>
	<script type="text/javascript" src="${ctx}/resources/pt/js/exchangedata/registerapply.js"></script>
	<script type="text/javascript" src="${ctx}/resources/pt/js/common/jquery.cmsWindown.js"></script>
	<script type="text/javascript">
		$(function() {
			adjustWidthToForm();
			$(window).resize(function(e) {
				// 调整自适应表单宽度
				adjustWidthToForm();
			});
			// 调整自适应表单宽度
			function adjustWidthToForm() {
				var div_body_width = $(window).width() * 0.76;
				$(".form_header").css("width", div_body_width - 5);
				$(".div_body").css("width", div_body_width);
				//var formWidth = $(dytableSelector + ".post-detail").width();
				// 调整标题与操作按钮宽度
				// $(".form_header>.form_title").css("width", formWidth);
				// $(".form_header>.form_toolbar>.form_operate").css("width",
				// formWidth);
				
				//$(".form_content").css("width", formWidth);
			}
			
			$(".form_header .form_close").click(function(e) {
				returnWindow();
				window.close();
			});
		});
	</script>
</body>
</html>