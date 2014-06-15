/**
 * jQuery Plugin for creating AJAX auto-suggest textfield
 * @version 2.1
 * @requires jQuery 1.4 or later
 *
 * Copyright (c) 2011 Lucky
 * Licensed under the GPL license:
 *   http://www.gnu.org/licenses/gpl.html
 */

(function($) {
	function autosuggest(callBackUrl, textField){
		
		this.divId="suggestions_holder";
		this.hovered=false;
		this.arrData=null;

		this.textField=textField;
		this.callBackUrl=callBackUrl;

		var width=this.textField.width() + 3;
		var minChars=1;
		var currRow=0;
		var suggestRow="suggest_row";
		var suggestItem="suggest_item";

		this.idField=null;
		this.submitOnSelect=false;
		this.thumbnail=false;
		this.description=false;

		this.textField.after('<div class="suggestions" id="' + this.divId + '"></div>');
		this.textField.attr("autocomplete", "off");

		this.holder=this.textField.next("#" + this.divId);
		this.holder.hide();

		this.onSelected=null;

		var me=this;
		var arr=null;
		this.textField.keyup(
			function(e){//&& e.keyCode!=13
				if(e.keyCode!=37 && e.keyCode!=38 && e.keyCode!=39 && e.keyCode!=40&&e.keyCode!=13){
					if(e.keyCode==186){  
						formatMail2(this,"mailconfig");
					}else{
					if($(this).val().length>=minChars){
						$.ajax({
							
							//url:me.callBackUrl + encodeURI($(this).val()),
							url:me.callBackUrl+"?word="+encodeURI(toTip(this)),
							success:function(data){
								try{
									//me.arrData=$.parseJSON(data);
									//var arr=me.arrData; 
									//alert(data); 
									arr=data;
									var html=""; 

									currRow=0;

									if(arr==null){
										me.hide();
									}
									else{
										if(arr.length>0){
											for(i=0;i<arr.length;i++){
												cssClass=suggestItem;

												if(i==0){
													cssClass+=" first";
												}
												if(i==(arr.length-1)){
													cssClass+=" last";
												}

												var id_field='';
												if(me.idField!=null){
													id_field=' id_field="' + arr[i].id + '"';
												}

												var thumb="";
												if(me.thumbnail==true){
													var style="";
													if(arr[i].thumbnail!=undefined){
														style=' style="background-image:url(' + arr[i].thumbnail + ');"';
													}
													thumb='<div class="thumbnail"' + style + '></div>';
												}

												var desc="";
												if(me.description==true){
													if(arr[i].description!=undefined){
														desc='<div class="description">' + arr[i].description + '</div>';
													}
												}

												html+='<div id="' + suggestRow + (i+1) + '" class="' + cssClass + '"' + id_field + ' seq_id="' + i + '" >' + thumb + '<div class="suggestion_title">' + arr[i].data.replace(new RegExp('(' + me.textField.val() + ')', 'gi'), "<b>$1</b>") + '</div>' + desc + '</div>';
											}

											me.holder.html(html);

											for(i=1;i<=arr.length;i++){
												var target=me.holder.find("#" + suggestRow + i);
												target.mouseover(function(e){
													me.hovered=true;
													me.unSelectAll(this);
													$(this).addClass("selected");
												});

												target.mouseout(function(e){
													me.hovered=false;
													$(this).removeClass("selected");
												});

												target.click(function(e){
													//var v=me.textField.val();
													//var v2=$(this).find(".suggestion_title").text();
													//if(!endWith(v2,';')){
													//	v2=v2+";";
													//}
													var vObj=getTarVal(me.textField);
													me.textField.val(vObj+target.find(".suggestion_title").text());
													//alert(v.substring(0,v.lastIndexOf(";")+1)+v2);
													//me.textField.val(v.substring(0,v.lastIndexOf(";")+1)+v2);
													//formatMail2(this,"mailconfig");
													formatMail22(me.textField,"mailconfig");
													if(me.idField!=null){
														me.idField.val($(this).attr("id_field"));
													}

													// Callback function
													if(me.onSelected!=null){
														me.onSelected.call(this, me.arrData[$(this).attr("seq_id")]);
													}

													if(me.submitOnSelect==true){
														$("form").has(me.textField).submit();
													}

													me.hide();
												});
											}

											me.show(me.holder.find("." + suggestItem).height() * arr.length);
										}
										else{
											me.hide();
										}
									}
								}
								catch(e){
									alert('Sorry, an error has occured!');
								}
							},
							error: function(xhr, status, ex){
								alert('Sorry, an error has occured!');
							}
						});
					}
					else{
						me.hide();
					}
					}
				}
				else{
					if(me.holder.css("display")!="none"){
						checkKey(e);
					}
					else{
						// Callback function
						if(me.onSelected!=null){
							//alert(me.onSelected);
							me.onSelected.call(this, null);
						}
					}
				}
			}
		);

		this.textField.bind(
			"blur",
			function(e){
				if(me.idField!=null){
					if(me.checkSelected(me.textField.val())==false){
						me.textField.val("");
						me.idField.val("");
					}
				}

				if(me.hovered==false){
					me.hide();
				}
				else{
					me.hovered=false;
				}
			}
		);

		this.show=function(height){
			this.holder.css({
				"position":"absolute",
				"left":this.textField.position().left + "px",
				//"top":this.textField.position().top + this.textField.height() + 5 + "px",
				"top":this.textField.position().top + $("#to").height() + 5 + "px",
				//"height":height + "px"
				//"left":$("#to").position().left + "px",
				//"top":$("#to").position().top + this.textField.height() + 5 + "px",
				"height":$("#to").height() + "px"
			});

			this.holder.css({
				//"width":width + "px"
				"width":$("#to").width() + "px"
			});

			this.holder.find("." + suggestItem).css({
				//"width":width + "px",
				"width":$("#to").width() + "px",
				"overflow":"hidden"
			});

			this.holder.show();
		}

		this.hide=function(){
			this.holder.hide();
		}

		this.unSelectAll=function(div){
			var id=$(div).attr("id");
			var rows=this.holder.find("." + suggestItem).get().length;

			for(i=1;i<=rows;i++){
				this.holder.find("#" + suggestRow + i).removeClass("selected");
			}

			currRow=parseInt(id.replace(suggestRow, ""));
			var rgx=/^[0-9]+$/;
			if(!rgx.test(currRow)){
				currRow=0;
			}
		}

		this.setWidth=function(w){
			width=w;
		}

		this.setMinChars=function(c){
			minChars=c;
		}

		this.preventEnter=function(){
			this.textField.keypress(
				function(e){
					if(e.keyCode==13){
						return false;
					}

					return true;
				}
			);
		}

		this.checkSelected=function(data){
			if(this.arrData!=null){
				for(var i=0;i<this.arrData.length;i++){
					if(this.arrData[i].data==data){
						return true;
					}
				}
			}

			return false;
		}
		function getTarVal(obj){
			var ojbv=obj.val();
			var os=ojbv.split(";");
			var relObj="";
			for(var i=0;i<os.length-1;i++){
				relObj+=os[i]+";";
			}
			return relObj;
		}
		function addressKey(th,event){
			if(event.keyCode==13&&me.idField==null){
				formatMail2(th,"mailconfig");
			}
		}

		function checkKey(e){
			if(me.holder.css("display")!="none"){
				var rows=me.holder.find("." + suggestItem).get().length;
				if(e.keyCode==40){
					currRow++;
					if(currRow<=rows){
						if(currRow>0){
							me.holder.find("#" + suggestRow + (currRow-1)).removeClass("selected");
						}

						var target=me.holder.find("#" + suggestRow + currRow);

						target.addClass("selected");
						//alert("1=="+target.find(".suggestion_title").text());
						
						var vObj=getTarVal(me.textField);
						//me.textField.val(vObj+target.find(".suggestion_title").text());
						//formatMail22(me.textField,"mailconfig");
						if(me.idField!=null){
							me.idField.val(target.attr("id_field"));
						}
					}
					else{
						me.holder.find("#" + suggestRow + rows).removeClass("selected");
						currRow=1;
						var target=me.holder.find("#" + suggestRow + currRow);

						target.addClass("selected");
						//alert("1=="+target.find(".suggestion_title").text());
						
						var vObj=getTarVal(me.textField);
						//me.textField.val(vObj+target.find(".suggestion_title").text());
						//formatMail22(me.textField,"mailconfig");
						if(me.idField!=null){
							me.idField.val(target.attr("id_field"));
						}
					}
				}
				else if(e.keyCode==38){
					currRow--;
					if(currRow>0){
						if(currRow<rows){
							me.holder.find("#" + suggestRow + (currRow+1)).removeClass("selected");
						}

						var target=me.holder.find("#" + suggestRow + currRow);

						target.addClass("selected");
						//alert("2=="+target.find(".suggestion_title").text());
						var vObj=getTarVal(me.textField);
						//me.textField.val(vObj+target.find(".suggestion_title").text());
						//formatMail22(me.textField,"mailconfig");
						if(me.idField!=null){
							me.idField.val(target.attr("id_field"));
						}
					}
					else{
						me.holder.find("#" + suggestRow + 1).removeClass("selected");
						currRow=rows;
						var target=me.holder.find("#" + suggestRow + currRow);

						target.addClass("selected");
						//alert("2=="+target.find(".suggestion_title").text());
						var vObj=getTarVal(me.textField);
						//me.textField.val(vObj+target.find(".suggestion_title").text());
						//formatMail22(me.textField,"mailconfig");
						if(me.idField!=null){
							me.idField.val(target.attr("id_field"));
						}
					}
				}
				else if(e.keyCode==13){
					if(me.idField!=null){
						if(me.checkSelected(me.textField.val())==false){
							me.textField.val("");
							me.idField.val("");
						}
					}

					// Callback function
					if(me.onSelected!=null){
						if(currRow>0){
							me.onSelected.call(this, me.arrData[currRow-1]);
						}
						else{
							me.onSelected.call(this, null);
						}
					}
					if(currRow>0){
						if(currRow<rows){
							me.holder.find("#" + suggestRow + (currRow+1)).removeClass("selected");
						}

						var target=me.holder.find("#" + suggestRow + currRow);

						target.addClass("selected");
						//alert("2=="+target.find(".suggestion_title").text());
						var vObj=getTarVal(me.textField);
						var vObjs=vObj.split(";");
						var relvObj='';
						for(var k=0;k<vObjs.length-1;k++){
							relvObj+=vObjs[k]+";";
						}
						//alert("2=="+target.find(".suggestion_title").text()+",obj=="+vObj);
						me.textField.val(relvObj+target.find(".suggestion_title").text());
						formatMail22(me.textField,"mailconfig");
						if(me.idField!=null){
							me.idField.val(target.attr("id_field"));
						}
					}
					else{
						currRow=1;
					}
					//formatMail22(me.textField,"mailconfig");
					me.hide();
				}
			}
			else{
				// Callback function
				if(me.onSelected!=null){
					me.onSelected.call(this, null);
				}
			}

			return true;
		}
	}

	$.fn.coolautosuggest = function(options) {
		var settings = {
			width: null,
			minChars: null,
			idField: null,
			submitOnSelect: false,
			showThumbnail : false,
			showDescription : false,
			onSelected : null
		};
		$.extend(settings, options);

		return this.each(function() {
			var obj = new autosuggest(settings.url, $(this));
			if(settings.width!=null){
				obj.setWidth(settings.width);
			}

			if(settings.minChars!=null){
				obj.setMinChars(settings.minChars);
			}

			if(settings.idField!=null){
				obj.idField=settings.idField;
				obj.preventEnter();
			}

			if(settings.submitOnSelect==true){
				obj.submitOnSelect=true;
			}
			else{
				obj.preventEnter();
			}

			if(settings.showThumbnail==true){
				obj.thumbnail=settings.showThumbnail;
			}

			if(settings.showDescription==true){
				obj.description=settings.showDescription;
			}

			/*if(obj.idField!=null){
				if(obj.checkSelected(obj.textField.val())==false){
					obj.textField.val("");
					obj.idField.val("");
				}
			}*/

			if($.isFunction(settings.onSelected)==true){
				obj.onSelected=settings.onSelected;
			}
		});
	}
})(jQuery);