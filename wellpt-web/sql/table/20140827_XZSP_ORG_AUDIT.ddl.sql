-- Add/modify columns 

--资源权限其他关联表
alter table audit_privilege_other_resource add uuid VARCHAR2(255 CHAR);
alter table audit_privilege_other_resource add CREATOR VARCHAR2(255 CHAR);
alter table audit_privilege_other_resource add CREATE_TIME TIMESTAMP(6);
alter table audit_privilege_other_resource add MODIFIER VARCHAR2(255 CHAR);
alter table audit_privilege_other_resource add MODIFY_TIME TIMESTAMP(6);
alter table audit_privilege_other_resource add REC_VER NUMBER(10);

--用户其他属性表
alter table ORG_USER_PROPERTY add CREATOR VARCHAR2(255 CHAR);
alter table ORG_USER_PROPERTY add CREATE_TIME TIMESTAMP(6);
alter table ORG_USER_PROPERTY add MODIFIER VARCHAR2(255 CHAR);
alter table ORG_USER_PROPERTY add MODIFY_TIME TIMESTAMP(6);
alter table ORG_USER_PROPERTY add REC_VER NUMBER(10);


--更新user_property记录.
update org_user_property a set a.creator='U0010000001',a.create_time='28-8月 -14 10.50.02.121000 上午',a.modifier='U0010000001',a.modify_time='28-8月 -14 10.50.02.121000 上午',a.rec_ver=0 where a.creator is null;

--更新权限视图资源记录 audit_privilege_other_resource
update audit_privilege_other_resource a set a.creator='U0010000001',a.create_time='28-8月 -14 10.50.02.121000 上午',a.modifier='U0010000001',a.modify_time='28-8月 -14 10.50.02.121000 上午',a.rec_ver=0,a.uuid= SYS_GUID() where a.uuid is null;



insert into cd_system_table_entity (UUID, CREATE_TIME, CREATOR, MODIFIER, MODIFY_TIME, REC_VER, CHINESE_NAME, CODE, FULL_ENTITY_NAME, MODULE_NAME, REMARK, TABLE_NAME)
values ('e292db1c-7ce5-42ba-949b-bdc4a94fc0be', '28-8月 -14 06.57.33.191000 下午', 'U0010000001', 'U0010000001', '28-8月 -14 06.57.33.191000 下午', 0, '用户其他属性表', '031', 'com.wellsoft.pt.org.entity.UserProperty', 'BASICDATA', '', 'org_user_property');


insert into cd_system_table_entity_attr (UUID, CREATE_TIME, CREATOR, MODIFIER, MODIFY_TIME, REC_VER, ATTRIBUTE_NAME, CHINESE_NAME, COLUMN_ALIASES, COLUMN_TYPE, CONSTANT, DATA_DICTIONARY, ENTITY_NAME, IS_ORGANIZE_SELECTION_BOX, REMARK, SYSTEM_TABLE_UUID, FIELD_NAME, IS_SYNCHRONIZATION)
values ('9228ddaa-051c-44ca-99a7-629967a51356', '28-8月 -14 06.57.33.193000 下午', 'U0010000001', 'U0010000001', '28-8月 -14 06.57.33.193000 下午', 0, 'name', '', 'userProperty_name', '', '', '', 'UserProperty', null, '', 'e292db1c-7ce5-42ba-949b-bdc4a94fc0be', 'name', null);

insert into cd_system_table_entity_attr (UUID, CREATE_TIME, CREATOR, MODIFIER, MODIFY_TIME, REC_VER, ATTRIBUTE_NAME, CHINESE_NAME, COLUMN_ALIASES, COLUMN_TYPE, CONSTANT, DATA_DICTIONARY, ENTITY_NAME, IS_ORGANIZE_SELECTION_BOX, REMARK, SYSTEM_TABLE_UUID, FIELD_NAME, IS_SYNCHRONIZATION)
values ('837104f0-b07c-4aae-bf24-a676205ff936', '28-8月 -14 06.57.33.195000 下午', 'U0010000001', 'U0010000001', '28-8月 -14 06.57.33.195000 下午', 0, 'userUuid', '', 'userProperty_userUuid', '', '', '', 'UserProperty', null, '', 'e292db1c-7ce5-42ba-949b-bdc4a94fc0be', 'user_uuid', null);

