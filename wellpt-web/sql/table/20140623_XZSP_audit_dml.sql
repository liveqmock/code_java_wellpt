insert into audit_resource (create_time, creator, modifier, modify_time, 
rec_ver, apply_to, code, dynamic, is_default, name, parent_uuid, remark, target, type, url, 
uuid) values (to_timestamp('23-06-2014 19:07:06.066', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('23-06-2014 19:07:06.066', 'dd-mm-yyyy hh24:mi:ss.ff'), 
0, '', 'B004020', 1, 0, '新建', 'ba595678-7c58-450b-b0c3-1d955d8295df', '', '', 'BUTTON', '', 
'b0643d3f-a4f9-4bbb-a1e8-d765347e42fa') ;
insert into audit_resource (create_time, creator, modifier, modify_time, 
rec_ver, apply_to, code, dynamic, is_default, name, parent_uuid, remark, target, type, url, 
uuid) values (to_timestamp('23-06-2014 19:07:06.052', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('23-06-2014 19:07:06.052', 'dd-mm-yyyy hh24:mi:ss.ff'), 
0, '', 'B004021', 1, 0, '未阅', 'ba595678-7c58-450b-b0c3-1d955d8295df', '', '', 'BUTTON', '', 
'a2845791-5eec-40f5-8940-4edeb14d9a89') ;
insert into audit_resource (create_time, creator, modifier, modify_time, 
rec_ver, apply_to, code, dynamic, is_default, name, parent_uuid, remark, target, type, url, 
uuid) values (to_timestamp('23-06-2014 16:50:06.640', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('23-06-2014 16:50:06.640', 'dd-mm-yyyy hh24:mi:ss.ff'), 
0, '', 'B004022', 1, 0, '已阅', 'ba595678-7c58-450b-b0c3-1d955d8295df', '', '', 'BUTTON', '', 
'c85e5d76-bad9-4abd-a71c-b5086790735f') ;
insert into audit_resource (create_time, creator, modifier, modify_time, 
rec_ver, apply_to, code, dynamic, is_default, name, parent_uuid, remark, target, type, url, 
uuid) values (to_timestamp('23-06-2014 19:07:06.072', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('23-06-2014 19:07:06.072', 'dd-mm-yyyy hh24:mi:ss.ff'), 
0, '', 'B004023', 1, 0, '删除', 'ba595678-7c58-450b-b0c3-1d955d8295df', '', '', 'BUTTON', '', 
'a0fe0a99-04c6-4b4a-a97a-5cc8da68a2e1') ;
insert into audit_resource (create_time, creator, modifier, modify_time, 
rec_ver, apply_to, code, dynamic, is_default, name, parent_uuid, remark, target, type, url, 
uuid) values (to_timestamp('23-06-2014 20:04:55.026', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('23-06-2014 20:04:55.026', 'dd-mm-yyyy hh24:mi:ss.ff'), 
0, '', 'B004024', 1, 0, '查看', 'ba595678-7c58-450b-b0c3-1d955d8295df', '', '', 'BUTTON', '', 
'ce14acc9-d7ed-449f-be68-1975ab65215e') ;
-- 提交按钮
update dyview_view_custom_button t set t.code = 'B004002' where t.code = 'B003006042';
delete from audit_privilege_resource t where t.resource_uuid = '87b971f4-eabc-49cb-a8ba-fbb7c476dfdb';
delete from audit_resource t where t.uuid = '87b971f4-eabc-49cb-a8ba-fbb7c476dfdb';
-- 已阅按钮
update dyview_view_custom_button t set t.code = 'B004021' where t.code = 'B003006022';
delete from audit_privilege_resource t where t.resource_uuid = 'de7b3c2a-7b86-43ba-a8be-2d751f0baeb9';
delete from audit_resource t where t.uuid = 'de7b3c2a-7b86-43ba-a8be-2d751f0baeb9';
-- 关注按钮
update dyview_view_custom_button t set t.code = 'B004008' where t.code = 'B003006019';
delete from audit_privilege_resource t where t.resource_uuid = '8c91abc9-aa83-49e9-9d14-2ba7f755e162';
delete from audit_resource t where t.uuid = '8c91abc9-aa83-49e9-9d14-2ba7f755e162';
-- 抄送按钮
update dyview_view_custom_button t set t.code = 'B004010' where t.code = 'B003006020';
delete from audit_privilege_resource t where t.resource_uuid = '5ca733f5-5c94-44c1-9a20-3697f0c84c21';
delete from audit_resource t where t.uuid = '5ca733f5-5c94-44c1-9a20-3697f0c84c21';
-- 跳转按钮
update dyview_view_custom_button t set t.code = 'B004016' where t.code = 'B003006041';
update audit_privilege_resource t set t.resource_uuid = 'fbb1c412-105d-4cbd-8fd5-7b3ff5f46c80' where t.resource_uuid = '4c88b918-c94d-4909-ab78-5e0b7df5d0e9';
delete from audit_privilege_resource t where t.resource_uuid = '4c88b918-c94d-4909-ab78-5e0b7df5d0e9';
delete from audit_resource t where t.uuid = '4c88b918-c94d-4909-ab78-5e0b7df5d0e9';
-- 签署意见按钮
update dyview_view_custom_button t set t.code = 'B004011' where t.code = 'B003006044';
update audit_privilege_resource t set t.resource_uuid = '13dd40da-1b12-41b4-87cc-4e7f73ef9e06' where t.resource_uuid = 'c1c4be68-e947-4969-8885-0ad1e121f382';
delete from audit_privilege_resource t where t.resource_uuid =  'c1c4be68-e947-4969-8885-0ad1e121f382';
delete from audit_resource t where t.uuid =  'c1c4be68-e947-4969-8885-0ad1e121f382';
-- 套打按钮
update dyview_view_custom_button t set t.code = 'B004009' where t.code = 'B003006043';
update audit_privilege_resource t set t.resource_uuid = 'aaca6aa9-20ed-4b43-b587-378cc6342b01' where t.resource_uuid = '57815a82-8700-4e7c-9780-f6ed0d38b867';
delete from audit_privilege_resource t where t.resource_uuid = '57815a82-8700-4e7c-9780-f6ed0d38b867';
delete from audit_resource t where t.uuid =  '57815a82-8700-4e7c-9780-f6ed0d38b867';
-- 转办按钮
update dyview_view_custom_button t set t.code = 'B004006' where t.code = 'B003006017';
update audit_privilege_resource t set t.resource_uuid = 'dbe45bde-c746-41ea-9ce0-20aa5c1ad43b' where t.resource_uuid = 'ba44b5f4-fc4a-4fe5-92b9-c81f9fde8050';
delete from audit_privilege_resource t where t.resource_uuid = 'ba44b5f4-fc4a-4fe5-92b9-c81f9fde8050';
delete from audit_resource t where t.uuid =  'ba44b5f4-fc4a-4fe5-92b9-c81f9fde8050';
-- 移交按钮
update dyview_view_custom_button t set t.code = 'B004015' where t.code = 'B003006040';
update audit_privilege_resource t set t.resource_uuid = '13be0bcc-8249-471d-bf08-6fdd0c0794f0' where t.resource_uuid = 'dcb31583-d119-48a7-a3c8-cb0c20361e03';
delete from audit_privilege_resource t where t.resource_uuid = 'dcb31583-d119-48a7-a3c8-cb0c20361e03';
delete from audit_resource t where t.uuid =  'dcb31583-d119-48a7-a3c8-cb0c20361e03';
-- 直接退回按钮
update dyview_view_custom_button t set t.code = 'B004004' where t.code = 'B003006015';
delete from audit_privilege_resource t where t.resource_uuid = '1ee70de9-87ef-4afc-a7f6-8e24556ce447';
delete from audit_resource t where t.uuid = '1ee70de9-87ef-4afc-a7f6-8e24556ce447';
-- 催办按钮
update dyview_view_custom_button t set t.code = 'B004014' where t.code = 'B003006021';
update audit_privilege_resource t set t.resource_uuid =  'da75e26c-bbc0-4654-bbad-20f954757a3c' where t.resource_uuid = '5680a727-b40e-4ec6-9d27-27fc3d6d55dd';
delete from audit_privilege_resource t where t.resource_uuid = '5680a727-b40e-4ec6-9d27-27fc3d6d55dd';
delete from audit_resource t where t.uuid =  '5680a727-b40e-4ec6-9d27-27fc3d6d55dd';
-- 未阅按钮
update dyview_view_custom_button t set t.code = 'B004021' where t.code = 'B003006023';
update audit_privilege_resource t set t.resource_uuid =  'a2845791-5eec-40f5-8940-4edeb14d9a89' where t.resource_uuid = 'd6dfd3c6-fc7e-4f12-a320-7f2e11b59003';
delete from audit_privilege_resource t where t.resource_uuid = 'd6dfd3c6-fc7e-4f12-a320-7f2e11b59003';
delete from audit_resource t where t.uuid = 'd6dfd3c6-fc7e-4f12-a320-7f2e11b59003';
-- 撤回按钮
update dyview_view_custom_button t set t.code = 'B004005' where t.code = 'B003006016';
delete from audit_privilege_resource t where t.resource_uuid = '2461ff9e-06f4-4cd4-a764-531b341a54b6';
delete from audit_resource t where t.uuid = '2461ff9e-06f4-4cd4-a764-531b341a54b6';
-- 会签按钮
update dyview_view_custom_button t set t.code = 'B004007' where t.code = 'B003006018';
update audit_privilege_resource t set t.resource_uuid =  '4cb44f6d-2a6c-40b8-b3e6-e5ed40449dc2' where t.resource_uuid = '554346c7-3329-4012-a16e-f2b27121e0c9';
delete from audit_privilege_resource t where t.resource_uuid = '554346c7-3329-4012-a16e-f2b27121e0c9';
delete from audit_resource t where t.uuid = '554346c7-3329-4012-a16e-f2b27121e0c9';
-- 取消关注按钮
update dyview_view_custom_button t set t.code = 'B004012' where t.code = 'B003006039';
update audit_privilege_resource t set t.resource_uuid =  'e54bc2e6-09e0-4f36-960f-ef3e61067d76' where t.resource_uuid = 'ed6e4da0-e99f-4657-9329-3ba6cac77ffc';
delete from audit_privilege_resource t where t.resource_uuid = 'ed6e4da0-e99f-4657-9329-3ba6cac77ffc';
delete from audit_resource t where t.uuid = 'ed6e4da0-e99f-4657-9329-3ba6cac77ffc';
-- 删除按钮
update dyview_view_custom_button t set t.code = 'B004023' where t.code = 'B003006007';
update audit_privilege_resource t set t.resource_uuid =  'a0fe0a99-04c6-4b4a-a97a-5cc8da68a2e1' where t.resource_uuid = 'edd3a283-52db-410b-985f-91b75cbe1361';
delete from audit_privilege_resource t where t.resource_uuid = 'edd3a283-52db-410b-985f-91b75cbe1361';
delete from audit_resource t where t.uuid =  'edd3a283-52db-410b-985f-91b75cbe1361';
-- 查看按钮
update dyview_view_custom_button t set t.code = 'B004024' where t.code = 'B003006025';
update audit_privilege_resource t set t.resource_uuid =  'ce14acc9-d7ed-449f-be68-1975ab65215e' where t.resource_uuid = 'd668f76a-c43b-4555-8906-d41b2119d4fa';
delete from audit_privilege_resource t where t.resource_uuid = 'd668f76a-c43b-4555-8906-d41b2119d4fa';
delete from audit_resource t where t.uuid =  'd668f76a-c43b-4555-8906-d41b2119d4fa';
-- 新建按钮
update dyview_view_custom_button t set t.code = 'B004020' where t.code = 'B003006026';
update audit_privilege_resource t set t.resource_uuid =  'b0643d3f-a4f9-4bbb-a1e8-d765347e42fa' where t.resource_uuid = 'e3d6ff7b-2a09-46f4-a58f-23d15d953714';
delete from audit_privilege_resource t where t.resource_uuid = 'e3d6ff7b-2a09-46f4-a58f-23d15d953714';
delete from audit_resource t where t.uuid =  'e3d6ff7b-2a09-46f4-a58f-23d15d953714';
update dyview_view_custom_button t set t.code = 'B004020' where t.code = 'B003006062';
update audit_privilege_resource t set t.resource_uuid =  'b0643d3f-a4f9-4bbb-a1e8-d765347e42fa' where t.resource_uuid = '2d1846b1-6ccb-42cc-9aa7-e39c3f0fa0c3';
delete from audit_privilege_resource t where t.resource_uuid = '2d1846b1-6ccb-42cc-9aa7-e39c3f0fa0c3';
delete from audit_resource t where t.uuid = '2d1846b1-6ccb-42cc-9aa7-e39c3f0fa0c3';

-- 用车管理
insert into audit_resource (create_time, creator, modifier, modify_time, 
rec_ver, code, name, remark, type, url, uuid) values (to_timestamp('23-06-2014 20:34:21.441', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 
'U0010000001', to_timestamp('23-06-2014 20:34:21.441', 'dd-mm-yyyy hh24:mi:ss.ff'), 0, '015', '用车管理', '用车管理', 'MENU', '', '385a65e5-02e6-4b16-94e0-5778c5034b8d') ;
insert into audit_resource (parent_uuid, create_time, creator, modifier, modify_time, 
rec_ver, code, name, remark, type, url, uuid) values ('385a65e5-02e6-4b16-94e0-5778c5034b8d', to_timestamp('23-06-2014 21:00:29.265', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 
'U0010000001', to_timestamp('23-06-2014 21:00:29.265', 'dd-mm-yyyy hh24:mi:ss.ff'), 0, '015001', '流程应用', '流程应用', 'MENU', '', 'c99d68ea-68ca-4a3f-b405-16bce5095553') ;
insert into audit_resource (create_time, creator, modifier, modify_time, 
rec_ver, apply_to, code, dynamic, is_default, name, parent_uuid, remark, target, type, url, 
uuid) values (to_timestamp('24-06-2014 09:29:20.837', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('24-06-2014 09:29:20.837', 'dd-mm-yyyy hh24:mi:ss.ff'), 
0, '', 'B015001001', 1, 0, '新增', 'c99d68ea-68ca-4a3f-b405-16bce5095553', '', '', 'BUTTON', 
'', '9fa1ea6b-e522-4b81-9fb9-3256c8956a5c') ;
insert into audit_resource (create_time, creator, modifier, modify_time, 
rec_ver, apply_to, code, dynamic, is_default, name, parent_uuid, remark, target, type, url, 
uuid) values (to_timestamp('24-06-2014 09:54:07.109', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('24-06-2014 09:54:07.109', 'dd-mm-yyyy hh24:mi:ss.ff'), 
0, '', 'B015001002', 1, 0, '新增用车申请', 'c99d68ea-68ca-4a3f-b405-16bce5095553', '', '', 'BUTTON', 
'', 'ec6235ec-a550-43a5-be0d-a6334e765cb7') ;
-- 用车管理——新建按钮
update dyview_view_custom_button t set t.code = 'B015001001' where t.code = 'B003006003' and t.uuid = '66bd6ac9-e5c4-4235-add9-56f64a07f50d';
delete from audit_privilege_resource t where t.resource_uuid = '6281486f-b410-41df-82ac-f14784a81d5c';
delete from audit_resource t where t.uuid = '6281486f-b410-41df-82ac-f14784a81d5c';
-- 用车管理——新增用车申请按钮
update dyview_view_custom_button t set t.code = 'B015001002' where t.code = 'B003006024' and t.uuid = '819176cf-b150-4227-92ff-e79c91d6aa84';
delete from audit_privilege_resource t where t.resource_uuid = '12894fe8-5e9c-4bc0-929a-1f294e7c12df';
delete from audit_resource t where t.uuid = '12894fe8-5e9c-4bc0-929a-1f294e7c12df';
-- 会议管理
insert into audit_resource (create_time, creator, modifier, modify_time, 
rec_ver, code, name, remark, type, url, uuid) values (to_timestamp('23-06-2014 20:35:47.495', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 
'U0010000001', to_timestamp('23-06-2014 20:35:47.495', 'dd-mm-yyyy hh24:mi:ss.ff'), 0, '016', '会议管理', '会议管理', 'MENU', '', '7b7e9622-6bdc-44dd-a21b-dc5c694a303f') ;
insert into audit_resource (parent_uuid, create_time, creator, modifier, modify_time, 
rec_ver, code, name, remark, type, url, uuid) values ('7b7e9622-6bdc-44dd-a21b-dc5c694a303f', to_timestamp('23-06-2014 21:05:59.124', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 
'U0010000001', to_timestamp('23-06-2014 21:05:59.124', 'dd-mm-yyyy hh24:mi:ss.ff'), 0, '016001', '流程应用', '流程应用', 'MENU', '', '7123a1be-1a6d-409e-a602-238163db982b') ;
insert into audit_resource (create_time, creator, modifier, modify_time, 
rec_ver, apply_to, code, dynamic, is_default, name, parent_uuid, remark, target, type, url, 
uuid) values (to_timestamp('24-06-2014 09:45:07.976', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('24-06-2014 09:45:07.976', 'dd-mm-yyyy hh24:mi:ss.ff'), 
0, '', 'B016001001', 1, 0, '新建会议纪要', '7123a1be-1a6d-409e-a602-238163db982b', '', '', 'BUTTON', 
'', '6e0f232d-36c3-4df5-94d8-368a1cb8eb4d') ;
insert into audit_resource (create_time, creator, modifier, modify_time, 
rec_ver, apply_to, code, dynamic, is_default, name, parent_uuid, remark, target, type, url, 
uuid) values (to_timestamp('24-06-2014 10:16:01.118', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('24-06-2014 10:16:01.118', 'dd-mm-yyyy hh24:mi:ss.ff'), 
0, '', 'B016001002', 1, 0, '新建会议安排', '7123a1be-1a6d-409e-a602-238163db982b', '', '', 'BUTTON', 
'', '7a9e5279-6ff4-49dd-9413-bd500bbb3f5b') ;
-- 会议管理——新建会议纪要按钮
update dyview_view_custom_button t set t.code = 'B016001001' where t.code = 'B003006013' and t.uuid = '6a0b1bbb-2877-4665-9757-e6f0e44e013c';
delete from audit_privilege_resource t where t.resource_uuid = '6cf25860-984e-413c-841a-51577f0a45da';
delete from audit_resource t where t.uuid = '6cf25860-984e-413c-841a-51577f0a45da';
-- 会议管理——新建会议安排按钮
update dyview_view_custom_button t set t.code = 'B016001002' where t.code = 'B003006014';
delete from audit_privilege_resource t where t.resource_uuid = 'cd5bbedf-1c21-4d2c-8d2f-cd2bccd3df59';
delete from audit_resource t where t.uuid = 'cd5bbedf-1c21-4d2c-8d2f-cd2bccd3df59';
-- 档案管理
insert into audit_resource (create_time, creator, modifier, modify_time, 
rec_ver, code, name, remark, type, url, uuid) values (to_timestamp('23-06-2014 20:37:15.371', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 
'U0010000001', to_timestamp('23-06-2014 20:37:15.371', 'dd-mm-yyyy hh24:mi:ss.ff'), 0, '017', '档案管理', '档案管理', 'MENU', '', 'c2612307-04bf-485f-873a-e8fad78b0b45') ;
insert into audit_resource (parent_uuid, create_time, creator, modifier, modify_time, 
rec_ver, code, name, remark, type, url, uuid) values ('c2612307-04bf-485f-873a-e8fad78b0b45', to_timestamp('23-06-2014 21:07:36.938', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 
'U0010000001', to_timestamp('23-06-2014 21:07:36.938', 'dd-mm-yyyy hh24:mi:ss.ff'), 0, '017001', '流程应用', '流程应用', 'MENU', '', '1f9e9fda-f70c-458b-85b2-69f14b3a8a62') ;
-- 物品管理
insert into audit_resource (create_time, creator, modifier, modify_time, 
rec_ver, code, name, remark, type, url, uuid) values (to_timestamp('23-06-2014 20:40:15.788', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 
'U0010000001', to_timestamp('23-06-2014 20:40:15.788', 'dd-mm-yyyy hh24:mi:ss.ff'), 0, '018', '物品管理', '物品管理', 'MENU', '', '0d63b3aa-8426-406e-9a59-f932af5cc2c8') ;
insert into audit_resource (parent_uuid, create_time, creator, modifier, modify_time, 
rec_ver, code, name, remark, type, url, uuid) values ('0d63b3aa-8426-406e-9a59-f932af5cc2c8', to_timestamp('23-06-2014 21:09:31.538', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 
'U0010000001', to_timestamp('23-06-2014 21:09:31.538', 'dd-mm-yyyy hh24:mi:ss.ff'), 0, '018001', '流程应用', '流程应用', 'MENU', '', '0d555a1a-970c-4cde-ab83-d51620d51308') ;
insert into audit_resource (create_time, creator, modifier, modify_time, 
rec_ver, apply_to, code, dynamic, is_default, name, parent_uuid, remark, target, type, url, 
uuid) values (to_timestamp('24-06-2014 10:03:21.101', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('24-06-2014 10:03:21.101', 'dd-mm-yyyy hh24:mi:ss.ff'), 
0, '', 'B018001001', 1, 0, '我要申领', '0d555a1a-970c-4cde-ab83-d51620d51308', '', '', 'BUTTON', 
'', '271ff0a4-60ab-427d-8aac-09c71c020d85') ;
-- 物品管理——我要申领按钮
update dyview_view_custom_button t set t.code = 'B018001001' where t.code = 'B003006053';
delete from audit_privilege_resource t where t.resource_uuid = '42a8c20d-9931-4ef1-be42-b825aaeeda08';
delete from audit_resource t where t.uuid = '42a8c20d-9931-4ef1-be42-b825aaeeda08';

COMMIT;