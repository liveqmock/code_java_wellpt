/**
 * 事件绑定
 */
function bindSearchItems() {
	$('#searchType1').change(searchType1Change);
	$('#searchType2').change(searchType2Change);
	$('#report_type').change(exportReport);
	$('#B_Reset').click(resetCondition);
	$('#checkAll').change(checkAll);
	$('#B_Search').click(searchMainPlan);
	$('#loadzxb').change(researchLoads);
	$('#startDay').blur(researchLoads);
	$('#endDay').blur(researchLoads);
	$('#addSubPlanLink').click(addSubPlan);
}

function checkedCount(){
	var checkItems = $("#nonEditTable :checked");
	return checkItems.length;
}
/**
 * 主计划行单击事件
 */
function mainPlanClick(){
	//改变行颜色
	//$('#nonEditTable tbody tr').css("background","#ffffff");
	//$(this).css("background","#B3D9FF");
	$('#nonEditTable tbody tr').removeClass("selectedrow");
	$(this).addClass("selectedrow");
	
	$("#saveTip").text("");
	var count = checkedCount();
	var flag = false;
	if(count == 0){
		flag = true;
	}else if(count==1){
		var checkItem = $("#nonEditTable :checked")[0];
		if(("tr_"+checkItem.id)==this.id){
			flag = true;
		}
	}
	if(flag){
		$('#productOrderNo').val(this.children.item(3).innerText);
		var sno = this.children.item(26).innerText;
		var zlevel = this.children.item(13).innerText;
		if(sno==""){sno = "100";}
		if(zlevel==""){zlevel = "1";}
		$('#sno').val(sno);
		$('#zlevel').val(zlevel);
		$('#desc').val(this.children.item(25).innerText);
		var sg = this.children.item(1).innerText;
		if(sg!=""){
			$('#productionManager').val(sg);
		}
	}else{
		$('#productOrderNo').val("");
		$('#sno').val("100");
		$('#zlevel').val("");
		$('#desc').val("");
		$('#productionManager').val("");
	}
	//行id
	var rowid = this.id;
	var aufnr = rowid.split("_")[1];
	JDS.call({
		service : "productionPlanService.findPlanDetail",
		data : [ aufnr ],
		async : false,
		success : function(result) {
			if(result.data.res=="success"){
				initPlanDetails(result.data.data);
			}else{
				$("#subPlanBody").html("");
			}
		}
	});
	JDS.call({
		service : "productionPlanService.getRemainAmt",
		data : [ aufnr ],
		async : false,
		success : function(result) {
			if(result.data.res=="success"){
				$("#caculator").val(result.data.data);
			}else{
				$("#caculator").val("0");
			}
		}
	});
}
/**
 * 初始化排产明细列表
 * @param data
 */
function initPlanDetails(data){
	var loadZxb = $("#loadzxb").val();
	var start = $('#startDay').val().replace(/\-/g,"");
	var txt=new Array();
	var i=0;
	var item;
	for(var j=0;j<data.length;j++){
		item = data[j];
		if(loadZxb!="" && loadZxb!=item[3]){
			continue;
		}
		if(eval(item[5])<eval(start)){
			continue;
		}
		txt[i++]="<tr class='smartTr' style='width:90px' id='"+item[1]+"'>";
		txt[i++]="<td style='width:90px'>"+item[0]+"</td>";
		txt[i++]="<td style='width:100px'><select style='border:0;padding:0;margin:0;height:100%;width:100%;font-size: 13px;' value='"+item[3]+"'>";
		var isMatched = false;
		for(var k=0;k<selectXbArray.length;k++){
			var xb=selectXbArray[k];
			txt[i++]=" <option value='"+trim(xb)+"'";
			if(trim(xb)===item[3]){
				txt[i++]=" selected";
				isMatched = true;
			}
			txt[i++]=">"+trim(xb)+"</option>";
		}
		if(!isMatched){
			txt[i++]=" <option value='"+item[3]+"' selected>"+item[3]+"</option>";
		}
		txt[i++]="</select></td>";
		txt[i++]="<td style='width:80px'>"+item[4]+"</td>";
		txt[i++]="<td style='width:80px'><input type='text' value='"+item[5]+"' maxlength='20' style='height: 100%;border: 0;' onfocus=\"WdatePicker({dateFmt:'yyyyMMdd'})\" class='Wdate'/></td>";
		txt[i++]="<td style='width:65px'><input type='text' value='"+item[6]+"' maxlength='10' style='width: 100%;height: 100%;border: 0;' onFocus='caculate(this)'/></td>";
		txt[i++]="<td style='width:65px'><input type='text' value='"+item[7]+"' maxlength='10' style='width: 100%;height: 100%;border: 0;' onFocus='caculate(this)'/></td>";
		txt[i++]="<td style='width:65px'><input type='text' value='"+item[8]+"' maxlength='10' style='width: 100%;height: 100%;border: 0;' onFocus='caculate(this)'/></td>";
		txt[i++]="<td style='width:65px''><input type='text' value='"+item[9]+"' maxlength='10' style='width: 100%;height: 100%;border: 0;'/></td>";
		txt[i++]="<td style='text-align: center;width:80px;'><a onclick='saveSubPlan(this)' style='color:#7DAECC;'>保存</a>&nbsp;";
		if(item[9]==0){
			txt[i++]="<a onclick='deleteSubPlan(this)' style='color:#7DAECC;'>删除</a></td>";
		}
		txt[i++]="</tr>";
	}
	$("#subPlanBody").html(txt.join(""));
}

/**
 * 跳转至第N页
 * @param num
 */
