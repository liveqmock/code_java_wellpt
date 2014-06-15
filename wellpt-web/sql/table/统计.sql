/*�ϱ������е���������*/
select d.data_id, d.reserved_number2, d.reserved_number1, d.create_time
  from is_exchange_data d, is_exchange_data_batch b
 where d.batch_id = b.uuid
   and b.type_id = '004140203SZ'
   and b.from_id = '004140203'
   and d.newest_data = 'yes'
 order by b.create_time desc;

/*���г�֤��������ɣ�*/
 select d.data_id, d.reserved_number2, d.reserved_number1, d.create_time
  from is_exchange_data d, is_exchange_data_batch b
 where d.batch_id = b.uuid
   and b.type_id = '000000000XK'
   and d.newest_data = 'yes'
 order by b.create_time desc;

/*�����ϱ�����Ч���ݣ��������壩*/
select count(*)
  from is_exchange_data d, is_exchange_data_batch b
 where d.batch_id = b.uuid
   and b.from_id = '004140203'
   and b.type_id = '004140203SZ'
   and d.reserved_number2 is not null
 order by b.create_time desc;
 
/*�����ϱ�����Ч���ݣ�������ɣ�*/
select count(*)
  from is_exchange_data d, is_exchange_data_batch b
 where d.batch_id = b.uuid
   and b.from_id = '004140203'
   and b.type_id = '000000000XK'
   and d.reserved_number2 is not null
 order by b.create_time desc;

/*�������*/
select x.XKJYXMMC  from userform_ssxx_xgxk x where dwmc is not null group by x.XKJYXMMC;

/*��ɵ�λ�������������ͳ��*/
select x.DWMC,x.xkjyxmmc,count(z.uuid) as ztNum  from userform_ssxx_xgxk x ,userform_ssxx_ztdj z
where z.uuid=x.parent_id and  dwmc is not null group by x.xkjyxmmc,x.dwmc order by x.dwmc

/*��֤�ĵ�λ*/
select b.from_id
  from is_exchange_data d, is_exchange_data_batch b
 where d.batch_id = b.uuid
   and b.type_id = '000000000XK'
   and d.newest_data = 'yes'
group by b.from_id

/*ǩ�ջ��˻صĵ�λ*/
select m.unit_id
  from is_exchange_data d, is_exchange_data_batch b,is_exchange_data_monitor m 
 where d.batch_id = b.uuid
   and m.data_id = d.uuid
   and b.type_id = '004140203SZ'
   and b.from_id = '004140203'
   and (m.receive_node='sign'or m.receive_node='back')
   group by m.unit_id