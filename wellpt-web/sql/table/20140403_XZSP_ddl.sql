create table XZSP_PRINT_RECORD
(
  uuid                    VARCHAR2(255 CHAR) not null,
  create_time             TIMESTAMP(6),
  creator                 VARCHAR2(255 CHAR),
  modifier                VARCHAR2(255 CHAR),
  modify_time             TIMESTAMP(6),
  rec_ver                 NUMBER(10),
  banjian_uuid            VARCHAR2(255 CHAR),
  matters_uuid            VARCHAR2(255 CHAR),
  print_template_id       VARCHAR2(255 CHAR),
  print_times             NUMBER(10),
  print_userid            VARCHAR2(255 CHAR),
  print_user_name         VARCHAR2(255 CHAR),
  project_uuid            VARCHAR2(255 CHAR),
  second_print_reason     VARCHAR2(255 CHAR),
  task_instance_serial_no VARCHAR2(255 CHAR)
);
commit;