function goToPage(num){
	pageInfo.currentPage=num;
	pageInfo.loadShare = $('#loadShare').attr("checked");
	$("#subPlanBody").html("");
	$("#mainTableBody").html("");
	//console.dir(pageInfo);
	JDS.call({
		service : "productionPlanService.searchPlanPage",
		data : [ pageInfo.searchType1,pageInfo.searchValue1,pageInfo.searchType2,pageInfo.searchValue2,pageInfo.searchType3,
		         pageInfo.searchValue3,pageInfo.searchValue3to,pageInfo.searchType4,pageInfo.searchValue4,pageInfo.loadShare,
		         sgNum,pageInfo.currentPage,pageInfo.pageSize,pageInfo.sortField,'0'],
		async : false,
		success : function(result) {
			if(result.data.res=="success"){
				var bodyHtml = new Array();
				var i=0;
				var plan;
				for(var j=0;j<result.data.data.length;j++){
					plan = result.data.data[j];
					var state = "";
					var color = "";
					if(plan[25]=="1"){
						color = "errorPlan";
						if(plan[24]=="1"||plan[24]=="2"){
							state = "完工";
							color = "finishPlan";
						}else if(plan[21]==" "||plan[21]=="0"){
							state = "未安排";
							color = "commonPlan";
						}else if(plan[21]=="1"){
							state = "未排满";
							color = "commonPlan";
						}else if(plan[21]=="2"){
							state = "已排满、欠料";
						}else if(plan[21]=="3"){
							state = "已排满、欠料";
						}else if(plan[21]=="5"){
							state = "已排满、欠料";
						}
					}else{
						if(plan[24]=="1"||plan[24]=="2"){
							state = "完工";
							color = "finishPlan";
						}else if(plan[21]==" "||plan[21]=="0"){
							state = "未安排";
							color = "commonPlan";
						}else if(plan[21]=="1"){
							state = "未排满";
							color = "commonPlan";
						}else if(plan[21]=="2"){
							state = "已排满、正常";
							color = "asignPlan";
						}else if(plan[21]=="3"){
							state = "已排满、超计划量";
							color = "errorPlan";
						}else if(plan[21]=="5"){
							state = "已排满、超交期";
							color = "errorPlan";
						}
					}
					bodyHtml[i++]="<tr id='main_"+plan[0]+"' class='smartTr "+color+"'>";
					bodyHtml[i++]="<td width='20px'><input type='checkBox' id='"+plan[0]+"'/></td>";
					bodyHtml[i++]="<td width='40px'>"+plan[18]+"</td>";
					bodyHtml[i++]="<td width='60px'>"+plan[3]+"</td>";
					bodyHtml[i++]="<td width='90px'>"+plan[0]+"</td>";
					bodyHtml[i++]="<td width='40px'>"+plan[2]+"</td>";
					bodyHtml[i++]="<td width='130px'>"+plan[19]+"</td>";
					bodyHtml[i++]="<td width='90px'>"+(plan[4]==null?"":plan[4])+"</td>";
					bodyHtml[i++]="<td width='30px'>"+plan[5]+"</td>";
					bodyHtml[i++]="<td width='60px'>"+plan[6]+"</td>";
					bodyHtml[i++]="<td width='60px'>"+plan[15]+"</td>";
					bodyHtml[i++]="<td width='60px'>"+plan[27]+"</td>";
					bodyHtml[i++]="<td width='80px'>"+plan[7]+"</td>";
					bodyHtml[i++]="<td width='200px'>"+plan[8]+"</td>";
					bodyHtml[i++]="<td width='40px'>"+plan[31]+"</td>";
					bodyHtml[i++]="<td width='60px'>"+plan[9]+"</td>";
					bodyHtml[i++]="<td width='60px'>"+plan[10]+"</td>";
					bodyHtml[i++]="<td width='60px'>"+plan[11]+"</td>";
					bodyHtml[i++]="<td width='60px'>"+plan[12]+"</td>";
					bodyHtml[i++]="<td width='60px'>"+plan[13]+"</td>";
					bodyHtml[i++]="<td width='100px'>"+plan[28]+"</td>";
					bodyHtml[i++]="<td width='100px'>"+plan[29]+"</td>";
					bodyHtml[i++]="<td width='100px'>"+plan[30]+"</td>";
					bodyHtml[i++]="<td width='100px'>"+plan[20]+"</td>";
					bodyHtml[i++]="<td width='120px'>"+state+"</td>";
					bodyHtml[i++]="<td width='130px'>"+plan[26]+"</td>";
					bodyHtml[i++]="<td width='130px'>"+plan[22]+"</td>";
					bodyHtml[i++]="<td width='40px'>"+plan[23]+"</td>";
					bodyHtml[i++]="<td width='60px'>"+plan[32]+"</td>";
					bodyHtml[i++]="</tr>";
				}
				$("#mainTableBody").html(bodyHtml.join(""));
				$('#nonEditTable tbody tr').click(mainPlanClick);
				$('#nonEditTable tbody tr').dblclick(giveAdvice);
				$('#nonEditTable tbody tr:first').click();
				
				$("#mainTableBody td").mouseover(showTip);
				$("#mainTableBody td").mouseout(hideTip);
				
				pageInfo.currentPage= result.data.pageInfo.currentPage;
				pageInfo.pageSize= result.data.pageInfo.pageSize;
				pageInfo.totalCount= result.data.pageInfo.totalCount;
				pageInfo.totalPage= result.data.pageInfo.totalPages;
				initPageInfo();
			}
		}
	});
}


