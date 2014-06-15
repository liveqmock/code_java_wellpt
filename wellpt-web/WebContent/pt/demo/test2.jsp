<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<%@ include file="/pt/common/taglibs.jsp"%>
<%@ include file="/pt/common/meta.jsp"%>













<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<meta http-equiv="Cache-Control" content="no-store" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Expires" content="0" />
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>视图解析</title>
<link rel="stylesheet" type="text/css"
	href="/wellpt-web/resources/bootstrap/css/bootstrap.css" />
</head>
<body>
	<div>
		<div>
		<!-- 视图 -->
		<div id="abc">
			<input type="hidden" id="viewUuid" name="viewUuid" value="5184081b-7508-49c0-aeb8-fe76c889a66f" />
			<input type="hidden" id="columnDefinitions" name="columnDefinitions" value="[com.wellsoft.pt.basicdata.dyview.entity.ColumnDefinition@8a7c7197, com.wellsoft.pt.basicdata.dyview.entity.ColumnDefinition@1bb1e42c, com.wellsoft.pt.basicdata.dyview.entity.ColumnDefinition@3d8bdeab, com.wellsoft.pt.basicdata.dyview.entity.ColumnDefinition@d80fc1f7, com.wellsoft.pt.basicdata.dyview.entity.ColumnDefinition@de53b0ed, com.wellsoft.pt.basicdata.dyview.entity.ColumnDefinition@c52f1e7c, com.wellsoft.pt.basicdata.dyview.entity.ColumnDefinition@58ed2e84, com.wellsoft.pt.basicdata.dyview.entity.ColumnDefinition@9acd7450, com.wellsoft.pt.basicdata.dyview.entity.ColumnDefinition@7b5dc86f]" />
			<input type="hidden" id="conditionTypes" name="conditionTypes" value="[com.wellsoft.pt.basicdata.dyview.entity.ConditionType@2a0d57a1, com.wellsoft.pt.basicdata.dyview.entity.ConditionType@5e3685ba]">
			
			
			<table class='view_search'><tr><td class='view_search_left'>标题&nbsp;&nbsp;&nbsp;&nbsp;|</td><td class='view_search_right'><div class='cond_class' id='0_648e75bf-5322-4183-bbfb-e55c3889abd6_cond' value='打印模板测试1_oa_dev_2013-06-02 14:35:22' appointColumn='流程.标题' appointColumnType = 'STRING'><a>打印模板</a></div></td></tr><tr><td class='view_search_left'>环节名称&nbsp;&nbsp;&nbsp;&nbsp;|</td><td class='view_search_right'><div class='cond_class' id='0_74c1a833-6331-4494-9fa1-688715e8db33_cond' value='发文流程' appointColumn='环节.名称' appointColumnType = 'STRING'><a></a></div></td></tr></tr><tr><td class='view_search_left'>关键字&nbsp;&nbsp;&nbsp;&nbsp;|</td><td class='view_search_right view_search_right_input'><input type='text' name='keyWord' id='keyWord' /></td><td class='view_search_right view_search_right_button' width='50'><button id='keySelect' type='button'>查询</button></td></tr></table>
			
			<div id = "update_5184081b-7508-49c0-aeb8-fe76c889a66f" class="view_content_list">
			<input type="hidden" id="page" name="page" value="com.wellsoft.pt.core.support.PagingInfo@770c234f" />
			<input value="viewShow" type="hidden" id="mark" name="mark"/>
			<input type="hidden" id="pageDefinition" name="pageDefinition" value="com.wellsoft.pt.basicdata.dyview.entity.PageDefinition@1f" />
			<input type="hidden" id="pageCurrentPage" name="pageCurrentPage" value="1"/>
			<input type="hidden" id="pageTotalCount" name="pageTotalCount" value="19"/>
			<input type="hidden" id="pageSize" name="pageSize" value="5"/>
			<input type="hidden" id="totalPages" name="totalPages" value="4"/>
			<table class="table">
					<thead>
					<tr class='thead_tr'><td width='15px'><input type='checkbox' class='checkall'/></td><td class='sortAble' orderby='asc' width='37%'>标题</td><td width='10%'>前办理人</td><td width='18%'>到达时间</td><td class='last' width='18%'>到期时间</td></tr>
					</thead>
					<tbody id="template" style="clear: both;">
					<tr class='tr_bg2 dataTr odd first  readed' src='http://192.168.0.53:8080/wellpt-web//workflow/work/view/todo?taskUuid=9a85a10b-4da1-42f1-879d-9f718610c4ed&flowInstUuid=63f152b7-c603-41d5-a09f-b2b7b4ea9ae4'><td width='15px'><input type='checkbox' class='checkeds'  value='63f152b7-c603-41d5-a09f-b2b7b4ea9ae4'/></td><td width=37%>某某发文1</td><td width=10%>acz</td><td width=18%>2013-7-13 14:30:32</td><td width=18%></td></tr><tr class='tr_bg2 even  readed'><td width='15px'></td><td width=37%>拟稿</td><td width=15%>发文（秘书型）</td><td  class='tr_td_button' style='text-align: right;'><div class='customButton'><button type="button" value="B003006015"  onclick="WorkFlow.rollback.call(this, '9a85a10b-4da1-42f1-879d-9f718610c4ed');">直接退回</button><button type="button" value="B003006017"  onclick="WorkFlow.transfer.call(this, '9a85a10b-4da1-42f1-879d-9f718610c4ed');">转办</button><button type="button" value="B003006018"  onclick="WorkFlow.counterSign.call(this, '9a85a10b-4da1-42f1-879d-9f718610c4ed');">会签</button><button type="button" value="B003006019"  onclick="WorkFlow.attention.call(this, '9a85a10b-4da1-42f1-879d-9f718610c4ed');">关注</button><button type="button" value="B003006020"  onclick="WorkFlow.copyTo.call(this, '9a85a10b-4da1-42f1-879d-9f718610c4ed');">抄送</button></div></td></tr><tr class='dataTr odd  tr_bg1  readed' src='http://192.168.0.53:8080/wellpt-web//workflow/work/view/todo?taskUuid=f51bec80-8f5a-477a-8d7a-ec5857ce21e5&flowInstUuid=ff5259e0-b279-43a1-aecd-3aa9f3e2742b'><td width='15px'><input type='checkbox' class='checkeds'  value='ff5259e0-b279-43a1-aecd-3aa9f3e2742b'/></td><td width=37%>派车申请</td><td width=10%>oa_dev</td><td width=18%>2013-7-13 14:23:54</td><td width=18%></td></tr><tr class='even  tr_bg1  readed'><td width='15px'></td><td width=37%>车辆调度</td><td width=15%>派车申请</td><td  class='tr_td_button' style='text-align: right;'><div class='customButton'><button type="button" value="B003006015"  onclick="WorkFlow.rollback.call(this, 'f51bec80-8f5a-477a-8d7a-ec5857ce21e5');">直接退回</button><button type="button" value="B003006017"  onclick="WorkFlow.transfer.call(this, 'f51bec80-8f5a-477a-8d7a-ec5857ce21e5');">转办</button><button type="button" value="B003006018"  onclick="WorkFlow.counterSign.call(this, 'f51bec80-8f5a-477a-8d7a-ec5857ce21e5');">会签</button><button type="button" value="B003006019"  onclick="WorkFlow.attention.call(this, 'f51bec80-8f5a-477a-8d7a-ec5857ce21e5');">关注</button><button type="button" value="B003006020"  onclick="WorkFlow.copyTo.call(this, 'f51bec80-8f5a-477a-8d7a-ec5857ce21e5');">抄送</button></div></td></tr><tr class='tr_bg2 dataTr odd  readed' src='http://192.168.0.53:8080/wellpt-web//workflow/work/view/todo?taskUuid=1baa7c6a-5ceb-4db6-ad1a-e62dd8fb0420&flowInstUuid=32f4cb4e-86f1-4639-bc9a-90418db60110'><td width='15px'><input type='checkbox' class='checkeds'  value='32f4cb4e-86f1-4639-bc9a-90418db60110'/></td><td width=37%>发文测试33</td><td width=10%>oa_dev</td><td width=18%>2013-7-12 20:56:14</td><td width=18%></td></tr><tr class='tr_bg2 even  readed'><td width='15px'></td><td width=37%>拟稿</td><td width=15%>发文（秘书型）</td><td  class='tr_td_button' style='text-align: right;'><div class='customButton'><button type="button" value="B003006015"  onclick="WorkFlow.rollback.call(this, '1baa7c6a-5ceb-4db6-ad1a-e62dd8fb0420');">直接退回</button><button type="button" value="B003006017"  onclick="WorkFlow.transfer.call(this, '1baa7c6a-5ceb-4db6-ad1a-e62dd8fb0420');">转办</button><button type="button" value="B003006018"  onclick="WorkFlow.counterSign.call(this, '1baa7c6a-5ceb-4db6-ad1a-e62dd8fb0420');">会签</button><button type="button" value="B003006019"  onclick="WorkFlow.attention.call(this, '1baa7c6a-5ceb-4db6-ad1a-e62dd8fb0420');">关注</button><button type="button" value="B003006020"  onclick="WorkFlow.copyTo.call(this, '1baa7c6a-5ceb-4db6-ad1a-e62dd8fb0420');">抄送</button></div></td></tr><tr class='dataTr odd  tr_bg1  readed' src='http://192.168.0.53:8080/wellpt-web//workflow/work/view/todo?taskUuid=9da18909-e409-41b4-adf0-e2de469c8c07&flowInstUuid=b4d90306-d556-4b47-9192-09555c2d6b4c'><td width='15px'><input type='checkbox' class='checkeds'  value='b4d90306-d556-4b47-9192-09555c2d6b4c'/></td><td width=37%>会议安排_oa_dev_2013-07-11 14:29:23</td><td width=10%>oa_dev</td><td width=18%>2013-7-11 14:29:24</td><td width=18%></td></tr><tr class='even  tr_bg1  readed'><td width='15px'></td><td width=37%>环节2</td><td width=15%>会议安排</td><td  class='tr_td_button' style='text-align: right;'><div class='customButton'><button type="button" value="B003006015"  onclick="WorkFlow.rollback.call(this, '9da18909-e409-41b4-adf0-e2de469c8c07');">直接退回</button><button type="button" value="B003006017"  onclick="WorkFlow.transfer.call(this, '9da18909-e409-41b4-adf0-e2de469c8c07');">转办</button><button type="button" value="B003006018"  onclick="WorkFlow.counterSign.call(this, '9da18909-e409-41b4-adf0-e2de469c8c07');">会签</button><button type="button" value="B003006019"  onclick="WorkFlow.attention.call(this, '9da18909-e409-41b4-adf0-e2de469c8c07');">关注</button><button type="button" value="B003006020"  onclick="WorkFlow.copyTo.call(this, '9da18909-e409-41b4-adf0-e2de469c8c07');">抄送</button></div></td></tr><tr class='tr_bg2 dataTr odd  readed' src='http://192.168.0.53:8080/wellpt-web//workflow/work/view/todo?taskUuid=e99c9faa-adc7-4e09-a1b8-6782783bcee1&flowInstUuid=497c1ebc-79a9-4c79-9588-5ef8447e409d'><td width='15px'><input type='checkbox' class='checkeds'  value='497c1ebc-79a9-4c79-9588-5ef8447e409d'/></td><td width=37%>会议安排_oa_dev_2013-07-08 16:54:33</td><td width=10%>oa_dev</td><td width=18%>2013-7-8 16:55:11</td><td width=18%></td></tr><tr class='tr_bg2 even  readed'><td width='15px'></td><td width=37%>环节2</td><td width=15%>会议安排</td><td  class='tr_td_button' style='text-align: right;'><div class='customButton'><button type="button" value="B003006015"  onclick="WorkFlow.rollback.call(this, 'e99c9faa-adc7-4e09-a1b8-6782783bcee1');">直接退回</button><button type="button" value="B003006017"  onclick="WorkFlow.transfer.call(this, 'e99c9faa-adc7-4e09-a1b8-6782783bcee1');">转办</button><button type="button" value="B003006018"  onclick="WorkFlow.counterSign.call(this, 'e99c9faa-adc7-4e09-a1b8-6782783bcee1');">会签</button><button type="button" value="B003006019"  onclick="WorkFlow.attention.call(this, 'e99c9faa-adc7-4e09-a1b8-6782783bcee1');">关注</button><button type="button" value="B003006020"  onclick="WorkFlow.copyTo.call(this, 'e99c9faa-adc7-4e09-a1b8-6782783bcee1');">抄送</button></div></td></tr> 
					</tbody>
			</table>
			



