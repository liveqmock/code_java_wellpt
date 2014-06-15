<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<style type="text/css">
#pageSizeSelect{
	width:60px;
}		
		
.finder-footer {
    color: #434343;
    display: block;
    font-size: 12px;
    height: 25px;
    margin-left: 9px;
    margin-bottom: 10px;
}
.page_ {
	border: 1px none;
    color: #000000;
    float: left;
    font-size: 12px;
    height: 20px;
    margin: 0 5px;
    margin-top: 1px;
    padding-top: 0;
    text-align: center;
}
.txt_page_no {
    cursor: pointer;
    float: left;
    font-size: 12px;
    height: 19px;
    margin:0 5px;
    text-align: center;
    display: none;
    width: 12px;
}

.txt_page {
	cursor:pointer;
    float: left;
    font-size: 12px;
    height: 19px;
    margin-left: 0;
    text-align: center;
    width: 12px;
    margin: 0 5px;
    display: none;
}

.txt_page_ {
	cursor:pointer;
    float: left;
    font-size: 12px;
    height: 19px;
    margin-left: 0;
    text-align: center;
    width: 12px;
}

.writ_page {
	 color: #666666;
    float: left;
    font-size: 12px;
    margin-left: 5px;
    margin-right: 5px;
}

.nub_page {
    color: #0F599C;
    cursor: pointer;
    float: left;
    font-size: 12px;
    height: 18px;
    margin-top: 1px;
    padding: 1px 6px;
    text-align: center;
    margin: 0 1px;
/*     background: none repeat scroll 0 0 #F1F4F5; */
}
.nub_page:hover{
	background: none repeat scroll 0 0 #0F599C;
    color: #FFFFFF;
}
.nub_page_ {
    background: none repeat scroll 0 0 #0F599C;
    color: #FFFFFF;
    cursor: pointer;
    float: left;
    font-size: 12px;
    height: 18px;
    margin: 0 1px;
    padding: 1px 6px;
    text-align: center;
}

.input_text {
    border: 1px solid #D7D7D7;
    color: #666666;
    float: left;
    font-size: 12px;
    height: 16px;
    width: 20px;

}

.input_btn {
 background: none repeat scroll 0 0 #E2E8EB;
    border-color: #FFFFFF #BEC6CE #BEC6CE #FFFFFF;
    border-style: solid;
    border-width: 1px;
    color: #666666;
    cursor: pointer;
    float: left;
    font-size: 12px;
    margin-left: 5px;
    text-align: center;
	line-height:20px;
	height:20px;
	padding:0px;
}

.jumpmodule {
    float: left;
    font-size: 12px;
    margin-top: 1px;
    margin-left: 5px;
}
.jumpmodule #jumppage {
    height: 20px;
    margin: 0;
    padding: 0;
    width: 20px;
}
.jumpmodule .jump_btn {
    background: url("${ctx}/resources/theme/images/v1_icon.png") no-repeat scroll 0 -165px transparent;
    border: 1px solid #DEE1E2;
    color: #0F599C;
    font-size: 12px;
    height: 22px;
    padding: 2px 5px;
}

.lastpage {
    cursor: pointer;
    float: left;
    margin-right: 10px;
    margin-top: -1px;
    display: none;
}

.firstpage {
	cursor: pointer;
    float: left;
    margin-left: 10px;
    display: none;
}

