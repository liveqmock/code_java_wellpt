CREATE TABLE ORG_GROUP_NESTED_GROUP
   (  "UUID" VARCHAR2(255 CHAR) NOT NULL ENABLE, 
        "CREATE_TIME" TIMESTAMP (6), 
  "CREATOR" VARCHAR2(255 CHAR), 
  "MODIFIER" VARCHAR2(255 CHAR), 
  "MODIFY_TIME" TIMESTAMP (6), 
  "REC_VER" NUMBER(10,0), 
  "GROUP_UUID" VARCHAR2(255 CHAR) NOT NULL ENABLE, 
  "NESTED_GROUP_UUID" VARCHAR2(255 CHAR) NOT NULL ENABLE, 
   PRIMARY KEY ("GROUP_UUID", "NESTED_GROUP_UUID")
);


alter table org_group add CATEGORY VARCHAR2(255 CHAR);