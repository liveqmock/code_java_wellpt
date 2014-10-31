-- 数据字典数据迁移
insert into XZSP_TEST40.cd_data_dict select * from XZSP_TEST8.cd_data_dict t where t.uuid not in (select uuid from XZSP_TEST40.cd_data_dict) order by t.create_time;
insert into XZSP_TEST40.cd_data_dict_attr select * from XZSP_TEST8.cd_data_dict_attr t where t.uuid not in (select uuid from XZSP_TEST40.cd_data_dict_attr);
commit;