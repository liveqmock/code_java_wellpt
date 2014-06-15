<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="en">
<head>
<%@ include file="/pt/common/taglibs.jsp"%>
<%@ include file="/pt/common/meta.jsp"%>
<%@ include file="/pt/dytable/form_css.jsp"%>	
<%@ include file="/pt/dytable/form_js.jsp"%>	
<title>附件夹</title>
<script src="${ctx}/resources/jquery/jquery.js"></script>
<script src="${ctx}/resources/jqueryui/js/jquery-ui.js"></script>
<script type="text/javascript" src="${ctx}/resources/pt/mail/global_zh_CN.js"></script>
<script type="text/javascript" src="${ctx}/resources/pt/js/global.js"></script>
<script src="${ctx}/pt/mail/js/mail.js" type="text/javascript"></script>
<script type="text/javascript" src="${ctx}/resources/timers/jquery.timers.min.js"></script>
</head>
<body>
<div class="viewContent">
<style type="text/css">

.form_toolbar {
    background: none repeat scroll 0 0 #F4F8FA;
    border-bottom: 1px solid #FFF;
    padding: 8px;
    text-align: right;
}
.form_operate {
    margin-top: 0px;
}
.form_toolbar button {
    background: url("/wellpt-web/resources/theme/images/v1_icon.png") no-repeat scroll 0 -165px transparent;
    border: 1px solid #DEE1E2;
    color: #0F599C;
    cursor: pointer;
    font-size: 12px;
    font-family: "Microsoft YaHei";
    margin-left: 2px;
    padding: 0 3px;
}
.form_toolbar button:hover{
	color: #ff7200;
}
.body_middle {
    border-bottom: 1px solid #ddd;
    border-top: 1px solid #ddd;
/*     height: 457px; */
/*     overflow: auto; */
    background: #fff;
}
.body_bottom .form_toolbar {
    border-top: 1px solid #FFF;
    text-align: left;
}
.body_bottom .form_operate .label {
    background: none repeat scroll 0 0 transparent;
    color: #666666;
    cursor: auto;
    float: right;
    font-family: "Microsoft YaHei";
    font-size: 12px;
    font-weight: inherit;
    margin-right: 5px;
    padding-top: 4px;
    text-shadow: 0 0 0 #666666;
}
.pageOne{
	background: url("${ctx}/resources/theme/images/v1_icon.png") no-repeat scroll 0 -165px transparent;
    border: 1px solid #DEE1E2;
    color: #0F599C;
    cursor: pointer;
    float: right;
    height: 20px;
    padding: 0 8px;
}
.pageOne:hover{
	color: #ff7200;
}

.dyform_table td {
    display: block;
}
.tdCheckbox{
	float: left;
	width: 20px;
	height: 20px;
}
#img{
	float: left;
}
.fileoperate,.fileSize,.fileName{
	text-indent: 20px;
}
.fileItem{
	border: 1px solid #FFFFFF;
    display: block;
    float: left;
    height: 155px;
    margin: 5px 15px;
    padding: 2px;
}
.fileItem:hover{
	border: 1px solid #C5CACD;
}

#img > img {
    height: 94px;
    width: 92px;
}
</style>
				<div  class="form_header">
					<!--标题-->
<!-- 					<div class="form_title"> -->
<%-- 						<h2><spring:message code="mail.label.fileFolder"/></h2><div class="form_close" title="关闭"></div> --%>
<!-- 					</div> -->
					<div id="toolbar" class="form_toolbar">
						<div id="mini_wf_opinion" style="margin-bottom: 2px;">
	
						</div>
						<div class="form_operate">
							<button id="btn_close" onclick="find2('star','${findName }');" type="button"><spring:message code="mail.label.star"/></button>
							<button id="btn_close" onclick="find2('img','${findName }');" type="button"><spring:message code="mail.label.img"/></button>
							<button id="btn_close" onclick="find2('word','${findName }');" type="button"><spring:message code="mail.label.word"/></button>
							<button id="btn_close" onclick="find2('mp3','${findName }');" type="button"><spring:message code="mail.label.mp3"/></button>
							<button id="btn_close" onclick="find2('avi','${findName }');" type="button"><spring:message code="mail.label.avi"/></button>
							<button id="btn_close" onclick="find2('rar','${findName }');" type="button"><spring:message code="mail.label.rar"/></button>
							<button id="btn_close" onclick="find2('','${findName }');" type="button"><spring:message code="mail.label.allFile"/></button>
						</div>
					</div>
				<div class="form_content" style="width: 933px;"></div>
				
				<!-- 动态表单 -->
				<div id="dyform">
					<div class="dytable_form">
						<div class="post-sign">
<!-- 							<div class="post-detail">表类型(tableType):1主表,2从表 -->
<!-- 								<table edittype="1" id="002" tabletype="1" width="100%"> -->
<!-- 									<tbody> -->
										
