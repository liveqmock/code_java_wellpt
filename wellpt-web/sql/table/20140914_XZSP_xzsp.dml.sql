-- Alter table 
alter table XZSP_EXCHANGE_AUGURIT
  storage
  (
    next 8
  )
;
-- Add/modify columns 
alter table XZSP_EXCHANGE_AUGURIT add type_id VARCHAR2(255 CHAR);
alter table XZSP_EXCHANGE_AUGURIT add tenant_id VARCHAR2(255 CHAR);
alter table XZSP_EXCHANGE_AUGURIT add task_inst_uuid VARCHAR2(255 CHAR);
