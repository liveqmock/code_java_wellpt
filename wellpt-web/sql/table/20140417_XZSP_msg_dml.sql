alter table msg_message_template add code varchar2(255 char) ;

-- 窗口登记
insert into msg_message_template (create_time, creator, modifier, 
modify_time, rec_ver, category, code, email_body, email_subject, id, mapping_rule, name, online_body, 
online_subject, send_time, send_way, sms_body, type, uuid) values (sysdate, 
'U0010000001', 'U0010000001', sysdate, 0, '行政审批', '013001', '', '', 'XZSP_T_CKDJ', 
'', '窗口登记', '', '', 'IN_TIME', 'SMS', '窗口登记内容', 'USER', sys_guid()) ;
-- 受理核准
insert into msg_message_template (create_time, creator, modifier, 
modify_time, rec_ver, category, code, email_body, email_subject, id, mapping_rule, name, online_body, 
online_subject, send_time, send_way, sms_body, type, uuid) values (sysdate, 
'U0010000001', 'U0010000001', sysdate, 0, '行政审批', '013002', '', '', 'XZSP_T_SLHZ', 
'', '受理核准', '', '', 'IN_TIME', 'SMS', '受理核准内容', 'USER', sys_guid()) ;
-- 补件
insert into msg_message_template (create_time, creator, modifier, 
modify_time, rec_ver, category, code, email_body, email_subject, id, mapping_rule, name, online_body, 
online_subject, send_time, send_way, sms_body, type, uuid) values (sysdate, 
'U0010000001', 'U0010000001', sysdate, 0, '行政审批', '013003', '', '', 'XZSP_T_BJ', 
'', '补件', '', '', 'IN_TIME', 'SMS', '补件内容', 'USER', sys_guid()) ;
-- 补件登记
insert into msg_message_template (create_time, creator, modifier, 
modify_time, rec_ver, category, code, email_body, email_subject, id, mapping_rule, name, online_body, 
online_subject, send_time, send_way, sms_body, type, uuid) values (sysdate, 
'U0010000001', 'U0010000001', sysdate, 0, '行政审批', '013004', '', '', 'XZSP_T_BJDJ', 
'', '补件登记', '', '', 'IN_TIME', 'SMS', '补件登记内容', 'USER', sys_guid()) ;
-- 退件
insert into msg_message_template (create_time, creator, modifier, 
modify_time, rec_ver, category, code, email_body, email_subject, id, mapping_rule, name, online_body, 
online_subject, send_time, send_way, sms_body, type, uuid) values (sysdate, 
'U0010000001', 'U0010000001', sysdate, 0, '行政审批', '013005', '', '', 'XZSP_T_TJ', 
'', '退件', '', '', 'IN_TIME', 'SMS', '退件内容', 'USER', sys_guid()) ;
-- 逾期退件
insert into msg_message_template (create_time, creator, modifier, 
modify_time, rec_ver, category, code, email_body, email_subject, id, mapping_rule, name, online_body, 
online_subject, send_time, send_way, sms_body, type, uuid) values (sysdate, 
'U0010000001', 'U0010000001', sysdate, 0, '行政审批', '013006', '', '', 'XZSP_T_YQTJ', 
'', '逾期退件', '', '', 'IN_TIME', 'SMS', '逾期退件内容', 'USER', sys_guid()) ;

