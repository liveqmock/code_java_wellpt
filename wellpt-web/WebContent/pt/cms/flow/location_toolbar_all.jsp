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
<style type="text/css">*
{margin:0;padding:0;font-size:100%;}
.map_content_dialog #dialogContent {
    height: 325px;
}
.nav .menu-item li {
    list-style-type: none;
}
.nav a{font-size:12px;text-decoration:none;color:#fff;}
.nav{background: none repeat scroll 0 0 #55595C; 
    color: #FFFFFF;
    height: 38px;
    padding: 0 0 0 10px;}
.nav .nav-list{float:left;height: 38px;}
.nav .nav-list {color:#fff;}
.nav .nav-icon{background: url("${ctx}/resources/theme/images/v1_map_bg.png") repeat-y scroll 0 0 #636668;
    float: right;
    height: 38px;
    padding-left: 10px;padding-right: 10px;}
.nav .nav-icon a{float:left;width:20px;height:20px;margin:5px;background:url(${ctx}/resources/theme/images/v1_icon.png) no-repeat 0 -375px;}
.nav .nav-icon a.refresh{background-position:-20px -375px;}
.nav .nav-icon a.fun {background-position: -65px -375px;cursor: pointer;}
.nav .nav-icon a {cursor: pointer;}
.nav .nav-icon a.help{background-position:-41px -375px;}
.home-content{width:100%;position:relative;}
.nav .nav-list i {
    background: url("${ctx}/resources/theme/images/v1_icon.png") no-repeat scroll -23px -665px transparent;
    display: inline-block;
    height: 18px;
    vertical-align: middle;
    width: 18px;
}
.nav .nav-list .menu {
cursor: pointer;
     height: 28px;
    padding-left: 15px;
    padding-right: 15px;
    padding-top: 10px;
    position: relative;
    z-index: 10;
    border-left: 1px solid #55595C;
    border-right: 1px solid #55595C;
}
.nav .nav-list .home{
display: inline-block;
    height: 25px;
    line-height: 10px;
    padding-top: 14px;}
.nav .nav-list .menu:hover{
	  background: none repeat scroll 0 0 #34383B;
border-left: 1px solid #303234;
border-right: 1px solid #91969B;
}
.nav .nav-list .menu-item{background: none repeat scroll 0 0 #FFFFFF;
    border: 1px solid #C3C8CD;
    box-shadow: 2px 2px 3px #B5B5B5;
    display: none;
    position: absolute;
    right: -40px;
    top: 38px;
    width: 130px;}
.nav .nav-list .menu-item .arrow{position:absolute;top:-8px;right:50px;width:13px;height:8px;background:url(${ctx}/resources/theme/images/v1_icon.png) no-repeat -49px -670px;}
.nav .nav-list .menu-item ul{padding:3px;margin-left: 0;line-height:32px;background: none repeat scroll 0 0 transparent;margin-right:0;}
.nav .nav-list .menu-item a{color: #0D579B;
    cursor: pointer;
    display: block;
    font-size: 12px;
    height: 28px;
    line-height: 28px;
    padding-left: 15px;
    padding-top: 0;}
.nav .nav-list .menu-item a:hover{padding:0;padding-left:15px;background:#4b9ad2;color:#fff;margin:0;}
.nav .cateTop_a {
    cursor: pointer;
}
.home.menu.nav-list-div img {
    margin-right: 2px;
     margin-top: -2px;
}
.nav .cateTop{display: none;}
.nav .cateSecond{display: none;}
.nav .map_icon_btn{
	float:left;
	height:30px;
    margin-top: 4px;
}
.nav .map_icon_btn:hover{
background:#34383B;
}
.map_content_dialog{
	display:none;	
	left: 0;
  position: absolute;
  background: none repeat scroll 0 0 #E5E5E5;
  top: 38px;
  z-index: 9999;
  width: 1202px;
  background:url("${ctx}/resources/theme/images/v1_bd.png") repeat-y scroll 100% 0 #E5E5E5;

}
.map_content_dialog .con-icon {
    background: url("${ctx}/resources/theme/images/v1_icon.png") no-repeat scroll -69px -419px transparent;
    height: 8px;
    position: absolute;
    right: 107px;
    top: -5px;
    width: 13px;
    z-index: 2;
}
.oa_map_l{
					float:left;margin:20px 0;
					background: url("${ctx}/resources/theme/images/v1_map2.png") no-repeat scroll 0 0 transparent; 
				}
				.oa_map_l ul{
					 height: 280px;
   					 width: 380px;
   					 list-style: none outside none;
   					 margin: 0 10px 5px 7px;
   					  background: none repeat scroll 0 0 transparent;
				}
				.oa_map_l ul li{
					background: none repeat scroll 0 0 #FFFFFF;
				    border: 1px solid #D5D5D5;
				    display: inline;
				    float: left;
				    height: 108px;
				    margin: 20px 0 0 15px;
				    width: 108px;
				    list-style: none outside none;
				}
				.oa_map_l ul li a{
					 display: block;
				    font-family: "Microsoft YaHei";
				    font-size: 14px;
				    height: 103px;
				    padding-top: 5px;
				    text-align: center;
				    color: #434343;
				    text-decoration: none;
				}
				.oa_map_l ul li a:hover{
					color:#ff7200;
                    background: none;
				}
				.oa_map_l ul li a img{
					display: block;
				    height: 65px;
				    margin: 0 auto 5px;
				    width: 80px;
				    border: 0 none;
				}
.oa_map_l_first{
background:none;
}
.jh1,.jh2{
    margin: 10px 5px;
}
.nav .nav-icon .full{
    background: url("${ctx}/resources/theme/images/v1_icon_full.png") no-repeat scroll 0 0 transparent;
}
.nav .nav-list .dw_xiala{
    background: url("${ctx}/resources/theme/images/v1_icon_xiala_dw.png") no-repeat scroll 0 0 transparent;
    display: block;
    height: 12px;
    left: 46%;
    position: absolute;
    top: 39px;
    width: 16px;
display:none;
}
.activity_{
    	  background: none repeat scroll 0 0 #34383B;
border-left: 1px solid #303234;
border-right: 1px solid #91969B;
}
</style>
<div class="nav navs">
<div class="nav-list">

<c:forEach var="cmsCategory" items="${cmsCategorys}" varStatus="status">
	<div class="home menu nav-list-div" style="float: left">
		<c:if test="${cmsCategory.cc.code !='YPT_GJL_SYBZ_SSGL' && cmsCategory.cc.code !='YPT_GJL_SYBZ_XZSP'}">
			<c:if test="${empty cmsCategory.cc.inputUrl}">
				<a>${cmsCategory.cc.title}</a>
			</c:if>
			<c:if test="${!empty cmsCategory.cc.inputUrl}">
				<a href="${ctx}${cmsCategory.cc.inputUrl}">${cmsCategory.cc.title}</a>
			</c:if>
			<span class="dw_xiala">&nbsp;</span>
		</c:if>
	</div>
</c:forEach>
</div>

<div class="nav-icon">
<div class="map_icon_btn" style="display:none;" title="模块导航"><a class="fun">&nbsp;</a></div>
<div class="map_icon_btn"  title="通讯录"><a href="${ctx}/cms/cmspage/readPage?uuid=ce557faa-c55b-4cde-96c3-c9cc26ada8d5&ContentArea=a851434e-a5df-4305-be27-5ef7d11ba2c3" style="background: url('${ctx}/resources/theme/images/contact.png') no-repeat scroll 0 0 transparent;"></a></div>
<div class="map_icon_btn"  title="返回首页"><a href="${ctx}/cms/cmspage/readPage?uuid=6012e415-4f39-444c-8e38-aa1e32444267" style="background: url('${ctx}/resources/theme/images/ldx_home_icon.png') no-repeat scroll 0 0 transparent;"></a></div>
<div class="map_icon_btn" title="刷新"><a class="refresh" onclick="window.location=location.href;">&nbsp;</a></div>

<c:forEach var="cmsCategory" items="${cmsCategorys}" varStatus="status">
<c:if test="${cmsCategory.cc.code=='YPT_GJL_SYBZ_SSGL' || cmsCategory.cc.code =='YPT_GJL_SYBZ_XZSP'}">
<div class="map_icon_btn" title="使用帮助">
<a class="help" href="${ctx}${cmsCategory.cc.inputUrl}">&nbsp;</a></div>
</c:if>
</c:forEach>

<div class="map_icon_btn" style="display: none;" title="全屏"><a class="full">&nbsp;</a></div>
</div>

<div class="map_content_dialog"><i class="con-icon">&nbsp;</i>

<div id="dialogContent">
<div class="oa_map_l oa_map_l_first">
<ul class="item_map">
	<li class="item_map"><a class="item_map" href="${ctx}/cms/cmspage/readPage?uuid=666d0f04-7200-47f7-82d3-f85c495d8ce1&amp;treeName=DOC_RECEIPT&amp;mid=20136315910846&amp;moduleid=9caeb90a-9a15-48be-8521-336005635cbd"><img alt="" class="item_map" src="${ctx}/resources/theme/images/icon/m_1.png" />收文管理</a></li>
	<li class="item_map"><a class="item_map" href="${ctx}/cms/cmspage/readPage?uuid=666d0f04-7200-47f7-82d3-f85c495d8ce1&amp;treeName=DOC_DISPATCH&amp;mid=20136315910846&amp;moduleid=7c7de149-fbc1-490c-b329-b210cbf21a05"><img alt="" class="item_map" src="${ctx}/resources/theme/images/icon/m_2.png" />发文管理</a></li>
	<li class="item_map"><a class="item_map" href="${ctx}/cms/cmspage/readPage?uuid=666d0f04-7200-47f7-82d3-f85c495d8ce1&amp;treeName=MAIL_CATE&amp;mid=20136315910846&amp;moduleid=3fc451f3-bfc8-4a7e-ba21-0fa7c53f2efe"><img alt="" class="item_map" src="${ctx}/resources/theme/images/icon/m_11.png" />邮件管理</a></li>
	<li class="item_map"><a class="item_map" href="${ctx}/cms/cmspage/readPage?uuid=ce557faa-c55b-4cde-96c3-c9cc26ada8d5&ContentArea=b64b1c1b-3433-4316-b529-4e03f107c96e"><img alt="" class="item_map" src="${ctx}/resources/theme/images/icon/m_12.png" />文件管理</a></li>
	
	<li class="item_map"><a class="item_map" href="${ctx}/cms/cmspage/readPage?uuid=ce557faa-c55b-4cde-96c3-c9cc26ada8d5&amp;201494101351966=e6be092d-9b4f-4d7d-9d0d-2e95340478c7"><img alt="" class="item_map" src="${ctx}/resources/theme/images/icon/m_21.png" />树形页面</a></li>
	<%-- <li class="item_map"><a class="item_map" href="${ctx}/cms/cmspage/readPage?uuid=666d0f04-7200-47f7-82d3-f85c495d8ce1&amp;treeName=VEHICLE_MANAGEMENT&amp;mid=20136315910846&amp;moduleid=bd2830a2-8370-437b-ab4d-ca39ca079543"><img alt="" class="item_map" src="${ctx}/resources/theme/images/icon/m_21.png" />用车管理</a></li> --%>
	<li class="item_map"><a class="item_map" href="${ctx}/cms/cmspage/readPage?uuid=fb4021ab-79da-4d99-adad-68d78d0d235c&amp;treeName=REPORT&amp;mid=2013710211246285&amp;moduleid=6a16b15e-6a94-4f93-853a-36b48ef6cb1f"><img alt="" class="item_map" src="${ctx}/resources/theme/images/icon/m_22.png" />统计报表</a></li>
</ul>
</div>

<div class="oa_map_l">
<ul class="item_map">
	<li class="item_map"><a class="item_map" href="${ctx}/cms/cmspage/readPage?uuid=ce557faa-c55b-4cde-96c3-c9cc26ada8d5&amp;treeName=SCHEDULE_CATE"><img alt="" class="item_map" src="${ctx}/resources/theme/images/icon/m_3.png" />个人日程</a></li>
	<li class="item_map"><a class="item_map" href="${ctx}/cms/cmspage/readPage?uuid=c9b26993-131a-4a2b-842c-8e3a11646dea&amp;treeName=LDX_BOOKMANAGE&amp;20149101843322=c4711b70-7bd7-4b1b-aec2-20caacbbb6dc"><img alt="" class="item_map" src="${ctx}/resources/theme/images/icon/m_10.png" />图书管理</a></li>
	<li class="item_map"><a class="item_map" href="${ctx}/cms/cmspage/readPage?uuid=666d0f04-7200-47f7-82d3-f85c495d8ce1&amp;treeName=MESSAGE&amp;mid=20136315910846&amp;moduleid=01c72d84-753a-43de-8475-b87527c8a693"><img alt="" class="item_map" src="${ctx}/resources/theme/images/icon/m_4.png" />我的消息</a></li>
	<li class="item_map"><a class="item_map" href="${ctx}/cms/cmspage/readPage?uuid=666d0f04-7200-47f7-82d3-f85c495d8ce1&amp;treeName=WUPIN_GUANLI&amp;mid=20136315910846&amp;moduleid=fc2b47b3-8e58-4f74-8904-f77f5217eb99"><img alt="" class="item_map" src="${ctx}/resources/theme/images/icon/m_4.png" />物品管理</a></li>
	<li class="item_map"><a class="item_map" href="${ctx}/cms/cmspage/readPage?uuid=666d0f04-7200-47f7-82d3-f85c495d8ce1&amp;treeName=CAIGOU_GUANLI&amp;mid=20136315910846&amp;moduleid=7913694e-b9ab-4a96-861f-87d08ca2e326"><img alt="" class="item_map" src="${ctx}/resources/theme/images/icon/m_4.png" />采购管理</a></li>
	<li class="item_map"><a class="item_map" href="${ctx}/cms/cmspage/readPage?uuid=7f2f78e0-024b-4c18-be87-915c337aa445&2014228163647814=2d031b61-f6f3-48b8-afc8-4a34400529a9&201422816336356=e250430a-99d4-47ae-8646-4a230659d802#"><img alt="" class="item_map" src="${ctx}/resources/theme/images/icon/m_4.png" />事项管理</a></li>
</ul>
</div>

<div class="oa_map_l">
<ul class="item_map">
	<li class="item_map"><a class="item_map" href="${ctx}/cms/cmspage/readPage?uuid=666d0f04-7200-47f7-82d3-f85c495d8ce1&amp;treeName=NOTICE&amp;mid=20136315910846&amp;moduleid=85c2e2a0-9bab-485f-b3ad-d8ff0916a90c"><img alt="" class="item_map" src="${ctx}/resources/theme/images/icon/m_17.png" />公告管理</a></li>
	<li class="item_map"><a class="item_map" href="${ctx}/cms/cmspage/readPage?uuid=50746c5f-5138-4891-90d1-b99a7c2ccf0c&amp;treeName=ADDREES_BOOK"><img alt="" class="item_map" src="${ctx}/resources/theme/images/icon/m_19.png" />通讯录</a></li>
	<li class="item_map"><a class="item_map" href="${ctx}/cms/cmspage/readPage?uuid=c9b26993-131a-4a2b-842c-8e3a11646dea&amp;treeName=meeting_manage&amp;20149101843322=db25efa6-d2db-427c-aec0-ccb7f6fe2117"><img alt="" class="item_map" src="${ctx}/resources/theme/images/icon/m_20.png" />会议管理</a></li>
	<li class="item_map"><a class="item_map" href="${ctx}/cms/cmspage/readPage?uuid=c9b26993-131a-4a2b-842c-8e3a11646dea&amp;treeName=ldx_zpgl_2jdh&amp;20149101843322=006eecd8-b988-47db-9ec0-b327dde2aeef"><img alt="" class="item_map" src="${ctx}/resources/theme/images/icon/m_21.png" />招聘管理</a></li>
	<li class="item_map"><a class="item_map" href="${ctx}/cms/cmspage/readPage?uuid=c9b26993-131a-4a2b-842c-8e3a11646dea&amp;treeName=ldx_rsgl_2jdh&amp;20149101843322=c5a9ec86-d033-4a48-9b97-915187502933"><img alt="" class="item_map" src="${ctx}/resources/theme/images/icon/m_14.png" />人事管理</a></li>
<!--	<li class="item_map"><a class="item_map" href="${ctx}/cms/cmspage/readPage?uuid=666d0f04-7200-47f7-82d3-f85c495d8ce1&amp;treeName=FILE_MANAGEMENT&amp;mid=20136315910846&amp;moduleid=a69b56e7-ad11-46da-bb7f-d3ef071280b3"><img alt="" class="item_map" src="${ctx}/resources/theme/images/icon/m_15.png" />档案管理</a></li>
<li class="item_map"><a class="item_map" href="${ctx}/cms/cmspage/readPage?uuid=666d0f04-7200-47f7-82d3-f85c495d8ce1&amp;treeName=SPECIAL_COLUMN&amp;mid=20136315910846&amp;moduleid=34d85406-e7be-41b3-800b-4f424268fb99"><img alt="" class="item_map" src="${ctx}/resources/theme/images/icon/m_13.png" />专题专栏</a></li>
-->
	<li class="item_map"><a class="item_map" href="${ctx}/cms/cmspage/readPage?uuid=666d0f04-7200-47f7-82d3-f85c495d8ce1&amp;treeName=DATAEXCHANGE&amp;mid=20136315910846&amp;moduleid=592f6730-a232-4758-95dd-bfe2ecada3fa"><img alt="" class="item_map" src="${ctx}/resources/theme/images/icon/m_20.png" />数据交换</a></li>
</ul>
</div>
</div>

<div class="view_foot" style="height: 3px;">&nbsp;</div>
</div>
</div>
<script type='text/javascript'>
$(function() {
//获取浏览器的路径
var ieUrl = location.pathname + location.search;
//工具栏选中加阴影
$(".home").each(function() {
	//获取a标签上面的路径
	var aUrl = $(this).find("a").attr("href");
	//如果浏览器的路径与a标签上面的路径相等，则加上样式
	if(ieUrl == aUrl) {
		$(this).addClass("activity_");
		$(this).find("a").next().css("display","inline");
	}
})

// var tempUuid1 = "aad79fc9-6c5e-424a-8bd4-1c0794e2f6be";
// var tempUuid11 = "851ca01d-6e5b-41d8-aa1f-082ed72c4598";
// var tempUuid2 = location.search.split("=")[1];
// if(tempUuid1 == tempUuid2) {
//   $(".shangshiguanli").parent().addClass("activity_");
//   $(".shangshiguanli").next().css("display","inline");
// }else if(tempUuid2.indexOf(tempUuid11)>-1) {
//   $(".lianxiren").parent().addClass("activity_");
//   $(".lianxiren").next().css("display","inline");
// }

     $(".full").click(function(){
			    var windowWidth =   $(window).width();
                var containerClass = $(".container").attr("class");
                if(containerClass.indexOf("fulled")>-1){
                    $.cookie("cookie.isfull", "no");
                    window.location=location.href;
                }else{
                    $.cookie("cookie.isfull", "yes");
                    proportion = parseInt(windowWidth) / 1200;
			        $(".container").css("width",windowWidth);
			        $(".container").css("background","#fff"); 
			        $("body").css("background","#fff");
			        $(".footer").next().remove();
			        $(".dnrw").each(function(){
			            var width = $(this).css("width");
			            var left = $(this).css("left");
			            var width_ = parseInt(width)*proportion;
			            var left_ = parseInt(left)*proportion;
			            $(this).css("width",width_);
			            $(this).css("left",left_);
			        });
                    $(".container").attr("class","container fulled");
                }
			    
	});
    var treeName ="";
	var search = location.search;
	var parm = search.split("&");
	for(var i=0;i<parm.length;i++){
		if(parm[i].indexOf("treeName")>-1){
				treeName = parm[i].split("=")[1];
		}
	}
$(document).mousedown(function(event){
   var temp = $(event.target).attr("class");
/**地图隐藏与现实**/
   if(temp=="fun"){
        if($(".map_content_dialog").css("display")=='none'){
			$(".map_content_dialog").show();
		}else{
			$(".map_content_dialog").hide();
		}
   }else if(temp!="item_map"){
        $(".map_content_dialog").hide();
   }
/**分类隐藏**/
   if(temp!="menu-item"&&temp!="cateTop_child_a"&&temp!="cateTop_a"){
        $(".menu-item").hide();
   }
   if(temp!="customButton_group_name_text"&&temp!="select_icon"&&temp!="customButton_group_button"){
        if($(".customButton_group_buttons").length>0){
              $(".customButton_group_buttons").hide();
        }
   }
   
});
});

            $(".nav .nav-list .menu").live("mouseover",function(){
                $(this).find(".dw_xiala").show(); 
			});
			$(".nav .nav-list .menu").live("mouseout",function(){
                 if($(this).hasClass("activity_")){
                 } else {
				   $(this).find(".dw_xiala").hide(); 
                 } 
			});
</script>
</body>
</html>