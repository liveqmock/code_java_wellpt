-- 添加新配置的角色信息
insert into XZSP.audit_role select * from XZSP_15.audit_role t1 
where t1.uuid not in(select uuid from XZSP.audit_role);

-- 添加新配置的用户角色
insert into XZSP.org_user_role
select * from XZSP_15.org_user_role  t1 
where t1.user_uuid in(select uuid from XZSP.org_user)
and t1.role_uuid in(select uuid from XZSP.audit_role)
and not exists(select t2.user_uuid from XZSP.org_user_role t2 where t1.user_uuid = t2.user_uuid and t1.role_uuid = t2.role_uuid);

-- 添加配置的权限
insert into XZSP.audit_privilege
select * from XZSP_15.audit_privilege t where t.uuid not in(
select t1.uuid from XZSP_15.audit_privilege t1, XZSP.audit_privilege t2 where t1.uuid = t2.uuid and t2.code = t1.code
);


delete from XZSP.Audit_Privilege_Resource;
delete from XZSP.Audit_Resource;
delete from XZSP.Audit_Role_Privilege;


insert into XZSP.Audit_Resource select * from XZSP_15.Audit_Resource;
insert into XZSP.Audit_Privilege select * from XZSP_15.Audit_Privilege t1 where t1.uuid not in(select t2.uuid from XZSP.Audit_Privilege t2);
insert into XZSP.Audit_Privilege_Resource select  * from XZSP_15.Audit_Privilege_Resource;
insert into XZSP.Audit_Role_Privilege select * from XZSP_15.Audit_Role_Privilege;
