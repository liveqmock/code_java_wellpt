--视图数据从test8库到test40库
ALTER TABLE XZSP_42.Dyview_View_Definition add temp NUMBER(10);
ALTER TABLE XZSP_43.Dyview_View_Definition add temp NUMBER(10);
update XZSP_43.Dyview_View_Definition t8
   set t8.temp = 1
 where t8.uuid in
       (select t1.uuid
          from XZSP_43.Dyview_View_Definition t1
         where t1.uuid not in
               (select t2.uuid from XZSP_42.Dyview_View_Definition t2));
--视图 
insert into XZSP_42.Dyview_View_Definition
  select *
    from XZSP_43.Dyview_View_Definition t1
   where t1.uuid not in
         (select t2.uuid from XZSP_42.Dyview_View_Definition t2);
--列        
insert into XZSP_42.Dyview_Column_Definition
  select *
    from XZSP_43.Dyview_Column_Definition t1
   where t1.view_def_uuid in (select t2.uuid
                                from XZSP_43.Dyview_View_Definition t2
                               where t2.temp = 1);
--查询
insert into XZSP_42.Dyview_Select_Definition
  select *
    from XZSP_43.Dyview_Select_Definition t1
   where t1.view_def_uuid in (select t2.uuid
                                from XZSP_43.Dyview_View_Definition t2
                               where t2.temp = 1);
--行样式
insert into XZSP_42.Dyview_Columncss_Definition
  select *
    from XZSP_43.Dyview_Columncss_Definition t1
   where t1.view_def_uuid in (select t2.uuid
                                from XZSP_43.Dyview_View_Definition t2
                               where t2.temp = 1);
--按钮
insert into XZSP_42.Dyview_View_Custom_Button
  select *
    from XZSP_43.Dyview_View_Custom_Button t1
   where t1.view_def_uuid in (select t2.uuid
                                from XZSP_43.Dyview_View_Definition t2
                               where t2.temp = 1);             
--分页            
insert into XZSP_42.Dyview_Page_Definition
  select *
    from XZSP_43.Dyview_Page_Definition t1
   where t1.view_def_uuid in (select t2.uuid
                                from XZSP_43.Dyview_View_Definition t2
                               where t2.temp = 1);                                
--搜索
insert into XZSP_42.Dyview_Select_Condition_Type
  select *
    from XZSP_43.Dyview_Select_Condition_Type t1
   where t1.uuid not in
         (select t2.uuid from XZSP_42.Dyview_Select_Condition_Type t2);
insert into XZSP_42.Dyview_Select_Exactkey
  select *
    from XZSP_43.Dyview_Select_Exactkey t1
   where t1.uuid not in
         (select t2.uuid from XZSP_42.Dyview_Select_Exactkey t2);

alter table XZSP_42.DYVIEW_VIEW_DEFINITION drop column temp;
alter table XZSP_43.DYVIEW_VIEW_DEFINITION drop column temp;
commit;

--更新导航数据
update audit_resource t set t.parent_uuid = '25e2ca0a-53c6-48dd-a018-dfd697674043' 
where t.parent_uuid = '9557c638-8867-453a-a8f9-9e82dc0b0dc5' and t.name like '商事管理%';
commit;

