---------------------------------------------------
-- Export file for user XZSP_42                  --
-- Created by Administrator on 2014/8/5, 9:53:14 --
---------------------------------------------------

spool meta_formdefinitiontbl.log

prompt
prompt Creating table DYFORM_FORM_DEFINITION
prompt =====================================
prompt
create table  DYFORM_FORM_DEFINITION
(
  UUID                    VARCHAR2(50) not null,
  CREATE_TIME             TIMESTAMP(6),
  CREATOR                 VARCHAR2(50),
  MODIFY_TIME             TIMESTAMP(6),
  REC_VER                 NUMBER(10),
  APPLY_TO                VARCHAR2(255 CHAR),
  OUTER_ID                VARCHAR2(255 CHAR),
  NAME                    VARCHAR2(50 CHAR),
  DISPLAY_NAME            VARCHAR2(100 CHAR),
  REMARK                  VARCHAR2(500 CHAR),
  FORM_DISPLAY            VARCHAR2(255 CHAR),
  HTML                    CLOB,
  MODULE_ID               VARCHAR2(255 CHAR),
  MODULE_NAME             VARCHAR2(255 CHAR),
  PRINT_TEMPLATE_ID       VARCHAR2(255 CHAR),
  PRINT_TEMPLATE_NAME     VARCHAR2(255 CHAR),
  DISPLAY_FORM_MODEL_NAME VARCHAR2(255 CHAR),
  DISPLAY_FORM_MODEL_ID   VARCHAR2(255 CHAR),
  CODE                    VARCHAR2(255 CHAR),
  VERSION                 VARCHAR2(50 CHAR),
  ENABLE_SIGNATURE        VARCHAR2(255 CHAR),
  DEFINITION_JSON         CLOB,
  MODIFIER                VARCHAR2(50),
  RELATION_TBL            VARCHAR2(255 CHAR)
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
comment on column  DYFORM_FORM_DEFINITION.APPLY_TO
  is 'Ӧ����';
comment on column  DYFORM_FORM_DEFINITION.OUTER_ID
  is '������id';
comment on column  DYFORM_FORM_DEFINITION.NAME
  is '����';
comment on column  DYFORM_FORM_DEFINITION.DISPLAY_NAME
  is '��ʾ����';
comment on column  DYFORM_FORM_DEFINITION.REMARK
  is '����';
comment on column  DYFORM_FORM_DEFINITION.FORM_DISPLAY
  is '����ʾ��ʽ �� ���� һ���ǿɱ༭չʾ�� һ����ֱ��չʾ�ı�';
comment on column  DYFORM_FORM_DEFINITION.HTML
  is '����htmlģ��';
comment on column  DYFORM_FORM_DEFINITION.MODULE_ID
  is 'ģ��ID';
comment on column  DYFORM_FORM_DEFINITION.MODULE_NAME
  is 'ģ����';
comment on column  DYFORM_FORM_DEFINITION.PRINT_TEMPLATE_ID
  is '��ӡģ���ID';
comment on column  DYFORM_FORM_DEFINITION.PRINT_TEMPLATE_NAME
  is '��ӡģ�������';
comment on column  DYFORM_FORM_DEFINITION.DISPLAY_FORM_MODEL_NAME
  is '��ʾ���ݵ�����';
comment on column  DYFORM_FORM_DEFINITION.DISPLAY_FORM_MODEL_ID
  is '��ʾ���ݶ�Ӧ�ı�uuid';
comment on column  DYFORM_FORM_DEFINITION.CODE
  is '�����';
comment on column  DYFORM_FORM_DEFINITION.VERSION
  is '�汾 ,��ʽ��1.0';
comment on column  DYFORM_FORM_DEFINITION.ENABLE_SIGNATURE
  is '�Ƿ����ñ�ǩ��';
comment on column  DYFORM_FORM_DEFINITION.DEFINITION_JSON
  is '����JSON';
alter table  DYFORM_FORM_DEFINITION
  add constraint DYFORM_FORM_DEFINITION primary key (UUID)
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
