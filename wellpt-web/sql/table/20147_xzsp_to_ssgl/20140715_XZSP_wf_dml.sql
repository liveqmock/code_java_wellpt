-- 工作流数据迁移
delete from XZSP_TEST40.Wf_Def_Format;
insert into XZSP_TEST40.Wf_Flow_Schema select * from XZSP_TEST8.Wf_Flow_Schema t where t.uuid not in (select uuid from XZSP_TEST40.Wf_Flow_Schema);
insert into XZSP_TEST40.Wf_Flow_Definition select * from XZSP_TEST8.Wf_Flow_Definition t where t.uuid not in (select uuid from XZSP_TEST40.Wf_Flow_Definition) order by t.create_time;
insert into XZSP_TEST40.Wf_Def_Category select * from XZSP_TEST8.Wf_Def_Category t where t.uuid not in (select uuid from XZSP_TEST40.Wf_Def_Category);
insert into XZSP_TEST40.Wf_Def_Format select * from XZSP_TEST8.Wf_Def_Format t where t.uuid not in (select uuid from XZSP_TEST40.Wf_Def_Format);
commit;