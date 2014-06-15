-- Create table
create table DYFORM_FORM_DEFINITION
(
  uuid                    varchar2(50),
  create_time             TIMESTAMP(6),
  creator                 varchar2(50),
  modify_time             TIMESTAMP(6),
  rec_ver                 NUMBER(10),
  APPLY_TO                VARCHAR2(255 CHAR),
  ID                      VARCHAR2(255 CHAR),
  NAME                    VARCHAR2(50 CHAR),
  Display_NAME            VARCHAR2(100 CHAR),
  remark                  VARCHAR2(500 CHAR),
  FORM_DISPLAY            VARCHAR2(255 CHAR),
  HTML                    clob,
  MODULE_ID               VARCHAR2(255 CHAR),
  MODULE_NAME             VARCHAR2(255 CHAR),
  PRINT_TEMPLATE_ID       VARCHAR2(255 CHAR),
  PRINT_TEMPLATE_NAME     VARCHAR2(255 CHAR),
  DISPLAY_FORM_MODEL_NAME VARCHAR2(255 CHAR),
  DISPLAY_FORM_MODEL_ID   VARCHAR2(255 CHAR),
  CODE                    VARCHAR2(255 CHAR),
  VERSION                 VARCHAR2(50 CHAR),
  ENABLE_SIGNATURE        VARCHAR2(255 CHAR),
  DEFINITION_JSON         clob
)
;
-- Add comments to the columns 
comment on column DYFORM_FORM_DEFINITION.APPLY_TO
  is '应用于';
comment on column DYFORM_FORM_DEFINITION.ID
  is '表单属性id';
comment on column DYFORM_FORM_DEFINITION.NAME
  is '表名';
comment on column DYFORM_FORM_DEFINITION.Display_NAME
  is '显示名称';
comment on column DYFORM_FORM_DEFINITION.remark
  is '描述';
comment on column DYFORM_FORM_DEFINITION.FORM_DISPLAY
  is '表单显示形式 ： 两种 一种是可编辑展示、 一种是直接展示文本';
comment on column DYFORM_FORM_DEFINITION.HTML
  is '定义html模板';
comment on column DYFORM_FORM_DEFINITION.MODULE_ID
  is '模块ID';
comment on column DYFORM_FORM_DEFINITION.MODULE_NAME
  is '模块名';
comment on column DYFORM_FORM_DEFINITION.PRINT_TEMPLATE_ID
  is '打印模板的ID';
comment on column DYFORM_FORM_DEFINITION.PRINT_TEMPLATE_NAME
  is '打印模板的名称';
comment on column DYFORM_FORM_DEFINITION.DISPLAY_FORM_MODEL_NAME
  is '显示单据的名称';
comment on column DYFORM_FORM_DEFINITION.DISPLAY_FORM_MODEL_ID
  is '显示单据对应的表单uuid';
comment on column DYFORM_FORM_DEFINITION.CODE
  is '表单编号';
comment on column DYFORM_FORM_DEFINITION.VERSION
  is '版本 ,形式：1.0';
comment on column DYFORM_FORM_DEFINITION.ENABLE_SIGNATURE
  is '是否启用表单签名';
comment on column DYFORM_FORM_DEFINITION.DEFINITION_JSON
  is '定义JSON';
-- Create/Recreate primary, unique and foreign key constraints 
alter table DYFORM_FORM_DEFINITION
  add constraint DYFORM_FORM_DEFINITION primary key (UUID);
-- Add/modify columns 
alter table DYFORM_FORM_DEFINITION add MODIFIER VARCHAR2(50);

-- Add/modify columns 
alter table DYFORM_FORM_DEFINITION rename column ID to OUTER_ID;

