var goUnitTree = new Object();
goUnitTree.expandImg = "./images/NodeExpand.gif";
goUnitTree.collapseImg = "./images/NodeCollapse.gif";
goUnitTree.leafImg = "./images/NodeBlank.png";
goUnitTree.blankImg = "./images/NodeBlank.png";
goUnitTree.showRole = false;
goUnitTree.roleNodes = null;
goUnitTree.xmlObject = new Array();
goUnitTree.curType = null;
goUnitTree.LTree = null;
goUnitTree.MTree = null;
goUnitTree.RTree = null;
goUnitTree.MTreePageCount = 100;
goUnitTree.xmlDOM = null;
goUnitTree.xmlIndex = 0;
goUnitTree.initArg = null;
goUnitTree.defaultRNode = null;
goUnitTree.amended=false;
goUnitTree.returnValue = null;
function StringBuffer() {
	this._laArray = new Array();
}
StringBuffer.prototype.append = function(psVar){
	this._laArray.push(psVar);
	return this;
};
StringBuffer.prototype.toString = function(){
	if (this._laArray.length == 0) 
		return "";
	return this._laArray.join("");
};
function oGetXmlHttpRequest() {
	if (window.XMLHttpRequest) {
		return new XMLHttpRequest();
	}
	else if (window.ActiveXObject) {
		try {
			return new ActiveXObject("Msxml2.XMLHTTP");
		} catch (e) {
			try {
				return new ActiveXObject("Microsoft.XMLHTTP");
			} catch (E) {
				return null;
			}
		}
	}
	else {
		return null;
	}
}
function oGetXMLDocument(){
	var xmldoc=null;
	if(window.ActiveXObject)
		xmldoc = new ActiveXObject("Microsoft.XMLDOM");
	else if(document.implementation && document.implementation.createDocument)
	 	xmldoc = document.implementation.createDocument("","",null);
	else
		alert('Your browser cannot handle this script');
	if (xmldoc!=null) xmldoc.async=false;
	return xmldoc;
}

