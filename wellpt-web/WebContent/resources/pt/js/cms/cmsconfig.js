window.wellwin = {};//减少命名空间污染
window.wellwin.showIfgoWidgets = true;
window.wellwin.pageId = location.search.split("=")[1];
//预览
$("#view").click(function() {
	window.open(ctx+"/cms/cmspage/readPage?uuid="+window.wellwin.pageId);
});
//打开尺寸设置弹出框
$("#savesize").click(function() {
	$("#dialog2").dialog("open");
});
//处理左侧widget区域的隐藏和显示
window.show = true;
$("#showHide").click(function() {
	if (window.show) {
		$("#widgetWrap").animate({
			width : "30px"
		}, function() {
			$("#ifgoWidgets").hide();
		});
		window.show = false;
	} else {
		$("#widgetWrap").animate({
			width : "100px"
		}, function() {
			$("#ifgoWidgets").show();
		});
		window.show = true;
	}
});
//初始化宽度
function intPageSize() {
	var size = parseFloat($("#size").val());
	var leftsize = $("#widgetWrap").width();
	if (size != '') {
		if (typeof (size) == 'number') {
			$("#main").width(leftsize + size + 10);
			$("#widgetlist").width(size);

			var win = window.wellwin;
			win.ifgo = {};//编辑区域的顶点位置及宽度
			var width = $(document).width();
			win.cols = (width - 10) / 10;

			win.toDropDiv = $("#widgetlist");
			var toDropDivOffset = win.toDropDiv.offset();
			win.ifgo.left = toDropDivOffset.left;//
			win.ifgo.top = toDropDivOffset.top;
			win.ifgo.width = win.toDropDiv.width();
		}
	}
}
//初始化widget的列表区域(通过后台获取json返回 解析生成相应的html)
function initWidget() {
	var allHtml = [];
	var cates = window.moduleCate;
	var cateHtml = ['<div class="cate_div"><div class="module_content" style="display:none;" id="','',
	                '"><div class="border_while"></div>','',
	                '</div><div class="cate_name">','',
	                '</div></div>'];
	var data = window.widgetData;
	var html = ['<div class="dnr" moduleId="','','" url="','','" dwidth="','','" dheight="','','"><h3>',
				'', '</h3></div>' ];
	for(var j=0;j<cates.length;j++){
		cateHtml[1] = cates[j].uuid;
		var moduleContent = [];
		for ( var i = 0; i < data.length; i++) {
			var d = data[i];
			if(cates[j].uuid==d.cateUuid){
				html[1] = d.id;
				html[3] = d.url;
				html[5] = d.width;
				html[7] = d.height;
				html[9] = d.title;
				moduleContent[moduleContent.length] = html.join("");
			}
		}
		cateHtml[3] = moduleContent.join("");
		cateHtml[5] = cates[j].name;
		allHtml[allHtml.length] = cateHtml.join("");
	}
	$("#ifgoWidgets").html(allHtml.join(""));
	//初始化列表宽高
	$("#widgetWrap").css({
		height : $(document).height() + 'px'
	});
	$("#widgetWrap table").css({
		height : $(window).height()-17 + 'px'
	});
}
//进行编辑widget区域的初始化布局
function initEditWidget() {
	var data = window.editWidgetData;
	var win = window.wellwin;
	var html = win.widgeHtml;
	var toDropDiv = win.toDropDiv;
	var ifgo = win.ifgo;
	for ( var i = 0; i < data.length; i++) {
		var d = data[i];
		win.widgets[d.wid] = {
			data : d,
			id : d.wid,
			min : {
				minx : ifgo.left + d.left,
				miny : ifgo.top + d.top
			},
			max : {
				maxx : ifgo.left + d.left + d.width,
				maxy : ifgo.top + d.top + d.height
			}
		};
		html[1] = d.wid;
		html[4] = d.title;
		html[7] = d.content;
		toDropDiv.append(html.join(""));
		var top = d.top;
		var left = d.left;
		var id = d.wid;
		top = top + ifgo.top;//Math.floor((top + ifgo.top)/10)*10;//加5修正误差
		left = left + ifgo.left;//Math.floor((left + ifgo.left)/10)*10;//加5修正误差
		var newWidget = $("#" + id);
		$("#"+d.wid+" .close").click(function(event) {
				var widgets = window.wellwin.widgets;
				if (widgets[$(event.target).parent().parent().attr("id")]) {
					if (confirm("您确定要删除此小窗口?")) {
						var wid = id;
						$(event.target).parent().parent().remove();//把自己从DOM中删除
						delete widgets[$(event.target).parent()
								.parent().attr("id")];
					}
				}
		});
		newWidget.css({
			left : left,
			top : top,
			zIndex : 10,
			width : d.width,
			height : d.height
		});
		//初始化拖拽的事件
		win.enableWidgetDrapResize(id);
	}
}
$(function() {
	//同步数据请求
	$.ajaxSetup({
		async : false
	});
	//AJAX获取可选widgets
	$.get(ctx+"/cms/module/getall", function(data) {
		window.widgetData = data.data.moudles;
		window.moduleCate = data.data.cates;
	});
	initWidget();
	//保存按钮点击事件
	$("#save").click(function() {
		var win = window.wellwin;
		var widgets = win.widgets;
		var ifgo = win.ifgo;
		var data = [];
		for ( var wid in widgets) {//遍历属性
			var tmp = widgets[wid].data;
			var tmpoffset = $("#" + wid).offset();
			//tmp.left -= ifgo.left;
			//tmp.top -= ifgo.top;
			tmp.top = Math.floor((tmpoffset.top
					- ifgo.top + 5) / 10) * 10;
			tmp.left = Math.floor((tmpoffset.left
					- ifgo.left + 5) / 10) * 10;//加5修正误差
			delete tmp["content"];
			data[data.length] = tmp;
			$("#" + wid).attr("moduleId", tmp.moduleId);
		}
		var widgetHtml = [
				'<div class="dnrw ','','" id="','',
				'" moduleid="','','"style="left:','','px; top:','','px;width:',''
				,'height:','','px;'
				,'">',
				'<h3> <div class="title" style="float: left;">',
				'',
				"</div><div class='moreb' style='float: right;'>",
				'', "</div></h3>",
				'<div class="widgetContent">', '',
				'</div>',
				'<div class="handler"></div>', "</div>" ];
		var html = '';
		for ( var wid in widgets) {
			widgetHtml[1] = widgets[wid].data.theme;
			widgetHtml[3] = wid;
			widgetHtml[5] = widgets[wid].data.moduleId;
			widgetHtml[7] = widgets[wid].data.left;
			widgetHtml[9] = widgets[wid].data.top;
			if (widgets[wid].data.isMaxWidth) {
				if (widgets[wid].data.showBorder) {
					widgetHtml[11] = "100%;position: fixed;";
				} else {
					widgetHtml[11] = "100%;position: fixed;border: medium none;";
				}
			} else {
				if (widgets[wid].data.showBorder) {
					widgetHtml[11] = widgets[wid].data.width
							+ "px;";
				} else {
					widgetHtml[11] = widgets[wid].data.width
							+ "px;border: medium none;";
				}
			}
			widgetHtml[13] = widgets[wid].data.height;
			if (widgets[wid].data.showTitle) {
				widgetHtml[17] = widgets[wid].data.title;
			} else {
				widgetHtml[17] = '';
			}
			if (widgets[wid].data.showMore) {
				widgetHtml[19] = "更多";
			} else {
				widgetHtml[19] = '';
			}
			html = html + widgetHtml.join("");
		}
		html = '<div class="container" style="width:'
				+ $("#size").val()
				+ 'px;"><div id="widgetlist" class="out">'
				+ html + '</div></div>';
		//正确和错误的返回处理
		$.post(ctx+"/cms/cmspage/config", {
			data : JSON.stringify(data),
			html : html,
			pageWidth : $("#size").val(),
			pageuuid : window.wellwin.pageId,
			success : function(result) {
				alert('保存成功!');
			}
		});
	});
	var win = window.wellwin;
	win.ifgo = {};//编辑区域的顶点位置及宽度
	var width = $(document).width();
	win.cols = (width - 10) / 10;
	win.widgets = {};//widgets的相关信息,碰撞检测用
	win.widgeHtml = [
			'<div class="dnrw" id="','','">',
			'<h3><div class="title">',
			'',
			"</div><div class='close' title='删除'></div><div class='dragIcon' title='拖动'></div><div class='set'></div></h3>",
			'<div class="widgetContent">', '', '</div>',
			'<div class="handler"></div>', "</div>" ];
	win.toDropDiv = $("#widgetlist");
	//绝对定位
	var toDropDivOffset = win.toDropDiv.offset();
	win.ifgo.left = toDropDivOffset.left;//
	win.ifgo.top = toDropDivOffset.top;
	win.ifgo.width = win.toDropDiv.width();
	win.oldPoint = {};
	//拖widget进入可视编辑区域
	$(".dnr")
			.dragable({
				handler : 'h3'
			})
			.ondragMouseDown(function(e, ele) {
				$(e.target).parent().prev().addClass("prevModule");
				//自己位置移走,没冲突,下面的要填充上来
				//oldPoint = {id:'tmp001',min:{minx:0,miny:0},max:{maxx:0,maxy:0}};
				window.cateid = $(e.target).parent().parent().attr("id");
				document.body.appendChild(ele);
				$("#widgetWrap").hide();
			})
			.ondrag(function(e, ele) {//拖拽回调函数
				var element = $(ele);
				if (element.attr("dwidth") != null
						&& element.attr("dwidth") != "") {
					element.css("height", element
							.attr("dheight"));
					element
							.css("width", element
									.attr("dwidth"));
				} else {
					element.css("height", 100);
					element.css("width", 200);
				}
				var ifgo = win.ifgo;
				var toDropDiv = win.toDropDiv;
				var widgets = win.widgets;
				var dealConflict = win.dealConflict;
				if (element.offset().left >= ifgo.left
						&& element.offset().top >= ifgo.top) {
					toDropDiv.removeClass("out").addClass(
							"over");//进入编辑区,修改背景

					//不能超出编辑区右边界
					if ((element.getCssVal("left") + element
							.width()) > (ifgo.left + ifgo.width))
						element.css({
							left : ifgo.left + ifgo.width
									- element.width()
						});

					if (!widgets['tmp001'])
						widgets['tmp001'] = {
							id : 'tmp001',
							min : {
								minx : 0,
								miny : 0
							},
							max : {
								maxx : 0,
								maxy : 0
							},
							data : {}
						};
					tmpWidget = widgets['tmp001'];
					var top = element.getCssVal('top') || 0;
					var left = element.getCssVal('left') || 0;
					tmpWidget.min.minx = left;
					tmpWidget.min.miny = top;
					tmpWidget.max.maxx = left + element.width();
					tmpWidget.max.maxy = top + element.height();
					//碰撞处理
					dealConflict(tmpWidget);
				} else {
					toDropDiv.removeClass("over").addClass(
							"out");//离开编辑区,改变编辑区背景
				}
			})
			.ondrop(function(e, ele) {//拖拽完成放置回调函数
				$("#"+window.cateid).find(".prevModule").after(ele);
				$("#"+window.cateid).find(".prevModule").removeClass("prevModule");
				$("#widgetWrap").show();
				var element = $(ele);
				var win = window.wellwin;
				var toDropDiv = win.toDropDiv;
				var ifgo = win.ifgo;
				var widgeHtml = win.widgeHtml;
//				var dealConflict = win.dealConflict;
				toDropDiv.removeClass("over").addClass("out");//拖拽结束,编辑区变成非编辑状态
				var widgets = win.widgets;
				var enableWidgetDrapResize = win.enableWidgetDrapResize;
				//在拖拽可放置范围内
				if (element.offset().left >= ifgo.left
						&& element.offset().top >= ifgo.top) {
					var top = element.getCssVal('top') || 0;
					var left = element.getCssVal('left') || 0;
					var mid = element.attr("moduleId");
					var myDate = new Date();
					var id = [ myDate.getFullYear(),
							myDate.getMonth() + 1,
							myDate.getDate(),
							myDate.getHours(),
							myDate.getMinutes(),
							myDate.getSeconds(),
							myDate.getMilliseconds() ].join("");
					widgeHtml[1] = id;
					widgeHtml[4] = element.find("h3").text();
					widgeHtml[7] = "";
					toDropDiv.append(widgeHtml.join(""));
					top = ifgo.top
							+ Math
									.floor((top - ifgo.top + 5) / 10)
							* 10;//加5修正误差
					left = ifgo.left
							+ Math
									.floor((left - ifgo.left + 5) / 10)
							* 10;//加5修正误差
					var newWidget = $("#" + id);
					$(".close", newWidget).click(function() {
						if (confirm("您确定要删除此小窗口?")) {
							var wid = id;
							$(".dnr[wid=" + wid + "]").css({
								display : ''
							});
							newWidget.detach();//把自己从DOM中删除
							var widgets = window.widgets;
							delete widgets[wid];
						}
					});
					newWidget.css({
						left : left,
						top : top,
						zIndex : 10,
						width : element.width(),
						height : element.height()
					});
					element.css({
						position : 'static',
						top : ele.srcY,
						left : ele.srcX,
						width : 100,
						height : 55
					});//改变原来的可选widget为静态

					//增加到编辑区的widgets对象中
					widgets[id] = {
						id : id,
						min : {
							minx : left,
							miny : top
						},
						max : {
							maxx : left + newWidget.width(),
							maxy : top + newWidget.height()
						},
						data : {
							editArea : toDropDiv.width(),
							moduleId : mid,
							pageId : win.pageId,
							showMore : true,
							showTitle : true,
							isTab : false,
							showBorder : true,
							openSearch : false,
							showNum : false,
							isMaxWidth : false,
							title : widgeHtml[4],
							width : newWidget.width(),
							height : newWidget.height(),
							rows : Math.ceil((newWidget
									.height() / 10)),
							jsContent : '',
							para : '',
							top : top,
							left : left,
							wid : id,
							url : element.attr("url"),
							content : ''//内容，提交时要删除
						}
					};
					delete widgets['tmp001'];
//					dealConflict(widgets[id]);
					//设置当前元素的拖拽事件
					enableWidgetDrapResize(id);

				} else {//不可放置,还原回去
					element.css({
						position : 'static',
						top : ele.srcY,
						left : ele.srcX,
						width : 100,
						height : 55
					});
					;
				}
		});
	//加载编辑器数据
	$.get(ctx+"/cms/cmspage/getpage", {
		uuid : window.wellwin.pageId
	}, function(data) {
		window.editWidgetData = data.data.beans;
		window.cpw = data.data.cmspage;
		window.option = data.data.option;
	});
	//设置画布
	$("#size").val(cpw);
	$("#size2").val(cpw);
	intPageSize();
	//初始化模块
	initEditWidget();
	var selectwidget = {};
	$("#dialog").dialog(
		{
			autoOpen : false,
			height : 300,
			width : 350,
			modal : true,
			draggable : false,
			resizable : false,
			buttons : {
				"确定" : function() {
					selectwidget.data.title = $("#title")
							.val();
					selectwidget.data.wid = $("#wid")
					.val();
					selectwidget.data.showTitle = $(
							"#showTitle").attr("checked") == "checked";
					selectwidget.data.showMore = $(
							"#showMore").attr("checked") == "checked";
					selectwidget.data.showBorder = $(
							"#showBorder").attr("checked") == "checked";
					selectwidget.data.openSearch = $(
							"#openSearch").attr("checked") == "checked";
					selectwidget.data.showNum = $(
							"#showNum").attr("checked") == "checked";
					selectwidget.data.isMaxWidth = $(
							"#isMaxWidth").attr("checked") == "checked";
					selectwidget.data.rows = $("#rows")
							.val();
					selectwidget.data.para = $("#para")
							.val();
					selectwidget.data.theme = $("#theme")
							.find("option:selected").val();
					selectwidget.data.moreStyle = $(
							"#moreStyle").find(
							"option:selected").val();
					selectwidget.data.jsContent = $(
							"#jsContent").val();
					if ($("#title").val() == "Tab容器") {
						var tjson = [];
						$(".selectView")
								.each(
										function() {
											var json = {};
											json.moduleId = $(
													this,
													"option:selected")
													.val();
											json.title = $(
													this)
													.find(
															"option:selected")
													.text();
											tjson
													.push(json);
										});
						selectwidget.data.tabVal = JSON
								.stringify(tjson);
						selectwidget.data.isTab = true;
					}
					if (selectwidget.data.showTitle) {
						$("#" + selectwidget.id).find(
								"h3 .title").html(
								selectwidget.data.title);
					} else {
						$("#" + selectwidget.id)
								.find("h3 .title")
								.find("title")
								.html(
										"——"
												+ selectwidget.data.title);
					}

					$(this).dialog("close");
				},
				"取消" : function() {
					$(this).dialog("close");
				}
			},
			close : function() {
				$(this).dialog("close");
			},
			open : function() {
				$("#theme").html(window.option);
				$("#title").attr("disabled", false);
				$("#title").val(selectwidget.data.title);
				$("#showTitle").attr("checked",
						selectwidget.data.showTitle);
				$("#showMore").attr("checked",
						selectwidget.data.showMore);
				$("#showBorder").attr("checked",
						selectwidget.data.showBorder);
				$("#openSearch").attr("checked",
						selectwidget.data.openSearch);
				$("#showNum").attr("checked",
						selectwidget.data.showNum);
				$("#isMaxWidth").attr("checked",
						selectwidget.data.isMaxWidth);
				$("#rows").val(selectwidget.data.rows);
				$("#para").val(selectwidget.data.para);
				$("#theme").val(selectwidget.data.theme);
				$("#moreStyle").val(
						selectwidget.data.moreStyle);
				$("#jsContent").val(
						selectwidget.data.jsContent);
				$("#wid").val(selectwidget.data.wid);
				if ($(".tabButton").length > 0) {
					$(".tabButton").each(function() {
						$(this).parent().parent().remove();
					});
				}
				if (selectwidget.data.title == "Tab容器") {

					$("#title")
							.attr("disabled", "disabled");
					//select的所有选项
					var module = window.widgetData;
					for ( var i in module) {
						if (module[i].title != "Tab容器") {
							window.moduleStr += "<option value='"+module[i].id+"'>"
									+ module[i].title
									+ "</option>";
							window.moduleStr = window.moduleStr
									.replace("undefined",
											"");
						}
					}
					var str = "<tr><td><input value='添加视图' type='button' class='tabButton' id='addview'/></td></tr>";
					var jsonstr = selectwidget.data.tabVal;
					if (jsonstr != "") {
						var vjson = JSON.parse(jsonstr);
					}
					for ( var j in vjson) {
						str += "<tr><td><select name='tabVal' class='selectView'>";
						str += "<option value='"+vjson[j].moduleId+"' selected='selected'>"
								+ vjson[j].title
								+ "</option>" + moduleStr;
						str += "</select></td><td><input value='删除视图' type='button' class='deleteview tabButton'/></td></tr>";
					}
					if ($("#addview").length == 0) {
						$("#dialog").find("table").append(
								str);
					}
					$("#addview")
							.click(
									function() {
										var addStr = "<tr><td><select name='tabVal' class='selectView'>"
												+ moduleStr
												+ "</select></td><td><input value='删除视图' type='button' class='deleteview'/></td></tr>";
										$(this)
												.parent()
												.parent()
												.parent()
												.append(
														addStr);
									});
					$(".deleteview").click(function() {
						$(this).parent().parent().remove();
					});
				}
			}
	});
	$("#dialog2").dialog(
		{
			autoOpen : false,
			height : 300,
			width : 350,
			modal : true,
			draggable : false,
			resizable : false,
			buttons : {
				"确定" : function() {
					var size = parseFloat($("#size2").val());
					$("#size").val(size);
					var leftsize = $("#widgetWrap").width();
					if (size != '') {
						if (typeof (size) == 'number') {
							$("#main").width(leftsize + size + 10);
							$("#widgetlist").width(size);
	
							var win = window.wellwin;
							win.ifgo = {};//编辑区域的顶点位置及宽度
							var width = $(document).width();
							win.cols = (width - 10) / 10;
	
							win.toDropDiv = $("#widgetlist");
							var toDropDivOffset = win.toDropDiv.offset();
							win.ifgo.left = toDropDivOffset.left;//
							win.ifgo.top = toDropDivOffset.top;
							win.ifgo.width = win.toDropDiv.width();
						}
					}
					$(this).dialog("close");
				},
				"取消" : function() {
					$(this).dialog("close");
				}
			},
			close : function() {
				$(this).dialog("close");
			},
			open : function() {}
	});
	//widget设置事件
	$(".set").click(function(event) {
		selectwidget = window.wellwin.widgets[$(event.target)
				.parent().parent().attr("id")];
		$("#dialog").dialog("open");
	});
	//打开该分类的widget列表
	$(".cate_div").click(function(){
		var display = $(this).find(".module_content").css("display");
		if(display=="none"){
			$(".module_content").hide();
			$(this).find(".module_content").show();
		}else{
			$(".module_content").hide();
		}
	}); 
	//关闭分类列表
	$(document).mousedown(function(event){
		   var temp = $(event.target).attr("class");
			/**地图隐藏与现实**/
		   if(temp!="cate_div"&&temp!="module_content"&&temp!="cate_name"){
				$(".module_content").hide();
		   }
	});
});

