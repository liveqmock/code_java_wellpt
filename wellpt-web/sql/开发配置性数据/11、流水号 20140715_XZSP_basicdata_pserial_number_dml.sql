prompt PL/SQL Developer import file
prompt Created on 2014年8月6日 by Administrator
set feedback off
set define off
prompt Deleting CD_SERIAL_NUMBER...
delete from CD_SERIAL_NUMBER;
commit;
prompt Loading CD_SERIAL_NUMBER...
insert into CD_SERIAL_NUMBER (uuid, create_time, creator, modifier, modify_time, rec_ver, code, head_part, id, incremental, initial_value, is_editor, is_fill_number, is_fill_position, key_part, last_part, name, pointer, remark, serial_num, start_date, type)
values ('2e98a3a0-4620-454f-83d3-191a7ce2ff10', to_timestamp('05-08-2014 23:15:13.424000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('05-08-2014 23:15:13.424000', 'dd-mm-yyyy hh24:mi:ss.ff'), 0, '012', '—', 'XZSP_PROJECT_CHILD_CODE', 1, '001', 0, 0, 1, '项目库子事项编号', null, '项目库子项目编号', null, null, null, null, '行政审批');
insert into CD_SERIAL_NUMBER (uuid, create_time, creator, modifier, modify_time, rec_ver, code, head_part, id, incremental, initial_value, is_editor, is_fill_number, is_fill_position, key_part, last_part, name, pointer, remark, serial_num, start_date, type)
values ('dde43c4b-79bf-4195-b626-843a502b60f8', to_timestamp('13-05-2014 14:44:24.459000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('05-08-2014 23:17:57.525000', 'dd-mm-yyyy hh24:mi:ss.ff'), 1, '018', ' ', 'XZSP_RECEIPT_LIB_DOCUMENT_CODE', 1, '01', 0, 0, 1, '${月}', null, '回执单库文书编号', null, null, null, null, '行政审批');
insert into CD_SERIAL_NUMBER (uuid, create_time, creator, modifier, modify_time, rec_ver, code, head_part, id, incremental, initial_value, is_editor, is_fill_number, is_fill_position, key_part, last_part, name, pointer, remark, serial_num, start_date, type)
values ('e195443b-8b0b-4d2a-99e5-b2c909233ad1', to_timestamp('13-05-2014 14:49:06.199000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('13-05-2014 14:49:06.199000', 'dd-mm-yyyy hh24:mi:ss.ff'), 0, '019', '${年}${月}${日}${时}${分}', 'XZSP_RESIDENTS_LIB_ID', 1, '1', 0, 1, 0, '${年}', null, '申报人库ID', null, null, null, null, '行政审批');
insert into CD_SERIAL_NUMBER (uuid, create_time, creator, modifier, modify_time, rec_ver, code, head_part, id, incremental, initial_value, is_editor, is_fill_number, is_fill_position, key_part, last_part, name, pointer, remark, serial_num, start_date, type)
values ('44b00f60-4d9d-45ef-a462-7cfb80519786', to_timestamp('13-05-2014 14:50:28.942000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('13-05-2014 14:50:28.942000', 'dd-mm-yyyy hh24:mi:ss.ff'), 0, '020', '${年}${月}${日}${时}${分}', 'XZSP_BLACKLIST_LIB_ID', 1, '1', 0, 1, 0, '${年}', null, '黑名单库ID', null, null, null, null, '行政审批');
insert into CD_SERIAL_NUMBER (uuid, create_time, creator, modifier, modify_time, rec_ver, code, head_part, id, incremental, initial_value, is_editor, is_fill_number, is_fill_position, key_part, last_part, name, pointer, remark, serial_num, start_date, type)
values ('f35641d0-3406-45e2-b500-21bd4b53c776', to_timestamp('13-05-2014 14:51:56.168000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('13-05-2014 14:51:56.168000', 'dd-mm-yyyy hh24:mi:ss.ff'), 0, '021', '${年}${月}${日}${时}${分}', 'XZSP_CHARGE_LIB_ID', 1, '1', 0, 1, 0, '${年}', null, '收费库ID', null, null, null, null, '行政审批');
insert into CD_SERIAL_NUMBER (uuid, create_time, creator, modifier, modify_time, rec_ver, code, head_part, id, incremental, initial_value, is_editor, is_fill_number, is_fill_position, key_part, last_part, name, pointer, remark, serial_num, start_date, type)
values ('f1695d0a-6f0e-404a-96f2-7acb9e4f1c6d', to_timestamp('13-05-2014 15:03:23.606000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('13-05-2014 15:03:23.606000', 'dd-mm-yyyy hh24:mi:ss.ff'), 0, '022', '${年}${月}${日}${时}${分}', 'XZSP_UNIT_LIB_ID', 1, '1', 0, 1, 0, '${年}', null, '单位库ID', null, null, null, null, '行政审批');
insert into CD_SERIAL_NUMBER (uuid, create_time, creator, modifier, modify_time, rec_ver, code, head_part, id, incremental, initial_value, is_editor, is_fill_number, is_fill_position, key_part, last_part, name, pointer, remark, serial_num, start_date, type)
values ('d9b73ba9-6457-4167-a5da-28ba90e1ebba', null, null, 'U0010000001', to_timestamp('05-08-2014 23:13:46.514000', 'dd-mm-yyyy hh24:mi:ss.ff'), 11, '011', '${dytable.XZFQ}${年}${月}', 'XZSP_PROJECT_CODE', 1, '0001', 0, 0, 1, '${月}', null, '项目库编号', null, null, null, null, '行政审批');
insert into CD_SERIAL_NUMBER (uuid, create_time, creator, modifier, modify_time, rec_ver, code, head_part, id, incremental, initial_value, is_editor, is_fill_number, is_fill_position, key_part, last_part, name, pointer, remark, serial_num, start_date, type)
values ('9e171fb2-d1c1-4ab8-a899-45752e21faf9', null, null, 'U0010000001', to_timestamp('13-05-2014 14:39:04.477000', 'dd-mm-yyyy hh24:mi:ss.ff'), 5, '012', '${年}${月}${日}${时}${分}', 'XZSP_MATERIAL_CODE', 1, '1', 0, 1, 0, '0', null, '材料库编号', null, null, null, null, '行政审批');
insert into CD_SERIAL_NUMBER (uuid, create_time, creator, modifier, modify_time, rec_ver, code, head_part, id, incremental, initial_value, is_editor, is_fill_number, is_fill_position, key_part, last_part, name, pointer, remark, serial_num, start_date, type)
values ('f690ae65-3091-4b88-ba92-1a57e54a4ef0', null, null, 'U0010000001', to_timestamp('05-08-2014 23:15:37.997000', 'dd-mm-yyyy hh24:mi:ss.ff'), 12, '013', '${年}${月}', 'XZSP_BANJIAN_SERIAL_NUMBER', 1, '0001', 0, 0, 1, '${月}', null, '办件单流水号', null, null, null, null, '行政审批');
insert into CD_SERIAL_NUMBER (uuid, create_time, creator, modifier, modify_time, rec_ver, code, head_part, id, incremental, initial_value, is_editor, is_fill_number, is_fill_position, key_part, last_part, name, pointer, remark, serial_num, start_date, type)
values ('0336d8e4-a898-4f35-85ec-e00dc677ce1a', null, null, 'U0010000001', to_timestamp('21-05-2014 16:21:22.807000', 'dd-mm-yyyy hh24:mi:ss.ff'), 3, '014', '0${分}${秒}', 'XZSP_BANJIAN_PASSWORD', 1, '0', 0, 1, 0, '0', null, '办件单密码', null, null, null, null, '行政审批');
insert into CD_SERIAL_NUMBER (uuid, create_time, creator, modifier, modify_time, rec_ver, code, head_part, id, incremental, initial_value, is_editor, is_fill_number, is_fill_position, key_part, last_part, name, pointer, remark, serial_num, start_date, type)
values ('d5e26177-6b26-4499-b089-a06ad23497c3', null, null, 'U0010000001', to_timestamp('13-05-2014 14:44:47.651000', 'dd-mm-yyyy hh24:mi:ss.ff'), 2, '017', '${年}${月}${日}${时}${分}', 'XZSP_MATERIAL_LIB_ID', 1, '1', 0, 1, 0, '${年}', null, '材料库ID', null, null, null, null, '行政审批');
insert into CD_SERIAL_NUMBER (uuid, create_time, creator, modifier, modify_time, rec_ver, code, head_part, id, incremental, initial_value, is_editor, is_fill_number, is_fill_position, key_part, last_part, name, pointer, remark, serial_num, start_date, type)
values ('2cf28c07-f305-496e-94d6-3a35fe4b932d', null, null, 'U0010000001', to_timestamp('13-05-2014 14:47:12.796000', 'dd-mm-yyyy hh24:mi:ss.ff'), 3, '016', '${年}${月}${日}${时}${分}', 'XZSP_LICENSE_LIB_ID', 1, '1', 0, 1, 0, '${年}', null, '证照库ID', null, null, null, null, '行政审批');
commit;
prompt 12 records loaded
set feedback on
set define on
prompt Done.
