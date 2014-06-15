/*数据交换*/
DELETE FROM Is_Exchange_Data_Monitor;
DELETE FROM Is_Exchange_Data ;
DELETE FROM Is_Exchange_Data_Batch; 
DELETE FROM Is_Exchange_Data_Log ;
DELETE FROM Is_Exchange_Data_Route;
DELETE FROM Is_Exchange_Data_Reply ;
DELETE FROM Is_Exchange_Data_Callback;


/*进销存*/
DELETE FROM PSI_PLACEFILE;
DELETE FROM PSI_STOCK;
DELETE FROM PSI_STOCKFIELD;
DELETE FROM PSI_VOLTAGE;	 

/*动态表单*/
DELETE FROM Userform_License_Definition;
DELETE FROM Userform_Material_Definition;
DELETE FROM Userform_Matters_Apply;
DELETE FROM Userform_Matters_Apply_Stuff;

DELETE FROM Userform_Matters_Definition;
DELETE FROM Userform_Matters_Def_Stuff;
DELETE FROM Userform_Spgc_Bj;
DELETE FROM Userform_Spgc_Bjgz;
DELETE FROM Userform_Spgc_Bjsl;
DELETE FROM Userform_Spgc_Cxjs;
DELETE FROM Userform_Spgc_Cxks;
DELETE FROM Userform_Spgc_Sj;
DELETE FROM Userform_Spgc_Sl;
DELETE FROM Userform_Spgc_Spgc;
DELETE FROM Userform_Ssgl_Notice;
DELETE FROM Userform_Ssgl_Sybz;
DELETE FROM Userform_Ssgl_Zlxz;
DELETE FROM Userform_Ssjc_Jgyc;
DELETE FROM Userform_Ssxx_Fzjg;
DELETE FROM Userform_Ssxx_Gdxx;
DELETE FROM Userform_Ssxx_Jycs;
DELETE FROM Userform_Ssxx_Nbba;
DELETE FROM Userform_Ssxx_Qdry;
DELETE FROM Userform_Ssxx_Qdzz;
DELETE FROM Userform_Ssxx_Qsxx;
DELETE FROM Userform_Ssxx_Xgxk;
DELETE FROM Userform_Ssxx_Xktz;
DELETE FROM Userform_Ssxx_Xzcf;
DELETE FROM Userform_Ssxx_Xzxk;
DELETE FROM Userform_Ssxx_Ychf;
DELETE FROM Userform_Ssxx_Ycxx;
DELETE FROM Userform_Ssxx_Ztdj;
DELETE FROM Userform_Ssxx_Ztdjshow;
DELETE FROM Userform_Ssxx_Zzjg;
DELETE FROM Userform_Stuffname_Definition;
DELETE FROM Userform_Unit_Definition;
DELETE FROM Userform_Zcfg_Ssgl;

/*WORK相关*/
DELETE FROM WORK_DAILY;	 
DELETE FROM WORK_DAILY_DETAIL;	 
DELETE FROM WORK_DYTABLE;	 
DELETE FROM WORK_PLAN;	 
DELETE FROM WORK_PLAN_AUDIT;	 
DELETE FROM WORK_PLAN_PERMISSION;	 
DELETE FROM WORK_REPORT;	 
DELETE FROM WORK_TASK;	 
DELETE FROM WORK_TASK_APPLY;	 
DELETE FROM WORK_TASK_ASSIGN;	 
DELETE FROM WORK_TASK_CANCEL;	 
DELETE FROM WORK_TASK_CHANGE;	 
DELETE FROM WORK_TASK_CYCLE;	 
DELETE FROM WORK_TASK_DELAY;	 
DELETE FROM WORK_TASK_RESOLVE;

/*邮件*/
DELETE FROM MAIL_BOX;	
DELETE FROM MAIL_CLEAN;
DELETE FROM MAIL_CONFIG;	
DELETE FROM MAIL_DISPLAYSET;	
DELETE FROM MAIL_FETCH_HISTORY;
DELETE FROM MAIL_FILES;	
DELETE FROM MAIL_FOLDERS;	
DELETE FROM MAIL_GBOX;	
DELETE FROM MAIL_GROUP_USER;	
DELETE FROM MAIL_JAMES_INBOX;	
DELETE FROM MAIL_LABEL_RELATION;	
DELETE FROM MAIL_OPERATION_HISTORY;	
DELETE FROM MAIL_OTHER_CONFIG;
DELETE FROM MAIL_OTHER_RECEIVE_HISTORY;	
DELETE FROM MAIL_POPNAME;	
DELETE FROM MAIL_RULE;	
DELETE FROM MAIL_SIGN;	
DELETE FROM MAIL_TEMP_FILES;	
DELETE FROM MAIL_USERS;	
DELETE FROM MAIL_USER_CONTACTOR;	

