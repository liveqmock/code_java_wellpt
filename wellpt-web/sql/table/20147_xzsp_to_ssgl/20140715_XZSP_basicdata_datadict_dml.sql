-- 打印模板数据迁移

 insert into XZSP_TEST40.cd_print_template(uuid, create_time, creator, modifier, modify_time, rec_ver,
 code, file_name_format, file_uuid, id, is_read_only, is_save_print_record,
 is_save_source, is_save_trace, name, print_interval, row_number, template_type, type, key_words)
 select t.uuid, t.create_time, t.creator, t.modifier, t.modify_time, t.rec_ver,
 t.code, t.file_name_format, t.file_uuid, t.id, t.is_read_only, t.is_save_print_record,
 t.is_save_source, t.is_save_trace, t.name, t.print_interval, t.row_number, t.template_type, t.type, t.key_words
 from XZSP_TEST8.cd_print_template t where t.uuid not in (select uuid from XZSP_TEST40.cd_print_template) 
 order by t.create_time;

commit;