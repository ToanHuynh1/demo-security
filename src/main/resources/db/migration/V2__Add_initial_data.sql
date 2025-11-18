-- V2__Add_initial_data.sql
-- Add initial data for roles, permissions, and sample records

-- Insert default admin user
INSERT INTO users (username, password, role) VALUES
('admin', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'ADMIN'); -- password: password

-- Insert default permissions for admin user (user_id = 1)
INSERT INTO user_permissions (user_id, permission) VALUES
(1, 'READ_USER'),
(1, 'WRITE_USER'),
(1, 'DELETE_USER'),
(1, 'READ_COURSE'),
(1, 'WRITE_COURSE'),
(1, 'DELETE_COURSE'),
(1, 'READ_CATEGORY'),
(1, 'WRITE_CATEGORY'),
(1, 'DELETE_CATEGORY'),
(1, 'READ_FILE'),
(1, 'WRITE_FILE'),
(1, 'DELETE_FILE');

-- Insert sample categories
INSERT INTO categories (name, description) VALUES
('Programming', 'Programming and software development courses'),
('Design', 'Graphic design and UI/UX courses'),
('Business', 'Business and entrepreneurship courses'),
('Marketing', 'Digital marketing and advertising courses');

-- Insert sample courses
INSERT INTO courses (name, description, price, category_id) VALUES
('Java Spring Boot Masterclass', 'Complete guide to building applications with Spring Boot', 99.99, 1),
('React.js Fundamentals', 'Learn React.js from scratch', 79.99, 1),
('UI/UX Design Principles', 'Master the fundamentals of user interface design', 89.99, 2),
('Digital Marketing Strategy', 'Complete digital marketing course', 119.99, 4);