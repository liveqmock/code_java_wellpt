--更新视图的关键字查询字段
update DYVIEW_SELECT_DEFINITION t set t.vague_key_select = 1 where t.for_key_select = 1 and t.vague_key_select is null and t.exact_key_select is null;