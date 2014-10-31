-- Create table
create table EXCEL_EXPORT_COLUMN_DEFINITION
(
  uuid                         VARCHAR2(255 CHAR) not null,
  create_time                  TIMESTAMP(6),
  creator                      VARCHAR2(255 CHAR),
  modifier                     VARCHAR2(255 CHAR),
  modify_time                  TIMESTAMP(6),
  rec_ver                      NUMBER(10),
  attribute_name               VARCHAR2(255 CHAR),
  column_num                   NUMBER(10),
  excel_export_definition_uuid VARCHAR2(255 CHAR),
  value_type                   VARCHAR2(255 CHAR),
  title_name                   VARCHAR2(255 CHAR)
)
tablespace USERS
  pctfree 10
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
alter table EXCEL_EXPORT_COLUMN_DEFINITION
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
create table EXCEL_EXPORT_DEFINITION
(
  uuid        VARCHAR2(255 CHAR) not null,
  create_time TIMESTAMP(6),
  creator     VARCHAR2(255 CHAR),
  modifier    VARCHAR2(255 CHAR),
  modify_time TIMESTAMP(6),
  rec_ver     NUMBER(10),
  code        VARCHAR2(255 CHAR),
  file_uuid   VARCHAR2(255 CHAR),
  id          VARCHAR2(255 CHAR),
  name        VARCHAR2(255 CHAR),
  start_row   NUMBER(10),
  view_uuid   VARCHAR2(255 CHAR),
  view_name   VARCHAR2(255 CHAR)
)
tablespace USERS
  pctfree 10
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
alter table EXCEL_EXPORT_DEFINITION
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
  
  
alter table EXCEL_EXPORT_COLUMN_DEFINITION
add constraint FK_4RX1WAUA949UKJWP2OVN4KFX7 foreign key (EXCEL_EXPORT_DEFINITION_UUID)
references EXCEL_EXPORT_DEFINITION (UUID);

alter table dyview_view_definition add DATA_MODULE_ID VARCHAR2(255 CHAR);
alter table dyview_view_definition add DATA_MODULE_NAME VARCHAR2(255 CHAR);
