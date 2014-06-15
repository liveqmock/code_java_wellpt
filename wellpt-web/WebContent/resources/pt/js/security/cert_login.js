$(function() {
	function checkAndPrepare() {
		try {
			// 这里检测是否安装客户端版本
			var b = false;
			try {
				b = fjcaWs.OpenUSBKey();
			} catch (e) {
				showErrorMsg("打开证书失败");
				fjcaWs.CloseUSBKey();
				return false;
			}
			if (!b) {
				showErrorMsg("打开证书失败");
				fjcaWs.CloseUSBKey();
				return false;
			}
			// 读取证书
			if (!fjcaWs.ReadCertFromKey()) {
				showErrorMsg("读取证书失败");
				fjcaWs.CloseUSBKey();
				return false;
			}
			var cert = fjcaWs.GetCertData();
			document.getElementById("textCert").value = cert;

			// 读取身份证
			var idNumber = fjcaControl.GetCertExtensionInfoById(cert, "1.2.156.10260.4.1.1");
			var certType = "personal";// 个人证书
			if (idNumber == null || $.trim(idNumber) == "") {
				idNumber = fjcaControl.GetCertExtensionInfoById(cert, "1.2.156.10260.4.1.3");
				certType = "enterprise";// 企业证书
			}
			document.getElementById("idNumber").value = idNumber;
			document.getElementById("certType").value = certType;

			// 对随机数签名
			var strData = document.getElementById("textOriginData").value;
			if (!fjcaWs.SignDataWithKey(strData)) {
				showErrorMsg("对数据签名失败");
				fjcaWs.CloseUSBKey();
				return false;
			}
			document.getElementById("textSignData").value = fjcaWs.GetSignData();

			fjcaWs.CloseUSBKey();
			return true;
		} catch (e) {
			// location.href("errorbd.jsp?style=4991");
			// alert(e.description);
			showErrorMsg("请确认您的电脑上是否已安装福建CA数字证书客户端软件3.1或以上版本且使用IE登录");
			gs.CloseUSBKey();
			return false;
		}
	}
	function loginByCert(formId) {
		if (formId) {
			if (checkAndPrepare()) {
				$("input[name=loginType]").val(4);
				var tenant = $("input[name=tenant]").val();
				var textCert = $("input[name=textCert]").val();
				var textOriginData = $("input[name=textOriginData]").val();
				var textSignData = $("input[name=textSignData]").val();
				var idNumber = $("input[name=idNumber]").val();
				var certType = $("input[name=certType]").val();
				JDS.call({
					service : "certificateLoginService.getLoginName",
					data : [ tenant, textCert, textOriginData, textSignData, idNumber, certType ],
					success : function(result) {
						var data = result.data;
						$("input[name=j_username]").hide();
						$("input[name=j_password]").hide();
						$("input[name=j_username]").val(data["loginName"]);
						$("input[name=j_password]").val(data["password"]);
						document.getElementById(formId).submit();
					},
					error : function(jqXHR) {
						var faultData = JSON.parse(jqXHR.responseText);
						$("input[name=loginType]").val("");
						showErrorMsg(faultData.msg);
					}
				});
			}
		}
	}

	function showErrorMsg(msg) {
		$(".error_message").html("<label class='control-label'>" + msg + "</label>");
	}

	$("#login_by_cert").click(function() {
		loginByCert("login");
	});
});