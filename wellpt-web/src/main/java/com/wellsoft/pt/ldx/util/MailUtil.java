package com.wellsoft.pt.ldx.util;

import java.io.File;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;
import java.util.Vector;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import sun.misc.BASE64Encoder;

public class MailUtil {
	String to = "";// 收件人
	String from = "";// 发件人
	String host = "";// smtp主机
	String username = "";
	String password = "";
	String filename = "";// 附件文件名
	String subject = "";// 邮件主题
	String content = "";// 邮件正文
	String tocc = "";
	String tobcc = "";
	@SuppressWarnings("rawtypes")
	Vector file = new Vector();// 附件文件集合

	public MailUtil() {
	}

	public MailUtil(String to, String from, String smtpServer, String username,
			String password, String subject, String content) {
		this.to = to;
		this.from = from;
		this.host = smtpServer;
		this.username = username;
		this.password = password;
		this.subject = subject;
		this.content = content;
	}

	@SuppressWarnings("rawtypes")
	public boolean send() {
		Properties props = new Properties();
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.auth", "true");
		Session session = Session.getDefaultInstance(props,
				new Authenticator() {
					public PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(username, password);
					}
				});
		try {
			MimeMessage msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress(from));
			msg.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(to));
			if (StringUtils.isNotEmpty(tocc)) {
				msg.setRecipients(Message.RecipientType.CC,
						InternetAddress.parse(tocc));
			}
			if (StringUtils.isNotEmpty(tobcc)) {
				msg.setRecipients(Message.RecipientType.BCC,
						InternetAddress.parse(tobcc));
			}
			subject = transferChinese(subject);
			msg.setSubject(subject);
			Multipart mp = new MimeMultipart();
			MimeBodyPart mbpContent = new MimeBodyPart();
			mbpContent.setContent(content, "text/html;charset=gb2312");
			mp.addBodyPart(mbpContent);
			Enumeration efile = file.elements();
			while (efile.hasMoreElements()) {
				MimeBodyPart mbpFile = new MimeBodyPart();
				filename = efile.nextElement().toString();
				FileDataSource fds = new FileDataSource(filename);
				mbpFile.setDataHandler(new DataHandler(fds));
				BASE64Encoder enc = new BASE64Encoder();
				mbpFile.setFileName("=?GBK?B?"
						+ enc.encode((fds.getName()).getBytes()) + "?=");
				// mbpFile.setFileName(fds.getName());
				mp.addBodyPart(mbpFile);
			}
			file.removeAllElements();
			msg.setContent(mp);
			msg.setSentDate(new Date());
			msg.saveChanges();
			Transport transport = session.getTransport("smtp");
			transport.connect(host, username, password);
			transport.sendMessage(msg, msg.getAllRecipients());
			transport.close();
		} catch (Exception mex) {
			mex.printStackTrace();
			return false;
		}
		return true;
	}

	public String transferChinese(String strText) {
		try {
			strText = MimeUtility.encodeText(new String(strText.getBytes(),
					"GB2312"), "GB2312", "B");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return strText;
	}

	@SuppressWarnings("unchecked")
	public void attachfile(String fname) {
		file.addElement(fname);
	}

	public Boolean sendMail(File file, String to, String subject, String content) {
		MailUtil sendmail = new MailUtil();
		sendmail.setHost("mail.leedarson.com");
		sendmail.setUsername("IT@leedarson.com");
		sendmail.setPassword("it612345");
		sendmail.setTo(to);
		sendmail.setFrom("IT@leedarson.com");
		sendmail.setSubject(subject);
		sendmail.setContent(content);
		if (file != null) {
			sendmail.attachfile(file.getPath());
		}
		return sendmail.send();
	}


	public Boolean sendMailSuper(List<File> files, String to, String subject,
			String content) {
		MailUtil sendmail = new MailUtil();
		sendmail.setHost("mail.leedarson.com");
		sendmail.setUsername("IT@leedarson.com");
		sendmail.setPassword("it612345");
		sendmail.setTo(to);
		sendmail.setFrom("IT@leedarson.com");
		sendmail.setSubject(subject);
		sendmail.setContent(content);
		for (File o : files)
			sendmail.attachfile(o.getPath());

		return sendmail.send();
	}

	public void setTo(String to) {
		this.to = to;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@SuppressWarnings("rawtypes")
	public void setFile(Vector file) {
		this.file = file;
	}

	public String getTocc() {
		return tocc;
	}

	public void setTocc(String tocc) {
		this.tocc = tocc;
	}

	public String getTobcc() {
		return tobcc;
	}

	public void setTobcc(String tobcc) {
		this.tobcc = tobcc;
	}

}
