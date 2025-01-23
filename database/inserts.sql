USE budget_manager;

INSERT INTO users (name, password, password_salt, mail, rolee)
VALUES
    ('Ewa Kowalczyk', 'hashed_password1', 'salt001', 'ewa.kowalczyk@example.com', 'USER'),
    ('Tomasz Nowicki', 'hashed_password2', 'salt002', 'tomasz.nowicki@example.com', 'USER'),
    ('Barbara Zawadzka', 'hashed_password3', 'salt003', 'barbara.zawadzka@example.com', 'USER'),
    ('Adam Wiśniewski', 'hashed_password4', 'salt004', 'adam.wisniewski@example.com', 'USER'),
    ('Katarzyna Zielińska', 'hashed_password5', 'salt005', 'katarzyna.zielinska@example.com', 'USER'),
    ('Mateusz Wójcik', 'hashed_password6', 'salt006', 'mateusz.wojcik@example.com', 'USER'),
    ('Joanna Kaczmarek', 'hashed_password7', 'salt007', 'joanna.kaczmarek@example.com', 'USER'),
    ('Michał Lis', 'hashed_password8', 'salt008', 'michal.lis@example.com', 'USER'),
    ('Monika Pawlak', 'hashed_password9', 'salt009', 'monika.pawlak@example.com', 'USER'),
    ('Piotr Maj', 'hashed_password10', 'salt010', 'piotr.maj@example.com', 'USER'),
    ('Anna Borkowska', 'hashed_password11', 'salt011', 'anna.borkowska@example.com', 'USER'),
    ('Jan Nowak', 'hashed_password12', 'salt012', 'jan.nowak@example.com', 'USER'),
    ('Marcin Duda', 'hashed_password13', 'salt013', 'marcin.duda@example.com', 'USER'),
    ('Patrycja Kowal', 'hashed_password14', 'salt014', 'patrycja.kowal@example.com', 'USER'),
    ('Rafał Lewandowski', 'hashed_password15', 'salt015', 'rafal.lewandowski@example.com', 'USER');



INSERT INTO groupss (group_name, goal, currency, join_code, admin_user_id)
VALUES
    ('Akcja Charytatywna', 'Wsparcie lokalnych inicjatyw', 'PLN', 'CHARYTATYWA', 1),
    ('Klub Technologiczny', 'Zakup nowego sprzętu', 'USD', 'TECHKLUB', 2),
    ('Fundusz Wakacyjny', 'Organizacja wakacji', 'EUR', 'WAKACJE2023', 3);

INSERT INTO users_groups (user_id, group_id, join_date)
VALUES

    (1, 1, '2023-11-01'),
    (2, 1, '2023-11-02'),
    (3, 1, '2023-11-03'),
    (4, 1, '2023-11-04'),
    (5, 1, '2023-11-05'),

  
    (6, 2, '2023-11-06'),
    (7, 2, '2023-11-07'),
    (8, 2, '2023-11-08'),
    (1, 2, '2023-11-09'), 
    (2, 2, '2023-11-10'), 
 
    (9, 3, '2023-11-11'),
    (10, 3, '2023-11-12'),
    (11, 3, '2023-11-13'),
    (1, 3, '2023-11-14'), 
    (5, 3, '2023-11-15'); 

INSERT INTO transactions (name, category, amount, date, user_id, group_id)
VALUES
    ('Darowizna 1', 'Charytatywne', 100.00, '2023-11-01', 1, 1),
    ('Zakup materiałów', 'Zakupy', 200.00, '2023-11-02', 2, 1),
    ('Organizacja eventu', 'Usługi', 300.00, '2023-11-03', 3, 1),
    ('Transport darów', 'Transport', 150.00, '2023-11-04', 4, 1),
    ('Druk plakatów', 'Reklama', 50.00, '2023-11-05', 5, 1),
    ('Nowa darowizna', 'Charytatywne', 400.00, '2023-11-06', 1, 1),
    ('Promocja wydarzenia', 'Reklama', 120.00, '2023-11-07', 2, 1),
    ('Wynajem sali', 'Usługi', 500.00, '2023-11-08', 3, 1),
    ('Zakup jedzenia', 'Zakupy', 250.00, '2023-11-09', 4, 1),
    ('Darowizna 2', 'Charytatywne', 300.00, '2023-11-10', 1, 1),
    ('Zakup książek', 'Edukacja', 600.00, '2023-11-11', 5, 1),
    ('Koszty transportu', 'Transport', 100.00, '2023-11-12', 4, 1),
    ('Druk ulotek', 'Reklama', 90.00, '2023-11-13', 3, 1);

INSERT INTO transactions (name, category, amount, date, user_id, group_id)
VALUES
    ('Zakup sprzętu', 'Sprzęt', 2000.00, '2023-11-01', 6, 2),
    ('Szkolenie', 'Edukacja', 500.00, '2023-11-02', 7, 2),
    ('Prezentacja', 'Usługi', 300.00, '2023-11-03', 8, 2),
    ('Zakup licencji', 'Software', 800.00, '2023-11-04', 1, 2),
    ('Promocja klubu', 'Marketing', 400.00, '2023-11-05', 2, 2),
    ('Druk materiałów', 'Zakupy', 200.00, '2023-11-06', 6, 2),
    ('Nowe komputery', 'Sprzęt', 5000.00, '2023-11-07', 7, 2),
    ('Organizacja warsztatów', 'Edukacja', 1500.00, '2023-11-08', 8, 2),
    ('Rejestracja domeny', 'Software', 100.00, '2023-11-09', 1, 2),
    ('Hosting', 'Software', 300.00, '2023-11-10', 2, 2),
    ('Koszty serwera', 'Software', 400.00, '2023-11-11', 6, 2),
    ('Zakup routera', 'Sprzęt', 600.00, '2023-11-12', 7, 2),
    ('Konferencja', 'Edukacja', 800.00, '2023-11-13', 8, 2);

INSERT INTO transactions (name, category, amount, date, user_id, group_id)
VALUES
    ('Rezerwacja hotelu', 'Noclegi', 3000.00, '2023-11-01', 9, 3),
    ('Bilety lotnicze', 'Transport', 5000.00, '2023-11-02', 10, 3),
    ('Ubezpieczenie podróżne', 'Ubezpieczenia', 400.00, '2023-11-03', 11, 3),
    ('Zakup map', 'Zakupy', 50.00, '2023-11-04', 1, 3),
    ('Wynajem samochodu', 'Transport', 1000.00, '2023-11-05', 5, 3),
    ('Wycieczki fakultatywne', 'Rozrywka', 800.00, '2023-11-06', 9, 3),
    ('Kolacja grupowa', 'Jedzenie', 300.00, '2023-11-07', 10, 3),
    ('Pamiątki', 'Zakupy', 150.00, '2023-11-08', 11, 3),
    ('Bilety na atrakcje', 'Rozrywka', 600.00, '2023-11-09', 1, 3),
    ('Rezerwacja restauracji', 'Jedzenie', 500.00, '2023-11-10', 5, 3),
    ('Zakup kosmetyków', 'Zakupy', 200.00, '2023-11-11', 9, 3),
    ('Transport publiczny', 'Transport', 100.00, '2023-11-12', 10, 3),
    ('Opłata za przewodnika', 'Usługi', 300.00, '2023-11-13', 11, 3);

