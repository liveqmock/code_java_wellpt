-- Create table
create table IS_SYS_PROPERTIES
(
  UUID        VARCHAR2(255 CHAR) not null,
  CREATE_TIME TIMESTAMP(6),
  CREATOR     VARCHAR2(255 CHAR),
  MODIFIER    VARCHAR2(255 CHAR),
  MODIFY_TIME TIMESTAMP(6),
  REC_VER     NUMBER(10),
  PRO_CN_NAME VARCHAR2(2500 CHAR),
  PRO_EN_NAME VARCHAR2(255 CHAR),
  MODULE_ID   VARCHAR2(255 CHAR),
  PRO_VALUE   VARCHAR2(255 CHAR)
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 8K
    minextents 1
    maxextents unlimited
  );
-- Create/Recreate primary, unique and foreign key constraints 
alter table IS_SYS_PROPERTIES
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
