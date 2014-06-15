////确保contextPath有值
//if (!window.contextPath) {
//	window.contextPath = "/" + window.location.pathname.split("/")[1];
//	window.ctx = window.contextPath;
//}
// 获取cookie
function getCookie(name) {
	var arr = document.cookie.match(new RegExp("(^| )" + name + "=([^;]*)(;|$)"));
	if (arr) {
		return decodeURI(arr[2]);
	} else {
		return null;
	}
}
// 确保contextPath有值
function getContextPath() {
	if (window.ctx == null || window.contextPath == null) {
		window.contextPath = getCookie("ctx");
		window.contextPath = window.contextPath == "\"\"" ? "" : window.contextPath;
		window.ctx = window.contextPath;
	}
	return window.ctx;
}
getContextPath();

String.prototype.replaceAll = function (str1,str2){      var str  = this;      var result  = str.replace(eval("/"+str1+"/gi"),str2);      return result;}
//提交验证
function check(stype){
	//验证收件人 
	var re=/^[^\s()<>@,;:\/]+@\w[\w\.-]+\.[a-z]{2,}$/i;
	var to =$("#to").val();
	if(to==''||to=='undefined'){
		oAlert(global.toMailNotNull);
		$("#to").focus();
		return ;
	}
	var tos=to.split(";");
	for(var i=0;i<tos.length;i++){
		if(tos[i].indexOf("@")>0){
			if(tos[i]!=''){
				if(tos[i].indexOf("<")>=0){
					tos[i]=tos[i].substring(tos[i].indexOf("<")+1,tos[i].indexOf(">"));
				}
		if(re.test(tos[i])==false){
			oAlert(global.toMailError);
			$("#to").focus();
			return ;
		}
			}
		}
	}
	//验证抄送 
	var cc =$("#cc").val();
	if(cc!=''&&cc!='undefined'){
		var ccs=cc.split(";");
		for(var i=0;i<ccs.length;i++){
			if(ccs[i].indexOf("@")>0){
				if(ccs[i]!=''){
					if(ccs[i].indexOf("<")>=0){
						ccs[i]=ccs[i].substring(ccs[i].indexOf("<")+1,ccs[i].indexOf(">"));
					}
			if(re.test(ccs[i])==false){
				oAlert(global.ccMailError);
				$("#cc").focus();
				return ;
			}
				}
			}
		}
	}
	//验证密送 
	var bcc =$("#bcc").val();
	if(bcc!=''&&bcc!='undefined'){
		var bccs=bcc.split(";");
		for(var i=0;i<bccs.length;i++){
			if(bccs[i]!=''){
				if(bccs[i].indexOf("<")>=0){
					bccs[i]=bccs[i].substring(bccs[i].indexOf("<")+1,bccs[i].indexOf(">"));
				}
			if(bccs[i].indexOf("@")>0){
			if(bccs[i]!=''&&re.test(bccs[i])==false){
				oAlert(global.bccMailError);
				$("#bcc").focus();
				return ;
			}
			}
			}
		}
	}
	
	//验证主题 
	var sendFlag1=$("#sendFlag1").val();
	var subject =$("#subject").val();
	if(subject==''||subject=='undefined'){
		var text=CKEDITOR.instances.text.getData();
		//oAlert(sendFlag1+text);
		if(sendFlag1=='1'&&text!=''){
			var arr="";
			if(text.indexOf("<br")<0){
				arr=text;
			}else{
			arr=text.substring(0,text.indexOf("<br"));
			}
		//oAlert(arr);
			$("#subject").val(arr);
		}else{
		oAlert(global.mailSubjectNotNull);
		$("#subject").focus();
		return ;
		}
	}
	if($("#ddmail").attr("checked")=='checked'){
		if($("#nymdhs").val()==''){
			oAlert(global.mailDingshiNotNull);
			return false;
		}
	}
	var fileupload=$("#fileupload").val();
	if(fileupload=='0'){
		oAlert(global.uploadingNotSend);
		return ;
	}
	//$("#mtype").val(1);
	$("#isSend").val(stype);
	var groupName=$("#groupName").val();
	if($("#ddmail").attr("checked")=='checked'){
		$("#mtype").val(5);
	}
	if($("#ffmail").attr("checked")=='checked'){
		$("#mtype").val(0);
	}
	
	if($("#ggmail").attr("checked")=='checked'){
		$("#mailType").val(2);
	}else{
		$("#mailType").val(1);
	}
	checkAfterSendMail();
	
}
//关闭窗口
function closeWindow(){
	window.close();
}

function checkAfterSendMail(){
$("#msg").html(global.getting+"<img id=\"loading\" src=\""+ctx+"/pt/mail/js/loading.gif\">");
var mailname=$("#mailname").val();
var rel=$("#rel").val();

var uuid=$("#uuid").val();
var attachment="";
var fileMid="";
//mongo统一上传控件处理
var fileObjs = WellFileUpload.files[elementID];
for(var i in fileObjs){
	var fileObj = fileObjs[i];
	var fileID = fileObj.fileID;
	fileMid += fileID+"#";
} 
$(".filename").each(function (){
	attachment+=$(this).html()+"#";
});
var isSend=$("#isSend").val();
var mtype=$("#mtype").val();
var mailType=$("#mailType").val();
var scolor=$("#scolor").val();
var groupName=$("#groupName").val();
var mid=$("#mid").val();
var status=$("#status").val();
var to=$("#to").val();
var cc=$("#cc").val();
var bcc=$("#bcc").val();
var nymdhs=$("#nymdhs").val();
var subject=$("#subject").val();

var text=CKEDITOR.instances.text.getData();
if(text!="" && text!=undefined){
	text=text.replaceAll("&","@w!@");
}
var from=$("#from").val();
pageLock("show");

$.ajax({
	type : "POST",
	url : ctx+"/mail/sendMail.action",
	data : "uuid="+uuid+"&attachment="+attachment+"&fileMid="+fileMid+"&isSend="+isSend+"&mtype="+mtype+"&mailType="+mailType+"&scolor="+scolor+"&groupName="+groupName+"&mid="+mid+"&status="+status+"&to="+to+"&cc="+cc+"&bcc="+bcc+"&nymdhs="+nymdhs+"&subject="+subject+"&text="+text+"&from="+from,
	dataType : "text",
	success : function callback(result) {
		if(result=='succ'){//发送成功
			oAlert(global.sendSuccess,closeWindow,"");
			pageLock("hide");
			$("#msg").html(global.sendSuccess);
		}else if(result=='save_draft_success'){//保存草稿成功
			oAlert(global.saveDraftSuccess,closeWindow,"");
			pageLock("hide");
		}else{//发送失败
			oAlert(global.sendError,closeWindow,"");
			pageLock("hide");
    		$("#msg").html(global.sendError,closeWindow,"");
		}
	},
	error: function (data, status, e)
	{
		oAlert(global.sendError,closeWindow,"");
		pageLock("hide");
	}
	}); 
	}
//页面加载后调用
function wmailOnLoad(){
	//附件上传
	$("#text_title").parent().append(fileHtmlStr());
	fileuploader_upload();
//	oAlert($("#attach").val());
	
	var rel=$("#rel").val();
	if(rel=='4'){
		$("#gmail").val(1);
		$("#ggmail").attr('checked',true);
		$("#ggmail").attr("readonly","readonly");
		$("#ffmail").attr('checked',false);
		$("#ffmail").attr("readonly","readonly");
	}
	var status=$("#status").val();
	var mailType=$("#mailType").val();
	if(status=='4'){
		$("#subject").attr("readonly","readonly");
		$("#ffmail").attr('checked',false);
		$("#ffmail").attr("disabled","disabled");
		$("#gmail").val(1);
		$("#ggmail").attr('checked',true);
		$("#ggmail").attr("disabled","disabled");
	}
	if(status!='4'){
		var mtype=$("#mtype").val();
		if(mailType=='2'){
		$("#ffmail").attr('checked',false);
		$("#gmail").val(1);
		$("#ggmail").attr('checked',true);
		}else{
			if(mtype=='0'){
				$("#ffmail").attr('checked',true);
				$("#ffshow").hide();
			}
			if(mtype=='2'){
				$("#ddmail").attr('checked',true);
				$("#nymdhs_span").show();
			}
		}
	}
	var attachment=$("#attachment").val();
// 	var attachment="";
	var fileMid=$("#fileMid").val();
	var attas=attachment.split("#");
	var fmids=fileMid.split("#");
	//oAlert('11'+attas);
	if(attas!=''&&fmids!=''){
		for(var i=0;i<attas.length;i++){
			//oAlert(attas[i]+","+fmids[i]);
			if(attas[i]!=''&&fmids[i]!=''){
				var uid=new UUID().id;
			var html = '<div class="files_div" id="'+uid+'"><p class="filename2">'+attas[i]+'</p>'+
        	//' <div id="progress" class="progress progress-success progress-striped progress-div"><div class="bar bar-div" style="width:100%"></div></div>'+
        	"<div class='delete_Div'><input type='button' id='deleteE' class='delete_input' onclick=\"deleteExistFile('"+attas[i]+"','"+fmids[i]+"','"+uid+"');\" value='删除' /></div></div>";
           $("#files").append(html);
			}
			
		}
	}
	
   
	
}
//删除已存在的文件
function deleteExistFile(filename,fMid,uid){
	//oAlert('deletefile');
	var fid=uid;
	$("#"+fid).hide();
	var attachment=$("#attachment").val();
	var fileMid=$("#fileMid").val();
	var relfileMid="";
	var relattachment="";
	var attas=attachment.split("#");
	var fmids=fileMid.split("#");
	if(attas!=''&&fmids!=''){
		for(var i=0;i<attas.length;i++){
			if(attas[i]!=filename&&fMid!=fmids[i]){
				relfileMid+=fMid[i];
				relattachment+=attas[i];
			}
		}
	}
	$("#attachment").val(relattachment);
	$("#fileMid").val(relfileMid);
}

//转发
function fileSendOther(fileName,fileMid){
	//oAlert(fileName+","+fileMid);
	var files=fileName+","+fileMid;
	window.location.href=ctx+"/mail/file_send_other.action?files="+files;
}

//提示跳转
function toTip(inp){
	var word="";
	var to=$(inp).val();
	if(to!=''&&!endWith(to,";")){
		
		if(to.indexOf(";")<0){
			word=to;
		}else{
			word=to.substring(to.lastIndexOf(";")+1,to.length);
		}
		
	}
	return word;
}

//群邮件验证
function checkG(stype){
	//验证收件人 
	var re=/^[^\s()<>@,;:\/]+@\w[\w\.-]+\.[a-z]{2,}$/i;
	var to =$("#to").val();
	if(to==''||to=='undefined'){
		var ccs=to.split(";");
		for(var i=0;i<ccs.length;i++){
			if(ccs[i]!=''){
				if(ccs[i].indexOf("<")>=0){
					ccs[i]=ccs[i].substring(ccs[i].indexOf("<")+1,ccs[i].indexOf(">"));
				}
			if(re.test(ccs[i])==false){
				oAlert(global.toMailError);
				$("#to").focus();
				return false;
			}
			}
		}
	}

	var cc =$("#cc").val();
	if(cc!=''&&cc!='undefined'){
		var ccs=cc.split(";");
		for(var i=0;i<ccs.length;i++){
			if(ccs[i]!=''){
				if(ccs[i].indexOf("<")>=0){
					ccs[i]=ccs[i].substring(ccs[i].indexOf("<")+1,ccs[i].indexOf(">"));
				}
			if(re.test(ccs[i])==false){
				oAlert(global.ccMailError);
				$("#cc").focus();
				return false;
			}
			}
		}
	}
	
	var bcc =$("#bcc").val();
	if(bcc!=''&&bcc!='undefined'){
		var ccs=bcc.split(";");
		for(var i=0;i<ccs.length;i++){
			if(ccs[i]!=''){
				if(ccs[i].indexOf("<")>=0){
					ccs[i]=ccs[i].substring(ccs[i].indexOf("<")+1,ccs[i].indexOf(">"));
				}
			if(re.test(ccs[i])==false){
				oAlert(global.bccMailError);
				$("#bcc").focus();
				return false;
			}
			}
		}
	}
	
	//验证主题 
	var subject =$("#subject").val();
	if(subject==''||subject=='undefined'){
		oAlert(global.mailSubjectNotNull);
		$("#subject").focus();
		return false;
	}
	$("#isSend").val(stype);
	return true;
}

//联系人选择到收件人2
function setTo2(obj){
	var to=$("#to");
	var tos=to.val().split(";");
	tos[tos.length-1]=obj;
	var str='';
	for(var i=0;i<tos.length;i++){
			str+=tos[i]+";";			
	}
	to.val(str);
	
}






//全选
function selectall(){
	$(".subcheckbox").each(function(){
		if($('#mycheckbox').attr("checked")=='checked'){
		$(this).attr("checked",true);
		}else{
			$(this).attr("checked",false);
		}
	});
	return ;
}
//获得已选
function getSelected() {
	var selected = '';
	$(".subcheckbox").each(function() {
		if ($(this).attr("checked") == "checked") {
			selected += $(this).val() + "#";
		}
	});
	return selected;
}
//获得所有
function getAllSelected() {
	var selected = '';
	$(".subcheckbox").each(function() {
		selected += $(this).val() + "#";
	});
	return selected;
}
//endwith实现
function endWith(str, oString) {
	var reg = new RegExp(oString + "$");
	return reg.test(str);
}
//添加抄送
function addCc() {

	$("#addCc").hide();
	$("#delCc").show();
	$("#mcc").show();

}

	
//添加密送
function addBcc() {

	$("#addBcc").hide();
	$("#delBcc").show();
	$("#mbcc").show();

}
//删除抄送
function delCc() {

	$("#addCc").show();
	$("#delCc").hide();
	$("#mcc").hide();
	$("#cc").val("");
}
//删除密送
function delBcc() {

	$("#addBcc").show();
	$("#delBcc").hide();
	$("#mbcc").hide();
	$("#bcc").val("");

}
// 验证邮箱
function isEmail(str) {
	if (isEmpty(str))
		return false;
	var re = /^[^\s()<>@,;:\/]+@\w[\w\.-]+\.[a-z]{2,}$/i;
	return re.test(str);
}

//邮件删除
function deleInBox(pageNo,mtype,dtype) {
	var selected='';
	selected=getSelected();
	if (selected!='') {
		if(confirm(global.deleteConfirm)) {
				var mid = selected;
				$.ajax({
					type : "POST",
					url : ctx+"/mail/inbox_mail_remove.action",
					data : "mids="+selected+"&pageNo="+pageNo+"&mtype="+mtype+"&dtype="+dtype,
					dataType : "text",
					success : function callback(result) {
						window.location.href=ctx+"/mail/inbox_mail.action?pageNo="+pageNo+"&mtype="+mtype;
					}
				});
		}
	} else {
		oAlert(global.selectARecord);
	}
}
//邮件已读未读设置
function readInBox(rtype,pageNo,mtype){
	var selected='';
	if(rtype==0||rtype==1){
		selected=getAllSelected();
	}else{
		selected=getSelected();
	}
	if (selected!='') {
		if(confirm(global.operationConfirm)) {
			var mid = selected;
			$.ajax({
				type : "POST",
				url : ctx+"/mail/toReadOrNot.action",
				data : "pageNo="+pageNo+"&mtype="+mtype+"&rtype="+rtype+"&selected="+selected,
				dataType : "text",
				success : function callback(result) {
					window.location.href=ctx+"/mail/inbox_mail.action?pageNo="+pageNo+"&mtype="+mtype;
				}
			});
	}
		//window.location.href=ctx+"/mail/toReadOrNot.action?pageNo="+pageNo+"&mtype="+mtype+"&rtype="+rtype+"&selected="+selected;
	} else {
		oAlert(global.selectARecord);
	}
}
//邮件移到发件箱
function moveInBox(pageNo,mtype){
	var selected='';
	
		selected=getSelected();
		
		
	
	if (selected!=''||selected.indexOf("#")>-1) {
		if(confirm(global.operationConfirm)) {
			var mid = selected;
			$.ajax({
				type : "POST",
				url : ctx+"/mail/toMove.action",
				data : "pageNo="+pageNo+"&mtype="+mtype+"&selected="+selected,
				dataType : "text",
				success : function callback(result) {
					window.location.href=ctx+"/mail/inbox_mail.action?pageNo="+pageNo+"&mtype="+mtype;
				}
			});
	}
		//window.location.href=ctx+"/mail/toMove.action?pageNo="+pageNo+"&mtype="+mtype+"&selected="+selected;
	} else {
		oAlert(global.selectARecord);
	}
}
//邮件转发
function toSendOtherInBox(pageNo,mtype){
	var selected='';
	
	selected=getSelected();

	if (selected!=''&&selected.indexOf("#")==selected.length-1) {
				window.location.href=ctx+"/mail/toSendOther.action?mailname=1&rel=6&pageNo="+pageNo+"&mtype="+mtype+"&selected="+selected;

	//window.location.href=ctx+"/mail/toMove.action?pageNo="+pageNo+"&mtype="+mtype+"&selected="+selected;
} else {
	oAlert(global.selectOnlyOne);
}
	
}
//删除邮件
function deleInInBox(mids,mid,pageNo,mtype,dtype) {
	
	if(confirm(global.deleteConfirm)) {
			var m = mids+"@"+mid;
			$.ajax({
				type : "POST",
				url : ctx+"/mail/inbox_mail_remove.action",
				data : "mids="+m+"&pageNo="+pageNo+"&mtype="+mtype+"&dtype="+dtype,
				dataType : "text",
				success : function callback(result) {
					window.location.href=ctx+"/mail/inbox_mail.action?pageNo="+pageNo+"&mtype="+mtype;
				}
			});
	}
}
//邮件已读未读设置
function readInInBox(selected,mid,pageNo,mtype,rtype){
		$.ajax({
			type : "POST",
			url : ctx+"/mail/toReadOrNot.action",
			data : "pageNo="+pageNo+"&mtype="+mtype+"&rtype="+rtype+"&selected="+selected+"@"+mid,
			dataType : "text",
			success : function callback(result) {
				if(rtype==3){
					window.location.href=ctx+"/mail/inbox_mail.action?pageNo="+pageNo+"&mtype="+mtype;
				}
				//
			}
		});
}
//移到发件箱
function moveInInBox(selected,mid,pageNo,mtype){

		var mid = selected;
		$.ajax({
			type : "POST",
			url : ctx+"/mail/toMove.action",
			data : "pageNo="+pageNo+"&mtype="+mtype+"&selected="+selected+"@"+mid,
			dataType : "text",
			success : function callback(result) {
				window.location.href=ctx+"/mail/inbox_mail.action?pageNo="+pageNo+"&mtype="+mtype;
			}
		});

}
//下载
function downLoad(fileMid,filename){
$.ajax({
	type : "POST",
	url : ctx+"/mail/downLoad_file.action",
	data : "fileMid="+fileMid+"&filename="+filename,
	dataType : "text",
	success : function callback(result) {
		//window.location.href=ctx+"/mail/inbox_mail.action?pageNo="+pageNo+"&mtype="+mtype;
	}
});
}

//删除
function fdelete(){
	var fids=getAllChecked();
	if(fids==''){
		oAlert(global.selectARecord);
	}else{
		$.ajax({
			type : "POST",
			url : ctx+"/mail/file_del.action",
			data : "fuids="+fids+"&ftype=2",
			dataType : "text",
			success : function callback(result) {
				var ftype=$("#ftype").val();
				var findName=$("#findName").val();
				var curPage=$("#curPage").val();
				refreshWindow($(".label"));
				refreshWindow($(".writeMail"));
//				window.location.href=ctx+'/mail/file_list.action?curPage='+curPage+'&ftype='+ftype+"&findName="+findName;
			}
		});
	}
}


