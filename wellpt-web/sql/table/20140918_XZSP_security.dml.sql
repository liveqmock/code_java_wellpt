-- Alter table 
alter table SYS_IP_CONFIG
  storage
  (
    next 8
  )
;
-- Add/modify columns 
alter table SYS_IP_CONFIG add sid VARCHAR2(255 CHAR);
alter table SYS_IP_CONFIG add sid_name VARCHAR2(255 CHAR);


drop table SYS_IP_CONFIG_USER;
