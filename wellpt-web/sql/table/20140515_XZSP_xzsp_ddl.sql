create table xzsp_chuwen (uuid varchar2(255 char) not null, create_time timestamp, creator 
varchar2(255 char), modifier varchar2(255 char), modify_time timestamp, rec_ver number(10,0), 
approval_code varchar2(255 char), approval_name varchar2(255 char), approval_id varchar2(255 char), approval_time timestamp, 
effective varchar2(255 char), flow_inst_uuid varchar2(255 char), folder_id varchar2(255 char), 
next_update_time timestamp, purpose varchar2(255 char), status number(10,0), task_inst_uuid 
varchar2(255 char), unit_id varchar2(255 char), unit_name varchar2(255 char), update_time timestamp, 
primary key (uuid)) ;
