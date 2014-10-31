-- Add/modify columns 
alter table XZSP_BANJIAN add newest_status VARCHAR2(255 CHAR);

-- Add/modify columns 
alter table XZSP_BANJIAN add received_date TIMESTAMP(6);

-- Add/modify columns 
alter table XZSP_BANJIAN add promise_transact_date TIMESTAMP(6);

-- Add/modify columns 
alter table XZSP_BANJIAN add operate_date TIMESTAMP(6);

-- Add/modify columns 
alter table XZSP_BANJIAN add matters_type VARCHAR2(255 CHAR);

-- Add/modify columns 
alter table XZSP_BANJIAN add dept_approval_views VARCHAR2(255 CHAR);

-- Add/modify columns 
alter table XZSP_BANJIAN add actual_transact_status VARCHAR2(255 CHAR);

-- Add/modify columns 
alter table XZSP_BANJIAN add actual_transact_quality VARCHAR2(255 CHAR);

-- Add/modify columns 
alter table XZSP_BANJIAN add actual_transact_date TIMESTAMP(6);