update audit_resource t set t.code = '019008004'  where t.uuid = 'cb199d3f-c1c7-48f0-b906-f90f85e20fe1';
update audit_resource t set t.code = '019008007'  where t.uuid = '423eedc5-cbeb-4923-80b5-b27da60c8b23';
update audit_resource t set t.code = '019008008'  where t.uuid = 'f28320cc-ccd1-40bb-a13c-fc03925059b4';
update audit_resource t set t.code = '019008009'  where t.uuid = 'ecfd7f10-eb73-4a2f-bf63-4e739454205a';
update audit_resource t set t.code = '019008016'  where t.uuid = '0b458d99-c021-4d1e-a90e-e29522fa2fc6';
update audit_resource t set t.code = '019008018'  where t.uuid = '28ef6f4c-67ef-49f8-b1c3-7e1d986b9a10';
update audit_resource t set t.code = '019008013'  where t.uuid = 'de77b9fc-6856-4297-98f1-116ffb8d7d23';
update audit_resource t set t.code = '019008005'  where t.uuid = 'de1fb0d3-1639-494a-a658-076b4ff8386f';
update audit_resource t set t.code = '019008010'  where t.uuid = '90cf973c-675f-4a9f-9b8b-f6237f176cf6';
update audit_resource t set t.code = '019008011'  where t.uuid = 'b52f5899-f864-4943-97b5-a194b6851d2e';
update audit_resource t set t.code = '019008012'  where t.uuid = 'c82cb370-fe37-4d78-bc16-5f19370cda91';
update audit_resource t set t.code = '019008014'  where t.uuid = '5ae5040d-a2d7-4597-b910-1b826af63fcf';
update audit_resource t set t.code = '019'  where t.uuid = '6cbef820-776c-4f71-a04a-b8f59fbd7173';
update audit_resource t set t.code = '019008019'  where t.uuid = 'd8f11f36-4aed-47ab-840a-890fbacc934a';
update audit_resource t set t.code = '019008006'  where t.uuid = '3d2d0319-335b-4252-ac12-6d9aa2e89318';
update audit_resource t set t.code = '019008015'  where t.uuid = 'd4021ecc-b90f-4814-912b-82acf3096188';
update audit_resource t set t.code = '019008001'  where t.uuid = 'cc998937-6a44-4b1d-9f1b-f2941bb2982b';
update audit_resource t set t.code = '019008002'  where t.uuid = '80dbe692-12db-4d00-a2bc-b871a0115a21';
update audit_resource t set t.code = '019008017'  where t.uuid = '3d8ada87-7b16-42eb-a03d-847d4a9f5feb';
update audit_resource t set t.code = '019008020'  where t.uuid = 'c2d8e4a4-1fcf-4d9e-8a67-aa61b6534f28';

update audit_resource t set t.code = '019002004001'  where t.uuid = 'c0c2f7fb-429c-42de-b9d6-faafb5263e3e';
update audit_resource t set t.code = '019002004002'  where t.uuid = 'a10608dc-0a60-48fe-8184-a64921af7954';
update audit_resource t set t.code = '019002004003'  where t.uuid = '3cd707bf-d637-42f8-9cc3-5912584bddf4';

update audit_resource t set t.code = '019002007001'  where t.uuid = 'c6d4c405-77e5-4de6-90f5-3a52aed3bbec';

update audit_resource t set t.code = '019002008001'  where t.uuid = 'cd8527f1-1aa1-45e5-8178-41ecb97978fd';

update audit_resource t set t.code = '019002009001'  where t.uuid = '5f2bd5ba-94e7-41b3-8945-414ec493a86b';

update audit_resource t set t.code = '019002016001'  where t.uuid = '393c5412-4ad0-4479-a761-4cb0437ba060';

update audit_resource t set t.code = '019002018001'  where t.uuid = '1206f832-eb15-47c0-9b1d-3a4082ae514f';
update audit_resource t set t.code = '019002018002'  where t.uuid = 'ad247886-ac20-443b-99b9-9bcd3183ab8c';
update audit_resource t set t.code = '019002018003'  where t.uuid = 'd7e9a38f-2e89-462b-9e6b-a1a04ae9772b';
update audit_resource t set t.code = '019002018004'  where t.uuid = 'a87efcfa-8ff4-4f19-9304-60a2a7bd10f3';

update audit_resource t set t.code = '019002013001'  where t.uuid = 'd8cda52e-263c-4d41-8f72-1ae9d058b96d';
update audit_resource t set t.code = '019002013002'  where t.uuid = '114b4193-cff4-47c1-bed7-bf19e8890137';
update audit_resource t set t.code = '019002013003'  where t.uuid = '43800087-814e-4389-a1f0-098f3671cfb9';
update audit_resource t set t.code = '019002013004'  where t.uuid = 'cf933b71-ac80-4b2a-80d6-e19f24651c70';
update audit_resource t set t.code = '019002013005'  where t.uuid = '4f2f5e2f-5e43-4bfd-9cfd-a41d46e26bd5';

update audit_resource t set t.code = '019002005002'  where t.uuid = 'a7760268-87be-4e6a-b90f-dc18694a8b1e';
update audit_resource t set t.code = '019002005003'  where t.uuid = 'bc450483-aeb3-487d-a5a6-d3d1a4f056b4';

