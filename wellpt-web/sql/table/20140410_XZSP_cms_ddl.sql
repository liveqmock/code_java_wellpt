alter table CMS_CATEGORY modify SHOW_TITLE VARCHAR2(255 CHAR);
alter table CMS_CATEGORY modify RESOURCE VARCHAR2(255 CHAR);
update CMS_CATEGORY t set t.show_title = t.title;
commit;