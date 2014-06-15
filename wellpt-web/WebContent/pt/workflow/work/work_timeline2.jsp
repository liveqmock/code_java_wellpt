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
	padding: 0;
	background-color: #F5F5F5;
	position: relative;
}

.timeline {
	margin: 0;
	padding: 0;
	background-color: #E4E6EB;
	display: block;
	width: 4px;
	height: 100%;
	position: absolute;
	left: 400px;
}

.timerline {
	margin: 0;
	padding: 0;
	background-color: #E4E6EB;
	display: block;
	width: 4px;
	height: 873px;
	position: absolute;
	left: 0px;
	top: 0px;
	z-index: 3;
}
.timerlabel {
	margin: 0;
	padding: 0;
	background-color: #E4E6EB;
	display: block;
	position: absolute;
	left: 0px;
	top: 0px;
	z-index: 3;
}

.item {
	margin: 10px 0;
	text-align: justify;
	word-wrap: break-word;
	position: relative;
}

.item .task {
	width: 200px;
	background-color: #F1FAFE;
	border: 2px solid #67C2EF;
	border-radius: 2px;
	display: block;
	font-size: 12px;
	padding: 5px;
	margin: 20px 0;
	position: relative;
	left: 50%;
}

.item.alt .task {
	left: 157px;
}

.right-arrow,.left-arrow {
	background-image:
		url("../../../pt/workflow/images/timeline-right-arrow.png");
	display: block;
	width: 20px;
	height: 20px;
	margin: 0px;
	padding: 0;
	position: absolute;
	left: -20px;
	top: 6px;
}

.left-arrow {
	background-image:
		url("../../../pt/workflow/images/timeline-left-arrow.png");
	left: 210px;
}

.item .icon {
	-moz-box-sizing: content-box;
	background: none repeat scroll 0 0 #383E4B;
	border: 2px solid #67C2EF;
	border-radius: 50em;
	color: #FFFFFF;
	font-size: 18px;
	height: 30px;
	line-height: 30px;
	margin-left: -16px;
	position: absolute;
	left: 400px;
	top: 0px;
	text-align: center;
	text-shadow: none;
	width: 30px;
	z-index: 2;
	text-align: center;
	top: 0px;
	text-align: center;
}

.item .time {
	background-color: #E4E6EB;
	border-radius: 4px;
	margin-top: 1px;
	padding: 5px 10px 5px 40px;
	position: absolute;
	left: 280px;
	top: 1px;
}

.item.alt .time {
	left: 400px;
}
</style>
</head>
<body>
	<div id="container">
		<!-- TimeLine导航 -->
		<div class="timeline"></div>

		<div class="item">
			<div class="task">
				Block Content
				<div>Block Content</div>
				<div>Block Content</div>
				<div class="arrow"></div>
			</div>
			<div class="icon"></div>
			<div class="time">3:43 PM</div>
		</div>
	</div>

	<script type="text/javascript" src="${ctx}/resources/jquery/jquery.js"></script>
	<script type="text/javascript" src="${ctx}/resources/pt/js/global.js"></script>
	<script type="text/javascript">
		$(function() {
			JDS.call({
				service : "workService.getTimeline",
				data : [ "d81f143b-d156-4795-882b-3beeb750bdea" ],
				success : function(result) {
					var timeline = result.data;
					initTimeLine(timeline);
				}
			});

			function initTimeLine(timeline) {
				var items = timeline.items;
				var $timeline = $("#container");
				$
						.each(
								items,
								function(i) {
									if (this.type == "timer") {
										var timeslot = '<div id="' + this.id + '" class="item alt timeritem"><div class="task">'
												+ this.content
												+ '<div>'
												+ this.content
												+ '</div><div class="left-arrow"></div></div><div class="icon"></div><div class="time">3:43 PM</div></div>';
										$timeline.append(timeslot);
									} else {
										var timeslot = '<div class="item"><div class="task">'
												+ this.content
												+ '<div>'
												+ this.content
												+ '</div><div class="right-arrow"></div></div><div class="icon"></div><div class="time">3:43 PM</div></div>';
										$timeline.append(timeslot);
									}
								});

				drawTimer(timeline);
			}

			function drawTimer(timeline) {
				var timers = timeline.timers;
				$.each(timers, function(i) {
					var $timerStart = $("#" + this.uuid + "_start");
					var $timerDue = $("#" + this.uuid + "_due");
					var $startTask = $("#" + this.uuid + "_start .task");
					var $dueTask = $("#" + this.uuid + "_due .task");
					if ($timerStart.length == 0 || $timerDue.length == 0) {
						return;
					}
					var $startPos = $timerStart.position();
					var $duePos = $timerDue.position();
					var $timerline = $('<div class="timerline"></div>');
					var height = ($duePos.top - $startPos.top + $dueTask.height()+10) + "px";
					var top = ($startPos.top + 10) + "px";
					var left = ($startTask.position().left - 4) + "px";
					$timerline.css("height", height);
					$timerline.css("top", top);
					$timerline.css("left", left);
					$("#container").append($timerline);
					
					var $timerlabel = $('<div class="timerlabel">' + this.name + '</div>');
					var labelTop = ($startPos.top - 10) + "px";
					var labelLeft = ($startTask.position().left - 4) + "px";
					$timerlabel.css("top", labelTop);
					$timerlabel.css("left", labelLeft);
					$("#container").append($timerlabel);
				});
			}
		});
	</script>
</body>
</html>