<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<%@ include file="/pt/common/taglibs.jsp"%>
<%@ include file="/pt/common/meta.jsp"%>
<title>租户注册</title>
<link href="${ctx}/resources/bootstrap/css/bootstrap.min.css" rel="stylesheet">
<style type="text/css">
.container-fluid{
	 font-family: "Microsoft YaHei";
	 font-size: 12px;
	 padding: 0;
}
.register_title_div{
	background: url("${ctx}/resources/pt/images/icon.png") repeat-x scroll 0 -53px transparent;
    color: #FFFFFF;
    font-size: 18px;
    height: 35px;
    padding-top: 5px;
    position: fixed;
    width: 100%;
}
.register_title_div2 {
    margin-bottom: 0;
    margin-left: auto;
    margin-right: auto;
    width: 80%;
}
.row-fluid {
    margin: 75px auto 0;
    width: 80%;
}
.form-horizontal .control-label{
    margin-right: 10px;
    font-size: 12px;
    cursor: auto;
}
.form-horizontal .content_odd {
    background: none repeat scroll 0 0 #F7F7F7;
    margin: 0;
    padding: 7px;
}
.form-horizontal .content_even {
    margin: 0;
    padding: 10px;
}
.div_center{
	margin: 0 auto;
	width: 750px;
}
.controls input {
	width: 400px;
	font-size: 12px;
}
.container-fluid .div_button button {
    background: url("${ctx}/resources/theme/images/btn_blue.png") repeat scroll 0 0 transparent;
    border: 0 none;
    color: #FFFFFF;
    font-size: 12px;
    height: 23px;
    line-height: 23px;
    margin-left: 5px;
    overflow: hidden;
    width: 50px;
    padding: 0;
}
.container-fluid .div_button button:hover{
	background: url("${ctx}/resources/theme/images/btn_orange.png") repeat scroll 0 0 transparent;
}
.div_button {
    text-align: center;
}
.content_title {
    background: none repeat scroll 0 0 #0F599C;
    color: #FFFFFF;
    height: 26px;
    padding: 4px 0 0 10px;
}
.register_government{
	display: none;
}

