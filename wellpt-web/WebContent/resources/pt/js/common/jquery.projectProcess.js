!function($) {
	var projectProcess = function(element, options) {
		this.options = options;
		this.$element = $(element);
		this.init();
	};
	projectProcess.prototype = {
		init : function() {
			var options = this.options;
			var $element = this.$element;
			var itemHeight = options.itemHeight;
			var itemWidth = options.itemWidth;
			var distance = options.distance;
			var data = options.data;
			var processes = data.data;
			var type = data.type;
			$element.html("");
			var html = "<div class='project'>";
			html += "<div class='project_name'><span class='projectName'>项目名称："+type+"</span></div>";
			html += "<div class='project_process'>";
			for (var i = 0; i < processes.length; i++) {
				var matters = processes[i].matter; 
				var beforeMatter = 0;
				var afterMatter = 0;
				for(var j = 0; j < matters.length; j++){
					if(matters[j].position=="after"){
						afterMatter++;
					}else{
						beforeMatter++;
					}
				}
				var stageHeight = 0;
				if(beforeMatter>afterMatter){
					stageHeight = beforeMatter*itemHeight;
				}else if(beforeMatter==afterMatter&&beforeMatter!=0&&afterMatter!=0){
					stageHeight = (beforeMatter+0.5)*itemHeight;
				}else if(beforeMatter<afterMatter-1){
					stageHeight = (afterMatter+0.5)*itemHeight;
				}else{
					stageHeight = itemHeight;
				}
				if(i==(processes.length-1)&&i!=0){//最后一个
					html += "<div class='project_process_child' style='height:100px;'>";
				}else{
					if(stageHeight<(100+itemHeight)){
						html += "<div class='project_process_child' style='height:"+(stageHeight+100)+"px;'>";
					}else{
						html += "<div class='project_process_child' style='height:"+stageHeight+"px;'>";
					}
				}
				//阶段
				html += "<div class='project_process_stage' style='height:"+stageHeight+"px;'>";
				html += "<div class='project_process_stage_name' style='background-color:"+options[processes[i].status]+";width:"+itemWidth+"px;height:"+itemHeight+"px;'>";
				html += "<span class='itemName' style='padding-top:"+((itemHeight/2)-10)+"px;'>"+processes[i].name+"</span>";
				html += "</div>";
				if(!(i+1==processes.length)){
					if((stageHeight-itemHeight)>100){
						html += "<div class='project_process_stage_line' style='height:"+(stageHeight-itemHeight)+"px;'>";
						html += "<div class='project_process_stage_line_top' style='margin-top: 20px;height:"+(stageHeight-itemHeight-70)+"px;'></div>";
						html += "<div class='project_process_stage_line_bottom' style='height:30px;'></div>";
						html += "</div>";
					}else{
						html += "<div class='project_process_stage_line' style='margin-top: 20px;height:100px;'>";
						html += "<div class='project_process_stage_line_top' style='height:"+(100-60)+"px;'></div>";
						html += "<div class='project_process_stage_line_bottom' style='height:30px;'></div>";
						html += "</div>";
					}
				}
				html += "</div>";
				//项目
				var beforStr = "";
				var afterStr = "";
				var beforeCount = 1;
				var afterCount = 1;
				for(var j = 0; j < matters.length; j++){
					if(matters[j].position=="after"){
						afterStr += "<div class='project_process_matter_after_child'>";
						if(afterCount==1){
							if(beforeMatter==0){
								afterStr += "<div class='project_process_matter_after_child_line frist only' style='height:"+(itemHeight)+"px;'>";
							}else{
								afterStr += "<div class='project_process_matter_after_child_line frist last' style='height:"+(itemHeight)+"px;'>";
							}
							
						}else if(afterCount==afterMatter){
							afterStr += "<div class='project_process_matter_after_child_line last' style='height:"+(itemHeight)+"px;'>";
						}else{
							afterStr += "<div class='project_process_matter_after_child_line' style='height:"+(itemHeight)+"px;'>";
						}
						afterCount++;
						afterStr += "</div>";
						afterStr += "<div class='project_process_matter_after_child_name'  style='background-color:"+options[matters[j].status]+";width:"+(itemWidth-5)+"px;height:"+(itemHeight-distance)+"px;margin-bottom: "+distance+"px;'>";
						afterStr += "<span class='itemName' style='padding-top:"+((itemHeight/2)-distance-3)+"px;'>"+matters[j].name+"</span>";
						afterStr += "</div>";
						afterStr += "</div>";
					}else{
						beforStr += "<div class='project_process_matter_before_child'>";
						if(beforeMatter==1&&afterMatter==0){
							beforStr += "<div class='project_process_matter_before_child_line one'>";
						}else{
							if(beforeCount==1){
								beforStr += "<div class='project_process_matter_before_child_line frist'>";
							}else if(beforeCount==beforeMatter){
								beforStr += "<div class='project_process_matter_before_child_line last'>";
							}else{
								beforStr += "<div class='project_process_matter_before_child_line'>";
							}
							beforeCount++;
						}
						beforStr += "</div>";
						beforStr += "<div class='project_process_matter_before_child_name' style='background-color:"+options[matters[j].status]+";width:"+(itemWidth-5)+"px;height:"+(itemHeight-distance)+"px;margin-bottom: "+distance+"px;'>";
						beforStr += "<span class='itemName' style='padding-top:"+((itemHeight/2)-distance-3)+"px;'>"+matters[j].name+"</span>";
						beforStr += "</div>";
						beforStr += "</div>";
					}
				}
				html += "<div class='project_process_matter_before' style='height:100%;'>"+beforStr+"</div>";
				if(beforeMatter==0){
					html += "<div class='project_process_matter_after' style='margin-left: 20px;'>"+afterStr+"</div>";
				}else{
					html += "<div class='project_process_matter_after' style='margin-top:"+itemHeight/2+"px;margin-left: 20px;'>"+afterStr+"</div>";
				}
				html += "</div>";
			}
			html += "</div>";
			
			html += "<div class='project_description'>";
			html += "<div class='project_description_content'>";
			html += "<div class='project_description_content_text'>注：</div>";
			html += "<div class='project_description_content_color' style='background-color:"+options.done+";'></div>";
			html += "<div class='project_description_content_text'>已办</div>";
			html += "<div class='project_description_content_color' style='background-color:"+options.doing+";'></div>";
			html += "<div class='project_description_content_text'>在办</div>";
			html += "<div class='project_description_content_color' style='background-color:"+options.notDo+";'></div>";
			html += "<div class='project_description_content_text'>未办</div>";
			html += "</div>";
			html += "</div>";
			
			html += "</div>";
			$element.html(html);
			// 增加数据点击事件
			$(".icon", $element).click(function() {
				
			});
		}
	};

	/*
	 * CommercialRegister PLUGIN DEFINITION =======================
	 */
	
	$.fn.projectProcess = function(option) {
		return this.each(function() {
			var $this = $(this);
			var options = $.extend({}, $.fn.projectProcess.defaults,typeof option == 'object' && option);
			new projectProcess(this, options);
		});
	};

	$.fn.projectProcess.defaults = {
		itemHeight : 60,
		itemWidth : 125,
		done : "#D6F3AD",
		doing : "#FCA195",
		notDo : "#8BE5FD",
		distance : 15
	};
	
}(window.jQuery);