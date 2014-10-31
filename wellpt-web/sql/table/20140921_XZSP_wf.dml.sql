-- Add/modify columns 
alter table wf_flow_instance add reserved_text11 VARCHAR2(255 CHAR);
alter table wf_flow_instance add reserved_text12 VARCHAR2(255 CHAR);


insert into cd_data_dict (create_time, creator, modifier, modify_time, 
rec_ver, code, name, parent_uuid, type, uuid) values (to_timestamp('21-09-2014 22:50:12.956', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 
'U0010000001', to_timestamp('21-09-2014 22:50:12.956', 'dd-mm-yyyy hh24:mi:ss.ff'), 0, 'WORKFLOW_RESERVED_TEXT_11', '预留文本字段11(255字符长度)', 
'9ded6b48-3fb3-4447-9431-794322b46166', '', '4bb34cd8-6ffb-461d-9d04-d7e469539123');

insert into cd_data_dict (create_time, creator, modifier, modify_time, 
rec_ver, code, name, parent_uuid, type, uuid) values (to_timestamp('21-09-2014 22:53:06.102', 'dd-mm-yyyy hh24:mi:ss.ff'), 'U0010000001', 
'U0010000001', to_timestamp('21-09-2014 22:53:06.102', 'dd-mm-yyyy hh24:mi:ss.ff'), 0, 'WORKFLOW_RESERVED_TEXT_12', '预留文本字段12(255字符长度)', 
'9ded6b48-3fb3-4447-9431-794322b46166', '', 'd092537d-f356-44db-8797-e8b06b38eeee') ;

-- 更新办理状态
update wf_flow_instance t set t.reserved_text11 = (select t1.bjzt from uf_xzsp_bjsq t1 where t1.lsh = t.reserved_text10 and rownum <= 1 );

commit;
