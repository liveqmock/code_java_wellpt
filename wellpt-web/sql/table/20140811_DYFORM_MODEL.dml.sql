create table DYFORM_DISPLAY_MODEL
(
  UUID             VARCHAR2(50) not null,
  CREATE_TIME      TIMESTAMP(6),
  CREATOR          VARCHAR2(50),
  MODIFY_TIME      TIMESTAMP(6),
  REC_VER          NUMBER(10),
  OUTER_ID         VARCHAR2(255 CHAR),
  DISPLAY_NAME     VARCHAR2(100 CHAR),
  REMARK           VARCHAR2(500 CHAR),
  HTML             CLOB,
  REFERRED_FORM_ID VARCHAR2(255 CHAR),
  MODIFIER         VARCHAR2(50),
  PREVIEW          VARCHAR2(10)
) ;
-- Create/Recreate primary, unique and foreign key constraints 
alter table DYFORM_DISPLAY_MODEL
  add constraint DYFORM_DISPLAY_MODE_PK primary key (UUID)
