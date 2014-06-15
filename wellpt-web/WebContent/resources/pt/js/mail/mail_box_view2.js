/*$(function() {
	$("#markSelectBox").powerFloat({
        width: 117,
        eventType: "click",
        target: ".qmpanel_shadow",
        showCall: function() {
            $(".menu_item").click(function() {
                $("#markSelectTo").text($(this).text());
                $.powerFloat.hide();
            });
        }
    });	
$("#markMoveBox").powerFloat({
        width: 117,
        eventType: "click",
        target: ".qmpanel_shadow2",
        showCall: function() {
            $(".menu_item").click(function() {
                $("markMoveTo").text($(this).text());
                $.powerFloat.hide();
            });
        }
    });	
$("#markSelectBox2").powerFloat({
    width: 117,
    eventType: "click",
    target: ".qmpanel_shadow",
    showCall: function() {
        $(".menu_item").click(function() {
            $("#markSelectTo2").text($(this).text());
            $.powerFloat.hide();
        });
    }
});	
$("#markMoveBox2").powerFloat({
    width: 117,
    eventType: "click",
    target: ".qmpanel_shadow2",
    showCall: function() {
        $(".menu_item").click(function() {
            $("markMoveTo2").text($(this).text());
            $.powerFloat.hide();
        });
    }
});	
});*/
function trim(str){ //删除左右两端的空格
    return str.replace(/(^\s*)|(\s*$)/g, "");
}

function openSdLayer(uuid,objId,conId){
	openLayer(objId,conId);
	var nymdhs=$("#showsdate").html();
	nymdhs=trim(nymdhs);
	var nys=nymdhs.split(' ');
	$("#muuid").val(uuid);
	$("#selddate").val(nys[0]);
	$("#seldtime").val(nys[1]);
	$("#showddate").html(nys[0]);
	$("#showdtime").html(nys[1]);
}

//弹出层
function openLayer(objId,conId){
var arrayPageSize   = getPageSize();//调用getPageSize()函数
//var arrayPageScroll = getPageScroll();//调用getPageScroll()函数
if ($('#popupAddr').length<1){
	
//创建弹出内容层
var popupDiv = document.createElement("div");
//给这个元素设置属性与样式
popupDiv.setAttribute("id","popupAddr");
popupDiv.style.position = "absolute";
popupDiv.style.border = "1px solid #ccc";
popupDiv.style.background = "#fff";
popupDiv.style.zIndex = 99;
//创建弹出背景层
var bodyBack = document.createElement("div");
bodyBack.setAttribute("id","bodybg");
bodyBack.style.position = "absolute";
bodyBack.style.width = "100%";
bodyBack.style.height = (arrayPageSize[1] + 35 + 'px');
bodyBack.style.zIndex = 98;
bodyBack.style.top = 0;
bodyBack.style.left = 0;
bodyBack.style.filter = "alpha(opacity=50)";
bodyBack.style.opacity = 0.5;
bodyBack.style.background = "#ddf";
//实现弹出(插入到目标元素之后)
var mybody = document.getElementById(objId);
insertAfter(popupDiv,mybody);//执行函数insertAfter()
insertAfter(bodyBack,mybody);//执行函数insertAfter()
}
//显示背景层
$('#bodybg').show("slow");
//显示内容层
var popObj=$('#popupAddr');
popObj.html($("#"+conId).html());
popObj.show("slow");
//让弹出层在页面中垂直左右居中(统一)
// popObj.style.width  = "600px";
// popObj.style.height = "400px";
// popObj.style.top  = arrayPageScroll[1] + (arrayPageSize[3] - 35 - 400) / 2 + 'px';
// popObj.style.left = (arrayPageSize[0] - 20 - 600) / 2 + 'px';
//让弹出层在页面中垂直左右居中(个性)
//var arrayConSize=getConSize(conId);
if($('#popupAddr').outerWidth()>100){
var awidth=$('#popupAddr').outerWidth();
var aheigth=$('#popupAddr').outerHeight();
 $('#popupAddr')
			.css({position : 'absolute',left : ($(window).width() - $('#popupAddr').outerWidth()) / 2,
				top : ($(window).height() - $('#popupAddr').outerHeight())/ 2 + $(document).scrollTop()
			});
}else{
	$('#popupAddr')
			.css({position : 'absolute',left : ($(window).width() - awidth) / 2,
				top : ($(window).height() - aheigth)/ 2 + $(document).scrollTop()
			});
}
}
function insertAfter(newElement,targetElement){//插入
	var parent = targetElement.parentNode;
	if(parent.lastChild == targetElement){
	parent.appendChild(newElement);
	}
	else{
	parent.insertBefore(newElement,targetElement.nextSibling);
	}
	}

