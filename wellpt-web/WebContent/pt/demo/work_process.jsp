<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<%@ include file="/pt/common/taglibs.jsp"%>
<%@ include file="/pt/common/meta.jsp"%>
<title>办理过程</title>
<style type="text/css">
/* 办理过程图形过程开始 */
#process {
	margin: 0 auto;
	margin-top: 14px;
	width: 875px;
	padding: 40px 0 60px 40px;
	background-color: #F5F5F5;
	border-top: 1px solid #BEBEBE;
	border-left: 1px solid #BEBEBE;
}

#process .proce {
	width: 150px;
	height: 20px;
	float: left;
	position: relative;
}

#process .proce1 {
	margin-left: 5px;
	width: 125px;
	background: url('../../resources/pt/images/workflow/process.png')
		no-repeat scroll -99px -14px transparent;
}

#process .proce2 {
	width: 197px;
	background: url('../../resources/pt/images/workflow/process.png')
		repeat-x scroll -27px -14px transparent;
}

#process .proce3 {
	width: 197px;
	background: url('../../resources/pt/images/workflow/process.png')
		no-repeat scroll -27px -38px transparent;
}

#process .proce4 {
	width: 150px;
	background: url('../../resources/pt/images/workflow/process.png')
		no-repeat scroll -74px -63px transparent;
}

.proce ul {
	margin-top: -25px;
	position: absolute;
	width: 320px;
	list-style: none;
	text-align: center;
	margin-left: -60px;
}

.proce1 ul {
	margin-left: -83px;
}

.proce2 ul {
	width: 420px;
}

.proce3 ul {
	width: 420px;
}

.proce ul li.tx1,.proce ul li.tx2 {
	font-size: 14px;
	font-weight: bold;
	color: #FF8239;
}

.proce ul li.tx2 {
	margin-top: 35px;
}

.proce ul li.tx3 {
	color: #828282;
}

.view_process {
	height: 20px;
	line-height: 20px;
	left: 20px;
}

.view_process .view_process_img {
	width: 15px;
	height: 15px;
	vertical-align: middle;
	display: inline-block;
	background: url('../../resources/pt/images/workflow/process.png')
		no-repeat scroll -149px -91px transparent;
}

.view_process .view_process_img:hover {
	cursor: pointer;
}

.view_process a {
	height: 15px;
	line-height: 15px;
	vertical-align: middle;
	margin-left: 7px;
	font-size: 12px;
	color: #0F599C;
	text-decoration: none;
}

.view_process a:hover {
	color: #FF7200;
	text-decoration: none;
}
/* 办理过程图形过程结束 */
</style>
</head>
<body>
<div>
	<div id="process">
		<div class="proce proce1">
			<ul>
				<li class="tx1">前一环节名</li>
				<li class="tx2">办理人</li>
			</ul>
		</div>
		<div class="proce proce2">
			<ul>
				<li class="tx1">当前环节名</li>
				<li class="tx2">办理人</li>
			</ul>
		</div>
		<div class="proce proce3">
			<ul>
				<li class="tx1 tx3">下一环节名</li>
				<li class="tx2 tx3">办理人</li>
			</ul>
		</div>
		<div class="proce proce4"></div>
		<div class="proce view_process">
			<span class="view_process_img"></span><span><a href="#">查看办理过程</a></span>
		</div>
	</div>
	</div>
</body>
</html>