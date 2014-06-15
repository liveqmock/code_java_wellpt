<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="/pt/common/header.jsp"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<link href="${ctx}/resources/jBox/Skins/Blue/jbox.css" rel="stylesheet"
	type="text/css" />
 
<body>
	<div class="row-fluid sortable">
		<div class="box span12">
			<div class="box-header well" data-original-title>
				<h2>
					<i class="icon-edit"></i> 工作任务
				</h2>
				<div class="box-icon"></div>
			</div>
			<div class="box-content">
				<form class="form-horizontal" action="${ctx }/worktask/daily/save"
					method="post">
					<fieldset> 
						 
						<div class="control-group">
							<label class="control-label"  >任务名称 </label>
							<div class="controls">
								${pojo.taskName }
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" >状态</label>
							<div class="controls">
								${pojo.statename }
							</div>

						</div>
						<div class="control-group">
							<label class="control-label" >目标与要求</label>
							<div class="controls">
								${pojo.target }
							</div>

						</div><div class="control-group">
							<label class="control-label"  >责任人 </label>
							<div class="controls">
								${pojo.dutyman }
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" >督办人</label>
							<div class="controls">
								${pojo.supervisor }
							</div>

						</div>
						<div class="control-group">
							<label class="control-label" >共享人</label>
							<div class="controls">
								${pojo.sharer }
							</div>

						</div><div class="control-group">
							<label class="control-label"  >计划开始时间 </label>
							<div class="controls">
								${pojo.planBeginTime }
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" >计划完成时间</label>
							<div class="controls">
								${pojo.planEndTime }
							</div>

						</div>
						<div class="control-group">
							<label class="control-label" >预估工作量</label>
							<div class="controls">
								${pojo.planWorkCount }
							</div>

						</div>
 
						<div class="form-actions"> 
							<button type="reset" onclick="window.close();" class="btn">关闭</button>
						</div>

					</fieldset>
				</form>

			</div>
		</div>
		<!--/span-->

	</div>
	<script type="text/javascript" src="${ctx}/resources/pt/js/global.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/jqgrid/js/i18n/grid.locale-cn.js"></script>
	<script src="${ctx}/resources/jBox/jquery.jBox.src.js"
		type="text/javascript"></script>
	<script src="${ctx}/resources/jBox/i18n/jquery.jBox-zh-CN.js"
		type="text/javascript"></script>
	<script type="text/javascript">
		$.jgrid.no_legacy_api = true;
		$.jgrid.useJSON = true;
		// 加载全局国际化资源
		I18nLoader.load("/resources/pt/js/global");
		// 加载动态表单定义模块国际化资源
		I18nLoader.load("/resources/worktask/js/daily/editpage");
	</script> 
</body>
</html>