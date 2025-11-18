-- V3__Add_user_email.sql
-- Add email field to users table

ALTER TABLE users ADD COLUMN email VARCHAR(255) UNIQUE;

-- Add index for email
CREATE INDEX idx_users_email ON users(email);

-- Update existing users with default email (optional)
-- UPDATE users SET email = CONCAT(username, '@example.com') WHERE email IS NULL;