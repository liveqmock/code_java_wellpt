-- Create/Recreate primary, unique and foreign key constraints 
alter table IS_EXCHANGE_SEND_MONITOR
  add primary key (UUID)
  using index 
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
alter table IS_EXCHANGE_SEND_MONITOR
  add constraint FK_JIADUG73QLIF7FW67EKKDIS9P foreign key (DATA_UUID)
  references IS_EXCHANGE_DATA (UUID);