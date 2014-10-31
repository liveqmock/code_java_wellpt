--行政审批的权限、资源、角色数据test8库到test40库 
insert into XZSP_42.Audit_Resource select * from XZSP_43.Audit_Resource t1 where t1.uuid not in(select t2.uuid from XZSP_42.Audit_Resource t2);
insert into XZSP_42.Audit_Privilege select * from XZSP_43.Audit_Privilege t1 where t1.uuid not in(select t2.uuid from XZSP_42.Audit_Privilege t2);
insert into XZSP_42.Audit_Privilege_Resource select  * from XZSP_43.Audit_Privilege_Resource t where
not exists (select  * from XZSP_42.Audit_Privilege_Resource t1 where t.privilege_uuid = t1.privilege_uuid and t.resource_uuid = t1.resource_uuid); 
insert into XZSP_42.AUDIT_ROLE select * from XZSP_43.AUDIT_ROLE t1 where t1.uuid not in(select t2.uuid from XZSP_42.AUDIT_ROLE t2);
insert into XZSP_42.Audit_Role_Privilege select * from XZSP_43.Audit_Role_Privilege t where 
not exists (select * from XZSP_42.Audit_Role_Privilege t1 where t.privilege_uuid = t1.privilege_uuid and t.role_uuid = t1.role_uuid);
