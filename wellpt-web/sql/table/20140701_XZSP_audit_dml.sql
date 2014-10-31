--琛屾斂瀹℃壒鐨勬潈闄愩�璧勬簮銆佽鑹叉暟鎹悎骞跺埌鍟嗕簨骞冲彴涓幓
insert into XZSP_TEST40.Audit_Resource select * from XZSP_TEST8.Audit_Resource t1 where t1.uuid not in(select t2.uuid from XZSP_TEST40.Audit_Resource t2);
insert into XZSP_TEST40.Audit_Privilege select * from XZSP_TEST8.Audit_Privilege t1 where t1.uuid not in(select t2.uuid from XZSP_TEST40.Audit_Privilege t2);
insert into XZSP_TEST40.Audit_Privilege_Resource select  * from XZSP_TEST8.Audit_Privilege_Resource t where
not exists (select  * from XZSP_TEST40.Audit_Privilege_Resource t1 where t.privilege_uuid = t1.privilege_uuid and t.resource_uuid = t1.resource_uuid); 
insert into XZSP_TEST40.AUDIT_ROLE select * from XZSP_TEST8.AUDIT_ROLE t1 where t1.uuid not in(select t2.uuid from XZSP_TEST40.AUDIT_ROLE t2);
insert into XZSP_TEST40.Audit_Role_Privilege select * from XZSP_TEST8.Audit_Role_Privilege t where 
not exists (select * from XZSP_TEST40.Audit_Role_Privilege t1 where t.privilege_uuid = t1.privilege_uuid and t.role_uuid = t1.role_uuid);