//转发
function toSendOtherMailBox(mailname,rel,pageNo,mtype){
	var selected='';
	
	selected=getSelected();
	if(mtype=='') mtype=0;
if (selected!=''&&selected.indexOf("#")==selected.length-1) {
				window.location.href=ctx+"/mail/mailbox_send_other.action?mailname="+mailname+"&rel="+rel+"&pageNo="+pageNo+"&mtype="+mtype+"&selected="+selected;

	//window.location.href=ctx+"/mail/toMove.action?pageNo="+pageNo+"&mtype="+mtype+"&selected="+selected;
} else {
	oAlert(global.selectOnlyOne);
}
	
}












//附件删除
function deleteFile(input){
	var s=input.parentNode.parentNode.rowIndex;
	$(input).parent().parent().remove();
	var files= $("#attachment").val();
	var filess=files.split("#");
	var f='';
	for(var i=0;i<filess.length;i++){
		if(i!=s&&filess[i]!='undefined'){
			f+=filess[i]+"#";
		}
	}
	f=f.substring(0,f.length-1);
	$("#attachment").val(f);
}

//群邮件验证
function checkG(ob){
	var re=/^[^\s()<>@,;:\/]+@\w[\w\.-]+\.[a-z]{2,}$/i;
	
	//验证抄送 
	var cc =$("#cc").val();
	if(cc!=''&&cc!='undefined'){
		var ccs=cc.split(";");
		for(var i=0;i<ccs.length;i++){
			if(css[i]!=''&&re.test(ccs[i])==false){
				oAlert(global.ccMailError);
				$("#cc").focus();
				return false;
			}
		}
	}
	//验证内容 
	var subject =$("#text").val();
	//oAlert($("#text").val());
	//oAlert($("#text").innerHTML);
	//oAlert(subject);
	//if(subject==''||subject=='undefined'){
		//oAlert("内容不能为空!");
		//$("#text").focus();
		//return false;
	//}
	
	return true;
	
}









//联系人选择到收件人
function setToMail(obj){
	var tar=$("#mtarget").val();
	var to;
	if(tar==''){
		to=$("#to");
	}else{
		to=$("#"+tar);
	}
	var tos=to.val().split(";");
	for(var i=0;i<tos.length;i++){
		if(tos[i]==obj){
			if(!endWith(to.val(),";")){
				if(to.val()==''){
					
				}else{
					to.val(to.val()+";");
				}
				
			}
			return ;
		}
	}
	if(endWith(to.val(),";")){
		to.val(to.val()+obj+";");
	}else{
		if(to.val()==''){
			to.val(obj+";");
		}else{
			to.val(to.val()+";"+obj+";");
		}
		
	}
}



//if(mails.indexOf(es[i])<0){
	//if(!endWith(mails,";")&&mails!=''){
	

	//选择人员
	function openUser(lname,lid){
		$.unit.open({
			title : global.selectUser,
			labelField : lname,
			valueField : lid,
			selectType : 4
		});
	}
	//获得用户名
function getuserids(ln,lln,ld){
	
	if(ld==''){
		return "";
	}
	
	var lns=ln.split(";");
	
	var llns=lln.split(";");
	var lds=ld.split(";");
	var ids="";
	
	for(var i=0;i<llns.length;i++){
		if(lns=='undefined'){
			ids+=lds[i];
		}else{
		for(var j=0;j<lns.length;j++){
			if(llns[i]==lns[j]){
				ids+=lds[i];
				break;
			}
		}
		}
	}
	return ids;
}



//重新选择
function rSelect(){
	$("#to").val($("#jto").val());
	$("#cc").val($("#jcc").val());
	$("#bcc").val($("#jbcc").val());
}
//
//function getAreaRange(obj) {  
//    var pos = new Object();  
//    var clone;  
//    if (isIE) {  
//        obj.focus();  
//        var range = document.selection.createRange();  
//         clone = range.duplicate();  
//  
//        clone.moveToElementText(obj);  
//        clone.setEndPoint( 'EndToEnd', range );  
//  
//        pos.start = clone.text.length - range.text.length;  
//        pos.end = clone.text.length - range.text.length + range.text.length;  
//    }else if(window.getSelection()) {  
//        pos.start = obj.selectionStart;  
//        pos.end = obj.selectionEnd;  
//    }  
//    var ed=obj.value.indexOf(";",pos.start);  
//    var st=obj.value.substring(0,ed).lastIndexOf(";")+1;  
//      
//    if (isIE) {  
//        clone.moveStart("character",st);  
//        clone.moveEnd( "character", ed-pos.end );  
//        clone.select();  
//    }else if(window.getSelection()) {  
//        obj.selectionStart=st;  
//        obj.selectionEnd=ed;  
//    }  
//  
//}  

//其他邮件
//添加其他邮件
function addOtherMail(){
	var content =getAddOtherMailHtml();
	
	 var json = new Object(); 
     json.content = content;
     json.title = "请填写您的其他邮箱地址";
     json.height= 191;
     json.width= 443;
     var buttons = new Object(); 
     buttons.下一步 = addOtherMail1;
     json.buttons = buttons;
     showDialog(json);
}
//下一步操作
function addOtherMail1(){
	var address = $("#othermail_address").val();
//	alert("其他邮件地址为："+address);
	$.ajax({
		type : "POST",
		url : ctx + "/mail/mailother_next.action",
		data : "address="+address,
		dataType : "text",
		success : function callback(result) {
			 closeDialog();
		     $("#20136315910846 div div").html(result);
//			 var json = new Object(); 
//		     json.title = "其他邮箱信息";
//		     json.height= 191;
//		     json.width= 443;
//		     $("#setMailDiv").html(result);
//		     $("#setMailDiv").oDialog(json);
		}
	});
}
//获得添加其他邮件页面html
function getAddOtherMailHtml(){
	var content="<div id='test_con3'>"
		+"<table>"
		+"<tr>";
		content+="<td align='left'>"+"<spring:message code='mail.label.addOtherMailN'/>"+"</td>";
		content+="</tr>"
		+"<tr>"
		+"<td><input type='text' name='address' size='15' id='othermail_address' /></td>"
		+"</tr>"
		+"</table>"
		+"</div>";
		return content;
}


//签名

function addSign(){
	 var json = new Object(); 
     json.content = getiSignHtml('','','',30);
     json.title = "新建个性签名";
     json.height= 430;
     json.width= 600;
     var buttons = new Object(); 
     buttons.确定 = signSubmit2;
     json.buttons = buttons;
     showDialog(json);

	showDialog(json);
	//showScheduleDialog(global.addSign,content,300,600);
//$.jBox('id:addSignDiv',{title:'新建个性签名',width:600,height:300,buttons: { '确定': 1, '取消': 0 },buttonsFocus: 1,submit:signSubmit});
}
//签名提交
function signSubmit(v, h, f){
	if (v == 1) {
		var signUuid=h.find("#signUuid").val();
		var signName=h.find("#signName").val();
		var signText=h.find("#signText").val();
		if(signName==''){
			oAlert(global.signNameNotNull);
			return false;
		}
		if(signText==''){
			oAlert(global.signContentNotNull);
			return false;
		}
		
		$.ajax({
			type : "POST",
			url : ctx+"/mail/save_sign.action",
			data : 'uuid='+signUuid+'&signName='+signName+'&signText='+signText,
			dataType : "text",
			success : function callback(result) {
				if(signUuid!=''){
					var ff="<tr>"+
			    	"<td width='100%'>"+
			    	"<div  style='width:100%;height:100px;border:1px solid blue;'>"+signText+"</div>"+
			    	"</td>"+
			    	"</tr>"+
			    	"<tr>"+
			    	"<td align='left'>"+
			    	"<button onclick=singEdit('"+signUuid+"','"+signName+"','"+signText+"')>"+global.editBtn+"</button><button onclick=signDel('"+signUuid+"')>"+global.deleteBtn+"</button>"+
			    	"</td>"+
			    	"</tr>";
					$("#"+signUuid).html(ff);
					//$("#sign option[value='"+signUuid+"']").remove();
				}else{
				signUuid=result;
				var s2="<table width='60%' class='showSignTable' id="+signUuid+">"+
		    	"<tr>"+
		    	"<td width='100%'>"+
		    	"<div  style='width:100%;height:100px;border:1px solid blue;'>"+signText+"</div>"+
		    	"</td>"+
		    	"</tr>"+
		    	"<tr>"+
		    	"<td align='left'>"+
		    	"<button onclick=singEdit('"+signUuid+"','"+signName+"','"+signText+"')>"+global.editBtn+"</button><button onclick=signDel('"+signUuid+"')>"+global.deleteBtn+"</button>"+
		    	"</td>"+
		    	"</tr>"+
		    	"</table>";
		    	$(".showSignTable").each(function(){
		    		$(this).hide();
		    	});
		    	var signHtml=$("#sign").html();
				var newSign="<option value='"+signUuid+"'>"+signName+"</option>";
				$("#sign").html(signHtml+newSign);
		    	var s1=$("#addSignLocation").html();
		    	$("#addSignLocation").html(s1+s2);
		    	//var checkIndex=$("#sign").get(0).selectedIndex;
		    	//$("#sign option[index='"+checkIndex+"']").attr("selected", false);
		    	$("#sign").val(signUuid);
				}
			}
		});
        
        
    }
    return true;

}
//签名提交2
function signSubmit2(){
		var signUuid=$("#signUuid").val();
		var signName=$("#signName").val();
		var signText=$("#signText").val();
		if(signName==''){
			oAlert(global.signNameNotNull);
			return false;
		}
		if(signText==''){
			oAlert(global.signContentNotNull);
			return false;
		}
		
		$.ajax({
			type : "POST",
			url : ctx+"/mail/save_sign.action",
			data : 'uuid='+signUuid+'&signName='+signName+'&signText='+signText,
			dataType : "text",
			success : function callback(result) {
				if(signUuid!=''){
					var ff="<tr>"+
			    	"<td width='100%'>"+
			    	"<div  style='width:100%;height:100px;border:1px solid blue;'>"+signText+"</div>"+
			    	"</td>"+
			    	"</tr>"+
			    	"<tr>"+
			    	"<td align='left'>"+
			    	"<button onclick=singEdit('"+signUuid+"','"+signName+"','"+signText+"')>"+global.editBtn+"</button><button onclick=signDel('"+signUuid+"')>"+global.deleteBtn+"</button>"+
			    	"</td>"+
			    	"</tr>";
					$("#"+signUuid).html(ff);
					//$("#sign option[value='"+signUuid+"']").remove();
				}else{
				signUuid=result;
				var s2="<table width='60%' class='showSignTable' id="+signUuid+">"+
		    	"<tr>"+
		    	"<td width='100%'>"+
		    	"<div  style='width:100%;height:100px;border:1px solid blue;'>"+signText+"</div>"+
		    	"</td>"+
		    	"</tr>"+
		    	"<tr>"+
		    	"<td align='left'>"+
		    	"<button onclick=singEdit('"+signUuid+"','"+signName+"','"+signText+"')>"+global.editBtn+"</button><button onclick=signDel('"+signUuid+"')>"+global.deleteBtn+"</button>"+
		    	"</td>"+
		    	"</tr>"+
		    	"</table>";
		    	$(".showSignTable").each(function(){
		    		$(this).hide();
		    	});
		    	var signHtml=$("#sign").html();
				var newSign="<option value='"+signUuid+"'>"+signName+"</option>";
				$("#sign").html(signHtml+newSign);
		    	var s1=$("#addSignLocation").html();
		    	$("#addSignLocation").html(s1+s2);
		    	//var checkIndex=$("#sign").get(0).selectedIndex;
		    	//$("#sign option[index='"+checkIndex+"']").attr("selected", false);
		    	$("#sign").val(signUuid);
				}
				closeDialog();
			}
		});
}
//签名变更
function signChange(){
	var sign=$("#sign").val();
	if(sign==''){
		$(".showSignTable").each(function(){
    		$(this).hide();
    	});
	}else{
		$(".showSignTable").each(function(){
    		if($(this).attr("id")==sign){
    			$(this).show();
    		}else{
    			$(this).hide();
    		}
    	});
	}
}
//修改签名
function singEdit(uuid,signName,signText){
	
	 var json = new Object(); 
     json.content = getiSignHtml(uuid,signName,signText,30);
     json.title = "编辑个性签名";
     json.height= 430;
     json.width= 600;
     var buttons = new Object(); 
     buttons.确定 = signSubmit2;
     json.buttons = buttons;
     showDialog(json);

     
//     var content = getiSignHtml(uuid,signName,signText,30);
// 	var title=global.editSign;
	//showScheduleDialog(global.editSign,content,300,600);
	//$.jBox('id:addSignDiv',{title:'新建个性签名',width:600,height:300,buttons: { '确定': 1, '取消': 0 },buttonsFocus: 1,submit:signSubmit});
}
//删除签名
function signDel(uuid){
	$.ajax({
		type : "POST",
		url : ctx+"/mail/del_sign.action",
		data : 'uuid='+uuid,
		dataType : "text",
		success : function callback(result) {
			$("#sign").val("");
			$("#"+uuid).hide();
			jQuery("#sign option[value='"+uuid+"']").remove(); 
			oAlert(result);
			
		}
	});
}
//获得签名HTML
function getiSignHtml(uuid,signName,signText,ddays){
	var content="<div id='test_con33'>"
		+"<input type='hidden' id='signUuid' name='signUuid' value='"+uuid+"'/>"
		+"<table class='tag_table' style='display: block;margin-left: 25px;width: 94%;'>"
		+"<tr class='signName_tr'>"
		+"<td>";
		if(signName==''){
		content+="<input id='signName' name='signName' value='"+global.sign1+"' type='text'/>";
		}else{
		content+="<input id='signName' name='signName' value='"+signName+"' type='text'/>";
		}
		content+="</td>"
		+"</tr>"
		+"<tr  class='signText_tr'>"
		+"<td>"
		+"<textarea  id='signText' name='signText'  class='ckeditor' >"+signText+"</textarea>"
		+"</td>"
		+"</tr>"
		+"</table>"
		//+"<center><button onclick='signSubmit2();'>"+global.confirm+"</button><button onclick='return closeDialog();'>"+global.cancel+"</button></center>"
		+"</div>"
		+"</div>"
		+"</div>"
		+"<div id='alterDayDiv' style='display: none;'>"
		+"<input type='hidden' id='wstatus'/>"
		+"<table>"
		+"<tr>"
		+"<td>"
		+global.autoClearTime
		+"</td>"
		+"</tr>"
		+"<tr>"
		+"<td>";
		if(ddays==30){
		content+="<input id='ddays'  name='ddays' checked='checked' class='ddays' type='radio' value='30'/>30"+global.dayDefault;
		}else{
			content+="<input id='ddays'  name='ddays' class='ddays' type='radio' value='30'/>30"+global.dayDefault;
		}
		content+="</td>"
		+"</tr>"
		+"<tr>"
		+"<td>";
		if(ddays==14){
			content+="<input id='ddays'  name='ddays' checked='checked' class='ddays' type='radio' value='14'/>14"+global.day;
			}else{
				content+="<input id='ddays'  name='ddays' class='ddays' type='radio' value='14'/>14"+global.day;
			}
		content+="</td>"
		+"</tr>"
		+"<tr>"
		+"<td>";
		if(ddays==7){
			content+="<input id='ddays'  name='ddays' checked='checked' class='ddays' type='radio' value='7'/>7"+global.day;
			}else{
				content+="<input id='ddays'  name='ddays' class='ddays' type='radio' value='7'/>7"+global.day;
			}
		content+="</td>"
		+"</tr>"
		+"<tr>"
		+"<td>";
		if(ddays==0){
			content+="<input id='ddays'  name='ddays' checked='checked' class='ddays' type='radio' value='0'/>"+global.logoutAutoClear;
			}else{
				content+="<input id='ddays'  name='ddays' class='ddays' type='radio' value='0'/>"+global.logoutAutoClear;
			}
		content+="</td>"
		+"</tr>"
		+"</table>"
		+"</div>";
	
	/*var content="<div id='test_con33'>"
		+"<input type='hidden' id='signUuid' name='signUuid' value='"+uuid+"'/>"
		+"<div id='dialog_form'>"
		+"<div class='dialog_form_content'>"
		+"<table width='100%'>" +
		"<tbody>"+
		+"<tr class='odd'>"
		+"<td class='value'>"
		+"<div class='td_class'>";
		if(signName==''){
			content+="<input id='signName' name='signName' value='"+global.sign1+"' type='text'/>"+"</div>";
		}else{
			content+="<input id='signName' name='signName' value='"+signName+"' type='text'/>"+"</div>";
		}
			content+=+"</div>"+"</td>"
		+"</tr>"
		+"<tr>"
		+"<td class='value'>"
		+"<div class='td_class'>"
		+"<textarea  id='signText' name='signText'  class='ckeditor' >"+signText+"</textarea>"
		+"</div>"
		+"</td>"
		+"</tr>"
		+"</tbody>"
		+"</table>"
		+"</div>"
		+"</div>"
		//+"<center><button onclick='signSubmit2();'>"+global.confirm+"</button><button onclick='return closeDialog();'>"+global.cancel+"</button></center>"
		+"</div>"
		+"</div>"
		+"</div>"
		+"<div id='alterDayDiv' style='display: none;'>"
		+"<input type='hidden' id='wstatus'/>"
		+"<table>"
		+"<tr>"
		+"<td>"
		+global.autoClearTime
		+"</td>"
		+"</tr>"
		+"<tr>"
		+"<td>";
		if(ddays==30){
		content+="<input id='ddays'  name='ddays' checked='checked' class='ddays' type='radio' value='30'/>30"+global.dayDefault;
		}else{
			content+="<input id='ddays'  name='ddays' class='ddays' type='radio' value='30'/>30"+global.dayDefault;
		}
		content+="</td>"
		+"</tr>"
		+"<tr>"
		+"<td>";
		if(ddays==14){
			content+="<input id='ddays'  name='ddays' checked='checked' class='ddays' type='radio' value='14'/>14"+global.day;
			}else{
				content+="<input id='ddays'  name='ddays' class='ddays' type='radio' value='14'/>14"+global.day;
			}
		content+="</td>"
		+"</tr>"
		+"<tr>"
		+"<td>";
		if(ddays==7){
			content+="<input id='ddays'  name='ddays' checked='checked' class='ddays' type='radio' value='7'/>7"+global.day;
			}else{
				content+="<input id='ddays'  name='ddays' class='ddays' type='radio' value='7'/>7"+global.day;
			}
		content+="</td>"
		+"</tr>"
		+"<tr>"
		+"<td>";
		if(ddays==0){
			content+="<input id='ddays'  name='ddays' checked='checked' class='ddays' type='radio' value='0'/>"+global.logoutAutoClear;
			}else{
				content+="<input id='ddays'  name='ddays' class='ddays' type='radio' value='0'/>"+global.logoutAutoClear;
			}
		content+="</td>"
		+"</tr>"
		+"</table>"
		+"</div>";*/
		return content;
}

