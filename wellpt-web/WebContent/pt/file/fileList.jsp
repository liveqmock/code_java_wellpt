<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/functions"  prefix="fn" %> <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %> <%@ include file="/pt/common/taglibs.jsp"%><!DOCTYPE html><html lang="en"><head><title><spring:message code="fileManager.label.fileManager" /></title><style type="text/css"></style><%@ include file="/pt/common/meta.jsp"%><!-- Bootstrap --><link rel="stylesheet"	href="${ctx}/resources/bootstrap/css/bootstrap.min.css" /><link rel="stylesheet"	href="${ctx}/resources/bootstrap/css/bootstrap-responsive.min.css" />	<link rel="stylesheet" type="text/css"	href="${ctx}/resources/jqueryui/css/base/jquery-ui.css" /><link rel="stylesheet" type="text/css"	href="${ctx}/resources/layout/jquery.layout.css" /><link rel="stylesheet" type="text/css"	href="${ctx}/resources/ztree/css/zTreeStyle/zTreeStyle.css" />	<link rel="stylesheet"	href="${ctx}/resources/pt/css/fileManager.css" />	<link rel="stylesheet" type="text/css" media="screen"	href="${ctx}/resources/pt/css/form.css" /> <link rel="stylesheet" type="text/css"	href="${ctx}/resources/pt/css/custom-admin-style.css" /></head><body style="background-color: #F7F7F7;">	 	<input type="hidden" id="currentId"  value="">	<input type="hidden" id="objType"  value="">	<input type="hidden" id="currentOperate"  value="">	<input type="hidden" id="moveToFolderId"  value="">	<input type="hidden" id="parentUuid"  value="${uuid}">	<input id="folderUuid" name="folderUuid" type="hidden" value="${uuid}" />		<div class="file_list">	<div class="file_list_h1 form_operate">		<a id="show_list" href="#"><spring:message code="fileManager.btn.showListFile" /><!-- 切换至夹视图 --></a>	</div>	<div class="file_list_h2">		 		<div class="file_list_h2_h2">			<table>				<thead>					<tr>						<td ><spring:message code="fileManager.label.fileName" /></td>						<td ><spring:message code="fileManager.label.createTime" /></td>					</tr>				</thead>				<tbody>				 			 	 			 	<c:if test="${(!empty pageFile.result) && fn:length(pageFile.result) > 0}">					<c:forEach  items="${pageFile.result}" var="file" >					<tr  class="folderList"  objType="file"  objId="${file.uuid}">	                	<td >	                		<div style="position:relative;">								<div class="file_icon file_${file.fileType}_img"></div>								<div class="file_name">									<A onclick="fileManagerEditFunc('','${file.uuid}','${ctx}/fileManager/file/edit?uuid=${file.uuid}');" href="#" rel=view_folder >				                		<c:choose>				                			<c:when test="${file.status eq '2'}">${file.title}				                			<c:if test="${(!empty file.currentVersion)}">(${file.currentVersion})</c:if></c:when>				                			<c:when test="${file.status eq '1'}">${file.title}<spring:message code="fileManager.btn.workDraft" /></c:when>				                			<c:otherwise>				                			${file.title}				                			</c:otherwise>				                		</c:choose>	                				</A>	                			</div>	                			 	                		</div>	                	</td>	                	<td align="left">	                		<fmt:formatDate value="${file.createTime}"   type="both"/>	                	</td>                	</tr>                						</c:forEach>					</tbody> 			 	</c:if>			 	 							</table>			<c:if test="${!empty pageFile}">			<div class="table-footer" id="footBar">			<div id="finder-footer-333c3e" class="finder-footer">				 					<div style="float: left;padding-top: 2px;"><spring:message code="fileManager.label.pagePerShow" />${pageFile.pageSize}<spring:message code="fileManager.label.pageCount" />								<spring:message code="fileManager.label.pageAll" />${pageFile.totalCount}<spring:message code="fileManager.label.pagePerShow" />| </div><a class="firstpage" href="${ctx}/fileManager/folder/fileList?id=${uuid}&page=1"><img src="${ctx}/resources/pt/images/v1_first.png" /></a>					<c:if test="${pageFile.totalPages<=7}">						<a							<c:if test="${pageFile.pageNo !=1 }">class="txt_page" href="${ctx}/fileManager/folder/fileList?id=${uuid}&page=${pageFile.pageNo-1}"  onmousemove="page_move(this)" onmouseout="page_out(this)"</c:if>							<c:if test="${pageFile.pageNo ==1 }">class="txt_page_no"</c:if>>							<img src="${ctx}/resources/pt/images/v1_prev.png" width="11"								height="11" />						</a>						<c:forEach begin="1" end="${pageFile.totalPages}" var="p_nub" step="1"> 							<a href="${ctx}/fileManager/folder/fileList?id=${uuid}&page=${p_nub}" 								<c:if test="${p_nub != pageFile.pageNo }">class="nub_page"</c:if>								<c:if test="${p_nub == pageFile.pageNo}">class="nub_page_"</c:if>>								${p_nub}							</a>						</c:forEach>						<a							<c:if test="${pageFile.pageNo != pageFile.totalPages}">class="txt_page" href="${ctx}/fileManager/folder/fileList?id=${uuid}&page=${pageFile.pageNo+1}" onmousemove="page_move(this)" onmouseout="page_out(this)"</c:if>							<c:if test="${pageFile.pageNo == pageFile.totalPages}">class="txt_page_no"</c:if>>							<img src="${ctx}/resources/pt/images/v1_next.png" />						</a>						 					</c:if>					<c:if test="${pageFile.totalPages>7 && pageFile.pageNo<4}">						<a							<c:if test="${pageFile.pageNo !=1 }">class="txt_page" href="${ctx}/fileManager/folder/fileList?id=${uuid}&page=${pageFile.pageNo-1}" onmousemove="page_move(this)" onmouseout="page_out(this)"</c:if>							<c:if test="${pageFile.pageNo ==1 }">class="txt_page_no"</c:if>>							<img src="${ctx}/resources/pt/images/v1_prev.png" width="11"								height="11" />						</a>						<c:forEach begin="1" end="4" var="p_nub">							<a href="${ctx}/fileManager/folder/fileList?id=${uuid}&page=${p_nub}" 								<c:if test="${p_nub != pageFile.pageNo }">class="nub_page"</c:if>								<c:if test="${p_nub == pageFile.pageNo }">class="nub_page_"</c:if>>								${p_nub}							</a>						</c:forEach>						<div class="page_">							…						</div>						<div class="page_">							…						</div>						<a href="${ctx}/fileManager/folder/fileList?id=${uuid}&page=${pageFile.totalPages}"							class="nub_page">							${pageFile.totalPages}						</a>						<div							<c:if test="${pageFile.pageNo != pageFile.totalPages}">class="txt_page"  href="${ctx}/fileManager/folder/fileList?id=${uuid}&page=${pageFile.pageNo+1}" onmousemove="page_move(this)" onmouseout="page_out(this)"</c:if>							<c:if test="${pageFile.pageNo == pageFile.totalPages}">class="txt_page_no"</c:if>>							<img src="${ctx}/resources/pt/images/v1_next.png" />						</div>					 					</c:if>					<c:if						test="${pageFile.totalPages>7 && pageFile.currentPage>=4 && pageFile.pageNo<=(page.totalPages-2)}">						<a							<c:if test="${pageFile.pageNo !=1 }">class="txt_page" href="${ctx}/fileManager/folder/fileList?id=${uuid}&page=${pageFile.pageNo}"  onmousemove="page_move(this)" onmouseout="page_out(this)"</c:if>							<c:if test="${pageFile.pageNo ==1 }">class="txt_page_no"</c:if>>							<img src="${ctx}/resources/pt/images/v1_prev.png" width="11"								height="11" />						</a>						<a  href="${ctx}/fileManager/folder/fileList?id=${uuid}&page=1" class="nub_page">							1						</a>						<div class="page_">							…						</div>						<c:forEach begin="${pageFile.pageNo -1 }" end="${pageFile.pageNo + 1 }"							var="p_nub">							<a href="${ctx}/fileManager/folder/fileList?id=${uuid}&page=${p_nub}" 								<c:if test="${p_nub != pageFile.pageNo }">class="nub_page"</c:if>								<c:if test="${p_nub == pageFile.pageNo}">class="nub_page_"</c:if>>								${p_nub}							</a>						</c:forEach>						<div class="page_">							…						</div>						<a href="${ctx}/fileManager/folder/fileList?id=${uuid}&page=${pageFile.totalPages}"							class="nub_page">							${pageFile.totalPages}						</a>						<a							<c:if test="${pageFile.pageNo != pageFile.totalPages}">class="txt_page" href="${ctx}/fileManager/folder/fileList?id=${uuid}&page=${pageFile.pageNo+1}" onmousemove="page_move(this)" onmouseout="page_out(this)"</c:if>							<c:if test="${pageFile.pageNo == pageFile.totalPages}">class="txt_page_no"</c:if>>							<img src="${ctx}/resources/pt/images/v1_next.png" />						</a>					</c:if>					<c:if						test="${pageFile.totalPages>7 && pageFile.pageNo>6 && pageFile.pageNo>(pageFile.totalPages-2)}">						<a							<c:if test="${pageFile.pageNo !=1 }">class="txt_page" href="${ctx}/fileManager/folder/fileList?id=${uuid}&page=${pageFile.pageNo}"  onmousemove="page_move(this)" onmouseout="page_out(this)"</c:if>							<c:if test="${pageFile.pageNo ==1 }">class="txt_page_no"</c:if>>							<img src="${ctx}/resources/pt/images/v1_prev.png" width="11"								height="11" />						</a>						<a  href="${ctx}/fileManager/folder/fileList?id=${uuid}&page=1" class="nub_page">							1						</a>						<div class="page_">							…						</div>						<div class="page_">							…						</div>						<c:forEach begin="${pageFile.totalPages-3}" end="${pageFile.totalPages}" var="p_nub">							<a href="${ctx}/fileManager/folder/fileList?id=${uuid}&page=${p_nub}" 								<c:if test="${p_nub != pageFile.pageNo }">class="nub_page"</c:if>								<c:if test="${p_nub == pageFile.pageNo}">class="nub_page_"</c:if>>								${p_nub}							</a>						</c:forEach>						<a							<c:if test="${pageFile.pageNo != pageFile.totalPages}">class="txt_page" href="${ctx}/fileManager/folder/fileList?id=${uuid}&page=${pageFile.pageNo+1}" onmousemove="page_move(this)" onmouseout="page_out(this)"</c:if>							<c:if test="${pageFile.pageNo == pageFile.totalPages}">class="txt_page_no"</c:if>>							<img src="${ctx}/resources/pt/images/v1_next.png" />						</a>						 											</c:if>					<div class="jumpmodule" style="width:120px;"><a class="lastpage" href="${ctx}/fileManager/folder/fileList?id=${uuid}&page=${pageFile.totalPages}"><img src="${ctx}/resources/pt/images/v1_last.png" /></a> | <spring:message code="fileManager.label.pageNow" /> <input type="text" id="jumppage" onkeyup="entyKeyBoard(event,this,${pageFile.pageNo});" value="${pageFile.pageNo  }" /> <spring:message code="fileManager.label.pageInfo" /> /<spring:message code="fileManager.label.pageAll" />${pageFile.totalPages}<spring:message code="fileManager.label.pageInfo" /></div>					 				</div>			</div>			</c:if>		</div>	</div> 		 		 		<div>			<div id="treeDialog">				<ul id="folder_tree" class="ztree"></ul>			</div>		</div>				 		 		</div>	<script type="text/javascript">			var refleshFlag = "0";	</script>	<!-- /container -->	<c:if test="${refleshLeft eq '1'}">	<script type="text/javascript">			 refleshFlag = "1";	</script>		</c:if>	<script src="${ctx}/resources/jquery/jquery.js"></script>	<script type="text/javascript"		src="${ctx}/resources/jqueryui/js/jquery-ui.js"></script>	<script src="${ctx}/resources/bootstrap/js/bootstrap.js"></script>	<script type="text/javascript"		src="${ctx}/resources/ztree/js/jquery.ztree.js"></script>	<script type="text/javascript"		src="${ctx}/resources/pt/js/global.js"></script>	<script type="text/javascript"		src="${ctx}/resources/pt/js/common/jquery.alerts.js"></script>	<script type="text/javascript"		src="${ctx}/resources/pt/js/file/list.js"></script>	<script type="text/javascript" src="${ctx}/resources/utils/ajaxfileupload.src.js"></script>	<script type="text/javascript" src="${ctx}/resources/utils/ajaxfileupload.js"></script>	<script type="text/javascript" src="${ctx}/resources/pt/js/common/jquery.excelimportrule.js"></script>	<script>	  	function page_move(obj){		$(obj).attr("class","txt_page_");	}	function page_out(obj){		$(obj).attr("class","txt_page");	}		function entyKeyBoard(event,obj,currentPage){		var code = event.keyCode;		var gotopage = obj.value;		var totalPages = 0;		totalPages =  ${pageFile.totalPages};		if(gotopage==""){			alert(fileManager.pleaseInputPageNum);			return false;		}		if(gotopage.match(/^\+?[1-9][0-9]*$/)){			if(gotopage>totalPages){				alert(fileManager.WarnOutOfAllPage);				return false;			}		}else{			alert(fileManager.pleaseInputLegalPag);			return false;		}			    if (code == 13) {	    	window.location.href=ctx+'/fileManager/folder/fileList?id=${uuid}&page='+gotopage;	    }	}	</script></body></html>