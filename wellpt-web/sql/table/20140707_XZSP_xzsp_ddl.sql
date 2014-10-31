-- Create table
create table XZSP_DRAWING_REVIEW
(
  uuid         VARCHAR2(255 CHAR) not null,
  create_time  TIMESTAMP(6),
  creator      VARCHAR2(255 CHAR),
  modifier     VARCHAR2(255 CHAR),
  modify_time  TIMESTAMP(6),
  rec_ver      NUMBER(10),
  banjian_uuid VARCHAR2(255 CHAR),
  dept_id      VARCHAR2(255 CHAR),
  dept_name    VARCHAR2(255 CHAR),
  dept_tele    VARCHAR2(255 CHAR),
  instruct     VARCHAR2(255 CHAR),
  review_time  TIMESTAMP(6),
  project_name VARCHAR2(255 CHAR),
  project_id   VARCHAR2(255 CHAR),
  status       VARCHAR2(255 CHAR)
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64
    next 8
    minextents 1
    maxextents unlimited
  );
-- Create/Recreate primary, unique and foreign key constraints 
alter table XZSP_DRAWING_REVIEW
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
