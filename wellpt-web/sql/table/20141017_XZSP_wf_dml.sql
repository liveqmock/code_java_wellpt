-- Add/modify columns 
alter table WF_TASK_INSTANCE add todo_user_id VARCHAR2(4000 CHAR);
alter table WF_TASK_INSTANCE add todo_user_name VARCHAR2(4000 CHAR);


-- Add/modify columns 
alter table WF_TASK_OPERATION add task_name VARCHAR2(255 CHAR);

