-- Add/modify columns 
alter table IS_EXCHANGE_DATA_FILE_UPLOAD rename column filename to file_name;
-- Add/modify columns 
alter table IS_EXCHANGE_DATA_FILE_UPLOAD add file_id VARCHAR2(255 CHAR);