function showTip(){
	var e = window.event;
	var tipDiv=document.getElementById("divTip");
	tipDiv.innerText=this.innerText;
	tipDiv.style.display="block";
	tipDiv.style.position="absolute";
	var liEvtCX = e.clientX; 
	var liEvtCY = e.clientY;
	var liBodyCW = document.body.clientWidth;
	var liBodyCH = document.body.clientHeight;
	var liBodyST = document.body.scrollTop;
	var liBodySL = document.body.scrollLeft;
	var liWidth = tipDiv.offsetWidth, liHeight = tipDiv.offsetHeight;
	var liT = liEvtCY <= (liBodyCH / 2) ? (liEvtCY + liBodyST + 25) : (liEvtCY
			+ liBodyST - liHeight - 5);
	var liL = liEvtCX <= (liBodyCW / 2) ? (liEvtCX + liBodySL) : (liEvtCX
			+ liBodySL - liWidth);
	if (liT < 0) {
		liT = 0;
	}
	if (liL < 0) {
		liL = 0;
	}
	tipDiv.style.left= liL + "px";
	tipDiv.style.top= liT + "px";
}

function hideTip(){
	$("#divTip").hide();
}

/**
 * 初始化分页信息
 */
function initPageInfo(){
	$("#paginationInfo").text("第"+(pageInfo.currentPage)+"页,共"+pageInfo.totalPage+"页,每页"+pageInfo.pageSize+"条,共"+pageInfo.totalCount+"条 ");
	if(pageInfo.currentPage==1||pageInfo.totalPage==1){
		$("#B_First").attr("disabled",true);
		$("#B_Previous").attr("disabled",true);
	}else{
		$("#B_First").attr("disabled",false);
		$("#B_Previous").attr("disabled",false);
	}
	if(pageInfo.totalPage==1||pageInfo.currentPage==pageInfo.totalPage){
		$("#B_Next").attr("disabled",true);
		$("#B_Last").attr("disabled",true);
	}else{
		$("#B_Next").attr("disabled",false);
		$("#B_Last").attr("disabled",false);
	}
}

/**
 * 翻页
 * @param num
 */
function addPage(num) {
    var targetPage = 1;
    num = parseInt(num);
    if (num != 0) {
        var targetPage = parseInt(pageInfo.currentPage) + num;
        if (targetPage < 0) {
            targetPage = 0;
        } else if (pageInfo.totalPage > 0 && pageInfo.totalPage < targetPage) {
            targetPage = pageInfo.totalPage;
        }
    }
    this.goToPage(targetPage);
}


/**
 * 查询类型1change事件
 */
function searchType1Change() {
	var width = $('#searchValue1').outerWidth();
	var parentType = "";
	var parentValue = "";
	var childType = $('#searchType1').val();
	JDS.call({
		service : "productionPlanService.getSelectItemByParent",
		data : [ parentType,parentValue,childType ],
		async : false,
		success : function(result) {
			if(result.data.res=="success"){
				$('#searchValue1').html(result.data.data);
				$('#searchValue1').css("width",width);
			}
		}
	});
}

/**
 * 查询类型2change事件
 */
function searchType2Change(){
	var width = $('#searchValue2').outerWidth();
	var parentType = $('#searchType1').val();
	var parentValue = "";
	var childType = $('#searchType2').val();
	JDS.call({
		service : "productionPlanService.getSelectItemByParent",
		data : [ parentType,parentValue,childType ],
		async : false,
		success : function(result) {
			if(result.data.res=="success"){
				$('#searchValue2').html(result.data.data);
				$('#searchValue2').css("width",width);
			}
		}
	});
}

/**
 * 重载负荷列表
 */
function researchLoads(flag) {
	var zxb = $('#loadzxb').val();
	var gxb = $('#loadGxb').val();
	var start = $('#startDay').val();
	var end = $('#endDay').val();
	var loadShare = $('#loadShare').attr("checked");
	JDS.call({
		service : "productionPlanService.searchLoads",
		data : [ start,end,gxb,loadShare,zxb,userType,sgNum ],
		async : false,
		success : function(result) {
			$("#scfhTbody").html("");
			var html= new Array();
			var i = 0;
			if(result.data.res=="success"){
				var data;
				for(var j=0;j<result.data.data.length;j++){
					data = result.data.data[j];
					html[i++]="<tr class='smartTr'";
					if(eval(data[6])<100||eval(data[6])>110){
						html[i++]=" style='color:red;'";
					}
					html[i++]=">";
					html[i++]="<td width='100px' title='双击查询当前线别相关订单' ondblclick='findBoundData(this,1)'>"+data[0]+"</td>";
					html[i++]="<td width='80px'>"+data[2]+"</td>";
					html[i++]="<td width='180px' title='双击查询当前日期相关订单' ondblclick='findBoundData(this,2)'>"+data[1]+"</td>";
					html[i++]="<td width='50px' style='text-align: right;'>"+data[3]+"</td>";
					html[i++]="<td width='50px' style='text-align: right;'>"+data[4]+"</td>";
					html[i++]="<td width='50px' style='text-align: right;'>"+data[5]+"</td>";
					html[i++]="<td width='100px' title='双击自动调整当前线别排产计划,此操作将删除原排产记录。排序优先级：生产日期&gt;订单优先级&gt;完工情况&gt;SD交期&gt;生产单号' style='text-align: right;' ondblclick=\"autoSortPlan('"+data[0]+"','"+data[1]+"')\">"+data[6]+"%</td>";
					html[i++]="</tr>";
				}
			}
			$("#scfhTbody").append(html.join(""));
		}
	});
	//重载计划列表
	if(flag==undefined||flag!="S"){
		$("#nonEditTable tbody .selectedrow").click();
	}
}

