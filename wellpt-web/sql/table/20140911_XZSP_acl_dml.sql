-- 文件管理创建者sid升级后数据清理
delete from acl_sid_member t where t.owner_sid in (
select t1.uuid from acl_sid t1 where t1.sid like 'GROUP_%%fileCreator'
);

delete from acl_entry t where t.sid in (
select t1.uuid from acl_sid t1 where t1.sid like 'GROUP_%%fileCreator'
);

delete from acl_object_identity t where t.owner_sid in (
select t1.uuid from acl_sid t1 where t1.sid like 'GROUP_%%fileCreator'
);

delete from acl_sid t1 where t1.sid like 'GROUP_%%fileCreator';

commit;

