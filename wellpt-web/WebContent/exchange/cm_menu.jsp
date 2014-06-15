<%@ page language="java" pageEncoding="UTF-8" %>
<%@ page import="java.util.Enumeration"%>
<ul class="nav nav-tabs nav-stacked main-menu">
	<li class="nav-header hidden-tablet">公文交换</li>
	<li><a class="ajax-link" href="inbox"><i class="icon-home"></i><span
			class="hidden-tablet"> 收件</span></a></li>
	<li><a class="ajax-link" href="outbox"><i
			class="icon-eye-open"></i><span class="hidden-tablet"> 发件</span></a></li>
	<li><a class="ajax-link" href="draft"><i class="icon-edit"></i><span
			class="hidden-tablet"> 草稿</span></a></li>
	<li><a class="ajax-link" href="trash"><i
			class="icon-align-justify"></i><span class="hidden-tablet"> 废件</span></a></li>
	<li><a href="supervise"><i class="icon-ban-circle"></i><span
			class="hidden-tablet"> 督办</span></a></li>
	<li><a href="config"><i class="icon-lock"></i><span
			class="hidden-tablet"> 配置</span></a></li>
	<!-- <li><a href="outunit"><i class="icon-lock"></i><span
			class="hidden-tablet"> 外部单位配置</span></a></li> -->
</ul>
