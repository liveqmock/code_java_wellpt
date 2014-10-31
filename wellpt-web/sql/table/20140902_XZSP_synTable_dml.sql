alter table IS_SYNCHRONOUS_SOURCE_TABLE add IS_RELATION_TABLE NUMBER(1);
alter table IS_SYNCHRONOUS_SOURCE_TABLE add RELATION_TABLE varchar2(2000 char);
alter table IS_SYNCHRONOUS_SOURCE_TABLE add tenant VARCHAR2(255 CHAR);
alter table IS_SYNCHRONOUS_SOURCE_TABLE add pre_modify_time TIMESTAMP(6);
-- Add/modify columns 
alter table IS_SYNCHRONOUS_SOURCE_FIELD add foreign_key_table VARCHAR2(255 CHAR);

