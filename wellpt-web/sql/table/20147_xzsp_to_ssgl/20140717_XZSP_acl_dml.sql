-- 工作流数据迁移

insert into XZSP_TEST40.acl_sid
select * from XZSP_TEST8.acl_sid t5 where  t5.id not in(select t3.id from XZSP_TEST40.acl_sid t3)
and t5.sid not in(select t2.sid from XZSP_TEST40.acl_sid t2);

insert into XZSP_TEST40.acl_object_identity
select * from XZSP_TEST8.acl_object_identity aoi where aoi.id in (
 select t.id from XZSP_TEST8.acl_object_identity t where t.object_id_class 
 in(select id from XZSP_TEST8.acl_class t1 where t1.class = 'com.wellsoft.pt.file.entity.FmFile' or t1.class = 'com.wellsoft.pt.file.entity.FmFolder')
);

insert into XZSP_TEST40.acl_sid_member
select * from XZSP_TEST8.acl_sid_member t where t.module_id = 'FILE' and
 t.id not in(select t3.id from XZSP_TEST40.acl_sid_member t3 where t3.module_id = 'FILE')
 and t.owner_sid in (select t2.owner_sid from XZSP_TEST40.acl_sid_member t2 where t2.module_id = 'FILE');

insert into XZSP_TEST40.acl_entry
select * from XZSP_TEST8.acl_entry ae where ae.acl_object_identity in (
 select t.id from XZSP_TEST8.acl_object_identity t where t.object_id_class 
 in(select id from XZSP_TEST8.acl_class t1 where t1.class = 'com.wellsoft.pt.file.entity.FmFile' or t1.class = 'com.wellsoft.pt.file.entity.FmFolder')
) and ae.id not in (select t3.id from XZSP_TEST40.acl_entry t3);

commit;