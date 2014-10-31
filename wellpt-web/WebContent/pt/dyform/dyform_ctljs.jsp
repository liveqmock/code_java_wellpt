<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<link rel="stylesheet" href="${ctx}/resources/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css" />

<script type="text/javascript" src="${ctx}/resources/ztree/js/jquery.ztree.js"></script>
<!-- 控件文件 -->
  
<script type="text/javascript" src="${ctx}/resources/pt/js/control/common/jquery.wControlConfigUtil.js"></script>

<script type="text/javascript" src="${ctx}/resources/pt/js/control/common/jquery.wControlUtil.js"></script>
<script type="text/javascript" src="${ctx}/resources/pt/js/control/common/jquery.wControlManager.js"></script>
<script type="text/javascript" src="${ctx}/resources/pt/js/control/common/jquery.wControlInterface.js"></script>
 
<script type="text/javascript" src="${ctx}/resources/pt/js/control/common/jquery.wTextCommonMethod.js"></script>
<%--
<script type="text/javascript" src="${ctx}/resources/pt/js/control/common/jquery.wControlCacheData.js"></script>
<script type="text/javascript" src="${ctx}/resources/pt/js/control/subform/jquery.wSubFormMethod.js"></script>
<script type="text/javascript" src="${ctx}/resources/pt/js/control/fileupload/jquery.wFileUploadMethod.js"></script>


<script type="text/javascript" src="${ctx}/resources/My97DatePicker/WdatePicker.js" ></script>
<script type="text/javascript" src="${ctx}/resources/pt/js/control/jquery.wDatePicker.js"></script>
<script type="text/javascript" src="${ctx}/resources/pt/js/control/jquery.wNumberInput.js"></script>
<script type="text/javascript" src="${ctx}/resources/pt/js/control/jquery.wRadio.js"></script>
<script type="text/javascript" src="${ctx}/resources/pt/js/control/jquery.wTextInput.js"></script>
<script type="text/javascript" src="${ctx}/resources/pt/js/control/jquery.wTextArea.js"></script>
<script type="text/javascript" src="${ctx}/resources/pt/js/control/jquery.wCkeditor.js"></script>
<script type="text/javascript" src="${ctx}/resources/pt/js/control/jquery.wCheckBox.js"></script>
<script type="text/javascript" src="${ctx}/resources/pt/js/control/jquery.wComboBox.js"></script>
<script type="text/javascript" src="${ctx}/resources/pt/js/control/jquery.wSerialNumber.js"></script>
<script type="text/javascript" src="${ctx}/resources/pt/js/basicdata/serialnumber/serialform.js"></script>
<script type="text/javascript" src="${ctx}/resources/pt/js/control/jquery.wUnit.js"></script>
<script type="text/javascript" src='${ctx}/resources/pt/js/org/unit/jquery.unit.js'></script>
<script type="text/javascript" src="${ctx}/resources/pt/js/control/jquery.wViewDisplay.js"></script>
<script type="text/javascript" src="${ctx}/resources/pt/js/control/jquery.wDialog.js"></script>
<script type="text/javascript" src="${ctx}/resources/pt/js/control/subform/jquery.wSubForm.js"></script>
<script type="text/javascript" src="${ctx}/resources/pt/js/control/subform/jquery.wSubForm4Group.js"></script>   

<link rel="stylesheet" type="text/css" href="${ctx}/resources/fileupload/jquery.fileupload-ui.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/resources/fileupload/fileupload.css" />

<script type="text/javascript"src="${ctx}/resources/pt/js/fileupload/well.fileupload.constant.js"></script>
<script type="text/javascript"src="${ctx}/resources/utils/ajaxfileupload.src.js"></script>
<script type="text/javascript"src="${ctx}/resources/utils/ajaxfileupload.js"></script>
<!-- 控件对应的class属性文件 -->
    <script type="text/javascript"src="${ctx}/resources/pt/js/dyform/common/FormClass.js"></script> 
<script type="text/javascript"src="${ctx}/resources/pt/js/dyform/controlproperty/wFileUploadClass.js"></script>
<script type="text/javascript"src="${ctx}/resources/pt/js/dyform/controlproperty/wCheckBoxClass.js"></script>
<script type="text/javascript"src="${ctx}/resources/pt/js/dyform/controlproperty/wCkeditorClass.js"></script>
<script type="text/javascript"src="${ctx}/resources/pt/js/dyform/controlproperty/wComboBoxClass.js"></script>
<script type="text/javascript"src="${ctx}/resources/pt/js/dyform/controlproperty/wComboTreeClass.js"></script>
<script type="text/javascript"src="${ctx}/resources/pt/js/dyform/controlproperty/wDatePickerClass.js"></script>
<script type="text/javascript"src="${ctx}/resources/pt/js/dyform/controlproperty/wFileUpload4IconClass.js"></script>
<script type="text/javascript"src="${ctx}/resources/pt/js/dyform/controlproperty/wFileUploadClass.js"></script>
<script type="text/javascript"src="${ctx}/resources/pt/js/dyform/controlproperty/wNumberInputClass.js"></script>
<script type="text/javascript"src="${ctx}/resources/pt/js/dyform/controlproperty/wRadioClass.js"></script>
<script type="text/javascript"src="${ctx}/resources/pt/js/dyform/controlproperty/wSerialNumberClass.js"></script>
<script type="text/javascript"src="${ctx}/resources/pt/js/dyform/controlproperty/wTextAreaClass.js"></script>
<script type="text/javascript"src="${ctx}/resources/pt/js/dyform/controlproperty/wTextInputClass.js"></script>
<script type="text/javascript"src="${ctx}/resources/pt/js/dyform/controlproperty/wUnitClass.js"></script>
<script type="text/javascript"src="${ctx}/resources/pt/js/dyform/controlproperty/wViewDisplayClass.js"></script>
<script type="text/javascript"src="${ctx}/resources/pt/js/dyform/controlproperty/wDialogClass.js"></script>
<script type="text/javascript"src="${ctx}/resources/pt/js/dyform/controlproperty/wTimeEmployClass.js"></script> 

<script type="text/javascript"src="${ctx}/resources/pt/js/fileupload/well.fileupload.js"></script>
<script type="text/javascript"src="${ctx}/resources/pt/js/fileupload/well.fileupload4Image.js"></script>
<script type="text/javascript" src="${ctx}/resources/pt/js/control/fileupload/jquery.wFileUpload.js"></script>
<script type="text/javascript" src="${ctx}/resources/pt/js/control/fileupload/jquery.wFileUpload4Image.js"></script>
<script type="text/javascript" src="${ctx}/resources/pt/js/control/fileupload/jquery.wFileUpload4Image.js"></script>
<script type="text/javascript"src="${ctx}/resources/pt/js/fileupload/well.fileupload4Icon.js"></script>
<script type="text/javascript" src="${ctx}/resources/pt/js/common/jquery.timeEmploy.js"></script>
<script type="text/javascript" src="${ctx}/resources/pt/js/control/jquery.wTimeEmploy.js"></script>--%>

<script type="text/javascript" src="${ctx}/resources/ztree/js/jquery.ztree.js"></script>
<script type="text/javascript" src="${ctx}/resources/ztree/js/jquery.ztree.exhide-3.5.js"></script>
<script type="text/javascript" src="${ctx}/resources/pt/js/control/jquery.wComboTree.js"></script>
<script type="text/javascript" src="${ctx}/resources/pt/js/common/jquery.comboTree.js"></script>
 



