-- Create table
create table DATA_SOURCE_DEFINITION
(
  uuid                  VARCHAR2(255 CHAR) not null,
  create_time           TIMESTAMP(6),
  creator               VARCHAR2(255 CHAR),
  modifier              VARCHAR2(255 CHAR),
  modify_time           TIMESTAMP(6),
  rec_ver               NUMBER(10),
  acl_hql_data_text     VARCHAR2(255 CHAR),
  choose_data_id        VARCHAR2(255 CHAR),
  choose_data_text      VARCHAR2(255 CHAR),
  data_interface_id     VARCHAR2(255 CHAR),
  data_interface_name   VARCHAR2(255 CHAR),
  data_source_id        VARCHAR2(255 CHAR),
  data_source_name      VARCHAR2(255 CHAR),
  data_source_num       VARCHAR2(255 CHAR),
  data_source_type_id   VARCHAR2(255 CHAR),
  data_source_type_name VARCHAR2(255 CHAR),
  id                    VARCHAR2(255 CHAR),
  in_data_scope         VARCHAR2(255 CHAR),
  out_data_scope        VARCHAR2(255 CHAR),
  out_data_source_id    VARCHAR2(255 CHAR),
  out_data_source_name  VARCHAR2(255 CHAR),
  search_condition      VARCHAR2(255 CHAR),
  sql_or_hql_text       VARCHAR2(255 CHAR)
)
tablespace USERS
  pctfree 10
  pctused 40
  initrans 1
  maxtrans 255
  storage
  (
    initial 64
    next 1
    minextents 1
    maxextents unlimited
  );
-- Create/Recreate primary, unique and foreign key constraints 
alter table DATA_SOURCE_DEFINITION
  add primary key (UUID)
  using index 
  tablespace USERS
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );



-- Create table
create table DATA_SOURCE_COLUMN
(
  uuid             VARCHAR2(255 CHAR) not null,
  create_time      TIMESTAMP(6),
  creator          VARCHAR2(255 CHAR),
  modifier         VARCHAR2(255 CHAR),
  modify_time      TIMESTAMP(6),
  rec_ver          NUMBER(10),
  column_aliase    VARCHAR2(255 CHAR),
  column_data_type VARCHAR2(255 CHAR),
  column_name      VARCHAR2(255 CHAR),
  default_sort     VARCHAR2(255 CHAR),
  entity_name      VARCHAR2(255 CHAR),
  field_name       VARCHAR2(255 CHAR),
  is_export        VARCHAR2(255 CHAR),
  title_name       VARCHAR2(255 CHAR),
  source_def_uuid  VARCHAR2(255 CHAR) not null
)
tablespace USERS
  pctfree 10
  pctused 40
  initrans 1
  maxtrans 255
  storage
  (
    initial 64
    next 1
    minextents 1
    maxextents unlimited
  );
-- Create/Recreate primary, unique and foreign key constraints 
alter table DATA_SOURCE_COLUMN
  add primary key (UUID)
  using index 
  tablespace USERS
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table DATA_SOURCE_COLUMN
  add constraint FK_7KHDRPMHGBD8DXHF1J172R6BB foreign key (SOURCE_DEF_UUID)
  references DATA_SOURCE_DEFINITION (UUID);

 -- Create table
create table DATA_SOURCE_PROFILE
(
  uuid                     VARCHAR2(255 CHAR) not null,
  create_time              TIMESTAMP(6),
  creator                  VARCHAR2(255 CHAR),
  modifier                 VARCHAR2(255 CHAR),
  modify_time              TIMESTAMP(6),
  rec_ver                  NUMBER(10),
  data_source_profile_id   VARCHAR2(255 CHAR),
  data_source_profile_name VARCHAR2(255 CHAR),
  data_source_profile_num  VARCHAR2(255 CHAR),
  database_sid             VARCHAR2(255 CHAR),
  database_type            VARCHAR2(255 CHAR),
  host                     VARCHAR2(255 CHAR),
  id                       VARCHAR2(255 CHAR),
  out_data_source_type     VARCHAR2(255 CHAR),
  pass_word                VARCHAR2(255 CHAR),
  port                     VARCHAR2(255 CHAR),
  user_name                VARCHAR2(255 CHAR)
)
tablespace USERS
  pctfree 10
  pctused 40
  initrans 1
  maxtrans 255
  storage
  (
    initial 64
    next 1
    minextents 1
    maxextents unlimited
  );
-- Create/Recreate primary, unique and foreign key constraints 
alter table DATA_SOURCE_PROFILE
  add primary key (UUID)
  using index 
  tablespace USERS
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

  