<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<style type="text/css">
		
.finder-footer {
    color: #434343;
    font-size: 12px;
    margin-left: 71px;
    width: 500px;
}
.page_ {
	margin-left: 5px;
	padding-top: 4px;
	float: left;
	color: #000;
	height: 16px;
	text-align: center;
	border: 1px;
	font-size: 12px;
}
.txt_page_no {
    float: left;
    font-size: 12px;
    height: 19px;
    margin-left: 5px;
    text-align: center;
    width: 12px;
    cursor:pointer;
}

.txt_page {
	cursor:pointer;
    float: left;
    font-size: 12px;
    height: 19px;
    margin-left: 5px;
    text-align: center;
    width: 12px;
}

.txt_page_ {
	cursor:pointer;
    float: left;
    font-size: 12px;
    height: 19px;
    margin-left: 5px;
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
    cursor: pointer;
    float: left;
    text-align: center;
    font-size:12px;
    color: #0F599C;
    height: 20px;
    padding: 0 6px;
    margin-top: 1px;
}
.nub_page_ {
    cursor: pointer;
    float: left;
    text-align: center;
    font-size:12px;
    color: #434343;
    height: 20px;
    padding: 0 6px;
    margin-top: 1px;
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
    margin-left: 10px;
    width: 110px;
    font-size: 12px;
}
.jumpmodule #jumppage {
    height: 20px;
    margin: 0;
    padding: 0;
    width: 20px;
}
.jumpmodule .jump_btn {
    background: url("/wellpt-web/resources/theme/images/v1_icon.png") no-repeat scroll 0 -165px transparent;
    border: 1px solid #DEE1E2;
    color: #0F599C;
    font-size: 12px;
    height: 22px;
    padding: 2px 5px;
}

