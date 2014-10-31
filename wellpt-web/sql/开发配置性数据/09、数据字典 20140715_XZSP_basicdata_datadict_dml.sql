prompt PL/SQL Developer import file
prompt Created on 2014年8月6日 by Administrator
set feedback off
set define off
prompt Disabling foreign key constraints for CD_DATA_DICT...
alter table CD_DATA_DICT disable constraint FKB499D0CD3F27264;
prompt Disabling foreign key constraints for CD_DATA_DICT_ATTR...
alter table CD_DATA_DICT_ATTR disable constraint FK96423A23B6A25483;
prompt Deleting CD_DATA_DICT_ATTR...
delete from CD_DATA_DICT_ATTR;
commit;
prompt Deleting CD_DATA_DICT...
delete from CD_DATA_DICT;
commit;
prompt Loading CD_DATA_DICT...
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('8eb200fa-71c7-4f12-a4e6-04a7231a64b6', to_timestamp('04-03-2013 18:54:30.740000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('04-03-2013 18:54:30.740000', 'dd-mm-yyyy hh24:mi:ss.ff'), 9, 'dy_work_task_change_detail_type', '类型', 'dy_work_task_change_detail_type', '2cfd8c22-330d-4223-ad5d-25010b8554fe');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('6ea62266-0080-47ef-abe8-0d0154309024', to_timestamp('04-03-2013 18:55:13.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('04-03-2013 18:57:46.737000', 'dd-mm-yyyy hh24:mi:ss.ff'), 10, 'dy_work_task_change_detail_begintime', '计划开始时间', 'dy_work_task_change_detail_begintime', '2cfd8c22-330d-4223-ad5d-25010b8554fe');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('70e6c30e-5baf-499b-b35e-66a369df8ac4', to_timestamp('04-03-2013 18:55:30.870000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('04-03-2013 18:55:30.870000', 'dd-mm-yyyy hh24:mi:ss.ff'), 9, 'dy_work_task_change_detail_endtime', '计划结束时间', 'dy_work_task_change_detail_endtime', '2cfd8c22-330d-4223-ad5d-25010b8554fe');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('c5c65d43-4b4f-4533-b0e3-b43354947f47', to_timestamp('22-02-2014 19:39:06.923000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('22-02-2014 19:39:06.923000', 'dd-mm-yyyy hh24:mi:ss.ff'), 7, 'exchangeData_reservedText4', '预留文本字段6', null, 'c85962cd-0fa7-46be-99b3-7d4a7852e9dd');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('fe03f181-e200-4151-b032-0d99901ce5cd', to_timestamp('22-02-2014 19:39:41.110000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('22-02-2014 19:39:41.110000', 'dd-mm-yyyy hh24:mi:ss.ff'), 7, 'exchangeData_reservedText5', '预留文本字段7', null, 'c85962cd-0fa7-46be-99b3-7d4a7852e9dd');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('0759c4ac-25c8-4d8a-a9a6-26c0350588ce', to_timestamp('22-02-2014 19:39:58.194000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('22-02-2014 19:39:58.194000', 'dd-mm-yyyy hh24:mi:ss.ff'), 7, 'exchangeData_reservedText6', '预留文本字段8', null, 'c85962cd-0fa7-46be-99b3-7d4a7852e9dd');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('c5edfc8b-011f-491c-b548-3dfdd31c2a18', to_timestamp('27-02-2014 18:22:44.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('18-07-2014 17:45:26.959000', 'dd-mm-yyyy hh24:mi:ss.ff'), 5, '015010', '材料或证照名(商事)', 'XZSP_MATERIAL_NAME_SS', '8c56947c-ac41-484e-9ad6-a5ae5ccb7104');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('622bf92d-3e4c-4e53-8fdf-bb4ab94891d8', to_timestamp('27-02-2014 18:23:20.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('27-02-2014 18:23:58.249000', 'dd-mm-yyyy hh24:mi:ss.ff'), 2, '国际船舶代理经营资格登记证', '国际船舶代理经营资格登记证', null, 'c5edfc8b-011f-491c-b548-3dfdd31c2a18');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('7eea01bd-54f7-4596-99b3-b400c1778929', to_timestamp('27-02-2014 18:23:33.587000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('27-02-2014 18:23:33.587000', 'dd-mm-yyyy hh24:mi:ss.ff'), 1, '餐饮服务许可证', '餐饮服务许可证', null, 'c5edfc8b-011f-491c-b548-3dfdd31c2a18');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('d4d9bec0-7424-494f-8e61-45108f836cb1', to_timestamp('27-02-2014 18:24:05.098000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('27-02-2014 18:24:05.098000', 'dd-mm-yyyy hh24:mi:ss.ff'), 1, '中华人民共和国电子出版物出版许可证', '中华人民共和国电子出版物出版许可证', null, 'c5edfc8b-011f-491c-b548-3dfdd31c2a18');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('3d769346-f6c3-40db-a18d-5db41f730f90', to_timestamp('27-02-2014 18:24:09.614000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('27-02-2014 18:24:09.614000', 'dd-mm-yyyy hh24:mi:ss.ff'), 1, '中华人民共和国金融许可证', '中华人民共和国金融许可证', null, 'c5edfc8b-011f-491c-b548-3dfdd31c2a18');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('c50c024c-a5b5-496e-9b85-9b51c9a2e190', to_timestamp('27-02-2014 18:24:14.364000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('27-02-2014 18:24:14.364000', 'dd-mm-yyyy hh24:mi:ss.ff'), 1, '保险兼业务代理业务许可证', '保险兼业务代理业务许可证', null, 'c5edfc8b-011f-491c-b548-3dfdd31c2a18');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('4bde5afc-ec45-4d77-ada8-1c00daf5efd9', to_timestamp('27-02-2014 18:24:18.405000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('27-02-2014 18:24:18.405000', 'dd-mm-yyyy hh24:mi:ss.ff'), 1, '食品卫生许可证', '食品卫生许可证', null, 'c5edfc8b-011f-491c-b548-3dfdd31c2a18');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('e203c309-ca22-4919-abc0-33ebd89a6b48', to_timestamp('27-02-2014 18:24:22.337000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('27-02-2014 18:24:22.337000', 'dd-mm-yyyy hh24:mi:ss.ff'), 1, '建筑业企业资质证书', '建筑业企业资质证书', null, 'c5edfc8b-011f-491c-b548-3dfdd31c2a18');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('64b6167b-fecd-4e22-9600-59aa88dc0f74', to_timestamp('27-02-2014 18:24:27.797000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('27-02-2014 18:24:27.797000', 'dd-mm-yyyy hh24:mi:ss.ff'), 1, '中华人民共和国危险化学品经营许可证', '中华人民共和国危险化学品经营许可证', null, 'c5edfc8b-011f-491c-b548-3dfdd31c2a18');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('453ab577-d225-41f2-82a0-e30a887ea393', to_timestamp('27-02-2014 18:24:31.450000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('27-02-2014 18:24:31.450000', 'dd-mm-yyyy hh24:mi:ss.ff'), 1, '福建省气瓶定期检验资格证', '福建省气瓶定期检验资格证', null, 'c5edfc8b-011f-491c-b548-3dfdd31c2a18');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('5d36d8b3-24ac-44d1-ac84-ac049b7e656b', to_timestamp('27-02-2014 18:24:35.651000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('27-02-2014 18:24:35.651000', 'dd-mm-yyyy hh24:mi:ss.ff'), 1, '消防安全检查合格证', '消防安全检查合格证', null, 'c5edfc8b-011f-491c-b548-3dfdd31c2a18');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('305eb0ef-410a-4c3e-9524-bd357a84912b', to_timestamp('27-02-2014 18:24:40.207000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('27-02-2014 18:24:40.207000', 'dd-mm-yyyy hh24:mi:ss.ff'), 1, '特种行业许可证', '特种行业许可证', null, 'c5edfc8b-011f-491c-b548-3dfdd31c2a18');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('8e8201d5-6c6a-4733-91b8-4725239b8610', to_timestamp('27-02-2014 18:24:44.164000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('27-02-2014 18:24:44.164000', 'dd-mm-yyyy hh24:mi:ss.ff'), 1, '卫生许可证', '卫生许可证', null, 'c5edfc8b-011f-491c-b548-3dfdd31c2a18');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('d55efa3c-e274-4097-9818-3bef38dd4607', to_timestamp('27-02-2014 18:24:53.989000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('27-02-2014 18:24:53.989000', 'dd-mm-yyyy hh24:mi:ss.ff'), 1, '排放污染物许可证', '排放污染物许可证', null, 'c5edfc8b-011f-491c-b548-3dfdd31c2a18');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('ac2f6fb0-5904-4e6d-9a78-402d317f7403', to_timestamp('27-02-2014 18:24:58.617000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('27-02-2014 18:24:58.617000', 'dd-mm-yyyy hh24:mi:ss.ff'), 1, '食品流通许可证', '食品流通许可证', null, 'c5edfc8b-011f-491c-b548-3dfdd31c2a18');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('d82df228-52fc-46c9-a7a3-56538980cf0f', to_timestamp('27-02-2014 18:25:02.912000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('27-02-2014 18:25:02.912000', 'dd-mm-yyyy hh24:mi:ss.ff'), 1, '保险公司法人许可证', '保险公司法人许可证', null, 'c5edfc8b-011f-491c-b548-3dfdd31c2a18');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('81eed697-bf41-4134-9d8f-f549caf42994', to_timestamp('27-02-2014 18:25:06.968000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('27-02-2014 18:25:06.968000', 'dd-mm-yyyy hh24:mi:ss.ff'), 1, '网络文化经营许可证', '网络文化经营许可证', null, 'c5edfc8b-011f-491c-b548-3dfdd31c2a18');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('53a185e4-6476-4975-a302-422bd4eecd10', to_timestamp('27-02-2014 18:25:10.766000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('27-02-2014 18:25:10.766000', 'dd-mm-yyyy hh24:mi:ss.ff'), 1, '典当经营许可证', '典当经营许可证', null, 'c5edfc8b-011f-491c-b548-3dfdd31c2a18');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('35dcbb2d-9a3c-4a68-9935-2ff4ccb299fd', to_timestamp('27-02-2014 18:25:15.877000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('27-02-2014 18:25:15.877000', 'dd-mm-yyyy hh24:mi:ss.ff'), 1, '保安服务许可证', '保安服务许可证', null, 'c5edfc8b-011f-491c-b548-3dfdd31c2a18');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('3637f17c-eb62-439c-b0dd-8dff92d3c154', to_timestamp('27-02-2014 18:25:19.842000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('27-02-2014 18:25:19.842000', 'dd-mm-yyyy hh24:mi:ss.ff'), 1, '因私出入境中介机构经营许可证', '因私出入境中介机构经营许可证', null, 'c5edfc8b-011f-491c-b548-3dfdd31c2a18');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('cf7ab846-1d62-47fe-95e9-6095e442e330', to_timestamp('27-02-2014 18:25:28.774000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('27-02-2014 18:25:28.774000', 'dd-mm-yyyy hh24:mi:ss.ff'), 1, '船员服务机构许可证', '船员服务机构许可证', null, 'c5edfc8b-011f-491c-b548-3dfdd31c2a18');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('53ff1837-ca46-4d95-a6d1-f2dad51866d4', to_timestamp('27-02-2014 18:25:32.338000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('27-02-2014 18:25:32.338000', 'dd-mm-yyyy hh24:mi:ss.ff'), 1, '水陆运输许可证', '水陆运输许可证', null, 'c5edfc8b-011f-491c-b548-3dfdd31c2a18');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('bd249419-2a8d-4a56-b569-cbcdcec23d4b', to_timestamp('27-02-2014 18:25:39.206000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('27-02-2014 18:25:39.206000', 'dd-mm-yyyy hh24:mi:ss.ff'), 1, '中华人民共和国拍卖经营批准证书', '中华人民共和国拍卖经营批准证书', null, 'c5edfc8b-011f-491c-b548-3dfdd31c2a18');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('22a91cee-48ef-45f9-896e-d3ba11c7d938', to_timestamp('27-02-2014 18:25:42.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('27-02-2014 18:43:34.022000', 'dd-mm-yyyy hh24:mi:ss.ff'), 2, '生猪定点屠宰证', '生猪定点屠宰证', null, 'c5edfc8b-011f-491c-b548-3dfdd31c2a18');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('ac306372-1c5c-437d-9534-e148511564a4', to_timestamp('27-02-2014 18:25:47.701000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('27-02-2014 18:25:47.701000', 'dd-mm-yyyy hh24:mi:ss.ff'), 1, '中华人民共和国医疗器械经营企业许可证', '中华人民共和国医疗器械经营企业许可证', null, 'c5edfc8b-011f-491c-b548-3dfdd31c2a18');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('1386e1bf-2916-4526-88ff-dbeca3fee314', to_timestamp('27-02-2014 18:25:51.553000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('27-02-2014 18:25:51.553000', 'dd-mm-yyyy hh24:mi:ss.ff'), 1, '烟草专卖生产企业许可证', '烟草专卖生产企业许可证', null, 'c5edfc8b-011f-491c-b548-3dfdd31c2a18');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('c1cc92db-39d3-4395-8d8a-cc20666b559d', to_timestamp('27-02-2014 18:25:56.450000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('27-02-2014 18:25:56.450000', 'dd-mm-yyyy hh24:mi:ss.ff'), 1, '特种烟草专卖经营企业许可证', '特种烟草专卖经营企业许可证', null, 'c5edfc8b-011f-491c-b548-3dfdd31c2a18');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('7c46129b-6c10-4e97-b1d6-5322aa6fe2f5', to_timestamp('27-02-2014 18:26:04.986000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('27-02-2014 18:26:04.986000', 'dd-mm-yyyy hh24:mi:ss.ff'), 1, '旅行社业务经营许可证', '旅行社业务经营许可证', null, 'c5edfc8b-011f-491c-b548-3dfdd31c2a18');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('5888e6cd-2c38-4c71-a8ed-2499712a4ad6', to_timestamp('27-02-2014 18:26:11.861000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('27-02-2014 18:26:11.861000', 'dd-mm-yyyy hh24:mi:ss.ff'), 1, '全国工业产品生产许可证', '全国工业产品生产许可证', null, 'c5edfc8b-011f-491c-b548-3dfdd31c2a18');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('f2433993-102d-4618-a998-edcf4c8cef12', to_timestamp('27-02-2014 18:26:16.889000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('27-02-2014 18:26:16.889000', 'dd-mm-yyyy hh24:mi:ss.ff'), 1, '保险兼业代理业务许可证', '保险兼业代理业务许可证', null, 'c5edfc8b-011f-491c-b548-3dfdd31c2a18');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('9d4ee510-7546-4173-88ee-9d7186fb7b0e', to_timestamp('27-02-2014 18:26:21.227000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('27-02-2014 18:26:21.227000', 'dd-mm-yyyy hh24:mi:ss.ff'), 1, '印刷经营许可证', '印刷经营许可证', null, 'c5edfc8b-011f-491c-b548-3dfdd31c2a18');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('a0be0475-b9a2-4f46-9b55-2081e39cbc5c', to_timestamp('16-05-2014 19:07:57.475000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('16-05-2014 19:07:57.475000', 'dd-mm-yyyy hh24:mi:ss.ff'), 0, '016', '泉州师范学院', null, null);
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('664bb40b-c35e-4817-9f9d-f13b2c7b7c00', to_timestamp('27-02-2014 18:26:24.466000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('27-02-2014 18:26:24.466000', 'dd-mm-yyyy hh24:mi:ss.ff'), 1, '音像制品经营许可证', '音像制品经营许可证', null, 'c5edfc8b-011f-491c-b548-3dfdd31c2a18');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('85447c02-63df-4558-b6c4-0b186c9f6435', to_timestamp('30-04-2014 10:55:21.312000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('30-04-2014 10:55:21.312000', 'dd-mm-yyyy hh24:mi:ss.ff'), 12, 'WORKFLOW_RESERVED_TEXT_9', '预留文本字段9(255字符长度)', null, '9ded6b48-3fb3-4447-9431-794322b46166');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('56d198fe-a2b1-4a6c-be6e-c971a15cb7f3', to_timestamp('09-05-2014 10:23:31.192000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('09-05-2014 10:23:31.192000', 'dd-mm-yyyy hh24:mi:ss.ff'), 5, 'File_reservedText6', '预留文本字段6(255字符长度)', null, 'c1dede19-dc63-4a54-a746-f21c360ec94e');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('a35eb98b-846b-4764-88b1-9c9860f8781a', to_timestamp('09-05-2014 10:23:49.734000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('09-05-2014 10:23:49.734000', 'dd-mm-yyyy hh24:mi:ss.ff'), 5, 'File_reservedText7', '预留文本字段7(255字符长度)', null, 'c1dede19-dc63-4a54-a746-f21c360ec94e');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('f222977e-cbac-4d42-9c25-dcb5b90a30e8', to_timestamp('09-05-2014 10:24:23.989000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('09-05-2014 10:24:23.989000', 'dd-mm-yyyy hh24:mi:ss.ff'), 5, 'File_reservedText8', '预留文本字段8(255字符长度)', null, 'c1dede19-dc63-4a54-a746-f21c360ec94e');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('64d091fa-3823-4612-b979-df844f647e27', to_timestamp('13-05-2014 10:14:15.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('18-07-2014 10:24:49.777000', 'dd-mm-yyyy hh24:mi:ss.ff'), 6, '015016', '事项类型', 'XZSP_MATTERS_TYPE', '8c56947c-ac41-484e-9ad6-a5ae5ccb7104');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('6c2f4d17-b4dd-4777-bffd-f759868bee18', to_timestamp('13-05-2014 10:17:59.682000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('18-07-2014 10:24:49.788000', 'dd-mm-yyyy hh24:mi:ss.ff'), 4, '1', '普通项目', null, '64d091fa-3823-4612-b979-df844f647e27');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('b7695c07-d0f1-4f69-beab-5eca2fbd1783', to_timestamp('16-05-2014 19:08:52.794000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('16-05-2014 19:08:52.794000', 'dd-mm-yyyy hh24:mi:ss.ff'), 0, '016001', '发文类型', 'QZSF_FWLX', 'a0be0475-b9a2-4f46-9b55-2081e39cbc5c');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('f7df77ca-4adc-42c1-b3e8-0b5a073a2aa1', to_timestamp('16-05-2014 19:10:19.531000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('16-05-2014 19:10:19.531000', 'dd-mm-yyyy hh24:mi:ss.ff'), 0, '016001001', '上行文', null, 'b7695c07-d0f1-4f69-beab-5eca2fbd1783');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('73509d54-1361-490e-b189-95f3419935e5', to_timestamp('16-05-2014 19:10:50.088000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('16-05-2014 19:10:50.088000', 'dd-mm-yyyy hh24:mi:ss.ff'), 0, '016001002', '下行文', null, 'b7695c07-d0f1-4f69-beab-5eca2fbd1783');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('2e372ab2-b363-4ea3-b8f3-c5ad51226cfc', to_timestamp('16-05-2014 19:11:14.266000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('16-05-2014 19:11:14.266000', 'dd-mm-yyyy hh24:mi:ss.ff'), 0, '016001003', '平行文', null, 'b7695c07-d0f1-4f69-beab-5eca2fbd1783');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('c86c030e-458f-4e78-a572-4a680849bfe7', to_timestamp('03-06-2014 19:32:26.116000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('03-06-2014 19:32:26.116000', 'dd-mm-yyyy hh24:mi:ss.ff'), 1, 'DYFORM_DATA_STATUS', '表单数据状态', null, '212a7db7-8563-45fa-8e52-92dd53a2eff1');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('5eb4c662-6d0c-4cfa-8b06-caacf96b99e3', to_timestamp('03-06-2014 19:33:03.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('03-06-2014 19:34:30.042000', 'dd-mm-yyyy hh24:mi:ss.ff'), 3, 'DYFORM_DATA_STATUS_DEL', '数据已删除状态', null, 'c86c030e-458f-4e78-a572-4a680849bfe7');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('e321c70f-7fed-43bd-a294-54aef8d8d964', to_timestamp('03-06-2014 19:34:16.546000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('03-06-2014 19:34:16.546000', 'dd-mm-yyyy hh24:mi:ss.ff'), 1, 'DYFORM_DATA_STATUS_DEFAULT', '表单数据默认状态', null, 'c86c030e-458f-4e78-a572-4a680849bfe7');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('64a0caed-e6cb-47d3-b718-410d464efba7', to_timestamp('04-06-2014 16:32:51.834000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('04-06-2014 16:32:51.834000', 'dd-mm-yyyy hh24:mi:ss.ff'), 3, '012001015', '材料与证照管理', null, '7e1223cc-3af2-464f-b563-2f01cbeb6943');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('462a8ca9-e9e9-4997-98da-d3b64c11f70a', to_timestamp('12-06-2014 19:17:39.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('18-07-2014 10:25:04.708000', 'dd-mm-yyyy hh24:mi:ss.ff'), 8, '015017', '所属分类', 'XZSP_PROJECT_BELONGING_TYPE', '8c56947c-ac41-484e-9ad6-a5ae5ccb7104');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('8bc7682b-9343-4a4d-9c79-465fa45f4316', to_timestamp('12-06-2014 19:25:05.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('18-07-2014 10:25:19.512000', 'dd-mm-yyyy hh24:mi:ss.ff'), 7, '015018', '建筑等级', 'XZSP_CONSTRUCT_LEVEL', '8c56947c-ac41-484e-9ad6-a5ae5ccb7104');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('cca8613e-de14-4c2b-86aa-14c35f909586', to_timestamp('12-06-2014 19:30:31.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('18-07-2014 10:25:34.199000', 'dd-mm-yyyy hh24:mi:ss.ff'), 8, '015019', '敏感性质', 'XZSP_SENSITIVE_NATURE', '8c56947c-ac41-484e-9ad6-a5ae5ccb7104');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('4fed7f66-be0f-4412-a2ae-8935fa37023b', to_timestamp('12-06-2014 19:31:56.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('18-07-2014 10:25:47.905000', 'dd-mm-yyyy hh24:mi:ss.ff'), 8, '015020', '协调项目', 'XZSP_COORDINATE_PROJECT', '8c56947c-ac41-484e-9ad6-a5ae5ccb7104');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('91d7e712-99fd-4b21-8417-cfee76088812', to_timestamp('12-06-2014 19:33:39.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('17-07-2014 16:56:55.548000', 'dd-mm-yyyy hh24:mi:ss.ff'), 6, '015021', '行政分区', 'XZSP_ADMINISTRATIVE_AREAS', '8c56947c-ac41-484e-9ad6-a5ae5ccb7104');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('489aed57-36d2-4006-a340-e43bbeb6a918', to_timestamp('12-06-2014 19:42:44.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('18-07-2014 10:29:11.243000', 'dd-mm-yyyy hh24:mi:ss.ff'), 10, '015022', '项目所在区域', 'XZSP_PROJECT_BELONGING_AREA', '8c56947c-ac41-484e-9ad6-a5ae5ccb7104');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('efc1d943-f899-424b-9b74-a3c2bdce3faa', to_timestamp('12-06-2014 19:47:57.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('18-07-2014 10:29:37.600000', 'dd-mm-yyyy hh24:mi:ss.ff'), 6, '015023', '土地供应方式', 'XZSP_LAND_SUPPLY_WAY', '8c56947c-ac41-484e-9ad6-a5ae5ccb7104');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('dbb0479c-1856-4f05-833b-e33f15e12e41', to_timestamp('12-06-2014 19:53:37.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('18-07-2014 10:29:55.149000', 'dd-mm-yyyy hh24:mi:ss.ff'), 8, '015024', '五大战役类别', 'XZSP_BATTLE_CATEGORY', '8c56947c-ac41-484e-9ad6-a5ae5ccb7104');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('96e5baff-3573-4eec-82ab-8440c07d7fa1', to_timestamp('12-06-2014 19:54:41.892000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('18-07-2014 10:29:55.155000', 'dd-mm-yyyy hh24:mi:ss.ff'), 1, '1', '重点项目建设战役', null, 'dbb0479c-1856-4f05-833b-e33f15e12e41');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('df96e562-a52d-4bae-8b9d-4f6cfb1512d6', to_timestamp('12-06-2014 19:56:02.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('18-07-2014 10:30:14.415000', 'dd-mm-yyyy hh24:mi:ss.ff'), 7, '015025', '重点跟踪', 'XZSP_IMPORT_TRACK', '8c56947c-ac41-484e-9ad6-a5ae5ccb7104');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('401422ff-2c61-46c4-bda9-c0ba323f5e90', to_timestamp('12-06-2014 19:56:02.992000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('18-07-2014 10:30:14.420000', 'dd-mm-yyyy hh24:mi:ss.ff'), 1, '1', '重点跟踪', null, 'df96e562-a52d-4bae-8b9d-4f6cfb1512d6');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('4f610a6b-b930-4708-8e49-66e4f7435716', to_timestamp('18-06-2014 16:20:13.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('18-06-2014 16:21:10.095000', 'dd-mm-yyyy hh24:mi:ss.ff'), 6, '7', '市政道路', '007', '2218d711-b625-44e5-8732-55d0af955279');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('e953026d-f144-4fd2-b642-a7675bfa28d4', to_timestamp('18-06-2014 16:22:41.476000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('18-06-2014 16:22:41.476000', 'dd-mm-yyyy hh24:mi:ss.ff'), 5, '8', '其它', '008', '2218d711-b625-44e5-8732-55d0af955279');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('f39118b5-e65c-41cc-b1f0-3c71ddb1597e', to_timestamp('07-07-2014 15:53:11.834000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('21-07-2014 17:44:43.230000', 'dd-mm-yyyy hh24:mi:ss.ff'), 14, '015013001002', '方案阶段', null, 'c33a8dbe-27e2-4431-a429-2033b2022c20');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('b6c98c37-3b8b-4575-ab7c-e7e6b3280113', to_timestamp('07-07-2014 15:54:07.040000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('21-07-2014 17:44:43.254000', 'dd-mm-yyyy hh24:mi:ss.ff'), 14, '015013001003', '施工图阶段', null, 'c33a8dbe-27e2-4431-a429-2033b2022c20');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('da71243f-5d99-4fba-b9bd-5cb61336a4ea', to_timestamp('07-07-2014 15:54:32.759000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('21-07-2014 17:44:43.250000', 'dd-mm-yyyy hh24:mi:ss.ff'), 14, '015013001004', '实施阶段', null, 'c33a8dbe-27e2-4431-a429-2033b2022c20');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('a8af1cf1-0e77-4ad9-9fbb-3ebd3e28735f', to_timestamp('18-07-2014 17:45:26.959000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('18-07-2014 17:45:26.959000', 'dd-mm-yyyy hh24:mi:ss.ff'), 0, '进出口商品检验鉴定机构资格证书', '进出口商品检验鉴定机构资格证书', null, 'c5edfc8b-011f-491c-b548-3dfdd31c2a18');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('a880cef0-0abe-4ef7-b8c1-77fb5c8ec975', to_timestamp('18-07-2014 17:35:54.415000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('18-07-2014 17:35:54.415000', 'dd-mm-yyyy hh24:mi:ss.ff'), 0, '中华人民共和国国境口岸食品生产经营单位许可证', '中华人民共和国国境口岸食品生产经营单位许可证', null, 'c5edfc8b-011f-491c-b548-3dfdd31c2a18');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('339ba909-f902-434c-91a4-5482ec09a8c8', to_timestamp('09-07-2014 16:12:24.665000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('18-07-2014 10:25:04.711000', 'dd-mm-yyyy hh24:mi:ss.ff'), 1, '4', '其他', null, '462a8ca9-e9e9-4997-98da-d3b64c11f70a');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('89eff38e-4189-467e-a23d-2c8e21886a90', null, null, 'U0010000001', to_timestamp('08-07-2014 17:45:48.943000', 'dd-mm-yyyy hh24:mi:ss.ff'), 1, '30023', '城市架设依附城市桥梁管线许可', null, 'f31c3e8c-71e2-4c48-8c75-74981cd447ea');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('f84530bb-9584-4f48-81d3-752a41bca701', to_timestamp('12-07-2014 15:53:50.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('24-07-2014 18:16:14.800000', 'dd-mm-yyyy hh24:mi:ss.ff'), 49, '015026', '材料性质', 'XZSP_MATERIAL_NATURE', '8c56947c-ac41-484e-9ad6-a5ae5ccb7104');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('a5cf08bd-ffe7-4229-a116-fcb34fe4947b', to_timestamp('12-07-2014 16:20:33.080000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('12-07-2014 16:20:33.080000', 'dd-mm-yyyy hh24:mi:ss.ff'), 0, '0018', '原件或复印件', null, 'f84530bb-9584-4f48-81d3-752a41bca701');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('1ffd88a8-da66-43bb-8c92-a19d7dd0fe1f', to_timestamp('12-07-2014 16:20:53.198000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('12-07-2014 16:20:53.198000', 'dd-mm-yyyy hh24:mi:ss.ff'), 0, '0019', '原件和电子', null, 'f84530bb-9584-4f48-81d3-752a41bca701');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('b83309f2-352b-4cd9-b274-0afe7780e2fb', null, null, 'U0010000001', to_timestamp('08-07-2014 17:45:39.713000', 'dd-mm-yyyy hh24:mi:ss.ff'), 1, '20013', '福建省瓶装燃气经营许可证', null, 'f31c3e8c-71e2-4c48-8c75-74981cd447ea');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('63d105fb-c1a1-4b8e-a330-f268725a1375', null, null, 'U0010000001', to_timestamp('08-07-2014 17:45:30.806000', 'dd-mm-yyyy hh24:mi:ss.ff'), 1, '20014', '福建省燃气汽车加气经营许可证', null, 'f31c3e8c-71e2-4c48-8c75-74981cd447ea');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('ff23da9b-e44f-438a-b206-f19dd03876c5', to_timestamp('14-07-2014 17:40:14.611000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('14-07-2014 17:40:14.611000', 'dd-mm-yyyy hh24:mi:ss.ff'), 0, '0020', '副本及复印件', null, 'f84530bb-9584-4f48-81d3-752a41bca701');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('ecf4639b-196f-4255-b41f-a270745f1cf1', null, null, 'U0010000001', to_timestamp('08-07-2014 17:44:50.804000', 'dd-mm-yyyy hh24:mi:ss.ff'), 1, '30024', '准予新建改建燃气工程许可', null, 'f31c3e8c-71e2-4c48-8c75-74981cd447ea');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('4bfafe0d-ab41-4030-a2bf-7560af1add72', null, null, 'U0010000001', to_timestamp('08-07-2014 17:44:40.787000', 'dd-mm-yyyy hh24:mi:ss.ff'), 1, '20015', '厦门市燃气工程许可证', null, 'f31c3e8c-71e2-4c48-8c75-74981cd447ea');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('e8686509-f82e-4e18-a8dd-a9a9daedbf14', null, null, 'U0010000001', to_timestamp('18-07-2014 10:25:19.523000', 'dd-mm-yyyy hh24:mi:ss.ff'), 4, '2', '二级', null, '8bc7682b-9343-4a4d-9c79-465fa45f4316');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('08b1b2bc-80de-4a25-95b1-25d346290c2e', null, null, 'U0010000001', to_timestamp('18-07-2014 10:25:19.520000', 'dd-mm-yyyy hh24:mi:ss.ff'), 5, '3', '三级', null, '8bc7682b-9343-4a4d-9c79-465fa45f4316');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('178d5c9c-3e60-4c41-9944-a21805ac8838', null, null, 'U0010000001', to_timestamp('18-07-2014 10:25:19.517000', 'dd-mm-yyyy hh24:mi:ss.ff'), 4, '4', '特级', null, '8bc7682b-9343-4a4d-9c79-465fa45f4316');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('6c184786-1aef-43a2-b795-1d30492c596d', null, null, 'U0010000001', to_timestamp('18-07-2014 10:25:19.515000', 'dd-mm-yyyy hh24:mi:ss.ff'), 5, '5', '未知', null, '8bc7682b-9343-4a4d-9c79-465fa45f4316');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('ca32460a-d93b-4e2e-9780-8334dc1e1ac5', null, null, 'U0010000001', to_timestamp('18-07-2014 10:25:34.207000', 'dd-mm-yyyy hh24:mi:ss.ff'), 5, '1', '敏感', null, 'cca8613e-de14-4c2b-86aa-14c35f909586');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('9c8ccdd5-7e81-4d8e-8001-f0b8a4999c47', null, null, 'U0010000001', to_timestamp('18-07-2014 10:25:34.205000', 'dd-mm-yyyy hh24:mi:ss.ff'), 6, '2', '非敏感', null, 'cca8613e-de14-4c2b-86aa-14c35f909586');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('dfa25551-9e9e-4bd9-9776-7b69fe3c7273', null, null, 'U0010000001', to_timestamp('18-07-2014 10:25:34.202000', 'dd-mm-yyyy hh24:mi:ss.ff'), 6, '3', '未知', null, 'cca8613e-de14-4c2b-86aa-14c35f909586');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('11c54734-03a0-4570-8849-091151d9fe00', null, null, 'U0010000001', to_timestamp('18-07-2014 10:25:47.908000', 'dd-mm-yyyy hh24:mi:ss.ff'), 5, '1', '协调项目', null, '4fed7f66-be0f-4412-a2ae-8935fa37023b');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('c72b50bc-6487-4169-8689-f337507d4eb6', null, null, 'U0010000001', to_timestamp('18-07-2014 10:25:47.913000', 'dd-mm-yyyy hh24:mi:ss.ff'), 6, '2', '非协调项目', null, '4fed7f66-be0f-4412-a2ae-8935fa37023b');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('2733c6d9-8bf0-47e8-8520-e858cf1ec6a5', null, null, 'U0010000001', to_timestamp('18-07-2014 10:25:47.910000', 'dd-mm-yyyy hh24:mi:ss.ff'), 6, '3', '未知', null, '4fed7f66-be0f-4412-a2ae-8935fa37023b');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('4a0c87e0-e593-495a-9dc3-97e81389d545', null, null, 'U0010000001', to_timestamp('12-06-2014 19:40:53.329000', 'dd-mm-yyyy hh24:mi:ss.ff'), 4, '350200', '福建省厦门市', null, '91d7e712-99fd-4b21-8417-cfee76088812');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('0bbda9be-6e04-4c15-bc12-01a2b47af6f6', null, null, 'U0010000001', to_timestamp('12-06-2014 19:40:53.315000', 'dd-mm-yyyy hh24:mi:ss.ff'), 4, '350203', '思明区', null, '91d7e712-99fd-4b21-8417-cfee76088812');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('149db826-1111-404e-a834-93ffb549185c', null, null, 'U0010000001', to_timestamp('12-06-2014 19:40:53.318000', 'dd-mm-yyyy hh24:mi:ss.ff'), 4, '350206', '湖里区', null, '91d7e712-99fd-4b21-8417-cfee76088812');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('73d0654f-06e3-4749-8c1a-32991c653e94', null, null, 'U0010000001', to_timestamp('12-06-2014 19:40:53.316000', 'dd-mm-yyyy hh24:mi:ss.ff'), 4, '350205', '海沧区', null, '91d7e712-99fd-4b21-8417-cfee76088812');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('d2c751d5-eaf1-44ec-ae3d-2f3de0771153', null, null, 'U0010000001', to_timestamp('12-06-2014 19:40:53.323000', 'dd-mm-yyyy hh24:mi:ss.ff'), 4, '350211', '集美区', null, '91d7e712-99fd-4b21-8417-cfee76088812');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('7e71aa21-d4b5-4fb6-adf4-bf5e0f76c6a2', null, null, 'U0010000001', to_timestamp('12-06-2014 19:40:53.326000', 'dd-mm-yyyy hh24:mi:ss.ff'), 4, '350212', '同安区', null, '91d7e712-99fd-4b21-8417-cfee76088812');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('ee1cc3ba-1b7c-4676-b79f-c1c906806c74', null, null, 'U0010000001', to_timestamp('12-06-2014 19:40:53.320000', 'dd-mm-yyyy hh24:mi:ss.ff'), 4, '350213', '翔安区', null, '91d7e712-99fd-4b21-8417-cfee76088812');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('323f4ff0-1a15-4f71-9e88-8082ed9721c2', null, null, 'U0010000001', to_timestamp('18-07-2014 10:29:11.260000', 'dd-mm-yyyy hh24:mi:ss.ff'), 8, '0', '全市', null, '489aed57-36d2-4006-a340-e43bbeb6a918');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('6f777907-3957-4135-8a99-557b6fc156a9', null, null, 'U0010000001', to_timestamp('18-07-2014 10:29:11.262000', 'dd-mm-yyyy hh24:mi:ss.ff'), 8, '1', '思明', null, '489aed57-36d2-4006-a340-e43bbeb6a918');
commit;
prompt 100 records committed...
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('70475c41-e348-418a-ac83-4d79003917c3', null, null, 'U0010000001', to_timestamp('18-07-2014 10:29:11.257000', 'dd-mm-yyyy hh24:mi:ss.ff'), 8, '2', '湖里', null, '489aed57-36d2-4006-a340-e43bbeb6a918');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('98feb3bb-a2e8-4a56-b4d1-c65157319240', null, null, 'U0010000001', to_timestamp('18-07-2014 10:29:11.249000', 'dd-mm-yyyy hh24:mi:ss.ff'), 8, '3', '海沧', null, '489aed57-36d2-4006-a340-e43bbeb6a918');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('50f59b69-eb6a-4d23-9b39-b7d1461cbc39', null, null, 'U0010000001', to_timestamp('18-07-2014 10:29:11.246000', 'dd-mm-yyyy hh24:mi:ss.ff'), 8, '4', '集美', null, '489aed57-36d2-4006-a340-e43bbeb6a918');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('931af160-870b-42fa-b6f4-78d76fa8f079', null, null, 'U0010000001', to_timestamp('18-07-2014 10:29:11.254000', 'dd-mm-yyyy hh24:mi:ss.ff'), 8, '5', '同安', null, '489aed57-36d2-4006-a340-e43bbeb6a918');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('116181d7-08d5-41f4-8426-664fae8860b0', null, null, 'U0010000001', to_timestamp('18-07-2014 10:29:11.252000', 'dd-mm-yyyy hh24:mi:ss.ff'), 8, '6', '翔安', null, '489aed57-36d2-4006-a340-e43bbeb6a918');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('350a0877-1bbe-4daa-8b0c-3a2c91c3cb20', null, null, 'U0010000001', to_timestamp('18-07-2014 10:29:37.611000', 'dd-mm-yyyy hh24:mi:ss.ff'), 4, '1', '划拨土地', null, 'efc1d943-f899-424b-9b74-a3c2bdce3faa');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('3838121b-8d37-41e6-967b-653e2923e32b', null, null, 'U0010000001', to_timestamp('18-07-2014 10:29:37.608000', 'dd-mm-yyyy hh24:mi:ss.ff'), 4, '2', '不涉及农用地', null, 'efc1d943-f899-424b-9b74-a3c2bdce3faa');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('fecd6103-3d80-41cb-aeab-b9ad9afdceca', null, null, 'U0010000001', to_timestamp('18-07-2014 10:29:37.605000', 'dd-mm-yyyy hh24:mi:ss.ff'), 4, '3', '涉及农用地', null, 'efc1d943-f899-424b-9b74-a3c2bdce3faa');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('236695f0-5840-48bf-9d28-26a496a2b6b8', null, null, 'U0010000001', to_timestamp('18-07-2014 10:29:37.603000', 'dd-mm-yyyy hh24:mi:ss.ff'), 4, '4', '未知', null, 'efc1d943-f899-424b-9b74-a3c2bdce3faa');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('ea2ce5ce-741d-430d-aff9-e214cf9d6e4f', null, null, 'U0010000001', to_timestamp('18-07-2014 10:29:55.161000', 'dd-mm-yyyy hh24:mi:ss.ff'), 6, '2', '城市建设战役', null, 'dbb0479c-1856-4f05-833b-e33f15e12e41');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('3b9ff85a-f9c4-4f5c-999a-d51543f6a9fa', null, null, 'U0010000001', to_timestamp('18-07-2014 10:29:55.163000', 'dd-mm-yyyy hh24:mi:ss.ff'), 6, '3', '小城镇改革发展战役', null, 'dbb0479c-1856-4f05-833b-e33f15e12e41');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('5b85c6e9-afba-4fe7-9308-656402e6055a', null, null, 'U0010000001', to_timestamp('18-07-2014 10:29:55.158000', 'dd-mm-yyyy hh24:mi:ss.ff'), 6, '4', '民生工程战役', null, 'dbb0479c-1856-4f05-833b-e33f15e12e41');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('e1366074-2ea3-46e6-b2b4-f1c4d40c88a7', null, null, 'U0010000001', to_timestamp('18-07-2014 10:29:55.152000', 'dd-mm-yyyy hh24:mi:ss.ff'), 6, '5', '新增长区域发展战役', null, 'dbb0479c-1856-4f05-833b-e33f15e12e41');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('6947047f-8ccd-4a5a-92a8-ca8a14ff7918', null, null, 'U0010000001', to_timestamp('18-07-2014 10:30:14.423000', 'dd-mm-yyyy hh24:mi:ss.ff'), 5, '2', '非重点跟踪', null, 'df96e562-a52d-4bae-8b9d-4f6cfb1512d6');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('47bd22ff-ad46-428e-9ae6-d7a0c174ef0a', null, null, 'U0010000001', to_timestamp('18-07-2014 10:30:14.417000', 'dd-mm-yyyy hh24:mi:ss.ff'), 5, '3', '未知', null, 'df96e562-a52d-4bae-8b9d-4f6cfb1512d6');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('aae19d51-cb3d-40e7-83b1-9a1bbcb86a74', null, null, 'U0010000001', to_timestamp('08-07-2014 17:40:19.655000', 'dd-mm-yyyy hh24:mi:ss.ff'), 1, '20021', '厦门市驻园单位公务用车进出公园许可证', null, 'f31c3e8c-71e2-4c48-8c75-74981cd447ea');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('1259b7eb-50bd-4088-a7d4-b22b788deec6', null, null, 'U0010000001', to_timestamp('08-07-2014 17:50:23.122000', 'dd-mm-yyyy hh24:mi:ss.ff'), 2, '20023', '排放污染物临时许可证', null, 'f31c3e8c-71e2-4c48-8c75-74981cd447ea');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('59ea6364-a269-455d-9936-ad90de1251af', to_timestamp('14-07-2014 17:48:49.884000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('14-07-2014 17:48:49.884000', 'dd-mm-yyyy hh24:mi:ss.ff'), 0, '30038', '携带、运输、邮寄档案出境许可批文', null, 'f31c3e8c-71e2-4c48-8c75-74981cd447ea');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('cebf1991-6417-4523-82f1-10aa888b5d7a', null, null, 'U0010000001', to_timestamp('08-07-2014 17:42:01.026000', 'dd-mm-yyyy hh24:mi:ss.ff'), 1, '30028', '厦门市公园竣工验收意见书', null, 'f31c3e8c-71e2-4c48-8c75-74981cd447ea');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('602b25a3-e84e-45fc-b632-f55b24eb60ef', to_timestamp('08-07-2014 17:42:58.235000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('08-07-2014 17:42:58.235000', 'dd-mm-yyyy hh24:mi:ss.ff'), 0, '20022', '园林绿化建设合格证', null, 'f31c3e8c-71e2-4c48-8c75-74981cd447ea');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('dcdc9df8-a140-4489-be35-fbffd0477a66', to_timestamp('08-07-2014 17:51:40.241000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('08-07-2014 17:51:40.241000', 'dd-mm-yyyy hh24:mi:ss.ff'), 0, '30033', '防雷装置设计修改意见书', null, 'f31c3e8c-71e2-4c48-8c75-74981cd447ea');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('12902dc9-9009-4551-9450-754e2519334d', null, null, 'U0010000001', to_timestamp('08-07-2014 17:42:18.783000', 'dd-mm-yyyy hh24:mi:ss.ff'), 1, '20019', '植物检疫证书', null, 'f31c3e8c-71e2-4c48-8c75-74981cd447ea');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('95eaecbb-249b-44a3-b9a6-42f68c1631fe', null, null, 'U0010000001', to_timestamp('08-07-2014 17:42:10.144000', 'dd-mm-yyyy hh24:mi:ss.ff'), 1, '20020', '木材运输证', null, 'f31c3e8c-71e2-4c48-8c75-74981cd447ea');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('c837cfcb-469d-49ff-8014-4689ea73ed80', to_timestamp('08-07-2014 16:13:12.020000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('08-07-2014 16:13:12.020000', 'dd-mm-yyyy hh24:mi:ss.ff'), 0, '30016', '厦门市单建人防工程开工意见书', null, 'f31c3e8c-71e2-4c48-8c75-74981cd447ea');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('99d264f2-a559-4066-96ff-5c9764924b4c', to_timestamp('08-07-2014 16:13:53.901000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('08-07-2014 16:13:53.901000', 'dd-mm-yyyy hh24:mi:ss.ff'), 0, '30017', '厦门市拆除人防通信设备、防空警报设施意见书', null, 'f31c3e8c-71e2-4c48-8c75-74981cd447ea');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('eac9f0b3-1af3-436e-af14-ca3122c725c8', to_timestamp('08-07-2014 16:14:35.463000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('08-07-2014 16:14:35.463000', 'dd-mm-yyyy hh24:mi:ss.ff'), 0, '30018', '厦门市拆除人防工程及配套设施意见书', null, 'f31c3e8c-71e2-4c48-8c75-74981cd447ea');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('a6409b6c-729c-4f58-9c2e-70c14149c6f9', null, null, 'U0010000001', to_timestamp('08-07-2014 16:16:14.642000', 'dd-mm-yyyy hh24:mi:ss.ff'), 1, '30019', '厦门市结合民用建筑修建防空地下室设计方案告知书', null, 'f31c3e8c-71e2-4c48-8c75-74981cd447ea');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('48101571-feb4-463b-a869-3f17124ab99b', null, null, 'U0010000001', to_timestamp('08-07-2014 17:47:25.932000', 'dd-mm-yyyy hh24:mi:ss.ff'), 1, '20010', '排水许可证', null, 'f31c3e8c-71e2-4c48-8c75-74981cd447ea');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('47b4c35b-82e8-42a4-8ed2-39b677192197', null, null, 'U0010000001', to_timestamp('08-07-2014 17:46:52.596000', 'dd-mm-yyyy hh24:mi:ss.ff'), 1, '30020', '项目雨水、污水接入市政管网确认书', null, 'f31c3e8c-71e2-4c48-8c75-74981cd447ea');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('efeccd01-b470-40ed-b2f6-030ccc3ef2b8', null, null, 'U0010000001', to_timestamp('08-07-2014 17:47:06.671000', 'dd-mm-yyyy hh24:mi:ss.ff'), 1, '30021', '供水管道动迁许可批复', null, 'f31c3e8c-71e2-4c48-8c75-74981cd447ea');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('0e1d13a5-e3ef-40c6-a71c-8e0ddd976b0e', null, null, 'U0010000001', to_timestamp('08-07-2014 17:46:41.433000', 'dd-mm-yyyy hh24:mi:ss.ff'), 1, '30022', '排水设施动迁许可批复', null, 'f31c3e8c-71e2-4c48-8c75-74981cd447ea');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('001b2516-e270-47a8-9466-61cb9f4aca1d', null, null, 'U0010000001', to_timestamp('08-07-2014 17:46:20.814000', 'dd-mm-yyyy hh24:mi:ss.ff'), 1, '10002', '厦门市排水设施验收表', null, 'f31c3e8c-71e2-4c48-8c75-74981cd447ea');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('2268de2b-19f0-44c9-8f0b-6d5d728281a5', to_timestamp('14-07-2014 17:28:21.471000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('14-07-2014 17:28:21.471000', 'dd-mm-yyyy hh24:mi:ss.ff'), 0, '30035', '行政许可决定书', null, 'f31c3e8c-71e2-4c48-8c75-74981cd447ea');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('27dd859f-fdb2-4b06-872d-1806f5b17c8d', to_timestamp('14-07-2014 17:37:54.992000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('14-07-2014 17:37:54.992000', 'dd-mm-yyyy hh24:mi:ss.ff'), 0, '30036', '出卖、转让档案许可批文', null, 'f31c3e8c-71e2-4c48-8c75-74981cd447ea');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('3cfae29a-1c02-4012-b772-efaa2beab279', null, null, 'U0010000001', to_timestamp('12-07-2014 17:02:17.187000', 'dd-mm-yyyy hh24:mi:ss.ff'), 9, '0002', '副本', null, 'f84530bb-9584-4f48-81d3-752a41bca701');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('9d21b29f-f4b4-4665-9cfc-565825f01e17', null, null, 'U0010000001', to_timestamp('12-07-2014 17:02:17.182000', 'dd-mm-yyyy hh24:mi:ss.ff'), 8, '0003', '复印件', null, 'f84530bb-9584-4f48-81d3-752a41bca701');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('4c32b7f4-99c4-4755-8fb0-35edaf99d1b1', null, null, 'U0010000001', to_timestamp('12-07-2014 17:02:17.179000', 'dd-mm-yyyy hh24:mi:ss.ff'), 10, '0001', '正本', null, 'f84530bb-9584-4f48-81d3-752a41bca701');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('1807c412-b24e-46ea-b1b6-ade4604cdcda', null, null, 'U0010000001', to_timestamp('12-07-2014 17:02:17.205000', 'dd-mm-yyyy hh24:mi:ss.ff'), 7, '0004', '电子', null, 'f84530bb-9584-4f48-81d3-752a41bca701');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('5dd6babb-eb13-42ca-822b-42d22de1d651', null, null, 'U0010000001', to_timestamp('12-07-2014 17:02:17.197000', 'dd-mm-yyyy hh24:mi:ss.ff'), 6, '0005', '扫描', null, 'f84530bb-9584-4f48-81d3-752a41bca701');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('482871fd-3bc6-4973-aca8-9305719da37f', null, null, 'U0010000001', to_timestamp('12-07-2014 17:02:17.192000', 'dd-mm-yyyy hh24:mi:ss.ff'), 5, '0006', '正本和电子', null, 'f84530bb-9584-4f48-81d3-752a41bca701');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('2108e670-d75b-4937-8914-edd5949b226d', null, null, 'U0010000001', to_timestamp('12-07-2014 17:02:17.194000', 'dd-mm-yyyy hh24:mi:ss.ff'), 5, '0007', '正本和扫描', null, 'f84530bb-9584-4f48-81d3-752a41bca701');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('1d1ff957-e622-4204-9463-75b8c2ebaec8', null, null, 'U0010000001', to_timestamp('12-07-2014 17:02:17.202000', 'dd-mm-yyyy hh24:mi:ss.ff'), 4, '0008', '正本或复印件', null, 'f84530bb-9584-4f48-81d3-752a41bca701');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('d3fa58e9-ed7a-4c9a-8b38-88086f49f274', null, null, 'U0010000001', to_timestamp('12-07-2014 17:02:17.174000', 'dd-mm-yyyy hh24:mi:ss.ff'), 4, '0011', '正本、副本及复印件', null, 'f84530bb-9584-4f48-81d3-752a41bca701');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('daa8c93c-25fe-499b-bb58-c53b8a7c0aff', null, null, 'U0010000001', to_timestamp('12-07-2014 17:02:17.164000', 'dd-mm-yyyy hh24:mi:ss.ff'), 4, '0009', '正本和复印件', null, 'f84530bb-9584-4f48-81d3-752a41bca701');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('dcee3dc8-a35f-4ac0-b1d2-b168d35ac6d3', null, null, 'U0010000001', to_timestamp('12-07-2014 17:02:17.199000', 'dd-mm-yyyy hh24:mi:ss.ff'), 4, '0010', '正本和副本', null, 'f84530bb-9584-4f48-81d3-752a41bca701');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('9f9def88-db8e-43bb-aba3-e79898710f5b', null, null, 'U0010000001', to_timestamp('12-07-2014 17:02:17.176000', 'dd-mm-yyyy hh24:mi:ss.ff'), 4, '0012', '副本和电子', null, 'f84530bb-9584-4f48-81d3-752a41bca701');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('1ac7a0ac-0819-4f3c-966c-d22b561fbe2e', null, null, 'U0010000001', to_timestamp('12-07-2014 17:02:17.167000', 'dd-mm-yyyy hh24:mi:ss.ff'), 2, '0013', '副本和扫描', null, 'f84530bb-9584-4f48-81d3-752a41bca701');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('dff039ca-b0da-40ba-8c6f-c4386b1353d6', null, null, 'U0010000001', to_timestamp('12-07-2014 17:02:17.189000', 'dd-mm-yyyy hh24:mi:ss.ff'), 2, '0014', '复印件和电子', null, 'f84530bb-9584-4f48-81d3-752a41bca701');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('83fc5fcf-5eb6-4ca4-8a58-0c11464573de', null, null, 'U0010000001', to_timestamp('12-07-2014 17:02:17.172000', 'dd-mm-yyyy hh24:mi:ss.ff'), 2, '0015', '复印件和扫描', null, 'f84530bb-9584-4f48-81d3-752a41bca701');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('fbb69f30-0ac2-4576-b63a-5fcfaeb9cdc6', null, null, 'U0010000001', to_timestamp('12-07-2014 17:02:17.169000', 'dd-mm-yyyy hh24:mi:ss.ff'), 2, '0016', '原件', null, 'f84530bb-9584-4f48-81d3-752a41bca701');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('f7700861-362d-4491-af1a-6b7cde342131', null, null, 'U0010000001', to_timestamp('12-07-2014 17:02:17.184000', 'dd-mm-yyyy hh24:mi:ss.ff'), 2, '0017', '原件和复印件', null, 'f84530bb-9584-4f48-81d3-752a41bca701');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('6a2c2897-8066-4282-babe-5f3490de0528', null, null, 'U0010000001', to_timestamp('15-07-2014 09:11:32.710000', 'dd-mm-yyyy hh24:mi:ss.ff'), 1, '0021', '光盘', null, 'f84530bb-9584-4f48-81d3-752a41bca701');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('98545736-f72b-4daf-bb36-56a1cc851a69', to_timestamp('09-07-2014 20:54:16.934000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('09-07-2014 20:54:16.934000', 'dd-mm-yyyy hh24:mi:ss.ff'), 117, '30034', '消防设计审核意见书', null, 'f31c3e8c-71e2-4c48-8c75-74981cd447ea');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('d4e1d51a-7fc0-4238-a89b-f0cc353731fe', to_timestamp('08-07-2014 16:12:46.201000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('08-07-2014 16:12:46.201000', 'dd-mm-yyyy hh24:mi:ss.ff'), 0, '30015', '厦门市单建人防工程建设意见书', null, 'f31c3e8c-71e2-4c48-8c75-74981cd447ea');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('1ab11700-ec57-4eff-a54f-46ed5c344bdb', to_timestamp('17-07-2014 17:04:33.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('17-07-2014 17:05:55.523000', 'dd-mm-yyyy hh24:mi:ss.ff'), 1, '012006', '导航效果', 'TREE_EFFECT', 'b6285038-465c-4e68-9f1f-bf7ac75957d6');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('e22212eb-e3da-4648-84ef-07fb1f5c6cff', to_timestamp('17-07-2014 17:06:15.118000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('18-07-2014 21:05:28.098000', 'dd-mm-yyyy hh24:mi:ss.ff'), 3, 'index_top_ssgl', '头部', null, '1ab11700-ec57-4eff-a54f-46ed5c344bdb');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('d07d6d1a-d932-45f6-8788-cfedebc983fd', to_timestamp('17-07-2014 17:06:32.254000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('17-07-2014 17:06:32.254000', 'dd-mm-yyyy hh24:mi:ss.ff'), 1, '012006002', '工具栏', null, '1ab11700-ec57-4eff-a54f-46ed5c344bdb');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('f9dc2aec-4d6c-495c-abca-3374ebba650b', to_timestamp('17-07-2014 17:07:05.795000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('17-07-2014 17:07:05.795000', 'dd-mm-yyyy hh24:mi:ss.ff'), 1, '012006003', '左侧一、二级导航', null, '1ab11700-ec57-4eff-a54f-46ed5c344bdb');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('09cf5e95-1aaf-4397-be85-a0afb4fc604a', to_timestamp('17-07-2014 17:07:27.457000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('17-07-2014 17:07:27.457000', 'dd-mm-yyyy hh24:mi:ss.ff'), 1, '012006004', '左侧树形导航', null, '1ab11700-ec57-4eff-a54f-46ed5c344bdb');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('c68c3345-f0b9-4f60-bce5-9eeedd73ed98', to_timestamp('17-07-2014 17:07:51.431000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('17-07-2014 17:07:51.431000', 'dd-mm-yyyy hh24:mi:ss.ff'), 1, '012006005', '大磁贴', null, '1ab11700-ec57-4eff-a54f-46ed5c344bdb');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('f34a2524-75d2-44cc-bad9-721fbc2986b6', to_timestamp('17-07-2014 17:08:11.171000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('17-07-2014 17:08:11.171000', 'dd-mm-yyyy hh24:mi:ss.ff'), 1, '012006006', '小磁贴', null, '1ab11700-ec57-4eff-a54f-46ed5c344bdb');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('7e70ef7f-7a93-4feb-8ea9-63b6ae101f98', to_timestamp('21-03-2013 18:05:58.680000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('21-03-2013 18:05:58.680000', 'dd-mm-yyyy hh24:mi:ss.ff'), 9, 'dy_work_task_delay_state', '任务状态', 'dy_work_task_delay_state', 'baf041d7-503f-4021-be08-97ad77bd755c');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('9b07ca28-ce59-4f5b-8911-f141859f1733', to_timestamp('03-03-2013 13:18:51.297000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('03-03-2013 13:18:51.297000', 'dd-mm-yyyy hh24:mi:ss.ff'), 9, 'dy_meeting_summary_detail_target', '目标与要求', 'dy_meeting_summary_detail_target', 'd4137060-b297-4f4e-82ae-fc88599d9d76');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('b46a7c16-df44-4312-b4ef-e590d02724c8', to_timestamp('21-03-2013 18:07:48.370000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('21-03-2013 18:07:48.370000', 'dd-mm-yyyy hh24:mi:ss.ff'), 9, 'dy_work_task_delay_begin_time', '开始时间', 'dy_work_task_delay_begin_time', 'baf041d7-503f-4021-be08-97ad77bd755c');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('afd3f11d-f1d5-4174-853a-1b298100061c', to_timestamp('21-03-2013 18:09:11.987000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('21-03-2013 18:09:11.987000', 'dd-mm-yyyy hh24:mi:ss.ff'), 9, 'dy_work_task_delay_target', '目标与要求', 'dy_work_task_delay_target', 'baf041d7-503f-4021-be08-97ad77bd755c');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('cb16fea5-5891-49f3-b1c1-676901952794', to_timestamp('21-03-2013 18:08:40.740000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('21-03-2013 18:08:40.740000', 'dd-mm-yyyy hh24:mi:ss.ff'), 9, 'dy_work_task_delay_count', '预估工作量', 'dy_work_task_delay_count', 'baf041d7-503f-4021-be08-97ad77bd755c');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('d4ff3983-c57f-43de-be1c-817a4dabed79', to_timestamp('21-03-2013 18:05:20.797000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('21-03-2013 18:05:20.797000', 'dd-mm-yyyy hh24:mi:ss.ff'), 9, 'dy_work_task_delay_name', '任务名称', 'dy_work_task_delay_name', 'baf041d7-503f-4021-be08-97ad77bd755c');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('61a9eea3-3e15-4e25-ae61-e4aca990c802', to_timestamp('03-03-2013 12:19:19.130000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('03-03-2013 12:19:19.130000', 'dd-mm-yyyy hh24:mi:ss.ff'), 9, 'dy_work_task_delay_detail_taskuuid', '任务uuid', 'dy_work_task_delay_detail_taskuuid', 'baf041d7-503f-4021-be08-97ad77bd755c');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('00a1d2cb-9d20-425d-a8da-a96a318f7023', to_timestamp('21-03-2013 18:08:15.380000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('21-03-2013 18:08:15.380000', 'dd-mm-yyyy hh24:mi:ss.ff'), 9, 'dy_work_task_delay_end_time', '结束时间', 'dy_work_task_delay_end_time', 'baf041d7-503f-4021-be08-97ad77bd755c');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('003fc670-69e4-472d-ba70-37b52e608623', to_timestamp('21-03-2013 20:01:21.810000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('21-03-2013 20:01:21.810000', 'dd-mm-yyyy hh24:mi:ss.ff'), 9, 'dy_work_task_cancel_detail_target', '目标与要求', 'dy_work_task_cancel_detail_target', 'bf11245a-3d95-49e3-84b3-ee91a8db27aa');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('1b1373c6-a50a-43ef-953f-9f37f857ada8', to_timestamp('21-03-2013 20:00:54.637000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('21-03-2013 20:00:54.637000', 'dd-mm-yyyy hh24:mi:ss.ff'), 9, 'dy_work_task_cancel_detail_count', '预估工作量', 'dy_work_task_cancel_detail_count', 'bf11245a-3d95-49e3-84b3-ee91a8db27aa');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('1dba0497-7afc-400b-b79c-a16114028201', to_timestamp('21-03-2013 19:59:21.780000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('21-03-2013 19:59:21.780000', 'dd-mm-yyyy hh24:mi:ss.ff'), 9, 'dy_work_task_cancel_detail_type', '任务类型', 'dy_work_task_cancel_detail_type', 'bf11245a-3d95-49e3-84b3-ee91a8db27aa');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('c9e00c15-fda7-46f3-a10f-974b594191d1', to_timestamp('21-03-2013 20:00:27.330000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('21-03-2013 20:00:27.330000', 'dd-mm-yyyy hh24:mi:ss.ff'), 9, 'dy_work_task_cancel_detail_end_time', '计划结束时间', 'dy_work_task_cancel_detail_end_time', 'bf11245a-3d95-49e3-84b3-ee91a8db27aa');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('add5aeb2-4ec2-490a-af5d-f2d8b8cf53f5', to_timestamp('21-03-2013 19:58:55.327000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('21-03-2013 19:58:55.327000', 'dd-mm-yyyy hh24:mi:ss.ff'), 9, 'dy_work_task_cancel_detail_name', '任务名称', 'dy_work_task_cancel_detail_name', 'bf11245a-3d95-49e3-84b3-ee91a8db27aa');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('c11d215c-75c1-4d72-9839-707d65c14f77', to_timestamp('04-03-2013 20:58:38.153000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('04-03-2013 20:58:38.153000', 'dd-mm-yyyy hh24:mi:ss.ff'), 9, 'dy_work_task_cancel_detail_uuid', '任务uuid', 'dy_work_task_cancel_detail_uuid', 'bf11245a-3d95-49e3-84b3-ee91a8db27aa');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('7637d549-d42e-48c8-9c45-a5a1581c1bc7', to_timestamp('21-03-2013 19:59:57.750000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('21-03-2013 19:59:57.750000', 'dd-mm-yyyy hh24:mi:ss.ff'), 9, 'dy_work_task_cancel_detail_begin_time', '计划开始时间', 'dy_work_task_cancel_detail_begin_time', 'bf11245a-3d95-49e3-84b3-ee91a8db27aa');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('729ad777-c18c-4192-a27b-98f67d3630a4', to_timestamp('21-08-2013 11:06:45.133000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('21-08-2013 11:06:45.133000', 'dd-mm-yyyy hh24:mi:ss.ff'), 5, 'File_reservedText5', '预留文本字段5(2000字符长度)', null, 'c1dede19-dc63-4a54-a746-f21c360ec94e');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('80a2c50c-e9fc-44bd-b795-72dd0c4c1ef2', to_timestamp('26-04-2013 10:28:48.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('26-04-2013 10:29:38.440000', 'dd-mm-yyyy hh24:mi:ss.ff'), 6, 'File_editFile', '可以编辑文档的人', null, 'c1dede19-dc63-4a54-a746-f21c360ec94e');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('8b9442a5-0d4b-451d-bd1a-acddcb1b60a3', to_timestamp('21-08-2013 11:09:07.240000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('21-08-2013 11:09:07.240000', 'dd-mm-yyyy hh24:mi:ss.ff'), 5, 'File_reservedDate1', '预留日期字段1', null, 'c1dede19-dc63-4a54-a746-f21c360ec94e');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('cad46f7e-98e7-4296-a8f5-b4d5678cf3a3', to_timestamp('12-09-2013 16:36:47.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('12-09-2013 16:37:43.087000', 'dd-mm-yyyy hh24:mi:ss.ff'), 6, 'FILE_APPROVAL_OPINION', '流程审批意见', 'FILE_APPROVAL_OPINION', 'c1dede19-dc63-4a54-a746-f21c360ec94e');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('cd631216-f4d5-4272-a5ad-0ec922c6132e', to_timestamp('20-08-2013 16:36:22.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('21-08-2013 11:04:42.317000', 'dd-mm-yyyy hh24:mi:ss.ff'), 7, 'File_reservedText1', '预留文本字段1(16字符长度)', null, 'c1dede19-dc63-4a54-a746-f21c360ec94e');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('d031dd55-0b07-448e-8531-407c7061435d', to_timestamp('10-07-2013 09:15:34.490000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('10-07-2013 09:15:34.490000', 'dd-mm-yyyy hh24:mi:ss.ff'), 5, 'File_parent_id', '所属夹', null, 'c1dede19-dc63-4a54-a746-f21c360ec94e');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('1288f87a-5035-4226-8cdc-330919f15041', to_timestamp('03-04-2013 11:36:13.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('03-04-2013 11:36:22.483000', 'dd-mm-yyyy hh24:mi:ss.ff'), 2, '006002', '库类型', 'lib_type', 'd380225e-77ff-4e51-9b77-55b5b57f7a24');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('14e9b29d-b1ba-4b89-a62c-95af2ef7cf2a', to_timestamp('13-05-2013 14:15:30.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('24-09-2013 12:04:35.633000', 'dd-mm-yyyy hh24:mi:ss.ff'), 1, '012003', '窗口元素主题', 'WIDGET_THEME', 'b6285038-465c-4e68-9f1f-bf7ac75957d6');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('71216cb3-081e-4988-8ad6-83a881da7cd7', to_timestamp('03-03-2013 13:17:33.830000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('03-03-2013 13:17:33.830000', 'dd-mm-yyyy hh24:mi:ss.ff'), 9, 'dy_meeting_summary_detail_name', '任务名称', 'dy_meeting_summary_detail_name', 'd4137060-b297-4f4e-82ae-fc88599d9d76');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('6d783aef-5c79-46d7-9a9d-b88b960c940d', to_timestamp('03-03-2013 13:19:03.673000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('03-03-2013 13:19:03.673000', 'dd-mm-yyyy hh24:mi:ss.ff'), 9, 'dy_meeting_summary_detail_type', '类型', 'dy_meeting_summary_detail_type', 'd4137060-b297-4f4e-82ae-fc88599d9d76');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('768fbe02-d883-4509-a831-b7be3b09d2b0', to_timestamp('03-03-2013 13:20:29.013000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('03-03-2013 13:20:29.013000', 'dd-mm-yyyy hh24:mi:ss.ff'), 9, 'dy_meeting_summary_detail_supervisior', '督办人', 'dy_meeting_summary_detail_supervisior', 'd4137060-b297-4f4e-82ae-fc88599d9d76');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('c6e42acc-543b-44ac-90d6-a91188ab4baa', to_timestamp('03-03-2013 13:18:30.230000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('03-03-2013 13:18:30.230000', 'dd-mm-yyyy hh24:mi:ss.ff'), 9, 'dy_meeting_summary_detail_endtime', '计划完成时间', 'dy_meeting_summary_detail_endtime', 'd4137060-b297-4f4e-82ae-fc88599d9d76');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('dcb09f22-1f4f-490a-a4bc-1b6e34395027', to_timestamp('03-03-2013 13:20:11.637000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('03-03-2013 13:20:11.637000', 'dd-mm-yyyy hh24:mi:ss.ff'), 9, 'dy_meeting_summary_detail_sharer', '共享人', 'dy_meeting_summary_detail_sharer', 'd4137060-b297-4f4e-82ae-fc88599d9d76');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('de244701-a887-485b-85d9-c68f56a53b38', to_timestamp('14-03-2013 17:17:44.030000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('14-03-2013 17:17:44.030000', 'dd-mm-yyyy hh24:mi:ss.ff'), 9, 'dy_work_report_detail_type', '类型', 'dy_work_report_detail_type', 'd4abeebe-3fe2-43c8-bf69-f5c382c910d9');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('ab9401e6-484b-4f88-bc1e-03a5d8902424', to_timestamp('14-03-2013 17:19:55.440000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('14-03-2013 17:19:55.440000', 'dd-mm-yyyy hh24:mi:ss.ff'), 9, 'dy_work_report_detail_count', '预计工作量', 'dy_work_report_detail_count', 'd4abeebe-3fe2-43c8-bf69-f5c382c910d9');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('9cec1c22-4ad8-45a7-9b55-68dde486274d', to_timestamp('14-03-2013 17:17:18.567000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('14-03-2013 17:17:18.567000', 'dd-mm-yyyy hh24:mi:ss.ff'), 9, 'dy_work_report_detail_name', '任务名称', 'dy_work_report_detail_name', 'd4abeebe-3fe2-43c8-bf69-f5c382c910d9');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('a672c9b1-1c7c-4dbd-babb-226c1f64dd50', to_timestamp('14-03-2013 17:19:36.310000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('14-03-2013 17:19:36.310000', 'dd-mm-yyyy hh24:mi:ss.ff'), 9, 'dy_work_report_detail_begin_time', '开始时间', 'dy_work_report_detail_begin_time', 'd4abeebe-3fe2-43c8-bf69-f5c382c910d9');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('145f57de-2586-4fee-ad93-c620b8c428c7', to_timestamp('14-03-2013 17:18:49.510000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('14-03-2013 17:18:49.510000', 'dd-mm-yyyy hh24:mi:ss.ff'), 9, 'dy_work_report_detail_end_time', '结束时间', 'dy_work_report_detail_end_time', 'd4abeebe-3fe2-43c8-bf69-f5c382c910d9');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('6043612a-c7dc-4142-a115-7abadbfab9c0', to_timestamp('14-03-2013 17:18:17.313000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('14-03-2013 17:18:17.313000', 'dd-mm-yyyy hh24:mi:ss.ff'), 9, 'dy_work_report_detail_target', '目标与要求', 'dy_work_report_detail_target', 'd4abeebe-3fe2-43c8-bf69-f5c382c910d9');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('3a7c9f42-8bfe-40ae-b7b4-226237fb4306', to_timestamp('14-03-2013 17:17:59.863000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('14-03-2013 17:17:59.863000', 'dd-mm-yyyy hh24:mi:ss.ff'), 9, 'dy_work_report_detail_state', '状态', 'dy_work_report_detail_state', 'd4abeebe-3fe2-43c8-bf69-f5c382c910d9');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('f0309707-6388-4a15-83a9-8724bfb2e2be', to_timestamp('21-03-2013 18:06:25.657000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('21-03-2013 18:06:25.657000', 'dd-mm-yyyy hh24:mi:ss.ff'), 9, 'dy_work_task_delay_type', '类型', 'dy_work_task_delay_type', 'baf041d7-503f-4021-be08-97ad77bd755c');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('c6f473c9-5b0d-43f7-b9b8-56eed6ab0b2b', null, null, 'U0010000001', to_timestamp('18-07-2014 10:23:45.776000', 'dd-mm-yyyy hh24:mi:ss.ff'), 4, '2', '证照', null, '2034504b-5972-4be7-8a50-30a18b08d1ae');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('2bfca8c4-9d2d-4d9d-8677-efe5329ed177', null, null, 'U0010000001', to_timestamp('18-07-2014 10:23:45.773000', 'dd-mm-yyyy hh24:mi:ss.ff'), 4, '3', '其他', null, '2034504b-5972-4be7-8a50-30a18b08d1ae');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('1cc0201d-f6b1-4a00-824c-86e477160602', to_timestamp('20-02-2013 10:55:26.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('28-02-2013 08:58:34.763000', 'dd-mm-yyyy hh24:mi:ss.ff'), 6, '011001', '字段映射', 'DY_FORM_FIELD_MAPPING', '212a7db7-8563-45fa-8e52-92dd53a2eff1');
commit;
prompt 200 records committed...
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('50d2449b-4405-431f-a57f-95cb1063cab4', null, null, 'U0010000001', to_timestamp('18-07-2014 10:23:07.666000', 'dd-mm-yyyy hh24:mi:ss.ff'), 4, '5', '其他', null, 'b9344d43-86df-4c14-857f-c1c9d460bbf8');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('2604f261-564b-4528-aa6c-022426d7b6db', to_timestamp('14-03-2013 15:01:11.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('08-05-2013 16:26:27.687000', 'dd-mm-yyyy hh24:mi:ss.ff'), 10, 'DY_EXCHANGE_FIELD_DOCNO', '文号', null, '5c6c164f-8b79-475c-956a-4dc5aeaf41aa');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('27d1b702-11a7-486b-bc5e-35f9c1a52173', to_timestamp('08-05-2013 16:29:17.817000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('08-05-2013 16:29:17.817000', 'dd-mm-yyyy hh24:mi:ss.ff'), 9, 'DY_EXCHANGE_FIELD_ATTACHMENT', '附件', null, '5c6c164f-8b79-475c-956a-4dc5aeaf41aa');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('1ade0b05-a8a5-4bb3-b6ce-e542a0a72436', to_timestamp('12-03-2013 15:12:51.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('13-07-2013 16:25:35.750000', 'dd-mm-yyyy hh24:mi:ss.ff'), 11, 'DY_EXCHANGE_FIELD_SENDEE_USER', '主送人', null, '5c6c164f-8b79-475c-956a-4dc5aeaf41aa');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('0bfa8cac-a99a-4216-9df0-21c20eaec7d1', to_timestamp('28-02-2013 14:10:53.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('08-05-2013 16:26:54.330000', 'dd-mm-yyyy hh24:mi:ss.ff'), 10, 'DY_EXCHANGE_FIELD_SECRET', '密级', null, '5c6c164f-8b79-475c-956a-4dc5aeaf41aa');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('11ea9cbe-cfb4-4443-a4cd-4389d9955316', to_timestamp('08-05-2013 16:26:02.650000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('08-05-2013 16:26:02.650000', 'dd-mm-yyyy hh24:mi:ss.ff'), 9, 'DY_EXCHANGE_FIELD_KEYWORD', '关键词', null, '5c6c164f-8b79-475c-956a-4dc5aeaf41aa');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('0c29ca7f-9718-4863-83f9-81c8fd21e7c7', to_timestamp('17-03-2013 08:13:40.877000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('17-03-2013 08:13:40.877000', 'dd-mm-yyyy hh24:mi:ss.ff'), 9, 'dy_work_task_resolve_assign_uuid', '任务uuid', 'dy_work_task_resolve_assign_uuid', '458dec95-0e31-4301-9791-6dc581eee834');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('f8d91b13-85f7-45cd-b65e-766b9388d36c', to_timestamp('03-03-2013 11:32:13.173000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('03-03-2013 11:32:13.173000', 'dd-mm-yyyy hh24:mi:ss.ff'), 9, 'dy_work_task_resolve_sharer', '共享者', 'dy_work_task_resolve_sharer', '458dec95-0e31-4301-9791-6dc581eee834');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('f89ec5eb-7ede-497c-a345-697732a1dc82', to_timestamp('04-04-2013 07:15:50.703000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('04-04-2013 07:15:50.703000', 'dd-mm-yyyy hh24:mi:ss.ff'), 10, 'dy_work_report_allcount', '任务总数', 'dy_work_report_allcount', '497e6dc9-82a2-483d-af48-910db6b86538');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('e8c811b7-6b76-4b96-8883-77b0c5f8301c', to_timestamp('09-04-2013 20:44:58.007000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('09-04-2013 20:44:58.007000', 'dd-mm-yyyy hh24:mi:ss.ff'), 10, 'dy_work_report_hege', '合格数', 'dy_work_report_hege', '497e6dc9-82a2-483d-af48-910db6b86538');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('558cc18f-668d-4fa3-9032-e52e5a11cad7', to_timestamp('04-04-2013 07:17:14.017000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('04-04-2013 07:17:14.017000', 'dd-mm-yyyy hh24:mi:ss.ff'), 10, 'dy_work_report_plan', '按计划数', 'dy_work_report_plan', '497e6dc9-82a2-483d-af48-910db6b86538');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('68c9d000-8b8a-4df6-9337-fb760b4436ee', to_timestamp('03-03-2013 09:57:35.920000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('03-03-2013 09:57:35.920000', 'dd-mm-yyyy hh24:mi:ss.ff'), 10, 'dy_work_report_type1', '报告类别', 'dy_work_report_type1', '497e6dc9-82a2-483d-af48-910db6b86538');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('513b0d09-5d37-4fb0-9b34-d85db2f348dd', to_timestamp('04-04-2013 07:16:17.953000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('04-04-2013 07:16:17.953000', 'dd-mm-yyyy hh24:mi:ss.ff'), 10, 'dy_work_report_delay', '延期数', 'dy_work_report_delay', '497e6dc9-82a2-483d-af48-910db6b86538');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('8cf4320c-ba28-4535-abf2-1e525b5e6745', to_timestamp('03-03-2013 09:57:59.513000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('03-03-2013 09:57:59.513000', 'dd-mm-yyyy hh24:mi:ss.ff'), 10, 'dy_work_report_type2', '报告进度', 'dy_work_report_type2', '497e6dc9-82a2-483d-af48-910db6b86538');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('7952c5c3-1935-479d-99d0-14a25a3ea175', to_timestamp('04-04-2013 07:16:48.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('09-04-2013 20:44:22.583000', 'dd-mm-yyyy hh24:mi:ss.ff'), 11, 'dy_work_report_good', '优秀数', 'dy_work_report_good', '497e6dc9-82a2-483d-af48-910db6b86538');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('a6b6450f-7825-4345-8be5-f315b8925f2e', to_timestamp('04-04-2013 07:17:26.767000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('04-04-2013 07:17:26.767000', 'dd-mm-yyyy hh24:mi:ss.ff'), 10, 'dy_work_report_unpass', '不合格数', 'dy_work_report_unpass', '497e6dc9-82a2-483d-af48-910db6b86538');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('b3eca037-6dd9-4b1c-b40f-9c87ad2cccec', to_timestamp('03-03-2013 10:28:49.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('03-03-2013 10:29:01.283000', 'dd-mm-yyyy hh24:mi:ss.ff'), 10, 'dy_work_task_assign_name', '任务名称', 'dy_work_task_assign_', '4abbacc4-00d7-4f01-9d89-c30414aaaa97');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('c4fa3522-8690-4c8c-b7f0-da800c817d85', to_timestamp('03-03-2013 10:30:33.300000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('03-03-2013 10:30:33.300000', 'dd-mm-yyyy hh24:mi:ss.ff'), 9, 'dy_work_task_assign_supervisior', '督办人', 'dy_work_task_assign_supervisior', '4abbacc4-00d7-4f01-9d89-c30414aaaa97');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('76aff2f2-5ffc-4068-9ddd-172d330f26c6', to_timestamp('03-03-2013 10:30:14.990000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('03-03-2013 10:30:14.990000', 'dd-mm-yyyy hh24:mi:ss.ff'), 9, 'dy_work_task_assign_dutyman', '责任人', 'dy_work_task_assign_dutyman', '4abbacc4-00d7-4f01-9d89-c30414aaaa97');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('851a37b4-e30d-443b-9a34-759c40a8c2c7', to_timestamp('03-03-2013 10:31:18.917000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('03-03-2013 10:31:18.917000', 'dd-mm-yyyy hh24:mi:ss.ff'), 9, 'dy_work_task_assign_begintime', '计划开始时间', 'dy_work_task_assign_begintime', '4abbacc4-00d7-4f01-9d89-c30414aaaa97');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('8d6cda1e-eaac-4eb0-b3a3-0fbb5326079a', to_timestamp('03-03-2013 10:29:20.430000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('03-03-2013 10:29:20.430000', 'dd-mm-yyyy hh24:mi:ss.ff'), 9, 'dy_work_task_assign_type', '任务类型', 'dy_work_task_assign_type', '4abbacc4-00d7-4f01-9d89-c30414aaaa97');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('999db47d-7a4c-4640-ae67-e061905b4bf3', to_timestamp('03-03-2013 10:32:00.243000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('03-03-2013 10:32:00.243000', 'dd-mm-yyyy hh24:mi:ss.ff'), 9, 'dy_work_task_assign_count', '预计工作量', 'dy_work_task_assign_count', '4abbacc4-00d7-4f01-9d89-c30414aaaa97');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('439e39a4-6786-4445-9af9-3a1ad257257d', to_timestamp('03-03-2013 10:31:35.310000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('03-03-2013 10:31:35.310000', 'dd-mm-yyyy hh24:mi:ss.ff'), 9, 'dy_work_task_assign_endtime', '计划完成时间', 'dy_work_task_assign_endtime', '4abbacc4-00d7-4f01-9d89-c30414aaaa97');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('1fda8fcc-df49-42a1-9c25-77b3e57f7209', to_timestamp('03-03-2013 10:29:48.147000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('03-03-2013 10:29:48.147000', 'dd-mm-yyyy hh24:mi:ss.ff'), 9, 'dy_work_task_assign_target', '目标与要求', 'dy_work_task_assign_target', '4abbacc4-00d7-4f01-9d89-c30414aaaa97');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('f8c61ea8-b3e0-4805-b8d0-dd22a2fbd858', to_timestamp('03-03-2013 10:30:56.723000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('03-03-2013 10:30:56.723000', 'dd-mm-yyyy hh24:mi:ss.ff'), 9, 'dy_work_task_assign_sharer', '共享人', 'dy_work_task_assign_sharer', '4abbacc4-00d7-4f01-9d89-c30414aaaa97');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('e4d9ffa9-0226-4d1e-8361-870c9c500d4a', to_timestamp('01-03-2013 12:03:15.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('01-03-2013 16:18:10.117000', 'dd-mm-yyyy hh24:mi:ss.ff'), 10, 'dy_work_plan_detail_sharer', '共享者', 'dy_work_plan_detail_sharer', '520434c2-827e-4d0c-9ae8-c21a6fb522c8');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('20bea7f9-7985-4e0d-bfe7-3c9336d2c2ee', to_timestamp('01-03-2013 11:03:33.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('01-03-2013 11:06:15.413000', 'dd-mm-yyyy hh24:mi:ss.ff'), 10, 'dy_work_plan_detail_type', '任务类型', 'dy_work_plan_detail_type', '520434c2-827e-4d0c-9ae8-c21a6fb522c8');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('34ef78c9-ae4a-4fb0-a1e0-151ed13062c9', to_timestamp('01-03-2013 12:02:51.927000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('01-03-2013 12:02:51.927000', 'dd-mm-yyyy hh24:mi:ss.ff'), 9, 'dy_work_plan_detail_workcount', '预估工作量', 'dy_work_plan_detail_workcount', '520434c2-827e-4d0c-9ae8-c21a6fb522c8');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('9fb4e336-fc8f-4062-932b-c02b74ac2aa3', to_timestamp('01-03-2013 11:21:57.727000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('01-03-2013 11:21:57.727000', 'dd-mm-yyyy hh24:mi:ss.ff'), 9, 'dy_work_plan_detail_target', '目标与要求', 'dy_work_plan_detail_target', '520434c2-827e-4d0c-9ae8-c21a6fb522c8');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('77780740-b1c6-4c70-aff9-f081f46e3e8a', to_timestamp('01-03-2013 11:25:21.983000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('01-03-2013 11:25:21.983000', 'dd-mm-yyyy hh24:mi:ss.ff'), 9, 'dy_work_plan_detail_endtime', '计划完成时间', 'dy_work_plan_detail_endtime', '520434c2-827e-4d0c-9ae8-c21a6fb522c8');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('76adf78a-c5a9-4e95-a15d-adff340723d5', to_timestamp('01-03-2013 11:22:21.010000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('01-03-2013 11:22:21.010000', 'dd-mm-yyyy hh24:mi:ss.ff'), 9, 'dy_work_plan_begin_time', '计划开始时间', 'dy_work_plan_begin_time', '520434c2-827e-4d0c-9ae8-c21a6fb522c8');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('72970b72-cd1c-49eb-a034-852acc68a05b', to_timestamp('01-03-2013 11:03:04.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('01-03-2013 18:50:53.757000', 'dd-mm-yyyy hh24:mi:ss.ff'), 11, 'dy_work_plan_detail_name', '任务名称', 'dy_work_plan_detail_name', '520434c2-827e-4d0c-9ae8-c21a6fb522c8');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('bd274cb3-1549-46bf-8e42-91fb753f4b0a', to_timestamp('01-03-2013 11:07:00.673000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('01-03-2013 11:07:00.673000', 'dd-mm-yyyy hh24:mi:ss.ff'), 9, 'dy_work_plan_detail_state', '任务状态', 'dy_work_plan_detail_state', '520434c2-827e-4d0c-9ae8-c21a6fb522c8');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('bdd4dbd5-f454-4499-b290-edf4e85429e3', to_timestamp('21-08-2013 10:05:43.653000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('21-08-2013 10:05:43.653000', 'dd-mm-yyyy hh24:mi:ss.ff'), 6, 'employ_endtime', '时间占用结束时间', null, '5233ad4f-c5a0-4825-808a-f7cab6ef355a');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('d42bcd06-7862-4a1e-9359-bd0d56702fdb', to_timestamp('21-08-2013 10:10:15.290000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('21-08-2013 10:10:15.290000', 'dd-mm-yyyy hh24:mi:ss.ff'), 6, 'resource_type', '占用的资源类别', null, '5233ad4f-c5a0-4825-808a-f7cab6ef355a');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('d9032c70-7989-441b-bb54-8728f0e7f7ad', to_timestamp('29-08-2013 16:35:26.400000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('29-08-2013 16:35:26.400000', 'dd-mm-yyyy hh24:mi:ss.ff'), 6, 'employ_name_car', '资源名称（车辆）', null, '5233ad4f-c5a0-4825-808a-f7cab6ef355a');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('d7329b3c-66eb-40c9-9ec9-d3f997c0216c', to_timestamp('29-08-2013 16:36:28.873000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('29-08-2013 16:36:28.873000', 'dd-mm-yyyy hh24:mi:ss.ff'), 6, 'employ_name_driver', '资源名称（司机）', null, '5233ad4f-c5a0-4825-808a-f7cab6ef355a');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('22b1f7de-e06f-416c-9f3d-3f1e5c25b3df', to_timestamp('20-08-2013 10:07:20.497000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('20-08-2013 10:07:20.497000', 'dd-mm-yyyy hh24:mi:ss.ff'), 1, '003004', '资源类别', 'RESOURCE_TYPE', 'fdf8472f-eced-4594-b829-9f890a41f5cc');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('3b7fe289-e867-4a4c-a853-614d17bd73c7', to_timestamp('20-02-2013 12:12:32.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('20-02-2013 12:12:46.240000', 'dd-mm-yyyy hh24:mi:ss.ff'), 3, '005003', '工作评价', 'work_evaluate', 'de8cd83a-dcd0-4bfa-9b33-e0555ff40e8c');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('55e8b62f-0643-4bf3-8e12-0360ddc5a294', to_timestamp('25-03-2013 10:29:14.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('25-03-2013 13:42:02.983000', 'dd-mm-yyyy hh24:mi:ss.ff'), 3, '011002', '表ID映射', 'DY_FORM_ID_MAPPING', '212a7db7-8563-45fa-8e52-92dd53a2eff1');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('60c33986-409a-485a-9f53-04b648e0355d', to_timestamp('13-05-2013 14:10:06.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('24-09-2013 12:04:08.483000', 'dd-mm-yyyy hh24:mi:ss.ff'), 2, '012002', '页面主题', 'PAGE_THEME', 'b6285038-465c-4e68-9f1f-bf7ac75957d6');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('64736d54-1bc1-45a7-a638-3cac021d34e7', to_timestamp('03-04-2013 11:33:44.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('02-05-2013 20:01:13.353000', 'dd-mm-yyyy hh24:mi:ss.ff'), 4, '006001', '动态表单', 'file_dynamic_table_type', 'd380225e-77ff-4e51-9b77-55b5b57f7a24');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('6991af23-c3dd-4d69-8661-d5303fa4e34f', null, null, 'U0000000001', to_timestamp('29-09-2013 19:31:26.903000', 'dd-mm-yyyy hh24:mi:ss.ff'), 2, '003005', '人员去向', 'user_trace', 'fdf8472f-eced-4594-b829-9f890a41f5cc');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('6f3c6d08-8a57-40c6-8808-edd2daf59b87', to_timestamp('14-04-2013 11:16:33.877000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('14-04-2013 11:16:33.877000', 'dd-mm-yyyy hh24:mi:ss.ff'), 1, '003002', '业务类型', 'BUSINESS_TYPE', 'fdf8472f-eced-4594-b829-9f890a41f5cc');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('7e1223cc-3af2-464f-b563-2f01cbeb6943', to_timestamp('13-05-2013 14:07:12.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('16-08-2013 14:20:55.077000', 'dd-mm-yyyy hh24:mi:ss.ff'), 3, '012001', '功能模块分类', 'MODULE_CATE', 'b6285038-465c-4e68-9f1f-bf7ac75957d6');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('a2afe3cb-4002-4be1-af1e-d08b0ec122b0', to_timestamp('22-04-2013 17:46:27.240000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('22-04-2013 17:46:27.240000', 'dd-mm-yyyy hh24:mi:ss.ff'), 1, '003003', '数据权限', 'DATA_PERMISSION', 'fdf8472f-eced-4594-b829-9f890a41f5cc');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('c308a62b-2965-4ee5-bf53-de62f971970b', null, null, 'U0000000001', to_timestamp('18-02-2013 11:12:04.147000', 'dd-mm-yyyy hh24:mi:ss.ff'), 1, '001002', '组织机构', 'PT_ORG', '0697ef08-6be4-49e8-aac0-79b68d49b531');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('c53e0678-972d-4142-8385-1b123dd961b9', to_timestamp('20-03-2013 21:07:34.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('26-03-2013 01:47:02.657000', 'dd-mm-yyyy hh24:mi:ss.ff'), 20, '002001', '动态按钮', 'SECURITY_DYBTN', 'e22e945b-bb5c-42d3-804c-71e131e44031');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('c6b816e3-557b-4a83-8fdc-81d71718be11', to_timestamp('22-04-2013 18:27:44.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0010000001', to_timestamp('23-07-2014 17:33:53.005000', 'dd-mm-yyyy hh24:mi:ss.ff'), 5, '003004', '模块ID', 'MODULE_ID', 'fdf8472f-eced-4594-b829-9f890a41f5cc');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('c7a86aba-e746-4c76-b1ba-1dfc6e86d4b4', null, null, 'U0000000001', to_timestamp('21-06-2013 16:58:18.200000', 'dd-mm-yyyy hh24:mi:ss.ff'), 2, '001003', '公共基础', 'PT_COMMON', '0697ef08-6be4-49e8-aac0-79b68d49b531');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('dbe69428-87d8-428c-b60e-3d220aa0a8c4', to_timestamp('02-04-2013 19:56:24.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('02-04-2013 19:56:33.547000', 'dd-mm-yyyy hh24:mi:ss.ff'), 2, '003001', '视图自定义', null, 'fdf8472f-eced-4594-b829-9f890a41f5cc');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('e2baddfe-9dd2-4447-8230-1a18be361146', to_timestamp('18-02-2013 11:34:03.680000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('18-02-2013 11:34:03.680000', 'dd-mm-yyyy hh24:mi:ss.ff'), 2, '005002', '工作状态', 'work_state', 'de8cd83a-dcd0-4bfa-9b33-e0555ff40e8c');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('e883c5b5-a42d-41d9-ad59-7d1296cdb136', to_timestamp('06-08-2013 21:03:54.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('06-08-2013 21:04:36.217000', 'dd-mm-yyyy hh24:mi:ss.ff'), 1, '012005', '按钮组别', 'BUTTON_GROUP', 'b6285038-465c-4e68-9f1f-bf7ac75957d6');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('e9389c1c-862d-4e21-aed4-01cb43a0cb36', to_timestamp('04-07-2013 09:58:45.920000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('04-07-2013 09:58:45.920000', 'dd-mm-yyyy hh24:mi:ss.ff'), 1, '013001', '会议室', 'MEET_ROOM', '7c1f3b64-b4d4-4442-bcba-3649cec8f0ab');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('ee676e8a-25e1-431a-90da-144ecd64b9ff', to_timestamp('18-02-2013 11:33:29.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0010000001', to_timestamp('03-01-2014 16:43:47.960000', 'dd-mm-yyyy hh24:mi:ss.ff'), 3, '005001', '工作类别', 'work_type', 'de8cd83a-dcd0-4bfa-9b33-e0555ff40e8c');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('f3c28ab1-789c-4499-bbd1-9a2cf87677c1', to_timestamp('14-05-2013 21:27:44.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('24-09-2013 14:56:21.637000', 'dd-mm-yyyy hh24:mi:ss.ff'), 5, '012004', '流程分类', null, 'b6285038-465c-4e68-9f1f-bf7ac75957d6');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('28f89f7c-64b9-4f1f-904c-cc7345f6bba9', to_timestamp('26-04-2013 10:28:04.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('26-04-2013 10:29:18.677000', 'dd-mm-yyyy hh24:mi:ss.ff'), 7, 'File_title', '标题', 'File_title', 'c1dede19-dc63-4a54-a746-f21c360ec94e');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('29daf113-90ad-4c68-b814-b8cb914c1e2d', to_timestamp('26-04-2013 10:30:09.077000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('26-04-2013 10:30:09.077000', 'dd-mm-yyyy hh24:mi:ss.ff'), 5, 'File_readFile', '可以阅读文档的人 ', null, 'c1dede19-dc63-4a54-a746-f21c360ec94e');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('06385f25-ab9b-4580-a3c3-a610e1be3116', to_timestamp('21-08-2013 11:05:59.493000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('21-08-2013 11:05:59.493000', 'dd-mm-yyyy hh24:mi:ss.ff'), 5, 'File_reservedText3', '预留文本字段3(64字符长度)', null, 'c1dede19-dc63-4a54-a746-f21c360ec94e');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('17ad4ea4-e7c3-42d4-b207-7e59844da78d', to_timestamp('21-08-2013 11:09:17.857000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('21-08-2013 11:09:17.857000', 'dd-mm-yyyy hh24:mi:ss.ff'), 5, 'File_reservedDate2', '预留日期字段2', null, 'c1dede19-dc63-4a54-a746-f21c360ec94e');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('181d204a-ed17-4c39-bb69-f35fa91ef7cb', to_timestamp('21-08-2013 11:08:10.423000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('21-08-2013 11:08:10.423000', 'dd-mm-yyyy hh24:mi:ss.ff'), 5, 'File_reservedNumber2', '预留数字字段2(浮点型)', null, 'c1dede19-dc63-4a54-a746-f21c360ec94e');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('4352b9cd-46da-4d4a-8978-8a4e60518f02', to_timestamp('21-08-2013 11:06:29.413000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('21-08-2013 11:06:29.413000', 'dd-mm-yyyy hh24:mi:ss.ff'), 5, 'File_reservedText4', '预留文本字段4(255字符长度)', null, 'c1dede19-dc63-4a54-a746-f21c360ec94e');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('372fc53e-6ba3-448c-87a0-4082297ba08e', to_timestamp('21-08-2013 11:08:19.933000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('21-08-2013 11:08:19.933000', 'dd-mm-yyyy hh24:mi:ss.ff'), 5, 'File_reservedNumber3', '预留数字字段3(浮点型)', null, 'c1dede19-dc63-4a54-a746-f21c360ec94e');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('2cd6e01b-f11f-4089-acbf-ec0fe2eaaad2', to_timestamp('21-08-2013 11:05:35.050000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('21-08-2013 11:05:35.050000', 'dd-mm-yyyy hh24:mi:ss.ff'), 5, 'File_reservedText2', '预留文本字段2(64字符长度)', null, 'c1dede19-dc63-4a54-a746-f21c360ec94e');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('dd2be4ee-dabe-4e32-946b-9e3fdeb57d17', to_timestamp('21-08-2013 11:07:27.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('21-08-2013 11:10:02.303000', 'dd-mm-yyyy hh24:mi:ss.ff'), 6, 'File_reservedNumber1', '预留数字字段1(整型)', null, 'c1dede19-dc63-4a54-a746-f21c360ec94e');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('8fdaee95-def3-4d24-8b16-0a72a3fe3bf3', to_timestamp('28-02-2013 14:05:59.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('08-05-2013 16:26:42.890000', 'dd-mm-yyyy hh24:mi:ss.ff'), 11, 'DY_EXCHANGE_FIELD_URGENT', '缓急', null, '5c6c164f-8b79-475c-956a-4dc5aeaf41aa');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('75400314-e60f-4bdc-933c-542f1c5b9ca7', to_timestamp('12-03-2013 15:12:28.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('13-07-2013 16:25:51.877000', 'dd-mm-yyyy hh24:mi:ss.ff'), 11, 'DY_EXCHANGE_FIELD_SENDEE_UNIT', '主送单位', null, '5c6c164f-8b79-475c-956a-4dc5aeaf41aa');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('7d178fe2-1a38-4476-9123-f7fb6db5b61f', to_timestamp('28-02-2013 14:05:27.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('08-05-2013 16:25:41.513000', 'dd-mm-yyyy hh24:mi:ss.ff'), 11, 'DY_EXCHANGE_FIELD_TITLE', '标题', null, '5c6c164f-8b79-475c-956a-4dc5aeaf41aa');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('bec8139e-7fdf-41a4-af81-a6e7840078fc', to_timestamp('13-07-2013 16:28:50.670000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('13-07-2013 16:28:50.670000', 'dd-mm-yyyy hh24:mi:ss.ff'), 9, 'DY_EXCHANGE_FIELD_OPINION', '操作意见', null, '5c6c164f-8b79-475c-956a-4dc5aeaf41aa');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('bc9756db-f02e-48c9-a4ed-7eaab3be73da', to_timestamp('25-03-2013 15:22:19.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('13-07-2013 16:26:44.890000', 'dd-mm-yyyy hh24:mi:ss.ff'), 11, 'DY_EXCHANGE_FIELD_SENDEE_OUTUNIT', '主送单位(外部)', null, '5c6c164f-8b79-475c-956a-4dc5aeaf41aa');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('b38cbdae-4983-434f-908b-1ddaf2c248f2', to_timestamp('12-03-2013 15:11:11.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('08-05-2013 16:27:13.357000', 'dd-mm-yyyy hh24:mi:ss.ff'), 10, 'DY_EXCHANGE_FIELD_SIGN_LIMIT', '签收时限', null, '5c6c164f-8b79-475c-956a-4dc5aeaf41aa');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('af0743eb-7db1-49ba-bc37-b9df87dd9956', to_timestamp('12-03-2013 15:13:53.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('08-05-2013 16:29:01.237000', 'dd-mm-yyyy hh24:mi:ss.ff'), 10, 'DY_EXCHANGE_FIELD_SUPERVISE_USER', '督办人', null, '5c6c164f-8b79-475c-956a-4dc5aeaf41aa');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('3b174cb8-185d-4875-9ce1-efd97f54b90f', to_timestamp('12-03-2013 15:11:42.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('08-05-2013 16:27:26.090000', 'dd-mm-yyyy hh24:mi:ss.ff'), 10, 'DY_EXCHANGE_FIELD_FEEDBACK_LIMIT', '反馈时限', null, '5c6c164f-8b79-475c-956a-4dc5aeaf41aa');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('29bc0157-1ed4-46db-bbb9-0f3b91a089b6', to_timestamp('06-05-2013 16:14:21.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('08-05-2013 16:28:47.080000', 'dd-mm-yyyy hh24:mi:ss.ff'), 10, 'DY_EXCHANGE_FIELD_COPY_USER', '抄送人', null, '5c6c164f-8b79-475c-956a-4dc5aeaf41aa');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('245a49aa-3ef3-4e6b-84c1-6f9257b296a2', to_timestamp('12-03-2013 15:12:07.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('13-07-2013 16:27:17.267000', 'dd-mm-yyyy hh24:mi:ss.ff'), 12, 'DY_EXCHANGE_FIELD_SENDER_USER', '发送人', null, '5c6c164f-8b79-475c-956a-4dc5aeaf41aa');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('250e93e8-615a-487b-a3a6-d680fdf7c448', to_timestamp('06-05-2013 16:14:44.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('08-05-2013 16:28:35.787000', 'dd-mm-yyyy hh24:mi:ss.ff'), 10, 'DY_EXCHANGE_FIELD_COPY_UNIT', '抄送单位', null, '5c6c164f-8b79-475c-956a-4dc5aeaf41aa');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('0697ef08-6be4-49e8-aac0-79b68d49b531', null, null, 'U0000000001', to_timestamp('05-11-2013 16:31:50.850000', 'dd-mm-yyyy hh24:mi:ss.ff'), 23, '001', '组织机构', 'ORG', null);
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('212a7db7-8563-45fa-8e52-92dd53a2eff1', to_timestamp('20-02-2013 10:06:07.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('20-02-2013 10:11:31.517000', 'dd-mm-yyyy hh24:mi:ss.ff'), 1, '011', '动态表单', null, null);
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('7c1f3b64-b4d4-4442-bcba-3649cec8f0ab', to_timestamp('04-07-2013 09:57:48.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('29-08-2013 15:25:54.870000', 'dd-mm-yyyy hh24:mi:ss.ff'), 2, '013', '会议管理', null, null);
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('b6285038-465c-4e68-9f1f-bf7ac75957d6', to_timestamp('11-05-2013 15:45:34.893000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('11-05-2013 15:45:34.893000', 'dd-mm-yyyy hh24:mi:ss.ff'), 0, '012', '内容管理', 'CMS', null);
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('d380225e-77ff-4e51-9b77-55b5b57f7a24', to_timestamp('03-04-2013 11:33:03.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('03-04-2013 11:33:13.077000', 'dd-mm-yyyy hh24:mi:ss.ff'), 1, '006', '文件管理', 'FILE_MAMAGER', null);
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('de8cd83a-dcd0-4bfa-9b33-e0555ff40e8c', null, null, 'U0010000001', to_timestamp('03-01-2014 16:44:44.075000', 'dd-mm-yyyy hh24:mi:ss.ff'), 4, '005', '工作计划管理', null, null);
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('e22e945b-bb5c-42d3-804c-71e131e44031', null, null, 'U0000000001', to_timestamp('23-09-2013 17:27:51.717000', 'dd-mm-yyyy hh24:mi:ss.ff'), 19, '002', '权限管理', 'SECURITY', null);
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('fdf8472f-eced-4594-b829-9f890a41f5cc', to_timestamp('02-04-2013 19:55:25.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('29-09-2013 19:31:26.897000', 'dd-mm-yyyy hh24:mi:ss.ff'), 2, '003', '基础数据', 'BASIC_DATA', null);
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('b51f0ce4-79cf-45eb-bc41-9e36f8087b19', to_timestamp('04-07-2013 10:01:43.067000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('04-07-2013 10:01:43.067000', 'dd-mm-yyyy hh24:mi:ss.ff'), 1, 'small_meetroom', '小会议室', 'small_meetroom', 'e9389c1c-862d-4e21-aed4-01cb43a0cb36');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('eb234c45-c841-4c53-8c20-d983c4a9e8a1', to_timestamp('18-02-2013 11:38:04.767000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('18-02-2013 11:38:04.767000', 'dd-mm-yyyy hh24:mi:ss.ff'), 2, '005001001', '重要工作', 'work_type_important', 'ee676e8a-25e1-431a-90da-144ecd64b9ff');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('4ac0c780-1986-4605-8cf1-3b4c85f972b7', to_timestamp('18-02-2013 11:38:43.967000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('18-02-2013 11:38:43.967000', 'dd-mm-yyyy hh24:mi:ss.ff'), 2, '005001003', '临时工作', 'work_type_temp', 'ee676e8a-25e1-431a-90da-144ecd64b9ff');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('5ea88183-348a-490d-b1b7-4b86cd30778d', to_timestamp('18-02-2013 11:38:22.440000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('18-02-2013 11:38:22.440000', 'dd-mm-yyyy hh24:mi:ss.ff'), 2, '005001002', '日常工作', 'work_type_daily', 'ee676e8a-25e1-431a-90da-144ecd64b9ff');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('30a1cada-5daa-4767-af44-f04bf8382206', to_timestamp('14-05-2013 21:29:30.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('16-05-2013 16:09:14.627000', 'dd-mm-yyyy hh24:mi:ss.ff'), 2, '012004005', '办结工作', null, 'f3c28ab1-789c-4499-bbd1-9a2cf87677c1');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('161c6997-f538-456e-a85f-8c3e7196b582', to_timestamp('14-05-2013 21:27:59.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('15-05-2013 14:38:19.657000', 'dd-mm-yyyy hh24:mi:ss.ff'), 1, '012004001', '新建流程', null, 'f3c28ab1-789c-4499-bbd1-9a2cf87677c1');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('266a5185-b56c-49f4-906c-73711b6b4321', to_timestamp('14-05-2013 21:28:58.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('16-05-2013 16:05:53.127000', 'dd-mm-yyyy hh24:mi:ss.ff'), 4, '012004004', '已办工作', null, 'f3c28ab1-789c-4499-bbd1-9a2cf87677c1');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('7bebd77c-6012-4b3e-b8e1-afedc24a8982', to_timestamp('14-05-2013 21:30:40.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('16-05-2013 16:07:25.780000', 'dd-mm-yyyy hh24:mi:ss.ff'), 1, '012004008', '已阅工作', null, 'f3c28ab1-789c-4499-bbd1-9a2cf87677c1');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('92f0e344-a056-4ba0-b09b-d94d65d3b494', to_timestamp('14-05-2013 21:28:35.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('15-05-2013 19:49:16.877000', 'dd-mm-yyyy hh24:mi:ss.ff'), 3, '012004003', '待办工作', null, 'f3c28ab1-789c-4499-bbd1-9a2cf87677c1');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('8d684570-97e3-4e98-b0bd-70128c3d3e91', to_timestamp('14-05-2013 21:29:46.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('16-05-2013 16:06:59.390000', 'dd-mm-yyyy hh24:mi:ss.ff'), 1, '012004006', '工作草稿', null, 'f3c28ab1-789c-4499-bbd1-9a2cf87677c1');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('c0d40b88-ea9a-4f02-b804-67ca28e877c8', to_timestamp('14-05-2013 21:30:07.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('16-05-2013 16:07:50.063000', 'dd-mm-yyyy hh24:mi:ss.ff'), 1, '012004007', '未阅工作', null, 'f3c28ab1-789c-4499-bbd1-9a2cf87677c1');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('b1b972f9-bc46-46e3-9dde-eabdacbd0738', to_timestamp('14-05-2013 21:28:22.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('16-05-2013 16:11:33.187000', 'dd-mm-yyyy hh24:mi:ss.ff'), 2, '012004002', '我关注', null, 'f3c28ab1-789c-4499-bbd1-9a2cf87677c1');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('6db1a9e7-f21d-469f-b379-56461eb123be', to_timestamp('28-02-2013 19:47:07.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('16-05-2013 09:34:57.213000', 'dd-mm-yyyy hh24:mi:ss.ff'), 11, 'dy_work_plan_sharer', '共享者', 'dy_work_plan_sharer', 'ec8ef157-5bec-494f-9f31-fa5cf9f4c3a2');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('88edbd89-09da-4003-b485-ec42c369da7a', to_timestamp('01-03-2013 14:00:25.403000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('01-03-2013 14:00:25.403000', 'dd-mm-yyyy hh24:mi:ss.ff'), 9, 'dy_work_plan_type1', '计划类型', 'dy_work_plan_type1', 'ec8ef157-5bec-494f-9f31-fa5cf9f4c3a2');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('0ed353fd-3b83-4254-9f35-fc42ef86df17', to_timestamp('28-02-2013 19:48:21.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('01-03-2013 13:59:16.430000', 'dd-mm-yyyy hh24:mi:ss.ff'), 10, 'dy_work_plan_supervisior', '监督人', 'dy_work_plan_supervisior', 'ec8ef157-5bec-494f-9f31-fa5cf9f4c3a2');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('356d48f5-14be-4e5a-bb62-663437d536e3', to_timestamp('20-02-2013 11:01:27.263000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('20-02-2013 11:01:27.263000', 'dd-mm-yyyy hh24:mi:ss.ff'), 5, 'Plan_ZRZ', '责任者', null, 'ec8ef157-5bec-494f-9f31-fa5cf9f4c3a2');
commit;
prompt 300 records committed...
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('eb34a403-ebed-42be-bb36-798694dac605', to_timestamp('01-03-2013 14:00:53.570000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('01-03-2013 14:00:53.570000', 'dd-mm-yyyy hh24:mi:ss.ff'), 9, 'dy_work_plan_type2', '工作计划进度', 'dy_work_plan_type2', 'ec8ef157-5bec-494f-9f31-fa5cf9f4c3a2');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('630dd09f-cd43-454f-9b5a-f0ed1da131b6', to_timestamp('14-03-2013 17:22:41.927000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('14-03-2013 17:22:41.927000', 'dd-mm-yyyy hh24:mi:ss.ff'), 9, 'dy_work_report_evaluate_name', '名称', 'dy_work_report_evaluate_name', '3f9a833c-115a-4b06-b4ae-f13851bf42b6');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('967db461-2d34-4885-beaf-1440c55e6cbf', to_timestamp('14-03-2013 17:22:58.273000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('14-03-2013 17:22:58.273000', 'dd-mm-yyyy hh24:mi:ss.ff'), 9, 'dy_work_report_evaluate_state', '状态', 'dy_work_report_evaluate_state', '3f9a833c-115a-4b06-b4ae-f13851bf42b6');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('f891aa30-435a-4b09-8cf6-8720fe680ac5', to_timestamp('28-11-2013 19:57:15.193000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('28-11-2013 19:57:15.193000', 'dd-mm-yyyy hh24:mi:ss.ff'), 7, 'exchangeData_reservedText3', '预留文本字段3', null, 'c85962cd-0fa7-46be-99b3-7d4a7852e9dd');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('adc74774-0e20-4a2f-9b66-3b64e61731cf', to_timestamp('28-11-2013 19:56:49.800000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('28-11-2013 19:56:49.800000', 'dd-mm-yyyy hh24:mi:ss.ff'), 7, 'exchangeData_reservedText2', '预留文本字段2', null, 'c85962cd-0fa7-46be-99b3-7d4a7852e9dd');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('d343ac69-3a2f-462c-a7af-6b187b1734e4', to_timestamp('28-11-2013 19:55:34.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('28-11-2013 19:56:18.787000', 'dd-mm-yyyy hh24:mi:ss.ff'), 8, 'exchangeData_reservedText1', '预留文本字段1', null, 'c85962cd-0fa7-46be-99b3-7d4a7852e9dd');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('9a8519f9-6409-4a43-b873-2636eee8dfdb', to_timestamp('28-11-2013 19:58:42.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0010000001', to_timestamp('10-12-2013 10:48:07.375000', 'dd-mm-yyyy hh24:mi:ss.ff'), 9, 'exchangeData_reservedNumber2', '预留文本字段5', null, 'c85962cd-0fa7-46be-99b3-7d4a7852e9dd');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('9c9ae391-320e-41ea-b206-0bd2f241ed90', to_timestamp('28-11-2013 19:57:58.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0010000001', to_timestamp('10-12-2013 10:47:54.875000', 'dd-mm-yyyy hh24:mi:ss.ff'), 8, 'exchangeData_reservedNumber1', '预留文本字段4', null, 'c85962cd-0fa7-46be-99b3-7d4a7852e9dd');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('0ffc59b4-5a15-4d89-a90f-15d93d9a345b', to_timestamp('03-03-2013 13:20:44.853000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('03-03-2013 13:20:44.853000', 'dd-mm-yyyy hh24:mi:ss.ff'), 9, 'dy_meeting_summary_detail_duty', '责任人', 'dy_meeting_summary_detail_duty', 'd4137060-b297-4f4e-82ae-fc88599d9d76');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('2ade4da9-41de-439e-b725-82af28581543', to_timestamp('03-03-2013 13:18:12.207000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('03-03-2013 13:18:12.207000', 'dd-mm-yyyy hh24:mi:ss.ff'), 9, 'dy_meeting_summary_detail_begintime', '计划开始时间', 'dy_meeting_summary_detail_begintime', 'd4137060-b297-4f4e-82ae-fc88599d9d76');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('9d029e0e-3274-4aaf-be03-341af7594f7b', to_timestamp('03-04-2013 11:36:43.233000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('03-04-2013 11:36:43.233000', 'dd-mm-yyyy hh24:mi:ss.ff'), 2, 'public', '公有库', null, '1288f87a-5035-4226-8cdc-330919f15041');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('373d11da-727e-416c-9f6d-61fb1e07b14c', to_timestamp('03-04-2013 11:37:13.813000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('03-04-2013 11:37:13.813000', 'dd-mm-yyyy hh24:mi:ss.ff'), 2, 'private', '私有库', null, '1288f87a-5035-4226-8cdc-330919f15041');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('7c5e5d64-1bb3-4b85-8620-490d77cf7c1c', to_timestamp('16-05-2013 13:51:12.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('31-05-2013 14:21:54.070000', 'dd-mm-yyyy hh24:mi:ss.ff'), 2, '012003002', '窗口主题二', null, '14e9b29d-b1ba-4b89-a62c-95af2ef7cf2a');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('d9ca5569-4772-4f59-9171-8e384502214e', to_timestamp('13-05-2013 14:22:10.547000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('13-05-2013 14:22:10.547000', 'dd-mm-yyyy hh24:mi:ss.ff'), 0, '012003001', '窗口主题一', null, '14e9b29d-b1ba-4b89-a62c-95af2ef7cf2a');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('d4137060-b297-4f4e-82ae-fc88599d9d76', to_timestamp('03-03-2013 13:17:07.330000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('03-03-2013 13:17:07.330000', 'dd-mm-yyyy hh24:mi:ss.ff'), 9, '011001010', '会议纪要', null, '1cc0201d-f6b1-4a00-824c-86e477160602');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('d4abeebe-3fe2-43c8-bf69-f5c382c910d9', to_timestamp('14-03-2013 17:16:39.563000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('14-03-2013 17:16:39.563000', 'dd-mm-yyyy hh24:mi:ss.ff'), 9, '011001012', '工作报告之工作计划', null, '1cc0201d-f6b1-4a00-824c-86e477160602');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('c85962cd-0fa7-46be-99b3-7d4a7852e9dd', to_timestamp('28-11-2013 19:53:14.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('28-11-2013 19:54:33.957000', 'dd-mm-yyyy hh24:mi:ss.ff'), 7, '011001012', '数据交换', null, '1cc0201d-f6b1-4a00-824c-86e477160602');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('e2eeba8a-24c4-4e27-aa7d-8c0078af9ea2', to_timestamp('06-12-2013 17:41:36.107000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('06-12-2013 17:41:36.107000', 'dd-mm-yyyy hh24:mi:ss.ff'), 2, '014', '行政审批', 'ADMINISTRATIVE_APPROVAL', 'dbe69428-87d8-428c-b60e-3d220aa0a8c4');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('fce338cc-2821-49e5-822f-d896bd29322c', to_timestamp('02-04-2013 19:59:49.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('02-04-2013 20:00:10.733000', 'dd-mm-yyyy hh24:mi:ss.ff'), 3, '003001001', '日期', null, 'dbe69428-87d8-428c-b60e-3d220aa0a8c4');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('98b5e61c-b8ea-40ba-9c9f-844176895db0', to_timestamp('18-02-2013 11:39:50.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('21-02-2013 12:56:05.497000', 'dd-mm-yyyy hh24:mi:ss.ff'), 4, '005002002', '进行中', 'work_state_doing', 'e2baddfe-9dd2-4447-8230-1a18be361146');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('35fe6c05-3156-4c79-97c7-99ad7a4cc467', to_timestamp('18-02-2013 11:39:06.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('18-02-2013 11:40:07.400000', 'dd-mm-yyyy hh24:mi:ss.ff'), 4, '005002001', '未开始', 'work_state_unbegin', 'e2baddfe-9dd2-4447-8230-1a18be361146');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('5c1ce83d-8472-487f-9a7f-1f3b19c4c541', to_timestamp('18-02-2013 11:40:26.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('21-02-2013 12:55:40.707000', 'dd-mm-yyyy hh24:mi:ss.ff'), 3, '005002003', '延期', 'work_state_delay', 'e2baddfe-9dd2-4447-8230-1a18be361146');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('4ba3e00e-02bc-45a5-b915-abdd06b5d3ac', to_timestamp('04-03-2013 22:04:12.360000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('04-03-2013 22:04:12.360000', 'dd-mm-yyyy hh24:mi:ss.ff'), 2, 'work_state_cancel', '撤销', 'work_state_cancel', 'e2baddfe-9dd2-4447-8230-1a18be361146');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('04b090de-422c-4e3b-afb8-d4fcca53ee1c', to_timestamp('21-02-2013 12:55:15.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('21-02-2013 12:55:50.117000', 'dd-mm-yyyy hh24:mi:ss.ff'), 3, '005002004', '完成', 'work_state_done', 'e2baddfe-9dd2-4447-8230-1a18be361146');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('f953df3d-f4de-49a0-9935-4110c28fa2a1', to_timestamp('06-08-2013 21:05:01.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('29-08-2013 14:48:05.147000', 'dd-mm-yyyy hh24:mi:ss.ff'), 4, '012005001', '标记为', null, 'e883c5b5-a42d-41d9-ad59-7d1296cdb136');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('fc178418-967a-44d1-8e2c-d4788c7490dd', to_timestamp('28-08-2013 21:17:56.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('29-08-2013 14:50:00.963000', 'dd-mm-yyyy hh24:mi:ss.ff'), 4, '012005002', '移动到', null, 'e883c5b5-a42d-41d9-ad59-7d1296cdb136');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('af03d9a1-53cd-40c6-a29f-8037ce0be910', to_timestamp('04-07-2013 10:01:20.900000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('04-07-2013 10:01:20.900000', 'dd-mm-yyyy hh24:mi:ss.ff'), 1, 'big_meetroom', '大会议室', 'big_meetroom', 'e9389c1c-862d-4e21-aed4-01cb43a0cb36');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('bf11245a-3d95-49e3-84b3-ee91a8db27aa', to_timestamp('04-03-2013 20:58:15.433000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('04-03-2013 20:58:15.433000', 'dd-mm-yyyy hh24:mi:ss.ff'), 9, '的', '任务撤销单', null, '1cc0201d-f6b1-4a00-824c-86e477160602');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('c1dede19-dc63-4a54-a746-f21c360ec94e', to_timestamp('20-02-2013 11:00:19.070000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('20-02-2013 11:00:19.070000', 'dd-mm-yyyy hh24:mi:ss.ff'), 5, '011001002', '文件管理', null, '1cc0201d-f6b1-4a00-824c-86e477160602');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('baf041d7-503f-4021-be08-97ad77bd755c', to_timestamp('03-03-2013 12:18:53.307000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('03-03-2013 12:18:53.307000', 'dd-mm-yyyy hh24:mi:ss.ff'), 9, '011001009', '工作任务延期', null, '1cc0201d-f6b1-4a00-824c-86e477160602');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('9ded6b48-3fb3-4447-9431-794322b46166', to_timestamp('25-03-2013 16:47:55.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('04-06-2013 09:27:25.990000', 'dd-mm-yyyy hh24:mi:ss.ff'), 12, '011001015', '工作流程', null, '1cc0201d-f6b1-4a00-824c-86e477160602');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('92e08e2e-4371-4dd9-92b2-4d0b801b5fa1', to_timestamp('03-03-2013 11:24:36.460000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('03-03-2013 11:24:36.460000', 'dd-mm-yyyy hh24:mi:ss.ff'), 9, '011001007', '任务分解明细', null, '1cc0201d-f6b1-4a00-824c-86e477160602');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('2cfd8c22-330d-4223-ad5d-25010b8554fe', to_timestamp('04-03-2013 18:53:18.393000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('04-03-2013 18:53:18.393000', 'dd-mm-yyyy hh24:mi:ss.ff'), 9, '011001011', '任务转办', null, '1cc0201d-f6b1-4a00-824c-86e477160602');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('497e6dc9-82a2-483d-af48-910db6b86538', to_timestamp('03-03-2013 09:57:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('03-03-2013 09:57:11.047000', 'dd-mm-yyyy hh24:mi:ss.ff'), 10, '011001005', '工作报告', null, '1cc0201d-f6b1-4a00-824c-86e477160602');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('458dec95-0e31-4301-9791-6dc581eee834', to_timestamp('03-03-2013 11:31:23.867000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('03-03-2013 11:31:23.867000', 'dd-mm-yyyy hh24:mi:ss.ff'), 9, '011001008', '工作任务分解', null, '1cc0201d-f6b1-4a00-824c-86e477160602');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('3f9a833c-115a-4b06-b4ae-f13851bf42b6', to_timestamp('14-03-2013 17:22:17.350000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('14-03-2013 17:22:17.350000', 'dd-mm-yyyy hh24:mi:ss.ff'), 9, '工作报告之评价', '工作报告之评价', '工作报告之评价', '1cc0201d-f6b1-4a00-824c-86e477160602');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('4abbacc4-00d7-4f01-9d89-c30414aaaa97', to_timestamp('03-03-2013 10:28:05.100000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('03-03-2013 10:28:05.100000', 'dd-mm-yyyy hh24:mi:ss.ff'), 9, '011001006', '任务交办', null, '1cc0201d-f6b1-4a00-824c-86e477160602');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('520434c2-827e-4d0c-9ae8-c21a6fb522c8', to_timestamp('01-03-2013 10:54:20.340000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('01-03-2013 10:54:20.340000', 'dd-mm-yyyy hh24:mi:ss.ff'), 9, '011001003', '工作计划明细单', null, '1cc0201d-f6b1-4a00-824c-86e477160602');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('5233ad4f-c5a0-4825-808a-f7cab6ef355a', to_timestamp('21-08-2013 10:02:55.213000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('21-08-2013 10:02:55.213000', 'dd-mm-yyyy hh24:mi:ss.ff'), 6, '011001016', '时间占用', null, '1cc0201d-f6b1-4a00-824c-86e477160602');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('5c6c164f-8b79-475c-956a-4dc5aeaf41aa', to_timestamp('28-02-2013 14:04:47.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('28-02-2013 14:05:04.350000', 'dd-mm-yyyy hh24:mi:ss.ff'), 9, '011001002', '公文交换', null, '1cc0201d-f6b1-4a00-824c-86e477160602');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('1f453123-69bf-4905-aa70-1637bd4b5266', to_timestamp('02-03-2013 13:02:05.830000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('02-03-2013 13:02:05.830000', 'dd-mm-yyyy hh24:mi:ss.ff'), 9, '011001004', '工作任务请示', null, '1cc0201d-f6b1-4a00-824c-86e477160602');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('ec8ef157-5bec-494f-9f31-fa5cf9f4c3a2', to_timestamp('20-02-2013 10:58:16.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('20-02-2013 10:59:15.460000', 'dd-mm-yyyy hh24:mi:ss.ff'), 5, '011001001', '工作计划', null, '1cc0201d-f6b1-4a00-824c-86e477160602');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('1843b9ab-0621-452c-a18b-080d7b284e1e', to_timestamp('20-08-2013 10:10:02.360000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('20-08-2013 10:10:02.360000', 'dd-mm-yyyy hh24:mi:ss.ff'), 1, 'CAR_RESOURCE', '车辆资源', null, '22b1f7de-e06f-416c-9f3d-3f1e5c25b3df');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('3f63445b-f204-4c6c-924a-6f61fb7aa903', null, null, 'U0010000001', to_timestamp('08-07-2014 17:44:32.282000', 'dd-mm-yyyy hh24:mi:ss.ff'), 1, '20016', '临时使用园林绿地许可证', null, 'f31c3e8c-71e2-4c48-8c75-74981cd447ea');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('232fb31d-c87c-49c6-89c8-ea22d0888b5e', null, null, 'U0010000001', to_timestamp('08-07-2014 17:44:24.136000', 'dd-mm-yyyy hh24:mi:ss.ff'), 1, '20017', '城市树木移伐许可证', null, 'f31c3e8c-71e2-4c48-8c75-74981cd447ea');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('80c2b105-cfce-4b59-ae05-cb67d28d967a', null, null, 'U0010000001', to_timestamp('08-07-2014 17:44:15.733000', 'dd-mm-yyyy hh24:mi:ss.ff'), 1, '30025', '厦门市市政园林工程审批意见书', null, 'f31c3e8c-71e2-4c48-8c75-74981cd447ea');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('a54a9b03-cfb0-4724-816a-a461fa0fc171', null, null, 'U0010000001', to_timestamp('08-07-2014 17:44:05.732000', 'dd-mm-yyyy hh24:mi:ss.ff'), 1, '30026', '厦门市市政工程设施验收通过意见书', null, 'f31c3e8c-71e2-4c48-8c75-74981cd447ea');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('e6c699d3-b1c5-4eb7-8a07-4e46e9e77aca', null, null, 'U0010000001', to_timestamp('08-07-2014 17:43:14.393000', 'dd-mm-yyyy hh24:mi:ss.ff'), 1, '30027', '厦门市市政园林局建设项目绿地率审核意见书', null, 'f31c3e8c-71e2-4c48-8c75-74981cd447ea');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('5cce78b7-fe5c-46ee-a79f-97311c6b1874', null, null, 'U0010000001', to_timestamp('16-06-2014 17:03:19.650000', 'dd-mm-yyyy hh24:mi:ss.ff'), 2, '1', '行政许可事项', null, 'da5e734a-eceb-4c4b-a063-9e61e0dda8cf');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('5c5c131d-8faf-4301-b9f2-ccf26ac0edb2', null, null, 'U0010000001', to_timestamp('16-06-2014 17:03:19.656000', 'dd-mm-yyyy hh24:mi:ss.ff'), 2, '2', '非行政许可事项', null, 'da5e734a-eceb-4c4b-a063-9e61e0dda8cf');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('c7e2d133-d0d7-4138-a3bb-c582a967afba', null, null, 'U0010000001', to_timestamp('16-06-2014 17:03:19.655000', 'dd-mm-yyyy hh24:mi:ss.ff'), 2, '3', '服务事项', null, 'da5e734a-eceb-4c4b-a063-9e61e0dda8cf');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('b6808bdb-934f-4a01-a07f-c48f3f48803a', null, null, 'U0010000001', to_timestamp('16-06-2014 17:03:19.652000', 'dd-mm-yyyy hh24:mi:ss.ff'), 2, '4', '其他', null, 'da5e734a-eceb-4c4b-a063-9e61e0dda8cf');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('c8ba1417-1121-443c-9dda-467134ab0aab', null, null, 'U0010000001', to_timestamp('16-06-2014 10:15:46.806000', 'dd-mm-yyyy hh24:mi:ss.ff'), 2, '1', '即办件', null, '957213a3-a6b4-4d88-995e-ebef567edcd7');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('257f7c7d-a904-4987-a748-42f7c28e81c1', null, null, 'U0010000001', to_timestamp('16-06-2014 10:15:46.804000', 'dd-mm-yyyy hh24:mi:ss.ff'), 2, '2', '承诺件', null, '957213a3-a6b4-4d88-995e-ebef567edcd7');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('65817686-444c-453d-8996-7cd7693e7492', null, null, 'U0010000001', to_timestamp('16-06-2014 10:15:46.808000', 'dd-mm-yyyy hh24:mi:ss.ff'), 2, '3', '联办件', null, '957213a3-a6b4-4d88-995e-ebef567edcd7');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('e84b4d76-67a4-4411-bc2b-7e0c944c9f34', null, null, 'U0010000001', to_timestamp('16-06-2014 10:16:12.434000', 'dd-mm-yyyy hh24:mi:ss.ff'), 2, '1', '审批', null, '4fc54293-e165-4cf9-a49d-130f13d427f2');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('d715b7ec-f6e6-4333-949a-2e4e319ba9ce', null, null, 'U0010000001', to_timestamp('16-06-2014 10:16:12.431000', 'dd-mm-yyyy hh24:mi:ss.ff'), 2, '2', '备案', null, '4fc54293-e165-4cf9-a49d-130f13d427f2');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('61583bbf-7da6-4c10-9438-7fa89da3b7e0', null, null, 'U0010000001', to_timestamp('16-06-2014 10:16:12.432000', 'dd-mm-yyyy hh24:mi:ss.ff'), 2, '3', '审核', null, '4fc54293-e165-4cf9-a49d-130f13d427f2');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('d2bc8208-34cb-4b71-888d-7bd06c571454', null, null, 'U0010000001', to_timestamp('16-06-2014 10:16:12.429000', 'dd-mm-yyyy hh24:mi:ss.ff'), 2, '4', '核准', null, '4fc54293-e165-4cf9-a49d-130f13d427f2');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('dd86b13b-dfcd-4427-a408-22ee7875dbd5', null, null, 'U0010000001', to_timestamp('16-06-2014 10:16:44.188000', 'dd-mm-yyyy hh24:mi:ss.ff'), 2, '1', '个人', null, '0b884de2-738b-47fc-823d-e2881b651d2c');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('9758118f-96c2-43bb-bd0e-bb0dd08d01e0', null, null, 'U0010000001', to_timestamp('16-06-2014 10:16:44.190000', 'dd-mm-yyyy hh24:mi:ss.ff'), 2, '2', '单位', null, '0b884de2-738b-47fc-823d-e2881b651d2c');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('3cd7d73f-942a-4c08-9a1d-32922e283757', null, null, 'U0010000001', to_timestamp('16-06-2014 10:16:44.187000', 'dd-mm-yyyy hh24:mi:ss.ff'), 2, '3', '个人和单位', null, '0b884de2-738b-47fc-823d-e2881b651d2c');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('35db6a57-aace-4804-a84d-916b48000f4e', null, null, 'U0010000001', to_timestamp('16-06-2014 10:34:25.712000', 'dd-mm-yyyy hh24:mi:ss.ff'), 3, '1', '有效', null, '664fa0ee-30ca-46f4-ac97-dc1a9293ae9c');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('8b78858b-5fd6-4a74-afaa-f49a02f009fc', null, null, 'U0010000001', to_timestamp('16-06-2014 10:34:25.714000', 'dd-mm-yyyy hh24:mi:ss.ff'), 3, '2', '过期', null, '664fa0ee-30ca-46f4-ac97-dc1a9293ae9c');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('04d277b4-5794-40fa-9dce-fdaa5623a32e', null, null, 'U0010000001', to_timestamp('16-06-2014 10:34:25.709000', 'dd-mm-yyyy hh24:mi:ss.ff'), 3, '3', '吊销', null, '664fa0ee-30ca-46f4-ac97-dc1a9293ae9c');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('f60d725d-65db-4038-ae0b-05ab8ae6de1f', null, null, 'U0010000001', to_timestamp('16-06-2014 10:34:25.710000', 'dd-mm-yyyy hh24:mi:ss.ff'), 3, '4', '撤销', null, '664fa0ee-30ca-46f4-ac97-dc1a9293ae9c');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('d3e8c14a-4d22-420f-93d0-37df9f3d6736', null, null, 'U0010000001', to_timestamp('16-06-2014 10:35:52.061000', 'dd-mm-yyyy hh24:mi:ss.ff'), 2, '1', '新建', null, '8bd4c579-653d-4cf1-85e6-849635cf37dc');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('4cd6b25c-5cb3-4e8e-822e-8e98459e5e46', null, null, 'U0010000001', to_timestamp('16-06-2014 10:35:52.064000', 'dd-mm-yyyy hh24:mi:ss.ff'), 2, '2', '改建', null, '8bd4c579-653d-4cf1-85e6-849635cf37dc');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('58c2f5e1-0235-4b04-81b9-dc861b167b53', null, null, 'U0010000001', to_timestamp('16-06-2014 10:35:52.063000', 'dd-mm-yyyy hh24:mi:ss.ff'), 2, '3', '扩建', null, '8bd4c579-653d-4cf1-85e6-849635cf37dc');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('c716d20d-70b3-44b5-8f5b-335c2dce194f', null, null, 'U0010000001', to_timestamp('16-06-2014 10:35:52.060000', 'dd-mm-yyyy hh24:mi:ss.ff'), 2, '4', '未知', null, '8bd4c579-653d-4cf1-85e6-849635cf37dc');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('8217f85a-91de-4ee9-abef-e9dc4cb8865f', null, null, 'U0010000001', to_timestamp('24-02-2014 14:58:25.193000', 'dd-mm-yyyy hh24:mi:ss.ff'), 4, '技术类别', '技术类别', null, 'ce20bb7a-8f40-4e32-b8e3-74aa54800725');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('735c47a1-5bf5-4071-b617-c8e1e08acd20', null, null, 'U0010000001', to_timestamp('24-02-2014 14:57:49.470000', 'dd-mm-yyyy hh24:mi:ss.ff'), 4, '业务类别', '业务类别', null, 'ce20bb7a-8f40-4e32-b8e3-74aa54800725');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('b8b2a019-6b89-46ac-bc53-dd716d1c7ba6', null, null, 'U0010000001', to_timestamp('16-06-2014 10:36:50.420000', 'dd-mm-yyyy hh24:mi:ss.ff'), 5, '1', '商品房', '001', '2218d711-b625-44e5-8732-55d0af955279');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('ed0ff18f-8fa1-401b-98b0-adb2d7e648d2', null, null, 'U0010000001', to_timestamp('16-06-2014 10:36:50.432000', 'dd-mm-yyyy hh24:mi:ss.ff'), 5, '2', '工业项目', '002', '2218d711-b625-44e5-8732-55d0af955279');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('1c247fea-a077-4495-84f6-e5af4e6a1b95', null, null, 'U0010000001', to_timestamp('16-06-2014 10:36:50.406000', 'dd-mm-yyyy hh24:mi:ss.ff'), 5, '3', '基础设施', '003', '2218d711-b625-44e5-8732-55d0af955279');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('e87e6950-ea28-40c5-949a-38077db38385', null, null, 'U0010000001', to_timestamp('16-06-2014 10:36:50.416000', 'dd-mm-yyyy hh24:mi:ss.ff'), 5, '4', '居住建筑', '004', '2218d711-b625-44e5-8732-55d0af955279');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('fa1db869-9e53-40d0-bbdf-c00bf4ae7e30', null, null, 'U0010000001', to_timestamp('16-06-2014 10:36:50.411000', 'dd-mm-yyyy hh24:mi:ss.ff'), 5, '5', '装修', '005', '2218d711-b625-44e5-8732-55d0af955279');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('c28b47d5-8a08-45fb-bf02-c31a68e33039', null, null, 'U0010000001', to_timestamp('16-06-2014 10:36:50.428000', 'dd-mm-yyyy hh24:mi:ss.ff'), 5, '6', '公共建筑', '006', '2218d711-b625-44e5-8732-55d0af955279');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('62dcc0a8-436d-4942-9eb8-5b2aef1da20b', null, null, 'U0010000001', to_timestamp('18-06-2014 16:23:08.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 7, '9', '未知', '009', '2218d711-b625-44e5-8732-55d0af955279');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('0638734b-44e7-4146-9600-d3d61eb7def6', null, null, 'U0010000001', to_timestamp('18-07-2014 10:24:49.782000', 'dd-mm-yyyy hh24:mi:ss.ff'), 5, '2', '财政审核项目', null, '64d091fa-3823-4612-b979-df844f647e27');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('6c989f79-36f1-4813-a250-e5c0fe7e28ee', null, null, 'U0010000001', to_timestamp('21-07-2014 17:44:43.248000', 'dd-mm-yyyy hh24:mi:ss.ff'), 12, '015013001006', '其他阶段', null, 'c33a8dbe-27e2-4431-a429-2033b2022c20');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('3eabee40-c7c7-4c67-a255-aa7985ee1758', null, null, 'U0010000001', to_timestamp('02-04-2014 10:25:00.076000', 'dd-mm-yyyy hh24:mi:ss.ff'), 10, '015013001001', '工可阶段', null, 'c33a8dbe-27e2-4431-a429-2033b2022c20');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('906f22e5-fd87-4a06-b9e4-0350436d59ff', null, null, 'U0010000001', to_timestamp('08-07-2014 17:45:59.617000', 'dd-mm-yyyy hh24:mi:ss.ff'), 2, '20012', '城市道路占用许可证', null, 'f31c3e8c-71e2-4c48-8c75-74981cd447ea');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('d9279af5-9940-4715-852f-abc2c54f014b', null, null, 'U0010000001', to_timestamp('08-07-2014 17:42:38.253000', 'dd-mm-yyyy hh24:mi:ss.ff'), 1, '20018', '园林绿化建设初验凭证', null, 'f31c3e8c-71e2-4c48-8c75-74981cd447ea');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('68271fa3-0862-431f-a664-6731edf665b2', null, null, 'U0010000001', to_timestamp('18-07-2014 10:24:49.779000', 'dd-mm-yyyy hh24:mi:ss.ff'), 5, '3', '装修消防', null, '64d091fa-3823-4612-b979-df844f647e27');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('57c593d7-7ab1-4c5d-8c50-db93f7439bf3', null, null, 'U0010000001', to_timestamp('18-07-2014 10:24:49.785000', 'dd-mm-yyyy hh24:mi:ss.ff'), 5, '4', '其他', null, '64d091fa-3823-4612-b979-df844f647e27');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('3ebfa7bf-0646-4090-8556-a278684f57fd', null, null, 'U0010000001', to_timestamp('17-07-2014 16:58:18.278000', 'dd-mm-yyyy hh24:mi:ss.ff'), 4, '015012', '回执单类型', 'XZSP_RECEIPTS_TYPE', '8c56947c-ac41-484e-9ad6-a5ae5ccb7104');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('0a7bd9e5-49ac-45ab-b5f1-02b6b547b689', null, null, 'U0010000001', to_timestamp('25-03-2014 17:27:13.640000', 'dd-mm-yyyy hh24:mi:ss.ff'), 3, '(收件)受理决定书', '(收件)受理决定书', null, '3ebfa7bf-0646-4090-8556-a278684f57fd');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('bcc346ce-41a2-41fe-a491-dd945bc24bc3', null, null, 'U0010000001', to_timestamp('08-07-2014 17:41:07.380000', 'dd-mm-yyyy hh24:mi:ss.ff'), 1, '30029', '厦门市公园建筑方案审批意见书', null, 'f31c3e8c-71e2-4c48-8c75-74981cd447ea');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('546697da-9ca6-46e6-947f-50fd0b1ac15d', null, null, 'U0010000001', to_timestamp('08-07-2014 17:40:58.164000', 'dd-mm-yyyy hh24:mi:ss.ff'), 1, '30030', '厦门市公园建筑方案施工图审批意见书', null, 'f31c3e8c-71e2-4c48-8c75-74981cd447ea');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('e313c51d-8711-4d3c-89ad-345a16d3b828', null, null, 'U0010000001', to_timestamp('08-07-2014 17:40:31.172000', 'dd-mm-yyyy hh24:mi:ss.ff'), 1, '30031', '厦门市公园建筑验收意见书', null, 'f31c3e8c-71e2-4c48-8c75-74981cd447ea');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('009de432-9c05-4c9e-99c7-1599a0edc219', null, null, 'U0010000001', to_timestamp('18-07-2014 10:25:04.714000', 'dd-mm-yyyy hh24:mi:ss.ff'), 4, '1', '建设类项目', null, '462a8ca9-e9e9-4997-98da-d3b64c11f70a');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('0cc08c16-9114-4190-ae18-ac67250c2403', null, null, 'U0010000001', to_timestamp('18-07-2014 10:25:04.717000', 'dd-mm-yyyy hh24:mi:ss.ff'), 5, '2', '非建设类项目', null, '462a8ca9-e9e9-4997-98da-d3b64c11f70a');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('288fa7ae-1518-4db7-80a3-58bfa549eb0d', null, null, 'U0010000001', to_timestamp('18-07-2014 10:25:04.720000', 'dd-mm-yyyy hh24:mi:ss.ff'), 5, '3', '财政类项目', null, '462a8ca9-e9e9-4997-98da-d3b64c11f70a');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('1fe4a0b3-addf-48b6-bb62-b67284b71c39', null, null, 'U0010000001', to_timestamp('18-07-2014 10:25:19.526000', 'dd-mm-yyyy hh24:mi:ss.ff'), 4, '1', '一级', null, '8bc7682b-9343-4a4d-9c79-465fa45f4316');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('95047d8c-0588-47b5-a148-8a7e1d635e5c', to_timestamp('27-02-2014 18:27:19.651000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('27-02-2014 18:27:19.651000', 'dd-mm-yyyy hh24:mi:ss.ff'), 1, '福建省燃气汽车加气经营许可证', '福建省燃气汽车加气经营许可证', null, 'c5edfc8b-011f-491c-b548-3dfdd31c2a18');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('35497ecf-ba02-4c8f-8b41-4342d4d982a6', to_timestamp('27-02-2014 18:27:23.037000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('27-02-2014 18:27:23.037000', 'dd-mm-yyyy hh24:mi:ss.ff'), 1, '福建省瓶装燃气供应许可证', '福建省瓶装燃气供应许可证', null, 'c5edfc8b-011f-491c-b548-3dfdd31c2a18');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('41a69e1e-616a-44e0-ab8a-e7a38159a061', to_timestamp('27-02-2014 18:27:26.156000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('27-02-2014 18:27:26.156000', 'dd-mm-yyyy hh24:mi:ss.ff'), 1, '中华人民共和国道路运输经营许可证', '中华人民共和国道路运输经营许可证', null, 'c5edfc8b-011f-491c-b548-3dfdd31c2a18');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('f0fdff32-aaf5-4f2c-85f7-dc943ab8b602', to_timestamp('27-02-2014 18:27:30.804000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('27-02-2014 18:27:30.804000', 'dd-mm-yyyy hh24:mi:ss.ff'), 1, '中华人民共和国采矿许可证', '中华人民共和国采矿许可证', null, 'c5edfc8b-011f-491c-b548-3dfdd31c2a18');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('2b59c727-e315-4357-9ee3-17f586e27b09', to_timestamp('27-02-2014 18:27:34.899000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('27-02-2014 18:27:34.899000', 'dd-mm-yyyy hh24:mi:ss.ff'), 1, '测绘资质证书', '测绘资质证书', null, 'c5edfc8b-011f-491c-b548-3dfdd31c2a18');
commit;
prompt 400 records committed...
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('afaac05f-77c9-4a48-ad07-9c1c0d82aa6c', to_timestamp('27-02-2014 18:27:38.980000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('27-02-2014 18:27:38.980000', 'dd-mm-yyyy hh24:mi:ss.ff'), 1, '职业介绍许可证', '职业介绍许可证', null, 'c5edfc8b-011f-491c-b548-3dfdd31c2a18');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('9251206b-563e-4dcc-8547-d537078550dc', to_timestamp('27-02-2014 18:27:42.744000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('27-02-2014 18:27:42.744000', 'dd-mm-yyyy hh24:mi:ss.ff'), 1, '广告经营单位广告经营活动检查表', '广告经营单位广告经营活动检查表', null, 'c5edfc8b-011f-491c-b548-3dfdd31c2a18');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('28a68155-0983-4b03-b9cf-a181c4754929', to_timestamp('27-02-2014 18:27:46.098000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('27-02-2014 18:27:46.098000', 'dd-mm-yyyy hh24:mi:ss.ff'), 1, '中华人民共和国专项计量授权证书', '中华人民共和国专项计量授权证书', null, 'c5edfc8b-011f-491c-b548-3dfdd31c2a18');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('f537a676-5623-4414-9a86-daca600159f9', to_timestamp('27-02-2014 18:25:35.912000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('27-02-2014 18:25:35.912000', 'dd-mm-yyyy hh24:mi:ss.ff'), 1, '文化经营许可证', '文化经营许可证', null, 'c5edfc8b-011f-491c-b548-3dfdd31c2a18');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('3dc3a1ec-11bc-4077-8a10-d80bbacc0e5a', to_timestamp('27-02-2014 18:26:34.827000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('27-02-2014 18:26:34.827000', 'dd-mm-yyyy hh24:mi:ss.ff'), 1, '福建省电影放映经营许可证', '福建省电影放映经营许可证', null, 'c5edfc8b-011f-491c-b548-3dfdd31c2a18');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('b093137f-f62f-4552-b6c5-dc01a7425f56', to_timestamp('27-02-2014 18:26:51.992000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('27-02-2014 18:26:51.992000', 'dd-mm-yyyy hh24:mi:ss.ff'), 1, '兽医经营许可证', '兽医经营许可证', null, 'c5edfc8b-011f-491c-b548-3dfdd31c2a18');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('240c531a-02c2-45b7-8a58-de4ee401a5cf', to_timestamp('07-07-2014 15:55:15.747000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('21-07-2014 17:44:43.252000', 'dd-mm-yyyy hh24:mi:ss.ff'), 14, '015013001005', '竣工验收和备案阶段', null, 'c33a8dbe-27e2-4431-a429-2033b2022c20');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('c8f99e5b-c7a7-41d6-a936-d4d27bc3626a', to_timestamp('08-07-2014 16:06:55.298000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('08-07-2014 16:06:55.298000', 'dd-mm-yyyy hh24:mi:ss.ff'), 0, '30003', '备案证明书、施工合同（专业分包合同或劳务分包合同）', null, 'f31c3e8c-71e2-4c48-8c75-74981cd447ea');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('8663052b-f54d-42c8-aa3b-dd341651bbbe', null, null, 'U0010000001', to_timestamp('08-07-2014 17:45:18.253000', 'dd-mm-yyyy hh24:mi:ss.ff'), 1, '30004', '工程担保保函收讫证明和加盖市造价站“原件收讫”印章的保函复印件两份', null, 'f31c3e8c-71e2-4c48-8c75-74981cd447ea');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('411e472f-f0b7-489c-bef7-db654608f5cf', to_timestamp('08-07-2014 16:07:38.421000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('08-07-2014 16:07:38.421000', 'dd-mm-yyyy hh24:mi:ss.ff'), 0, '30005', '收费单', null, 'f31c3e8c-71e2-4c48-8c75-74981cd447ea');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('979270c6-251b-4bfd-9a82-24803dd67ea1', to_timestamp('08-07-2014 16:07:57.946000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('08-07-2014 16:07:57.946000', 'dd-mm-yyyy hh24:mi:ss.ff'), 0, '30006', '散装水泥专项资金', null, 'f31c3e8c-71e2-4c48-8c75-74981cd447ea');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('cb938a98-9a5e-4b2c-a858-bb39e81c84ee', null, null, 'U0010000001', to_timestamp('20-07-2014 13:32:02.752000', 'dd-mm-yyyy hh24:mi:ss.ff'), 3, '30048', '防雷装置验收意见书或防雷装置整改意见书', null, 'f31c3e8c-71e2-4c48-8c75-74981cd447ea');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('3adbc26a-9af2-4f28-bb7d-71b6c23d6477', null, null, 'U0010000001', to_timestamp('08-07-2014 17:51:21.969000', 'dd-mm-yyyy hh24:mi:ss.ff'), 1, '30008', '防雷装置设计审核意见书', null, 'f31c3e8c-71e2-4c48-8c75-74981cd447ea');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('95072b59-a006-498f-bd5e-32d84875ae90', to_timestamp('08-07-2014 15:52:13.667000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('08-07-2014 15:52:13.667000', 'dd-mm-yyyy hh24:mi:ss.ff'), 0, '30001', '建设项目选址意见书', null, 'f31c3e8c-71e2-4c48-8c75-74981cd447ea');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('9ff20383-e4df-445b-87c9-81293f7c826b', to_timestamp('08-07-2014 16:05:48.170000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('08-07-2014 16:05:48.170000', 'dd-mm-yyyy hh24:mi:ss.ff'), 0, '30002', '建设工程档案（预）验收认可书', null, 'f31c3e8c-71e2-4c48-8c75-74981cd447ea');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('310cd3f3-0904-48ce-a457-4b3fab3730af', to_timestamp('08-07-2014 16:06:10.511000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('08-07-2014 16:06:10.511000', 'dd-mm-yyyy hh24:mi:ss.ff'), 0, '10001', '开工报告申请表', null, 'f31c3e8c-71e2-4c48-8c75-74981cd447ea');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('725f16c9-e2ca-4f0f-99a8-743fd327db2a', to_timestamp('08-07-2014 16:06:29.482000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('08-07-2014 16:06:29.482000', 'dd-mm-yyyy hh24:mi:ss.ff'), 0, '20008', '建筑起重机械备案证', null, 'f31c3e8c-71e2-4c48-8c75-74981cd447ea');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('67ec3f72-c0f5-4ae8-961a-66da018a71e4', to_timestamp('08-07-2014 15:52:39.944000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('08-07-2014 15:52:39.944000', 'dd-mm-yyyy hh24:mi:ss.ff'), 0, '20001', '建设用地规划许可证', null, 'f31c3e8c-71e2-4c48-8c75-74981cd447ea');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('3ed3613e-84b0-4b01-997f-bbca43036f0f', to_timestamp('15-07-2014 10:26:59.527000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('15-07-2014 10:26:59.527000', 'dd-mm-yyyy hh24:mi:ss.ff'), 0, '30045', '厦门市新建（改建、扩建）通信配套（公众电信网）建设工程竣工验收意见书', null, 'f31c3e8c-71e2-4c48-8c75-74981cd447ea');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('9fbf9ea7-6e4b-4577-88bb-b06c3038f593', to_timestamp('27-02-2014 18:26:27.635000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('27-02-2014 18:26:27.635000', 'dd-mm-yyyy hh24:mi:ss.ff'), 1, '中华人民共和国出版物经营许可证', '中华人民共和国出版物经营许可证', null, 'c5edfc8b-011f-491c-b548-3dfdd31c2a18');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('e35d1ddb-96e6-472e-b817-21d84964308e', to_timestamp('27-02-2014 18:26:31.348000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('27-02-2014 18:26:31.348000', 'dd-mm-yyyy hh24:mi:ss.ff'), 1, '福建省电影发行经营许可证', '福建省电影发行经营许可证', null, 'c5edfc8b-011f-491c-b548-3dfdd31c2a18');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('b4f005b6-cb06-48d0-baa5-c5c84e1d79a3', to_timestamp('27-02-2014 18:26:38.378000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('27-02-2014 18:26:38.378000', 'dd-mm-yyyy hh24:mi:ss.ff'), 1, '广播电视节目制作经营许可证', '广播电视节目制作经营许可证', null, 'c5edfc8b-011f-491c-b548-3dfdd31c2a18');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('9485a94e-ed2e-4026-ae13-22c6e6829970', to_timestamp('27-02-2014 18:26:42.442000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('27-02-2014 18:26:42.442000', 'dd-mm-yyyy hh24:mi:ss.ff'), 1, '娱乐经营许可证', '娱乐经营许可证', null, 'c5edfc8b-011f-491c-b548-3dfdd31c2a18');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('6ee995b1-139f-4c33-9624-9cac72e22d31', to_timestamp('27-02-2014 18:26:47.925000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('27-02-2014 18:26:47.925000', 'dd-mm-yyyy hh24:mi:ss.ff'), 1, '营业性演出许可证', '营业性演出许可证', null, 'c5edfc8b-011f-491c-b548-3dfdd31c2a18');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('26dba3b8-1ac1-4541-a12c-dac87e8abb36', to_timestamp('27-02-2014 18:26:55.928000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('27-02-2014 18:26:55.928000', 'dd-mm-yyyy hh24:mi:ss.ff'), 1, '国际海运辅助业经营资格登记证', '国际海运辅助业经营资格登记证', null, 'c5edfc8b-011f-491c-b548-3dfdd31c2a18');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('3735d58d-92e6-469a-8a83-639260093967', to_timestamp('27-02-2014 18:26:59.004000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('27-02-2014 18:26:59.004000', 'dd-mm-yyyy hh24:mi:ss.ff'), 1, '厦门市国内水运辅助业备案证明', '厦门市国内水运辅助业备案证明', null, 'c5edfc8b-011f-491c-b548-3dfdd31c2a18');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('d4ba5684-f63e-4221-a842-83d334942a77', to_timestamp('27-02-2014 18:27:01.879000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('27-02-2014 18:27:01.879000', 'dd-mm-yyyy hh24:mi:ss.ff'), 1, '福建省机动车维修经营许可证', '福建省机动车维修经营许可证', null, 'c5edfc8b-011f-491c-b548-3dfdd31c2a18');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('a62f4e81-5c6d-452c-8d0d-eb76bfd47484', to_timestamp('27-02-2014 18:27:05.710000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('27-02-2014 18:27:05.710000', 'dd-mm-yyyy hh24:mi:ss.ff'), 1, '福建省野生动物驯养繁殖许可证', '福建省野生动物驯养繁殖许可证', null, 'c5edfc8b-011f-491c-b548-3dfdd31c2a18');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('f686e090-239f-4ce6-99ee-b35d6c542df0', to_timestamp('27-02-2014 18:27:09.671000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('27-02-2014 18:27:09.671000', 'dd-mm-yyyy hh24:mi:ss.ff'), 1, '燃气经营许可证', '燃气经营许可证', null, 'c5edfc8b-011f-491c-b548-3dfdd31c2a18');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('d1a8434f-5e14-4c53-821c-275281780e0b', to_timestamp('15-07-2014 08:45:46.415000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('15-07-2014 08:45:46.415000', 'dd-mm-yyyy hh24:mi:ss.ff'), 0, '30044', '消防验收意见书', null, 'f31c3e8c-71e2-4c48-8c75-74981cd447ea');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('86b5dbd7-f35a-4408-b44d-ff09eaa8a775', to_timestamp('15-07-2014 10:27:30.593000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('15-07-2014 10:27:30.593000', 'dd-mm-yyyy hh24:mi:ss.ff'), 0, '30046', '厦门市新建（改建、扩建）通信配套（公众电信网）建设工程设计申报回执单', null, 'f31c3e8c-71e2-4c48-8c75-74981cd447ea');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('b5c82925-9c66-46bd-a747-7b3da0432a34', to_timestamp('15-07-2014 12:01:06.074000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('15-07-2014 12:01:06.074000', 'dd-mm-yyyy hh24:mi:ss.ff'), 0, '30047', '海洋工程建设项目海洋环境影响报告书（表）', null, 'f31c3e8c-71e2-4c48-8c75-74981cd447ea');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('239b8757-bcd7-4b09-97ee-73c4c9acb102', to_timestamp('15-07-2014 12:01:38.899000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('15-07-2014 12:01:38.899000', 'dd-mm-yyyy hh24:mi:ss.ff'), 0, '20025', '渔业捕捞许可证', null, 'f31c3e8c-71e2-4c48-8c75-74981cd447ea');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('fc545acf-13f7-45d7-9388-fedbe3ca24b8', to_timestamp('08-07-2014 15:53:14.638000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('08-07-2014 15:53:14.638000', 'dd-mm-yyyy hh24:mi:ss.ff'), 0, '20002', '建设工程规划许可证', null, 'f31c3e8c-71e2-4c48-8c75-74981cd447ea');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('2e051cd1-6388-425c-9086-9956a1c5139f', null, null, 'U0010000001', to_timestamp('08-07-2014 17:49:49.897000', 'dd-mm-yyyy hh24:mi:ss.ff'), 1, '20003', '排放污染物许可证', null, 'f31c3e8c-71e2-4c48-8c75-74981cd447ea');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('4b1aa384-bd09-4820-8c62-e87a4c5c246d', null, null, 'U0010000001', to_timestamp('08-07-2014 17:40:07.923000', 'dd-mm-yyyy hh24:mi:ss.ff'), 1, '20004', '危险废物经营许可证', null, 'f31c3e8c-71e2-4c48-8c75-74981cd447ea');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('bbeb7fb5-cd6e-42af-8668-d0e968d8e719', null, null, 'U0010000001', to_timestamp('08-07-2014 17:53:43.067000', 'dd-mm-yyyy hh24:mi:ss.ff'), 1, '20005', '危险废物收集经营许可证', null, 'f31c3e8c-71e2-4c48-8c75-74981cd447ea');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('20ca6ae8-7600-4202-ba00-2b298199aa4a', to_timestamp('08-07-2014 15:54:36.487000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('08-07-2014 15:54:36.487000', 'dd-mm-yyyy hh24:mi:ss.ff'), 0, '20006', '废弃电器电子产品处理资格证书', null, 'f31c3e8c-71e2-4c48-8c75-74981cd447ea');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('4deb75b2-43ac-466a-a291-72fa6bac787c', to_timestamp('08-07-2014 15:54:54.055000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('08-07-2014 15:54:54.055000', 'dd-mm-yyyy hh24:mi:ss.ff'), 0, '20007', '辐射安全许可证', null, 'f31c3e8c-71e2-4c48-8c75-74981cd447ea');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('f31c3e8c-71e2-4c48-8c75-74981cd447ea', to_timestamp('08-07-2014 15:50:25.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('04-08-2014 11:36:46.912000', 'dd-mm-yyyy hh24:mi:ss.ff'), 158, '015010', '材料或证照名', 'XZSP_MATERIAL_NAME', '8c56947c-ac41-484e-9ad6-a5ae5ccb7104');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('23f3ac59-f851-4719-989d-0b7e6f34b07b', null, null, 'U0010000001', to_timestamp('08-07-2014 17:51:59.486000', 'dd-mm-yyyy hh24:mi:ss.ff'), 1, '30007', '防雷装置验收意见书', null, 'f31c3e8c-71e2-4c48-8c75-74981cd447ea');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('d98982e0-3266-44ae-99bd-6b42307a9865', to_timestamp('08-07-2014 16:09:44.792000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('20-07-2014 14:56:11.136000', 'dd-mm-yyyy hh24:mi:ss.ff'), 2, '30009', '河道管理范围内的工程建设方案申请和审查意见书', null, 'f31c3e8c-71e2-4c48-8c75-74981cd447ea');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('d83a908a-254d-40c6-b04e-16c8e37038f1', null, null, 'U0010000001', to_timestamp('08-07-2014 17:48:28.188000', 'dd-mm-yyyy hh24:mi:ss.ff'), 1, '20009', '施放气球资质证', null, 'f31c3e8c-71e2-4c48-8c75-74981cd447ea');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('6fef66de-5f7a-4e46-a0fb-e3831c01dd7e', to_timestamp('08-07-2014 16:10:58.020000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('08-07-2014 16:10:58.020000', 'dd-mm-yyyy hh24:mi:ss.ff'), 0, '30010', '厦门市防空地下室施工图审查结果备案意见书', null, 'f31c3e8c-71e2-4c48-8c75-74981cd447ea');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('64722c0e-7f10-43d7-ab25-1239436401d4', to_timestamp('08-07-2014 16:11:15.223000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('08-07-2014 16:11:15.223000', 'dd-mm-yyyy hh24:mi:ss.ff'), 0, '30011', '行政许可意见书', null, 'f31c3e8c-71e2-4c48-8c75-74981cd447ea');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('6efc12c3-67d3-43c6-b63e-722d49a1ec87', to_timestamp('14-07-2014 19:36:16.870000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('14-07-2014 19:36:16.870000', 'dd-mm-yyyy hh24:mi:ss.ff'), 0, '30039', '赠送、交换、出卖国家所有档案复制件许可批文', null, 'f31c3e8c-71e2-4c48-8c75-74981cd447ea');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('45ea3925-0a5d-4f92-97cc-28ab56eba845', null, null, 'U0010000001', to_timestamp('15-07-2014 12:01:06.073000', 'dd-mm-yyyy hh24:mi:ss.ff'), 3, '30041', '文件材料归档范围和保管期限核准批文', null, 'f31c3e8c-71e2-4c48-8c75-74981cd447ea');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('e07a2678-e304-4b37-93e3-49e6da8f1065', to_timestamp('14-07-2014 19:38:01.716000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('14-07-2014 19:38:01.716000', 'dd-mm-yyyy hh24:mi:ss.ff'), 0, '30040', '延期向社会开放档案审批批文', null, 'f31c3e8c-71e2-4c48-8c75-74981cd447ea');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('143c0104-4add-4c17-96ee-fcbd9d95aa1a', to_timestamp('14-07-2014 19:45:29.065000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('14-07-2014 19:45:29.065000', 'dd-mm-yyyy hh24:mi:ss.ff'), 0, '30042', '厦门市重点建设项目档案验收意见书', null, 'f31c3e8c-71e2-4c48-8c75-74981cd447ea');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('ea14f16a-1d16-4948-8121-4dd8a12218c0', null, null, 'U0010000001', to_timestamp('14-07-2014 19:55:42.411000', 'dd-mm-yyyy hh24:mi:ss.ff'), 1, '20024', '福建省档案服务机构备案证书', null, 'f31c3e8c-71e2-4c48-8c75-74981cd447ea');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('ced5decb-225d-4899-a39c-235240a48f27', to_timestamp('14-07-2014 19:55:42.412000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('14-07-2014 19:55:42.412000', 'dd-mm-yyyy hh24:mi:ss.ff'), 0, '30043', '销毁国有企业资产与产权变动档案备案批文', null, 'f31c3e8c-71e2-4c48-8c75-74981cd447ea');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('161f15ec-0e84-46b0-868b-b41f72343fa8', to_timestamp('08-07-2014 17:52:16.884000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('08-07-2014 17:52:16.884000', 'dd-mm-yyyy hh24:mi:ss.ff'), 0, '30032', '防雷装置整改意见书', null, 'f31c3e8c-71e2-4c48-8c75-74981cd447ea');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('5e4b553d-b926-44da-a69d-d913ec7ce741', null, null, 'U0010000001', to_timestamp('14-07-2014 17:42:21.342000', 'dd-mm-yyyy hh24:mi:ss.ff'), 1, '30037', '档案服务机构开展档案服务范围核定批文', null, 'f31c3e8c-71e2-4c48-8c75-74981cd447ea');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('3581b8be-dd6d-4685-8202-0d5dceaa018c', to_timestamp('08-07-2014 16:11:32.464000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('08-07-2014 16:11:32.464000', 'dd-mm-yyyy hh24:mi:ss.ff'), 0, '30012', '厦门市易地修建防空地下室竣工验收意见书', null, 'f31c3e8c-71e2-4c48-8c75-74981cd447ea');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('e373a6a3-3b63-49d0-8e42-1a1a36432106', to_timestamp('08-07-2014 16:11:49.473000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('08-07-2014 16:11:49.473000', 'dd-mm-yyyy hh24:mi:ss.ff'), 0, '30013', '厦门市易地修建防空地下室意见书', null, 'f31c3e8c-71e2-4c48-8c75-74981cd447ea');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('a6a4b0b7-25ff-445c-971d-5b78a706d88b', to_timestamp('08-07-2014 16:12:11.415000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('08-07-2014 16:12:11.415000', 'dd-mm-yyyy hh24:mi:ss.ff'), 0, '30014', '厦门市公用人防工程平时使用意见书', null, 'f31c3e8c-71e2-4c48-8c75-74981cd447ea');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('daca0f27-0af2-4c84-88a0-e1850e24400d', null, null, 'U0010000001', to_timestamp('08-07-2014 17:46:10.808000', 'dd-mm-yyyy hh24:mi:ss.ff'), 1, '20011', '城市挖掘施工许可证', null, 'f31c3e8c-71e2-4c48-8c75-74981cd447ea');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('da5e734a-eceb-4c4b-a063-9e61e0dda8cf', to_timestamp('23-01-2014 11:44:59.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('16-06-2014 17:03:19.648000', 'dd-mm-yyyy hh24:mi:ss.ff'), 2, '015004', '项目类型', 'XZSP_PROJECT_TYPE', '8c56947c-ac41-484e-9ad6-a5ae5ccb7104');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('957213a3-a6b4-4d88-995e-ebef567edcd7', to_timestamp('23-01-2014 11:47:48.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('16-06-2014 10:15:46.802000', 'dd-mm-yyyy hh24:mi:ss.ff'), 2, '015005', '办件类型', 'XZSP_BJ_TYPE', '8c56947c-ac41-484e-9ad6-a5ae5ccb7104');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('4fc54293-e165-4cf9-a49d-130f13d427f2', to_timestamp('23-01-2014 11:49:58.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('16-06-2014 10:16:12.427000', 'dd-mm-yyyy hh24:mi:ss.ff'), 2, '015006', '办件属性', 'XZSP_BJ_PROPERTY', '8c56947c-ac41-484e-9ad6-a5ae5ccb7104');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('0b884de2-738b-47fc-823d-e2881b651d2c', to_timestamp('23-01-2014 11:54:12.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('16-06-2014 10:16:44.185000', 'dd-mm-yyyy hh24:mi:ss.ff'), 2, '015007', '面向用户', 'XZSP_FACE_TO_USER', '8c56947c-ac41-484e-9ad6-a5ae5ccb7104');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('664fa0ee-30ca-46f4-ac97-dc1a9293ae9c', to_timestamp('26-01-2014 21:34:17.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('16-06-2014 10:34:25.707000', 'dd-mm-yyyy hh24:mi:ss.ff'), 3, '015008', '证照有效性标识', 'XZSP_IDENTIFY_EFFECTIVE', '8c56947c-ac41-484e-9ad6-a5ae5ccb7104');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('8bd4c579-653d-4cf1-85e6-849635cf37dc', to_timestamp('27-01-2014 11:32:55.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('16-06-2014 10:35:52.058000', 'dd-mm-yyyy hh24:mi:ss.ff'), 2, '015009', '建筑性质', 'XZSP_CONSTRUCT_PROPERTY', '8c56947c-ac41-484e-9ad6-a5ae5ccb7104');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('c35e534e-e2fa-4962-b992-aab6c8a56e9e', to_timestamp('19-02-2014 10:14:24.714000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('19-02-2014 10:14:24.714000', 'dd-mm-yyyy hh24:mi:ss.ff'), 6, null, '动态表单', null, '1cc0201d-f6b1-4a00-824c-86e477160602');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('5d7ee6bc-ce3c-4e0e-8abb-ca7870dd3936', to_timestamp('19-02-2014 10:25:54.699000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('19-02-2014 10:25:54.699000', 'dd-mm-yyyy hh24:mi:ss.ff'), 6, 'REAL_ONLY_RESERVED_DATE1', '只读日期字段1', 'REAL_ONLY_RESERVED_DATE1', 'c35e534e-e2fa-4962-b992-aab6c8a56e9e');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('95bd5a7d-b7b6-4dab-b61a-d19032119829', to_timestamp('19-02-2014 10:26:20.996000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('19-02-2014 10:26:20.996000', 'dd-mm-yyyy hh24:mi:ss.ff'), 6, 'REAL_ONLY_RESERVED_DATE2', '只读日期字段2', 'REAL_ONLY_RESERVED_DATE2', 'c35e534e-e2fa-4962-b992-aab6c8a56e9e');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('ff14bff8-d511-4f42-9dde-2b6ecbffc817', to_timestamp('19-02-2014 10:26:39.077000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('19-02-2014 10:26:39.077000', 'dd-mm-yyyy hh24:mi:ss.ff'), 6, 'REAL_ONLY_RESERVED_DATE3', '只读日期字段3', 'REAL_ONLY_RESERVED_DATE3', 'c35e534e-e2fa-4962-b992-aab6c8a56e9e');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('56264286-7838-4840-893b-9d9eaeecd279', to_timestamp('19-02-2014 10:27:10.607000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('19-02-2014 10:27:10.607000', 'dd-mm-yyyy hh24:mi:ss.ff'), 6, 'REAL_ONLY_RESERVED_DATE4', '只读日期字段4', 'REAL_ONLY_RESERVED_DATE4', 'c35e534e-e2fa-4962-b992-aab6c8a56e9e');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('7912a072-3e49-4c48-a1c6-ed6600432978', to_timestamp('19-02-2014 10:27:32.689000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('19-02-2014 10:27:32.689000', 'dd-mm-yyyy hh24:mi:ss.ff'), 6, 'REAL_ONLY_RESERVED_DATE5', '只读日期字段5', 'REAL_ONLY_RESERVED_DATE5', 'c35e534e-e2fa-4962-b992-aab6c8a56e9e');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('98ebc8ca-ff70-4fb6-9e54-03c78f77c0b0', to_timestamp('19-02-2014 10:33:17.899000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('19-02-2014 10:33:17.899000', 'dd-mm-yyyy hh24:mi:ss.ff'), 6, 'REAL_ONLY_RESERVED_TEXT10', '只读文本字段10', 'REAL_ONLY_RESERVED_TEXT10', 'c35e534e-e2fa-4962-b992-aab6c8a56e9e');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('ce20bb7a-8f40-4e32-b8e3-74aa54800725', to_timestamp('21-02-2014 16:48:43.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('21-02-2014 16:52:03.959000', 'dd-mm-yyyy hh24:mi:ss.ff'), 3, '014004', '常见问题', 'CJWT_SSGL', '48ca3fd5-f28c-4ea4-b27f-84c8d49d9393');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('d74440cb-1c13-4b36-9393-62e1bb95b606', to_timestamp('27-02-2014 15:14:31.212000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('27-02-2014 15:14:31.212000', 'dd-mm-yyyy hh24:mi:ss.ff'), 3, '012001014', '行政审批', null, '7e1223cc-3af2-464f-b563-2f01cbeb6943');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('7c6980c8-838a-4c1a-8d8b-1d3789cf8a93', to_timestamp('05-03-2014 15:44:18.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('05-03-2014 15:44:57.085000', 'dd-mm-yyyy hh24:mi:ss.ff'), 13, 'WORKFLOW_TIME_LIMIT', '办理时限', null, '9ded6b48-3fb3-4447-9431-794322b46166');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('e9fc7b4b-5255-4ab2-95df-cebba4322317', to_timestamp('05-03-2014 16:21:33.653000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('05-03-2014 16:21:33.653000', 'dd-mm-yyyy hh24:mi:ss.ff'), 3, '其它类别', '其它类别', null, 'ce20bb7a-8f40-4e32-b8e3-74aa54800725');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('46e8d0a3-1ccf-430f-b45b-a165205ff240', to_timestamp('14-03-2014 16:31:00.538000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('14-03-2014 16:31:00.538000', 'dd-mm-yyyy hh24:mi:ss.ff'), 12, 'WORKFLOW_RESERVED_TEXT_6', '预留文本字段6(255字符长度)', null, '9ded6b48-3fb3-4447-9431-794322b46166');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('c4478cf0-1821-436b-8c05-9085b317a920', to_timestamp('14-03-2014 16:32:21.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('30-04-2014 10:55:40.988000', 'dd-mm-yyyy hh24:mi:ss.ff'), 13, 'WORKFLOW_RESERVED_TEXT_7', '预留文本字段7(255字符长度)', null, '9ded6b48-3fb3-4447-9431-794322b46166');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('42a15681-810e-4663-8820-e7df65d1adc1', to_timestamp('17-03-2014 19:57:50.704000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('17-03-2014 19:57:50.704000', 'dd-mm-yyyy hh24:mi:ss.ff'), 12, 'WORKFLOW_DUE_TIME', '到期时间', null, '9ded6b48-3fb3-4447-9431-794322b46166');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('66c0435b-86a2-458c-851d-05ce11c58df4', to_timestamp('24-03-2014 14:32:16.687000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('24-03-2014 14:32:16.687000', 'dd-mm-yyyy hh24:mi:ss.ff'), 2, '申请材料接收凭证', '申请材料接收凭证', null, '3ebfa7bf-0646-4090-8556-a278684f57fd');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('8e1fa176-3fc7-4897-8ca8-ad97571f3ff6', to_timestamp('24-03-2014 14:32:46.099000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('24-03-2014 14:32:46.099000', 'dd-mm-yyyy hh24:mi:ss.ff'), 2, '补正材料通知书', '补正材料通知书', null, '3ebfa7bf-0646-4090-8556-a278684f57fd');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('baa0bc06-de07-42dd-92e5-0e1c08ff2893', to_timestamp('24-03-2014 14:33:05.787000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('24-03-2014 14:33:05.787000', 'dd-mm-yyyy hh24:mi:ss.ff'), 2, '审查阶段补正材料通知书', '审查阶段补正材料通知书', null, '3ebfa7bf-0646-4090-8556-a278684f57fd');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('25a2d222-a02e-4f41-8da1-5efbc279971b', to_timestamp('24-03-2014 14:33:33.508000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('24-03-2014 14:33:33.508000', 'dd-mm-yyyy hh24:mi:ss.ff'), 2, '不予行政许可决定书', '不予行政许可决定书', null, '3ebfa7bf-0646-4090-8556-a278684f57fd');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('3e1ded20-22d7-47ba-8f70-c07ebef9c708', to_timestamp('24-03-2014 14:34:19.857000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('24-03-2014 14:34:19.857000', 'dd-mm-yyyy hh24:mi:ss.ff'), 2, '不予受理告知书', '不予受理告知书', null, '3ebfa7bf-0646-4090-8556-a278684f57fd');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('58fb9c9d-4d11-4632-8215-2ef7d6368849', to_timestamp('24-03-2014 14:34:45.280000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('24-03-2014 14:34:45.280000', 'dd-mm-yyyy hh24:mi:ss.ff'), 2, '特别程序告知书', '特别程序告知书', null, '3ebfa7bf-0646-4090-8556-a278684f57fd');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('0bc4cfb1-a744-41a7-a518-4308e3411d38', to_timestamp('25-03-2014 17:25:36.034000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('25-03-2014 17:25:36.034000', 'dd-mm-yyyy hh24:mi:ss.ff'), 2, '项目卡', '项目卡', null, '3ebfa7bf-0646-4090-8556-a278684f57fd');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('2218d711-b625-44e5-8732-55d0af955279', to_timestamp('25-03-2014 20:21:13.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('17-07-2014 16:58:10.085000', 'dd-mm-yyyy hh24:mi:ss.ff'), 6, '015013', '项目性质', 'XZSP_PROJECT_PROPERTY', '8c56947c-ac41-484e-9ad6-a5ae5ccb7104');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('e9e1c0d6-8b2f-433c-81b8-4cc812660255', to_timestamp('26-03-2014 19:30:06.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('18-07-2014 10:24:34.254000', 'dd-mm-yyyy hh24:mi:ss.ff'), 7, '015014', '项目进程', 'XZSP_PROJECT_PROCESS', '8c56947c-ac41-484e-9ad6-a5ae5ccb7104');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('94e14bfc-f644-41fb-ab02-c5675c576a59', to_timestamp('30-03-2014 10:35:20.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('31-03-2014 20:20:17.307000', 'dd-mm-yyyy hh24:mi:ss.ff'), 25, '001004', '业务管理', 'BUSINESS_MANAGEMENT', '0697ef08-6be4-49e8-aac0-79b68d49b531');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('892ffd9b-dadb-40e7-89f0-80e039ed00d2', to_timestamp('30-03-2014 10:41:10.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('31-03-2014 20:28:38.197000', 'dd-mm-yyyy hh24:mi:ss.ff'), 31, 'BUSINESS_OWNER', '部门窗口', null, '94e14bfc-f644-41fb-ab02-c5675c576a59');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('eec0c2a9-5b38-4449-806f-3f4b3391dd78', to_timestamp('30-03-2014 10:45:49.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('31-03-2014 20:29:45.523000', 'dd-mm-yyyy hh24:mi:ss.ff'), 26, 'BUSINESS_RECIPIENT', '发件窗口', null, '94e14bfc-f644-41fb-ab02-c5675c576a59');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('59ac5c43-b4ee-4f93-9b81-4c23c6120e6c', to_timestamp('30-03-2014 10:48:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('31-03-2014 20:29:18.928000', 'dd-mm-yyyy hh24:mi:ss.ff'), 25, 'BUSINESS_SENDER', '登记窗口', null, '94e14bfc-f644-41fb-ab02-c5675c576a59');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('c33a8dbe-27e2-4431-a429-2033b2022c20', to_timestamp('31-03-2014 11:20:53.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('21-07-2014 17:44:43.228000', 'dd-mm-yyyy hh24:mi:ss.ff'), 15, '1', '建设项目联合审批', 'XZSP_PROJECT_PROCESS_JSXM', 'e9e1c0d6-8b2f-433c-81b8-4cc812660255');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('65487bd2-1704-4452-ad95-49cc2cc5d5b7', to_timestamp('31-03-2014 11:21:31.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('18-07-2014 10:24:34.269000', 'dd-mm-yyyy hh24:mi:ss.ff'), 8, '2', '企业注册联合审批', 'XZSP_PROJECT_PROCESS_QYZC', 'e9e1c0d6-8b2f-433c-81b8-4cc812660255');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('28eb81de-42a3-471b-85c5-0c8939e548c7', to_timestamp('10-04-2014 09:55:02.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('17-07-2014 16:57:54.768000', 'dd-mm-yyyy hh24:mi:ss.ff'), 5, '015015', '审阅单位', 'XZSP_REVIEW_UNIT', '8c56947c-ac41-484e-9ad6-a5ae5ccb7104');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('2bb309af-7aee-4e2b-b56b-84e04fb9e7ed', to_timestamp('30-04-2014 10:54:28.510000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('30-04-2014 10:54:28.510000', 'dd-mm-yyyy hh24:mi:ss.ff'), 12, 'WORKFLOW_RESERVED_TEXT_8', '预留文本字段8(255字符长度)', null, '9ded6b48-3fb3-4447-9431-794322b46166');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('fd8adaa3-726d-4dae-bfc2-17e771b54f99', to_timestamp('19-02-2014 10:28:16.144000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('19-02-2014 10:28:16.144000', 'dd-mm-yyyy hh24:mi:ss.ff'), 6, 'REAL_ONLY_RESERVED_NUMBER1', '只读数字字段1', 'REAL_ONLY_RESERVED_NUMBER1', 'c35e534e-e2fa-4962-b992-aab6c8a56e9e');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('027cad45-22ee-4ad6-a783-deb5d3d5ba8d', to_timestamp('19-02-2014 10:28:35.069000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('19-02-2014 10:28:35.069000', 'dd-mm-yyyy hh24:mi:ss.ff'), 6, 'REAL_ONLY_RESERVED_NUMBER2', '只读数字字段2', 'REAL_ONLY_RESERVED_NUMBER2', 'c35e534e-e2fa-4962-b992-aab6c8a56e9e');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('31843159-ab50-4301-b10e-230519cd1eda', to_timestamp('19-02-2014 10:28:55.146000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('19-02-2014 10:28:55.146000', 'dd-mm-yyyy hh24:mi:ss.ff'), 6, 'REAL_ONLY_RESERVED_NUMBER3', '只读数字字段3', 'REAL_ONLY_RESERVED_NUMBER3', 'c35e534e-e2fa-4962-b992-aab6c8a56e9e');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('fe972621-a85f-4052-beeb-ea9f84bd8370', to_timestamp('19-02-2014 10:29:19.482000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('19-02-2014 10:29:19.482000', 'dd-mm-yyyy hh24:mi:ss.ff'), 6, 'REAL_ONLY_RESERVED_NUMBER4', '只读数字字段4', 'REAL_ONLY_RESERVED_NUMBER4', 'c35e534e-e2fa-4962-b992-aab6c8a56e9e');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('c725ec17-3509-4fe8-8755-989610476e68', to_timestamp('19-02-2014 10:29:42.526000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('19-02-2014 10:29:42.526000', 'dd-mm-yyyy hh24:mi:ss.ff'), 6, 'REAL_ONLY_RESERVED_NUMBER5', '只读数字字段5', 'REAL_ONLY_RESERVED_NUMBER5', 'c35e534e-e2fa-4962-b992-aab6c8a56e9e');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('ae2828b6-986a-407c-afce-e2e3c68a9ec0', to_timestamp('19-02-2014 10:30:05.962000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('19-02-2014 10:30:05.962000', 'dd-mm-yyyy hh24:mi:ss.ff'), 6, 'REAL_ONLY_RESERVED_TEXT1', '只读文本字段1', 'REAL_ONLY_RESERVED_TEXT1', 'c35e534e-e2fa-4962-b992-aab6c8a56e9e');
commit;
prompt 500 records committed...
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('f606238c-4abd-4af1-86b7-033ef6ac30fd', to_timestamp('19-02-2014 10:30:24.113000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('19-02-2014 10:30:24.113000', 'dd-mm-yyyy hh24:mi:ss.ff'), 6, 'REAL_ONLY_RESERVED_TEXT2', '只读文本字段2', 'REAL_ONLY_RESERVED_TEXT2', 'c35e534e-e2fa-4962-b992-aab6c8a56e9e');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('fba0a29c-d796-4eeb-aeff-5c271c43dba6', to_timestamp('19-02-2014 10:30:42.166000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('19-02-2014 10:30:42.166000', 'dd-mm-yyyy hh24:mi:ss.ff'), 6, 'REAL_ONLY_RESERVED_TEXT3', '只读文本字段3', 'REAL_ONLY_RESERVED_TEXT3', 'c35e534e-e2fa-4962-b992-aab6c8a56e9e');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('6222c3a2-4104-4ebe-ad6f-eed583c810f6', to_timestamp('19-02-2014 10:31:00.271000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('19-02-2014 10:31:00.271000', 'dd-mm-yyyy hh24:mi:ss.ff'), 6, 'REAL_ONLY_RESERVED_TEXT4', '只读文本字段4', 'REAL_ONLY_RESERVED_TEXT4', 'c35e534e-e2fa-4962-b992-aab6c8a56e9e');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('8850e12b-8d8b-4cec-8347-8bf50a37660a', to_timestamp('19-02-2014 10:31:19.839000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('19-02-2014 10:31:19.839000', 'dd-mm-yyyy hh24:mi:ss.ff'), 6, 'REAL_ONLY_RESERVED_TEXT5', '只读文本字段5', 'REAL_ONLY_RESERVED_TEXT5', 'c35e534e-e2fa-4962-b992-aab6c8a56e9e');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('7bb20f00-273d-484e-ad09-b79e56175ccc', to_timestamp('19-02-2014 10:31:51.476000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('19-02-2014 10:31:51.476000', 'dd-mm-yyyy hh24:mi:ss.ff'), 6, 'REAL_ONLY_RESERVED_TEXT6', '只读文本字段6', 'REAL_ONLY_RESERVED_TEXT6', 'c35e534e-e2fa-4962-b992-aab6c8a56e9e');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('2cabf9bb-2b0e-4dea-bd65-856b16236633', to_timestamp('19-02-2014 10:32:19.323000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('19-02-2014 10:32:19.323000', 'dd-mm-yyyy hh24:mi:ss.ff'), 6, 'REAL_ONLY_RESERVED_TEXT7', '只读文本字段7', 'REAL_ONLY_RESERVED_TEXT7', 'c35e534e-e2fa-4962-b992-aab6c8a56e9e');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('962c9c0e-9ec8-4969-9ffd-1d68aa22021d', to_timestamp('19-02-2014 10:32:41.443000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('19-02-2014 10:32:41.443000', 'dd-mm-yyyy hh24:mi:ss.ff'), 6, 'REAL_ONLY_RESERVED_TEXT8', '只读文本字段8', 'REAL_ONLY_RESERVED_TEXT8', 'c35e534e-e2fa-4962-b992-aab6c8a56e9e');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('67dd8daf-deb6-4783-95f5-5a738626e501', to_timestamp('19-02-2014 10:33:00.509000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('19-02-2014 10:33:00.509000', 'dd-mm-yyyy hh24:mi:ss.ff'), 6, 'REAL_ONLY_RESERVED_TEXT9', '只读文本字段9', 'REAL_ONLY_RESERVED_TEXT9', 'c35e534e-e2fa-4962-b992-aab6c8a56e9e');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('3f15d835-584f-4346-b348-d0a7026da6cf', to_timestamp('21-08-2013 10:04:37.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('29-08-2013 16:46:13.660000', 'dd-mm-yyyy hh24:mi:ss.ff'), 10, 'employ_name_meet', '资源名称（会议名）', null, '5233ad4f-c5a0-4825-808a-f7cab6ef355a');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('0ce26478-1ada-4798-9745-3e73d2470e41', to_timestamp('21-08-2013 10:05:15.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('21-08-2013 10:05:15.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 6, 'employ_begintime', '时间占用开始时间', null, '5233ad4f-c5a0-4825-808a-f7cab6ef355a');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('e93b1f9a-29cb-45b1-8f17-01a43effe079', to_timestamp('21-08-2013 10:06:56.647000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('21-08-2013 10:06:56.647000', 'dd-mm-yyyy hh24:mi:ss.ff'), 6, 'employ_work', '占用时间的事务', null, '5233ad4f-c5a0-4825-808a-f7cab6ef355a');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('da3159a3-6a87-4632-bbc5-0e68480756a8', to_timestamp('03-03-2013 11:25:57.717000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('03-03-2013 11:25:57.717000', 'dd-mm-yyyy hh24:mi:ss.ff'), 9, 'dy_work_task_resolve_detail_type', '任务类型', 'dy_work_task_resolve_detail_type', '92e08e2e-4371-4dd9-92b2-4d0b801b5fa1');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('b1db9b77-61bb-41ae-b047-1d6e05d2632c', to_timestamp('03-03-2013 11:26:59.767000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('03-03-2013 11:26:59.767000', 'dd-mm-yyyy hh24:mi:ss.ff'), 9, 'dy_work_task_resolve_detail_begintime', '计划开始时间', 'dy_work_task_resolve_detail_begintime', '92e08e2e-4371-4dd9-92b2-4d0b801b5fa1');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('9d6fc8d0-ee6e-45c9-af0d-f808280542e8', to_timestamp('03-03-2013 11:25:12.830000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('03-03-2013 11:25:12.830000', 'dd-mm-yyyy hh24:mi:ss.ff'), 9, 'dy_work_task_resolve_detail_name', '任务名称', 'dy_work_task_resolve_detail_name', '92e08e2e-4371-4dd9-92b2-4d0b801b5fa1');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('927e5de2-c663-42de-bd47-1b4b0e2b67ab', to_timestamp('03-03-2013 11:27:40.167000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('03-03-2013 11:27:40.167000', 'dd-mm-yyyy hh24:mi:ss.ff'), 9, 'dy_work_task_resolve_detail_count', '预计工作量', 'dy_work_task_resolve_detail_count', '92e08e2e-4371-4dd9-92b2-4d0b801b5fa1');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('85490ba9-5f8c-49c9-8ddb-39360ddec29d', to_timestamp('03-03-2013 11:26:19.377000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('03-03-2013 11:26:19.377000', 'dd-mm-yyyy hh24:mi:ss.ff'), 9, 'dy_work_task_resolve_detail_target', '目标与要求', 'dy_work_task_resolve_detail_target', '92e08e2e-4371-4dd9-92b2-4d0b801b5fa1');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('2521c55a-c7e1-4926-9988-0be39c056a58', to_timestamp('03-03-2013 11:27:57.423000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('03-03-2013 11:27:57.423000', 'dd-mm-yyyy hh24:mi:ss.ff'), 9, 'dy_work_task_resolve_detail_sharer', '共享者', 'dy_work_task_resolve_detail_sharer', '92e08e2e-4371-4dd9-92b2-4d0b801b5fa1');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('590553d0-7b24-4c86-b69b-79155a2b2fd4', to_timestamp('03-03-2013 11:27:18.597000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('03-03-2013 11:27:18.597000', 'dd-mm-yyyy hh24:mi:ss.ff'), 9, 'dy_work_task_resolve_detail_endtime', '计划完成时间', 'dy_work_task_resolve_detail_endtime', '92e08e2e-4371-4dd9-92b2-4d0b801b5fa1');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('557cdb30-67c2-4403-9a4b-e581f9bde17c', to_timestamp('03-03-2013 11:28:43.537000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('03-03-2013 11:28:43.537000', 'dd-mm-yyyy hh24:mi:ss.ff'), 9, 'dy_work_task_resolve_detail_duty', '责任人', 'dy_work_task_resolve_detail_duty', '92e08e2e-4371-4dd9-92b2-4d0b801b5fa1');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('2ef51b31-9d35-4b0b-b17f-df11b9267c78', to_timestamp('03-03-2013 11:28:24.733000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('03-03-2013 11:28:24.733000', 'dd-mm-yyyy hh24:mi:ss.ff'), 9, 'dy_work_task_resolve_detail_supervisior', '监督人', 'dy_work_task_resolve_detail_supervisior', '92e08e2e-4371-4dd9-92b2-4d0b801b5fa1');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('4a4d3d56-ff16-493d-94fd-c01ea0355cbd', null, null, 'U0000000001', to_timestamp('04-06-2013 09:27:26.040000', 'dd-mm-yyyy hh24:mi:ss.ff'), 12, 'WORKFLOW_OPINION', '办理意见', null, '9ded6b48-3fb3-4447-9431-794322b46166');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('4c0d72dd-d632-4971-be7d-420040a620c5', null, null, 'U0000000001', to_timestamp('04-06-2013 09:27:26.050000', 'dd-mm-yyyy hh24:mi:ss.ff'), 12, 'WORKFLOW_CREATOR_DEPARTMENT', '创建人部门', null, '9ded6b48-3fb3-4447-9431-794322b46166');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('5918a4a9-4a49-4676-a166-68e4e812da35', to_timestamp('20-08-2013 21:00:24.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('20-08-2013 21:03:10.343000', 'dd-mm-yyyy hh24:mi:ss.ff'), 13, 'WORKFLOW_RESERVED_NUMBER_3', '预留数字字段3(浮点型)', null, '9ded6b48-3fb3-4447-9431-794322b46166');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('261c5335-7375-45ce-90a1-4ad328015a7e', to_timestamp('20-08-2013 20:57:20.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('20-08-2013 21:04:03.147000', 'dd-mm-yyyy hh24:mi:ss.ff'), 13, 'WORKFLOW_RESERVED_TEXT_2', '预留文本字段2(64字符长度)', null, '9ded6b48-3fb3-4447-9431-794322b46166');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('1b1b59fa-bdde-4d03-8e65-57cfb0eec851', to_timestamp('20-08-2013 20:59:25.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('20-08-2013 21:02:38.090000', 'dd-mm-yyyy hh24:mi:ss.ff'), 14, 'WORKFLOW_RESERVED_NUMBER_1', '预留数字字段1(整型)', null, '9ded6b48-3fb3-4447-9431-794322b46166');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('07c7fc20-1ab3-44cc-8152-9ed50a430e6c', null, null, 'U0000000001', to_timestamp('04-06-2013 09:27:26.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 12, 'WORKFLOW_CREATOR_POST', '创建人岗位', null, '9ded6b48-3fb3-4447-9431-794322b46166');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('0cf35bf3-1a2b-4e03-ad98-6258b57a1578', null, null, 'U0000000001', to_timestamp('04-06-2013 09:27:26.030000', 'dd-mm-yyyy hh24:mi:ss.ff'), 12, 'WORKFLOW_CURRENT_TASK', '当前环节', null, '9ded6b48-3fb3-4447-9431-794322b46166');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('037ea0e3-b6e5-467f-96df-cb0c595c64ff', null, null, 'U0000000001', to_timestamp('04-06-2013 09:27:26.040000', 'dd-mm-yyyy hh24:mi:ss.ff'), 12, 'WORKFLOW_TITLE', '标题', null, '9ded6b48-3fb3-4447-9431-794322b46166');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('8be1cb7a-7a0d-420f-bd74-a16f6e3bd158', null, null, 'U0000000001', to_timestamp('04-06-2013 09:27:26.010000', 'dd-mm-yyyy hh24:mi:ss.ff'), 12, 'WORKFLOW_NEXT_TASK', '下一环节', null, '9ded6b48-3fb3-4447-9431-794322b46166');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('77dbb1ad-86cf-4c1b-83e5-35f507f7d96b', to_timestamp('20-08-2013 20:56:33.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('20-08-2013 21:03:38.693000', 'dd-mm-yyyy hh24:mi:ss.ff'), 13, 'WORKFLOW_RESERVED_TEXT_1', '预留文本字段1(16字符长度)', null, '9ded6b48-3fb3-4447-9431-794322b46166');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('79b52a5b-041f-4e52-a44c-58ed9b18ac46', null, null, 'U0000000001', to_timestamp('04-06-2013 09:27:26.020000', 'dd-mm-yyyy hh24:mi:ss.ff'), 12, 'WORKFLOW_CREATE_TIME', '创建时间', null, '9ded6b48-3fb3-4447-9431-794322b46166');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('74c976e2-e4d8-4290-9b14-b456ff7639d1', to_timestamp('20-08-2013 21:00:01.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('20-08-2013 21:02:56.337000', 'dd-mm-yyyy hh24:mi:ss.ff'), 13, 'WORKFLOW_RESERVED_NUMBER_2', '预留数字字段2(浮点型)', null, '9ded6b48-3fb3-4447-9431-794322b46166');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('719b6f25-28cd-4775-bf74-77b3925ac379', to_timestamp('20-08-2013 20:58:53.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('20-08-2013 21:12:46.383000', 'dd-mm-yyyy hh24:mi:ss.ff'), 13, 'WORKFLOW_RESERVED_TEXT_5', '预留文本字段5(2000字符长度)', null, '9ded6b48-3fb3-4447-9431-794322b46166');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('6d661d6e-3253-457f-8242-afba2b9ee767', to_timestamp('20-08-2013 21:01:32.663000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('20-08-2013 21:01:32.663000', 'dd-mm-yyyy hh24:mi:ss.ff'), 12, 'WORKFLOW_RESERVED_DATE_2', '预留日期字段2', null, '9ded6b48-3fb3-4447-9431-794322b46166');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('a0481b22-d5aa-4923-9848-712ae19036eb', to_timestamp('20-08-2013 20:58:30.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('20-08-2013 21:11:40.107000', 'dd-mm-yyyy hh24:mi:ss.ff'), 13, 'WORKFLOW_RESERVED_TEXT_4', '预留文本字段4(255字符长度)', null, '9ded6b48-3fb3-4447-9431-794322b46166');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('98d28978-2956-41a6-8ec1-d8421c6d1d6a', null, null, 'U0000000001', to_timestamp('04-06-2013 09:27:26.020000', 'dd-mm-yyyy hh24:mi:ss.ff'), 11, 'WORKFLOW_SERIAL_NO', '流水号', null, '9ded6b48-3fb3-4447-9431-794322b46166');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('db16b44c-52be-4716-a126-5fb8e072fc12', to_timestamp('20-08-2013 21:01:04.347000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('20-08-2013 21:01:04.347000', 'dd-mm-yyyy hh24:mi:ss.ff'), 12, 'WORKFLOW_RESERVED_DATE_1', '预留日期字段1', null, '9ded6b48-3fb3-4447-9431-794322b46166');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('d55a918f-cefd-4f6b-8f36-999dd523c996', to_timestamp('20-08-2013 20:58:11.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('20-08-2013 21:06:58.717000', 'dd-mm-yyyy hh24:mi:ss.ff'), 13, 'WORKFLOW_RESERVED_TEXT_3', '预留文本字段3(64字符长度)', null, '9ded6b48-3fb3-4447-9431-794322b46166');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('cb9aaa41-8ec2-4bb6-9d78-0f6a1e9a552c', null, null, 'U0000000001', to_timestamp('04-06-2013 09:27:26.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 12, 'WORKFLOW_CREATOR', '创建人', null, '9ded6b48-3fb3-4447-9431-794322b46166');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('75cffd7a-1020-4335-940f-a8496b7c68bf', to_timestamp('02-03-2013 13:03:01.447000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('02-03-2013 13:03:01.447000', 'dd-mm-yyyy hh24:mi:ss.ff'), 9, 'dy_work_task_apply_type', '任务类型', 'dy_work_task_apply_type', '1f453123-69bf-4905-aa70-1637bd4b5266');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('cab52b59-76bc-489f-a3bf-c1c9d45b1b74', to_timestamp('02-03-2013 13:04:58.377000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('02-03-2013 13:04:58.377000', 'dd-mm-yyyy hh24:mi:ss.ff'), 9, 'dy_work_task_apply_supervisor', '督办人', 'dy_work_task_apply_supervisor', '1f453123-69bf-4905-aa70-1637bd4b5266');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('c6a6c5ee-94b9-49fd-a63c-a9150ecebd2b', to_timestamp('02-03-2013 13:02:57.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('02-03-2013 13:03:25.670000', 'dd-mm-yyyy hh24:mi:ss.ff'), 10, 'dy_work_task_apply_target', '目标与要求', 'dy_work_task_apply_target', '1f453123-69bf-4905-aa70-1637bd4b5266');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('dab2132b-4716-4f54-8bda-ec7b690a3b90', to_timestamp('02-03-2013 13:06:26.157000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('02-03-2013 13:06:26.157000', 'dd-mm-yyyy hh24:mi:ss.ff'), 9, 'dy_work_task_apply_name', '任务名称', 'dy_work_task_apply_name', '1f453123-69bf-4905-aa70-1637bd4b5266');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('d86dd441-a0ed-4fc3-8638-91b19c2b25fe', to_timestamp('02-03-2013 13:07:17.030000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('02-03-2013 13:07:17.030000', 'dd-mm-yyyy hh24:mi:ss.ff'), 9, 'dy_work_task_apply_count', '预估工作量', 'dy_work_task_apply_count', '1f453123-69bf-4905-aa70-1637bd4b5266');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('24cb091b-2d40-45c7-84b1-303d95390f3e', to_timestamp('02-03-2013 13:05:38.240000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('02-03-2013 13:05:38.240000', 'dd-mm-yyyy hh24:mi:ss.ff'), 9, 'dy_work_task_apply_end_time', '计划完成时间', 'dy_work_task_apply_end_time', '1f453123-69bf-4905-aa70-1637bd4b5266');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('2b4fb458-d50b-40c4-9c53-7cb47d09edc3', to_timestamp('02-03-2013 13:02:35.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('02-03-2013 13:04:27.393000', 'dd-mm-yyyy hh24:mi:ss.ff'), 13, 'dy_work_task_apply_sharer', '共享者', 'dy_work_task_apply_sharer', '1f453123-69bf-4905-aa70-1637bd4b5266');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('3ad09534-ed1c-4ed9-bcb3-e055217e25ef', to_timestamp('02-03-2013 13:05:18.527000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('02-03-2013 13:05:18.527000', 'dd-mm-yyyy hh24:mi:ss.ff'), 9, 'dy_work_task_apply_begin_time', '计划开始时间', 'dy_work_task_apply_begin_time', '1f453123-69bf-4905-aa70-1637bd4b5266');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('ca095901-526b-44ff-b7c6-714fc4bdea7d', to_timestamp('20-07-2014 16:12:41.434000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('20-07-2014 16:12:41.434000', 'dd-mm-yyyy hh24:mi:ss.ff'), 0, '20028', '渔业无线电台执照', null, 'f31c3e8c-71e2-4c48-8c75-74981cd447ea');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('9069246b-2ad6-4fa3-93ab-df4ff2a2fbb1', to_timestamp('20-07-2014 17:18:52.235000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('20-07-2014 17:18:52.235000', 'dd-mm-yyyy hh24:mi:ss.ff'), 0, '30051', '路由勘察报告批复', null, 'f31c3e8c-71e2-4c48-8c75-74981cd447ea');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('96a762ce-79c3-48a0-9c52-c2d69f0ed62f', to_timestamp('20-07-2014 17:19:29.760000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('20-07-2014 17:19:29.760000', 'dd-mm-yyyy hh24:mi:ss.ff'), 0, '20030', '施工许可证', null, 'f31c3e8c-71e2-4c48-8c75-74981cd447ea');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('44ea05dc-dd9d-48aa-a5e2-5f9c78fe219a', to_timestamp('23-07-2014 17:33:53.005000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('23-07-2014 17:33:53.005000', 'dd-mm-yyyy hh24:mi:ss.ff'), 0, 'XZSP', '行政审批', null, 'c6b816e3-557b-4a83-8fdc-81d71718be11');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('028a0ad0-1ee3-401c-9906-8c99fef37b80', to_timestamp('24-07-2014 18:16:14.801000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('24-07-2014 18:16:14.801000', 'dd-mm-yyyy hh24:mi:ss.ff'), 0, '0022', '原件及复印件、复印件', null, 'f84530bb-9584-4f48-81d3-752a41bca701');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('4113e3d6-eb2a-42fc-9174-c85e0f79151a', to_timestamp('20-07-2014 13:38:11.399000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('20-07-2014 13:38:11.399000', 'dd-mm-yyyy hh24:mi:ss.ff'), 0, '20026', '取水许可证', null, 'f31c3e8c-71e2-4c48-8c75-74981cd447ea');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('bffa83f0-4c1f-43ce-a374-37b461a14440', to_timestamp('22-07-2014 19:42:37.562000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('22-07-2014 19:42:37.562000', 'dd-mm-yyyy hh24:mi:ss.ff'), 0, '012007', '页面分类', 'PAGE_TYPE', 'b6285038-465c-4e68-9f1f-bf7ac75957d6');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('44a8f9b1-6abb-4bc1-8689-ffe8394b815b', to_timestamp('22-07-2014 19:42:56.037000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('22-07-2014 19:42:56.037000', 'dd-mm-yyyy hh24:mi:ss.ff'), 0, '1', '建设联审', null, 'bffa83f0-4c1f-43ce-a374-37b461a14440');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('c6b9bbcc-df52-487c-b52f-ed82083f4925', to_timestamp('22-07-2014 19:43:07.211000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('22-07-2014 19:43:07.211000', 'dd-mm-yyyy hh24:mi:ss.ff'), 0, '2', '商事', null, 'bffa83f0-4c1f-43ce-a374-37b461a14440');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('8f9e5abd-f91d-4add-856c-d72a3a5af10d', to_timestamp('22-07-2014 19:43:20.466000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('22-07-2014 19:43:20.466000', 'dd-mm-yyyy hh24:mi:ss.ff'), 0, '3', '其他', null, 'bffa83f0-4c1f-43ce-a374-37b461a14440');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('b7b671a5-61c0-47fc-a7d2-8692703ccfae', to_timestamp('20-07-2014 16:09:05.001000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('20-07-2014 16:09:05.001000', 'dd-mm-yyyy hh24:mi:ss.ff'), 0, '20027', '渔业船舶检验证书', null, 'f31c3e8c-71e2-4c48-8c75-74981cd447ea');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('603a67fb-0ad1-4a28-9017-4180d8fc1dc9', to_timestamp('20-07-2014 16:51:12.974000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('20-07-2014 16:51:12.974000', 'dd-mm-yyyy hh24:mi:ss.ff'), 0, '3004', '关于审批海域使用申请的请示', null, 'f31c3e8c-71e2-4c48-8c75-74981cd447ea');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('35f65f55-6f5e-4d31-af49-025dd5a10b2d', to_timestamp('20-07-2014 16:51:52.630000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('20-07-2014 16:51:52.630000', 'dd-mm-yyyy hh24:mi:ss.ff'), 0, '30049', '审核意见通知书', null, 'f31c3e8c-71e2-4c48-8c75-74981cd447ea');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('a60a5eeb-fc2f-4a8a-a72c-59c73d16ae7f', to_timestamp('20-07-2014 16:52:25.529000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('20-07-2014 16:52:25.529000', 'dd-mm-yyyy hh24:mi:ss.ff'), 0, '30050', '关于审批海域使用申请的请示', null, 'f31c3e8c-71e2-4c48-8c75-74981cd447ea');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('2269c0e4-00c0-43e0-b3d8-22f376654440', to_timestamp('20-07-2014 17:53:30.067000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('20-07-2014 17:53:30.067000', 'dd-mm-yyyy hh24:mi:ss.ff'), 0, '30052', '关于无居民海岛使用的审核意见', null, 'f31c3e8c-71e2-4c48-8c75-74981cd447ea');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('157e9716-2674-474c-87cf-3819c1d349e8', to_timestamp('21-07-2014 10:33:39.746000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('21-07-2014 14:21:53.284000', 'dd-mm-yyyy hh24:mi:ss.ff'), 1, '015027', '申报来源', 'XZSP_SBLY', '8c56947c-ac41-484e-9ad6-a5ae5ccb7104');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('b0ecfe4e-8f5b-434c-8c97-c55dde29d3f6', to_timestamp('21-07-2014 10:35:18.917000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('21-07-2014 19:41:46.366000', 'dd-mm-yyyy hh24:mi:ss.ff'), 1, '1', '内部申报', null, '157e9716-2674-474c-87cf-3819c1d349e8');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('3fe2784a-4f1f-4b1f-876b-f8639d83a2d2', to_timestamp('21-07-2014 10:35:33.095000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('21-07-2014 19:41:55.505000', 'dd-mm-yyyy hh24:mi:ss.ff'), 1, '2', '网上申报', null, '157e9716-2674-474c-87cf-3819c1d349e8');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('d9efd9bf-55bd-4fd0-8cc0-1fe237869ab9', to_timestamp('20-08-2013 10:09:37.273000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('20-08-2013 10:09:37.273000', 'dd-mm-yyyy hh24:mi:ss.ff'), 1, 'DRIVER_RESOURCE', '司机资源', null, '22b1f7de-e06f-416c-9f3d-3f1e5c25b3df');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('8e8409b6-1faf-43eb-947d-cd13be6cc19d', to_timestamp('20-08-2013 10:09:04.490000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('20-08-2013 10:09:04.490000', 'dd-mm-yyyy hh24:mi:ss.ff'), 1, 'MEET_RESOURCE', '会议资源', null, '22b1f7de-e06f-416c-9f3d-3f1e5c25b3df');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('93ba7716-af49-42e7-bfff-61d7ddcd6678', to_timestamp('20-02-2013 12:15:44.953000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('20-02-2013 12:15:44.953000', 'dd-mm-yyyy hh24:mi:ss.ff'), 3, '005003004', '不及格', 'work_evaluate_bad', '3b7fe289-e867-4a4c-a853-614d17bd73c7');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('21d7a02c-10ec-451c-89ff-76fdf253fddc', to_timestamp('20-02-2013 12:13:10.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('20-02-2013 12:14:38.747000', 'dd-mm-yyyy hh24:mi:ss.ff'), 4, '005003001', '优秀', 'work_evaluate_excellent', '3b7fe289-e867-4a4c-a853-614d17bd73c7');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('4d5f101c-7bf7-42c5-9668-7837715ad6fc', to_timestamp('20-02-2013 12:15:27.713000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('20-02-2013 12:15:27.713000', 'dd-mm-yyyy hh24:mi:ss.ff'), 3, '005003003', '及格', 'work_evaluate_pass', '3b7fe289-e867-4a4c-a853-614d17bd73c7');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('64e9523d-845b-4160-be3e-6da22b4a6b14', to_timestamp('20-02-2013 12:15:04.370000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('20-02-2013 12:15:04.370000', 'dd-mm-yyyy hh24:mi:ss.ff'), 3, '005003002', '良好', 'work_evaluate_good', '3b7fe289-e867-4a4c-a853-614d17bd73c7');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('e71960d5-1af3-47f2-9b72-54bdce097523', to_timestamp('28-03-2013 11:56:59.297000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('28-03-2013 11:56:59.297000', 'dd-mm-yyyy hh24:mi:ss.ff'), 3, 'dy_form_id_task_resolve_detail', '工作任务分解之上级任务', 'dy_form_id_task_resolve_detail', '55e8b62f-0643-4bf3-8e12-0360ddc5a294');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('eade0d28-7110-4533-bf0d-4dcb0435f3bc', to_timestamp('25-03-2013 10:29:49.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('25-03-2013 10:31:16.343000', 'dd-mm-yyyy hh24:mi:ss.ff'), 5, 'WORK_TASK_SUB_1', '工作计划从表1', null, '55e8b62f-0643-4bf3-8e12-0360ddc5a294');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('036e3c05-3511-49eb-9223-6c0fec79b6a4', to_timestamp('28-03-2013 11:56:14.563000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('28-03-2013 11:56:14.563000', 'dd-mm-yyyy hh24:mi:ss.ff'), 3, 'dy_form_id_task_cancel_detail', '工作撤销之详细', 'dy_form_id_task_cancel_detail', '55e8b62f-0643-4bf3-8e12-0360ddc5a294');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('054fca46-8349-4f5a-be66-070149b79e86', to_timestamp('25-03-2013 10:31:36.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('25-03-2013 10:31:49.170000', 'dd-mm-yyyy hh24:mi:ss.ff'), 3, 'WORK_TASK_SUB_2', '工作计划从表2', null, '55e8b62f-0643-4bf3-8e12-0360ddc5a294');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('2af474a8-9695-4e35-88fa-ca5c5c29bc7f', to_timestamp('28-03-2013 11:55:43.987000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('28-03-2013 11:55:43.987000', 'dd-mm-yyyy hh24:mi:ss.ff'), 3, 'dy_form_id_report_evaluate', '工作报告之评价', 'dy_form_id_report_evaluate', '55e8b62f-0643-4bf3-8e12-0360ddc5a294');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('454fb8fe-d837-4f1d-af7a-660411c3a3cc', to_timestamp('28-03-2013 11:56:30.047000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('28-03-2013 11:56:30.047000', 'dd-mm-yyyy hh24:mi:ss.ff'), 3, 'dy_form_id_task_change_detail', '工作转办之详细', 'dy_form_id_task_change_detail', '55e8b62f-0643-4bf3-8e12-0360ddc5a294');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('6817f31f-8a04-4b2d-afbe-fb3027225468', to_timestamp('28-03-2013 11:56:44.063000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('28-03-2013 11:56:44.063000', 'dd-mm-yyyy hh24:mi:ss.ff'), 3, 'dy_form_id_task_delay_detail', '工作延期之详细', 'dy_form_id_task_delay_detail', '55e8b62f-0643-4bf3-8e12-0360ddc5a294');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('a1d2ba9f-b6ec-460c-bacf-18414ec24e11', to_timestamp('28-03-2013 11:55:58.673000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('28-03-2013 11:55:58.673000', 'dd-mm-yyyy hh24:mi:ss.ff'), 3, 'dy_form_id_report_plan', '工作报告之计划', 'dy_form_id_report_plan', '55e8b62f-0643-4bf3-8e12-0360ddc5a294');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('9d04fb8c-1803-4f0f-af2b-1a8bf4034dd3', to_timestamp('23-10-2013 10:53:22.927000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('23-10-2013 10:53:22.927000', 'dd-mm-yyyy hh24:mi:ss.ff'), 3, 'WUPIN_SUBTABLE1', '物品管理从表1', null, '55e8b62f-0643-4bf3-8e12-0360ddc5a294');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('30870a5c-bcb5-4418-bd42-8f0958a90c33', to_timestamp('16-05-2013 11:38:08.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('31-05-2013 14:03:51.990000', 'dd-mm-yyyy hh24:mi:ss.ff'), 7, '012002002', '新版OA', null, '60c33986-409a-485a-9f53-04b648e0355d');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('15c669b1-7cfe-4ac3-8a95-2c512b64aa59', null, null, 'U0000000001', to_timestamp('02-05-2013 20:01:13.363000', 'dd-mm-yyyy hh24:mi:ss.ff'), 4, '86a71d80-4ea7-4819-8d72-a7e30bdb2fdc', '表单1', null, '64736d54-1bc1-45a7-a638-3cac021d34e7');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('0f4b1dda-d9f5-42ce-be8c-e3707195ae1e', to_timestamp('27-09-2013 15:46:15.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('27-09-2013 15:53:36.843000', 'dd-mm-yyyy hh24:mi:ss.ff'), 2, '003005002', '请假', 'user_position_state', '6991af23-c3dd-4d69-8661-d5303fa4e34f');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('cff00c48-2512-412b-a556-3924fc8fb0a5', to_timestamp('04-03-2013 18:56:35.987000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('04-03-2013 18:56:35.987000', 'dd-mm-yyyy hh24:mi:ss.ff'), 9, 'dy_work_task_change_detail_duty', '责任人', 'dy_work_task_change_detail_duty', '2cfd8c22-330d-4223-ad5d-25010b8554fe');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('20354f10-cc88-4143-a2c0-98dce22d1da4', to_timestamp('04-03-2013 19:22:00.997000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('04-03-2013 19:22:00.997000', 'dd-mm-yyyy hh24:mi:ss.ff'), 9, 'dy_work_task_change_detail_count', '预计工作量', 'dy_work_task_change_detail_count', '2cfd8c22-330d-4223-ad5d-25010b8554fe');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('24668824-4737-4c17-9865-1e49b1e1d8ba', to_timestamp('04-03-2013 18:53:45.230000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('04-03-2013 18:53:45.230000', 'dd-mm-yyyy hh24:mi:ss.ff'), 9, 'dy_work_task_change_detail_name', '任务名称', 'dy_work_task_change_detail_name', '2cfd8c22-330d-4223-ad5d-25010b8554fe');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('3eac6be8-976f-4640-8608-b04a5c5e45de', to_timestamp('04-03-2013 18:56:17.463000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('04-03-2013 18:56:17.463000', 'dd-mm-yyyy hh24:mi:ss.ff'), 9, 'dy_work_task_change_detail_sharer', '共享人', 'dy_work_task_change_detail_sharer', '2cfd8c22-330d-4223-ad5d-25010b8554fe');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('472a38d9-47c4-41a9-bf81-190a90aa8e04', to_timestamp('04-03-2013 18:55:57.443000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('04-03-2013 18:55:57.443000', 'dd-mm-yyyy hh24:mi:ss.ff'), 9, 'dy_work_task_change_detail_supervisior', '督办人', 'dy_work_task_change_detail_supervisior', '2cfd8c22-330d-4223-ad5d-25010b8554fe');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('e880b7db-98c2-4d74-bd7a-619c8830e0e0', to_timestamp('04-03-2013 18:54:48.810000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('04-03-2013 18:54:48.810000', 'dd-mm-yyyy hh24:mi:ss.ff'), 9, 'dy_work_task_change_detail_target', '目标与要求', 'dy_work_task_change_detail_target', '2cfd8c22-330d-4223-ad5d-25010b8554fe');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('b95ab33e-a90e-42d3-a518-af8925767229', to_timestamp('22-11-2013 10:34:19.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('22-11-2013 10:36:32.867000', 'dd-mm-yyyy hh24:mi:ss.ff'), 3, '5', '待发', null, 'b38ec801-cf4d-4dcd-83b9-754b2c79435c');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('2ac47909-a724-435c-8240-028caf75eaf5', to_timestamp('20-06-2013 14:54:11.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('20-06-2013 15:07:20.357000', 'dd-mm-yyyy hh24:mi:ss.ff'), 3, '4', '创建', null, '3a40505e-d62c-4f14-9f0f-fc0e3e37c838');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('1db30b38-2843-49d4-9bbc-4a512bd16c98', to_timestamp('22-06-2013 14:09:30.057000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('22-06-2013 14:09:30.057000', 'dd-mm-yyyy hh24:mi:ss.ff'), 1, '32', '草稿', null, '3a40505e-d62c-4f14-9f0f-fc0e3e37c838');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('73c8223f-879c-4f55-b302-3be551e93261', to_timestamp('20-06-2013 14:53:49.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('24-06-2013 11:23:24.333000', 'dd-mm-yyyy hh24:mi:ss.ff'), 5, '1', '可读', null, '3a40505e-d62c-4f14-9f0f-fc0e3e37c838');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('48ca3fd5-f28c-4ea4-b27f-84c8d49d9393', to_timestamp('08-12-2013 15:52:33.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('08-12-2013 15:52:39.151000', 'dd-mm-yyyy hh24:mi:ss.ff'), 1, '014', '商事管理', null, null);
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('5b89d0f0-aa01-4bd4-99e5-58a52b750fe0', to_timestamp('08-12-2013 15:53:05.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('08-12-2013 15:54:37.891000', 'dd-mm-yyyy hh24:mi:ss.ff'), 2, '014001', '咨询投诉', 'ADVISORY_COMPLAINTS', '48ca3fd5-f28c-4ea4-b27f-84c8d49d9393');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('7bfaf2f0-42da-46ae-be64-6d07f99f346e', to_timestamp('08-12-2013 15:53:33.061000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('08-12-2013 15:53:33.061000', 'dd-mm-yyyy hh24:mi:ss.ff'), 1, '014002', '自主公示', 'SELF_PUBLICITY_APPLY', '48ca3fd5-f28c-4ea4-b27f-84c8d49d9393');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('9eabfbb9-8e76-4dcd-9b10-402c4bd556a1', to_timestamp('08-12-2013 15:54:10.699000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('08-12-2013 15:54:10.699000', 'dd-mm-yyyy hh24:mi:ss.ff'), 1, '1', '荣誉', null, '7bfaf2f0-42da-46ae-be64-6d07f99f346e');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('84b25565-46a3-442d-b5e0-05fc5962b3f7', to_timestamp('08-12-2013 15:54:21.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('08-12-2013 15:55:23.538000', 'dd-mm-yyyy hh24:mi:ss.ff'), 2, '2', '资质', null, '7bfaf2f0-42da-46ae-be64-6d07f99f346e');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('65343c10-6483-424b-a666-84e0de5ccf30', to_timestamp('08-12-2013 15:55:01.743000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('08-12-2013 15:55:01.743000', 'dd-mm-yyyy hh24:mi:ss.ff'), 2, '1', '咨询', null, '5b89d0f0-aa01-4bd4-99e5-58a52b750fe0');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('d3aebaf7-9ead-4b07-ab33-b90daa2dcf3f', to_timestamp('08-12-2013 15:55:12.211000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('08-12-2013 15:55:12.211000', 'dd-mm-yyyy hh24:mi:ss.ff'), 2, '2', '投诉', null, '5b89d0f0-aa01-4bd4-99e5-58a52b750fe0');
commit;
prompt 600 records committed...
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('3f59caad-5e96-4132-b275-8219d580fcb1', null, null, 'U0010000001', to_timestamp('18-07-2014 10:23:07.663000', 'dd-mm-yyyy hh24:mi:ss.ff'), 3, '1', '个人', 'RECEIVING_OBJECT_PERSON', 'b9344d43-86df-4c14-857f-c1c9d460bbf8');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('8c56947c-ac41-484e-9ad6-a5ae5ccb7104', to_timestamp('23-12-2013 03:48:02.108000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('23-12-2013 03:48:02.108000', 'dd-mm-yyyy hh24:mi:ss.ff'), 1, '015', '行政审批', null, null);
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('b9344d43-86df-4c14-857f-c1c9d460bbf8', to_timestamp('23-12-2013 03:49:34.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('18-07-2014 10:23:07.640000', 'dd-mm-yyyy hh24:mi:ss.ff'), 4, '015001', '受理对象', 'RECEIVING_OBJECT', '8c56947c-ac41-484e-9ad6-a5ae5ccb7104');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('9c374f25-c06d-47db-9ae1-9c364ff8bd20', null, null, 'U0010000001', to_timestamp('18-07-2014 10:23:07.649000', 'dd-mm-yyyy hh24:mi:ss.ff'), 3, '4', '企业', 'RECEIVING_OBJECT_ENTERPRISE', 'b9344d43-86df-4c14-857f-c1c9d460bbf8');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('beaf4dea-835e-43b3-8144-466900eff5b8', null, null, 'U0010000001', to_timestamp('18-07-2014 10:23:07.676000', 'dd-mm-yyyy hh24:mi:ss.ff'), 3, '2', '机关事业单位', 'RECEIVING_OBJECT_ORGANS', 'b9344d43-86df-4c14-857f-c1c9d460bbf8');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('9267664f-1746-40c6-b3c2-35ad68e0e865', null, null, 'U0010000001', to_timestamp('18-07-2014 10:23:07.656000', 'dd-mm-yyyy hh24:mi:ss.ff'), 3, '3', '社团', 'RECEIVING_OBJECT_SOCIETIES', 'b9344d43-86df-4c14-857f-c1c9d460bbf8');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('6d6ea4f5-9c54-4915-93dc-3eb40da324b5', to_timestamp('23-12-2013 03:57:54.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('18-07-2014 10:23:32.500000', 'dd-mm-yyyy hh24:mi:ss.ff'), 7, '015002', '审批事项类型', 'APPROVAL_MATTERS_TYPE', '8c56947c-ac41-484e-9ad6-a5ae5ccb7104');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('111e11a3-b50a-49c2-92db-3e6c4914f47d', null, null, 'U0010000001', to_timestamp('18-07-2014 10:23:32.526000', 'dd-mm-yyyy hh24:mi:ss.ff'), 4, '1', '承诺件', 'APPROVAL_MATTERS_TYPE_COMMITMENT', '6d6ea4f5-9c54-4915-93dc-3eb40da324b5');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('f8cb9f4e-adeb-4c74-897f-859f554947fc', null, null, 'U0010000001', to_timestamp('18-07-2014 10:23:32.519000', 'dd-mm-yyyy hh24:mi:ss.ff'), 4, '2', '即办件', 'APPROVAL_MATTERS_TYPE_IMMEDIATELY', '6d6ea4f5-9c54-4915-93dc-3eb40da324b5');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('de5793e5-f6d2-4281-b89a-e8c0afe9303a', null, null, 'U0010000001', to_timestamp('18-07-2014 10:23:32.552000', 'dd-mm-yyyy hh24:mi:ss.ff'), 4, '3', '上报件', 'APPROVAL_MATTERS_TYPE_REPORTED', '6d6ea4f5-9c54-4915-93dc-3eb40da324b5');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('9f74cd0a-899a-42bf-a78a-f56924fccfbe', null, null, 'U0010000001', to_timestamp('18-07-2014 10:23:32.534000', 'dd-mm-yyyy hh24:mi:ss.ff'), 4, '6', '行政许可事项', null, '6d6ea4f5-9c54-4915-93dc-3eb40da324b5');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('2239d6e8-8a91-49fc-81a6-b0dbff980bbd', to_timestamp('26-12-2013 16:51:42.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('26-12-2013 16:52:18.461000', 'dd-mm-yyyy hh24:mi:ss.ff'), 2, '014003', '审批环节名称', 'SPHJMC', '48ca3fd5-f28c-4ea4-b27f-84c8d49d9393');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('0745f75e-053c-4919-ad7b-13c5c029d1c0', to_timestamp('26-12-2013 16:52:44.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('26-12-2013 17:02:27.929000', 'dd-mm-yyyy hh24:mi:ss.ff'), 3, '0001', '承办', 'SPHJMC_CB', '2239d6e8-8a91-49fc-81a6-b0dbff980bbd');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('2252a568-8078-446f-8a0d-7a2b03a63b1d', to_timestamp('26-12-2013 16:53:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('26-12-2013 17:02:38.554000', 'dd-mm-yyyy hh24:mi:ss.ff'), 3, '0002', '审核', 'SPHJMC_SH', '2239d6e8-8a91-49fc-81a6-b0dbff980bbd');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('c54be52c-e37c-4be1-8a39-201c4ed309c4', to_timestamp('26-12-2013 16:53:10.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('26-12-2013 17:02:49.773000', 'dd-mm-yyyy hh24:mi:ss.ff'), 3, '0003', '批准', 'SPHJMC_PZ', '2239d6e8-8a91-49fc-81a6-b0dbff980bbd');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('7a45d389-02f9-4221-a935-d12e437019d5', null, null, 'U0010000001', to_timestamp('18-07-2014 10:23:32.532000', 'dd-mm-yyyy hh24:mi:ss.ff'), 5, '4', '非行政许可事项', null, '6d6ea4f5-9c54-4915-93dc-3eb40da324b5');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('ee7b5588-16ff-4349-a486-be8495a45c02', null, null, 'U0010000001', to_timestamp('18-07-2014 10:23:32.537000', 'dd-mm-yyyy hh24:mi:ss.ff'), 4, '5', '服务事项', null, '6d6ea4f5-9c54-4915-93dc-3eb40da324b5');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('d85a5754-fd0f-401e-89ad-33b99930303d', null, null, 'U0010000001', to_timestamp('18-07-2014 10:23:32.529000', 'dd-mm-yyyy hh24:mi:ss.ff'), 5, '7', '其他', null, '6d6ea4f5-9c54-4915-93dc-3eb40da324b5');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('2034504b-5972-4be7-8a50-30a18b08d1ae', to_timestamp('23-12-2013 04:06:30.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('18-07-2014 10:23:45.766000', 'dd-mm-yyyy hh24:mi:ss.ff'), 5, '015003', '审批结果材料类型', 'APPROVAL_RESULT_TYPE', '8c56947c-ac41-484e-9ad6-a5ae5ccb7104');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('404d18f4-c8ea-4847-acfe-47cb8bea365f', null, null, 'U0010000001', to_timestamp('18-07-2014 10:23:45.770000', 'dd-mm-yyyy hh24:mi:ss.ff'), 4, '1', '批复', null, '2034504b-5972-4be7-8a50-30a18b08d1ae');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('6b43ed39-4447-4de7-acdb-cc57f1f185ed', to_timestamp('22-11-2013 10:33:27.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('22-11-2013 10:35:44.343000', 'dd-mm-yyyy hh24:mi:ss.ff'), 3, '14', '已收', null, 'b38ec801-cf4d-4dcd-83b9-754b2c79435c');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('4edc1dd7-993a-4311-9ddb-d7bb96465308', to_timestamp('22-11-2013 10:34:02.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('22-11-2013 10:36:16.927000', 'dd-mm-yyyy hh24:mi:ss.ff'), 3, '28', '已拒', null, 'b38ec801-cf4d-4dcd-83b9-754b2c79435c');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('5c9ebe36-c6ab-4370-928b-027e745fbb3f', to_timestamp('22-11-2013 10:33:44.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('22-11-2013 10:36:45.063000', 'dd-mm-yyyy hh24:mi:ss.ff'), 3, '6', '待收', null, 'b38ec801-cf4d-4dcd-83b9-754b2c79435c');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('0e9fbaeb-4417-4d91-b223-8dc73cbf267a', to_timestamp('22-11-2013 10:34:58.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('22-11-2013 10:36:04.230000', 'dd-mm-yyyy hh24:mi:ss.ff'), 3, '15', '已发', null, 'b38ec801-cf4d-4dcd-83b9-754b2c79435c');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('3fece1c0-0c6f-4f56-8c42-64e40413800c', to_timestamp('01-06-2013 15:53:17.547000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('01-06-2013 15:53:17.547000', 'dd-mm-yyyy hh24:mi:ss.ff'), 2, '1', '可读', null, '7c9dfb17-6b27-4e2e-8f8d-b50d83d4fa12');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('c3a52910-aab2-4944-b19c-7c1e65b79966', to_timestamp('31-05-2013 15:19:45.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('09-10-2013 09:55:07.600000', 'dd-mm-yyyy hh24:mi:ss.ff'), 9, '2048', '督办', null, '0c85ea0c-d911-41db-ab8e-c30a872dd41c');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('90d433cd-d7f2-4a15-ab6d-f1527a18bd8a', to_timestamp('31-05-2013 15:23:52.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('31-05-2013 15:24:08.970000', 'dd-mm-yyyy hh24:mi:ss.ff'), 8, '8192', '委托', null, '0c85ea0c-d911-41db-ab8e-c30a872dd41c');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('4461b0ad-b994-40cc-9e9a-c617f86ed440', to_timestamp('31-05-2013 15:22:05.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('09-10-2013 09:54:44.420000', 'dd-mm-yyyy hh24:mi:ss.ff'), 8, '4096', '监控', null, '0c85ea0c-d911-41db-ab8e-c30a872dd41c');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('616db210-b497-46f9-9ffd-12c3f3807b3b', to_timestamp('31-05-2013 15:17:49.947000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('31-05-2013 15:17:49.947000', 'dd-mm-yyyy hh24:mi:ss.ff'), 7, '512', '未阅', null, '0c85ea0c-d911-41db-ab8e-c30a872dd41c');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('13059074-67f3-42c2-bc46-ddecb07bbbc3', to_timestamp('31-05-2013 15:13:04.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('31-05-2013 15:13:31.970000', 'dd-mm-yyyy hh24:mi:ss.ff'), 8, '1024', '已阅', null, '0c85ea0c-d911-41db-ab8e-c30a872dd41c');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('ff5c3d09-90d1-49bc-b125-0a58a3874231', to_timestamp('16-05-2013 09:20:43.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('31-05-2013 15:10:34.447000', 'dd-mm-yyyy hh24:mi:ss.ff'), 7, '128', '已办', null, '0c85ea0c-d911-41db-ab8e-c30a872dd41c');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('e940a19d-35ff-49ab-bc17-60cf2dcfc4c1', to_timestamp('31-05-2013 15:18:41.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('31-05-2013 15:19:03.507000', 'dd-mm-yyyy hh24:mi:ss.ff'), 8, '256', '关注', null, '0c85ea0c-d911-41db-ab8e-c30a872dd41c');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('ea506a84-791b-4bd6-951f-d0998a4c343c', to_timestamp('31-05-2013 15:20:27.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('24-06-2013 10:41:45.090000', 'dd-mm-yyyy hh24:mi:ss.ff'), 8, '32', '草稿', null, '0c85ea0c-d911-41db-ab8e-c30a872dd41c');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('f0dfc3b9-2998-4779-ab54-54f766130811', to_timestamp('16-05-2013 09:17:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('31-05-2013 15:11:01.617000', 'dd-mm-yyyy hh24:mi:ss.ff'), 6, '64', '待办', null, '0c85ea0c-d911-41db-ab8e-c30a872dd41c');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('e408d3da-2994-4341-b92b-c5ef184eabd0', to_timestamp('09-07-2013 10:21:03.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('13-07-2013 16:31:00.343000', 'dd-mm-yyyy hh24:mi:ss.ff'), 2, '32', '草稿', null, '1236782f-a860-4473-bb32-08d224895523');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('09c1f924-d6b4-41df-b5e2-f25ac9a7a60d', to_timestamp('09-07-2013 10:21:36.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('13-07-2013 16:30:42.343000', 'dd-mm-yyyy hh24:mi:ss.ff'), 2, '2048', '督办', null, '1236782f-a860-4473-bb32-08d224895523');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('67249343-0909-46a4-bbe8-5ccb678fcffd', to_timestamp('09-07-2013 10:22:37.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('13-07-2013 16:31:57.157000', 'dd-mm-yyyy hh24:mi:ss.ff'), 2, '64', '待办', null, '1236782f-a860-4473-bb32-08d224895523');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('8cd725ee-2f1a-4037-b039-f6d448d16529', to_timestamp('13-07-2013 12:38:38.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('13-07-2013 16:31:19.157000', 'dd-mm-yyyy hh24:mi:ss.ff'), 2, '32768', '发件箱', null, '1236782f-a860-4473-bb32-08d224895523');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('9b565897-5e0d-4b29-bc2d-d4c9d1e20ffe', to_timestamp('13-07-2013 12:38:12.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('13-07-2013 16:30:20.627000', 'dd-mm-yyyy hh24:mi:ss.ff'), 2, '16384', '收件箱', null, '1236782f-a860-4473-bb32-08d224895523');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('b7e6ba1b-b4bc-4fdb-8b5f-76b5dc63b8b8', to_timestamp('09-07-2013 10:23:10.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('13-07-2013 16:31:36.313000', 'dd-mm-yyyy hh24:mi:ss.ff'), 2, '4', '创建', null, '1236782f-a860-4473-bb32-08d224895523');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('bf677d94-89d4-49e7-a626-3a40a0e55866', to_timestamp('09-07-2013 10:23:24.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('13-07-2013 16:32:23.983000', 'dd-mm-yyyy hh24:mi:ss.ff'), 2, '8', '删除', null, '1236782f-a860-4473-bb32-08d224895523');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('b5c3c8c0-b438-4bd2-a2a0-6505bad631b4', to_timestamp('27-09-2013 15:46:48.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('27-09-2013 15:53:45.317000', 'dd-mm-yyyy hh24:mi:ss.ff'), 2, '003005003', '出差', 'user_position_state', '6991af23-c3dd-4d69-8661-d5303fa4e34f');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('bbdf1c71-126c-4235-927a-b5b2b05a98f8', to_timestamp('27-09-2013 15:45:39.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('27-09-2013 15:53:28.220000', 'dd-mm-yyyy hh24:mi:ss.ff'), 2, '003005001', '在岗', 'user_position_state', '6991af23-c3dd-4d69-8661-d5303fa4e34f');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('a627ea5e-3a3c-453d-9214-391bff376129', to_timestamp('27-09-2013 15:47:02.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('27-09-2013 15:54:02.313000', 'dd-mm-yyyy hh24:mi:ss.ff'), 2, '003005004', '外出', 'user_position_state', '6991af23-c3dd-4d69-8661-d5303fa4e34f');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('df72a458-ef73-4816-b423-25285e09cad2', to_timestamp('27-09-2013 15:47:26.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('27-09-2013 15:54:09.333000', 'dd-mm-yyyy hh24:mi:ss.ff'), 2, '003005005', '离岗', 'user_position_state', '6991af23-c3dd-4d69-8661-d5303fa4e34f');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('756b72e6-7996-451d-9352-aeebf85e60c0', to_timestamp('14-04-2013 11:17:46.777000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('14-04-2013 11:17:46.777000', 'dd-mm-yyyy hh24:mi:ss.ff'), 1, 'SCHEDULE', '公文交换', null, '6f3c6d08-8a57-40c6-8808-edd2daf59b87');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('0dea400f-b7ea-4a10-b32c-d2605c087121', to_timestamp('14-04-2013 11:18:18.717000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('14-04-2013 11:18:18.717000', 'dd-mm-yyyy hh24:mi:ss.ff'), 1, 'WORKFLOW', '流程管理', null, '6f3c6d08-8a57-40c6-8808-edd2daf59b87');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('33ca485d-4cba-45b9-9809-f2fccc5160b4', null, null, 'U0000000001', to_timestamp('16-08-2013 17:13:43.717000', 'dd-mm-yyyy hh24:mi:ss.ff'), 2, '012001001', '邮件模块', null, '7e1223cc-3af2-464f-b563-2f01cbeb6943');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('65e05332-f823-4fe3-a46f-2a7c0d20f908', to_timestamp('27-11-2013 14:51:32.240000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('27-11-2013 14:51:32.240000', 'dd-mm-yyyy hh24:mi:ss.ff'), 3, '012001013', '事项管理', null, '7e1223cc-3af2-464f-b563-2f01cbeb6943');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('66c8ed41-47a6-4056-b120-020e877dbf0c', to_timestamp('16-08-2013 17:16:38.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('16-08-2013 17:19:55.343000', 'dd-mm-yyyy hh24:mi:ss.ff'), 4, '012001004', '通讯录', null, '7e1223cc-3af2-464f-b563-2f01cbeb6943');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('0547ad34-4531-4616-a07a-0ba4b792cdf4', to_timestamp('16-08-2013 17:14:24.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('16-08-2013 17:20:03.627000', 'dd-mm-yyyy hh24:mi:ss.ff'), 5, '012001005', '公文交换', null, '7e1223cc-3af2-464f-b563-2f01cbeb6943');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('1f1cd417-f01b-4852-a474-bd3072ba9bd9', to_timestamp('09-09-2013 14:16:04.673000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('09-09-2013 14:16:04.673000', 'dd-mm-yyyy hh24:mi:ss.ff'), 3, '012001008', '日程模块', null, '7e1223cc-3af2-464f-b563-2f01cbeb6943');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('21cc62ed-4fb9-4a60-abdc-9fb78864cf63', to_timestamp('09-09-2013 15:02:59.030000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('09-09-2013 15:02:59.030000', 'dd-mm-yyyy hh24:mi:ss.ff'), 3, '012001012', '其他模块', null, '7e1223cc-3af2-464f-b563-2f01cbeb6943');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('9d13ce26-b715-4032-bbf2-84966d7c3a9b', null, null, 'U0000000001', to_timestamp('09-09-2013 14:17:43.703000', 'dd-mm-yyyy hh24:mi:ss.ff'), 5, '012001003', '流程模块', null, '7e1223cc-3af2-464f-b563-2f01cbeb6943');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('9d4589e9-59d8-4675-8a60-782c89cd38cd', to_timestamp('09-09-2013 14:49:54.063000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('09-09-2013 14:49:54.063000', 'dd-mm-yyyy hh24:mi:ss.ff'), 3, '012001011', '静态模块', null, '7e1223cc-3af2-464f-b563-2f01cbeb6943');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('afb12e62-210b-452b-8e65-e776a5b8e9ce', to_timestamp('21-11-2013 10:02:52.140000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('21-11-2013 10:02:52.140000', 'dd-mm-yyyy hh24:mi:ss.ff'), 3, '012001011', '数据交换', null, '7e1223cc-3af2-464f-b563-2f01cbeb6943');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('d6d0ce55-675e-4c94-963a-511269cde272', to_timestamp('16-08-2013 17:20:43.217000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('16-08-2013 17:20:43.217000', 'dd-mm-yyyy hh24:mi:ss.ff'), 3, '012001006', '文件管理', null, '7e1223cc-3af2-464f-b563-2f01cbeb6943');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('cf5a9cdc-3b19-4edb-8ea0-13eb46a00941', to_timestamp('06-11-2013 21:02:08.580000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('06-11-2013 21:02:08.580000', 'dd-mm-yyyy hh24:mi:ss.ff'), 3, '012001013', '物品模块', null, '7e1223cc-3af2-464f-b563-2f01cbeb6943');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('fd2d2eed-47a1-4756-85c1-50333a54ea1e', null, null, 'U0000000001', to_timestamp('16-08-2013 17:15:14.250000', 'dd-mm-yyyy hh24:mi:ss.ff'), 3, '012001002', '消息模块', null, '7e1223cc-3af2-464f-b563-2f01cbeb6943');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('c62036f9-edbf-43c9-a85d-603b592bdb90', to_timestamp('22-04-2013 17:48:29.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('16-05-2013 09:00:21.233000', 'dd-mm-yyyy hh24:mi:ss.ff'), 2, '2', '可写', null, 'a2afe3cb-4002-4be1-af1e-d08b0ec122b0');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('b38ec801-cf4d-4dcd-83b9-754b2c79435c', to_timestamp('22-11-2013 10:32:02.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('22-11-2013 10:33:03.443000', 'dd-mm-yyyy hh24:mi:ss.ff'), 2, '003003005', '数据交换', null, 'a2afe3cb-4002-4be1-af1e-d08b0ec122b0');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('b486c533-0015-41f8-88d2-6e498ad26258', to_timestamp('22-04-2013 17:49:45.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('16-05-2013 09:00:05.360000', 'dd-mm-yyyy hh24:mi:ss.ff'), 2, '16', '管理', null, 'a2afe3cb-4002-4be1-af1e-d08b0ec122b0');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('c106b95d-f122-4447-ab30-fb172d793761', to_timestamp('22-04-2013 17:49:16.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('16-05-2013 09:02:20.390000', 'dd-mm-yyyy hh24:mi:ss.ff'), 2, '8', '删除', null, 'a2afe3cb-4002-4be1-af1e-d08b0ec122b0');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('95b4deea-9bac-49d7-98ea-ce581f540fb3', to_timestamp('22-04-2013 17:48:55.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('20-06-2013 15:03:19.210000', 'dd-mm-yyyy hh24:mi:ss.ff'), 2, '4', '创建', null, 'a2afe3cb-4002-4be1-af1e-d08b0ec122b0');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('7c9dfb17-6b27-4e2e-8f8d-b50d83d4fa12', to_timestamp('01-06-2013 15:52:25.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('01-06-2013 15:52:42.400000', 'dd-mm-yyyy hh24:mi:ss.ff'), 2, '003003002', '通讯录', null, 'a2afe3cb-4002-4be1-af1e-d08b0ec122b0');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('843951b9-9403-47e1-b990-4cab5936f57c', to_timestamp('22-04-2013 17:47:44.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('28-04-2013 14:33:41.237000', 'dd-mm-yyyy hh24:mi:ss.ff'), 2, '1', '可读', null, 'a2afe3cb-4002-4be1-af1e-d08b0ec122b0');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('0c85ea0c-d911-41db-ab8e-c30a872dd41c', to_timestamp('16-05-2013 09:14:56.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('16-05-2013 09:26:57.750000', 'dd-mm-yyyy hh24:mi:ss.ff'), 7, '003003001', '工作流', 'DATA_PERMISSION_WORKFLOW', 'a2afe3cb-4002-4be1-af1e-d08b0ec122b0');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('1236782f-a860-4473-bb32-08d224895523', to_timestamp('09-07-2013 10:20:42.687000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('09-07-2013 10:20:42.687000', 'dd-mm-yyyy hh24:mi:ss.ff'), 1, '003003004', '公文交换', null, 'a2afe3cb-4002-4be1-af1e-d08b0ec122b0');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('3a40505e-d62c-4f14-9f0f-fc0e3e37c838', to_timestamp('20-06-2013 14:53:24.037000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('20-06-2013 14:53:24.037000', 'dd-mm-yyyy hh24:mi:ss.ff'), 1, '003003003', '邮件', null, 'a2afe3cb-4002-4be1-af1e-d08b0ec122b0');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('e5f97bf1-a736-49c3-a5dc-889447a4e7d4', to_timestamp('20-03-2013 21:13:03.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('21-03-2013 09:00:26.417000', 'dd-mm-yyyy hh24:mi:ss.ff'), 21, 'DYBTN_WF_MONITOR', '工作流督办', null, 'c53e0678-972d-4142-8385-1b123dd961b9');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('4918d23a-b218-4f45-8862-923d6b4a83be', to_timestamp('20-03-2013 21:09:22.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('21-03-2013 16:23:16.583000', 'dd-mm-yyyy hh24:mi:ss.ff'), 21, 'DYBTN_WF_TODO', '工作流待办', null, 'c53e0678-972d-4142-8385-1b123dd961b9');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('4efd27f7-8e08-49df-aeb3-6f709a8becef', null, null, 'U0000000001', to_timestamp('26-03-2013 01:47:02.657000', 'dd-mm-yyyy hh24:mi:ss.ff'), 22, 'DYBTN_WF_ADMIN', '工作流监控', null, 'c53e0678-972d-4142-8385-1b123dd961b9');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('94aa9ee8-b5f7-4856-9a3c-5f3a93efd3bc', to_timestamp('20-03-2013 21:10:26.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('21-03-2013 08:59:52.980000', 'dd-mm-yyyy hh24:mi:ss.ff'), 20, 'DYBTN_WF_DONE', '工作流已办', null, 'c53e0678-972d-4142-8385-1b123dd961b9');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('7eaec2bc-b691-4cef-a3b7-1f1fe9d4b572', to_timestamp('22-04-2013 19:45:52.197000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('22-04-2013 19:45:52.197000', 'dd-mm-yyyy hh24:mi:ss.ff'), 2, 'SCHEDULE', '日程', null, 'c6b816e3-557b-4a83-8fdc-81d71718be11');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('73060bbb-2c94-43fe-981f-bcda2a2abde5', to_timestamp('22-04-2013 19:46:15.077000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('22-04-2013 19:46:15.077000', 'dd-mm-yyyy hh24:mi:ss.ff'), 2, 'EXCHANGE', '公文交换', null, 'c6b816e3-557b-4a83-8fdc-81d71718be11');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('d982c473-c439-4fd0-a05a-08ccf0016097', to_timestamp('22-04-2013 19:04:28.693000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('22-04-2013 19:04:28.693000', 'dd-mm-yyyy hh24:mi:ss.ff'), 2, 'MAIL', '邮件', null, 'c6b816e3-557b-4a83-8fdc-81d71718be11');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('ad9527b3-0829-463c-acaa-5f810a3228d1', to_timestamp('21-11-2013 12:05:01.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('21-11-2013 12:05:22.977000', 'dd-mm-yyyy hh24:mi:ss.ff'), 5, 'DATAEXCHANGE', '数据交换', null, 'c6b816e3-557b-4a83-8fdc-81d71718be11');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('3da17536-04f3-42e8-b950-4b008bce1e21', to_timestamp('19-11-2013 10:08:14.707000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('19-11-2013 10:08:14.707000', 'dd-mm-yyyy hh24:mi:ss.ff'), 4, 'DISPOSE', '配置信息', null, 'c6b816e3-557b-4a83-8fdc-81d71718be11');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('5d559eaf-7700-4e74-a8d8-ccb63676064b', to_timestamp('22-04-2013 18:29:29.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('22-04-2013 19:02:06.387000', 'dd-mm-yyyy hh24:mi:ss.ff'), 4, 'ORG', '组织机构', null, 'c6b816e3-557b-4a83-8fdc-81d71718be11');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('588f115e-0224-42b0-9fd5-54c8471554fe', to_timestamp('22-04-2013 19:51:42.370000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('22-04-2013 19:51:42.370000', 'dd-mm-yyyy hh24:mi:ss.ff'), 2, 'EXAM', '在线考试', null, 'c6b816e3-557b-4a83-8fdc-81d71718be11');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('440b94fb-14dd-4f07-a5c5-60c3a1eccd6e', to_timestamp('01-06-2013 15:30:05.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('01-06-2013 17:25:57.317000', 'dd-mm-yyyy hh24:mi:ss.ff'), 5, 'CONTACTS', '通讯录', null, 'c6b816e3-557b-4a83-8fdc-81d71718be11');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('376b9e9d-bd10-403d-9d62-a0cb6a4b3dc2', to_timestamp('22-04-2013 19:03:05.883000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('22-04-2013 19:03:05.883000', 'dd-mm-yyyy hh24:mi:ss.ff'), 2, 'BASICDATA', '基础数据', null, 'c6b816e3-557b-4a83-8fdc-81d71718be11');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('381da5c5-6552-4325-8659-9d6eeaa29daf', to_timestamp('22-04-2013 19:04:49.340000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('22-04-2013 19:04:49.340000', 'dd-mm-yyyy hh24:mi:ss.ff'), 2, 'FILE', '文件管理', null, 'c6b816e3-557b-4a83-8fdc-81d71718be11');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('1975aac3-7ef0-4ac2-a3a2-89e395ceeac7', to_timestamp('22-04-2013 19:52:06.560000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('22-04-2013 19:52:06.560000', 'dd-mm-yyyy hh24:mi:ss.ff'), 2, 'WORKTASK', '工作计划管理', null, 'c6b816e3-557b-4a83-8fdc-81d71718be11');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('29143673-0b42-441b-b893-04746812a947', to_timestamp('22-04-2013 19:03:35.550000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('22-04-2013 19:03:35.550000', 'dd-mm-yyyy hh24:mi:ss.ff'), 2, 'WORKFLOW', '工作流程', null, 'c6b816e3-557b-4a83-8fdc-81d71718be11');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('dfc0a523-6fcd-4045-93dd-5dfb413d9709', to_timestamp('22-04-2013 18:31:05.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('22-04-2013 19:02:22.207000', 'dd-mm-yyyy hh24:mi:ss.ff'), 4, 'SECURITY', '权限管理', null, 'c6b816e3-557b-4a83-8fdc-81d71718be11');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('fd9da2ef-96b2-4bdc-aac3-95b838fcda1c', to_timestamp('22-04-2013 19:55:30.100000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('22-04-2013 19:55:30.100000', 'dd-mm-yyyy hh24:mi:ss.ff'), 2, 'DYTABLE', '动态表单', null, 'c6b816e3-557b-4a83-8fdc-81d71718be11');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('2c8e486e-5bc1-472f-af23-d0871692947a', to_timestamp('03-03-2013 13:19:51.070000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('03-03-2013 13:19:51.070000', 'dd-mm-yyyy hh24:mi:ss.ff'), 9, 'dy_meeting_summary_detail_count', '预估工作量', 'dy_meeting_summary_detail_count', 'd4137060-b297-4f4e-82ae-fc88599d9d76');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('2a162423-1526-423a-b6ac-ed4542f3df99', to_timestamp('20-07-2014 16:14:54.403000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('20-07-2014 16:14:54.403000', 'dd-mm-yyyy hh24:mi:ss.ff'), 0, '10003', '渔业专用航标 设置（搬迁/拆除）申请表', null, 'f31c3e8c-71e2-4c48-8c75-74981cd447ea');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('ba02a07c-1e3f-40c1-9edd-c2ec4db00a06', to_timestamp('20-07-2014 16:19:21.350000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('20-07-2014 16:19:21.350000', 'dd-mm-yyyy hh24:mi:ss.ff'), 0, '20029', '渔业船员证书', null, 'f31c3e8c-71e2-4c48-8c75-74981cd447ea');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('d0367e8e-0295-47cb-938e-e7e13c1f0fdc', to_timestamp('03-08-2014 16:28:46.633000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('03-08-2014 16:31:21.637000', 'dd-mm-yyyy hh24:mi:ss.ff'), 2, '015013001007', '用地规划许可阶段', null, 'c33a8dbe-27e2-4431-a429-2033b2022c20');
insert into CD_DATA_DICT (uuid, create_time, creator, modifier, modify_time, rec_ver, code, name, type, parent_uuid)
values ('e9f15b93-017d-4d95-a68f-753d0a9e53e7', to_timestamp('26-07-2014 15:34:24.667000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('26-07-2014 15:34:24.667000', 'dd-mm-yyyy hh24:mi:ss.ff'), 0, 'WORKFLOW_RESERVED_TEXT_10', '预留文本字段10(255字符长度)', null, '9ded6b48-3fb3-4447-9431-794322b46166');
commit;
prompt 692 records loaded
prompt Loading CD_DATA_DICT_ATTR...
insert into CD_DATA_DICT_ATTR (uuid, create_time, creator, modifier, modify_time, rec_ver, attr_order, name, value, data_dict_uuid)
values ('57853902-ae12-465b-b66d-0b5cbf64a87b', to_timestamp('16-05-2013 16:07:25.780000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('16-05-2013 16:07:25.780000', 'dd-mm-yyyy hh24:mi:ss.ff'), 0, 0, 'uuid', 'ba6c79bd-1e60-446d-a70b-e1eb16bb5805', '7bebd77c-6012-4b3e-b8e1-afedc24a8982');
insert into CD_DATA_DICT_ATTR (uuid, create_time, creator, modifier, modify_time, rec_ver, attr_order, name, value, data_dict_uuid)
values ('5f8b01a0-071b-4f31-894b-1f678e222567', null, null, 'U0000000001', to_timestamp('15-05-2013 11:28:14.170000', 'dd-mm-yyyy hh24:mi:ss.ff'), 1, 0, 'icon', '/resources/pt/images/cms/gl_icon2.jpg', 'f3c28ab1-789c-4499-bbd1-9a2cf87677c1');
insert into CD_DATA_DICT_ATTR (uuid, create_time, creator, modifier, modify_time, rec_ver, attr_order, name, value, data_dict_uuid)
values ('6729a654-3549-4f01-a69d-8b94b5419db9', to_timestamp('22-11-2013 10:36:16.927000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('22-11-2013 10:36:16.927000', 'dd-mm-yyyy hh24:mi:ss.ff'), 0, 0, '个人', 'person', '4edc1dd7-993a-4311-9ddb-d7bb96465308');
insert into CD_DATA_DICT_ATTR (uuid, create_time, creator, modifier, modify_time, rec_ver, attr_order, name, value, data_dict_uuid)
values ('6bda0266-6636-401b-88f2-0058653274fe', null, null, 'U0000000001', to_timestamp('16-05-2013 16:05:53.157000', 'dd-mm-yyyy hh24:mi:ss.ff'), 2, 0, 'uuid', '6d06eb30-68be-49fc-a96e-364e6fe77899', '266a5185-b56c-49f4-906c-73711b6b4321');
insert into CD_DATA_DICT_ATTR (uuid, create_time, creator, modifier, modify_time, rec_ver, attr_order, name, value, data_dict_uuid)
values ('7239e78b-8b83-4bda-adec-0971ad2cd9d3', to_timestamp('16-05-2013 10:10:11.483000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('16-05-2013 10:10:11.483000', 'dd-mm-yyyy hh24:mi:ss.ff'), 0, 0, 'toid', 'flow_view', 'f3c28ab1-789c-4499-bbd1-9a2cf87677c1');
insert into CD_DATA_DICT_ATTR (uuid, create_time, creator, modifier, modify_time, rec_ver, attr_order, name, value, data_dict_uuid)
values ('73a7299d-d279-4690-8346-8e8374026881', to_timestamp('31-05-2013 15:19:03.507000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('31-05-2013 15:19:03.507000', 'dd-mm-yyyy hh24:mi:ss.ff'), 0, 0, '个人', 'person', 'e940a19d-35ff-49ab-bc17-60cf2dcfc4c1');
insert into CD_DATA_DICT_ATTR (uuid, create_time, creator, modifier, modify_time, rec_ver, attr_order, name, value, data_dict_uuid)
values ('755cf3bc-d8ad-471a-9ffc-0393fac91abc', null, null, 'U0000000001', to_timestamp('09-10-2013 09:54:44.453000', 'dd-mm-yyyy hh24:mi:ss.ff'), 1, 0, '个人', 'person', '4461b0ad-b994-40cc-9e9a-c617f86ed440');
insert into CD_DATA_DICT_ATTR (uuid, create_time, creator, modifier, modify_time, rec_ver, attr_order, name, value, data_dict_uuid)
values ('7b9d89d9-898f-4277-98c9-4397ec4d37f8', to_timestamp('13-07-2013 16:31:57.157000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('13-07-2013 16:31:57.157000', 'dd-mm-yyyy hh24:mi:ss.ff'), 0, 0, '个人', 'person', '67249343-0909-46a4-bbe8-5ccb678fcffd');
insert into CD_DATA_DICT_ATTR (uuid, create_time, creator, modifier, modify_time, rec_ver, attr_order, name, value, data_dict_uuid)
values ('850ce822-2d1a-4195-b3a7-4dcd8b8d45dd', to_timestamp('22-11-2013 10:36:32.867000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('22-11-2013 10:36:32.867000', 'dd-mm-yyyy hh24:mi:ss.ff'), 0, 0, '个人', 'person', 'b95ab33e-a90e-42d3-a518-af8925767229');
insert into CD_DATA_DICT_ATTR (uuid, create_time, creator, modifier, modify_time, rec_ver, attr_order, name, value, data_dict_uuid)
values ('90f23e28-aeee-4b8a-a417-f0184f4870bd', null, null, 'U0000000001', to_timestamp('20-06-2013 15:07:20.363000', 'dd-mm-yyyy hh24:mi:ss.ff'), 1, 0, '全部', 'all', '2ac47909-a724-435c-8240-028caf75eaf5');
insert into CD_DATA_DICT_ATTR (uuid, create_time, creator, modifier, modify_time, rec_ver, attr_order, name, value, data_dict_uuid)
values ('9940f387-eb0b-49e2-a12a-d8c9dadfc267', to_timestamp('13-07-2013 16:31:19.157000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('13-07-2013 16:31:19.157000', 'dd-mm-yyyy hh24:mi:ss.ff'), 0, 0, '个人', 'person', '8cd725ee-2f1a-4037-b039-f6d448d16529');
insert into CD_DATA_DICT_ATTR (uuid, create_time, creator, modifier, modify_time, rec_ver, attr_order, name, value, data_dict_uuid)
values ('a1a7035e-cfa9-4152-943a-1aefd4a5e0dc', to_timestamp('22-11-2013 10:35:44.413000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('22-11-2013 10:35:44.413000', 'dd-mm-yyyy hh24:mi:ss.ff'), 0, 0, '个人', 'person', '6b43ed39-4447-4de7-acdb-cc57f1f185ed');
insert into CD_DATA_DICT_ATTR (uuid, create_time, creator, modifier, modify_time, rec_ver, attr_order, name, value, data_dict_uuid)
values ('a898737a-b1bd-4d9b-92bd-35a120c969e6', to_timestamp('20-06-2013 15:03:19.210000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('20-06-2013 15:03:19.210000', 'dd-mm-yyyy hh24:mi:ss.ff'), 0, 0, '个人', 'person', '95b4deea-9bac-49d7-98ea-ce581f540fb3');
insert into CD_DATA_DICT_ATTR (uuid, create_time, creator, modifier, modify_time, rec_ver, attr_order, name, value, data_dict_uuid)
values ('b3508c2a-cb30-4483-97a5-9c5a677f898a', to_timestamp('16-05-2013 16:06:59.390000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('16-05-2013 16:06:59.390000', 'dd-mm-yyyy hh24:mi:ss.ff'), 0, 0, 'uuid', '68d63a55-7ac5-498f-9d61-14ed107d06ac', '8d684570-97e3-4e98-b0bd-70128c3d3e91');
insert into CD_DATA_DICT_ATTR (uuid, create_time, creator, modifier, modify_time, rec_ver, attr_order, name, value, data_dict_uuid)
values ('bb6f2177-7b99-4ded-8b44-8b858911bdaf', to_timestamp('31-05-2013 15:11:01.617000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('31-05-2013 15:11:01.617000', 'dd-mm-yyyy hh24:mi:ss.ff'), 0, 0, '个人', 'person', 'f0dfc3b9-2998-4779-ab54-54f766130811');
insert into CD_DATA_DICT_ATTR (uuid, create_time, creator, modifier, modify_time, rec_ver, attr_order, name, value, data_dict_uuid)
values ('bc921a85-8c94-4633-879f-eb9ccdd999be', to_timestamp('01-06-2013 15:53:17.553000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('01-06-2013 15:53:17.553000', 'dd-mm-yyyy hh24:mi:ss.ff'), 0, 0, '全部', 'all', '3fece1c0-0c6f-4f56-8c42-64e40413800c');
insert into CD_DATA_DICT_ATTR (uuid, create_time, creator, modifier, modify_time, rec_ver, attr_order, name, value, data_dict_uuid)
values ('c47003b8-427f-424c-a04c-c05957c97a80', to_timestamp('13-07-2013 16:31:36.313000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('13-07-2013 16:31:36.313000', 'dd-mm-yyyy hh24:mi:ss.ff'), 0, 0, '个人', 'person', 'b7e6ba1b-b4bc-4fdb-8b5f-76b5dc63b8b8');
insert into CD_DATA_DICT_ATTR (uuid, create_time, creator, modifier, modify_time, rec_ver, attr_order, name, value, data_dict_uuid)
values ('c485184e-203a-4c01-82be-e51f4ed98b65', null, null, 'U0000000001', to_timestamp('09-10-2013 09:55:07.607000', 'dd-mm-yyyy hh24:mi:ss.ff'), 2, 0, '个人', 'person', 'c3a52910-aab2-4944-b19c-7c1e65b79966');
insert into CD_DATA_DICT_ATTR (uuid, create_time, creator, modifier, modify_time, rec_ver, attr_order, name, value, data_dict_uuid)
values ('c6774d88-058f-455d-aef3-bd7957118d11', to_timestamp('31-05-2013 15:17:49.947000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('31-05-2013 15:17:49.947000', 'dd-mm-yyyy hh24:mi:ss.ff'), 0, 0, '个人', 'person', '616db210-b497-46f9-9ffd-12c3f3807b3b');
insert into CD_DATA_DICT_ATTR (uuid, create_time, creator, modifier, modify_time, rec_ver, attr_order, name, value, data_dict_uuid)
values ('c91c937f-c570-4250-b987-75502fa2e195', to_timestamp('31-05-2013 15:13:31.970000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('31-05-2013 15:13:31.970000', 'dd-mm-yyyy hh24:mi:ss.ff'), 0, 0, '个人', 'person', '13059074-67f3-42c2-bc46-ddecb07bbbc3');
insert into CD_DATA_DICT_ATTR (uuid, create_time, creator, modifier, modify_time, rec_ver, attr_order, name, value, data_dict_uuid)
values ('cedbc811-ae99-41a6-adfd-53b560762419', to_timestamp('16-05-2013 09:02:20.407000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('16-05-2013 09:02:20.407000', 'dd-mm-yyyy hh24:mi:ss.ff'), 0, 0, '个人', 'person', 'c106b95d-f122-4447-ab30-fb172d793761');
insert into CD_DATA_DICT_ATTR (uuid, create_time, creator, modifier, modify_time, rec_ver, attr_order, name, value, data_dict_uuid)
values ('cf71e34f-e81d-424b-92b1-1470ccc9a4cc', to_timestamp('16-05-2013 09:00:21.233000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('16-05-2013 09:00:21.233000', 'dd-mm-yyyy hh24:mi:ss.ff'), 0, 0, '个人', 'person', 'c62036f9-edbf-43c9-a85d-603b592bdb90');
insert into CD_DATA_DICT_ATTR (uuid, create_time, creator, modifier, modify_time, rec_ver, attr_order, name, value, data_dict_uuid)
values ('d37f11bf-dd3b-4b33-9d2e-3d7e26cc0aeb', null, null, 'U0000000001', to_timestamp('16-05-2013 16:11:33.187000', 'dd-mm-yyyy hh24:mi:ss.ff'), 1, 0, 'uuid', 'd8e5e225-75f1-4bf2-a5f4-70132e75dbc6', 'b1b972f9-bc46-46e3-9dde-eabdacbd0738');
insert into CD_DATA_DICT_ATTR (uuid, create_time, creator, modifier, modify_time, rec_ver, attr_order, name, value, data_dict_uuid)
values ('d766eb63-4aae-4f9d-a28e-5c39746a7e4e', to_timestamp('13-07-2013 16:31:00.343000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('13-07-2013 16:31:00.343000', 'dd-mm-yyyy hh24:mi:ss.ff'), 0, 0, '个人', 'person', 'e408d3da-2994-4341-b92b-c5ef184eabd0');
insert into CD_DATA_DICT_ATTR (uuid, create_time, creator, modifier, modify_time, rec_ver, attr_order, name, value, data_dict_uuid)
values ('e3748bb7-56d9-44a4-b0b7-43215f6e7183', to_timestamp('28-04-2013 14:33:41.333000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('28-04-2013 14:33:41.333000', 'dd-mm-yyyy hh24:mi:ss.ff'), 0, 0, '个人', 'person', '843951b9-9403-47e1-b990-4cab5936f57c');
insert into CD_DATA_DICT_ATTR (uuid, create_time, creator, modifier, modify_time, rec_ver, attr_order, name, value, data_dict_uuid)
values ('e5c1f072-ef2b-46f5-b78b-27c96275531f', to_timestamp('13-07-2013 16:30:20.627000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('13-07-2013 16:30:20.627000', 'dd-mm-yyyy hh24:mi:ss.ff'), 0, 0, '个人', 'person', '9b565897-5e0d-4b29-bc2d-d4c9d1e20ffe');
insert into CD_DATA_DICT_ATTR (uuid, create_time, creator, modifier, modify_time, rec_ver, attr_order, name, value, data_dict_uuid)
values ('eb08ad21-6839-4aa1-875c-047021f4004e', to_timestamp('31-05-2013 15:20:27.683000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('31-05-2013 15:20:27.683000', 'dd-mm-yyyy hh24:mi:ss.ff'), 0, 0, '个人', 'person', 'ea506a84-791b-4bd6-951f-d0998a4c343c');
insert into CD_DATA_DICT_ATTR (uuid, create_time, creator, modifier, modify_time, rec_ver, attr_order, name, value, data_dict_uuid)
values ('ef129c75-c82c-438a-9e2e-0643479cb987', to_timestamp('16-05-2013 16:07:50.063000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('16-05-2013 16:07:50.063000', 'dd-mm-yyyy hh24:mi:ss.ff'), 0, 0, 'uuid', 'edbc65ff-8307-401c-b4e2-bf2f45d32797', 'c0d40b88-ea9a-4f02-b804-67ca28e877c8');
insert into CD_DATA_DICT_ATTR (uuid, create_time, creator, modifier, modify_time, rec_ver, attr_order, name, value, data_dict_uuid)
values ('f6cf6572-0b55-44a2-a97d-19aee4d23711', to_timestamp('16-05-2013 09:00:05.360000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('16-05-2013 09:00:05.360000', 'dd-mm-yyyy hh24:mi:ss.ff'), 0, 0, '个人', 'person', 'b486c533-0015-41f8-88d2-6e498ad26258');
insert into CD_DATA_DICT_ATTR (uuid, create_time, creator, modifier, modify_time, rec_ver, attr_order, name, value, data_dict_uuid)
values ('f91667e8-afb5-4af7-ae44-4e8277c42e31', to_timestamp('22-11-2013 10:36:04.230000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('22-11-2013 10:36:04.230000', 'dd-mm-yyyy hh24:mi:ss.ff'), 0, 0, '个人', 'person', '0e9fbaeb-4417-4d91-b223-8dc73cbf267a');
insert into CD_DATA_DICT_ATTR (uuid, create_time, creator, modifier, modify_time, rec_ver, attr_order, name, value, data_dict_uuid)
values ('f9a172c5-77df-47d1-8501-4a03173f712a', to_timestamp('13-05-2013 14:22:10.547000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('13-05-2013 14:22:10.547000', 'dd-mm-yyyy hh24:mi:ss.ff'), 0, 0, 'class', 'title2', 'd9ca5569-4772-4f59-9171-8e384502214e');
insert into CD_DATA_DICT_ATTR (uuid, create_time, creator, modifier, modify_time, rec_ver, attr_order, name, value, data_dict_uuid)
values ('0ad5acbe-3b08-4625-be6e-b1bb8158cd94', to_timestamp('22-11-2013 10:36:45.063000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('22-11-2013 10:36:45.063000', 'dd-mm-yyyy hh24:mi:ss.ff'), 0, 0, '个人', 'person', '5c9ebe36-c6ab-4370-928b-027e745fbb3f');
insert into CD_DATA_DICT_ATTR (uuid, create_time, creator, modifier, modify_time, rec_ver, attr_order, name, value, data_dict_uuid)
values ('1a70a8e2-6713-4275-8000-27f922a62a8c', to_timestamp('13-07-2013 16:32:23.983000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('13-07-2013 16:32:23.983000', 'dd-mm-yyyy hh24:mi:ss.ff'), 0, 0, '个人', 'person', 'bf677d94-89d4-49e7-a626-3a40a0e55866');
insert into CD_DATA_DICT_ATTR (uuid, create_time, creator, modifier, modify_time, rec_ver, attr_order, name, value, data_dict_uuid)
values ('1fd7c4d6-78e1-4dd7-bce8-ba687b7368c0', to_timestamp('15-05-2013 19:46:54.750000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('15-05-2013 19:46:54.750000', 'dd-mm-yyyy hh24:mi:ss.ff'), 0, 0, 'uuid', 'e5bd6741-3f88-4e57-b25a-b202deb7dbbb', '92f0e344-a056-4ba0-b09b-d94d65d3b494');
insert into CD_DATA_DICT_ATTR (uuid, create_time, creator, modifier, modify_time, rec_ver, attr_order, name, value, data_dict_uuid)
values ('22bb8341-4b74-40b9-a433-fe127bf70588', to_timestamp('15-05-2013 14:38:19.670000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('15-05-2013 14:38:19.670000', 'dd-mm-yyyy hh24:mi:ss.ff'), 0, 0, 'uuid', 'a5096a90-d440-42a5-8728-9389365eb822', '161c6997-f538-456e-a85f-8c3e7196b582');
insert into CD_DATA_DICT_ATTR (uuid, create_time, creator, modifier, modify_time, rec_ver, attr_order, name, value, data_dict_uuid)
values ('296e81f1-e209-4b49-8756-e80f5c69332d', null, null, 'U0000000001', to_timestamp('24-06-2013 11:23:24.360000', 'dd-mm-yyyy hh24:mi:ss.ff'), 1, 0, '个人', 'person', '73c8223f-879c-4f55-b302-3be551e93261');
insert into CD_DATA_DICT_ATTR (uuid, create_time, creator, modifier, modify_time, rec_ver, attr_order, name, value, data_dict_uuid)
values ('2fd2d34a-6e21-4546-95cd-d60f4780015e', to_timestamp('22-06-2013 14:09:30.077000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('22-06-2013 14:09:30.077000', 'dd-mm-yyyy hh24:mi:ss.ff'), 0, 0, '个人', 'person', '1db30b38-2843-49d4-9bbc-4a512bd16c98');
insert into CD_DATA_DICT_ATTR (uuid, create_time, creator, modifier, modify_time, rec_ver, attr_order, name, value, data_dict_uuid)
values ('40842b4f-5014-4b99-982f-d49d9d3ce8c6', to_timestamp('13-07-2013 16:30:42.343000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('13-07-2013 16:30:42.343000', 'dd-mm-yyyy hh24:mi:ss.ff'), 0, 0, '全部', 'all', '09c1f924-d6b4-41df-b5e2-f25ac9a7a60d');
insert into CD_DATA_DICT_ATTR (uuid, create_time, creator, modifier, modify_time, rec_ver, attr_order, name, value, data_dict_uuid)
values ('4391fe32-ef92-469f-b068-ffad24bf07d4', null, null, 'U0000000001', to_timestamp('31-05-2013 14:21:54.070000', 'dd-mm-yyyy hh24:mi:ss.ff'), 1, 0, 'class', 'newoatitle', '7c5e5d64-1bb3-4b85-8620-490d77cf7c1c');
insert into CD_DATA_DICT_ATTR (uuid, create_time, creator, modifier, modify_time, rec_ver, attr_order, name, value, data_dict_uuid)
values ('45c1bd2a-c6c2-4fec-8031-848e458ddceb', to_timestamp('31-05-2013 15:10:34.457000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('31-05-2013 15:10:34.457000', 'dd-mm-yyyy hh24:mi:ss.ff'), 0, 0, '个人', 'person', 'ff5c3d09-90d1-49bc-b125-0a58a3874231');
insert into CD_DATA_DICT_ATTR (uuid, create_time, creator, modifier, modify_time, rec_ver, attr_order, name, value, data_dict_uuid)
values ('46f620a6-051b-49aa-acc5-9b996641b5d9', to_timestamp('16-05-2013 16:06:21.187000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('16-05-2013 16:06:21.187000', 'dd-mm-yyyy hh24:mi:ss.ff'), 0, 0, 'uuid', '7562da88-a45f-4117-944b-701936948b33', '30a1cada-5daa-4767-af44-f04bf8382206');
insert into CD_DATA_DICT_ATTR (uuid, create_time, creator, modifier, modify_time, rec_ver, attr_order, name, value, data_dict_uuid)
values ('472f567e-7ea6-4d04-81e7-301225574b1e', to_timestamp('31-05-2013 15:24:08.970000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0000000001', 'U0000000001', to_timestamp('31-05-2013 15:24:08.970000', 'dd-mm-yyyy hh24:mi:ss.ff'), 0, 0, '个人', 'person', '90d433cd-d7f2-4a15-ab6d-f1527a18bd8a');
insert into CD_DATA_DICT_ATTR (uuid, create_time, creator, modifier, modify_time, rec_ver, attr_order, name, value, data_dict_uuid)
values ('5691bc16-5009-4f0a-87e6-bdf794a42963', null, null, 'U0000000001', to_timestamp('31-05-2013 14:03:51.990000', 'dd-mm-yyyy hh24:mi:ss.ff'), 3, 0, 'css', '/resources/theme/css/wellnewoa.css', '30870a5c-bcb5-4418-bd42-8f0958a90c33');
commit;
prompt 43 records loaded
prompt Enabling foreign key constraints for CD_DATA_DICT...
alter table CD_DATA_DICT enable constraint FKB499D0CD3F27264;
prompt Enabling foreign key constraints for CD_DATA_DICT_ATTR...
alter table CD_DATA_DICT_ATTR enable constraint FK96423A23B6A25483;
set feedback on
set define on
prompt Done.
