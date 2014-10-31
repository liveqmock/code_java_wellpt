--商事数据升级--------------------------------
------------------从表 商事登记_行政许可拓展 (1.0)--------------
insert into uf_ssxx_xktz
(UUID, CREATOR, CREATE_TIME, MODIFIER, MODIFY_TIME, FORM_uuID, BT, Z) 
select 
UUID, CREATOR, CREATE_TIME, MODIFIER, MODIFY_TIME, FORM_ID, BT, Z 
from userform_ssxx_xktz;
commit;

--数据关系 

 insert into uf_ssxx_xktz_rl (uuid, creator, create_time, modifier, modify_time,   data_uuid, sort_order, mainform_data_uuid, mainform_form_uuid)

select uuid, creator, create_time, modifier, modify_time   , uuid, sort_order, parent_id , '5de009ea-bbc6-4ba7-8f99-26514a8099cb'

from userform_ssxx_xktz

commit;

------------------主表 商事登记_行政许可信息 (1.0)--------------
insert into uf_ssxx_xzxk
(UUID, CREATOR, CREATE_TIME, MODIFIER, MODIFY_TIME, FORM_UUID, ZCH, BZDWDM, BZDWMC, ZJH, ZZMC, GXRQ, BZRQ, XQGXRQ, JGMC, DJZT, XGWJ, XKFW, ZTMC, FDDBR, ZTLX, JYCS, SIGNATURE_)

select 
UUID, CREATOR, CREATE_TIME, MODIFIER, MODIFY_TIME, FORM_ID, ZCH, BZDWDM, BZDWMC, ZJH, ZZMC, GXRQ, BZRQ, XQGXRQ, JGMC, DJZT, XGWJ,  XKFW, ZTMC, FDDBR, ZTLX, JYCS, SIGNATURE_
from userform_ssxx_xzxk;
commit;

--转成json数据
update uf_ssxx_xzxk set zzmc = '{"'|| ZZMC || '":"' ||  ZZMC || '"}';
commit;



------------------ 商事登记_主体基本信息------------------
insert into uf_ssxx_ztdj( UUID, CREATOR, CREATE_TIME, MODIFIER, MODIFY_TIME,  FORM_uuID, ZCH, ZTMC, LSZTMC, FDDBR, JYHW, XKJYHW, ZS, LXDH, LLRXX, ZTLX, YYQX, HZRQ, DJJG, NBQK, ZTZT, ZC, QTWJ, FDDBRZWDM, FDDBRZWMC, FDDBRZJHM, RJCZE, RJCZEDW, SJCZE, SJCZEDW, ZTLXDM, DJJGDM, ZTZTDM, CLRQ)

select  UUID, CREATOR, CREATE_TIME, MODIFIER, MODIFY_TIME,  FORM_ID, ZCH, ZTMC, LSZTMC, FDDBR, JYHW, XKJYHW, ZS, LXDH, LLRXX, ZTLX, YYQX, HZRQ, DJJG, NBQK, ZTZT, ZC, QTWJ, FDDBRZWDM, FDDBRZWMC, FDDBRZJHM, RJCZE, RJCZEDW, SJCZE, SJCZEDW, ZTLXDM, DJJGDM, ZTZTDM, CLRQ
from USERFORM_SSXX_ZTDJ;
commit;
------------------从表 商事登记_主体的经营场所 (1.0) ------------------
insert into uf_ssxx_jycs

(UUID, CREATOR, CREATE_TIME, MODIFIER, MODIFY_TIME,    FORM_uuID, JYCS)

select UUID, CREATOR, CREATE_TIME, MODIFIER, MODIFY_TIME,    FORM_ID, JYCS from  USERFORM_SSXX_JYCS ;
commit;
--数据关系
insert into uf_ssxx_jycs_rl (uuid, creator, create_time, modifier, modify_time,   data_uuid, sort_order, mainform_data_uuid, mainform_form_uuid)

select uuid, creator, create_time, modifier, modify_time   , uuid, sort_order, parent_id , '0e46407a-d196-4997-afb0-e270cf62720e'

from USERFORM_SSXX_JYCS;
commit;
------------------从表 商事登记_主体的股东信息 (1.0) ------------------
insert into uf_ssxx_gdxx  (UUID, CREATOR, CREATE_TIME, MODIFIER, MODIFY_TIME,    FORM_uuID,   GDMC, GDLX,  GDLXDM, GB, GBDM, RJCZE, RJCZEDW, SJCZE, SJCZEDW, CZBL )