//获取页面实际大小
function getPageSize(){
var xScroll,yScroll;
if (window.innerHeight  &&  window.scrollMaxY){
xScroll = document.body.scrollWidth;
yScroll = window.innerHeight + window.scrollMaxY;
} else if (document.body.scrollHeight > document.body.offsetHeight){
sScroll = document.body.scrollWidth;
yScroll = document.body.scrollHeight;
} else {
xScroll = document.body.offsetWidth;
yScroll = document.body.offsetHeight;
}
var windowWidth,windowHeight;
//var pageHeight,pageWidth;
if (self.innerHeight) {
windowWidth = self.innerWidth;
windowHeight = self.innerHeight;
} else if (document.documentElement  &&  document.documentElement.clientHeight) {
windowWidth = document.documentElement.clientWidth;
windowHeight = document.documentElement.clientHeight;
} else if (document.body) {
windowWidth = document.body.clientWidth;
windowHeight = document.body.clientHeight;
}
var pageWidth,pageHeight;
if(yScroll < windowHeight){
pageHeight = windowHeight;
} else {
pageHeight = yScroll;
}
if(xScroll < windowWidth) {
pageWidth = windowWidth;
} else {
pageWidth = xScroll;
}
//alert(pageWidth+','+pageHeight+','+windowWidth+','+windowHeight);
arrayPageSize = new Array(pageWidth,pageHeight,windowWidth,windowHeight);
return arrayPageSize;
}

//关闭弹出层
function closeLayer(){
	$('#popupAddr').hide("slow");
	
	$('#bodybg').hide("slow");
//$("#popupAddr").style.display = "none";
//$("#bodybg").style.display = "none";
return false;
}



	
	//对“拖动点”定义：onMousedown="StartDrag(this)" onMouseup="StopDrag(this)" onMousemove="Drag(this)"即可
	var move=false,oldcolor,_X,_Y;
function StartDrag(obj){  //定义准备拖拽的函数
obj.setCapture(); //对当前对象的鼠标动作进行跟踪
oldcolor=obj.style.backgroundColor;
obj.style.background="#999";
move=true;
//获取鼠标相对内容层坐标
var parentwin=document.getElementById("popupAddr");
_X=parentwin.offsetLeft-event.clientX;
_Y=parentwin.offsetTop-event.clientY;
}
function Drag(obj){        //定义拖拽函数
if(move){
var parentwin=document.getElementById("popupAddr");
parentwin.style.left=event.clientX+_X;
parentwin.style.top=event.clientY+_Y;
}
}
function StopDrag(obj){   //定义停止拖拽函数
obj.style.background=oldcolor;
obj.releaseCapture(); //停止对当前对象的鼠标跟踪
move=false;
}
//移到发件箱
function moveInMailBox(rel,pageNo,mtype,uuid,ctype,mailname,ctype){
		if(confirm('确认操作么?')) {
			$.ajax({
				type : "POST",
				url : ctx+"/mail/mailbox_move.action",
				data : "pageNo="+pageNo+"&mtype="+mtype+"&selected="+uuid+"&ctype="+ctype,
				dataType : "text",
				success : function callback(result) {
					var listView=$("#listView").val();
					var nextUuid=$("#nextUuid").val();
					if(listView=='1'&&nextUuid!=''){
						var allUuid=$("#allUuid").val();
						//alert(allUuid);
						//alert(allUuid2);
						//alert("${ctx}/mail/mailbox_view2.action?rel="+rel+"&mailname="+mailname+"&uuid="+nextUuid+"&pageNo="+pageNo+"&mtype="+mtype+"&allUuid="+allUuid2);
						window.location.href=ctx+"/mail/mailbox_view2.action?rel="+rel+"&mailname="+mailname+"&uuid="+nextUuid+"&pageNo="+pageNo+"&mtype="+mtype+"&allUuid="+allUuid;
					}else{
					window.location.href=ctx+"/mail/mail_box_list.action?rel="+rel+"&pageNo="+pageNo+"&mtype="+mtype+"&mailname="+mailname+"&ctype="+ctype;
					}
				}
			});
	}
		//window.location.href=contextPath+"/mail/toMove.action?pageNo="+pageNo+"&mtype="+mtype+"&selected="+selected;
}
//转发
function toSendOtherInMailBox(rel,pageNo,mtype,uuid,mailname){
	if(mtype=='') mtype=0;
				window.location.href=ctx+"/mail/mailbox_send_other.action?rel="+rel+"&mailname="+mailname+"&pageNo="+pageNo+"&mtype="+mtype+"&selected="+uuid;

	
}
function toSendBCInMailBox(rel,pageNo,mtype,uuid,mailname){
	if(mtype=='') mtype=0;
	window.location.href=ctx+"/mail/mailbox_send_bc.action?rel="+rel+"&mailname="+mailname+"&pageNo="+pageNo+"&mtype="+mtype+"&selected="+uuid;


}
//转发
function toSendOther2InMailBox(rel,pageNo,mtype,uuid,mailname){
	if(mtype=='') mtype=0;
				window.location.href=ctx+"/mail/mailbox_send_other2.action?rel="+rel+"&mailname="+mailname+"&pageNo="+pageNo+"&mtype="+mtype+"&selected="+uuid;

	
}

