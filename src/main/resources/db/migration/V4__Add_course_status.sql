-- V4__Add_course_status.sql
-- Add status field to courses table

ALTER TABLE courses ADD COLUMN status VARCHAR(20) DEFAULT 'DRAFT';

-- Update existing courses to PUBLISHED status
UPDATE courses SET status = 'PUBLISHED' WHERE id IS NOT NULL;

-- Add check constraint for status values
ALTER TABLE courses ADD CONSTRAINT chk_course_status
CHECK (status IN ('DRAFT', 'PUBLISHED', 'ARCHIVED'));

-- Add index for status
CREATE INDEX idx_courses_status ON courses(status);