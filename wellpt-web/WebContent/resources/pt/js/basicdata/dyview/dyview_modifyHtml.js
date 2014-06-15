$(function() {
	
	var fajian = '6794b3a3-9ceb-4c8b-934c-c73c4e8fe81a';
	var fajian_2ji = '8d727950-f6a5-4054-812c-1b21a6ffc9ee';
	
	var daishou_dacitie = "8f231bde-eea5-4aab-a584-94d7ace87df3";
	var daishou_2ji = "64a266d6-ab41-4a3f-9d1c-abc6d2e013c8";
	
    var tuihui_2ji = "b32cb6e0-39f5-4f9a-bbee-7465241e9ad1";
	
	var yishou_dacitie = "bce01b8a-02cb-4d97-8b9e-6ce47137c020";
	var yishou_2ji = "5d9918f4-d048-414a-91c0-ba2fd84f742d";
	
	var xzxk_1ji = "a2121453-765e-42a4-b7f3-b0e5b46179be";
	var xzxk_2ji = "74da0ae6-c227-4811-8147-8b9c5b85ce47";
	
	var shztdjxx = "63533377-7ff9-49b9-bf34-cf5b098fc5e5";//商事主体登记信息
	
	var fsyc_2ji = "fc817f49-f841-46ff-b399-f453efa9fe70";//发送异常二级页面
	
	var viewArr = new Array();
	viewArr.push(fajian);
	viewArr.push(fajian_2ji);
	viewArr.push(daishou_dacitie);
	viewArr.push(daishou_2ji);
	viewArr.push(tuihui_2ji);
	viewArr.push(yishou_dacitie);
	viewArr.push(yishou_2ji);
	viewArr.push(xzxk_1ji);
	viewArr.push(xzxk_2ji);
	viewArr.push(shztdjxx);
	viewArr.push(fsyc_2ji);
	
	//改变视图中逾期的列的颜色
	changeLineColor(viewArr,"redClass",">",0);
}); 
//改变指定位置的颜色 条件 >,<,=,like,!=
function changeLineColor(viewArr,className,condition,defaultValue) {
	for(var index=0;index<viewArr.length;index++) {
		if(viewArr[index] != "") {
			$("#update_"+viewArr[index]).find(".dataTr").each(function() {
				var tempLength = $(this).find("td").length;
				var lastLength = tempLength - parseInt(1);
				var temp = $(this).find("td").eq(lastLength).text();
				if(condition==">"){
					if(temp > defaultValue) {
						$(this).addClass(className);
					}
				}else if(condition=="<"){
					if(temp < defaultValue) {
						$(this).addClass(className);
					}
				}else if(condition=="="){
					if(temp == defaultValue) {
						$(this).addClass(className);
					}
				}else if(condition=="!="){
					if(temp != defaultValue) {
						$(this).addClass(className);
					}
				}else if(condition=="like"){
					if(temp.indexOf(defaultValue)>-1) {
						$(this).addClass(className);
					}
				}
			});
		}
	}
	
	
}