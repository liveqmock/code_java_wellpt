package com.wellsoft.pt.repository.web;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.activation.MimetypesFileTypeMap;
import javax.jcr.Binary;
import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.Property;
import javax.jcr.PropertyIterator;
import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.apache.commons.lang.StringUtils;
import org.apache.jackrabbit.commons.JcrUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.wellsoft.pt.basicdata.ca.service.FJCAAppsService;
import com.wellsoft.pt.core.support.PagingInfo;
import com.wellsoft.pt.repository.dao.FileUploadDao;
import com.wellsoft.pt.repository.entity.FileEntity;
import com.wellsoft.pt.repository.entity.FileUpload;
import com.wellsoft.pt.repository.exception.JcrException;
import com.wellsoft.pt.repository.support.FileUploadHandler;
import com.wellsoft.pt.repository.support.JcrConstants;
import com.wellsoft.pt.repository.support.JcrRepositoryHelper;
import com.wellsoft.pt.utils.ca.FJCAUtils;

@Service
@Transactional
class JcrFileServiceImpl implements FileService {
	private static Logger logger = LoggerFactory.getLogger(JcrFileServiceImpl.class);

	@Autowired
	private FJCAAppsService fjcaAppsService;

	@Autowired
	private FileUploadDao fileUploadDao;

	@Override
	public List<FileEntity> downFiles(String moduleName, String nodeName) {
		String path = this.getModuleNodePath(moduleName, nodeName);
		return this.down(path);
	}

	@Override
	public List<FileEntity> downFiles(String nodeName) {
		String path = this.getFolderNodePath(nodeName);
		return this.down(path);
	}

	private synchronized List<FileEntity> down(String path) {
		List<FileEntity> fileEntityList = Lists.newArrayList();
		try {
			Session session = JcrRepositoryHelper.getSystemSession();

			if (session.nodeExists(path)) {
				Node folderNode = session.getNode(path);
				NodeIterator entries = folderNode.getNodes();
				while (entries.hasNext()) {
					Node entry = entries.nextNode();
					if (entry.isNodeType(JcrConstants.FILE)) {
						Node contentNode = entry.getNode(JcrConstants.CONTENT);
						FileEntity file = new FileEntity();
						file.setFilename(entry.getName());
						file.setSize(contentNode.getProperty(JcrConstants.SIZE).getLong());
						file.setEdittime(contentNode.getProperty(JcrConstants.JCR_LASTMODIFIED).getDate());
						Binary binary = contentNode.getProperty(JcrConstants.JCR_DATA).getBinary();

						InputStream in = new BufferedInputStream(binary.getStream());
						file.setFile(in);
						fileEntityList.add(file);
					} else if (entry.isNodeType(JcrConstants.NT_LINKEDFILE)) {
						PropertyIterator pi = entry.getProperties(JcrConstants.JCR_CONTENT);
						while (pi.hasNext()) {
							Property pro = (Property) pi.next();
							Node contentNode = pro.getNode();
							FileEntity file = new FileEntity();
							file.setFilename(entry.getName());
							file.setSize(contentNode.getProperty(JcrConstants.SIZE).getLong());
							file.setEdittime(contentNode.getProperty(JcrConstants.JCR_LASTMODIFIED).getDate());
							Binary binary = contentNode.getProperty(JcrConstants.JCR_DATA).getBinary();

							InputStream in = new BufferedInputStream(binary.getStream());
							file.setFile(in);
							fileEntityList.add(file);
							break;
						}
					}
				}
			}
		} catch (Exception e) {
			logger.debug("download file error" + e.getMessage());
			throw new JcrException();
		}
		return fileEntityList;
	}

	public synchronized InputStream down(String path, String fileName) {
		InputStream in = null;
		try {
			Session session = JcrRepositoryHelper.getSystemSession();
			if (session.nodeExists(path + "/" + fileName)) {
				Node entry = session.getNode(path + "/" + fileName);
				if (session.nodeExists(path + "/" + fileName + "/" + JcrConstants.CONTENT)) {
					Node contentNode = session.getNode(path + "/" + fileName + "/" + JcrConstants.CONTENT);
					Binary binary = contentNode.getProperty(JcrConstants.JCR_DATA).getBinary();
					in = new BufferedInputStream(binary.getStream());
				} else {
					PropertyIterator pi = entry.getProperties(JcrConstants.JCR_CONTENT);
					while (pi.hasNext()) {
						Property pro = (Property) pi.next();
						Node contentNode = pro.getNode();
						Binary binary = contentNode.getProperty(JcrConstants.JCR_DATA).getBinary();
						in = new BufferedInputStream(binary.getStream());
						break;
					}
				}
			}
		} catch (Exception e) {
			logger.debug("download file error" + e.getMessage());
			e.printStackTrace();
		}
		return in;
	}