insert into cd_system_table_entity_attr (UUID, CREATE_TIME, CREATOR, MODIFIER, MODIFY_TIME, REC_VER, ATTRIBUTE_NAME, CHINESE_NAME, COLUMN_ALIASES, COLUMN_TYPE, CONSTANT, DATA_DICTIONARY, ENTITY_NAME, IS_ORGANIZE_SELECTION_BOX, REMARK, SYSTEM_TABLE_UUID, FIELD_NAME, IS_SYNCHRONIZATION)
values ('41bbc06d-6bfc-4dbd-8211-7fcdc7805d81', '28-8月 -14 06.57.33.196000 下午', 'U0010000001', 'U0010000001', '28-8月 -14 06.57.33.196000 下午', 0, 'uuid', '', 'userProperty_uuid', '', '', '', 'UserProperty', null, '', 'e292db1c-7ce5-42ba-949b-bdc4a94fc0be', 'uuid', null);

insert into cd_system_table_entity_attr (UUID, CREATE_TIME, CREATOR, MODIFIER, MODIFY_TIME, REC_VER, ATTRIBUTE_NAME, CHINESE_NAME, COLUMN_ALIASES, COLUMN_TYPE, CONSTANT, DATA_DICTIONARY, ENTITY_NAME, IS_ORGANIZE_SELECTION_BOX, REMARK, SYSTEM_TABLE_UUID, FIELD_NAME, IS_SYNCHRONIZATION)
values ('a7fe2e6f-0031-4024-9578-bbce950ccf0b', '28-8月 -14 06.57.33.197000 下午', 'U0010000001', 'U0010000001', '28-8月 -14 06.57.33.197000 下午', 0, 'value', '', 'userProperty_value', '', '', '', 'UserProperty', null, '', 'e292db1c-7ce5-42ba-949b-bdc4a94fc0be', 'value', null);

insert into cd_system_table_entity_attr (UUID, CREATE_TIME, CREATOR, MODIFIER, MODIFY_TIME, REC_VER, ATTRIBUTE_NAME, CHINESE_NAME, COLUMN_ALIASES, COLUMN_TYPE, CONSTANT, DATA_DICTIONARY, ENTITY_NAME, IS_ORGANIZE_SELECTION_BOX, REMARK, SYSTEM_TABLE_UUID, FIELD_NAME, IS_SYNCHRONIZATION)
values ('d3743552-fe79-4008-b8cf-7e38c6c66502', '28-8月 -14 07.40.03.624000 下午', 'U0010000001', 'U0010000001', '28-8月 -14 07.40.03.624000 下午', 0, 'recVer', '', 'userProperty_recVer', '', '', '', 'UserProperty', null, '', 'e292db1c-7ce5-42ba-949b-bdc4a94fc0be', 'rec_ver', null);

insert into cd_system_table_entity_attr (UUID, CREATE_TIME, CREATOR, MODIFIER, MODIFY_TIME, REC_VER, ATTRIBUTE_NAME, CHINESE_NAME, COLUMN_ALIASES, COLUMN_TYPE, CONSTANT, DATA_DICTIONARY, ENTITY_NAME, IS_ORGANIZE_SELECTION_BOX, REMARK, SYSTEM_TABLE_UUID, FIELD_NAME, IS_SYNCHRONIZATION)
values ('8f410c32-7a4a-4d36-b575-46d1c369473c', '28-8月 -14 07.40.03.612000 下午', 'U0010000001', 'U0010000001', '28-8月 -14 07.40.03.612000 下午', 0, 'createTime', '', 'userProperty_createTime', '', '', '', 'UserProperty', null, '', 'e292db1c-7ce5-42ba-949b-bdc4a94fc0be', 'create_time', null);

