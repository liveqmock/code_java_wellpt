-- Alter table 
alter table WF_TASK_INSTANCE
  storage
  (
    next 1
  )
;
-- Add/modify columns 
alter table WF_TASK_INSTANCE modify reserved_text1 VARCHAR2(64 CHAR);