update audit_resource t set t.code = '019002010001'  where t.uuid = 'f40744d2-72b2-4b04-8937-ee04c1bd9007';
update audit_resource t set t.code = '019002010002'  where t.uuid = '93a6bef6-a14c-4433-8ff4-8c94b92672e6';
update audit_resource t set t.code = '019002010003'  where t.uuid = '1a33fa01-e9c6-4baa-a67d-2ca8d0c7c679';
update audit_resource t set t.code = '019002010004'  where t.uuid = 'ac7672e6-b598-4588-80ef-0d11591059dd';
update audit_resource t set t.code = '019002010005'  where t.uuid = '925ad570-868b-4064-9598-673073df2c97';
update audit_resource t set t.code = '019002010006'  where t.uuid = 'dffbbf4e-1fee-475b-bc6a-010596e41e23';
update audit_resource t set t.code = '019002010007'  where t.uuid = 'e7a5f3d5-2873-43f6-a1eb-0bebb0035482';
update audit_resource t set t.code = '019002010008'  where t.uuid = '40f842a0-a261-467d-b7fd-26e7b6a973f2';
update audit_resource t set t.code = '019002010009'  where t.uuid = '6bf27d6f-a059-4e90-82fc-682c5413f617';
update audit_resource t set t.code = '019002010010'  where t.uuid = '8fdc21bc-6040-42f9-97b6-cc860813f955';
update audit_resource t set t.code = '019002010011'  where t.uuid = '39746778-4a5a-4c2f-ae05-b8629d4f72ca';

update audit_resource t set t.code = '019002011001'  where t.uuid = '94ff346f-b46c-4ad6-b0c9-89a76e33562e';
update audit_resource t set t.code = '019002011002'  where t.uuid = 'fcd78a8c-bca1-4b1f-82fb-4b70dd18701e';
update audit_resource t set t.code = '019002011003'  where t.uuid = 'c6763960-dba6-4173-b45a-511e2de2f791';
update audit_resource t set t.code = '019002011004'  where t.uuid = '3ce70f60-5b59-4bc9-890a-dbf51d48ee62';

update audit_resource t set t.code = '019002012001'  where t.uuid = '785f9270-b72d-4e26-975c-f989c25aba4d';

update audit_resource t set t.code = '019002014001'  where t.uuid = 'c4e08988-9f29-48c8-9a89-434448ead761';
update audit_resource t set t.code = '019002014002'  where t.uuid = '1f0c9db3-dbb0-4bc6-8b22-702e2bfffbd4';
update audit_resource t set t.code = '019002014003'  where t.uuid = '90f76891-2c6c-4970-8a5a-3885012d2382';
update audit_resource t set t.code = '019002014004'  where t.uuid = 'a77179d4-66cd-4822-abca-0f2e66539821';

update audit_resource t set t.code = '019002'  where t.uuid = '093052f4-f76b-40bb-b1f3-6560f09e2d59';
update audit_resource t set t.code = '019003'  where t.uuid = 'eee4556f-871e-404c-986e-2d114a2fc6b2';
update audit_resource t set t.code = '019004'  where t.uuid = 'ca87253f-7483-4590-93de-9a61dfb38fd8';
update audit_resource t set t.code = '019005'  where t.uuid = '6c63e2e5-1293-4d10-aebb-1204478a246d';
update audit_resource t set t.code = '019006'  where t.uuid = 'fb6df54e-3d5d-4eec-817a-34751a370db1';
update audit_resource t set t.code = '019007'  where t.uuid = '69f1d9f0-4b36-4338-8611-7008db2ecccd';
update audit_resource t set t.code = '019008'  where t.uuid = '25e2ca0a-53c6-48dd-a018-dfd697674043';

update audit_resource t set t.code = '019002019001'  where t.uuid = 'a497db76-966e-48b6-804f-d4d082be0e94';

update audit_resource t set t.code = '019002006002'  where t.uuid = '210df0f8-ae8d-4d86-9913-9002fdd4e806';
update audit_resource t set t.code = '019002006001'  where t.uuid = 'd0afc389-fce9-43ec-9f99-6e9299829019';

update audit_resource t set t.code = '019002015002'  where t.uuid = '296a5804-639a-4f93-a47c-c72c7a3c774b';
update audit_resource t set t.code = '019002015001'  where t.uuid = '0d627ab5-7458-4319-828c-727a02ad8e2e';
update audit_resource t set t.code = '019002015003'  where t.uuid = '26c80c05-c348-492c-8e92-ee0b1631b60a';

