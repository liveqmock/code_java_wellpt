function _XMLHTTP() {
	this.getXMLHttp = function() {
		if (window.XMLHttpRequest) {
			return new XMLHttpRequest();
		} else {
			if (window.ActiveXObject) {
				try {
					return new ActiveXObject("Msxml2.XMLHTTP");
				} catch (e) {
					try {
						return new ActiveXObject("Microsoft.XMLHTTP");
					} catch (E) {
						return null;
					}
				}
			} else {
				alert("Your browser cannot handle this script");
				return null;
			}
		}
	};
	this.getXMLDocument = function(async) {
		var xmldoc = null;
		if (window.ActiveXObject) {
			xmldoc = new ActiveXObject("Microsoft.XMLDOM");
		} else {
			if (document.implementation
					&& document.implementation.createDocument) {
				xmldoc = document.implementation.createDocument("", "", null);
			} else {
				alert("Your browser cannot handle this script");
			}
		}
		if (xmldoc != null) {
			xmldoc.async = (async == true) ? true : false;
		}
		return xmldoc;
	};
}
function DBServer(applyName) {
	this.Super = _XMLHTTP;
	this.Super();
	this.applyName = applyName;
	this.url = "/servlet/" + applyName + "?open&Temp=" + Math.random();
	this.initRequest = function() {
		this.requestXML.loadXML("<requests></requests>");
		return this.requestXML.firstChild;
	};
	this.xmlhttp = this.getXMLHttp();
	this.requestXML = this.getXMLDocument();
	this.requests = this.initRequest();
	this.setRequestXML = function(xml) {
		this.requestXML.loadXML(xml);
		return this.requestXML.firstChild;
	};
	this.addElement = function(parentElement, name, value) {
		var element = this.requestXML.createElement(name);
		if (value != null) {
			element.appendChild(this.requestXML.createTextNode(value));
		}
		parentElement.appendChild(element);
		return element;
	};
	this.addRequest = function(id, action, sql) {
		if (sql == null || sql == "") {
			return null;
		}
		var request = this.requestXML.createElement("request");
		if (id == null || id == "") {
			id = this.applyName;
		}
		request.setAttribute("id", id);
		if (action == null || action == "") {
			action = "read";
		}
		this.addElement(request, "action", action);
		this.addElement(request, (action == "rfc") ? "function" : "sql", sql);
		this.requests.appendChild(request);
		return request;
	};
	this.addParameters = function(request, name) {
		if (name == null || name == "") {
			var tArray = request.selectNodes("parameters");
			if (tArray == null || tArray.length == 0) {
				name = "p_0";
			} else {
				name = "p_" + tArray.length;
			}
		}
		var element = this.addElement(request, "parameters");
		element.setAttribute("name", name);
		return element;
	};
	this.addParameter = function(parameters, parameterValue) {
		if (parameterValue == null) {
			return null;
		}
		var element = this.addElement(parameters, "parameter", parameterValue);
		return element;
	};
	this.addField = function(parentElement, name, value) {
		if (value == null) {
			return null;
		}
		var element = this.addElement(parentElement, "field", value);
		element.setAttribute("name", name);
		return element;
	};
	this.addStructure = function(parentElement, name) {
		var element = parentElement.selectSingleNode('//structure[@name="'
				+ name + '"]');
		if (element == null) {
			element = this.addElement(parentElement, "structure", null);
		}
		element.setAttribute("name", name);
		return element;
	};
	this.addTable = function(parentElement, name) {
		var element = parentElement.selectSingleNode('//table[@name="' + name
				+ '"]');
		if (element == null) {
			element = this.addElement(parentElement, "table", null);
		}
		element.setAttribute("name", name);
		return element;
	};
	this.sendData = function(async, readystatechange) {
		var async = (async == true) ? true : false;
		if (this.xmlhttp == null) {
			return false;
		}
		this.xmlhttp.open("Post", this.url, async);
		this.xmlhttp.setRequestHeader("Content-Type",
				"application/x-www-form-urlencoded;");
		if (async == true) {
			this.readyFunction = readystatechange;
			var db = this;
			this.xmlhttp.onreadystatechange = function() {
				if (db.xmlhttp.readyState == 4 && db.xmlhttp.status == 200) {
					db.responseXML = db.xmlhttp.responseXML;
					var sizer = new DBSizer(db);
					if (sizer.stopToSizer) {
						sizer.selectRecord();
					} else {
						eval(db.readyFunction + "(db)");
					}
				}
			};
			this.xmlhttp.send(this.requestXML);
		} else {
			this.xmlhttp.send(this.requestXML);
			this.responseXML = this.xmlhttp.responseXML;
		}
	};
	this.getResponseXML = function() {
		return this.responseXML;
	};
	this.getResponseNodeForID = function(id, pID) {
		if (id == null || id == "") {
			id = this.applyName;
		}
		var apply = this.responseXML.selectSingleNode("/responses/" + id);
		if (apply == null || pID == null || pID == "") {
			return apply;
		}
		var nodes = apply.selectNodes("response");
		if (nodes == null || nodes.length == 0) {
			return null;
		}
		for ( var i = 0; i < nodes.length; i++) {
			if (nodes[i].getAttribute("name") == pID) {
				return nodes[i];
			}
		}
		return null;
	};
	this.getResponseNodeForRequest = function(request) {
		var id = request.getAttribute("id");
		return this.getResponseNodeForID(id);
	};
	this.getResponseNodeForParameters = function(parameters) {
		var id = parameters.parentNode.getAttribute("id");
		return this.getResponseNodeForID(id, parameters.getAttribute("name"));
	};
	this.getResponseArrayForID = function(id, pID) {
		var node, tName, tValue, pIndex, status, type, tObj, tStructure, tTable;
		var aReturn = new Array();
		if (id == null || id == "") {
			id = this.applyName;
		}
		var apply = this.responseXML.selectSingleNode("/responses/" + id);
		if (apply == null) {
			return null;
		}
		type = apply.getAttribute("type");
		var nodes = apply.selectNodes("response");
		if (nodes == null || nodes.length == 0) {
			return null;
		}
		for ( var i = 0; i < nodes.length; i++) {
			if (pID != null && pID != "") {
				if (nodes[i].getAttribute("name") == pID) {
					pIndex = i;
				} else {
					continue;
				}
			}
			status = nodes[i].getAttribute("status");
			aReturn[i] = (status == "success") ? new Array() : null;
			if (status != "success") {
				continue;
			}
			if (type == "rfc") {
				tObj = new Object;
				aReturn[i] = tObj;
				var fields = nodes[i].selectNodes("field");
				for ( var j = 0; j < fields.length; j++) {
					tName = fields[j].getAttribute("name");
					tValue = fields[j].text;
					eval("tObj.field_" + tName + "=tValue");
				}
				var structures = nodes[i].selectNodes("structure");
				for ( var j = 0; j < structures.length; j++) {
					tName = structures[j].getAttribute("name");
					tStructure = new Object;
					eval("tObj.structure_" + tName + "=tStructure");
					var fields = structures[j].selectNodes("field");
					for ( var k = 0; k < fields.length; k++) {
						tName = fields[k].getAttribute("name");
						tValue = fields[k].text;
						eval("tStructure.field_" + tName + "=tValue");
					}
				}
				var tables = nodes[i].selectNodes("table");
				for ( var t = 0; t < tables.length; t++) {
					var records = tables[t].selectNodes("record");
					if (records.length == 0) {
						continue;
					}
					tName = tables[t].getAttribute("name");
					tTable = new Array();
					eval("tObj.table_" + tName + "=tTable");
					for ( var j = 0; j < records.length; j++) {
						tTable[j] = new Object;
						var fields = records[j].selectNodes("field");
						for ( var k = 0; k < fields.length; k++) {
							tName = fields[k].getAttribute("name");
							tValue = fields[k].text;
							eval("tTable[j].field_" + tName + "=tValue");
						}
					}
				}
				continue;
			}
			var records = nodes[i].selectNodes("record");
			for ( var j = 0; j < records.length; j++) {
				aReturn[i][j] = new Array();
				var fields = records[j].selectNodes("field");
				for ( var k = 0; k < fields.length; k++) {
					tName = fields[k].getAttribute("name");
					tValue = fields[k].text;
					aReturn[i][j].push(tValue);
				}
			}
		}
		if (pID != null && pID != "") {
			return aReturn[pIndex];
		} else {
			return aReturn;
		}
	};
	this.getResponseArrayForRequest = function(request) {
		var id = request.getAttribute("id");
		return this.getResponseArrayForID(id);
	};
	this.getResponseArrayForParameters = function(parameters) {
		var id = parameters.parentNode.getAttribute("id");
		return this.getResponseArrayForID(id, parameters.getAttribute("name"));
	};
}
function DBConfig(id) {
	this.Super = _XMLHTTP;
	this.Super();
	this.id = id;
	this.url = "/servlet/DBConfig?open&Temp=" + Math.random();
	this.initRequest = function() {
		this.requestXML.loadXML("<requests></requests>");
		return this.requestXML.firstChild;
	};
	this.xmlhttp = this.getXMLHttp();
	this.requestXML = this.getXMLDocument();
	this.requests = this.initRequest();
	this.initData = function() {
		if (this.xmlhttp == null) {
			return false;
		}
		this.xmlhttp.open("Post", this.url, false);
		var id = this.requestXML.createElement("id");
		id.appendChild(this.requestXML.createTextNode(this.id));
		var request = this.requestXML.createElement("request");
		request.appendChild(id);
		this.requests.appendChild(request);
		this.xmlhttp.send(this.requestXML);
		this.responseXML = this.xmlhttp.responseXML;
		this.config = this.responseXML
				.selectSingleNode("/responses/" + this.id);
		if (this.config == null) {
			return false;
		}
		var fields = this.config.selectNodes("field");
		for ( var j = 0; j < fields.length; j++) {
			var name = fields[j].getAttribute("name");
			var value = fields[j].text;
			if (name == "In" || name == "Out" || name == "SQL") {
				eval("this.c" + name + "=value.split('~~')");
			} else {
				if (name == "Async") {
					eval("this.c" + name + "=(value=='1')?true:false");
				} else {
					eval("this.c" + name + "=value");
				}
			}
		}
		this.cDatas = new Array();
		for ( var i = 0; i < this.cSQL.length; i++) {
			if (this.cSQL[i] == "") {
				continue;
			}
			var t = this.cSQL[i].split("``");
			var tArray = new Array();
			tArray.ID = t[0];
			tArray.SQL = t[1];
			tArray.Action = t[2];
			t = new Array();
			for ( var j = 0; j < this.cIn.length; j++) {
				var a = this.cIn[j].split("``");
				if (a[0] == tArray.ID) {
					t.push(a[1]);
				}
			}
			tArray.In = t;
			t = new Array();
			for ( var j = 0; j < this.cOut.length; j++) {
				var a = this.cOut[j].split("``");
				if (a[0] == tArray.ID) {
					t.push(a[1]);
				}
			}
			tArray.Out = t;
			this.cDatas.push(tArray);
		}
		return true;
	};
	this.inited = this.initData();
}
function DBManager(config, id) {
	if (config == null && id != null && id != "") {
		config = new DBConfig(id);
	}
	this.config = config;
	this.Super = DBServer;
	this.Super(config.cApply);
	this.getFieldObjValue = function(field) {
		var value = null;
		switch (field.type) {
		case "radio":
		case "checkbox":
			if (field.checked == true) {
				value = field.value;
			}
			break;
		case "select-one":
		case "select-multiple":
			var tArray = new Array();
			for ( var j = 0; j < field.options.length; j++) {
				if (field.options[j].selected != true) {
					continue;
				}
				tArray[tArray.length] = field.options[j].value;
			}
			value = tArray.join(";");
			break;
		case "hidden":
		case "text":
		case "textarea":
			value = field.value;
			break;
		}
		return value;
	};
	this.getFieldValue = function(fieldName) {
		var value = null;
		try {
			var field = eval("document.all." + fieldName);
			if (typeof (field) == "object") {
				if (typeof (field.type) == "undefined"
						&& typeof (field.length) != "undefined") {
					var tArray = new Array();
					for ( var n = 0; n < field.length; n++) {
						var one = this.getFieldObjValue(field[n]);
						if (one != null) {
							tArray[tArray.length] = one;
						}
					}
					value = tArray.join(";");
				} else {
					value = this.getFieldObjValue(field);
				}
			}
		} catch (e) {
		}
		return value;
	};
	this.getParameterValue = function() {
		var tValue, tArray, list, temp, index, row, column, field, tName, define, oneDatas, inputName;
		this.inDatas = new Array();
		this.inNames = new Array();
		this.requestCount = new Array();
		for ( var k = 0; k < this.config.cDatas.length; k++) {
			oneDatas = new Array();
			oneNames = new Array();
			var count = -1;
			for ( var i = 0; i < this.config.cDatas[k]["In"].length; i++) {
				define = this.config.cDatas[k]["In"][i];
				if (define == "") {
					oneDatas[i] = null;
					continue;
				}
				tValue = null;
				inputName = null;
				if (define.indexOf("LIST(") == 0
						|| define.indexOf("=LIST(") > 0) {
					if (define.indexOf("LIST(") == 0) {
						temp = define.substring(5, define.length - 1);
					} else {
						inputName = define.substring(0, define
								.indexOf("=LIST("));
						temp = define.substring(define.indexOf("=LIST(") + 6,
								define.length - 1);
					}
					tArray = temp.split(",");
					tName = tArray[0];
					row = tArray[1];
					column = tArray[2];
					index = tArray[3] - 0;
					try {
						field = eval("document.all." + tName);
						tArray = field.value.split(row);
						for ( var j = 0; j < tArray.length; j++) {
							temp = tArray[j].split(column);
							tArray[j] = temp[index];
						}
						tValue = tArray;
						if (inputName == null || inputName.indexOf("->") == -1) {
							if (count == -1) {
								count = tArray.length;
							} else {
								count = (tArray.length < count) ? tArray.length
										: count;
							}
						}
					} catch (e) {
					}
				} else {
					if (define.indexOf("FLIST(") == 0
							|| define.indexOf("=FLIST(") > 0) {
						if (define.indexOf("=FLIST(") > 0) {
							inputName = define.substring(0, define
									.indexOf("=FLIST("));
						}
						temp = define.substring(define.lastIndexOf(",") + 1,
								define.length - 1);
						index = temp - 0;
						tName = define.substring(define.indexOf("FLIST(") + 6,
								define.length - 2 - temp.length);
						try {
							list = eval(tName);
							tValue = list.GetColValue(index);
							if (inputName == null
									|| inputName.indexOf("->") == -1) {
								if (count == -1) {
									count = tValue.length;
								} else {
									count = (tValue.length < count) ? tValue.length
											: count;
								}
							}
						} catch (e) {
						}
					} else {
						if (define.indexOf("FUNCTION(") == 0
								|| define.indexOf("=FUNCTION(") > 0) {
							try {
								if (define.indexOf("=FUNCTION(") > 0) {
									inputName = define.substring(0, define
											.indexOf("=FUNCTION("));
								}
								tValue = eval(define.substring(define
										.indexOf("FUNCTION(") + 9,
										define.length - 1));
							} catch (e) {
							}
						} else {
							if (define.indexOf("=") > 0) {
								inputName = define.substring(0, define
										.indexOf("="));
								tValue = this
										.getFieldValue(define.substring(define
												.indexOf("=") + 1,
												define.length));
								if (tValue == null) {
									try {
										tValue = eval(define.substring(define
												.indexOf("=") + 1,
												define.length));
									} catch (e) {
									}
								}
							} else {
								inputName = define;
								tValue = this.getFieldValue(define);
								if (tValue == null) {
									try {
										tValue = eval(define);
									} catch (e) {
									}
								}
							}
						}
					}
				}
				if (tValue == null) {
					tValue = "";
				}
				oneDatas[i] = tValue;
				oneNames[i] = inputName;
			}
			this.inDatas[k] = oneDatas;
			this.inNames[k] = oneNames;
			this.requestCount[k] = count;
		}
		return true;
	};
	this.setRequest = function() {
		var request, tName, aName, tStructure, tTable, tRecord, records, record, loXML, loRoot, loNode;
		for ( var k = 0; k < this.config.cDatas.length; k++) {
			if (this.config.cDatas[k]["Action"] == "rfc") {
				request = this.addRequest(this.config.cDatas[k]["ID"],
						this.config.cDatas[k]["Action"],
						this.config.cDatas[k]["SQL"]);
				var parameters = this.addParameters(request);
				for ( var i = 0; i < this.inDatas[k].length; i++) {
					tName = this.inNames[k][i];
					if (tName.indexOf(".") != -1) {
						aName = tName.split(".");
						tStructure = this.addStructure(parameters, aName[0]);
						this.addField(tStructure, aName[1], this.inDatas[k][i]);
					} else {
						if (tName.indexOf("->") != -1) {
							aName = tName.split("->");
							tTable = this.addTable(parameters, aName[0]);
							records = tTable.selectNodes("record");
							if (typeof (this.inDatas[k][i]) == "object") {
								for ( var j = 0; j < this.inDatas[k][i].length; j++) {
									if (records[j] == null) {
										records[j] = this.addElement(tTable,
												"record");
									}
									this.addField(records[j], aName[1],
											this.inDatas[k][i][j]);
								}
							} else {
								if (records == null || records.length == 0) {
									record = this.addElement(tTable, "record");
								} else {
									record = records[0];
								}
								this.addField(record, aName[1],
										this.inDatas[k][i]);
							}
						} else {
							if (tName.indexOf("<-XML") != -1) {
								tName = tName.substring(0, tName
										.indexOf("<-XML"));
								tTable = this.addTable(parameters, tName);
								loXML = this.getXMLDocument();
								loXML.loadXML(this.inDatas[k][i]);
								loRoot = loXML.firstChild;
								for ( var j = 0; j < loRoot.childNodes.length; j++) {
									if (!loRoot.childNodes[j].hasChildNodes()) {
										continue;
									}
									tRecord = this.addElement(tTable, "record");
									for ( var l = 0; l < loRoot.childNodes[j].childNodes.length; l++) {
										loNode = loRoot.childNodes[j].childNodes[l];
										this.addField(tRecord, loNode.nodeName,
												loNode.text);
									}
								}
							} else {
								this.addField(parameters, tName,
										this.inDatas[k][i]);
							}
						}
					}
				}
			} else {
				request = this.addRequest(this.config.cDatas[k]["ID"],
						this.config.cDatas[k]["Action"],
						this.config.cDatas[k]["SQL"]);
				if (this.requestCount[k] == -1) {
					var parameters = this.addParameters(request);
					for ( var i = 0; i < this.inDatas[k].length; i++) {
						this.addParameter(parameters, this.inDatas[k][i]);
					}
				} else {
					for ( var j = 0; j < this.requestCount[k]; j++) {
						var parameters = this.addParameters(request);
						for ( var i = 0; i < this.inDatas[k].length; i++) {
							if (typeof (this.inDatas[k][i]) == "object") {
								this.addParameter(parameters,
										this.inDatas[k][i][j]);
							} else {
								this.addParameter(parameters,
										this.inDatas[k][i]);
							}
						}
					}
				}
			}
		}
		return true;
	};
	this.execute = function() {
		this.getParameterValue();
		this.setRequest();
		var db = this;
		this.sendData(this.config.cAsync, "db.setOutput");
		if (this.config.cAsync != true) {
			var sizer = new DBSizer(this);
			if (sizer.stopToSizer) {
				sizer.selectRecord();
				return null;
			}
			return this.setOutput(this);
		}
	};
	this.setOutput = function(db) {
		if (db == null) {
			db = this;
		}
		var node, tName, tValue, response, oneReturn, status, type, loXML, loRoot, loLine, lsXML, tTableName;
		var aReturn = new Array();
		for ( var n = 0; n < db.config.cDatas.length; n++) {
			response = db.getResponseNodeForID(db.config.cDatas[n]["ID"]);
			if (response == null) {
				continue;
			}
			type = response.getAttribute("type");
			var nodes = response.selectNodes("response");
			if (nodes == null || nodes.length == 0) {
				aReturn.push(new Array());
				continue;
			}
			oneReturn = new Array();
			for ( var i = 0; i < nodes.length; i++) {
				status = nodes[i].getAttribute("status");
				oneReturn[i] = new Object();
				oneReturn[i].type = type;
				oneReturn[i].status = (status == "success") ? true : false;
				if (status != "success") {
					continue;
				}
				if (type == "rfc") {
					var fields = nodes[i].selectNodes("field");
					for ( var j = 0; j < fields.length; j++) {
						tName = fields[j].getAttribute("name");
						tValue = fields[j].text;
						eval("oneReturn[i].field_" + tName + "=tValue");
					}
					var structures = nodes[i].selectNodes("structure");
					for ( var j = 0; j < structures.length; j++) {
						tName = structures[j].getAttribute("name");
						tStructure = new Object;
						eval("oneReturn[i].structure_" + tName + "=tStructure");
						var fields = structures[j].selectNodes("field");
						for ( var k = 0; k < fields.length; k++) {
							tName = fields[k].getAttribute("name");
							tValue = fields[k].text;
							eval("tStructure.field_" + tName + "=tValue");
						}
					}
					var tables = nodes[i].selectNodes("table");
					for ( var t = 0; t < tables.length; t++) {
						var records = tables[t].selectNodes("record");
						if (records.length == 0) {
							continue;
						}
						tTableName = tables[t].getAttribute("name");
						tTable = new Array();
						eval("oneReturn[i].table_" + tTableName + "=tTable");
						loXML = db.getXMLDocument();
						loXML
								.loadXML('<root name="' + tTableName
										+ '"></root>');
						loRoot = loXML.firstChild;
						for ( var j = 0; j < records.length; j++) {
							tTable[j] = new Object;
							loLine = db.addElement(loRoot, "line");
							var fields = records[j].selectNodes("field");
							for ( var k = 0; k < fields.length; k++) {
								tName = fields[k].getAttribute("name");
								tValue = fields[k].text;
								eval("tTable[j].field_" + tName + "=tValue");
								db.addElement(loLine, tName, tValue);
							}
							for ( var k = 0; k < db.config.cDatas[n]["Out"].length; k++) {
								var define = db.config.cDatas[n]["Out"][k];
								if (define == "") {
									continue;
								}
								if (define.indexOf(tTableName + "->") == -1) {
									continue;
								}
								db.setFieldValue(define, fields, oneReturn[i]);
							}
						}
						eval("oneReturn[i].tableXML_" + tTableName
								+ "=loXML.xml");
					}
					for ( var k = 0; k < db.config.cDatas[n]["Out"].length; k++) {
						var define = db.config.cDatas[n]["Out"][k];
						if (define == "") {
							continue;
						}
						if (define.indexOf("->") != -1) {
							continue;
						}
						db.setFieldValue(define, null, oneReturn[i]);
					}
					continue;
				}
				oneReturn[i].avalue = new Array();
				oneReturn[i].nvalue = new Array();
				var records = nodes[i].selectNodes("record");
				if (records != null && records.length > 0) {
					for ( var j = 0; j < records.length; j++) {
						oneReturn[i].avalue[j] = new Array();
						oneReturn[i].nvalue[j] = new Array();
						var fields = records[j].selectNodes("field");
						for ( var k = 0; k < fields.length; k++) {
							tName = fields[k].getAttribute("name");
							tValue = fields[k].text;
							oneReturn[i].avalue[j].push(tValue);
							oneReturn[i].nvalue[j][tName] = tValue;
						}
						for ( var k = 0; k < db.config.cDatas[n]["Out"].length; k++) {
							if (db.config.cDatas[n]["Out"][k] == "") {
								continue;
							}
							db.setFieldValue(db.config.cDatas[n]["Out"][k],
									fields, oneReturn[i]);
						}
					}
				} else {
					for ( var k = 0; k < db.config.cDatas[n]["Out"].length; k++) {
						if (db.config.cDatas[n]["Out"][k] == "") {
							continue;
						}
						db.setFieldValue(db.config.cDatas[n]["Out"][k], null,
								oneReturn[i]);
					}
				}
			}
			aReturn.push(oneReturn);
		}
		return aReturn;
	};
	this.setFieldValue = function(define, fields, obj) {
		var tName, tValue, fieldName, tArray, temp, list, count, rowindex, cindex;
		var status = obj.status;
		if (define == null) {
			return;
		}
		if (define.indexOf("LIST(") == 0) {
			temp = define.substring(5, define.length - 1);
			return this.setListFieldValue(temp, fields, obj);
		} else {
			if (define.indexOf("FLIST(") == 0) {
				temp = define.substring(6, define.length - 1);
				tName = temp.substring(0, temp.lastIndexOf(","));
				fieldName = temp.substring(temp.lastIndexOf(",") + 1,
						temp.length);
				tArray = fieldName.split("|");
				try {
					list = eval(tName);
					if (list == null) {
						return false;
					}
					count = list.GetRowCount();
					list.AppendRow();
					rowindex = count + 1;
					count = list.GetColCount();
					for ( var i = 0; i < tArray.length; i++) {
						tName = tArray[i].substring(tArray[i].indexOf("=") + 1,
								tArray[i].length);
						cindex = tArray[i].substring(0, tArray[i].indexOf("=")) - 0;
						tValue = this.getFieldTextByName(fields, tName, obj);
						if (tValue != null && cindex < count) {
							list.SetCellValue(rowindex, cindex, tValue);
						}
					}
				} catch (e) {
				}
			} else {
				if (define.indexOf("FUNCTION(") == 0) {
					try {
						temp = define.substring(9, define.length - 1);
						tArray = temp.split(",");
						temp = tArray[0] + "(";
						for ( var i = 1; i < tArray.length; i++) {
							tValue = this.getFieldTextByName(fields, tArray[i],
									obj);
							if (i == 1) {
								temp += '"'
										+ tValue.replace(/\"/g, '\\"').replace(
												/\\/g, "\\\\") + '"';
							} else {
								temp += ',"'
										+ tValue.replace(/\"/g, '\\"').replace(
												/\\/g, "\\\\") + '"';
							}
						}
						if (tArray.length > 1) {
							temp += ",this";
						} else {
							temp += "this";
						}
						temp += "," + status + ")";
						return eval(temp);
					} catch (e) {
					}
				} else {
					tName = define;
					fieldName = define;
					if (define.indexOf("=") != -1) {
						fieldName = define.substring(0, define.indexOf("="));
						tName = define.substring(define.indexOf("=") + 1,
								define.length);
					}
					tValue = this.getFieldTextByName(fields, tName, obj);
					if (tValue != null) {
						return this.setFormField(fieldName, tValue);
					}
				}
			}
		}
	};
	this.getFieldTextByName = function(fields, name, obj) {
		if (name == null || name == "") {
			return null;
		}
		if (name.indexOf("->") != -1 || obj.type != "rfc") {
			if (fields == null || fields.length == null) {
				return null;
			}
			if (name.indexOf("->") != -1) {
				name = name.substring(name.indexOf("->") + 2, name.length);
			}
			for ( var k = 0; k < fields.length; k++) {
				if (fields[k].getAttribute("name") != null
						&& fields[k].getAttribute("name").toLowerCase() == name
								.toLowerCase()) {
					return fields[k].text;
				}
			}
		} else {
			if (name.indexOf("<-XML") != -1) {
				var tableName = name.substring(0, name.indexOf("<-XML"));
				return eval("obj.tableXML_" + tableName);
			} else {
				if (name.indexOf(".") != -1) {
					var aName = name.split(".");
					return eval("obj.structure_" + aName[0] + ".field_"
							+ aName[1]);
				} else {
					return eval("obj.field_" + name);
				}
			}
		}
		return null;
	};
	this.setFormField = function(name, value) {
		try {
			var field = eval("document.all." + name);
			if (typeof (field) == "object") {
				if (typeof (field.length) != "undefined") {
					for ( var n = 0; n < field.length; n++) {
						this.setFormFieldObj(field[n], value);
					}
					value = tArray.join(";");
				} else {
					this.setFormFieldObj(field, value);
				}
			}
		} catch (e) {
		}
		return true;
	};
	this.setFormFieldObj = function(field, value) {
		sValue = ";" + value + ";";
		switch (field.type) {
		case "radio":
			if (sValue.indexOf(";" + field.value + ";") != -1) {
				field.checked = true;
			} else {
				return;
			}
			break;
		case "checkbox":
			if (sValue.indexOf(";" + field.value + ";") != -1) {
				field.checked = true;
			} else {
				field.checked = false;
			}
			break;
		case "select-one":
		case "select-multiple":
			for ( var j = 0; j < field.options.length; j++) {
				if (sValue.indexOf(";" + field.options[j].value + ";") != -1
						|| sValue.indexOf(";" + field.options[j].text + ";") != -1) {
					field.options[j].selected = true;
				} else {
					field.options[j].selected = false;
				}
			}
			break;
		case "hidden":
		case "text":
		case "textarea":
			field.value = value;
			break;
		}
		if (typeof field.onclick == "function") {
			field.onclick();
		}
		if (typeof field.onchange == "function") {
			field.onchange();
		}
		return true;
	};
	this.setListFieldValue = function(define, fields, obj) {
		var one, index, cindex, tValue;
		var tArray = define.split(",");
		var tName = tArray[0];
		var row = tArray[1];
		var column = tArray[2];
		var sFields = tArray[3].split("|");
		try {
			var field = eval("document.all." + tName);
			if (field == null) {
				return false;
			}
			if (field.value != "") {
				tArray = field.value.split(row);
				one = new Array();
				index = tArray.length;
				for ( var i = 0, n = tArray[0].split(column).length; i < n; i++) {
					one[i] = "";
				}
				for ( var i = 0; i < sFields.length; i++) {
					tName = sFields[i].substring(sFields[i].indexOf("=") + 1,
							sFields[i].length);
					cindex = sFields[i].substring(0, sFields[i].indexOf("=")) - 0;
					tValue = this.getFieldTextByName(fields, tName, obj);
					if (cindex < one.length && tValue != null) {
						one[cindex] = tValue;
					}
				}
				tArray[index] = one.join(column);
				field.value = tArray.join(row);
			} else {
				one = new Array();
				for ( var i = 0; i < sFields.length; i++) {
					tName = sFields[i].substring(sFields[i].indexOf("=") + 1,
							sFields[i].length);
					cindex = sFields[i].substring(0, sFields[i].indexOf("=")) - 0;
					tValue = this.getFieldTextByName(fields, tName, obj);
					if (tValue != null) {
						one[cindex] = tValue;
					}
					for ( var j = 0; j < cindex; j++) {
						if (one[j] == null) {
							one[j] = "";
						}
					}
				}
				field.value = one.join(column);
			}
			if (typeof field.onclick == "function") {
				field.onclick();
			}
			if (typeof field.onchange == "function") {
				field.onchange();
			}
		} catch (e) {
		}
		return true;
	};
}
function DBQuery(config) {
	this.config = config;
	this.stopToQuery = (config.cQuery != null && config.cQuery != "") ? true
			: false;
	this.query = new Array();
	this.getQueryHTML = function() {
		var loBuf = new StringBuffer();
		loBuf.append('<div id="ID_QueryDiv" style="width:100%;padding:20px;">');
		loBuf
				.append('<table border="0" cellspacing="0" cellpadding="5" class="BorderNone">');
		var laQuery = config.cQuery.split("~~");
		for ( var i = 0; i < laQuery.length; i++) {
			if (laQuery[i] == "") {
				continue;
			}
			var laValue = laQuery[i].split("``");
			this.query.push(laValue);
			loBuf.append('<tr><td style="text-align:left;width:20%;">');
			loBuf.append(laValue[0].trim() + (laValue[5] == "1" ? " *" : ""));
			loBuf.append('</td><td style="text-align:left;width:80%;">');
			loBuf.append(this.getInputHTML(laValue));
			loBuf.append("</td></tr>");
		}
		loBuf.append("</table>");
		loBuf.append("</div>");
		var lsHTML = loBuf.toString();
		loBuf = null;
		return lsHTML;
	};
	this.getInputHTML = function(paValue) {
		var lsID = "_" + paValue[1];
		var lsType = paValue[2];
		var lsContent = paValue[3];
		var lsDefault = paValue[4];
		if (lsDefault.indexOf("FUNCTION(") != -1) {
			try {
				lsDefault = eval(lsDefault.substring(lsDefault
						.indexOf("FUNCTION(") + 9, lsDefault.length - 1));
			} catch (e) {
				lsDefault = lsDefault.substring(
						lsDefault.indexOf("FUNCTION(") + 9,
						lsDefault.length - 1);
			}
		}
		lsDefault = lsDefault.replace(/\"/g, "&quot;");
		var lsOne = "";
		if (lsType == "0" || lsType == "1") {
			lsOne += '<input id="' + lsID + '" name="' + lsID
					+ '" type="text" class="TextBox" value="' + lsDefault
					+ '" />';
		} else {
			if (lsType == "2" || lsType == "3") {
				lsOne += '<input id="' + lsID + '" name="' + lsID
						+ '" type="text" class="'
						+ (lsType == "2" ? "Date" : "Time") + '" value="'
						+ lsDefault + '" onfocus="this.blur();Display'
						+ (lsType == "2" ? "Date" : "Time") + '(this);" />';
			} else {
				if (lsType == "4" || lsType == "5" || lsType == "6") {
					if (lsType == "5") {
						lsOne += '<select id="' + lsID + '" name="' + lsID
								+ '" class="AutoSize">';
					}
					var laItem = lsContent.replace(/\"/g, "&quot;").split(";");
					var laDefault = lsDefault.split(";");
					var lsValue, lsText;
					for ( var j = 0; j < laItem.length; j++) {
						if (laItem[j].indexOf("|") == -1) {
							lsValue = laItem[j];
							lsText = laItem[j];
						} else {
							lsValue = laItem[j].substring(laItem[j]
									.indexOf("|") + 1, laItem[j].length);
							lsText = laItem[j].substring(0, laItem[j]
									.indexOf("|"));
						}
						if (lsType == "4" || lsType == "6") {
							lsOne += '<input name="'
									+ lsID
									+ '" id="'
									+ lsID
									+ "_"
									+ j
									+ '" value="'
									+ lsValue
									+ '" type="'
									+ ((lsType == "4") ? "radio" : "checkbox")
									+ '" class="'
									+ ((lsType == "4") ? "Radio" : "CheckBox")
									+ '"'
									+ (laDefault.indexOf(lsValue) != -1 ? " checked"
											: "") + ' /><label for="' + lsID
									+ "_" + j + '">' + lsText
									+ "</label>&nbsp;";
						} else {
							if (lsType == "5") {
								lsOne += '<option value="'
										+ lsValue
										+ '"'
										+ (laDefault.indexOf(lsValue) != -1 ? " selected"
												: "") + " />" + lsText;
							}
						}
					}
					if (lsType == "5") {
						lsOne += "</select>";
					}
				} else {
					if (lsType == "7") {
						lsOne = '<input id="'
								+ lsID
								+ '" name="'
								+ lsID
								+ '" type="text" class="TextBox" style="width:auto;" value="'
								+ lsDefault
								+ '" onfocus="this.blur();" onDblClick="this.value=\'\';return false;" />';
						lsOne += '<input type="button" class="DropDown" onclick="'
								+ lsContent.replace(/\"/g, "&quot;")
								+ ';return false;" />';
					} else {
						if (lsType == "8") {
							var lsText = "", lsValue = "";
							if (lsDefault != "") {
								var laDefault = lsDefault.split(";");
								var laText = new Array();
								var laValue = new Array();
								for ( var i = 0; i < laDefault.length; i++) {
									if (laDefault[i].indexOf("|") != -1) {
										laText.push(laDefault[i].substring(0,
												laDefault[i].indexOf("|")));
										laValue.push(laDefault[i].substring(
												laDefault[i].indexOf("|") + 1,
												laDefault[i].length));
									} else {
										laText.push(laDefault[i]);
										laValue.push(laDefault[i]);
									}
								}
								lsText = laText.join(";");
								lsValue = laValue.join(";");
							}
							lsOne += '<input id="' + lsID + '_Text" name="'
									+ lsID + '_T" value="' + lsText
									+ '" /><input id="' + lsID + '" name="'
									+ lsID + '" value="' + lsValue + '" />';
							var loXmlS = new XMLDOM("LoadString", lsContent);
							var loRootS = loXmlS.RootElement;
							var loBufCB = new StringBuffer();
							loBufCB
									.append('<SPAN id="ID_QueryCB" style="display:none;"');
							loBufCB.append(' TextField="').append(lsID).append(
									'_T" ValueField="').append(lsID)
									.append('"');
							if ($null(loRootS.getAttribute("Width"))) {
								loBufCB.append(' Width="100%"');
							}
							for ( var i = 0; i < loRootS.attributes.length; i++) {
								var loAttr = loRootS.attributes[i];
								var lsAttrName = loAttr.name;
								var lsAttrValue = loAttr.value;
								lsAttrValue = (lsAttrName == "Width" && lsAttrValue == "") ? "100%"
										: lsAttrValue;
								loBufCB.append(" ").append(lsAttrName).append(
										'="').append(lsAttrValue).append('"');
							}
							loBufCB.append(">");
							for ( var i = 0; i < loRootS.childNodes.length; i++) {
								loBufCB.append(loRootS.childNodes[i].xml);
							}
							loBufCB.append("</SPAN>");
							lsOne += loBufCB.toString();
							loBufCB = null;
							loXmlS.Delete();
							loXmlS = null;
						}
					}
				}
			}
		}
		return lsOne;
	};
	this.showWin = function() {
		var config = this.config;
		var lsID = this.config.cID;
		var laInputField = this.query;
		var loWin = new Win();
		loWin.config = this.config;
		this.config.win = loWin;
		loWin.Name = "_DBQuery_" + lsID;
		loWin.KeyPressFun = function() {
			var licode = window.event.keyCode;
			if (licode != "13") {
				return;
			}
			$Id("_DBOK_" + lsID).onclick();
			return false;
		};
		loWin.Title = sGetLang("P_VIEW_INPUTSEARCH");
		loWin.Width = "70%";
		loWin.Height = "60%";
		loWin.BodyHTML = this.getQueryHTML();
		var loBuf = new StringBuffer();
		loBuf.append('<span style="width:10px;"></span>');
		loBuf.append('<span id="_DBOK_' + lsID + '" class="Win_Btn">').append(
				sGetLang("P_WIN_OK")).append("</span>");
		loBuf.append('<span style="width:10px;"></span>');
		loBuf.append('<span id="_DBCancel_' + lsID + '" class="Win_Btn">')
				.append(sGetLang("P_WIN_CANCEL")).append("</span>");
		loBuf.append('<span style="width:20px;"></span>');
		loWin.FootHTML = loBuf.toString();
		loBuf = null;
		loWin.Init();
		var laElem = $Name("ID_QueryCB");
		for ( var i = 0; i < laElem.length; i++) {
			bInitComboBox(laElem[i]);
		}
		$Id("_DBOK_" + lsID).onclick = function() {
			var laNullField = new Array();
			var laNumField = new Array();
			for ( var i = 0; i < laInputField.length; i++) {
				if (laInputField[i][5] == "1") {
					laNullField.push("_" + laInputField[i][1]);
				}
				if (laInputField[i][2] == "1") {
					laNumField.push("_" + laInputField[i][1]);
				}
			}
			if (laNullField.length > 0) {
				if (bValidNull(laNullField.join(";")) == false) {
					return;
				}
			}
			if (laNumField.length > 0) {
				if (bValidNumber(laNumField.join(";")) == false) {
					return;
				}
			}
			var db = new DBManager(config);
			var laValue = db.execute();
			loWin.Delete();
		};
		$Id("_DBCancel_" + lsID).onclick = function() {
			loWin.Delete();
		};
	};
}
function DBSizer(db) {
	this.db = db;
	this.config = db.config;
	this.stopToSizer = (this.config.cSizer != null && this.config.cSizer != "") ? true
			: false;
	this.sizer = new Array();
	this.sizerHTML = new Object();
	this.sizerFields = new Array();
	this.sizerFuns = new Array();
	this.isRFC = (this.config.cDatas[0]["Action"] == "rfc") ? true : false;
	this.getSizerHTML = function() {
		var laSizer = this.config.cSizer.split("~~");
		var loTable, laField, laFun, liIndex;
		for ( var i = 0; i < laSizer.length; i++) {
			if (laSizer[i] == "") {
				continue;
			}
			var laValue = laSizer[i].split("``");
			var liIndex = this.sizer.indexOf(laValue[0]);
			if (liIndex == -1) {
				this.sizer.push(laValue[0]);
				loTable = new StringBuffer();
				loTable
						.append('<table border="0" cellspacing="0" cellpadding="3">');
				loTable
						.append("<tr><td style='width:20' style=\"background-color:#EEEEEE\">");
				loTable.append("<input type=checkbox id='_DBSELECTALL_"
						+ (this.sizer.length - 1) + "' value='"
						+ (this.sizer.length - 1) + "' class='checkbox'/>");
				loTable.append("</td>");
				this.sizerHTML[laValue[0]] = loTable;
				var laField = new Array();
				laField.push(laValue[2]);
				this.sizerFields.push(laField);
				laFun = new Array();
				laFun.push(laValue[4]);
				this.sizerFuns.push(laFun);
			} else {
				loTable = this.sizerHTML[laValue[0]];
				this.sizerFields[liIndex].push(laValue[2]);
				this.sizerFuns[liIndex].push(laValue[4]);
			}
			loTable.append('<td style="width:' + laValue[3]
					+ '%;" style="background-color:#EEEEEE">');
			loTable.append(laValue[1]);
			loTable.append("</td>");
		}
		var loBuf = new StringBuffer();
		loBuf.append('<div id="ID_QueryDiv" style="width:100%;padding:10px;">');
		if (this.sizer.length > 1) {
			loBuf
					.append('<div id="ID_PageLabel" class="PageLabel" style="height:500px;">');
		}
		var loXML, laNode, loNode, lsValue;
		for ( var i = 0; i < this.sizer.length; i++) {
			loTable = this.sizerHTML[this.sizer[i]];
			loTable.append("</tr>");
			laField = this.sizerFields[i];
			laFun = this.sizerFuns[i];
			if (this.isRFC) {
				loXML = db.responseXML.selectSingleNode("//table[@name='"
						+ this.sizer[i] + "']");
				if (loXML == null) {
					continue;
				}
				laNode = loXML.selectNodes("record");
			} else {
				loXML = db.getResponseNodeForID(this.sizer[i]);
				if (loXML == null) {
					continue;
				}
				laNode = loXML.selectNodes("response/record");
			}
			for ( var j = 0; j < laNode.length; j++) {
				loTable.append("<tr>");
				loTable.append("<td><input type=checkbox name='_DBSELECT_" + i
						+ "' value='" + i + "_" + j
						+ "' class='checkbox'/></td>");
				for ( var k = 0; k < laField.length; k++) {
					loNode = laNode[j].selectSingleNode("field[@name='"
							+ laField[k] + "']");
					if (loNode != null) {
						lsValue = loNode.text;
						if (laFun[k] != null && laFun[k] != "") {
							try {
								lsValue = eval(laFun[k] + '("'
										+ lsValue.replace(/\"/g, '\\"')
										+ '",laNode[j])');
							} catch (e) {
							}
						}
						loTable.append("<td>" + lsValue + "</td>");
					} else {
						loTable.append("<td>&nbsp;</td>");
					}
				}
				loTable.append("</tr>");
			}
			loTable.append("</table>");
			if (this.sizer.length > 1) {
				var lsLabel = this.sizer[i];
				for ( var j = 0; j < this.config.cSQL.length; j++) {
					var laValue = this.config.cSQL[j].split("``");
					if (laValue[0] == lsLabel) {
						lsLabel = laValue[laValue.length - 1];
						break;
					}
				}
				loBuf
						.append('<span id="ID_PageContent" style="display:none;" Label="'
								+ lsLabel + '" />');
				loBuf.append(loTable.toString());
				loBuf.append("</span>");
			} else {
				loBuf.append(loTable.toString());
			}
		}
		if (this.sizer.length > 1) {
			loBuf.append("</div>");
		}
		loBuf.append("</div>");
		var lsHTML = loBuf.toString();
		loBuf = null;
		return lsHTML;
	};
	this.selectRecord = function() {
		var sizerObj = this;
		var db = this.db;
		var lsID = db.config.cID;
		var loWin = new Win();
		loWin.db = this.db;
		this.db.win = loWin;
		loWin.Name = "_DBSizer_" + lsID;
		loWin.KeyPressFun = function() {
			var licode = window.event.keyCode;
			if (licode != "13") {
				return;
			}
			$Id("_DBOK_" + lsID).onclick();
			return false;
		};
		loWin.Title = sGetLang("P_VIEW_SEARCHRESULT");
		loWin.Width = "70%";
		loWin.Height = "60%";
		loWin.BodyHTML = this.getSizerHTML();
		var loBuf = new StringBuffer();
		loBuf.append('<span style="width:10px;"></span>');
		loBuf.append('<span id="_DBOK_' + lsID + '" class="Win_Btn">').append(
				sGetLang("P_WIN_OK")).append("</span>");
		loBuf.append('<span style="width:10px;"></span>');
		loBuf.append('<span id="_DBCancel_' + lsID + '" class="Win_Btn">')
				.append(sGetLang("P_WIN_CANCEL")).append("</span>");
		loBuf.append('<span style="width:20px;"></span>');
		loWin.FootHTML = loBuf.toString();
		loBuf = null;
		loWin.Init();
		if (this.sizer.length > 1) {
			bInitPageLabel();
		}
		for ( var i = 0; i < this.sizer.length; i++) {
			var loButton = $Id("_DBSELECTALL_" + i);
			if (loButton == null) {
				continue;
			}
			loButton.onclick = function() {
				var laCheck = $Name("_DBSELECT_" + this.value);
				for ( var j = 0; j < laCheck.length; j++) {
					laCheck[j].checked = this.checked;
				}
			};
		}
		$Id("_DBOK_" + lsID).onclick = function() {
			var laValue = new Array();
			for ( var i = 0; i < sizerObj.sizer.length; i++) {
				var laCheck = $Name("_DBSELECT_" + i);
				for ( var j = 0; j < laCheck.length; j++) {
					if (laCheck[j].checked) {
						laValue.push(laCheck[j].value);
					}
				}
			}
			if (laValue.length == 0) {
				alert(sGetLang("P_NOSELECT"));
				return;
			}
			var loXML, laNode, loParent;
			for ( var i = 0; i < sizerObj.sizer.length; i++) {
				if (sizerObj.isRFC) {
					loXML = db.responseXML.selectSingleNode("//table[@name='"
							+ sizerObj.sizer[i] + "']");
					if (loXML == null) {
						continue;
					}
					laNode = loXML.selectNodes("record");
				} else {
					loXML = db.getResponseNodeForID(sizerObj.sizer[i]);
					if (loXML == null) {
						continue;
					}
					laNode = loXML.selectNodes("response/record");
				}
				for ( var j = laNode.length - 1; j >= 0; j--) {
					if (laValue.indexOf(i + "_" + j) == -1) {
						loParent = laNode[j].parentNode;
						loParent.removeChild(laNode[j]);
						if (loParent.getAttribute("status") == "success"
								&& loParent.childNodes.length == 0) {
							loParent.setAttribute("status", "failure");
						}
					}
				}
			}
			if (db.readyFunction != null && db.readyFunction != "") {
				eval(db.readyFunction + "(db)");
			} else {
				db.setOutput(db);
			}
			loWin.Delete();
		};
		$Id("_DBCancel_" + lsID).onclick = function() {
			loWin.Delete();
		};
	};
}
function DBExecute(id) {
	var config = new DBConfig(id);
	var query = new DBQuery(config);
	if (query.stopToQuery) {
		query.showWin();
	} else {
		var db = new DBManager(config);
		var laValue = db.execute();
		return laValue;
	}
}
function DBExcute(id) {
	return DBExecute(id);
}
function downloadFile(files, path) {
	if (files == null || path == null) {
		return null;
	}
	var lsURL = gsOAURL + "zDB.nsf/agtDownloadFile?openagent&Path="
			+ encodeURIComponent(path);
	var xmlhttp = oGetXmlHttpRequest();
	if (xmlhttp == null) {
		return;
	}
	xmlhttp.open("POST", lsURL, false);
	xmlhttp.send(escape(files.join(";")));
	var loXML = xmlhttp.responseXML;
	if (loXML == null) {
		return null;
	}
	var laNode = loXML.selectNodes("/root/file");
	var laFile = new Array();
	for ( var i = 0; i < laNode.length; i++) {
		if (laNode[i].text != null && laNode[i].text != "") {
			laFile.push(laNode[i].text);
		}
	}
	return laFile;
}
function StringToUnicode(source) {
	if (source == null) {
		return null;
	}
	var result = "";
	for ( var i = 0; i < source.length; i++) {
		var liCode = source.charCodeAt(i);
		if (liCode < 16) {
			result += "0" + liCode.toString(16).toUpperCase();
		} else {
			if (liCode < 128) {
				result += liCode.toString(16).toUpperCase();
			} else {
				execScript("hexasc = hex(asc('" + source.charAt(i) + "'))",
						"vbscript");
				result += hexasc;
			}
		}
	}
	return result;
}
function UnicodeToString(source) {
	if (source == null) {
		return null;
	}
	var result = "", part1 = "", part2 = "", num;
	for ( var i = 0; i < source.length - 1; i++) {
		part1 = source.substring(i, i + 2);
		num = parseInt(part1, 16);
		if (part2 != "") {
			execScript("chrfromasc = chr('&H" + part2 + part1 + "')",
					"vbscript");
			result += chrfromasc;
			part2 = "";
		} else {
			if (num < 128) {
				execScript("chrfromasc = chr('&H" + part1 + "')", "vbscript");
				result += chrfromasc;
				part2 = "";
			} else {
				part2 = part1;
			}
		}
		i++;
	}
	return result;
}