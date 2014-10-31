
function selColor(color){
	 $("#scolor").val(color);
    $("#subject").css("color",color);
    $("#fontcolorElement").css("background-color",color);
    $("#fontColor").val(color); 
}

function checkMust(_this){
	if(!_this.checked){  
		$("#checkRule_5").each(function(){ 
			this.checked  = false;
		  }); 
	}
	
}
function checkUnique(_this){
	if(_this.checked){
		  $("#checkRule_1").each(function(){
			  this.checked=true;
		  });
	}
}