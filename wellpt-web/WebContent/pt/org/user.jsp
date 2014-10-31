<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<%@ include file="/pt/common/taglibs.jsp"%>
<%@ include file="/pt/common/meta.jsp"%>
<title>User List</title>
<link rel="stylesheet" type="text/css"
	href="${ctx}/resources/jqueryui/css/base/jquery-ui.css" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/resources/jqgrid/css/ui.jqgrid.css" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/resources/layout/jquery.layout.css" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/resources/ztree/css/zTreeStyle/zTreeStyle.css" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/resources/pt/css/custom-admin-style.css" />
<body>
	<div class="ui-layout-west">
		<div>
			<div class="btn-group btn-group-top">
				<!-- 				<label>登录名</label><input id="query_loginName" name="query_loginName" /> -->
				<div class="query-fields">
					<input id="tenantId" name="tenantId" value="${tenantId }"
						type="hidden" /> <input id="query_user" name="query_user"
						size="12" /> <input id="currentUserId" name="currentUserId"
						value="${currentUserId }" type="hidden" /> <input
						id="isDepartmentAdmin" name="isDepartmentAdmin"
						value="${isDepartmentAdmin }" type="hidden" /> <input
						id="departmentUuid" name="departmentUuid"
						value="${departmentUuid }" type="hidden" /> <input
						id="roleUserUuid" name="roleUserUuid" value="" type="hidden" />
					<button id="btn_query" type="button" class="btn">查询</button>
					<input id="deptUuid" name="deptUuid" type="hidden">
				</div>
				<privilege:button authority="B001001001" id="btn_add" class="btn">新 增</privilege:button>
				<privilege:button authority="B001001003" id="btn_del_all"
					class="btn">删 除</privilege:button>
				<privilege:button authority="B001001004" id="btn_imp" class="btn">导 入</privilege:button>
				<privilege:button authority="B001001005" id="btn_imp_commonuser"
					class="btn">从集团中导入</privilege:button>
			</div>
			<table id="list"></table>
			<div id="pager"></div>
		</div>
	</div>
	<div class="ui-layout-center">
		<div>
			<form action="" id="user_form" name="user_form">
				<div class="tabs">
					<ul class="ul-head">
						<li><a href="#tabs-1">基本信息</a></li>
						<li><a href="#tabs-2">工作信息</a></li>
						<li><a href="#tabs-3">角色信息</a></li>
						<li><a href="#tabs-4">角色树</a></li>
						<!-- <li><a href="#tabs-5">权限信息</a></li> -->
						<!-- <li><a href="#tabs-6">权限树</a></li> -->
						<li id="li-imp" style="display: none"><a href="#tabs-5">导入用户</a></li>
					</ul>
					<input type="hidden" id="uuid" name="uuid" /> <input type="hidden"
						id="id" name="id" />
						<input type="hidden"
						id="externalId" name="externalId" />
					<div id="tabs-1">
						<table>
							<tr>
								<td style="width: 65px;"><label for="loginName">登录名</label></td>
								<td><input id="loginName" name="loginName" type="text"
									class="full-width" /></td>
								<td></td>
							</tr>
							<tr>
								<td><label for="password">密码</label></td>
								<td><input id="password" name="password" type="password"
									class="full-width"></td>
								<td></td>
							</tr>
							<tr>
								<td><label for="userName">姓名</label></td>
								<td><input id="userName" name="userName" type="text"
									class="full-width" /></td>
								<td></td>
							</tr>
							<tr>
								<td><label for="englishName">英文名</label></td>
								<td><input id="englishName" name="englishName" type="text"
									class="full-width" /></td>
								<td></td>
							</tr>
							<tr>
								<td><label for="code">编号</label></td>
								<td><input id="code" name="code" type="text"
									class="full-width" /></td>
								<td></td>
							</tr>
							<tr>
								<td><label>性别</label></td>
								<td><input id="sex_man" name="sex" type="radio" value="1" /><label
									for="sex_man">男</label> <input id="sex_girl" name="sex"
									type="radio" value="2" /><label for="sex_girl">女</label></td>
								<td></td>
							</tr>
							<tr>
								<td class="align-top"><label for="subjectDN">证书主体</label></td>
								<td><input id="subjectDN" name="subjectDN" type="text"
									class="full-width"
									<privilege:access ifNotGranted="B001001007">readonly="readonly"</privilege:access>></input></td>
								<td></td>
							</tr>
							<tr>
								<td class="align-top"><label for="remark">备注</label></td>
								<td><textarea id="remark" name="remark" class="full-width"></textarea></td>
								<td></td>
							</tr>
							<tr>
								<td></td>
								<td><span><input id="isAllowedBack"
										name="isAllowedBack" type="checkbox" /><label
										for="isAllowedBack">允许此用户登录后台</label></span> <span><input
										id="enabled" name="enabled" type="checkbox" value="false" /><label
										for="enabled">启用</label></span></td>
								<td></td>
							</tr>
							<tr>
								<td></td>
								<td><span><input id="notAllowedTenantId"
										name="notAllowedTenantId" type="checkbox" checked="checked">
										<label for="notAllowedTenantId">不允许此用户登录此系统</label></span></td>
								<td></td>
							</tr>
							<privilege:access ifGranted="B001001006">
								<tr>
									<td></td>
									<td><span><input id="onlyLogonWidthCertificate"
											name="onlyLogonWidthCertificate" type="checkbox"
											checked="checked"><label
											for="onlyLogonWidthCertificate">只能以证书登录</label></span></td>
									<td></td>
								</tr>
							</privilege:access>
						</table>
					</div>
					<div id="tabs-2">
						<table>
							<tr>
								<td><label for="employeeNumber">照片</label></td>
								<td><img id="user_photo" src="#"
									style="width: 80px; height: 100px"></img> <span> <input
										type="file" id="upload" name="upload" /><input type="button"
										id="btn_upload" value="上传" class="btn" style="width: 50px;" />
								</span></td>
								<td><input id="photoUuid" name="photoUuid" type="hidden">
								</td>
							</tr>
							<tr>
								<td><label for="employeeNumber">员工编号</label></td>
								<td><input type="text" id="employeeNumber"
									name="employeeNumber" class="full-width" /></td>
								<td></td>
							</tr>
					
				<!-- 			<tr>
								<td><label for="jobName">岗位</label></td>
								<td><input type="text" id="jobName" name="jobName"
									class="full-width" /></td>
								<td><input type="hidden" id="jobCode" name="jobCode" />
								<input type="hidden" id="jobId" name="jobId" /></td>
							</tr> -->
								<tr>
								<td><label for="majorJobName">职位</label></td>
								<td><input type="text" id="majorJobName" name="majorJobName"
									class="full-width" /></td>
								<td><input type="hidden" id="majorJobId" name="majorJobId" /></td>
							</tr>
							<tr>
								<td><label for="otherJobNames">其他职位</label></td>
								<td><input type="text" id="otherJobNames" name="otherJobNames"
									class="full-width" /></td>
								<td><input type="hidden" id="otherJobIds" name="otherJobIds" /></td>
							</tr>
							<tr>
								<td style="width: 65px;"><label for="departmentName">所属部门</label></td>
								<td><input type="text" id="departmentName"
									name="departmentName" class="full-width" readonly="true"/></td>
								<td><input type="hidden" id="departmentId"
									name="departmentId" /></td>
							</tr>
							<tr>
								<td><label for="leaderNames">上级领导</label></td>
								<td><input type="text" id="leaderNames" name="leaderNames"
									class="full-width" /></td>
								<td><input type="hidden" id="leaderIds" name="leaderIds" /></td>
							</tr>
							<tr>
								<td><label for="deputyNames">职务代理人</label></td>
								<td><input type="text" id="deputyNames" name="deputyNames"
									class="full-width" /></td>
								<td><input type="hidden" id="deputyIds" name="deputyIds" /></td>
							</tr>
							<tr>
								<td style="width: 65px;"><label for="groupNames">所属群组</label></td>
								<td><input type="text" id="groupNames" name="groupNames"
									class="full-width" /></td>
								<td><input type="hidden" id="groupIds" name="groupIds" /></td>
							</tr>
							<tr>
								<td><label for="mobilePhone">手机(主)</label></td>
								<td><input type="text" id="mobilePhone" name="mobilePhone"
									class="full-width" /></td>
								<td></td>
							</tr>
							<tr>
								<td><label for="otherMobilePhone">手机(其他)</label></td>
								<td><input type="text" id="otherMobilePhone" name="otherMobilePhone"
									class="full-width" /></td>
								<td></td>
							</tr>
							<tr>
								<td></td>
								<td><span><input id="receiveSmsMessage"
										name="receiveSmsMessage" type="checkbox" checked="checked">
										<label for="receiveSmsMessage">接收短信消息</label></span></td>
								<td></td>
							</tr>
							<tr>
								<td><label for="officePhone">办公电话/分机</label></td>
								<td><input type="text" id="officePhone" name="officePhone"
									class="full-width" /></td>
								<td></td>
							</tr>
							<tr>
								<td><label for="fax">传真</label></td>
								<td><input type="text" id="fax" name="fax"
									class="full-width" /></td>
								<td></td>
							</tr>
			<!-- 				<tr>
								<td><label for="idNumber">身份证号</label></td>
								<td><input type="text" id="idNumber" name="idNumber"
									class="full-width" /></td>
								<td></td>
							</tr> -->
							<tr>
								<td><label for="homePhone">家庭电话</label></td>
								<td><input type="text" id="homePhone" name="homePhone"
									class="full-width" /></td>
								<td></td>
							</tr>
				<!-- 			<tr>
								<td><label for="email">电子邮件()</label></td>
								<td><input type="text" id="email" name="email"
									class="full-width" /></td>
								<td></td>
							</tr> -->
							<tr>
								<td><label for="mainEmail">电子邮件(主)</label></td>
								<td><input type="text" id="mainEmail" name="mainEmail"
									class="full-width" /></td>
								<td></td>
							</tr>
								<tr>
								<td><label for="otherEmail">电子邮件(其他)</label></td>
								<td><input type="text" id="otherEmail" name="otherEmail"
									class="full-width" /></td>
								<td></td>
							</tr>
								<!-- 	<tr>
								<td><label for="personnelArea">人事范围</label></td>
								<td><input type="text" id="personnelArea" name="personnelArea"
									class="full-width" /></td>
								<td></td>
								</tr> -->
								<tr>
								<td><label>公司主体</label></td>
								<td>
									<select id="principalCompany" name="principalCompany" type="text" class="full-width">
								    <option value =""></option>
									<option value ="1000">绿色照明</option>
									<option value ="2000">厦门光电</option>
									<option value ="3000">立达信照明</option>
									<option value ="4000">电光源</option>
									<option value ="4600">绿天光电</option>
									<option value ="5000">海德信</option>
									<option value ="6000">四川鼎吉</option>
									<option value ="6500">四川联恺</option>
									<option value ="7000">赢科</option>
									<option value ="G001">厦门德之信</option>
									<option value ="G002">立达信科技</option>
									</select>
									</td>
								<td></td>
								
							</tr>
						</table>
					</div>
					<div id="tabs-3">
						<div style="float: left">
							<ul id="role_tree" class="ztree">
							</ul>
						</div>
						<div style="width: 200px; margin-left: 50px; float: left">
							<div>
								<label>已选角色</label>
							</div>
							<select id="selected_role" multiple="multiple"
								style="height: 300px; width: 200px;">
							</select>
						</div>
						<div style="clear: both;"></div>
					</div>
					<div id="tabs-4">
						<div>
							<ul id="user_role_nested_role_tree" class="ztree">
							</ul>
						</div>
					</div>
					
					<div id="tabs-5" style="display: none">
						<div style="float: left">
							<ul id="privilege_tree" class="ztree">
							</ul>
						</div>
						<div style="width: 200px; margin-left: 100px; float: left">
							<div>
								<label>已选权限</label>
							</div>
							<select id="selected_privilege" multiple="multiple"
								style="height: 200px; width: 200px;">
							</select>
						</div>
						<div style="clear: both;"></div>
					</div>
					
					<div id="tabs-6" style="display: none">
						<div>
							<ul id="user_privilege_tree" class="ztree">
							</ul>
						</div>
					</div>
					
					
				</div>
				<div class="btn-group btn-group-bottom">
					<privilege:button authority="B001001002" id="btn_save" class="btn">保存</privilege:button>
					<privilege:button authority="B001001003" id="btn_del" class="btn">删除</privilege:button>
					<!--<privilege:dynamicButton authority="001" cssClass="btn" />-->
				</div>
			</form>
		</div>
	</div>

	<div id="importUser" title="导入用户" style="display: none">
		<form action="" id="import_form" name="import_form"
			enctype='multipart/form-data' method="post">
			<table>
				<tr>
					<td><label>选择XLS文件：</label></td>
					<td>
						<div>
							<input type="file" name="upload" id="uploadfile" /> <input
								type="hidden" name="ruleId" id="ruleId" value="org_department_001;org_duty_001;org_job_001;org_user_001;org_user_job_001;org_employee_001;org_employee_job_001;" />
						</div>
					</td>
				</tr>
			</table>
		</form>
	</div>

	<div id="importCommonUser" title="从集团中导入用户" style="display: none">
		<form action="" id="import_common_user_form"
			name="import_common_user_form" enctype='multipart/form-data'
			method="post">
			<table>
				<tr>
					<td><label for="commonUser">选择用户</label></td>
					<td><input id="commonUserNames" name="commonUserNames"
						type="text" class="full-width" /> <input id="commonUserIds"
						name="commonUserIds" type="hidden" class="full-width" /></td>
					<td></td>
				</tr>
				<tr>
					<td></td>
					<td><span><input id="enableTenantId"
							name="enableTenantId" type="checkbox" checked="checked"><label
							for="enableTenantId">不允许此用户登录此系统</label></span></td>
					<td></td>
				</tr>
			</table>
		</form>
	</div>

	<!-- Project -->
	<script type="text/javascript" src="${ctx}/resources/jquery/jquery.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/jqgrid/js/i18n/grid.locale-cn.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/jqgrid/js/jquery.jqGrid.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/jqueryui/js/jquery-ui.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/layout/jquery.layout.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/form/jquery.form.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/ztree/js/jquery.ztree.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/utils/ajaxfileupload.js"></script>
	<script type="text/javascript" src="${ctx}/resources/pt/js/global.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/pt/js/org/unit/jquery.unit.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/pt/js/system_admin.js"></script>
	<script type="text/javascript" src="${ctx}/resources/pt/js/org/user.js"></script>

</body>
</html>