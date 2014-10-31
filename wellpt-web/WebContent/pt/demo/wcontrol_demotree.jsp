<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/pt/common/taglibs.jsp"%>
<!DOCTYPE html>
<html><head><meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>jOrgChart - A jQuery OrgChart Plugin</title>
    
     <c:set var="ctx" value="${pageContext.request.contextPath}" />
    
    <link rel="stylesheet" href="${ctx}/resources/pt/js/control2/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="${ctx}/resources/pt/js/control2/css/jquery.jOrgChart.css"/>
    <link rel="stylesheet" href="${ctx}/resources/pt/js/control2/css/custom.css"/>
    <link href="${ctx}/resources/pt/js/control2/css/prettify.css" type="text/css" rel="stylesheet" />
<link rel="stylesheet" href="${ctx}/resources/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css" />



   <script type="text/javascript" src="${ctx}/resources/pt/js/control2/prettify.js"></script>
    
    <!-- jQuery includes -->
    
     <script type="text/javascript" src="${ctx}/resources/pt/js/control2/jquery-1.7.1.min.js"></script> 
      <script type="text/javascript" src="${ctx}/resources/pt/js/control2/jquery-ui.min.js"></script> 
      
      <script type="text/javascript" src="${ctx}/resources/ztree/js/jquery.ztree.js"></script>
<script type="text/javascript" src="${ctx}/resources/ztree/js/jquery.ztree.exhide-3.5.js"></script>
      
      
 <script type="text/javascript" src="${ctx}/resources/pt/js/control2/jquery.jOrgChart.js"></script>
    
  
 <script>
 
 var treeObj = {
		    "root": [{
		        "name": "Top Parent",
		         "branches": [
		             {  "name": "Child 1",
		                "branches": [
		                    {"name": "Child 1.1"},
		                    {"name": "Child 1.2"},
		                    {"name": "Child 1.3"}
		                ]
		            },
		            {"name": "Child 2"},
		            {  "name": "Child 3",
		                "branches": [
		                    {    "name": "Child 3.1",
		                         "branches": [
		                            {"name": "Child 3.1.1"}
		                         ]
		                    },
		                    {"name": "Child 3.2"},
		                    {    "name": "Child 3.3",
		                         "branches": [
		                            {    "name": "Child 3.3.1",
		                                 "branches": [
		                                    {"name": "Child 3.3.1.1"},
		                                    {"name": "Child 3.3.1.2"}
		                                ]
		                            },
		                            {"name": "Child 3.3.2"},
		                            {    "name": "Child 3.3.3",
		                                 "branches": [
		                                    {"name": "Child 3.3.3.1"},
		                                    {    "name": "Child 3.3.3.2",
		                                         "branches": [
		                                            {"name": "Child 3.3.3.2.1"},
		                                            {"name": "Child 3.3.3.2.2"},
		                                            {"name": "Child 3.3.3.2.3"}
		                                        ]
		                                    },
		                                    {"name": "Child 3.3.3.3"}
		                                ]
		                            }
		                        ]
		                    }
		                ]
		            }
		        ]
		    }]
		};

	

		
 
    
    jQuery(document).ready(function() {
    	
    	//Convert data to html
		function to_ul(branches) {
		    var ul = document.createElement("ul");
		    levelsCount++;
		    for (var i = 0, n = branches.length; i < n; i++) {
		        var branch = branches[i];
		        var li = document.createElement("li");
		        console.log(i);
		        if(levelsCount>=levelsToShow && branch.branches) {
		            li.className = "collapsed";
		        }
		        var text = document.createTextNode(branch.name);
		        li.appendChild(text);

		        if (branch.branches) {
		            li.appendChild(to_ul(branch.branches));
		        }

		        ul.appendChild(li);
		    }
		    return ul;
		}

		var levelsToShow = 2,
		    levelsCount = 0;
		    treeEl = document.getElementById("tree");
		treeEl.appendChild(to_ul(treeObj.root));
    	
    	//$("#tree ul").jOrgChart();
    	
        $("#org1").jOrgChart({
            chartElement : '#chart',
            dragAndDrop  : true
        });
    });
    </script>
  </head>

  <body onload="prettyPrint();">
  

    <div class="topbar">
        <div class="topbar-inner">
            <div class="container">
                <a class="brand" href="#">jQuery Organisation Chart</a>
                <ul class="nav">
                    <li><a href="http://github.com/wesnolte">Github</a></li>
                    <li><a href="http://twitter.com/wesnolte">Twitter</a></li>                  
                    <li><a href="http://th3silverlining.com">Blog</a></li>      
                </ul>
                <div class="pull-right">
                    <div class="alert-message info" id="show-list">Show underlying list.</div>
                    
<pre class="prettyprint lang-html" id="list-html" style="display:none"></pre>       
                </div>              
            </div>
        </div>
    </div>
    
    <ul id="org1" style="display:none">
    <li>  立达信绿色照明股份有限公司
    <ul>
    <li id="cwb">财务部</li>
    <li id="kfb">开发部
     <!-- <ul>
             <li>  <a href="http://tquila.com" target="_blank">开发1部</a></li>
              <li>开发2部
	              <ul>
	             <li> <div>项目1</div></li>
	             <li> <div ><ul id="cityTree" class="ztree"></ul></div></li>
	              </ul>
	              
              </li>
      </ul> -->
    
    </li>
    <li id="csb">测试部</li>
<!--     <li id="ssb">实施部</li>
    <li id="xzb">行政部</li> -->
     </ul>
       </li>
   </ul>  
   
   <div id="tree" style="display:none"></div>
    
    <ul id="org" style="display:none">
    
    
    	
    <li>
       立达信绿色照明股份有限公司
       <ul>
         <li id="beer">Beer</li>
         <li>Vegetables
           <a href="http://wesnolte.com" target="_blank">Click me</a>
           <ul>
             <li>Pumpkin</li>
             <li>
                <a href="http://tquila.com" target="_blank">Aubergine</a>
                <p>A link and paragraph is all we need.</p>
                
             </li>
           </ul>
         </li>
         <li class="fruit">Fruit
           <ul>
             <li>Apple
               <ul>
                 <li>Granny Smith</li>
               </ul>
             </li>
             <li>Berries
               <ul>
                 <li>Blueberry</li>
                 <li><img src="images/raspberry.jpg" alt="Raspberry"/></li>
                 <li>Cucumber</li>
               </ul>
             </li>
           </ul>
         </li>
         <li>Bread</li>
         <li class="collapsed">Chocolate
           <ul>
             <li>Topdeck</li>
             <li>Reese's Cups</li>
           </ul>
         </li>
       </ul>
     </li>
   </ul>            
    
    <div id="chart" class="orgChart"></div>
    
    <script>
        jQuery(document).ready(function() {
            
            /* Custom jQuery for the example */
            $("#show-list").click(function(e){
                e.preventDefault();
                
                $('#list-html').toggle('fast', function(){
                    if($(this).is(':visible')){
                        $('#show-list').text('Hide underlying list.');
                        $(".topbar").fadeTo('fast',0.9);
                    }else{
                        $('#show-list').text('Show underlying list.');
                        $(".topbar").fadeTo('fast',1);                  
                    }
                });
            });
            
            $('#list-html').text($('#org').html());
            
            $("#org").bind("DOMSubtreeModified", function() {
                $('#list-html').text('');
                
                $('#list-html').text($('#org').html());
                
                prettyPrint();                
            });
        });
    </script>

</body>
</html>