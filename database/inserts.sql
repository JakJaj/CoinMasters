USE budget_manager;

INSERT INTO users (name, password, password_salt, mail, rolee)
VALUES
    ('Ewa Kowalczyk', 'hashed_password6', 'salt901', 'ewa.kowalczyk@example.com', 'admin'), 
    ('Tomasz Nowicki', 'hashed_password7', 'salt234', 'tomasz.nowicki@example.com', 'admin'), 
    ('Barbara Zawadzka', 'hashed_password8', 'salt567', 'barbara.zawadzka@example.com', 'admin'), 
    ('Adam Wiśniewski', 'hashed_password9', 'salt890', 'adam.wisniewski@example.com', 'user'),
    ('Katarzyna Zielińska', 'hashed_password10', 'salt123', 'katarzyna.zielinska@example.com', 'user'),
    ('Mateusz Wójcik', 'hashed_password11', 'salt456', 'mateusz.wojcik@example.com', 'user');


INSERT INTO groupss (group_name, goal, currency, join_code, admin_user_id)
VALUES
    ('Akcja Charytatywna', 'Wsparcie lokalnych inicjatyw', 'PLN', 'CHARYTATYWA', 1), 
    ('Klub Technologiczny', 'Zakup nowego sprzętu', 'USD', 'TECHKLUB', 2),           
    ('Fundusz Wakacyjny', 'Organizacja wakacji', 'EUR', 'WAKACJE2023', 3);          


INSERT INTO users_groups (user_id, group_id, join_date)
VALUES
    (1, 1, '2025-01-01'),
    (2, 1, '2025-01-02'),
    (3, 1, '2025-01-03'),
    (4, 1, '2025-01-04');



INSERT INTO users_groups (user_id, group_id, join_date)
VALUES
    (2, 2, '2025-01-05'),
    (3, 2, '2025-01-06'),
    (1, 3, '2025-01-07'),
    (4, 3, '2025-01-08');


INSERT INTO transactions (name, category, amount, date, user_id, group_id)
VALUES
    ('Darowizna na schronisko', 'Charytatywne', 200.00, '2025-01-10', 1, 1),
    ('Organizacja koncertu', 'Charytatywne', 500.00, '2025-01-12', 1, 1),
    ('Zakup materiałów edukacyjnych', 'Edukacja', 150.00, '2025-01-14', 1, 1),
    ('Transport darów', 'Logistyka', 120.00, '2025-01-16', 1, 1),
    ('Posiłki dla wolontariuszy', 'Logistyka', 80.00, '2025-01-18', 1, 1);

INSERT INTO transactions (name, category, amount, date, user_id, group_id)
VALUES
    ('Wsparcie dla domu dziecka', 'Charytatywne', 300.00, '2025-01-11', 2, 1),
    ('Plakaty promujące wydarzenie', 'Marketing', 100.00, '2025-01-13', 2, 1),
    ('Wynajem sali na aukcję', 'Logistyka', 400.00, '2025-01-15', 2, 1),
    ('Prowadzenie kampanii online', 'Marketing', 250.00, '2025-01-17', 2, 1),
    ('Zakup sprzętu', 'Inne', 350.00, '2025-01-19', 2, 1);

INSERT INTO transactions (name, category, amount, date, user_id, group_id)
VALUES
    ('Przygotowanie paczek świątecznych', 'Charytatywne', 400.00, '2025-01-10', 3, 1),
    ('Promocja wydarzenia na Facebooku', 'Marketing', 150.00, '2025-01-12', 3, 1),
    ('Opłaty administracyjne', 'Inne', 200.00, '2025-01-14', 3, 1),
    ('Książki edukacyjne', 'Edukacja', 250.00, '2025-01-16', 3, 1),
    ('Transport paczek', 'Logistyka', 100.00, '2025-01-18', 3, 1);

INSERT INTO transactions (name, category, amount, date, user_id, group_id)
VALUES
    ('Oprawa graficzna wydarzenia', 'Marketing', 120.00, '2025-01-11', 4, 1),
    ('Zakup zabawek dla dzieci', 'Charytatywne', 600.00, '2025-01-13', 4, 1),
    ('Wynajem sprzętu nagłaśniającego', 'Logistyka', 500.00, '2025-01-15', 4, 1),
    ('Druk broszur informacyjnych', 'Marketing', 200.00, '2025-01-17', 4, 1),
    ('Posiłki dla beneficjentów', 'Logistyka', 300.00, '2025-01-19', 4, 1);

INSERT INTO transactions (name, category, amount, date, user_id, group_id)
VALUES
    ('Zakup laptopa', 'Technologia', 4500.00, '2025-01-20', 2, 2),
    ('Licencja oprogramowania', 'Technologia', 800.00, '2025-01-21', 3, 2);

INSERT INTO transactions (name, category, amount, date, user_id, group_id)
VALUES
    ('Bilety lotnicze', 'Podróże', 1200.00, '2025-01-22', 1, 3),
    ('Rezerwacja hotelu', 'Podróże', 2500.00, '2025-01-23', 4, 3),
    ('Ubezpieczenie podróżne', 'Podróże', 300.00, '2025-01-24', 4, 3);