.lastpage {
    float: left;
    margin-left: -10px;
    margin-right: 5px;
    cursor: pointer;
}

.firstpage {
	cursor: pointer;	
    float: left;
    margin-left: 5px;
    margin-right: -5px;
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
    	function entyKeyBoard(event,obj,currentPage){
    		var code = event.keyCode;
    	    if (code == 13) {
    	        go("goto",currentPage);
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
				var value = $(".cond_class_bg").parent().attr("value");
				alert("value " + value);
				params.value = value;
				var appointColumn = $(".cond_class_bg").parent().attr("appointColumn");
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
				var orderbyArr = $("#orderbyArr").val();
				params.viewUuid = viewUuid;
				params.title = title;
				params.orderbyArr =orderbyArr;
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
			var gotopage = $("#jumppage").val();
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
				}else if(method == "goto") {
					if(gotopage==""){
						alert("请输入页数！");
						return false;
					}
					if(gotopage.match(/^\+?[1-9][0-9]*$/)){
						if(gotopage<=totalPages){
							params.currentPage = gotopage;
						}else{
							alert("输入的页数超出总页数！");
							return false;
						}
					}else{
						alert("请输入合法的页数！");
						return false;
					}
				}else if(method == "first"){
					params.currentPage = 1;
				}else if(method == "last"){
					params.currentPage = totalPages;
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
				}else if(method == "goto") {
					if(gotopage==""){
						alert("请输入页数！");
						return false;
					}
					if(gotopage.match(/^\+?[1-9][0-9]*$/)){
						if(gotopage<=totalPages){
							page = gotopage;
						}else{
							alert("输入的页数超出总页数！");
							return false;
						}
					}else{
						alert("请输入合法的页数！");
						return false;
					}
				}else if(method == "first"){
					page = 1;
				}else if(method == "last"){
					page = totalPages;
				}
				alert(page);
				alert(viewUuid);
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
				}else if(method == "goto") {
					if(gotopage==""){
						alert("请输入页数！");
						return false;
					}
					if(gotopage.match(/^\+?[1-9][0-9]*$/)){
						alert(1);
						if(gotopage<=totalPages){
							page = gotopage;
						}else{
							alert("输入的页数超出总页数！");
							return false;
						}
					}else{
						alert("请输入合法的页数！");
						return false;
					}
				}else if(method == "first"){
					page = 1;
				}else if(method == "last"){
					page = totalPages;
				}
				params.viewUuid = viewUuid;
				params.currentPage = page;
				params.pageSize = pageSize;
				var value = $(".cond_class_bg").attr("value");
				params.value = value;
				var appointColumn = $(".cond_class_bg").attr("appointColumn");
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
				var orderbyArr = $("#orderbyArr").val(); 
				params.viewUuid = viewUuid;
				params.title = title;
				params.orderbyArr = orderbyArr;
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
					
					<div style="float: left;padding-top: 2px;">每页显示5条  共19条 | </div><div class="firstpage" onclick="go('first',1);"><img src="/wellpt-web/resources/pt/images/v1_first.png" /></div>
					
						<div
							
							class="txt_page_no">
							<img src="/wellpt-web/resources/pt/images/v1_prev.png" width="11"
								height="11" />
						</div>
						 
							<div onclick="gotopage_btn(1,1)"
								
								class="nub_page_">
								1
							</div>
						 
							<div onclick="gotopage_btn(1,2)"
								class="nub_page"
								>
								2
							</div>
						 
							<div onclick="gotopage_btn(1,3)"
								class="nub_page"
								>
								3
							</div>
						 
							<div onclick="gotopage_btn(1,4)"
								class="nub_page"
								>
								4
							</div>
						
						<div
							class="txt_page" onclick="go('next',1)" onmousemove="page_move(this)" onmouseout="page_out(this)"
							>
							<img src="/wellpt-web/resources/pt/images/v1_next.png" />
						</div>
						<input value="4" type="hidden" id="allpage" />
						<input value="5" type="hidden" id="pagesize" />
					

					

					

					
					<div class="jumpmodule"><div class="lastpage" onclick="go('last',1);"><img src="/wellpt-web/resources/pt/images/v1_last.png" /></div> | 第 <input type="text" id="jumppage" onkeyup="entyKeyBoard(event,this,1);" value="1" /> 页 /共4页  </div>
					<!--   <input id="jump_btn" class="jump_btn" type="button" onclick="go('goto',1);" value="确定"  /> -->
					
				</div>
			</div>
	</body>
	
</html>

			</div>
		</div>
		</div>
	</div>	
	<script type="text/javascript" src="/wellpt-web/resources/jquery/jquery.js"></script>
	<script type="text/javascript"
		src="/wellpt-web/resources/pt/js/global.js"></script>
	<script type="text/javascript"
		src="/wellpt-web/resources/pt/js/basicdata/dyview/dyview_explain.js"></script>
		
			<script type="text/javascript" src="/wellpt-web//resources/jqueryui/js/jquery-ui.js"></script>
		
			<script type="text/javascript" src="/wellpt-web//resources/pt/js/workflow/work/list_work_view.js"></script>
		
			<script type="text/javascript" src="/wellpt-web//resources/pt/js/org/unit/jquery.unit.js"></script>
		
		
</body>
</html>

