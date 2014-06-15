<?xml version="1.0"?>

<!DOCTYPE Repository PUBLIC "-//The Apache Software Foundation//DTD Jackrabbit 2.0//EN"
                            "http://jackrabbit.apache.org/dtd/repository-2.4.dtd">

<Repository>

    <DataSources>
        <DataSource name="${tenant}">
            <param name="driver" value="${jdbc_driver}"/>
            <param name="url" value="${jdbc_url}"/>
            <param name="user" value="${jdbc_username}"/>
            <param name="password" value="${jdbc_password}"/> 
            <param name="databaseType" value="${jdbc_type}"/>
            <param name="maxPoolSize" value="10"/>
        </DataSource>
    </DataSources>

 
      <FileSystem class="org.apache.jackrabbit.core.fs.db.MSSqlFileSystem">
        <param name="dataSourceName" value="${tenant}"/>      
         <param name="schemaObjectPrefix" value="JCR_REP_" />
    </FileSystem>
 
   
    <DataStore class="org.apache.jackrabbit.core.data.db.DbDataStore">
        <param name="dataSourceName" value="${tenant}"/>
        <param name="minRecordLength" value="1024" />
        <param name="copyWhenReading" value="true" />
        <param name="tablePrefix" value="JCR_" />
        <param name="schemaObjectPrefix" value="DS_" />
    </DataStore>

  

     <Security appName="Jackrabbit">
       
        <SecurityManager class="org.apache.jackrabbit.core.security.simple.SimpleSecurityManager" workspaceName="security">
          
            <!-- <WorkspaceAccessManager class="..."/> -->
            <!-- <param name="config" value="${r"${rep.home}"}/${tenant}/security.xml"/> -->
        </SecurityManager>

       
        <AccessManager class="org.apache.jackrabbit.core.security.simple.SimpleAccessManager">
            <!-- <param name="config" value=${r"${rep.home}"}/${tenant}/access.xml"/> -->
        </AccessManager>

        <LoginModule class="org.apache.jackrabbit.core.security.simple.SimpleLoginModule">
          
           <param name="anonymousId" value="anonymous"/>
          
           <param name="adminId" value="admin"/>
        </LoginModule>
    </Security>

   
    <Workspaces rootPath="${r"${rep.home}"}/${tenant}/workspaces" defaultWorkspace="default" maxIdleTime="2"/>
    
    <Workspace name="${r"${wsp.name}"}">
       
      
          <FileSystem class="org.apache.jackrabbit.core.fs.db.MSSqlFileSystem">
            <param name="dataSourceName" value="${tenant}"/>
            <param name="schemaObjectPrefix" value="${r"${wsp.name}_"}" />
        </FileSystem>

       
        <PersistenceManager class="org.apache.jackrabbit.core.persistence.pool.MSSqlPersistenceManager">
            <param name="dataSourceName" value="${tenant}"/>
            <param name="schemaObjectPrefix" value="JCR_WSP_Per" />
        </PersistenceManager>
      
        <SearchIndex class="org.apache.jackrabbit.core.query.lucene.SearchIndex">
            <param name="path" value="${r"${wsp.home}"}/index"/>
        </SearchIndex>
    </Workspace>

   
    <Versioning rootPath="${r"${rep.home}"}/${tenant}/version">
       
        <FileSystem class="org.apache.jackrabbit.core.fs.db.MSSqlFileSystem">
            <param name="dataSourceName" value="${tenant}"/>
             <param name="schemaObjectPrefix" value="JCR_version_File" />
        </FileSystem>

       
        <PersistenceManager class="org.apache.jackrabbit.core.persistence.pool.MSSqlPersistenceManager">
            <param name="dataSourceName" value="${tenant}"/>
            <param name="schemaObjectPrefix" value="JCR_version_Per" />
        </PersistenceManager>
    </Versioning>

   
    <SearchIndex class="org.apache.jackrabbit.core.query.lucene.SearchIndex">
        <param name="path" value="${r"${rep.home}"}/${tenant}/repository/index"/>
    </SearchIndex>
    
  

</Repository>
