-- Add/modify columns 
alter table WF_TASK_SUB_FLOW modify COPY_FIELDS VARCHAR2(4000 CHAR);
alter table WF_TASK_SUB_FLOW modify RETURN_ADDITION_FIELDS VARCHAR2(4000 CHAR);
alter table WF_TASK_SUB_FLOW modify RETURN_OVERRIDE_FIELDS VARCHAR2(4000 CHAR);