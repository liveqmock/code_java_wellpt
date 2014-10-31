<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>

<title>整灯计划</title>
</head>
<body>
<%@ include file="/pt/common/taglibs.jsp"%>
<%@ include file="/pt/common/meta.jsp"%>
<%@ include file="/pt/dyform/dyform_css.jsp"%>
<%@ include file="/pt/dyform/dyform_js.jsp"%>
<%@ include file="/pt/dyform/dyform_ctljs.jsp"%>
<script type="text/javascript" src="${ctx}/resources/pt/js/ldx/prodplan/productPlan.js"></script>
<style type="text/css">
.searchDiv {
	float: top;
	width: 99%;
	height: 50px;
}
.mainPlanDiv {
	float: top;
	width: 99%;
	margin-top: 5px;
	height: 280px;
	border: 1px solid #C9C9C9;
	overflow: auto;
}
.pageDiv {
	width: 99%;
	float: top;
	height: 30px;
	margin-top: 5px;
}
.detailPlanDiv {
	float: top;
	width: 99%;
	margin-top: 1px;
	height: 275px;
}
.detailLeftDiv {
	margin-left: 5px;
	height: 270px;
	float: left;
	width: 52%;
	border: 1px solid #C9C9C9;
	overflow: auto;
}
.detailRightDiv {
	height: 270px;
	float: right;
	width: 47%;
	border: 1px solid #C9C9C9;
	overflow: auto;
}
.smartBtn{
	background: url("../../resources/pt/images/v1_icon.png") no-repeat scroll 0 -165px transparent;
	border: 1px solid #dee1e2;
	color: #0f599c;
	display: block;
	float: left;
	font-size: 12px;
	height: 22px;
	width:36px;
	margin: 0 3px;
	padding: 0 5px;
}
table{
	width: 100%;
	border-color: gray;
	color: #2E2E2E;
	font-family: "Microsoft YaHei";
	font-size: 12px;
	text-align: left;
	border-collapse: separate;
}
table tr{
	white-space: nowrap;
}
table th{
	font-family: "Microsoft YaHei";
	font-size: 12px;
	overflow: hidden;
	display: block;
	float: left;
	word-break: keep-all;
	white-space: nowrap;
	overflow: hidden;
	text-overflow: ellipsis;
	padding: 0px;
	line-height: 20px;
	text-align: center;
	vertical-align: top;
	background: none repeat scroll 0 0 #0F599C;
	border-right: 1px solid #ffffff;
	color: #fff;
}
table td{
	border: medium none;
	font-family: "Microsoft YaHei";
	font-size: 12px;
	overflow: hidden;
	display: block;
	float: left;
	word-break: keep-all;
	line-height: 20px;
	height:20px;
	white-space: nowrap;
	text-overflow: ellipsis;
	padding: 0px;
	border-right: 1px solid #C9C9C9;
	border-bottom: 1px solid #C9C9C9;
}
.divTip {
	display:none;
	padding: 2px;
	background-color:#ffffff;
	font-size:12px;
	border:1px solid #aaa;
	-webkit-box-shadow:0 0 5px #aaa;
	box-shadow:2px 2px 30px #909090;
	z-index:100;
}
.smartTr{
	height: 20px;
	line-height: 20px;
	cursor: pointer;
}
.commonPlan{
	color: #000000;
}
.errorPlan{
	color: #D90000;
}
.asignPlan{
	color: #008400;
}
.finishPlan{
	color: #006CD9;
}
.clear{
	clear:both;
}
.selectedrow{
	background: #B3D9FF;
}
</style>


	<div id="divTip" class="divTip"> 
		提示内容
	</div>
	<div class="div_body" style="">
		<!--顶层搜索栏begin-->
		<div id="searchDiv" class="searchDiv">
			<!--生产订单修改栏 begin-->
			<div class="fisherFilterFieldset" style="float:left;display: block;height: 100%;width: 20%;margin-top:5px;margin-left:5px;height:45px;border: 1px solid #C9C9C9;">
				<div style="float:left;width:75%;margin-left:5px;">
					<table id="main_plan"  >
						<thead>
							<tr>
								<th width="24%">
									优先级
								</th>
								<th width="22%">
									备注
								</th>
								<th width="29%">
									难易度
								</th>
								<th width="21%">
									生管
								</th>
						   </tr>
					   </thead>
					   <tbody>
						   <tr>
								<td width="24%">
									<input type="text" name="sno" id="sno" style="border:0;padding:0;margin:0;height:100%;width:100%;"/>
								</td>
								<td width="22%">
									<input type="text" name="desc" id="desc" style="border:0;padding:0;margin:0;height:100%;width:100%;"/>
								</td>
								<td width="29%">
									<input type="text" name="zlevel" id="zlevel" style="border:0;padding:0;margin:0;height:100%;width:100%;"/>
								</td>
								<td width="21%">
									<select name="productionManager" id="productionManager" style="border:0;padding:0;margin:0;height:100%;width:100%;font-size: 13px;">
										<option value=""></option>
										<c:forEach var="sg" items="${selectSgList}">
											<option value="${sg}">${sg}</option>
										</c:forEach>
									</select>
								</td>
								 <td class="fisherFilterValueTd2" width="35%" style="display:none;">
									<input type="text" name="productOrderNo" id="productOrderNo" style="border:0;padding:0;margin:0;height:100%;width:100%;"/>
								</td>
						   </tr>
					   </tbody>
					</table>
				</div>
				<div style="float:left;width:30px;padding-left:5px;">
					<button type="button" class="smartBtn" id="B_save" onclick="saveMainPlan('');">保存</button>
					<button type="button" class="smartBtn" id="B_complete" onclick="saveMainPlan('4');">完工</button>
			    </div>
			</div>
			<!--订单修改栏 end-->
			<!--搜索条件begin-->
			<div style="float:left;display: block;height: 100%;width:57%;margin-top:5px;margin-left:5px;height:45px;border: 1px solid #C9C9C9;">
				<table id="flexigrid-filter" style="width: 100%">
					<tr>
				        <td style="width:14%;">
				        	<select name="searchType1" id="searchType1" style="border:0;padding:0;margin:0;height:100%;width:100%;font-size: 13px;">
								<option value="zsg">生管</option>
								<option value="zxh">线别</option>
								<option value="class">课别</option>
								<option value="dwerks">厂别</option>
								<option value="department">部别</option>
								<option value="zzt">状态</option>
							</select>
						</td>
						<td style="width:26%;" colspan="3">
							<select name="searchValue1" id="searchValue1" style="border:0;padding:0;margin:0;height:100%;width:100%;font-size: 13px;">
								<option value=""></option>
								<c:forEach var="sg" items="${selectSgList}">
									<option value="${sg}">${sg}</option>
								</c:forEach>
							</select>
						</td>
						<td style="width:14%;">
							<select name="searchType2" id="searchType2" style="border:0;padding:0;margin:0;height:100%;width:100%;font-size: 13px;">
								<option value=""></option>
								<option value="zxh">线别</option>
								<option value="zsg">生管</option>
								<option value="class">课别</option>
								<option value="dwerks">厂别</option>
								<option value="department">部别</option>
								<option value="zzt">状态</option>
							</select>
						</td> 
						<td style="width:45%;" colspan="2">
							<select name="searchValue2" id="searchValue2" style="border:0;padding:0;margin:0;height:100%;width:100%;font-size: 13px;">
								<option value=""></option>
							</select>
						</td>
				   </tr>
				   <tr>
				   		<td style="width:14%;">
							<select name="searchType3" id="searchType3" style="border:0;padding:0;margin:0;height:100%;width:100%;font-size: 13px;">
								<option value="edatu">SD交期</option>
								<option value="gstrp">上线日期</option>
								<option value="gltrp">完工日期</option>
								<option value="scrq">生产日期</option>
								<option value="mpsdate">MPS上线日期</option>
								<option value="yjcq">预计船期</option>
							</select>
						</td> 
						<td style="width:12%;">
							<input type="text" name="searchValue3" id="searchValue3" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="Wdate" style="border:0;padding:0;margin:0;height:100%;width:100%;"/>
						</td>
						<td style="width:2%;">
							至
						</td>
						<td style="width:12%;">
							<input type="text" name="searchValue3to" id="searchValue3to" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="Wdate" style="border:0;padding:0;margin:0;height:100%;width:100%;"/>
						</td>
						<td style="width:14%;">
							<select name="searchType4" id="searchType4" style="border:0;padding:0;margin:0;height:100%;width:100%;font-size: 13px;">
								<option value="aufnr">生产订单</option>
								<option value="kdauf">销售订单</option>
								<option value="sortl">客户号</option>
								<option value="matnr">物料ID</option>
								<option value="zgrun">物料描述</option>
								<option value="zqt">备注</option>
							</select>
						</td> 
						<td style="width:14%;">
							<input type="text" name="searchValue4" id="searchValue4" style="border:0;padding:0;margin:0;height:100%;width:100%;"/>
						</td>
						<td style="width:30%;border:0;">
							<div style="float:right;width: 100%;">
								<button type="button" class="smartBtn" id="B_Reset">重置</button>
								<button type="button" class="smartBtn" id="B_Search">查找</button>
								<button type="button" class="smartBtn" id="B_expAll" onclick="alert('功能建设中');">导出</button>
								<input type="checkBox" id="sgdd" style="width:15px;" checked="true" title="勾选查看手工订单"/>
				        	</div>
				        </td>
				   </tr>
				</table>
			</div>
			<!--搜索条件end-->
			<!--报表栏begin-->
			<div style="float:left;display: block;height:100%;width: 6%;height:45px;margin-top:5px;margin-left:5px;border: 1px solid #C9C9C9;">
				<table>
					<tr>
						<th width="100%">
							报表类型
						</th>
					</tr>
					<tr>
						<td width="100%">
							<select name="report_type" id="report_type" style="border:0;padding:0;margin:0;height:100%;width:100%;font-size: 13px;">
								<option value=""></option>
								<option value="3">3日计划</option>
								<option value="7">7日计划</option>
								<option value="10">10日计划</option>
							</select>
						</td>
					</tr>
				</table>
			</div>
			<!--报表栏end-->
			<!--操作栏begin-->
			<div style="float:left;display: block;height:100%;width: 130px;height:45px;margin-top:5px;margin-left:5px;border: 1px solid #C9C9C9;">
				<div style="float:top;width:100%;">
					<div style="margin-left:0px;text-align:center;">
						<button type="button" class="smartBtn" id="B_showUpload" onclick="mpsUpload();">分配</button>
						<button type="button" class="smartBtn" id="B_showWgUpload" onclick="wgUpload();">报工</button>
						<button type="button" class="smartBtn" id="B_publish" onclick="publishAllPlan();">发布</button>
					</div>
				</div>
				<div style="float:top;width:100%;">
					<div style="margin-left:0px;text-align:center;">
						<button type="button" class="smartBtn" id="B_refWo" onclick="manualRefresh(3)">追加</button>
						<button type="button" class="smartBtn" id="B_refresh" onclick="manualRefresh(1)">欠料</button>
						<button type="button" class="smartBtn" id="B_refMps" onclick="manualRefresh(2)">MPS</button>
					</div>
				</div>
			</div>
			<!--操作栏end-->
		</div>
		<!--顶层搜索栏end-->
		<div class="clear"></div>
		<!--排程主数据栏begin-->
		<div id="mainPlanDiv" class="mainPlanDiv">
			<div id="mainPlanInnerDiv" style="width:2210px;height:100%;margin-left:2px;">
				<table id="nonEditTable">
					<thead class='fixedHeader'>
						<tr class="thead_tr">
							<th width="20px">&nbsp;</th>
							<th width="40px">&nbsp;</th>
							<th width="60px">&nbsp;</th>
							<th width="90px">&nbsp;</th>
							<th width="40px">&nbsp;</th>
							<th width="130px" id="sortByZxhscrq" class="sortImgUp" title="点击排序" onclick="sortByPage('zxhscrq')">
								&nbsp;
							</th>
							<th width="90px">&nbsp;</th>
							<th width="30px">&nbsp;</th>
							<th width="60px" id="sortByEdatu" class="sortImgUp" title="点击排序" onclick="sortByPage('edatu')">
								&nbsp;
							</th>
							<th width="60px" id="sortByZyjcq" class="sortImgUp" title="点击排序" onclick="sortByPage('zyjcq')">
								&nbsp;
							</th>
							<th width="60px" id="sortByZmpstime" class="sortImgUp" title="点击排序" onclick="sortByPage('zsxrq')">
								MPS
							</th>
							<th width="80px">&nbsp;</th>
							<th width="200px">&nbsp;</th>
							<th width="40px">&nbsp;</th>
							<th width="60px">&nbsp;</th>
							<th width="60px" onclick="sortByPage('gstrp')">
								&nbsp;
							</th>
							<th width="60px" onclick="sortByPage('gltrp')">
								&nbsp;
							</th>
							<th width="121px">生产标准工时</th>
							<th width="100px">&nbsp;</th>
							<th width="100px">&nbsp;</th>
							<th width="100px">&nbsp;</th>
							<th width="100px">&nbsp;</th>
							<th width="382px">备注</th>
							<th width="40px">&nbsp;</th>
							<th width="60px">&nbsp;</th>
						</tr>
						<tr class="thead_tr">
							<th width="20px"><input type="checkBox" id="checkAll"/></th>
							<th width="40px">生管</th>
							<th width="60px">客户</th>
							<th width="90px">生产订单</th>
							<th width="40px">厂别</th>
							<th width="130px" id="sortByZxhscrq" class="sortImgUp" title="点击排序" onclick="sortByPage('zxhscrq')">
								生产状况
							</th>
							<th width="90px">销售订单号</th>
							<th width="30px">行项</th>
							<th width="60px" id="sortByEdatu" class="sortImgUp" title="点击排序" onclick="sortByPage('edatu')">
								SD交期
							</th>
							<th width="60px" id="sortByZyjcq" class="sortImgUp" title="点击排序" onclick="sortByPage('zyjcq')">
								预计船期
							</th>
							<th width="60px" id="sortByZmpstime" class="sortImgUp" title="点击排序" onclick="sortByPage('zsxrq')">
								上线日期
							</th>
							<th width="80px">ID</th>
							<th width="200px" class='table-sortable:default'>物料描述</th>
							<th width="40px">难易度</th>
							<th width="60px">计划量</th>
							<th width="60px" id="sortByGstrp" class="sortImgUp" title="点击排序" onclick="sortByPage('gstrp')">
								上线日期
							</th>
							<th width="60px" id="sortByGltrp" class="sortImgUp" title="点击排序" onclick="sortByPage('gltrp')">
								完工日期
							</th>
							<th width="60px">总装</th>
							<th width="60px">包装</th>
							<th width="100px">完成量</th>
							<th width="100px">未完成量</th>
							<th width="100px">当前排产量</th>
							<th width="100px">累计排产量</th>
							<th width="120px">状态</th>
							<th width="130px">物料状况</th>
							<th width="130px">其他</th>
							<th width="40px">优先级</th>
							<th width="60px">可排产量</th>
						</tr>
					</thead>
					<tbody id="mainTableBody">
					</tbody>
				</table>
			</div>
		</div>
		<!--排程主数据栏end-->
		<div class="clear"></div>
		<!--翻页信息栏begin-->
		<div id="pageDiv" class="pageDiv">
			<div style="float:left;" width="500px;">
				<div id="latestRefeshDiv" class="shortCell" style="float:top;width:560px;height:28px;">
					<div sytle="float:left;height:28px;width:160px;overflow:hidden;">
						<div style="width:80px;float: left;height: 100%;">
							<input type="text" name="caculator" id="caculator" value="0" style="border:1px solid #C9C9C9;padding:0;margin:0;height:100%;width:80px;" ondblclick="clearCaculator(this);"/>
						</div>
						<div style="width:70px;float: left;height: 100%;">
							<button type="button" class="smartBtn" id="autoArrange" style="width:70px;" onclick="autoArrange()">排产建议</button>
						</div>
					</div>
					<div style="float:left;height:28px;width:300px;margin-left:10px;display: none;">
						<a id="latestRefeshTip" style="width:300px;height:30px;">杨小强在2014-08-07 15:46:31进行了追加工单刷新操作</a>
					</div>
					<div style="float:left;height:28px;width:50px;margin-left:10px;">
						<a id="saveTip" >保存成功</a>
					</div>
				</div>
			</div>
			<div style="width:500px;float:right;height:100%;">
				<div style="float:left;height: 100%;">
					<label id="paginationInfo" style="font-size:12px;"></label>
				</div>
				<div style="float:right;height: 100%;">
					<span align="right" id="wwctrl_B_Previous">
						<button type="button" class="smartBtn" id="B_First" style="width:60px;" onclick="goToPage(1)">首页</button>
						<button type="button" class="smartBtn" id="B_Previous" style="width:60px;" onclick="addPage(-1)">上一页</button>
					</span>
					<span align="right" id="wwctrl_B_Next">
						<button type="button" class="smartBtn" id="B_Next" style="width:60px;" onclick="addPage(1)">下一页</button>
						<button type="button" class="smartBtn" id="B_Last" style="width:60px;" onclick="goToPage(pageInfo.totalPage)">末页</button>
					</span>
				</div>
			</div>
		</div>
		<!--翻页信息栏end-->
		<div class="clear"></div>
		<!--详细排程栏begin-->
		<div id="detailPlanDiv" class="detailPlanDiv">
			<!--左侧详细排程编辑栏begin-->
			<div id="detailLeftDiv" class="detailLeftDiv">
				<div id="mainPlanInnerDiv" style="width:710px;height:100%;margin-left:2px;">
					<table id="detailPlanEdit" style="width:98%;text-overflow:ellipsis;" class="sortable table-stripeclass:alternate">
						<thead class='fixedHeader'>
							<tr>
								<th style='width:90px'>&nbsp;</th>
								<th style='width:100px'>&nbsp;</th>
								<th style='width:80px'>&nbsp;</th>
								<th style='width:80px'>&nbsp;</th>
								<th style='width:197px' colspan="2">计划生产数量</th>
								<th style='width:65px'>&nbsp;</th>
								<th style='width:80px'>&nbsp;</th>
							</tr>
							<tr>
								<th style='width:90px'>生产订单</th>
								<th style='width:100px'>线别</th>
								<th style='width:80px'>工序</th>
								<th style='width:80px'>生产日期</th>
								<th style='width:65px'>A</th>
								<th style='width:65px'>B</th>
								<th style='width:65px'>C</th>
								<th style='width:65px'>完成量</th>
								<th style='width:80px'>操作</th>
							</tr>
						</thead>
						<tbody id="subPlanBody">
						</tbody>
						<tfoot>
							<tr><td style='text-align:center;width:698px;'><a id='addSubPlanLink' >添加排产信息</a></td></tr>
						</tfoot>
					</table>
				</div>
			</div>
			<!--左侧详细排程编辑栏end-->
			<!--右侧详细排程列表栏begin-->
			<div id="detailRightDiv" class="detailRightDiv">
				<div id="mainPlanInnerDiv" style="width:650px;height:100%;margin-left:2px;float: ">
					<table id="detailPlanView" style="width:96%;text-overflow:ellipsis;" class="sortable table-stripeclass:alternate">
						<thead class='fixedHeader'>
							<tr>
								<th id="loadsOrderByXb" title="点击排序" style='width:100px'>线别</th>
								<th style='width:80px'>工序</th>
								<th style='width:180px' id="loadsOrderByGstrp" title="点击排序">生产日期</th>
								<th style='width:152px'>计划生产数量</th>
								<th style='width:100px'>生产负荷<input type="checkBox" id="loadShare" style="width:15px;" title="勾选启用本组产能共享"/></th>
							</tr>
							<tr>
								<th style='width:100px'>
									<select name="loadzxb" id="loadzxb" style="border-bottom:1px solid #0F599C;padding:0;margin:0;height:100%;width:100%;font-size: 13px;">
										<option value=""></option>
										<c:forEach var="xb" items="${xbLoadList}">
											<option value="${xb}">${xb}</option>
										</c:forEach>
									</select>
								</th>
								<th style='width:80px'>
									<select name="loadGxb" id="loadGxb" style="border-bottom:1px solid #0F599C;padding:0;margin:0;height:100%;width:100%;font-size: 13px;">
										<option value="">所有工序</option>
										<option value="all">总装+包装</option>
										<option value="zz">总装</option>
										<option value="bz">包装</option>
									</select>
								</th>
								<th width="180px">
									<div style="float:left;margin-left: 4px;">
										<input type="text" name="startDay" id="startDay" value="${startDay}" style="border-bottom:1px solid #0F599C;padding:0;margin:0;height:100%;width:80px;"/>
									</div>
									<div style="float:left">
									至
									</div>
									<div style="float:left">
										<input type="text" name="endDay" id="endDay" value="${endDay}" style="border-bottom:1px solid #0F599C;padding:0;margin:0;height:100%;width:80px;"/>
									</div>
								</th>
								<th style='width:50px'>A</th>
								<th style='width:50px'>B</th>
								<th style='width:50px'>C</th>
								<th style='width:100px'>
									<a id="B_gather" style="width:50px;height:20px;color: #ffffff;">汇总</a>
									<a id="B_seprate" style="width:50px;height:20px;color: #ffffff;">整理</a>
								</th>
							</tr>
						</thead>
						<tbody id="scfhTbody">
							
						</tbody>
					</table>
				</div>
			</div>
		</div>
		<!--详细排程栏end-->
	</div>
	<!-- jQuery UI begin -->
	<div id="autoSortPlanConfirm" title="生产线排产计划自动调整">
		<table cellpadding="3" class="fisherFilterTable" style="margin-top:5px;width:300px;">
			<tr class="smartTr">
				<td width="80px">线别</td>
				<td width="200px">
					<input type="text" name="autoSortZxb" id="autoSortZxb" readonly="true" style="border:0;padding:0;margin:0;height:100%;width:100%;"/>
				</td>
			</tr>
			<tr class="smartTr">
				<td width="80px">开始日期</td>
				<td width="200px">
					<input type="text" name="autoSortStart" id="autoSortStart" readonly="true" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="Wdate" style="border:0;padding:0;margin:0;height:100%;width:100%;"/>
				</td>
			</tr>
			<tr class="smartTr">
				<td width="80px">结束日期</td>
				<td width="200px">
					<input type="text" name="autoSortEnd" id="autoSortEnd" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="Wdate" style="border:0;padding:0;margin:0;height:100%;width:100%;"/>
				</td>
			</tr>
			<tr class="smartTr">
				<td colspan="2" width="280px" style="border: 0;">
					<div style="float:right;">
						<button type="button" class="smartBtn" id="auto_confirm" onclick="autoConfirm();">确认</button>
						<button type="button" class="smartBtn" id="auto_cancel" onclick="autoCancel();">取消</button>
					</div>
				</td>
			</tr>
		</table>
	</div>
	<div id="wgUploadDialog" title="上传完工模板" style="display:none;">
		<table cellpadding="3" class="fisherFilterTable" style="margin-top:5px;width:505px;margin-top: -23px;">
			<tr class="smartTr">
				<td width="100px"></td>
				<td width="341px">
					<input type="text" name="autoSortZxb" id="autoSortZxb" readonly="true" style="border:0;padding:0;margin:0;height:100%;width:100%;"/>
				</td>
			</tr>
			<tr class="smartTr">
				<td width="100px">生产日期</td>
				<td width="150px">
					<input type="text" name="wgrq" id="wgrq" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="Wdate" style="border:0;padding:0;margin:0;height:100%;width:100%;"/>
				</td>
				<td width="190px">
					<div>
						<button type="button" class="smartBtn" id="btn_wg_download" onclick="downloadWgTemplate();">下载</button>
						<button type="button" class="smartBtn" style="width:90px;" id="btn_mes_download" onclick="downloadMesTemplate();">MES报工数据</button>
					</div>
				</td>
			</tr>
			<tr>
				<td width="100px"><label>选择XLS文件：</label></td>
				<td width="341px">
					<div>
						<input type="file" name="uploadWgFile" id="uploadWgFile" />
					</div>
				</td>
			</tr>
		</table>
	</div>
	
	<div id="mpsUploadDialog" title="MPS模板" style="display:none;">
		<table cellpadding="3" style="margin-top:5px;width:405px;margin-top: 20px;">
			<tr>
				<td width="100px">
					<div>
						<button type="button" class="smartBtn" id="btn_msp_download" style="width:70px;" onclick="downloadMpsTemplate();">模板下载</button>
					</div>
				</td>
				<td width="251px">
					<div>
						<input type="file" name="uploadMpsFile" id="uploadMpsFile" />
					</div>
				</td>
			</tr>
		</table>
	</div>
	
	<div id="wgLoadsDiv" title="完工数量" style="word-break:break-all;">
		<table id="cnCompareTable" style="width:700px;text-overflow:ellipsis;">
			<thead class='fixedHeader'>
				<tr>
					<th width='100px'>线别</th>
					<th width='80px'>日期</th>
					<th width='80px'>完成量</th>
					<th width='80px'>达成率</th>
				</tr>
			</thead>
			<tbody id="cnCompareTbody">
			</tbody>
		</table>
		<div style="float:right;margin-top:20px;margin-right:30px;width: 300px;">
			<button type="button" class="smartBtn" id="B_completeExport" onclick="exportSuccessComplete();">下载结果</button>
			<button type="button" class="smartBtn" id="B_completeConfirm" onclick="completeConfirm();">更新计划</button>
			<button type="button" class="smartBtn" id="B_completeCancel" onclick="completeCancel();">取消</button>
		</div>
	</div>
	<!-- jQuery UI end   -->
	<!-- form for download  -->
	<div style="display: none;">
		<div style="display: none">
			<iframe  name="download_template_form_iframe" style="width: 1px; height: 0px;" ></iframe>
		</div>
		<form id="downloadForm" method="post" target="download_template_form_iframe">
		</form>
	</div>
	
