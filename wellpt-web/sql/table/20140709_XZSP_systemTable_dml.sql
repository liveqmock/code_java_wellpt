--系统表模块的数据同步 （test8的库与test31的库）
insert into XZSP_TEST31.cd_system_table_entity select * from XZSP_TEST8.Cd_System_Table_Entity t1 where t1.uuid not in (select t2.uuid from XZSP_TEST31.cd_system_table_entity t2)
insert into XZSP_TEST31.Cd_System_Table_Entity_Attr select * from XZSP_TEST8.Cd_System_Table_Entity_Attr t1 where t1.system_table_uuid not in(select t2.system_table_uuid from XZSP_TEST31.Cd_System_Table_Entity_Attr t2)