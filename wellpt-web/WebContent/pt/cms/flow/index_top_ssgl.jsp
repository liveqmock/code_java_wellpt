<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/pt/common/taglibs.jsp" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
<title>立达信云平台</title>
<link href="${ctx}/resources/bootstrap/css/bootstrap.css" rel="stylesheet">
<script src="${ctx}/resources/jquery/jquery.js"></script>
</head>
<body>
	<link href="${ctx}/resources/theme/css/urlPage.css" rel="stylesheet">
<div class="header">
<a href="${ctx}/cms/cmspage/readPage?uuid=6012e415-4f39-444c-8e38-aa1e32444267"><h1 style="cursor: pointer;width: 311px;background:url('${ctx}/resources/pt/images/login/ldx_logo.png');height: 54px;background-repeat:no-repeat;" ></h1></a>
<!-- <h1><a href="#">威尔软件</a></h1> -->
<!--     <h2>统一业务应用与开发云平台</h2> -->
<c:if test="${user.departmentId == 'D0000000194'}">
<ul class="user-info">
<!-- 	<li> -->
<%-- 		<a href="${ctx}/cms/cmspage/readPage?uuid=cdf182d9-6778-4606-afd1-fce2d6ef4a66"><img class="member_message" src="${ctx}/resources/theme/images/v1_icon_return.png" />返回首页</a> --%>
<!-- 	</li> -->
	<li class="slide">
	<a href="#" id="op_username">
	<img class="member_icon" src="${ctx}/resources/theme/images/v1_icon_user.png" />
	<span class="member-info-show" id="username">${user.userName}</span>
	</a>
<!-- 	<i class="member-info-show"> </i> -->
	<div class="slide-con"><i class="con-icon">&nbsp; </i>
	<div class="member-info member-info-show">


	<div class="operate member-info-show">
	<a class="member-info-show" href="${ctx}/j_spring_security_logout">注销</a>
	</div>
	</div>
	</div>
	</li>
	<li><a class="message-show"  href="${ctx}/cms/cmspage/readPage?uuid=7f2f78e0-024b-4c18-be87-915c337aa445&treeName=XZSP_WDXX&2014228163647814=01c72d84-753a-43de-8475-b87527c8a693"><img class="member_message" src="${ctx}/resources/theme/images/v1_icon_message.png" />我的消息<sup class="num">0</sup></a></li>
	<li style="width:50px;"><a href="${ctx}/j_spring_security_logout"><img class="member_message" src="${ctx}/resources/theme/images/v1_icon_user4.png" />&nbsp;&nbsp;&nbsp;注销</a></li>
</ul>				
</c:if>
<input class="member-info-show" id="user_uuid" type="hidden" value="${user.uuid}"/>
	<input class="member-info-show" id="user_contact_is_null" type="hidden" value="${contactIsNull}"/>
	<input class="member-info-show" id="user_mobilePhone" type="hidden" value="${user.mobilePhone}"/>
	<input class="member-info-show" id="user_officePhone" type="hidden" value="${user.officePhone}"/>
	<input class="member-info-show" id="user_isShowContact" type="hidden" value="${isShowContact}"/>
