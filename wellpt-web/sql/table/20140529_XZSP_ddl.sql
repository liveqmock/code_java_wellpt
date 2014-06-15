-- Drop columns 
alter table IS_EXCHANGE_DATA_FILE_UPLOAD drop column NODE_NAME;
alter table IS_EXCHANGE_DATA_FILE_UPLOAD drop column MODULE_NAME;
-- Add/modify columns 
alter table IS_EXCHANGE_DATA_FILE_UPLOAD add FILE_ID VARCHAR2(255 CHAR);