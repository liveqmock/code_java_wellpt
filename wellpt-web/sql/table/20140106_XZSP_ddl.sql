/*修改注册号为空的最新状态，（注册号为空）上次已退回*/
update is_exchange_data t set t.newest_data='no' where t.reserved_number2 is null;
/*修改有许可经营范围，但是没有指定相关许可单位的数据，text5的数据库执行正常，数据正常*/
/*更新代码后，备份数据，执行控制层 /exchangedata/client/repairData*/

create table msg_mas_config (uuid varchar2(255 char) not null, create_time timestamp, creator 
varchar2(255 char), modifier varchar2(255 char), modify_time timestamp, rec_ver number(10,0), 
api_code varchar2(255 char), db_name varchar2(255 char), im_ip varchar2(255 char), is_open 
number(1,0), login_name varchar2(255 char), login_password varchar2(255 char), send_limit number(10,0), 
primary key (uuid));

drop table msg_short_message;

create table MSG_SHORT_MESSAGE
(
  UUID                   VARCHAR2(255 CHAR) not null,
  CREATE_TIME            TIMESTAMP(6),
  CREATOR                VARCHAR2(255 CHAR),
  MODIFIER               VARCHAR2(255 CHAR),
  MODIFY_TIME            TIMESTAMP(6),
  REC_VER                NUMBER(10),
  BODY                   VARCHAR2(255 CHAR),
  IS_SEND_SUCC           NUMBER(1),
  ISREAD                 NUMBER(1), 
  RECEIVED               VARCHAR2(255 CHAR),
  RECEIVED_TIME          TIMESTAMP(6),
  RECIPIENT_MOBILE_PHONE VARCHAR2(255 CHAR),
  RECIPIENT_NAME         VARCHAR2(255 CHAR),
  SEND                   VARCHAR2(255 CHAR),
  SEND_MOBILE_PHONE      VARCHAR2(255 CHAR),
  SEND_TIME              TIMESTAMP(6),
  SENDER_NAME            VARCHAR2(255 CHAR),
  SMID                   NUMBER(10),
  TYPE                   NUMBER(10),
  REC_ID 				 NUMBER(10),
  REC_MSG                VARCHAR2(255 CHAR),
  IS_RE_SEND             NUMBER(1)
)