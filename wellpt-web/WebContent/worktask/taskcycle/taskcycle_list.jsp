<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="/pt/common/header.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<body>
	<div class="row-fluid sortable">
		<div class="box span12">
			<div class="box-header well" data-original-title>
				<h2>周期性任务</h2>
				<div class="box-icon"></div>
			</div>
			<script>
				function doadd() {
				 
					window.open ("${ctx }/worktask/taskcycle/editpage.action",'newwindow','height=600,width=1000,top=50,left=50,toolbar=no,menubar=no,scrollbars=no, resizable=no,location=no, status=no') ;
				}
				function doedit(uuid) {
					window.open ("${ctx }/worktask/taskcycle/editpage.action?uuid="+ uuid,'newwindow','height=600,width=1000,top=50,left=50,toolbar=no,menubar=no,scrollbars=no, resizable=no,location=no, status=no') ;
				 
				}
				function dodel(uuid){ 
					if(confirm('您确定要删除吗？')){
						window
						.open("${ctx }/worktask/taskcycle/del.action?uuid="
								+ uuid);
					}
					
				}
			</script>
			<div class="box-content">
				<form action="${ctx }/worktask/taskcycle/list.action" method="post">
					<div>
						<a class="btn btn-info" href="#nogo" onclick="doadd();"> <i
							class="icon-edit icon-white"></i> 新增
						</a> <input type="text" name="name" value="${name }" />
						<button type="submit" class="btn">查 询</button>
					</div>
				</form>
				<table class="table table-bordered table-striped table-condensed">
					<thead>
						<tr>

							<th>名称</th>
							<th>类型</th>
							<th>执行状态</th>
							<th>时间间隔</th>
							<th>共享者</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${page.result }" var="item">
							<tr>

								<td style="cursor: pointer;">${item.name }</td>
								<td style="cursor: pointer;" class="center">${item.typeName
									}</td>
								<td style="cursor: pointer;" class="center">${item.stateName
									}</td>
								<td style="cursor: pointer;" class="center">${item.timeAfter
									}</td>
								<td style="cursor: pointer;" class="center">${item.sharernames
									}</td>
								<td style="cursor: pointer;" class="center"><a href='#nogo'
									onclick="doedit('${item.uuid}')"    >修改</a>|<a href='#nogo'
									onclick="dodel('${item.uuid}')"    >删除</a></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>

				<div class="pagination pagination-centered">${pageUtils }</div>
			</div>
		</div>
		<!--/span-->
	</div>

<!-- 不能删除 -->
<div id="iframe_fix" class="clearfix block lines" style="height:0px;width:0px;visibility: hidden;clear: both;"></div>




</body>
</html>