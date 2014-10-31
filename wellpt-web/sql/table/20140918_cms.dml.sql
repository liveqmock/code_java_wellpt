
-- Add/modify columns 
alter table CMS_CATEGORY add CATE_NAME VARCHAR2(255 CHAR);
alter table CMS_CATEGORY add CATE_UUID VARCHAR2(255 CHAR);

-- Create table
create table VIEW_FIELD_SELECT
(
  uuid                VARCHAR2(255 CHAR) not null,
  create_time         TIMESTAMP(6),
  creator             VARCHAR2(255 CHAR),
  modifier            VARCHAR2(255 CHAR),
  modify_time         TIMESTAMP(6),
  rec_ver             NUMBER(10),
  content_format      VARCHAR2(255 CHAR),
  default_value       VARCHAR2(255 CHAR),
  dict_code           VARCHAR2(255 CHAR),
  dict_name           VARCHAR2(255 CHAR),
  exact_value         VARCHAR2(255 CHAR),
  field               VARCHAR2(255 CHAR),
  is_area             NUMBER(1),
  is_exact            NUMBER(1),
  is_like             NUMBER(1),
  optdata             VARCHAR2(255 CHAR),
  option_data_source  VARCHAR2(255 CHAR),
  org_input_mode      VARCHAR2(255 CHAR),
  select_type         VARCHAR2(255 CHAR),
  select_type_id      VARCHAR2(255 CHAR),
  show_name           VARCHAR2(255 CHAR),
  select_def_uuid     VARCHAR2(255 CHAR) not null,
  sort_order          NUMBER(10),
  data_source_id      VARCHAR2(255 CHAR),
  data_source_name    VARCHAR2(255 CHAR),
  select_name_column  VARCHAR2(255 CHAR),
  select_value_column VARCHAR2(255 CHAR)
)
tablespace OA_DATA
  pctfree 10
  pctused 40
  initrans 1
  maxtrans 255
  storage
  (
    initial 64
    next 1
    minextents 1
    maxextents unlimited
  );


-- 视图查询定义表添加字段
alter table view_field_select add data_source_id VARCHAR2(255 CHAR);
alter table view_field_select add data_source_name VARCHAR2(255 CHAR);
alter table view_field_select add select_name_column VARCHAR2(255 CHAR);
alter table view_field_select add select_value_column VARCHAR2(255 CHAR);