insert into cd_system_table_entity_attr (UUID, CREATE_TIME, CREATOR, MODIFIER, MODIFY_TIME, REC_VER, ATTRIBUTE_NAME, CHINESE_NAME, COLUMN_ALIASES, COLUMN_TYPE, CONSTANT, DATA_DICTIONARY, ENTITY_NAME, IS_ORGANIZE_SELECTION_BOX, REMARK, SYSTEM_TABLE_UUID, FIELD_NAME, IS_SYNCHRONIZATION)
values ('76c8e56a-5165-40a9-93a4-e0cab194ed56', '28-8月 -14 07.40.03.623000 下午', 'U0010000001', 'U0010000001', '28-8月 -14 07.40.03.623000 下午', 0, 'creator', '', 'userProperty_creator', '', '', '', 'UserProperty', null, '', 'e292db1c-7ce5-42ba-949b-bdc4a94fc0be', 'creator', null);

insert into cd_system_table_entity_attr (UUID, CREATE_TIME, CREATOR, MODIFIER, MODIFY_TIME, REC_VER, ATTRIBUTE_NAME, CHINESE_NAME, COLUMN_ALIASES, COLUMN_TYPE, CONSTANT, DATA_DICTIONARY, ENTITY_NAME, IS_ORGANIZE_SELECTION_BOX, REMARK, SYSTEM_TABLE_UUID, FIELD_NAME, IS_SYNCHRONIZATION)
values ('dbb32897-556c-46d2-8226-421fbe5426c1', '28-8月 -14 07.40.03.624000 下午', 'U0010000001', 'U0010000001', '28-8月 -14 07.40.03.624000 下午', 0, 'modifier', '', 'userProperty_modifier', '', '', '', 'UserProperty', null, '', 'e292db1c-7ce5-42ba-949b-bdc4a94fc0be', 'modifier', null);

insert into cd_system_table_entity_attr (UUID, CREATE_TIME, CREATOR, MODIFIER, MODIFY_TIME, REC_VER, ATTRIBUTE_NAME, CHINESE_NAME, COLUMN_ALIASES, COLUMN_TYPE, CONSTANT, DATA_DICTIONARY, ENTITY_NAME, IS_ORGANIZE_SELECTION_BOX, REMARK, SYSTEM_TABLE_UUID, FIELD_NAME, IS_SYNCHRONIZATION)
values ('34753f1d-88a5-4ad8-837b-ba1b51b2140c', '28-8月 -14 07.40.03.624000 下午', 'U0010000001', 'U0010000001', '28-8月 -14 07.40.03.624000 下午', 0, 'modifyTime', '', 'userProperty_modifyTime', '', '', '', 'UserProperty', null, '', 'e292db1c-7ce5-42ba-949b-bdc4a94fc0be', 'modify_time', null);

insert into cd_system_table_entity (UUID, CREATE_TIME, CREATOR, MODIFIER, MODIFY_TIME, REC_VER, CHINESE_NAME, CODE, FULL_ENTITY_NAME, MODULE_NAME, REMARK, TABLE_NAME)
values ('3be6f2d0-adc1-4dbf-9ba2-9faa038c297f', '28-8月 -14 06.53.52.462000 下午', 'U0010000001', 'U0010000001', '28-8月 -14 06.53.52.462000 下午', 0, '权限视图资源表', '030', 'com.wellsoft.pt.security.audit.entity.PrivilegeResource', 'BASICDATA', '', 'audit_privilege_other_resource');

insert into cd_system_table_entity_attr (UUID, CREATE_TIME, CREATOR, MODIFIER, MODIFY_TIME, REC_VER, ATTRIBUTE_NAME, CHINESE_NAME, COLUMN_ALIASES, COLUMN_TYPE, CONSTANT, DATA_DICTIONARY, ENTITY_NAME, IS_ORGANIZE_SELECTION_BOX, REMARK, SYSTEM_TABLE_UUID, FIELD_NAME, IS_SYNCHRONIZATION)
values ('e2727533-0717-48b1-8ceb-0f3105a41c06', '28-8月 -14 08.13.06.935000 下午', 'U0010000001', 'U0010000001', '28-8月 -14 08.13.06.935000 下午', 0, 'createTime', '', 'privilegeResource_createTime', '', '', '', 'PrivilegeResource', null, '', '3be6f2d0-adc1-4dbf-9ba2-9faa038c297f', 'create_time', null);

