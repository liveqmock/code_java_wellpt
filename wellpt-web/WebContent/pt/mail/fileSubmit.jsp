<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="com.wellsoft.pt.mail.support.SmartUpload"%>
<%@ page import="com.wellsoft.pt.mail.support.FileUtil"%>

<%   
    String pageErrorInfo = null;   
    SmartUpload su = null;   
    try{   
        su = new SmartUpload();   
        su.initialize(pageContext);   
        su.upload();   
        pageErrorInfo = FileUtil.fileUpload(su,pageContext);   
        System.out.println(pageErrorInfo);
        //if(pageErrorInfo.indexOf("error")<0){   
        //    out.print(pageErrorInfo);   
        //}   
    }catch(Exception e){   
        pageErrorInfo = e.getMessage();
        e.printStackTrace();
    }finally{   
        su = null;   
        if(pageErrorInfo!=null){   
            out.print(pageErrorInfo);   
        }   
    }   
%>  