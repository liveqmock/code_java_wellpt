--视图数据从test8库到test40库
ALTER TABLE XZSP_TEST40.Dyview_View_Definition add temp NUMBER(10);
ALTER TABLE XZSP_TEST8.Dyview_View_Definition add temp NUMBER(10);
update XZSP_TEST8.Dyview_View_Definition t8
   set t8.temp = 1
 where t8.uuid in
       (select t1.uuid
          from XZSP_TEST8.Dyview_View_Definition t1
         where t1.uuid not in
               (select t2.uuid from XZSP_TEST40.Dyview_View_Definition t2));
--视图 
insert into XZSP_TEST40.Dyview_View_Definition
  select *
    from XZSP_TEST8.Dyview_View_Definition t1
   where t1.uuid not in
         (select t2.uuid from XZSP_TEST40.Dyview_View_Definition t2);
--列        
insert into XZSP_TEST40.Dyview_Column_Definition
  select *
    from XZSP_TEST8.Dyview_Column_Definition t1
   where t1.view_def_uuid in (select t2.uuid
                                from XZSP_TEST8.Dyview_View_Definition t2
                               where t2.temp = 1);
--查询
insert into XZSP_TEST40.Dyview_Select_Definition
  select *
    from XZSP_TEST8.Dyview_Select_Definition t1
   where t1.view_def_uuid in (select t2.uuid
                                from XZSP_TEST8.Dyview_View_Definition t2
                               where t2.temp = 1);
--行样式
insert into XZSP_TEST40.Dyview_Columncss_Definition
  select *
    from XZSP_TEST8.Dyview_Columncss_Definition t1
   where t1.view_def_uuid in (select t2.uuid
                                from XZSP_TEST8.Dyview_View_Definition t2
                               where t2.temp = 1);
--按钮
insert into XZSP_TEST40.Dyview_View_Custom_Button
  select *
    from XZSP_TEST8.Dyview_View_Custom_Button t1
   where t1.view_def_uuid in (select t2.uuid
                                from XZSP_TEST8.Dyview_View_Definition t2
                               where t2.temp = 1);             
--分页            
insert into XZSP_TEST40.Dyview_Page_Definition
  select *
    from XZSP_TEST8.Dyview_Page_Definition t1
   where t1.view_def_uuid in (select t2.uuid
                                from XZSP_TEST8.Dyview_View_Definition t2
                               where t2.temp = 1);                                
--搜索
insert into XZSP_TEST40.Dyview_Select_Condition_Type
  select *
    from XZSP_TEST8.Dyview_Select_Condition_Type t1
   where t1.uuid not in
         (select t2.uuid from XZSP_TEST40.Dyview_Select_Condition_Type t2);
insert into XZSP_TEST40.Dyview_Select_Exactkey
  select *
    from XZSP_TEST8.Dyview_Select_Exactkey t1
   where t1.uuid not in
         (select t2.uuid from XZSP_TEST40.Dyview_Select_Exactkey t2);

alter table XZSP_TEST40.DYVIEW_VIEW_DEFINITION drop column temp;
alter table XZSP_TEST8.DYVIEW_VIEW_DEFINITION drop column temp;
commit;

--导航
insert into XZSP_TEST31.Cms_Category select * from XZSP_TEST8.Cms_Category t where t.SHOW_TITLE like '%xzsp%' ;
commit;

--页面元素、widget、page
insert into XZSP_TEST40.Cms_Page select * from XZSP_TEST31.Cms_Page t1 where t1.uuid not in(select t2.uuid from XZSP_TEST40.Cms_Page t2);
insert into XZSP_TEST40.Cms_Module select * from XZSP_TEST31.Cms_Module t1 where t1.uuid not in(select t2.uuid from XZSP_TEST40.Cms_Module t2);
insert into XZSP_TEST40.Cms_Widget select * from XZSP_TEST31.Cms_Widget t1 where t1.uuid not in (
select p.uuid from XZSP_TEST40.Cms_Widget p) and t1.page_uuid in(select p.uuid from XZSP_TEST40.Cms_Page p );
commit;