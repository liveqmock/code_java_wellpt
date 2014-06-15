<?xml version="1.0"?>


<!DOCTYPE Repository
          PUBLIC "-//The Apache Software Foundation//DTD Jackrabbit 2.0//EN"
          "http://jackrabbit.apache.org/dtd/repository-2.4.dtd">

<!-- Example Repository Configuration File
     Used by
     - org.apache.jackrabbit.core.config.RepositoryConfigTest.java
     -
-->
<Repository>
   
    <FileSystem class="org.apache.jackrabbit.core.fs.local.LocalFileSystem">
        <param name="path" value="${r"${rep.home}"}/repository"/>
    </FileSystem>

    <!--
        data store configuration
    -->
    <DataStore class="org.apache.jackrabbit.core.data.FileDataStore"/>

    <!--
        security configuration
    -->
    <Security appName="Jackrabbit">
        <!--
            security manager:
            class: FQN of class implementing the JackrabbitSecurityManager interface
        -->
        <SecurityManager class="org.apache.jackrabbit.core.security.simple.SimpleSecurityManager" workspaceName="security">
            <!--
            workspace access:
            class: FQN of class implementing the WorkspaceAccessManager interface
            -->
            <!-- <WorkspaceAccessManager class="..."/> -->
            <!-- <param name="config" value="${r"${rep.home}"}/security.xml"/> -->
        </SecurityManager>

        <!--
            access manager:
            class: FQN of class implementing the AccessManager interface
        -->
        <AccessManager class="org.apache.jackrabbit.core.security.simple.SimpleAccessManager">
            <!-- <param name="config" value="${r"${rep.home}"}/access.xml"/> -->
        </AccessManager>

        <LoginModule class="org.apache.jackrabbit.core.security.simple.SimpleLoginModule">
           <!-- 
              anonymous user name ('anonymous' is the default value)
            -->
           <param name="anonymousId" value="anonymous"/>
           <!--
              administrator user id (default value if param is missing is 'admin')
            -->
           <param name="adminId" value="admin"/>
        </LoginModule>
    </Security>

    <!--
        location of workspaces root directory and name of default workspace
    -->
    <Workspaces rootPath="${r"${rep.home}"}/workspaces" defaultWorkspace="default"/>
    <!--
        workspace configuration template:
        used to create the initial workspace if there's no workspace yet
    -->
    <Workspace name="${r"${wsp.home}"}">
        <!--
            virtual file system of the workspace:
            class: FQN of class implementing the FileSystem interface
        -->
        <FileSystem class="org.apache.jackrabbit.core.fs.local.LocalFileSystem">
            <param name="path" value="${r"${wsp.home}"}"/>
        </FileSystem>
        <!--
            persistence manager of the workspace:
            class: FQN of class implementing the PersistenceManager interface
        -->
      
        
         <PersistenceManager class="org.apache.jackrabbit.core.persistence.bundle.BundleFsPersistenceManager">
          <!-- <param name="persistent" value="false"/>-->
        </PersistenceManager>
        <!--
            Search index and the file system it uses.
            class: FQN of class implementing the QueryHandler interface
        -->
     <!--     <SearchIndex class="org.apache.jackrabbit.core.query.lucene.SearchIndex">
            <param name="path" value="${r"${wsp.home}"}/index"/>
            <param name="supportHighlighting" value="true"/>
        </SearchIndex>-->
        
        
         <!--<SearchIndex class="org.apache.jackrabbit.core.query.lucene.SearchIndex">
            <param name="path" value="${r"${wsp.home}"}/index"/>
            <param name="extractorPoolSize" value="2"/>
            <param name="tikaConfigPath" value="${r"${wsp.home}"}/tika-config.xml"/>
            <param name="supportHighlighting" value="true"/>
        </SearchIndex>-->
        
         <SearchIndex class="org.apache.jackrabbit.core.query.lucene.SearchIndex">
            <param name="path" value="${r"${wsp.home}"}/index"/>
        </SearchIndex>
    </Workspace>

    <!--
        Configures the versioning
    -->
    <Versioning rootPath="${r"${rep.home}"}/version">
        <!--
            Configures the filesystem to use for versioning for the respective
            persistence manager
        -->
        <FileSystem class="org.apache.jackrabbit.core.fs.local.LocalFileSystem">
            <param name="path" value="${r"${rep.home}"}/version" />
        </FileSystem>

        <!--
            Configures the persistence manager to be used for persisting version state.
            Please note that the current versioning implementation is based on
            a 'normal' persistence manager, but this could change in future
            implementations.
        -->
        <!-- <PersistenceManager class="org.apache.jackrabbit.core.persistence.pool.DerbyPersistenceManager">
          <param name="url" value="jdbc:derby:/version/db;create=true"/>
          <param name="schemaObjectPrefix" value="version_"/>
        </PersistenceManager>-->
        
         <PersistenceManager class="org.apache.jackrabbit.core.persistence.bundle.BundleFsPersistenceManager">
         <!--   <param name="persistent" value="false"/>-->
        </PersistenceManager>
    </Versioning>

    <!--
        Search index for content that is shared repository wide
        (/jcr:system tree, contains mainly versions)
   
    <SearchIndex class="org.apache.jackrabbit.core.query.lucene.SearchIndex">
        <param name="path" value="${r"${rep.home}"}/repository/index"/>
         <param name="textFilterClasses" value="org.apache.jackrabbit.extractor.PlainTextExtractor,org.apache.jackrabbit.extractor.MsWordTextExtractor,org.apache.jackrabbit.extractor.MsExcelTextExtractor,org.apache.jackrabbit.extractor.MsPowerPointTextExtractor,org.apache.jackrabbit.extractor.PdfTextExtractor,org.apache.jackrabbit.extractor.OpenOfficeTextExtractor,org.apache.jackrabbit.extractor.RTFTextExtractor,org.apache.jackrabbit.extractor.HTMLTextExtractor,org.apache.jackrabbit.extractor.XMLTextExtractor"/>
            <param name="extractorPoolSize" value="2"/>
        <param name="supportHighlighting" value="true"/>
    </SearchIndex> -->
    
    
    <SearchIndex class="org.apache.jackrabbit.core.query.lucene.SearchIndex">
        <param name="path" value="${r"${rep.home}"}/repository/index"/>
    </SearchIndex>
    
     <!--  <SearchIndex class="org.apache.jackrabbit.core.query.lucene.SearchIndex">
            <param name="path" value="${r"${rep.home}"}/index"/>
            <param name="tikaConfigPath" value="${r"${rep.home}"}/tika-config.xml"/>
            <param name="supportHighlighting" value="true"/>
        </SearchIndex>-->
</Repository>