	/**
	 * 根据路径获取需要添加的文件目录节点
	 */
	private Node getFolderNode(String path, Session session) throws RepositoryException {
		return JcrUtils.getOrCreateByPath(path, JcrConstants.FOLDER_TYPE, session);
	}

	private String getModuleNodePath(String moduleName, String nodeName) {
		return "/" + moduleName + "/" + nodeName + "/" + nodeName;
	}

	private String getFolderNodePath(String nodeName) {
		return getModuleNodePath(JcrConstants.FOLDER_NODE_NAME, nodeName);
	}

	@Override
	public InputStream downFile(String moduleName, String nodeName, String fileName) {
		String path = this.getModuleNodePath(moduleName, nodeName);
		return this.down(path, fileName);
	}

	@Override
	public InputStream downFile(String nodeName, String fileName) {
		String path = this.getFolderNodePath(nodeName);
		return this.down(path, fileName);
	}

	public List<FileEntity> getFiles(String moduleName, String nodeName) {
		String path = this.getModuleNodePath(moduleName, nodeName);
		return this.get(path);
	}

	private synchronized List<FileEntity> get(String path) {
		List<FileEntity> fileEntityList = Lists.newArrayList();
		try {
			Session session = JcrRepositoryHelper.getSystemSession();

			if (session.nodeExists(path)) {
				Node folderNode = session.getNode(path);
				NodeIterator entries = folderNode.getNodes();
				while (entries.hasNext()) {
					Node entry = entries.nextNode();
					if (entry.isNodeType(JcrConstants.FILE)) {
						Node contentNode = entry.getNode(JcrConstants.CONTENT);
						FileEntity file = new FileEntity();
						file.setFilename(entry.getName());
						file.setSize(contentNode.getProperty(JcrConstants.SIZE).getLong());
						file.setEdittime(contentNode.getProperty(JcrConstants.JCR_LASTMODIFIED).getDate());

						fileEntityList.add(file);
					} else if (entry.isNodeType(JcrConstants.NT_LINKEDFILE)) {
						PropertyIterator pi = entry.getProperties(JcrConstants.JCR_CONTENT);
						while (pi.hasNext()) {
							Property pro = (Property) pi.next();
							Node contentNode = pro.getNode();
							FileEntity file = new FileEntity();
							file.setFilename(entry.getName());
							file.setSize(contentNode.getProperty(JcrConstants.SIZE).getLong());
							file.setEdittime(contentNode.getProperty(JcrConstants.JCR_LASTMODIFIED).getDate());
							fileEntityList.add(file);
							break;
						}
					}
				}
			}
		} catch (Exception e) {
			logger.debug("download file error" + e.getMessage());
			throw new JcrException();
		}
		return fileEntityList;
	}

	@Override
	public synchronized FileEntity getFile(String moduleName, String nodeName, String fileName) {
		String path = this.getModuleNodePath(moduleName, nodeName);
		FileEntity file = new FileEntity();
		try {
			Session session = JcrRepositoryHelper.getSystemSession();
			if (session.nodeExists(path + "/" + fileName)) {
				Node entry = session.getNode(path + "/" + fileName);
				if (session.nodeExists(path + "/" + fileName + "/" + JcrConstants.CONTENT)) {
					Node contentNode = entry.getNode(JcrConstants.CONTENT);

					file.setFilename(entry.getName());
					file.setSize(contentNode.getProperty(JcrConstants.SIZE).getLong());
					file.setEdittime(contentNode.getProperty(JcrConstants.JCR_LASTMODIFIED).getDate());
				} else {
					PropertyIterator pi = entry.getProperties(JcrConstants.JCR_CONTENT);
					while (pi.hasNext()) {
						Property pro = (Property) pi.next();
						Node contentNode = pro.getNode();
						file.setFilename(entry.getName());
						file.setSize(contentNode.getProperty(JcrConstants.SIZE).getLong());
						file.setEdittime(contentNode.getProperty(JcrConstants.JCR_LASTMODIFIED).getDate());
						break;
					}
				}
			}
		} catch (Exception e) {
			logger.debug("download file error" + e.getMessage());
			e.printStackTrace();
		}
		return file;

	}

	@Override
	public List<FileEntity> getFiles(String nodeName) {
		String path = this.getFolderNodePath(nodeName);
		return this.get(path);
	}