/*//删除邮件
function deleInMailBox(rel,pageNo,mtype,uuid,mailname,ctype){
	 var obj = new Object(); 
	 obj.rel = rel;
	 obj.pageNo = pageNo;
	 obj.mtype= mtype;
	 obj.uuid= uuid;
	 obj.mailname= mailname;
	 obj.ctype= ctype;

	oConfirm ("确认彻底删除吗？",deleInMailBox1,obj,"");
	
}
function deleInMailBox1(obj){
	if(mtype=="") mtype="0";
			$.ajax({
				type : "POST",
				url : ctx+"/mail/mail_box_cremove.action",
				data : "mids="+uuid+"&pageNo="+pageNo+"&mtype="+mtype,
				dataType : "text",
				success : function callback(result) {
					var listView=$("#listView").val();
					var nextUuid=$("#nextUuid").val();
					if(listView=='1'&&nextUuid!=''){
						var allUuid=$("#allUuid").val();
						window.location.href=ctx+"/mail/mailbox_view2.action?rel="+rel+"&mailname="+mailname+"&uuid="+nextUuid+"&pageNo="+pageNo+"&mtype="+mtype+"&allUuid="+allUuid;
					}else{
					window.location.href=ctx+"/mail/mail_box_list.action?rel="+rel+"&pageNo="+pageNo+"&mtype="+mtype+"&mailname="+mailname+"&ctype="+ctype;
					}
			}});

}*/

