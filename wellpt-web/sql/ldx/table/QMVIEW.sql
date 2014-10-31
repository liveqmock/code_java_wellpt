-- Create table
create table QMVIEW
(
  uuid        VARCHAR2(255 CHAR) not null,
  create_time TIMESTAMP(6),
  creator     VARCHAR2(255 CHAR),
  modifier    VARCHAR2(255 CHAR),
  modify_time TIMESTAMP(6),
  rec_ver     NUMBER(10),
  wl          VARCHAR2(255 CHAR),
  shortdesc   VARCHAR2(255 CHAR),
  factory     VARCHAR2(255 CHAR),
  qmpcgl      VARCHAR2(255 CHAR),
  qmjylx01    VARCHAR2(255 CHAR),
  qmjylx03    VARCHAR2(255 CHAR),
  qmjylx10    VARCHAR2(255 CHAR),
  qmjylx89    VARCHAR2(255 CHAR),
  qmjylxldx01 VARCHAR2(255 CHAR),
  qmjylxldx02 VARCHAR2(255 CHAR),
  qmjylxldx03 VARCHAR2(255 CHAR),
  qmjylxldx04 VARCHAR2(255 CHAR),
  qmjylxldx05 VARCHAR2(255 CHAR),
  qmjylxldx06 VARCHAR2(255 CHAR),
  qmpjjyq     VARCHAR2(255 CHAR),
  status      VARCHAR2(255 CHAR)
)
tablespace OA_DATA
  pctfree 10
  pctused 40
  initrans 1
  maxtrans 255
  storage
  (
    initial 64
    next 8
    minextents 1
    maxextents unlimited
  );
-- Create/Recreate primary, unique and foreign key constraints 
alter table QMVIEW
  add constraint QMVIEWPRIMARY primary key (UUID)
  using index 
  tablespace OA_DATA
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