/**
 * 查询
 */
function searchMainPlan() {
	pageInfo.searchType1 = $('#searchType1').val();
	pageInfo.searchValue1 = $('#searchValue1').val();
	pageInfo.searchType2 = $('#searchType2').val();
	pageInfo.searchValue2 = $('#searchValue2').val();
	pageInfo.searchType3 = $('#searchType3').val();
	pageInfo.searchValue3 = $('#searchValue3').val();
	pageInfo.searchValue3to = $('#searchValue3to').val();
	pageInfo.searchType4 = $('#searchType4').val();
	pageInfo.searchValue4 = $('#searchValue4').val();
	goToPage(1);
}

/**
 * 重置查询条件
 */
function resetCondition(){
	$('#searchValue1').val("");
	$('#searchValue2').val("");
	$('#searchValue3').val("");
	$('#searchValue3to').val("");
	$('#searchValue4').val("");
	pageInfo.searchValue1 = "";
	pageInfo.searchValue2 = "";
	pageInfo.searchValue3 = "";
	pageInfo.searchValue3to = "";
	pageInfo.searchValue4 = "";
	goToPage(1);
}

/**
 * 排产计算器，点击排产量时扣除当前值
 * @param object
 */
function caculate(object){
	$("#caculator").val($("#caculator").val()-object.value);
}

/**
 * 排产计算器，双击时置为0
 * @param object
 */
function clearCaculator(object){
	$("#caculator").val("0");
}

/**
 * 生成排产建议
 */
function giveAdvice(){
	var aufnr = this.id.split("_")[1];
	//双击订单信息时先重置排产计数器
	JDS.call({
		service : "productionPlanService.getRemainAmt",
		data : [ aufnr ],
		async : false,
		success : function(result) {
			if(result.data.res=="success"){
				$("#caculator").val(result.data.data);
				advice(aufnr,false);
			}else{
				//console.log(result.data.res);
				$("#caculator").val("0");
			}
		}
	});
}

/**
 * 全选
 */
function checkAll(){
	if($('#checkAll').attr("checked")=="checked"){
		$('#mainTableBody :checkBox').attr("checked",true);
	}else{
		$('#mainTableBody :checkBox').removeAttr("checked");
	}
}

/**
 * 修改主计划
 */
function saveMainPlan(state){
	var count = checkedCount();
	var saveData={
		'aufnr':$('#productOrderNo').val(),
		'zyxj':$('#sno').val(),
		'zqt':$('#desc').val(),
		'zsg':$('#productionManager').val(),
		'zlevel':$('#zlevel').val(),
		'ids':''
	};
	if(saveData.zyxj!="" && !saveData.zyxj.match(/^\d+$/)){
		alert("优先级只能录入数字!");
		return;
	}
	if(saveData.zlevel!="" && !saveData.zlevel.match(/^[0-9]+(.[0-9]{1,3})?$/)){
		alert("难易度只能录入数值!");
		return;
	}
	if(count==0){
		//1未勾选，保存当前输入记录
		saveData.ids = "";
	}else{
		//2勾选多条记录
		saveData.aufnr = "";
		var ids = "";
		$("#mainTableBody :checked").each(function(){
			ids = ids + this.id +",";
		});
		saveData.ids = ids;
	}
	if(state=='4'){
		//点击完工
		oConfirm("确认对所选订单进行完工操作?", function() {
			$.get("finishPlan", saveData).done(function(){
				goToPage(1);
			});
		});
	}else{
		//点击保存
		$.ajax({
			url: "savePlanDetail",
			type: "post",
			data: saveData,
			contentType: "application/x-www-form-urlencoded; charset=utf-8"
		}).done(function(data){
			//alert("保存成功");
			if(data!=""){
				alert("订单："+data+"已经安排任务，不能重新分配生管!");
			}else{
				alert("保存成功");
			}
			goToPage(pageInfo.currentPage);
		});
	}
}

/**
 * 生成排产建议
 * @param aufnr 生产订单号
 * @param flag 
 */