//删除邮件
function deleInMailBox(rel,pageNo,mtype,uuid,mailname,ctype) {
	if(mtype=="") mtype="0";
		if(confirm('确认删除么?')) {
				$.ajax({
					type : "POST",
					url : ctx+"/mail/mail_box_cremove.action",
					data : "mids="+uuid+"&pageNo="+pageNo+"&mtype="+mtype,
					dataType : "text",
					success : function callback(result) {
						var listView=$("#listView").val();
						var nextUuid=$("#nextUuid").val();
						if(listView=='1'&&nextUuid!=''){
							var allUuid=$("#allUuid").val();
							window.location.href=ctx+"/mail/mailbox_view2.action?rel="+rel+"&mailname="+mailname+"&uuid="+nextUuid+"&pageNo="+pageNo+"&mtype="+mtype+"&allUuid="+allUuid;
						}else{
						window.location.href=ctx+"/mail/mail_box_list.action?rel="+rel+"&pageNo="+pageNo+"&mtype="+mtype+"&mailname="+mailname+"&ctype="+ctype;
						}
				}});
		}
}
//置已读未读
function readInMailBox(rel,rtype,pageNo,mtype,uuid,mailname,ctype){
		if(confirm('确认操作么?')) {
			$.ajax({
				type : "POST",
				url : ctx+"/mail/mailbox_readornot.action",
				data : "pageNo="+pageNo+"&mtype="+mtype+"&rtype="+rtype+"&selected="+uuid,
				dataType : "text",
				success : function callback(result) {
					if(rtype=='3'){
						var listView=$("#listView").val();
						var nextUuid=$("#nextUuid").val();
						if(listView=='1'&&nextUuid!=''){
							var allUuid=$("#allUuid").val();
							window.location.href=ctx+"/mail/mailbox_view2.action?rel="+rel+"&mailname="+mailname+"&uuid="+nextUuid+"&pageNo="+pageNo+"&mtype="+mtype+"&allUuid="+allUuid;
						}else{
						window.location.href=ctx+"/mail/mail_box_list.action?rel="+rel+"&pageNo="+pageNo+"&mtype="+mtype+"&mailname="+mailname+"&ctype="+ctype;
						}
					}
					if(rtype=='4'){
						$("#star").attr("src",ctx+"/pt/mail/js/star-on.png");
					}
					if(rtype=='5'){
						$("#star").attr("src",ctx+"/pt/mail/js/star-off.png");
					}
					//window.location.href=contextPath+"/mail/send_mail.action?pageNo="+pageNo+"&mtype="+mtype;
				}
			});
	}
		//window.location.href=contextPath+"/mail/toReadOrNot.action?pageNo="+pageNo+"&mtype="+mtype+"&rtype="+rtype+"&selected="+selected;
}
function setSelThis(obj){
	$(obj).css('background-color','blue');
}
function setSelThis2(obj){
	$(obj).css('background-color','#fff');
}
function setStar(obj,uuid){
	var objs=$(obj).attr('src');
	if(objs.indexOf('star-on')>-1){
		updateStar(obj,0,uuid);
	}else{
		updateStar(obj,1,uuid);
	}
}
function updateStar(obj,stype,uuid){
	$.ajax({
		type : "POST",
		url : ctx+"/mail/mailbox_setstar.action",
		data : "uuid="+uuid+"&stype="+stype,
		dataType : "text",
		success : function callback(result) {
			if(stype==0){
				$(obj).attr('src',ctx+'/pt/mail/js/star-off.png');
			}else{
				$(obj).attr('src',ctx+'/pt/mail/js/star-on.png');
			}
		}
	});
}

function addInFolder(rel,pageNo,mtype,mailname,uid){
	var fname=$("#fname").val();
	var uuid=$("#uuid").val();
	if(fname==''){
		alert("文件夹名称不能为空!");
		fname.focus();
		return false;
	}
	$.ajax({
		type : "POST",
		url : ctx+"/mail/add_folder2.action",
		data : "uuid="+uuid+"&fname="+fname+"&selected="+uid,
		dataType : "text",
		success : function callback(result) {
			if(result.indexOf("error")>-1){
				alert("文件夹名称已存在!");
			}else{
			closeLayer();
			var listView=$("#listView").val();
			var nextUuid=$("#nextUuid").val();
			if(listView=='1'&&nextUuid!=''){
				var allUuid=$("#allUuid").val();
				window.location.href=ctx+"/mail/mailbox_view2.action?rel="+rel+"&mailname="+mailname+"&uuid="+nextUuid+"&pageNo="+pageNo+"&mtype="+mtype+"&allUuid="+allUuid;
			}else{
				window.location.href=ctx+"/mail/mail_box_list.action?rel="+rel+"&pageNo="+pageNo+"&mtype="+mtype+"&mailname="+mailname;
			}
			}
		}
	});
}
function mailView2Load(){
	var sendStatus=$("#sendStatus").val();
	if(sendStatus=='1'){
		$("#che1").attr("disabled","disabled");
		$("#che2").attr("disabled","disabled");
	}
}
function viewSendStatus(){
	$("#viewSendStatus3").show();
	$("#viewSendStatus2").show();
	$("#viewSendStatus1").hide();
}
function viewSendStatus2(){
	$("#viewSendStatus3").hide();
	$("#viewSendStatus2").hide();
	$("#viewSendStatus1").show();
}