.register_title_buttons {
	background: none repeat scroll 0 0 #F4F8FA;
    height: 28px;
    left: 0;
    padding-top: 7px;
    position: fixed;
    top: 40px;
    width: 100%;
}
.register_title_button {
    margin: 0 auto;
    width: 80%;
}
.form_btn{
	background: url("/wellpt-web/resources/theme/images/v1_icon.png") no-repeat scroll 0 -165px transparent;
    border: 1px solid #DEE1E2;
    color: #0F599C;
    cursor: pointer;
    font-family: "Microsoft YaHei";
    font-size: 12px;
    height: 20px;
    line-height: 16px;
    margin-right: 5px;
    float: right;
}
.form_btn:hover{
	color: #ff7200;
}
label[class=error]{
	color: red;
    display: inline;
    font-size: 12px;
    margin-left: 15px;
}
.depatermentType {
    height: 35px;
}
.depatermentType_child {
    height: 27px;
    margin: 0 auto;
    padding-top: 8px;
    width: 170px;
}
.depatermentType_item {
    background: none repeat scroll 0 0 #F6F6F6;
    border: 1px solid #DEE1E2;
    cursor: pointer;
    float: left;
    text-align: center;
    width: 80px;
}
.depatermentType_item:hover{
	background: none repeat scroll 0 0 #888888;
	color: #fff;
}
.depatermentType_child .activity{
	background: none repeat scroll 0 0 #888888;
	color: #fff;
}
/*******重写bootstrap********/
.row-fluid .offset4:first-child {
    margin: 0;
    width: 100%;
}
form {
    margin: 0;
}
.form-horizontal .controls {
    margin-left: 0;
}
</style>
<!-- Bootstrap -->
</head>
<body style="margin: 0px; padding: 0px;">
	<div class="container-fluid">
		<div class="register_title_div"><div class="register_title_div2">租户注册申请</div></div>
		<div class="register_title_buttons">
			<div class="register_title_button">
				<button type="submit" class="form_btn" id="btn_save">提交</button>
				<button class="form_btn" onclick="location='${ctx}/index'">返回</button>
			</div>
		</div>
		<div class="row-fluid">
			<div class="span4 offset4">
				<form id="register_form" method="post" class="form-horizontal">
						<div class="content_title">基本信息</div>
						<div class="register_basic_content">
							<div class="control-group  content_odd">
								<div class="div_center">
								<label for="account" class="control-label">账号</label>
								<div class="controls">
									<input id="account" name="account" type="text" 
										value="" />
								</div>
								</div>
							</div>
							<div class="control-group content_even">
							<div class="div_center">
								<label for="password" class="control-label">密码</label>
								<div class="controls">
									<input id="password" name="password" type="password"
										value="" />
								</div>
							</div></div>
							<div class="control-group content_odd">
							<div class="div_center">
								<label for="confirm_password content_even" class="control-label">再次输入密码</label>
								<div class="controls">
									<input id="confirm_password" name="confirm_password"
										type="password" value="" />
								</div>
							</div></div>
							
						</div>
						<div class="depatermentType">
							<input type="hidden" id="depatermentType" name="depatermentType" value="business" />
							<div class="depatermentType_child">
							     <div class="depatermentType_item activity" val="business">企业</div>
							     <div class="depatermentType_item " val="government">政府</div>
							</div>
						</div>
						<div class="register_detail_content register_business">
						<div class="content_title">企业信息</div>
							<div class="control-group content_odd">
								<div class="div_center">
								<label for="name" class="control-label">企业名称</label>
								<div class="controls">
									<input id="name" name="name" type="text"
										value=""/>
								</div>
								</div>
							</div>
							<div class="control-group content_even">
							<div class="div_center">
								<label for="email" class="control-label">企业邮箱</label>
								<div class="controls">
									<input id="email" name="email" type="text"
										value=""/>
								</div>
							</div></div>
							<div class="control-group content_odd">
							<div class="div_center">
								<label for="address" class="control-label">企业地址</label>
								<div class="controls">
									<input id="address" name="address" type="text"
										value=""/>
								</div>
							</div></div>
							<div class="control-group content_even">
							<div class="div_center">
								<label for="postcode" class="control-label">邮编</label>
								<div class="controls">
									<input id="postcode" name="postcode" type="text"
										value="" />
								</div>
							</div></div>
							<div class="control-group content_odd">
							<div class="div_center">
								<label for="businessLicenseNum" class="control-label">营业执照注册号</label>
								<div class="controls">
									<input id="businessLicenseNum" name="businessLicenseNum" type="text"
										value="" />
								</div>
							</div></div>
							<div class="control-group content_even">
							<div class="div_center">
								<label class="control-label">营业执照所在地</label>
								<div class="controls">
									<select id="businessLicenseAddress" name="businessLicenseAddress">
										<option value="">省份</option>
										<option value="北京市">北京市</option>
										<option value="天津市">天津市</option>
										<option value="上海市">上海市</option>
										<option value="重庆市">重庆市</option>
										<option value="安徽省">安徽省</option>
										<option value="福建省">福建省</option>
										<option value="浙江省">浙江省</option>
										<option value="江西省">江西省</option>
										<option value="山东省">山东省</option>
										<option value="河南省">河南省</option>
										<option value="湖北省">湖北省</option>
										<option value="湖南省">湖南省</option>
										<option value="广东省">广东省</option>
										<option value="海南省">海南省</option>
										<option value="山西省">山西省</option>
										<option value="青海省">青海省</option>
										<option value="江苏省">江苏省</option>
										<option value="辽宁省">辽宁省</option>
										<option value="吉林省">吉林省</option>
										<option value="湖南省">湖南省</option>
										<option value="河北省">河北省</option>
										<option value="贵州省">贵州省</option>
										<option value="四川省">四川省</option>
										<option value="云南省">云南省</option>
										<option value="陕西省">陕西省</option>
										<option value="甘肃省">甘肃省</option>
										<option value="黑龙江省">黑龙江省</option>
										<option value="广西壮族自治区">广西壮族自治区</option>
										<option value="宁夏回族自治区">宁夏回族自治区</option>
										<option value="新疆维吾尔自治区">新疆维吾尔自治区</option>
										<option value="内蒙古自治区">内蒙古自治区</option>
										<option value="西藏自治区">西藏自治区</option>
										<option value="台湾省">台湾省</option>
										<option value="香港特别行政区">香港特别行政区</option>
										<option value="澳门特别行政区">澳门特别行政区</option>													
									</select>
								</div>
							</div></div>
							<div class="control-group content_odd">
							<div class="div_center">
								<label for="businessDeadline" class="control-label">营业期限</label>
								<div class="controls">
									<input id="businessDeadline" name="businessDeadline"
									type="text" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"
									class="Wdate full-width" />&nbsp;&nbsp;&nbsp; 
									<input type="checkbox" id="businessDeadlineCheck" value="forever" name="businessDeadlineCheck" style="width:20px;margin-top: -1px;"/>长期
								</div>
							</div></div>
							<div class="control-group content_even">
							<div class="div_center">
								<label for="businessScope" class="control-label">经营范围</label>
								<div class="controls">
									<input id="businessScope" name="businessScope" type="text"
										value=""/>
								</div>
							</div></div>
							<div class="control-group content_odd">
							<div class="div_center">
								<label for="businessLicensePic" class="control-label">营业执照副本扫描</label>
								<div class="controls">
									<input id="businessLicensePic" name="businessLicensePic" type="text"
										value=""  />
								</div>
							</div></div>
							<div class="control-group content_even">
							<div class="div_center">
								<label for="registerFigure" class="control-label">注册资本</label>
								<div class="controls">
									<input id="registerFigure" name="registerFigure" type="text"
										value=""/>&nbsp;&nbsp;&nbsp;&nbsp;租户：万
								</div>
							</div></div>
							<div class="control-group content_odd">
							<div class="div_center">
								<label for="institutionalCode" class="control-label">组织机构代码</label>
								<div class="controls">
									<input id="institutionalCode" name="institutionalCode" type="text"
										value="" />
								</div>
							</div></div>
						</div>
						
						
						<div class="register_detail_content register_government">
							<div class="content_title">租户信息</div>
							<div class="control-group content_odd">
							<div class="div_center">
								<label for="governmentRegisterForm" class="control-label">政府信息登记表</label>
								<div class="controls">
									<div style="float: left;">
										请下载<a id="government_download" href="${ctx }/security/tenant/download?uuid=xxdjb_zheng_fu-175c39">政府信息登记表</a>按要求填写表格后，上传加盖公章扫描件
										</br><input type="file" id="governmentRegisterForm" name="governmentRegisterForm"/>
										<input type="hidden" name="governmentRegisterFormUuid" id="governmentRegisterFormUuid"/>
									</div>
								</div>
							</div></div>
						</div>
						<div class="content_title">运营者信息</div>
						<div class="control-group content_even">
						<div class="div_center">
							<label for="businessIdCardName" class="control-label">运营者身份证姓名</label>
							<div class="controls">
								<input id="businessIdCardName" name="businessIdCardName" type="text"
									value=""/>&nbsp;&nbsp;&nbsp;&nbsp;如果名字包含".",请勿省略
							</div>
						</div></div>
						<div class="control-group content_odd">
						<div class="div_center">
							<label for="businessIdCardNum" class="control-label">运营者身份证号码</label>
							<div class="controls">
								<input id="businessIdCardNum" name="businessIdCardNum" type="text"
									value=""/>
							</div>
						</div></div>
						<div class="control-group content_even">
						<div class="div_center">
							<label for="businessIdCardPic" class="control-label">运营者手持身份证件照片</label>
							<div class="controls">
							<div style="float: left;"><img src="${ctx}/resources/pt/images/idCardPic.jpg" width="100px"/></br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;参考示例</div>
							<div style="float: left; padding-left: 20px;">
							身份证上的所有信息清晰可见，必须能看清证件号。
							</br>照片需免冠，建议未化妆，手持证件人的五官清晰可见。
							</br>照片内容真实有效，不得做任何修改。
							</br><input type="file" id="businessIdCardPic" name="businessIdCardPic"/>
							<input type="hidden" name="businessIdCardPicUuid" id="businessIdCardPicUuid"/>
							</div>
								
								
							</div>
						</div></div>
						<div class="control-group content_odd">
						<div class="div_center">
							<label for="mobileNum" class="control-label">手机号码</label>
							<div class="controls">
								<input id="mobileNum" name="mobileNum" type="text"
									value="" />
							</div>
						</div></div>
						<div class="register_business">
							<div class="control-group content_even">
								<div class="div_center">
									<label for="position" class="control-label">职务</label>
									<div class="controls">
										<input id="position" name="position" type="text"
											value="" />
									</div>
							</div></div>
						</div>
						<div class="control-group content_even">
						<div class="div_center">
							<label for="authorizedOperatorsFile" class="control-label">授权运营书</label>
							<div class="controls">
								<div style="float: left;">
									请下载<a id="warrant_download" href="${ctx }/security/tenant/download?uuid=xxdjb_shou_quan_yun_ying-175c39">授权运营书</a>按要求填写表格后，上传加盖公章扫描件
									</br><input type="file" id="authorizedOperatorsFile" name="authorizedOperatorsFile"/>
									<input type="hidden" id="authorizedOperatorsFileUuid" name="authorizedOperatorsFileUuid"/>
								</div>
							</div>
						</div></div>
