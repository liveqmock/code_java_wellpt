<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<%@ include file="/pt/common/taglibs.jsp"%>
<%@ include file="/pt/common/meta.jsp"%>
<%@ include file="/pt/dyform/dyform_css.jsp"%>
<title>${workBean.title}</title>
<link type="text/css" rel="stylesheet"
	href="${ctx}/resources/chosen/chosen.min.css" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/resources/pt/css/workflow/work_view.css" />
</head>
<body>
	<div class="div_body">
		<div class="div_body_content">
		    <!-- add by huanglinchuan 2014.10.18 begin -->
		    <div class="" id="topHeader">
		    <!-- add by huanglinchuan 2014.10.18 end -->
			<div class="form_header">
				<!--标题-->
				<div class="form_title">
					<h2>${workBean.title}</h2>
					<div class="form_close" title="关闭"></div>
				</div>
				<div id="toolbar" class="form_toolbar"
					style="padding: 8px 0 10px 0px;">
					<div id="mini_wf_opinion" style="margin-bottom: 2px;">
						<!-- 				<div class="wf_opinion"> -->
						<!-- 					<div class="sign-opinion-value"> -->
						<!-- 						<select class="opinion-select" data-placeholder="签署意见"> -->
						<!-- 							<option></option> -->
						<!-- 							<option>同意</option> -->
						<!-- 							<option>不同意</option> -->
						<!-- 							<option>拒绝</option> -->
						<!-- 						</select> -->
						<!-- 					</div> -->
						<!-- 					<div class="sign-opinion-input"> -->
						<!-- 						<textarea></textarea> -->
						<!-- 						<span class="sign-open"></span> -->
						<!-- 						<div class="navbar" -->
						<!-- 							style="padding: 0px; margin: 0px; border: none;"> -->
						<!-- 							<div class="navbar-inner" -->
						<!-- 								style="padding: 0px; margin: 0px; border: none;"> -->
						<!-- 								<ul class="nav pull-right"> -->
						<!-- 									<li class="open"><a href="#" class="dropdown-toggle" -->
						<!-- 										data-toggle="dropdown"><span class="btn-sign-opinion"></span> -->
						<!-- 									</a> -->
						<!-- 										<ul -->
						<!-- 											class="pull-right dropdown-navbar navbar-pink dropdown-menu dropdown-caret dropdown-closer dropdown-custom" -->
						<!-- 											style="right: -7px; top: 28px;"> -->
						<!-- 											<li class="nav-header"><span><i -->
						<!-- 													class="icon-cog-blue"></i>管理</span></li> -->
						<!-- 											<li><a href="#"> -->
						<!-- 													<div class="clearfix" style="margin: 0 2px; border: none;"> -->
						<!-- 														<div class="accordion" id="accordion"> -->
						<!-- 															<div class="accordion-group"> -->
						<!-- 																<div class="accordion-heading"> -->
						<!-- 																	<a class="accordion-toggle collapsed" href="#">意见分类1 -->
						<!-- 																	</a> <span class="accordion-toggle-icon collapsed"></span> -->
						<!-- 																</div> -->

						<!-- 																<div class="accordion-body collapse in"> -->
						<!-- 																	<div class="accordion-inner"> -->
						<!-- 																		<ul> -->
						<!-- 																			<li class="opinion-menu-item"><a href="#" -->
						<!-- 																				tabindex="-1">同意1同意1同意1同意1同意1同意1同意1</a></li> -->
						<!-- 																			<li class="opinion-menu-item"><a href="#" -->
						<!-- 																				tabindex="-1">不同意1</a></li> -->
						<!-- 																			<li class="opinion-menu-item"><a href="#" -->
						<!-- 																				tabindex="-1">同意1</a></li> -->
						<!-- 																			<li class="opinion-menu-item"><a href="#" -->
						<!-- 																				tabindex="-1">同意1</a></li> -->
						<!-- 																			<li class="opinion-menu-item"><a href="#" -->
						<!-- 																				tabindex="-1">同意同意同意同意同意同意不同意不同意fffffffff</a></li> -->
						<!-- 																		</ul> -->
						<!-- 																	</div> -->
						<!-- 																</div> -->
						<!-- 															</div> -->

						<!-- 															<div class="accordion-group"> -->
						<!-- 																<div class="accordion-heading"> -->
						<!-- 																	<a class="accordion-toggle" href="#">意见分类2</a> <span -->
						<!-- 																		class="accordion-toggle-icon"></span> -->
						<!-- 																</div> -->

						<!-- 																<div class="accordion-body collapse"> -->
						<!-- 																	<div class="accordion-inner"> -->
						<!-- 																		<ul> -->
						<!-- 																			<li>同意2</li> -->
						<!-- 																			<li>不同意2</li> -->
						<!-- 																		</ul> -->
						<!-- 																	</div> -->
						<!-- 																</div> -->
						<!-- 															</div> -->

						<!-- 															<div class="accordion-group"> -->
						<!-- 																<div class="accordion-heading"> -->
						<!-- 																	<a class="accordion-toggle" href="#">意见分类3</a> <span -->
						<!-- 																		class="accordion-toggle-icon"></span> -->
						<!-- 																</div> -->

						<!-- 																<div class="accordion-body collapse"> -->
						<!-- 																	<div class="accordion-inner"> -->
						<!-- 																		<ul> -->
						<!-- 																			<li>同意3</li> -->
						<!-- 																			<li>不同意3</li> -->
						<!-- 																		</ul> -->
						<!-- 																	</div> -->
						<!-- 																</div> -->
						<!-- 															</div> -->
						<!-- 														</div> -->
						<!-- 													</div> -->
						<!-- 											</a></li> -->
						<!-- 										</ul></li> -->
						<!-- 								</ul> -->
						<!-- 							</div> -->
						<!-- 						</div> -->
						<!-- 					</div> -->
						<!-- 				</div> -->
					</div>
					<div class="form_operate wf_operate" style="display: none;">
						<c:forEach var="btn" items="${workBean.buttons}">
							<button id="${btn.id}" name="${btn.code}" taskId="${btn.taskId}"
								userIds="${btn.users}" copyUserIds="${btn.copyUsers}" style="display: none;">${btn.name}</button>
						</c:forEach>
						<!-- 				<button id="btn_close" onclick="window.close();" type="button">关闭</button> -->
						<!-- 				&nbsp;&nbsp; -->
					</div>
					<!-- 	<button id="btn_save" class="btn">保存</button> -->
					<!-- 	<button id="btn_submit" class="btn">提交</button> -->
					<!-- 	<a href="#" id="rollback" class="easyui-linkbutton">退回</a> -->
					<!-- 	<a href="#" id="cancel" class="easyui-linkbutton">撤回</a> -->
					<!-- 	<a href="#" id="transfer" class="easyui-linkbutton">转办</a> -->
					<!-- 	<a href="#" id="counterSign" class="easyui-linkbutton">会签</a> -->
					<!-- 	<a href="#" id="attention" class="easyui-linkbutton">关注</a> -->
					<!-- 	<a href="#" id="webPrint" class="easyui-linkbutton">打印页面</a> -->
					<!-- 	<a href="#" id="print" class="easyui-linkbutton">套打</a> -->
				</div>
			</div>
			</div>
			<div class="form_content"
				style="width: 875px; overflow-x:hidden; margin: 0 15px 0 25px;background-color:#ffffff;">
				<div id="process" style="display: none;">
					<div class="proce proce1">
						<ul>
							<li class="tx1"><span id="pre_task_name">前一环节名</span></li>
							<li class="tx2"><span id="pre_task_assignee">办理人</span></li>
						</ul>
					</div>
					<div class="proce proce2">
						<ul>
							<li class="tx1"><span id="cur_task_name">当前环节名</span></li>
							<li class="tx2"><span id="cur_task_assignee">办理人</span></li>
						</ul>
					</div>
					<div class="proce proce3">
						<ul>
							<li class="tx1 tx3"><span id="next_task_name">下一环节名</span></li>
							<li class="tx2 tx3"><span id="next_task_assignee">办理人</span></li>
						</ul>
					</div>
					<div class="proce proce4"></div>
					<div class="proce view_process">
						<span class="view_process_img"><a href="#">查阅办理过程</a></span>
					</div>
					<!-- 					<div class="proce"> -->
					<!-- 						<span class="timeline"><a href="#">查看时间轴</a></span> -->
					<!-- 					</div> -->
				</div>
			</div>
			
			<form:form id="wf_form" commandName="workBean" action="new"
				method="post" cssClass="cleanform">
				<form:hidden id="wf_flowDefUuid" path="flowDefUuid"></form:hidden>
				<form:hidden id="wf_flowDefId" path="flowDefId"></form:hidden>
				<form:hidden id="wf_flowInstUuid" path="flowInstUuid"></form:hidden>
				<form:hidden id="wf_taskInstUuid" path="taskInstUuid"></form:hidden>
				<form:hidden id="wf_taskId" path="taskId"></form:hidden>
				<form:hidden id="wf_formUuid" path="formUuid"></form:hidden>
				<form:hidden id="wf_dataUuid" path="dataUuid"></form:hidden>
				<form:hidden id="wf_title" path="title"></form:hidden>
				<form:hidden id="wf_aclRole" path="aclRole"></form:hidden>
				<form:hidden id="wf_serialNoDefId" path="serialNoDefId"></form:hidden>
				<form:hidden id="wf_suspensionState" path="suspensionState"></form:hidden>
				<form:hidden id="wf_isFirstTaskNode" path="isFirstTaskNode"></form:hidden>
				<c:forEach var="extraParam" items="${workBean.extraParams}">
					<input id="${extraParam.key}" name="${extraParam.key}"
						type="hidden" value="${extraParam.value}" />
				</c:forEach>
			</form:form>
			<!-- 动态表单 -->
			<form id="dyform"></form>
			<div class="view_process_list">
				<div class="view_process_header">
					<a>办理过程</a>
				</div>
				<div class="view_process_content"></div>
			</div>
			<!-- 主子流程共享信息 -->
			<div id="share_flow" style="display: none;">
				<div class="view_process_header">
					<a>子流程跟踪办理情况</a>
				</div>
				<div class="share_flow_content">
					<table class="table view_process_table">
						<thead>
							<tr>
								<th>名称</th>
								<th>办理对象</th>
								<th>办理意见</th>
								<th>办理状态</th>
								<th>当前在办</th>
							</tr>
						</thead>
						<tbody id="share_flow_body">
						</tbody>
					</table>
				</div>
			</div>

			<div>
				<!-- 环节选择 -->
				<div id="dlg_select_task" class="wf-dlg"></div>
				<!-- 跳转环节选择 -->
				<div id="dlg_select_goto_task" class="wf-dlg"></div>
				<!-- 退回环节选择 -->
				<div id="dlg_select_rollback_task" class="wf-dlg"></div>
				<!-- 子流程选择 -->
				<div id="dlg_select_sub_flow">
					<input id="wf_select_sub_flow" type="hidden">
				</div>
				<!-- 子流程合并等待 -->
				<div id="dlg_sub_flow_merge"></div>
				<!-- 套打 -->
				<div id="dlg_select_print_template"></div>
				<input name="opinions" value='${workBean.opinionsAsJson}'
					type="hidden">

				<!-- 签署意见 -->
				<!-- 			<div id="dlg_sign_opinion" class="form-horizontal" -->
				<!-- 				style="display: none; margin-bottom: 0px; padding-bottom: 0px;"> -->
				<!-- 				<div class="nav-collapse nav-collapse-top collapse" -->
				<!-- 					style="margin-bottom: 10px;"> -->
				<!-- 					<ul class="nav pull-right"> -->
				<!-- 						<li class="dropdown user-avatar open"><a href="#" -->
				<!-- 							class="dropdown-toggle" data-toggle="dropdown"><span>常用意见</span></a> -->
				<!-- 							<ul id="common_opinion" class="dropdown-menu" -->
				<!-- 								style="left: -110px;"> -->
				<!-- 															<li><a id="opinion_mgr" href="#"><span>管理</span></a></li> -->
				<!-- 															<li class="divider"></li> -->
				<!-- 															<li><a href="#"><span>同意</span></a></li> -->
				<!-- 															<li><a href="#"><span>不同意</span></a></li> -->
				<!-- 															<li><a href="#"><span>拒绝</span></a></li> -->
				<!-- 															<li class="dropdown-submenu pull-left"><a tabindex="-1" -->
				<!-- 																href="#">More options</a> -->
				<!-- 																<ul class="dropdown-menu"> -->
				<!-- 																	<li><a href="#"><span>同意</span></a></li> -->
				<!-- 																	<li><a href="#"><span>不同意</span></a></li> -->
				<!-- 																	<li><a href="#"><span>拒绝</span></a></li> -->
				<!-- 																</ul></li> -->
				<!-- 							</ul></li> -->
				<!-- 					</ul> -->
				<!-- 				</div> -->
				<!-- 				<div class="control-group" style="margin-bottom: 10px;"> -->
				<!-- 					<div class="textarea"> -->
				<%-- 						<c:if test="${not empty workBean.opinions}"> --%>
				<!-- 							<textarea name="opinionText" style="width: 98%; height: 200px;"> </textarea> -->
				<%-- 						</c:if> --%>
				<%-- 						<c:if test="${empty workBean.opinions}"> --%>
				<!-- 							<textarea name="opinionText" style="width: 98%; height: 245px;"> </textarea> -->
				<%-- 						</c:if> --%>
				<!-- 					</div> -->
				<!-- 				</div> -->
				<!-- 				<div class="control-group" style="margin-bottom: 0px;"> -->
				<%-- 					<c:if test="${not empty workBean.opinions}"> --%>
				<!-- 						<label class="control-label" -->
				<!-- 							style="text-align: left; width: 70px;">意见立场：</label> -->
				<%-- 						<c:forEach var="opinion" items="${workBean.opinions}"> --%>
				<!-- 							<label class="radio inline"> <input type="radio" -->
				<%-- 								name="opinionValue" value="${opinion.value}">${opinion.name} --%>
				<!-- 							</label> -->
				<%-- 						</c:forEach> --%>
				<%-- 					</c:if> --%>
				<!-- 				</div> -->
				<!-- 			</div> -->
				<!-- 常用意见管理 -->
				<!-- 			<div id="dlg_opinion_mgr" style="display: none;"> -->
				<!-- 				<div class="form-horizontal"> -->
				<!-- 					<div class="control-group"> -->
				<!-- 						<label class="control-label" style="width: 89px;" -->
				<!-- 							for="opinion_category">新增意见分类: </label> -->
				<!-- 						<div class="controls form_operate"> -->
				<!-- 							<input id="opinion_category" type="text" placeholder="新分类" -->
				<!-- 								class="input-xlarge" style="width: 524px"> -->
				<!-- 							<button id="btn_opinion_category_add">增加</button> -->
				<!-- 						</div> -->
				<!-- 					</div> -->
				<!-- 					<div class="control-group"> -->
				<!-- 						<div class="controls form_operate"> -->
				<!-- 							<span id="span_opinion_category"> <label -->
				<!-- 								class="radio inline label-opinion-category" -->
				<!-- 								style="display: none;"></label> -->
				<!-- 							</span> -->
				<!-- 							<div class="pull-right"> -->
				<!-- 								<button id="btn_opinion_category_edit">修改</button> -->
				<!-- 								<button id="btn_opinion_category_del">删除</button> -->
				<!-- 								<button id="btn_opinion_category_move_left">左移</button> -->
				<!-- 								<button id="btn_opinion_category_move_right">右移</button> -->
				<!-- 							</div> -->
				<!-- 						</div> -->
				<!-- 					</div> -->
				<!-- 				</div> -->
				<!-- 				<div -->
				<!-- 					style="border: none; width: 100%; height: 1px; background-color: #ccc; margin-bottom: 10px;"></div> -->
				<!-- 				<div class="form-horizontal"> -->
				<!-- 					<div class="control-group"> -->
				<!-- 						<label class="control-label" style="width: 47px;" -->
				<!-- 							for="opinion_content">新意见: </label> -->
				<!-- 						<div class="controls form_operate"> -->
				<!-- 							<input id="opinion_content" type="text" placeholder="新意见" -->
				<!-- 								class="input-xlarge" style="width: 566px"> -->
				<!-- 							<button id="btn_opinion_content_add">增加</button> -->
				<!-- 						</div> -->
				<!-- 					</div> -->
				<!-- 					<div class="control-group"> -->
				<!-- 						<div class="controls form_operate"> -->
				<!-- 							<select id="opinion_contents" name="opinion_contents" -->
				<!-- 								class="input-xlarge" multiple="multiple" size="8" -->
				<!-- 								style="width: 626px; overflow: auto;"> -->
				<!-- 							</select> -->
				<!-- 							<div class="btn-group"> -->
				<!-- 								<div style="margin-top: 9px;"> -->
				<!-- 									<button id="btn_opinion_content_del">删除</button> -->
				<!-- 								</div> -->
				<!-- 								<div style="margin-top: 9px;"> -->
				<!-- 									<button id="btn_opinion_content_move_up">上移</button> -->
				<!-- 								</div> -->
				<!-- 								<div style="margin-top: 9px;"> -->
				<!-- 									<button id="btn_opinion_content_move_down">下移</button> -->
				<!-- 								</div> -->
				<!-- 							</div> -->
				<!-- 						</div> -->
				<!-- 					</div> -->
				<!-- 				</div> -->
				<!-- 			</div> -->
				<!-- 常用意见分类编辑管理 -->
				<!-- 			<div id="dlg_opinion_category_mgr" style="display: none;"> -->
				<!-- 				<div class="control-group"> -->
				<!-- 					<label class="control-label" for="textinput">意见分类</label> -->
				<!-- 					<div class="controls"> -->
				<!-- 						<input id="opinion_category_edit" name="opinion_category_edit" -->
				<!-- 							type="text" placeholder="placeholder" class="input-xlarge"> -->
				<!-- 					</div> -->
				<!-- 				</div> -->
				<!-- 			</div> -->

				<!-- 办理意见 -->
				<div id="dlg_view_opinion"></div>
				<!-- 办理过程 -->
				<div id="dlg_view_process" style="display: none;">
					<div class="controls"
						style="margin-bottom: 10px; margin-left: 10px;">
						<label class="checkbox inline"> <input
							id="show_rollback_record" type="checkbox" checked="checked">显示撤回前及撤回记录
						</label> <label class="checkbox inline"> <input
							id="show_no_opinion_record" type="checkbox" checked="checked">显示未签署意见记录
						</label>
						<label class="checkbox inline view_process">
						<span class="view_process_img"><a href="#">查阅流程图</a></span>
						 </label>
					</div>
					<div id="process_content" style="margin-left: 10px;"></div>
				</div>
				<div style="display: none">
					<iframe id="print_form_iframe" name="print_form_iframe"
						style="width: 1px; height: 0px;"></iframe>
					<form id="print_form" name="print_form" action="" method="post"
						target="print_form_iframe">
						<input type="hidden" name="filename" value="${workBean.title}" />
						<input type="hidden" name="flowInstUuid"
							value="${workBean.flowInstUuid}" /> <input type="hidden"
							name="taskInstUuid" value="${workBean.taskInstUuid}" /> <input
							type="hidden" name="formUuid" value="${workBean.formUuid}" /> <input
							type="hidden" name="dataUuid" value="${workBean.dataUuid}" /> <input
							type="hidden" name="printService" value="" /> <input
							type="hidden" name="printTemplateId"
							value="${workBean.printTemplateId}" />
					</form>
				</div>
			</div>
			<!-- Project -->
			<%@ include file="/pt/dyform/dyform_js.jsp"%>
	 		<%@ include file="/pt/dyform/dyform_excel_js.jsp"%>
			<script type="text/javascript"
				src="${ctx}/resources/chosen/chosen.jquery.min.js"></script>
			<script type="text/javascript"
				src="${ctx}/resources/pt/js/common/jquery.workflowOpinion.js"></script>
			<script type="text/javascript"
				src="${ctx}/resources/pt/js/workflow/work/work_view.js"></script>
			<script type="text/javascript"
				src="${ctx}/resources/pt/js/common/jquery.cmsWindown.js"></script>
		</div>
	</div>
	<div class="body_foot"></div>
	<script type="text/javascript">
		$(function() {
			/*add by huanglinchuan 2014.10.18 begin*/
			window.onscroll=function(){ 
				if ($(document).scrollTop() > 0) 
				{ 
					$("#topHeader").addClass('floatHeader'); 
					$(".form_header").addClass("formHeader_top0");
				}else{ 
					$("#topHeader").removeClass('floatHeader'); 
					$(".form_header").removeClass("formHeader_top0");
				} 
			}; 	
			/*add by huanglinchuan 2014.10.18 end*/
		});
	</script>
</body>
</html>