//function PromptUnit(psTargetNames,paInitNames,paInitIDs,psTitle,pbMultiple,piSelectType,psNameType,pbShowType,psType,pbLoginStatus,psExcludeValues, callback){
//	/*
//	psTargetNames：按钮显示的名称和已选节点的目标值名称(类似邮件收件人、发件人、抄送等一起选择);	该参数设置默认双击节点自动选中到对应的目标值名称则格式必须“名称|1”,不设置则默认取第一个按钮(按钮点击切换将改变对应的分类名),该参数必须名称两个以上才生效，多个用“;”隔开。当这个有定义多个时，则paInitValues是数组与目标值名称顺序一一对应，否则为文本类型。
//	paInitNames：仅一个目标域值时，以;分割的文本值，当为多个目标域值时则为与psTargetNames一一对应的数组成员值。
//	paInitIDs：仅一个目标域值时，以;分割的文本值，当为多个目标域值时则为与psTargetNames一一对应的数组成员值。
//	piSelectType：限制用户只能选择的节点类型(默认"1")；0-都不能选择，1-都可以选择，2-仅允许选择部门，4-仅允许选择人员，8-表示仅允许选择公共群组，其他值为0/1/2/4/8相加组合。
//	psNameType：返回的组织名称格式，两个字符(默认“21”)：分别表示部门节点/人员节点；每一个字符表示，“1”-名称，“2”-不带根全路径（集团版自动带根），“3”-带根全路径。
//	pbShowType：是否显示类型选择下拉框。默认显示。
//	psType：备选值：集团|All;我的单位|MyUnit;我的部门|MyDept;我的领导|MyLeader;我的下属|MyUnderling;公共群组|PublicGroup;个人群组|PrivateGroup;上级部门|MyParentDept;在线人员|LoginUser;部门树|Dept
//						也可以是节点值（可以多值，且要求支持路径值或者id），用来选择此节点树下的值。默认为MyUnit.，当值为Dept，不显示姓氏下拉框。
//	pbLoginStatus：是否显示在线/离线状态，默认不显示
//	psExcludeValues：不允许选择节点值，多值以;分割。
//	返回值为两个类型值，laReturn["name"]/laReturn["id"]分别表示返回的名称及id列表，当psTargetNames没有定义时，laReturn["name"]/laReturn["id"]均为以;分割的字符串值，否则则是与目标值名称一一对应的数组列表。
//	*/
//	var laArg = new Array();
//	laArg["Target"] = psTargetNames!=null && psTargetNames!=""?psTargetNames:null;
//	laArg["Name"] = paInitNames;
//	laArg["ID"] = paInitIDs;
//	laArg["Title"] = psTitle;
//	laArg["Multiple"] = pbMultiple;
//	laArg["SelectType"] = (piSelectType!=null)?piSelectType:1;
//	laArg["NameType"] = (psNameType!=null && psNameType!="")?psNameType:"21";
//	laArg["ShowType"] = (pbShowType!=null && pbShowType==false)?false:true;
//	laArg["Type"] = (psType!=null && psType!="")?psType:"MyUnit";
//	laArg["LoginStatus"] = (pbLoginStatus==true)?true:false;
//	laArg["Exclude"] = psExcludeValues;
//	//var lsModal = " dialogWidth:"+800+"px;dialogHeight:"+500+"px;center:yes;status:no;help:no";
//	openUnitDialog(ctx + "/resources/pt/js/org/unit/unit.htm", psTitle, 800, 500, "laArg", laArg, function(){
//		var laValue = $.dialog.data("unitReVal");
//		if(callback != undefined){
//			callback.call(laValue);
//		};
//	});
//	//var laValue = showModalDialog("/"+getRootPath()+"/resources/pt/js/org/unit/unit.htm",laArg,lsModal);
//	//return laValue;
//}
function getRootPath(){
    //获取当前网址，如： http://localhost:8083/uimcardprj/share/meun.jsp
   // var curWwwPath=window.document.location.href;
    //获取主机地址之后的目录，如： uimcardprj/share/meun.jsp
    var pathName=window.document.location.pathname;
    //var pos=curWwwPath.indexOf(pathName);
    //获取主机地址，如： http://localhost:8083
   // var localhostPaht=curWwwPath.substring(0,pos);
    //获取带"/"的项目名，如：/uimcardprj
    var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1);
    return(projectName);
}
function UnitOnloadEvent(){
	goForm = document.forms[0];
	goUnitTree.initArg = window.dialogArguments;
	if (goUnitTree.initArg==null){
		goUnitTree.initArg={};
		goUnitTree.initArg["Target"]=null;
		goUnitTree.initArg["Name"]=null;
		goUnitTree.initArg["ID"]=null;
		goUnitTree.initArg["Multiple"]=true;
		goUnitTree.initArg["SelectType"]=1;
		goUnitTree.initArg["NameType"]="21";
		goUnitTree.initArg["ShowType"]=true;
		goUnitTree.initArg["Type"]="MyUnit";
		goUnitTree.initArg["LoginStatus"]=false;
		goUnitTree.initArg["Exclude"]=null;
	}
	goUnitTree.LTree = $("#ID_LTree");
	goUnitTree.MTree = $("#ID_MTree");
	goUnitTree.RTree = $("#ID_RTree");
	goUnitTree.xmlDOM = $.parseXML("<units/>");//oGetXMLDocument();
	goUnitTree.xmlObject["RTree"] = $(goUnitTree.xmlDOM).selectSingleNode("units");//goUnitTree.xmlDOM.createElement("units");
	if (goUnitTree.initArg["ShowType"]!=true) {
		$("#UnitType").parent().hide();
		$("#UnitType").parent().after("<div style='background:#F4F8FB;height: 40px;margin-bottom: 15px;width: 100%;'></div>");
	}
	var lsUnitType = (goUnitTree.initArg["Type"]!=null && goUnitTree.initArg["Type"]!="")?goUnitTree.initArg["Type"]:"";
	//goUnitTree.curType = sSetUnitType(lsUnitType, goUnitTree);
	sSetUnitType(lsUnitType, goUnitTree);
	//ID_MSurname.style.display=(goUnitTree.curType=="Dept")?"none":"";
	if(goUnitTree.curType=="Dept"){
		$("#ID_MSurname").hide();
	}else{
		$("#ID_MSurname").show();
	}
	var liCount = 0;
	if(goUnitTree.initArg["Target"]!=null){
		bMakeButton();
		var laNode = goUnitTree.xmlObject["RTree"].children();
		for(var j=0;j<laNode.length;j++){
			if(goUnitTree.initArg["ID"][j]==null || goUnitTree.initArg["ID"][j]=="") continue;
			var laID = goUnitTree.initArg["ID"][j].split(";");
			var laName=goUnitTree.initArg["Name"][j].split(";");
			for(var i=0;i<laID.length;i++){
				var loUnit = $(goUnitTree.xmlDOM.createElement("unit"));
				loUnit.setAttribute("type","-1");
				loUnit.setAttribute("path",laName[i]);
				loUnit.setAttribute("isLeaf","1");
				loUnit.setAttribute("name",laName[i]);
				loUnit.setAttribute("id",laID[i]);
				$(laNode[j]).appendChild(loUnit);
			}
			var lsName = $(laNode[j]).getAttribute("name");
			if (lsName!=null && lsName.lastIndexOf("(")!=-1) $(laNode[j]).setAttribute("name",lsName.substring(0,lsName.lastIndexOf("("))+"("+laNode[j].childNodes.length+")");
			liCount += $(laNode[j]).children().length;
		}
	}else if(goUnitTree.initArg["ID"]!=null && goUnitTree.initArg["ID"]!=""){
		var laID = goUnitTree.initArg["ID"].split(";");
		var laName=goUnitTree.initArg["Name"].split(";");
		for(var i=0;i<laID.length;i++){
			var loUnit = $(goUnitTree.xmlDOM.createElement("unit"));
			loUnit.setAttribute("type","-1");
			loUnit.setAttribute("path",laName[i]);
			loUnit.setAttribute("isLeaf","1");
			loUnit.setAttribute("name",laName[i]);
			loUnit.setAttribute("id",laID[i]);
			goUnitTree.xmlObject["RTree"].appendChild(loUnit);
			liCount++;
		}
	}
	$("#ID_RMemberCount").text(""+liCount);
	goUnitTree.RTree.html(sGetNodeHTML(goUnitTree.xmlObject["RTree"],-1,3,true));
	goUnitTree.showRole = $("#ID_ShowRole").attr("checked") == "checked" ? true : false;
	bCreateTree(goUnitTree.curType,false);
	//document.body.focus();
	//$(document).foucs();
}
function sSetUnitType(psType, goUnitTree){
	var lsUnitType = psType;
	$.ajax({
		url : ctx + "/org/unit/tree/type/xml",
		type : "GET",
		dataType : "xml",
		cache : false,
		async : false,
		error : function(data) {
		},
		success : function(data) {
			var laNode = $(data).selectNodes("types/type");
			var liIndex = -1;
			if (laNode!=null){
				for(var i=0;i<laNode.length;i++){
					if (lsUnitType.toLowerCase()==$(laNode[i]).getAttribute("id").toLowerCase()){
						liIndex=i;
					};
					var loEntry = new Option($(laNode[i]).text(),$(laNode[i]).getAttribute("id"));
					if (1==goForm.UnitType.options.length && goForm.UnitType.options[0].text==""){
						goForm.UnitType.options[0]=loEntry;
					}else{
						goForm.UnitType.options[goForm.UnitType.options.length]=loEntry;
					}
				}
			}
			if (liIndex==-1){
				if(lsUnitType!=""){
					var loEntry = null;
					if(lsUnitType.indexOf("|")!=-1){
						var laTemp = lsUnitType.split("|");
						loEntry = new Option(laTemp[0],laTemp[1]);
					}else{
						loEntry = new Option(lsUnitType,lsUnitType);
					}
					if (1==goForm.UnitType.options.length && goForm.UnitType.options[0].text==""){
						goForm.UnitType.options[0]=loEntry;
						goForm.UnitType.selectedIndex=0;
					}else{
						goForm.UnitType.options[goForm.UnitType.options.length]=loEntry;
						goForm.UnitType.selectedIndex=goForm.UnitType.options.length-1;
					}
				}
			}else{
				goForm.UnitType.selectedIndex=liIndex;
			}
			lsUnitType = (goForm.UnitType.selectedIndex>=0)?goForm.UnitType.options[goForm.UnitType.selectedIndex].value:"";
			goUnitTree.curType = lsUnitType;
		}
	});
}
function bMakeButton(){
	var laTarget = goUnitTree.initArg["Target"].split(";");
	var lsHtml ="<table height=100% width=100% border=0 style='table-layout:fixed;'>";
	var lsMenu = "";
	for(var i = 0;i<laTarget.length;i++){
		var lsName = (laTarget[i].indexOf("|")!=-1)?laTarget[i].substring(0,laTarget[i].indexOf("|")):laTarget[i];
		var loUnit = goUnitTree.xmlDOM.createElement("unit");
		loUnit.setAttribute("type","0");
		loUnit.setAttribute("path",lsName);
		loUnit.setAttribute("isLeaf","0");
		loUnit.setAttribute("name",lsName+"(0)");
		loUnit.setAttribute("id","R_"+i);
		goUnitTree.xmlObject["RTree"].appendChild(loUnit);
		if (i==0 || laTarget[i].indexOf("|1")!=-1) goUnitTree.defaultRNode = $(loUnit);
		if(lsName.length>4){
			var lsNewName = "";
			for(var j = 0;j<lsName.length;j+=4) lsNewName += (j==0?"":"<br>")+lsName.substr(j,4);
			lsName = lsNewName;
		}
		lsHtml +="<tr><td align=center valign=middle height=" + 100/laTarget.length + "%>";
		lsHtml +="<button class=BORDER_BUTTON type=button id='ID_Added_" + i + "' onclick='AddSelectedNode(" + i + ");' ><span>" + lsName + "</span></button>";
		lsHtml +="</td></tr>";
		lsMenu += '<A onClick="SelectedMoveTo('+i+');return false;" ID="ID_RMoveMenuItem" HREF="">'+lsName+'</A>';
	}
	lsHtml +="</table>";
	$("#ID_Button").html(lsHtml);
	$("#ID_RMoveMenu").html(lsMenu);
	$("#RMoveToSelected").show();
}
function UnitTypeChangeEvent(psUnitType){
	goUnitTree.curType=psUnitType;
	goForm.KeyWords.value="";
	for(var i=goForm.MSurname.options.length-1;i>0; i--) goForm.MSurname.options[i]=null;
	//ID_MMemberCount.innerText = "0";
	$("#ID_MMemberCount").text("0");
	bCreateTree(goUnitTree.curType);
	//document.body.focus();
	//$(document).foucs();
}
function bCreateTree(psType,pbExpandAll){
	var loNode = null;
	if(pbExpandAll){
		loNode = goUnitTree.xmlObject[psType+"_ALL"];
	}else{
		loNode = goUnitTree.xmlObject[psType+"_ALL"];
		if(loNode==null) loNode = goUnitTree.xmlObject[psType];
	}
	if(loNode==null){
		var url = ctx + "/org/unit/tree/unit/xml" + "?login="+(goUnitTree.initArg["LoginStatus"]?"1":"0")+"&all="+(pbExpandAll?"2":"1");
		//刘志群添加
		if (psType == "Unit" || psType == "UnitUser") {
			url = ctx + "/superadmin/unit/commonUnitTree/unit/tree?id=&excludeTenantId="+goUnitTree.initArg["Exclude"];
			//$("#keySearch").hide();
			//$(".unit-type").html("");
		}
		$.ajax({
				url : url,
				type : "POST",
				//url : "./MyUnit.xml",
				//type : "GET",
				data : {optionType: psType},
				dataType : "xml",
				error : function(data) {
				},
				success: function(data){					
					loNode = $(data).selectSingleNode("units");
					if(loNode==null){ 
						loNode = $(goUnitTree.xmlDOM).selectSingleNode("units");//goUnitTree.xmlDOM.createElement("units");
					}
					goUnitTree.xmlObject[psType+(pbExpandAll?"_ALL":"")]=loNode;
					
					if(loNode !=null){
						var lsHTML = sGetNodeHTML(loNode,-1,1,pbExpandAll?true:false);
						goUnitTree.LTree.html(lsHTML);
						//使用XML对象接口判断
						if (loNode.children().length==1 && pbExpandAll!=true) {
							//goUnitTree.LTree.children(0).children(1).click();
							toggle(goUnitTree.LTree.children(0).children(1)[1], true);
						}
						if(goUnitTree.amended){
							goUnitTree.amended=false;
							goUnitTree.RTree.html(sGetNodeHTML(goUnitTree.xmlObject["RTree"],-1,3,true));
						}
					}
				}
		});
	}else{
		var lsHTML = sGetNodeHTML(loNode,-1,1,pbExpandAll?true:false);
		goUnitTree.LTree.html(lsHTML);
		if (loNode.children().length==1 && pbExpandAll!=true){
			//goUnitTree.LTree.children(0).children(1).click();
			toggle(goUnitTree.LTree.children(0).children(1)[1], true);
		}
		if(goUnitTree.amended){
			goUnitTree.amended=false;
			goUnitTree.RTree.html(sGetNodeHTML(goUnitTree.xmlObject["RTree"],-1,3,true));
		}
	}
//	if(loNode==null){
//		var xmlhttp = oGetXmlHttpRequest();
//		xmlhttp.open("POST","tree/xml.action?login="+(goUnitTree.initArg["LoginStatus"]?"1":"0")+"&all="+(pbExpandAll?"2":"1")+"&t="+Math.random(), false);
//		xmlhttp.send(psType);
//		loNode = xmlhttp.responseXML.selectSingleNode("units");
//		delete xmlhttp;
//		xmlhttp = null;
//		if(loNode==null) loNode = goUnitTree.xmlDOM.createElement("units");
//		goUnitTree.xmlObject[psType+(pbExpandAll?"_ALL":"")]=loNode;
//	}
//	if(loNode==null) return false;
//	var lsHTML = sGetNodeHTML(loNode,-1,1,pbExpandAll?true:false);
//	goUnitTree.LTree.innerHTML = lsHTML;
//	if (loNode.childNodes.length==1 && pbExpandAll!=true) goUnitTree.LTree.children[0].children[1].click();
//	if(goUnitTree.amended){
//		goUnitTree.amended=false;
//		goUnitTree.RTree.innerHTML = sGetNodeHTML(goUnitTree.xmlObject["RTree"],-1,3,true);
//	}
}
// showLeafName	是否显示根名称
function sGetNodeHTML(poParent,piIndent,piTreeType,pbExpand, showLeafName){
	var loBuf = new StringBuffer();
	if (poParent==null) {
		return "";
	}
	var liIndent = piIndent+1;
	for(var i=0;i<poParent.children().length;i++){
		var loNode = $(poParent.children().get(i));
		if(loNode.getAttribute("uid")==null || loNode.getAttribute("uid")=="") loNode.setAttribute("uid",(goUnitTree.xmlIndex++)+"");
		var lsID = loNode.getAttribute("id");
		var lsType = loNode.getAttribute("type");
		var lsSex = loNode.getAttribute("sex");
		var lsLabel = loNode.getAttribute("name");
		// 显示根名称
		if(showLeafName) {
			if(lsLabel.lastIndexOf("/") != -1) {
				lsLabel = lsLabel.substring(lsLabel.lastIndexOf("/")+1,lsLabel.length);				
			}
		}
		var lsPath = loNode.getAttribute("path");
		var lsJob = (loNode.getAttribute("job")!=null && loNode.getAttribute("job")!="")?loNode.getAttribute("job"):"";
		var lsTitle = (loNode.getAttribute("title")!=null && loNode.getAttribute("title")!="")?loNode.getAttribute("title").replace(/\;/g, "\n"):(lsPath+(lsJob!=""?"("+lsJob+")":""));
		var lbCheckbox = true;
		if (goUnitTree.initArg["SelectType"]==0 || lsType=="0" || lsType=="5"){
			lbCheckbox = false;
		}else if(goUnitTree.initArg["SelectType"]==1 || piTreeType==3){
			lbCheckbox = true;
		}else if(lsType=="1" && (goUnitTree.initArg["SelectType"] & 2)!=2){
			lbCheckbox = false;
		}else if(lsType=="2" && (goUnitTree.initArg["SelectType"] & 4)!=4){
			lbCheckbox = false;
		}else if(lsType=="3" && (goUnitTree.initArg["SelectType"] & 8)!=8){
			lbCheckbox = false;
		}else if(lsType=="4" && (goUnitTree.initArg["SelectType"] & 16)!=16){
			lbCheckbox = false;
		}
		var lbShowNodeImg = (piTreeType==1 || piTreeType==3 && goUnitTree.initArg["Target"]!=null)?true:false;
		var lbChecked = loNode.getAttribute("checked")=="1"?true:false;
		if ((piTreeType==1 || piTreeType==2) && goUnitTree.xmlObject["RTree"].children().length>0){
			var laNode = goUnitTree.xmlObject["RTree"].selectNodes("//unit[@id='"+lsID+"']");
			if(laNode != null){
				for(var j=0;j<laNode.length;j++){
					if($(laNode[j]).getAttribute("type")!="-1") continue;
					$(laNode[j]).setAttribute("type",lsType);
					$(laNode[j]).setAttribute("path",lsPath);
					$(laNode[j]).setAttribute("title",lsTitle);
					goUnitTree.amended=true;//标记修正过右边树
				}
				if (laNode.length>0 && goUnitTree.initArg["Target"]==null) {
					lbChecked=true;
				}
			}
		}
		var lbOpen = pbExpand?true:false;
		var lbSelect = true;
		if(goUnitTree.initArg["Exclude"]!=null){
			if (goUnitTree.initArg["Exclude"].charAt(0)!=";") goUnitTree.initArg["Exclude"] = ";"+goUnitTree.initArg["Exclude"]+";";
			if (goUnitTree.initArg["Exclude"].indexOf(";"+lsID+";")!=-1) lbSelect=false;
		}
		loBuf.append("<div id=\"").append(lsID).append("\" uid=\"").append(loNode.getAttribute("uid")).append("\" indent=\"").append(""+liIndent).append("\" isLeaf=\"").append(loNode.getAttribute("isLeaf")).append("\" class=\"UT-item\">");
		loBuf.append("<img name=\"-indent\" width=\"").append(""+(16*liIndent)).append("\" height=16 src=\"").append(goUnitTree.blankImg).append("\"/>");
		if (loNode.getAttribute("isLeaf")!="1"){
			loBuf.append("<img name=\"-icon\" class=\"UT-icon\" isOpen=\"").append((lbOpen?"1":"0")).append("\" src=\"").append((lbOpen?goUnitTree.collapseImg:goUnitTree.expandImg)).append("\" onclick=\"toggle(this);\" style=\"display:").append((lbShowNodeImg?"":"none")).append("\"/>");
		}else{
			loBuf.append("<img name=\"-icon\" width=11 height=16 src=\"").append(goUnitTree.leafImg).append("\" style=\"display:").append((lbShowNodeImg?"":"none")).append("\"/>");
		}
		//刘志群添加
		/**if (goUnitTree.curType == "CommonUnitTree") {
			if (lsType == "1") {
				lbCheckbox = true;
			} else {
				lbCheckbox = false;
			}
		}*/
		if (lbCheckbox)
			loBuf.append("<input type=\"checkbox\" name=\"-checkbox\" class=\"UT-checkbox\" TreeType=\"").append(""+piTreeType).append("\" UnitType=\"").append(lsType).append("\" path=\"").append(lsPath).append("\" value=\"").append(lsID).append("\"").append((lbChecked?" checked":"")).append((lbSelect?"":" NotSelect=\"1\"")).append(" onclick=\"selectCheckBox(this);\" ondblclick=\"dblSelectCheckBox(this);\"></input>");
		else
			loBuf.append("&nbsp;");
		if(lsSex!=null && lsSex!="")
			loBuf.append("<img name=\"-flagimg\" class=\"UT-flagimg\" src=\"./images/User").append(lsSex).append('.gif' + "\"/>");
		loBuf.append("<span name=\"-anchor\" class=\"UT-span\" onclick=\"selectText(this);\" ondblclick=\"dblSelectText(this);\" onMouseOver=\"this.className='UT-span-mouseover';\" onMouseOut=\"this.className='UT-span';\" title=\"").append(lsTitle).append("\">");
		loBuf.append(lsLabel);
		if(lsJob!="")
			loBuf.append("<font name=\"-job\" style=\"color:gray;display:").append((((piTreeType===1||piTreeType==2) && goUnitTree.showRole==true)?"":"none")).append("\">(").append(lsJob).append(")</font>");
		loBuf.append("</span>");
		if (loNode.getAttribute("isLeaf")!="1"){
			loBuf.append("<div name=\"-cont\" class=\"UT-cont\" style=\"display:").append((lbOpen?'block':'none')).append(";\">");
			loBuf.append(sGetNodeHTML(loNode,liIndent,piTreeType,pbExpand));
			loBuf.append("</div>");
		}
		loBuf.append("</div>");
	}
	return loBuf.toString();
}
function oGetObjectByName(poObj,psName){
	var loDiv = $(poObj).parent();
//	var loDivChildren = loDiv.children();
//	for(var i=0;i<loDivChildren.length;i++){
//		if($(loDivChildren[i]).attr("name")==psName){
//			return loDivChildren[i];
//		}
//	}
	//alert($($("*[name='" + psName + "']", loDiv)[0]));
	return $($("*[name='" + psName + "']", loDiv[0])[0]);
}
// noSetSrc 不设置图片，默认设置图片
function toggle(poObj, noSetSrc){
	var loCont = oGetObjectByName(poObj,"-cont");
	if ($(poObj).attr("isOpen")=="1"){
		$(poObj).attr("isOpen", "0");
		if(!noSetSrc){
			$(poObj).attr("src", goUnitTree.expandImg);
		}
		if(loCont!=null && loCont.length != 0) {
			//loCont.style.display="none";
			loCont.hide();
		}
	}else{
		$(poObj).attr("isOpen", "1");
		if(!noSetSrc){
			$(poObj).attr("src", goUnitTree.collapseImg);
		}
		if($(poObj).attr("src") == "./images/NodeExpand.gif"){
			$(poObj).attr("src", goUnitTree.collapseImg);
		}
		if(loCont!=null && loCont.length != 0){
			loCont.show();
			//loCont.show();
			if (loCont.children().length==0){
				var loDiv = $(poObj).parent();
				var url = ctx + "/org/unit/tree/toggle/unit/xml?&all=0&login="+(goUnitTree.initArg["LoginStatus"]?"1":"0");
				//刘志群添加
				if (goUnitTree.curType == "Unit" || goUnitTree.curType == "UnitUser") {
					url = ctx + "/superadmin/unit/commonUnitTree/unit/tree?excludeTenantId="+goUnitTree.initArg["Exclude"];
				}
				$.ajax({
						url : url,
						type : "POST",
						//url : "./MyUnit2.xml",
						//type : "GET",
						data : {optionType: goUnitTree.curType, id:loDiv.attr("id")},
						dataType : "xml",
						error : function(data) {
						},
						success: function(data){
							var loNode = $(data).selectSingleNode("/units/unit[@id='"+loDiv.attr("id")+"']");
							if (loNode==null) return;
							var loParent = goUnitTree.xmlObject[goUnitTree.curType].selectSingleNode("//unit[@uid='"+loDiv.attr("uid")+"']");
							if (loParent==null) return;
							for(var i=0;i<loNode.children().length;i++) loParent.appendChild($(loNode.children().get(i)).cloneNode(true));
							loCont.html(sGetNodeHTML(loParent,loDiv.attr("indent")-0,1));
							if(goUnitTree.amended){
								goUnitTree.amended=false;								
								goUnitTree.RTree.html(sGetNodeHTML(goUnitTree.xmlObject["RTree"],-1,3,true));
							}
							if (goUnitTree.roleNodes!=null){
								var laNode = $("FONT", loCont);
								for (var i=0;i<laNode.length;i++){
									goUnitTree.roleNodes.push(laNode[i]);
								}
							}
						}
				});
//				var xmlhttp = oGetXmlHttpRequest();
//				xmlhttp.open("POST","unit_tree!unitXml.action?&all=0&login="+(goUnitTree.initArg["LoginStatus"]?"1":"0")+"&t="+Math.random(), false);
//				xmlhttp.send(loDiv.id);
//				var loNode = xmlhttp.responseXML.selectSingleNode("/units/unit[@id='"+loDiv.id+"']");
//				delete xmlhttp;
//				xmlhttp = null;
//				if (loNode==null) return;
//				var loParent = goUnitTree.xmlObject[goUnitTree.curType].selectSingleNode("//unit[@uid='"+loDiv.uid+"']");
//				if (loParent==null) return;
//				for(var i=0;i<loNode.childNodes.length;i++) loParent.appendChild(loNode.childNodes[i].cloneNode(true));
//				loCont.innerHTML = sGetNodeHTML(loParent,loDiv.indent-0,1);
//				if(goUnitTree.amended){
//					goUnitTree.amended=false;
//					goUnitTree.RTree.innerHTML = sGetNodeHTML(goUnitTree.xmlObject["RTree"],-1,3,true);
//				}
//				if (goUnitTree.roleNodes!=null){
//					var laNode = loCont.getElementsByTagName("FONT");
//					for (var i=0;i<laNode.length;i++) goUnitTree.roleNodes.push(laNode[i]);
//				}
			}
		}
	}
}
function MSurnameChangeEvent(psValue){
	if (psValue=="-0-"){
		//document.all.ID_MPrevPage.style.display=(goUnitTree.MTreePageCount<ID_MMemberCount.innerText-0)?"":"none";
		//document.all.ID_MNextPage.style.display=(goUnitTree.MTreePageCount<ID_MMemberCount.innerText-0)?"":"none";
		if(goUnitTree.MTreePageCount< $("#ID_MMemberCount").text()-0){
			$("#ID_MPrevPage").show();
			$("#ID_MNextPage").show();
		}else{
			$("#ID_MPrevPage").hide();
			$("#ID_MNextPage").hide();
		};
		goUnitTree.xmlObject["MTree"].setAttribute("curpage","0");
		goUnitTree.MTree.html(sGetNodeHTML(goUnitTree.xmlObject["MTree"].children(":first"),-1,2));
	}else{
		//document.all.ID_MPrevPage.style.display="none";
		//document.all.ID_MNextPage.style.display="none";
		$("#ID_MPrevPage").show();
		$("#ID_MNextPage").show();
		var loParent = $(goUnitTree.xmlDOM).selectSingleNode("units");//goUnitTree.xmlDOM.createElement("units");
		var laNode = goUnitTree.xmlObject["MTree"].selectNodes("//unit[@isLeaf='1']");
		if(laNode != null && laNode.length != 0){
			for(var i=0;i<laNode.length;i++){
				if(laNode[i].getAttribute("name").charAt(0)!=psValue) continue;
				loParent.appendChild(laNode[i].cloneNode(true));
			}
		}
		goUnitTree.MTree.html(sGetNodeHTML(loParent,-1,2));
	}
}
function MTreeCurPage(pbNext){
	var liIndex = goUnitTree.xmlObject["MTree"].getAttribute("curpage")-0;
	var liCount = goUnitTree.xmlObject["MTree"].getAttribute("pagecount")-0;
	if(pbNext){
		if(liIndex==liCount-1) return;
		liIndex++;
	}else{
		if(liIndex==0) return;
		liIndex--;
	}
	goUnitTree.xmlObject["MTree"].setAttribute("curpage",""+liIndex);
	//goUnitTree.MTree.html(sGetNodeHTML(goUnitTree.xmlObject["MTree"].children(":nth-child(" + (liIndex + 1) + ")"),-1,2));
	goUnitTree.MTree.html(sGetNodeHTML($(goUnitTree.xmlObject["MTree"].children().get(liIndex)),-1,2));
}
function bSetMTree(psID){
	for(var i=goForm.MSurname.options.length-1;i>0; i--) goForm.MSurname.options[i]=null;
	ID_MMemberCount.innerText = "0";
	goUnitTree.MTree.html("&nbsp;");
	var loNode = null;
	var url = ctx + "/org/unit/tree/leaf/unit/xml?type="+(goUnitTree.curType=="Dept"?"D":"U")+"&login="+(goUnitTree.initArg["LoginStatus"]?"1":"0");
	//刘志群添加
	if (goUnitTree.curType == "Unit" || goUnitTree.curType == "UnitUser") {
		url = ctx + "/superadmin/unit/commonUnitTree/leaf/unit/tree?type="+(goUnitTree.curType=="Unit"?"D":"U");
	}
	$.ajax({
		url : url,
		type : "POST",
		//url : "./Leaf.xml",
		//type : "GET",
		data : {optionType: goUnitTree.curType, id:psID},
		dataType : "xml",
		cache : false,
		async : false,
		error : function(data) {
		},
		success : function(data) {
			loNode = $(data).selectSingleNode("units");
		}
	});
//	var xmlhttp = oGetXmlHttpRequest();
//	xmlhttp.open("POST","unit_tree!unitXml.action?type="+(goUnitTree.curType=="Dept"?"D":"U")+"&login="+(goUnitTree.initArg["LoginStatus"]?"1":"0")+"&t="+Math.random(), false);
//	xmlhttp.send(psID);
//	var loNode = xmlhttp.responseXML.selectSingleNode("units");
//	delete xmlhttp;
//	xmlhttp = null;
	if(loNode==null) {
		return false;
	}
	var liCount = loNode.children().length;
	goUnitTree.xmlObject["MTree"] = loNode;
	//ID_MMemberCount.innerText = ""+liCount;
	$("#ID_MMemberCount").text(""+liCount);
	//document.all.ID_MPrevPage.style.display=(goUnitTree.MTreePageCount<liCount)?"":"none";
	//document.all.ID_MNextPage.style.display=(goUnitTree.MTreePageCount<liCount)?"":"none";
	if((goUnitTree.MTreePageCount<liCount)){
		$("#ID_MPrevPage").show();
		$("#ID_MNextPage").show();
	}else{
		$("#ID_MPrevPage").hide();
		$("#ID_MNextPage").hide();
	};
	var lsSurname="";
	var liPage = Math.round(liCount/goUnitTree.MTreePageCount);
	if(liPage*goUnitTree.MTreePageCount<liCount) liPage++;
//	for(var i=0;i<liPage;i++){
//		var loParent = $(goUnitTree.xmlDOM.createElement("unit"));
//		loParent.setAttribute("type","0");
//		loParent.setAttribute("path","");
//		loParent.setAttribute("isLeaf","0");
//		loParent.setAttribute("name","Page_"+i);
//		loParent.setAttribute("id","Page_"+i);
//		goUnitTree.xmlObject["MTree"].appendChild(loParent);
//		for(var j=0;j<goUnitTree.MTreePageCount;j++){
//			var liIndex = i*goUnitTree.MTreePageCount+j;
//			if(liIndex>=liCount) {
//				break;
//			}
//			var lsName = loNode.children(":first").getAttribute("name");
//			if(lsSurname.indexOf(lsName.charAt(0))==-1) {
//				lsSurname+=lsName.charAt(0);
//			}
//			loParent.appendChild(loNode.children(":first"));
//		}
//	}
//	goUnitTree.xmlObject["MTree"].setAttribute("curpage","0");
//	goUnitTree.xmlObject["MTree"].setAttribute("pagecount",""+liPage);
//	goUnitTree.MTree.html(sGetNodeHTML(goUnitTree.xmlObject["MTree"].children(":first"),-1,2));
	goUnitTree.MTree.html(sGetNodeHTML(loNode,-1,2));
	if(goUnitTree.curType!="Dept"){
		for(var i=goForm.MSurname.options.length-1;i>0;i--)goForm.MSurname.options[i]=null;
		goForm.MSurname.selectedIndex=0;
		for(var i=0;i<lsSurname.length;i++){
			var loEntry = new Option(lsSurname.charAt(i),lsSurname.charAt(i));
			goForm.MSurname.options[goForm.MSurname.options.length]=loEntry;
		}
	}
	return true;
}
function AddUnit(poObj,poParent){
	var loUnit = poParent.selectSingleNode("./unit[@id='"+$(poObj).attr("value")+"']");
	if(loUnit!=null) return;
	loUnit = $(goUnitTree.xmlDOM.createElement("unit"));
	loUnit.setAttribute("type",$(poObj).attr("UnitType"));
	loUnit.setAttribute("path",$(poObj).attr("path"));
	loUnit.setAttribute("isLeaf","1");
	var lsName = $(poObj).attr("path");
	if($(poObj).attr("UnitType")=="1"){
		if(goUnitTree.initArg["NameType"].charAt(0)=="1"){
			lsName = "/"+lsName;
			lsName = lsName.substring(lsName.lastIndexOf("/")+1,lsName.length);
		}else if(goUnitTree.initArg["NameType"].charAt(0)=="2"){
			if(lsName.indexOf("/")!=-1) lsName = lsName.substring(lsName.indexOf("/")+1,lsName.length);
		}
	}else if($(poObj).attr("UnitType")=="2"){
		if(goUnitTree.initArg["NameType"].charAt(1)=="1"){
			lsName = lsName.substring(lsName.lastIndexOf("/")+1,lsName.length);
		}else if(goUnitTree.initArg["NameType"].charAt(1)=="2"){
			if(lsName.indexOf("/")!=-1) lsName = lsName.substring(lsName.indexOf("/")+1,lsName.length);
		}
	}
	loUnit.setAttribute("name",lsName);
	loUnit.setAttribute("id",$(poObj).attr("value"));
	var loSpan = oGetObjectByName(poObj,"-anchor");
	loUnit.setAttribute("title",loSpan.attr("title").replace(/\n/g, ";"));
	poParent.appendChild(loUnit);
	var lsName = poParent.getAttribute("name");
	if (lsName!=null && lsName.lastIndexOf("(")!=-1) poParent.setAttribute("name",lsName.substring(0,lsName.lastIndexOf("("))+"("+poParent.childNodes.length+")");
}
function DelUnit(poObj,poParent){
	var loUnit = poParent.selectSingleNode("unit[@id='"+$(poObj).val()+"']");
	if(loUnit==null) return;
	poParent.removeChild(loUnit);
	var lsName = poParent.getAttribute("name");
	if (lsName!=null && lsName.lastIndexOf("(")!=-1) poParent.setAttribute("name",lsName.substring(0,lsName.lastIndexOf("("))+"("+poParent.childNodes.length+")");
}
function selectCheckBox(poObj,defaultRNode){	
	if($(poObj).attr("TreeType")==3){
		var loUnit = goUnitTree.xmlObject["RTree"].selectSingleNode("//unit[@uid='"+$(poObj).parent().attr("uid")+"']");
		if(loUnit!=null) loUnit.setAttribute("checked",poObj.checked?"1":"");
	}else{
		if($(poObj).attr("NotSelect")=="1"){
			poObj.checked=!poObj.checked;
			return;
		}
		//刘志群添加
		if (goUnitTree.initArg["Multiple"] == false) {
			goUnitTree.xmlObject["RTree"].text("");
		}
		
		if(defaultRNode==null && goUnitTree.initArg["Target"]!=null) return;
		if(poObj.checked){
			AddUnit(poObj,defaultRNode||goUnitTree.xmlObject["RTree"]);
		}else{
			DelUnit(poObj,defaultRNode||goUnitTree.xmlObject["RTree"]);
		}
		
		if(goUnitTree.initArg["Target"]==null){
			if($(poObj).attr("TreeType")==1){
				//var laNode = goUnitTree.MTree.getElementsByTagName("INPUT");
				var laNode = $("INPUT", goUnitTree.MTree);
				for (var i=0;i<laNode.length;i++){
					if($(poObj).val()==$(laNode[i]).val()){
						laNode[i].checked = poObj.checked;
						break;
					}
				}
			}
			laNode = $("INPUT", goUnitTree.LTree);
			for (var i=0;i<laNode.length;i++){
				if($(poObj).val()==$(laNode[i]).val()){
					laNode[i].checked = poObj.checked;
				} else {
					if (goUnitTree.initArg["Multiple"] == false) {
						laNode[i].checked = "";
					}
				}
			}
		}
		
		var laNode = goUnitTree.xmlObject["RTree"].selectNodes("//unit[@isLeaf='1']");
		$("#ID_RMemberCount").text((laNode==null)?0:laNode.length);
		goUnitTree.RTree.html(sGetNodeHTML(goUnitTree.xmlObject["RTree"],-1,3,true));
	}
}
function dblSelectCheckBox(poObj){
	selectCheckBox(poObj,goUnitTree.defaultRNode);
}
function selectText(poObj){
	var loDiv = $(poObj).parent();
	if (loDiv.attr("isLeaf")=="1"){
		var loCheckBox = oGetObjectByName(poObj,"-checkbox");
		if(loCheckBox!=null && loCheckBox.length != 0){
			loCheckBox.get(0).checked = !loCheckBox.get(0).checked;
			selectCheckBox(loCheckBox.get(0));
		}
	}else{
		bSetMTree(loDiv.attr("id"));
	}
}
function dblSelectText(poObj){
	if (goUnitTree.defaultRNode==null) return;
	if ($(poObj).parent().attr("isLeaf")=="1"){
		var loCheckBox = oGetObjectByName(poObj,"-checkbox");
		if(loCheckBox!=null && loCheckBox.length != 0){
			selectCheckBox(loCheckBox.get(0),goUnitTree.defaultRNode);
		}
	}
}
function SelectAll(pbSelect,poTree){
	var lsSelected = ";";
	var laNode = $("INPUT", poTree);
	for (var i=0;i<laNode.length;i++){
		if($(laNode[i]).attr("NotSelect")=="1"){
			continue;
		}
		laNode[i].checked = pbSelect;
		if(goUnitTree.initArg["Target"]!=null) continue;
		lsSelected += $(laNode[i]).val() + ";";
		if(pbSelect){
			AddUnit(laNode[i],goUnitTree.xmlObject["RTree"]);
		}else{
			DelUnit(laNode[i],goUnitTree.xmlObject["RTree"]);
		}
	}
	if(goUnitTree.initArg["Target"]==null){
		if(poTree.id="ID_LTree"){
			//laNode = goUnitTree.MTree.getElementsByTagName("INPUT");
			laNode = $("INPUT", goUnitTree.MTree);
			for (var i=0;i<laNode.length;i++){
				if(lsSelected.indexOf(";"+$(laNode[i]).val()+";")!=-1){
					laNode[i].checked = pbSelect;
				}
			}
		}else{
			laNode = $("INPUT", goUnitTree.LTree);
			for (var i=0;i<laNode.length;i++){
				if(lsSelected.indexOf(";"+$(laNode[i]).val()+";")!=-1){
					laNode[i].checked = pbSelect;
				}
			}
		}
		laNode = goUnitTree.xmlObject["RTree"].selectNodes("//unit[@isLeaf='1']");
		if(laNode != null){
			$("#ID_RMemberCount").text(""+laNode.length);
		}else{
			$("#ID_RMemberCount").text("0");
		}
		goUnitTree.RTree.html(sGetNodeHTML(goUnitTree.xmlObject["RTree"],-1,3,true));
	}
}
function ExpandAll(){
	bCreateTree(goUnitTree.curType,true);
}
function CollapseAll(){
	bCreateTree(goUnitTree.curType,false);
}
function ShowRole(pbShow){
	goUnitTree.showRole = pbShow;
	if (goUnitTree.roleNodes==null){
		goUnitTree.roleNodes = new Array();
		var laNode = $("FONT", goUnitTree.LTree);
		for (var i=0;i<laNode.length;i++){
			goUnitTree.roleNodes.push(laNode[i]);
			$(laNode[i]).css("display", pbShow?"":"none");
		}
	}else{
		for(var i=0;i<goUnitTree.roleNodes.length;i++) goUnitTree.roleNodes[i].style.display=pbShow?"":"none";
	}
	//var laNode = goUnitTree.MTree.getElementsByTagName("FONT");
	var laNode = $("FONT", goUnitTree.MTree);
	for (var i=0;i<laNode.length;i++){
		//laNode[i].style.display=pbShow?"":"none";
		$(laNode).css("display", pbShow?"":"none");
	}
}
function AddSelectedNode(piIndex){
	var loTarget = goUnitTree.xmlObject["RTree"].childNodes[piIndex];
	if(loTarget==null) return;
	var lbChanged=false;
	var laNode = $("INPUT", goUnitTree.LTree);
	for (var i=0;i<laNode.length;i++){
		if(laNode[i].checked!=true)continue;
		AddUnit(laNode[i],$(loTarget));
		laNode[i].checked=false;
		lbChanged=true;
	}
	//laNode = goUnitTree.MTree.getElementsByTagName("INPUT");
	laNode = $("INPUT", goUnitTree.MTree);
	for (var i=0;i<laNode.length;i++){
		if(laNode[i].checked!=true){
			continue;
		}
		AddUnit(laNode[i],$(loTarget));
		laNode[i].checked = false;
		lbChanged=true;
	}
	if(lbChanged){
		var laNode = goUnitTree.xmlObject["RTree"].selectNodes("//unit[@isLeaf='1']");
		$("#ID_RMemberCount").text(""+laNode.length);
		goUnitTree.RTree.html(sGetNodeHTML(goUnitTree.xmlObject["RTree"],-1,3,true));
	}
}
function SelectedDelete(){
	//var laNode = goUnitTree.RTree.getElementsByTagName("INPUT");
	var laNode = $("INPUT", goUnitTree.RTree);
	var liCount = laNode.length;
	var lsDeleted = ";";
	for (var i=0;i<laNode.length;i++){
		if(laNode[i].checked!=true)continue;
		var loUnit = goUnitTree.xmlObject["RTree"].selectSingleNode("//unit[@uid='"+$(laNode[i]).parent().attr("uid")+"']");
		if(loUnit==null) continue;
		lsDeleted += loUnit.getAttribute("id")+";";
		var loParent = loUnit.parent();
		loParent.removeChild(loUnit);
		var lsName = loParent.getAttribute("name");
		if (lsName!=null && lsName.lastIndexOf("(")!=-1) loParent.setAttribute("name",lsName.substring(0,lsName.lastIndexOf("("))+"("+loParent.childNodes.length+")");
		lbChanged=true;
		liCount--;
	}
	if(liCount<laNode.length){
		$("#ID_RMemberCount").text(""+liCount);
		goUnitTree.RTree.html(sGetNodeHTML(goUnitTree.xmlObject["RTree"],-1,3,true));
	}
	if(goUnitTree.initArg["Target"]==null){
		laNode = $("INPUT", goUnitTree.LTree);
		for (var i=0;i<laNode.length;i++){if(lsDeleted.indexOf(";"+laNode[i].value+";")!=-1)laNode[i].checked=false;}
		//laNode = goUnitTree.MTree.getElementsByTagName("INPUT");
		laNode = $("INPUT", goUnitTree.MTree);
		for (var i=0;i<laNode.length;i++){
			if(lsDeleted.indexOf(";"+$(laNode[i]).val()+";")!=-1){
				laNode[i].checked = false;
			}
		}
	}
}
function SelectedEmpty(){
	var laNode = goUnitTree.xmlObject["RTree"].selectNodes("//unit[@isLeaf='1']");
	if(laNode == null){
		return;
	}
	for (var i=0;i<laNode.length;i++) {
		$(laNode[i]).parent().removeChild($(laNode[i]));
	}
	laNode = goUnitTree.xmlObject["RTree"].children();
	for (var i=0;i<laNode.length;i++){
		var lsName = laNode[i].getAttribute("name");
		if (lsName!=null && lsName.lastIndexOf("(")!=-1) {
			$(laNode[i]).setAttribute("name",lsName.substring(0,lsName.lastIndexOf("("))+"(0)");
		}
	}
	$("#ID_RMemberCount").text("0");
	goUnitTree.RTree.html(sGetNodeHTML(goUnitTree.xmlObject["RTree"],-1,3,true));
	if(goUnitTree.initArg["Target"]==null){
		laNode = $("INPUT", goUnitTree.LTree);
		for (var i=0;i<laNode.length;i++) {
			laNode[i].checked=false;
		}
		//laNode = goUnitTree.MTree.getElementsByTagName("INPUT");
		laNode = $("INPUT", goUnitTree.MTree);
		for (var i=0;i<laNode.length;i++) {
			laNode[i].checked=false;
		}
	}
}
function SelectedMove(pbUp){
	var laNode = goUnitTree.xmlObject["RTree"].selectNodes("//unit[@checked='1']");
	if(laNode == null){
		return;
	}
	var laID = new Array();
	for (var i=0;i<laNode.length;i++) laID.push($(laNode[i]).getAttribute("uid"));
	var lbChanged=false;
	if (pbUp){
		for (var i=0;i<laID.length;i++){
			var loNode = goUnitTree.xmlObject["RTree"].selectSingleNode("//unit[@uid='"+laID[i]+"']");
			var loPrev = loNode.prev();
			if (loPrev==null || loPrev.length == 0) break;
			//var loParent = loNode.parent();
			var loCopy = loPrev.cloneNode(true);
			//loParent.replaceChild(loNode.cloneNode(true),loPrev);
			//loParent.replaceChild(loCopy,loNode);
			loPrev.replaceWith(loNode.cloneNode(true));
			loNode.replaceWith(loCopy);
			lbChanged=true;
		}
	}else{
		for (var i=laID.length-1;i>=0;i--){
			var loNode = goUnitTree.xmlObject["RTree"].selectSingleNode("//unit[@uid='"+laID[i]+"']");
			var loNext = loNode.next();
			if (loNext==null || loNext.length == 0) break;
			//var loParent = loNode.parentNode;
			var loCopy = loNext.cloneNode(true);
			//loParent.replaceChild(loNode.cloneNode(true),loNext);
			//loParent.replaceChild(loCopy,loNode);
			loNext.replaceWith(loNode.cloneNode(true));
			loNode.replaceWith(loCopy);
			lbChanged=true;
		}
	}
	if(lbChanged) {
		goUnitTree.RTree.html(sGetNodeHTML(goUnitTree.xmlObject["RTree"],-1,3,true));
	}
}
function SelectedMoveTo(piIndex){
	var loTarget = goUnitTree.xmlObject["RTree"].childNodes[piIndex];
	if(loTarget==null) return;
	var laNode = goUnitTree.xmlObject["RTree"].selectNodes("//unit[@checked='1']");
	var lbChanged=false;
	for (var i=0;i<laNode.length;i++){
		var loParent = laNode[i].parentNode;
		if(loParent.getAttribute("id")=="R_"+piIndex) continue;
		var loTest = loTarget.selectSingleNode("unit[@id='"+laNode[i].getAttribute("id")+"']");
		if (loTest!=null) continue;
		var loCopy = laNode[i].cloneNode(true);
		loCopy.setAttribute("checked","");
		loTarget.appendChild(loCopy);
		loParent.removeChild(laNode[i]);
		var lsName = loParent.getAttribute("name");
		if (lsName!=null && lsName.lastIndexOf("(")!=-1) loParent.setAttribute("name",lsName.substring(0,lsName.lastIndexOf("("))+"("+loParent.childNodes.length+")");
		lbChanged=true;
	}
	if(lbChanged){
		var lsName = loTarget.getAttribute("name");
		if (lsName!=null && lsName.lastIndexOf("(")!=-1) loTarget.setAttribute("name",lsName.substring(0,lsName.lastIndexOf("("))+"("+loTarget.childNodes.length+")");
		goUnitTree.RTree.html(sGetNodeHTML(goUnitTree.xmlObject["RTree"],-1,3,true));
	}
}