.goClass {
	cursor: pointer;
    margin-left: 5px;
    margin-right: 5px;
    text-decoration: underline;
}

		</style>
		<script type="text/javascript">
		$(function(){
			$(".firstpage").live("mouseover",function(){
				if($(this).find("img").attr("src").indexOf("gray")>-1){
				}else{
					$(this).find("img").attr("src","${ctx}/resources/pt/images/v1_first_org.png");
				}
			});
			$(".firstpage").live("mouseout",function(){
				if($(this).find("img").attr("src").indexOf("gray")>-1){
				}else{
					$(this).find("img").attr("src","${ctx}/resources/pt/images/v1_first.png");
				}
			});
			$(".lastpage").live("mouseover",function(){
				if($(this).find("img").attr("src").indexOf("gray")>-1){
				}else{
					$(this).find("img").attr("src","${ctx}/resources/pt/images/v1_last_org.png");
				}
			});
			$(".lastpage").live("mouseout",function(){
				if($(this).find("img").attr("src").indexOf("gray")>-1){
				}else{
					$(this).find("img").attr("src","${ctx}/resources/pt/images/v1_last.png");
				}
			});
			$(".txt_page").live("mouseover",function(){
				if($(this).find("img").attr("src").indexOf("next")>-1){
					$(this).find("img").attr("src","${ctx}/resources/pt/images/v1_next_org.png");
				}else{
					$(this).find("img").attr("src","${ctx}/resources/pt/images/v1_prev_org.png");
				}
			});
			$(".txt_page").live("mouseout",function(){
				if($(this).find("img").attr("src").indexOf("next")>-1){
					$(this).find("img").attr("src","${ctx}/resources/pt/images/v1_next.png");
				}else{
					$(this).find("img").attr("src","${ctx}/resources/pt/images/v1_prev.png");
				}
			});
		});
		
    	function pagestate(){
    		var firstvew = document.getElementById("firstvew");
  			var backvew = document.getElementById("backvew");
  			var nextvew = document.getElementById("nextvew");
  			var lastvew = document.getElementById("lastvew");
  			var totlepage=document.getElementById("allpage").value;
  			var currentPage = document.getElementById("topage").value;
  			if(parseInt(currentPage)>=parseInt(totlepage)){
  			nextvew.style.display="none";
  			lastvew.style.display="none";
  			}
  			if(parseInt(currentPage)<=1){
    		firstvew.style.display="none";
  			backvew.style.display="none";			
  			}
    	}
    	/**到第几页回车相应事件**/
    	function entyKeyBoard(event,obj,currentPage){
    		var code = event.keyCode;
    	    if (code == 13) {
    	    	go("goto",currentPage,obj);
    	    }
    	}
    	
    	/**到第几页点击相应事件**/
		function gotopage_btn(currentPage,topage,obj){
			var parmStr = $(obj).parents("#abc").find("#parmStr").val(); 
			var parmStr2 = "";
			if(parmStr != undefined) {
				parmStr2 = parmStr.substring(1);
			}
			var pageSize = $(obj).parents("#abc").find("#pagesize").val();
			var mark = $(obj).parents("#abc").find("#mark").val();
			var viewUuid = $(obj).parents("#abc").find("#viewUuid").val();
			var params = {};
			if(mark == "viewSelect") {
				var keyWordsArray = new Array();
				var keyWordObj = new Object();
				var keyWord = "";
				var beginTime = "";
				var endTime = "";
				var searchField = "";
				if($(obj).parents("#abc").find("#keyWord").val() != undefined){
					keyWord =$(obj).parents("#abc").find("#keyWord").val(); 
					keyWord = keyWord.replace("关键字搜索","");
					keyWord = keyWord.replace(" ","");
					keyWordObj.all=keyWord;
					keyWordsArray.push(keyWordObj);
					
					beginTime =$(obj).parents("#abc").find("#beginTime").val(); 
					endTime =$(obj).parents("#abc").find("#endTime").val(); 
					searchField =$(obj).parents("#abc").find(".searchField").val(); 
					
				}else if($(obj).parents("#abc").find(".selectKeyText").val() != undefined) {
					$(".selectKeyText").each(function(){
						var keyWordKey = $(this).attr("field");
						var keyWordValue = $(this).val().replace(" ","");
						keyWordObj[keyWordKey] = keyWordValue;
					});
					keyWordsArray.push(keyWordObj);
				}else {
					keyWord =$(obj).parents(".dnrw").find(".indexViewKeyWord").val();
					keyWord = keyWord.replace("关键字搜索","");
					keyWord = keyWord.replace(" ","");
					keyWordObj.all=keyWord;
					keyWordsArray.push(keyWordObj);
				}
				
				
				
				if(keyWord == "关键字搜索") {
					keyWord = "";
				}
				var optionValue = $(".cond_class_bg").parent().attr("value");
				var appointColumn = $(".cond_class_bg").parent().attr("appointColumn");
				var orderTitle = $("#title").val();
				var orderbyArr = $("#orderbyArr").val();
				var dyViewQueryInfo = new Object();
				var condSelect = new Object();
				var pageInfo = new Object();
				pageInfo.currentPage = topage;
				pageInfo.pageSize = pageSize;
				dyViewQueryInfo.pageInfo = pageInfo;
				dyViewQueryInfo.viewUuid = viewUuid;
				condSelect.orderTitle = orderTitle;
				condSelect.orderbyArr = orderbyArr;
				condSelect.optionValue = optionValue;
				condSelect.appointColumn = appointColumn;
				condSelect.beginTime = beginTime;
				condSelect.endTime = endTime;
				condSelect.searchField = searchField;
				condSelect.keyWords = keyWordsArray;
				dyViewQueryInfo.condSelect = condSelect;
				
				var parmArray = parmStr.split("&");
				var expandParams = new Object();
				for(var o=0;o<parmArray.length;o++){
					var key_ = parmArray[o].split("=")[0];
					var val_ = parmArray[o].split("=")[1];
					if(key_!=""){
						if(key_=='openBy'){
							dyViewQueryInfo.openBy = val_;
						}else{
							expandParams[key_] = val_;
						}
					}
					
				}
				dyViewQueryInfo.expandParams = expandParams;
				
				var url = ctx + '/basicdata/dyview/view_show_param.action';
				$.ajax({
					url:url,
					type:"POST",
					data:JSON.stringify(dyViewQueryInfo),
					dataType:'text',
					contentType:'application/json',
					success:function(data) {
						$("#update_"+viewUuid).html(data);
						formDate();
					}
				});
			}else if(mark == "viewShow"){
				var dyViewQueryInfo = new Object();
				var pageInfo = new Object();
				pageInfo.currentPage = topage;
				pageInfo.pageSize = pageSize;
				dyViewQueryInfo.pageInfo = pageInfo;
				dyViewQueryInfo.viewUuid = viewUuid;
				
				var parmArray = parmStr2.split("&");
				var expandParams = new Object();
				for(var o=0;o<parmArray.length;o++){
					var key_ = parmArray[o].split("=")[0];
					var val_ = parmArray[o].split("=")[1];
					if(key_=='openBy'){
						dyViewQueryInfo.openBy = val_;
					}else{
						expandParams[key_] = val_;
					}
				}
				dyViewQueryInfo.expandParams = expandParams;
				
				var url = ctx + '/basicdata/dyview/view_show_forpage.action';
				$.ajax({
					url:url,
					type:"POST",
					data:JSON.stringify(dyViewQueryInfo),
					dataType:'text',
					contentType:'application/json',
					success:function(data) {
						$("#update_"+viewUuid).html(data);
						formDate();
					}
				});
			}
 		}
		
		function go(method,currentPage,obj){
			var parmStr = $(obj).parents("#abc").find("#parmStr").val(); 
			var parmStr2 = parmStr.substring(1);
			var pageSize = $(obj).parents("#abc").find("#pagesize").val();
			var totalPages = $(obj).parents("#abc").find("#totalPages").val();
			var gotopage = $(obj).parents("#abc").find("#jumppage").val();
			var mark = $(obj).parents("#abc").find("#mark").val();
			var viewUuid = $(obj).parents("#abc").find("#viewUuid").val();
			if(mark == "viewSelect") {
				var keyWordsArray = new Array();
				var keyWordObj = new Object();
				var keyWord = "";
				var beginTime = "";
				var endTime = "";
				var searchField = "";
				
				if($(obj).parents("#abc").find("#keyWord").val() != undefined){
					keyWord =$(obj).parents("#abc").find("#keyWord").val(); 
					keyWord = keyWord.replace("关键字搜索","");
					keyWord = keyWord.replace(" ","");
					keyWordObj.all=keyWord;
					keyWordsArray.push(keyWordObj);
					
					beginTime =$(obj).parents("#abc").find("#beginTime").val(); 
					endTime =$(obj).parents("#abc").find("#endTime").val(); 
					searchField =$(obj).parents("#abc").find(".searchField").val(); 
					
				}else if($(obj).parents("#abc").find(".selectKeyText").val() != undefined) {
					$(".selectKeyText").each(function(){
						var keyWordKey = $(this).attr("field");
						var keyWordValue = $(this).val().replace(" ","");
						keyWordObj[keyWordKey] = keyWordValue;
					});
					keyWordsArray.push(keyWordObj);
				}else {
					keyWord =$(obj).parents(".dnrw").find(".indexViewKeyWord").val();
					keyWord = keyWord.replace("关键字搜索","");
					keyWord = keyWord.replace(" ","");
					keyWordObj.all=keyWord;
					keyWordsArray.push(keyWordObj);
				}
				if(keyWord == "关键字搜索") {
					keyWord = "";
				}
				
				var optionValue = $(".cond_class_bg").parent().attr("value");
				var appointColumn = $(".cond_class_bg").parent().attr("appointColumn");
				var orderTitle = $("#title").val();
				var orderbyArr = $("#orderbyArr").val();
				var dyViewQueryInfo = new Object();
				var condSelect = new Object();
				var pageInfo = new Object();
				
				if(method == "next") {
					pageInfo.currentPage = currentPage + parseInt(1);
				}else if(method == "back") {
					pageInfo.currentPage = currentPage - parseInt(1);
				}else if(method == "goto") {
					if(gotopage==""){
						oAlert("请输入页数！");
						return false;
					}
					if(gotopage.match(/^\+?[1-9][0-9]*$/)){
						if(parseInt(gotopage)<=parseInt(totalPages)){
							pageInfo.currentPage = gotopage;
						}else{
							oAlert("输入的页数超出总页数！");
							return false;
						}
					}else{
						oAlert("请输入合法的页数！");
						return false;
					}
				}else if(method == "first"){
					pageInfo.currentPage = 1;
				}else if(method == "last"){
					pageInfo.currentPage = totalPages;
				}
				
				pageInfo.pageSize = pageSize;
				dyViewQueryInfo.pageInfo = pageInfo;
				dyViewQueryInfo.viewUuid = viewUuid;
				condSelect.orderTitle = orderTitle;
				condSelect.orderbyArr = orderbyArr;
				condSelect.optionValue = optionValue;
				condSelect.appointColumn = appointColumn;
				condSelect.beginTime = beginTime;
				condSelect.endTime = endTime;
				condSelect.searchField = searchField;
				condSelect.keyWords = keyWordsArray;
				dyViewQueryInfo.condSelect = condSelect;
				
				var parmArray = parmStr.split("&");
				var expandParams = new Object();
				for(var o=0;o<parmArray.length;o++){
					var key_ = parmArray[o].split("=")[0];
					var val_ = parmArray[o].split("=")[1];
					if(key_!=""){
						if(key_=='openBy'){
							dyViewQueryInfo.openBy = val_;
						}else{
							expandParams[key_] = val_;
						}
					}
					
				}
				dyViewQueryInfo.expandParams = expandParams;
				
				var url = ctx + '/basicdata/dyview/view_show_param.action';
				$.ajax({
					url:url,
					type:"POST",
					data:JSON.stringify(dyViewQueryInfo),
					dataType:'text',
					contentType:'application/json',
					success:function(data) {
						$("#update_"+viewUuid).html(data);
						formDate();
					}
				});
			}else if (mark == "viewShow") {
				var page = new Object();
				if(method == 'next') {
					page = currentPage + parseInt(1);
					
				}else if(method == "back") {
					page = currentPage - parseInt(1);
				}else if(method == "goto") {
					if(gotopage==""){
						oAlert("请输入页数！");
						return false;
					}
					if(gotopage.match(/^\+?[1-9][0-9]*$/)){
						
						if(parseInt(gotopage)<=parseInt(totalPages)){
							page = gotopage;
						}else{
							oAlert("输入的页数超出总页数！");
							return false;
						}
					}else{
						oAlert("请输入合法的页数！");
						return false;
					}
				}else if(method == "first"){
					page = 1;
				}else if(method == "last"){
					page = totalPages;
				}
				var dyViewQueryInfo = new Object();
				var pageInfo = new Object();
				pageInfo.currentPage = page;
				pageInfo.pageSize = pageSize;
				dyViewQueryInfo.pageInfo = pageInfo;
				dyViewQueryInfo.viewUuid = viewUuid;
				
				var parmArray = parmStr2.split("&");
				var expandParams = new Object();
				for(var o=0;o<parmArray.length;o++){
					var key_ = parmArray[o].split("=")[0];
					var val_ = parmArray[o].split("=")[1];
					if(key_=='openBy'){
						dyViewQueryInfo.openBy = val_;
					}else{
						expandParams[key_] = val_;
					}
				}
				dyViewQueryInfo.expandParams = expandParams;
				
				var url = ctx + '/basicdata/dyview/view_show_forpage.action';
				$.ajax({
					url:url,
					type:"POST",
					data:JSON.stringify(dyViewQueryInfo),
					dataType:'text',
					contentType:'application/json',
					success:function(data) {
						$("#update_"+viewUuid).html(data);
						formDate();
					}
				});
			}
		}
		
		
		//格式化时间
		function formDate(){
			$(".dataTr td").each(function(){
				var _reTimeReg = /^(?:19|20)[0-9][0-9]-(?:(?:[1-9])|(?:1[0-2]))-(?:(?:[1-9])|(?:[1-2][1-9])|(?:[1-3][0-1])) (?:(?:2[0-3])|(?:1[0-9])|(?:[1-9])|0):[0-5][0-9]:[0-5][0-9]$/;
				var tempText = $(this).text();
				if(_reTimeReg.test(tempText)){
					
					var dataStr = tempText.split(" ")[0];
					var timeStr = tempText.split(" ")[1];
					var dataStrArray = dataStr.split("-");
					var timeStrArray = timeStr.split(":");
					var strArray = new Array();
					strArray[0] = dataStrArray[0];
					strArray[1] = dataStrArray[1];
					strArray[2] = dataStrArray[2];
					strArray[3] = timeStrArray[0];
					strArray[4] = timeStrArray[1];
					strArray[5] = timeStrArray[2];
					for(var i=1;i<6;i++){
						if(strArray[i].length==1){
							strArray[i] = 0+""+strArray[i];
						}
					}
	/**		var str = strArray[0]+"-"+strArray[1]+"-"+strArray[2]+" "+strArray[3]+":"+strArray[4]+":"+strArray[5];**/
					var str = strArray[0]+"-"+strArray[1]+"-"+strArray[2]+" "+strArray[3]+":"+strArray[4];
					$(this).text(str);
					$(this).attr("title",str);
				}
			});
		}
		
		function changePageSize(pageSize,obj) {
			var parmStr = $(obj).parents("#abc").find("#parmStr").val(); 
			var parmStr2 = parmStr.substring(1);
			var totalPages = $(obj).parents("#abc").find("#totalPages").val();
			var gotopage = $(obj).parents("#abc").find("#jumppage").val();
			var mark = $(obj).parents("#abc").find("#mark").val();
			var viewUuid = $(obj).parents("#abc").find("#viewUuid").val();
			var dyViewQueryInfo = new Object();
			var pageInfo = new Object();
			pageInfo.currentPage = 1;
			pageInfo.pageSize = pageSize;
			dyViewQueryInfo.pageInfo = pageInfo;
			dyViewQueryInfo.viewUuid = viewUuid;
			dyViewQueryInfo.openBy = parmStr2.split("=")[1];
			
			var parmArray = parmStr.split("&");
			var expandParams = new Object();
			for(var o=0;o<parmArray.length;o++){
				var key_ = parmArray[o].split("=")[0];
				var val_ = parmArray[o].split("=")[1];
				if(key_!=""){
					if(key_=='openBy'){
						dyViewQueryInfo.openBy = val_;
					}else{
						expandParams[key_] = val_;
					}
				}
				
			}
			dyViewQueryInfo.expandParams = expandParams;
			
			var url = ctx + '/basicdata/dyview/view_show_forpage.action';
			$.ajax({
				url:url,
				type:"POST",
				data:JSON.stringify(dyViewQueryInfo),
				dataType:'text',
				contentType:'application/json',
				success:function(data) {
					$("#update_"+viewUuid).html(data);
					formDate();
				}
			});
			
		}
		
		
    </script>	
	</head>
	<body>

		<div class="table-footer" id="footBar">
			<div id="finder-footer-333c3e" class="finder-footer">
					<c:if test="${pageDefinition.isPaging == true}">
					<div style="float: left;padding-top: 1px;">每页<select id="pageSizeSelect" onChange="changePageSize(this.value,this);">
											<option value="10">10</option>
											<option value="20">20</option>
											<option value="50">50</option>
											<option value="100">100</option>
											</select>行  共${page.totalCount}行 &nbsp; </div>
					<div class="firstpage" onclick="go('first',${page.currentPage  },this);">
					<c:if test="${page.currentPage !=1 }">
						<img src="${ctx}/resources/pt/images/v1_first.png" />
					</c:if>
					<c:if test="${page.currentPage ==1 }">
						<img src="${ctx}/resources/pt/images/v1_first_gray.png" />
					</c:if>
					</div>
					<c:if test="${page.totalPages<=7}">
						<div
							<c:if test="${page.currentPage !=1 && page.totalPages != 0}">class="txt_page" onclick="go('back',${page.currentPage },this)"</c:if>
							<c:if test="${page.currentPage ==1 }">class="txt_page_no"</c:if>>
							<c:if test="${page.currentPage !=1 }">
								<img src="${ctx}/resources/pt/images/v1_prev.png" width="11" height="11" />
							</c:if>
							<c:if test="${page.currentPage ==1 }">
								<img src="${ctx}/resources/pt/images/v1_prev_gray.png" width="11" height="11" />
							</c:if>
						</div>
						<c:forEach begin="1" end="${page.totalPages}" var="p_nub" step="1"> 
							<div onclick="gotopage_btn(${page.currentPage},${p_nub},this)"
								<c:if test="${p_nub != page.currentPage }">class="nub_page"</c:if>
								<c:if test="${p_nub == page.currentPage}">class="nub_page_"</c:if>>
								${p_nub}
							</div>
						</c:forEach>
						<div
							<c:if test="${page.currentPage != page.totalPages && page.totalPages != 0}">class="txt_page" onclick="go('next',${page.currentPage },this)"</c:if>
							<c:if test="${page.currentPage == page.totalPages || page.totalPages == 0}">class="txt_page_no"</c:if>>
							<c:if test="${page.currentPage != page.totalPages && page.totalPages != 0}">
								<img src="${ctx}/resources/pt/images/v1_next.png" width="11" height="11" />
							</c:if>
							<c:if test="${page.currentPage == page.totalPages || page.totalPages == 0}">
								<img src="${ctx}/resources/pt/images/v1_next_gray.png" width="11" height="11" />
							</c:if>
						</div>
						<input value="${page.totalPages}" type="hidden" id="allpage" />
						<input value="${page.pageSize}" type="hidden" id="pagesize" />
					</c:if>

					<c:if test="${page.totalPages>7 && page.currentPage<4}">
						<div
							<c:if test="${page.currentPage !=1 }">class="txt_page" onclick="go('back',${page.currentPage },this)"</c:if>
							<c:if test="${page.currentPage ==1 }">class="txt_page_no"</c:if>>
							<c:if test="${page.currentPage !=1 }">
								<img src="${ctx}/resources/pt/images/v1_prev.png" width="11" height="11" />
							</c:if>
							<c:if test="${page.currentPage ==1 }">
								<img src="${ctx}/resources/pt/images/v1_prev_gray.png" width="11" height="11" />
							</c:if>
						</div>
						<c:forEach begin="1" end="4" var="p_nub">
							<div onclick="gotopage_btn(${page.currentPage},${p_nub },this)"
								<c:if test="${p_nub != page.currentPage }">class="nub_page"</c:if>
								<c:if test="${p_nub == page.currentPage }">class="nub_page_"</c:if>>
								${p_nub}
							</div>
						</c:forEach>
						<div class="page_">
							…
						</div>
						<div class="page_">
							…
						</div>
						<div onclick="gotopage_btn(${page.currentPage},${page.totalPages},this)"
							class="nub_page">
							${page.totalPages}
						</div>
						<div
							<c:if test="${page.currentPage != page.totalPages}">class="txt_page" onclick="go('next',${page.currentPage},this)"</c:if>
							<c:if test="${page.currentPage == page.totalPages}">class="txt_page_no"</c:if>>
							<c:if test="${page.currentPage != page.totalPages }">
								<img src="${ctx}/resources/pt/images/v1_next.png" width="11" height="11" />
							</c:if>
							<c:if test="${page.currentPage == page.totalPages}">
								<img src="${ctx}/resources/pt/images/v1_next_gray.png" width="11" height="11" />
							</c:if>
						</div>
						<input value="${page.totalPages}" type="hidden" id="allpage" />
						<input value="${page.pageSize}" type="hidden" id="pagesize" ps='ps'/>
					</c:if>

					<c:if
						test="${page.totalPages>7 && page.currentPage>=4 && page.currentPage<=(page.totalPages-2)}">
						<div
							<c:if test="${page.currentPage !=1 }">class="txt_page" onclick="go('back',${page.currentPage },this)"</c:if>
							<c:if test="${page.currentPage ==1 }">class="txt_page_no"</c:if>>
							<c:if test="${page.currentPage !=1 }">
								<img src="${ctx}/resources/pt/images/v1_prev.png" width="11" height="11" />
							</c:if>
							<c:if test="${page.currentPage ==1 }">
								<img src="${ctx}/resources/pt/images/v1_prev_gray.png" width="11" height="11" />
							</c:if>
						</div>
						<div onclick="gotopage_btn(${page.currentPage},1,this)" class="nub_page">
							1
						</div>
						<div class="page_">
							…
						</div>
						<c:forEach begin="${page.currentPage -1 }" end="${page.currentPage + 1 }"
							var="p_nub">
							<div onclick="gotopage_btn(${page.currentPage},${p_nub},this)"
								<c:if test="${p_nub != page.currentPage }">class="nub_page"</c:if>
								<c:if test="${p_nub == page.currentPage }">class="nub_page_"</c:if>>
								${p_nub}
							</div>
						</c:forEach>
						<div class="page_">
							…
						</div>
						<div onclick="gotopage_btn(${page.currentPage},${page.totalPages },this)"
							class="nub_page">
							${page.totalPages}
						</div>
						<div
							<c:if test="${page.currentPage != page.totalPages}">class="txt_page" onclick="go('next',${page.currentPage },this)" </c:if>
							<c:if test="${page.currentPage != page.totalPages }">class="txt_page_no"</c:if>>
							<c:if test="${page.currentPage != page.totalPages }">
								<img src="${ctx}/resources/pt/images/v1_next.png" width="11" height="11" />
							</c:if>
							<c:if test="${page.currentPage == page.totalPages}">
								<img src="${ctx}/resources/pt/images/v1_next_gray.png" width="11" height="11" />
							</c:if>
						</div>
						<input value="${page.totalPages}" type="hidden" id="allpage" />
						<input value="${page.pageSize}" type="hidden" id="pagesize" ps='ps'/>
					</c:if>

					<c:if
						test="${page.totalPages>7 && page.currentPage>6 && page.currentPage>(page.totalPages-2)}">
						<div
							<c:if test="${page.currentPage !=1 }">class="txt_page" onclick="go('back',${page.currentPage },this)"</c:if>
							<c:if test="${page.currentPage ==1 }">class="txt_page_no"</c:if>>
							<c:if test="${page.currentPage !=1 }">
								<img src="${ctx}/resources/pt/images/v1_prev.png" width="11" height="11" />
							</c:if>
							<c:if test="${page.currentPage ==1 }">
								<img src="${ctx}/resources/pt/images/v1_prev_gray.png" width="11" height="11" />
							</c:if>
						</div>
						<div onclick="gotopage_btn(${page.currentPage},1,this)" class="nub_page">
							1
						</div>
						<div class="page_">
							…
						</div>
						<div class="page_">
							…
						</div>
						<c:forEach begin="${page.totalPages-3}" end="${page.totalPages}" var="p_nub">
							<div onclick="gotopage_btn(${page.currentPage},${p_nub },this )"
								<c:if test="${p_nub != page.currentPage }">class="nub_page"</c:if>
								<c:if test="${p_nub == page.currentPage }">class="nub_page_"</c:if>>
								${p_nub}
							</div>
						</c:forEach>
						<div 
							<c:if test="${page.currentPage != page.totalPages}">class="txt_page" onclick="go('next',${page.currentPage},this)"</c:if>
							<c:if test="${page.currentPage == page.totalPages}">class="txt_page_no"</c:if>>
							<c:if test="${page.currentPage != page.totalPages }">
								<img src="${ctx}/resources/pt/images/v1_next.png" width="11" height="11" />
							</c:if>
							<c:if test="${page.currentPage == page.totalPages}">
								<img src="${ctx}/resources/pt/images/v1_next_gray.png" width="11" height="11" />
							</c:if>
						</div>
						<input value="${page.totalPages}" type="hidden" id="allpage" />
						<input value="${page.pageSize}" type="hidden" id="pagesize" ps='ps'/>
						<input value="" type="hidden" id="mark"/>
						
					</c:if>
					<div class="jumpmodule">
					<div class="lastpage" onclick="go('last',${page.currentPage  },this);">
					
					<c:if test="${page.currentPage != page.totalPages && page.totalPages !=0}">
						<img src="${ctx}/resources/pt/images/v1_last.png" />
					</c:if>
					<c:if test="${page.currentPage == page.totalPages || page.totalPages ==0}">
						<img src="${ctx}/resources/pt/images/v1_last_gray.png" />
					</c:if>
					</div> &nbsp; 第 <input type="text" id="jumppage" onkeyup="entyKeyBoard(event,this,${page.currentPage});" value="${page.currentPage  }" />页/共${page.totalPages}页  </div>
					<!--   <input id="jump_btn" class="jump_btn" type="button" onclick="go('goto',${page.currentPage  });" value="确定"  /> -->
					</c:if>
				</div>
			</div>
	</body>
	
</html>
