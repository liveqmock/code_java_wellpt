<%@page import="com.wellsoft.pt.dyform.support.enums.EnumRelationTblSystemField"%>
<%@page import="com.wellsoft.pt.dyform.support.enums.EnumSystemField"%>
<%@page import="com.wellsoft.pt.core.resource.Config"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
 <script type="text/javascript" src="${ctx}/resources/utils/json2.js"></script>
<script type="text/javascript" src="${ctx}/resources/jquery/jquery.js"></script>
<script type="text/javascript" src="${ctx}/resources/bootstrap/js/bootstrap.js"></script>
<script type="text/javascript" src="${ctx}/resources/pt/js/global.js"></script>
<script type="text/javascript"
	src="${ctx}/resources/jqgrid/js/i18n/grid.locale-cn.js"></script>
	
<script type="text/javascript">
	$.jgrid.no_legacy_api = true;
	$.jgrid.useJSON = true;
	// 加载全局国际化资源
	I18nLoader.load("/resources/pt/js/global");
	// 加载动态表单定义模块国际化资源
	I18nLoader.load("/resources/pt/js/dyform/dyform");
</script>
 
<script src="${ctx}/resources/My97DatePicker/WdatePicker.js"
	type="text/javascript"></script>

<script type="text/javascript"
		src="${ctx}/resources/pt/js/dyform/dyform_combine.js"></script>		
	
<script type="text/javascript"
		src="${ctx}/resources/ckeditor4.4.3/ckeditor.js"></script>	

<script type="text/javascript"src="${ctx}/resources/pt/js/ldx/unit/jquery.product.js"></script>  

<script type="text/javascript"
		src="${ctx}/resources/pt/js/dyform/dyform_custom.js"></script>			
 	<%-- 	
   <script type="text/javascript"
		src="${ctx}/resources/pt/js/control2/common/jquery.wControlUtil.js"></script>
<script type="text/javascript"
		src="${ctx}/resources/pt/js/control2/common/jquery.wControlInterface.js"></script>
<script type="text/javascript"
		src="${ctx}/resources/pt/js/control2/common/jquery.wControlManager.js"></script>		
<script type="text/javascript"
		src="${ctx}/resources/pt/js/control2/common/jquery.wRadioCheckBoxCommonMethod.js"></script>	 
	
<script type="text/javascript"
		src="${ctx}/resources/pt/js/control2/jquery.wCheckBox.js"></script>	    
<script type="text/javascript"
		src="${ctx}/resources/pt/js/control2/jquery.wRadio.js"></script>	
<script type="text/javascript"
		src="${ctx}/resources/pt/js/control2/jquery.wComboBox.js"></script>	   
  
   <script type="text/javascript"
		src="${ctx}/resources/pt/js/control2/common/jquery.wControlUtil.js"></script>
<script type="text/javascript"
		src="${ctx}/resources/pt/js/control2/common/jquery.wControlInterface.js"></script>
<script type="text/javascript"
		src="${ctx}/resources/pt/js/control2/common/jquery.wControlManager.js"></script>		
<script type="text/javascript"
		src="${ctx}/resources/pt/js/control2/common/jquery.wRadioCheckBoxCommonMethod.js"></script>	 
	
<script type="text/javascript"
		src="${ctx}/resources/pt/js/control2/jquery.wCheckBox.js"></script>	    
<script type="text/javascript"
		src="${ctx}/resources/pt/js/control2/jquery.wRadio.js"></script>	
<script type="text/javascript"
		src="${ctx}/resources/pt/js/control2/jquery.wComboBox.js"></script>	   

<script type="text/javascript"
		src="${ctx}/resources/pt/js/control2/common/jquery.wTextCommonMethod.js"></script>	 

<script type="text/javascript"
		src="${ctx}/resources/pt/js/control2/jquery.wTextInput.js"></script>	   
		
 
	 
<script type="text/javascript"
		src="${ctx}/resources/pt/js/control2/jquery.wNumberInput.js"></script>	   
		

 <script type="text/javascript"
		src="${ctx}/resources/pt/js/control2/jquery.wDatePicker.js"></script>	
		