<c:if test="${user.departmentId !='D0000000194'}">
<ul class="user-info">
<!-- 	<li> -->
<%-- 		<a href="${ctx}/cms/cmspage/readPage?uuid=cdf182d9-6778-4606-afd1-fce2d6ef4a66"><img class="member_message" src="${ctx}/resources/theme/images/v1_icon_return.png" />返回首页</a> --%>
<!-- 	</li> -->
	<li class="message_li"><a class="message-show" href="${ctx}/cms/cmspage/readPage?uuid=c9b26993-131a-4a2b-842c-8e3a11646dea&treeName=ldx_message&View=5abfb3f7-878a-4b5a-ad69-5f29d44926e8">
	<img class="member_message" src="${ctx}/resources/theme/images/v1_icon_message.png" />消息<sup class="num">0</sup></a></li>
	<li class="slide username_li"><a class="handle member-info-show" href="#">
	<img class="member_icon" src="${ctx}/resources/theme/images/v1_icon_user.png" />
	<span class="member-info-show" id="username">
		<c:if test="${user.userName == '系统管理员'}">管理员</c:if>
		<c:if test="${user.userName != '系统管理员'}">${user.userName}</c:if>
		<i class="member-info-show"> </i>
	</span>
	</a>
	<div class="slide-con"><i class="con-icon">&nbsp; </i>
	<div class="member-info member-info-show">
	<div class="clearfix info  member-info-show">
	<span class="photo  member-info-show">
	<div style="height: 120px;width: 120px;">
	<c:choose>  
    <c:when test="${!empty user.photoUrl && user.photoUrl != ''}">
    	<img alt="" class="member-info-show user_img" src="${ctx}${user.photoUrl}" />
    </c:when>  
    <c:otherwise>
    <c:if test="${user.sex eq '1'}"><img alt="" class="member-info-show user_img" src="${ctx}/resources/pt/images/ldx/man.png"/></c:if>
    <c:if test="${user.sex eq '2'}"><img alt="" class="member-info-show user_img" src="${ctx}/resources/pt/images/ldx/woman.png"/></c:if>
    </c:otherwise>  
	</c:choose> 
	</div>
<%-- 	${ctx}/${user.photoUrl} --%>
	</span> 
	<div class="summary member-info-show">
		<p class="member-info-show" style="float:left"><span class="label member-info-show" style="width:55px">姓&nbsp;&nbsp;&nbsp;&nbsp;名</span><span class="label member-info-show">:</span>
			<span class="v1_head_username member-info-show">${user.userName}</span>
		</p>
		<p class="member-info-show" style="float:left"><span class="label member-info-show" style="width:55px">部&nbsp;&nbsp;&nbsp;&nbsp;门</span><span class="label member-info-show">:</span>
			<span class="v1_head_dpname member-info-show">${user.departmentName}</span>
		</p>
		<p class="member-info-show" style="float:left"><span class="label member-info-show" style="width:55px">岗&nbsp;&nbsp;&nbsp;&nbsp;位</span><span class="label member-info-show">:</span>
			<span class="v1_head_jobname member-info-show">${user.jobName}</span>
		</p>
		<p class="member-info-show" style="float:left"><span class="label member-info-show" style="width:55px">上次登录</span><span class="label member-info-show">:</span>
			<span class="v1_head_bftime member-info-show"><fmt:formatDate value="${user.lastLoginTime}" pattern="yyyy-MM-dd HH:mm"/></span>
		</p>
	</div>
	</div>
	
	<div class="operate member-info-show">
	<a class="password_modif member-info-show" href="#" >修改密码</a>
	<a class="member-info-show" id="op_userSet" href="#">用户设置</a>
	<c:if test="${rtxOpen == true}">
	<a class="member-info-show" href="${ctx}/basicdata/rtx/singlesignon" id="rtx" target="rtx">RTX</a>
	</c:if>
	
	<c:if test="${user.isAdmin == true}">
	<a class="member-info-show" href="${ctx}/passport/admin/main" id="admin_manage" target="_blank">管理后台</a>
	</c:if>
	<a class="member-info-show" href="${ctx}/j_spring_security_logout">注销</a></div>
	</div>
	</div>
	</li>
	<c:forEach var="cmsCategory" items="${cmsCategorys}" varStatus="status">
		<c:if test="${cmsCategory.cc.code=='XZSP_JJGXT'}">
		<li><div onclick="${cmsCategory.cc.jsContent}"><img class="member_message" src="${ctx}/resources/theme/images/v1_icon_message.png" />${cmsCategory.cc.title}</div></li>
		</c:if>
	</c:forEach>
</ul>			
</c:if>

