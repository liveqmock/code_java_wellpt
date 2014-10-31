-- Create table
create table PLANVIEW
(
  uuid        VARCHAR2(255 CHAR) not null,
  create_time TIMESTAMP(6),
  creator     VARCHAR2(255 CHAR),
  modifier    VARCHAR2(255 CHAR),
  modify_time TIMESTAMP(6),
  rec_ver     NUMBER(10),
  wl          VARCHAR2(255 CHAR),
  shortdesc   VARCHAR2(255 CHAR),
  factory     VARCHAR2(255 CHAR),
  scstore     VARCHAR2(255 CHAR),
  wbstore     VARCHAR2(255 CHAR),
  pricejhjgrq VARCHAR2(255 CHAR),
  pricejhjg   VARCHAR2(255 CHAR),
  ficokj1jgdw VARCHAR2(255 CHAR),
  ppmrp1srz   VARCHAR2(255 CHAR),
  ppmrp2cglx  VARCHAR2(255 CHAR),
  status      VARCHAR2(255 CHAR)
)
tablespace OA_DATA
  pctfree 10
  pctused 40
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
alter table PLANVIEW
  add constraint PLANVIEWPRIMARY primary key (UUID)
  using index 
  tablespace OA_DATA
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
