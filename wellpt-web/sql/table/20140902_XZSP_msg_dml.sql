alter table msg_short_message drop column BUSINESS_LOGIC_ID;

alter table msg_short_message add reserved_text1 varchar2(255 char);

alter table msg_short_message add reserved_text2 varchar2(255 char);

alter table msg_short_message add reserved_text3 varchar2(255 char);

