<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/pt/common/taglibs.jsp" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
<title></title>
<link href="${ctx}/resources/bootstrap/css/bootstrap.css" rel="stylesheet">
<script src="${ctx}/resources/jquery/jquery.js"></script>
</head>
<body>
	<link href="${ctx}/resources/theme/css/urlPage.css" rel="stylesheet">
<div class="header">
<%-- 旧版本的行政审批头部<h1 style="width: 230px;background:url('${ctx}/resources/pt/img/zzffzx-logo4.png') no-repeat 0 1px;height: 60px;" onclick="location.href='${ctx}/cms/cmspage/readPage?uuid=3aead82f-9e34-4540-8a08-0253da4e032e'">&nbsp;</h1> --%>
<%--<h1 style="width: 255px;background:url('${ctx}/resources/pt/img/zzffzx-logo7.png') no-repeat 0 1px;height: 60px;cursor: auto;" >&nbsp;</h1> --%>
<%--
<h1 style="width: 230px;background:url('${ctx}/resources/pt/img/zzffzx-logo4.png') no-repeat 0 1px;height: 60px;cursor: auto;" >&nbsp;</h1>
<h2  style="width: 267px; border-bottom: 1px solid #FFFFFF;
    border-left: medium none;
    margin-left: -185px;font-size: 18px;margin-top: 17px;">
    <div style="text-align: left;letter-spacing:15px;">厦门市审批服务云</div>
     <div style="text-align: left;font-size: 14px;">厦门市行政审批（商事登记）信息管理平台</div>
    </h2>
--%>
<h1><a href="#">威尔软件</a></h1>
    <h2>统一业务应用与开发云平台</h2>

<ul class="user-info">
	<li class="slide"><a class="handle member-info-show" href="#">
	<img class="member_icon" src="${ctx}/resources/theme/images/v1_icon_user.png" />
	<span class="member-info-show" id="username">${user.userName}</span>
	<i class="member-info-show"> </i></a>
	<div class="slide-con"><i class="con-icon">&nbsp; </i>
	<div class="member-info member-info-show">
	<div class="clearfix info  member-info-show">
	<span class="photo  member-info-show">
	<div style="height: 120px;width: 120px;"><img alt="" class="member-info-show user_img" src="${ctx}${user.photoUrl}" /></div>
<%-- 	${ctx}/${user.photoUrl} --%>
	</span> 
	<input class="member-info-show" id="user_uuid" type="hidden" value="${user.uuid}"/>
	<input class="member-info-show" id="user_contact_is_null" type="hidden" value="${contactIsNull}"/>
	<input class="member-info-show" id="user_mobilePhone" type="hidden" value="${user.mobilePhone}"/>
	<input class="member-info-show" id="user_officePhone" type="hidden" value="${user.officePhone}"/>
	<input class="member-info-show" id="user_isShowContact" type="hidden" value="${isShowContact}"/>
	<div class="summary member-info-show">
		<p class="member-info-show">
			<span class="label member-info-show">姓&nbsp;&nbsp;&nbsp;&nbsp;名：</span>
			<span class="v1_head_username member-info-show">${user.userName}</span>
		</p>
		<p class="member-info-show">
			<span class="label member-info-show">部&nbsp;&nbsp;&nbsp;&nbsp;门：</span>
			<span class="v1_head_dpname member-info-show">${user.departmentName}</span>
		</p>
		<p class="member-info-show">
			<span class="label member-info-show">岗&nbsp;&nbsp;&nbsp;&nbsp;位：</span>
			<span class="v1_head_jobname member-info-show">${user.jobName}</span>
		</p>
		<p class="member-info-show">
			<span class="label member-info-show">上次登录：</span>
			<span class="v1_head_bftime member-info-show"><fmt:formatDate value="${user.lastLoginTime}" pattern="yyyy-MM-dd HH:mm"/></span>
		</p>
	</div>
	</div>

	<div class="operate member-info-show">
	<a class="password_modif member-info-show" href="#" style="display:none;">修改密码</a>
	<a class="member-info-show" id="op_userSet" href="#">用户设置</a>
	<c:if test="${rtxOpen == true}">
	<a class="member-info-show" href="${ctx}/basicdata/rtx/singlesignon" id="rtx" target="rtx">RTX</a>
	</c:if>
	
	<c:if test="${user.isAdmin == true}">
	<a class="member-info-show" href="${ctx}/passport/admin/main" id="admin_manage" target="_blank">管理后台</a>
	</c:if>
	<a class="member-info-show" href="${ctx}/security_logout">注销</a></div>
	</div>
	</div>
	</li>
	<li><a href="${ctx}/cms/cmspage/readPage?uuid=72fb8304-952e-4664-8aa3-5068da63d803&treeName=QZSF_WDXX&201441213014582=01c72d84-753a-43de-8475-b87527c8a693"><img class="member_message" src="${ctx}/resources/theme/images/v1_icon_message.png" />我的消息<sup class="num"  style="right: 17px;">0</sup></a></li>
