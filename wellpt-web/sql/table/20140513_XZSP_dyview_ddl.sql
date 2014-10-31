-- Add/modify columns
alter table dyview_view_definition modify DEFAULT_CONDITION VARCHAR2(2000 CHAR);

-- Create table
create table DYVIEW_SELECT_EXACTKEY
(
  UUID            VARCHAR2(255 CHAR) not null,
  CREATE_TIME     TIMESTAMP(6),
  CREATOR         VARCHAR2(255 CHAR),
  MODIFIER        VARCHAR2(255 CHAR),
  MODIFY_TIME     TIMESTAMP(6),
  REC_VER         NUMBER(10),
  KEY_NAME        VARCHAR2(255 CHAR),
  KEY_VALUE       VARCHAR2(255 CHAR),
  SELECT_DEF_UUID VARCHAR2(255 CHAR) not null,
  KEY_UUID        VARCHAR2(255 CHAR)
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 16K
    next 8K
    minextents 1
    maxextents unlimited
  );
-- Create/Recreate primary, unique and foreign key constraints 
alter table DYVIEW_SELECT_EXACTKEY
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
alter table DYVIEW_SELECT_EXACTKEY
  add constraint FK_3121WH0M1EBQU16YNCROT60F9 foreign key (SELECT_DEF_UUID)
  references DYVIEW_SELECT_DEFINITION (UUID);
  
 alter table dyview_select_definition  add VAGUE_KEY_SELECT NUMBER(1);
 alter table dyview_select_definition  add EXACT_KEY_SELECT NUMBER(1);
