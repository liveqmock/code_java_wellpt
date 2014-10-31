package com.wellsoft.pt.utils.mail;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.internet.MimeUtility;

/**
 * @author Administrator
 * 
 */
public class MessageUtils {
	public static String decodeText(String text) throws UnsupportedEncodingException {
		if (text != null && (text.indexOf("=?") != -1)) {
			text = MimeUtility.decodeText(text);
		}
		return text;
	}

	public static List<BodyPart> getMessageAttachment(Object content) throws MessagingException, IOException {
		// System.setProperty(
		// "mail.mime.multipart.ignoreexistingboundaryparameter", "true");
		// List<BodyPart> bodyParts = new ArrayList<BodyPart>();
		// if (content instanceof String) {
		// return bodyParts;
		// }
		// Part part = null;
		// if (content instanceof Part) {
		// part = (Part) content;
		// if (part.isMimeType("multipart/*")) {
		// Multipart multipart = (Multipart) part.getContent();
		// for (int index = 0; index < multipart.getCount(); index++) {
		// BodyPart bodyPart = multipart.getBodyPart(index);
		// String dp = bodyPart.getDisposition();
		// if (Part.ATTACHMENT.equals(dp) || Part.INLINE.equals(dp)) {
		// bodyParts.add(bodyPart);
		// }
		// }
		// } else if (part.isMimeType("message/rfc822")) {
		// bodyParts.addAll(getMessageAttachment(part.getContent()));
		// }
		// }

		List<BodyPart> bodyParts = new ArrayList<BodyPart>();
		if (content instanceof Multipart) {
			Multipart multipart = (Multipart) content;
			for (int index = 0; index < multipart.getCount(); index++) {
				BodyPart bodyPart = multipart.getBodyPart(index);
				String dp = bodyPart.getDisposition();
				if (Part.ATTACHMENT.equals(dp) || Part.INLINE.equals(dp)) {
					bodyParts.add(bodyPart);
				} else if (bodyPart.isMimeType("multipart/*")) {
					bodyParts.addAll(getMessageAttachment(bodyPart.getContent()));
				} else if (bodyPart.isMimeType("message/rfc822")) {
					bodyParts.addAll(getMessageAttachment(bodyPart.getContent()));
				} else if (bodyPart.isMimeType("image/*")) {
					bodyParts.add(bodyPart);
				} else if (bodyPart.isMimeType("application/*")) {
					bodyParts.add(bodyPart);
				}
			}
		}

		return bodyParts;
	}

	public static String getMessageContentText(Object content) throws IOException, MessagingException {
		// if (content instanceof Part) {
		// return fetchContentText((Part) content);
		// }
		String contentText = "";
		if (content instanceof Multipart) {
			Multipart multipart = (Multipart) content;
			for (int i = 0; i < multipart.getCount(); i++) {
				BodyPart bodyPart = multipart.getBodyPart(i);
				if (bodyPart.isMimeType("text/plain")) {
					contentText = bodyPart.getContent().toString();
				} else if (bodyPart.isMimeType("text/html")) {
					contentText = bodyPart.getContent().toString();
				} else if (bodyPart.isMimeType("multipart/*")) {
					contentText = getMessageContentText(bodyPart.getContent());
				} else if (bodyPart.isMimeType("message/rfc822")) {
					contentText = getMessageContentText(bodyPart.getContent());
				}
			}
		} else {
			contentText = content.toString();
		}

		return contentText;
	}

	public static String fetchContentText(Part p) {
		String text = "";
		if (p == null)
			return text;

		try {
			if (!p.isMimeType("text/rfc822-headers") && p.isMimeType("text/*")) {
				try {
					Object pContent;
					try {
						pContent = p.getContent();

					} catch (UnsupportedEncodingException e) {
						pContent = "Message has an illegal encoding. " + e.getLocalizedMessage();
					}
					if (pContent != null) {
						text = pContent.toString();
					} else {
						text = "Illegal content";
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else if (p.isMimeType("multipart/*")) {
				try {
					Multipart mp = (Multipart) p.getContent();
					int count = mp.getCount();
					for (int i = 0; i < count; i++) {
						text = fetchContentText(mp.getBodyPart(i));
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else if (p.isMimeType("message/rfc822")) {
				text = fetchContentText((Part) p.getContent());
			} else {
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return text;
	}

	@SuppressWarnings("rawtypes")
	public ArrayList fetchParts(Part p, ArrayList parts) throws Exception {
		if (p == null)
			return null;

		if (!p.isMimeType("text/rfc822-headers") && p.isMimeType("text/*")) {
			try {
				Object pContent = null;
				try {
					pContent = p.getContent();
				} catch (UnsupportedEncodingException e) {
					pContent = "Message has an illegal encoding. " + e.getLocalizedMessage();
				}
				System.out.println(pContent);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (p.isMimeType("multipart/*")) {
			try {
				Multipart mp = (Multipart) p.getContent();
				int count = mp.getCount();
				for (int i = 0; i < count; i++) {
					fetchParts(mp.getBodyPart(i), parts);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (p.isMimeType("message/rfc822")) {
			fetchParts((Part) p.getContent(), parts);
		} else {
			try {
				// attatch
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return parts;
	}

}
