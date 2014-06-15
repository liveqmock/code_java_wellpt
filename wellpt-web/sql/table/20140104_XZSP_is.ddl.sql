/*monitor加入事项字段*/
alter table is_exchange_data_monitor  add matter  VARCHAR2(255 CHAR)

/*创建重发记录表 新建实体ExchangeDataRepest*/
create table IS_EXCHANGE_DATA_REPEST
(
  uuid                       VARCHAR2(255 CHAR) not null,
  create_time                TIMESTAMP(6),
  creator                    VARCHAR2(255 CHAR),
  modifier                   VARCHAR2(255 CHAR),
  modify_time                TIMESTAMP(6),
  rec_ver                    NUMBER(10),
  batch_id                   VARCHAR2(255 CHAR),
  code                       NUMBER(10),
  data_id                    VARCHAR2(255 CHAR),
  data_rec_ver               NUMBER(10),
  exchange_data_monitor_uuid VARCHAR2(255 CHAR),
  from_id                    VARCHAR2(255 CHAR),
  interval                   NUMBER(10),
  msg                        VARCHAR2(255 CHAR),
  op_time                    TIMESTAMP(6),
  retransmission_num         NUMBER(10),
  send_method                VARCHAR2(255 CHAR),
  send_num                   NUMBER(10),
  status                     VARCHAR2(255 CHAR),
  system_uuid                VARCHAR2(255 CHAR),
  unit_id                    VARCHAR2(255 CHAR)
)