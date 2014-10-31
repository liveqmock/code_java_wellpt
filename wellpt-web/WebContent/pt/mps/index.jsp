<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<%@ include file="/pt/common/taglibs.jsp"%>
<%@ include file="/pt/common/meta.jsp"%>
<%@ include file="/pt/dyform/dyform_css.jsp"%>
<%@ include file="/pt/dyform/dyform_js.jsp"%>
<%@ include file="/pt/dyform/dyform_ctljs.jsp"%>
<style>
td     { font-family: 宋体; font-size: 15pt;}
.ooib { border-width: 2px; border-style: none solid solid; border-color: #CC3333; background-color: #E4E5EE;}
.ooih td { border-width: 2px; padding: 10 15; }
.ooihj { color: #CC3333; background-color: #E4E5EE; border-style: solid solid none; border-color: #CC3333; cursor: hand}
.ooihs { color: #6600CC; background-color: #ccccFF; border-style: solid; border-color: #6600CC #6600CC #CC3333; cursor: hand}
.ooihx { border-style: none none solid; border-color: #CC3333; }
</style>
<script language="JavaScript">
function ghbq(td)
{
  var tr = td.parentElement.cells;
  var ob = obody.rows;
  for(var ii=0; ii<tr.length-1; ii++)
  {
    tr[ii].className = (td.cellIndex==ii)?"ooihj":"ooihs";
    ob[ii].style.display = (td.cellIndex==ii)?"block":"none";
  }
}
</script>
<title>MPS导入页面</title>
</head>
<body>
<div>
<table class="ooih" border="0" cellspacing="0" cellpadding="0" >
  <tr>
<td class="ooihj" nowrap onClick="ghbq(this)">整灯</td>
<td class="ooihs" nowrap onClick="ghbq(this)">部件</td>
<td class="ooihx" width="100%">&nbsp;</td>
  </tr>
</table>
<table class="ooib" id="obody" border="0" cellspacing="0" cellpadding="0" width="80%" height="260px;">
  <tr>
    <td>
<div style="margin-top:10px;margin-left:20px;">
<h3>整灯业务导入</h3>
<form method="POST" enctype="multipart/form-data" action="../../mps/upex">
  File to upload: <input type="file" name="attachment"><br/>
  Notes about the file: <input type="text" name="objectUuid" value="mb.xls" readonly><br/>
  <br/>
  <input type="submit" value="Press" > to upload the file!
</form>
<form method="POST"  action="../../download" name="s1" id="s1" >
<input type="submit"   value="下载" style="margin-top:20px;"/>
</form>
</div>
    </td>
  </tr>
  <tr style="display: none">
    <td>
<div style="margin-top:10px;margin-left:20px;">
<center>
<table>
<tr><td colspan="2"><h3 style="margin-right:100px;">部件业务导入</h3><P></P><p></p></td></tr>
<tr>
<td style="margin-top:100px;" colspan="2">
File to upload:  <input type="file" />
</td>
</tr>
<tr><td><input type="radio"  name="cho"/>LED-驱动自制</td><Td><input type="radio"  name="cho"/>LED-驱动外协</td></tr>
<tr><td colspan="2"><input type="radio"  name="cho"/>电感-自制</td></tr>
<tr><td><input type="radio" name="cho"/>光源组件-自制</td><td><input type="radio" name="cho"/>光源组件-外协</td></tr>
<tr><td><input type="radio" name="cho"/>注塑部-自制</td></tr>
<tr><td>
<input type="button" style="margin-left:20px;margin-top:10px;"  value="Press" /> to upload the file
</td></tr>
<tr><td colspan="2"><form method="POST"  action="../../download" name="s1" id="s1" >
<input type="submit"   value="下载" style="margin-top:20px;"/>
</form></td></tr>
</table>
</center>
</div>
    </td>
  </tr>
</table>
</div>
</body>
</html>