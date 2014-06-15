$(function(){
	/*******邮件我的文件夹、标签、其他邮件的动态按钮***********/
	//我的文件夹
	$("[name$='customButton_group_button_value_type']").remove();
	$(".folder.liveData").each(function(){
		var str_folder = '<div class="customButton_group_button" name="customButton_group_button_value_type" onclick="moveMailBoxCms(\'0\',\'1\',\'0\',\''+$(this).attr("folderid")+'\',\'\',\'customButton_group_button\');">'+$(this).attr("foldername")+'</div>';
		$(".customButton_group_buttons").eq(1).find(".customButton_group_button").last().before(str_folder);
	});
	//其他邮件
	$(".other.liveData").each(function(){
		var str_other = '<div class="customButton_group_button" name="customButton_group_button_value_type" onclick="moveMailBox2Cms(\'0\',\'1\',\'0\',\'6\',\'\',\''+$(this).attr("othername")+'\',\'customButton_group_button\');">'+$(this).attr("othername")+'</div>';
		$(".customButton_group_buttons").eq(1).find(".customButton_group_button").last().before(str_other);
	});
	//我的标签
	$(".mlabel.liveData").each(function(){
		var str_mlabel = '<div class="customButton_group_button" name="customButton_group_button_value_type" onclick="setLabelCms(\''+$(this).attr("labelid")+'\',\''+$(this).attr("labelname")+'\',\''+$(this).attr("labelcolor")+'\',\'1\',\'customButton_group_button\');">'+$(this).attr("labelname")+'</div>';
		$(".customButton_group_buttons").eq(0).find(".customButton_group_button").last().before(str_mlabel);
		
	});
	
	//邮件高级列的处理
	  $(".mailItem").each(function(){
		 //标签
		if($(this).attr("class").indexOf("tag_")>-1){
			var colors = $(this).attr("color").split(",");
		    var colorNames = $(this).attr("colorname").split(",");
		    var labeluuids = $(this).attr("labeluuid").split(",");
		    var str = "<div class='view_tags'>";
		    for(var i = 0 ;i<colors.length;i++){
		    	if(colors[i]!=""){
		    		str += "<div class='view_tags_item' labeluuid='"+labeluuids[i]+"' style='background:"+colors[i]+";color: #FFFFFF;float: left;margin-left: 5px;width: auto;border-radius:5px;font-size: 11px;padding:0 2px;'>"+colorNames[i]+
		    		"<span class='closeTagSideDiv' style='display: none;font-size: 15px; padding-left: 3px;' title='取消此标签' onclick='setLabel2Cms(\""+labeluuids[i]+"\",\""+ $(this).parents('tr').find('td').eq(0).find('.checkeds').val()+"\",\"closeTagSideDiv\");'>x</span></div>";
		    	}
		    	
		    	
		    	$(".closeTagSideDiv").click(function(event){
			    	event.stopPropagation();//阻止冒泡
			    });
		    }
		    str += "</div>";
		    $(this).after(str);
		    $(this).remove();
		}
		 //主题颜色
		if($(this).attr("class").indexOf("subject_")>-1){
			if($(this).find("span").text()==""){
				$(this).css("color","#000");
			}else{
				$(this).css("color",$(this).find("span").text());
			}
		}
	  });
	  //点击标签时
	  $(".view_tags_item").click(function(){
		  $.ajax({
				type : "POST",
				url : ctx+"/basicdata/dyview/view_show",
				data : "viewUuid=2f5b414a-175e-4754-871b-e233e6c5dff8&status_="+$(this).attr("labeluuid"),
				dataType : "text",
				success : function callback(result) {
					 $("#201441213014582 div div").html(result);
				}
			});
	  });
	  //鼠标滑上标签时
	  $('.view_tags_item').each(function(){
		  $(this).hover(function(){
			  $(this).find('.closeTagSideDiv').show();
	         },function(){
	        	 $(this).find('.closeTagSideDiv').hide();
	              });
	  });
	 
});