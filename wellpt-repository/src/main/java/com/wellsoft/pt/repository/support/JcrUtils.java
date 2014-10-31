package com.wellsoft.pt.repository.support;

import java.io.File;
import java.io.IOException;
import java.security.PrivilegedAction;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.Value;
import javax.jcr.ValueFormatException;
import javax.jcr.Workspace;
import javax.jcr.query.Query;
import javax.jcr.query.QueryManager;
import javax.jcr.query.QueryResult;
import javax.jcr.security.AccessControlList;
import javax.jcr.security.AccessControlManager;
import javax.jcr.security.AccessControlPolicy;
import javax.jcr.security.AccessControlPolicyIterator;
import javax.jcr.security.Privilege;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.security.auth.Subject;

import org.apache.jackrabbit.core.SessionImpl;
import org.apache.jackrabbit.core.security.principal.PrincipalImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Deprecated
public class JcrUtils {
	private static Logger log = LoggerFactory.getLogger(JcrUtils.class);

	/**
	 * 
	 */
	public static String[] usrValue2String(Value[] values, String usrId)
			throws ValueFormatException, IllegalStateException,
			javax.jcr.RepositoryException {
		ArrayList<String> list = new ArrayList<String>();

		// for (int i = 0; i < values.length; i++) {
		// // Admin and System user is not propagated across the child nodes
		// if (!values[i].getString().equals(Config.SYSTEM_USER) &&
		// !values[i].getString().equals(Config.ADMIN_USER)) {
		// list.add(values[i].getString());
		// }
		// }
		//
		// if (Config.USER_ASSIGN_DOCUMENT_CREATION) {
		// // No add an user twice
		// if (!list.contains(usrId)) {
		// list.add(usrId);
		// }
		// }

		return (String[]) list.toArray(new String[list.size()]);
	}

	/**
	 * 
	 */
	public static String[] rolValue2String(Value[] values)
			throws ValueFormatException, IllegalStateException,
			javax.jcr.RepositoryException {
		ArrayList<String> list = new ArrayList<String>();

		// for (int i = 0; i < values.length; i++) {
		// // Do not propagate private OpenKM roles
		// if (!values[i].getString().equals(Config.DEFAULT_ADMIN_ROLE)) {
		// list.add(values[i].getString());
		// }
		// }

		return (String[]) list.toArray(new String[list.size()]);
	}

	/**
	 * 
	 */
	public static String[] value2String(Value[] values)
			throws ValueFormatException, IllegalStateException,
			javax.jcr.RepositoryException {
		ArrayList<String> list = new ArrayList<String>();

		for (int i = 0; i < values.length; i++) {
			list.add(values[i].getString());
		}

		return (String[]) list.toArray(new String[list.size()]);
	}

