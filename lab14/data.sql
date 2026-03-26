-- Tạo database
IF DB_ID('task_management_db') IS NULL
    CREATE DATABASE task_management_db;
GO

USE task_management_db;
GO

-- ========================
-- Bảng users
-- ========================
CREATE TABLE users (
                       id INT IDENTITY(1,1) PRIMARY KEY,
                       username VARCHAR(50) NOT NULL UNIQUE,
                       email VARCHAR(100) NOT NULL UNIQUE,
                       password VARCHAR(255) NOT NULL,
                       created_at DATETIME DEFAULT GETDATE(),
                       updated_at DATETIME DEFAULT GETDATE(),
                       is_active BIT DEFAULT 1
);
GO

-- Trigger cập nhật updated_at khi update users
CREATE TRIGGER trg_users_update
    ON users
    AFTER UPDATE
              AS
BEGIN
UPDATE users
SET updated_at = GETDATE()
    FROM users u
    INNER JOIN inserted i ON u.id = i.id;
END;
GO

-- ========================
-- Bảng roles
-- ========================
CREATE TABLE roles (
                       id INT IDENTITY(1,1) PRIMARY KEY,
                       name VARCHAR(50) NOT NULL UNIQUE
);
GO

-- ========================
-- Bảng user_roles (Many-to-Many)
-- ========================
CREATE TABLE user_roles (
                            user_id INT NOT NULL,
                            role_id INT NOT NULL,
                            PRIMARY KEY (user_id, role_id),
                            FOREIGN KEY (user_id) REFERENCES users(id),
                            FOREIGN KEY (role_id) REFERENCES roles(id)
);
GO

-- ========================
-- Bảng projects
-- ========================
CREATE TABLE projects (
                          id INT IDENTITY(1,1) PRIMARY KEY,
                          name VARCHAR(100) NOT NULL,
                          description VARCHAR(500),
                          created_by INT,
                          created_at DATETIME DEFAULT GETDATE(),
                          is_active BIT DEFAULT 1,
                          FOREIGN KEY (created_by) REFERENCES users(id)
);
GO

-- ========================
-- Bảng project_users (Many-to-Many)
-- ========================
CREATE TABLE project_users (
                               project_id INT NOT NULL,
                               user_id INT NOT NULL,
                               PRIMARY KEY (project_id, user_id),
                               FOREIGN KEY (project_id) REFERENCES projects(id),
                               FOREIGN KEY (user_id) REFERENCES users(id)
);
GO

-- ========================
-- Bảng tasks
-- ========================
CREATE TABLE tasks (
                       id INT IDENTITY(1,1) PRIMARY KEY,
                       title VARCHAR(100) NOT NULL,
                       description VARCHAR(500),
                       deadline DATETIME,
                       status VARCHAR(50),
                       project_id INT,
                       assigned_user_id INT,
                       created_at DATETIME DEFAULT GETDATE(),
                       updated_at DATETIME DEFAULT GETDATE(),
                       is_active BIT DEFAULT 1,
                       FOREIGN KEY (project_id) REFERENCES projects(id),
                       FOREIGN KEY (assigned_user_id) REFERENCES users(id)
);
GO

-- Trigger cập nhật updated_at khi update tasks
CREATE TRIGGER trg_tasks_update
    ON tasks
    AFTER UPDATE
              AS
BEGIN
UPDATE tasks
SET updated_at = GETDATE()
    FROM tasks t
    INNER JOIN inserted i ON t.id = i.id;
END;
GO

-- ========================
-- Indexes để truy vấn nhanh
-- ========================
CREATE INDEX idx_tasks_project ON tasks(project_id);
CREATE INDEX idx_tasks_user ON tasks(assigned_user_id);
CREATE INDEX idx_projects_user ON projects(created_by);
GO
-- ========================
-- Insert roles mặc định
-- ========================
INSERT INTO roles (name) VALUES ('USER');
INSERT INTO roles (name) VALUES ('ADMIN');
GO

-- ========================
-- Insert user mẫu
-- ========================
INSERT INTO users (username, email, password)
VALUES ('dungFc', 'dn94465@gmail.com', '123456'); -- password ví dụ
GO

-- Gán role USER cho user vừa tạo
INSERT INTO user_roles (user_id, role_id)
VALUES (
    (SELECT id FROM users WHERE username='dungFc'),
    (SELECT id FROM roles WHERE name='USER')
);