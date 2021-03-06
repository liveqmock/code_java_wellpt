-- Add/modify columns 
alter table MSG_MESSAGE_TEMPLATE modify EMAIL_BODY VARCHAR2(2000 CHAR);
alter table MSG_MESSAGE_TEMPLATE modify ONLINE_BODY VARCHAR2(2000 CHAR);
alter table MSG_MESSAGE_TEMPLATE modify SMS_BODY VARCHAR2(2000 CHAR);
alter table MSG_MESSAGE_TEMPLATE add online_feedback NUMBER(1);
alter table MSG_MESSAGE_TEMPLATE add arrive_event VARCHAR2(255 CHAR);
alter table MSG_MESSAGE_TEMPLATE add online_arrive_js_event VARCHAR2(255 CHAR);
alter table MSG_MESSAGE_TEMPLATE add online_level VARCHAR2(255 CHAR);
alter table MSG_MESSAGE_TEMPLATE add online_feedback_option VARCHAR2(255 CHAR);
alter table MSG_MESSAGE_TEMPLATE add online_feedback_event VARCHAR2(255 CHAR);
alter table MSG_MESSAGE_TEMPLATE add online_add_schedule NUMBER(1);
alter table MSG_MESSAGE_TEMPLATE add online_forward NUMBER(1);
alter table MSG_MESSAGE_TEMPLATE add online_forward_explain VARCHAR2(255 CHAR);
alter table MSG_MESSAGE_TEMPLATE add schedule_title VARCHAR2(255 CHAR);
alter table MSG_MESSAGE_TEMPLATE add schedule_begin_date TIMESTAMP(6);
alter table MSG_MESSAGE_TEMPLATE add schedule_addrees VARCHAR2(255 CHAR);
alter table MSG_MESSAGE_TEMPLATE add schedule_call_date TIMESTAMP(6);
alter table MSG_MESSAGE_TEMPLATE add schedule_body VARCHAR2(2000 CHAR);
alter table MSG_MESSAGE_TEMPLATE add rws_url VARCHAR2(255 CHAR);
alter table MSG_MESSAGE_TEMPLATE add rws_verify_msg VARCHAR2(255 CHAR);
alter table MSG_MESSAGE_TEMPLATE add rws_content VARCHAR2(255 CHAR);