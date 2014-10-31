<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<%@ include file="/pt/common/taglibs.jsp"%>
<%@ include file="/pt/common/meta.jsp"%>
<title>module List</title>
<link rel="stylesheet" type="text/css"
	href="${ctx}/resources/jqueryui/css/base/jquery-ui.css" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/resources/jqgrid/css/ui.jqgrid.css" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/resources/layout/jquery.layout.css" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/resources/pt/css/custom-admin-style.css" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/resources/ztree/css/zTreeStyle/zTreeStyle.css" />
</head>
<body>
	<div class="ui-layout-center">
		<div>
			<form action="" id="sys_form">
				<div id="tabs">
					<ul>
						<li><a href="#tabs-1">内网同步</a></li>
					</ul>
					<div id="tabs-1">
						<table  style="width: 100%;">
							<tr><td>前置机租户</td><td></td></tr>
							<tr>
								<td style="width: 130px;">当前网络</td>
								<td>
									<select id="xt_wl" name="xt_zh">
										<option value="in">内网</option>
										<option value="out">外网</option>
									</select>
								</td>
							</tr>
							
							<tr><td>前置机租户</td><td></td></tr>
							<tr>
								<td style="width: 130px;">前置机租户外网(流出)</td>
								<td><input id="qzj_zh_ww_out" name="qzj_zh_ww_out" type="text" style="width:100%;"/>
								</td>
							</tr>
							<tr>
								<td style="width: 130px;">前置机租户外网(流入)</td>
								<td><input id="qzj_zh_ww_in" name="qzj_zh_ww_in" type="text" style="width:100%;"/>
								</td>
							</tr>
							<tr>
								<td style="width: 130px;">前置机租户内网(流出)</td>
								<td><input id="qzj_zh_nw_out" name="qzj_zh_nw_out" type="text" style="width:100%;"/>
								</td>
							</tr>
							<tr>
								<td style="width: 130px;">前置机租户内网(流入)</td>
								<td><input id="qzj_zh_nw_in" name="qzj_zh_nw_in" type="text" style="width:100%;"/>
								</td>
							</tr>
							
							<tr><td>前置机FTP</td><td></td></tr>
							<tr><td>外网(流出)</td><td></td></tr>
							<tr>
								<td style="width: 130px;">FTP外网(流出)地址</td>
								<td><input id="qzj_ftp_ww_out_host" name="qzj_ftp_ww_out_host" type="text" style="width:100%;"/>
								</td>
							</tr>
							<tr>
								<td style="width: 130px;">FTP外网(流出)用户名</td>
								<td><input id="qzj_ftp_ww_out_username" name="qzj_ftp_ww_out_username" type="text" style="width:100%;"/>
								</td>
							</tr>
							<tr>
								<td style="width: 130px;">FTP外网(流出)密码</td>
								<td><input id="qzj_ftp_ww_out_password" name="qzj_ftp_ww_out_password" type="text" style="width:100%;"/>
								</td>
							</tr>
							<tr>
								<td style="width: 130px;">FTP外网(流出)端口</td>
								<td><input id="qzj_ftp_ww_out_post" name="qzj_ftp_ww_out_post" type="text" style="width:100%;"/>
								</td>
							</tr>
							
							<tr><td>外网(流入)</td><td></td></tr>
							<tr>
								<td style="width: 130px;">FTP外网(流入)地址</td>
								<td><input id="qzj_ftp_ww_in_host" name="qzj_ftp_ww_in_host" type="text" style="width:100%;"/>
								</td>
							</tr>
							<tr>
								<td style="width: 130px;">FTP外网(流入)用户名</td>
								<td><input id="qzj_ftp_ww_in_username" name="qzj_ftp_ww_in_username" type="text" style="width:100%;"/>
								</td>
							</tr>
							<tr>
								<td style="width: 130px;">FTP外网(流入)密码</td>
								<td><input id="qzj_ftp_ww_in_password" name="qzj_ftp_ww_in_password" type="text" style="width:100%;"/>
								</td>
							</tr>
							<tr>
								<td style="width: 130px;">FTP外网(流入)端口</td>
								<td><input id="qzj_ftp_ww_in_post" name="qzj_ftp_ww_in_post" type="text" style="width:100%;"/>
								</td>
							</tr>
							
							<tr><td>内网(流出)</td><td></td></tr>
							<tr>
								<td style="width: 130px;">FTP内网(流出)地址</td>
								<td><input id="qzj_ftp_nw_out_host" name="qzj_ftp_nw_out_host" type="text" style="width:100%;"/>
								</td>
							</tr>
							<tr>
								<td style="width: 130px;">FTP内网(流出)用户名</td>
								<td><input id="qzj_ftp_nw_out_username" name="qzj_ftp_nw_out_username" type="text" style="width:100%;"/>
								</td>
							</tr>
							<tr>
								<td style="width: 130px;">FTP内网(流出)密码</td>
								<td><input id="qzj_ftp_nw_out_password" name="qzj_ftp_nw_out_password" type="text" style="width:100%;"/>
								</td>
							</tr>
							<tr>
								<td style="width: 130px;">FTP内网(流出)端口</td>
								<td><input id="qzj_ftp_nw_out_post" name="qzj_ftp_nw_out_post" type="text" style="width:100%;"/>
								</td>
							</tr>
							
							<tr><td>内网(流入)</td><td></td></tr>
							<tr>
								<td style="width: 130px;">FTP内网(流入)地址</td>
								<td><input id="qzj_ftp_nw_in_host" name="qzj_ftp_nw_in_host" type="text" style="width:100%;"/>
								</td>
							</tr>
							<tr>
								<td style="width: 130px;">FTP内网(流入)用户名</td>
								<td><input id="qzj_ftp_nw_in_username" name="qzj_ftp_nw_in_username" type="text" style="width:100%;"/>
								</td>
							</tr>
							<tr>
								<td style="width: 130px;">FTP内网(流入)密码</td>
								<td><input id="qzj_ftp_nw_in_password" name="qzj_ftp_nw_in_password" type="text" style="width:100%;"/>
								</td>
							</tr>
							<tr>
								<td style="width: 130px;">FTP内网(流入)端口</td>
								<td><input id="qzj_ftp_nw_in_post" name="qzj_ftp_nw_in_post" type="text" style="width:100%;"/>
								</td>
							</tr>
							
						</table>
					</div>

				</div>
				<div class="btn-group btn-group-bottom">
					<button id="btn_save" type="button" class="btn">保存</button>
				</div>
			</form>		
		</div>
	</div>
	<!-- Project -->
	<script type="text/javascript" src="${ctx}/resources/jquery/jquery.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/jqueryui/js/jquery-ui.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/jqgrid/js/i18n/grid.locale-cn.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/jqgrid/js/jquery.jqGrid.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/layout/jquery.layout.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/form/jquery.form.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/ztree/js/jquery.ztree.js"></script>	
	<script type="text/javascript"
		src="${ctx}/resources/pt/js/org/unit/jquery.unit.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/pt/js/global.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/pt/js/common/jquery.comboTree.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/pt/js/system_admin.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/pt/js/exchangedata/sysProperties.js"></script>
</body>
</html>