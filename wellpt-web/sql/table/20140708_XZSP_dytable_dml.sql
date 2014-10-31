-- userform_matters_definition
/*办理内容*/
ALTER TABLE userform_matters_definition modify BANLI_CONTENT  VARCHAR2(2000 CHAR);
/*办理流程*/
ALTER TABLE userform_matters_definition modify BANLI_PROCESS  VARCHAR2(2000 CHAR);
/*收费依据*/
ALTER TABLE userform_matters_definition modify CHARGES_BASIS  VARCHAR2(2000 CHAR);
/*收费标准*/
ALTER TABLE userform_matters_definition modify CHARGES_STANDARDS  VARCHAR2(2000 CHAR);
/*收费主体*/
ALTER TABLE userform_matters_definition modify CHARGES_SUBJECT  VARCHAR2(2000 CHAR);
/*收费公式*/
ALTER TABLE userform_matters_definition modify CHARGES_FORMULA  VARCHAR2(2000 CHAR);
/*包串联事项*/
ALTER TABLE userform_matters_definition modify LIANBAN_MATTERS  VARCHAR2(2000 CHAR);
commit;