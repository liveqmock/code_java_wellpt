
-- Add/modify columns 
alter table WF_TASK_OPERATION add extra_info VARCHAR2(4000 CHAR);

-- Add/modify columns 
alter table WF_TASK_IDENTITY add related_task_operation_uuid VARCHAR2(255 CHAR);