function OKClick(){
	var laReturn = new Array();
	//var laNode = goUnitTree.xmlObject["RTree"].childNodes;
	var laNode = goUnitTree.xmlObject["RTree"].children();
	if (laNode.length==0){
		laReturn["name"] = "";
		laReturn["id"] = "";
	}else if($(laNode[0]).getAttribute("type")=="0"){
		laReturn["name"] = new Array();
		laReturn["id"] = new Array();
		for(var i=0;i<laNode.length;i++){
			var laName = new Array();
			var laID = new Array();
			for(var j=0;j<$(laNode[i]).children().length;j++){
				laName.push($(laNode[i]).children(j).getAttribute("name"));
				laID.push($(laNode[i]).children(j).getAttribute("id"));
			}
			laReturn["name"].push(laName.join(";"));
			laReturn["id"].push(laID.join(";"));
		}
	}else{
		var laName = new Array();
		var laID = new Array();
		for(var i=0;i<laNode.length;i++){
			laName.push($(laNode[i]).getAttribute("name"));
			laID.push($(laNode[i]).getAttribute("id"));
		}
		laReturn["name"] = laName.join(";");
		laReturn["id"] = laID.join(";");
	}
	goUnitTree.returnValue = {};
	goUnitTree.returnValue.id = laReturn["id"];
	goUnitTree.returnValue.name = laReturn["name"];
	//$.dialog.data("unitReVal", laReturn);
	//$.dialog.close();
	//window.returnValue = laReturn;
	//window.close();
}
function bSearch(){
	var psType = goUnitTree.curType;
	var pbExpandAll = false;
	var keyWord = $("#KeyWords").val();
	$.ajax({
		url : ctx + "/org/unit/tree/search/xml" + "?login="+(goUnitTree.initArg["LoginStatus"]?"1":"0")+"&all=1",
		type : "POST",
		data : {optionType: psType, searchValue: keyWord},
		dataType : "xml",
		error : function(data) {
		},
		success: function(data){
			var loNode = $(data).selectSingleNode("units");
			if(loNode==null){ 
				loNode = $(goUnitTree.xmlDOM).selectSingleNode("units");//goUnitTree.xmlDOM.createElement("units");
			}
			goUnitTree.xmlObject[psType]=loNode;
			
			if(loNode !=null){
				var lsHTML = sGetNodeHTML(loNode,-1,1,false);
				goUnitTree.LTree.html(lsHTML);
				//使用XML对象接口判断
				if (loNode.children().length==1 && pbExpandAll!=true) {
					// goUnitTree.LTree.children(0).children(1).click();
					toggle(goUnitTree.LTree.children(0).children(1)[1], true);
				}
				if(goUnitTree.amended){
					goUnitTree.amended=false;
					goUnitTree.RTree.html(sGetNodeHTML(goUnitTree.xmlObject["RTree"],-1,3,true));
				}
				// 更新中间树
				if(keyWord != null && $.trim(keyWord) != ""){
					goUnitTree.MTree.html(sGetNodeHTML(loNode, -1, 2, false, true));
				} else{
					goUnitTree.MTree.html(sGetNodeHTML(null, -1, 2, false, true));
				}
			}
		}
	});
}