<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
外向发货编号:${wxfhdh}
<br />
<center>
	<b><font size=5>福建增值税专用发票</font> </b>
</center>
<br />
<table border="1"
	style="border-style: solid; border-color: blue; border-collapse: collapse; width: 100%">
	<tr style="border-color: blue; width: 100%">
		<td style="border-style: solid" width="15%" height="30" align="center"
			rowspan="4">购货单位</td>
		<td style="border-style: solid" width="15%" height="30" align="center">名称</td>
		<td style="border-style: solid" width="30%" height="30" align="center"
			colspan="3">${object.name1}</td>
		<td style="border-style: solid" width="10%" height="30" align="center"
			rowspan="4">密码区</td>
		<td style="border-style: solid" width="30%" height="30" align="center"
			rowspan="4" colspan="3"></td>
	</tr>
	<tr style="border-color: blue">
		<td style="border-style: solid" width="15%" height="30" align="center">纳税人识别号</td>
		<td style="border-style: solid" width="30%" height="30" align="center"
			colspan="3">${object.remark}</td>
	</tr>
	<tr style="border-color: blue">
		<td style="border-style: solid" width="15%" height="30" align="center">地址
			电话</td>
		<td style="border-style: solid" width="30%" height="30" align="center"
			colspan="3">${object.zadrc}</td>
	</tr>
	<tr style="border-color: blue">
		<td style="border-style: solid" width="15%" height="30" align="center">开户行及帐号</td>
		<td style="border-style: solid" width="30%" height="30" align="center"
			colspan="3">${object.zbank}</td>
	</tr>
	<tr style="border-color: blue; width: 100%">
		<td style="border-style: solid" width="15%" height="30" align="center">货物或应税劳务名称</td>
		<td style="border-style: solid" width="15%" height="30" align="center">规格型号</td>
		<td style="border-style: solid" width="10%" height="30" align="center">单位</td>
		<td style="border-style: solid" width="10%" height="30" align="center">数量</td>
		<td style="border-style: solid" width="10%" height="30" align="center">单价</td>
		<td style="border-style: solid" width="10%" height="30" align="center"></td>
		<td style="border-style: solid" width="10%" height="30" align="center">金额</td>
		<td style="border-style: solid" width="10%" height="30" align="center">税率</td>
		<td style="border-style: solid" width="10%" height="30" align="center">税额</td>
	</tr>
	<c:forEach var="entity" items="${objects}">
		<tr style="border-color: blue; width: 100%">
			<td style="border-style: solid" width="15%" height="30"
				align="center">${entity.zptxt}</td>
			<td style="border-style: solid" width="15%" height="30"
				align="center"><c:choose>
					<c:when test="${'0' eq entity.rfmng}">
						<font color="red">${entity.kdmat}</font>
					</c:when>
					<c:otherwise>
						${entity.kdmat}
					</c:otherwise>
				</c:choose>
			</td>
			<td style="border-style: solid" width="10%" height="30"
				align="center">套</td>
			<td style="border-style: solid" width="10%" height="30"
				align="center">${entity.rfmng}</td>
			<td style="border-style: solid" width="10%" height="30"
				align="center">${entity.netpr}</td>
			<td style="border-style: solid" width="10%" height="30"
				align="center"></td>
			<td style="border-style: solid" width="10%" height="30"
				align="center">${entity.money}</td>
			<td style="border-style: solid" width="10%" height="30"
				align="center">17%</td>
			<td style="border-style: solid" width="10%" height="30"
				align="center">${entity.tax}</td>
		</tr>
	</c:forEach>
	<tr style="border-color: blue; width: 100%">
		<td style="border-style: solid" width="15%" height="30" align="center">合计</td>
		<td style="border-style: solid" width="15%" height="30" align="center"></td>
		<td style="border-style: solid" width="10%" height="30" align="center"></td>
		<td style="border-style: solid" width="10%" height="30" align="center"></td>
		<td style="border-style: solid" width="10%" height="30" align="center"></td>
		<td style="border-style: solid" width="10%" height="30" align="center"></td>
		<td style="border-style: solid" width="10%" height="30" align="center"><b>${totalMoney}</b>
		</td>
		<td style="border-style: solid" width="10%" height="30" align="center"></td>
		<td style="border-style: solid" width="10%" height="30" align="center"><b>${totalTax}</b>
		</td>
	</tr>
	<tr style="border-color: blue; width: 100%">
		<td style="border-style: solid" width="15%" height="30" align="center">价税合计（大写）</td>
		<td style="border-style: solid" width="85%" height="30" align="center"
			colspan="8"><b>${total}</b></td>
	</tr>
	<tr style="border-color: blue; width: 100%">
		<td style="border-style: solid" width="15%" height="30" align="center"
			rowspan="4">销货单位</td>
		<td style="border-style: solid" width="15%" height="30" align="center">名称</td>
		<td style="border-style: solid" width="30%" height="30" align="center"
			colspan="3">立达信绿色照明股份有限公司</td>
		<td style="border-style: solid" width="10%" height="30" align="center"
			rowspan="4">备注</td>
		<td style="border-style: solid" width="10%" height="30" align="center">供应商编号</td>
		<td style="border-style: solid" width="20%" height="30" align="center"
			colspan="2">21633</td>
	</tr>
	<tr style="border-color: blue; width: 100%">
		<td style="border-style: solid" width="15%" height="30" align="center">纳税人识别号</td>
		<td style="border-style: solid" width="30%" height="30" align="center"
			colspan="3">350625757374127</td>
		<td style="border-style: solid" width="10%" height="30" align="center">订舱编码</td>
		<td style="border-style: solid" width="20%" height="30" align="center"
			colspan="2">${object.xabln}</td>
	</tr>
	<tr style="border-color: blue; width: 100%">
		<td style="border-style: solid" width="15%" height="30" align="center">地址
			电话</td>
		<td style="border-style: solid" width="30%" height="30" align="center"
			colspan="3">长泰县兴泰开发区 0596-7029515</td>
		<td style="border-style: solid" width="10%" height="30" align="center">收货人代码</td>
		<td style="border-style: solid" width="20%" height="30" align="center"
			colspan="2">${object.zrnum}</td>
	</tr>
	<tr style="border-color: blue; width: 100%">
		<td style="border-style: solid" width="15%" height="30" align="center">开户行及账号</td>
		<td style="border-style: solid" width="30%" height="30" align="center"
			colspan="3">中国农业银行长泰支行660101040006972</td>
		<td style="border-style: solid" width="10%" height="30" align="center"></td>
		<td style="border-style: solid" width="20%" height="30" align="center"
			colspan="2"></td>
	</tr>
</table>