function advice(aufnr,flag){
	var leftAmt = $('#caculator').val();
	if(!flag){
		leftAmt = "";
	}
	var zxb = $('#loadzxb').val();
	var gxb = $('#loadGxb').val();
	var start = $('#startDay').val();
	var end = $('#endDay').val();
	$("#detailPlanEdit tbody tr").each(function(){
		if(this.id<10000){
			var parent = this.parentNode;
			parent.removeChild(this);
		}
	});
	JDS.call({
		service : "productionPlanService.generateAdvice",
		data : [ aufnr,start,end,gxb,zxb,leftAmt,$('loadShare').checked,gsConfig.type,gsConfig.zz,gsConfig.bz ],
		async : false,
		success : function(result) {
			if(result.data.res=="success"){
				var txt=new Array();
				var i=0;
				var data;
				for(var j=0;j<result.data.data.length;j++){
					data = result.data.data[j];
					txt[i++]="<tr class='smartTr' style='width:90px' id='"+data.zscrwd+"'>";
					txt[i++]="<td style='width:90px'>"+data.aufnr+"</td>";
					txt[i++]="<td style='width:100px'><select style='border:0;padding:0;margin:0;height:100%;width:100%;font-size: 13px;' value='"+data.zxh+"'>";
					var isMatched = false;
					var xb;
					for(var k=0;k<selectXbArray.length;k++){
						xb = selectXbArray[k];
						txt[i++]=" <option value='"+trim(xb)+"'";
						if(trim(xb)===data.zxh){
							txt[i++]=" selected";
							isMatched = true;
						}
						txt[i++]=">"+trim(xb)+"</option>";
					}
					if(!isMatched){
						txt[i++]=" <option value='"+data.zxh+"' selected>"+data.zxh+"</option>";
					}
					txt[i++]="</select></td>";
					txt[i++]="<td style='width:80px'>"+data.ltxa1+"</td>";
					txt[i++]="<td style='width:80px'><input type='text' value='"+data.gstrp+"' maxlength='20' onfocus=\"WdatePicker({dateFmt:'yyyyMMdd'})\" style='height: 100%;border: 0;'/></td>";
					txt[i++]="<td style='width:65px'><input type='text' value='"+data.gamng01+"' maxlength='10' style='width: 100%;height: 100%;border: 0;' onFocus='caculate(this)'/></td>";
					txt[i++]="<td style='width:65px'><input type='text' value='"+data.gamng02+"' maxlength='10' style='width: 100%;height: 100%;border: 0;' onFocus='caculate(this)'/></td>";
					txt[i++]="<td style='width:65px'><input type='text' value='"+data.gamng03+"' maxlength='10' style='width: 100%;height: 100%;border: 0;' onFocus='caculate(this)'/></td>";
					txt[i++]="<td style='width:65px''><span style='color:red'>(建议)</span></td>";
					txt[i++]="<td style='text-align: center;width:80px;'><a onclick='saveSubPlan(this)' style='color:#7DAECC;'>保存</a>&nbsp;";
					txt[i++]="<a onclick='deleteSubPlan(this)' style='color:#7DAECC;'>删除</a></td>";
					txt[i++]="</tr>";
				}
				$("#detailPlanEdit tbody").append(txt.join(""));
			}else{
				oAlert(result.data.msg);
			}
		}
	});
}

/**
 * 保存排产记录
 * @param current
 */
function saveSubPlan(current){
	var tr = current.parentNode.parentNode;
	var model = {
		'zscrwd' : tr.id,
		'aufnr' : tr.children.item(0).innerText,
		'ltxa1' : tr.children.item(2).innerText,
		'zxh' : tr.children.item(1).children.item(0).value,
		'gstrp' : tr.children.item(3).children.item(0).value,
		'gamng01' : tr.children.item(4).children.item(0).value,
		'gamng02' : tr.children.item(5).children.item(0).value,
		'gamng03' : tr.children.item(6).children.item(0).value,
		'type' : gsConfig.type,
		'zz' : gsConfig.zz,
		'bz' : gsConfig.bz
	};
//	console.dir(modelSubPlan);
	if(model.zxh==null||model.zxh==""){
		$("#saveTip").text("请选择线别!");
		return;
	}
	$("#saveTip").text("保存中...");
	JDS.call({
		service : "productionPlanService.saveSubPlan",
		data : [ model.zscrwd,model.aufnr,model.zxh,model.ltxa1,model.gstrp,model.gamng01,model.gamng02,model.gamng03,model.type,model.zz,model.bz ],
		async : false,
		success : function(result) {
			//console.dir(result);
			if(result.data.res=="success"){
				$("#saveTip").text("保存成功");
				tr.id=result.data.zscrwd;
				tr.children.item(2).innerText = result.data.ltxa1;
				tr.children.item(4).children.item(0).value = result.data.a;
				tr.children.item(5).children.item(0).value = result.data.b;
				tr.children.item(6).children.item(0).value = result.data.c;
				if(tr.children.item(7).innerText=="(建议)"){
					tr.children.item(7).innerText = "0";
				}
				var mainTr = $("#main_"+model.aufnr);
				mainTr[0].children.item(5).innerText=result.data.zxhscrq;
				mainTr[0].children.item(19).innerText=result.data.zwcl;
				mainTr[0].children.item(20).innerText=result.data.zwwcl;
				mainTr[0].children.item(21).innerText=result.data.zdqpcl;
				mainTr[0].children.item(22).innerText=result.data.ypcl;
				researchLoads("S");
			}else{
				oAlert(result.data.msg);
			}
		}
	});
}


/**
 * 删除排产记录
 * @param current
 */
function deleteSubPlan(current){
	var tr = current.parentNode.parentNode;
	var id = parseInt(tr.id);
	if(id<1000){
		var parent = tr.parentNode;
		parent.removeChild(tr);
	}else{
		oConfirm("确定删除当前排产信息?", function() {
			var model = {
				'zscrwd' : tr.id,
				'aufnr' : tr.children.item(0).innerText,
				'type' : gsConfig.type,
				'zz' : gsConfig.zz,
				'bz' : gsConfig.bz
			};
			$("#saveTip").text("删除中...");
			JDS.call({
				service : "productionPlanService.deleteSubPlan",
				data : [ model.zscrwd,model.aufnr,model.type,model.zz,model.bz ],
				async : false,
				success : function(result) {
					if(result.data.res=="success"){
						$("#saveTip").text("删除成功");
						var mainTr = $("#main_"+model.aufnr);
						mainTr[0].children.item(5).innerText=result.data.zxhscrq;
						mainTr[0].children.item(19).innerText=result.data.zwcl;
						mainTr[0].children.item(20).innerText=result.data.zwwcl;
						mainTr[0].children.item(21).innerText=result.data.zdqpcl;
						mainTr[0].children.item(22).innerText=result.data.ypcl;
						var parent = tr.parentNode;
						parent.removeChild(tr);
						researchLoads();
					}else{
						oAlert(result.data.msg);
					}
				}
			});
		});
	}
}

