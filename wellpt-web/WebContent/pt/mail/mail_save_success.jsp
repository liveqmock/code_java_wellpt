<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<%@ include file="/pt/common/taglibs.jsp"%>
<%@ include file="/pt/common/meta.jsp"%>
<title>mail_send_success</title>
<link href="${ctx}/resources/pt/css/mail_send_success.css" rel="stylesheet">
<script src="${ctx}/resources/jquery/jquery.js"></script>
</head>
<body>
<%-- <div class="mail_send_success_header">
	<!--标题-->
	<div class="mail_send_success_title">
		<h3>保存成功</h3>
	</div>
	<div id="toolbar" class="mail_send_success_operate">
		<button id="mail_send_success_close">关闭</button>
		<script type="text/javascript">
			$("#mail_send_success_close").click(function() {
				window.close();
			});
		</script>
	</div>
	</div>
	<div class="mail_send_success_content"></div>
	<div id="addFolder"></div>
	<div class="mail_send_success_form">
		<div class="post-sign">	
			<div class="post-detail">
				<table width="100%">  
					<tbody>
				        <tr class="odd">
					        
				        	<td width="90%">
				        		<div style="OVERFLOW-Y: auto; OVERFLOW-X:hidden;height:200px;TEXT-ALIGN: center;font-size:30px;" class="entryContent">
				        			您的邮件已保存成功，并已保存到"草稿箱"文件夹<br />
				        			<c:if test="${mailType!='0' }">此邮件是定时邮件，将在 ${nymdhs } 发出。</c:if></font><br/>
									<c:if test="${mailType=='0' }">
										<a href="#" onclick="window.location.href='${ctx}/mail/mailbox_view.action?rel=2&mailname=0&uuid=${uid}&pageNo=0&mtype=0';">查看此邮件</a><br/>
									</c:if>
									<c:if test="${mailType!='0' }">
										<a href="#" onclick="window.location.href='${ctx}/mail/mailbox_view2.action?rel=2&mailname=0&uuid=${uid}&pageNo=0&mtype=0';">查看此邮件</a><br/>
									</c:if>
									<a  href="#" onclick="returnWindow();window.close();">返回邮箱</a>
									
				        		</div>
			        		</td>
				        </tr>  
			   		 </tbody>
			    </table>
			</div>
		</div>
	</div> --%>
	<div class="form_header">
		<!--标题-->
		<div class="form_title">
			<h2><spring:message code="mail.label.saveSuccess"/></h2>
		</div>
		<div id="toolbar" class="form_toolbar">
			<div class="form_operate">
				<button id="btn_close" onclick="window.opener=null;window.close();" type="button"><spring:message code="mail.btn.close"/></button>
			</div>
		</div>
	</div>
	<div class="form_content"></div>
		 <!-- 动态表单 -->
		
	</div>
	<script type="text/javascript" src="${ctx}/resources/jquery/jquery.js"></script>
	<script type="text/javascript" src="${ctx}/resources/pt/js/global.js"></script>
	<script type="text/javascript" src="${ctx}/resources/timers/jquery.timers.min.js"></script>
</body>
</html>