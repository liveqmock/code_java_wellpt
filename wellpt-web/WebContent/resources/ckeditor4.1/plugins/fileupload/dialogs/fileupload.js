(function() {
    CKEDITOR.dialog.add("fileupload", 
    function(editor) {
        return {
            title: "标准附件样式插入",
            minWidth: 500,
            minHeight:200,
            contents: [{
                id: "tab1",
                label: "label",
                title: "title",
                expand: true,
                padding: 0,
                elements: [
                ]
            }],
            onOk: function() {
            	var ckTable = '<tr class="title"><td class="Label" colspan="3">附件</td><td><input id="fileupload" name="fileupload" type="text"></td></tr>';
            	
				editor.insertHtml(ckTable);
            }
        };
    });
})();