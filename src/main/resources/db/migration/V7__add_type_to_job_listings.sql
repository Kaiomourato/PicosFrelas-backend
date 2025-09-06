-- V7__add_type_to_job_listings.sql
ALTER TABLE job_listings ADD COLUMN type VARCHAR(255) NOT NULL DEFAULT 'JOB';