select  UUID, CREATOR, CREATE_TIME, MODIFIER, MODIFY_TIME,    FORM_ID, GDMC, GDLX,  GDLXDM, GB, GBDM, RJCZE, RJCZEDW, SJCZE, SJCZEDW, CZBL 
from userform_ssxx_gdxx;
commit;
--数据关系
insert into uf_ssxx_gdxx_rl (uuid, creator, create_time, modifier, modify_time,   data_uuid, sort_order, mainform_data_uuid, mainform_form_uuid)

select uuid, creator, create_time, modifier, modify_time   , uuid, sort_order, parent_id , '0e46407a-d196-4997-afb0-e270cf62720e'

from userform_ssxx_gdxx;
commit;
------------------从表 商事登记_主体组织机构信息 (1.0) ------------------

insert into uf_ssxx_zzjg (UUID, CREATOR, CREATE_TIME, MODIFIER, MODIFY_TIME, FORM_uuID, XM, ZW, ZWDM)

select  
UUID, CREATOR, CREATE_TIME, MODIFIER, MODIFY_TIME, FORM_ID, XM, ZW, ZWDM

from userform_ssxx_zzjg;
commit;
insert into uf_ssxx_zzjg_rl (uuid, creator, create_time, modifier, modify_time,   data_uuid, sort_order, mainform_data_uuid, mainform_form_uuid)

select uuid, creator, create_time, modifier, modify_time   , uuid, sort_order, parent_id , '0e46407a-d196-4997-afb0-e270cf62720e'

from userform_ssxx_zzjg;

------------------从表 商事登记_主体分支机构信息 (1.0) ------------------

insert into uf_ssxx_fzjg  (UUID, CREATOR, CREATE_TIME, MODIFIER, MODIFY_TIME, FORM_uuID,  MC, JYCS, FZR, BZ)

select  
 UUID, CREATOR, CREATE_TIME, MODIFIER, MODIFY_TIME, FORM_ID,  MC, JYCS, FZR,  BZ


from userform_ssxx_fzjg;
commit;
--数据关系

insert into uf_ssxx_fzjg_rl (uuid, creator, create_time, modifier, modify_time,   data_uuid, sort_order, mainform_data_uuid, mainform_form_uuid)

select uuid, creator, create_time, modifier, modify_time   , uuid, sort_order, parent_id , '0e46407a-d196-4997-afb0-e270cf62720e'

from userform_ssxx_fzjg;
commit;
------------------从表 商事登记_相关许可单位 (1.0) ------------------

insert into uf_ssxx_xgxk  
(UUID, CREATOR, CREATE_TIME, MODIFIER, MODIFY_TIME, FORM_uuID, XKJYXMMC, XKJYXMDM, DWMC, DWDM)


select  
UUID, CREATOR, CREATE_TIME, MODIFIER, MODIFY_TIME, FORM_ID, XKJYXMMC, XKJYXMDM, DWMC, DWDM
from userform_ssxx_xgxk;
commit;

insert into uf_ssxx_xgxk_rl (uuid, creator, create_time, modifier, modify_time,   data_uuid, sort_order, mainform_data_uuid, mainform_form_uuid)

select uuid, creator, create_time, modifier, modify_time   , uuid, sort_order, parent_id , '0e46407a-d196-4997-afb0-e270cf62720e'

from userform_ssxx_xgxk;
commit;

------------------从表 商事登记_主体清算信息 (1.0) ------------------
insert into uf_ssxx_qsxx  
(UUID, CREATOR, CREATE_TIME, MODIFIER, MODIFY_TIME, FORM_uuID, QSZFZR, QSZRY, QSZDZ, QSZDH, QSZBARQ)
select  
UUID, CREATOR, CREATE_TIME, MODIFIER, MODIFY_TIME, FORM_ID, QSZFZR, QSZRY, QSZDZ, QSZDH, QSZBARQ
from userform_ssxx_qsxx;
commit;
--数据关系
insert into uf_ssxx_qsxx_rl (uuid, creator, create_time, modifier, modify_time,   data_uuid, sort_order, mainform_data_uuid, mainform_form_uuid)

