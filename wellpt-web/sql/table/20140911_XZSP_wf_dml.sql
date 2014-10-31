alter table WF_TASK_INSTANCE add is_parallel NUMBER(1,0);
alter table WF_TASK_INSTANCE add parallel_task_inst_uuid varchar2(255 char);