insert into cd_system_table_entity_attr (UUID, CREATE_TIME, CREATOR, MODIFIER, MODIFY_TIME, REC_VER, ATTRIBUTE_NAME, CHINESE_NAME, COLUMN_ALIASES, COLUMN_TYPE, CONSTANT, DATA_DICTIONARY, ENTITY_NAME, IS_ORGANIZE_SELECTION_BOX, REMARK, SYSTEM_TABLE_UUID, FIELD_NAME, IS_SYNCHRONIZATION)
values ('ea2d9b12-e849-4c04-9c17-2cf108d5eb36', '28-8月 -14 08.13.06.947000 下午', 'U0010000001', 'U0010000001', '28-8月 -14 08.13.06.947000 下午', 0, 'creator', '', 'privilegeResource_creator', '', '', '', 'PrivilegeResource', null, '', '3be6f2d0-adc1-4dbf-9ba2-9faa038c297f', 'creator', null);

insert into cd_system_table_entity_attr (UUID, CREATE_TIME, CREATOR, MODIFIER, MODIFY_TIME, REC_VER, ATTRIBUTE_NAME, CHINESE_NAME, COLUMN_ALIASES, COLUMN_TYPE, CONSTANT, DATA_DICTIONARY, ENTITY_NAME, IS_ORGANIZE_SELECTION_BOX, REMARK, SYSTEM_TABLE_UUID, FIELD_NAME, IS_SYNCHRONIZATION)
values ('90becbe9-f3a3-4130-ae58-3e536b27a5e2', '28-8月 -14 08.13.06.948000 下午', 'U0010000001', 'U0010000001', '28-8月 -14 08.13.06.948000 下午', 0, 'modifier', '', 'privilegeResource_modifier', '', '', '', 'PrivilegeResource', null, '', '3be6f2d0-adc1-4dbf-9ba2-9faa038c297f', 'modifier', null);

insert into cd_system_table_entity_attr (UUID, CREATE_TIME, CREATOR, MODIFIER, MODIFY_TIME, REC_VER, ATTRIBUTE_NAME, CHINESE_NAME, COLUMN_ALIASES, COLUMN_TYPE, CONSTANT, DATA_DICTIONARY, ENTITY_NAME, IS_ORGANIZE_SELECTION_BOX, REMARK, SYSTEM_TABLE_UUID, FIELD_NAME, IS_SYNCHRONIZATION)
values ('9f8073ca-158e-4fb3-8077-b9133a0467eb', '28-8月 -14 08.13.06.948000 下午', 'U0010000001', 'U0010000001', '28-8月 -14 08.13.06.948000 下午', 0, 'modifyTime', '', 'privilegeResource_modifyTime', '', '', '', 'PrivilegeResource', null, '', '3be6f2d0-adc1-4dbf-9ba2-9faa038c297f', 'modify_time', null);

insert into cd_system_table_entity_attr (UUID, CREATE_TIME, CREATOR, MODIFIER, MODIFY_TIME, REC_VER, ATTRIBUTE_NAME, CHINESE_NAME, COLUMN_ALIASES, COLUMN_TYPE, CONSTANT, DATA_DICTIONARY, ENTITY_NAME, IS_ORGANIZE_SELECTION_BOX, REMARK, SYSTEM_TABLE_UUID, FIELD_NAME, IS_SYNCHRONIZATION)
values ('9cefeaa9-1a87-4099-bb4c-9f4a6c913dfe', '28-8月 -14 08.13.06.949000 下午', 'U0010000001', 'U0010000001', '28-8月 -14 08.13.06.949000 下午', 0, 'privilegeUuid', '', 'privilegeResource_privilegeUuid', '', '', '', 'PrivilegeResource', null, '', '3be6f2d0-adc1-4dbf-9ba2-9faa038c297f', 'privilege_uuid', null);