select uuid, creator, create_time, modifier, modify_time   , uuid, sort_order, parent_id , '0e46407a-d196-4997-afb0-e270cf62720e'
from userform_ssxx_qsxx;
commit;
 
  
------------------uf_ssgl_zlxz ------------------
insert into uf_ssgl_zlxz 
(
 UUID          ,
  CREATOR       ,
  CREATE_TIME    ,
  MODIFIER      ,
  MODIFY_TIME  ,
  FORM_uuID        ,
  TEXT_TITLE   ,
  FILEUPLOAD   ,
  NOTICE_CONTENT 
)


select 
UUID          ,
  CREATOR       ,
  CREATE_TIME    ,
  MODIFIER      ,
  MODIFY_TIME  ,
  FORM_ID        ,
  TEXT_TITLE   ,
  FILEUPLOAD   ,
  NOTICE_CONTENT 

from 
 

USERFORM_SSGL_ZLXZ;
commit;
------------------ uf_ssgl_zcfg------------------

insert into uf_ssgl_zcfg(
UUID      ,      
  CREATOR     ,   
  CREATE_TIME   ,
  MODIFIER      , 
  MODIFY_TIME    ,
 
  FORM_uuID      ,  
 
  TEXT_TITLE    , 
  FABU_PERSON     ,
  FABU_TIME   ,   
  FILEUPLOAD    ,
  NOTICE_CONTENT  )
select
UUID      ,      
  CREATOR     ,   
  CREATE_TIME   ,
  MODIFIER      , 
  MODIFY_TIME    ,
 
  FORM_ID      ,  
 
  TEXT_TITLE    , 
  FABU_PERSON     ,
  FABU_TIME   ,   
  FILEUPLOAD    ,
  NOTICE_CONTENT 
 
 from  USERFORM_ZCFG_SSGL;
commit;
------------------uf_ssgl_hdwd------------------
insert into uf_ssgl_hdwd(
  UUID ,               
  CREATOR  ,         
  CREATE_TIME   ,      
  MODIFIER     ,     
  MODIFY_TIME  ,       
  FORM_uuID       ,      
  FAQ_TITLE      ,    
  FAQ_QUESTION_PERSON ,
  FAQ_QUESTION_TIME  ,
  FAQ_ANSWER_PERSON  , 
  FAQ_ANSWER_TIME  ,  
  FAQ_QUESTION,       
  FAQ_ANSWER   
)

select  UUID ,               
  CREATOR  ,         
  CREATE_TIME   ,      
  MODIFIER     ,     
  MODIFY_TIME  ,       
  FORM_ID       ,      
  FAQ_TITLE      ,    
  FAQ_QUESTION_PERSON ,
  FAQ_QUESTION_TIME  ,
  FAQ_ANSWER_PERSON  , 
  FAQ_ANSWER_TIME  ,  
  FAQ_QUESTION,       
  FAQ_ANSWER  
  
  from userform_interactive_faq;
commit;
------------------uf_ssgl_faq ------------------
  insert into

uf_ssgl_faq(UUID, CREATOR, CREATE_TIME, MODIFIER, MODIFY_TIME, FORM_uuID, FAQ_TITLE, FAQ_TYPE, FAQ_RESPONDENT, FAQ_TIME, SIGNATURE_, FAQ_QUESTION, FAQ_ANSWER
)


select   UUID, CREATOR, CREATE_TIME, MODIFIER, MODIFY_TIME, FORM_ID, FAQ_TITLE, FAQ_TYPE, FAQ_RESPONDENT, FAQ_TIME, SIGNATURE_, FAQ_QUESTION, FAQ_ANSWER

from
USERFORM_FAQ;
commit;

update uf_ssgl_faq set FAQ_TYPe_name = FAQ_TYPE ;
commit;
--将证件名称字段的值设置为map结构
update uf_ssgl_faq set FAQ_TYPe_name = '{"'|| FAQ_TYPe_name || '":"' ||  FAQ_TYPe_name || '"}';
commit;


