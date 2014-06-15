delete from TASK_JOB_DETAILS;
commit;
insert into TASK_JOB_DETAILS (uuid, create_time, creator, modifier, modify_time, rec_ver, code, end_time, expression, id, interval_time, job_class_name, name, remark, repeat_count, repeat_day_of_month, repeat_day_of_season, repeat_day_of_week, repeat_day_of_year, repeat_interval, start_time, tenant_id, time_point, timing_mode, type)
values ('0a111d74-d74d-4c69-a384-e102d68ff36f', to_timestamp('06-11-2013 11:41:21.570000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('06-11-2013 11:41:21.570000', 'dd-mm-yyyy hh24:mi:ss.ff'), 0, null, null, null, null, null, 'com.wellsoft.pt.psi.support.StockArchiveJob', '按周期归档', null, 0, null, null, null, null, 0, to_timestamp('06-11-2014 11:41:21.477000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'T001', null, null, 'temporary');
insert into TASK_JOB_DETAILS (uuid, create_time, creator, modifier, modify_time, rec_ver, code, end_time, expression, id, interval_time, job_class_name, name, remark, repeat_count, repeat_day_of_month, repeat_day_of_season, repeat_day_of_week, repeat_day_of_year, repeat_interval, start_time, tenant_id, time_point, timing_mode, type)
values ('35119888-f209-4a16-b455-ea7f63d023ba', to_timestamp('17-09-2013 20:25:34.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('21-11-2013 21:40:39.023000', 'dd-mm-yyyy hh24:mi:ss.ff'), 13, '003', null, '30/60 * * ? * *', 'FETCH_MAIL', null, 'com.wellsoft.pt.mail.job.FetchMailJob', '用户邮件定时收取', '用户邮件定时收取', 0, null, '第 1', null, '01', 0, null, 'T001', null, null, 'timing');
insert into TASK_JOB_DETAILS (uuid, create_time, creator, modifier, modify_time, rec_ver, code, end_time, expression, id, interval_time, job_class_name, name, remark, repeat_count, repeat_day_of_month, repeat_day_of_season, repeat_day_of_week, repeat_day_of_year, repeat_interval, start_time, tenant_id, time_point, timing_mode, type)
values ('8226f055-f59e-4209-b5ee-f5a2ba6ecf45', to_timestamp('30-10-2013 14:51:56.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('08-12-2013 16:10:37.743000', 'dd-mm-yyyy hh24:mi:ss.ff'), 7, '001', to_timestamp('28-11-2013 14:51:50.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '30/60 * * ? * *', 'STOCK_WARNING', null, 'com.wellsoft.pt.psi.support.StockWarningJob', '定时库存预警', null, null, null, '第 1', null, '01', null, to_timestamp('29-10-2013 14:51:43.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'T001', '15:00:10', 2, 'timing');
insert into TASK_JOB_DETAILS (uuid, create_time, creator, modifier, modify_time, rec_ver, code, end_time, expression, id, interval_time, job_class_name, name, remark, repeat_count, repeat_day_of_month, repeat_day_of_season, repeat_day_of_week, repeat_day_of_year, repeat_interval, start_time, tenant_id, time_point, timing_mode, type)
values ('c6c1ec3c-3bcc-4026-905c-e5928fe01ebc', to_timestamp('06-11-2013 10:28:19.463000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('06-11-2013 10:28:19.463000', 'dd-mm-yyyy hh24:mi:ss.ff'), 6, '002', to_timestamp('29-11-2013 10:28:11.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '30/60 * * ? * *', 'ARCHIVE_FILE', null, 'com.wellsoft.pt.psi.support.StockArchiveJob', '定时器监控文件库', null, null, null, '第 1', null, '01', null, to_timestamp('06-11-2013 10:28:07.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'T001', '10:29:00', 1, 'timing');
insert into TASK_JOB_DETAILS (uuid, create_time, creator, modifier, modify_time, rec_ver, code, end_time, expression, id, interval_time, job_class_name, name, remark, repeat_count, repeat_day_of_month, repeat_day_of_season, repeat_day_of_week, repeat_day_of_year, repeat_interval, start_time, tenant_id, time_point, timing_mode, type)
values ('e228c629-9355-411d-93ee-ec750d3dca91', to_timestamp('17-09-2013 20:33:42.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('21-11-2013 21:40:35.243000', 'dd-mm-yyyy hh24:mi:ss.ff'), 33, '002', null, '0/60,30/60 * * ? * *', 'INBOX_MAIL_MIGRATION', '08:42:00', 'com.wellsoft.pt.mail.job.InboxMailMigrationJob', 'OA邮件定时迁移', 'OA邮件定时迁移', 0, null, '第 1', 'SUN,MON', '01', 0, null, 'T001', null, 6, 'timing');
insert into TASK_JOB_DETAILS (uuid, create_time, creator, modifier, modify_time, rec_ver, code, end_time, expression, id, interval_time, job_class_name, name, remark, repeat_count, repeat_day_of_month, repeat_day_of_season, repeat_day_of_week, repeat_day_of_year, repeat_interval, start_time, tenant_id, time_point, timing_mode, type)
values ('1f2a4aa4-03f9-4929-bf9f-063eeb528f2b', to_timestamp('01-01-2014 14:39:26.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('02-01-2014 00:58:46.220000', 'dd-mm-yyyy hh24:mi:ss.ff'), 10, '005', null, '0/60 * * ? * *', 'EXCHANGEDATA_REPEAT', null, 'com.wellsoft.pt.integration.support.ExchangeDataRepeatTask', '数据交换重发', null, null, null, '第 1', null, '01', null, null, 'T001', '14:35:30', 6, 'timing');
insert into TASK_JOB_DETAILS (uuid, create_time, creator, modifier, modify_time, rec_ver, code, end_time, expression, id, interval_time, job_class_name, name, remark, repeat_count, repeat_day_of_month, repeat_day_of_season, repeat_day_of_week, repeat_day_of_year, repeat_interval, start_time, tenant_id, time_point, timing_mode, type)
values ('002c53a9-10b6-4958-b5c8-c4937743fe52', to_timestamp('04-01-2014 17:40:46.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 'U0010000001', to_timestamp('05-01-2014 15:17:10.240000', 'dd-mm-yyyy hh24:mi:ss.ff'), 2, '006', null, '0/60 * * ? * *', 'SHORTMESSAGE', null, 'com.wellsoft.pt.message.sms.MasJob', '短信定时收发', null, null, null, '第 1', null, '01', 0, null, 'T001', null, 1, 'timing');
commit;

alter table msg_short_message add send_statu NUMBER(1);
alter table msg_short_message drop column IS_SEND_SUCC;
alter table msg_short_message rename column send_statu to send_status;

/*通知公告*/
alter table USERFORM_SSGL_NOTICE drop column notice_content;
alter table USERFORM_SSGL_NOTICE  add notice_content  clob;
/*使用帮助*/
alter table userform_ssgl_sybz drop column notice_content;
alter table userform_ssgl_sybz  add notice_content  clob;
/*资料下载*/
alter table userform_ssgl_zlxz drop column notice_content;
alter table userform_ssgl_zlxz  add notice_content  clob;
/*政策法规*/
alter table userform_zcfg_ssgl drop column notice_content;
alter table userform_zcfg_ssgl  add notice_content  clob;

update is_exchange_data t set t.send_limit_num=2 where t.uuid='42355189-ff05-43f2-b52a-fba81e6461a7';
update is_exchange_data t set t.send_limit_num=4 where t.uuid='b14dcd64-9631-462e-b2cd-84f59fbc2764';