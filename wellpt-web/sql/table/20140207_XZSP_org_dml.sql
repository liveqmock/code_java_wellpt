alter table task_job_details rename column interval_time to repeat_interval_time;

alter table org_option add owner varchar2(4000 char);

alter table org_option add owner_name varchar2(4000 char);

alter table org_option rename column value to id;
