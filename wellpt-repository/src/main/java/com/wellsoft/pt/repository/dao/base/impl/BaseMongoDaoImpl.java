package com.wellsoft.pt.repository.dao.base.impl;

 
 
import java.io.InputStream;
 
import java.net.UnknownHostException;
 
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
 
import java.util.Map;
 
 
import java.util.List;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;

 
 
 
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.WriteResult;
 
 
 
import com.mongodb.Mongo;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSInputFile;
 
import com.wellsoft.pt.dytable.utils.DynamicUtils;
import com.wellsoft.pt.repository.dao.base.BaseMongoDao;
import com.wellsoft.pt.repository.entity.mongo.file.MongoFileEntity;
import com.wellsoft.pt.repository.support.enums.EnumQueryPattern;
import com.wellsoft.pt.core.resource.Config;
 
@Repository
public class BaseMongoDaoImpl implements BaseMongoDao{
	
	/*private String serverURL = "";
	private String serverPort = "";
	private String serverDbName = "";
	private String serverUsername = "";
	private String serverPassword = "";*/
	
	private  Mongo mongo;
	private  DB db ;
	
	/**
	 * @param host
	 * @param port
	 * @param dbname
	 * @throws UnknownHostException
	 */
	public BaseMongoDaoImpl(){
		
	}
	
	public BaseMongoDaoImpl(String host, int port, String dbName)throws UnknownHostException{
		this.mongo = new Mongo(host, port);
		this.db =  this.mongo.getDB(dbName); 
	}
	
	@PostConstruct
	public void init() throws UnknownHostException{
		String serverURL = Config.getValue("mongodb.server.url");
		String serverPort = Config.getValue("mongodb.server.port");
		String serverDbName = Config.getValue("mongodb.server.dbname");
		//String serverUsername = Config.getValue("mongodb.server.username");
		//String serverPassword = Config.getValue("mongodb.server.password");
		this.mongo = new Mongo(serverURL, Integer.parseInt(serverPort));
		this.db =  this.mongo.getDB(serverDbName); 
	}

	 
	public MongoFileEntity saveFile(String filePath, String fileName, String contentType, InputStream inputStream, String id) { 
		 Assert.notNull(filePath,"parameter[filePath] is null");
		 Assert.notNull(fileName,"parameter[fileName]  is null");
		 
		 Assert.notNull(contentType,"parameter[contentType]  is null");
		 Assert.notNull(inputStream,"parameter[inputStream]  is null");
		 
		
		if(filePath == null) filePath = "fs";
		GridFS gridFS = new GridFS(db, filePath);
		GridFSInputFile gfsFile = gridFS.createFile(inputStream);
		gfsFile.setFilename(fileName);
		gfsFile.setContentType(contentType); 
		if(id == null){
			id = DynamicUtils.getRandomUUID();
			
		} 
		
		gfsFile.setId(id);//这里的id千万不能为null，否则会出现乱码
		gfsFile.put("uploadDate", new Date());
		
		gfsFile.save();
		//String id = gfsFile.getId().toString();
		
		GridFSDBFile dbFile =gridFS.findOne(new BasicDBObject("_id", id));
		
		MongoFileEntity entity = new MongoFileEntity(dbFile); 
	 
		 
		return entity;
		
		
 
		
	 
	
		
	}


	@Override
	public MongoFileEntity findFileById(String filePath, String id)   {
		 Assert.notNull(filePath,"parameter[filePath] is null");
		 Assert.notNull(id,"parameter[id]  is null");
		
		GridFS  gridFS = new GridFS(db, filePath );
		
		GridFSDBFile dbFile = gridFS.findOne(new BasicDBObject("_id", id));
		 if(dbFile == null){
			return null;
		 }
		 
		 
		MongoFileEntity entity = new MongoFileEntity(dbFile); 
		return entity;
	}

	
	@Override
	public List<MongoFileEntity> findFileByName(String filePath, String fileName, EnumQueryPattern qpattern) {
		 Assert.notNull(filePath,"parameter[filePath] is null");
		 Assert.notNull(qpattern,"parameter[qpattern]  is null");
		GridFS  gridFS = new GridFS(db, filePath );
		BasicDBObject  query = new BasicDBObject(); 
		Pattern pattern = Pattern.compile(
				EnumQueryPattern.getMatchPatternStr(qpattern, fileName),
				Pattern.CASE_INSENSITIVE);
		query.append("filename", pattern); 
		List<GridFSDBFile> dbFiles = gridFS.find(query);
		List<MongoFileEntity> files = new ArrayList<MongoFileEntity>();
		for(GridFSDBFile dbFile: dbFiles){
			MongoFileEntity file = new MongoFileEntity(dbFile);
			files.add(file);
		}
		 
		 return files;
		 
	}

 

	@Override
	public void deleteFileById(String filePath, String id) {
		 Assert.notNull(filePath,"parameter[filePath] is null");
		 Assert.notNull(id,"parameter[id]  is null");
		GridFS  gridFS = new GridFS(db, filePath );
		gridFS.remove(new BasicDBObject("_id", id));
	}


	@Override
	public void deleteFileByFileName(String filePath, String fileName) {
		 Assert.notNull(filePath,"parameter[filePath] is null");
		 Assert.notNull(fileName,"parameter[fileName]  is null");
		GridFS  gridFS = new GridFS(db, filePath );
		gridFS.remove(fileName);
		
	}
	

