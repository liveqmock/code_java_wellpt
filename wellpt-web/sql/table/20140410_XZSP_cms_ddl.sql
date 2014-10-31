alter table CMS_CATEGORY add SHOW_TITLE VARCHAR2(255 CHAR);
alter table CMS_CATEGORY add RESOURCES VARCHAR2(255 CHAR);
update CMS_CATEGORY t set t.show_title = t.title;
commit;