/**
 * 汇总
 * @returns {Boolean}
 */
function planGather(){
	//对当前线别，在起始日期之后的排产记录进行汇总
	var xb = $('#loadzxb').val();
	var rq = $('#startDay').val();
	if(xb==""){
		alert("请选择线别!");
		return false;
	}
	if(rq==""){
		alert("请选择开始日期!");
		return false;
	}
	var message = "确认对"+xb+" 从"+rq+"之后的排产进行汇总?";
	oConfirm(message, function() {
		JDS.call({
			service : "productionPlanService.planGather",
			data : [ xb,rq ],
			async : false,
			success : function(result) {
				if(result.data.res=="success"){
					researchLoads();
				}else{
					oAlert(result.data.msg);
				}
			}
		});
	});
}

/**
 * 整理
 * @returns {Boolean}
 */
function planSeprate(){
	//对当前线别，在起始日期之后的排产记录进行汇总
	var xb = $('#loadzxb').val();
	var rq = $('#startDay').val();
	if(xb==""){
		alert("请选择线别!");
		return false;
	}
	if(rq==""){
		alert("请选择开始日期!");
		return false;
	}
	var message = "确认对"+xb+" 从"+rq+"之后的排产进行整理?";
	oConfirm(message, function() {
		JDS.call({
			service : "productionPlanService.planSeprate",
			data : [ xb,rq,gsConfig.type,gsConfig.zz,gsConfig.bz ],
			async : false,
			success : function(result) {
				if(result.data.res=="success"){
					researchLoads();
				}else{
					oAlert(result.data.msg);
				}
			}
		});
	});
}

/**
 * 双击负荷栏查找相关数据
 * @param data
 * @param type
 */
function findBoundData(data,type){
	var zxh = "";
	var zrq = "";
	if(type==1){
		zxh = data.innerText;
		zrq = data.parentNode.children.item(2).innerText;
		pageInfo.searchType3 = "scrq";
		pageInfo.searchValue3 = zrq;
		pageInfo.searchValue3to = "";
	}
	if(type==2){
		zrq = data.innerText;
		zxh = data.parentNode.children.item(0).innerText;
		pageInfo.searchType3 = "scrq";
		pageInfo.searchValue3 = zrq;
		pageInfo.searchValue3to = zrq;
	}
	pageInfo.searchValue1 = "";
	pageInfo.searchType2 = "zxh";
	pageInfo.searchValue2 = zxh;
	pageInfo.searchValue4 = "";
	goToPage(1);
}

function trim(str){
	return str.replace(/(^\s*)|(\s*$)/g, "");
}

/**
 * 打开线别整理dialog
 * @param zxh
 * @param zrq
 */
function autoSortPlan(zxh,zrq){
	$('#autoSortStart').val(zrq);
	$('#autoSortZxb').val(zxh);
	JDS.call({
		service : "productionPlanService.getEndDateInit",
		data : [ zrq,'15' ],
		async : false,
		success : function(result) {
			if(result.data.res=="success"){
				researchLoads();
				$('#autoSortEnd').val(result.data.data);
			}else{
				oAlert(result.data.msg);
			}
		}
	});
	$("#autoSortEndLable").css("color","black");
	$("#autoSortPlanConfirm" ).dialog( "open");
}

/**
 * 对当前选定线别进行自动整理
 */
function autoConfirm(){
	var start =  $('#autoSortStart').val();
	var end =  $('#autoSortEnd').val();
	var zxh =  $('#autoSortZxb').val();
	if(null==end || end==""){
		$("#autoSortEndLable").css("color","red");
		return;
	}
	$( "#autoSortPlanConfirm" ).dialog( "close" );
	JDS.call({
		service : "productionPlanService.autoArrangeXbPlan",
		data : [ zxh,start,end,gsConfig.type,gsConfig.zz,gsConfig.bz ],
		async : false,
		success : function(result) {
			if(result.data.res=="success"){
				researchLoads();
				goToPage(pageInfo.currentPage);
			}else{
				oAlert(result.data.msg);
			}
		}
	});
}

/**
 * 根据排产计数器中的值进行自动排产
 */
function autoArrange(){
	var aufnr = $("#nonEditTable tbody .selectedrow").attr("id").split("_")[1];
	var leftAmt = $('#caculator').val();
	if(!leftAmt.match(/^\d+$/)){
		oAlert("产能计算只能录入整数!");
		return false;
	}
	advice(aufnr,true);
}

function testFind(aufnr){
	JDS.call({
		service : "productionPlanService.findTest",
		data : [ aufnr ],
		async : false,
		success : function(result) {
			if(result.data.res=="success"){
				researchLoads();
				goToPage(pageInfo.currentPage);
			}else{
				oAlert(result.data.msg);
			}
		}
	});
}

/**
 * 取消整理操作,关闭dialog
 */
function autoCancel(){
	$( "#autoSortPlanConfirm" ).dialog( "close" );
}

/**
 * 上传完工模板
 */
