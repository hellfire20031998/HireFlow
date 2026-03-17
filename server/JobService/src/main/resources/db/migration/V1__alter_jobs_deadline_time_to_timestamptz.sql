-- Fix: jobs.deadline_time was bigint (Java Long); entity now uses Instant (timestamptz).
-- Run once in PostgreSQL (psql, pgAdmin, DBeaver, etc.) connected to database: hireflowjob

-- Option A: Existing values are epoch milliseconds (from Java Long). Converts them to timestamptz.
ALTER TABLE jobs
  ALTER COLUMN deadline_time TYPE timestamp with time zone
  USING CASE
    WHEN deadline_time IS NULL THEN NULL
    ELSE to_timestamp((deadline_time::numeric) / 1000.0) AT TIME ZONE 'UTC'
  END;

-- Option B: Column is empty or you don't need old values. Uncomment and use instead of Option A:
-- ALTER TABLE jobs
--   ALTER COLUMN deadline_time TYPE timestamp with time zone USING NULL;
