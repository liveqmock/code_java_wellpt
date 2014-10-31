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
function getXmlDom(jsonpxml) {
	var xmldom=null;
	if(window.ActiveXObject){
		 xmldom = createDocument();
		 xmldom.loadXML(jsonpxml);
	}else{
		 var domParser = new DOMParser();
		  xmldom = domParser.parseFromString(jsonpxml,'application/xml');
	}
	return xmldom;
}

function createDocument() {
    if (typeof arguments.callee.activeXString != "string") {
        var versions = ["MSXML2.DOMDocument.6.0", "MSXML2.DOMDocument.3.0", "MSXML2.DOMDocument"],
            i, len;
        for (i = 0, len = versions.length; i < len; i++) {
            try {
                var xmldom = new ActiveXObject(versions[i]);
                arguments.callee.activeXString = versions[i];
                break;
            } catch (ex) {
                //跳过
            }
        }
    }
    return new ActiveXObject(arguments.callee.activeXString);
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
		goUnitTree.initArg["ShowType"]=true;
		goUnitTree.initArg["descFlag"]=false;
		goUnitTree.initArg["Type"]="LED";
	}
	goUnitTree.LTree = $("#ID_LTree");
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
	var liCount = 0;
	if(goUnitTree.initArg["Target"]!=null){
		bMakeButton();
		var laNode = goUnitTree.xmlObject["RTree"].children();
		for(var j=0;j<laNode.length;j++){
			if(goUnitTree.initArg["ID"][j]==null || goUnitTree.initArg["ID"][j]=="") continue;
			var laID = goUnitTree.initArg["ID"][j].split(";");
			var laName=goUnitTree.initArg["Name"][j].split(";");
			var laDesc=null;
			for(var i=0;i<laID.length;i++){
				var loUnit = $(goUnitTree.xmlDOM.createElement("unit"));
				loUnit.setAttribute("type","-1");
				loUnit.setAttribute("path",laName[i]);
				loUnit.setAttribute("isLeaf","1");
				loUnit.setAttribute("name",laName[i]);
				loUnit.setAttribute("id",laID[i]);
				if(laDesc[i]!=null){
					loUnit.setAttribute("desc",laDesc[i]);
				}
				$(laNode[j]).appendChild(loUnit);
			}
			var lsName = $(laNode[j]).getAttribute("name");
			if (lsName!=null && lsName.lastIndexOf("(")!=-1) $(laNode[j]).setAttribute("name",lsName.substring(0,lsName.lastIndexOf("("))+"("+laNode[j].childNodes.length+")");
			liCount += $(laNode[j]).children().length;
		}
	}else if(goUnitTree.initArg["ID"]!=null && goUnitTree.initArg["ID"]!=""){
		var laID = goUnitTree.initArg["ID"].split(";");
		var laName=goUnitTree.initArg["Name"].split(";");
		var laDesc=null;
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
	var _this=this;
	$.ajax({
		url : ctx + "/ldx/unit/product/type/xml",
		type : "GET",
		jsonp: "jsonpCallback",//服务端用于接收callback调用的function名的参数
        dataType:'jsonp',   
		//dataType : "xml",
		cache : false,
		async : false,
		error : function(data) {
		},
		success : function(data) {
			
		 //modify by zky 改为jsonp格式传递
    	 var xmldoc = _this.getXmlDom(data.xml);
			
			var laNode = $(xmldoc).selectNodes("types/type");
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
		
			//从unit.htm移过来.
			$("#UnitType").chosen({disable_search_threshold: 10});
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
	var _this=this;
	if(pbExpandAll){
		loNode = goUnitTree.xmlObject[psType+"_ALL"];
	}else{
		loNode = goUnitTree.xmlObject[psType+"_ALL"];
		if(loNode==null) loNode = goUnitTree.xmlObject[psType];
	}
	if(loNode==null){
		var url = ctx + "/ldx/unit/product/unit/xml" + "?all="+(pbExpandAll?"2":"1");
		$.ajax({
				url : url,
				type : "GET",
				//url : "./MyUnit.xml",
				//type : "GET",
				data : {optionType: psType},
				//dataType : "xml",
				
				jsonp: "jsonpCallback",//服务端用于接收callback调用的function名的参数
		        dataType:'jsonp',   
		        
				error : function(data) {
				},
				success: function(data){			
					
					 //modify by zky 改为jsonp格式传递
					 var xmldoc = _this.getXmlDom(data.xml);
					
					loNode = $(xmldoc).selectSingleNode("units");
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
		var lsLabel = loNode.getAttribute("name");
		
		// 显示根名称
		if(showLeafName) {
			if(lsLabel.lastIndexOf("/") != -1) {
				lsLabel = lsLabel.substring(lsLabel.lastIndexOf("/")+1,lsLabel.length);				
			}
		}
		var lsPath = loNode.getAttribute("path");
		var lbCheckbox = true;
		var lbShowNodeImg = (piTreeType==1 || piTreeType==3 && goUnitTree.initArg["Target"]!=null)?true:false;
		var lbChecked = loNode.getAttribute("checked")=="1"?true:false;
		if ((piTreeType==1 || piTreeType==2) && goUnitTree.xmlObject["RTree"].children().length>0){
			var laNode = goUnitTree.xmlObject["RTree"].selectNodes("//unit[@id='"+lsID+"']");
			if(laNode != null){
				for(var j=0;j<laNode.length;j++){
					if($(laNode[j]).getAttribute("type")!="-1") continue;
					$(laNode[j]).setAttribute("type",lsType);
					$(laNode[j]).setAttribute("path",lsPath);
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
		if (lbCheckbox)
			loBuf.append("<input type=\"checkbox\" name=\"-checkbox\" class=\"UT-checkbox\" TreeType=\"").append(""+piTreeType)
			.append("\" UnitType=\"").append(lsType)
			.append("\" path=\"").append(lsLabel)
			.append("\" value=\"").append(lsID)
			.append("\"").append((lbChecked?" checked":""))
			.append((lbSelect?"":" NotSelect=\"1\""))
			.append(" onclick=\"selectCheckBox(this);\" ondblclick=\"dblSelectCheckBox(this);\"></input>");
		else
			loBuf.append("&nbsp;");
		loBuf.append("<span name=\"-anchor\" class=\"UT-span\" onclick=\"selectText(this);\" ondblclick=\"dblSelectText(this);\" onMouseOver=\"this.className='UT-span-mouseover';\" onMouseOut=\"this.className='UT-span';\">");
		loBuf.append(lsID);
		loBuf.append("&nbsp;/&nbsp;");
		loBuf.append(lsLabel);
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
	return $($("*[name='" + psName + "']", loDiv[0])[0]);
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
	var _this=this;
	for(var i=goForm.MSurname.options.length-1;i>0; i--) goForm.MSurname.options[i]=null;
	ID_MMemberCount.innerText = "0";
	goUnitTree.MTree.html("&nbsp;");
	var loNode = null;
	var url = ctx + "/ldx/unit/product/leaf/unit/xml?type="+(goUnitTree.curType=="Dept"?"D":"U")+"&login="+(goUnitTree.initArg["LoginStatus"]?"1":"0");
	//刘志群添加
	if (goUnitTree.curType == "Unit" || goUnitTree.curType == "UnitUser") {
		url = ctx + "/superadmin/unit/commonUnitTree/leaf/unit/tree?type="+(goUnitTree.curType=="Unit"?"D":"U");
	}
	$.ajax({
		url : url,
		type : "GET",
		//url : "./Leaf.xml",
		//type : "GET",
		data : {optionType: goUnitTree.curType, id:psID},
		
		jsonp: "jsonpCallback",//服务端用于接收callback调用的function名的参数
        dataType:'jsonp',   
		
		//dataType : "xml",
		cache : false,
		async : false,
		error : function(data) {
		},
		success : function(data) {
			 //modify by zky 改为jsonp格式传递
			var xmldoc = _this.getXmlDom(data.xml);
			
			loNode = $(xmldoc).selectSingleNode("units");
		}
	});
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
	loUnit.setAttribute("name",lsName);
	loUnit.setAttribute("id",$(poObj).attr("value"));
	var loSpan = oGetObjectByName(poObj,"-anchor");
	var multiple = goUnitTree.initArg["multiple"];//是否单选
	if(!multiple){
		poParent.empty();
	}
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

function toggle(poObj, noSetSrc){
	
}

function OKClick(){
	var laReturn = new Array();
	//var laNode = goUnitTree.xmlObject["RTree"].childNodes;
	var laNode = goUnitTree.xmlObject["RTree"].children();
	if (laNode.length==0){
		goUnitTree.returnValue = {};
		goUnitTree.returnValue.id = ""
		goUnitTree.returnValue.name = "";
		goUnitTree.returnValue.desc = "";
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
}

function bSearch(){
	var psType = goUnitTree.curType;
	var _this=this;
	var pbExpandAll = false;
	var keyWord = encodeURIComponent($("#KeyWords").val());
	$.ajax({
		url : ctx + "/ldx/unit/product/search/xml" + "?all=1",
		type : "GET",
		data : {optionType: psType, searchValue: keyWord},
		//dataType : "xml",
		jsonp: "jsonpCallback",//服务端用于接收callback调用的function名的参数
        dataType:'jsonp',   
		error : function(data) {
		},
		success: function(data){
			
			 //modify by zky 改为jsonp格式传递
			var xmldoc = _this.getXmlDom(data.xml);
			
			
			var loNode = $(xmldoc).selectSingleNode("units");
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

function getLongDesc(id){
	$.get(ctx+"/ldx/unit/product/search/getLondDesc",{id:id}, function(data) {
		return data;
	});
}