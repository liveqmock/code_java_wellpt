prompt PL/SQL Developer import file
prompt Created on 2014��8��6�� by Administrator
set feedback off
set define off
prompt Deleting CD_SERIAL_NUMBER...
delete from CD_SERIAL_NUMBER;
commit;
prompt Loading CD_SERIAL_NUMBER...
insert into CD_SERIAL_NUMBER (uuid, create_time, creator, modifier, modify_time, rec_ver, code, head_part, id, incremental, initial_value, is_editor, is_fill_number, is_fill_position, key_part, last_part, name, pointer, remark, serial_num, start_date, type)
values ('2e98a3a0-4620-454f-83d3-191a7ce2ff10', to_timestamp('05-08-2014 23:15:13.424000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('05-08-2014 23:15:13.424000', 'dd-mm-yyyy hh24:mi:ss.ff'), 0, '012', '��', 'XZSP_PROJECT_CHILD_CODE', 1, '001', 0, 0, 1, '��Ŀ����������', null, '��Ŀ������Ŀ���', null, null, null, null, '��������');
insert into CD_SERIAL_NUMBER (uuid, create_time, creator, modifier, modify_time, rec_ver, code, head_part, id, incremental, initial_value, is_editor, is_fill_number, is_fill_position, key_part, last_part, name, pointer, remark, serial_num, start_date, type)
values ('dde43c4b-79bf-4195-b626-843a502b60f8', to_timestamp('13-05-2014 14:44:24.459000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('05-08-2014 23:17:57.525000', 'dd-mm-yyyy hh24:mi:ss.ff'), 1, '018', ' ', 'XZSP_RECEIPT_LIB_DOCUMENT_CODE', 1, '01', 0, 0, 1, '${��}', null, '��ִ����������', null, null, null, null, '��������');
insert into CD_SERIAL_NUMBER (uuid, create_time, creator, modifier, modify_time, rec_ver, code, head_part, id, incremental, initial_value, is_editor, is_fill_number, is_fill_position, key_part, last_part, name, pointer, remark, serial_num, start_date, type)
values ('e195443b-8b0b-4d2a-99e5-b2c909233ad1', to_timestamp('13-05-2014 14:49:06.199000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('13-05-2014 14:49:06.199000', 'dd-mm-yyyy hh24:mi:ss.ff'), 0, '019', '${��}${��}${��}${ʱ}${��}', 'XZSP_RESIDENTS_LIB_ID', 1, '1', 0, 1, 0, '${��}', null, '�걨�˿�ID', null, null, null, null, '��������');
insert into CD_SERIAL_NUMBER (uuid, create_time, creator, modifier, modify_time, rec_ver, code, head_part, id, incremental, initial_value, is_editor, is_fill_number, is_fill_position, key_part, last_part, name, pointer, remark, serial_num, start_date, type)
values ('44b00f60-4d9d-45ef-a462-7cfb80519786', to_timestamp('13-05-2014 14:50:28.942000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('13-05-2014 14:50:28.942000', 'dd-mm-yyyy hh24:mi:ss.ff'), 0, '020', '${��}${��}${��}${ʱ}${��}', 'XZSP_BLACKLIST_LIB_ID', 1, '1', 0, 1, 0, '${��}', null, '��������ID', null, null, null, null, '��������');
insert into CD_SERIAL_NUMBER (uuid, create_time, creator, modifier, modify_time, rec_ver, code, head_part, id, incremental, initial_value, is_editor, is_fill_number, is_fill_position, key_part, last_part, name, pointer, remark, serial_num, start_date, type)
values ('f35641d0-3406-45e2-b500-21bd4b53c776', to_timestamp('13-05-2014 14:51:56.168000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('13-05-2014 14:51:56.168000', 'dd-mm-yyyy hh24:mi:ss.ff'), 0, '021', '${��}${��}${��}${ʱ}${��}', 'XZSP_CHARGE_LIB_ID', 1, '1', 0, 1, 0, '${��}', null, '�շѿ�ID', null, null, null, null, '��������');
insert into CD_SERIAL_NUMBER (uuid, create_time, creator, modifier, modify_time, rec_ver, code, head_part, id, incremental, initial_value, is_editor, is_fill_number, is_fill_position, key_part, last_part, name, pointer, remark, serial_num, start_date, type)
values ('f1695d0a-6f0e-404a-96f2-7acb9e4f1c6d', to_timestamp('13-05-2014 15:03:23.606000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('13-05-2014 15:03:23.606000', 'dd-mm-yyyy hh24:mi:ss.ff'), 0, '022', '${��}${��}${��}${ʱ}${��}', 'XZSP_UNIT_LIB_ID', 1, '1', 0, 1, 0, '${��}', null, '��λ��ID', null, null, null, null, '��������');
insert into CD_SERIAL_NUMBER (uuid, create_time, creator, modifier, modify_time, rec_ver, code, head_part, id, incremental, initial_value, is_editor, is_fill_number, is_fill_position, key_part, last_part, name, pointer, remark, serial_num, start_date, type)
values ('d9b73ba9-6457-4167-a5da-28ba90e1ebba', null, null, 'U0010000001', to_timestamp('05-08-2014 23:13:46.514000', 'dd-mm-yyyy hh24:mi:ss.ff'), 11, '011', '${dytable.XZFQ}${��}${��}', 'XZSP_PROJECT_CODE', 1, '0001', 0, 0, 1, '${��}', null, '��Ŀ����', null, null, null, null, '��������');
insert into CD_SERIAL_NUMBER (uuid, create_time, creator, modifier, modify_time, rec_ver, code, head_part, id, incremental, initial_value, is_editor, is_fill_number, is_fill_position, key_part, last_part, name, pointer, remark, serial_num, start_date, type)
values ('9e171fb2-d1c1-4ab8-a899-45752e21faf9', null, null, 'U0010000001', to_timestamp('13-05-2014 14:39:04.477000', 'dd-mm-yyyy hh24:mi:ss.ff'), 5, '012', '${��}${��}${��}${ʱ}${��}', 'XZSP_MATERIAL_CODE', 1, '1', 0, 1, 0, '0', null, '���Ͽ���', null, null, null, null, '��������');
insert into CD_SERIAL_NUMBER (uuid, create_time, creator, modifier, modify_time, rec_ver, code, head_part, id, incremental, initial_value, is_editor, is_fill_number, is_fill_position, key_part, last_part, name, pointer, remark, serial_num, start_date, type)
values ('f690ae65-3091-4b88-ba92-1a57e54a4ef0', null, null, 'U0010000001', to_timestamp('05-08-2014 23:15:37.997000', 'dd-mm-yyyy hh24:mi:ss.ff'), 12, '013', '${��}${��}', 'XZSP_BANJIAN_SERIAL_NUMBER', 1, '0001', 0, 0, 1, '${��}', null, '�������ˮ��', null, null, null, null, '��������');
insert into CD_SERIAL_NUMBER (uuid, create_time, creator, modifier, modify_time, rec_ver, code, head_part, id, incremental, initial_value, is_editor, is_fill_number, is_fill_position, key_part, last_part, name, pointer, remark, serial_num, start_date, type)
values ('0336d8e4-a898-4f35-85ec-e00dc677ce1a', null, null, 'U0010000001', to_timestamp('21-05-2014 16:21:22.807000', 'dd-mm-yyyy hh24:mi:ss.ff'), 3, '014', '0${��}${��}', 'XZSP_BANJIAN_PASSWORD', 1, '0', 0, 1, 0, '0', null, '���������', null, null, null, null, '��������');
insert into CD_SERIAL_NUMBER (uuid, create_time, creator, modifier, modify_time, rec_ver, code, head_part, id, incremental, initial_value, is_editor, is_fill_number, is_fill_position, key_part, last_part, name, pointer, remark, serial_num, start_date, type)
values ('d5e26177-6b26-4499-b089-a06ad23497c3', null, null, 'U0010000001', to_timestamp('13-05-2014 14:44:47.651000', 'dd-mm-yyyy hh24:mi:ss.ff'), 2, '017', '${��}${��}${��}${ʱ}${��}', 'XZSP_MATERIAL_LIB_ID', 1, '1', 0, 1, 0, '${��}', null, '���Ͽ�ID', null, null, null, null, '��������');
insert into CD_SERIAL_NUMBER (uuid, create_time, creator, modifier, modify_time, rec_ver, code, head_part, id, incremental, initial_value, is_editor, is_fill_number, is_fill_position, key_part, last_part, name, pointer, remark, serial_num, start_date, type)
values ('2cf28c07-f305-496e-94d6-3a35fe4b932d', null, null, 'U0010000001', to_timestamp('13-05-2014 14:47:12.796000', 'dd-mm-yyyy hh24:mi:ss.ff'), 3, '016', '${��}${��}${��}${ʱ}${��}', 'XZSP_LICENSE_LIB_ID', 1, '1', 0, 1, 0, '${��}', null, '֤�տ�ID', null, null, null, null, '��������');
commit;
prompt 12 records loaded
set feedback on
set define on
prompt Done.
