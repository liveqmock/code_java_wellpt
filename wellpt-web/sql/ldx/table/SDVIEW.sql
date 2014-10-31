-- Create table
create table SDVIEW
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
  sdxszz      VARCHAR2(255 CHAR),
  sdfxqd      VARCHAR2(255 CHAR),
  sdxmlbz     VARCHAR2(255 CHAR),
  sdsfl       VARCHAR2(255 CHAR),
  sdkmszz     VARCHAR2(255 CHAR),
  sdkyxjc     VARCHAR2(255 CHAR),
  sdysz       VARCHAR2(255 CHAR),
  sdzzz       VARCHAR2(255 CHAR),
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
alter table SDVIEW
  add constraint SDVIEWPRIMARY primary key (UUID)
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
