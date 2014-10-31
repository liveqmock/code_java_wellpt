package com.wellsoft.pt.repository.service.impl;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.jcr.AccessDeniedException;
import javax.jcr.ItemNotFoundException;
import javax.jcr.NoSuchWorkspaceException;
import javax.jcr.RepositoryException;
import javax.security.auth.Subject;

import org.apache.jackrabbit.core.HierarchyManager;
import org.apache.jackrabbit.core.RepositoryContext;
import org.apache.jackrabbit.core.XASessionImpl;
import org.apache.jackrabbit.core.config.WorkspaceConfig;
import org.apache.jackrabbit.core.id.ItemId;
import org.apache.jackrabbit.core.security.AMContext;
import org.apache.jackrabbit.core.security.AccessManager;
import org.apache.jackrabbit.core.security.SystemPrincipal;
import org.apache.jackrabbit.core.security.authorization.AccessControlProvider;
import org.apache.jackrabbit.core.security.authorization.WorkspaceAccessManager;
import org.apache.jackrabbit.spi.Name;
import org.apache.jackrabbit.spi.Path;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A <code>SystemSession</code> ...
 */
@Deprecated
public class ContentSystemSession extends XASessionImpl {

	private static Logger log = LoggerFactory.getLogger(ContentSystemSession.class);
	private static final boolean DEBUG = false;

	/**
	 * Package private factory method
	 *
	 * @param rep
	 * @param wspConfig
	 * @return
	 * @throws RepositoryException
	 */
	public static ContentSystemSession create(RepositoryContext rep, WorkspaceConfig wspConfig)
			throws RepositoryException {
		if (DEBUG)
			log.debug("create()");
		// create subject with SystemPrincipal
		Set<SystemPrincipal> principals = new HashSet<SystemPrincipal>();
		principals.add(new SystemPrincipal());
		Subject subject = new Subject(true, principals, Collections.EMPTY_SET, Collections.EMPTY_SET);
		ContentSystemSession oss = new ContentSystemSession(rep, subject, wspConfig);

		return oss;
	}

	/**
	 * private constructor
	 *
	 * @param rep
	 * @param wspConfig
	 */
	private ContentSystemSession(RepositoryContext rep, Subject subject, WorkspaceConfig wspConfig)
			throws RepositoryException {
		super(rep, subject, wspConfig);
	}

	/* (non-Javadoc)
	 * @see javax.jcr.Session#logout()
	 */
	public synchronized void logout() {

		super.logout();

	}

	protected AccessManager createAccessManager(Subject subject, HierarchyManager hierMgr)
			throws AccessDeniedException, RepositoryException {
		/**
		 * use own AccessManager implementation rather than relying on
		 * configurable AccessManager to handle SystemPrincipal privileges
		 * correctly
		 */
		return new SystemAccessManager();
		//return super.createAccessManager(subject, hierMgr);
	}

	//--------------------------------------------------------< inner classes >
	private class SystemAccessManager implements AccessManager {

		SystemAccessManager() {
		}

		//----------------------------------------------------< AccessManager >
		@Override
		public void init(AMContext context) throws AccessDeniedException, Exception {
			// none
		}

		@Override
		public void init(AMContext arg0, AccessControlProvider arg1, WorkspaceAccessManager arg2)
				throws AccessDeniedException, Exception {
			// none
		}

		@Override
		public void close() throws Exception {
			// none
		}

		@Override
		public boolean canAccess(String workspaceName) throws NoSuchWorkspaceException, RepositoryException {
			// allow everything
			return true;
		}

		@Override
		public boolean isGranted(Path absPath, int permissions) throws RepositoryException {
			// allow everything
			return true;
		}

		@Override
		public boolean isGranted(Path parentPath, Name childName, int permissions) throws RepositoryException {
			// allow everything
			return true;
		}

		// @Override
		// TODO Enable @Override when use jackrabbit 1.6
		public void checkPermission(Path absPath, int permissions) throws AccessDeniedException, RepositoryException {
			// allow everything
		}

		@Override
		public boolean canRead(Path arg0, ItemId arg1) throws RepositoryException {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public void checkPermission(ItemId arg0, int arg1) throws AccessDeniedException, ItemNotFoundException,
				RepositoryException {
			// TODO Auto-generated method stub

		}

		@Override
		public boolean isGranted(ItemId arg0, int arg1) throws ItemNotFoundException, RepositoryException {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public void checkRepositoryPermission(int arg0) throws AccessDeniedException, RepositoryException {
			// TODO Auto-generated method stub

		}
	}
}