function wgUpload(){
	$("#wgUploadDialog").dialog({
		autoOpen : true,
		height : 300,
		width : 500,
		modal : true,
		buttons : {
			"确定" : function() {
				var file = $("#uploadWgFile").val();
				if (file == '') {
					oAlert("请选择Excel文件");
					return;
				}
				if (file.indexOf(".") < 0) {
					return;
				}
				var fileType = file.substring(file.lastIndexOf("."), file.length);
				if (fileType == ".xls" || fileType == ".xlsx") {
					$.ajaxFileUpload({
						url : ctx + "/productionPlan/productPlan/uploadWg",// 链接到服务器的地址
						secureuri : false,
						fileElementId : 'uploadWgFile',// 文件选择框的ID属性
						dataType : 'text', // 服务器返回的数据格式
						success : function(data, status) {
							//console.dir(data);
							if (data == "success") {
								$("#wgUploadDialog").dialog("close");
								showPlanCompareDiv();
							} else {
								oAlert(data);
							}
						},
						error : function(data, status, e) {
							oAlert("导入失败");
						}
					});
				} else {
					oAlert("请选择Excel文件");
					return;
				}
			},
			"取消" : function() {
				$(this).dialog("close");
			}
		}
	});
}

/**
 * 上传完工模板
 */
function mpsUpload(){
	$("#mpsUploadDialog").dialog({
		autoOpen : true,
		height : 300,
		width : 500,
		modal : true,
		buttons : {
			"确定" : function() {
				var file = $("#uploadMpsFile").val();
				if (file == '') {
					oAlert("请选择Excel文件");
					return;
				}
				if (file.indexOf(".") < 0) {
					return;
				}
				var fileType = file.substring(file.lastIndexOf("."), file.length);
				if (fileType == ".xls" || fileType == ".xlsx") {
					$.ajaxFileUpload({
						url : ctx + "/productionPlan/productPlan/uploadMps",// 链接到服务器的地址
						secureuri : false,
						fileElementId : 'uploadMpsFile',// 文件选择框的ID属性
						dataType : 'text', // 服务器返回的数据格式
						success : function(data, status) {
							if (data == "success") {
								oAlert("导入成功!");
								$("#mpsUploadDialog").dialog("close");
							} else {
								oAlert(data);
							}
						},
						error : function(data, status, e) {
							oAlert("导入失败");
						}
					});
				} else {
					oAlert("请选择Excel文件");
					return;
				}
			},
			"取消" : function() {
				$(this).dialog("close");
			}
		}
	});
}

/**
 * 下载完工模板
 */
function downloadWgTemplate(){
	var scrq = $('#wgrq').val();
	if(scrq==null || scrq ==""){
		oAlert("请选择报工日期!");
		return ;
	}
	$('#downloadForm').attr("action",ctx + "/productionPlan/productPlan/downloadWg?scrq="+scrq);
	$('#downloadForm').submit();
}

/**
 * 下载MES完工模板
 */
function downloadMesTemplate(){
	var scrq = $('#wgrq').val();
	if(scrq==null || scrq ==""){
		oAlert("请选择报工日期!");
		return ;
	}
	$('#downloadForm').attr("action",ctx + "/productionPlan/productPlan/downloadMes?scrq="+scrq);
	$('#downloadForm').submit();
}

/**
 * 下载MPS模板
 */
function downloadMpsTemplate(){
	$('#downloadForm').attr("action",ctx + "/productionPlan/productPlan/downloadMps");
	$('#downloadForm').submit();
}

/**
 * 完工数量上传成功后弹出实际产量与计划产量对比窗口
 */
function showPlanCompareDiv() {
	$.getJSON("generatePlanCompare",{
		'type' : gsConfig.type,
		'zz' : gsConfig.zz,
		'bz' : gsConfig.bz
	}).done(function(ret) {
		if(null!=ret){
			$("#planCompareTable").hide();
			$("#B_completeConfirm").show();
			$("#cnCompareTbody").html("");
			var html=new Array();
			var j = 0;
			var loads = ret[0].loads;
			if(null!=loads && loads.length>0){
				for(var i=0;i<loads.length;i++){
					html[j++]="<tr style='color:"+loads[i][0]+";'>";
					html[j++]="<td width='100px'>"+loads[i][1]+"</td>";
					html[j++]="<td width='80px'>"+loads[i][2]+"</td>";
					html[j++]="<td width='80px' style='text-align: right;'>"+loads[i][3]+"</td>";
					html[j++]="<td width='80px' style='text-align: right;'>"+loads[i][4]+"</td>";
					html[j++]="</tr>";
				}
			}
			$("#cnCompareTbody").append(html.join(""));
		}
		$("#B_completeExport").hide();
		$("#wgLoadsDiv").dialog("open");
	});
}

/**
 * 上传完成量后确认操作
 */
function completeConfirm() {
	oConfirm("此操作将删除原有的排产数据，请确认!", function() {
		$.get("completeConfirm", {
			'type' : gsConfig.type,
			'zz' : gsConfig.zz,
			'bz' : gsConfig.bz
		}).done(function(ret) {
			alert(ret);
			goToPage(1);
			researchLoads();
			$("#planCompareTable").hide();
			$("#B_completeConfirm").hide();
			$("#B_completeExport").show();
		});
	});
}

/**
 * 上传完成后导出更新明细
 */
function exportSuccessComplete(){
	$('#downloadForm').attr("action",ctx + "/productionPlan/productPlan/exportSuccessComplete");
	$('#downloadForm').submit();
}

/**
 * 上传完成量后取消操作
 */
function completeCancel(){
	$("#planCompareTbody").html("");
	$("#cnCompareTbody").html("");
	$("#wgLoadsDiv").dialog("close");
}
/**
 * 手工刷新操作
 */
function manualRefresh(type){
	var info;
	if(type==1){
		info="确认进行排产欠料刷新操作?";
	}else if(type==2){
		info="确认进行MPS欠料刷新操作?";
	}else if(type==3){
		info="确认进行追加工单刷新操作?";
	}
	oConfirm(info, function() {
		JDS.call({
			service : "productionPlanService.manualRefresh",
			data : [ type ],
			async : false,
			success : function(result) {
				if(result.data.res=="success"){
					oAlert("操作成功!");
					researchLoads();
					goToPage(pageInfo.currentPage);
				}else{
					oAlert(result.data.msg);
				}
			}
		});
	});
}