-- 审批-承办
insert into msg_message_template (create_time, creator, modifier, 
modify_time, rec_ver, category, code, email_body, email_subject, id, mapping_rule, name, online_body, 
online_subject, send_time, send_way, sms_body, type, uuid) values (sysdate, 
'U0010000001', 'U0010000001', sysdate, 0, '行政审批', '013007', '', '', 'XZSP_T_SPCB', 
'', '审批-承办', '', '', 'IN_TIME', 'SMS', '审批-承办内容', 'USER', sys_guid()) ;
-- 审核
insert into msg_message_template (create_time, creator, modifier, 
modify_time, rec_ver, category, code, email_body, email_subject, id, mapping_rule, name, online_body, 
online_subject, send_time, send_way, sms_body, type, uuid) values (sysdate, 
'U0010000001', 'U0010000001', sysdate, 0, '行政审批', '013008', '', '', 'XZSP_T_SH', 
'', '审核', '', '', 'IN_TIME', 'SMS', '审核内容', 'USER', sys_guid()) ;
-- 批准
insert into msg_message_template (create_time, creator, modifier, 
modify_time, rec_ver, category, code, email_body, email_subject, id, mapping_rule, name, online_body, 
online_subject, send_time, send_way, sms_body, type, uuid) values (sysdate, 
'U0010000001', 'U0010000001', sysdate, 0, '行政审批', '013009', '', '', 'XZSP_T_PZ', 
'', '批准', '', '', 'IN_TIME', 'SMS', '批准内容', 'USER', sys_guid()) ;
-- 过程补件
insert into msg_message_template (create_time, creator, modifier, 
modify_time, rec_ver, category, code, email_body, email_subject, id, mapping_rule, name, online_body, 
online_subject, send_time, send_way, sms_body, type, uuid) values (sysdate, 
'U0010000001', 'U0010000001', sysdate, 0, '行政审批', '013010', '', '', 'XZSP_T_GCBJ', 
'', '过程补件', '', '', 'IN_TIME', 'SMS', '过程补件内容', 'USER', sys_guid()) ;
-- 过程补件登记
insert into msg_message_template (create_time, creator, modifier, 
modify_time, rec_ver, category, code, email_body, email_subject, id, mapping_rule, name, online_body, 
online_subject, send_time, send_way, sms_body, type, uuid) values (sysdate, 
'U0010000001', 'U0010000001', sysdate, 0, '行政审批', '013011', '', '', 'XZSP_T_GCBJDJ', 
'', '过程补件登记', '', '', 'IN_TIME', 'SMS', '过程补件登记内容', 'USER', sys_guid()) ;
-- 过程补件核准
insert into msg_message_template (create_time, creator, modifier, 
modify_time, rec_ver, category, code, email_body, email_subject, id, mapping_rule, name, online_body, 
online_subject, send_time, send_way, sms_body, type, uuid) values (sysdate, 
'U0010000001', 'U0010000001', sysdate, 0, '行政审批', '013012', '', '', 'XZSP_T_GCBJHZ', 
'', '过程补件核准', '', '', 'IN_TIME', 'SMS', '过程补件核准内容', 'USER', sys_guid()) ;
-- 过程退件
insert into msg_message_template (create_time, creator, modifier, 
modify_time, rec_ver, category, code, email_body, email_subject, id, mapping_rule, name, online_body, 
online_subject, send_time, send_way, sms_body, type, uuid) values (sysdate, 
'U0010000001', 'U0010000001', sysdate, 0, '行政审批', '013013', '', '', 'XZSP_T_GCTJ', 
'', '过程退件', '', '', 'IN_TIME', 'SMS', '过程退件内容', 'USER', sys_guid()) ;
-- 特殊程序开始
insert into msg_message_template (create_time, creator, modifier, 
modify_time, rec_ver, category, code, email_body, email_subject, id, mapping_rule, name, online_body, 
online_subject, send_time, send_way, sms_body, type, uuid) values (sysdate, 
'U0010000001', 'U0010000001', sysdate, 0, '行政审批', '013014', '', '', 'XZSP_T_TSCXKS', 
'', '特殊程序开始', '', '', 'IN_TIME', 'SMS', '特殊程序开始内容', 'USER', sys_guid()) ;
-- 特殊程序结束
insert into msg_message_template (create_time, creator, modifier, 
modify_time, rec_ver, category, code, email_body, email_subject, id, mapping_rule, name, online_body, 
online_subject, send_time, send_way, sms_body, type, uuid) values (sysdate, 
'U0010000001', 'U0010000001', sysdate, 0, '行政审批', '013015', '', '', 'XZSP_T_TSCXJS', 
'', '特殊程序结束', '', '', 'IN_TIME', 'SMS', '特殊程序结束内容', 'USER', sys_guid()) ;

-- 出文
insert into msg_message_template (create_time, creator, modifier, 
modify_time, rec_ver, category, code, email_body, email_subject, id, mapping_rule, name, online_body, 
online_subject, send_time, send_way, sms_body, type, uuid) values (sysdate, 
'U0010000001', 'U0010000001', sysdate, 0, '行政审批', '013016', '', '', 'XZSP_T_CW', 
'', '出文', '', '', 'IN_TIME', 'SMS', '出文内容', 'USER', sys_guid()) ;
-- 窗口发件
insert into msg_message_template (create_time, creator, modifier, 
modify_time, rec_ver, category, code, email_body, email_subject, id, mapping_rule, name, online_body, 
online_subject, send_time, send_way, sms_body, type, uuid) values (sysdate, 
'U0010000001', 'U0010000001', sysdate, 0, '行政审批', '013017', '', '', 'XZSP_T_CKFJ', 
'', '窗口发件', '', '', 'IN_TIME', 'SMS', '窗口发件内容', 'USER', sys_guid()) ;

commit;
