-- 任务调度
DELETE FROM QRTZ_TRIGGER_LISTENERS;
DELETE FROM QRTZ_SIMPLE_TRIGGERS;
DELETE FROM QRTZ_SCHEDULER_STATE;
DELETE FROM QRTZ_PAUSED_TRIGGER_GRPS;
-- DELETE FROM QRTZ_LOCKS;
DELETE FROM QRTZ_JOB_LISTENERS;
DELETE FROM QRTZ_FIRED_TRIGGERS;
DELETE FROM QRTZ_CRON_TRIGGERS;
DELETE FROM QRTZ_CALENDARS;
DELETE FROM QRTZ_BLOB_TRIGGERS;
DELETE FROM QRTZ_TRIGGERS;
DELETE FROM QRTZ_JOB_DETAILS;

COMMIT;