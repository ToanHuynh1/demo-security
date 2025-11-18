-- V1__Initial_schema.sql
-- Initial database schema for Demo Security Application

-- Create users table
CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(255) NOT NULL,
    refresh_token VARCHAR(512),
    refresh_token_expiry BIGINT
);

-- Create user_permissions table for user permissions
CREATE TABLE user_permissions (
    user_id BIGINT NOT NULL,
    permission VARCHAR(255),
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Create categories table
CREATE TABLE categories (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE,
    description TEXT
);

-- Create courses table
CREATE TABLE courses (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    description TEXT,
    price DOUBLE,
    category_id BIGINT,
    FOREIGN KEY (category_id) REFERENCES categories(id)
);

-- Create uploaded_files table
CREATE TABLE uploaded_files (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    file_name VARCHAR(255),
    file_url VARCHAR(500),
    public_id VARCHAR(255),
    file_type VARCHAR(100),
    file_size BIGINT,
    uploaded_at TIMESTAMP
);

-- Create indexes for better performance
CREATE INDEX idx_users_username ON users(username);
CREATE INDEX idx_users_role ON users(role);
CREATE INDEX idx_user_permissions_user_id ON user_permissions(user_id);
CREATE INDEX idx_categories_name ON categories(name);
CREATE INDEX idx_courses_category_id ON courses(category_id);
CREATE INDEX idx_uploaded_files_public_id ON uploaded_files(public_id);