/*exchange_data_batch*/
alter table IS_EXCHANGE_DATA_BATCH drop column all_unit;
alter table IS_EXCHANGE_DATA_BATCH drop column bf;
alter table IS_EXCHANGE_DATA_BATCH rename column from_user_msg to from_user;

/*exchange_data*/
alter table IS_EXCHANGE_DATA add matter_id VARCHAR2(255 CHAR);

alter table IS_EXCHANGE_DATA add form_id VARCHAR2(255 CHAR);

alter table IS_EXCHANGE_DATA add reserved_text4 VARCHAR2(255 CHAR);
alter table IS_EXCHANGE_DATA add reserved_text5 VARCHAR2(255 CHAR);
alter table IS_EXCHANGE_DATA add reserved_text6 VARCHAR2(255 CHAR);

alter table IS_EXCHANGE_DATA add drafter VARCHAR2(255 CHAR);
alter table IS_EXCHANGE_DATA add draft_time TIMESTAMP(6);
alter table IS_EXCHANGE_DATA add releaser VARCHAR2(255 CHAR);

alter table IS_EXCHANGE_DATA drop column send_limit_status;

alter table IS_EXCHANGE_DATA rename column send_limit_num to upload_limit_num;
alter table IS_EXCHANGE_DATA add content_status VARCHAR2(255 CHAR);

-- Create table
create table IS_EXCHANGE_SEND_MONITOR
(
  uuid           VARCHAR2(255 CHAR) not null,
  create_time    TIMESTAMP(6),
  creator        VARCHAR2(255 CHAR),
  modifier       VARCHAR2(255 CHAR),
  modify_time    TIMESTAMP(6),
  rec_ver        NUMBER(10),
  from_id        VARCHAR2(255 CHAR),
  limit_time     TIMESTAMP(6),
  send_limit_num NUMBER(10),
  send_msg       VARCHAR2(255 CHAR),
  send_node      VARCHAR2(255 CHAR),
  send_time      TIMESTAMP(6),
  send_type      VARCHAR2(255 CHAR),
  send_user      VARCHAR2(255 CHAR),
  data_uuid      VARCHAR2(255 CHAR)
);
/*exchange_data_monitor*/
alter table IS_EXCHANGE_DATA_MONITOR drop column reapply_status;
alter table IS_EXCHANGE_DATA_MONITOR drop column reply_limit_status;
alter table IS_EXCHANGE_DATA_MONITOR drop column reapply_user_msg;
alter table IS_EXCHANGE_DATA_MONITOR rename column reply_user_msg to reply_user;
alter table IS_EXCHANGE_DATA_MONITOR rename column cancel_user_msg to cancel_user;
alter table IS_EXCHANGE_DATA_MONITOR add send_id VARCHAR2(255 CHAR);
alter table IS_EXCHANGE_DATA_MONITOR add matter_id VARCHAR2(255 CHAR);
alter table IS_EXCHANGE_DATA_MONITOR add unit_type VARCHAR2(255 CHAR);

alter table IS_EXCHANGE_DATA_LOG add matter_id VARCHAR2(255 CHAR);
alter table IS_EXCHANGE_DATA_REPEST add matter_id VARCHAR2(255 CHAR);
alter table IS_EXCHANGE_DATA_REPLY add matter_id VARCHAR2(255 CHAR);
alter table IS_EXCHANGE_DATA_ROUTE add matter_id VARCHAR2(255 CHAR);

/*
alter table IS_EXCHANGE_DATA_BATCH drop column limit_time;
alter table IS_EXCHANGE_DATA drop column send_node;
alter table IS_EXCHANGE_DATA_MONITOR drop column from_id;
*/