	/**
	 * This method discards all pending changes currently recorded in this
	 * Session that apply to this Node or any of its descendants.
	 * 
	 * @param node
	 *            The node to cancel.
	 */
	public static void discardsPendingChanges(Node node) {
		try {
			// JSR-170: page 173
			// http://www.day.com/maven/jsr170/javadocs/jcr-1.0/javax/jcr/Item.html#refresh(boolean)
			if (node != null) {
				node.refresh(false);
			} else {
				log.warn("node == NULL");
			}
		} catch (javax.jcr.RepositoryException e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * This method discards all pending changes currently recorded in this
	 * Session that apply to this Session.
	 * 
	 * @param node
	 *            The node to cancel.
	 */
	public static void discardsPendingChanges(Session session) {
		try {
			// http://www.day.com/maven/jsr170/javadocs/jcr-1.0/javax/jcr/Session.html#refresh(boolean)
			if (session != null) {
				session.refresh(false);
			} else {
				log.warn("session == NULL");
			}
		} catch (javax.jcr.RepositoryException e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * Make a silent logout
	 */
	public static void logout(Session session) {
		if (session != null && session.isLive()) {
			for (String lt : session.getLockTokens()) {
				session.removeLockToken(lt);
			}
			session.logout();
		}
	}

	// /**
	// * Load lock tokens from database
	// */
	// public static void loadLockTokens(Session session) throws
	// javax.jcr.RepositoryException {
	// List<LockToken> ltList = LockTokenDAO.findByUser(session.getUserID());
	//
	// for (Iterator<LockToken> it = ltList.iterator(); it.hasNext();) {
	// LockToken lt = it.next();
	// session.addLockToken(lt.getToken());
	// }
	// }
	//
	// /**
	// * Add lock token to user data
	// */
	// public static void addLockToken(Session session, Node node) throws
	// DatabaseException, javax.jcr.RepositoryException {
	// log.debug("addLockToken({}, {})", session, node);
	// LockToken lt = new LockToken();
	// lt.setUser(session.getUserID());
	// lt.setToken(getLockToken(node.getUUID()));
	// LockTokenDAO.add(lt);
	// log.debug("addLockToken: void");
	// }
	//
	// /**
	// * Remove lock token from user data
	// */
	// public static void removeLockToken(Session session, Node node) throws
	// DatabaseException,
	// javax.jcr.RepositoryException {
	// log.debug("removeLockToken({}, {})", session, node);
	// LockTokenDAO.remove(session.getUserID(), getLockToken(node.getUUID()));
	// log.debug("removeLockToken: void");
	// }

	/**
	 * Obtain lock token from node id
	 */
	public static String getLockToken(String id) {
		StringBuffer buf = new StringBuffer();
		buf.append(id.toString());
		buf.append('-');
		buf.append(getCheckDigit(id.toString()));
		return buf.toString();
	}

	/**
	 * Calculate check digit for lock token
	 */
	private static char getCheckDigit(String uuid) {
		int result = 0;

		int multiplier = 36;
		for (int i = 0; i < uuid.length(); i++) {
			char c = uuid.charAt(i);
			if (c >= '0' && c <= '9') {
				int num = c - '0';
				result += multiplier * num;
				multiplier--;
			} else if (c >= 'A' && c <= 'F') {
				int num = c - 'A' + 10;
				result += multiplier * num;
				multiplier--;
			} else if (c >= 'a' && c <= 'f') {
				int num = c - 'a' + 10;
				result += multiplier * num;
				multiplier--;
			}
		}

		int rem = result % 37;
		if (rem != 0) {
			rem = 37 - rem;
		}
		if (rem >= 0 && rem <= 9) {
			return (char) ('0' + rem);
		} else if (rem >= 10 && rem <= 35) {
			return (char) ('A' + rem - 10);
		} else {
			return '+';
		}
	}

	/**
	 * 
	 */
	public static void grant(Session session, String path, String principal,
			String privilege) throws javax.jcr.RepositoryException {
		AccessControlManager acm = ((SessionImpl) session)
				.getAccessControlManager();
		AccessControlPolicyIterator acpi = acm.getApplicablePolicies(path);
		AccessControlPolicy acp = acpi.nextAccessControlPolicy();
		Privilege[] privileges = new Privilege[] { acm
				.privilegeFromName(Privilege.JCR_ALL) };
		((AccessControlList) acp).addAccessControlEntry(new PrincipalImpl(
				principal), privileges);
		session.save();
	}

	/**
	 * Repository Hot-Backup
	 */
	public static File hotBackup() throws RepositoryException, IOException {
		log.debug("hotBackup()");
		String date = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		// String backDirName = "OpenKM" + Config.INSTALL + "_" + date;
		// File backDir = new File(System.getProperty("java.io.tmpdir") +
		// File.separator + backDirName);
		// FileUtils.deleteQuietly(backDir);
		// backDir.mkdir();
		// boolean oldSystemReadonly = Config.SYSTEM_READONLY;
		//
		// try {
		// Config.SYSTEM_READONLY = true;
		// RepositoryCopier.copy((RepositoryImpl)
		// DefaultRepositoryManager.getRepository(), backDir);
		// } catch (javax.jcr.RepositoryException e) {
		// FileUtils.deleteQuietly(backDir);
		// throw new RepositoryException(e.getMessage(), e);
		// } finally {
		// Config.SYSTEM_READONLY = oldSystemReadonly;
		// }

		// log.debug("hotBackup: {}", backDir);
		// return backDir;
		return null;
	}

	/**
	 * Get JCR Session
	 */
	public static Session getSession() throws javax.jcr.LoginException,
			javax.jcr.RepositoryException {
		Object obj = null;

		try {
			InitialContext ctx = new InitialContext();
			Subject subject = (Subject) ctx
					.lookup("java:comp/env/security/subject");
			obj = Subject.doAs(subject, new PrivilegedAction<Object>() {
				public Object run() {
					Session s = null;

					try {
						s = JcrRepositoryHelper.getRepository().login();
					} catch (javax.jcr.LoginException e) {
						return e;
					} catch (javax.jcr.RepositoryException e) {
						return e;
					}

					return s;
				}
			});
		} catch (NamingException e) {
			throw new javax.jcr.LoginException(e.getMessage());
		}

		if (obj instanceof javax.jcr.LoginException) {
			throw (javax.jcr.LoginException) obj;
		} else if (obj instanceof javax.jcr.RepositoryException) {
			throw (javax.jcr.LoginException) obj;
		} else if (obj instanceof javax.jcr.Session) {
			Session session = (javax.jcr.Session) obj;
			// DirectAuthModule.loadUserData(session);
			return session;
		} else {
			return null;
		}
	}

	/**
	 * Get node type
	 */
	public static String getNodeType(Node node)
			throws javax.jcr.RepositoryException {
		String ret = "unknown";

		if (node.isNodeType(JcrConstants.FILE)) {
			ret = JcrConstants.FILE;
		} else if (node.isNodeType(JcrConstants.FOLDER_TYPE)) {
			ret = JcrConstants.FOLDER_TYPE;
		}
		// else if (node.isNodeType(Mail.TYPE)) {
		// ret = Mail.TYPE;
		// }

		return ret;
	}

	/**
	 * Get node uuid from path
	 */
	public static String getUUID(Session session, String path)
			throws javax.jcr.RepositoryException {
		Node rootNode = session.getRootNode();
		Node node = rootNode.getNode(path.substring(1));
		return node.getUUID();
	}

	/**
	 * Get node path from uuid
	 */
	public static String getPath(Session session, String uuid)
			throws javax.jcr.RepositoryException {
		Node node = session.getNodeByUUID(uuid);
		return node.getPath();
	}

	/**
	 * Calculate user quota
	 */
	public static long calculateQuota(Session session)
			throws javax.jcr.RepositoryException {
		// "/jcr:root/okm:root//element(*, okm:document)[okm:content/@okm:author='"+session.getUserID()+"']";
		String qs = "/jcr:root//element(*, okm:document)[okm:content/@okm:author='"
				+ session.getUserID() + "']";
		Workspace workspace = session.getWorkspace();
		QueryManager queryManager = workspace.getQueryManager();
		Query query = queryManager.createQuery(qs, "xpath");
		QueryResult result = query.execute();
		long size = 0;

		for (NodeIterator nit = result.getNodes(); nit.hasNext();) {
			Node node = nit.nextNode();
			Node contentNode = node.getNode(JcrConstants.CONTENT);
			size += contentNode.getProperty(JcrConstants.SIZE).getLong();
		}

		return size;
	}
}
