<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<style type="text/css">
		.page_ {
	margin-left: 5px;
	padding-top: 4px;
	float: left;
	color: #666666;
	height: 16px;
	text-align: center;
	border: 1px;
	font-size: 12px;
}
.txt_page_no {
    background-color: #FFFFFF;
    border: 1px solid #E7E7E7;
    float: left;
    font-size: 12px;
    height: 19px;
    margin-left: 5px;
    text-align: center;
    width: 60px;
    cursor:pointer;
}

.txt_page {
	cursor:pointer;
   background-color: #FFFFFF;
    border: 1px solid #E7E7E7;
    float: left;
    font-size: 12px;
    height: 19px;
    margin-left: 5px;
    text-align: center;
    width: 60px;
}

.txt_page_ {
	cursor:pointer;
   background-color: #FFFFFF;
    border: 1px solid #E7E7E7;
    float: left;
    font-size: 12px;
    height: 19px;
    margin-left: 5px;
    text-align: center;
    width: 60px;
}

.writ_page {
	 color: #666666;
    float: left;
    font-size: 12px;
    margin-left: 5px;
    margin-right: 5px;
}

.nub_page {
 background: none repeat scroll 0 0 #E2E8EB;
    border-color: #FFFFFF #BEC6CE #BEC6CE #FFFFFF;
    border-style: solid;
    border-width: 1px;
    color:#0000000;
    cursor: pointer;
    float: left;
    height: 19px;
    margin-left: 5px;
    padding: 0 6px;
    text-align: center;
}


