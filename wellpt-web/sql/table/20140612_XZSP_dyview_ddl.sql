--视图新增字段
alter table dyview_view_definition add BUTTON_PLACE NUMBER(1);
alter table dyview_view_definition add SPECIAL_FIELD NUMBER(1);
alter table dyview_view_definition add SPECIAL_FIELD_METHOD VARCHAR2(255 CHAR);
alter table dyview_view_definition add REQUEST_PARAM_ID VARCHAR2(255 CHAR);
alter table dyview_view_definition add REQUEST_PARAM_NAME VARCHAR2(255 CHAR);
alter table dyview_view_definition add RESPONSE_PARAM_ID VARCHAR2(255 CHAR);
alter table dyview_view_definition add RESPONSE_PARAM_NAME VARCHAR2(255 CHAR);

