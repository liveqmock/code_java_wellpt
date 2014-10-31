-- Create table
create table PPVIEW
(
  uuid          VARCHAR2(255 CHAR) not null,
  create_time   TIMESTAMP(6),
  creator       VARCHAR2(255 CHAR),
  modifier      VARCHAR2(255 CHAR),
  modify_time   TIMESTAMP(6),
  rec_ver       NUMBER(10),
  wl            VARCHAR2(255 CHAR),
  shortdesc     VARCHAR2(255 CHAR),
  factory       VARCHAR2(255 CHAR),
  scstore       VARCHAR2(255 CHAR),
  wbstore       VARCHAR2(255 CHAR),
  ppmrp1srz     VARCHAR2(255 CHAR),
  ppmrp1type    VARCHAR2(255 CHAR),
  ppmrp1control VARCHAR2(255 CHAR),
  ppmrp1pldx    VARCHAR2(255 CHAR),
  ppmrp1group   VARCHAR2(255 CHAR),
  ppmrp2cglx    VARCHAR2(255 CHAR),
  ppmrp2tscgl   VARCHAR2(255 CHAR),
  ppmrp2fc      VARCHAR2(255 CHAR),
  ppmrp2zzscsj  VARCHAR2(255 CHAR),
  ppmrp2jhjhsj  VARCHAR2(255 CHAR),
  ppmrp2shclsj  VARCHAR2(255 CHAR),
  ppmrp2bjm     VARCHAR2(255 CHAR),
  ppmrp2aqkc    VARCHAR2(255 CHAR),
  ppmrp3clz     VARCHAR2(255 CHAR),
  ppmrp3xhms    VARCHAR2(255 CHAR),
  ppmrp3xqxhqj  VARCHAR2(255 CHAR),
  ppmrp3nxxhqj  VARCHAR2(255 CHAR),
  ppmrp3kyxjc   VARCHAR2(255 CHAR),
  ppmrp4bjfp    VARCHAR2(255 CHAR),
  ppmrp4dljz    VARCHAR2(255 CHAR),
  ppgzjhcswj    VARCHAR2(255 CHAR),
  ppgzjhscgly   VARCHAR2(255 CHAR),
  ppgzjhbzyc    VARCHAR2(255 CHAR),
  ppgzjhgdyc    VARCHAR2(255 CHAR),
  ppbstma       VARCHAR2(255 CHAR),
  status        VARCHAR2(255 CHAR)
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
alter table PPVIEW
  add constraint PPVIEWPRIMARY primary key (UUID)
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
