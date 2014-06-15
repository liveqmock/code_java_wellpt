create table is_exchange_data_file_upload (uuid varchar2(255 char) not null, create_time timestamp, 
creator varchar2(255 char), modifier varchar2(255 char), modify_time timestamp, rec_ver number(10,0), 
batch_id varchar2(255 char), business_type_id varchar2(255 char), business_type_name varchar2(255 
char), certificate varchar2(2000 char), content_type varchar2(255 char), department_id varchar2(255 
char), department_name varchar2(255 char), digest_algorithm varchar2(255 char), digest_value 
varchar2(255 char), file_size number(19,0) not null, filename varchar2(255 char), module_name 
varchar2(255 char), node_name varchar2(255 char), sign_upload_file number(1,0) not null, signature_value 
varchar2(255 char), type_id varchar2(255 char), type_name varchar2(255 char), unit_id varchar2(255 
char), unit_name varchar2(255 char), upload_time timestamp, user_id varchar2(255 char), user_name 
varchar2(255 char), primary key (uuid)) ;