CREATE TABLE "AUDIT_PRIVILEGE_OTHER_RESOURCE" 
(  "PRIVILEGE_UUID" VARCHAR2(255 CHAR) NOT NULL, 
  "RESOURCE_UUID" VARCHAR2(255 CHAR) NOT NULL, 
  "TYPE" VARCHAR2(255 CHAR), 
   PRIMARY KEY ("PRIVILEGE_UUID", "RESOURCE_UUID")
   );