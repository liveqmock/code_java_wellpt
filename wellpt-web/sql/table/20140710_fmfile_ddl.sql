-- FM_FILE
ALTER TABLE FM_FILE modify RESERVED_TEXT5  VARCHAR2(2000 CHAR);
ALTER TABLE FM_FILE modify TITLE  VARCHAR2(500 CHAR);
-- FM_FOLDER
alter table FM_FOLDER drop column RESERVED_DATE1;
alter table FM_FOLDER drop column RESERVED_DATE2;
alter table FM_FOLDER drop column RESERVED_DATE3;
alter table FM_FOLDER drop column RESERVED_LONG1;
alter table FM_FOLDER drop column RESERVED_LONG2;
alter table FM_FOLDER drop column RESERVED_LONG3;
alter table FM_FOLDER drop column RESERVED_NUMBER1;
alter table FM_FOLDER drop column RESERVED_NUMBER2;
alter table FM_FOLDER drop column RESERVED_NUMBER3;
alter table FM_FOLDER drop column RESERVED_NUMBER4;
alter table FM_FOLDER drop column RESERVED_NUMBER5;
alter table FM_FOLDER drop column RESERVED_TEXT1;
alter table FM_FOLDER drop column RESERVED_TEXT2;
alter table FM_FOLDER drop column RESERVED_TEXT3;
alter table FM_FOLDER drop column RESERVED_TEXT4;
alter table FM_FOLDER drop column RESERVED_TEXT5;
alter table FM_FOLDER drop column RESERVED_TEXT6;
alter table FM_FOLDER drop column RESERVED_TEXT7;
alter table FM_FOLDER drop column RESERVED_TEXT8;
alter table FM_FOLDER drop column RESERVED_TEXT9;
alter table FM_FOLDER drop column RESERVED_TEXT10;
alter table FM_FOLDER drop column RESERVED_TEXT11;
alter table FM_FOLDER drop column RESERVED_TEXT12;
-- FM_FOLDER_ROLE
ALTER TABLE FM_FOLDER_ROLE modify ROLE_NAME  VARCHAR2(4000 CHAR);
ALTER TABLE FM_FOLDER_ROLE modify ROLE_VALUE  VARCHAR2(4000 CHAR);
commit;
