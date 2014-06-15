create table xzsp_bujian (uuid varchar2(255 char) not null, create_time timestamp, creator 
varchar2(255 char), modifier varchar2(255 char), modify_time timestamp, rec_ver number(10,0), 
action varchar2(255 char), flow_inst_uuid varchar2(255 char), opinion_text varchar2(255 char), 
opinion_value varchar2(255 char), dead_line timestamp, qztj_status number(10,0), required number(10,0), status number(10,0), 
task_inst_uuid varchar2(255 char), type varchar2(255 char), primary key (uuid)) ;
