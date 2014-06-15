package com.wellsoft.pt.common.component.jqgrid.tag;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class TfootTag extends SimpleTagSupport {
	@Override
	public void doTag() throws JspException, IOException {
		PageContext pageContext = (PageContext) this.getJspBody()
				.getJspContext();
		HttpServletRequest request = (HttpServletRequest) pageContext
				.getRequest();
		JspWriter out = pageContext.getOut();
		getJspBody().invoke(out);
	}
}