wellwin.enableWidgetDrapResize = function(id) {
	var win = window.wellwin;
	$("#" + id)
			.dragable({
				handler : 'h3'
			})
			.ondrag(function(e, ele) {
				var element = $(ele);
				var ifgo = win.ifgo;
				var toDropDiv = win.toDropDiv;
				var dealConflict = win.dealConflict;
				var widgets = win.widgets;
				if (element.offset().left >= ifgo.left
						&& element.offset().top >= ifgo.top) {
					toDropDiv.removeClass("out").addClass("over");//进入编辑区,修改背景
					//不能超出编辑区右边界
					if ((element.getCssVal("left") + element
							.width()) > (ifgo.left + ifgo.width))
						element.css({
							left : ifgo.left + ifgo.width
									- element.width()
						});
				} else {
					if ((element.getCssVal("left") < ifgo.left)) {
						element.css({
							left : ifgo.left
						});
					}
					if ((element.getCssVal("top") < ifgo.top)) {
						element.css({
							top : ifgo.top
						});
					}
				}
				if (element.getCssVal("height") < ifgo.height) {
					$("#widgetlist").height(
							element.getCssVal("height"));
				}
				widgets[id].min.minx = element.css("left").replace("px","");
				widgets[id].min.miny = element.css("top").replace("px","");
				widgets[id].max.maxx = parseInt(element.css("left").replace("px","")) + element.width();
				widgets[id].max.maxy = parseInt(element.css("top").replace("px","")) + element.height();
				//处理碰撞
				dealConflict(widgets[id]);
			})
			.ondrop(function(e, ele) {
				var win = window.wellwin;
				var toDropDiv = win.toDropDiv;
				var ifgo = win.ifgo;
				var element = $(ele);
				var widgets = win.widgets;
//				var dealConflict = win.dealConflict;
				toDropDiv.removeClass("over").addClass("out");
				//在拖拽可放置范围内
				var id = element.attr("id");
				if (element.offset().left >= ifgo.left
						&& element.offset().top >= ifgo.top) {
					var top = element.getCssVal('top') || 0;
					var left = element.getCssVal('left') || 0;
					top = ifgo.top
							+ Math
									.floor((top - ifgo.top + 5) / 10)
							* 10;//加5修正误差
					left = ifgo.left
							+ Math
									.floor((left - ifgo.left + 5) / 10)
							* 10;//加5修正误差
					element.css({
						left : left,
						top : top,
						zIndex : 10
					});
					widgets[id].data.left = left;
					widgets[id].data.top = top;
					//处理碰撞
//					dealConflict(widgets[id]);
				} else {//不可放置,还原回去
					element.detach();//把自己从DOM中删除
					delete widgets[id];
				}
			})
			.resizable({
					handler : '.handler',
					min : {
						width : 60,
						height : 40
					},
					max : {
						width : 2000,
						height : 2000
					}
				})
			.onStop(function(e, ele) {
				var element = $(ele);
				var resizeData = ele.resizeData;
				var w = resizeData.targetW;
				var h = resizeData.targetH;
				var win = window.wellwin;
				var widgets = win.widgets;
				var id = element.attr("id");
				widgets[id].data.width = w;
				widgets[id].data.height = h;
				//widgets[id].data.rows = Math.ceil(h / 10);

			})
			.onResize(function(e, ele) {
				var element = $(ele);
				var resizeData = ele.resizeData;
				var widget = element.parent();
				var win = window.wellwin;
				var ifgo = win.ifgo;
				//计算碰撞,下移widget,注意要循环计算
				if ((element.getCssVal("left") + element.width()) > (ifgo.left + ifgo.width))//不能超出边界
					element.css({
						width : ifgo.left + ifgo.width- element.width()
				});
			});
};

