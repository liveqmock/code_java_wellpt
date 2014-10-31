-- 位数据迁移
insert into WELL_PT_TEST40.UNIT_BUSINESS_TYPE select * from WELL_PT_TEST8.UNIT_BUSINESS_TYPE t where t.uuid not in (select uuid from WELL_PT_TEST40.UNIT_BUSINESS_TYPE);

insert into WELL_PT_TEST40.UNIT_COMMON_UNIT select * from WELL_PT_TEST8.UNIT_COMMON_UNIT t where t.uuid not in (select uuid from WELL_PT_TEST40.UNIT_COMMON_UNIT) order by t.create_time;

insert into WELL_PT_TEST40.UNIT_COMMON_UNIT_TREE select * from WELL_PT_TEST8.UNIT_COMMON_UNIT_TREE t where t.uuid not in (select uuid from WELL_PT_TEST40.UNIT_COMMON_UNIT_TREE) order by t.create_time;

insert into WELL_PT_TEST40.UNIT_BUSINESS_TREE select * from WELL_PT_TEST8.UNIT_BUSINESS_TREE t where t.uuid not in (select uuid from WELL_PT_TEST40.UNIT_BUSINESS_TREE) order by t.create_time;

commit;
