<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>文件管理</title>
<%@ include file="/pt/common/taglibs.jsp"%>
<style type="text/css">
</style>
<%@ include file="/pt/common/meta.jsp"%>

</head>
<body>
	<!-- Bootstrap -->
	<link rel="stylesheet"
		href="${ctx}/resources/bootstrap/css/bootstrap.css" />
	<link rel="stylesheet" type="text/css"
		href="${ctx}/resources/jqueryui/css/base/jquery-ui.css" />
	<link rel="stylesheet" type="text/css"
		href="${ctx}/resources/ztree/css/zTreeStyle/zTreeStyle.css" />
	<link rel="stylesheet" href="${ctx}/resources/pt/css/fileManager.css" />

	<input type="hidden" id="currentId" value="">
	<input type="hidden" id="objType" value="">
	<input type="hidden" id="currentOperate" value="">
	<input type="hidden" id="moveToFolderId" value="">
	<input type="hidden" id="parentUuid" value="${folderUuid}">

	<div class="file_list">
		<div class="file_list_h1">
			<c:if test="${ folderUuid eq ''}">
				<a class="btn" href="${ctx}/folder/listView" target="_blank"><i
					class="icon-plus"></i>列表查看</a>
			</c:if>
			<c:if test="${isAdmin eq 'true' && folderUuid eq ''}">
				<a class="btn" href="${ctx}/announcement/folder/add?parentUuid="
					target="_blank"><i class="icon-plus"></i>增加库</a>
			</c:if>
			<c:if test="${ folderUuid ne ''}">
				<c:choose>
					<c:when test="${(folderAdmin eq '1') || (libAdmin eq '1')}">
						<a class="btn"
							href="${ctx}/announcement/folder/add?parentUuid=${folderUuid}"
							target="_blank"><i class="icon-plus"></i>增加夹</a>
						<a class="btn" id="addFile" href="#"><i class="icon-plus"></i>增加文档</a>
						<a class="btn" href="#" id="move">移动</a>
						<a class="btn" href="#" id="copy">拷贝</a>
						<a class="btn" href="#" id="import">导入</a>


					</c:when>

					<c:when test="${ createFile eq '1'}">
						<a class="btn" id="addFile" href="#"><i class="icon-plus"></i>增加文档</a>

					</c:when>

				</c:choose>
			</c:if>
		</div>
		<div class="file_list_h2">
			<div class="file_list_h2_h1">
				<c:choose>
					<c:when test="${ folderUuid eq libUuid }">
						<div class="file_list_h2_h1_l1 file_list_h2_h1_l">${parentTitle}</div>
					</c:when>
					<c:when test="${ folderUuid ne ''}">
						<div class="file_list_h2_h1_l1 file_list_h2_h1_l">
							<a href="${ctx}/cms/folder/list/${parentUuid}?libUuid=${libUuid}">返回上一级</a>&nbsp;|
							<%--	<a href="${ctx}/cms/folder/list/${libUuid}?libUuid=${libUuid}">${libName}</a> --%>
							<c:if
								test="${(!empty parentFolders) && fn:length(parentFolders) > 0}">
								<c:forEach items="${parentFolders}" var="tempFolder"
									varStatus="status">
									<a
										href="${ctx}/cms/folder/list/${tempFolder.uuid}?libUuid=${libUuid}">${tempFolder.title}</a>
									<c:if test="${status.index < (fn:length(parentFolders)) }">></c:if>
								</c:forEach>
							</c:if>
							<span class="file_map">${parentTitle}</span>
						</div>
					</c:when>
				</c:choose>
				<!-- 				<div class="file_list_h2_h1_l2 file_list_h2_h1_l"> -->
				<%-- 					已全部加载，共<span class="file_num">${pageFile.page.totalCount }</span>个 --%>
				<!-- 				</div> -->
			</div>
			<div class="file_list_h2_h2">
				<table>
					<thead>
						<tr>
							<td width="5%" style="padding: 0px; text-align: center;"><input
								type="checkbox" class="checkAll"></td>
							<td width="50%">文件名</td>
							<td width="25%">创建日期</td>
						</tr>
					</thead>

					<c:choose>
						<c:when
							test="${(!empty childfolders.result) && fn:length(childfolders.result) > 0}">
							<c:forEach items="${childfolders.result}" var="folder">
								<tr class="folderList" objType="folder" objId="${folder.uuid}">
									<td width="5%" style="padding: 0px; text-align: center;"><input
										type="checkbox"></td>
									<td>
										<div style="position: relative;">
											<div class="file_icon folder_img"
												onclick="window.location.href='${ctx}/cms/folder/list/${folder.uuid}?libUuid=${libUuid}'"></div>
											<div class="file_name">
												<a
													href="${ctx}/cms/folder/list/${folder.uuid}?libUuid=${libUuid}"
													rel=view_folder>${folder.title}</a>
											</div>
										</div>
									</td>
									<td><fmt:formatDate value="${folder.createTime}"
											type="both" /></td>
								</tr>
							</c:forEach>
							<c:forEach items="${childFiles.result}" var="file">
								<tr class="folderList" objType="file" objId="${file.uuid}">
									<td width="5%" style="padding: 0px; text-align: center;"><input
										type="checkbox"></td>
									<td>
										<div style="position: relative;">
											<%-- <div class="file_icon file_${file.fileType}_img"></div> --%>
											<A href="${ctx}/cms/file/edit?uuid=${file.uuid}"
												target="_blank" rel=view_folder><div
													class="file_icon file_other_img"></div></A>
											<div class="file_name">
												<A href="${ctx}/cms/file/edit?uuid=${file.uuid}"
													target="_blank" rel=view_folder> <c:choose>
														<c:when test="${file.status eq '2'}">${file.title}
				                			<c:if test="${(!empty file.currentVersion)}">(${file.currentVersion})</c:if>
														</c:when>
														<c:when test="${file.status eq '1'}">${file.title}(草搞)</c:when>
														<c:otherwise>
				                			${file.title}
				                			</c:otherwise>
													</c:choose>
												</A>
											</div>

										</div>

									</td>
									<td><fmt:formatDate value="${file.createTime}" type="both" />
									</td>
								</tr>
							</c:forEach>
						</c:when>
						<c:when
							test="${(!empty childFiles.result) && fn:length(childFiles.result) > 0}">
							<c:forEach items="${childFiles.result}" var="file">
								<tr class="folderList" objType="file" objId="${file.uuid}">
									<td width="5%" style="padding: 0px; text-align: center;"><input
										type="checkbox"></td>
									<td>
										<div style="position: relative;">
											<%-- <div class="file_icon file_${file.fileType}_img"></div> --%>
											<A href="${ctx}/cms/file/edit?uuid=${file.uuid}"
												target="_blank" rel=view_folder><div
													class="file_icon file_other_img"></div></A>
											<div class="file_name">
												<A href="${ctx}/cms/file/edit?uuid=${file.uuid}"
													target="_blank" rel=view_folder> <c:choose>
														<c:when test="${file.status eq '2'}">${file.title}
				                			<c:if test="${(!empty file.currentVersion)}">(${file.currentVersion})</c:if>
														</c:when>
														<c:when test="${file.status eq '1'}">${file.title}(草搞)</c:when>
														<c:otherwise>
				                			${file.title}
				                			</c:otherwise>
													</c:choose>
												</A>
											</div>
										</div>
									</td>
									<td align="left"><fmt:formatDate
											value="${file.createTime}" type="both" /></td>
								</tr>
							</c:forEach>
						</c:when>
						<c:when
							test="${(!empty pageFile.result) && fn:length(pageFile.result) > 0}">
							<c:forEach items="${pageFile.result}" var="file">
								<tr class="folderList" objType="file" objId="${file.uuid}">
									<td width="5%" style="padding: 0px; text-align: center;"><input
										type="checkbox"></td>
									<td>
										<div style="position: relative;">
											<%-- <div class="file_icon file_${file.fileType}_img"></div> --%>
											<A href="${ctx}/cms/file/edit?uuid=${file.uuid}"
												target="_blank" rel=view_folder><div
													class="file_icon file_other_img"></div></A>
											<div class="file_name">
												<A href="${ctx}/cms/file/edit?uuid=${file.uuid}"
													target="_blank" rel=view_folder> <c:choose>
														<c:when test="${file.status eq '2'}">${file.title}
				                			<c:if test="${(!empty file.currentVersion)}">(${file.currentVersion})</c:if>
														</c:when>
														<c:when test="${file.status eq '1'}">${file.title}(草搞)</c:when>
														<c:otherwise>
				                			${file.title}
				                			</c:otherwise>
													</c:choose>
												</A>
											</div>
										</div>
									</td>
									<td align="left"><fmt:formatDate
											value="${file.createTime}" type="both" /></td>
								</tr>
							</c:forEach>
							<tr class="folderList">
								<td colspan="2">
									<div class="pagination pagination-centered">
										<ul>
											<li>
											<li class="active"><c:out value="${pageFile.pageNo }"></c:out>/<c:out
													value="${pageFile.totalPages }"></c:out>页</li>
											<c:if
												test="${pageFile.pageNo<=pageFile.totalPages&&pageFile.pageNo>1 }">
												<li><a
													href="${ctx}/folder/indexList?id=${uuid}&page=${pageFile.pageNo-1}">上一页</a>
												</li>
											</c:if>
											<c:if
												test="${pageFile.pageNo<=pageFile.totalPages && pageFile.totalPages >1}">
												<li><a
													href="${ctx}/folder/indexList?id=${uuid}&page=${pageFile.pageNo+1}">下一页</a>
												</li>
											</c:if>
										</ul>
									</div>
								</td>
							</tr>
							<!-- 分页插件放在这边 -->
						</c:when>
						<c:otherwise>

						</c:otherwise>
					</c:choose>

				</table>
			</div>
		</div>



		<div id="treeDialog" style="display: none">
			<ul id="folder_tree" class="ztree"></ul>
		</div>

		<div id="showTemplateDialog" style="display: none">
			请选择模板：<select id="templateList">
				<option value="">--请选择--</option>
			</select>
		</div>

		<div id="importDialog" style="display: none">
			选择并下载模板：<select class="excelSelect" id="excelSelect"></select> <br>
			<input type="file" id="upload" name="upload" /><input type="button"
				id="uploadBtn" value="上传" /> <input type="hidden"
				name="currentTemplateId" id="currentTemplateId" />
		</div>


	</div>
	<script type="text/javascript">
		var refleshFlag = "0";
	</script>
	<!-- /container -->
	<c:if test="${refleshLeft eq '1'}">
		<script type="text/javascript">
			refleshFlag = "1";
		</script>
	</c:if>
	<script src="${ctx}/resources/jquery/jquery.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/jqueryui/js/jquery-ui.js"></script>
	<script src="${ctx}/resources/bootstrap/js/bootstrap.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/ztree/js/jquery.ztree.js"></script>
	<script type="text/javascript" src="${ctx}/resources/pt/js/global.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/pt/js/file/list.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/pt/js/common/jquery.excelimportrule.js"></script>

</body>
</html>