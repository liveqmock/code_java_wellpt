/*上报的所有的商事主体*/
select d.data_id, d.reserved_number2, d.reserved_number1, d.create_time
  from is_exchange_data d, is_exchange_data_batch b
 where d.batch_id = b.uuid
   and b.type_id = '004140203SZ'
   and b.from_id = '004140203'
   and d.newest_data = 'yes'
 order by b.create_time desc;

/*所有出证（行政许可）*/
 select d.data_id, d.reserved_number2, d.reserved_number1, d.create_time
  from is_exchange_data d, is_exchange_data_batch b
 where d.batch_id = b.uuid
   and b.type_id = '000000000XK'
   and d.newest_data = 'yes'
 order by b.create_time desc;

/*工商上报的有效数据（商事主体）*/
select count(*)
  from is_exchange_data d, is_exchange_data_batch b
 where d.batch_id = b.uuid
   and b.from_id = '004140203'
   and b.type_id = '004140203SZ'
   and d.reserved_number2 is not null
 order by b.create_time desc;
 
/*工商上报的有效数据（行政许可）*/
select count(*)
  from is_exchange_data d, is_exchange_data_batch b
 where d.batch_id = b.uuid
   and b.from_id = '004140203'
   and b.type_id = '000000000XK'
   and d.reserved_number2 is not null
 order by b.create_time desc;

/*许可事项*/
select x.XKJYXMMC  from userform_ssxx_xgxk x where dwmc is not null group by x.XKJYXMMC;

/*许可单位的许可事项主体统计*/
select x.DWMC,x.xkjyxmmc,count(z.uuid) as ztNum  from userform_ssxx_xgxk x ,userform_ssxx_ztdj z
where z.uuid=x.parent_id and  dwmc is not null group by x.xkjyxmmc,x.dwmc order by x.dwmc

/*出证的单位*/
select b.from_id
  from is_exchange_data d, is_exchange_data_batch b
 where d.batch_id = b.uuid
   and b.type_id = '000000000XK'
   and d.newest_data = 'yes'
group by b.from_id

/*签收或退回的单位*/
select m.unit_id
  from is_exchange_data d, is_exchange_data_batch b,is_exchange_data_monitor m 
 where d.batch_id = b.uuid
   and m.data_id = d.uuid
   and b.type_id = '004140203SZ'
   and b.from_id = '004140203'
   and (m.receive_node='sign'or m.receive_node='back')
   group by m.unit_id