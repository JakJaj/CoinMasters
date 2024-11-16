USE budget_manager;

INSERT INTO users (name, password, password_salt, mail)
VALUES 
    ('Jan Kowalski', 'hashed_password1', 'salt123', 'jan.kowalski@example.com'),
    ('Anna Nowak', 'hashed_password2', 'salt456', 'anna.nowak@example.com'),
    ('Piotr Wiśniewski', 'hashed_password3', 'salt789', 'piotr.wisniewski@example.com'),
    ('Maria Zielińska', 'hashed_password4', 'salt012', 'maria.zielinska@example.com'),
    ('Karol Malinowski', 'hashed_password5', 'salt345', 'karol.malinowski@example.com');


INSERT INTO groups (group_name, goal, currency, join_code, admin_user_id)
VALUES 
    ('Family Budget', 'Save for a car', 'PLN', 'JOIN123', 1),
    ('Friends Trip', 'Plan a vacation', 'USD', 'TRAVEL2023', 2),
    ('Work Team', 'Team party fund', 'EUR', 'PARTYFUN', 3),
    ('Sports Club', 'New equipment', 'GBP', 'SPORT2023', 4),
    ('Neighbors Fund', 'Shared utilities', 'PLN', 'NEIGHBOR', 5);


INSERT INTO users_groups (user_id, group_id, join_date)
VALUES 
    (1, 1, '2023-11-01'),
    (2, 1, '2023-11-02'),
    (3, 2, '2023-11-03'),
    (4, 3, '2023-11-04'),
    (5, 4, '2023-11-05');


INSERT INTO users_groups (user_id, group_id, join_date)
VALUES 
    (1, 2, '2023-11-06'),
    (3, 1, '2023-11-07'),
    (4, 4, '2023-11-08'),
    (2, 3, '2023-11-09'),
    (5, 5, '2023-11-10');

INSERT INTO transactions (name, category, date, amount, user_id, group_id)
VALUES 
    ('Groceries', 'Food', '2023-11-07', 123.45, 1, 1),
    ('Fuel', 'Transport', '2023-11-08', 200.00, 2, 1),
    ('Hotel Booking', 'Travel', '2023-11-09', 500.00, 3, 2),
    ('New Equipment', 'Sports', '2023-11-10', 300.00, 4, 3),
    ('Utilities', 'Bills', '2023-11-11', 150.00, 5, 5);

INSERT INTO transactions (name, category, date, amount, user_id, group_id)
VALUES 
    ('Dinner', 'Food', '2023-11-07', 75.50, 1, 1),
    ('Flight Tickets', 'Travel', '2023-11-08', 1200.00, 2, 2),
    ('Party Supplies', 'Entertainment', '2023-11-09', 400.00, 3, 3),
    ('Gym Membership', 'Fitness', '2023-11-10', 250.00, 4, 4),
    ('Internet Bill', 'Bills', '2023-11-11', 100.00, 5, 5);
