-- Add/modify columns 
alter table ACL_ENTRY add object_id_identity VARCHAR2(64 CHAR);

-- ACL升级
update acl_entry t1 set t1.object_id_identity = (select t2.object_id_identity from acl_object_identity t2 where t1.acl_object_identity = t2.id)
where t1.object_id_identity is null;


-- Add/modify columns 
alter table ACL_CLASS rename column id to uuid;
alter table acl_class add CREATOR VARCHAR2(255 CHAR);
alter table acl_class add CREATE_TIME TIMESTAMP(6);
alter table acl_class add MODIFIER VARCHAR2(255 CHAR);
alter table acl_class add MODIFY_TIME TIMESTAMP(6);
alter table acl_class add REC_VER NUMBER(10);

  
-- Add/modify columns 
alter table ACL_ENTRY rename column id to uuid;
alter table acl_entry add CREATOR VARCHAR2(255 CHAR);
alter table acl_entry add CREATE_TIME TIMESTAMP(6);
alter table acl_entry add MODIFIER VARCHAR2(255 CHAR);
alter table acl_entry add MODIFY_TIME TIMESTAMP(6);
alter table acl_entry add REC_VER NUMBER(10);

  
-- Add/modify columns 
alter table ACL_OBJECT_IDENTITY rename column id to uuid;
alter table acl_object_identity add CREATOR VARCHAR2(255 CHAR);
alter table acl_object_identity add CREATE_TIME TIMESTAMP(6);
alter table acl_object_identity add MODIFIER VARCHAR2(255 CHAR);
alter table acl_object_identity add MODIFY_TIME TIMESTAMP(6);
alter table acl_object_identity add REC_VER NUMBER(10);
  
-- Add/modify columns 
alter table ACL_SID rename column id to uuid;
alter table acl_sid add CREATOR VARCHAR2(255 CHAR);
alter table acl_sid add CREATE_TIME TIMESTAMP(6);
alter table acl_sid add MODIFIER VARCHAR2(255 CHAR);
alter table acl_sid add MODIFY_TIME TIMESTAMP(6);
alter table acl_sid add REC_VER NUMBER(10);
 
-- Add/modify columns 
alter table ACL_SID_MEMBER rename column id to uuid;
alter table acl_sid_member add CREATOR VARCHAR2(255 CHAR);
alter table acl_sid_member add CREATE_TIME TIMESTAMP(6);
alter table acl_sid_member add MODIFIER VARCHAR2(255 CHAR);
alter table acl_sid_member add MODIFY_TIME TIMESTAMP(6);
alter table acl_sid_member add REC_VER NUMBER(10);
 

update acl_sid t set
       t.creator     = 'U0010000001',
       t.create_time = to_timestamp('27-08-2014 21:24:21.854',
                                    'dd-mm-yyyy hh24:mi:ss.ff'),
       t.modifier    = 'U0010000001',
       t.modify_time = to_timestamp('27-08-2014 21:24:21.854',
                                    'dd-mm-yyyy hh24:mi:ss.ff'),
       t.rec_ver     = 0
 where t.rec_ver is null;
 
 update acl_class t set
       t.creator     = 'U0010000001',
       t.create_time = to_timestamp('27-08-2014 21:24:21.854',
                                    'dd-mm-yyyy hh24:mi:ss.ff'),
       t.modifier    = 'U0010000001',
       t.modify_time = to_timestamp('27-08-2014 21:24:21.854',
                                    'dd-mm-yyyy hh24:mi:ss.ff'),
       t.rec_ver     = 0
 where t.rec_ver is null;
 
  update acl_object_identity t set
       t.creator     = 'U0010000001',
       t.create_time = to_timestamp('27-08-2014 21:24:21.854',
                                    'dd-mm-yyyy hh24:mi:ss.ff'),
       t.modifier    = 'U0010000001',
       t.modify_time = to_timestamp('27-08-2014 21:24:21.854',
                                    'dd-mm-yyyy hh24:mi:ss.ff'),
       t.rec_ver     = 0
 where t.rec_ver is null;
 
   update acl_entry t set
       t.creator     = 'U0010000001',
       t.create_time = to_timestamp('27-08-2014 21:24:21.854',
                                    'dd-mm-yyyy hh24:mi:ss.ff'),
       t.modifier    = 'U0010000001',
       t.modify_time = to_timestamp('27-08-2014 21:24:21.854',
                                    'dd-mm-yyyy hh24:mi:ss.ff'),
       t.rec_ver     = 0
 where t.rec_ver is null;
 
    update acl_sid_member t set
       t.creator     = 'U0010000001',
       t.create_time = to_timestamp('27-08-2014 21:24:21.854',
                                    'dd-mm-yyyy hh24:mi:ss.ff'),
       t.modifier    = 'U0010000001',
       t.modify_time = to_timestamp('27-08-2014 21:24:21.854',
                                    'dd-mm-yyyy hh24:mi:ss.ff'),
       t.rec_ver     = 0
 where t.rec_ver is null;
 
 commit;
 
 