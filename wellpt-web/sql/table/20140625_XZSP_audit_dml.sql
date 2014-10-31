delete from XZSP.Audit_Privilege_Resource;
delete from XZSP.Audit_Resource;
delete from XZSP.Audit_Role_Privilege;
commit;

insert into XZSP.Audit_Resource select * from XZSP_31.Audit_Resource;
insert into XZSP.Audit_Privilege select * from XZSP_31.Audit_Privilege t1 where t1.uuid not in(select t2.uuid from XZSP.Audit_Privilege t2);
insert into XZSP.Audit_Privilege_Resource select  * from XZSP_31.Audit_Privilege_Resource;
insert into XZSP.Audit_Role_Privilege select * from XZSP_31.Audit_Role_Privilege;
commit;