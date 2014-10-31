--更新主体登记
--定义的修改
update dytable_field_definition t
   set t.type = 8
 where t.fieldname = 'fabu_time'
   and t.form_uuid = (select d.uuid
                        from dytable_form_definition d
                       where d.descname like '%政策法规%');
update dytable_field_definition t
   set t.type = 8
 where t.fieldname = 'faq_time'
   and t.form_uuid = (select d.uuid
                        from dytable_form_definition d
                       where d.descname like '%行政审批_FAQ%');
update dytable_field_definition t
   set t.type = 8
 where t.fieldname = 'release_time'
   and t.form_uuid = (select d.uuid
                        from dytable_form_definition d
                       where d.descname='通知公告（商事管理）');
                       
update dytable_field_definition t
   set t.type = 8
 where t.fieldname = 'faq_question_time'
   and t.form_uuid = (select d.uuid
                        from dytable_form_definition d
                       where d.descname='互动问答（商事管理）');
                       
update dytable_field_definition t
   set t.type = 8
 where t.fieldname = 'faq_answer_time'
   and t.form_uuid = (select d.uuid
                        from dytable_form_definition d
                       where d.descname='互动问答（商事管理）');
-- Add/modify columns 
alter table USERFORM_SSXX_ZTDJ add HZRQ_1 TIMESTAMP(6);
alter table USERFORM_SSXX_ZTDJ add CLRQ_1 TIMESTAMP(6);
alter table USERFORM_SSXX_ZTDJ add YYQX_1 TIMESTAMP(6);

update userform_ssxx_ztdj z
   set z.clrq_1 = to_date(z.clrq, 'yyyy-MM-dd'),
       z.yyqx_1 = to_date(z.yyqx, 'yyyy-MM-dd'),
       z.hzrq_1 = to_date(z.hzrq, 'yyyy-MM-dd');
commit;
update userform_ssxx_ztdj z
   set z.clrq = null,
       z.yyqx = null,
       z.hzrq = null;
commit;
-- Add/modify columns 
alter table USERFORM_SSXX_ZTDJ modify YYQX TIMESTAMP(6);
alter table USERFORM_SSXX_ZTDJ modify HZRQ TIMESTAMP(6);
alter table USERFORM_SSXX_ZTDJ modify CLRQ TIMESTAMP(6);

update userform_ssxx_ztdj z
   set z.clrq = z.clrq_1,
       z.yyqx = z.yyqx_1,
       z.hzrq = z.hzrq_1;
commit;
-- Drop columns 
alter table USERFORM_SSXX_ZTDJ drop column HZRQ_1;
alter table USERFORM_SSXX_ZTDJ drop column CLRQ_1;
alter table USERFORM_SSXX_ZTDJ drop column YYQX_1;

--行政许可
-- Add/modify columns 
update userform_ssxx_xzxk t
   set t.bzrq   = replace(t.bzrq, ' 00:00:00', ''),
       t.xqgxrq = replace(t.xqgxrq, ' 00:00:00', ''),
       t.gxrq   = replace(t.gxrq, ' 00:00:00', '');
commit;
update userform_ssxx_xzxk t
   set t.bzrq   = replace(t.bzrq, '.0', ''),
       t.xqgxrq = replace(t.xqgxrq, '.0', ''),
       t.gxrq   = replace(t.gxrq, '.0', '');
commit;
alter table USERFORM_SSXX_XZXK add GXRQ_1 TIMESTAMP(6);
alter table USERFORM_SSXX_XZXK add BZRQ_1 TIMESTAMP(6);
alter table USERFORM_SSXX_XZXK add XQGXRQ_1 TIMESTAMP(6);

update USERFORM_SSXX_XZXK z
   set z.GXRQ_1 = to_date(z.GXRQ, 'yyyy-MM-dd'),
       z.BZRQ_1 = to_date(z.BZRQ, 'yyyy-MM-dd'),
       z.XQGXRQ_1 = to_date(z.XQGXRQ, 'yyyy-MM-dd');
commit;

update USERFORM_SSXX_XZXK z
   set z.GXRQ = null,
       z.BZRQ = null,
       z.XQGXRQ = null;
commit;
-- Add/modify columns 
alter table USERFORM_SSXX_XZXK modify GXRQ TIMESTAMP(6);
alter table USERFORM_SSXX_XZXK modify BZRQ TIMESTAMP(6);
alter table USERFORM_SSXX_XZXK modify XQGXRQ TIMESTAMP(6);

update USERFORM_SSXX_XZXK z
   set z.GXRQ = z.GXRQ_1,
       z.BZRQ = z.BZRQ_1,
       z.XQGXRQ = z.XQGXRQ_1;

-- Drop columns 
alter table USERFORM_SSXX_XZXK drop column GXRQ_1;
alter table USERFORM_SSXX_XZXK drop column BZRQ_1;
alter table USERFORM_SSXX_XZXK drop column XQGXRQ_1;

--清算信息
-- Add/modify columns 
alter table userform_ssxx_qsxx add QSZBARQ_1 TIMESTAMP(6);

update userform_ssxx_qsxx z
   set z.QSZBARQ_1 = to_date(z.QSZBARQ, 'yyyy-MM-dd');    
commit;

