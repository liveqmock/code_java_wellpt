<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/pt/common/taglibs.jsp" %>
<script type="text/javascript">
<!--
  $(function(){
	  $("#dialogModule").parent().css("width", $(".container").css("width"));
	  $("#dialogModule").parent().css("height", "700px");
	  $("#dialogModule").parent().css("top", "90px");
	  
	  $("#dialogModule").parent().css("left", 
			  (document.body.scrollWidth - parseInt($(".container").css("width").replace("x", "").replace("y", "")))/2);
	 //alert($(".navs").css("left"));
	  //$("#dialogModule").parent().css("left", $(".navs").css("left"));
	 // $("#dialogModule").parent().css("top", $(".navs").css("top"));
	  /* if($.browser.msie  && $.browser.version < 10){  
		  $("#dialogModule").parent().css("left", $(".navs").css("left"));
		} */
	  $("#dialogModule").prev().removeClass("ui-widget-header");
	  $("#dialogModule").prev().css("background-color", "#18b3e1");
	  $(".ui-dialog-title").css("color", "#ffffff");
	 
	  
	  $(".ui-dialog .ui-dialog-titlebar-close").hover(function(){
		  $(this).css("background", "none repeat scroll 0 0 #18b3e1");
		 $(this).css("height", "30px");
		  
	  });
	  //alert( $(".ui-dialog-titlebar-close[role='button']").size());
	 // $(".ui-dialog-titlebar-close[role='button']").attr("href", "javascript:void(0)");
	  //alert($(".ui-dialog-titlebar-close[role='button']"));
	 // $(".ui-icon-closethick").css("margin-top", "0");
	  //$(".ui-dialog-titlebar-close[role='button']").remove();
	  $(".ui-dialog-title").next("input").remove();
	  /* $(".ui-dialog-title").after("<input type='text' name='searchNav' style='float:right;margin-bottom:0px;padding'/>"); */
  });

//-->
</script>
<div style="clear: both;   overflow: hidden; padding: 0px; height: 800px;"   tabindex="0" >
	<div style="left:10px; top:10px;width:240px;position: absolute"   class="dnrw ">
    
     <div style="clear:both;overflow-x:hidden;overflow-y:auto; " class="tab-content"  >
      <div class="tab-pane active">
       <link rel="stylesheet" href="/resources/theme/css/urlPage.css"> 
       <div class="newoa_cate">  
        
        <div class="clearfix fun-list catelist"> 
       		<div class="jo"> 
          <div class="list-item  list-item_left" style="border-color: rgb(197, 202, 205); 
          	background: none repeat scroll 0% 0% rgb(255, 255, 255);
          	 box-shadow: 2px 2px 3px rgb(181, 181, 181); height: 50px;"
          	 onclick="achorClass('', this)" > 
           <h4 style="padding-top: 15px;13px;"> <a  class="openchild
            <c:if test="${catCode eq ''}">activite</c:if>
           ">全部</a> </h4> 
           <div class="list-content "> 
            <div class="hide-border"></div> 
            <ul> 
            </ul> 
           </div> 
          </div> 
           
         </div> 
           <c:forEach items="${list }" var="l" >
             
         <div class="jo"> 
          <div class="list-item  list-item_left" style="border-color: rgb(197, 202, 205); 
          	background: none repeat scroll 0% 0% rgb(255, 255, 255);
          	 box-shadow: 2px 2px 3px rgb(181, 181, 181);height: 50px;"
          	 onclick="achorClass('${l.cc.code}', this)" > 
           <h4  style="padding-top: 15px;13px;"> <a  class="openchild 
            <c:if test="${catCode eq l.cc.code}">activite</c:if>
           
           ">${l.cc.title}</a> </h4> 
           <div class="list-content "> 
            <div class="hide-border"></div> 
            <ul> 
            </ul> 
           </div> 
          </div> 
           
         </div> 
      
         </c:forEach>
        	
        
        </div> 
 
       </div>
      </div>
     </div>
     <div style="height:3px;" class="view_foot"></div>
    </div>



     <div   style="width: 935px; left: 265px;height:640px;position: absolute" ><div class="jspPane" style="clear:both;width: 935px; height:630px;overflow-x:hidden;overflow-y:auto;">
     	 <input type='text' id="searchNav" name='searchNav' style='float: right; margin-bottom: -10px; margin-top: 10px;margin-right: 30px;'/>
     <div  class="tab-pane active">
       <div class="viewContent" id="nav_view"> 
        <link rel="stylesheet" href="/resources/theme/css/urlPage.css"> 
        <div class="con" style="margin-left: 10px; margin-top: 0px; margin-right: 10px;">
          <c:set var="isSpecified" value="false"></c:set><!-- 是否有指定的分类  -->
          <c:forEach items="${list }" var="l" >
            <%--  <c:if test="${l.cc.code eq catCode }"><!-- 有指定分类 --> --%>
             <%-- 	<c:set var="isSpecified" value="true"></c:set> --%>
             	 <fieldset class="navfieldset" name="${l.cc.code}" code="${l.cc.code}" style="padding-left: 10px;;">
             	  <legend style="font-size: 13px;font-weight: bold;">${l.cc.title }</legend> <div class="clear"></div>  
             	   <div class="gl_con_r"  code="${l.cc.code}"> 
             	   	 <span  code="${l.cc.code}"></span>
             	   	 <div class="clear"></div>
             	   </div>
             	  </fieldset>
		          <script type="text/javascript"> 
		          	  //加载叶结点
		          	   $(function(){
		          		 loadChirldNav("${l.cc.code}");
		          	  });
				      	
		          </script>　
		         
		         <div class="clear"></div>  
             <%-- </c:if>  --%>
         </c:forEach> 
      <%-- 　   <c:forEach items="${list}" var="l" > 
             <c:if test="${isSpecified ne 'true'   }"><!-- 没有指定分类 -->
             	 <fieldset class="navfieldset" name="${l.cc.code}" code="${l.cc.code}"  style="padding-left: 10px;">
             	  <legend>${l.cc.title }</legend> <div class="clear"></div>  
             	   <div class="gl_con_r"  code="${l.cc.code}"> 
             	   	 <span  code="${l.cc.code}"></span>
             	   	 <div class="clear"></div>  
             	   </div>
             	  </fieldset>
             	  
             	  <div class="gl_con_r" code="${l.cc.code}"> 
			          <script type="text/javascript"> 
			          	  //加载叶结点
			          	   $(function(){　
			          		 loadChirldNav("${l.cc.code}");
			          	  });
					      	
			          </script>　
		         </div> 
		         <div class="clear"></div>  
             </c:if>
         </c:forEach> 　 --%>
        </div> 
       </div> 
       
      </div></div>
      </div></div>
      
     <!--  <div class="jspVerticalBar"><div class="jspCap jspCapTop"></div><div class="jspTrack" style="height: 586px;"><div class="jspDrag" style="height: 72px; top: 0px;"><div class="jspDragTop"></div><div class="jspDragBottom"></div></div></div><div class="jspCap jspCapBottom"></div></div>
 -->
