package com.wellsoft.pt.repository.service.impl;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.jcr.Node;
import javax.jcr.Repository;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.SimpleCredentials;
import javax.jcr.UnsupportedRepositoryOperationException;
import javax.jcr.nodetype.InvalidNodeTypeDefinitionException;
import javax.jcr.nodetype.NodeType;
import javax.jcr.nodetype.NodeTypeExistsException;

import org.apache.jackrabbit.commons.cnd.CndImporter;
import org.apache.jackrabbit.commons.cnd.ParseException;
import org.apache.jackrabbit.core.RepositoryImpl;
import org.apache.jackrabbit.core.config.RepositoryConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wellsoft.pt.core.mt.service.TenantService;
import com.wellsoft.pt.core.mt.support.Tenantable;
import com.wellsoft.pt.core.resource.Config;
import com.wellsoft.pt.mt.entity.Tenant;
import com.wellsoft.pt.repository.service.RepositoryService;
import com.wellsoft.pt.repository.support.JcrConstants;
import com.wellsoft.pt.repository.support.RepoFtlHelper;
import com.wellsoft.pt.security.support.IgnoreLoginUtils;
import com.wellsoft.pt.utils.security.SpringSecurityUtils;

@Service("RepositoryService")
public class RepositoryServiceImpl implements RepositoryService {
	private static Logger logger = LoggerFactory.getLogger(RepositoryServiceImpl.class);
	@Autowired
	private TenantService tenantService;

	// private static javax.jcr.Repository repository = null;
	// private static Session systemSession = null;

	private Map<String, Repository> cacheRepoMap = new HashMap<String, Repository>();

	private Map<String, Session> cacheSessionMap = new HashMap<String, Session>();

	private RepoFtlHelper flthelper = new RepoFtlHelper();

	/**
	 * 根据创建的租户库建立jcr仓库模版文件， 并进行加载
	 */
	@Override
	public void createRepository(Tenant tenant) {
		// 根据模版生成相应的仓库文件

		try {
			// flthelper.init();
			StringWriter repoconfig;
			if (true) {
				repoconfig = flthelper.processFile(JcrConstants.FTL_FILE_REPO_TEMPLATE_PATH);
			} else {
				repoconfig = flthelper
						.process(JcrConstants.FTL_REPO_TEMPLATE_PATH, flthelper.convertTenantData(tenant));
			}
			String path = Config.APP_DATA_DIR + "/jcr/tenant_" + tenant.getId() + "/repository";
			File repHome = new File(path);
			InputStream input = new ByteArrayInputStream(repoconfig.toString().getBytes());
			RepositoryConfig config = RepositoryConfig.create(input, repHome.getAbsolutePath());

			Repository tenantrepository = RepositoryImpl.create(config);
			Session tenantession = tenantrepository.login(new SimpleCredentials("username", "password".toCharArray()));
			registerCustomNodeTypes(tenantession);
			Node rootNode = tenantession.getRootNode();
			Node contentNode = null;
			try {
				contentNode = rootNode.getNode(JcrConstants.FOLDER_NODE_NAME);
			} catch (javax.jcr.PathNotFoundException e) {

			}
			if (contentNode == null) {
				rootNode.addNode(JcrConstants.FOLDER_NODE_NAME);
			}
			cacheRepoMap.put(tenant.getAccount(), tenantrepository);
			cacheSessionMap.put(tenant.getAccount(), tenantession);
			cacheRepoMap.put(tenant.getId(), tenantrepository);
			cacheSessionMap.put(tenant.getId(), tenantession);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public synchronized Session getSession() {

		Session tenantsession = this.cacheSessionMap.get(getTenantKey());
		return tenantsession;
	}
	
	@Override
	public Session getSession(String tenantKey) { 
		return  this.cacheSessionMap.get(tenantKey);
	}

	@Override
	public synchronized Repository getRepository() {

		Repository tenantrepo = this.cacheRepoMap.get(getTenantKey());
		return tenantrepo;
	}

	/**
	 * 
	 * @param tenant
	 */
	@Override
	public void deleteRepository(Tenant tenant) {
		shutdown(tenant);
	}

	/**
	 * 启动程序初始化所有的仓库，并将仓库进行缓存
	 */
	@PostConstruct
	public void initAllRepository() {
		flthelper = new RepoFtlHelper();
		try {
			flthelper.init();
			List<Tenant> atenants = tenantService.getActiveTenants();
			for (Tenant tenant : atenants) {
				createRepository(tenant);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private String getTenantKey() {
		if (IgnoreLoginUtils.isIgnoreLogin()) {
			return IgnoreLoginUtils.getUserDetails().getTenant();
		}
		return ((Tenantable) SpringSecurityUtils.getCurrentUser()).getTenant();
	}

	/**
	 * 某个租户的jcr仓库关闭
	 * 
	 * @param tenant
	 */
	@Override
	public synchronized void shutdown(Tenant tenant) {
		logout(tenant);
		Session session = this.cacheSessionMap.get(tenant.getAccount());
		this.cacheSessionMap.remove(tenant.getAccount());
		session = null;
		Repository repository = this.cacheRepoMap.get(tenant.getAccount());
		((RepositoryImpl) repository).shutdown();
		this.cacheRepoMap.remove(tenant.getAccount());
		repository = null;
		logger.debug("shutdownRepository: void");
	}

	/**
	 * 退出某个租户的jcr仓库
	 * 
	 * @param tenant
	 */
	@Override
	public synchronized void logout(Tenant tenant) {
		Session session = this.cacheSessionMap.get(getTenantKey());
		if (session != null && session.isLive()) {
			for (String lt : session.getLockTokens()) {
				logger.debug("Remove LockToken: {}", lt);
				session.removeLockToken(lt);
			}
			session.logout();
		}
	}

	// 关闭用户所在租户的仓库
	@Override
	public synchronized void shutdown() {
		logout();
		Session session = this.getSession();
		this.cacheSessionMap.remove(getTenantKey());
		session = null;
		Repository repository = this.getRepository();
		((RepositoryImpl) repository).shutdown();
		this.cacheRepoMap.remove(getTenantKey());
		repository = null;
		logger.debug("shutdownRepository: void");
	}

	@Override
	public synchronized void logout() {
		Session session = this.getSession();
		if (session != null && session.isLive()) {
			for (String lt : session.getLockTokens()) {
				logger.debug("Remove LockToken: {}", lt);
				session.removeLockToken(lt);
			}
			session.logout();
		}
	}

	/**
	 * 根据租户信息进行仓库返回
	 * 
	 * @return
	 * @throws javax.jcr.RepositoryException
	 */

	@SuppressWarnings("unchecked")
	private synchronized static void registerCustomNodeTypes(Session session)
			throws InvalidNodeTypeDefinitionException, NodeTypeExistsException,
			UnsupportedRepositoryOperationException, ParseException, RepositoryException, IOException {
		String repConfig = Config.CLASS_DIR + File.separator + "jcr" + File.separator + Config.CUSTOMNODE_CONFIG;
		InputStream is = new FileInputStream(repConfig);
		Reader cnd = new InputStreamReader(is);
		NodeType[] nodeTypes = CndImporter.registerNodeTypes(cnd, session);
	}

	@Override
	public synchronized String getRootPath() throws RepositoryException {

		// Initializes Repository and SystemSession

		Session session = getSession();
		String rootPath = session.getRootNode().getPath();
		// JCRSessionManager.getInstance().putSystemSession(systemSession);
		return rootPath;
	}


}
