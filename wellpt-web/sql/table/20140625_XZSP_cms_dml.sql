--视图
delete from XZSP.Dyview_Select_Condition_Type;
delete from XZSP.Dyview_Select_Exactkey;
delete from XZSP.Dyview_Select_Definition;
delete from XZSP.Dyview_Column_Definition;
delete from XZSP.Dyview_Columncss_Definition;
delete from XZSP.Dyview_View_Custom_Button;
delete from XZSP.Dyview_Page_Definition;
delete from XZSP.Dyview_View_Definition;
commit;
insert into XZSP.Dyview_View_Definition select * from XZSP_31.Dyview_View_Definition;
insert into XZSP.Dyview_Select_Definition select * from XZSP_31.Dyview_Select_Definition;
insert into XZSP.Dyview_Column_Definition select * from XZSP_31.Dyview_Column_Definition;
insert into XZSP.Dyview_Columncss_Definition select * from XZSP_31.Dyview_Columncss_Definition;
insert into XZSP.Dyview_View_Custom_Button select * from XZSP_31.Dyview_View_Custom_Button;
insert into XZSP.Dyview_Page_Definition select * from XZSP_31.Dyview_Page_Definition;
insert into XZSP.Dyview_Select_Condition_Type select * from XZSP_31.Dyview_Select_Condition_Type;
insert into XZSP.Dyview_Select_Exactkey select * from XZSP_31.Dyview_Select_Exactkey;
commit;

--导航
delete from XZSP.Cms_Category;
commit;
insert into XZSP.Cms_Category select * from XZSP_31.Cms_Category;
commit;

--页面元素、widget、page
delete from XZSP.Cms_Widget;
delete from XZSP.Cms_Module;
delete from XZSP.Cms_Page;
commit;
insert into XZSP.Cms_Page select * from XZSP_31.Cms_Page;
insert into XZSP.Cms_Module select * from XZSP_31.Cms_Module;
insert into XZSP.Cms_Widget select * from XZSP_31.Cms_Widget;
commit;