//文件夹
function managedFolder(){
	var fname=$("#fname").val();
	var uuid=$("#uuid").val();
	var status=$("#status").val();
	if(fname==''){
		if(status=='0'){
			oAlert(global.folderNameNotNull);
			}else{
				oAlert(global.labelNameNotNull);
			}
		fname.focus();
		return false;
	}
	$.ajax({
		type : "POST",
		url : ctx+"/mail/add_folder.action",
		data : "uuid="+uuid+"&fname="+fname+"&status="+status,
		dataType : "text",
		success : function callback(result) {
			if(result.indexOf("error")>-1){
				if(status=='0'){
				oAlert(global.folderNameIsExist);
				}else{
					oAlert(global.labelNameIsExist);
				}
			}else{
				closeDialog();
				uuid=$("#uuid").val();
				if(uuid!=''&&uuid!='undefined'&&uuid!=null){
					var f="";
					if(status=='0'){
						f=uuid+"name";
						$("#"+f).html(fname);
						
//						//查找对应文件夹
						JDS.call({
							service : "mailBoxService.getLabelByUuid",
							data : [ uuid ],
							async: false,
							success : function(result1) {
								var labelname = result1.data.fname;
								var labelUuid = result1.data.uuid;
								
								//修改左侧导航文件夹名
								$(".folder.liveData").each(function(){
									if($(this).attr("folderid") == labelUuid){
										$(this).attr("foldername",labelname);
										$(this).attr("name_",labelname);
										$(this).text(labelname);
									}
								});
								
								$(".mail_div  ul li").click(function(){
									$(".mail_div  ul li").removeClass('active');
									if($(this).attr("id")!="mytag"&&$(this).attr("id")!="othermail"&&$(this).attr("id")!="myfile"){
										$(this).addClass('active');
									}
								});
							}
						});
					}else{
						f=uuid+"name2";
						$("#"+f).find("font").text(fname);
						//查找对应标签
						JDS.call({
							service : "mailBoxService.getLabelByUuid",
							data : [ uuid ],
							async: false,
							success : function(result1) {
								var labelname = result1.data.fname;
								var labelUuid = result1.data.uuid;
								
								//修改左侧导航标签名
								$(".mlabel.liveData").each(function(){
									if($(this).attr("labelid") == labelUuid){
										$(this).attr("labelname",labelname);
										$(this).attr("name_",labelname);
										$(this).find("span").eq(1).text(labelname);
									}
								});
								
								$(".mail_div  ul li").click(function(){
									$(".mail_div  ul li").removeClass('active');
									if($(this).attr("id")!="mytag"&&$(this).attr("id")!="othermail"&&$(this).attr("id")!="myfile"){
										$(this).addClass('active');
									}
								});
							}
						});
					}
				
				}else{
					var uuidss=result;
					if(status=='0'){
						var tr="<tr id=\""+uuidss+"tr\" class=\"folder_content\">"+
						"<td id="+uuidss+"name2>"+fname+"</td>"+
						"<td id="+uuidss+"noread>0</td>"+
						"<td id="+uuidss+"total>0</td>"+
						"<td><a href='#' onclick=\"openFolder('"+fname+"','"+uuidss+"','"+status+"');\">"+global.modifyName+"</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
						tr+="<a href='#' onclick=\"clearFolder('"+uuidss+"');\">"+global.clear+"</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
						tr+="<a href='#' onclick=\"deleteFolder('"+uuidss+"','"+fname+"','"+status+"');\">"+global.deleteBtn+"</a></td>"+
						"</tr>";
						$("#ftr").after(tr);
						
						//查找对应文件夹
						JDS.call({
							service : "mailBoxService.getLabelByUuid",
							data : [ result ],
							async: false,
							success : function(result1) {
								var labelname = result1.data.fname;
								var labelUuid = result1.data.uuid;
								//添加至下拉文件夹列表
								$(".folder.liveData").each(function(){
									var str_folder = '<div class="customButton_group_button" onclick="moveMailBoxCms(\'0\',\'1\',\'0\',\''+labelUuid+'\',\'\',\'customButton_group_button\');"> '+labelname+' </div>';
									$(".customButton_group_buttons").eq(1).find(".customButton_group_button").last().before(str_folder);
								});
								//添加至左侧导航菜单文件夹
								var str_left_folder = '<li id="folder" class="folder liveData" foldername="'+labelname+'" folderid="'+labelUuid+'" url="/basicdata/dyview/view_show?viewUuid=1cb04818-9156-4ccf-aee9-0af292065563&status_='+labelUuid+'" name_="'+labelname+'" style="display: list-item;">'+labelname+'</li>';
								$(".folder.liveData").first().before(str_left_folder);
								
								$(".mail_div  ul li").click(function(){
									$(".mail_div  ul li").removeClass('active');
									if($(this).attr("id")!="mytag"&&$(this).attr("id")!="othermail"&&$(this).attr("id")!="myfile"){
										$(this).addClass('active');
									}
								});
							}
						});
					}else{
						var uids=uuidss.split("|#|");
						var uuid=uids[0];
						var tr="<tr id=\""+uuid+"tr2\" class=\"folder_content\">"+
						
						"<td id='"+uuid+"name2'><span id='"+uuid+"name22' style='background-color:"+uids[1]+";display: block;float: left;height: 10px;margin-left: 10px;margin-top: 4px;width: 10px; border-radius: 4px 4px 4px 4px;' onclick=\"selLabelColor('"+uids[0]+"');\"></span><font color="+uids[1]+">"+fname+"</font></td>"+
						"<td id="+uuid+"noread2>0</td>"+
						"<td id="+uuid+"total2>0</td>"+
						"<td><a href='#' onclick=\"openFolder('"+fname+"','"+uuid+"','"+status+"');\">"+global.modifyName+"</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
						tr+="<a href='#' onclick=\"deleteFolder('"+uuid+"','"+fname+"','"+status+"');\">"+global.deleteBtn+"</a></td>"+
						"</tr>";
						$("#ftr2").after(tr);
							//var uu=uuid+"name22";
							//oAlert(uids[1]);
							//$("#"+uu).css('background-color',uids[1]); 
						
						
						
						//查找对应标签
						JDS.call({
							service : "mailBoxService.getLabelByUuid",
							data : [ result ],
							async: false,
							success : function(result1) {
								var labelcolor = result1.data.color;
								var labelname = result1.data.fname;
								var labelUuid = result1.data.uuid;
								//添加至下拉标签
								$(".mlabel.liveData").each(function(){
									var str_mlabel = '<div class="customButton_group_button" onclick="setLabelCms(\''+labelUuid+'\',\''+labelname+'\',\''+labelcolor+'\',\'1\',\'customButton_group_button\');"> '+labelname+' </div>';
									$(".customButton_group_buttons").eq(0).find(".customButton_group_button").last().before(str_mlabel);
								});
								//添加至左侧导航菜单标签
								var str_left_label = '<li id="mlabel" class="mlabel liveData" labelcolor="'+labelcolor+'" labelname="'+labelname+'" labelid="'+labelUuid+'" url="/basicdata/dyview/view_show?viewUuid=2f5b414a-175e-4754-871b-e233e6c5dff8&status_='+labelUuid+'" name_="'+labelname+'" style="display: list-item;">'+
								'<span style="cursor: pointer;background-color: '+labelcolor+';display:block;width:10px;height:10px;border-radius: 4px 4px 4px 4px;float: left;margin: 8px 5px 0 20px;"></span>'+
								'<span style="color:'+labelcolor+'; ">'+labelname+'</span></li>';
								$(".mlabel.liveData").first().before(str_left_label);
								
								$(".mail_div  ul li").click(function(){
									$(".mail_div  ul li").removeClass('active');
									if($(this).attr("id")!="mytag"&&$(this).attr("id")!="othermail"&&$(this).attr("id")!="myfile"){
										$(this).addClass('active');
									}
								});
							}
						});
					}
				}
			}
			if(status=='0'){
				oAlert("文件夹创建成功！");
			}else{
				oAlert("标签创建成功！");
			}
		}
	});
}

//清空文件夹
function clearFolder(uuid){
	var obj = new Object(); 
	obj.uuid = uuid;
	oConfirm (global.clearConfirm,clearFolder1,obj,"");
	
}
function clearFolder1(obj){
	var uuid = obj.uuid;
	 $.ajax({
			type : "POST",
			url : ctx+"/mail/clear_folder.action",
			data : "uuid="+uuid,
			dataType : "text",
			success : function callback(result) {
				if(result.indexOf("error")>-1){
					oAlert("error!");
				}else{
				var f=uuid+"noread";
				$("#"+f).html("0");
				var ff=uuid+"total";
				$("#"+ff).html("0");
					oAlert(global.success);
				}
			}
		});
}

/*//清空文件夹
function clearFolder(uuid){
 if(confirm(global.clearConfirm)) {
 $.ajax({
		type : "POST",
		url : ctx+"/mail/clear_folder.action",
		data : "uuid="+uuid,
		dataType : "text",
		success : function callback(result) {
			if(result.indexOf("error")>-1){
				oAlert("error!");
			}else{
			var f=uuid+"noread";
			$("#"+f).html("0");
			var ff=uuid+"total";
			$("#"+ff).html("0");
			oAlert(global.success);
			}
		}
	});
 }
}*/

//日期变更
function alterDay(day,status){
	
//$.jBox('id:alterDayDiv',{title:global.sysAutoClearTime,width:400,height:200,buttons: { '是': 1, '否': 0 },buttonsFocus: 1,submit:daySubmit});
	var json = new Object(); 
     json.content = $("#alterDayDiv").html();
     json.title = global.sysAutoClearTime;
     json.height= 230;
     json.width= 443;
     var buttons = new Object(); 
     buttons.确定 = daySubmit;
     json.buttons = buttons;
     showDialog(json);
     
     $("#wstatus").val(status);
		$(".ddays").each(function (){
			if($(this).val()==day){
				$(this).attr("checked",true);
			}
		});
}

function daySubmit(){
//var h = $("#alterDayDiv");
//if (v == 1) {
var day='0';
var wstatus=$("#wstatus").val();
$(".ddays").each(function(){
	if($(this).attr("checked")=="checked"){
		day=$(this).val();
	}
});
$.ajax({
	type : "POST",
	url : ctx+"/mail/set_clean_day.action",
	data : "status="+wstatus+'&days='+day,
	dataType : "text",
	success : function callback(result) {
		var s=day+global.day+"|<a href='#' onclick=alterDay('"+day+"','"+wstatus+"')>"+global.modify+"</a>";
		$("#"+wstatus+"label").html(s);
		oAlert(result);
		closeDialog();
	}
});
//}
return true;
}

//删除文件夹或标签
function deleteFolder(uuid,fname,status){
	var obj = new Object(); 
	obj.uuid = uuid;
	obj.fname = fname;
	obj.status = status;
	oConfirm (global.deleteConfirm,deleteFolder1,obj,"");
}
function deleteFolder1(obj){
	var uuid = obj.uuid;
	var fname = obj.fname;
	var status = obj.status;
	
	//查找导航及下拉菜单对应标签并删除
	JDS.call({
		service : "mailBoxService.getLabelByUuid",
		data : [ uuid ],
		async: false,
		success : function(result1) {
			var color1 = result1.data.color;
			var fname1 = result1.data.fname;
			var uuid1 = result1.data.uuid;
			//看我的标签是不是存在子项
			if($(".mlabel.liveData").length>1){
				//从左侧导航菜单标签中移除
				$(".mlabel.liveData").each(function(){
					if($(this).attr('labelid')==uuid1){
						$(this).remove();
					}
				});
				//从下拉标签中移除
				$(".customButton_group_button").each(function(){
					if($(this).text()== fname1){
						$(this).remove();
					}
				});
			} else {
				//从左侧导航菜单标签中移除
				$(".mlabel.liveData").each(function(){
					if($(this).attr('labelid')==uuid1){
						$(this).remove();
					}
				});
				//从下拉标签中移除
				$(".customButton_group_button").each(function(){
					if($(this).text()== fname1){
						$(this).remove();
					}
				});
				$("#mytag").remove();//删除“我的标签”
			}
			
			$(".mail_div  ul li").click(function(){
				$(".mail_div  ul li").removeClass('active');
				if($(this).attr("id")!="mytag"&&$(this).attr("id")!="othermail"&&$(this).attr("id")!="myfile"){
					$(this).addClass('active');
				}
			});
		}
	});
	//删除标签
	$.ajax({
		type : "POST",
		url : ctx+"/mail/del_folder.action",
		data : "uuid="+uuid,
		dataType : "text",
		success : function callback(result) {
			if(result.indexOf("error")>-1){
				if(status=='0'){
					oAlert(global.folderExistMail);
				}else{
					oAlert(global.labelExistMail);
				}
			}else{
				var f='';
				if(status=='0'){
					f=uuid+"tr";
					
					//看我的文件夹下是不是存在子项
					if($(".folder.liveData").length>1){
						//从左侧导航菜单文件夹中移除
						$(".folder.liveData").each(function(){
							if($(this).attr('folderid')==uuid){
								$(this).remove();
							}
						});
						//从下拉文件夹中移除
						$(".customButton_group_button").each(function(){
							if($(this).text()== fname){
								$(this).remove();
							}
						});
					} else {
						//从左侧导航菜单文件夹中移除
						$(".folder.liveData").each(function(){
							if($(this).attr('folderid')==uuid){
								$(this).remove();
							}
						});
						//从下拉文件夹中移除
						$(".customButton_group_button").each(function(){
							if($(this).text()== fname){
								$(this).remove();
							}
						});
						$("#myfile").remove();//删除“我的文件夹”
					}
				}else{
					f=uuid+"tr2";
				}
				$("#"+f).hide();
				oAlert(global.success);
				$("#"+f).remove();
			}
		}
	});
}

/*//删除文件夹
function deleteFolder(uuid,status){
 if(confirm(global.deleteConfirm)) {
 $.ajax({
		type : "POST",
		url : ctx+"/mail/del_folder.action",
		data : "uuid="+uuid,
		dataType : "text",
		success : function callback(result) {
			if(result.indexOf("error")>-1){
				if(status=='0'){
				oAlert(global.folderExistMail);
				}else{
					oAlert(global.labelExistMail);
				}
			}else{
				var f='';
				if(status=='0'){
					f=uuid+"tr";
				}else{
					f=uuid+"tr2";
				}
			
			$("#"+f).hide();
			oAlert(global.success);
			}
		}
	});
 }
}*/
//新建文件夹或标签
function openNewFileFox(status){
	var content =getFileHtml('','',status);
	var title='';
	if(status=='0'){
		title=global.addFolder;
	}else{
		title=global.addLabel;
	}
	
	 var json = new Object(); 
     json.content = content;
     json.title = title;
     json.height= 191;
     json.width= 443;
     var buttons = new Object(); 
     buttons.确定 = managedFolder;
     json.buttons = buttons;
     showDialog(json);

	//showScheduleDialog(title,content,300,600);
}

//修改文件夹
function openFolder(fname,uuid,status){
	var content =getFileHtml(uuid,fname,status);
	var title='';
	if(status=='0'){
		title=global.editFolder;
	}else{
		title=global.editLabel;
	}
	
	 var json = new Object(); 
     json.content = content;
     json.title = title;
     json.height= 191;
     json.width= 443;
     var buttons = new Object(); 
     buttons.确定 = managedFolder;
     json.buttons = buttons;
     showDialog(json);

	//showScheduleDialog(title,content,300,600);
}
//获得文件夹
	function getFileHtml(uuid,fname,status,type){
		var content="<div id='test_con3'>"
			+"<input type='hidden' id='uuid' name='uuid' value='"+uuid+"' />"
			+"<input type='hidden' id='status' name='status' value='"+status+"' />"
			+"<table>"
			+"<tr>";
			if(status=='0'){
			content+="<td align='left' >"+global.folderNameMust+"</td>";
			}else{
				content+="<td align='left' >"+global.labelNameMust+"</td>";
			}
			content+="</tr>"
			+"<tr>"
			+"<td><input type='text' id='fname' value='"+fname+"' name='fname' size='50' style='width:88%;'/></td>"
			+"</tr>"
			+"</table>"
			//+"<div align='center' style=''>"
			//+"<table><tr><td>"
			//+"<button onclick=\"managedFolder();\">"+global.confirm+"</button></td><td>"
			//+"<button onclick='return closeDialog();'>"+global.cancel+"</button></td><td>"
			//+"</td></tr></table>"
			//+"</div>"
			+"</div>";
			return content;
	}
	
	
//	//弹出DIALOG窗口
//	function showScheduleDialog (title,content,height,width){
//		$("#dialogModule").dialog({
//			title:title,
//			autoOpen: true,
//			height: height,
//			width: width,
//			modal: true,
//			open:function() {
//				$(".ui-widget-overlay").css("background", "#000");
//				$(".ui-widget-overlay").css("opacity", "0.5");
//				if(content!=""){
//					$(".dialogcontent").html(content);
//				}
//			}
//		});	
//	}
	//关闭DIALOG窗口
//	function closeDialog(){
//		$("#dialogModule").dialog("close");
//	}
	//个人邮件设置
	function replyTextclick(){
		oAlert('reptext');
		$('#reply').attr('checked',true);
	}
	//回复内容点击
	function replyTextclick2(){
		var text=CKEDITOR.instances.replytext.getData();
		if(text==''){
			$('#reply').attr('checked',false);
		}
		
	}

	
//	//清空系统文件夹
//	function clearMailBox2(status) {
//			if(confirm(global.clearConfirm)) {
//					$.ajax({
//						type : "POST",
//						url : ctx+"/mail/mail_box_clear2.action",
//						data : "status="+status,
//						dataType : "text",
//						success : function callback(result) {
//							oAlert(result);
//							$("#"+status+"hassend").html("0");
//							$("#"+status+"hasdel").html("0");
//						}
//					});
//			}
//	}
	//清空系统文件夹
	function clearMailBox2(status){
		var obj = new Object(); 
		obj.status = status;
		oConfirm (global.clearConfirm,clearMailBox21,obj,"");
	}
	function clearMailBox21(obj){
		var status = obj.status;
		$.ajax({
			type : "POST",
			url : ctx+"/mail/mail_box_clear2.action",
			data : "status="+status,
			dataType : "text",
			success : function callback(result) {
				oAlert(result);
				$("#"+status+"hassend").html("0");
				$("#"+status+"hasdel").html("0");
			}
		});
	}
	

//删除规则
	function delRule(uuid){
		$.ajax({
			type : "POST",
			url : ctx+"/mail/del_rule.action",
			data : 'uuid='+uuid,
			dataType : "text",
			success : function callback(result) {
				$("#"+uuid+"tr").hide();
				oAlert(result);
				
			}
		});
	}

	

//发送其他点击
	function sendOther2Click(){
		$("#sendOther1").attr("checked",true);
		$("#showSendOther").show();
	}

	//发送其他点击2
	function sendOther2Click2(){
		var sendOther2=$("#sendOther2").val();
		if(sendOther2!=''){
			var re=/^[^\s()<>@,;:\/]+@\w[\w\.-]+\.[a-z]{2,}$/i;
			if(re.test(sendOther2)==false){
				oAlert(global.mailAddressError);
				$("#sendOther2").focus();
				return false;
			}
			$("#sendOther1").attr("checked",true);
			$("#showSendOther").show();
		}else{
			$("#sendOther1").attr("checked",false);
			$("#showSendOther").hide();
		}
	}
	//是否能修改
	function opeClick(type){
		if(type==1){
			$("#moveFile1").attr("disabled",false);
			$("#moveFile2").attr("disabled",false);
			$("#setRead").attr("disabled",false);
			$("#setStar").attr("disabled",false);
			$("#sendOther1").attr("disabled",false);
			$("#sendOther2").attr("disabled",false);
			$("#sendOther3").attr("disabled",false);
			$("#reply").attr("disabled",false);
			$("#replytext").attr("disabled",false);
		}else{
			$("#moveFile1").attr("disabled",true);
			$("#moveFile2").attr("disabled",true);
			$("#setRead").attr("disabled",true);
			$("#setStar").attr("disabled",true);
			$("#sendOther1").attr("disabled",true);
			$("#sendOther2").attr("disabled",true);
			$("#sendOther3").attr("disabled",true);
			$("#reply").attr("disabled",true);
			$("#replytext").attr("disabled",true);
		}
	}
	//是否启用
	function ifQiyong(uuid,type){
		$.ajax({
			type : "POST",
			url : ctx+"/mail/set_rule.action",
			data : 'uuid='+uuid+'&ctype='+type,
			dataType : "text",
			success : function callback(result) {
				if(type==1){
					var s=global.hasQiyong+"|<a href='#' onclick=ifQiyong('"+uuid+"',2)>"+global.close+"</a>";
					$("#"+uuid).html(s);
				}else{
					var s=global.hasClose+"|<a href='#' onclick=ifQiyong('"+uuid+"',1)>"+qiyong+"</a>";
					$("#"+uuid).html(s);
				}
			}
		});
	}
