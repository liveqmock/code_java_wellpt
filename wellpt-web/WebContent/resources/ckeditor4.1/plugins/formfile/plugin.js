(function() {
    CKEDITOR.plugins.add("formfile", {
        requires: ["dialog"],
        init: function(a) {
            a.addCommand("formfile", new CKEDITOR.dialogCommand("formfile"));
            a.ui.addButton("formfile", {
                label: "插入从表",//调用dialog时显示的名称
                command: "formfile",
                icon: this.path + "images/anchor.png"//在toolbar中的图标
            });
            CKEDITOR.dialog.add("formfile", this.path + "dialogs/formfile.js");
        }
    });
})();