//碰撞处理
wellwin.dealConflict = function(point) {
//	alert(JSON.stringify(point));
	var win = window;
	//调用获得相交的矩形
	var points = win.wellwin.findConflictRects(point);
	var i = 50;
	var toMovePoint;
	while (points && points.length > 0 && i > 0) {
		toMovePoint = points.shift();
		//移动已经冲突的widget,接着可能自己的移动引起的冲突要继续处理
//		win.wellwin.moveConflict(toMovePoint, point);
		i--;
	}

};
//移动冲突的矩形
wellwin.moveConflict = function(toMovePoint, srcPoint) {
	var win = window.wellwin;
	var widgets = win.widgets;
	var moveWid = toMovePoint.id;
	var ifgo = win.ifgo;
	var tmpSrcPoint = {//跟网格对齐的问题
		id : srcPoint.id,
		min : {
			minx : 0,//只上下移动,x轴无用
			miny : ifgo.top
					+ Math.floor((srcPoint.min.miny - ifgo.top) / 10.0)
					* 10 //冲突对象要往上移动时向下取整,否则向上取整
		},
		max : {
			maxx : 0,//只上下移动,x轴无用
			maxy : ifgo.top
					+ Math.floor((srcPoint.max.maxy - ifgo.top) / 10.0)
					* 10 //冲突对象要往上移动时向下取整,否则向上取整
		}
	};
	var miny = 0;
	var maxy = 0;
	var moveHeight = Math
			.ceil((toMovePoint.max.maxy - toMovePoint.min.miny) / 10) * 10;//要移动节点的高度

	//向上时,取拖拽节点的左上角的位置
	maxy = srcPoint.min.miny;
	miny = maxy - moveHeight;

	var toDropDiv = win.toDropDiv;//编辑区的jquery对象

	var rows = (miny - win.ifgo.top) / 10;
	var vpoint = {//虚拟要移动到的矩形
		id : moveWid,
		min : {
			minx : toMovePoint.min.minx,//只上下移动所以X还是原来的位置
			miny : miny
		},
		max : {
			maxx : toMovePoint.max.maxx,//只上下移动所以X还是原来的位置
			maxy : maxy
		//还是移动对象的高度
		}
	}
	for ( var i = 0; i < rows; i++) {
		vpoint.min.miny = miny - i * 10;
		vpoint.max.maxy = maxy - i * 10;
		result = win.findConflictRects(vpoint);
		if (result && result.length > 0)
			continue;
		else {//节点可以上移
			vpoint.id = toMovePoint.id;
			$.extend(true, widgets[toMovePoint.id], vpoint);
			widgets[toMovePoint.id] = vpoint;
			$("#" + moveWid).css({
				left : vpoint.min.minx,
				top : vpoint.min.miny,
				zIndex : 10
			});
			win.dealConflict(vpoint);
			return;
		}
	}
	//没有向上移动
	tmpSrcPoint = {//跟网格对齐的问题
		id : srcPoint.id,
		min : {
			minx : 0,//只上下移动,x轴无用
			miny : ifgo.top
					+ Math.ceil((srcPoint.min.miny - ifgo.top) / 10.0)
					* 10 //冲突对象要往上移动时向下取整,否则向上取整
		},
		max : {
			maxx : 0,//只上下移动,x轴无用
			maxy : ifgo.top
					+ Math.ceil((srcPoint.max.maxy - ifgo.top) / 10.0)
					* 10 //冲突对象要往上移动时向下取整,否则向上取整
		}
	};
	miny = tmpSrcPoint.max.maxy;
	maxy = miny + moveHeight;

	vpoint.min.miny = miny;
	vpoint.max.maxy = maxy;
	$.extend(true, widgets[toMovePoint.id], vpoint);
	widgets[toMovePoint.id] = vpoint;
	$("#" + moveWid).css({
		left : vpoint.min.minx,
		top : vpoint.min.miny,
		zIndex : 10
	});
	win.dealConflict(vpoint);

};
//找出相交的矩形,可能多个,所以返回数组
wellwin.findConflictRects = function(point) {
	var points = [];
	var win = window.wellwin;
	var widgets = win.widgets;
	var isConflict = win.isConflict;
	for ( var wid in widgets) {//遍历属性
		if (point.id && point.id == wid)//是自己的
			continue;
		if (isConflict(point, widgets[wid]))
			points[points.length] = widgets[wid];
	}
	return points;
}
//矩形是否相交
wellwin.isConflict = function(point1, point2) {
	var retVal = true;
	var rect = {}//相交矩形
	rect.min = {
			minx : Math.max(point1.min.minx, point2.min.minx),
			miny : Math.max(point1.min.miny, point2.min.miny)
		};
	rect.max = {
		maxx : Math.min(point1.max.maxx, point2.max.maxx),
		maxy : Math.min(point1.max.maxy, point2.max.maxy)
	};
	if (rect.min.minx > rect.max.maxx || rect.min.miny > rect.max.maxy){
		retVal = false;
	}
//	if(point2.data.title=="日程"){
//		alert("拖动min:"+point1.min.minx+","+point1.min.miny+"拖动max:"+point1.max.maxx+","+point1.max.maxy+
//				"比较min:"+point2.min.minx+","+point2.min.miny+"拖动max:"+point2.max.maxx+","+point2.max.maxy+
//				"取值min:"+JSON.stringify(rect.min)+",max"+JSON.stringify(rect.max));
//		alert(retVal);
//	}
	return retVal;
};