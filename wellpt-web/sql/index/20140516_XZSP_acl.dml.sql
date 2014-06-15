create index acl_entry_batch_idx on acl_entry (granting, mask, acl_object_identity, sid);

create index acl_sid_member_batch_idx on acl_sid_member (member, module_id, owner_sid);
