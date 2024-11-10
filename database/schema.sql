-- Tworzenie bazy danych
CREATE DATABASE IF NOT EXISTS budget_manager;
USE budget_manager;

-- Tabela users
CREATE TABLE users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(45) NOT NULL,
    password VARCHAR(255) NOT NULL,
    password_salt VARCHAR(255) NOT NULL,
    mail VARCHAR(45) UNIQUE NOT NULL
);

-- Tabela groups
CREATE TABLE groups (
    group_id INT AUTO_INCREMENT PRIMARY KEY,
    group_name VARCHAR(45) NOT NULL,
    goal VARCHAR(45),
    currency VARCHAR(45),
    join_code VARCHAR(45) UNIQUE NOT NULL,
    admin_user_id INT,
    FOREIGN KEY (admin_user_id) REFERENCES users(user_id)
);

-- Tabela transactions
CREATE TABLE transactions (
    transaction_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(45) NOT NULL,
    category VARCHAR(45) NOT NULL,
    date DATE NOT NULL,
    user_id INT NOT NULL,
    group_id INT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (group_id) REFERENCES groups(group_id)
);

-- Tabela pośrednia users_groups
CREATE TABLE users_groups (
    user_id INT NOT NULL,
    group_id INT NOT NULL,
    join_date DATE NOT NULL,
    PRIMARY KEY (user_id, group_id),
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (group_id) REFERENCES groups(group_id)
);