function chehuiMail(sendDate,uuid,to,cc,bcc,mailconfig,rel ,pageNo,mtype,mailname ){
	var html1 = '<div  id="chehui"><table style="border: 1px;">'+
		'<tr><td><strong>&nbsp;&nbsp;&nbsp;&nbsp;确定撤回此邮件吗？</strong></td></tr>'+
		'<tr><td>&nbsp;&nbsp;&nbsp;&nbsp;如果撤回成功，对方将只能看到邮件的主题，并得到已被撤回的提示。</td></tr>'+
		'<tr><td style="color: gray;">详细说明：</td></tr><tr>'+
		'<td style="color: gray;">&nbsp;&nbsp;&nbsp;&nbsp;1.仅尝试撤回发往域名内邮箱的邮件，不支持从其他邮箱撤回。</td></tr>'+
		'<tr><td style="color: gray;">&nbsp;&nbsp;&nbsp;&nbsp;2.如果对方已经阅读，将不予撤回。</td></tr>'+
		'<tr><td style="color: gray;">&nbsp;&nbsp;&nbsp;&nbsp;3.撤回结果将通过系统邮件通知您。</td></tr>'+
		'</table></div>';

var html2 = '<div >' +
    '<p>此邮件不支持被撤回</p><br/><div>此邮件距发送时已超过15天，无法撤回。</div>' +
    '</div>';
    
    


var states = {};
states.state1 = {
content: html1,
buttons: { '确定': 1, '取消': 0 },
buttonsFocus: 1,
submit: function (v, h, f) {
if (v == 0) {
    return true; // close the window
}
else {
	if(validateSendDate(sendDate)){
		$.jBox.goToState('state2');
	}else{
		//$.jBox.nextState('<div class="msg-div">正在提交...</div>');
		var html3 = '<div  ><table border="1px" width="100%" style="border:1px solid blue;">'+
	'<tr><td colspan="2"><strong>&nbsp;&nbsp;&nbsp;&nbsp;<img src= ctx+"/pt/mail/js/loading.gif"/>正在撤回中...</strong></td></tr>';
	var str="";
	if(to!=''&&to!=null){
		var tos=to.split(";");
		for(var i=0;i<tos.length;i++){
			if(tos[i]!=''&&tos[i].indexOf(mailconfig)>0){
				str+=tos[i]+";";
				tos[i]=tos[i].replace("<","&lt;");
				tos[i]=tos[i].replace(">","&gt;");
				html3+='<tr><td width="50%">&nbsp;&nbsp;&nbsp;&nbsp;'+tos[i]+'</td><td align="right" width="50%">正在撤回...</td></tr>';
			}
		}
	}
	if(cc!=''&&cc!=null){
		var tos=cc.split(";");
		for(var i=0;i<tos.length;i++){
			if(tos[i]!=''&&tos[i].indexOf(mailconfig)>0){
				str+=tos[i]+";";
				tos[i]=tos[i].replace("<","&lt;");
				tos[i]=tos[i].replace(">","&gt;");
				html3+='<tr><td width="50%">&nbsp;&nbsp;&nbsp;&nbsp;'+tos[i]+'</td><td align="right" width="50%">正在撤回...</td></tr>';
			}
		}
	}
	if(bcc!=''&&bcc!=null){
		var tos=bcc.split(";");
		for(var i=0;i<tos.length;i++){
			if(tos[i]!=''&&tos[i].indexOf(mailconfig)>0){
				str+=tos[i]+";";
				tos[i]=tos[i].replace("<","&lt;");
				tos[i]=tos[i].replace(">","&gt;");
				html3+='<tr><td width="50%">&nbsp;&nbsp;&nbsp;&nbsp;'+tos[i]+'</td><td align="right" width="50%">正在撤回...</td></tr>';
				
			}
		}
	}
	
	html3+='</table></div>';
        $.jBox.goToState('state3',html3);

       //var re=drawMail(uuid,str);
       $.ajax({
   		type : "POST",
   		url : ctx+"/mail/draw_mail.action",
   		data : "uuid="+uuid+"&mails="+str,
   		dataType : "text",
   		success : function callback(result) {
   			var rel=result;
   			var html4 = '<div  ><table border="1px" width="100%" style="border: 1px;">'+
   		   	'<tr><td colspan="2"><strong>&nbsp;&nbsp;&nbsp;&nbsp;撤回操作已完成</strong></td></tr>';
   		   	
   		        var ress=rel.split("|");
   		        for(var j=0;j<ress.length;j++){
   		        	if(ress[j]!=''&&ress[j]!=null){
   		        	var res=ress[j].split("#");
   		        	var jieguo="";
   		        	if(res[1]=='1'){
   		        		jieguo='已撤回';
   		        	}
   		        	if(res[1]=='2'){
   		        		jieguo='<font color="red">撤回失败，邮件可能已被删除</font>';
   		        	}
   		        	if(res[1]=='3'){
   		        		jieguo='<font color="red">撤回失败，对方已读</font>';
   		        	}
   		        	res[0]=res[0].replace("<","&lt;");
   					res[0]=res[0].replace(">","&gt;");
   		        	html4+='<tr><td width="50%">&nbsp;&nbsp;&nbsp;&nbsp;'+res[0]+'</td><td width="50%">'+jieguo+'</td></tr>';
   		        	}
   		        }
   		        html4+='</table></div>';

   			$.jBox.goToState('state4',html4);
   		}
   	}); 
      // var re="";
       
	}
}
return false;
}
};
states.state2 = {
content: html2,
buttons: {  '确定': 0 },
buttonsFocus: 0, 
submit: function (v, o, f) {
if (v == 0) {
    return true; // close the window
} 
return false;
}
};
states.state3 = {
content: '',
buttons: {'关闭': 0},
buttonsFocus: 0,
submit: function (v, o, f) {
	if (v == 0) {
	    return true; // close the window
	} 
	return false;
	}
};
states.state4 = {
content: '',
buttons: { '完成': 0 },
buttonsFocus: 0,
submit: function (v, o, f) {
	if (v == 0) {
		window.location.href=ctx+'/mail/mailbox_view2.action?rel='+rel +'&mailname='+mailname +'&uuid='+uuid+'&pageNo='+pageNo+'&mtype='+mtype;
	    return true; // close the window
	} 
	return false;
	}
};


$.jBox.open(states, '撤回邮件', 450, 'auto');

}
function drawMail(uuid,str){
	var rel="";
	$.ajax({
		type : "POST",
		url : ctx+"/mail/draw_mail.action",
		data : "uuid="+uuid+"&mails="+str,
		dataType : "text",
		success : function callback(result) {
			alert(result);
			rel=result;
		}
	}); 
	return rel;
}
//是否超过15天
function validateSendDate(sendDate){
	var ss=sendDate.split(" ");
	var s=ss[0];
	var arr=s.split("-");
	var starttime = new Date(arr[0], arr[1], arr[2]);
	var starttimes = starttime.getTime();
    var ERP_TIME = new Date();
    var lktime = new Date(ERP_TIME.getYear(),(ERP_TIME.getMonth()+1),(ERP_TIME.getDate()-15));
    var lktimes = lktime.getTime();
    if(starttimes<lktimes){
    	return true;
    }
    return false;
}
function sdcheck2(){
	var ddate=$("#selddate").val();
	 var arr = ddate.split("-");
	 var dtime=$("#seldtime").val();
	 var brr = dtime.split(":");
	    var starttime = new Date(arr[0], arr[1], arr[2],brr[0],brr[1]);
	    var starttimes = starttime.getTime();
	    var ERP_TIME = new Date();
	    var lktime = new Date(ERP_TIME.getYear(),(ERP_TIME.getMonth()+1),ERP_TIME.getDate(),ERP_TIME.getHours(),ERP_TIME.getMinutes());
	    var lktimes = lktime.getTime();
	    
	    

	if (starttimes <= lktimes) {
	    	$("#msg").html("您设置的定时时间已经过期");
			return false;
	}
	$("#msg").html("");
	var uuid=$("#muuid").val();
	var sdate=ddate+" "+dtime;
	$.ajax({
		type : "POST",
		url : ctx+"/mail/mailbox_dingshi.action",
		data : "uuid="+uuid+"&sdate="+sdate,
		dataType : "json",
		success : function callback(result) {
			$("#showsdate").html(sdate);
			closeLayer();
			return true;
		}
	}); 
	
	return false;
	
}
function changeSD(){
	var ddate=$("#selddate").val();
	 var dtime=$("#seldtime").val();
	$("#showddate").html(ddate);
	$("#showdtime").html(dtime);
	
}

