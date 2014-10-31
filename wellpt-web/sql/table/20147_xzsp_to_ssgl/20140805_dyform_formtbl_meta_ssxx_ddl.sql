----------------------------------------------------
-- Export file for user XZSP_42                   --
-- Created by Administrator on 2014/8/5, 13:41:13 --
----------------------------------------------------

spool 20140805_dyform_formtbl_meta_ssxx_ddl.log

prompt
prompt Creating table UF_SPGC_BJ
prompt =========================
prompt
create table  UF_SPGC_BJ
(
  UUID        VARCHAR2(255 CHAR) not null,
  CREATOR     VARCHAR2(255 CHAR),
  CREATE_TIME TIMESTAMP(6),
  MODIFIER    VARCHAR2(255 CHAR),
  MODIFY_TIME TIMESTAMP(6),
  REC_VER     NUMBER(10),
  FORM_UUID   VARCHAR2(255 CHAR),
  STATUS      VARCHAR2(10 CHAR),
  SIGNATURE_  VARCHAR2(10 CHAR),
  SPSXBH      VARCHAR2(18 CHAR),
  ZJBH        VARCHAR2(100 CHAR),
  BYZDD       TIMESTAMP(6),
  BYZDC       VARCHAR2(500 CHAR),
  BYZDB       VARCHAR2(100 CHAR),
  BYZDA       VARCHAR2(100 CHAR),
  ZFTHYY      VARCHAR2(2000 CHAR),
  FZDW        VARCHAR2(200 CHAR),
  BZ          VARCHAR2(200 CHAR),
  SPSXZXBH    VARCHAR2(255 CHAR),
  BJJGZZJGDM  VARCHAR2(9 CHAR),
  BJJG        VARCHAR2(2 CHAR),
  YXTYWLSH    VARCHAR2(255 CHAR),
  BLJGMS      VARCHAR2(2000 CHAR),
  ZJMC        VARCHAR2(200 CHAR),
  ZJYXQX      VARCHAR2(30 CHAR),
  YWLSH       VARCHAR2(80 CHAR),
  BJSJ        TIMESTAMP(6),
  JEDWDM      VARCHAR2(20 CHAR),
  SJBBH       NUMBER(10),
  SPBZH       VARCHAR2(255 CHAR),
  SFJE        FLOAT,
  BJJGMC      VARCHAR2(200 CHAR)
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table  UF_SPGC_BJ
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

prompt
prompt Creating table UF_SPGC_BJGZ
prompt ===========================
prompt
create table  UF_SPGC_BJGZ
(
  UUID        VARCHAR2(255 CHAR) not null,
  CREATOR     VARCHAR2(255 CHAR),
  CREATE_TIME TIMESTAMP(6),
  MODIFIER    VARCHAR2(255 CHAR),
  MODIFY_TIME TIMESTAMP(6),
  REC_VER     NUMBER(10),
  FORM_UUID   VARCHAR2(255 CHAR),
  STATUS      VARCHAR2(10 CHAR),
  SIGNATURE_  VARCHAR2(10 CHAR),
  SPSXBH      VARCHAR2(18 CHAR),
  BYZDD       TIMESTAMP(6),
  BYZDC       VARCHAR2(500 CHAR),
  BYZDB       VARCHAR2(100 CHAR),
  YWLSH       VARCHAR2(80 CHAR),
  BYZDA       VARCHAR2(100 CHAR),
  SJBBH       NUMBER(10),
  BZ          VARCHAR2(200 CHAR),
  BJGZFCR     VARCHAR2(60 CHAR),
  SPSXZXBH    VARCHAR2(3 CHAR),
  BJGZSJ      TIMESTAMP(6),
  BJGZYY      VARCHAR2(2000 CHAR),
  YXTYWLSH    VARCHAR2(100 CHAR),
  BJCLGZQD    VARCHAR2(2000 CHAR)
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table  UF_SPGC_BJGZ
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

prompt
prompt Creating table UF_SPGC_BJGZ_RL
prompt ==============================
prompt
create table  UF_SPGC_BJGZ_RL
(
  UUID               VARCHAR2(255 CHAR) not null,
  CREATOR            VARCHAR2(255 CHAR),
  CREATE_TIME        TIMESTAMP(6),
  MODIFIER           VARCHAR2(255 CHAR),
  MODIFY_TIME        TIMESTAMP(6),
  REC_VER            NUMBER(10),
  DATA_UUID          VARCHAR2(255 CHAR),
  MAINFORM_DATA_UUID VARCHAR2(255 CHAR),
  MAINFORM_FORM_UUID VARCHAR2(255 CHAR),
  SORT_ORDER         VARCHAR2(255 CHAR),
  PARENT_UUID        VARCHAR2(255 CHAR)
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table  UF_SPGC_BJGZ_RL
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

prompt
prompt Creating table UF_SPGC_BJSL
prompt ===========================
prompt
create table  UF_SPGC_BJSL
(
  UUID        VARCHAR2(255 CHAR) not null,
  CREATOR     VARCHAR2(255 CHAR),
  CREATE_TIME TIMESTAMP(6),
  MODIFIER    VARCHAR2(255 CHAR),
  MODIFY_TIME TIMESTAMP(6),
  REC_VER     NUMBER(10),
  FORM_UUID   VARCHAR2(255 CHAR),
  STATUS      VARCHAR2(10 CHAR),
  SIGNATURE_  VARCHAR2(10 CHAR),
  YWLSH       VARCHAR2(80 CHAR),
  SJBBH       NUMBER(10),
  SPSXBH      VARCHAR2(18 CHAR),
  YXTYWLSH    VARCHAR2(100 CHAR),
  SPSXZXBH    VARCHAR2(3 CHAR),
  BYZDD       TIMESTAMP(6),
  BYZDC       VARCHAR2(500 CHAR),
  BYZDB       VARCHAR2(100 CHAR),
  BYZDA       VARCHAR2(100 CHAR),
  BZ          VARCHAR2(200 CHAR),
  BJSLJTDD    VARCHAR2(400 CHAR),
  SLDWCBRXM   VARCHAR2(60 CHAR),
  BJCLQD      VARCHAR2(2000 CHAR),
  BJSJ        TIMESTAMP(6)
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table  UF_SPGC_BJSL
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

prompt
prompt Creating table UF_SPGC_BJSL_RL
prompt ==============================
prompt
create table  UF_SPGC_BJSL_RL
(
  UUID               VARCHAR2(255 CHAR) not null,
  CREATOR            VARCHAR2(255 CHAR),
  CREATE_TIME        TIMESTAMP(6),
  MODIFIER           VARCHAR2(255 CHAR),
  MODIFY_TIME        TIMESTAMP(6),
  REC_VER            NUMBER(10),
  DATA_UUID          VARCHAR2(255 CHAR),
  MAINFORM_DATA_UUID VARCHAR2(255 CHAR),
  MAINFORM_FORM_UUID VARCHAR2(255 CHAR),
  SORT_ORDER         VARCHAR2(255 CHAR),
  PARENT_UUID        VARCHAR2(255 CHAR)
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table  UF_SPGC_BJSL_RL
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

prompt
prompt Creating table UF_SPGC_BJ_RL
prompt ============================
prompt
create table  UF_SPGC_BJ_RL
(
  UUID               VARCHAR2(255 CHAR) not null,
  CREATOR            VARCHAR2(255 CHAR),
  CREATE_TIME        TIMESTAMP(6),
  MODIFIER           VARCHAR2(255 CHAR),
  MODIFY_TIME        TIMESTAMP(6),
  REC_VER            NUMBER(10),
  DATA_UUID          VARCHAR2(255 CHAR),
  MAINFORM_DATA_UUID VARCHAR2(255 CHAR),
  MAINFORM_FORM_UUID VARCHAR2(255 CHAR),
  SORT_ORDER         VARCHAR2(255 CHAR),
  PARENT_UUID        VARCHAR2(255 CHAR)
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table  UF_SPGC_BJ_RL
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

prompt
prompt Creating table UF_SPGC_CXJS
prompt ===========================
prompt
create table  UF_SPGC_CXJS
(
  UUID        VARCHAR2(255 CHAR) not null,
  CREATOR     VARCHAR2(255 CHAR),
  CREATE_TIME TIMESTAMP(6),
  MODIFIER    VARCHAR2(255 CHAR),
  MODIFY_TIME TIMESTAMP(6),
  REC_VER     NUMBER(10),
  FORM_UUID   VARCHAR2(255 CHAR),
  STATUS      VARCHAR2(10 CHAR),
  SIGNATURE_  VARCHAR2(10 CHAR),
  BYZDD       TIMESTAMP(6),
  BYZDC       VARCHAR2(500 CHAR),
  BYZDB       VARCHAR2(100 CHAR),
  BYZDA       VARCHAR2(100 CHAR),
  SJBBH       NUMBER(10),
  BZ          VARCHAR2(200 CHAR),
  TBCXJSRQ    TIMESTAMP(6),
  TBCXJG      VARCHAR2(2000 CHAR),
  TBCXSFJE    VARCHAR2(30 CHAR),
  SPSXBH      VARCHAR2(18 CHAR),
  XH          NUMBER(10),
  SPSXZXBH    VARCHAR2(3 CHAR),
  YXTYWLSH    VARCHAR2(100 CHAR),
  YWLSH       VARCHAR2(80 CHAR),
  JGCSRQ      TIMESTAMP(6)
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table  UF_SPGC_CXJS
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

prompt
prompt Creating table UF_SPGC_CXJS_RL
prompt ==============================
prompt
create table  UF_SPGC_CXJS_RL
(
  UUID               VARCHAR2(255 CHAR) not null,
  CREATOR            VARCHAR2(255 CHAR),
  CREATE_TIME        TIMESTAMP(6),
  MODIFIER           VARCHAR2(255 CHAR),
  MODIFY_TIME        TIMESTAMP(6),
  REC_VER            NUMBER(10),
  DATA_UUID          VARCHAR2(255 CHAR),
  MAINFORM_DATA_UUID VARCHAR2(255 CHAR),
  MAINFORM_FORM_UUID VARCHAR2(255 CHAR),
  SORT_ORDER         VARCHAR2(255 CHAR),
  PARENT_UUID        VARCHAR2(255 CHAR)
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table  UF_SPGC_CXJS_RL
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

prompt
prompt Creating table UF_SPGC_CXKS
prompt ===========================
prompt
create table  UF_SPGC_CXKS
(
  UUID        VARCHAR2(255 CHAR) not null,
  CREATOR     VARCHAR2(255 CHAR),
  CREATE_TIME TIMESTAMP(6),
  MODIFIER    VARCHAR2(255 CHAR),
  MODIFY_TIME TIMESTAMP(6),
  REC_VER     NUMBER(10),
  FORM_UUID   VARCHAR2(255 CHAR),
  STATUS      VARCHAR2(10 CHAR),
  SIGNATURE_  VARCHAR2(10 CHAR),
  SPSXBH      VARCHAR2(18 CHAR),
  TBCXZLMC    VARCHAR2(60 CHAR),
  TBCXSXDW    VARCHAR2(2 CHAR),
  BYZDD       TIMESTAMP(6),
  BYZDC       VARCHAR2(500 CHAR),
  BYZDB       VARCHAR2(100 CHAR),
  YWLSH       VARCHAR2(80 CHAR),
  BYZDA       VARCHAR2(100 CHAR),
  SJBBH       NUMBER(10),
  XH          NUMBER(10),
  BZ          VARCHAR2(200 CHAR),
  SPSXZXBH    VARCHAR2(3 CHAR),
  YXTYWLSH    VARCHAR2(100 CHAR),
  TBCXQDLYHYJ VARCHAR2(2000 CHAR),
  TBCXPZRDH   VARCHAR2(40 CHAR),
  TBCXSX      NUMBER(10),
  TBCXPZR     VARCHAR2(60 CHAR),
  TBCXKSRQ    TIMESTAMP(6),
  SQNR        VARCHAR2(2000 CHAR),
  TBCXZL      VARCHAR2(2 CHAR),
  TBCXPZRSJ   VARCHAR2(30 CHAR)
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table  UF_SPGC_CXKS
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

prompt
prompt Creating table UF_SPGC_CXKS_RL
prompt ==============================
prompt
create table  UF_SPGC_CXKS_RL
(
  UUID               VARCHAR2(255 CHAR) not null,
  CREATOR            VARCHAR2(255 CHAR),
  CREATE_TIME        TIMESTAMP(6),
  MODIFIER           VARCHAR2(255 CHAR),
  MODIFY_TIME        TIMESTAMP(6),
  REC_VER            NUMBER(10),
  DATA_UUID          VARCHAR2(255 CHAR),
  MAINFORM_DATA_UUID VARCHAR2(255 CHAR),
  MAINFORM_FORM_UUID VARCHAR2(255 CHAR),
  SORT_ORDER         VARCHAR2(255 CHAR),
  PARENT_UUID        VARCHAR2(255 CHAR)
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table  UF_SPGC_CXKS_RL
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

prompt
prompt Creating table UF_SPGC_SJ
prompt =========================
prompt
create table  UF_SPGC_SJ
(
  UUID        VARCHAR2(255 CHAR) not null,
  CREATOR     VARCHAR2(255 CHAR),
  CREATE_TIME TIMESTAMP(6),
  MODIFIER    VARCHAR2(255 CHAR),
  MODIFY_TIME TIMESTAMP(6),
  REC_VER     NUMBER(10),
  FORM_UUID   VARCHAR2(255 CHAR),
  STATUS      VARCHAR2(10 CHAR),
  SIGNATURE_  VARCHAR2(10 CHAR),
  SPSXBH      VARCHAR2(18 CHAR),
  BYZDD       TIMESTAMP(6),
  BYZDC       VARCHAR2(500 CHAR),
  SQRDH       VARCHAR2(30 CHAR),
  SQRYX       VARCHAR2(80 CHAR),
  BYZDB       VARCHAR2(100 CHAR),
  BYZDA       VARCHAR2(100 CHAR),
  WSTJSJ      TIMESTAMP(6),
  BZ          VARCHAR2(200 CHAR),
  SPSXZXBH    VARCHAR2(3 CHAR),
  SQRHSQDW    VARCHAR2(200 CHAR),
  SQRSJ       VARCHAR2(40 CHAR),
  YXTYWLSH    VARCHAR2(100 CHAR),
  YWLSH       VARCHAR2(80 CHAR),
  WSTXCLQD    VARCHAR2(2000 CHAR),
  SQRXM       VARCHAR2(60 CHAR),
  HZBH        VARCHAR2(100 CHAR),
  SJBBH       NUMBER(10)
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table  UF_SPGC_SJ
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

prompt
prompt Creating table UF_SPGC_SJ_RL
prompt ============================
prompt
create table  UF_SPGC_SJ_RL
(
  UUID               VARCHAR2(255 CHAR) not null,
  CREATOR            VARCHAR2(255 CHAR),
  CREATE_TIME        TIMESTAMP(6),
  MODIFIER           VARCHAR2(255 CHAR),
  MODIFY_TIME        TIMESTAMP(6),
  REC_VER            NUMBER(10),
  DATA_UUID          VARCHAR2(255 CHAR),
  MAINFORM_DATA_UUID VARCHAR2(255 CHAR),
  MAINFORM_FORM_UUID VARCHAR2(255 CHAR),
  SORT_ORDER         VARCHAR2(255 CHAR),
  PARENT_UUID        VARCHAR2(255 CHAR)
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table  UF_SPGC_SJ_RL
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

prompt
prompt Creating table UF_SPGC_SL
prompt =========================
prompt
create table  UF_SPGC_SL
(
  UUID        VARCHAR2(255 CHAR) not null,
  CREATOR     VARCHAR2(255 CHAR),
  CREATE_TIME TIMESTAMP(6),
  MODIFIER    VARCHAR2(255 CHAR),
  MODIFY_TIME TIMESTAMP(6),
  REC_VER     NUMBER(10),
  FORM_UUID   VARCHAR2(255 CHAR),
  STATUS      VARCHAR2(10 CHAR),
  SIGNATURE_  VARCHAR2(10 CHAR),
  SPSXBH      VARCHAR2(18 CHAR),
  GDBLSX      NUMBER(10),
  BYZDD       TIMESTAMP(6),
  BYZDC       VARCHAR2(500 CHAR),
  BYZDB       VARCHAR2(100 CHAR),
  BYZDA       VARCHAR2(100 CHAR),
  BZ          VARCHAR2(200 CHAR),
  GDBLSXDW    VARCHAR2(2 CHAR),
  SLJGMC      VARCHAR2(100 CHAR),
  FSYWGXD     VARCHAR2(20 CHAR),
  SLDWCBRXM   VARCHAR2(60 CHAR),
  TJFS        VARCHAR2(2 CHAR),
  XMMC        VARCHAR2(400 CHAR),
  SLZLQD      VARCHAR2(2000 CHAR),
  YWLSH       VARCHAR2(80 CHAR),
  SJBBH       NUMBER(10),
  SLDWCBRGH   VARCHAR2(50 CHAR),
  FLGDGXD     VARCHAR2(20 CHAR),
  SQDWJBRYJ   VARCHAR2(80 CHAR),
  SLYF        VARCHAR2(2 CHAR),
  SLJTDD      VARCHAR2(300 CHAR),
  SLSJ        TIMESTAMP(6),
  GDSF        VARCHAR2(1000 CHAR),
  SPSXMC      VARCHAR2(200 CHAR),
  SPSXZXBH    VARCHAR2(3 CHAR),
  SQDWJBRSJ   VARCHAR2(30 CHAR),
  YXTYWLSH    VARCHAR2(100 CHAR),
  SQDWLXDH    VARCHAR2(40 CHAR),
  HZBH        VARCHAR2(100 CHAR),
  SQDWJBRXM   VARCHAR2(60 CHAR),
  BSLYY       VARCHAR2(2000 CHAR),
  SLJGZZJGDM  VARCHAR2(9 CHAR),
  SQDWHSQRXM  VARCHAR2(200 CHAR)
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table  UF_SPGC_SL
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

prompt
prompt Creating table UF_SPGC_SL_RL
prompt ============================
prompt
create table  UF_SPGC_SL_RL
(
  UUID               VARCHAR2(255 CHAR) not null,
  CREATOR            VARCHAR2(255 CHAR),
  CREATE_TIME        TIMESTAMP(6),
  MODIFIER           VARCHAR2(255 CHAR),
  MODIFY_TIME        TIMESTAMP(6),
  REC_VER            NUMBER(10),
  DATA_UUID          VARCHAR2(255 CHAR),
  MAINFORM_DATA_UUID VARCHAR2(255 CHAR),
  MAINFORM_FORM_UUID VARCHAR2(255 CHAR),
  SORT_ORDER         VARCHAR2(255 CHAR),
  PARENT_UUID        VARCHAR2(255 CHAR)
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table  UF_SPGC_SL_RL
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

prompt
prompt Creating table UF_SPGC_SPGC
prompt ===========================
prompt
create table  UF_SPGC_SPGC
(
  UUID        VARCHAR2(255 CHAR) not null,
  CREATOR     VARCHAR2(255 CHAR),
  CREATE_TIME TIMESTAMP(6),
  MODIFIER    VARCHAR2(255 CHAR),
  MODIFY_TIME TIMESTAMP(6),
  REC_VER     NUMBER(10),
  FORM_UUID   VARCHAR2(255 CHAR),
  STATUS      VARCHAR2(10 CHAR),
  SIGNATURE_  VARCHAR2(10 CHAR),
  SPSXBH      VARCHAR2(18 CHAR),
  BYZDD       TIMESTAMP(6),
  BYZDC       VARCHAR2(500 CHAR),
  SPHJDM      VARCHAR2(4 CHAR),
  BYZDB       VARCHAR2(100 CHAR),
  BYZDA       VARCHAR2(100 CHAR),
  SPYJ        VARCHAR2(2000 CHAR),
  SPHJMC      VARCHAR2(50 CHAR),
  BZ          VARCHAR2(200 CHAR),
  SPSXZXBH    VARCHAR2(3 CHAR),
  SPRZWMC     VARCHAR2(50 CHAR),
  SPSJ        TIMESTAMP(6),
  YXTYWLSH    VARCHAR2(100 CHAR),
  SPRZWDM     VARCHAR2(5 CHAR),
  SPRXM       VARCHAR2(60 CHAR),
  YWLSH       VARCHAR2(80 CHAR),
  SJBBH       NUMBER(10),
  SPBZH       NUMBER(10)
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table  UF_SPGC_SPGC
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

prompt
prompt Creating table UF_SPGC_SPGC_RL
prompt ==============================
prompt
create table  UF_SPGC_SPGC_RL
(
  UUID               VARCHAR2(255 CHAR) not null,
  CREATOR            VARCHAR2(255 CHAR),
  CREATE_TIME        TIMESTAMP(6),
  MODIFIER           VARCHAR2(255 CHAR),
  MODIFY_TIME        TIMESTAMP(6),
  REC_VER            NUMBER(10),
  DATA_UUID          VARCHAR2(255 CHAR),
  MAINFORM_DATA_UUID VARCHAR2(255 CHAR),
  MAINFORM_FORM_UUID VARCHAR2(255 CHAR),
  SORT_ORDER         VARCHAR2(255 CHAR),
  PARENT_UUID        VARCHAR2(255 CHAR)
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table  UF_SPGC_SPGC_RL
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

prompt
prompt Creating table UF_SSGL_FAQ
prompt ==========================
prompt
create table  UF_SSGL_FAQ
(
  UUID           VARCHAR2(255 CHAR) not null,
  CREATOR        VARCHAR2(255 CHAR),
  CREATE_TIME    TIMESTAMP(6),
  MODIFIER       VARCHAR2(255 CHAR),
  MODIFY_TIME    TIMESTAMP(6),
  REC_VER        NUMBER(10),
  FORM_UUID      VARCHAR2(255 CHAR),
  STATUS         VARCHAR2(10 CHAR),
  SIGNATURE_     VARCHAR2(50 CHAR),
  FAQ_QUESTION   VARCHAR2(3999 CHAR),
  FAQ_TYPE_NAME  VARCHAR2(255 CHAR),
  FAQ_ANSWER     VARCHAR2(3999 CHAR),
  FAQ_TITLE      VARCHAR2(255 CHAR),
  FAQ_RESPONDENT VARCHAR2(255 CHAR),
  FAQ_TIME       TIMESTAMP(6),
  FAQ_TYPE       VARCHAR2(255 CHAR)
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table  UF_SSGL_FAQ
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

prompt
prompt Creating table UF_SSGL_FAQ_RL
prompt =============================
prompt
create table  UF_SSGL_FAQ_RL
(
  UUID               VARCHAR2(255 CHAR) not null,
  CREATOR            VARCHAR2(255 CHAR),
  CREATE_TIME        TIMESTAMP(6),
  MODIFIER           VARCHAR2(255 CHAR),
  MODIFY_TIME        TIMESTAMP(6),
  REC_VER            NUMBER(10),
  DATA_UUID          VARCHAR2(255 CHAR),
  MAINFORM_DATA_UUID VARCHAR2(255 CHAR),
  MAINFORM_FORM_UUID VARCHAR2(255 CHAR),
  SORT_ORDER         VARCHAR2(255 CHAR),
  PARENT_UUID        VARCHAR2(255 CHAR)
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255;
alter table  UF_SSGL_FAQ_RL
  add primary key (UUID)
  using index 
  tablespace USERS
  pctfree 10
  initrans 2
  maxtrans 255;

prompt
prompt Creating table UF_SSGL_HDWD
prompt ===========================
prompt
create table  UF_SSGL_HDWD
(
  UUID                VARCHAR2(255 CHAR) not null,
  CREATOR             VARCHAR2(255 CHAR),
  CREATE_TIME         TIMESTAMP(6),
  MODIFIER            VARCHAR2(255 CHAR),
  MODIFY_TIME         TIMESTAMP(6),
  REC_VER             NUMBER(10),
  FORM_UUID           VARCHAR2(255 CHAR),
  STATUS              VARCHAR2(10 CHAR),
  SIGNATURE_          VARCHAR2(50 CHAR),
  FAQ_QUESTION        VARCHAR2(255 CHAR),
  FAQ_QUESTION_PERSON VARCHAR2(255 CHAR),
  FAQ_QUESTION_TIME   VARCHAR2(255 CHAR),
  FAQ_ANSWER          VARCHAR2(4000 CHAR),
  FAQ_ANSWER_TIME     VARCHAR2(255 CHAR),
  FAQ_TITLE           VARCHAR2(255 CHAR),
  FAQ_ANSWER_PERSON   VARCHAR2(255 CHAR)
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table UF_SSGL_HDWD_RL
prompt ==============================
prompt
create table  UF_SSGL_HDWD_RL
(
  UUID               VARCHAR2(255 CHAR) not null,
  CREATOR            VARCHAR2(255 CHAR),
  CREATE_TIME        TIMESTAMP(6),
  MODIFIER           VARCHAR2(255 CHAR),
  MODIFY_TIME        TIMESTAMP(6),
  REC_VER            NUMBER(10),
  DATA_UUID          VARCHAR2(255 CHAR),
  MAINFORM_DATA_UUID VARCHAR2(255 CHAR),
  MAINFORM_FORM_UUID VARCHAR2(255 CHAR),
  SORT_ORDER         VARCHAR2(255 CHAR),
  PARENT_UUID        VARCHAR2(255 CHAR)
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255;

prompt
prompt Creating table UF_SSGL_NOTICE
prompt =============================
prompt
create table  UF_SSGL_NOTICE
(
  UUID                VARCHAR2(255 CHAR) not null,
  CREATOR             VARCHAR2(255 CHAR),
  CREATE_TIME         TIMESTAMP(6),
  MODIFIER            VARCHAR2(255 CHAR),
  MODIFY_TIME         TIMESTAMP(6),
  REC_VER             NUMBER(10),
  FORM_UUID           VARCHAR2(255 CHAR),
  STATUS              VARCHAR2(10 CHAR),
  SIGNATURE_          VARCHAR2(50 CHAR),
  CURRENT_USER        VARCHAR2(255 CHAR),
  TEXT_TITLE          VARCHAR2(500 CHAR),
  SEND_OBJECT_NAME    VARCHAR2(4000 CHAR),
  RESPONSIBILITY_NAME VARCHAR2(4000 CHAR),
  RELEASE_TIME        TIMESTAMP(6),
  FILEUPLOAD          VARCHAR2(255 CHAR),
  NOTICE_CONTENT      CLOB,
  SEND_OBJECT         VARCHAR2(4000 CHAR),
  RESPONSIBILITY      VARCHAR2(255 CHAR)
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table  UF_SSGL_NOTICE
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

prompt
prompt Creating table UF_SSGL_NOTICE_RL
prompt ================================
prompt
create table  UF_SSGL_NOTICE_RL
(
  UUID               VARCHAR2(255 CHAR) not null,
  CREATOR            VARCHAR2(255 CHAR),
  CREATE_TIME        TIMESTAMP(6),
  MODIFIER           VARCHAR2(255 CHAR),
  MODIFY_TIME        TIMESTAMP(6),
  REC_VER            NUMBER(10),
  DATA_UUID          VARCHAR2(255 CHAR),
  MAINFORM_DATA_UUID VARCHAR2(255 CHAR),
  MAINFORM_FORM_UUID VARCHAR2(255 CHAR),
  SORT_ORDER         VARCHAR2(255 CHAR),
  PARENT_UUID        VARCHAR2(255 CHAR)
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table  UF_SSGL_NOTICE_RL
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

prompt
prompt Creating table UF_SSGL_SYBZ
prompt ===========================
prompt
create table  UF_SSGL_SYBZ
(
  UUID        VARCHAR2(255 CHAR) not null,
  CREATOR     VARCHAR2(255 CHAR),
  CREATE_TIME TIMESTAMP(6),
  MODIFIER    VARCHAR2(255 CHAR),
  MODIFY_TIME TIMESTAMP(6),
  REC_VER     NUMBER(10),
  FORM_UUID   VARCHAR2(255 CHAR),
  STATUS      VARCHAR2(10 CHAR),
  SIGNATURE_  VARCHAR2(10 CHAR),
  TEXT_TITLE  VARCHAR2(255 CHAR),
  FILEUPLOAD  VARCHAR2(255 CHAR)
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table  UF_SSGL_SYBZ
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

prompt
prompt Creating table UF_SSGL_SYBZ_RL
prompt ==============================
prompt
create table  UF_SSGL_SYBZ_RL
(
  UUID               VARCHAR2(255 CHAR) not null,
  CREATOR            VARCHAR2(255 CHAR),
  CREATE_TIME        TIMESTAMP(6),
  MODIFIER           VARCHAR2(255 CHAR),
  MODIFY_TIME        TIMESTAMP(6),
  REC_VER            NUMBER(10),
  DATA_UUID          VARCHAR2(255 CHAR),
  MAINFORM_DATA_UUID VARCHAR2(255 CHAR),
  MAINFORM_FORM_UUID VARCHAR2(255 CHAR),
  SORT_ORDER         VARCHAR2(255 CHAR),
  PARENT_UUID        VARCHAR2(255 CHAR)
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table  UF_SSGL_SYBZ_RL
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

prompt
prompt Creating table UF_SSGL_ZCFG
prompt ===========================
prompt
create table  UF_SSGL_ZCFG
(
  UUID           VARCHAR2(255 CHAR) not null,
  CREATOR        VARCHAR2(255 CHAR),
  CREATE_TIME    TIMESTAMP(6),
  MODIFIER       VARCHAR2(255 CHAR),
  MODIFY_TIME    TIMESTAMP(6),
  REC_VER        NUMBER(10),
  FORM_UUID      VARCHAR2(255 CHAR),
  STATUS         VARCHAR2(10 CHAR),
  SIGNATURE_     VARCHAR2(50 CHAR),
  FILEUPLOAD     VARCHAR2(255 CHAR),
  TEXT_TITLE     VARCHAR2(255 CHAR),
  NOTICE_CONTENT CLOB,
  FABU_PERSON    VARCHAR2(255 CHAR),
  FABU_TIME      TIMESTAMP(6)
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table UF_SSGL_ZCFG_RL
prompt ==============================
prompt
create table  UF_SSGL_ZCFG_RL
(
  UUID               VARCHAR2(255 CHAR) not null,
  CREATOR            VARCHAR2(255 CHAR),
  CREATE_TIME        TIMESTAMP(6),
  MODIFIER           VARCHAR2(255 CHAR),
  MODIFY_TIME        TIMESTAMP(6),
  REC_VER            NUMBER(10),
  DATA_UUID          VARCHAR2(255 CHAR),
  MAINFORM_DATA_UUID VARCHAR2(255 CHAR),
  MAINFORM_FORM_UUID VARCHAR2(255 CHAR),
  SORT_ORDER         VARCHAR2(255 CHAR),
  PARENT_UUID        VARCHAR2(255 CHAR)
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255;

prompt
prompt Creating table UF_SSGL_ZLXZ
prompt ===========================
prompt
create table  UF_SSGL_ZLXZ
(
  UUID           VARCHAR2(255 CHAR) not null,
  CREATOR        VARCHAR2(255 CHAR),
  CREATE_TIME    TIMESTAMP(6),
  MODIFIER       VARCHAR2(255 CHAR),
  MODIFY_TIME    TIMESTAMP(6),
  REC_VER        NUMBER(10),
  FORM_UUID      VARCHAR2(255 CHAR),
  STATUS         VARCHAR2(10 CHAR),
  SIGNATURE_     VARCHAR2(10 CHAR),
  FILEUPLOAD     VARCHAR2(255 CHAR),
  TEXT_TITLE     VARCHAR2(255 CHAR),
  NOTICE_CONTENT CLOB
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table  UF_SSGL_ZLXZ
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

prompt
prompt Creating table UF_SSGL_ZLXZ_RL
prompt ==============================
prompt
create table  UF_SSGL_ZLXZ_RL
(
  UUID               VARCHAR2(255 CHAR) not null,
  CREATOR            VARCHAR2(255 CHAR),
  CREATE_TIME        TIMESTAMP(6),
  MODIFIER           VARCHAR2(255 CHAR),
  MODIFY_TIME        TIMESTAMP(6),
  REC_VER            NUMBER(10),
  DATA_UUID          VARCHAR2(255 CHAR),
  MAINFORM_DATA_UUID VARCHAR2(255 CHAR),
  MAINFORM_FORM_UUID VARCHAR2(255 CHAR),
  SORT_ORDER         VARCHAR2(255 CHAR),
  PARENT_UUID        VARCHAR2(255 CHAR)
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table  UF_SSGL_ZLXZ_RL
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

prompt
prompt Creating table UF_SSJC_JGYC
prompt ===========================
prompt
create table  UF_SSJC_JGYC
(
  UUID          VARCHAR2(255 CHAR) not null,
  CREATOR       VARCHAR2(255 CHAR),
  CREATE_TIME   TIMESTAMP(6),
  MODIFIER      VARCHAR2(255 CHAR),
  MODIFY_TIME   TIMESTAMP(6),
  REC_VER       NUMBER(10),
  FORM_UUID     VARCHAR2(255 CHAR),
  STATUS        VARCHAR2(10 CHAR),
  SIGNATURE_    VARCHAR2(10 CHAR),
  FILEUPLOAD    VARCHAR2(255 CHAR),
  REMARKS       VARCHAR2(255 CHAR),
  BODY_CONTENT  VARCHAR2(255 CHAR),
  WORRY         VARCHAR2(255 CHAR),
  CREATE_DATE   TIMESTAMP(6),
  CREATE_PERSON VARCHAR2(255 CHAR),
  CREATE_DEPT   VARCHAR2(255 CHAR),
  REPLYDATE     NUMBER(10),
  TITLE         VARCHAR2(255 CHAR),
  REPLYTIME     TIMESTAMP(6)
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table  UF_SSJC_JGYC
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

prompt
prompt Creating table UF_SSJC_JGYC_RL
prompt ==============================
prompt
create table  UF_SSJC_JGYC_RL
(
  UUID               VARCHAR2(255 CHAR) not null,
  CREATOR            VARCHAR2(255 CHAR),
  CREATE_TIME        TIMESTAMP(6),
  MODIFIER           VARCHAR2(255 CHAR),
  MODIFY_TIME        TIMESTAMP(6),
  REC_VER            NUMBER(10),
  DATA_UUID          VARCHAR2(255 CHAR),
  MAINFORM_DATA_UUID VARCHAR2(255 CHAR),
  MAINFORM_FORM_UUID VARCHAR2(255 CHAR),
  SORT_ORDER         VARCHAR2(255 CHAR),
  PARENT_UUID        VARCHAR2(255 CHAR)
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table  UF_SSJC_JGYC_RL
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

prompt
prompt Creating table UF_SSXX_FZJG
prompt ===========================
prompt
create table  UF_SSXX_FZJG
(
  UUID        VARCHAR2(255 CHAR) not null,
  CREATOR     VARCHAR2(255 CHAR),
  CREATE_TIME TIMESTAMP(6),
  MODIFIER    VARCHAR2(255 CHAR),
  MODIFY_TIME TIMESTAMP(6),
  REC_VER     NUMBER(10),
  FORM_UUID   VARCHAR2(255 CHAR),
  STATUS      VARCHAR2(10 CHAR),
  SIGNATURE_  VARCHAR2(10 CHAR),
  FZR         VARCHAR2(30 CHAR),
  JYCS        VARCHAR2(100 CHAR),
  MC          VARCHAR2(100 CHAR),
  BZ          VARCHAR2(10 CHAR)
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table  UF_SSXX_FZJG
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

prompt
prompt Creating table UF_SSXX_FZJG_RL
prompt ==============================
prompt
create table  UF_SSXX_FZJG_RL
(
  UUID               VARCHAR2(255 CHAR) not null,
  CREATOR            VARCHAR2(255 CHAR),
  CREATE_TIME        TIMESTAMP(6),
  MODIFIER           VARCHAR2(255 CHAR),
  MODIFY_TIME        TIMESTAMP(6),
  REC_VER            NUMBER(10),
  DATA_UUID          VARCHAR2(255 CHAR),
  MAINFORM_DATA_UUID VARCHAR2(255 CHAR),
  MAINFORM_FORM_UUID VARCHAR2(255 CHAR),
  SORT_ORDER         VARCHAR2(255 CHAR),
  PARENT_UUID        VARCHAR2(255 CHAR)
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table  UF_SSXX_FZJG_RL
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

prompt
prompt Creating table UF_SSXX_GDXX
prompt ===========================
prompt
create table  UF_SSXX_GDXX
(
  UUID        VARCHAR2(255 CHAR) not null,
  CREATOR     VARCHAR2(255 CHAR),
  CREATE_TIME TIMESTAMP(6),
  MODIFIER    VARCHAR2(255 CHAR),
  MODIFY_TIME TIMESTAMP(6),
  REC_VER     NUMBER(10),
  FORM_UUID   VARCHAR2(255 CHAR),
  STATUS      VARCHAR2(10 CHAR),
  SIGNATURE_  VARCHAR2(10 CHAR),
  GBDM        VARCHAR2(6 CHAR),
  SJCZE       FLOAT,
  GDLX        VARCHAR2(30 CHAR),
  GB          VARCHAR2(100 CHAR),
  RJCZEDW     VARCHAR2(30 CHAR),
  SJCZEDW     VARCHAR2(30 CHAR),
  RJCZE       FLOAT,
  GDMC        VARCHAR2(100 CHAR),
  GDLXDM      VARCHAR2(7 CHAR),
  CZBL        VARCHAR2(10 CHAR)
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table  UF_SSXX_GDXX
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

prompt
prompt Creating table UF_SSXX_GDXX_RL
prompt ==============================
prompt
create table  UF_SSXX_GDXX_RL
(
  UUID               VARCHAR2(255 CHAR) not null,
  CREATOR            VARCHAR2(255 CHAR),
  CREATE_TIME        TIMESTAMP(6),
  MODIFIER           VARCHAR2(255 CHAR),
  MODIFY_TIME        TIMESTAMP(6),
  REC_VER            NUMBER(10),
  DATA_UUID          VARCHAR2(255 CHAR),
  MAINFORM_DATA_UUID VARCHAR2(255 CHAR),
  MAINFORM_FORM_UUID VARCHAR2(255 CHAR),
  SORT_ORDER         VARCHAR2(255 CHAR),
  PARENT_UUID        VARCHAR2(255 CHAR)
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table  UF_SSXX_GDXX_RL
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

prompt
prompt Creating table UF_SSXX_JYCS
prompt ===========================
prompt
create table  UF_SSXX_JYCS
(
  UUID        VARCHAR2(255 CHAR) not null,
  CREATOR     VARCHAR2(255 CHAR),
  CREATE_TIME TIMESTAMP(6),
  MODIFIER    VARCHAR2(255 CHAR),
  MODIFY_TIME TIMESTAMP(6),
  REC_VER     NUMBER(10),
  FORM_UUID   VARCHAR2(255 CHAR),
  STATUS      VARCHAR2(10 CHAR),
  SIGNATURE_  VARCHAR2(10 CHAR),
  JYCS        VARCHAR2(200 CHAR)
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table  UF_SSXX_JYCS
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

prompt
prompt Creating table UF_SSXX_JYCS_RL
prompt ==============================
prompt
create table  UF_SSXX_JYCS_RL
(
  UUID               VARCHAR2(255 CHAR) not null,
  CREATOR            VARCHAR2(255 CHAR),
  CREATE_TIME        TIMESTAMP(6),
  MODIFIER           VARCHAR2(255 CHAR),
  MODIFY_TIME        TIMESTAMP(6),
  REC_VER            NUMBER(10),
  DATA_UUID          VARCHAR2(255 CHAR),
  MAINFORM_DATA_UUID VARCHAR2(255 CHAR),
  MAINFORM_FORM_UUID VARCHAR2(255 CHAR),
  SORT_ORDER         VARCHAR2(255 CHAR),
  PARENT_UUID        VARCHAR2(255 CHAR)
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table  UF_SSXX_JYCS_RL
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

prompt
prompt Creating table UF_SSXX_NBBA
prompt ===========================
prompt
create table  UF_SSXX_NBBA
(
  UUID        VARCHAR2(255 CHAR) not null,
  CREATOR     VARCHAR2(255 CHAR),
  CREATE_TIME TIMESTAMP(6),
  MODIFIER    VARCHAR2(255 CHAR),
  MODIFY_TIME TIMESTAMP(6),
  REC_VER     NUMBER(10),
  FORM_UUID   VARCHAR2(255 CHAR),
  STATUS      VARCHAR2(10 CHAR),
  SIGNATURE_  VARCHAR2(10 CHAR),
  ZTLX        VARCHAR2(10 CHAR),
  XGWJ        VARCHAR2(255 CHAR),
  ZTMC        VARCHAR2(50 CHAR),
  FDDBR       VARCHAR2(100 CHAR),
  JYCS        VARCHAR2(100 CHAR),
  RJRQ        TIMESTAMP(6),
  ZCH         VARCHAR2(17 CHAR),
  MC          VARCHAR2(100 CHAR),
  BGSM        VARCHAR2(500 CHAR),
  ND          NUMBER(10)
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table  UF_SSXX_NBBA
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

prompt
prompt Creating table UF_SSXX_NBBA_RL
prompt ==============================
prompt
create table  UF_SSXX_NBBA_RL
(
  UUID               VARCHAR2(255 CHAR) not null,
  CREATOR            VARCHAR2(255 CHAR),
  CREATE_TIME        TIMESTAMP(6),
  MODIFIER           VARCHAR2(255 CHAR),
  MODIFY_TIME        TIMESTAMP(6),
  REC_VER            NUMBER(10),
  DATA_UUID          VARCHAR2(255 CHAR),
  MAINFORM_DATA_UUID VARCHAR2(255 CHAR),
  MAINFORM_FORM_UUID VARCHAR2(255 CHAR),
  SORT_ORDER         VARCHAR2(255 CHAR),
  PARENT_UUID        VARCHAR2(255 CHAR)
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table  UF_SSXX_NBBA_RL
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

prompt
prompt Creating table UF_SSXX_QDRY
prompt ===========================
prompt
create table  UF_SSXX_QDRY
(
  UUID        VARCHAR2(255 CHAR) not null,
  CREATOR     VARCHAR2(255 CHAR),
  CREATE_TIME TIMESTAMP(6),
  MODIFIER    VARCHAR2(255 CHAR),
  MODIFY_TIME TIMESTAMP(6),
  REC_VER     NUMBER(10),
  FORM_UUID   VARCHAR2(255 CHAR),
  STATUS      VARCHAR2(10 CHAR),
  SIGNATURE_  VARCHAR2(10 CHAR),
  ZTLX        VARCHAR2(10 CHAR),
  SYRQ        TIMESTAMP(6),
  XGWJ        VARCHAR2(255 CHAR),
  ZTMC        VARCHAR2(50 CHAR),
  YXRQ        TIMESTAMP(6),
  FDDBR       VARCHAR2(100 CHAR),
  JYCS        VARCHAR2(100 CHAR),
  ZCH         VARCHAR2(17 CHAR),
  MC          VARCHAR2(100 CHAR),
  SYDWDM      VARCHAR2(9 CHAR),
  SYDWMC      VARCHAR2(100 CHAR)
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table  UF_SSXX_QDRY
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

prompt
prompt Creating table UF_SSXX_QDRY_RL
prompt ==============================
prompt
create table  UF_SSXX_QDRY_RL
(
  UUID               VARCHAR2(255 CHAR) not null,
  CREATOR            VARCHAR2(255 CHAR),
  CREATE_TIME        TIMESTAMP(6),
  MODIFIER           VARCHAR2(255 CHAR),
  MODIFY_TIME        TIMESTAMP(6),
  REC_VER            NUMBER(10),
  DATA_UUID          VARCHAR2(255 CHAR),
  MAINFORM_DATA_UUID VARCHAR2(255 CHAR),
  MAINFORM_FORM_UUID VARCHAR2(255 CHAR),
  SORT_ORDER         VARCHAR2(255 CHAR),
  PARENT_UUID        VARCHAR2(255 CHAR)
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table  UF_SSXX_QDRY_RL
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

prompt
prompt Creating table UF_SSXX_QDZZ
prompt ===========================
prompt
create table  UF_SSXX_QDZZ
(
  UUID        VARCHAR2(255 CHAR) not null,
  CREATOR     VARCHAR2(255 CHAR),
  CREATE_TIME TIMESTAMP(6),
  MODIFIER    VARCHAR2(255 CHAR),
  MODIFY_TIME TIMESTAMP(6),
  REC_VER     NUMBER(10),
  FORM_UUID   VARCHAR2(255 CHAR),
  STATUS      VARCHAR2(10 CHAR),
  SIGNATURE_  VARCHAR2(10 CHAR),
  ZTLX        VARCHAR2(10 CHAR),
  XGWJ        VARCHAR2(255 CHAR),
  QDRQ        TIMESTAMP(6),
  ZTMC        VARCHAR2(50 CHAR),
  YXRQ        TIMESTAMP(6),
  FDDBR       VARCHAR2(100 CHAR),
  JYCS        VARCHAR2(100 CHAR),
  ZCH         VARCHAR2(17 CHAR),
  SYDWDM      VARCHAR2(9 CHAR),
  SYDWMC      VARCHAR2(100 CHAR),
  MC          VARCHAR2(255 CHAR)
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table  UF_SSXX_QDZZ
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

prompt
prompt Creating table UF_SSXX_QDZZ_RL
prompt ==============================
prompt
create table  UF_SSXX_QDZZ_RL
(
  UUID               VARCHAR2(255 CHAR) not null,
  CREATOR            VARCHAR2(255 CHAR),
  CREATE_TIME        TIMESTAMP(6),
  MODIFIER           VARCHAR2(255 CHAR),
  MODIFY_TIME        TIMESTAMP(6),
  REC_VER            NUMBER(10),
  DATA_UUID          VARCHAR2(255 CHAR),
  MAINFORM_DATA_UUID VARCHAR2(255 CHAR),
  MAINFORM_FORM_UUID VARCHAR2(255 CHAR),
  SORT_ORDER         VARCHAR2(255 CHAR),
  PARENT_UUID        VARCHAR2(255 CHAR)
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table  UF_SSXX_QDZZ_RL
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

prompt
prompt Creating table UF_SSXX_QSXX
prompt ===========================
prompt
create table  UF_SSXX_QSXX
(
  UUID        VARCHAR2(255 CHAR) not null,
  CREATOR     VARCHAR2(255 CHAR),
  CREATE_TIME TIMESTAMP(6),
  MODIFIER    VARCHAR2(255 CHAR),
  MODIFY_TIME TIMESTAMP(6),
  REC_VER     NUMBER(10),
  FORM_UUID   VARCHAR2(255 CHAR),
  STATUS      VARCHAR2(10 CHAR),
  SIGNATURE_  VARCHAR2(10 CHAR),
  QSZDH       VARCHAR2(30 CHAR),
  QSZRY       VARCHAR2(1000 CHAR),
  XKJYXMMC    VARCHAR2(255 CHAR),
  QSZBARQ     TIMESTAMP(6),
  QSZDZ       VARCHAR2(100 CHAR),
  QSZFZR      VARCHAR2(100 CHAR)
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table  UF_SSXX_QSXX
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

prompt
prompt Creating table UF_SSXX_QSXX_RL
prompt ==============================
prompt
create table  UF_SSXX_QSXX_RL
(
  UUID               VARCHAR2(255 CHAR) not null,
  CREATOR            VARCHAR2(255 CHAR),
  CREATE_TIME        TIMESTAMP(6),
  MODIFIER           VARCHAR2(255 CHAR),
  MODIFY_TIME        TIMESTAMP(6),
  REC_VER            NUMBER(10),
  DATA_UUID          VARCHAR2(255 CHAR),
  MAINFORM_DATA_UUID VARCHAR2(255 CHAR),
  MAINFORM_FORM_UUID VARCHAR2(255 CHAR),
  SORT_ORDER         VARCHAR2(255 CHAR),
  PARENT_UUID        VARCHAR2(255 CHAR)
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table  UF_SSXX_QSXX_RL
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

prompt
prompt Creating table UF_SSXX_XGXK
prompt ===========================
prompt
create table  UF_SSXX_XGXK
(
  UUID        VARCHAR2(255 CHAR) not null,
  CREATOR     VARCHAR2(255 CHAR),
  CREATE_TIME TIMESTAMP(6),
  MODIFIER    VARCHAR2(255 CHAR),
  MODIFY_TIME TIMESTAMP(6),
  REC_VER     NUMBER(10),
  FORM_UUID   VARCHAR2(255 CHAR),
  STATUS      VARCHAR2(10 CHAR),
  SIGNATURE_  VARCHAR2(10 CHAR),
  XKJYXMDM    VARCHAR2(20 CHAR),
  DWMC        VARCHAR2(100 CHAR),
  XKJYXMMC    VARCHAR2(100 CHAR),
  DWDM        VARCHAR2(9 CHAR)
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table  UF_SSXX_XGXK
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

prompt
prompt Creating table UF_SSXX_XGXK_RL
prompt ==============================
prompt
create table  UF_SSXX_XGXK_RL
(
  UUID               VARCHAR2(255 CHAR) not null,
  CREATOR            VARCHAR2(255 CHAR),
  CREATE_TIME        TIMESTAMP(6),
  MODIFIER           VARCHAR2(255 CHAR),
  MODIFY_TIME        TIMESTAMP(6),
  REC_VER            NUMBER(10),
  DATA_UUID          VARCHAR2(255 CHAR),
  MAINFORM_DATA_UUID VARCHAR2(255 CHAR),
  MAINFORM_FORM_UUID VARCHAR2(255 CHAR),
  SORT_ORDER         VARCHAR2(255 CHAR),
  PARENT_UUID        VARCHAR2(255 CHAR)
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table  UF_SSXX_XGXK_RL
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

prompt
prompt Creating table UF_SSXX_XKTZ
prompt ===========================
prompt
create table  UF_SSXX_XKTZ
(
  UUID        VARCHAR2(255 CHAR) not null,
  CREATOR     VARCHAR2(255 CHAR),
  CREATE_TIME TIMESTAMP(6),
  MODIFIER    VARCHAR2(255 CHAR),
  MODIFY_TIME TIMESTAMP(6),
  REC_VER     NUMBER(10),
  FORM_UUID   VARCHAR2(255 CHAR),
  STATUS      VARCHAR2(10 CHAR),
  SIGNATURE_  VARCHAR2(10 CHAR),
  BT          VARCHAR2(50 CHAR),
  Z           VARCHAR2(50 CHAR)
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table  UF_SSXX_XKTZ
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

prompt
prompt Creating table UF_SSXX_XKTZ_RL
prompt ==============================
prompt
create table  UF_SSXX_XKTZ_RL
(
  UUID               VARCHAR2(255 CHAR) not null,
  CREATOR            VARCHAR2(255 CHAR),
  CREATE_TIME        TIMESTAMP(6),
  MODIFIER           VARCHAR2(255 CHAR),
  MODIFY_TIME        TIMESTAMP(6),
  REC_VER            NUMBER(10),
  DATA_UUID          VARCHAR2(255 CHAR),
  MAINFORM_DATA_UUID VARCHAR2(255 CHAR),
  MAINFORM_FORM_UUID VARCHAR2(255 CHAR),
  SORT_ORDER         VARCHAR2(255 CHAR),
  PARENT_UUID        VARCHAR2(255 CHAR)
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table  UF_SSXX_XKTZ_RL
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

prompt
prompt Creating table UF_SSXX_XZCF
prompt ===========================
prompt
create table  UF_SSXX_XZCF
(
  UUID        VARCHAR2(255 CHAR) not null,
  CREATOR     VARCHAR2(255 CHAR),
  CREATE_TIME TIMESTAMP(6),
  MODIFIER    VARCHAR2(255 CHAR),
  MODIFY_TIME TIMESTAMP(6),
  REC_VER     NUMBER(10),
  FORM_UUID   VARCHAR2(255 CHAR),
  STATUS      VARCHAR2(10 CHAR),
  SIGNATURE_  VARCHAR2(10 CHAR),
  CFDJJGDM    VARCHAR2(9 CHAR),
  CFDJJGMC    VARCHAR2(100 CHAR),
  ZTMC        VARCHAR2(50 CHAR),
  WHXWDX      VARCHAR2(500 CHAR),
  CFZL        VARCHAR2(200 CHAR),
  ZTLX        VARCHAR2(10 CHAR),
  XGWJ        VARCHAR2(255 CHAR),
  CFRQ        TIMESTAMP(6),
  FDDBR       VARCHAR2(100 CHAR),
  CFYJ        VARCHAR2(1000 CHAR),
  JYCS        VARCHAR2(100 CHAR),
  DSR         VARCHAR2(100 CHAR),
  ZCH         VARCHAR2(17 CHAR)
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table  UF_SSXX_XZCF
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

prompt
prompt Creating table UF_SSXX_XZCF_RL
prompt ==============================
prompt
create table  UF_SSXX_XZCF_RL
(
  UUID               VARCHAR2(255 CHAR) not null,
  CREATOR            VARCHAR2(255 CHAR),
  CREATE_TIME        TIMESTAMP(6),
  MODIFIER           VARCHAR2(255 CHAR),
  MODIFY_TIME        TIMESTAMP(6),
  REC_VER            NUMBER(10),
  DATA_UUID          VARCHAR2(255 CHAR),
  MAINFORM_DATA_UUID VARCHAR2(255 CHAR),
  MAINFORM_FORM_UUID VARCHAR2(255 CHAR),
  SORT_ORDER         VARCHAR2(255 CHAR),
  PARENT_UUID        VARCHAR2(255 CHAR)
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table  UF_SSXX_XZCF_RL
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

prompt
prompt Creating table UF_SSXX_XZXK
prompt ===========================
prompt
create table  UF_SSXX_XZXK
(
  UUID        VARCHAR2(255 CHAR) not null,
  CREATOR     VARCHAR2(255 CHAR),
  CREATE_TIME TIMESTAMP(6),
  MODIFIER    VARCHAR2(255 CHAR),
  MODIFY_TIME TIMESTAMP(6),
  REC_VER     NUMBER(10),
  FORM_UUID   VARCHAR2(255 CHAR),
  STATUS      VARCHAR2(10 CHAR),
  SIGNATURE_  VARCHAR2(50 CHAR),
  ZCH         VARCHAR2(15 CHAR),
  ZTMC        VARCHAR2(100 CHAR),
  JGMC        VARCHAR2(100 CHAR),
  ZJH         VARCHAR2(30 CHAR),
  XKFW        VARCHAR2(3000 CHAR),
  BZRQ        TIMESTAMP(6),
  XGWJ        VARCHAR2(255 CHAR),
  ZTLX        VARCHAR2(30 CHAR),
  BZDWDM      VARCHAR2(9 CHAR),
  BZDWMC      VARCHAR2(100 CHAR),
  GXRQ        TIMESTAMP(6),
  DJZT        VARCHAR2(10 CHAR),
  FDDBR       VARCHAR2(100 CHAR),
  JYCS        VARCHAR2(200 CHAR),
  XQGXRQ      TIMESTAMP(6),
  ZZMC        VARCHAR2(100 CHAR)
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table  UF_SSXX_XZXK
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

prompt
prompt Creating table UF_SSXX_XZXK_RL
prompt ==============================
prompt
create table  UF_SSXX_XZXK_RL
(
  UUID               VARCHAR2(255 CHAR) not null,
  CREATOR            VARCHAR2(255 CHAR),
  CREATE_TIME        TIMESTAMP(6),
  MODIFIER           VARCHAR2(255 CHAR),
  MODIFY_TIME        TIMESTAMP(6),
  REC_VER            NUMBER(10),
  DATA_UUID          VARCHAR2(255 CHAR),
  MAINFORM_DATA_UUID VARCHAR2(255 CHAR),
  MAINFORM_FORM_UUID VARCHAR2(255 CHAR),
  SORT_ORDER         VARCHAR2(255 CHAR),
  PARENT_UUID        VARCHAR2(255 CHAR)
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table  UF_SSXX_XZXK_RL
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

prompt
prompt Creating table UF_SSXX_YCHF
prompt ===========================
prompt
create table  UF_SSXX_YCHF
(
  UUID        VARCHAR2(255 CHAR) not null,
  CREATOR     VARCHAR2(255 CHAR),
  CREATE_TIME TIMESTAMP(6),
  MODIFIER    VARCHAR2(255 CHAR),
  MODIFY_TIME TIMESTAMP(6),
  REC_VER     NUMBER(10),
  FORM_UUID   VARCHAR2(255 CHAR),
  STATUS      VARCHAR2(10 CHAR),
  SIGNATURE_  VARCHAR2(10 CHAR),
  XGWJ        VARCHAR2(255 CHAR),
  SQPS        VARCHAR2(200 CHAR),
  ZCH         VARCHAR2(17 CHAR),
  SQSM        VARCHAR2(200 CHAR)
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table  UF_SSXX_YCHF
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

prompt
prompt Creating table UF_SSXX_YCHF_RL
prompt ==============================
prompt
create table  UF_SSXX_YCHF_RL
(
  UUID               VARCHAR2(255 CHAR) not null,
  CREATOR            VARCHAR2(255 CHAR),
  CREATE_TIME        TIMESTAMP(6),
  MODIFIER           VARCHAR2(255 CHAR),
  MODIFY_TIME        TIMESTAMP(6),
  REC_VER            NUMBER(10),
  DATA_UUID          VARCHAR2(255 CHAR),
  MAINFORM_DATA_UUID VARCHAR2(255 CHAR),
  MAINFORM_FORM_UUID VARCHAR2(255 CHAR),
  SORT_ORDER         VARCHAR2(255 CHAR),
  PARENT_UUID        VARCHAR2(255 CHAR)
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table  UF_SSXX_YCHF_RL
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

prompt
prompt Creating table UF_SSXX_YCXX
prompt ===========================
prompt
create table  UF_SSXX_YCXX
(
  UUID        VARCHAR2(255 CHAR) not null,
  CREATOR     VARCHAR2(255 CHAR),
  CREATE_TIME TIMESTAMP(6),
  MODIFIER    VARCHAR2(255 CHAR),
  MODIFY_TIME TIMESTAMP(6),
  REC_VER     NUMBER(10),
  FORM_UUID   VARCHAR2(255 CHAR),
  STATUS      VARCHAR2(10 CHAR),
  SIGNATURE_  VARCHAR2(10 CHAR),
  XGWJ        VARCHAR2(255 CHAR),
  YCSM        VARCHAR2(200 CHAR),
  ZCH         VARCHAR2(17 CHAR),
  YCYY        VARCHAR2(200 CHAR)
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table  UF_SSXX_YCXX
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

prompt
prompt Creating table UF_SSXX_YCXX_RL
prompt ==============================
prompt
create table  UF_SSXX_YCXX_RL
(
  UUID               VARCHAR2(255 CHAR) not null,
  CREATOR            VARCHAR2(255 CHAR),
  CREATE_TIME        TIMESTAMP(6),
  MODIFIER           VARCHAR2(255 CHAR),
  MODIFY_TIME        TIMESTAMP(6),
  REC_VER            NUMBER(10),
  DATA_UUID          VARCHAR2(255 CHAR),
  MAINFORM_DATA_UUID VARCHAR2(255 CHAR),
  MAINFORM_FORM_UUID VARCHAR2(255 CHAR),
  SORT_ORDER         VARCHAR2(255 CHAR),
  PARENT_UUID        VARCHAR2(255 CHAR)
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table  UF_SSXX_YCXX_RL
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

prompt
prompt Creating table UF_SSXX_ZTDJ
prompt ===========================
prompt
create table  UF_SSXX_ZTDJ
(
  UUID        VARCHAR2(255 CHAR) not null,
  CREATOR     VARCHAR2(255 CHAR),
  CREATE_TIME TIMESTAMP(6),
  MODIFIER    VARCHAR2(255 CHAR),
  MODIFY_TIME TIMESTAMP(6),
  REC_VER     NUMBER(10),
  FORM_UUID   VARCHAR2(255 CHAR),
  STATUS      VARCHAR2(10 CHAR),
  SIGNATURE_  VARCHAR2(50 CHAR),
  ZCH         VARCHAR2(15 CHAR),
  SJCZE       FLOAT,
  FDDBRZJHM   VARCHAR2(40 CHAR),
  ZTMC        VARCHAR2(100 CHAR),
  LXDH        VARCHAR2(30 CHAR),
  JYHW        VARCHAR2(3000 CHAR),
  ZTLXDM      VARCHAR2(2 CHAR),
  FDDBRZWDM   VARCHAR2(4 CHAR),
  ZTLX        VARCHAR2(30 CHAR),
  XKJYHW      VARCHAR2(3000 CHAR),
  FDDBR       VARCHAR2(60 CHAR),
  RJCZEDW     VARCHAR2(30 CHAR),
  LLRXX       VARCHAR2(30 CHAR),
  LSZTMC      VARCHAR2(100 CHAR),
  SJCZEDW     VARCHAR2(30 CHAR),
  RJCZE       FLOAT,
  ZS          VARCHAR2(200 CHAR),
  ZTZTDM      VARCHAR2(2 CHAR),
  ZC          VARCHAR2(255 CHAR),
  HZRQ        TIMESTAMP(6),
  ZTZT        VARCHAR2(30 CHAR),
  DJJGDM      VARCHAR2(10 CHAR),
  NBQK        VARCHAR2(4 CHAR),
  DJJG        VARCHAR2(50 CHAR),
  CLRQ        TIMESTAMP(6),
  YYQX        TIMESTAMP(6),
  QTWJ        VARCHAR2(255 CHAR),
  FDDBRZWMC   VARCHAR2(255 CHAR)
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table  UF_SSXX_ZTDJ
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

prompt
prompt Creating table UF_SSXX_ZTDJ_RL
prompt ==============================
prompt
create table  UF_SSXX_ZTDJ_RL
(
  UUID               VARCHAR2(255 CHAR) not null,
  CREATOR            VARCHAR2(255 CHAR),
  CREATE_TIME        TIMESTAMP(6),
  MODIFIER           VARCHAR2(255 CHAR),
  MODIFY_TIME        TIMESTAMP(6),
  REC_VER            NUMBER(10),
  DATA_UUID          VARCHAR2(255 CHAR),
  MAINFORM_DATA_UUID VARCHAR2(255 CHAR),
  MAINFORM_FORM_UUID VARCHAR2(255 CHAR),
  SORT_ORDER         VARCHAR2(255 CHAR),
  PARENT_UUID        VARCHAR2(255 CHAR)
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table  UF_SSXX_ZTDJ_RL
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

prompt
prompt Creating table UF_SSXX_ZZJG
prompt ===========================
prompt
create table  UF_SSXX_ZZJG
(
  UUID        VARCHAR2(255 CHAR) not null,
  CREATOR     VARCHAR2(255 CHAR),
  CREATE_TIME TIMESTAMP(6),
  MODIFIER    VARCHAR2(255 CHAR),
  MODIFY_TIME TIMESTAMP(6),
  REC_VER     NUMBER(10),
  FORM_UUID   VARCHAR2(255 CHAR),
  STATUS      VARCHAR2(10 CHAR),
  SIGNATURE_  VARCHAR2(10 CHAR),
  ZWDM        VARCHAR2(4 CHAR),
  ZW          VARCHAR2(20 CHAR),
  XM          VARCHAR2(60 CHAR)
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table  UF_SSXX_ZZJG
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

prompt
prompt Creating table UF_SSXX_ZZJG_RL
prompt ==============================
prompt
create table  UF_SSXX_ZZJG_RL
(
  UUID               VARCHAR2(255 CHAR) not null,
  CREATOR            VARCHAR2(255 CHAR),
  CREATE_TIME        TIMESTAMP(6),
  MODIFIER           VARCHAR2(255 CHAR),
  MODIFY_TIME        TIMESTAMP(6),
  REC_VER            NUMBER(10),
  DATA_UUID          VARCHAR2(255 CHAR),
  MAINFORM_DATA_UUID VARCHAR2(255 CHAR),
  MAINFORM_FORM_UUID VARCHAR2(255 CHAR),
  SORT_ORDER         VARCHAR2(255 CHAR),
  PARENT_UUID        VARCHAR2(255 CHAR)
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table  UF_SSXX_ZZJG_RL
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


spool off
