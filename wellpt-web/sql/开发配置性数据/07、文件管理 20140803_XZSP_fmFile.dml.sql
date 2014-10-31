--查询商事中的行政审批相关，并删除

--假删除文件夹FmFolder
select * from XZSP.FM_FOLDER t where t.id = 'MATTERS_LIB' or t.id = 'LICENSE_LIB' or t.id = 'MATERIAL_MANAGE' or t.id = 'UNIT_MANAGE' or t.id = 'MATERIAL_LIB';
update XZSP.fm_folder folder set folder.state = '0' where folder.uuid in(select t.uuid from XZSP.FM_FOLDER t where t.id = 'MATTERS_LIB' or t.id = 'LICENSE_LIB' or t.id = 'MATERIAL_MANAGE' or t.id = 'UNIT_MANAGE' or t.id = 'MATERIAL_LIB');

--假删除文档FmFile
select * from XZSP.fm_file f where f.folder_id in(select t.uuid from XZSP.FM_FOLDER t where t.id = 'MATTERS_LIB' or t.id = 'LICENSE_LIB' or t.id = 'MATERIAL_MANAGE' or t.id = 'UNIT_MANAGE' or t.id = 'MATERIAL_LIB');
update XZSP.fm_file fm set fm.status = '0' where fm.uuid in(select f.uuid from XZSP.fm_file f where f.folder_id in(select t.uuid from XZSP.FM_FOLDER t where t.id = 'MATTERS_LIB' or t.id = 'LICENSE_LIB' or t.id = 'MATERIAL_MANAGE' or t.id = 'UNIT_MANAGE' or t.id = 'MATERIAL_LIB'));

--delete fm_folder_role
select *
  from XZSP.FM_FOLDER_ROLE r
 where r.obj_id in (select t.uuid from XZSP.FM_FOLDER t where t.state = 0);

delete from XZSP.FM_FOLDER_ROLE r where r.obj_id in (select t.uuid from XZSP.FM_FOLDER t where t.state = 0);

select *
  from XZSP.FM_FOLDER_ROLE r
 where r.file_id in (select t.uuid from XZSP.FM_FILE t where t.status = 0);

delete from XZSP.FM_FOLDER_ROLE r where r.file_id in (select t.uuid from XZSP.FM_FILE t where t.status = 0);

--delete FM_FOLDER_DYNAMIC_FORM
select * from XZSP.FM_FOLDER_DYNAMIC_FORM form where form.obj_id in (select t.uuid from XZSP.FM_FOLDER t where t.state = 0);

delete  from XZSP.FM_FOLDER_DYNAMIC_FORM form where form.obj_id in (select t.uuid from XZSP.FM_FOLDER t where t.state = 0);

--delete FM_FILE
select * from XZSP.FM_FILE f where f.folder_id in (select t.uuid from XZSP.FM_FOLDER t where t.state = 0);
delete from XZSP.FM_FILE f where f.folder_id in (select t.uuid from XZSP.FM_FOLDER t where t.state = 0);
select * from XZSP.FM_FILE f where f.status = 0;
delete from XZSP.FM_FILE f where f.status = 0;

--delete FM_FOLDER
select * from XZSP.fm_folder f where  f.parent_uuid in (select t.uuid from XZSP.FM_FOLDER t where t.state = 0);
delete from XZSP.fm_folder f where  f.parent_uuid in (select t.uuid from XZSP.FM_FOLDER t where t.state = 0);
select * from XZSP.FM_FOLDER t where t.state = 0;
delete from XZSP.FM_FOLDER t where t.state = 0;


--将行政审批的数据插入商事管理中

--FM_FOLDER_ROLE_USER
insert into XZSP.FM_FOLDER_ROLE_USER
  select *
    from XZSP_46.FM_FOLDER_ROLE_USER t1
   where t1.uuid not in
         (select t2.uuid from XZSP.FM_FOLDER_ROLE_USER t2);
         
--FM_FOLDER
insert into XZSP.FM_FOLDER
  select *
    from XZSP_46.FM_FOLDER t1
   where t1.uuid not in
         (select t2.uuid from XZSP.FM_FOLDER t2);
         
--FM_FILE
insert into XZSP.FM_FILE
  select *
    from XZSP_46.FM_FILE t1
   where t1.uuid not in
         (select t2.uuid from XZSP.FM_FILE t2);
         
--FM_FILE_VERSION
insert into XZSP.FM_FILE_VERSION
  select *
    from XZSP_46.FM_FILE_VERSION t1
   where t1.uuid not in
         (select t2.uuid from XZSP.FM_FILE_VERSION t2);
         
--FM_FOLDER_ROLE
insert into XZSP.FM_FOLDER_ROLE
  select *
    from XZSP_46.FM_FOLDER_ROLE t1
   where t1.uuid not in
         (select t2.uuid from XZSP.FM_FOLDER_ROLE t2);
   
--FM_FOLDER_DYNAMIC_FORM
insert into XZSP.FM_FOLDER_DYNAMIC_FORM
  select *
    from XZSP_46.FM_FOLDER_DYNAMIC_FORM t1
   where t1.uuid not in
         (select t2.uuid from XZSP.FM_FOLDER_DYNAMIC_FORM t2);



         


         

         



