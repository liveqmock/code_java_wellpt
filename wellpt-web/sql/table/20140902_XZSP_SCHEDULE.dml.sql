alter table schedule add content varchar2(255 char);

create table SCHEDULE_ATTENTION
(
  uuid           VARCHAR2(255 CHAR) not null,
  create_time    TIMESTAMP(6),
  creator        VARCHAR2(255 CHAR),
  modifier       VARCHAR2(255 CHAR),
  modify_time    TIMESTAMP(6),
  rec_ver        NUMBER(10),
  attention_id   VARCHAR2(255 CHAR),
  user_id        VARCHAR2(255 CHAR),
  attention_name VARCHAR2(255 CHAR)
);
create table SCHEDULE_TAG_MEMBER
(
  uuid        VARCHAR2(255 CHAR) not null,
  create_time TIMESTAMP(6),
  creator     VARCHAR2(255 CHAR),
  modifier    VARCHAR2(255 CHAR),
  modify_time TIMESTAMP(6),
  rec_ver     NUMBER(10),
  member_id   VARCHAR2(255 CHAR),
  member_name VARCHAR2(255 CHAR),
  member_type VARCHAR2(255 CHAR),
  tag_id      VARCHAR2(255 CHAR)
);