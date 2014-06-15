create table wf_flow_instance_param (uuid varchar2(255 char) not null, flow_inst_uuid varchar2(255 char), 
	name varchar2(255 char), value varchar2(255 char), primary key (uuid));
	
alter table wf_flow_instance add form_uuid varchar2(255 char) ;

alter table wf_task_timer add status number(10,0);

alter table wf_task_timer drop column last_pause_time;

alter table wf_task_sub_flow_relation add allow_submit number(1,0);

alter table wf_task_sub_flow_relation add submit_status number(10,0);

alter table wf_task_sub_flow_relation drop column status;