insert into cd_system_table_entity_attr (UUID, CREATE_TIME, CREATOR, MODIFIER, MODIFY_TIME, REC_VER, ATTRIBUTE_NAME, CHINESE_NAME, COLUMN_ALIASES, COLUMN_TYPE, CONSTANT, DATA_DICTIONARY, ENTITY_NAME, IS_ORGANIZE_SELECTION_BOX, REMARK, SYSTEM_TABLE_UUID, FIELD_NAME, IS_SYNCHRONIZATION)
values ('09fe15db-7f73-488c-8e7c-01b9dd996f29', '28-8月 -14 08.13.06.949000 下午', 'U0010000001', 'U0010000001', '28-8月 -14 08.13.06.949000 下午', 0, 'recVer', '', 'privilegeResource_recVer', '', '', '', 'PrivilegeResource', null, '', '3be6f2d0-adc1-4dbf-9ba2-9faa038c297f', 'rec_ver', null);

insert into cd_system_table_entity_attr (UUID, CREATE_TIME, CREATOR, MODIFIER, MODIFY_TIME, REC_VER, ATTRIBUTE_NAME, CHINESE_NAME, COLUMN_ALIASES, COLUMN_TYPE, CONSTANT, DATA_DICTIONARY, ENTITY_NAME, IS_ORGANIZE_SELECTION_BOX, REMARK, SYSTEM_TABLE_UUID, FIELD_NAME, IS_SYNCHRONIZATION)
values ('5c5bf589-fbc4-421d-b4db-416f2794a409', '28-8月 -14 08.13.06.950000 下午', 'U0010000001', 'U0010000001', '28-8月 -14 08.13.06.950000 下午', 0, 'resourceUuid', '', 'privilegeResource_resourceUuid', '', '', '', 'PrivilegeResource', null, '', '3be6f2d0-adc1-4dbf-9ba2-9faa038c297f', 'resource_uuid', null);

insert into cd_system_table_entity_attr (UUID, CREATE_TIME, CREATOR, MODIFIER, MODIFY_TIME, REC_VER, ATTRIBUTE_NAME, CHINESE_NAME, COLUMN_ALIASES, COLUMN_TYPE, CONSTANT, DATA_DICTIONARY, ENTITY_NAME, IS_ORGANIZE_SELECTION_BOX, REMARK, SYSTEM_TABLE_UUID, FIELD_NAME, IS_SYNCHRONIZATION)
values ('f54f030b-4fc4-4add-8e74-ebdae00af4d3', '28-8月 -14 08.13.06.950000 下午', 'U0010000001', 'U0010000001', '28-8月 -14 08.13.06.950000 下午', 0, 'type', '', 'privilegeResource_type', '', '', '', 'PrivilegeResource', null, '', '3be6f2d0-adc1-4dbf-9ba2-9faa038c297f', 'type', null);

insert into cd_system_table_entity_attr (UUID, CREATE_TIME, CREATOR, MODIFIER, MODIFY_TIME, REC_VER, ATTRIBUTE_NAME, CHINESE_NAME, COLUMN_ALIASES, COLUMN_TYPE, CONSTANT, DATA_DICTIONARY, ENTITY_NAME, IS_ORGANIZE_SELECTION_BOX, REMARK, SYSTEM_TABLE_UUID, FIELD_NAME, IS_SYNCHRONIZATION)
values ('674426fe-6357-4268-b311-ba2ad4f39330', '28-8月 -14 08.13.06.950000 下午', 'U0010000001', 'U0010000001', '28-8月 -14 08.13.06.950000 下午', 0, 'uuid', '', 'privilegeResource_uuid', '', '', '', 'PrivilegeResource', null, '', '3be6f2d0-adc1-4dbf-9ba2-9faa038c297f', 'uuid', null);



commit;
