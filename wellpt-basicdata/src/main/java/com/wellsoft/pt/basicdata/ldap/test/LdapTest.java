package com.wellsoft.pt.basicdata.ldap.test;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;

/**

* @author winter

*

*/

public class LdapTest {

	/**

	* @param args

	*/

	public static void main(String[] args) {

		String userName = "oatest"; //用户名称

		String password = "oa0.123"; //密码

		String host = "172.16.26.210"; //AD服务器

		String port = "389"; //端口

		String domain = "@lcp.cn"; //邮箱的后缀名

		String url = new String("ldap://" + host + ":" + port);

		String user = userName.indexOf(domain) > 0 ? userName : userName + domain;

		Hashtable env = new Hashtable();

		DirContext ctx;

		env.put(Context.SECURITY_AUTHENTICATION, "simple");

		env.put(Context.SECURITY_PRINCIPAL, user); //不带邮箱后缀名的话，会报错，具体原因还未探究。高手可以解释分享。

		env.put(Context.SECURITY_CREDENTIALS, password);

		env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");

		env.put(Context.PROVIDER_URL, url);

		try {

			ctx = new InitialDirContext(env);

			//创建objectclass属性 
			Attribute objclass = new BasicAttribute("objectclass");
			objclass.add("top");
			objclass.add("organizationalunit");
			//创建cn属性 
			Attribute cn = new BasicAttribute("ou", "Employee");
			//创建Attributes，并添加objectclass和cn属性 
			Attributes attrs = new BasicAttributes();
			attrs.put(objclass);
			attrs.put(cn);
			//将属性绑定到新的条目上，创建该条目 
			//dirContext.bind(ldapGroupDN, null, attrs); 
			String ldapGroupDN = "ou=Employee,dc=lcp,dc=cn";
			//ctx.bind(ldapGroupDN, null, attrs);

			//ctx.unbind("ou=OU1,dc=lcp,dc=cn");

			ctx.close();

			System.out.println("验证成功！");

		} catch (NamingException err) {

			err.printStackTrace();

			System.out.println("验证失败！");

		}

	}

}
