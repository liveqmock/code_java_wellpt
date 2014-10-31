$(function() {
	var bean = {
		"uuid" : null,
		"ipAddress1" : null,
		"ipAddress2" : null,
		"applyTo" : null,
		"validPeriod" : null,
		"domainAddress1" : null,
		"domainAddress2" : null,
	};

	var beans = [];

	var login_verify_code_selector = "#login_verify_code_list";
	var ip_login_limit_selector = "#ip_login_limit_list";
	var ip_sms_verify_selector = "#ip_sms_verify_list";
	var domain_login_limit_selector = "#domain_login_limit_list";

	// JQuery UI按钮
	$("input[type=submit], a, button", $(".btn-group")).button();
	// JQuery UI页签
	$(".tabs").tabs();

	// 用户登录时需要图形码校验的IP设置
	$(login_verify_code_selector)
			.jqGrid(
					{
						datatype : "local",
						colNames : [ "uuid", "applyTo", "需要校验的IP地址(段)",
								"不校验的IP地址(段)" ],
						colModel : [ {
							name : "uuid",
							index : "uuid",
							width : "180",
							hidden : true
						}, {
							name : "applyTo",
							index : "applyTo",
							width : "180",
							hidden : true
						}, {
							name : "ipAddress1",
							index : "ipAddress1",
							width : "180",
							editable : true
						}, {
							name : "ipAddress2",
							index : "ipAddress2",
							width : "180",
							editable : true
						} ],
						sortable : false,
						multiselect : true,
						cellEdit : true,// 表示表格可编辑
						cellsubmit : "clientArray", // 表示在本地进行修改
						autowidth : true,
						height : "auto",
						afterEditCell : function(rowid, cellname, value, iRow,
								iCol) {
							// Modify event handler to save on blur.
							$("#" + iRow + "_" + cellname,
									login_verify_code_selector).one(
									'blur',
									function() {
										$(login_verify_code_selector).saveCell(
												iRow, iCol);
									});
						}
					});

	// 用户登录的IP设置
	$(ip_login_limit_selector).jqGrid(
			{
				datatype : "local",
				colNames : [ "uuid", "applyTo", "userIds", "用户",
						"允许登录的IP地址(段)", "禁止登录的IP地址(段)" ],
				colModel : [ {
					name : "uuid",
					index : "uuid",
					width : "180",
					hidden : true
				}, {
					name : "applyTo",
					index : "applyTo",
					width : "180",
					hidden : true
				}, {
					name : "userIds",
					index : "userIds",
					width : "180",
					hidden : true
				}, {
					name : "usernames",
					index : "usernames",
					width : "180",
					editable : true
				}, {
					name : "ipAddress1",
					index : "ipAddress1",
					width : "180",
					editable : true
				}, {
					name : "ipAddress2",
					index : "ipAddress2",
					width : "180",
					editable : true
				} ],
				sortable : false,
				multiselect : true,
				cellEdit : true,// 表示表格可编辑
				cellsubmit : "clientArray", // 表示在本地进行修改
				autowidth : true,
				height : "auto",
				afterEditCell : function(rowid, cellname, value, iRow, iCol) {
					var rowData = $(ip_login_limit_selector).getRowData(rowid);
					var cell_selector = "#" + iRow + "_" + cellname;
					if (cellname == "usernames") {
						$(cell_selector, ip_login_limit_selector).one(
								'focus',
								function() {
									$.unit.open({
										initNames : value,
										initIDs : rowData.userIds,
										selectType : 6,
										afterSelect : function(retVal) {
											$(cell_selector).val(retVal.name);
											$(ip_login_limit_selector)
													.saveCell(iRow, iCol);
											$(ip_login_limit_selector)
													.setCell(rowid, "userIds",
															retVal.id);
										},
										close : function(e) {
											$(ip_login_limit_selector)
													.saveCell(iRow, iCol);
										}
									});
								});
					} else {
						// Modify event handler to save on blur.
						$(cell_selector, ip_login_limit_selector).one(
								'blur',
								function() {
									$(ip_login_limit_selector).saveCell(iRow,
											iCol);
								});
					}
				}
			});

	// 手机短信二次验证的IP设置
	$(ip_sms_verify_selector).jqGrid(
			{
				datatype : "local",
				colNames : [ "uuid", "applyTo", "userIds", "用户",
						"需要二次验证的IP地址(段)", "不二次验证的IP地址(段)" ],
				colModel : [ {
					name : "uuid",
					index : "uuid",
					width : "180",
					hidden : true
				}, {
					name : "applyTo",
					index : "applyTo",
					width : "180",
					hidden : true
				}, {
					name : "userIds",
					index : "userIds",
					width : "180",
					hidden : true
				}, {
					name : "usernames",
					index : "usernames",
					width : "180",
					editable : true
				}, {
					name : "ipAddress1",
					index : "ipAddress1",
					width : "180",
					editable : true
				}, {
					name : "ipAddress2",
					index : "ipAddress2",
					width : "180",
					editable : true
				} ],
				sortable : false,
				multiselect : true,
				cellEdit : true,// 表示表格可编辑
				cellsubmit : "clientArray", // 表示在本地进行修改
				autowidth : true,
				height : "auto",
				afterEditCell : function(rowid, cellname, value, iRow, iCol) {
					var rowData = $(ip_sms_verify_selector).getRowData(rowid);
					var cell_selector = "#" + iRow + "_" + cellname;
					if (cellname == "usernames") {
						$(cell_selector, ip_sms_verify_selector).one(
								'focus',
								function() {
									$.unit.open({
										initNames : value,
										initIDs : rowData.userIds,
										selectType : 6,
										afterSelect : function(retVal) {
											$(cell_selector).val(retVal.name);
											$(ip_sms_verify_selector).saveCell(
													iRow, iCol);
											$(ip_sms_verify_selector)
													.setCell(rowid, "userIds",
															retVal.id);
										},
										close : function(e) {
											$(ip_sms_verify_selector).saveCell(
													iRow, iCol);
										}
									});
								});
					} else {
						// Modify event handler to save on blur.
						$(cell_selector, ip_sms_verify_selector).one(
								'blur',
								function() {
									$(ip_sms_verify_selector).saveCell(iRow,
											iCol);
								});
					}
				}
			});
	
	
	// 用户登录的域设置
	$(domain_login_limit_selector).jqGrid(
			{
				datatype : "local",
				colNames : [ "uuid", "applyTo", "userIds", "用户",
						"允许登录的域", "禁止登录的域" ],
				colModel : [ {
					name : "uuid",
					index : "uuid",
					width : "180",
					hidden : true
				}, {
					name : "applyTo",
					index : "applyTo",
					width : "180",
					hidden : true
				}, {
					name : "userIds",
					index : "userIds",
					width : "180",
					hidden : true
				}, {
					name : "usernames",
					index : "usernames",
					width : "180",
					editable : true
				}, {
					name : "domainAddress1",
					index : "domainAddress1",
					width : "180",
					editable : true
				}, {
					name : "domainAddress2",
					index : "domainAddress2",
					width : "180",
					editable : true
				} ],
				sortable : false,
				multiselect : true,
				cellEdit : true,// 表示表格可编辑
				cellsubmit : "clientArray", // 表示在本地进行修改
				autowidth : true,
				height : "auto",
				afterEditCell : function(rowid, cellname, value, iRow, iCol) {
					var rowData = $(domain_login_limit_selector).getRowData(rowid);
					var cell_selector = "#" + iRow + "_" + cellname;
					if (cellname == "usernames") {
						$(cell_selector, domain_login_limit_selector).one(
								'focus',
								function() {
									$.unit.open({
										initNames : value,
										initIDs : rowData.userIds,
										selectType : 6,
										afterSelect : function(retVal) {
											$(cell_selector).val(retVal.name);
											$(domain_login_limit_selector)
													.saveCell(iRow, iCol);
											$(domain_login_limit_selector)
													.setCell(rowid, "userIds",
															retVal.id);
										},
										close : function(e) {
											$(domain_login_limit_selector)
													.saveCell(iRow, iCol);
										}
									});
								});
					} else {
						// Modify event handler to save on blur.
						$(cell_selector, domain_login_limit_selector).one(
								'blur',
								function() {
									$(domain_login_limit_selector).saveCell(iRow,
											iCol);
								});
					}
				}
			});
	

	// 用户登录时需要图形码校验的IP设置
	$("#btn_login_verify_code_add").click(function() {
		var newDate = new Date().getTime();
		var mydata = {
			uuid : null,
			applyTo : "1",
			rowStatus : "added"
		};
		$(login_verify_code_selector).jqGrid("addRowData", newDate, mydata);
	});
	$("#btn_login_verify_code_del").click(
			function() {
				var rowids = $(login_verify_code_selector).jqGrid(
						'getGridParam', 'selarrrow');
				if (rowids.length == 0) {
					alert("请选择记录!");
					return;
				}
				for ( var i = (rowids.length - 1); i >= 0; i--) {
					var rowData = $(login_verify_code_selector).getRowData(
							rowids[i]);
					if (rowData.uuid != null && $.trim(rowData.uuid) != "") {
						rowData.rowStatus = "deleted";
						beans.push(rowData);
					}
					$(login_verify_code_selector).jqGrid('delRowData',
							rowids[i]);
				}
			});

	// 用户登录的IP设置
	$("#btn_ip_login_limit_add").click(function() {
		var newDate = new Date().getTime();
		var mydata = {
			uuid : null,
			applyTo : "2",
			rowStatus : "added"
		};
		$(ip_login_limit_selector).jqGrid("addRowData", newDate, mydata);
	});
	$("#btn_ip_login_limit_del").click(
			function() {
				var rowids = $(ip_login_limit_selector).jqGrid('getGridParam',
						'selarrrow');
				if (rowids.length == 0) {
					alert("请选择记录!");
					return;
				}
				for ( var i = (rowids.length - 1); i >= 0; i--) {
					var rowData = $(ip_login_limit_selector).getRowData(
							rowids[i]);
					if (rowData.uuid != null && $.trim(rowData.uuid) != "") {
						rowData.rowStatus = "deleted";
						beans.push(rowData);
					}
					$(ip_login_limit_selector).jqGrid('delRowData', rowids[i]);
				}
			});

	// 手机短信二次验证的IP设置
	$("#btn_ip_sms_verify_add").click(function() {
		var newDate = new Date().getTime();
		var mydata = {
			uuid : null,
			applyTo : "3",
			rowStatus : "added"
		};
		$(ip_sms_verify_selector).jqGrid("addRowData", newDate, mydata);

		// 显示短信验证码有效期限
		$("#sms_valid_period").show();
	});
	$("#btn_ip_sms_verify_del").click(
			function() {
				var rowids = $(ip_sms_verify_selector).jqGrid('getGridParam',
						'selarrrow');
				if (rowids.length == 0) {
					alert("请选择记录!");
					return;
				}
				for ( var i = (rowids.length - 1); i >= 0; i--) {
					var rowData = $(ip_sms_verify_selector).getRowData(
							rowids[i]);
					if (rowData.uuid != null && $.trim(rowData.uuid) != "") {
						rowData.rowStatus = "deleted";
						beans.push(rowData);
					}
					$(ip_sms_verify_selector).jqGrid('delRowData', rowids[i]);
				}

				// 隐藏短信验证码有效期限
				if ($(ip_sms_verify_selector).getRowData().length == 0) {
					$("#sms_valid_period").hide();
				}
			});
	
	
	// 用户登录的域设置
	$("#btn_domain_login_limit_add").click(function() {
		var newDate = new Date().getTime();
		var mydata = {
			uuid : null,
			applyTo : "4",
			rowStatus : "added"
		};
		$(domain_login_limit_selector).jqGrid("addRowData", newDate, mydata);
	});
	$("#btn_domain_login_limit_del").click(
			function() {
				var rowids = $(domain_login_limit_selector).jqGrid('getGridParam',
						'selarrrow');
				if (rowids.length == 0) {
					alert("请选择记录!");
					return;
				}
				for ( var i = (rowids.length - 1); i >= 0; i--) {
					var rowData = $(domain_login_limit_selector).getRowData(
							rowids[i]);
					if (rowData.uuid != null && $.trim(rowData.uuid) != "") {
						rowData.rowStatus = "deleted";
						beans.push(rowData);
					}
					$(domain_login_limit_selector).jqGrid('delRowData', rowids[i]);
				}
			});
	

	// 允许登录本系统的用户
	$("#usernames").click(function() {
		$.unit.open({
			labelField : "usernames",
			valueField : "userIds",
			title : "选择用户",
			selectType : 6
		});
	});

	// 保存用户信息
	$("#btn_save").click(function() {
		var changes1 = $(login_verify_code_selector).getChangedCells('all');
		for ( var i = 0; i < changes1.length; i++) {
			var bean = changes1[i];
			bean.rowStatus = "edited";
			beans.push(bean);
		}

		var changes2 = $(ip_login_limit_selector).getChangedCells('all');
		for ( var i = 0; i < changes2.length; i++) {
			var bean = changes2[i];
			bean.rowStatus = "edited";
			beans.push(bean);
		}

		var changes3 = $(ip_sms_verify_selector).getRowData();
		var validPeriod = $("#validPeriod").val();
		if (validPeriod != null) {
			validPeriod = $.trim(validPeriod);
		}
		for ( var i = 0; i < changes3.length; i++) {
			var bean = changes3[i];
			bean.rowStatus = "edited";
			bean.validPeriod = validPeriod;
			beans.push(bean);
		}
		
		
		var changes4 = $(domain_login_limit_selector).getChangedCells('all');
		for ( var i = 0; i < changes4.length; i++) {
			var bean = changes4[i];
			bean.rowStatus = "edited";
			beans.push(bean);
		}

		// 系统访问用户
		var object = {};
		object.usernames = $("#usernames").val();
		object.userIds = $("#userIds").val();
		JDS.call({
			service : "systemAccessService.saveAllFromMap",
			data : [ object ],
			async : false,
			success : function(result) {
			}
		});
		// IP配置
		JDS.call({
			service : "ipSecurityConfigService.saveAllBean",
			data : [ beans ],
			success : function(result) {
				// 清除数据
				clear();

				// 初始化数据
				initData();

				alert("保存成功!");
			}
		});
	});

	// 初始化数据
	function initData() {
		// 系统访问用户
		JDS.call({
			service : "systemAccessService.getAllAsMap",
			data : [],
			success : function(result) {
				var data = result.data;
				$("#usernames").val(data.usernames);
				$("#userIds").val(data.userIds);
			}
		});
		// 隐藏短信验证码有效期限
		$("#sms_valid_period").hide();
		// IP配置
		JDS.call({
			service : "ipSecurityConfigService.getAllBean",
			data : [],
			success : function(result) {
				// 遍历数组添加数据
				$(result.data).each(
						function() {
							if (this.applyTo === "1") {
								$(login_verify_code_selector).jqGrid(
										"addRowData", this.uuid, this);
							} else if (this.applyTo === "2") {
								$(ip_login_limit_selector).jqGrid("addRowData",
										this.uuid, this);
							}else if (this.applyTo === "4") {
								$(domain_login_limit_selector).jqGrid("addRowData",
										this.uuid, this);
							} else if (this.applyTo === "3") {
								$(ip_sms_verify_selector).jqGrid("addRowData",
										this.uuid, this);
								// 设置短信验证码有效期限
								if (this.validPeriod != null) {
									$("#validPeriod").val(this.validPeriod);
								}
								// 隐藏短信验证码有效期限
								$("#sms_valid_period").show();
							}
						});
			}
		});
	}
	initData();

	function clear() {
		beans = [];

		// 1、用户登录时需要图形码校验的IP设置
		var $loginList = $(login_verify_code_selector);
		$loginList.jqGrid("clearGridData");

		// 2、用户登录的IP设置
		var $limitList = $(ip_login_limit_selector);
		$limitList.jqGrid("clearGridData");

		// 3、手机短信二次验证的IP设置
		var $smsList = $(ip_sms_verify_selector);
		$smsList.jqGrid("clearGridData");
		
		// 4、用户登录的域设置
		var $domainlimitList = $(domain_login_limit_selector);
		$domainlimitList.jqGrid("clearGridData");
	}
	$(window).resize(function(e) {
		var width = $(window).width();
		var height = $(window).height();
		var dheight = $(document).height();
		var vscroll = (dheight - height) > 0;
		if (vscroll) {
			width = width - 20;
		}
		if (grid = $('.ui-jqgrid-btable:visible')) {
			grid.each(function(index) {
				if (Browser.isIE()) {
					$(this).setGridWidth(width - 30);
				} else if (Browser.isChrome()) {
					$(this).setGridWidth(width - 32);
				} else if (Browser.isMozila()) {
					$(this).setGridWidth(width - 30);
				} else {
					$(this).setGridWidth(width);
				}
			});
		}
	});
	$(window).trigger("resize");
});