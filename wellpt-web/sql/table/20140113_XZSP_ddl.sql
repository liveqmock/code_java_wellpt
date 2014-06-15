update is_exchange_data_monitor m 
set m.receive_status='success',m.receive_time=m.create_time  
where m.reply_limit_status=1 and m.receive_time is null;
commit;

alter table is_exchange_data add  release_time TIMESTAMP(6);

alter table is_exchange_data add  valid_data varchar2(255 CHAR);
