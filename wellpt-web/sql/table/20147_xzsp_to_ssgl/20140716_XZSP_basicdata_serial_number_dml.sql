-- 数据字典数据迁移
insert into XZSP_TEST40.Cd_Serial_Number select * from XZSP_TEST8.Cd_Serial_Number t where t.uuid not in (select uuid from XZSP_TEST40.Cd_Serial_Number) order by t.create_time;
commit;