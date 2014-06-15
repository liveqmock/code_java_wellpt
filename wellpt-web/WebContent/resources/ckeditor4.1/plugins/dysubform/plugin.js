(function() {
	
	 
	//有两种方法可进入该对话框
	//1:通过点击工具栏的插入从表,如果通过这种方式，需要先判断焦点的位置，不得在从表所在的位置再创建另一个从表
	//2:通过双击ckeditor编辑框内的从表元素
	var pluginName = (typeof CkPlugin == "undefined") ? "dysubform": CkPlugin.SUBFORM; 
	 
    CKEDITOR.plugins.add(pluginName, {
    	requires: ["dialog"],
        init: function(a) {
        	 
        	 a.focusedDomPropertyUrl4dysubform =  this.path + "dialogs/property.html";//属性窗口中的html对应的地址,dysubform.js中要使用到该变量
        	 
        	 //定义"设置从表"对话框
            CKEDITOR.dialog.add(pluginName, this.path + "dialogs/" + pluginName +".js");
            
        	//定义命令，用于打开"设置从表"对话框
            a.addCommand(pluginName, new CKEDITOR.dialogCommand(pluginName));
            
            //定义一个按钮,用于触发打开"设置从表"对话框的命令
            a.ui.addButton(pluginName, {
                label: "插入从表",//调用dialog时显示的名称
                command: pluginName,
                icon: this.path + "images/anchor.png"//在toolbar中的图标
            });
            
                      
           /* if ( a.contextMenu ) {
                a.addMenuGroup( 'mygroup', -1 );
                a.addMenuItem( pluginName, {
                    label: '设置从表',
                    command: pluginName,
                    group: 'mygroup'
                } );
                a.contextMenu.addListener( function( element ) {
                	//console.log(CKEDITOR.getPluginContainerDom);
                	console.log(element.getName());
                    return { pluginName: CKEDITOR.TRISTATE_ON };
                } );
            }*/
           
            //定义双击事件
            a.on( 'doubleclick', function( evt ) { 
					var element = evt.data.element;  //element是CKEDITOR.dom.node类的对象
					
					var pluginContainerDomElement = CKEDITOR.getPluginContainerDomElement(pluginName, element); 
				 
					
					if(pluginContainerDomElement != null){ 
					 
						a.focusedDom = pluginContainerDomElement;
   						 evt.data.dialog = pluginName;
   						 
   						window.setTimeout(function(){
   						   
   						  
   						  if(typeof initPropertyDialog != "undefined"){
   							 
   							 initPropertyDialog(a);//重新初始化属性窗口 
   						  } 
   						}, 200);
					} 
        		}
           );
           
         /*   
            if ( a.contextMenu )
    		{
    			a.contextMenu.addListener( function( element, selection )
    				{
    					if ( !element )
    						return null;

    					var isTable	= element.is( 'table' ) || element.hasAscendant( 'table' );

    					if ( isTable )
    					{
    						return {
    							tabledelete : CKEDITOR.TRISTATE_OFF,
    							table : CKEDITOR.TRISTATE_OFF
    						};
    					}

    					return null;
    				} );
    		}*/
            
            
            
        }
    });
})();