/*消息*/
DELETE FROM MSG_MESSAGE_CONTENT; 
DELETE FROM MSG_SHORT_MESSAGE;

/*日程*/
DELETE FROM SCHEDULE;
DELETE FROM SCHEDULE_GROUP;
DELETE FROM SCHEDULE_GROUP_USER;
DELETE FROM SCHEDULE_LEADER;
DELETE FROM SCHEDULE_MENBER;
DELETE FROM SCHEDULE_SECRETARY;

/*商事公示*/
DELETE FROM BM_ADVISORY_COMPLAINTS;
DELETE FROM BM_PUBLICITY_ATTACH;
DELETE FROM BM_REGISTER_APPLY;
DELETE FROM BM_SUBMIT_VERIFY;
DELETE FROM BM_SELF_PUBLICITY_APPLY;

/*公共基础——excel导入规则*/
DELETE FROM CD_EXCEL_COLUMN_DEFINITION;
DELETE FROM CD_EXCEL_IMPORT_RULE;

/*公共基础——打印记录*/
DELETE FROM CD_PRINT_RECORD;

DELETE FROM CD_READ_MARKER;
DELETE FROM CD_RTX;

/*文件管理*/
DELETE FROM FM_FILE_VERSION;
DELETE FROM FM_FOLDER_ROLE T WHERE T.FILE_ID IS NOT NULL;
DELETE FROM FM_FILE;

/*用户操作日志*/
DELETE FROM LOG_USER_OPERATION;
DELETE FROM ORG_CONTACT;
DELETE FROM ORG_USER_LOGIN_LOG;

/*公示平台*/
DELETE FROM WE_ADMIN_GROUP;
DELETE FROM WE_ADMIN;
DELETE FROM WE_LOG;
DELETE FROM WE_POST;
DELETE FROM WE_SUBSCRIBE;
DELETE FROM WE_CATALOG;
DELETE FROM WE_CONFIG;

/*工作流程*/
DELETE FROM WF_TASK_ACTIVITY;
DELETE FROM WF_TASK_COUNTER_SIGN; 
DELETE FROM WF_TASK_IDENTITY;
DELETE FROM WF_TASK_INSTANCE; 
DELETE FROM WF_TASK_OPERATION; 
DELETE FROM WF_TASK_SUB_FLOW_RELATION;
DELETE FROM WF_TASK_SUB_FLOW;
DELETE FROM WF_TASK_TIMER_LOG;
DELETE FROM WF_TASK_TIMER_USER;
DELETE FROM WF_TASK_TIMER;
DELETE FROM WF_TASK_TRANSFER;
DELETE FROM WF_FLOW_INSTANCE;

COMMIT;

insert into XZSP.TASK_JOB_DETAILS (UUID, CREATE_TIME, CREATOR, MODIFIER, MODIFY_TIME, REC_VER, CODE, END_TIME, EXPRESSION, ID, INTERVAL_TIME, JOB_CLASS_NAME, NAME, REMARK, REPEAT_COUNT, REPEAT_DAY_OF_MONTH, REPEAT_DAY_OF_SEASON, REPEAT_DAY_OF_WEEK, REPEAT_DAY_OF_YEAR, REPEAT_INTERVAL, START_TIME, TENANT_ID, TIME_POINT, TIMING_MODE, TYPE)
values ('1f2a4aa4-03f9-4929-bf9f-063eeb528f2b', '01-1月 -14 02.39.26.000000 下午', 'U0010000001', 'U0010000001', '02-1月 -14 12.00.08.984000 上午', 9, '005', '', '0/60 * * ? * *', 'EXCHANGEDATA_REPEAT', '', 'com.wellsoft.pt.integration.support.ExchangeDataRepeatTask', '数据交换重发', '', null, '', '第 1', '', '01', null, '', 'T001', '14:35:30', 6, 'timing');