<script type="text/javascript"
		src="${ctx}/resources/pt/js/control2/jquery.wComboTree.js"></script>
		
		<script type="text/javascript"
		src="${ctx}/resources/pt/js/control2/jquery.wDialog.js"></script>	 
		
		<script type="text/javascript"src="${ctx}/resources/pt/js/dyform/controlproperty/wTimeEmployClass.js"></script>
		
<script type="text/javascript" src="${ctx}/resources/pt/js/common/jquery.timeEmploy.js"></script>
		<script type="text/javascript"
		src="${ctx}/resources/pt/js/control2/jquery.wTimeEmploy.js"></script>	 
		

<script type="text/javascript"
		src="${ctx}/resources/pt/js/control2/jquery.wSerialNumber.js"></script>	   	  	   
		


<script type="text/javascript"
		src="${ctx}/resources/pt/js/control2/jquery.wCkeditor.js"></script>


<script type="text/javascript"
		src="${ctx}/resources/pt/js/control2/jquery.wEmbedded.js"></script>	

<script type="text/javascript"
		src="${ctx}/resources/pt/js/control2/leedarson/jquery.wJobSelect.js"></script>	

	<script type="text/javascript"
		src="${ctx}/resources/pt/js/control2/fileupload/jquery.wFileUploadMethod.js"></script>	
		
<script type="text/javascript"
		src="${ctx}/resources/pt/js/control2/fileupload/jquery.wFileUpload.js"></script>	

 

<script type="text/javascript"
		src="${ctx}/resources/pt/js/control2/subform/jquery.wSubFormMethod.js"></script>	
	
		<script type="text/javascript"
		src="${ctx}/resources/pt/js/control2/subform/jquery.wSubForm4Group.js"></script>	
<script type="text/javascript"
		src="${ctx}/resources/validate/js/jquery.validate.js"></script>	
			<script type="text/javascript"
		src="${ctx}/resources/validate/js/additional-methods.js"></script>	
 <script type="text/javascript"
		src="${ctx}/resources/pt/js/dyform/validate/validation.js"></script>	
  <script type="text/javascript"
		src="${ctx}/resources/pt/js/dyform/validate/Theme.js"></script>	
		
 <script type="text/javascript"
		src="${ctx}/resources/pt/js/dyform/validate/additional-methods.js"></script>	
		
		  <script type="text/javascript"
		src="${ctx}/resources/pt/js/dyform/common/dyform_constant.js"></script>	
<script type="text/javascript"
		src="${ctx}/resources/pt/js/dyform/dyform_explain.js"></script>	  

<script type="text/javascript"
		src="${ctx}/resources/pt/js/dyform/common/function.js"></script>	    
		
		<script type="text/javascript"
		src="${ctx}/resources/pt/js/control2/fileupload/jquery.wFileUpload.js"></script>	
		
		<script type="text/javascript"
		src="${ctx}/resources/pt/js/control2/fileupload/jquery.wFileUpload4Image.js"></script>	
		
	<script type="text/javascript"src="${ctx}/resources/pt/js/fileupload/well.fileupload4Image.js"></script>   
	
  <script type="text/javascript"src="${ctx}/resources/pt/js/basicdata/view/view_explain.js"></script>  
  
  
<script type="text/javascript"
		src="${ctx}/resources/pt/js/dyform/dyform_en.js"></script>	 	 	
<script type="text/javascript"
		src="${ctx}/resources/pt/js/dyform/dyform_explain.js"></script>	  
		
<script type="text/javascript"
		src="${ctx}/resources/pt/js/control2/subform/jquery.wSubFormMethod.js"></script>	
<script type="text/javascript"
		src="${ctx}/resources/pt/js/control2/subform/jquery.wSubForm.js"></script>--%>  
<script type="text/javascript"
		src="${ctx}/resources/pt/js/org/unit/jquery.unit.js"></script>	  
<script type="text/javascript"
		src="${ctx}/resources/pt/js/control2/jquery.wUnit.js"></script>	
		
		<script type="text/javascript"
		src="${ctx}/resources/pt/js/control2/jquery.wTextArea.js"></script>	    
			<script type="text/javascript"
		src="${ctx}/resources/pt/js/control2/jquery.wDialog.js"></script>	 
	 
 