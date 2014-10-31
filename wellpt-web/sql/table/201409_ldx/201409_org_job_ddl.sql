CREATE TABLE ORG_JOB
   (  "UUID" VARCHAR2(255 CHAR) NOT NULL ENABLE, 
        "CREATE_TIME" TIMESTAMP (6), 
  "CREATOR" VARCHAR2(255 CHAR), 
  "MODIFIER" VARCHAR2(255 CHAR), 
  "MODIFY_TIME" TIMESTAMP (6), 
  "REC_VER" NUMBER(10,0), 
        "ID" VARCHAR2(255 CHAR) NOT NULL ENABLE, 
        "CODE" VARCHAR2(255 CHAR), 
        "NAME" VARCHAR2(255 CHAR),
 "FUNCTION_ID" VARCHAR2(255 CHAR),
"FUNCTION_NAME" VARCHAR2(255 CHAR),
"EXTERNAL_ID"  VARCHAR2(255 CHAR),
  "DEPARTMENT_NAME" VARCHAR2(255 CHAR),
  "DEPARTMENT_UUID" VARCHAR2(255 CHAR),
  "REMARK" VARCHAR2(255 CHAR), 
"DUTY_UUID" VARCHAR2(255 CHAR),
"DUTY_NAME" VARCHAR2(255 CHAR),
  "LEADER_NAMES" VARCHAR2(255 CHAR),
  "FUNCTION_NAMES" VARCHAR2(255 CHAR),
  PRIMARY KEY ("UUID")
  
);

--岗位角色关联表
CREATE TABLE ORG_JOB_ROLE
   (  "JOB_UUID" VARCHAR2(255 CHAR) NOT NULL ENABLE, 
  "ROLE_UUID" VARCHAR2(255 CHAR) NOT NULL ENABLE, 
   PRIMARY KEY ("JOB_UUID", "ROLE_UUID")
);

--岗位权限关联表
CREATE TABLE ORG_JOB_PRIVILEGE
   (  "JOB_UUID" VARCHAR2(255 CHAR) NOT NULL ENABLE, 
  "PRIVILEGE_UUID" VARCHAR2(255 CHAR) NOT NULL ENABLE, 
   PRIMARY KEY ("JOB_UUID", "PRIVILEGE_UUID")
);

--岗位职能关联表
CREATE TABLE ORG_JOB_FUNCTION
   (  "UUID" VARCHAR2(255 CHAR) NOT NULL ENABLE, 
        "CREATE_TIME" TIMESTAMP (6), 
  "CREATOR" VARCHAR2(255 CHAR), 
  "MODIFIER" VARCHAR2(255 CHAR), 
  "MODIFY_TIME" TIMESTAMP (6), 
  "REC_VER" NUMBER(10,0), 
  "JOB_UUID" VARCHAR2(255 CHAR) NOT NULL ENABLE, 
"FUNCTION_UUID" VARCHAR2(255 CHAR) NOT NULL ENABLE, 
   PRIMARY KEY ("JOB_UUID", "FUNCTION_UUID")
);

--岗位上级领导关联表
CREATE TABLE ORG_JOB_LEADER
   (  "UUID" VARCHAR2(255 CHAR) NOT NULL ENABLE, 
        "CREATE_TIME" TIMESTAMP (6), 
  "CREATOR" VARCHAR2(255 CHAR), 
  "MODIFIER" VARCHAR2(255 CHAR), 
  "MODIFY_TIME" TIMESTAMP (6), 
  "REC_VER" NUMBER(10,0), 
  "JOB_UUID" VARCHAR2(255 CHAR) NOT NULL ENABLE, 
  "LEADER_UUID" VARCHAR2(255 CHAR) NOT NULL ENABLE, 
   PRIMARY KEY ("JOB_UUID", "LEADER_UUID")
);

--用户职位表
CREATE TABLE ORG_USER_JOB
   (  "UUID" VARCHAR2(255 CHAR) NOT NULL ENABLE, 
        "CREATE_TIME" TIMESTAMP (6), 
  "CREATOR" VARCHAR2(255 CHAR), 
  "MODIFIER" VARCHAR2(255 CHAR), 
  "MODIFY_TIME" TIMESTAMP (6), 
  "REC_VER" NUMBER(10,0), 
"USER_UUID" VARCHAR2(255 CHAR) NOT NULL ENABLE, 
  "JOB_UUID" VARCHAR2(255 CHAR) NOT NULL ENABLE, 

  "JOB_NAME" VARCHAR2(255 CHAR) , 
  "USER_LOGIN_NAME" VARCHAR2(255 CHAR) , 


 "IS_MAJOR" NUMBER(1,0) NOT NULL ENABLE, 
PRIMARY KEY ("UUID")
);


--岗位群组关联表
CREATE TABLE ORG_GROUP_JOB 
   (  "GROUP_UUID" VARCHAR2(255 CHAR) NOT NULL ENABLE, 
  "JOB_UUID" VARCHAR2(255 CHAR) NOT NULL ENABLE, 
   PRIMARY KEY ("GROUP_UUID", "JOB_UUID")
);