$("#mail_close").click(function() {
	returnWindow();
});


//获取url参数
function readParam(){
	var arrayObj = new Array();
	
	$(".checkeds:checked").each(function(i){
		var s = new Object(); 
		var index = $(this).parent().parent(".dataTr").attr("src").indexOf("?");
		var search = $(this).parent().parent(".dataTr").attr("src").substr(index);
		var searchArray = search.replace("?", "").split("&");
		for(var i=0;i<searchArray.length;i++){
			var paraArray = searchArray[i].split("=");
			var key = paraArray[0];
			var value = paraArray[1];
			s[key] = value;
		}
		arrayObj.push(s);
	});
	
	return arrayObj;
}

//删除邮件（cms）
function moveMail(obj){
	var arrayObj = readParam();
	var uuids = new Array();
	for (var i=0;i<=arrayObj.length-1;i++) { 
		var uuid = arrayObj[i]["uuid"];
		uuids.push(uuid);
	}
	if (uuids!=''||uuids.length>0) {
		oConfirm(global.confirmMailDeleted,moveAllMail,obj,"");
	}else{
		oAlert(global.uncheckedAnyMail);
	}
}
function moveAllMail(obj){
	var arrayObj = readParam();
	var uuids = new Array();
	for (var i=0;i<=arrayObj.length-1;i++) { 
		var uuid = arrayObj[i]["uuid"];
		uuids.push(uuid);
	}
	$.ajax({
		type : "POST",
		url : ctx+"/mail/mail_move.action",
		data : "uuids="+uuids+"&ctype="+'3',
		dataType : "text",
		success : function() {
			var moduleid = $(obj).parents(".active").attr("moduleid");
			//我的文件夹、其他邮件、标签刷新当前视图
			if(moduleid == "folder" || moduleid == "other" || moduleid == "mlabel"){
				refreshMailLive($(obj));
			} else {
				refreshWindow($(obj));
			}
		}
	});
}

