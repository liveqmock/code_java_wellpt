create table xzsp_banjian (uuid varchar2(255 char) not null, create_time timestamp, creator 
varchar2(255 char), modifier varchar2(255 char), modify_time timestamp, rec_ver number(10,0), 
task_inst_uuid varchar2(255 char), flow_inst_uuid varchar2(255 char), matters_id varchar2(255 char), matters_name varchar2(255 
char), owner_department_id varchar2(255 char), owner_unit_id varchar2(255 char), project_code 
varchar2(255 char), project_id varchar2(255 char), project_name varchar2(255 char), task_id 
varchar2(255 char), task_name varchar2(255 char), status varchar2(255 char), primary key (uuid)) ;