	@Override
	public DBObject findDocumentById(String collectionName, String id) {
		 Assert.notNull(collectionName,"parameter[collectionName] is null");
		 Assert.notNull(id,"parameter[id]  is null");
		DBCollection dbCollection = db.getCollection(collectionName);
		 
		return dbCollection.findOne(new BasicDBObject("_id", id));
	}

	
	public DBCursor findDocument(String collectionName, DBObject query) {
		 Assert.notNull(collectionName,"parameter[collectionName] is null");
		 Assert.notNull(query,"parameter[query]  is null");
		DBCollection dbCollection = db.getCollection(collectionName);
		DBCursor dbCursor = dbCollection.find(query);
		return dbCursor;
	}


	/** 
	 * 
	 * 
	 * (non-Javadoc)
	 * @see com.wellsoft.pt.repository.dao.base.impl.well.mongodb.dao.base.BaseMongoDao#createDocument(java.lang.String, java.util.Map)
	 */
	@Override
	public DBObject createDocument(String collectionName, Map<String, Object> params, String id) {
		 Assert.notNull(collectionName,"parameter[collectionName] is null");
		 Assert.notNull(params,"parameter[params]  is null");
		 
		if(params == null || collectionName == null){
			return null;
		}
		 
		BasicDBObject obj = new BasicDBObject();
		obj.putAll(params);
		
		 if(id == null){
			 id = DynamicUtils.getRandomUUID();
		}
		 
		 obj.put("_id",  id ); 
		
		DBObject dbObj = this.createDocument(collectionName, obj);
		
		return dbObj;
	}
	
	@Override
	public DBObject createDocument(String collectionName, DBObject dbObject) { 
		 Assert.notNull(collectionName,"parameter[collectionName]  is null");
		 Assert.notNull(dbObject,"parameter[dbObject]  is null");
		 
		 
		if(collectionName == null){
			return null;
		}
		
		DBCollection dbCollection = db.getCollection(collectionName);
		 if(dbObject == null){
			 return null;
		 }
		dbCollection.insert(dbObject); 
		return dbObject;
	}

	
	/** 
	 * 
	 * 
	 * (non-Javadoc)
	 * @see com.wellsoft.pt.repository.dao.base.impl.well.mongodb.dao.base.BaseMongoDao#createDocument(java.lang.String, java.util.Map)
	 */
	@Override
	public int  updateDocument(String collectionName, Map<String, Object> params, String id) {
		 
		 Assert.notNull(collectionName,"parameter[collectionName]  is null");
		 Assert.notNull(params,"parameter[params]  is null");
		 Assert.notNull(id,"parameter[id]  is null");
		  
	 
		BasicDBObject obj = new BasicDBObject();
		obj.putAll(params);
		
		 
		return this.updateDocument(collectionName, obj, new BasicDBObject("_id",   id ));
		
		//return this.findDocument(collectionName, new BasicDBObject("_id",  id ));
	}


	@Override
	public int  updateDocument(String collectionName, DBObject dbObject, DBObject query) { 
		 
		 Assert.notNull(collectionName,"parameter[collectionName]  is null");
		 Assert.notNull(dbObject,"parameter[dbObject]  is null");
		 Assert.notNull(query,"parameter[query]  is null");
		 dbObject.removeField("_id");
		DBCollection dbCollection = db.getCollection(collectionName); 
		WriteResult result = dbCollection.update(query, dbObject);
		return result.getN();
	}
	


	@Override
	public int  updateDocument(String collectionName, DBObject dbObject,
			String id) {
		 Assert.notNull(collectionName,"parameter[collectionName]  is null");
		 Assert.notNull(dbObject,"parameter[dbObject]  is null");
		 Assert.notNull(id,"parameter[id]  is null");
		//DBCollection dbCollection = db.getCollection(collectionName); 
		//dbCollection.update(new BasicDBObject("_id", new Id(id)), dbObject); 
		return this.updateDocument(collectionName, dbObject, new BasicDBObject("_id",  id ));
		//return this.findDocument(collectionName, new BasicDBObject("_id",  id ));
	}
	
	@Override
	public void deleteDocumentById(String collectionName, String id) {
		 Assert.notNull(collectionName,"parameter[collectionName]  is null");
		 Assert.notNull(id,"parameter[id]  is null");
		 
		DBCollection dbCollection = db.getCollection(collectionName);
		 
		dbCollection.remove(new BasicDBObject("_id",  id )); 
	}


	@Override
	public DBObject findOneDocument(String collectionName, DBObject query) {
		 Assert.notNull(collectionName,"parameter[collectionName]  is null");
		 Assert.notNull(query,"parameter[query]  is null");
		 
		
		DBCollection dbCollection = db.getCollection(collectionName);
		DBObject dbObj = dbCollection.findOne(query);
		return dbObj;
	}

	@Override
	public MongoFileEntity findOneFile  (String filePath, DBObject query) {
		 Assert.notNull(filePath,"parameter[filePath] is null");
		 Assert.notNull(query,"parameter[query]  is null");
		
		GridFS  gridFS = new GridFS(db, filePath );
		
		GridFSDBFile dbFile = gridFS.findOne(query);
		 if(dbFile == null){
			return null;
		 }
		 
		 
		MongoFileEntity entity = new MongoFileEntity(dbFile); 
		return entity;
	}

	@Override
	public List<GridFSDBFile> findProtoFiles(String filePath, String synbeforedays) {
		GridFS  gridFS = new GridFS(db, filePath );
		BasicDBObject  query = new BasicDBObject();
		Calendar cl = new GregorianCalendar();
		cl.setTime(new Date());
		cl.add(Calendar.DAY_OF_YEAR, Integer.parseInt(synbeforedays) * -1);
		query.put("uploadDate", new BasicDBObject("$gt", cl.getTime()));
		List<GridFSDBFile> dbFiles = gridFS.find(query);
		return dbFiles;
	}
	
	


	






 

	 
	
	

}
