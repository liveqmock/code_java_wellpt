---------------------------------------------------
-- Export file for user XZSP_42                  --
-- Created by Administrator on 2014/8/6, 2:55:10 --
---------------------------------------------------

spool 13、行政审批表单定义 20140805_dyform_formtbl_meta_szsp_ddl.log

prompt
prompt Creating table UF_XZSP_BJSQ
prompt ===========================
prompt
create table   UF_XZSP_BJSQ
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
  XMMC        VARCHAR2(255 CHAR),
  BJLX        VARCHAR2(255 CHAR),
  SJRQ        TIMESTAMP(6),
  SQRMC       VARCHAR2(255 CHAR),
  CNBJRQ      TIMESTAMP(6),
  LSH         VARCHAR2(255 CHAR),
  MM          VARCHAR2(255 CHAR),
  SXBH        VARCHAR2(255 CHAR),
  SLDW        VARCHAR2(255 CHAR),
  XMBH        VARCHAR2(255 CHAR),
  SXMC        VARCHAR2(255 CHAR),
  ZBWJ        VARCHAR2(255 CHAR),
  QJSJ        TIMESTAMP(6),
  BZ          VARCHAR2(255 CHAR),
  SQDWMC      VARCHAR2(255 CHAR),
  CWWJ        VARCHAR2(255 CHAR),
  GCTJWJ      VARCHAR2(255 CHAR),
  SQDWLXFS    VARCHAR2(255 CHAR),
  SQRLXFS     VARCHAR2(255 CHAR),
  SQDWDM      VARCHAR2(255 CHAR),
  SQRBH       VARCHAR2(255 CHAR),
  CNBJR       NUMBER(10),
  LXR         VARCHAR2(255 CHAR),
  FRDB        VARCHAR2(255 CHAR),
  DLR         VARCHAR2(255 CHAR),
  YWZT        VARCHAR2(255 CHAR),
  DZ          VARCHAR2(255 CHAR),
  SPBZ        VARCHAR2(255 CHAR),
  SXBZ        VARCHAR2(255 CHAR),
  SFJS        VARCHAR2(255 CHAR),
  DJCKBZ      VARCHAR2(255 CHAR),
  YB          VARCHAR2(255 CHAR),
  QJBS        VARCHAR2(255 CHAR),
  BJZT        VARCHAR2(255 CHAR),
  SLDWDM      VARCHAR2(255 CHAR),
  QJBSDM      VARCHAR2(255 CHAR),
  SLDWMC      VARCHAR2(255 CHAR),
  QJBSMC      VARCHAR2(255 CHAR),
  SFJSMC      VARCHAR2(255 CHAR),
  SFJSDM      VARCHAR2(255 CHAR),
  BJLXMC      VARCHAR2(255 CHAR),
  BJLXDM      VARCHAR2(255 CHAR),
  ZXYWCZ      VARCHAR2(255 CHAR),
  SBLY        VARCHAR2(255 CHAR),
  SBLYMC      VARCHAR2(255 CHAR),
  SBLYDM      VARCHAR2(255 CHAR),
  XMUUID      VARCHAR2(255 CHAR),
  XMDBH       VARCHAR2(255 CHAR)
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
alter table   UF_XZSP_BJSQ
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
prompt Creating table UF_XZSP_BJSQ_RL
prompt ==============================
prompt
create table   UF_XZSP_BJSQ_RL
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
alter table   UF_XZSP_BJSQ_RL
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
prompt Creating table UF_XZSP_BLACK
prompt ============================
prompt
create table   UF_XZSP_BLACK
(
  UUID             VARCHAR2(255 CHAR) not null,
  CREATOR          VARCHAR2(255 CHAR),
  CREATE_TIME      TIMESTAMP(6),
  MODIFIER         VARCHAR2(255 CHAR),
  MODIFY_TIME      TIMESTAMP(6),
  REC_VER          NUMBER(10),
  FORM_UUID        VARCHAR2(255 CHAR),
  STATUS           VARCHAR2(10 CHAR),
  SIGNATURE_       VARCHAR2(10 CHAR),
  CZRY             VARCHAR2(255 CHAR),
  JOINBLACK_REASON VARCHAR2(255 CHAR),
  CZSJ             VARCHAR2(255 CHAR)
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
alter table   UF_XZSP_BLACK
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
prompt Creating table UF_XZSP_BLACK_RL
prompt ===============================
prompt
create table   UF_XZSP_BLACK_RL
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
alter table   UF_XZSP_BLACK_RL
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
prompt Creating table UF_XZSP_CHARGE
prompt =============================
prompt
create table   UF_XZSP_CHARGE
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
  JFGLBJDH    VARCHAR2(255 CHAR),
  SFMC        VARCHAR2(255 CHAR),
  JFDWHGRBH   VARCHAR2(255 CHAR),
  SFSXBH      VARCHAR2(255 CHAR),
  SFZT        VARCHAR2(255 CHAR),
  JFDWHGRMC   VARCHAR2(255 CHAR),
  JFJE        FLOAT,
  SFMCBH      VARCHAR2(4 CHAR),
  SFBH        VARCHAR2(50 CHAR)
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
alter table   UF_XZSP_CHARGE
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
prompt Creating table UF_XZSP_CHARGE_RL
prompt ================================
prompt
create table   UF_XZSP_CHARGE_RL
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
alter table   UF_XZSP_CHARGE_RL
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
prompt Creating table UF_XZSP_DWK_BLACK
prompt ================================
prompt
create table   UF_XZSP_DWK_BLACK
(
  UUID            VARCHAR2(255 CHAR) not null,
  CREATOR         VARCHAR2(255 CHAR),
  CREATE_TIME     TIMESTAMP(6),
  MODIFIER        VARCHAR2(255 CHAR),
  MODIFY_TIME     TIMESTAMP(6),
  REC_VER         NUMBER(10),
  FORM_UUID       VARCHAR2(255 CHAR),
  STATUS          VARCHAR2(10 CHAR),
  SIGNATURE_      VARCHAR2(10 CHAR),
  DWLXRYJ         VARCHAR2(255 CHAR),
  DWFRDZYJ        VARCHAR2(255 CHAR),
  DWFRLXDH        VARCHAR2(255 CHAR),
  DWDH            VARCHAR2(255 CHAR),
  DWMC            VARCHAR2(255 CHAR),
  SHJRHMD         VARCHAR2(255 CHAR),
  DWFRDM          VARCHAR2(255 CHAR),
  SHJRHMD_REAL    VARCHAR2(1000 CHAR),
  ZZJGDM          VARCHAR2(255 CHAR),
  DWLXR           VARCHAR2(255 CHAR),
  DWLXRSJ         VARCHAR2(255 CHAR),
  DWFRDB          VARCHAR2(255 CHAR),
  DWDZ            VARCHAR2(255 CHAR),
  DWCZ            VARCHAR2(255 CHAR),
  SHJRHMD_DISPLAY VARCHAR2(1000 CHAR),
  DWLXRZJH        VARCHAR2(255 CHAR)
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
alter table   UF_XZSP_DWK_BLACK
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
prompt Creating table UF_XZSP_DWK_BLACK_RL
prompt ===================================
prompt
create table   UF_XZSP_DWK_BLACK_RL
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
alter table   UF_XZSP_DWK_BLACK_RL
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
prompt Creating table UF_XZSP_LICENSE_DEF
prompt ==================================
prompt
create table   UF_XZSP_LICENSE_DEF
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
  ZZLX        VARCHAR2(255 CHAR),
  DZQMZSYZ    VARCHAR2(255 CHAR),
  ZZFZXX      VARCHAR2(50 CHAR),
  DZQZQZSJ    TIMESTAMP(6),
  LY          VARCHAR2(255 CHAR),
  DZQMSFBZ    VARCHAR2(50 CHAR),
  SPJGLX      VARCHAR2(255 CHAR),
  ZZKZXX      VARCHAR2(2000 CHAR),
  YWXW        VARCHAR2(50 CHAR),
  DZQZMC      VARCHAR2(30 CHAR),
  DZQZQZR     VARCHAR2(20 CHAR),
  BZDWMC      VARCHAR2(255 CHAR),
  YXJZSJ      TIMESTAMP(6),
  GXRQ        TIMESTAMP(6),
  XWSJ        TIMESTAMP(6),
  MJ          VARCHAR2(4 CHAR),
  YXKSSJ      TIMESTAMP(6),
  ZZMC        VARCHAR2(255 CHAR),
  DZQMZS      VARCHAR2(255 CHAR),
  ZZBGJL      VARCHAR2(255 CHAR),
  YXXBS       VARCHAR2(255 CHAR),
  ZZSYZBH     VARCHAR2(255 CHAR),
  HZSJ        TIMESTAMP(6),
  DZQMSJ      VARCHAR2(500 CHAR),
  BZDWDM      VARCHAR2(255 CHAR),
  XWMS        VARCHAR2(20 CHAR),
  ZZH         VARCHAR2(255 CHAR),
  ZZSYZMC     VARCHAR2(255 CHAR),
  ZZDM        VARCHAR2(255 CHAR),
  XCGXRQ      TIMESTAMP(6),
  DZQZGZ      VARCHAR2(30 CHAR),
  JGRYMC      VARCHAR2(255 CHAR),
  ZZFJ        VARCHAR2(255 CHAR)
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
alter table   UF_XZSP_LICENSE_DEF
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
prompt Creating table UF_XZSP_LICENSE_DEF_RL
prompt =====================================
prompt
create table   UF_XZSP_LICENSE_DEF_RL
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
alter table   UF_XZSP_LICENSE_DEF_RL
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
prompt Creating table UF_XZSP_MATERIAL_DEF
prompt ===================================
prompt
create table   UF_XZSP_MATERIAL_DEF
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
  CLLXDM      VARCHAR2(255 CHAR),
  CLTJSJ      TIMESTAMP(6),
  CLSSXMBH    VARCHAR2(28 CHAR),
  CLMC        VARCHAR2(255 CHAR),
  CLYXXBS     VARCHAR2(255 CHAR),
  CLSYZBH     VARCHAR2(255 CHAR),
  CLBH        VARCHAR2(28 CHAR),
  CLSYZMC     VARCHAR2(255 CHAR),
  CLFJ        VARCHAR2(255 CHAR)
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
alter table   UF_XZSP_MATERIAL_DEF
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
prompt Creating table UF_XZSP_MATERIAL_DEF_RL
prompt ======================================
prompt
create table   UF_XZSP_MATERIAL_DEF_RL
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
alter table   UF_XZSP_MATERIAL_DEF_RL
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
prompt Creating table UF_XZSP_MATTERS
prompt ==============================
prompt
create table   UF_XZSP_MATTERS
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
  SXMC        VARCHAR2(255 CHAR),
  SXBH        VARCHAR2(255 CHAR),
  SXLX_       VARCHAR2(255 CHAR),
  SSFSXMC     VARCHAR2(255 CHAR),
  SSFSXBH     VARCHAR2(255 CHAR),
  SQBGMB      VARCHAR2(255 CHAR),
  LXR         VARCHAR2(255 CHAR),
  SFMC        VARCHAR2(255 CHAR),
  SSBMDW      VARCHAR2(255 CHAR),
  SLHZRY_     VARCHAR2(255 CHAR),
  LXDH        VARCHAR2(255 CHAR),
  FDBLQX      VARCHAR2(255 CHAR),
  BJSX        VARCHAR2(255 CHAR),
  BLLC        VARCHAR2(2000 CHAR),
  SQSLJGDM    VARCHAR2(255 CHAR),
  BLNR        VARCHAR2(2000 CHAR),
  SLSJ        VARCHAR2(255 CHAR),
  XMLX_       VARCHAR2(255 CHAR),
  SLDD        VARCHAR2(255 CHAR),
  SPTJ        VARCHAR2(2000 CHAR),
  SSBMDWDM    VARCHAR2(255 CHAR),
  BLYJ        VARCHAR2(2000 CHAR),
  SQBGFB      VARCHAR2(255 CHAR),
  SPJGCLLYDM  VARCHAR2(255 CHAR),
  MXYH_       VARCHAR2(255 CHAR),
  SPJGCLLY    VARCHAR2(255 CHAR),
  SPJGCLLX_   VARCHAR2(2000 CHAR),
  CKDJRY_     VARCHAR2(255 CHAR),
  SPJGYXQX    VARCHAR2(255 CHAR),
  SQSLJG      VARCHAR2(255 CHAR),
  BJLX_       VARCHAR2(255 CHAR),
  SFYJ        VARCHAR2(2000 CHAR),
  CNBLQX      VARCHAR2(255 CHAR),
  SFBZ        VARCHAR2(2000 CHAR),
  SFZT        VARCHAR2(2000 CHAR),
  SFGS        VARCHAR2(255 CHAR),
  BZ          VARCHAR2(255 CHAR),
  ZNJHSB      VARCHAR2(255 CHAR),
  BHCLSX      VARCHAR2(2000 CHAR),
  BHCLSXBH    VARCHAR2(600 CHAR),
  GLSPLC      VARCHAR2(255 CHAR),
  QZSX        VARCHAR2(1000 CHAR),
  QZSXBH      VARCHAR2(255 CHAR),
  GLSPLCID    VARCHAR2(255 CHAR),
  SSJD        VARCHAR2(255 CHAR),
  XSGCTLB_    VARCHAR2(255 CHAR),
  QY_         VARCHAR2(255 CHAR),
  WWXS_       VARCHAR2(255 CHAR),
  SSJDDM      VARCHAR2(255 CHAR),
  SXID        VARCHAR2(255 CHAR),
  SSFSX       VARCHAR2(255 CHAR),
  SSFSXDM     VARCHAR2(255 CHAR),
  SXLXMC      VARCHAR2(255 CHAR),
  SXLX        VARCHAR2(255 CHAR),
  XMLXMC      VARCHAR2(255 CHAR),
  XMLX        VARCHAR2(255 CHAR),
  BJLXMC      VARCHAR2(255 CHAR),
  BJLX        VARCHAR2(255 CHAR),
  BJSX_       VARCHAR2(255 CHAR),
  BJSXMC      VARCHAR2(255 CHAR),
  MXYHMC      VARCHAR2(255 CHAR),
  SSBMDWMC    VARCHAR2(255 CHAR),
  CKDJRYDM    VARCHAR2(255 CHAR),
  CKDJRY      VARCHAR2(255 CHAR),
  SLHZRYDM    VARCHAR2(255 CHAR),
  SPJGCLLXDM  VARCHAR2(1000 CHAR),
  SQSLJGMC    VARCHAR2(255 CHAR),
  SPJGCLLX    VARCHAR2(1000 CHAR),
  QZSXDM      VARCHAR2(255 CHAR),
  SPJGCLLYMC  VARCHAR2(255 CHAR),
  QZSXMC      VARCHAR2(255 CHAR),
  BHCLSXDM    VARCHAR2(1000 CHAR),
  BHCLSXMC    VARCHAR2(255 CHAR),
  GLSPLCMC    VARCHAR2(255 CHAR),
  SSJDMC      VARCHAR2(255 CHAR),
  XSGCTLB     VARCHAR2(255 CHAR),
  XSGCTLBDM   VARCHAR2(255 CHAR),
  QY          VARCHAR2(255 CHAR),
  QYDM        VARCHAR2(255 CHAR),
  WWXS        VARCHAR2(255 CHAR),
  WWXSDM      VARCHAR2(255 CHAR),
  MXYH        VARCHAR2(255 CHAR),
  SLHZRY      VARCHAR2(255 CHAR)
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
prompt Creating table UF_XZSP_MATTERS_DEF_STUFF
prompt ========================================
prompt
create table   UF_XZSP_MATTERS_DEF_STUFF
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
  CLSM        VARCHAR2(255 CHAR),
  CLMC        VARCHAR2(255 CHAR),
  CLXZ        VARCHAR2(255 CHAR),
  YJDZSL      VARCHAR2(255 CHAR),
  CLBH        VARCHAR2(255 CHAR),
  CLLXMC      VARCHAR2(255 CHAR),
  CLLXDM      VARCHAR2(255 CHAR),
  YJZZSL      VARCHAR2(255 CHAR)
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
alter table   UF_XZSP_MATTERS_DEF_STUFF
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
prompt Creating table UF_XZSP_MATTERS_DEF_STUFF_RL
prompt ===========================================
prompt
create table   UF_XZSP_MATTERS_DEF_STUFF_RL
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
alter table   UF_XZSP_MATTERS_DEF_STUFF_RL
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
prompt Creating table UF_XZSP_MATTERS_GZR
prompt ==================================
prompt
create table   UF_XZSP_MATTERS_GZR
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
  LX          VARCHAR2(255 CHAR),
  BZ          VARCHAR2(255 CHAR),
  GZR         NUMBER(10)
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
alter table   UF_XZSP_MATTERS_GZR
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
prompt Creating table UF_XZSP_MATTERS_GZR_RL
prompt =====================================
prompt
create table   UF_XZSP_MATTERS_GZR_RL
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
alter table   UF_XZSP_MATTERS_GZR_RL
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
prompt Creating table UF_XZSP_MATTERS_RL
prompt =================================
prompt
create table   UF_XZSP_MATTERS_RL
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

prompt
prompt Creating table UF_XZSP_RECEIPT_DEFINITION
prompt =========================================
prompt
create table   UF_XZSP_RECEIPT_DEFINITION
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
  BJDH        VARCHAR2(255 CHAR),
  HZFJ        VARCHAR2(255 CHAR),
  HZSJ        TIMESTAMP(6),
  HZDMC       VARCHAR2(255 CHAR),
  HZDBH       VARCHAR2(50 CHAR),
  HZBH        VARCHAR2(50 CHAR)
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
alter table   UF_XZSP_RECEIPT_DEFINITION
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
prompt Creating table UF_XZSP_RECEIPT_DEFINITION_RL
prompt ============================================
prompt
create table   UF_XZSP_RECEIPT_DEFINITION_RL
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
alter table   UF_XZSP_RECEIPT_DEFINITION_RL
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
prompt Creating table UF_XZSP_SBR_BLACK
prompt ================================
prompt
create table   UF_XZSP_SBR_BLACK
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
  LXDH        VARCHAR2(255 CHAR),
  SJH         VARCHAR2(255 CHAR),
  ZJLXDM      VARCHAR2(1 CHAR),
  ZJH         VARCHAR2(255 CHAR),
  DZYJ        VARCHAR2(255 CHAR),
  ZJLXMC      VARCHAR2(255 CHAR),
  SHJRHMD     VARCHAR2(255 CHAR),
  XM          VARCHAR2(255 CHAR),
  XB          VARCHAR2(50 CHAR),
  ZJLX        VARCHAR2(255 CHAR)
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
alter table   UF_XZSP_SBR_BLACK
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
prompt Creating table UF_XZSP_SBR_BLACK_RL
prompt ===================================
prompt
create table   UF_XZSP_SBR_BLACK_RL
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
alter table   UF_XZSP_SBR_BLACK_RL
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
prompt Creating table UF_XZSP_SUBBJSQD
prompt ===============================
prompt
create table   UF_XZSP_SUBBJSQD
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
  SJZZSL      NUMBER(10),
  SSSXMC      VARCHAR2(255 CHAR),
  TJSJSJ      VARCHAR2(255 CHAR),
  BJYY        VARCHAR2(255 CHAR),
  CLMC        VARCHAR2(255 CHAR),
  YJDZSL      VARCHAR2(255 CHAR),
  CLLX        VARCHAR2(255 CHAR),
  SFBJCL      VARCHAR2(255 CHAR),
  YJZZSL      VARCHAR2(255 CHAR),
  DJFJ        VARCHAR2(255 CHAR),
  SSSXBH      VARCHAR2(255 CHAR),
  CLLXMC      VARCHAR2(255 CHAR),
  CLLXDM      VARCHAR2(255 CHAR),
  SJDZSL      NUMBER(10)
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
alter table   UF_XZSP_SUBBJSQD
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
prompt Creating table UF_XZSP_SUBBJSQD_BJ
prompt ==================================
prompt
create table   UF_XZSP_SUBBJSQD_BJ
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
  SJZZSL      NUMBER(10),
  SSSXMC      VARCHAR2(255 CHAR),
  BJYY        VARCHAR2(255 CHAR),
  BJZT        VARCHAR2(255 CHAR),
  CLMC        VARCHAR2(255 CHAR),
  YJDZSL      VARCHAR2(255 CHAR),
  CLLX        VARCHAR2(255 CHAR),
  YJZZSL      VARCHAR2(255 CHAR),
  SJDZSL      NUMBER(10),
  DJFJ        VARCHAR2(255 CHAR),
  SSSXBH      VARCHAR2(255 CHAR),
  CLLXMC      VARCHAR2(255 CHAR),
  CLLXDM      VARCHAR2(255 CHAR)
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
alter table   UF_XZSP_SUBBJSQD_BJ
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
prompt Creating table UF_XZSP_SUBBJSQD_BJ_RL
prompt =====================================
prompt
create table   UF_XZSP_SUBBJSQD_BJ_RL
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
alter table   UF_XZSP_SUBBJSQD_BJ_RL
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
prompt Creating table UF_XZSP_SUBBJSQD_CAD
prompt ===================================
prompt
create table   UF_XZSP_SUBBJSQD_CAD
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
  GCTFJ       VARCHAR2(255 CHAR),
  GCTMC       VARCHAR2(255 CHAR)
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
alter table   UF_XZSP_SUBBJSQD_CAD
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
prompt Creating table UF_XZSP_SUBBJSQD_CAD_RL
prompt ======================================
prompt
create table   UF_XZSP_SUBBJSQD_CAD_RL
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
alter table   UF_XZSP_SUBBJSQD_CAD_RL
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
prompt Creating table UF_XZSP_SUBBJSQD_RL
prompt ==================================
prompt
create table   UF_XZSP_SUBBJSQD_RL
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
alter table   UF_XZSP_SUBBJSQD_RL
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
prompt Creating table UF_XZSP_SUBCLLIST_ADD
prompt ====================================
prompt
create table   UF_XZSP_SUBCLLIST_ADD
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
  CLMC        VARCHAR2(255 CHAR),
  SCSJ        TIMESTAMP(6),
  TJSJSJ      VARCHAR2(255 CHAR),
  SCR         VARCHAR2(255 CHAR),
  DJFJ        VARCHAR2(255 CHAR),
  SJZZSL      NUMBER(10)
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
alter table   UF_XZSP_SUBCLLIST_ADD
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
prompt Creating table UF_XZSP_SUBCLLIST_ADD_RL
prompt =======================================
prompt
create table   UF_XZSP_SUBCLLIST_ADD_RL
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
alter table   UF_XZSP_SUBCLLIST_ADD_RL
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
prompt Creating table UF_XZSP_XMK_BLACK
prompt ================================
prompt
create table   UF_XZSP_XMK_BLACK
(
  UUID         VARCHAR2(255 CHAR) not null,
  CREATOR      VARCHAR2(255 CHAR),
  CREATE_TIME  TIMESTAMP(6),
  MODIFIER     VARCHAR2(255 CHAR),
  MODIFY_TIME  TIMESTAMP(6),
  REC_VER      NUMBER(10),
  FORM_UUID    VARCHAR2(255 CHAR),
  STATUS       VARCHAR2(10 CHAR),
  SIGNATURE_   VARCHAR2(50 CHAR),
  XMJSDWZZJGDM VARCHAR2(255 CHAR),
  TDGYFS       VARCHAR2(100 CHAR),
  XMDZ         VARCHAR2(255 CHAR),
  XMSJDWZZJGDM VARCHAR2(255 CHAR),
  XMBH         VARCHAR2(255 CHAR),
  GLXMBH       VARCHAR2(255 CHAR),
  XMMC         VARCHAR2(255 CHAR),
  WDZYLB       VARCHAR2(100 CHAR),
  XMSZQY       VARCHAR2(100 CHAR),
  XMXZ         VARCHAR2(100 CHAR),
  SFBM         VARCHAR2(100 CHAR),
  JSGM         FLOAT,
  XMZRDWMC     VARCHAR2(255 CHAR),
  SJXMBH       VARCHAR2(255 CHAR),
  LXRYJ        VARCHAR2(255 CHAR),
  XTXM         VARCHAR2(100 CHAR),
  ZJLY         VARCHAR2(100 CHAR),
  SSFL         VARCHAR2(100 CHAR),
  XMJSDWMC     VARCHAR2(255 CHAR),
  MGXZ         VARCHAR2(100 CHAR),
  LXRSJHM      VARCHAR2(255 CHAR),
  JZXZ         VARCHAR2(100 CHAR),
  LXRXM        VARCHAR2(255 CHAR),
  XMBZ         VARCHAR2(255 CHAR),
  XMZRDWZZJGDM VARCHAR2(255 CHAR),
  ZDGC         VARCHAR2(100 CHAR),
  ZDGZ         VARCHAR2(100 CHAR),
  ZTZGM        FLOAT,
  SHJRHMD      VARCHAR2(100 CHAR),
  XZFQ         VARCHAR2(100 CHAR),
  YDMJ         FLOAT,
  JZMJ         FLOAT,
  XMSJDWMC     VARCHAR2(255 CHAR),
  JZDJ         VARCHAR2(100 CHAR),
  SJXMMC       VARCHAR2(255 CHAR),
  XMJSDWDM     VARCHAR2(255 CHAR),
  XMSJDWDM     VARCHAR2(255 CHAR),
  LXRZJH       VARCHAR2(255 CHAR),
  XMDWSYZ      VARCHAR2(255 CHAR),
  XMDWSYZDM    VARCHAR2(255 CHAR),
  XMDWSYZMC    VARCHAR2(255 CHAR),
  XMDBH        VARCHAR2(255 CHAR)
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
alter table   UF_XZSP_XMK_BLACK
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
prompt Creating table UF_XZSP_XMK_BLACK_RL
prompt ===================================
prompt
create table   UF_XZSP_XMK_BLACK_RL
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
alter table   UF_XZSP_XMK_BLACK_RL
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
