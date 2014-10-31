alter table schedule add is_leader_view varchar2(255 char);

create table SCHEDULE_TAG
(
  uuid           VARCHAR2(255 CHAR) not null,
  create_time    TIMESTAMP(6),
  creator        VARCHAR2(255 CHAR),
  modifier       VARCHAR2(255 CHAR),
  modify_time    TIMESTAMP(6),
  rec_ver        NUMBER(10),
  color          VARCHAR2(255 CHAR),
  name           VARCHAR2(255 CHAR),
  sort           NUMBER(10),
  user_id        VARCHAR2(255 CHAR),
  is_leader_view VARCHAR2(255 CHAR),
  is_view        VARCHAR2(2 CHAR),
  view_ids       VARCHAR2(255 CHAR),
  view_names     VARCHAR2(255 CHAR)
);
create table SCHEDULE_TAG_SCHE
(
  uuid        VARCHAR2(255 CHAR) not null,
  create_time TIMESTAMP(6),
  creator     VARCHAR2(255 CHAR),
  modifier    VARCHAR2(255 CHAR),
  modify_time TIMESTAMP(6),
  rec_ver     NUMBER(10),
  schedule_id VARCHAR2(255 CHAR),
  tag_id      VARCHAR2(255 CHAR)
);