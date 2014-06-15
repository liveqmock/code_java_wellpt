--------------------1.找从表----------------------------------------------
delete from DYTABLE_FIELD_RELATION a where a.uuid in(
 select fr.uuid from DYTABLE_FORM_RELATION t,DYTABLE_FIELD_RELATION fr
  where t.main_form_uuid='3eb52dec-a241-42f7-ada2-c57c725bcdfd'
 and t.uuid = fr.form_relation_uuid);
 
 delete from DYTABLE_FORM_RELATION t
 where t.main_form_uuid='3eb52dec-a241-42f7-ada2-c57c725bcdfd';
 
 ---------------------2.找字段规则表---------------------------------------
 delete from dytable_field_check_rule a where a.uuid in(
select fc.uuid 
  from DYTABLE_FIELD_DEFINITION fd,
       dytable_field_check_rule fc,
       DYTABLE_FORM_DEFINITION  fm
 where fd.uuid = fc.field_definition_uid and fm.uuid = fd.form_uuid and fm.uuid = '3eb52dec-a241-42f7-ada2-c57c725bcdfd'
 );
 
 ---------------------3.找字段定义为字典的数据-----------------------------
delete from DYTABLE_FIELD_OPTION a where a.uuid in (
select fo.uuid as fouuid
  from DYTABLE_FIELD_DEFINITION fd,
       DYTABLE_FIELD_OPTION fo,
       DYTABLE_FORM_DEFINITION  fm
 where fo.field_definition_uid = fd.uuid and fm.uuid = fd.form_uuid and fm.uuid = '3eb52dec-a241-42f7-ada2-c57c725bcdfd'
 );
 
 delete from DYTABLE_FIELD_DEFINITION a where a.uuid in (
select fd.uuid as fouuid
  from DYTABLE_FIELD_DEFINITION fd,
       DYTABLE_FORM_DEFINITION  fm
 where fm.uuid = fd.form_uuid and fm.uuid = '3eb52dec-a241-42f7-ada2-c57c725bcdfd'
 );
 
 delete from DYTABLE_FORM_DEFINITION t where t.uuid='3eb52dec-a241-42f7-ada2-c57c725bcdfd';