<ul class="user-info">
	<c:forEach var="cmsCategory" items="${cmsCategorys}" varStatus="status">
		<c:if test="${cmsCategory.cc.code!='TREE_LDX_TOP_NAV'}"> 
			<%-- <li><div style="width: 80px;"><a href="">${cmsCategory.cc.title}</a></div></li> --%>
			<li class="mail_li"
			<c:if test="${cmsCategory.cc.title eq '发起工作'}">style="display:none;"</c:if> 
			>
			<c:if test="${cmsCategory.cc.title eq '邮件'}">
				<a  
			onclick='window.open("${cmsCategory.cc.inputUrl}")'>
			<img class="member_mail"  src="${ctx}/resources/theme/images/v1_icon_mail.png" />${cmsCategory.cc.title}<!-- <sup class="num">0</sup> --></a>
			</c:if>
			<c:if test="${!cmsCategory.cc.title eq '邮件'}">
				<a  
			href="${ctx}${cmsCategory.cc.inputUrl}">
			<img class="member_mail"   src="${ctx}/resources/theme/images/v1_icon_mail.png" />${cmsCategory.cc.title}<!-- <sup class="num">0</sup> --></a>
			</c:if>
		</c:if>	
		 
		<c:if test="${cmsCategory.cc.code=='TREE_LDX_TOP_NAV'}"> 
		<li class="nav_li"  ><a  onclick="${cmsCategory.cc.jsContent}">
			<img class="member_nav"  src="${ctx}/resources/theme/images/v1_icon_nav.png" />${cmsCategory.cc.title}</a></li>
		
		</c:if>
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
				  <!-- 
					  <tr>
					    <td class="Label">联系人*</td>
					    <td class="value">
					    <div class="td_class" id="div_contactName">
					   		 <input class="v1_input_newpassword" id="contactName" name="contactName" type="text" />
				   		</div>
					    </td>
					  </tr>
				  -->
				  <tr>
					    <td class="Label">Bo用户</td>
					    <td class="value">
					    <div class="td_class" id="div_boUser">
					   		 <input class="v1_input_newpassword" id="boUser" name="boUser" type="text" readonly="true"/>
				   		</div>
					    </td>
					  </tr>
				  <tr>
					    <td class="Label">Bo密码</td>
					    <td class="value">
					    <div class="td_class" id="div_boPwd">
					   		 <input class="v1_input_newpassword" id="boPwd" name="boPwd" type="text" />
				   		</div>
					    </td>
					  </tr>
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
				    <td class="Label">手机(其他)</td>
				    <td class="value">
				    <div class="td_class" id="div_email">
				   		 <input class="v1_input_newpassword" id="otherMobilePhone" name="otherMobilePhone" type="text" />
			   		</div>
				    </td>
				  </tr>
				  <tr class="odd">
				    <td class="Label">邮件(主)</td>
				    <td class="value">
				    <div class="td_class" id="div_email">
				   		 <input class="v1_input_newpassword" id="mainEmail" name="mainEmail" type="text" />
			   		</div>
				    </td>
				  </tr>
				  	  <tr class="odd">
				    <td class="Label">邮件(其他)</td>
				    <td class="value">
				    <div class="td_class" id="div_email">
				   		 <input class="v1_input_newpassword" id="otherEmail" name="otherEmail" type="text" />
			   		</div>
				    </td>
				  </tr>
				  <tr>
				    <td class="Label">办公电话/分机*</td>
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

