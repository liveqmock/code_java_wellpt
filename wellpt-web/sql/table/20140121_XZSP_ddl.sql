-- Create table
create table DYVIEW_COLUMNCSS_DEFINITION
(
  uuid                   VARCHAR2(255 CHAR) not null,
  create_time            TIMESTAMP(6),
  creator                VARCHAR2(255 CHAR),
  modifier               VARCHAR2(255 CHAR),
  modify_time            TIMESTAMP(6),
  rec_ver                NUMBER(10),
  column_condition       VARCHAR2(255 CHAR),
  column_condition_value VARCHAR2(255 CHAR),
  font_color             VARCHAR2(255 CHAR),
  font_wide              VARCHAR2(255 CHAR),
  view_column            VARCHAR2(255 CHAR),
  view_def_uuid          VARCHAR2(255 CHAR) not null,
  css_id                 VARCHAR2(255 CHAR)
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 16
    next 8
    minextents 1
    maxextents unlimited
  );
-- Create/Recreate primary, unique and foreign key constraints 
alter table DYVIEW_COLUMNCSS_DEFINITION
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
alter table DYVIEW_COLUMNCSS_DEFINITION
  add constraint FK32A53C4AC89212D5 foreign key (VIEW_DEF_UUID)
  references DYVIEW_VIEW_DEFINITION (UUID);