<!-- 									</tbody>						 -->
<!-- 								</table> -->
<!-- 							</div> -->
						</div>
					</div>
				</div>
			</div>
		<div class="body_middle">
			<table>
				<tr>
					<c:forEach items="${fileList }" var="frow" varStatus="status">
						<td class="fileItem" id="${frow.uuid}2"  onclick="fselect('${frow.uuid }');" onmouseover="showCheck('${frow.uuid }');" onmouseout="hideCheck('${frow.uuid }');">
							<table class="dyform_table">
							<tr>
								<td class="tdCheckbox" align="right" width="2%" valign="top">
								<div class="tdCheckbox_div" >
									<input id="${frow.uuid}"  style="display: none;" type="checkbox" align="left"/>
									<input id="${frow.uuid }3" type="hidden" value="${frow.fileName }"/>
									<input id="${frow.uuid }4" type="hidden" value="${frow.fileMid }"/>
								</div>
								</td>
								<td id="img">
									<c:if test="${frow.fileType=='img' }">
										<img src="${ctx }/pt/mail/js/img.jpg"></img>
									</c:if>
									<c:if test="${frow.fileType=='mp3' }">
										<img src="${ctx }/pt/mail/js/mp3.jpg"></img>
									</c:if>
									<c:if test="${frow.fileType=='rar' }">
										<img src="${ctx }/pt/mail/js/rar.jpg"></img>
									</c:if>
									<c:if test="${frow.fileType=='word' }">
										<img src="${ctx }/pt/mail/js/word.jpg"></img>
									</c:if>
									<c:if test="${frow.fileType=='avi' }">
										<img src="${ctx }/pt/mail/js/avi.jpg"></img>
									</c:if>
								</td>
							</tr>
							<tr class="dyform_table_indent">
								<td class="fileName" colspan="2" align="center" title="${frow.fileName }">
									<c:choose><c:when test="${fn:length(frow.fileName)>8 }"><c:out value="${fn:substring(frow.fileName,0,8) }.."/></c:when><c:otherwise><c:out value="${frow.fileName }"/></c:otherwise></c:choose>
								</td>
							</tr>
							<tr class="dyform_table_indent">
								<td class="fileSize"  colspan="2" align="center" >
									<c:out value="${frow.fileSize }"></c:out>
								</td>
							</tr>
							
							<tr class="dyform_table_indent">
								<td class="fileoperate"  id="${frow.uuid}6" style="display:none;" align="center" colspan="2">
									<a href="#"  onclick="fileSendOther('${frow.fileName}','${frow.fileMid }')"><img title="转发" src="${ctx }/pt/mail/js/other.gif"></img></a>
									<a href="${ctx}/mail/downLoad_file.action?fileMid=${frow.fileMid}&filename=${frow.fileName}"><img title="下载" src="${ctx }/pt/mail/js/download.gif"></img></a> 
									<c:if test="${frow.fileStatus=='1' }"><a  href="#" onclick="setFileStar('${frow.uuid}');"><img title="收藏" id="${frow.uuid}5"  src="${ctx }/pt/mail/js/star-on.png"></img></a></c:if>
									<c:if test="${frow.fileStatus=='0' }"><a  href="#" onclick="setFileStar('${frow.uuid}');"><img title="收藏" id="${frow.uuid}5"  src="${ctx }/pt/mail/js/star-off.png"></img></a></c:if>
								</td>
							</tr>
							</table>
						</td>
						<c:if test="${status.count%6==0 }">
							</tr>
							<tr>
						</c:if>
					</c:forEach>
				</tr>
				</table>
				</td>
				</tr>
				
				<tr>
				
					</tr>
				</table>
		</div>
		
		<div class="body_bottom">
			<div id="toolbar_bottom" class="form_toolbar">
				<div id="mini_wf_opinion">

				</div>
				<div class="form_operate">
					
				
					<button  onclick="fileSendOther2();" type="button"><spring:message code="mail.btn.send"/></button>
					<button onclick="fdownload();" type="button"><spring:message code="mail.btn.download"/></button>
					<button onclick="fstar();" type="button">添加收藏</button>
					<button onclick="fdelete();" type="button"><spring:message code="mail.btn.delete"/></button>
					
					<c:if test="${curPage<totalPage }">
					<a href="#" style="cursor: pointer;" onclick="window.location.href='${ctx}/mail/file_list.action?curPage=${curPage+1 }&ftype=${ftype }&findName=${findName }';">
						<div class="pageOne nextFilePage">></div>
					</a>
					</c:if>
					<c:if test="${curPage>=totalPage }">
						<div class="pageOne nextFilePage">></div>
					</c:if>
					
					<c:if test="${curPage>1 }">
					<a href="#" style="cursor: pointer;" onclick="window.location.href='${ctx}/mail/file_list.action?curPage=${curPage-1 }&ftype=${ftype }&findName=${findName }';">
						<div class="pageOne prevFilePage"><</div>
					</a>
					</c:if>
					<c:if test="${curPage<=1 }">
						<div class="pageOne prevFilePage"><</div>
					</c:if>
					
					
					
					<label class="label">
					<spring:message code="mail.label.find1"/>
					<c:out value="${totalSize }"></c:out>
					<spring:message code="mail.label.find2"/>
					</label>
				</div>
			</div>
		</div>
		
<%-- 	<script src="${ctx}/resources/jquery/jquery.js"></script> --%>
	<script type="text/javascript" src="${ctx}/resources/pt/js/global.js"></script>
<%-- 	<script type="text/javascript" src="${ctx}/resources/pt/js/common/jquery.cmsWindown.js"></script> --%>
	<script type="text/javascript">
		$(".form_header .form_close").click(function(e) {
			returnWindow();
			window.close();
		});
	</script>
</div>
</body>
</html>