function publishAllPlan(){
	oConfirm("确认将当前所有生产安排发布为最新版本?", function() {
		JDS.call({
			service : "productionPlanService.publishAllPlan",
			data : [sgNum],
			async : false,
			success : function(result) {
				if(result.data.res=="success"){
					JDS.call({
						service : "productionPlanService.callMesProcedureAfterPublish",
						data : [],
						async : false,
						success : function() {
							oAlert("操作成功!");
							researchLoads();
							goToPage(pageInfo.currentPage);
						}
					});
				}else{
					oAlert(result.data.msg);
				}
			}
		});
	});
}

/**
 * 导出日计划报表
 */
function exportReport(){
	var type = $('#report_type').val();
	if(type!=""){
		var searchType1 = $('#searchType1').val();
		var searchValue1 = $('#searchValue1').val();
		var searchType2 = $('#searchType2').val();
		var searchValue2 = $('#searchValue2').val();
		var searchType3 = $('#searchType3').val();
		var searchValue3 = $('#searchValue3').val();
		var searchValue3to = $('#searchValue3to').val();
		var searchType4 = $('#searchType4').val();
		var searchValue4 = $('#searchValue4').val();
		var action = ctx + "/productionPlan/productPlan/exportExcelAll?reportType=" + type + "&searchType1="
				+ searchType1 + "&searchValue1=" + searchValue1 + "&searchType2=" + searchType2
				+ "&searchValue2=" + searchValue2 + "&searchType3=" + searchType3 + "&searchValue3="
				+ searchValue3 + "&searchValue3to=" + searchValue3to + "&searchType4=" + searchType4
				+ "&searchValue4=" + searchValue4;
		$('#downloadForm').attr("action",action);
		$('#downloadForm').submit();
		$('#report_type').val("");
	}
}

/**
 * 排序
 * @param id
 */
function sortByPage(id){
	pageInfo.searchType1 = $('#searchType1').val();
	pageInfo.searchValue1 = $('#searchValue1').val();
	pageInfo.searchType2 = $('#searchType2').val();
	pageInfo.searchValue2 = $('#searchValue2').val();
	pageInfo.searchType3 = $('#searchType3').val();
	pageInfo.searchValue3 = $('#searchValue3').val();
	pageInfo.searchValue3to = $('#searchValue3to').val();
	pageInfo.searchType4 = $('#searchType4').val();
	pageInfo.searchValue4 = $('#searchValue4').val();
	pageInfo.sortField = id+",to_number(case when zyxj = ' ' or zyxj is null then '999999' else zyxj end )";
	goToPage(1);
}

/**
 * 手工添加排产记录
 */
function addSubPlan(){
	//return;
	var startDate = $("#startDay").val().replace(/\-/g,"");
	var detailbody = $("#subPlanBody");
	var node;
	if(detailbody[0].children.length>0){
		for(var i=0;i<detailbody[0].children.length;i++){
			node = detailbody[0].children.item(i);
			if(node.id=='0'){
				alert("有尚未保存的排产信息!");
				return;
			}
		}
	}
	var aufnr = $("#nonEditTable tbody .selectedrow").attr("id").split("_")[1];
	var zxh = $("#loadzxb").val();
	var txt=new Array();
	var i=0;
	var item;
	txt[i++]="<tr class='smartTr' style='width:90px' id='"+0+"'>";
	txt[i++]="<td style='width:90px'>"+aufnr+"</td>";
	txt[i++]="<td style='width:100px'><select style='border:0;padding:0;margin:0;height:100%;width:100%;font-size: 13px;' value='"+zxh+"'>";
	var isMatched = false;
	for(var k=0;k<selectXbArray.length;k++){
		var xb=selectXbArray[k];
		txt[i++]=" <option value='"+trim(xb)+"'";
		if(trim(xb)===zxh){
			txt[i++]=" selected";
			isMatched = true;
		}
		txt[i++]=">"+trim(xb)+"</option>";
	}
	txt[i++]="</select></td>";
	txt[i++]="<td style='width:80px'>总装+包装</td>";
	txt[i++]="<td style='width:80px'><input type='text' value='"+startDate+"' maxlength='20' style='height: 100%;border: 0;' onfocus=\"WdatePicker({dateFmt:'yyyyMMdd'})\" class='Wdate'/></td>";
	txt[i++]="<td style='width:65px'><input type='text' value='0' maxlength='10' style='width: 100%;height: 100%;border: 0;' onFocus='caculate(this)'/></td>";
	txt[i++]="<td style='width:65px'><input type='text' value='0' maxlength='10' style='width: 100%;height: 100%;border: 0;' onFocus='caculate(this)'/></td>";
	txt[i++]="<td style='width:65px'><input type='text' value='0' maxlength='10' style='width: 100%;height: 100%;border: 0;' onFocus='caculate(this)'/></td>";
	txt[i++]="<td style='width:65px''>0</td>";
	txt[i++]="<td style='text-align: center;width:80px;'><a onclick='saveSubPlan(this)' style='color:#7DAECC;'>保存</a>&nbsp;";
	txt[i++]="<a onclick='deleteSubPlan(this)' style='color:#7DAECC;'>删除</a></td>";
	txt[i++]="</tr>";
	detailbody.html(detailbody[0].innerHTML+txt.join(""));
}