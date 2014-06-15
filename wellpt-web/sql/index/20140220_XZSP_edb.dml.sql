-- Create/Recreate indexes 
create index IESM_DEFINATION_BATCH_IDX on IS_EXCHANGE_SEND_MONITOR (FROM_ID, DATA_UUID, SEND_NODE, CREATOR, SEND_TIME)
  tablespace USERS
  pctfree 10
  initrans 2
  maxtrans 163
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
-- Create/Recreate indexes 
create index IEDM_DEFINATION_BATCH_IDX on IS_EXCHANGE_DATA_MONITOR (UNIT_ID, SEND_ID, RECEIVE_NODE, CREATE_TIME)
  tablespace USERS
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
-- Create/Recreate indexes 
create index IED_DEFINATION_BATCH_IDX on IS_EXCHANGE_DATA (upload_limit_num)
  tablespace USERS
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