//彻底删除邮件（cms）
function delMail(obj){
	var arrayObj = readParam();
	var uuids = new Array();
	for (var i=0;i<=arrayObj.length-1;i++) { 
		var uuid = arrayObj[i]["uuid"];
		uuids.push(uuid);
	}
	if (uuids!=''||uuids.length>0) {
		oConfirm (global.confirmMailTotalDeleted,delAllMail,obj,"");
	}else{
		oAlert(global.uncheckedAnyMail);
	}
}
function delAllMail(obj){
	var arrayObj = readParam();
	var uuids = new Array();
	for (var i=0;i<=arrayObj.length-1;i++) { 
		var uuid = arrayObj[i]["uuid"];
		uuids.push(uuid);
	}
	$.ajax({
		type : "POST",
		url : ctx+"/mail/mail_delete.action",
		data : "uuids="+uuids+"&ctype="+'3',
		dataType : "text",
		success : function() {
			var moduleid = $(obj).parents(".active").attr("moduleid");
			//我的文件夹、其他邮件、标签刷新当前视图
			if(moduleid == "folder" || moduleid == "other" || moduleid == "mlabel"){
				refreshMailLive($(obj));
			} else {
				refreshWindow($(obj));
			}
		}
	});
}

//转发（cms）
function forwardingMail(){
	var arrayObj = readParam();
	var uuids = new Array();
	for (var i=0;i<=arrayObj.length-1;i++) { 
		var uuid = arrayObj[i]["uuid"];
		uuids.push(uuid);
	}
	if (uuids!=''||uuids.length>0) {
		var arrayObj = readParam();
		for (var i=0;i<=arrayObj.length-1;i++) { 
			if (arrayObj.length > 1) {
				oAlert2(global.selectOnlyOne);
			} else {
				toSendOtherInMailBox(arrayObj[i]["rel"],arrayObj[i]["pageNo"],arrayObj[i]["mtype"],arrayObj[i]["uuid"],arrayObj[i]["mailname"]);
			}
		}
	}else{
		oAlert(global.uncheckedAnyMail);
	}
}

