<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ include file="/pt/common/taglibs.jsp"%>
<!DOCTYPE  html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title><spring:message code="fileManager.label.fileManager" /></title>
	<%@ include file="/pt/common/meta.jsp"%>
	
	<link rel="stylesheet" type="text/css" href="${ctx}/resources/ztree/css/zTreeStyle/zTreeStyle.css" />
	<link type="text/css" rel="stylesheet" href="${ctx}/resources/fileupload/jquery.fileupload-ui.css" />
	<link type="text/css" rel="stylesheet" href="${ctx}/resources/fileupload/fileupload.css" />
	<link type="text/css" rel="stylesheet" href="${ctx}/resources/fileupload/fileupload.css" />

</head>
<body>
	<div class="test_con3">
		<input type='hidden' id='uuid' name='uuid'  />
		<input type='hidden' id='ctype' name='ctype' />
		<table>
			<tr class='dialog_tr tr_odd' >
				<td align='left' width='20%'  class='left_td'>title</td>
				<td class='td_align_left'>
					<input  type='text' size='50' id='scheduleName' name='scheduleName' value='' />
					<select id='scheTag' onchange='selTag(this)'>
						<option value=''></option>
					</select>
					<div class='chosecolor'>
						<input type='hidden' id='mycolor' name='mycolor' value=''/>
						<span id='showcolor' style=''></span>
					</div>
					<div class='colorbutton' onclick='choseColor(this)'>"
						<div class='colors' style='display:none;'>
							<span class="selectcolor" style="background-color:black;" onclick="selColor('black');">&nbsp;&nbsp;&nbsp;&nbsp;</span>
							<span class="selectcolor" style="background-color:red ;" onclick="selColor('red');">&nbsp;&nbsp;&nbsp;&nbsp;</span>
							<span class="selectcolor" style="background-color:blue ;" onclick="selColor('blue');">&nbsp;&nbsp;&nbsp;&nbsp;</span>
							<span class="selectcolor" style="background-color:orange; " onclick="selColor('orange');">&nbsp;&nbsp;&nbsp;&nbsp;</span>
							<span class="selectcolor" style="background-color:green; " onclick="selColor('green');">&nbsp;&nbsp;&nbsp;&nbsp;</span>
							<span class="selectcolor" style="background-color:yellow; " onclick="selColor('yellow');">&nbsp;&nbsp;&nbsp;&nbsp;</span>
							<span class="selectcolor" style="background-color:purple; " onclick="selColor('purple');">&nbsp;&nbsp;&nbsp;&nbsp;</span>
							<span class="selectcolor" style="background-color:silver; " onclick="selColor('silver');">&nbsp;&nbsp;&nbsp;&nbsp;</span>
							<span class="selectcolor" style="background-color:tan; " onclick="selColor('tan');">&nbsp;&nbsp;&nbsp;&nbsp;</span>
							<span class="selectcolor" style="background-color:maroon;" onclick="selColor('maroon');">&nbsp;&nbsp;&nbsp;&nbsp;</span>
							<span class="selectcolor" style="background-color:olive;" onclick="selColor('olive');">&nbsp;&nbsp;&nbsp;&nbsp;</span>
							<span class="selectcolor" style="background-color:gray;" onclick="selColor('gray');">&nbsp;&nbsp;&nbsp;&nbsp;</span>
							<span class="selectcolor" style="background-color:lime;" onclick="selColor('lime');">&nbsp;&nbsp;&nbsp;&nbsp;</span>
						</div>
					</div>
				</td>
			</tr>
		
		<tr class="dialog_tr tr_even">
				</td>
			</tr>
			<tr class='dialog_tr tr_odd' >
				<td align='left' width='20%'  class='left_td'>title</td>
				<td class='td_align_left'>
					<input  type='text' size='50' id='scheduleName' name='scheduleName' value='' />
				</td>
			</tr>
			<tr class='dialog_tr tr_odd' >
				<td align='left' width='20%'  class='left_td'>title</td>
				<td class='td_align_left'>
					<input  type='text' size='50' id='scheduleName' name='scheduleName' value='' />
				</td>
			</tr>
			<tr class='dialog_tr tr_odd' >
				<td align='left' width='20%'  class='left_td'>title</td>
				<td class='td_align_left'>
					<input  type='text' size='50' id='scheduleName' name='scheduleName' value='' />
				</td>
			</tr>
			<tr class='dialog_tr tr_odd' >
				<td align='left' width='20%'  class='left_td'>title</td>
				<td class='td_align_left'>
					<input  type='text' size='50' id='scheduleName' name='scheduleName' value='' />
				</td>
			</tr>
			<tr class='dialog_tr tr_odd' >
				<td align='left' width='20%'  class='left_td'>title</td>
				<td class='td_align_left'>
					<input  type='text' size='50' id='scheduleName' name='scheduleName' value='' />
				</td>
			</tr>
			<tr class='dialog_tr tr_odd' >
				<td align='left' width='20%'  class='left_td'>title</td>
				<td class='td_align_left'>
					<input  type='text' size='50' id='scheduleName' name='scheduleName' value='' />
				</td>
			</tr>
			<tr class='dialog_tr tr_odd' >
				<td align='left' width='20%'  class='left_td'>title</td>
				<td class='td_align_left'>
					<input  type='text' size='50' id='scheduleName' name='scheduleName' value='' />
				</td>
			</tr>
			<tr class='dialog_tr tr_odd' >
				<td align='left' width='20%'  class='left_td'>title</td>
				<td class='td_align_left'>
					<input  type='text' size='50' id='scheduleName' name='scheduleName' value='' />
				</td>
			</tr>
			<tr class='dialog_tr tr_odd' >
				<td align='left' width='20%'  class='left_td'>title</td>
				<td class='td_align_left'>
					<input  type='text' size='50' id='scheduleName' name='scheduleName' value='' />
				</td>
			</tr>
			<tr class='dialog_tr tr_odd' >
				<td align='left' width='20%'  class='left_td'>title</td>
				<td class='td_align_left'>
					<input  type='text' size='50' id='scheduleName' name='scheduleName' value='' />
				</td>
			</tr>
			
			<tr class='dialog_tr tr_odd' >
				<td align='left' width='20%'  class='left_td'>title</td>
				<td class='td_align_left'>
					<input  type='text' size='50' id='scheduleName' name='scheduleName' value='' />
				</td>
			</tr>
			<tr class='dialog_tr tr_odd' >
				<td align='left' width='20%'  class='left_td'>title</td>
				<td class='td_align_left'>
					<input  type='text' size='50' id='scheduleName' name='scheduleName' value='' />
				</td>
			</tr>
			<tr>
				<td align='left' width='20%'  class='left_td'>fujian</td>
				<td id="annex_td" class='td_align_left'></td>
			</tr>
		</table>
	</div>

	<script type="text/javascript" src="${ctx}/resources/fileupload/js/jquery.fileupload.js"></script>	
	<script type="text/javascript" src="${ctx}/resources/fileupload/js/jquery.iframe-transport.js"></script>	
	<script type="text/javascript" src="${ctx}/resources/fileupload/js/jquery.fileupload-process.js"></script>	
	<script type="text/javascript" src="${ctx}/resources/fileupload/js/jquery.fileupload-fp.js"></script>	
	<script type="text/javascript" src="${ctx}/resources/fileupload/js/jquery.fileupload-ui.js"></script>	
	<script type="text/javascript" src="${ctx}/resources/fileupload/js/uuid.js"></script>	

	<script type="text/javascript">
		//上传邮件附件
		var elementID = "mail_schedule_ctlID";
		var fileupload = new WellFileUpload(elementID);
		var dbFiles = [];
		/* 	<c:if test="${dbFiles != '' and dbFiles != null}">
		 dbFiles = ${dbFiles};
		</c:if>
		for(var i in dbFiles){
			dbFiles[i].fileName = dbFiles[i].filename;
		} */
		fileupload.init(false,$("#annex_td"),false, false, dbFiles);	
	</script>
	
</body>
</html>