	@Override
	public void deleteFiles(String moduleName, String nodeName) {
		String path = this.getModuleNodePath(moduleName, nodeName);
		this.delete(path);
	}

	@Override
	public void deleteFiles(String nodeName) {
		String path = this.getFolderNodePath(nodeName);
		this.delete(path);
	}

	@Override
	public void deleteFile(String nodeName, String fileName) {
		String path = this.getFolderNodePath(nodeName);
		this.delete(path, fileName);
	}

	@Override
	public void deleteFile(String moduleName, String nodeName, String fileName) {
		String path = this.getModuleNodePath(moduleName, nodeName);
		this.delete(path, fileName);
	}

	private synchronized void delete(String path) {
		try {
			Session session = JcrRepositoryHelper.getSystemSession();

			if (session.nodeExists(path)) {
				Node folderNode = session.getNode(path);
				NodeIterator entries = folderNode.getNodes();
				while (entries.hasNext()) {
					Node entry = entries.nextNode();
					if (entry.isNodeType(JcrConstants.FILE)) {

						this.delete(path, entry.getName());
					} else if (entry.isNodeType(JcrConstants.NT_LINKEDFILE)) {
						deleteSource(entry);
						// entry.remove();

					}
				}
				folderNode.remove();
				session.save();
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.debug("download file error" + e.getMessage());
			throw new JcrException();
		}
	}

	private void deleteSource(Node referenceFileNode) throws RepositoryException {
		if (referenceFileNode.isNodeType(JcrConstants.NT_LINKEDFILE)) {
			PropertyIterator pi = referenceFileNode.getProperties(JcrConstants.JCR_CONTENT);
			Node contentNode;
			while (pi.hasNext()) {
				Property pro = (Property) pi.next();
				contentNode = pro.getNode();
				PropertyIterator res = contentNode.getReferences();
				if (res != null) {
					if (res.getSize() >= 2) {
						referenceFileNode.remove();
						break;
					} else if (res.getSize() == 1) {

						contentNode.getParent().remove();
						referenceFileNode.remove();
					}
				}

				break;
			}
		}
	}

	private synchronized void delete(String path, String fileName) {
		try {
			Session session = JcrRepositoryHelper.getSystemSession();

			if (session.nodeExists(path + "/" + fileName)) {
				Node fileNode = session.getNode(path + "/" + fileName);
				if (fileNode.isNodeType(JcrConstants.FILE)) {
					Node fileToLink = session.getNode(path + "/" + fileName + "/" + JcrConstants.CONTENT);
					PropertyIterator pi = fileToLink.getReferences();
					while (pi.hasNext()) {
						Property p = (Property) pi.next();
						// p.setValue((Value) null);
						p.getParent().remove();
						// session.save();
					}
					fileNode.remove();
				} else if (fileNode.isNodeType(JcrConstants.NT_LINKEDFILE)) {
					// fileNode.remove();
					deleteSource(fileNode);
				}

				session.save();
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug("delete file error" + e.getMessage());
			throw new JcrException();
		}
	}

	@Override
	public boolean isTempExistFile(String tempid, String fileName) {
		String path = this.getModuleNodePath(JcrConstants.TEMP_NODE_NAME, tempid);
		return isExist(path, fileName);
	}

	@Override
	public boolean isExistFile(String moduleName, String nodeName, String fileName) {
		String path = this.getModuleNodePath(moduleName, nodeName);
		return this.isExist(path, fileName);
	}

	@Override
	public boolean isExistFile(String nodeName, String fileName) {
		String path = this.getFolderNodePath(nodeName);
		return this.isExist(path, fileName);
	}

	public boolean isExist(String path, String fileName) {
		try {
			Session session = JcrRepositoryHelper.getSystemSession();

			if (session.nodeExists(path + "/" + fileName)) {
				return true;
			}
		} catch (Exception e) {
			logger.debug("download file error" + e.getMessage());
			throw new JcrException();
		}
		return false;

	}

	@Override
	public void renameFile(String nodeName, String oldFileName, String newFileName) {
		String path = this.getFolderNodePath(nodeName);
		this.rename(path, oldFileName, newFileName);
	}

	@Override
	public void renameFile(String moduleName, String nodeName, String oldFileName, String newFileName) {
		String path = this.getModuleNodePath(moduleName, nodeName);
		this.rename(path, oldFileName, newFileName);
	}

	private synchronized void rename(String path, String oldFileName, String newFileName) {
		try {
			Session session = JcrRepositoryHelper.getSystemSession();

			if (session.nodeExists(path + "/" + oldFileName)) {
				Node fileNode = session.getNode(path + "/" + oldFileName);
				session.move(fileNode.getPath(), fileNode.getParent().getPath() + "/" + newFileName);
			}
		} catch (Exception e) {
			logger.debug("download file error" + e.getMessage());
			throw new JcrException();
		}
	}

	@Override
	public void copyFiles(String moduleName, String nodeName, String newNodeName) {
		List<FileEntity> files = downFiles(moduleName, nodeName.toString());
		// String oldPath = this.getModuleNodePath(moduleName, nodeName);
		// String newPath = this.getModuleNodePath(moduleName, newNodeName);
		//
		// try {
		// Session session = JcrRepositoryHelper.getSystemSession();
		// JcrUtils.getOrCreateByPath(newPath, JcrConstants.FOLDER_TYPE,
		// session);
		//
		// session.save();
		// session.getWorkspace().copy(oldPath, newPath);
		//
		// Node oldeFolderNode = this.getFolderNode(oldPath, session);
		// Node newFolderNode = this.getFolderNode(newPath, session);
		//
		// session.save();
		// } catch (RepositoryException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }

	}

	private synchronized void moveFile(String oldPath, String moduleName, String nodeName) {
		try {
			Session session = JcrRepositoryHelper.getSystemSession();

			if (session.nodeExists(oldPath)) {

				String newPath = "/" + moduleName + "/" + nodeName;
				Node folderNode = getFolderNode(newPath + "/" + nodeName, session);

				session.move(oldPath, folderNode.getPath());
			}
		} catch (Exception e) {
			logger.debug("download file error" + e.getMessage());
			e.printStackTrace();
			throw new JcrException();
		}
	}

	private Node saveFile(FileEntity file, Node folderNode, Session session) {
		return saveFile(file.getFile(), file.getFilename(), folderNode, session);
	}

	private synchronized Node saveFile(InputStream is, String fileName, Node folderNode, Session session) {
		// 扩展名
		String extName = "";
		Node fileNode;
		try {
			long size = is.available();

			String mimeType = new MimetypesFileTypeMap().getContentType(fileName);
			if (mimeType == null) {
				mimeType = "application/msword";
			}
			if (fileName.lastIndexOf(".") >= 0) {
				extName = fileName.substring(fileName.lastIndexOf("."));
			}
			// 判断节点存在 存在则覆盖，不存在则新建
			// if (isExist(folderNode.getPath(), fileName)) {
			// fileNode = folderNode.getNode(fileName);
			// } else {
			// fileNode = folderNode.addNode(fileName, JcrConstants.FILE);
			// }
			fileNode = JcrUtils.getOrCreateByPath(folderNode.getPath() + "/" + fileName, JcrConstants.FILE, session);

			fileNode.setProperty(JcrConstants.NAME, fileName);
			Node contentNode = JcrUtils.getOrCreateByPath(fileNode.getPath() + "/" + JcrConstants.CONTENT,
					JcrConstants.CONTENT_TYPE, session);

			contentNode.setProperty(JcrConstants.SIZE, size);
			contentNode.setProperty(JcrConstants.AUTHOR, session.getUserID());
			// contentNode.setProperty(Document.VERSION_COMMENT, "");
			contentNode.setProperty(JcrConstants.JCR_MIMETYPE, mimeType);

			// jcr:encoding only have sense for text/* MIME
			if (extName.startsWith("text/")) {
				contentNode.setProperty(JcrConstants.JCR_ENCODING, "UTF-8");
			}
			Binary binary = session.getValueFactory().createBinary(is);

			contentNode.setProperty(JcrConstants.JCR_DATA, binary);
			contentNode.setProperty(JcrConstants.JCR_LASTMODIFIED, Calendar.getInstance());
			session.save();
		} catch (Exception e) {
			logger.debug("save file error" + e.getMessage());
			e.printStackTrace();
			throw (new JcrException());
		}
		return fileNode;
	}

	/**
	 * 上传引用文件，会有一份原始文件保存在固定下面，所有引用文件全部删除，原始文件才被删除
	 * 
	 * @param moduleName
	 * @param nodeName
	 * @param file
	 */
	@Override
	public void uploadRefenceFile(String moduleName, String nodeName, FileEntity file) {
		// 将文件上传到多文件目录下

		String uuid = UUID.randomUUID().toString();
		this.referenceFile(moduleName, moduleName + "_" + JcrConstants.MULTI_FILE_SOURCE + "_" + uuid,
				file.getFilename(), nodeName);

	}

	/**
	 * 
	 * 将某个节点下的所有文件与另外节点关联，这里可以理解为将某个文件在另外的节点创建 关联
	 * 
	 * @param modulename
	 * @param nodename
	 * @param newnodename
	 */
	@Override
	public void referenceFiles(String moduleName, String nodeName, String newnodeName) {
		List<FileEntity> files = this.getFiles(moduleName, nodeName);
		for (FileEntity file : files) {
			this.referenceFile(moduleName, nodeName, file.getFilename(), newnodeName);
		}

	}

	/**
	 * 将某个节点下的文件与另外节点关联，这里可以理解为将某个文件在另外的节点创建 关联
	 * 
	 * @param moduleName
	 * @param nodeName
	 * @param newnodeName
	 * @param fileName
	 */
	@Override
	public synchronized void referenceFile(String moduleName, String nodeName, String fileName, String newnodeName) {
		try {

			Session session = JcrRepositoryHelper.getSystemSession();

			String path = this.getModuleNodePath(moduleName, nodeName);
			Node folderNode = this.getFolderNode(path, session);
			// The nt:file node the new link will point to*
			Node fileToLink;

			if (session.nodeExists(path + "/" + fileName)) {

				Node fileNode = session.getNode(path + "/" + fileName);
				if (fileNode.isNodeType(JcrConstants.FILE)) {
					fileToLink = session.getNode(path + "/" + fileName + "/" + JcrConstants.CONTENT);
					Node newFolderNode = this.getFolderNode(getModuleNodePath(moduleName, newnodeName), session);

					fileToLink = session.getNode(path + "/" + fileName + "/" + JcrConstants.CONTENT);

					Node newLink = JcrUtils.getOrAddNode(newFolderNode, fileName, JcrConstants.NT_LINKEDFILE);
					// Adding the content as a reference to the "real" content*
					// newLink.setProperty("jcr:content", fileToLink);
					newLink.setProperty(JcrConstants.JCR_CONTENT, fileToLink);
					session.save();
				} else if (fileNode.isNodeType(JcrConstants.NT_LINKEDFILE)) {
					// fileNode.remove();
					PropertyIterator pi = fileNode.getProperties(JcrConstants.JCR_CONTENT);
					Node contentNode;
					while (pi.hasNext()) {
						Property pro = (Property) pi.next();
						contentNode = pro.getNode();
						Node newFolderNode = this.getFolderNode(getModuleNodePath(moduleName, newnodeName), session);

						Node newLink = JcrUtils.getOrAddNode(newFolderNode, fileName, JcrConstants.NT_LINKEDFILE);
						// Adding the content as a reference to the "real"
						// content*
						// newLink.setProperty("jcr:content", fileToLink);
						newLink.setProperty(JcrConstants.JCR_CONTENT, contentNode);
						session.save();

						break;
					}
				}

			}

			// Creating the link node...*

		} catch (RepositoryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void convert2SWF(List<java.io.File> files) {
		if (null == files || files.size() == 0) {
			return;
		}
		List<java.io.File> dosFiles = new ArrayList<java.io.File>();

		for (int i = 0; i < files.size(); i++) {
			dosFiles.add(files.get(i));
			// 每20个文档一个处理线程
			if (i % 20 == 0) {

				new Thread(new FileUploadHandler(dosFiles.subList(i, i == 0 ? i + 1 : (i % 20 + 1) * 20))).start();
				// tempFiles.clear();
			}
		}
	}

	@Override
	public FileEntity convert2SWF(InputStream fileStream, String fileName) {
		// new Thread(new FileUploadHandler(fileStream, fileName));
		FileUploadHandler handler = new FileUploadHandler();
		return handler.test(fileStream, fileName);
	}

	/** 
	 * 如何描述该方法
	 * 
	 * (non-Javadoc)
	 * @see com.wellsoft.pt.repository.service.FileService#convert2PDF(java.io.InputStream, java.lang.String)
	 */
	@Override
	public void convert2PDF(InputStream fileStream, String fileName) {
		// TODO Auto-generated method stub
		FileUploadHandler handler = new FileUploadHandler();
		handler.test2(fileStream, fileName);
	}

	/** 
	 * 如何描述该方法
	 * 
	 * (non-Javadoc)
	 * @see com.wellsoft.pt.repository.service.FileService#saveFileUpload(com.wellsoft.pt.dytable.support.FileUpload)
	 */
	@Override
	public void saveFileUpload(FileUpload fileUpload) {
		fileUploadDao.save(fileUpload);
	}

	/** 
	 * (non-Javadoc)
	 * @see com.wellsoft.pt.repository.service.FileService#setFileUploadSign(com.wellsoft.pt.repository.entity.FileUpload)
	 */
	@Override
	public void setFileUploadSign(FileUpload fileUpload) {
		FileUpload upload = this.fileUploadDao.get(fileUpload.getUuid());
		if (StringUtils.equals(upload.getDigestValue(), fileUpload.getDigestValue())) {
			String digestValue = fileUpload.getDigestValue();
			String signatureValue = fileUpload.getSignatureValue();
			String certificate = fileUpload.getCertificate();
			// 当前用户证书登录验证
			fjcaAppsService.checkCurrentCertificate(certificate);

			// 数字签名验证
			int retCode = FJCAUtils.verify(digestValue, signatureValue, certificate);
			if (retCode != 0) {
				throw new RuntimeException("附件[" + upload.getFilename() + "]数字签名验证失败!");
			}
			upload.setCertificate(certificate);
			upload.setSignatureValue(signatureValue);
		} else {
			throw new RuntimeException("无法对文件[" + upload.getFilename() + "]进行数字签名!");
		}
	}

	/**
	 * 文件上传查询
	 * 
	 * @param hql
	 * @param values
	 * @param itemClass
	 * @param pagingInfo
	 * @return
	 */
	public <ITEM extends Serializable> List<ITEM> queryFileUpload(String hql, final Map<String, ?> values,
			Class<ITEM> itemClass, PagingInfo pagingInfo) {
		return this.fileUploadDao.query(hql, values, itemClass, pagingInfo);
	}

	/** 
	 * 如何描述该方法
	 * 
	 * (non-Javadoc)
	 * @see com.wellsoft.pt.repository.service.FileService#getFiles2(java.lang.String, java.lang.String)
	 */
	@Override
	public List<FileEntity> getFiles2(String moduleName, String nodeName) {
		String path = this.getModuleNodePath(moduleName, nodeName);
		return this.get(path);
	}

	// private Node saveFile(File file, String fileName, Node folderNode,
	// Session session) {
	//
	// // 扩展名
	// String extName = "";
	// Node fileNode;
	//
	// try {
	// InputStream is = new BufferedInputStream(new FileInputStream(file));
	// long size = is.available();
	//
	// String mimeType = new MimetypesFileTypeMap().getContentType(file);
	// if (mimeType == null) {
	// mimeType = "application/msword";
	// }
	// if (fileName.lastIndexOf(".") >= 0) {
	// extName = fileName.substring(fileName.lastIndexOf("."));
	// }
	// // session = JcrRepositoryHelper.getSystemSession();
	// // Node folderNode = session.getNode("/" +
	// // JcrConstants.FOLDER_NODE_NAME + "/" + folderPath);
	// fileNode = folderNode.addNode(fileName, JcrConstants.FILE);
	// if (sessionService.getCurrentUser() != null) {
	// fileNode.setProperty(JcrConstants.AUTHOR, sessionService
	// .getCurrentUser().getUuid());
	// }
	// fileNode.setProperty(JcrConstants.NAME, fileName);
	//
	// Node contentNode = fileNode.addNode(JcrConstants.CONTENT,
	// JcrConstants.CONTENT_TYPE);
	// contentNode.setProperty(JcrConstants.SIZE, size);
	// contentNode.setProperty(JcrConstants.AUTHOR, session.getUserID());
	// // contentNode.setProperty(Document.VERSION_COMMENT, "");
	// contentNode.setProperty(JcrConstants.JCR_MIMETYPE, mimeType);
	//
	// // jcr:encoding only have sense for text/* MIME
	// if (extName.startsWith("text/")) {
	// contentNode.setProperty(JcrConstants.JCR_ENCODING, "UTF-8");
	// }
	// Binary binary = session.getValueFactory().createBinary(is);
	//
	// contentNode.setProperty(JcrConstants.JCR_DATA, binary);
	// contentNode.setProperty(JcrConstants.JCR_LASTMODIFIED,
	// Calendar.getInstance());
	// session.save();
	// } catch (Exception e) {
	// logger.debug("save file error" + e.getMessage());
	// e.printStackTrace();
	// return null;
	// }
	// return fileNode;
	// }
	//
	// /**
	// *
	// * <p>
	// * Title: createFile
	// * </p>
	// * <p>
	// * Description:将文件上传到jcr的目录下面
	// * </p>
	// *
	// * @param file
	// * 文件流
	// * @param fileName
	// * 文件名称
	// * @param folderPath
	// * 文件目录
	// * @return
	// * @throws IOException
	// * @see
	// com.FileService.oa.core.content.service.FileManager#createFile(java.io.File,
	// * java.lang.String, java.lang.String)
	// */
	// @Override
	// public String createFile(File file, String fileName, String folderPath)
	// throws IOException {
	// String nodename = UUID.randomUUID().toString();
	// String extName = "";
	// Session session = null;
	// InputStream is = new BufferedInputStream(new FileInputStream(file));
	// try {
	// long size = is.available();
	//
	// String mimeType = new MimetypesFileTypeMap().getContentType(file);
	// if (mimeType == null) {
	// mimeType = "application/msword";
	// }
	// if (fileName.lastIndexOf(".") >= 0) {
	// extName = fileName.substring(fileName.lastIndexOf("."));
	// }
	// session = JcrRepositoryHelper.getSystemSession();
	// Node folderNode = session.getNode("/"
	// + JcrConstants.FOLDER_NODE_NAME + "/" + folderPath);
	// Node fileNode = folderNode.addNode(nodename, JcrConstants.FILE);
	// if (sessionService.getCurrentUser() != null) {
	// fileNode.setProperty(JcrConstants.AUTHOR, sessionService
	// .getCurrentUser().getUuid());
	// }
	// fileNode.setProperty(JcrConstants.NAME, fileName);
	//
	// Node contentNode = fileNode.addNode(JcrConstants.CONTENT,
	// JcrConstants.CONTENT_TYPE);
	// contentNode.setProperty(JcrConstants.SIZE, size);
	// contentNode.setProperty(JcrConstants.AUTHOR, session.getUserID());
	// // contentNode.setProperty(Document.VERSION_COMMENT, "");
	// contentNode.setProperty(JcrConstants.JCR_MIMETYPE, mimeType);
	//
	// // jcr:encoding only have sense for text/* MIME
	// if (extName.startsWith("text/")) {
	// contentNode.setProperty(JcrConstants.JCR_ENCODING, "UTF-8");
	// }
	// Binary binary = session.getValueFactory().createBinary(is);
	//
	// contentNode.setProperty(JcrConstants.JCR_DATA, binary);
	// contentNode.setProperty(JcrConstants.JCR_LASTMODIFIED,
	// Calendar.getInstance());
	// session.save();
	// } catch (Exception e) {
	// logger.debug("create file error" + e.getMessage());
	// e.printStackTrace();
	// return null;
	// }
	// return nodename;
	// }
	//
	// /**
	// *
	// * @Title: createFolder
	// * @Description: 建立目录，目录名称为uuid的名称
	// * @param @return 设定文件
	// * @return String 返回类型
	// * @throws
	// */
	// private Node createFolder(String uuid, Session session) {
	// // 通过uuid产生对应的name
	//
	// Node folderNode = null;
	// try {
	// // session = JcrRepositoryHelper.getSystemSession();
	// Node rootNode = session.getRootNode();
	// Node contentNode = rootNode.getNode(JcrConstants.FOLDER_NODE_NAME);
	// folderNode = contentNode.addNode(uuid, JcrConstants.FOLDER_TYPE);
	// session.save();
	// } catch (RepositoryException e) {
	// logger.debug("createfolder" + e.getMessage());
	// e.printStackTrace();
	//
	// }
	// return folderNode;
	// }

	// public InputStream getContent(String id, String folderPath, boolean
	// checkout)
	// throws Exception {
	//
	// Session session = null;
	// session = JcrRepositoryHelper.getSystemSession();
	// Node folderNode = session.getNode("/" + JcrConstants.FOLDER_NODE_NAME
	// + "/" + folderPath);
	// Node fileNode = folderNode.getNode(id);
	// Node conteNode = fileNode.getNode("jcr:content");
	// Property property = conteNode.getProperty(JcrConstants.JCR_DATA);
	//
	// return property.getBinary().getStream();
	// }
	//
	// @Override
	// public InputStream getContent(String id) throws Exception {
	// // TODO Auto-generated method stub
	// return null;
	// }
	//
	// @Override
	// public File getFile(String id) throws Exception {
	// // TODO Auto-generated method stub
	// return null;
	// }
	//
	// private String getFolderPath(String folderId) {
	// return "/" + JcrConstants.FOLDER_NODE_NAME + "/" + folderId;
	// }

	// @Override
	// public void delete(String id) {
	// // TODO Auto-generated method stub
	//
	// }
	//
	// @Override
	// public Document getDocumentByName(String username) {
	// // TODO Auto-generated method stub
	// return null;
	// }
	//
	// @Override
	// public Document getDocumentById(String id) {
	// // TODO Auto-generated method stub
	// return null;
	// }
	//
	// @Override
	// public void rename(String id, String newName) {
	// // TODO Auto-generated method stub
	//
	// }
	//
	// @Override
	// public Document getProperties(String docPath) {
	// // TODO Auto-generated method stub
	// return null;
	// }
	//
	// @Override
	// public void setProperties(Document doc) {
	// // TODO Auto-generated method stub
	//
	// }
	//
	// @Override
	// public InputStream getContentByVersion(String docPath, String versionId)
	// {
	// // TODO Auto-generated method stub
	// return null;
	// }
	//
	// @Override
	// public void setContent(String id, File file) throws Exception {
	// // TODO Auto-generated method stub
	//
	// }
	//
	// @Override
	// public void purge(String docPath) {
	// // TODO Auto-generated method stub
	//
	// }
	//
	// @Override
	// public List<Document> getChilds(String fldPath) {
	// try {
	//
	// Session session = JcrRepositoryHelper.getSystemSession();
	// Node folderNode = session.getNode(fldPath);
	// NodeIterator iterator = folderNode.getNodes();
	//
	// } catch (Exception ex) {
	//
	// }
	// return null;
	// }
	//
	// @Override
	// public void move(String id, String fldPath) throws Exception {
	// // TODO Auto-generated method stub
	//
	// }
	//
	// @Override
	// public void copy(String docPath, String fldPath) throws IOException {
	// // TODO Auto-generated method stub
	//
	// }
	//
	// @Override
	// public String getPath(String uuid) {
	// // TODO Auto-generated method stub
	// return null;
	// }
	//
	// @Override
	// public boolean isValid(String docPath) {
	// // TODO Auto-generated method stub
	// return false;
	// }

	// @Override
	// public String create(File file, String fileName, String folderPath)
	// throws IOException {
	// // TODO Auto-generated method stub
	// return null;
	// }
	//
	// @Override
	// public InputStream getContent(String id, boolean checkout) throws
	// Exception {
	// // TODO Auto-generated method stub
	// return null;
	// }

	// @Override
	// public void uploadFile(String uuid, File... files) {
	//
	// try {
	// Session session = JcrRepositoryHelper.getSystemSession();
	//
	// // 根据uuid建立目录节点
	//
	// Node foldernode = this.createFolder(uuid, session);
	//
	// if (foldernode != null) {
	// if (files != null) {
	// for (int i = 0; i < files.length; i++) {
	// saveFile(files[i], files[i].getName(), foldernode,
	// session);
	// }
	// }
	// }
	// } catch (RepositoryException e) {
	// logger.debug("open repository" + e.getMessage());
	// }
	// }

	/**
	 * 如何描述该方法
	 * 
	 */
	// @Override
	// public void uploadFile(String uuid, List<File> filelist) {
	// try {
	// Session session = JcrRepositoryHelper.getSystemSession();
	//
	// // 根据uuid建立目录节点
	//
	// Node foldernode = this.createFolder(uuid, session);
	// if (foldernode != null) {
	// if (filelist != null) {
	// for (int i = 0; i < filelist.size(); i++) {
	// saveFile(filelist.get(i), filelist.get(i).getName(),
	// foldernode, session);
	// }
	// }
	// }
	// } catch (RepositoryException e) {
	// logger.debug("open repository" + e.getMessage());
	// }
	//
	// }

	// @Override
	// public Map<String, InputStream> downFiles(String uuid) {
	// Map<String, InputStream> filemap = Maps.newHashMap();
	// InputStream in = null;
	// try {
	//
	// Session session = JcrRepositoryHelper.getSystemSession();
	// Node folderNode = session.getNode("/"
	// + JcrConstants.FOLDER_NODE_NAME + "/" + uuid);
	// NodeIterator entries = folderNode.getNodes();
	// while (entries.hasNext()) {
	// Node entry = entries.nextNode();
	// if (entry.isNodeType(JcrConstants.FILE)) {
	// Node contentNode = entry.getNode(JcrConstants.CONTENT);
	// Binary binary = contentNode.getProperty(
	// JcrConstants.JCR_DATA).getBinary();
	// filemap.put(entry.getName(),
	// new BufferedInputStream(binary.getStream()));
	// }
	// }
	// } catch (Exception e) {
	// // TODO: handle exception
	// }
	// return filemap;
	// }

}
