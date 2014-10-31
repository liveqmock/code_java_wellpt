-- 政务外网存在的部门id升级
update XZSP.org_department t set t.id = 'D0000000441' where t.id = 'D0000000141';
update XZSP.org_department t set t.id = 'D0000000442' where t.id = 'D0000000142';
update XZSP.org_department t set t.id = 'D0000000443' where t.id = 'D0000000143';
update XZSP.org_department t set t.id = 'D0000000444' where t.id = 'D0000000144';

-- 本地内网增加用户(U0010000053	20-12月-13 07.03.01.000000 下午	3.52601E+17	胡亮信)
-- insert into ORG_USER (uuid, create_time, creator, modifier, modify_time, rec_ver, account_non_expired, account_non_locked, code, credentials_non_expired, department_name, deputy_names, employee_number, enabled, fax, group_names, id, id_number, is_allowed_back, issys, job_name, last_login_time, leader_names, login_name, mobile_phone, office_phone, password, photo_uuid, remark, role_names, sex, tenant_id, trace, user_name)
-- values ('c14a4863-27c7-4fe8-ac48-b069c5d72826', to_timestamp('20-12-2013 19:03:01.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('04-03-2014 16:11:01.475000', 'dd-mm-yyyy hh24:mi:ss.ff'), 52, null, null, '010101', null, '市行政服务中心管理委员会', null, null, 1, null, null, 'U0010000053', '352601195811190017', 0, 0, null, to_timestamp('04-03-2014 16:05:36.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), null, '352601195811190017', '13806021633', null, '3cb6385a2b9bcc442bd2b94ac5aa2c2d', '1b9553b2-5947-4951-9684-0f426cc0cdfc', null, null, '1', 'T001', '003005001', '胡亮信');
-- commit;
-- insert into ORG_DEPARTMENT_USER_JOB (uuid, create_time, creator, modifier, modify_time, rec_ver, is_major, job_code, job_name, department_uuid, user_uuid)
-- values ('29476a25-0129-48e7-b132-1544814f6277', to_timestamp('04-03-2014 16:11:01.481000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('04-03-2014 16:11:01.481000', 'dd-mm-yyyy hh24:mi:ss.ff'), 0, 1, null, null, 'bdf2d934-b80c-4314-9631-651e94071136', 'c14a4863-27c7-4fe8-ac48-b069c5d72826');
-- commit;
-- insert into ORG_USER_ROLE (user_uuid, role_uuid)
-- values ('c14a4863-27c7-4fe8-ac48-b069c5d72826', '002f0423-e997-41f6-a9e4-d541cabb73d5');
-- commit;

-- 政务外网用户U0010000531到U0010000551的id升级为U0011000531到U0011000551
update org_user t set t.id = 'U0011000531' where t.id = 'U0010000531';
update org_user t set t.id = 'U0011000532' where t.id = 'U0010000532';
update org_user t set t.id = 'U0011000533' where t.id = 'U0010000533';
update org_user t set t.id = 'U0011000534' where t.id = 'U0010000534';
update org_user t set t.id = 'U0011000535' where t.id = 'U0010000535';
update org_user t set t.id = 'U0011000536' where t.id = 'U0010000536';
update org_user t set t.id = 'U0011000537' where t.id = 'U0010000537';
update org_user t set t.id = 'U0011000538' where t.id = 'U0010000538';
update org_user t set t.id = 'U0011000539' where t.id = 'U0010000539';
update org_user t set t.id = 'U0011000540' where t.id = 'U0010000540';
update org_user t set t.id = 'U0011000541' where t.id = 'U0010000541';
update org_user t set t.id = 'U0011000542' where t.id = 'U0010000542';
update org_user t set t.id = 'U0011000543' where t.id = 'U0010000543';
update org_user t set t.id = 'U0011000544' where t.id = 'U0010000544';
update org_user t set t.id = 'U0011000545' where t.id = 'U0010000545';
update org_user t set t.id = 'U0011000546' where t.id = 'U0010000546';
update org_user t set t.id = 'U0011000547' where t.id = 'U0010000547';
update org_user t set t.id = 'U0011000548' where t.id = 'U0010000548';
update org_user t set t.id = 'U0011000549' where t.id = 'U0010000549';
update org_user t set t.id = 'U0011000550' where t.id = 'U0010000550';
update org_user t set t.id = 'U0011000551' where t.id = 'U0010000551';

-- 其他要升级的id
update org_user t set t.id = 'U0010000954' where t.id = 'U0010000554';
update org_user t set t.id = 'U0010000955' where t.id = 'U0010000555';
update org_user t set t.id = 'U0010000956' where t.id = 'U0010000556';
update org_user t set t.id = 'U0010000957' where t.id = 'U0010000557';
update org_user t set t.id = 'U0010000958' where t.id = 'U0010000558';
update org_user t set t.id = 'U0010000959' where t.id = 'U0010000559';
update org_user t set t.id = 'U0010000960' where t.id = 'U0010000560';
update org_user t set t.id = 'U0010000961' where t.id = 'U0010000561';
update org_user t set t.id = 'U0010000962' where t.id = 'U0010000562';
update org_user t set t.id = 'U0010000963' where t.id = 'U0010000563';
update org_user t set t.id = 'U0010000964' where t.id = 'U0010000564';
update org_user t set t.id = 'U0010000965' where t.id = 'U0010000565';
update org_user t set t.id = 'U0010000966' where t.id = 'U0010000566';
update org_user t set t.id = 'U0010000967' where t.id = 'U0010000567';
update org_user t set t.id = 'U0010000968' where t.id = 'U0010000568';
update org_user t set t.id = 'U0010000969' where t.id = 'U0010000569';
update org_user t set t.id = 'U0010000970' where t.id = 'U0010000570';
update org_user t set t.id = 'U0010000971' where t.id = 'U0010000571';
update org_user t set t.id = 'U0010000972' where t.id = 'U0010000572';
update org_user t set t.id = 'U0010000973' where t.id = 'U0010000573';
update org_user t set t.id = 'U0010000974' where t.id = 'U0010000574';
update org_user t set t.id = 'U0010000975' where t.id = 'U0010000575';
update org_user t set t.id = 'U0010000976' where t.id = 'U0010000576';
update org_user t set t.id = 'U0010000977' where t.id = 'U0010000577';
update org_user t set t.id = 'U0010000978' where t.id = 'U0010000578';
update org_user t set t.id = 'U0010000979' where t.id = 'U0010000579';
update org_user t set t.id = 'U0010000980' where t.id = 'U0010000580';
update org_user t set t.id = 'U0010000981' where t.id = 'U0010000581';
update org_user t set t.id = 'U0010000982' where t.id = 'U0010000582';
update org_user t set t.id = 'U0010000983' where t.id = 'U0010000583';

-- 更新acl_sid
update acl_sid t set t.sid = 'U0011000531' where t.sid = 'U0010000531';
update acl_sid t set t.sid = 'U0011000532' where t.sid = 'U0010000532';
update acl_sid t set t.sid = 'U0011000533' where t.sid = 'U0010000533';
update acl_sid t set t.sid = 'U0011000534' where t.sid = 'U0010000534';
update acl_sid t set t.sid = 'U0011000535' where t.sid = 'U0010000535';
update acl_sid t set t.sid = 'U0011000536' where t.sid = 'U0010000536';
update acl_sid t set t.sid = 'U0011000537' where t.sid = 'U0010000537';
update acl_sid t set t.sid = 'U0011000538' where t.sid = 'U0010000538';
update acl_sid t set t.sid = 'U0011000539' where t.sid = 'U0010000539';
update acl_sid t set t.sid = 'U0011000540' where t.sid = 'U0010000540';
update acl_sid t set t.sid = 'U0011000541' where t.sid = 'U0010000541';
update acl_sid t set t.sid = 'U0011000542' where t.sid = 'U0010000542';
update acl_sid t set t.sid = 'U0011000543' where t.sid = 'U0010000543';
update acl_sid t set t.sid = 'U0011000544' where t.sid = 'U0010000544';
update acl_sid t set t.sid = 'U0011000545' where t.sid = 'U0010000545';
update acl_sid t set t.sid = 'U0011000546' where t.sid = 'U0010000546';
update acl_sid t set t.sid = 'U0011000547' where t.sid = 'U0010000547';
update acl_sid t set t.sid = 'U0011000548' where t.sid = 'U0010000548';
update acl_sid t set t.sid = 'U0011000549' where t.sid = 'U0010000549';
update acl_sid t set t.sid = 'U0011000550' where t.sid = 'U0010000550';
update acl_sid t set t.sid = 'U0011000551' where t.sid = 'U0010000551';

update acl_sid t set t.sid = 'U0010000954' where t.sid = 'U0010000554';
update acl_sid t set t.sid = 'U0010000955' where t.sid = 'U0010000555';
update acl_sid t set t.sid = 'U0010000956' where t.sid = 'U0010000556';
update acl_sid t set t.sid = 'U0010000957' where t.sid = 'U0010000557';
update acl_sid t set t.sid = 'U0010000958' where t.sid = 'U0010000558';
update acl_sid t set t.sid = 'U0010000959' where t.sid = 'U0010000559';
update acl_sid t set t.sid = 'U0010000960' where t.sid = 'U0010000560';
update acl_sid t set t.sid = 'U0010000961' where t.sid = 'U0010000561';
update acl_sid t set t.sid = 'U0010000962' where t.sid = 'U0010000562';
update acl_sid t set t.sid = 'U0010000963' where t.sid = 'U0010000563';
update acl_sid t set t.sid = 'U0010000964' where t.sid = 'U0010000564';
update acl_sid t set t.sid = 'U0010000965' where t.sid = 'U0010000565';
update acl_sid t set t.sid = 'U0010000966' where t.sid = 'U0010000566';
update acl_sid t set t.sid = 'U0010000967' where t.sid = 'U0010000567';
update acl_sid t set t.sid = 'U0010000968' where t.sid = 'U0010000568';
update acl_sid t set t.sid = 'U0010000969' where t.sid = 'U0010000569';
update acl_sid t set t.sid = 'U0010000970' where t.sid = 'U0010000570';
update acl_sid t set t.sid = 'U0010000971' where t.sid = 'U0010000571';
update acl_sid t set t.sid = 'U0010000972' where t.sid = 'U0010000572';
update acl_sid t set t.sid = 'U0010000973' where t.sid = 'U0010000573';
update acl_sid t set t.sid = 'U0010000974' where t.sid = 'U0010000574';
update acl_sid t set t.sid = 'U0010000975' where t.sid = 'U0010000575';
update acl_sid t set t.sid = 'U0010000976' where t.sid = 'U0010000576';
update acl_sid t set t.sid = 'U0010000977' where t.sid = 'U0010000577';
update acl_sid t set t.sid = 'U0010000978' where t.sid = 'U0010000578';
update acl_sid t set t.sid = 'U0010000979' where t.sid = 'U0010000579';
update acl_sid t set t.sid = 'U0010000980' where t.sid = 'U0010000580';
update acl_sid t set t.sid = 'U0010000981' where t.sid = 'U0010000581';
update acl_sid t set t.sid = 'U0010000982' where t.sid = 'U0010000582';
update acl_sid t set t.sid = 'U0010000983' where t.sid = 'U0010000583';

-- 更新acl_sid_member
update acl_sid_member t set t.member = 'U0011000531' where t.member = 'U0010000531';
update acl_sid_member t set t.member = 'U0011000532' where t.member = 'U0010000532';
update acl_sid_member t set t.member = 'U0011000533' where t.member = 'U0010000533';
update acl_sid_member t set t.member = 'U0011000534' where t.member = 'U0010000534';
update acl_sid_member t set t.member = 'U0011000535' where t.member = 'U0010000535';
update acl_sid_member t set t.member = 'U0011000536' where t.member = 'U0010000536';
update acl_sid_member t set t.member = 'U0011000537' where t.member = 'U0010000537';
update acl_sid_member t set t.member = 'U0011000538' where t.member = 'U0010000538';
update acl_sid_member t set t.member = 'U0011000539' where t.member = 'U0010000539';
update acl_sid_member t set t.member = 'U0011000540' where t.member = 'U0010000540';
update acl_sid_member t set t.member = 'U0011000541' where t.member = 'U0010000541';
update acl_sid_member t set t.member = 'U0011000542' where t.member = 'U0010000542';
update acl_sid_member t set t.member = 'U0011000543' where t.member = 'U0010000543';
update acl_sid_member t set t.member = 'U0011000544' where t.member = 'U0010000544';
update acl_sid_member t set t.member = 'U0011000545' where t.member = 'U0010000545';
update acl_sid_member t set t.member = 'U0011000546' where t.member = 'U0010000546';
update acl_sid_member t set t.member = 'U0011000547' where t.member = 'U0010000547';
update acl_sid_member t set t.member = 'U0011000548' where t.member = 'U0010000548';
update acl_sid_member t set t.member = 'U0011000549' where t.member = 'U0010000549';
update acl_sid_member t set t.member = 'U0011000550' where t.member = 'U0010000550';
update acl_sid_member t set t.member = 'U0011000551' where t.member = 'U0010000551';

update acl_sid_member t set t.member = 'U0010000954' where t.member = 'U0010000554';
update acl_sid_member t set t.member = 'U0010000955' where t.member = 'U0010000555';
update acl_sid_member t set t.member = 'U0010000956' where t.member = 'U0010000556';
update acl_sid_member t set t.member = 'U0010000957' where t.member = 'U0010000557';
update acl_sid_member t set t.member = 'U0010000958' where t.member = 'U0010000558';
update acl_sid_member t set t.member = 'U0010000959' where t.member = 'U0010000559';
update acl_sid_member t set t.member = 'U0010000960' where t.member = 'U0010000560';
update acl_sid_member t set t.member = 'U0010000961' where t.member = 'U0010000561';
update acl_sid_member t set t.member = 'U0010000962' where t.member = 'U0010000562';
update acl_sid_member t set t.member = 'U0010000963' where t.member = 'U0010000563';
update acl_sid_member t set t.member = 'U0010000964' where t.member = 'U0010000564';
update acl_sid_member t set t.member = 'U0010000965' where t.member = 'U0010000565';
update acl_sid_member t set t.member = 'U0010000966' where t.member = 'U0010000566';
update acl_sid_member t set t.member = 'U0010000967' where t.member = 'U0010000567';
update acl_sid_member t set t.member = 'U0010000968' where t.member = 'U0010000568';
update acl_sid_member t set t.member = 'U0010000969' where t.member = 'U0010000569';
update acl_sid_member t set t.member = 'U0010000970' where t.member = 'U0010000570';
update acl_sid_member t set t.member = 'U0010000971' where t.member = 'U0010000571';
update acl_sid_member t set t.member = 'U0010000972' where t.member = 'U0010000572';
update acl_sid_member t set t.member = 'U0010000973' where t.member = 'U0010000573';
update acl_sid_member t set t.member = 'U0010000974' where t.member = 'U0010000574';
update acl_sid_member t set t.member = 'U0010000975' where t.member = 'U0010000575';
update acl_sid_member t set t.member = 'U0010000976' where t.member = 'U0010000576';
update acl_sid_member t set t.member = 'U0010000977' where t.member = 'U0010000577';
update acl_sid_member t set t.member = 'U0010000978' where t.member = 'U0010000578';
update acl_sid_member t set t.member = 'U0010000979' where t.member = 'U0010000579';
update acl_sid_member t set t.member = 'U0010000980' where t.member = 'U0010000580';
update acl_sid_member t set t.member = 'U0010000981' where t.member = 'U0010000581';
update acl_sid_member t set t.member = 'U0010000982' where t.member = 'U0010000582';
update acl_sid_member t set t.member = 'U0010000983' where t.member = 'U0010000583';

-- 用户相关信息合并
insert into XZSP.org_user
select * from XZSP_15.org_user t where t.uuid not in(
select uuid from XZSP.org_user
) and t.id not in(
select id from XZSP.org_user
);

insert into XZSP.org_department
select * from XZSP_15.org_department t where t.uuid not in(
select uuid from XZSP.org_department
) and t.id not in(
select id from XZSP.org_department
);

insert into  XZSP.org_department_user_job
select * from  XZSP_15.org_department_user_job t where t.user_uuid not in(
select j.user_uuid from XZSP.org_department_user_job j
) and exists (select t2.uuid from XZSP.org_department t2 where t2.uuid = t.department_uuid)
and exists (select t3.uuid from XZSP.org_user t3 where t3.uuid = t.user_uuid);

insert into  XZSP.org_user_role 
select * from XZSP_15.org_user_role t where t.user_uuid not in(
select j.user_uuid from XZSP.org_user_role j
) and exists (select t3.uuid from XZSP.org_user t3 where t3.uuid = t.user_uuid);