------------------userform_spgc_sj ------------------
insert into uf_spgc_sj
  (UUID, CREATOR, CREATE_TIME, MODIFIER, MODIFY_TIME, FORM_uuID, YWLSH, SJBBH, SPSXBH, SPSXZXBH, YXTYWLSH, SQRHSQDW, SQRXM, SQRSJ, SQRDH, SQRYX, WSTJSJ, WSTXCLQD, HZBH, BZ, BYZDA, BYZDB, BYZDC, BYZDd)
  
  
  select 
  UUID, CREATOR, CREATE_TIME, MODIFIER, MODIFY_TIME, FORM_ID, YWLSH, SJBBH, SPSXBH, SPSXZXBH, YXTYWLSH, SQRHSQDW, SQRXM, SQRSJ, SQRDH, SQRYX, WSTJSJ, WSTXCLQD, HZBH, BZ, BYZDA, BYZDB, BYZDC, BYZDD
  from USERFORM_SPGC_SJ;
  commit;
------------------userform_spgc_sl ------------------
insert into Uf_SPGC_SL
(UUID, CREATOR, CREATE_TIME, MODIFIER, MODIFY_TIME, FORM_uuID, YWLSH, SJBBH, SPSXBH, SPSXZXBH, YXTYWLSH, SPSXMC, GDBLSX, GDBLSXDW, GDSF, XMMC, FLGDGXD, FSYWGXD, SQDWHSQRXM, SQDWJBRXM, SQDWLXDH, SQDWJBRSJ, SQDWJBRYJ, SLJGZZJGDM, SLJGMC, SLDWCBRXM, SLDWCBRGH, SLJTDD, SLSJ, HZBH, SLZLQD, SLYF, BSLYY, TJFS, BZ, BYZDA, BYZDB, BYZDC, BYZDD)

  
  select 
 UUID, CREATOR, CREATE_TIME, MODIFIER, MODIFY_TIME, FORM_ID, YWLSH, SJBBH, SPSXBH, SPSXZXBH, YXTYWLSH, SPSXMC, GDBLSX, GDBLSXDW, GDSF, XMMC, FLGDGXD, FSYWGXD, SQDWHSQRXM, SQDWJBRXM, SQDWLXDH, SQDWJBRSJ, SQDWJBRYJ, SLJGZZJGDM, SLJGMC, SLDWCBRXM, SLDWCBRGH, SLJTDD, SLSJ, HZBH, SLZLQD, SLYF, BSLYY, TJFS, BZ, BYZDA, BYZDB, BYZDC, BYZDD
  from USERFORM_SPGC_SL;
  commit;
------------------userform_spgc_spgc ------------------
insert into UF_SPGC_SPGC
(UUID, CREATOR, CREATE_TIME, MODIFIER, MODIFY_TIME, FORM_uuID, YWLSH, SJBBH, SPBZH, SPSXBH, SPSXZXBH, YXTYWLSH, SPHJDM, SPHJMC, SPRXM, SPRZWDM, SPRZWMC, SPYJ, SPSJ, BZ, BYZDA, BYZDB, BYZDC, BYZDD)

  
  select 
 UUID, CREATOR, CREATE_TIME, MODIFIER, MODIFY_TIME, FORM_ID, YWLSH, SJBBH, SPBZH, SPSXBH, SPSXZXBH, YXTYWLSH, SPHJDM, SPHJMC, SPRXM, SPRZWDM, SPRZWMC, SPYJ, SPSJ, BZ, BYZDA, BYZDB, BYZDC, BYZDD

  from USERFORM_SPGC_SPGC;
  commit;
------------------userform_spgc_bj ------------------
insert into uf_SPGC_BJ
(UUID, CREATOR, CREATE_TIME, MODIFIER, MODIFY_TIME, FORM_uuID, YWLSH, SJBBH, SPSXBH, SPSXZXBH, YXTYWLSH, BJJGZZJGDM, BJJGMC, BJSJ, BJJG, BLJGMS, ZFTHYY, ZJMC, ZJBH, ZJYXQX, FZDW, SFJE, JEDWDM, BZ, BYZDA, BYZDB, BYZDC, BYZDD)

  
  select 
UUID, CREATOR, CREATE_TIME, MODIFIER, MODIFY_TIME, FORM_ID, YWLSH, SJBBH, SPSXBH, SPSXZXBH, YXTYWLSH, BJJGZZJGDM, BJJGMC, BJSJ, BJJG, BLJGMS, ZFTHYY, ZJMC, ZJBH, ZJYXQX, FZDW, SFJE, JEDWDM, BZ, BYZDA, BYZDB, BYZDC, BYZDD


  from USERFORM_SPGC_BJ;
  commit;