<script type="text/javascript">
<!--
  $(function(){
	   
	   $("#dialogModule").css("height", "20000px");
	   
	   $("#searchNav").keydown(function(event){//输入键时搜索 
		   if(event.keyCode == 13){
			   stopDefault(event);
			   var val = $(this).val().toLowerCase();
			   $(".list-item", $("#dialogModule")[0]).first().trigger("click"); 
			   if($.trim(val).length == ''){
				   return true;
			   }
			  //console.log($(".navfieldset").size());
			 
			   
			   
			   var $navView = $("#nav_view");
			   $(".flowItem", $navView[0]).each(function(){
				   var itemVal = $(this).find("a").html().toLowerCase();
				   if(itemVal.indexOf(val) != -1){
					   $(this).show();
					   $(this).find("a").addClass("searchResult");
					   $(this).parents(".gl_con2").show();//显示三级容器
					   $(this).parents(".gl_con2").prev(".gl_tit4").show();//显示二级标题
					   $(this).parents(".navfieldset").show();//显示一级标题
				   }else{
					   $(this).hide();
				   }
			   });
			   
			   $(".gl_tit4",  $navView[0]).each(function(){
				   if($(this).html().indexOf(val) != -1){//如果在二级标题中含有关键字，则显示所有二级下的三级标题
					   $(this).addClass("searchResult");
					  $(this).show();
					  $(this).next().find(".flowItem").show();
				   }else{
					  //console.log($(this).next().find("flowItem").not(":hidden").size());
					   if($(this).next().find(".flowItem").not(":hidden").size() > 0){
						   $(this).show(); 
					   }else{
						   $(this).hide();
					   }
				   }
				  
			   });
			    
			    
			   $(".navfieldset",  $navView[0]).each(function(){
				   if($(this).find("legend").html().indexOf(val) != -1){//如果在一级标题中含有关键字，则显示所有一级下的所有内容  
					   showNavItem($(this).attr("code")); 
				   		var _this = this;
					   window.setTimeout(function(){$(_this).find("legend").addClass("searchResult");}, 200);
				   }else{ 
					   if($(this).find(".gl_tit4").not(":hidden").size() > 0 || $(this).find(".flowItem").not(":hidden").size() > 0){
						   $(this).show(); 
						   $(this).next(".clear").show();
					   }else{
						   $(this).hide();
						   $(this).next(".clear").hide();
					   }
				   }
				  
			   });
			    
			    
			    return true;
		   }
	   });
	   function stopDefault(e) {
	        //如果提供了事件对象，则这是一个非IE浏览器 
	        if(e && e.preventDefault) {
	        　　//阻止默认浏览器动作(W3C)
	        　　		e.preventDefault();
	        } else {
	        　　//IE中阻止函数器默认动作的方式 
	        　　		window.event.returnValue = false; 
	        }
	        return false;
	    }
  });

</script>
      