<div class="contact-detail-show" style="display: none;">
	<div >
		<div class="dialog_form_content">
			<table width="100%">
				<tbody>
					<!-- 
				  <tr class="odd">
				    <td class="Label">照片</td>
				    <td class="value">
				    <form method="POST" id="upload_share_form" enctype="multipart/form-data" >
				    <div id="div_photo">
						<img id="contact_user_photo" style="width: 80px; height: 100px"></img>
					</div>
					</form>
				   </td>
				  </tr>
				   -->
				  <tr class="odd">
					  <td style="width:18%;text-align:left;">&nbsp;&nbsp;&nbsp;&nbsp;联系人</td>
					  <td>
					  <div id="div_userName">
					     <label style="font-size:100%;" id="contact_boUser" name="contact_boUser" type="text" readonly="true"/>
				   	</div>
					   </td>
				</tr>
				  <tr class="odd">
				    <td style="width:18%;text-align:left;">&nbsp;&nbsp;&nbsp;&nbsp;性别</td>
				    <td>
				    <div id="div_sex" style="float: left">
						<label style="font-size:100%;" id="contact_sex" name="contact_sex" type="text" value="1" readonly="true" />
					</div>
				   </td>
				  </tr>
				  <tr class="odd">
				    <td style="width:18%;text-align:left;">&nbsp;&nbsp;&nbsp;&nbsp;部门名称</td>
				    <td>
				    <div id="div_bm" style="float: left">
						<label style="font-size:100%;" id="contact_dept" name="contact_dept" type="text" value="1" readonly="true" />
					</div>
				   </td>
				  </tr>				  
				   <tr class="odd">
				    <td style="width:18%;text-align:left;">&nbsp;&nbsp;&nbsp;&nbsp;手机号</td>
				    <td>
				    <div id="div_mobilePhone">
						<label style="font-size:100%;" id="contact_mobilePhone" name="contact_mobilePhone" type="text" readonly="true" />
					<div class="div_input_phone">
					</div>
					</div>
				   </td>
				  </tr>
				  	<tr class="odd">
				    <td style="width:18%;text-align:left;">&nbsp;&nbsp;&nbsp;&nbsp;手机(其他)</td>
				    <td>
				    <div id="div_phone">
				   		 <label style="font-size:100%;" id="contact_otherMobilePhone" name="contact_otherMobilePhone" type="text" readonly="true" />
			   		</div>
				    </td>
				  </tr>
				  <tr class="odd">
				    <td style="width:18%;text-align:left;">&nbsp;&nbsp;&nbsp;&nbsp;邮件(主)</td>
				    <td>
				    <div id="div_email">
				   		 <label style="font-size:100%;" id="contact_mainEmail" name="contact_mainEmail" type="text" readonly="true"  />
			   		</div>
				    </td>
				  </tr>
				  <tr class="odd">
				    <td style="width:18%;text-align:left;">&nbsp;&nbsp;&nbsp;&nbsp;邮件(其他)</td>
				    <td>
				    <div id="div_otheremail">
				   		 <label style="font-size:100%;" id="contact_otherEmail" name="contact_otherEmail" type="text" readonly="true"  />
			   		</div>
				    </td>
				  </tr>
				  <tr class="odd">
				    <td style="width:18%;text-align:left;">&nbsp;&nbsp;&nbsp;&nbsp;家庭电话</td>
				    <td>
				    <div id="div_homePhone">
				   		 <label style="font-size:100%;" id="contact_homePhone" name="contact_homePhone" type="text" readonly="true"  />
			   		</div>
				    </td>
				  </tr>				  
				  <tr class="odd">
				    <td style="width:18%;text-align:left;">&nbsp;&nbsp;&nbsp;&nbsp;办公电话/分机</td>
				    <td>
				    <div id="div_officePhone">
				   		 <label style="font-size:100%;" id="contact_officePhone" name="contact_officePhone" type="text" readonly="true"  />
			   		</div>
				    </td>
				  </tr>
				  <tr class="odd">
				    <td style="width:18%;text-align:left;">&nbsp;&nbsp;&nbsp;&nbsp;传真</td>
				    <td>
				    <div id="div_fax">
				    <label style="font-size:100%;" class="v1_input_doublepassword" id="contact_fax" name="contact_fax" type="text" readonly="true"  />
				    </div>
				    </td>
				  </tr>
				</tbody>
			</table>
		</div>
	  </div>	
</div>
<script type="text/javascript">
//获取浏览器的路径
var ieUrl = location.pathname + location.search;
//头部导航选中加阴影
$(".top-nav li").each(function() {
	//获取a标签上面的路径
	var aUrl = $(this).find("a").attr("href");
	//如果浏览器的路径与a标签上面的路径相等，则加上样式
	if(ieUrl == aUrl) {
		$(this).addClass("header_");
	}
})
</script>
<script type="text/javascript" src="${ctx}/resources/pt/js/cms/index_top.js"></script>
</body>
</html>