// 控制输入
	function inputkeypress(inputobj){
		if(!inputobj.value.match(/^\d*?$/))
		inputobj.value=inputobj.t_value;
		else
		inputobj.t_value=inputobj.value;
		if(inputobj.value.match(/^(?:\d+(?:\.\d+)?)?$/))
		inputobj.o_value=inputobj.value;
		if(/\.\d\d$/.test(inputobj.value))event.returnValue=false;
		}
		function inputkeyup(inputobj){
		if(!inputobj.value.match(/^\d*?$/))
		inputobj.value=inputobj.t_value;
		else
		inputobj.t_value=inputobj.value;
		if(inputobj.value.match(/^(?:\d?)?$/))
		inputobj.o_value=inputobj.value;
		}
		function inputblur(inputobj){
		if(!inputobj.value.match(/^(?:\d?)?$/))
		inputobj.value=inputobj.o_value;
		else{
		if(inputobj.value.match(/^\.\d+$/))
		inputobj.value=0+inputobj.value;
		if(inputobj.value.match(/^\.$/))
		inputobj.value=0;
		inputobj.o_value=inputobj.value;
		}
		}
		
		//修改规则
		function editRule(uuid,qiyong,sendPerson1,sendPerson2,sendPerson3,sendDomain1,sendDomain2,sendDomain3,receivePerson1,receivePerson2,receivePerson3,title1,title2,title3,mailSize1,mailSize2,mailSize3,receiveTime1,receiveTime2,receiveTime3,operation,moveFile1,moveFile2,setRead,setStar,sendOther1,sendOther2,sendOther3,reply,replytext){
			createGz();
			$("#guuid").val(uuid);
			$("#qiyong").val(qiyong);
			if(sendPerson1=='1')
				$("#sendPerson1").attr("checked",true);
			
			
			$("#sendPerson2").val(sendPerson2);
			$("#sendPerson3").val(sendPerson3);
			if(sendDomain1=='1')
			$("#sendDomain1").attr("checked",true);
			
			$("#sendDomain2").val(sendDomain2);
			$("#sendDomain3").val(sendDomain3);
			
			
			if(receivePerson1=='1')
			$("#receivePerson1").attr("checked",true);
			
			$("#receivePerson2").val(receivePerson2);
			$("#receivePerson3").val(receivePerson3);
			
			if(title1=='1')
			$("#title1").attr("checked",true);
			
			$("#title2").val(title2);
			$("#title3").val(title3);
			if(mailSize1=='1')
			$("#mailSize1").attr("checked",true);
			
			$("#mailSize2").val(mailSize2);
			$("#mailSize3").val(mailSize3);
			if(receiveTime1=='1')
			$("#receiveTime1").attr("checked",true);
			
			$("#receiveTime2").val(receiveTime2);
			$("#receiveTime3").val(receiveTime3);
			
			
			$(".operation").each(function(){
				if($(this).val()==operation){
					$(this).attr("checked",true);
				}
			});
			
			 if(moveFile1=='1')
			 $("#moveFile1").attr("checked",true);
			 
			$("#moveFile2").val(moveFile2);
			if(setRead=='1')
			$("#setRead").attr("checked",true);
			if(setStar=='1')
			$("#setStar").attr("checked",true);
			if(sendOther1=='1')
			$("#sendOther1").attr("checked",true);
			$("#sendOther2").val(sendOther2);
			$("#sendOther3").val(sendOther3);
			if(reply=='1')
			 $("#reply").attr("checked",true);
			$("#replytext").val(replytext);
			if(sendOther1=='1'){
				$("#showSendOther").show();
			}
			 if(operation==1){
			$("#moveFile1").attr("disabled",false);
			$("#moveFile2").attr("disabled",false);
			$("#setRead").attr("disabled",false);
			$("#setStar").attr("disabled",false);
			$("#sendOther1").attr("disabled",false);
			$("#sendOther2").attr("disabled",false);
			$("#sendOther3").attr("disabled",false);
			$("#reply").attr("disabled",false);
			$("#replytext").attr("disabled",false);
		}else{
			$("#moveFile1").attr("disabled",true);
			$("#moveFile2").attr("disabled",true);
			$("#setRead").attr("disabled",true);
			$("#setStar").attr("disabled",true);
			$("#sendOther1").attr("disabled",true);
			$("#sendOther2").attr("disabled",true);
			$("#sendOther3").attr("disabled",true);
			$("#reply").attr("disabled",true);
			$("#replytext").attr("disabled",true);
		}
	}
		//创建规则
		function createGz(){
			 $("#gzdiv").hide();
			 $("#showgz").show();
			 
			 $("#guuid").val("");
				$("#qiyong").val("1");
					$("#sendPerson1").attr("checked",false);
				
				
				$("#sendPerson2").val("");
				$("#sendPerson3").val("");
				$("#sendDomain1").attr("checked",false);
				
				
				$("#sendDomain2").val("");
				$("#sendDomain3").val("");
				
				
				$("#receivePerson1").attr("checked",false);
				
				$("#receivePerson2").val("");
				$("#receivePerson3").val("");
				
				$("#title1").attr("checked",false);
				
				$("#title2").val("");
				$("#title3").val("");
				$("#mailSize1").attr("checked",false);
				$("#mailSize2").val("");
				$("#mailSize3").val("");
				$("#receiveTime1").attr("checked",false);
				
				$("#receiveTime2").val("01:00");
				$("#receiveTime3").val("01:00");
				
				
				$(".operation").each(function(){
					if($(this).val()=="1"){
						$(this).attr("checked",true);
					}
				});
				
				$("#moveFile1").attr("checked",false);
				//$("#moveFile2").attr("disabled",false);
				$("#setRead").attr("checked",false);
				$("#setStar").attr("checked",false);
				$("#sendOther1").attr("checked",false);
				$("#sendOther2").val("");
				$(".sendOther3").each(function(){
					if($(this).val()=="1"){
						$(this).attr("checked",true);
					}
				});
				$("#showSendOther").hide();
				$("#reply").attr("checked",false);
				$("#replytext").val("");
		 }
		//创建规则2
		 function createGz2(){
			 $("#gzdiv").show();
			 $("#showgz").hide();
		 }
		 //文件夹类型跳转
		 function changeFolder(t){
			 if(t==1){
				 $("#button2").removeClass('active');
				 $("#button1").addClass('active');
				 $("#button3").removeClass('active');
				 $("#folder2").hide();
				 $("#folder1").show();
				 $("#folder3").hide();
			 }
			 if(t==2){
				 $("#button1").removeClass('active');
				 $("#button2").addClass('active');
				 $("#button3").removeClass('active');
				 $("#folder1").hide();
				 $("#folder2").show();
				 $("#folder3").hide();
			 }
			 if(t==3){
				 $("#button1").removeClass('active');
				 $("#button2").removeClass('active');
				 $("#button3").addClass('active');
				 $("#folder1").hide();
				 $("#folder2").hide();
				 $("#folder3").show();
			 }
		 }
		 function autoSendClick(){
			 $("#autoSendOtherFlag").each(function() {
					if ($(this).val()=='2') {
						$(this).attr("checked",true);
					}
				});
		 }
		//保存规则
		 function saveRule(){
		 	var uuid=$("#guuid").val();
		 	var qiyong='';
		 	
		 	$("#qiyong").each(function (){
		 		if($(this).attr("checked")=="checked"){
		 			qiyong=$(this).val();
		 		}
		 	});
		 	var sendPerson1='0';
		 	if($("#sendPerson1").attr("checked")=="checked"){
		 		sendPerson1='1';
		 	}
		 	var sendPerson2=$("#sendPerson2").val();
		 	var sendPerson3=$("#sendPerson3").val();
		 	if(sendPerson1=='1'&&sendPerson3==''){
		 		oAlert(global.sendPerson3);
		 		$("#sendPerson3").focus();
		 		return false;
		 	}
		 	var sendDomain1='0';
		 	if($("#sendDomain1").attr("checked")=="checked"){
		 		sendDomain1='1';
		 	}
		 	var sendDomain2=$("#sendDomain2").val();
		 	var sendDomain3=$("#sendDomain3").val();
		 	if(sendDomain1=='1'&&sendDomain3==''){
		 		oAlert(global.sendDomain3);
		 		$("#sendDomain3").focus();
		 		return false;
		 	}
		 	
		 	
		 	var receivePerson1='0';
		 	if($("#receivePerson1").attr("checked")=="checked"){
		 		receivePerson1='1';
		 	}
		 	var receivePerson2=$("#receivePerson2").val();
		 	var receivePerson3=$("#receivePerson3").val();
		 	if(receivePerson1=='1'&&receivePerson3==''){
		 		oAlert(global.receivePerson3);
		 		$("#receivePerson3").focus();
		 		return false;
		 	}
		 	
		 	var title1='0';
		 	if($("#title1").attr("checked")=="checked"){
		 		title1='1';
		 	}
		 	var title2=$("#title2").val();
		 	var title3=$("#title3").val();
		 	if(title1=='1'&&title3==''){
		 		oAlert(global.title3);
		 		$("#title3").focus();
		 		return false;
		 	}
		 	
		 	var mailSize1='0';
		 	if($("#mailSize1").attr("checked")=="checked"){
		 		mailSize1='1';
		 	}
		 	var mailSize2=$("#mailSize2").val();
		 	var mailSize3=$("#mailSize3").val();
		 	if(mailSize1=='1'&&mailSize3==''){
		 		oAlert(global.mailSize3);
		 		$("#mailSize3").focus();
		 		return false;
		 	}
		 	
		 	var receiveTime1='0';
		 	if($("#receiveTime1").attr("checked")=="checked"){
		 		receiveTime1='1';
		 	}
		 	var receiveTime2=$("#receiveTime2").val();
		 	var receiveTime3=$("#receiveTime3").val();
		 	
		 	var ruleName="";
		 	var deng="";
		 	if(qiyong=='1'&&sendPerson1!='1'&&sendDomain1!='1'&&receivePerson1!='1'&&title1!='1'&&mailSize1!='1'&&receiveTime1!='1'){
		 		oAlert(global.filterNotNull);
		 		return false;
		 	}
		 	if(sendPerson1=='1'){
		 		ruleName+=global.sender+(sendPerson2=='1'?global.contain:global.notContain)+sendPerson3+"\"";
		 	}
		 	if(sendDomain1=='1'&&ruleName==''){
		 		ruleName+=global.sendDomain+(sendDomain2=='1'?global.contain:global.notContain)+sendDomain3+"\"";
		 	}
		 	if(sendDomain1=='1'&&ruleName!=''&&deng==''){
		 		deng=global.deng;
		 	}
		 	if(receivePerson1=='1'&&ruleName==''){
		 		ruleName+=global.receivePerson+(receivePerson2=='1'?global.contain:global.notContain)+receivePerson3+"\"";
		 	}
		 	if(receivePerson1=='1'&&ruleName!=''&&deng==''){
		 		deng=global.deng;
		 	}
		 	if(title1=='1'&&ruleName==''){
		 		ruleName+=global.title+(title2=='1'?global.contain:global.notContain)+title3+"\"";
		 	}
		 	if(title1=='1'&&ruleName!=''&&deng==''){
		 		deng=global.deng;
		 	}
		 	
		 	if(mailSize1=='1'&&ruleName==''){
		 		ruleName+=global.mailSize+(mailSize2=='1'?global.dadengyu:global.shaoyu)+mailSize3+"\"";
		 	}
		 	if(mailSize1=='1'&&ruleName!=''&&deng==''){
		 		deng=global.deng;
		 	}
		 	
		 	if(receiveTime1=='1'&&ruleName==''){
		 		ruleName+=global.receiveTime1+receiveTime2.substring(0,2)+global.receiveTime2+receiveTime3.substring(0,2)+global.receiveTime3+"\"";
		 	}
		 	if(receiveTime1=='1'&&ruleName!=''&&deng==''){
		 		deng=global.deng;
		 	}
		 	
		 	var ruleName=global.mif+":\""+ruleName+deng;
		 	
		 	
		 	var operation;
		 	$(".operation").each(function(){
		 	    if($(this).attr("checked")=="checked"){
		 	    	operation=$(this).val();
		 	    }
		 	 });
		 	 
		 	 
		 	 var moveFile1='0';
		 	if($("#moveFile1").attr("checked")=="checked"){
		 		moveFile1='1';
		 	}
		 	var moveFile2=$("#moveFile2").val();
		 	if(moveFile2==''){
		 		oAlert(global.mailSize3);
		 		$("#moveFile2").focus();
		 		return false;
		 	}
		 	
		 	var setRead='0';
		 	if($("#setRead").attr("checked")=="checked"){
		 		setRead='1';
		 	}
		 	
		 	var setStar='0';
		 	if($("#setStar").attr("checked")=="checked"){
		 		setStar='1';
		 	}
		 	
		 	var sendOther1='0';
		 	if($("#sendOther1").attr("checked")=="checked"){
		 		sendOther1='1';
		 	}
		 	
		 	var sendOther2=$("#sendOther2").val();
		 	var sendOther3;
		 	$(".sendOther3").each(function(){
		 	    if($(this).attr("checked")=="checked"){
		 	    	sendOther3=$(this).val();
		 	    }
		 	 });
		 	if(sendOther1=='1'){
		 		var re=/^[^\s()<>@,;:\/]+@\w[\w\.-]+\.[a-z]{2,}$/i;
		 		if(sendOther2==''||re.test(sendOther2)==false){
		 			oAlert(global.sendOther);
		 			$("#sendOther2").focus();
		 			return false;
		 		}
		 	} 
		 	
		 	 var reply='0';
		 	if($("#reply").attr("checked")=="checked"){
		 		reply='1';
		 	}
		 	

//		 	var replytext=CKEDITOR.instances.replytext.getData();
		 	if(reply=='1'){
		 		if(replytext==''){
		 			oAlert(global.replyTest);
		 			//$("#replytext").focus();
		 			return false;
		 		}
		 	}
		 	if(qiyong=='1'&&operation=='1' && (moveFile1!='1'&&setRead!='1'&&setStar!='1'&&sendOther1!='1'&&reply!='1')){
		 		oAlert(global.ruleNotNull);
		 		return false;
		 	}
		 	var ruleName2="";
		 	var deng2="";
		 	if(operation=='2'){
		 		ruleName2=global.ruleNotNull+"\"";
		 	}
		 	if(operation=='1'&&moveFile1=='1'&&ruleName2==''){
		 	var checkText=$("#moveFile2").find("option:selected").text(); 
		 		ruleName2=global.moveTo+checkText+"\"";
		 	}
		 	if(moveFile1=='1'&&ruleName2!=''&&deng2==''){
		 		deng2=global.deng;
		 	}
		 	if(operation=='1'&&setRead=='1'&&ruleName2==''){
		 		ruleName2=global.setRead+"\"";
		 	}
		 	if(setRead=='1'&&ruleName2!=''&&deng2==''){
		 		deng2=global.deng;
		 	}
		 	if(operation=='1'&&setStar=='1'&&ruleName2==''){
		 		ruleName2=global.biaoxingbiao+"\"";
		 	}
		 	if(setStar=='1'&&ruleName2!=''&&deng2==''){
		 		deng2=global.deng;
		 	}
		 	if(operation=='1'&&sendOther1=='1'&&ruleName2==''){
		 		ruleName2=global.sendOtherTo+sendOther2+"\"";
		 	}
		 	if(sendOther1=='1'&&ruleName2!=''&&deng2==''){
		 		deng2=global.deng;
		 	}
		 	if(operation=='1'&&reply=='1'&&ruleName2==''){
		 		ruleName2=global.autoReply+"\"";
		 	}
		 	if(reply=='1'&&ruleName2!=''&&deng2==''){
		 		deng2=global.deng;
		 	}
		 	ruleName2=global.ze+"\""+ruleName2+deng2+"";
		 	
		 	ruleName=ruleName+"  "+ruleName2;
		 	$.ajax({
		 		type : "POST",
		 		url : ctx+"/mail/save_rule.action",
		 		data : 'ruleName='+ruleName+'&uuid='+uuid+'&qiyong='+qiyong+'&sendPerson1='+sendPerson1+'&sendPerson2='+sendPerson2+'&sendPerson3='+sendPerson3+'&sendDomain1='+sendDomain1+'&sendDomain2='+sendDomain2+'&sendDomain3='+sendDomain3+'&receivePerson1='+receivePerson1+'&receivePerson2='+receivePerson2+'&receivePerson3='+receivePerson3+'&title1='+title1+'&title2='+title2+'&title3='+title3+'&mailSize1='+mailSize1+'&mailSize2='+mailSize2+'&mailSize3='+mailSize3+'&receiveTime1='+receiveTime1+'&receiveTime2='+receiveTime2+'&receiveTime3='+receiveTime3+'&operation='+operation+'&moveFile1='+moveFile1+'&moveFile2='+moveFile2+'&setRead='+setRead+'&setStar='+setStar+'&sendOther1='+sendOther1+'&sendOther2='+sendOther2+'&sendOther3='+sendOther3+'&reply='+reply+'&replytext='+replytext,
		 		dataType:"text",
		 		success: function callback(result) {
		 			if(uuid!=''){
		 				if(qiyong=='1'){
		 					var s=global.hasQiyong+"|<a href='#' onclick=ifQiyong('"+uuid+"',2)>"+global.close+"</a>";
		 					$("#"+uuid).html(s);
		 				}else{
		 					var s=global.hasClose+"|<a href='#' onclick=ifQiyong('"+uuid+"',1)>"+global.qiyong+"</a>";
		 					$("#"+uuid).html(s);
		 				}
		 				$("#"+uuid+"ruleName").html(ruleName);
		 			}else{
		 		uuid=result;
		 			var s1=$("#showRuleTable").html();
		 			var s2="<tr>"+
		 		"<td>"+
		 		ruleName+
		 		"</td>"+
		 		"<td id='"+uuid+"'>";
		 		if(qiyong=='1'){
		 			s2+=global.hasQiyong+"|<a href='#' onclick=ifQiyong('"+uuid+"',2)>"+global.close+"</a>";
		 		}else{
		 			s2+=global.hasClose+"|<a href='#' onclick=ifQiyong('"+uuid+"',1)>"+global.qiyong+"</a>";
		 		}
		 		s2+="</td>"+
		 		"<td>"+
		 		"<a href='#' onclick=editRule('"+uuid+"','"+qiyong+"','"+sendPerson1+"','"+sendPerson2+"','"+sendPerson3+"','"+sendDomain1+"','"+sendDomain2+"','"+sendDomain3+"','"+receivePerson1+"','"+receivePerson2+"','"+receivePerson3+"','"+title1+"','"+title2+"','"+title3+"','"+mailSize1+"','"+mailSize2+"','"+mailSize3+"','"+receiveTime1+"','"+receiveTime2+"','"+receiveTime3+"','"+operation+"','"+moveFile1+"','"+moveFile2+"','"+setRead+"','"+setStar+"','"+sendOther1+"','"+sendOther2+"','"+sendOther3+"','"+reply+"','"+replytext+"')>[修改]</a>&nbsp;&nbsp;&nbsp;&nbsp;<a href='#' onclick=delRule('"+uuid+"')>[删除]</a>"+
		 		"</td>"+
		 		"</tr>";
		 		$("#showRuleTable").html(s2+s1);
		 			}
		 		createGz2();
		 		oAlert("创建成功！");
		 		}
		 	});
		 }
		 //添加常规
		 function addDisplaySet(){
				var uuid=$("#cuuid").val();
				var addTo;
				if($("#addTo").attr("checked")=='checked'){
					addTo=1;
				}else{
					addTo=0;
				}
				var listView;
				$("#listView").each(function() {
					if ($(this).attr("checked") == "checked") {
						listView=$(this).val();
					}
				});
				var sign=$("#sign").val();
				
				var sendFlag1='0';
				$("#sendFlag1").each(function() {
					if ($(this).attr("checked") == "checked") {
						sendFlag1='1';
					}
				});
				var sendFlag2='0';
				$("#sendFlag2").each(function() {
					if ($(this).attr("checked") == "checked") {
						sendFlag2='1';
					}
				});
				var sendFlag3='0';
				$("#sendFlag3").each(function() {
					if ($(this).attr("checked") == "checked") {
						sendFlag3='1';
					}
				});
				
				var replyFlag;
				$(".replyFlag").each(function() {
					if ($(this).attr("checked") == "checked") {
						replyFlag=$(this).val();
					}
				});
				
				var replyTitleFlag;
				$(".replyTitleFlag").each(function() {
					if ($(this).attr("checked") == "checked") {
						replyTitleFlag=$(this).val();
					}
				});
				
				var autoSendOtherFlag;
				$(".autoSendOtherFlag").each(function() {
					if ($(this).attr("checked") == "checked") {
						autoSendOtherFlag=$(this).val();
					}
				});
				
				var autoSendTo=$("#autoSendTo").val();
				if(autoSendOtherFlag=='2'){
				var re=/^[^\s()<>@,;:\/]+@\w[\w\.-]+\.[a-z]{2,}$/i;
					if(re.test(autoSendTo)==false){
						oAlert(global.mailAddressError);
						$("#autoSendTo").focus();
						return false;
					}
				}
				var hasAutoSendFlag=$("#hasAutoSendFlag").val();
				//oAlert('uuid='+uuid+'&addTo='+addTo+'&listView='+listView+'&sign='+sign+'&sendFlag1='+sendFlag1+'&sendFlag2='+sendFlag2+'&sendFlag3='+sendFlag3+'&replyFlag='+replyFlag+'&replyTitleFlag='+replyTitleFlag+'&autoSendOtherFlag='+autoSendOtherFlag+'&autoSendTo='+autoSendTo+'&hasAutoSendFlag='+hasAutoSendFlag);
				$.ajax({
					type : "POST",
					url : ctx+"/mail/save_display_set.action",
					data : 'uuid='+uuid+'&addTo='+addTo+'&listView='+listView+'&sign='+sign+'&sendFlag1='+sendFlag1+'&sendFlag2='+sendFlag2+'&sendFlag3='+sendFlag3+'&replyFlag='+replyFlag+'&replyTitleFlag='+replyTitleFlag+'&autoSendOtherFlag='+autoSendOtherFlag+'&autoSendTo='+autoSendTo+'&hasAutoSendFlag='+hasAutoSendFlag,
					dataType : "text",
					success : function callback(result) {
						oAlert(result);
					}
				});
			}
		 //未发送其他
			function nosendOther(){
				$("#autoSendTo").attr("disabled",true);
				$("#hasAutoSendFlag").attr("disabled",true);
			}
			 //未发送其他
			function yessendOther(){
				$("#autoSendTo").attr("disabled",false);
				$("#hasAutoSendFlag").attr("disabled",false);
			}
			function keyPress() {       
				var keyCode = event.keyCode;    
				event.returnValue = ((keyCode >= 48 && keyCode <= 57));   
			} 
			
			
			//发送

			//格式化邮址
			function formatMail(th,mid){
				
				var end=$("#"+th).val();
				var mails="";
				if(end!=''){
					var es=end.split(";");
					for(var i=0;i<es.length;i++){
						if(es[i]!=''&&es[i].indexOf("@")<0){
							var m=es[i]+"<"+es[i]+"@"+$("#"+mid).val()+">";
							//oAlert(mails+".."+m);
							//oAlert(mails.indexOf(m));
							if(!endWith(mails,";")&&mails!=''){
								if(mails.indexOf(m)<0){
									mails=mails+";"+m;
								}
							}else{
								if(mails.indexOf(m)<0){
									mails=mails+m;
								}
							}
							
						}else{
							if(mails.indexOf(es[i])<0){
							if(!endWith(mails,";")&&mails!=''){
								mails=mails+";"+es[i];
							}else{
								mails=mails+es[i];
							}
							}
						}
					}
				}
				if(mails!=''&&!endWith(mails,";")){
					mails=mails+";";
				}
				$("#"+th).val(mails);
				
				
			}
			//格式化邮址
			function formatMail2(th,mid){
				var end=$(th).val();
				var mails="";
				if(end!=''){
					var es=end.split(";");
					for(var i=0;i<es.length;i++){
						if(es[i]!=''&&es[i].indexOf("@")<0){
							var m=es[i]+"<"+es[i]+"@"+$("#"+mid).val()+">";
							if(!endWith(mails,";")&&mails!=''){
								if(mails.indexOf(m)<0){
									mails=mails+";"+m;
								}
							}else{
								if(mails.indexOf(m)<0){
									mails=mails+m;
								}
							}
							
						}else{
							if(mails.indexOf(es[i])<0){
							if(!endWith(mails,";")&&mails!=''){
								mails=mails+";"+es[i];
							}else{
								mails=mails+es[i];
							}
							}
						}
					}
				}
				if(mails!=''&&!endWith(mails,";")){
					mails=mails+";";
				}
				
				$(th).val(mails);
				
			}
			//格式化邮址
			function formatMail22(th,mid){
				
				var end=th.val();
				var mails="";
				if(end!=''){
					var es=end.split(";");
					for(var i=0;i<es.length;i++){
						if(es[i]!=''&&es[i].indexOf("@")<0){
							var m=es[i]+"<"+es[i]+"@"+$("#"+mid).val()+">";
							if(!endWith(mails,";")&&mails!=''){
								if(mails.indexOf(m)<0){
									mails=mails+";"+m;
								}
							}else{
								if(mails.indexOf(m)<0){
									mails=mails+m;
								}
							}
							
						}else{
							if(mails.indexOf(es[i])<0){
							if(!endWith(mails,";")&&mails!=''){
								mails=mails+";"+es[i];
							}else{
								mails=mails+es[i];
							}
							}
						}
					}
				}
				if(mails!=''&&!endWith(mails,";")){
					mails=mails+";";
				}
				th.val(mails);
				
			}
			
			
			$(document).ready(function(){
			    $("ul.menu li:first-child").addClass("current");
			    $("div.content").find("div.layout:not(:first-child)").hide();
			    $("div.content div.layout").attr("id", function(){return idNumber("No")+ $("div.content div.layout").index(this)});
			    $("ul.menu li").click(function(){
			        var c = $("ul.menu li");
			        var index = c.index(this);
			        var p = idNumber("No");
			        show(c,index,p);
			    });
			    
			     
				 $("#topLink").css({top:(window.screen.availHeight-($("#topLink").height()*12))});
				   // var menuYloc = $("#topLink").offset().top; 
				    //$(window).scroll(function() {
				        //var offsetTop = menuYloc + $(window).scrollTop() + "px";		
				        //$("#topLink").animate({top: offsetTop}, {duration: 300, queue: false}); 
				    //}); 
				
			    
			    function show(controlMenu,num,prefix){
			        var content= prefix + num;
			        $('#'+content).siblings().hide();
			        $('#'+content).show();
			        controlMenu.eq(num).addClass("current").siblings().removeClass("current");
			    };
			    
			    
			 
			    function idNumber(prefix){
			        var idNum = prefix;
			        return idNum;
			    };
			 });
			
			
			function setValue(obj){
				$("#mtarget").val(obj);
				$("#"+obj).focus();
			}

			
			
			function switchSysBar(){
				var img = $("#barImg").attr('src');
				if(img.indexOf("right") > 0){
					$("#switchPoint").html('<img id="barImg" src="'+ctx+'/pt/mail/js/left.gif">');
					$("#rightf").show(); 
					$("#frmTitle").attr("width","80%");
				}else{
					$("#switchPoint").html('<img id="barImg" src="'+ctx+'/pt/mail/js/right.gif">');
					
					$("#rightf").hide();
					$("#frmTitle").attr("width","98%");
				}
			}
			
			
			//选择
			function setSelThis(obj){
				$(obj).css('background-color','blue');
			}
			//选择
			function setSelThis2(obj){
				$(obj).css('background-color','#fff');
			}
			
			
			//设置星标
			function setStar(obj,uuid){
				var objs=$(obj).attr('src');
				if(objs.indexOf('star-on')>-1){
					updateStar(obj,0,uuid);
				}else{
					updateStar(obj,1,uuid);
				}
			}
			//后台设置星标
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
			
			
			//1=add 2=delete设置标签
			function setLabel(uuid,fname,color,status){
				updateLabel(uuid,fname,color,status);
			}
			//设置标签2
			function setLabel2(mailuuid,labeluuid,status){
				updateLabel2(mailuuid,labeluuid,status);
			}
			//更新标签2
			function updateLabel2(mailuuid,labeluuid,status){
				$.ajax({
					type : "POST",
					url : ctx+"/mail/update_label.action",
					data : "uuid="+labeluuid+"&status="+status+"&muuid="+mailuuid,
					dataType : "text",
					success : function callback(result) {
						var ids=mailuuid+labeluuid;
						$("#"+ids).hide();
					}
				});
			}
			//更新标签
			function updateLabel(uuid,fname,color,status){
				var selected='';
				selected=getSelected();
				if (selected!='') {
				$.ajax({
					type : "POST",
					url : ctx+"/mail/update_label.action",
					data : "uuid="+uuid+"&status="+status+"&muuid="+selected+"&fname="+fname+"&color="+color,
					dataType : "text",
					success : function callback(result) {
						//oAlert(result);
						var uuids=selected.split("#");
						for(var i=0;i<uuids.length;i++){
							if(uuids[i]!=''){
								var tdid=uuids[i]+'label';
								//var ress=result.split('|#|');
								var txt1="<span id='"+uuids[i]+uuid+"' style='background-color: "+color+"'>"+fname+"<a href='#' onclick=\"setLabel2('"+uuids[i]+"','"+uuid+"','2');\">"+global.deleteBtn+"</a></span>";   
								$("#"+tdid).append(txt1);
							}
							
						}
						
					}
				});
				} else {
					oAlert(global.selectARecord);
				}
			}
			var labelobj1 = new Object();
			//1=add 2=delete设置标签
			function setLabelCms(uuid,fname,color,status,obj){
				labelobj1 = obj;
				updateLabelCms(uuid,fname,color,status);
			}
			
			var cancel_label_obj = new Object();
			//取消标签
			function setLabel2Cms(labeluuid,mailuuid,obj){
				cancel_label_obj = obj;
				updateLabel2Cms(labeluuid,mailuuid,cancel_label_obj);
			}
			//取消标签
			function updateLabel2Cms(labeluuid,mailuuid,cancel_label_obj){
				$.ajax({
					type : "POST",
					url : ctx+"/mail/cancel_label_cms.action",
					data : "labeluuid="+labeluuid+"&mailuuid="+mailuuid,
					dataType : "text",
					success : function callback(result) {
						var moduleid = "";
						if(cancel_label_obj=="closeTagSideDiv"){
							moduleid = $(".closeTagSideDiv").eq(0).parents(".active").attr("moduleid");
							cancel_label_obj = $(".closeTagSideDiv").eq(0);
						}else{
							moduleid = $(cancel_label_obj).parents(".active").attr("moduleid");
						}
						//我的文件夹、其他邮件、标签刷新当前视图
						if(moduleid == "folder" || moduleid == "other" || moduleid == "mlabel"){
							refreshMailLive($(cancel_label_obj));
							refreshWindow($(".writeMail"));
						} else {
							refreshWindow($(cancel_label_obj));
							refreshWindow($(".writeMail"));
						}
					}
				});
			}
			//更新标签
			function updateLabelCms(uuid,fname,color,status){
				var arrayObj = readParam();
				var uuids = new Array();
				for (var i=0;i<=arrayObj.length-1;i++) { 
					var uuid1 = arrayObj[i]["uuid"];
					uuids.push(uuid1);
				}
				if (uuids!=''||uuids.length>0) {
					$.ajax({
						type : "POST",
						url : ctx+"/mail/update_label_cms.action",
						data : "uuid="+uuid+"&status="+status+"&uuids="+uuids+"&fname="+fname+"&color="+color,
						dataType : "text",
						success : function callback(result) {
							var moduleid = "";
							if(labelobj1=="customButton_group_button"){
								moduleid = $(".customButton_group_button").eq(0).parents(".active").attr("moduleid");
								labelobj1 = $(".customButton_group_button").eq(0);
							}else{
								moduleid = $(labelobj1).parents(".active").attr("moduleid");
							}
							//我的文件夹、其他邮件、标签刷新当前视图
							if(moduleid == "folder" || moduleid == "other" || moduleid == "mlabel"){
								refreshMailLive($(labelobj1));
								refreshWindow($(".writeMail"));
							} else {
								refreshWindow($(labelobj1));
								refreshWindow($(".writeMail"));
							}
							
							/*var uuids=selected.split("#");
							for(var i=0;i<uuids.length;i++){
								if(uuids[i]!=''){
									var tdid=uuids[i]+'label';
									//var ress=result.split('|#|');
									var txt1="<span id='"+uuids[i]+uuid+"' style='background-color: "+color+"'>"+fname+"<a href='#' onclick=\"setLabel2('"+uuids[i]+"','"+uuid+"','2');\">"+global.deleteBtn+"</a></span>";   
									$("#"+tdid).append(txt1);
								}
								
							}*/
							
						}
					});
				} else {
					oAlert(global.uncheckedAnyMail);
				}
			}
			
			
			//弹出文件夹
			function openMailListFolder(rel,pageNo,mtype,mailname){
					var selected='';
					selected=getSelected();
					if (selected!='') {
						var content=getMailListFileHtml('','',rel,pageNo,mtype,mailname,0,'',0);
						var title=global.addFolder;
						var json = 
						{
							title:title,  /*****标题******/
							autoOpen: true,  /*****初始化之后，是否立即显示对话框******/
							modal: true,     /*****是否模式对话框******/
							closeOnEscape: true, /*当按 Esc之后，是否应该关闭对话框******/
							draggable: true, /*****是否允许拖动******/  
							resizable: true, /*****是否可以调整对话框的大小******/  
							stack : false,   /*****对话框是否叠在其他对话框之上******/
							height: 400, /*****标题******/
							width: 860,   /*****标题******/
							content: content,/*****内容******/
							//open：事件,
							buttons: {
								"确定": addMailListFolder
								//"取消": 事件2
							}
						};

						showDialog(json);
//						showScheduleDialog(global.addFolder,content,300,600);
					} else {
						oAlert(global.selectARecord);
					}
			}
			var folderObj = new Object();
			
			//弹出文件夹(cms)
			function openMailListFolderCms(rel,pageNo,mtype,mailname,obj){
				folderObj = obj;
				var arrayObj = readParam();
				var uuids = new Array();
				for (var i=0;i<=arrayObj.length-1;i++) { 
					var uuid = arrayObj[i]["uuid"];
					uuids.push(uuid);
				}
				if (uuids!=''||uuids.length>0) {
					var content=getMailListFileHtml('','',rel,pageNo,mtype,mailname,0,'',0);
					 var json = new Object(); 
				     json.content = content;
				     json.title = "新建文件夹";
				     json.height= 191;
				     json.width= 443;
				     var buttons = new Object(); 
				     buttons.确定 = addMailListFolderCms;
				     json.buttons = buttons;
				     showDialog(json);
				}else{
					oAlert(global.uncheckedAnyMail);
				}
			}
			
			//添加文件夹(cms)
			function addMailListFolderCms(){
				var rel=$("#inrel").val();
				var pageNo=$("#inpageNo").val();
				var mtype=$("#inmtype").val();
				var mailname=$("#inmailname").val();
				
				var arrayObj = readParam();
				var uuids = new Array();
				for (var i=0;i<=arrayObj.length-1;i++) { 
					var uuid1 = arrayObj[i]["uuid"];
					uuids.push(uuid1);
				}
				if (uuids!=''||uuids.length>0) {
					var fname=$("#fname").val();
					var uuid=$("#uuid").val();
					if(fname==''){
						oAlert("文件夹名称不能为空!");
						fname.focus();
						return false;
					}
					$.ajax({
						type : "POST",
						url : ctx+"/mail/add_folder2_cms.action",
						data : "uuid="+uuid+"&fname="+fname+"&status=0&uuids="+uuids,
						dataType : "text",
						success : function callback(result) {
							if(result.indexOf("error")>-1){
								oAlert("文件夹名称已存在!");
							}else{
								//查找对应文件夹
								JDS.call({
									service : "mailBoxService.getLabelByUuid",
									data : [ result ],
									async: false,
									success : function(result) {
										var labelname = result.data.fname;
										var labelUuid = result.data.uuid;
										
										//改变左侧“我的文件夹”状态
										changePlusForAdd('folder');
										
										//如果是第一个文件夹，左侧需先添加头部
										if(!($("#myfile").length>0)){
											var str_left_folder_top = '<li id="myfile" onclick="changePlus(\'folder\');">'+
											'<img id="folderImg" src="/wellpt-web/resources/ckfinder/skins/kama/images/ckfminus.gif">我的文件夹</li>'+
											'<li id="folder" class="folder liveData" foldername="'+labelname+'" folderid="'+labelUuid+'" url="/basicdata/dyview/view_show?viewUuid=1cb04818-9156-4ccf-aee9-0af292065563&status_='+labelUuid+'" name_="'+labelname+'" style="display: list-item;">'+labelname+'</li>';
											$(".left3").find("li").eq(0).after(str_left_folder_top);
										} else {
											//添加至左侧导航菜单文件夹
											var str_left_folder = '<li id="folder" class="folder liveData" foldername="'+labelname+'" folderid="'+labelUuid+'" url="/basicdata/dyview/view_show?viewUuid=1cb04818-9156-4ccf-aee9-0af292065563&status_='+labelUuid+'" name_="'+labelname+'" style="display: list-item;">'+labelname+'</li>';
											$(".folder.liveData").first().before(str_left_folder);
											
											//添加至下拉文件夹列表
											$(".folder.liveData").each(function(){
												var str_folder = '<div class="customButton_group_button" onclick="moveMailBoxCms(\'0\',\'1\',\'0\',\''+labelUuid+'\',\'\',\'customButton_group_button\');"> '+labelname+' </div>';
												$(".customButton_group_buttons").eq(1).find(".customButton_group_button").last().before(str_folder);
											});
										}
										$(".mail_div  ul li").click(function(){
											$(".mail_div  ul li").removeClass('active');
											if($(this).attr("id")!="mytag"&&$(this).attr("id")!="othermail"&&$(this).attr("id")!="myfile"){
												$(this).addClass('active');
											}
										});
									}
								});
								closeDialog();
								refreshWindow($(folderObj));
								refreshWindow($(".writeMail"));
							}
						}
					});
				} else {
					oAlert("请选择一条记录");
				}
			}
			
			//弹出文件夹2
			function openMailListFolderIn(rel,pageNo,mtype,mailname,mailUuid,type){
					var selected='';
					selected=getSelected();
					if (selected!='') {
						var content=getMailListFileHtml('','',rel,pageNo,mtype,mailname,0,mailUuid,type);
						var title=global.addFolder;
						var json = 
						{
							title:title,  /*****标题******/
							autoOpen: true,  /*****初始化之后，是否立即显示对话框******/
							modal: true,     /*****是否模式对话框******/
							closeOnEscape: true, /*当按 Esc之后，是否应该关闭对话框******/
							draggable: true, /*****是否允许拖动******/  
							resizable: true, /*****是否可以调整对话框的大小******/  
							stack : false,   /*****对话框是否叠在其他对话框之上******/
							height: 400, /*****标题******/
							width: 860,   /*****标题******/
							content: content,/*****内容******/
							//open：事件,
							buttons: {
								"确定": addInFolder
								//"取消": 事件2
							}
						};

						showDialog(json);
//						showScheduleDialog(global.addFolder,content,300,600);
					} else {
						oAlert(global.selectARecord);
					}
			}
			//弹出标签
			function openMailListLabel(rel,pageNo,mtype,mailname){
						var content=getMailListFileHtml('','',rel,pageNo,mtype,mailname,2,'',0);
						var title="新建标签";
						 var json = new Object(); 
					     json.content = content;
					     json.title = title;
					     json.height= 191;
					     json.width= 443;
					     var buttons = new Object(); 
					     buttons.确定 = addMailListLabel;
					     json.buttons = buttons;
					     showDialog(json);
						
//						var json = 
//						{
//							title:title,  /*****标题******/
//							autoOpen: true,  /*****初始化之后，是否立即显示对话框******/
//							modal: true,     /*****是否模式对话框******/
//							closeOnEscape: true, /*当按 Esc之后，是否应该关闭对话框******/
//							draggable: true, /*****是否允许拖动******/  
//							resizable: true, /*****是否可以调整对话框的大小******/  
//							stack : false,   /*****对话框是否叠在其他对话框之上******/
//							height: 400, /*****标题******/
//							width: 860,   /*****标题******/
//							content: content,/*****内容******/
//							//open：事件,
//							buttons: {
//								"确定": addMailListLabel
//								//"取消": 事件2
//							}
//						};
//
//						showDialog(json);
//						showScheduleDialog(global.addLabel,content,300,600);
			}
			
			
			var labelObj = new Object();
			//弹出标签(cms)
			function openMailListLabelCms(rel,pageNo,mtype,mailname,obj){
				labelObj = obj;
				var arrayObj = readParam();
				var uuids = new Array();
				for (var i=0;i<=arrayObj.length-1;i++) { 
					var uuid = arrayObj[i]["uuid"];
					uuids.push(uuid);
				}
				if (uuids!=''||uuids.length>0) {
					var content=getMailListFileHtml('','',rel,pageNo,mtype,mailname,2,'',0);
					var title="新建标签";
					 var json = new Object(); 
				     json.content = content;
				     json.title = title;
				     json.height= 191;
				     json.width= 443;
				     var buttons = new Object(); 
				     buttons.确定 = addMailListLabelCms;
				     json.buttons = buttons;
				     showDialog(json);
				}else{
					oAlert(global.uncheckedAnyMail);
				}
			}
			
			//弹出标签
			function openMailListLabelIn(rel,pageNo,mtype,mailname,mailUuid,type){
						var content=getMailListFileHtml('','',rel,pageNo,mtype,mailname,2,mailUuid,type);
						var title=global.addLabel;
						var json = 
						{
							title:title,  /*****标题******/
							autoOpen: true,  /*****初始化之后，是否立即显示对话框******/
							modal: true,     /*****是否模式对话框******/
							closeOnEscape: true, /*当按 Esc之后，是否应该关闭对话框******/
							draggable: true, /*****是否允许拖动******/  
							resizable: true, /*****是否可以调整对话框的大小******/  
							stack : false,   /*****对话框是否叠在其他对话框之上******/
							height: 400, /*****标题******/
							width: 860,   /*****标题******/
							content: content,/*****内容******/
							//open：事件,
							buttons: {
								"确定": addMailListLabelIn
								//"取消": 事件2
							}
						};

						showDialog(json);
//						showScheduleDialog(global.addLabel,content,300,600);
			}
			






			
			//获得文件夹HTML
			function getMailListFileHtml(uuid,fname,rel,pageNo,mtype,mailname,status,mailUuid,type){
					var content="<div id='test_con3'>"
						+"<input type='hidden' id='uuid' name='uuid' value='"+uuid+"' />"
						+"<input type='hidden' id='status' name='status' value='"+status+"' />"
						+"<input type='hidden' id='inrel' name='inrel' value='"+rel+"' />"
						+"<input type='hidden' id='inpageNo' name='inpageNo' value='"+pageNo+"' />"
						+"<input type='hidden' id='inmtype' name='inmtype' value='"+mtype+"' />"
						+"<input type='hidden' id='inmailname' name='inmailname' value='"+mailname+"' />"
						+"<input type='hidden' id='inmailUuid' name='inmailUuid' value='"+mailUuid+"' />"
						+"<table style='margin:16px 100px;'>"
						+"<tr>";
						if(status==0){
						content+="<td align='left' >"+"请输入文件夹名称"+"</td>";
						}else{
							content+="<td align='left' >"+"请输入标签名称"+"</td>";
						}
						content+="</tr>"
						+"<tr>"
						+"<td><input type='text' id='fname' value='"+fname+"' name='fname' size='50'/></td>"
						+"</tr>"
						+"</table>"
//						+"<div align='center' style=''>"
//						+"<table><tr><td>";
//						if(type==0){
//							if(status==0){
//								content+="<button onclick=\"addMailListFolder();\">"+global.confirm+"</button>";
//								}else{
//									content+="<button onclick=\"addMailListLabel();\">"+global.confirm+"</button>";
//								}
//						}else{
//							if(status==0){
//							content+="<button onclick=\"addInFolder();\">"+global.confirm+"</button></td><td>";
//							}else{
//								content+="<button onclick=\"addMailListLabelIn();\">"+global.confirm+"</button>";
//							}
//							}
//						content+="</td><td><button onclick='return closeDialog();'>"+global.cancel+"</button>"
//						+"</td></tr></table>"
//						+"</div>"
						+"</div>";
						return content;
				}
			//添加标签（cms）	
			function addMailListLabelCms(){
				var arrayObj = readParam();
				var uuids = new Array();
				for (var i=0;i<=arrayObj.length-1;i++) { 
					var uuid1 = arrayObj[i]["uuid"];
					uuids.push(uuid1);
				}
				if (uuids!=''||uuids.length>0) {
					var fname=$("#fname").val();
					var uuid=$("#uuid").val();
					if(fname==''){
						oAlert("标签名称不能为空!");
						fname.focus();
						return false;
					}
					$.ajax({
						type : "POST",
						url : ctx+"/mail/add_label_cms.action",
						data : "uuid="+uuid+"&fname="+fname+"&status=2&uuids="+uuids,
						dataType : "text",
						success : function callback(result) {
							if(result.indexOf("error")>-1){
								oAlert("标签名称已存在!");
							}else{
								//查找对应标签
								JDS.call({
									service : "mailBoxService.getLabelByUuid",
									data : [ result ],
									async: false,
									success : function(result) {
										var labelcolor = result.data.color;
										var labelname = result.data.fname;
										var labelUuid = result.data.uuid;
										
										//改变左侧“我的标签”状态
										changePlusForAdd('mlabel');
										
										//如果是第一个标签，左侧需先添加头部
										if(!($("#mytag").length>0)){
											var str_left_label_top = '<li id="mytag" onclick="changePlus(\'mlabel\');">'+
											'<img id="mlabelImg" src="/wellpt-web/resources/ckfinder/skins/kama/images/ckfminus.gif">我的标签</li>'+
											'<li id="mlabel" class="mlabel liveData" labelcolor="'+labelcolor+'" labelname="'+labelname+'" labelid="'+labelUuid+'" url="/basicdata/dyview/view_show?viewUuid=2f5b414a-175e-4754-871b-e233e6c5dff8&status_='+labelUuid+'" name_="'+labelname+'" style="display: list-item;">'+
											'<span style="cursor: pointer;background-color: '+labelcolor+';display:block;width:10px;height:10px;border-radius: 4px 4px 4px 4px;float: left;margin: 8px 5px 0 20px;"></span>'+
											'<span style="color:'+labelcolor+'; ">'+labelname+'</span></li>';
											$(".left3").find("li").eq(0).after(str_left_label_top);
										} else {
											//添加至左侧导航菜单标签
											var str_left_label = '<li id="mlabel" class="mlabel liveData" labelcolor="'+labelcolor+'" labelname="'+labelname+'" labelid="'+labelUuid+'" url="/basicdata/dyview/view_show?viewUuid=2f5b414a-175e-4754-871b-e233e6c5dff8&status_='+labelUuid+'" name_="'+labelname+'" style="display: list-item;">'+
											'<span style="cursor: pointer;background-color: '+labelcolor+';display:block;width:10px;height:10px;border-radius: 4px 4px 4px 4px;float: left;margin: 8px 5px 0 20px;"></span>'+
											'<span style="color:'+labelcolor+'; ">'+labelname+'</span></li>';
											$(".mlabel.liveData").first().before(str_left_label);
											
											//添加至下拉标签
											$(".mlabel.liveData").each(function(){
												var str_mlabel = '<div class="customButton_group_button" onclick="setLabelCms(\''+labelUuid+'\',\''+labelname+'\',\''+labelcolor+'\',\'1\',\'customButton_group_button\');"> '+labelname+' </div>';
												$(".customButton_group_buttons").eq(0).find(".customButton_group_button").last().before(str_mlabel);
											});
										}
										$(".mail_div  ul li").click(function(){
											$(".mail_div  ul li").removeClass('active');
											if($(this).attr("id")!="mytag"&&$(this).attr("id")!="othermail"&&$(this).attr("id")!="myfile"){
												$(this).addClass('active');
											}
										});
									}
								});
								closeDialog();
								refreshWindow($(labelObj));
								refreshWindow($(".writeMail"));
							}
						}
					});
				} else {
					oAlert("请选择一条记录");
				}
				
				
				
				
				var rel=$("#inrel").val();
				var pageNo=$("#inpageNo").val();
				var mtype=$("#inmtype").val();
				var mailname=$("#inmailname").val();
				var selected='';
				selected=getSelected();
				
			}
			//添加标签
			function addMailListLabel(){
				var rel=$("#inrel").val();
				var pageNo=$("#inpageNo").val();
				var mtype=$("#inmtype").val();
				var mailname=$("#inmailname").val();
				var selected='';
				selected=getSelected();
				var fname=$("#fname").val();
				var uuid=$("#uuid").val();
				if(fname==''){
					oAlert("标签名称不能为空!");
					fname.focus();
					return false;
				}
				$.ajax({
					type : "POST",
					url : ctx+"/mail/add_label.action",
					data : "uuid="+uuid+"&fname="+fname+"&status=2&selected="+selected,
					dataType : "text",
					success : function callback(result) {
						if(result.indexOf("error")>-1){
							oAlert("标签名称已存在!");
						}else{
							closeDialog();
						window.location.href=ctx+"/mail/mail_box_list.action?rel="+rel+"&pageNo="+pageNo+"&mtype="+mtype+"&mailname="+mailname;
						}
					}
				});
			}
			//添加文件夹
			function addMailListFolder(){
				var rel=$("#inrel").val();
				var pageNo=$("#inpageNo").val();
				var mtype=$("#inmtype").val();
				var mailname=$("#inmailname").val();
				var selected='';
				selected=getSelected();
				if (selected!='') {
				var fname=$("#fname").val();
				var uuid=$("#uuid").val();
				if(fname==''){
					oAlert("文件夹名称不能为空!");
					fname.focus();
					return false;
				}
				$.ajax({
					type : "POST",
					url : ctx+"/mail/add_folder2.action",
					data : "uuid="+uuid+"&fname="+fname+"&status=0&selected="+selected,
					dataType : "text",
					success : function callback(result) {
						if(result.indexOf("error")>-1){
							oAlert("文件夹名称已存在!");
						}else{
							closeDialog();
						window.location.href=ctx+"/mail/mail_box_list.action?rel="+rel+"&pageNo="+pageNo+"&mtype="+mtype+"&mailname="+mailname;
						}
					}
				});
				} else {
					oAlert("请选择一条记录");
				}
			}
			 
			
			
			
			//已读未读设置
			function readMailBox(rel,rtype,pageNo,mtype,mailname){
				var selected='';
				if(rtype==0||rtype==1){
					selected=getAllSelected();
				}else{
					selected=getSelected();
				}
				if(mtype=='') mtype=0;
				if (selected!='') {
					if(confirm(global.operationConfirm)) {
						var mid = selected;
						$.ajax({
							type : "POST",
							url : ctx+"/mail/mailbox_readornot.action",
							data : "pageNo="+pageNo+"&mtype="+mtype+"&rtype="+rtype+"&selected="+selected,
							dataType : "text",
							success : function callback(result) {
								window.location.href=ctx+"/mail/mail_box_list.action?rel="+rel+"&pageNo="+pageNo+"&mtype="+mtype+"&mailname="+mailname;
							}
						});
				}
					//window.location.href=ctx+"/mail/toReadOrNot.action?pageNo="+pageNo+"&mtype="+mtype+"&rtype="+rtype+"&selected="+selected;
				} else {
					oAlert(global.selectARecord);
				}
			}
			
			
			


			//移到发件箱
			function moveMailBox(rel,pageNo,mtype,ctype,mailname){
				var selected='';
				
					selected=getSelected();
					
					if(mtype=='') mtype=0;
				if (selected!=''||selected.indexOf("#")>-1) {
					if(confirm(global.operationConfirm)) {
						var mid = selected;
						$.ajax({
							type : "POST",
							url : ctx+"/mail/mailbox_move.action",
							data : "rel="+rel+"&pageNo="+pageNo+"&mtype="+mtype+"&selected="+selected+"&ctype="+ctype+"&mailname="+mailname,
							dataType : "text",
							success : function callback(result) {
								window.location.href=ctx+"/mail/mail_box_list.action?rel="+rel+"&pageNo="+pageNo+"&mtype="+mtype+"&ctype="+ctype+"&mailname="+mailname;
							}
						});
				}
					//window.location.href=ctx+"/mail/toMove.action?pageNo="+pageNo+"&mtype="+mtype+"&selected="+selected;
				} else {
					oAlert(global.selectARecord);
				}
			}
			//移到其他邮件
			function moveMailBox2(rel,pageNo,mtype,ctype,mailname,otherMail){
				var selected='';
				
				selected=getSelected();
				
				if(mtype=='') mtype=0;
				if (selected!=''||selected.indexOf("#")>-1) {
					if(confirm(global.operationConfirm)) {
						var mid = selected;
						$.ajax({
							type : "POST",
							url : ctx+"/mail/mailbox_move2.action",
							data : "rel="+rel+"&pageNo="+pageNo+"&mtype="+mtype+"&selected="+selected+"&ctype="+ctype+"&mailname="+mailname+"&otherMail="+otherMail,
							dataType : "text",
							success : function callback(result) {
								window.location.href=ctx+"/mail/mail_box_list.action?rel="+rel+"&pageNo="+pageNo+"&mtype="+mtype+"&ctype="+ctype+"&mailname="+mailname;
							}
						});
					}
					//window.location.href=ctx+"/mail/toMove.action?pageNo="+pageNo+"&mtype="+mtype+"&selected="+selected;
				} else {
					oAlert(global.selectARecord);
				}
			}
			//移到其他邮件
			function moveMailBox2Cms(rel,pageNo,mtype,ctype,mailname,otherMail,obj){
				var arrayObj = readParam();
				var uuids = new Array();
				for (var i=0;i<=arrayObj.length-1;i++) { 
					var uuid = arrayObj[i]["uuid"];
					uuids.push(uuid);
				}
				if(mtype=='') mtype=0;
				if (uuids!=''||uuids.length>0) {
					$.ajax({
						type : "POST",
						url : ctx+"/mail/mailbox_move2_cms.action",
						data : "rel="+rel+"&pageNo="+pageNo+"&mtype="+mtype+"&uuids="+uuids+"&ctype="+ctype+"&mailname="+mailname+"&otherMail="+otherMail,
						dataType : "text",
						success : function callback(result) {
							var moduleid = "";
							if(obj=="customButton_group_button"){
								moduleid = $(".customButton_group_button").eq(0).parents(".active").attr("moduleid");
								obj = $(".customButton_group_button").eq(0);
							}else{
								moduleid = $(obj).parents(".active").attr("moduleid");
							}
							//我的文件夹、其他邮件、标签刷新当前视图
							if(moduleid == "folder" || moduleid == "other" || moduleid == "mlabel"){
								refreshMailLive($(obj));
								refreshWindow($(".writeMail"));
							} else {
								refreshWindow($(obj));
								refreshWindow($(".writeMail"));
							}
						}
					});
				} else {
					oAlert(global.selectARecord);
				}
			}
			//删除邮件
			function deleMailBox(rel,pageNo,mtype,mailname) {
				var selected='';
				selected=getSelected();
				if(mtype=="") mtype="0";
				if (selected!='') {
					if(confirm(global.deleteConfirm)) {
							var mid = selected;
							$.ajax({
								type : "POST",
								url : ctx+"/mail/mail_box_cremove.action",
								data : "mids="+selected+"&pageNo="+pageNo+"&mtype="+mtype,
								dataType : "text",
								success : function callback(result) {
									window.location.href=ctx+"/mail/mail_box_list.action?rel="+rel+"&pageNo="+pageNo+"&mtype="+mtype+"&mailname="+mailname;
									
								}
							});
					}
				} else {
					oAlert(global.selectARecord);
				}
			}
			//删除邮件
			function clearMailBox(rel,pageNo,mtype,mailname) {
				if(mtype=="") mtype="0";
					if(confirm(global.clearConfirm)) {
							$.ajax({
								type : "POST",
								url : ctx+"/mail/mail_box_clear.action",
								//data : "mids="+selected+"&pageNo="+pageNo+"&mtype="+mtype,
								dataType : "text",
								success : function callback(result) {
									window.location.href=ctx+"/mail/mail_box_list.action?rel="+rel+"&pageNo="+pageNo+"&mtype="+mtype+"&mailname="+mailname;
									
								}
							});
					}
			}
			//群邮件转发
			function toSendOtherGMailBox(rel,pageNo,mtype,isSend){
				var selected='';
				
				selected=getSelected();
				if(mtype=='') mtype=0;
				if (selected!=''&&selected.indexOf("#")==selected.length-1) {
							window.location.href=ctx+"/mail/mailbox_send_otherg.action?rel="+rel+"&isSend="+isSend+"&pageNo="+pageNo+"&mtype="+mtype+"&selected="+selected;

				//window.location.href=ctx+"/mail/toMove.action?pageNo="+pageNo+"&mtype="+mtype+"&selected="+selected;
			} else {
				oAlert(global.selectOnlyOne);
			}
				
			}
			//排序
			function sort(sort,rel ,pageNo,mtype,mailname){
				if(sort=="desc"){
					window.location.href=ctx+"/mail/mail_box_list.action?rel="+rel+"&pageNo="+pageNo+"&mtype="+mtype+"&mailname="+mailname+"&sort=asc";
				}else{
					window.location.href=ctx+"/mail/mail_box_list.action?rel="+rel+"&pageNo="+pageNo+"&mtype="+mtype+"&mailname="+mailname+"&sort=desc";
				}
			}
			//转发
			function toPage(rel,mtype,mailname){
				var pageNo=$("#toPage").val();
				window.location.href=ctx+"/mail/mail_box_list.action?rel="+rel+"&pageNo="+pageNo+"&mtype="+mtype+"&mailname="+mailname;
			}
			//打开
			function openPage(){
				var v=$("#divpage:visible");
				if(v.length>0){
					$("#divpage").hide();
				}else{
				$("#divpage").show();
				}
			}
			
			
			
			//obj传入：
			//folder:点击左侧“我的文件夹”触发事件；mlabel：点击左侧“我的标签”触发事件
			function changePlus(obj){
				var mobj=obj+"Img";
				var imgsrc=$("#"+mobj).attr("src");
				var myobj=$("."+obj);
				if(myobj.length>0){
				if(imgsrc.indexOf("minus.gif")>0){
					$("#"+mobj).attr("src",ctx+"/resources/ckfinder/skins/kama/images/ckfplus.gif");
					$("."+obj).each(function (){
						$(this).hide();
					});
				}else{
					$("#"+mobj).attr("src",ctx+"/resources/ckfinder/skins/kama/images/ckfminus.gif");
					$("."+obj).each(function (){
						$(this).show();
					});
				}
				}
			}
			
			//obj传入：
			//mlabel：标记为“新建标签”后左侧导航触发事件；folder:移动到“新建文件夹”后左侧导航触发事件
			function changePlusForAdd(obj){
				var mobj=obj+"Img";
				var imgsrc=$("#"+mobj).attr("src");
				var myobj=$("."+obj);
				if(myobj.length>0){
				if(imgsrc.indexOf("minus.gif")>0){
					
				}else{
					$("#"+mobj).attr("src",ctx+"/resources/ckfinder/skins/kama/images/ckfminus.gif");
					$("."+obj).each(function (){
						$(this).show();
					});
				}
				}
			}
			
			function mailLink(tabid, text, url){
				window.location.href=url;
			}
			
			function ajaxMailContent(moduleId,title){
				pageLock("show");
				$.ajax({
					type:"post",
					async:false,
					data : {"uuid":moduleId,"type":"Id"},
					url:ctx+"/cms/cmspage/viewcontent",
					success:function(result){
						$("#"+window.dnrwId+" ul li a" ).text(title);
						$("#"+window.dnrwId+" div div").attr("moduleid",result.split("*#")[0]);
						$("#"+window.dnrwId+" .viewContent").parent().html(result.split("*#")[1]);
						//格式化时间
						formDate();
						if(title=="附件夹"){
							$("#"+window.dnrwId+ " .jspVerticalBar").remove();
							jsmod("#"+window.dnrwId + " .body_middle");
							
						}else{
							jsmod("#"+window.dnrwId + " .tab-content");
						}
						pageLock("hide");
					}
				});
			}
			function refreshMailLive(element){
				var id_ = element.parents(".active").attr("moduleid");
				var url = $("#"+id_).attr("url");
				var title = $("#"+id_).attr("name_");
//				pageLock("show");
				$.ajax({
					type:"post",
					async:false,
					url:ctx+url,
					success:function(result){
						result = result.substring(result.indexOf("<body>")+6,result.indexOf("</body>"));
						$("#"+window.dnrwId+" ul li a" ).text(title);
						$("#"+window.dnrwId+" div div").attr("moduleid",id_);
						$("#"+window.dnrwId+" div div").html(result);
						//格式化时间
						formDate();
//						pageLock("hide");
					}
				});
			}
			//选择用户
			var mailusername,mailuserid,mailconfig;
			function openMailUser(lname,lid,mconfig){
				mailusername=lname;
				mailuserid=lid;
				mailconfig=mconfig;
			var ln=$("#"+lname).val();
				
				var ld=$("#"+lid).val();
				var ltemp=lname+"_temp";
				var lln=$("#"+ltemp).val();
				$("#"+lid).val(getuserids(ln,lln,ld));
				openMUser(lname,lid);
				
				}
			//选择用户
			function openMUser(lname,lid){
				$.unit.open({
					title : global.selectUser,
					 afterSelect : afterSelectMailUser,
					labelField : lname,
					valueField : lid,
					selectType : 4
				});
			}
			String.prototype.replaceAll  = function(s1,s2){    
				return this.replace(new RegExp(s1,"gm"),s2);    
				};



			//用户选择后处理
			function afterSelectMailUser(returnValue) {
				//var id = returnValue.id;
				//var name = returnValue.name;
				//oAlert("returnValue.name=="+returnValue.name);
				//oAlert("returnValue.name=="+returnValue.id);
				var ids=returnValue.id.split(';');
				var names=returnValue.name;
				var mnames=$("#"+mailusername).val();
				//names=names.replace(',',';');
				mnames=mnames.replace(names,'');
				var realnames='';
				var mynames=returnValue.name.split(';');
				for(var i=0;i<ids.length;i++){
					getMailUserNo(ids[i],mailusername,mnames,names,mynames[i],mailconfig);
				}
				
				//formatMail(mailusername,mailconfig);
				}
			//获得用户登录用户名
			function getMailUserNo(userid,mailusername,mnames,names,relname,mailconfig){
				$.ajax({
					type : "POST",
					url : ctx+"/mail/getUserNameById.action?userid="+userid,
					//data : "userid="+userid,
					dataType : "text",
					success : function callback(result) {
						var realnames=relname+"<"+result+"@"+$("#"+mailconfig).val()+">;";
						var nn=$("#"+mailusername).val().replace(names,'');
						$("#"+mailusername).val(nn+realnames);
					},
					error: function (data, status, e)
					{
						//oAlert(e);
					}
				});
			}


			//分别发送
			function changMailType(){
				var ggmail=$("#ggmail").attr("checked");
				if(ggmail=='checked'){
					$("#ffmail").attr("checked",false);
				}
				var ffmail=$("#ffmail").attr("checked");
				if(ffmail=='checked'){
					$("#ffshow").hide();
				}else{
					$("#ffshow").show();
				}
				
			}
			//分别发送
			function changMailDs(){
				var ggmail=$("#ddmail").attr("checked");
				if(ggmail=='checked'){
					$("#nymdhs_span").show();
				}else{
					$("#nymdhs_span").hide();
				}
			}

			//添加最经联系人
			function addContactor(){
				var anames=$("#anames").val();
				if(anames!=''){
				$.ajax({
					type : "POST",
					url : ctx+"/mail/add_contactor.action",
					data : "anames="+anames,
					dataType : "text",
					success : function callback(result) {
						queryContactor();
						closeLayer();
					}
				}); 
				}else{
					oAlert(global.lastContactorNotNull);
				}
			}

			//查询最经联系人
			function queryContactor(){
				var cname=$("#cname").val();
				$.ajax({
					type : "POST",
					url : ctx+"/mail/query_contactor.action",
					data : "cname="+cname,
					dataType : "json",
					success : function callback(result) {
						var s="";
						for(var i=0;i<result.length;i++){
							s+="<li><a href='#' ondblclick=\"setToMail('"+result[i].mailName+"');\"> "+result[i].mailName.substring(0,10)+" </a></li>";
						}
						$("#treeContactor").html(s);
					}
				}); 
			}
			
			
			//定时发送
			function sdcheck(){
				
				var ddate=$("#selddate").val();
				var ar=ddate.split(" ");
				 var arr = ar[0].split("-");
				 var brr = ar[1].split(":");
				    var starttime = new Date(arr[0], arr[1], arr[2],brr[0],brr[1]);
				    var starttimes = starttime.getTime();
				    var ERP_TIME = new Date();
				    var lktime = new Date(ERP_TIME.getYear(),(ERP_TIME.getMonth()+1),ERP_TIME.getDate(),ERP_TIME.getHours(),ERP_TIME.getMinutes());
				    var lktimes = lktime.getTime();
				    
				    

				if (starttimes <= lktimes) {
				    	$("#msg").html(global.dingshiTimeOut);
						return false;
				}
				$("#msg").html("");
				if(check('-1')){
					$("#nymdhs").val($("#selddate").val());
					closeLayer();
					document.form.submit();
					return true;
				}
				return false;
				
			}
			function openSDLayer(objId,conId){
				var ds=$("#ds").attr("checked");
				if(ds=='checked'){
				openLayer(objId,conId);
				var ddate=$("#nymdhs").val();
				$("#selddate").val(ddate);
				changeSD();
				}
			}
			function changeSD(){
				
				 var ddate=$("#selddate").val();
				$("#showddate").html(ddate);
				
			}
			//定时器修改时间
			function changeSD2(year){
				
				var nyear=$("#selyear").val();
				var nmonth=$("#selmonth").val();
				var nday=$("#selday").val();
				var nhour=$("#selhour").val();
				var nsec=$("#selsec").val();
				var h=parseInt(nhour,10);
				var str="";
				if(h>=4&&h<10){
					str = str+" "+global.zaoshang;
				}else if(h>=10&&h<13){
					str = str+" "+global.zhongwu;
				}else if(h>=13&&h<18){
					str = str+" "+global.xiawu;
				}else if(h>=18&&h<24){
					str = str+" "+global.wanshang;
				}else if(h>=24||h<4){
					str = str+" "+global.linceng;
				}
				if(year!=nyear){
					$("#showyear").html(nyear+global.year);
					$("#showyear").show();
				}else{
					$("#showyear").hide();
				}
				$("#showmonth").html(nmonth+global.month);
				$("#showday").html(nday+global.ri);
				
				$("#showhour").html(str+" "+nhour);
				$("#showsec").html(nsec);
			}
			
			//mail_box_view2
			
			function openSdLayer(uuid,objId,conId){
				openLayer(objId,conId);
				var nymdhs=$("#showsdate").html();
				oAlert(nymdhs);
				oAlert(uuid);
				nymdhs=trim(nymdhs);
				var nys=nymdhs.split(' ');
				$("#muuid").val(uuid);
				$("#selddate").val(nys[0]);
				$("#seldtime").val(nys[1]);
				$("#showddate").html(nys[0]);
				$("#showdtime").html(nys[1]);
			}


			//移到发件箱
			function moveInMailBox(rel,pageNo,mtype,uuid,ctype,mailname,ctype){
					if(confirm(global.operationConfirm)) {
						if(mtype=='') mtype=0;
						$.ajax({
							type : "POST",
							url : ctx+"/mail/mailbox_move.action",
							data : "rel="+rel+"&pageNo="+pageNo+"&mtype="+mtype+"&selected="+uuid+"&ctype="+'3'+"&mailname="+mailname,
							dataType : "text",
							success : function callback(result) {
								returnWindow();
								window.close();

							}
						});
						/* $.ajax({
							type : "POST",
							url : "${ctx}/mail/mailbox_move.action",
							data : "pageNo="+pageNo+"&mtype="+mtype+"&selected="+uuid+"&ctype="+ctype,
							dataType : "text",
							success : function callback(result) {
								oAlert("删除成功！");
								 var listView=$("#listView").val();
								var nextUuid=$("#nextUuid").val();
								if(listView=='1'&&nextUuid!=''){
									var allUuid=$("#allUuid").val();
									//oAlert(allUuid);
									//oAlert(allUuid2);
									//oAlert("${ctx}/mail/mailbox_view2.action?rel="+rel+"&mailname="+mailname+"&uuid="+nextUuid+"&pageNo="+pageNo+"&mtype="+mtype+"&allUuid="+allUuid2);
									window.location.href="${ctx}/mail/mailbox_view2.action?rel="+rel+"&mailname="+mailname+"&uuid="+nextUuid+"&pageNo="+pageNo+"&mtype="+mtype+"&allUuid="+allUuid;
								}else{
								window.location.href="${ctx}/mail/mail_box_list.action?rel="+rel+"&pageNo="+pageNo+"&mtype="+mtype+"&mailname="+mailname+"&ctype="+ctype;
								} 
							}
						}); */
				}
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
			//删除邮件
			function deleInMailBox(rel,pageNo,mtype,uuid,mailname,ctype) {
				if(mtype=="") mtype="0";
					if(confirm(global.deleteConfirm)) {
							$.ajax({
								type : "POST",
								url : ctx+"/mail/mail_box_cremove.action",
								data : "mids="+uuid+"&pageNo="+pageNo+"&mtype="+mtype,
								dataType : "text",
								success : function callback(result) {
									returnWindow();
									window.close();
									/* var listView=$("#listView").val();
									var nextUuid=$("#nextUuid").val();
									if(listView=='1'&&nextUuid!=''){
										var allUuid=$("#allUuid").val();
										window.location.href="${ctx}/mail/mailbox_view2.action?rel="+rel+"&mailname="+mailname+"&uuid="+nextUuid+"&pageNo="+pageNo+"&mtype="+mtype+"&allUuid="+allUuid;
									}else{
									window.location.href="${ctx}/mail/mail_box_list.action?rel="+rel+"&pageNo="+pageNo+"&mtype="+mtype+"&mailname="+mailname+"&ctype="+ctype;
									} */
							}});
					}
				
			}
			//置已读未读
			function readInMailBox(rel,rtype,pageNo,mtype,uuid,mailname,ctype){
					if(confirm(global.operationConfirm)) {
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
								//window.location.href=ctx+"/mail/send_mail.action?pageNo="+pageNo+"&mtype="+mtype;
							}
						});
				}
					//window.location.href=ctx+"/mail/toReadOrNot.action?pageNo="+pageNo+"&mtype="+mtype+"&rtype="+rtype+"&selected="+selected;
			}

			//添加文件夹
			function addInFolder(){
				var rel=$("#inrel").val();
				var pageNo=$("#inpageNo").val();
				var mtype=$("#inmtype").val();
				var mailname=$("#inmailname").val();
				var mailname=$("#inmailname").val();
				var uid=$("#inmailUuid").val();
				var fname=$("#fname").val();
				var uuid=$("#uuid").val();
				if(fname==''){
					oAlert(global.folderNameNotNull);
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
							oAlert(global.folderNameIsExist);
						}else{
							closeDialog();
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

			//添加标签	
			function addMailListLabelIn(){
				var rel=$("#inrel").val();
				var pageNo=$("#inpageNo").val();
				var mtype=$("#inmtype").val();
				var mailname=$("#inmailname").val();
				var mailname=$("#inmailname").val();
				var uid=$("#inmailUuid").val();
				var fname=$("#fname").val();
				var uuid=$("#uuid").val();
				if(fname==''){
					oAlert(global.labelNameNotNull);
					fname.focus();
					return false;
				}
				$.ajax({
					type : "POST",
					url : ctx+"/mail/add_label.action",
					data : "uuid="+uuid+"&fname="+fname+"&status=2",
					dataType : "text",
					success : function callback(result) {
						if(result.indexOf("error")>-1){
							oAlert(global.labelNameIsExist);
						}else{
							closeDialog();
							var allUuid=$("#allUuid").val();
							window.location.href=ctx+"/mail/mailbox_view2.action?rel="+rel+"&mailname="+mailname+"&uuid="+mailUuid+"&pageNo="+pageNo+"&mtype="+mtype+"&allUuid="+allUuid;
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
			
			
			//收邮件
			function drawMail(uuid,str){
				var rel="";
				$.ajax({
					type : "POST",
					url : ctx+"/mail/draw_mail.action",
					data : "uuid="+uuid+"&mails="+str,
					dataType : "text",
					success : function callback(result) {
						oAlert(result);
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
				    	$("#msg").html(global.dingshiTimeOut);
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
			
			//mail_file_manage
			//展示所选
			function showCheck(fileid){
				var ff5=fileid+'6';
				$("#"+fileid).show();
				$("#"+ff5).show();
			}
			//隐藏所选
			function hideCheck(fileid){
				var ff5=fileid+'6';
				if($("#"+fileid).attr("checked")!='checked'){
				$("#"+fileid).hide();
				$("#"+ff5).hide();
				}
			}
			//选择
			function fselect(fileid){
				var ff=fileid+'2';
				var ff5=fileid+'6';
				$("#"+ff).toggleClass("active2");
				var ff1=fileid;
				if($("#"+ff1).attr("checked")=='checked'){
					$("#"+ff1).hide();
					$("#"+ff5).hide();
					$("#"+ff1).attr("checked",false);
				}else{
					$("#"+ff1).show();
					$("#"+ff5).show();
					$("#"+ff1).attr("checked",true);
				}
				
			}
			//设置文件收藏
			function setFileStar(fileId){
				var ff=fileId+"5";
				var fsrc=$("#"+ff).attr("src");
				var ftype=0;
				if(fsrc.indexOf("star-off")>0){
					ftype=1;
				}else{
					ftype=0;
				}
				$.ajax({
					type : "POST",
					url : ctx+"/mail/file_del.action",
					data : "fuids="+fileId+"&ftype="+ftype,
					dataType : "text",
					success : function callback(result) {
						if(fsrc.indexOf("star-off")>0){
							$("#"+ff).attr("src",ctx+'/pt/mail/js/star-on.png');
						}else{
							$("#"+ff).attr("src",ctx+'/pt/mail/js/star-off.png');
						}
					}
				});
			}
			//全选
			function getAllChecked(){
				var fids="";
				$(":checked").each(function (){
					var id=$(this).attr("id");
					fids+=id+";";
				});
				return fids;
			}
			//全选2
			function getAllChecked2(){
				var fnames="";
				var fids="";
				$(":checked").each(function (){
					var id=$(this).attr("id");
					if(id != "undefined" && id != undefined){
						var ff3=id+"3";
						var ff4=id+"4";
						fids+=$("#"+ff4).val()+";";
						fnames+=$("#"+ff3).val()+";";
					}
				});
				return fnames+","+fids;
			}
			
			//转发
			function fileSendOther2(){
				var files=getAllChecked2();
				if(files!=','){
					window.location.href=ctx+"/mail/file_send_other.action?files="+files;
				}else{
					oAlert(global.selectARecord);
				}
			}
			//下载
			function fdownload(){
				var fids=getAllChecked2();
				if(fids==','){
					oAlert(global.selectARecord);
				}else{
					window.location.href=ctx+'/mail/downLoad_allfile.action?fuids='+fids;
				}
			}
			//收藏
			function fstar(){
				var fids=getAllChecked();
				if(fids==''){
					oAlert(global.selectARecord);
				}else{
					$.ajax({
						type : "POST",
						url : ctx+"/mail/file_del.action",
						data : "fuids="+fids+"&ftype=1",
						dataType : "text",
						success : function callback(result) {
							$(":checked").each(function (){
								var id=$(this).attr("id");
								var ff3=id+"5";
								$("#"+ff3).attr("src",ctx+'/pt/mail/js/star-on.png');
							});
						}
					});
				}
			}
			
			//查找
			function find(){
				var ftype=$("#ftype").val();
				var findName=$("#findName").val();
				window.location.href=ctx+'/mail/file_list.action?curPage=0&ftype='+ftype+"&findName="+findName;
			}
			//查找2
			function find2(ftype,findName){
				$.ajax({
					type : "POST",
					url : ctx+"/mail/file_list.action",
					data : "curPage=0&ftype="+ftype+"&findName="+findName,
					dataType : "text",
					success : function callback(result) {
						//只截取<body></body>里面的内容
						result = result.substring(result.indexOf("<body>")+6,result.indexOf("</body>"));
						$("#"+window.dnrwId+" div div").html(result);
					}
				});
//				window.location.href=ctx+'/mail/file_list.action?curPage=0&ftype='+ftype+"&findName="+findName;
				
			}
			//页面加载
			function fileonload(){
				var ftype=$("#ftype").val();
				if(ftype==''){
					$("#showmenu").hide();
				}else{
					$("#showmenu").show();
					if(ftype=='star'){
						$("#vtypespan").html(global.star);
					}
					if(ftype=='img'){
						$("#vtypespan").html(global.img);
					}
					if(ftype=='word'){
						$("#vtypespan").html(global.word);
					}
					if(ftype=='mp3'){
						$("#vtypespan").html(global.mp3);
					}
					if(ftype=='avi'){
						$("#vtypespan").html(global.avi);
					}
					if(ftype=='rar'){
						$("#vtypespan").html(global.rar);
					}
				}
			}
			
			//mail_group_view
			//移到发件箱
			function moveInMailBox(rel,pageNo,mtype,uuid,ctype,mailname){
				if(confirm(global.operationConfirm)) {
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
								//oAlert("${ctx}/mail/view_group_mail.action?uuid="+nextUuid+"&rel="+rel+"&pageNo="+pageNo+"&mtype="+mtype+"&allUuid="+allUuid);
								window.location.href=ctx+"/mail/view_group_mail.action?uuid="+nextUuid+"&rel="+rel+"&pageNo="+pageNo+"&mtype="+mtype+"&allUuid="+allUuid;
							}else{
								window.location.href=ctx+"/mail/mail_box_list.action?rel="+rel+"&pageNo="+pageNo+"&mtype="+mtype+"&mailname="+mailname;
							}
						}
					});
				}
				//window.location.href=ctx+"/mail/toMove.action?pageNo="+pageNo+"&mtype="+mtype+"&selected="+selected;
			}
			//详情页删除邮件（cms）
			function moveInMailBoxCms(uuid,ctype){
				var obj = new Object(); 
				obj.uuid = uuid;
				obj.ctype = ctype;
				oConfirm ("确认删除吗？",moveInMailBoxCms1,obj,"");
			}
			function moveInMailBoxCms1(obj){
				var uuid = obj.uuid;
				var ctype = obj.ctype;
				
				var uuids = new Array();
				uuids.push(uuid);
				$.ajax({
					type : "POST",
					url : ctx+"/mail/mail_move.action",
					data : "uuids="+uuids+"&ctype="+ctype,
					dataType : "text",
					success : function() {
						oAlert(global.success);
						returnWindow();
						window.close();
					}
				});
			}
			//详情页彻底删除邮件（cms）
			function deleInMailBoxCms(uuid){
				var obj = new Object(); 
				obj.uuid = uuid;
				oConfirm ("确认彻底删除吗？",deleInMailBoxCms1,obj,"");
			}
			function deleInMailBoxCms1(obj){
				var uuid = obj.uuid;
				
				var uuids = new Array();
				uuids.push(uuid);
				$.ajax({
					type : "POST",
					url : ctx+"/mail/mail_delete.action",
					data : "uuids="+uuids,
					dataType : "text",
					success : function() {
						oAlert(global.success);
						returnWindow();
						window.close();
					}
				});
			}
			
			
			//删除邮件
			function deleInMailBox(rel,pageNo,mtype,uuid,mailname) {
				if(mtype=="") mtype="0";
					if(confirm(global.deleteConfirm)) {
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
										//oAlert("${ctx}/mail/view_group_mail.action?uuid="+nextUuid+"&rel="+rel+"&pageNo="+pageNo+"&mtype="+mtype+"&allUuid="+allUuid2);
										window.location.href=ctx+"/mail/view_group_mail.action?uuid="+nextUuid+"&rel="+rel+"&pageNo="+pageNo+"&mtype="+mtype+"&allUuid="+allUuid;
									}else{
									window.location.href=ctx+"/mail/mail_box_list.action?rel="+rel+"&pageNo="+pageNo+"&mtype="+mtype+"&mailname="+mailname;
									}
							}});
					}
				
			}
			//置已读未读
			function readInMailBox(rel,rtype,pageNo,mtype,uuid,mailname,ctype){
					if(confirm(global.operationConfirm)) {
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
										window.location.href=ctx+"/mail/view_group_mail.action?uuid="+nextUuid+"&rel="+rel+"&pageNo="+pageNo+"&mtype="+mtype+"&allUuid="+allUuid;
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
								//window.location.href=ctx+"/mail/send_mail.action?pageNo="+pageNo+"&mtype="+mtype;
							}
						});
				}
					//window.location.href=ctx+"/mail/toReadOrNot.action?pageNo="+pageNo+"&mtype="+mtype+"&rtype="+rtype+"&selected="+selected;
			}
			//转发
			function toSendOtherGInMailBox(rel,pageNo,mtype,isSend,uuid){
				if(mtype=='') mtype=0;
							window.location.href=ctx+"/mail/mailbox_send_otherg.action?rel="+rel+"&isSend="+isSend+"&pageNo="+pageNo+"&mtype="+mtype+"&selected="+uuid;
			}
			//回复
			function toReplyGInMailBox(rel,pageNo,mtype,isSend,uuid){
				if(mtype=='') mtype=0;
							window.location.href=ctx+"/mail/mailbox_send_greply.action?rel="+rel+"&isSend="+isSend+"&pageNo="+pageNo+"&mtype="+mtype+"&selected="+uuid;
			}

			function switchLogBar(){
			    var logt=$("#showlog:visible");
			if(logt.length <= 0){
				$("#showlog").show(); 
			}else{
				$("#showlog").hide();
			}
			}
			
			//mail_other_list
			function receive(uuid,username,pass,pop3,pop3Port,imap,imapPort){
				$("#msg").html(global.getting+"<img id=\"loading\" src=\""+ctx+"/pt/mail/js/loading.gif\">");
				$.ajax({
					type : "POST",
					url : ctx+"/mail/mailother_receive.action",
					data : "username="+username+"&pass="+pass+"&pop3="+pop3+"&pop3Port="+pop3Port+"&imap="+imap+"&imapPort="+imapPort,
					dataType : "text",
					success : function callback(result) {
						var res=result.split("#");
						var noread=uuid+"noread";
						var total=uuid+"total";
						$("#"+noread).html(res[1]);
						$("#"+total).html(res[0]);
						$("#msg").html("");
						oAlert(global.getSuccess);
					},
					error: function (data, status, e)
					{
						$("#msg").html(global.getError);
					}
				});
			}
			
			  //读取其他邮件
		    function read_othermail(othermailname){
		    	$.ajax({
					type : "POST",
					url : ctx+"/basicdata/dyview/view_show",
					data : "viewUuid=2a201893-761b-4bfd-b2bd-61bc5e1317a0"+"&status_="+othermailname,
					dataType : "text",
					success : function callback(result) {
						 $("#20136315910846 div div").html(result);
					}
				});
		    };
			
			function OtherAddCancel(){
//				window.location.href=ctx+"/mail/mail_pconfig.action?ptype=3";
				refreshWindow($(".box"));
				refreshWindow($(".writeMail"));
			}
			
			
			
			
			function toTip(inp){
				var word="";
				var to=$(inp).val();
				if(to!=''&&!endWith(to,";")){
					
					if(to.indexOf(";")<0){
						word=to;
					}else{
						word=to.substring(to.lastIndexOf(";")+1,to.length);
					}
					
				}
				return word;
			}
			
			//撤回邮件
			function chehuiMail(sendDate,uuid,to,cc,bcc,mailconfig,rel ,pageNo,mtype,mailname ){
				var html1 = '<div  id="chehui"><table style="border: 1px;">'+
					'<tr><td><strong>&nbsp;&nbsp;&nbsp;&nbsp;'+global.chehui1+'</strong></td></tr>'+
					'<tr><td>&nbsp;&nbsp;&nbsp;&nbsp;'+global.chehui2+'</td></tr>'+
					'<tr><td style="color: gray;">'+global.chehui3+'</td></tr><tr>'+
					'<td style="color: gray;">&nbsp;&nbsp;&nbsp;&nbsp;'+global.chehui4+'</td></tr>'+
					'<tr><td style="color: gray;">&nbsp;&nbsp;&nbsp;&nbsp;'+global.chehui5+'</td></tr>'+
					'<tr><td style="color: gray;">&nbsp;&nbsp;&nbsp;&nbsp;'+global.chehui6+'</td></tr>'+
					'</table></div>';

			var html2 = '<div >' +
			    '<p>'+global.chehui7+'</p><br/><div>'+global.chehui8+'</div>' +
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
				'<tr><td colspan="2"><strong>&nbsp;&nbsp;&nbsp;&nbsp;<img src="'+ctx+'/pt/mail/js/loading.gif"/>'+global.chehuiing+'</strong></td></tr>';
				var str="";
				if(to!=''&&to!=null){
					var tos=to.split(";");
					for(var i=0;i<tos.length;i++){
						if(tos[i]!=''&&tos[i].indexOf(mailconfig)>0){
							str+=tos[i]+";";
							tos[i]=tos[i].replace("<","&lt;");
							tos[i]=tos[i].replace(">","&gt;");
							html3+='<tr><td width="50%">&nbsp;&nbsp;&nbsp;&nbsp;'+tos[i]+'</td><td align="right" width="50%">'+global.chehuiing+'</td></tr>';
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
							html3+='<tr><td width="50%">&nbsp;&nbsp;&nbsp;&nbsp;'+tos[i]+'</td><td align="right" width="50%">'+global.chehuiing+'</td></tr>';
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
							html3+='<tr><td width="50%">&nbsp;&nbsp;&nbsp;&nbsp;'+tos[i]+'</td><td align="right" width="50%">'+global.chehuiing+'</td></tr>';
							
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
			   			//oAlert(rel);
			   			var html4 = '<div  ><table border="1px" width="100%" style="border: 1px;">'+
			   		   	'<tr><td colspan="2"><strong>&nbsp;&nbsp;&nbsp;&nbsp;'+global.chehuiComplete+'</strong></td></tr>';
			   		   	
			   		        var ress=rel.split("|");
			   		        for(var j=0;j<ress.length;j++){
			   		        	if(ress[j]!=''&&ress[j]!=null){
			   		        	var res=ress[j].split("#");
			   		        	var jieguo="";
			   		        	if(res[1]=='1'){
			   		        		jieguo=global.jieguo1;
			   		        	}
			   		        	if(res[1]=='2'){
			   		        		jieguo='<font color="red">'+global.jieguo2+'</font>';
			   		        	}
			   		        	if(res[1]=='3'){
			   		        		jieguo='<font color="red">'+global.jieguo3+'</font>';
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


			$.jBox.open(states, global.chehuiMail, 450, 'auto');

			}
			
			
			
			
			 
			//更新标签
				function updateColor(uuid,color){
					$.ajax({
						type : "POST",
						url : ctx+"/mail/update_color.action",
						data : "uuid="+uuid+"&color="+color,
						dataType : "text",
						success : function callback(result) {
							  var u=uuid+"name22";
						      $("#"+u).css("background-color",color);
						}
					});
				}
				
				
//				 var ocolorPopup = window.createPopup();
				 var ecolorPopup=null;

				 function colordialogmouseout(obj){
				   obj.style.borderColor="";
				   obj.bgColor="";
				 }

				 function colordialogmouseover(obj){
				   obj.style.borderColor="#0A66EE";
				   obj.bgColor="#EEEEEE";
				 }

				 function colordialogmousedown(color,uuid){

				 updateColor(uuid,color);
				   //ecolorPopup.value=color;
				  // oAlert(color);
				   //document.body.bgColor=color;
				   ocolorPopup.document.body.blur();
				 }

				 function colordialogmore(){
				   var sColor=dlgHelper.ChooseColorDlg(ecolorPopup.value);
				   sColor = sColor.toString(16);
				   if (sColor.length < 6) {
				       var sTempString = "000000".substring(0,6-sColor.length);
				       sColor = sTempString.concat(sColor);
				   }
				   ecolorPopup.value="#"+sColor.toUpperCase();
				   //document.body.bgColor="#"+sColor.toUpperCase();
				   ocolorPopup.document.body.blur();
				 }

				 function selLabelColor(uuid){
				 	var u=uuid+"name22";
				   //var e=event.srcElement;
				   var e=$("#"+u);
				   //oAlert(e);
				   e.onkeyup=selLabelColor;
				   ecolorPopup=e;
				   var ocbody;
				   var oPopBody = ocolorPopup.document.body;
				   var colorlist=new Array(40);
				   oPopBody.style.backgroundColor = "#f9f8f7";
				   oPopBody.style.border = "solid #999999 1px";
				   oPopBody.style.fontSize = "12px";

				   colorlist[0]="#000000";    colorlist[1]="#993300";    colorlist[2]="#333300";    colorlist[3]="#003300";
				   colorlist[4]="#003366";    colorlist[5]="#000080";    colorlist[6]="#333399";    colorlist[7]="#333333";

				   colorlist[8]="#800000";    colorlist[9]="#FF6600";    colorlist[10]="#808000";colorlist[11]="#008000";
				   colorlist[12]="#008080";colorlist[13]="#0000FF";colorlist[14]="#666699";colorlist[15]="#808080";

				   colorlist[16]="#FF0000";colorlist[17]="#FF9900";colorlist[18]="#99CC00";colorlist[19]="#339966";
				   colorlist[20]="#33CCCC";colorlist[21]="#3366FF";colorlist[22]="#800080";colorlist[23]="#999999";

				   colorlist[24]="#FF00FF";colorlist[25]="#FFCC00";colorlist[26]="#FFFF00";colorlist[27]="#00FF00";
				   colorlist[28]="#00FFFF";colorlist[29]="#00CCFF";colorlist[30]="#993366";colorlist[31]="#CCCCCC";

				   colorlist[32]="#FF99CC";colorlist[33]="#FFCC99";colorlist[34]="#FFFF99";colorlist[35]="#CCFFCC";
				   colorlist[36]="#CCFFFF";colorlist[37]="#99CCFF";colorlist[38]="#CC99FF";colorlist[39]="#FFFFFF";

				   ocbody = "";
				   ocbody += "<table CELLPADDING=0 CELLSPACING=3>";
				   ocbody += "<tr height=\"20\" width=\"20\"><td align=\"center\"><table style=\"border:1px solid #808080;\" width=\"12\" height=\"12\" bgcolor=\""+e.value+"\"><tr><td></td></tr></table></td><td bgcolor=\"eeeeee\" colspan=\"7\" style=\"font-size:12px;\" align=\"center\">当前颜色</td></tr>";
				   for(var i=0;i<colorlist.length;i++){
				       if(i%8==0)
				           ocbody += "<tr>";
				       ocbody += "<td width=\"14\" height=\"16\" style=\"border:1px solid;\" onMouseOut=\"parent.colordialogmouseout(this);\" onMouseOver=\"parent.colordialogmouseover(this);\" onMouseDown=\"parent.colordialogmousedown('"+colorlist[i]+"','"+uuid+"')\" align=\"center\" valign=\"middle\"><table style=\"border:1px solid #808080;\" width=\"12\" height=\"12\" bgcolor=\""+colorlist[i]+"\"><tr><td></td></tr></table></td>";
				       if(i%8==7)
				           ocbody += "</tr>";
				   }
				   ocbody += "<tr><td align=\"center\" height=\"22\" colspan=\"8\" onMouseOut=\"parent.colordialogmouseout(this);\" onMouseOver=\"parent.colordialogmouseover(this);\" style=\"border:1px solid;font-size:12px;cursor:default;\" onMouseDown=\"parent.colordialogmore()\">其它颜色</td></tr>";
				   ocbody += "</table>";

				   oPopBody.innerHTML=ocbody;
				  // oAlert(e.offsetLeft+","+(e.offsetTop+e.offsetHeight));
				   //ocolorPopup.show(e.offsetLeft, e.offsetTop+e.offsetHeight, 158, 147, e);
				   var offset = e.offset();
				 //oAlert(offset.left+","+(offset.top+offset.left));
				 var left=offset.left;
				 var top=offset.top+offset.left;
				   ocolorPopup.show(left, top, 158, 147, document.body);
				 }
				 
				 
				 
				//选择
				 function setSelThis3(obj,color){
				 	$(obj).css('background-color',color);
				 }
				 
				//获取url参数（cms）
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
				 //移动邮件（cms）
					function moveMailBoxCms(rel,pageNo,mtype,ctype,mailname,obj){
						
						var arrayObj = readParam();
						var uuids = new Array();
						for (var i=0;i<=arrayObj.length-1;i++) { 
							var uuid = arrayObj[i]["uuid"];
							uuids.push(uuid);
						}
						if(mtype=='') mtype=0;
						if (uuids!=''||uuids.length>0) {
								$.ajax({
									type : "POST",
									url : ctx+"/mail/mailbox_move_cms.action",
									data : "rel="+rel+"&pageNo="+pageNo+"&mtype="+mtype+"&uuids="+uuids+"&ctype="+ctype+"&mailname="+mailname,
									dataType : "text",
									success : function callback(result) {
//										window.location.href=ctx+"/mail/mail_box_list.action?rel="+rel+"&pageNo="+pageNo+"&mtype="+mtype+"&ctype="+ctype+"&mailname="+mailname;
										var moduleid = "";
										if(obj=="customButton_group_button"){
//											moduleid = $("#20136315910846").find(".active").eq(1).attr("moduleid");
//											obj = $("#20136315910846").find(".active").find("#abc");
											moduleid = $(".customButton_group_button").eq(0).parents(".active").attr("moduleid");
											obj = $(".customButton_group_button").eq(0);
										}else{
											moduleid = $(obj).parents(".active").attr("moduleid");
										}
										//我的文件夹、其他邮件、标签刷新当前视图
										if(moduleid == "folder" || moduleid == "other" || moduleid == "mlabel"){
											refreshMailLive($(obj));
											refreshWindow($(".writeMail"));
										} else {
											refreshWindow($(obj));
											refreshWindow($(".writeMail"));
										}
									}
								});
							//window.location.href=ctx+"/mail/toMove.action?pageNo="+pageNo+"&mtype="+mtype+"&selected="+selected;
						} else {
							oAlert(global.uncheckedAnyMail);
						}
					}
					
					
				/*	//1=add 2=delete设置标签（cms）
					function setLabelCms(uuid,status,obj){
						updateLabelCms(uuid,status,obj);
					}
					
					//更新标签(cms)
					function updateLabelCms(uuid,status,obj){
						var arrayObj = readParam();
						var uuids = new Array();
						for (var i=0;i<=arrayObj.length-1;i++) { 
							var uuid1 = arrayObj[i]["uuid"];
							uuids.push(uuid1);
						}
						if (uuids!=''||uuids.length>0) {
							$.ajax({
								type : "POST",
								url : ctx+"/mail/update_label_cms.action",
								data : "uuid="+uuid+"&status="+status+"&uuids="+uuids,
								dataType : "text",
								success : function callback(result) {
									var moduleid = "";
									if(obj=="customButton_group_button"){
//										moduleid = $("#20136315910846").find(".active").eq(1).attr("moduleid");
//										obj = $("#20136315910846").find(".active").find("#abc");
										moduleid = $(".customButton_group_button").eq(0).parents(".active").attr("moduleid");
										obj = $(".customButton_group_button").eq(0);
									}else{
										moduleid = $(obj).parents(".active").attr("moduleid");
									}
									alert('moduleid:'+moduleid);
									//我的文件夹、其他邮件、标签刷新当前视图
									if(moduleid == "folder" || moduleid == "other" || moduleid == "mlabel"){
										refreshMailLive($(obj));
									} else {
										refreshWindow($(obj));
									}
									
									
									alert("更新标签后返回的结果："+result);
//									var uuids=selected.split("#");
//									for(var i=0;i<uuids.length;i++){
//										if(uuids[i]!=''){
//											var tdid=uuids[i]+'label';
//											var ress=result.split('|#|');
//											var txt1="<span id='"+uuids[i]+uuid+"' style='background-color: "+ress[1]+"'>"+ress[0]+"<a href='#' onclick=\"setLabel2('"+uuids[i]+"','"+uuid+"','2');\">"+global.deleteBtn+"</a></span>";   
//											$("#"+tdid).append(txt1);
//										}
//									}
								}
							});
						} else {
							oAlert("请选择一条记录");
						}
					}*/
					
					//其他邮件设置(cms)
					function configOthermail(str){
						$.ajax({
							type : "POST",
							url : ctx+"/mail/mailother_next22.action",
							data : "uuid="+str+"&delFlag=1",
							dataType : "text",
							success : function callback(result) {
								 $("#20136315910846 div div").html(result);
							}
						});
					}
						
					
var isIE = (navigator.appName.toLowerCase().indexOf('internet explorer')+1?1:0)
