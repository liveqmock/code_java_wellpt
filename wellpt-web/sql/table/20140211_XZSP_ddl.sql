/*异常结果处理表结构变更*/
alter table USERFORM_SSJC_JGYC drop column BODY_CONTENT;
alter table USERFORM_SSJC_JGYC  add BODY_CONTENT  clob;