//全部置未读（cms）
function unreadMail(obj){
	var arrayObj = readParam();
	var uuids = new Array();
	for (var i=0;i<=arrayObj.length-1;i++) { 
		var uuid = arrayObj[i]["uuid"];
		uuids.push(uuid);
	}
	if (uuids!=''||uuids.length>0) {
		oConfirm (global.confirmMailUnread,markAllUnread,obj,"");
	}else{
		oAlert(global.uncheckedAnyMail);
	}
}
function markAllUnread(obj){
	var arrayObj = readParam();
	var uuids = new Array();
	for (var i=0;i<=arrayObj.length-1;i++) { 
		var uuid = arrayObj[i]["uuid"];
		uuids.push(uuid);
	}
	$.ajax({
		type : "POST",
		url : ctx+"/mail/unread.action",
		data : "uuids="+uuids,
		dataType : "text",
		success : function() {
			var moduleid = $(obj).parents(".active").attr("moduleid");
			//我的文件夹、其他邮件、标签刷新当前视图
			if(moduleid == "folder" || moduleid == "other" || moduleid == "mlabel"){
				refreshMailLive($(obj));
			} else {
				refreshWindow($(obj));
				//刷新左侧导航
				refreshWindow($(".writeMail"));
			}
		}
	});
}
//全部置已读（cms）
function readMail(obj){
	var arrayObj = readParam();
	var uuids = new Array();
	for (var i=0;i<=arrayObj.length-1;i++) { 
		var uuid = arrayObj[i]["uuid"];
		uuids.push(uuid);
	}
	if (uuids!=''||uuids.length>0) {
		oConfirm (global.confirmMailRead,markAllRead,obj,"");
	}else{
		oAlert(global.uncheckedAnyMail);
	}
}
function markAllRead(obj){
	var arrayObj = readParam();
	var uuids = new Array();
	for (var i=0;i<=arrayObj.length-1;i++) { 
		var uuid = arrayObj[i]["uuid"];
		uuids.push(uuid);
	}
	$.ajax({
		type : "POST",
		url : ctx+"/mail/read.action",
		data : "uuids="+uuids,
		dataType : "text",
		success : function() {
			var moduleid = $(obj).parents(".active").attr("moduleid");
			//我的文件夹、其他邮件、标签刷新当前视图
			if(moduleid == "folder" || moduleid == "other" || moduleid == "mlabel"){
				refreshMailLive($(obj));
			} else {
				refreshWindow($(obj));
				//刷新左侧导航
				refreshWindow($(".writeMail"));
			}
		}
	});
}
//全部标记为星标邮件（cms）
function setToStar(obj){
	var arrayObj = readParam();
	var uuids = new Array();
	for (var i=0;i<=arrayObj.length-1;i++) { 
		var uuid = arrayObj[i]["uuid"];
		uuids.push(uuid);
	}
	if (uuids!=''||uuids.length>0) {
		oConfirm (global.confirmMailToStar,setAllToStar,obj,"");
	}else{
		oAlert(global.uncheckedAnyMail);
	}
}
function setAllToStar(obj){
	var arrayObj = readParam();
	var uuids = new Array();
	for (var i=0;i<=arrayObj.length-1;i++) { 
		var uuid = arrayObj[i]["uuid"];
		uuids.push(uuid);
	}
	$.ajax({
		type : "POST",
		url : ctx+"/mail/mailbox_settostar_or_not.action",
		data : "uuids="+uuids+"&stype="+"1",
		dataType : "text",
		success : function callback(result) {
			var moduleid = $(obj).parents(".active").attr("moduleid");
			//我的文件夹、其他邮件、标签刷新当前视图
			if(moduleid == "folder" || moduleid == "other" || moduleid == "mlabel"){
				refreshMailLive($(obj));
			} else {
				refreshWindow($(obj));
			}
		}
	});
}
//全部标记为取消星标（cms）
function unsetToStar(obj){
	var arrayObj = readParam();
	var uuids = new Array();
	for (var i=0;i<=arrayObj.length-1;i++) { 
		var uuid = arrayObj[i]["uuid"];
		uuids.push(uuid);
	}
	if (uuids!=''||uuids.length>0) {
		oConfirm (global.confirmMailCancelStar,unsetAllToStar,obj,"");
	}else{
		oAlert(global.uncheckedAnyMail);
	}
}
function unsetAllToStar(obj){
	var arrayObj = readParam();
	var uuids = new Array();
	for (var i=0;i<=arrayObj.length-1;i++) { 
		var uuid = arrayObj[i]["uuid"];
		uuids.push(uuid);
	}
	$.ajax({
		type : "POST",
		url : ctx+"/mail/mailbox_settostar_or_not.action",
		data : "uuids="+uuids+"&stype="+"0",
		dataType : "text",
		success : function callback(result) {
			var moduleid = $(obj).parents(".active").attr("moduleid");
			//我的文件夹、其他邮件、标签刷新当前视图
			if(moduleid == "folder" || moduleid == "other" || moduleid == "mlabel"){
				refreshMailLive($(obj));
			} else {
				refreshWindow($(obj));
			}
		}
	});
}