</body>
<script type="text/javascript">
	var gsConfig = {
		'type':'1',
		'zz': '0.5',
		'bz': '0.5'
	};
	//线别选择项
	var selectXbList= "${xbLoadList}";
	var selectXbArray=selectXbList.replace("[", "").replace("]", "").split(",");
	//分页信息
	var pageInfo={
		'searchType1':"",
		'searchValue1':"",
		'searchType2':"",
		'searchValue2':"",
		'searchType3':"",
		'searchValue3':"",
		'searchValue3to':"",
		'searchType4':"",
		'searchValue4':"",
		'sgdd':"1",
		'loadShare':"",
		'sortField':"edatu,to_number(case when zyxj = ' ' or zyxj is null then '999999' else zyxj end )",
		'currentPage':"${pageInfo.currentPage}",
		'pageSize':"${pageInfo.pageSize}",
		'totalCount':"${pageInfo.totalCount}",
		'totalPage':"${pageInfo.totalPages}"
	};
	
	//保存时勾选的id
	var checkIds=[];
	
	var uploadType = "mps";
	
	var showModelFlag = true;
	
	var checkFlag = false;
	//当前登录生管编号
	var userType = "${userType}";
	var sgNum = "${sgNum}";
	
	$(document).ready(function() {
		//设置页面宽度
		var div_body_width = $(window).width()*0.99;
		$(".form_header").css("width", div_body_width - 3);
		$(".div_body").css("width", div_body_width);
		//主计划行点击事件
		$('#nonEditTable tbody tr').click(mainPlanClick);
		$('#nonEditTable tbody tr').dblclick(giveAdvice);
		//默认选择第一行
		$('#nonEditTable tbody tr:first').click();
		//负荷栏点击事件
		$('#B_gather').click(planGather);
		$('#B_seprate').click(planSeprate);
		$("#startDay").focus(function(){
			WdatePicker({dateFmt:'yyyy-MM-dd',onpicked:function(){
				$("#startDay").blur();
			}});
		});
		$("#endDay").focus(function(){
			WdatePicker({dateFmt:'yyyy-MM-dd',onpicked:function(){
				$("#endDay").blur();
			}});
		});
		goToPage(1);
		
		//设置分页信息
		initPageInfo();
		researchLoads();
		//查询条件事件绑定
		bindSearchItems();
		$("#autoSortPlanConfirm").dialog({
	      autoOpen: false,
	      height: 200,
      	  width: 300,
      	  modal: true,
      	  buttons:{}
	    });
		$("#wgLoadsDiv").dialog({
	      autoOpen: false,
	      height: 600,
      	  width: 450
	    });
	});
</script>
</html>