/*系统表，注:删除要按照以下顺序执行*/
DELETE FROM  CD_SYSTEM_TABLE_ENTITY_ATTR t where t.system_table_uuid = (select a.uuid from  CD_SYSTEM_TABLE_ENTITY a where a.full_entity_name = 'com.wellsoft.exchange.entity.ExchangeDocument');
DELETE FROM  CD_SYSTEM_TABLE_ENTITY_ATTR t where t.system_table_uuid = (select a.uuid from  CD_SYSTEM_TABLE_ENTITY a where a.full_entity_name = 'com.wellsoft.exchange.entity.ExchangeSender');
DELETE FROM  CD_SYSTEM_TABLE_ENTITY_ATTR t where t.system_table_uuid = (select a.uuid from  CD_SYSTEM_TABLE_ENTITY a where a.full_entity_name = 'com.wellsoft.exchange.entity.ExchangeSendee');
DELETE FROM  CD_SYSTEM_TABLE_ENTITY_ATTR t where t.system_table_uuid = (select a.uuid from  CD_SYSTEM_TABLE_ENTITY a where a.full_entity_name = 'com.wellsoft.pt.integration.entity.AdvisoryComplaints');
DELETE FROM  CD_SYSTEM_TABLE_ENTITY_ATTR t where t.system_table_uuid = (select a.uuid from  CD_SYSTEM_TABLE_ENTITY a where a.full_entity_name = 'com.wellsoft.pt.integration.entity.SelfPublicityApply');
DELETE FROM  CD_SYSTEM_TABLE_ENTITY_ATTR t where t.system_table_uuid = (select a.uuid from  CD_SYSTEM_TABLE_ENTITY a where a.full_entity_name = 'com.wellsoft.pt.integration.entity.EnterpriseRegisteredApply');

DELETE FROM  CD_SYSTEM_TABLE_RELATIONSHIP t where t.system_table_uuid = (select a.uuid from  CD_SYSTEM_TABLE_ENTITY a where a.full_entity_name = 'com.wellsoft.exchange.entity.ExchangeDocument');
DELETE FROM  CD_SYSTEM_TABLE_RELATIONSHIP t where t.system_table_uuid = (select a.uuid from  CD_SYSTEM_TABLE_ENTITY a where a.full_entity_name = 'com.wellsoft.exchange.entity.ExchangeSender');
DELETE FROM  CD_SYSTEM_TABLE_RELATIONSHIP t where t.system_table_uuid = (select a.uuid from  CD_SYSTEM_TABLE_ENTITY a where a.full_entity_name = 'com.wellsoft.exchange.entity.ExchangeSendee');
DELETE FROM  CD_SYSTEM_TABLE_RELATIONSHIP t where t.system_table_uuid = (select a.uuid from  CD_SYSTEM_TABLE_ENTITY a where a.full_entity_name = 'com.wellsoft.pt.integration.entity.AdvisoryComplaints');
DELETE FROM  CD_SYSTEM_TABLE_RELATIONSHIP t where t.system_table_uuid = (select a.uuid from  CD_SYSTEM_TABLE_ENTITY a where a.full_entity_name = 'com.wellsoft.pt.integration.entity.SelfPublicityApply');
DELETE FROM  CD_SYSTEM_TABLE_RELATIONSHIP t where t.system_table_uuid = (select a.uuid from  CD_SYSTEM_TABLE_ENTITY a where a.full_entity_name = 'com.wellsoft.pt.integration.entity.EnterpriseRegisteredApply');

DELETE FROM CD_SYSTEM_TABLE_ENTITY t WHERE T.FULL_ENTITY_NAME = 'com.wellsoft.exchange.entity.ExchangeDocument';
DELETE FROM CD_SYSTEM_TABLE_ENTITY t WHERE T.FULL_ENTITY_NAME = 'com.wellsoft.exchange.entity.ExchangeSender';
DELETE FROM CD_SYSTEM_TABLE_ENTITY t WHERE T.FULL_ENTITY_NAME = 'com.wellsoft.exchange.entity.ExchangeSendee';
DELETE FROM CD_SYSTEM_TABLE_ENTITY t WHERE T.FULL_ENTITY_NAME = 'com.wellsoft.pt.integration.entity.AdvisoryComplaints';
DELETE FROM CD_SYSTEM_TABLE_ENTITY t WHERE T.FULL_ENTITY_NAME = 'com.wellsoft.pt.integration.entity.SelfPublicityApply';
DELETE FROM CD_SYSTEM_TABLE_ENTITY t WHERE T.FULL_ENTITY_NAME = 'com.wellsoft.pt.integration.entity.EnterpriseRegisteredApply';
COMMIT;

COMMIT;