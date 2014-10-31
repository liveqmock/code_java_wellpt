-- CD_ID_GENERATOR
alter table cd_id_generator add uuid VARCHAR2(255 CHAR);
alter table cd_id_generator add CREATOR VARCHAR2(255 CHAR);
alter table cd_id_generator add CREATE_TIME TIMESTAMP(6);
alter table cd_id_generator add MODIFIER VARCHAR2(255 CHAR);
alter table cd_id_generator add MODIFY_TIME TIMESTAMP(6);
alter table cd_id_generator add REC_VER NUMBER(10);
alter table cd_id_generator
  drop constraint SYS_C0027164 cascade;


update cd_id_generator t
   set t.uuid        = SYS_GUID(),
       t.creator     = 'U0010000001',
       t.create_time = to_timestamp('27-08-2014 21:24:21.854',
                                    'dd-mm-yyyy hh24:mi:ss.ff'),
       t.modifier    = 'U0010000001',
       t.modify_time = to_timestamp('27-08-2014 21:24:21.854',
                                    'dd-mm-yyyy hh24:mi:ss.ff'),
       t.rec_ver     = 0
 where t.uuid is null;
commit;
alter table cd_id_generator
  add primary key (UUID)
  using index 
  tablespace USERS
  pctfree 10
  initrans 2
  maxtrans 255;
commit;
-- CD_READ_MARKER
alter table cd_read_marker add uuid VARCHAR2(255 CHAR);
alter table cd_read_marker add CREATOR VARCHAR2(255 CHAR);
alter table cd_read_marker add CREATE_TIME TIMESTAMP(6);
alter table cd_read_marker add MODIFIER VARCHAR2(255 CHAR);
alter table cd_read_marker add MODIFY_TIME TIMESTAMP(6);
alter table cd_read_marker add REC_VER NUMBER(10);
alter table cd_read_marker
  drop constraint SYS_C0027168 cascade;


update cd_read_marker t
   set t.uuid        = SYS_GUID(),
       t.creator     = 'U0010000001',
       t.create_time = to_timestamp('27-08-2014 21:24:21.854',
                                    'dd-mm-yyyy hh24:mi:ss.ff'),
       t.modifier    = 'U0010000001',
       t.modify_time = to_timestamp('27-08-2014 21:24:21.854',
                                    'dd-mm-yyyy hh24:mi:ss.ff'),
       t.rec_ver     = 0
 where t.uuid is null;
commit;
alter table cd_read_marker
  add primary key (UUID)
  using index 
  tablespace USERS
  pctfree 10
  initrans 2
  maxtrans 255;
commit;