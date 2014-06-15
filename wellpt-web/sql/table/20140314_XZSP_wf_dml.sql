alter table wf_task_instance drop column reserved_text5;

alter table wf_task_instance add reserved_text5 varchar2(255 char);

alter table wf_task_instance add reserved_text6 varchar2(255 char);

alter table wf_task_instance add reserved_text7 varchar2(2000 char);

