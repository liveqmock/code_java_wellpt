create index msg_mc_recipient_idx on msg_message_content (recipient,isread);

select count(*) as col_0_0_ from msg_message_content messagecon0_ where messagecon0_.isread=0 
and messagecon0_.recipient='U0010000047' ;
