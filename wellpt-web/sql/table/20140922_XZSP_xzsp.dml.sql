-- Add/modify columns 
alter table xzsp_banjian add action_status VARCHAR2(255 CHAR);
alter table xzsp_banjian add action VARCHAR2(255 CHAR);
alter table xzsp_banjian add action_type VARCHAR2(255 CHAR);
alter table xzsp_banjian add tj_manner_value VARCHAR2(255 CHAR);
alter table xzsp_banjian add tj_manner_text VARCHAR2(255 CHAR);

alter table xzsp_banjian_process add action_status VARCHAR2(255 CHAR);
