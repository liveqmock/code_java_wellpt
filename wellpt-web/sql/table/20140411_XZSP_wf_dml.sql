alter table wf_flow_instance add alarm_time timestamp ;
alter table wf_flow_instance add due_time timestamp ;
alter table wf_flow_instance add reserved_date1 timestamp ;
alter table wf_flow_instance add reserved_date2 timestamp ;
alter table wf_flow_instance add reserved_number1 number(10,0) ;
alter table wf_flow_instance add reserved_number2 double precision ;
alter table wf_flow_instance add reserved_number3 double precision ;
alter table wf_flow_instance add reserved_text1 varchar2(255 char) ;
alter table wf_flow_instance add reserved_text2 varchar2(255 char) ;
alter table wf_flow_instance add reserved_text3 varchar2(255 char) ;
alter table wf_flow_instance add reserved_text4 varchar2(255 char) ;
alter table wf_flow_instance add reserved_text5 varchar2(255 char) ;
alter table wf_flow_instance add reserved_text6 varchar2(255 char) ;
alter table wf_flow_instance add reserved_text7 varchar2(255 char) ;
alter table wf_flow_instance add reserved_text8 varchar2(255 char) ;
alter table wf_flow_instance add reserved_text9 varchar2(255 char) ;

alter table wf_task_instance add action varchar2(255 char) ;

alter table wf_task_operation add action varchar2(255 char) ;

alter table wf_task_operation add opinion_label varchar2(255 char) ;