</ul>

<ul class="top-nav">
	<c:forEach var="cmsCategory" items="${cmsCategorys}" varStatus="status">
		<li><a href="${ctx}${cmsCategory.cc.inputUrl}">${cmsCategory.cc.title}</a></li>
	</c:forEach>
</ul>

</div>
<iframe frameborder="0" height="0" name="rtx" src="about:blank" width="0"></iframe>
<div class="user-profile-settings" style="display: none;">
	<div >
		<div class="dialog_form_content">
			<table width="100%">
				<tbody>
				  <tr class="odd">
				    <td class="Label">照片</td>
				    <td class="value">
				    <form method="POST" id="upload_share_form" enctype="multipart/form-data" >
				    <div class="td_class" id="div_photo">
						<img id="user_photo"
								style="width: 80px; height: 100px"></img>
						 <span> <input class="input_photo" type="file" id="upload" name="upload" />
						 <input onclick="btnUpload()" class="input_photo" type="button" id="btn_upload" value="上传" class="btn" style="width: 50px;" />
						 <input class="input_photo" id="photoUuid" name="photoUuid" type="hidden">
						</span>
					</div>
					</form>
				   </td>
				  </tr>
				  <c:if test="${isShowContact == 'true'}">
					  <tr>
					    <td class="Label">联系人*</td>
					    <td class="value">
					    <div class="td_class" id="div_contactName">
					   		 <input class="v1_input_newpassword" id="contactName" name="contactName" type="text" />
				   		</div>
					    </td>
					  </tr>
				  </c:if>
				  <tr class="odd">
				    <td class="Label">性别</td>
				    <td class="value">
				    <div class="td_class" id="div_sex" style="float: left">
						<input id="sex_man" name="sex" type="radio" value="1" />
						男
						<input id="sex_girl" name="sex" type="radio" value="2" />
						女
					</div>
				   </td>
				  </tr>
				   <tr>
				    <td class="Label">手机*</td>
				    <td class="value">
				    <div class="td_class" id="div_mobilePhone">
						<input class="v1_input_password" id="mobilePhone" name="mobilePhone" type="text" />
					<div class="div_input_phone"><input id="receiveSmsMessage"
						name="receiveSmsMessage" type="checkbox" style="float: left;" checked="checked">
						<label for="receiveSmsMessage">接收短信消息</label>
					</div>
					</div>
				   </td>
				  </tr>
				  <tr class="odd">
				    <td class="Label">邮件</td>
				    <td class="value">
				    <div class="td_class" id="div_email">
				   		 <input class="v1_input_newpassword" id="email" name="email" type="text" />
			   		</div>
				    </td>
				  </tr>
				  <tr>
				    <td class="Label">办公电话*</td>
				    <td class="value">
				    <div class="td_class" id="div_officePhone">
				   		 <input class="v1_input_newpassword" id="officePhone" name="officePhone" type="text" />
			   		</div>
				    </td>
				  </tr>
				  <tr class="odd">
				  </tr>
				    <tr class="odd">
				    <td class="Label">传真</td>
				    <td class="value">
				    <div class="td_class" id="div_fax">
				    <input class="v1_input_doublepassword" id="fax" name="fax" type="text" />
				    </div>
				    </td>
				  </tr>
				  <tr class="odd">
				    <td class="Label">身份证号</td>
				    <td class="value">
				    <div class="td_class" id="div_idNumber">
				    <input class="v1_input_doublepassword" id="idNumber" name="idNumber" type="text" />
				    </div>
				    </td>
				  </tr>
				  <tr class="odd" style="display: none;">
				    <td class="Label">我的去向</td>
				    <td class="value">
				    <div class="td_class" id="div_position_state">
				    </div>
				    </td>
				  </tr>
				</tbody>
			</table>
		</div>
	  </div>	
</div>
<script type="text/javascript" src="${ctx}/resources/pt/js/cms/index_top.js"></script>
</body>
</html>