.nub_page_ {
	 background-color: #FFFFFF;
    border-color: #BEC6CE #FFFFFF #FFFFFF #BEC6CE;
    border-style: solid;
    border-width: 1px;
    color: #000000;
    cursor: pointer;
    float: left;
    height: 19px;
    margin-left: 5px;
    padding: 0 6px;
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
		</style>
		<script type="text/javascript">
		function page_move(obj){
			$(obj).attr("class","txt_page_");
		}
		function page_out(obj){
			$(obj).attr("class","txt_page");
		}
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
		
		function gotopage_btn(currentPage,topage){
			var pageSize = $("#pagesize").val();
			var mark = $("#mark").val();
			var viewUuid = $("#viewUuid").val();
			var params = {};
			if(mark == "keyWord") {
				var keyWord = $("#keyWord").val();
				var keyWords = new Array();
				keyWords = keyWord.split(",");
				params.keyWords = keyWords;
				params.viewUuid = viewUuid;
				params.currentPage = topage;
				params.pageSize = pageSize;
				var url = contextPath + '/basicdata/dyview/view_data_keySelect.action';
				$.ajax({
					url:url,
					type:"POST",
					data:JSON.stringify(params),
					dataType:'text',
					contentType:'application/json',
					success:function(data) {
						$("#update_"+viewUuid).html(data);
					}
				});
			}else if(mark == "viewShow"){
				
				var url = contextPath + '/basicdata/dyview/viewdata_show?viewUuid='+viewUuid+'&currentPage='+topage;
				$.ajax({
					url:url,
					type:"POST",
					dataType:'text',
					contentType:'application/json',
					success:function(data) {
						$("#update_"+viewUuid).html(data);
					}
				});
			}else if(mark == "condSelect") {
				params.viewUuid = viewUuid;
				params.currentPage = topage;
				params.pageSize = pageSize;
				var value = $("div[id][id$='_cond']").attr("value");
				params.value = value;
				var appointColumn = $("div[id][id$='_cond']").attr("appointColumn");
				params.appointColumn =appointColumn;
				var url = contextPath + '/basicdata/dyview/view_data_condSelect.action';
				$.ajax({
					url:url,
					type:"POST",
					data:JSON.stringify(params),
					dataType:'text',
					contentType:'application/json',
					success:function(data) {
						$("#update_"+viewUuid).html(data);
					}
				});
			}else if(mark == "clickSort"){
				var title = $("#title").val();
				params.viewUuid = viewUuid;
				params.title = title;
				params.currentPage = topage;
				params.pageSize = pageSize;
				
					var url = contextPath + '/basicdata/dyview/view_data_sort.action';
					$.ajax({
						url:url,
						type:"POST",
						data:JSON.stringify(params),
						dataType:'text',
						contentType:'application/json',
						success:function(data) {
							$("#update_"+viewUuid).html(data);
						}
					});
			}
 		}
				
		function go(method,currentPage){
			var pageSize = $("#pagesize").val();
			var pageCurrentPage = $("#pageCurrentPage").val();
			var totalPages = $("#totalPages").val();
			
			var mark = $("#mark").val();
			var viewUuid = $("#viewUuid").val();
			if(mark == "keyWord") {
				var params = {};
				var keyWord = $("#keyWord").val();
				var keyWords = new Array();
				keyWords = keyWord.split(",");
				params.keyWords = keyWords;
				params.viewUuid = viewUuid;
				params.currentPage = currentPage;
				params.pageSize = pageSize;
				if(method == "next") {
					params.currentPage = currentPage + parseInt(1);
				}else if(method == "back") {
					params.currentPage = currentPage - parseInt(1);
				}
				var url = contextPath + '/basicdata/dyview/view_data_keySelect.action';
				$.ajax({
					url:url,
					type:"POST",
					data:JSON.stringify(params),
					dataType:'text',
					contentType:'application/json',
					success:function(data) {
						$("#update_"+viewUuid).html(data);
					}
				});
			}else if (mark == "viewShow") {
				var page = null;
				if(method == 'next') {
					page = currentPage + parseInt(1);
					
				}else if(method == "back") {
					page = currentPage - parseInt(1);
				}
				var url = contextPath + '/basicdata/dyview/viewdata_show?viewUuid='+viewUuid+'&currentPage='+page;
				$.ajax({
					url:url,
					type:"POST",
					dataType:'text',
					contentType:'application/json',
					success:function(data) {
						$("#update_"+viewUuid).html(data);
					}
				});
			}else if(mark == "condSelect") {
				var params = {};
				var page = null;
				if(method == 'next') {
					page = currentPage + parseInt(1);
					
				}else if(method == "back") {
					page = currentPage - parseInt(1);
				}
				params.viewUuid = viewUuid;
				params.currentPage = page;
				params.pageSize = pageSize;
				var value = $("div[id][id$='_cond']").attr("value");
				params.value = value;
				var appointColumn = $("div[id][id$='_cond']").attr("appointColumn");
				params.appointColumn =appointColumn;
				var url = contextPath + '/basicdata/dyview/view_data_condSelect.action';
				$.ajax({
					url:url,
					type:"POST",
					data:JSON.stringify(params),
					dataType:'text',
					contentType:'application/json',
					success:function(data) {
						$("#update_"+viewUuid).html(data);
					}
				});
			}
			else if(mark == "clickSort"){
				var title = $("#title").val();
				params.viewUuid = viewUuid;
				params.title = title;
				params.currentPage = topage;
				params.pageSize = pageSize;
				
					var url = contextPath + '/basicdata/dyview/view_data_sort.action';
					$.ajax({
						url:url,
						type:"POST",
						data:JSON.stringify(params),
						dataType:'text',
						contentType:'application/json',
						success:function(data) {
							$("#update_"+viewUuid).html(data);
						}
					});
			}
		}
		
    </script>	
	</head>
	<body>

		<div class="table-footer" id="footBar">
			<div id="finder-footer-333c3e" class="finder-footer">
					<c:if test="${pageDefinition.isPaging == true}">
					<div style="float: left;">每页显示${page.pageSize}条  共${page.totalCount}条 |</div>
					<c:if test="${page.totalPages<=7}">
						<div
							<c:if test="${page.currentPage !=1 }">class="txt_page" onclick="go('back',${page.currentPage })" onmousemove="page_move(this)" onmouseout="page_out(this)"</c:if>
							<c:if test="${page.currentPage ==1 }">class="txt_page_no"</c:if>>
							<img src="${ctx}/resources/pt/images/f_page.jpg" width="11"
								height="11" />
							上一页
						</div>
						<c:forEach begin="1" end="${page.totalPages}" var="p_nub" step="1"> 
							<div onclick="gotopage_btn(${page.currentPage},${p_nub})"
								<c:if test="${p_nub != page.currentPage }">class="nub_page"</c:if>
								<c:if test="${p_nub == page.currentPage}">class="nub_page_"</c:if>>
								${p_nub}
							</div>
						</c:forEach>
						<div
							<c:if test="${page.currentPage != page.totalPages}">class="txt_page" onclick="go('next',${page.currentPage })" onmousemove="page_move(this)" onmouseout="page_out(this)"</c:if>
							<c:if test="${page.currentPage == page.totalPages}">class="txt_page_no"</c:if>>
							下一页
							<img src="${ctx}/resources/pt/images/next_page.jpg" />
						</div>
						<input value="${page.totalPages}" type="hidden" id="allpage" />
						<input value="${page.pageSize}" type="hidden" id="pagesize" />
					</c:if>

					<c:if test="${page.totalPages>7 && page.currentPage<4}">
						<div
							<c:if test="${page.currentPage !=1 }">class="txt_page" onclick="go('back',${page.currentPage })" onmousemove="page_move(this)" onmouseout="page_out(this)"</c:if>
							<c:if test="${page.currentPage ==1 }">class="txt_page_no"</c:if>>
							<img src="${ctx}/resources/pt/images/f_page.jpg" width="11"
								height="11" />
							上一页
						</div>
						<c:forEach begin="1" end="4" var="p_nub">
							<div onclick="gotopage_btn(${page.currentPage},${p_nub })"
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
						<div onclick="gotopage_btn(${page.currentPage},${page.totalPages})"
							class="nub_page">
							${page.totalPages}
						</div>
						<div
							<c:if test="${page.currentPage != page.totalPages}">class="txt_page" onclick="go('next',${page.currentPage})" onmousemove="page_move(this)" onmouseout="page_out(this)"</c:if>
							<c:if test="${page.currentPage == page.totalPages}">class="txt_page_no"</c:if>>
							下一页
							<img src="${ctx}/resources/pt/images/next_page.jpg" />
						</div>
						<input value="${page.totalPages}" type="hidden" id="allpage" />
						<input value="${page.pageSize}" type="hidden" id="pagesize" ps='ps'/>
					</c:if>

					<c:if
						test="${page.totalPages>7 && page.currentPage>=4 && page.currentPage<=(page.totalPages-2)}">
						<div
							<c:if test="${page.currentPage !=1 }">class="txt_page" onclick="go('back',${page.currentPage })" onmousemove="page_move(this)" onmouseout="page_out(this)"</c:if>
							<c:if test="${page.currentPage ==1 }">class="txt_page_no"</c:if>>
							<img src="${ctx}/resources/pt/images/f_page.jpg" width="11"
								height="11" />
							上一页
						</div>
						<div onclick="gotopage_btn(${page.currentPage},1)" class="nub_page">
							1
						</div>
						<div class="page_">
							…
						</div>
						<c:forEach begin="${page.currentPage -1 }" end="${page.currentPage + 1 }"
							var="p_nub">
							<div onclick="gotopage_btn(${page.currentPage},${p_nub})"
								<c:if test="${p_nub != page.currentPage }">class="nub_page"</c:if>
								<c:if test="${p_nub == page.currentPage }">class="nub_page_"</c:if>>
								${p_nub}
							</div>
						</c:forEach>
						<div class="page_">
							…
						</div>
						<div onclick="gotopage_btn(${page.currentPage},${page.totalPages })"
							class="nub_page">
							${page.totalPages}
						</div>
						<div
							<c:if test="${page.currentPage != page.totalPages}">class="txt_page" onclick="go('next',${page.currentPage })" onmousemove="page_move(this)" onmouseout="page_out(this)"</c:if>
							<c:if test="${page.currentPage != page.totalPages }">class="txt_page_no"</c:if>>
							下一页
							<img src="${ctx}/resources/pt/images/next_page.jpg" />
						</div>
						<input value="${page.totalPages}" type="hidden" id="allpage" />
						<input value="${page.pageSize}" type="hidden" id="pagesize" ps='ps'/>
					</c:if>

					<c:if
						test="${page.totalPages>7 && page.currentPage>6 && page.currentPage>(page.totalPages-2)}">
						<div
							<c:if test="${page.currentPage !=1 }">class="txt_page" onclick="go('back',${page.currentPage })" onmousemove="page_move(this)" onmouseout="page_out(this)"</c:if>
							<c:if test="${page.currentPage ==1 }">class="txt_page_no"</c:if>>
							<img src="${ctx}/resources/pt/images/f_page.jpg" width="11"
								height="11" />
							上一页
						</div>
						<div onclick="gotopage_btn(${page.currentPage},1)" class="nub_page">
							1
						</div>
						<div class="page_">
							…
						</div>
						<div class="page_">
							…
						</div>
						<c:forEach begin="${page.totalPages-3}" end="${page.totalPages}" var="p_nub">
							<div onclick="gotopage_btn(${page.currentPage},${p_nub })"
								<c:if test="${p_nub != page.currentPage }">class="nub_page"</c:if>
								<c:if test="${p_nub == page.currentPage }">class="nub_page_"</c:if>>
								${p_nub}
							</div>
						</c:forEach>
						<div
							<c:if test="${page.currentPage !=page.totalPages} ">class="txt_page" onclick="go('next',${page.currentPage  })" onmousemove="page_move(this)" onmouseout="page_out(this)"</c:if>
							<c:if test="${page.currentPage ==page.totalPages}">class="txt_page_no"</c:if>>
							下一页
							<img src="${ctx}/resources/pt/images/next_page.jpg" />
						</div>
						<input value="${page.totalPages}" type="hidden" id="allpage" />
						<input value="${page.pageSize}" type="hidden" id="pagesize" ps='ps'/>
						<input value="" type="hidden" id="mark"/>
						
					</c:if>
					</c:if>
					共${page.totalPages}页
				</div>
			</div>
	</body>
	
</html>
