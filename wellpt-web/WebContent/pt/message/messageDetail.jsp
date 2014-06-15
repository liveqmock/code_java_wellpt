<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="/pt/common/taglibs.jsp"%>
<%@ include file="/pt/common/meta.jsp"%>
<%@ include file="/pt/dytable/form_css.jsp"%>	
<%@ include file="/pt/dytable/form_js.jsp"%>	
<title>消息内容</title>
</head>
<body>
	<div class="div_body">
		<div class="div_body_content">
		<div class="form_header">
			<!--标题-->
			<div class="form_title">
				<h2>消息内容</h2><div class="form_close" title="关闭"></div>
			</div>
			<div id="toolbar" class="form_toolbar" style="padding: 8px 0 10px 0px;">
				<div id="mini_wf_opinion" style="margin-bottom: 2px;">

				</div>
				<div class="form_operate">
					<button id="btn_dyform" name="btn_dyform" style="display: none">保存</button></div>
				</div>
		</div>
		<div class="form_content" style="width: 875px; margin: 0 15px 0 25px;"></div>
		
		<!-- 动态表单 -->
		<div id="dyform">
			<div class="dytable_form">
				<div class="post-sign">
					<div class="post-detail"><!-- 表类型(tableType):1主表,2从表 -->
						<table edittype="1" id="002" tabletype="1" width="100%">
							<tbody>
								<c:choose>
									<c:when test="${type=='send'}">
								        <tr class="odd">
											<td class="Label">收件人</td>
											<td class="value">${mc.recipientName}</td>
										</tr>
									</c:when>
									<c:when test="${type=='receive'}">
								         <tr class="odd">
											<td class="Label">发件人</td>
											<td class="value">${mc.senderName}</td>
										</tr>
									</c:when>
								</c:choose>
								<tr>
									<td class="Label">时间</td>
									<td class="value" colspan="3"><fmt:formatDate value="${mc.receivedTime}"
											type="both" /></td>
								</tr>
							
						        <tr class="odd">
							        <td class="Label">内容</td>
						        	<td class="value">
						        		${mc.body }
						       		</td>
						        </tr>  
						       <tr>
								    <td class="Label"></td>
								    <td class="value"></td>
								</tr>
							</tbody>						
						</table>
					</div>
				</div>
			</div>
		</div>
		</div>
	</div>
	<div class="body_foot">
	</div>
		
	<script src="${ctx}/resources/jquery/jquery.js"></script>
	<script type="text/javascript" src="${ctx}/resources/pt/js/global.js"></script>
	<script type="text/javascript" src="${ctx}/resources/pt/js/common/jquery.cmsWindown.js"></script>
	<script type="text/javascript">
		$(function(){
			$(window).resize(function(e) {
				// 调整自适应表单宽度
				adjustWidthToForm();
			});
			// 调整自适应表单宽度
			function adjustWidthToForm() {
				var div_body_width = $(window).width() * 0.76;
				$(".form_header").css("width", div_body_width - 5);
				$(".div_body").css("width", div_body_width);
			}
			
			$(".form_header .form_close").click(function(e) {
				returnWindow();
				window.close();
			});
		});
	</script>
</body>
</html>