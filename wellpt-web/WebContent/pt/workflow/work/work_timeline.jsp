<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<%@ include file="/pt/common/taglibs.jsp"%>
<%@ include file="/pt/common/meta.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Time Line</title>
<style type="text/css">
* {
	margin: 0;
	padding: 0;
}

ul,ol {
	list-style: none outside none;
}

#container {
	width: 860px;
	margin: 0 auto;
}

.item {
	width: 408px;
	margin: 20px 10px 10px;
	float: left;
	/* 	background-color: #0ff; */
	border: 1px solid #b4bbcd;
	min-height: 50px;
	text-align: justify;
	word-wrap: break-word;
}

.inner {
	padding: 10px;
}

.timeline_container {
	width: 16px;
	text-align: center;
	margin: 0 auto;
	cursor: pointer;
	display: block;
	background-color: #f00;
}

.timeline {
	margin: 0 auto;
	background-color: #e08989;
	display: block;
	float: left;
	height: 100%;
	left: 428px;
	margin-top: 10px;
	position: absolute;
	width: 4px;
}

.timeline.hover {
	cursor: pointer;
	margin: 0 auto;
}

.timeline div.plus {
	width: 14px;
	height: 14px;
	position: relative;
	left: -6px;
	background: #f00;
}

.rightCorner {
	background-image: url("../../../pt/workflow/images/right.png");
	display: block;
	height: 15px;
	margin-left: 408px;
	margin-top: 8px;
	padding: 0;
	vertical-align: top;
	width: 13px;
	z-index: 2;
	position: absolute;
}

.leftCorner {
	background-image: url("../../../pt/workflow/images/left.png");
	display: block;
	height: 15px;
	width: 13px;
	margin-left: -13px;
	margin-top: 8px;
	position: absolute;
	z-index: 2;
}

.deletebox {
	color: #c00;
	float: right;
	font-weight: bold;
	margin: 8px 10px;
	text-decoration: none;
}

/* popup */
#popup {
	display: block;
	width: 408px;
	float: left;
	background-color: #fff;
	border: 1px solid #a9b6d2;
	min-height: 60px;
	display: none;
	position: absolute;
	margin: 10px;
}

#update {
	width: 100%;
	resize: none;
	min-height: 50px;
}

#update_button {
	background-color: #c00;
	border: 1px solid #333333;
	color: white;
	cursor: pointer;
	font-weight: bold;
	margin-top: 5px;
	padding: 5px;
}
</style>
</head>
<body>
	<div id="container">
		<!-- TimeLine导航 -->
		<div class="timeline_container">
			<div class="timeline">
				<div class="plus"></div>
			</div>
		</div>

		<div class="item">
			<a href="#" class="deletebox">X</a>
			<div class="inner">Block Content</div>
		</div>
		<div class="item">
			<a href="#" class="deletebox">X</a>
			<div class="inner">Block Content</div>
		</div>
		<div class="item">
			<a href="#" class="deletebox">X</a>
			<div class="inner">Block Content</div>
		</div>
		<div class="item">
			<a href="#" class="deletebox">X</a>
			<div class="inner">Block Content</div>
		</div>
		<div class="item">
			<a href="#" class="deletebox">X</a>
			<div class="inner">Block Content</div>
		</div>
		<div class="item">
			<a href="#" class="deletebox">X</a>
			<div class="inner">Block Content</div>
		</div>
		<div class="item">
			<a href="#" class="deletebox">X</a>
			<div class="inner">Block Content</div>
		</div>
		<div class="item">
			<a href="#" class="deletebox">X</a>
			<div class="inner">Block Content</div>
		</div>
		<div class="item">
			<a href="#" class="deletebox">X</a>
			<div class="inner">Block Content</div>
		</div>

		<div id="popup" class="shade">
			<div class="Popup_rightCorner"></div>
			<div class="inner">
				<h3>What's Up?</h3>
				<div class="clearfix">
					<textarea name="" id="update"></textarea>
				</div>
				<div class="clearfix">
					<input type="submit" id="update_button" value="Update" />
				</div>
			</div>
		</div>
	</div>

	<script type="text/javascript" src="${ctx}/resources/jquery/jquery.js"></script>
	<script type="text/javascript"
		src="${ctx}/pt/workflow/js/masonry.pkgd.js"></script>

	<script type="text/javascript">
		$(function() {
			// masonry plugin call
			$("#container").masonry({
				itemSelector : ".item"
			});

			// timeline_container add mousemove event
			$(".timeline_container").mousemove(function(e) {
				var topdiv = $("#containertop").height();
				var page = e.pageY - topdiv - 26;
				$(".plus").css({
					"top" : page + "px",
					"background" : "url('../../../pt/workflow/images/plus.png')",
					"margin-left" : "1px"
				});
			}).mouseout(function(e) {
				$(".plus").css({
					"top" : "0px",
					"background" : "url('')",
					"margin-left" : "0px"
				});
			});

			//injecting arrow points
			function Arrow_Points() {
				var $items = $("#container").find(".item");
				$.each($items, function(i, obj) {
					var posLeft = $(obj).css("left");
					if (posLeft == "0px") {
						var html = "<span class='rightCorner'></span>";
						$(obj).prepend(html);
					} else {
						var html = "<span class='leftCorner'></span>";
						$(obj).prepend(html);
					}
				});
			}
			Arrow_Points();

			$(".deletebox").live("click", function(e) {
				$(this).parent().fadeOut("slow");

				$("#container").masonry("remove", $(this).parent());

				$("#container").masonry({
					itemSelector : ".item"
				});

				$(".rightCorner").hide();

				$(".leftCorner").hide();

				Arrow_Points();

				return false;
			});

			//Timeline navigator on click action
			$(".timeline_container").click(function(e) {
				var topdiv = $("#containertop").height();
				//Current Postion
				$("#popup").css({
					"top" : (e.pageY - topdiv - 33) + "px"
				});
				$("#popup").fadeIn();//Popup block show
				//textbox focus
				$("#update").focus();
			});

			//Mouseover no action
			$("#popup").mouseup(function() {
				return false;
			});

			//outside action of the popup block
			$(document).mouseup(function() {
				$("#popup").hide();
			});

			//update button action
			$("#update_button").live(
					"click",
					function() {
						//textbox value
						var x = $("#update").val();
						//ajax part
						$("#container").prepend(
								'<div class="item"><a href="#" class="deletebox">X</a>'
										+ '<div class="inner">' + x + '</div></div>');
						//reload masnory
						//$("#container").masonry("reload");
						$("#container").masonry({
							itemSelector : ".item"
						});
						//Hiding existing arrows
						$(".rightCorner").hide();
						$(".leftCorner").hide();
						//injecting fresh arrows
						Arrow_Points();
						//clear popup text box value
						$("#update").val("");
						//popup hide
						$("#popup").hide();
						return false;
					});
		});
	</script>
</body>
</html>