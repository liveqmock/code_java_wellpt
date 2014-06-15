  
  
-- Create table
create table REPO_FOLDER
(
  UUID        VARCHAR2(50) not null,
  CREATE_TIME TIMESTAMP(6),
  CREATOR     VARCHAR2(50),
  REMARK      VARCHAR2(500),
  MODIFY_TIME TIMESTAMP(6),
  MODIFIER    VARCHAR2(50),
  REC_VER     NUMBER(10)
) ;
-- Add comments to the columns 
comment on column REPO_FOLDER.UUID
  is '文件夹ID';
comment on column REPO_FOLDER.CREATE_TIME
  is '创建时间';
comment on column REPO_FOLDER.CREATOR
  is '创建者';
comment on column REPO_FOLDER.REMARK
  is '备注';
comment on column REPO_FOLDER.MODIFY_TIME
  is '最后修改时间';
comment on column REPO_FOLDER.MODIFIER
  is '修改人';
-- Create/Recreate primary, unique and foreign key constraints 
alter table REPO_FOLDER
  add constraint REPO_FOLDER_ID_KEY primary key (UUID)
   ;

  
-- Create table
create table REPO_FILE
(
  UUID             VARCHAR2(50) not null,
  CREATE_TIME      TIMESTAMP(6),
  CREATOR          VARCHAR2(50),
  MODIFY_TIME      TIMESTAMP(6),
  MODIFIER         VARCHAR2(50),
  DIGEST_VALUE     VARCHAR2(1000),
  DIGEST_ALGORITHM VARCHAR2(1000),
  SIGNATURE_VALUE  VARCHAR2(1000),
  CERTIFICATE      VARCHAR2(3000),
  SWF_UUID         VARCHAR2(50),
  REC_VER          NUMBER(10)
) ;
-- Add comments to the columns 
comment on column REPO_FILE.UUID
  is 'Id';
comment on column REPO_FILE.SWF_UUID
  is 'Swf副本';
-- Create/Recreate primary, unique and foreign key constraints 
alter table REPO_FILE
  add constraint REPO_FILE_KEY primary key (UUID)
  ;

  
  
  
  
  -- Create table
create table REPO_FILE_IN_FOLDER
(
  UUID        VARCHAR2(50) not null,
  FOLDER_UUID VARCHAR2(50),
  FILE_UUID   VARCHAR2(50),
  PURPOSE     VARCHAR2(50) not null,
  CREATE_TIME TIMESTAMP(6) not null,
  CREATOR     VARCHAR2(50) not null,
  MODIFY_TIME TIMESTAMP(6) not null,
  MODIFIER    VARCHAR2(50) not null,
  REC_VER     NUMBER(10)
) ;
-- Add comments to the columns 
comment on column REPO_FILE_IN_FOLDER.UUID
  is '文件夹文件关系ID';
comment on column REPO_FILE_IN_FOLDER.FOLDER_UUID
  is '文件夹ID,外键,参照repo_folder表中的UUID字段';
comment on column REPO_FILE_IN_FOLDER.FILE_UUID
  is '文件ID,外键，但他参照是mongodb文件表的_id字段';
comment on column REPO_FILE_IN_FOLDER.PURPOSE
  is '文件用途,一般为字段的名称，例如有一个字段是为附件，则该字段中的文件和文件夹关系就是保存在这个表中，其purpose就直接填写该附件字段名';
-- Create/Recreate primary, unique and foreign key constraints 
alter table REPO_FILE_IN_FOLDER
  add constraint REPO_FILE_IN_FOLDER_KEY primary key (UUID)
  ;

  
  
  
  -- Create table
create table REPO_FOLDER_OPERATE_LOG
(
  UUID         VARCHAR2(50) not null,
  FOLDER_UUID  VARCHAR2(50),
  OPERATE_TYPE VARCHAR2(5) not null,
  CREATE_TIME  TIMESTAMP(6) not null,
  CREATOR      VARCHAR2(50) not null,
  MODIFY_TIME  TIMESTAMP(6) not null,
  MODIFIER     VARCHAR2(50) not null,
  REC_VER      NUMBER(10),
  FILE_JSON    CLOB
)
 ;
-- Add comments to the columns 
comment on column REPO_FOLDER_OPERATE_LOG.UUID
  is '文件文件夹关系ID
只是作为主键，没有其他用途
';
comment on column REPO_FOLDER_OPERATE_LOG.FOLDER_UUID
  is '文件夹ID,外键,参照repo_folder表中的UUID字段';
comment on column REPO_FOLDER_OPERATE_LOG.OPERATE_TYPE
  is '参考枚举类EnumOperateType';
-- Create/Recreate primary, unique and foreign key constraints 
alter table REPO_FOLDER_OPERATE_LOG
  add constraint REPO_FOLDER_OPERATE_LOG_KEY primary key (UUID)
  ;

  
  
-- Add/modify columns 
alter table REPO_FILE add PHYSICAL_FILE_ID VARCHAR2(50);
-- Add comments to the columns 
comment on column REPO_FILE.PHYSICAL_FILE_ID
  is '实体文件ID';

  
-- Add/modify columns 
alter table REPO_FILE add FILE_NAME VARCHAR2(200);
alter table REPO_FILE add CONTENT_TYPE VARCHAR2(50);

-- Add/modify columns 
alter table REPO_FILE add FILE_SIZE long;
-- Add comments to the columns 
comment on column REPO_FILE.FILE_SIZE
  is '文件大小 ';

  
  