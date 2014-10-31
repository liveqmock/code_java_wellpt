<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Bo Report</title>
</head>
<body>
<div id="leamain">
<iframe id="iframe1" name="iframe1" src="${url }" frameBorder="0" scrolling="no" width="100%"
marginheight="0" marginwidth="0" height="${height }"></iframe>
<script type="text/javascript" language="javascript">   
function dyniframesize(down) { 
	var pTar = null; 
	if (document.getElementById){ 
		pTar = document.getElementById(down); 
	}else{ 
		eval('pTar = ' + down + ';'); 
	} 
	if (pTar && !window.opera){ 
	//begin resizing iframe 
		pTar.style.display="block" ;
		if (pTar.contentDocument && pTar.contentDocument.body.offsetHeight){ 
	//ns6 syntax 
			pTar.height = pTar.contentDocument.body.offsetHeight +20; 
			pTar.width = pTar.contentDocument.body.scrollWidth+20; 
		}else if (pTar.Document && pTar.Document.body.scrollHeight){ 
	//ie5+ syntax 
			pTar.height = pTar.Document.body.scrollHeight; 
			pTar.width = pTar.Document.body.scrollWidth; 
		} 
	}
}
</script>
</div>
</body>
</html>