update userform_ssxx_qsxx z
   set z.QSZBARQ = null;
commit;
-- Add/modify columns 
alter table userform_ssxx_qsxx modify QSZBARQ TIMESTAMP(6);


update userform_ssxx_qsxx z
   set z.QSZBARQ = z.QSZBARQ_1;

-- Drop columns 
alter table userform_ssxx_qsxx drop column QSZBARQ_1;

-- 审批过程
alter table userform_ssxx_xzcf modify CFRQ TIMESTAMP(6);

alter table userform_ssxx_nbba modify RJRQ TIMESTAMP(6);

alter table userform_ssxx_qdzz modify QDRQ TIMESTAMP(6);

alter table userform_ssxx_qdzz modify YXRQ TIMESTAMP(6);

alter table userform_ssxx_qdry modify SYRQ TIMESTAMP(6);

alter table userform_ssxx_qdry modify YXRQ TIMESTAMP(6);

delete userform_ssjc_jgyc;
commit;
alter table userform_ssjc_jgyc modify replytime TIMESTAMP(6);

delete userform_spgc_sj;
commit;
alter table userform_spgc_sj modify WSTJSJ TIMESTAMP(6);

delete userform_spgc_sl;
commit;
alter table userform_spgc_sl modify slsj TIMESTAMP(6);

delete userform_spgc_spgc;
commit;
alter table userform_spgc_spgc modify SPSJ TIMESTAMP(6);

delete userform_spgc_bj;
commit;
alter table userform_spgc_bj modify  bjsj TIMESTAMP(6);

alter table userform_spgc_bjgz modify  BJGZSJ TIMESTAMP(6);

alter table userform_spgc_bjsl modify  BJSJ TIMESTAMP(6);

alter table userform_spgc_cxks modify  TBCXKSRQ TIMESTAMP(6);

alter table userform_spgc_cxjs modify  JGCSRQ TIMESTAMP(6);

alter table userform_spgc_cxjs modify  TBCXJSRQ TIMESTAMP(6);

--互动问答（商事管理）
-- Add/modify columns 
alter table userform_interactive_faq add faq_question_time_1 TIMESTAMP(6);
alter table userform_interactive_faq add faq_answer_time_1 TIMESTAMP(6);

update userform_interactive_faq z
   set z.faq_question_time_1 = to_date(z.faq_question_time, 'yyyy-MM-dd HH24:mi:ss'),
       z.faq_answer_time_1 = to_date(z.faq_answer_time, 'yyyy-MM-dd HH24:mi:ss');
commit;
update userform_interactive_faq z
   set z.faq_question_time = null,
       z.faq_answer_time = null;
commit;
-- Add/modify columns 
alter table userform_interactive_faq modify faq_question_time TIMESTAMP(6);
alter table userform_interactive_faq modify faq_answer_time TIMESTAMP(6);

update  userform_interactive_faq z
   set z.faq_question_time_1 = z.modify_time
   where z.faq_question_time_1 is null;
   
update  userform_interactive_faq z
  set z.faq_answer_time_1 = z.modify_time
   where z.faq_answer_time is null;   

update userform_interactive_faq z
   set z.faq_question_time = z.faq_question_time_1,z.faq_answer_time = z.faq_answer_time_1;

-- Drop columns 
alter table userform_interactive_faq drop column faq_question_time_1;
alter table userform_interactive_faq drop column faq_answer_time_1;

--政策法规（商事管理）
-- Add/modify columns 
alter table userform_zcfg_ssgl add fabu_time_1 TIMESTAMP(6);

update userform_zcfg_ssgl z
   set z.fabu_time_1 = to_date(z.fabu_time, 'yyyy-MM-dd HH24:mi:ss');
commit;
update userform_zcfg_ssgl z
   set z.fabu_time = null;
commit;
-- Add/modify columns 
alter table userform_zcfg_ssgl modify fabu_time TIMESTAMP(6);

update userform_zcfg_ssgl z
   set z.fabu_time = z.fabu_time_1;
commit;
-- Drop columns 
alter table userform_zcfg_ssgl drop column fabu_time_1;

--常见问题
-- Add/modify columns 
alter table userform_faq add faq_time_1 TIMESTAMP(6);

update userform_faq z
   set z.faq_time_1 = to_date(z.faq_time, 'yyyy-MM-dd HH24:mi:ss');
commit;
update userform_faq z
   set z.faq_time = null;
commit;
-- Add/modify columns 
alter table userform_faq modify faq_time TIMESTAMP(6);

update userform_faq z
   set z.faq_time = z.faq_time_1;
commit;
-- Drop columns 
alter table userform_faq drop column faq_time_1;

--通知公告（商事管理）
-- Add/modify columns 
alter table userform_ssgl_notice add release_time_1 TIMESTAMP(6);

update userform_ssgl_notice z
   set z.release_time_1 = to_date(z.release_time, 'yyyy-MM-dd HH24:mi:ss');
commit;
update userform_ssgl_notice z
   set z.release_time = null;
commit;
-- Add/modify columns 
alter table userform_ssgl_notice modify release_time TIMESTAMP(6);

update userform_ssgl_notice z
   set z.release_time = z.release_time_1;
commit;
-- Drop columns 
alter table userform_ssgl_notice drop column release_time_1;