<!-- 						<div class="control-group"> -->
<!-- 						<div class="div_center"> -->
<!-- 							<div class="div_button"> -->
<!-- 								<button type="submit" class="btn btn-primary">提交</button> -->
<%-- 								<button class="btn btn-primary" onclick="location='${ctx}/index'">返回</button> --%>
<!-- 							</div> -->
<!-- 						</div></div> -->
						<div class="control-group">
							<!-- 显示登录失败原因 -->
							<c:if test="${not empty param.error}">
								<label class="control-label">登录失败：</label>
								<c:out value="${SPRING_SECURITY_LAST_EXCEPTION.message}" />
							</c:if>
						</div>
						<div class="control-group">
							<div class="controls">
								<input type="hidden" id="tenant" name="tenant" value="${tenant}"
									placeholder="tentent" />
							</div>
						</div>
				</form>
			</div>
		</div>
	</div>
	<!-- /container -->
	<script src="${ctx}/resources/jquery/jquery.js"></script>
	<script src="${ctx}/resources/validate/js/jquery.validate.js"></script>
	<script src="${ctx}/resources/bootstrap/js/bootstrap.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/My97DatePicker/WdatePicker.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/jqueryui/js/jquery-ui.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/jqgrid/js/i18n/grid.locale-cn.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/jqgrid/js/jquery.jqGrid.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/pt/js/global.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/layout/jquery.layout.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/form/jquery.form.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/pt/js/org/unit/jquery.unit.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/utils/ajaxfileupload.src.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/validform/js/Validform_v5.2.1.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/pt/js/basicdata/tenantregister/tenantregister.js"></script>
	<script type="text/javascript">
		$(".depatermentType_item").click(function(){
			var type = $(this).attr("val");
			$("#depatermentType").val(type);
			$(".depatermentType_item").attr("class","depatermentType_item");
			$(this).attr("class","depatermentType_item activity");
			$(".register_business").hide();
			$(".register_government").hide();
			$(".register_"+type).show();
		});
		$("#register_form").validate({
			rules : {
				companyName : {
					required : true
				},
				account : {
					required : true,
					remote : {
						url : "${ctx}/common/validate/check/exists",
						type : "POST",
						data : {
							checkType : "tenant",
							fieldName : "account",
							fieldValue : function() {
								return $('#account').val();
							}
						}
					}
				},
				email : {
					required : true,
					email : true
				},
				password : {
					required : true,
					minlength : 5
				},
				confirm_password : {
					required : true,
					minlength : 5,
					equalTo : "#password"
				}
			},
			messages : {
				companyName : {
					required : "公司名称不能为空!"
				},
				account : {
					remote : "该账号已存在!"
				},
				email : {
					required : "邮件地址不能为空!",
					email : "无效的邮件地址!"
				},
				password : {
					required : "密码不能为空!",
					minlength : jQuery.format("密码不能小于{0}个字符!")
				},
				confirm_password : {
					required : "确认密码不能为空!",
					minlength : "确认密码不能小于{0}个字符!",
					equalTo : "两次输入密码不一致!"
				}
			}
		});
	</script>
</body>
</html>