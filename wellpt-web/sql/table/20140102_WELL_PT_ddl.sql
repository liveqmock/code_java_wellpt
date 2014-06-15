create table mt_pinyin (uuid varchar2(255 char) not null, create_time timestamp, creator varchar2(255 
char), modifier varchar2(255 char), modify_time timestamp, rec_ver number(10,0), entity_uuid 
varchar2(255 char), pinyin varchar2(255 char), type varchar2(255 char), primary key (uuid)) 