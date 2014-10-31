-- Add/modify columns 
alter table bm_advisory_complaints add CREATOR VARCHAR2(255 CHAR);
alter table bm_advisory_complaints add CREATE_TIME TIMESTAMP(6);
alter table bm_advisory_complaints add MODIFIER VARCHAR2(255 CHAR);
alter table bm_advisory_complaints add MODIFY_TIME TIMESTAMP(6);
alter table bm_advisory_complaints add REC_VER NUMBER(10);

alter table bm_publicity_attach add CREATOR VARCHAR2(255 CHAR);
alter table bm_publicity_attach add CREATE_TIME TIMESTAMP(6);
alter table bm_publicity_attach add MODIFIER VARCHAR2(255 CHAR);
alter table bm_publicity_attach add MODIFY_TIME TIMESTAMP(6);
alter table bm_publicity_attach add REC_VER NUMBER(10);

alter table bm_register_apply add CREATOR VARCHAR2(255 CHAR);
alter table bm_register_apply add CREATE_TIME TIMESTAMP(6);
alter table bm_register_apply add MODIFIER VARCHAR2(255 CHAR);
alter table bm_register_apply add MODIFY_TIME TIMESTAMP(6);
alter table bm_register_apply add REC_VER NUMBER(10);


alter table bm_self_publicity_apply add CREATOR VARCHAR2(255 CHAR);
alter table bm_self_publicity_apply add CREATE_TIME TIMESTAMP(6);
alter table bm_self_publicity_apply add MODIFIER VARCHAR2(255 CHAR);
alter table bm_self_publicity_apply add MODIFY_TIME TIMESTAMP(6);
alter table bm_self_publicity_apply add REC_VER NUMBER(10);


alter table bm_submit_verify add CREATOR VARCHAR2(255 CHAR);
alter table bm_submit_verify add CREATE_TIME TIMESTAMP(6);
alter table bm_submit_verify add MODIFIER VARCHAR2(255 CHAR);
alter table bm_submit_verify add MODIFY_TIME TIMESTAMP(6);
alter table bm_submit_verify add REC_VER NUMBER(10);

alter table fm_folder_role_user add CREATOR VARCHAR2(255 CHAR);
alter table fm_folder_role_user add CREATE_TIME TIMESTAMP(6);
alter table fm_folder_role_user add MODIFIER VARCHAR2(255 CHAR);
alter table fm_folder_role_user add MODIFY_TIME TIMESTAMP(6);
alter table fm_folder_role_user add REC_VER NUMBER(10);

alter table mail_users add uuid VARCHAR2(255 CHAR);
alter table mail_users add CREATOR VARCHAR2(255 CHAR);
alter table mail_users add CREATE_TIME TIMESTAMP(6);
alter table mail_users add MODIFIER VARCHAR2(255 CHAR);
alter table mail_users add MODIFY_TIME TIMESTAMP(6);
alter table mail_users add REC_VER NUMBER(10);

-- Create/Recreate primary, unique and foreign key constraints 
alter table MAIL_USERS
  drop constraint SYS_C0033531 cascade;
alter table MAIL_USERS
  add primary key (UUID)
  using index 
  tablespace USERS
  pctfree 10
  initrans 2
  maxtrans 255;