<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<%@ include file="/pt/common/taglibs.jsp"%>
<%@ include file="/pt/common/meta.jsp"%>
<%@ include file="/pt/dyform/dyform_css.jsp"%>
<%@ include file="/pt/dyform/dyform_js.jsp"%>
<%@ include file="/pt/dyform/dyform_ctljs.jsp"%>
<title>复制标准产能</title>
</head>
<body>
	<div class="div_body" style="">
		<div class="form_header" style="">
			<div class="form_title">
				<h2>复制标准产能</h2>
			</div>
			<div id="toolbar" class="form_toolbar">
				<div class="form_operate">
					<button id="form_save" type="button">复制</button>
					<button id="form_close" type="button">关闭</button>
				</div>
			</div>
		</div>
		<form action="" id="capacityform" class="dyform">
			<table>
				<tr>
					<td class="title" colspan="2">复制标准产能</td>
				</tr>
				<tr class="field">
					<td width="110px;">生管</td>
					<td><input type="text" name="zsg" id="zsg"/></td>
				</tr>
				<tr class="field">
					<td width="110px;">开始日期</td>
					<td><input type="text" name="zrqStart" id="zrqStart" onfocus="WdatePicker({dateFmt:'yyyyMMdd'})" class="Wdate" /></td>
				</tr>
				<tr class="field">
					<td width="110px;">结束日期</td>
					<td><input type="text" name="zrqEnd" id="zrqEnd" onfocus="WdatePicker({dateFmt:'yyyyMMdd'})" class="Wdate" /></td>
				</tr>
				<tr class="field">
					<td width="110px;">批量上传</td>
					<td>
						<button type="button" id="btn_template_download" onclick="downloadTemplate();">模板下载</button>
						<input type="file" name="uploadFile" id="uploadFile" />
						<button type="button" id="upload">上传</button>
					</td>
				</tr>
			</table>
		</form>
		<div style="display: none">
			<iframe  name="download_template_form_iframe" style="width: 1px; height: 0px;" ></iframe>
		</div>
		<form id="downloadForm" method="post" target="download_template_form_iframe">
		</form>
	</div>
	</div>
</body>
<script type="text/javascript">
$(document).ready(function() {
	var div_body_width = $(window).width() * 0.76;
	$(".form_header").css("width", div_body_width - 5);
	$(".div_body").css("width", div_body_width);
	$("#form_close").click(function() {
		window.close();
	});
	$("#form_save").click(function() {
		JDS.call({
			service : "productionLineCapacityService.copyLineCapacity",
			data : [  $("#zsg").val(), $("#zrqStart").val(), $("#zrqEnd").val() ],
			async : false,
			success : function(result) {
				if(result.data.res=="success"){
					window.opener.reloadFileParentWindow();
					window.close();
				}else{
					oAlert2(result.data.err);
				}
			}
		});
	});
	$("#upload").click(function(){
		var file = $("#uploadFile").val();
		if (file == '') {
			oAlert("请选择Excel文件");
			return;
		}
		if (file.indexOf(".") < 0) {
			return;
		}
		var fileType = file.substring(file.lastIndexOf("."), file.length);
		if (fileType == ".xls" || fileType == ".xlsx") {
			$.ajaxFileUpload({
				url : ctx + "/productionPlan/lineCapacity/upload",// 链接到服务器的地址
				secureuri : false,
				fileElementId : 'uploadFile',// 文件选择框的ID属性
				dataType : 'text', // 服务器返回的数据格式
				success : function(data, status) {
					oAlert(data);
				},
				error : function(data, status, e) {
					oAlert("导入失败");
				}
			});
		} else {
			oAlert("请选择Excel文件");
		}
	});
});
/**
 * 下载模板
 */
function downloadTemplate(){
	$('#downloadForm').attr("action",ctx + "/productionPlan/lineCapacity/download");
	$('#downloadForm').submit();
}
/**
 * 上传
 */
function upload(){
	var file = $("#uploadFile").val();
	if (file == '') {
		oAlert("请选择Excel文件");
		return;
	}
	if (file.indexOf(".") < 0) {
		return;
	}
	var fileType = file.substring(file.lastIndexOf("."), file.length);
	if (fileType == ".xls" || fileType == ".xlsx") {
		$.ajaxFileUpload({
			url : ctx + "/productionPlan/lineCapacity/upload",// 链接到服务器的地址
			secureuri : false,
			fileElementId : 'uploadFile',// 文件选择框的ID属性
			dataType : 'text', // 服务器返回的数据格式
			success : function(data, status) {
				oAlert(data);
			},
			error : function(data, status, e) {
				oAlert("导入失败");
			}
		});
	} else {
		oAlert("请选择Excel文件");
	}
}
</script>
</html>