update audit_resource t set t.code = '019002001012'  where t.uuid = '22e0ea89-69a0-4d2c-8793-c01647dce600';
update audit_resource t set t.code = '019002001013'  where t.uuid = '0190f22e-0fb5-414d-a099-fecce78858b1';
update audit_resource t set t.code = '019002001014'  where t.uuid = 'fc65638d-8033-40fb-9cdf-dab53a7545b0';
update audit_resource t set t.code = '019002001001'  where t.uuid = '6256e640-bce9-4dda-91e6-9abad1500fbc';
update audit_resource t set t.code = '019002001002'  where t.uuid = 'a91880cd-267d-4f32-b6fc-eae200152b81';
update audit_resource t set t.code = '019002001003'  where t.uuid = '59bf06e9-9efc-418a-a449-f8ad42aed2a6';
update audit_resource t set t.code = '019002001004'  where t.uuid = '08a25b1c-10e0-49af-8447-69a6970d582e';
update audit_resource t set t.code = '019002001005'  where t.uuid = '70a56933-c456-4f78-b03f-c5fc868c19ba';
update audit_resource t set t.code = '019002001009'  where t.uuid = 'd4ed6f69-835e-4c9e-99ee-63046c58744f';
update audit_resource t set t.code = '019002001010'  where t.uuid = 'd21bf52f-495e-46af-af54-298bce6b2211';
update audit_resource t set t.code = '019002001011'  where t.uuid = '8b311088-ceeb-4a84-855f-c9b9c4ad763c';

update audit_resource t set t.code = '019002002006'  where t.uuid = '6b2171a9-e699-4698-a22f-1a033e6c7dfa';
update audit_resource t set t.code = '019002002007'  where t.uuid = '4c37cf46-6c22-40b6-af53-8a0e4b158852';
update audit_resource t set t.code = '019002002002'  where t.uuid = 'f16fc614-8d9e-4b58-b780-4713d7785b3b';
update audit_resource t set t.code = '019002002003'  where t.uuid = '47315030-b283-4892-97fd-bb220dfd70a3';
update audit_resource t set t.code = '019002002004'  where t.uuid = '5f00dd1a-eacd-4b06-858f-fd4fb9ea5182';

update audit_resource t set t.code = '019002017001'  where t.uuid = '9029de90-3874-4af5-88b3-fced367efc71';
update audit_resource t set t.code = '019002017002'  where t.uuid = '0645b7b5-8f36-444d-aa8d-49a41cc3871b';
update audit_resource t set t.code = '019002017003'  where t.uuid = '86bb7b7b-d42d-4d0a-bee0-f4789b9faaec';

update audit_resource t set t.code = '019008020001'  where t.uuid = '5492154f-65fd-4614-8454-1b975c16d13a';
update audit_resource t set t.code = '019008020002'  where t.uuid = '2319ecb8-e0a3-4e0b-a6be-f60e0f6cc559';
update audit_resource t set t.code = '019008020003'  where t.uuid = '553d068c-db12-4641-9387-4d8d1f330fb3';
update audit_resource t set t.code = '019008020004'  where t.uuid = 'bfef021f-9020-45cf-977c-7499d91f1c2a';
commit;






--导航
--将正式机上的界面管理下面的商事管理导航迁移到商事管理模块的导航设置中
select t.name, t.uuid, t.code, t.parent_uuid from audit_resource t where t.name like '商事管理%';

update audit_resource t set t.parent_uuid = '25e2ca0a-53c6-48dd-a018-dfd697674043' 
where t.parent_uuid = '9557c638-8867-453a-a8f9-9e82dc0b0dc5' and t.name like '商事管理%';

select t.name, t.uuid, t.code, t.parent_uuid from audit_resource t where t.parent_uuid = 'de1fb0d3-1639-494a-a658-076b4ff8386f';
update audit_resource t set t.code = '019002005001'  where t.uuid = 'a7760268-87be-4e6a-b90f-dc18694a8b1e';
update audit_resource t set t.code = '019002005002'  where t.uuid = 'a7760268-87be-4e6a-b90f-dc18694a8b1e';



insert into XZSP_42.Cms_Category select * from XZSP_43.Cms_Category t where t.SHOW_TITLE like '%xzsp%' ;

--页面元素、widget、page
insert into XZSP_42.Cms_Page select * from XZSP_43.Cms_Page t1 where t1.uuid not in(select t2.uuid from XZSP_42.Cms_Page t2);
insert into XZSP_42.Cms_Module select * from XZSP_43.Cms_Module t1 where t1.uuid not in(select t2.uuid from XZSP_42.Cms_Module t2);
insert into XZSP_42.Cms_Widget select * from XZSP_43.Cms_Widget t1 where t1.uuid not in (
select p.uuid from XZSP_42.Cms_Widget p) and t1.page_uuid in(select p.uuid from XZSP_42.Cms_Page p );


update xzsp_42.audit_resource t set t.code = (select t2.code from xzsp_43.audit_resource t2 where t2.uuid = t.uuid);
update xzsp_42.cms_category t set t.resources = (select t2.resources from xzsp_43.cms_category t2 where t2.uuid = t.uuid);

