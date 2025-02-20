INSERT INTO users (id, email, password_hash, full_name, role) VALUES
('3fa85f64-5717-4562-b3fc-2c963f66afa6', 'admin@example.com', 'hash1', 'Admin User', 'ADMIN'),
('4fa85f64-5717-4562-b3fc-2c963f66afa7', 'manager@example.com', 'hash2', 'Manager User', 'MANAGER'),
('5fa85f64-5717-4562-b3fc-2c963f66afa8', 'customer1@example.com', 'hash3', 'Customer One', 'CUSTOMER'),
('6fa85f64-5717-4562-b3fc-2c963f66afa9', 'customer2@example.com', 'hash4', 'Customer Two', 'CUSTOMER');

INSERT INTO shows (id, code, name, description, created_by) VALUES
('910b962e-041c-11ec-9a03-0242ac130011', 'SH003', 'Hamilton', 'A modern hip-hop musical', '4fa85f64-5717-4562-b3fc-2c963f66afa7'),
('a10b962e-041c-11ec-9a03-0242ac130012', 'SH004', 'Wicked', 'A prequel to The Wizard of Oz', '4fa85f64-5717-4562-b3fc-2c963f66afa7');

INSERT INTO venues (id, code, name, location, capacity, created_by) VALUES
('831b962e-041c-11ec-9a03-0242ac130005', 'VN002', 'West End Theater', 'London, UK', 1500, '4fa85f64-5717-4562-b3fc-2c963f66afa7'),
('841b962e-041c-11ec-9a03-0242ac130006', 'VN003', 'Sydney Opera House', 'Sydney, Australia', 2500, '4fa85f64-5717-4562-b3fc-2c963f66afa7');

INSERT INTO functions (id, code, show_id, venue_id, date, base_price, currency, created_by) VALUES
('111b962e-041c-11ec-9a03-0242ac130031', 'FN001', '910b962e-041c-11ec-9a03-0242ac130011', '831b962e-041c-11ec-9a03-0242ac130005', '2024-09-20 19:30:00', 50.00, 'USD', '4fa85f64-5717-4562-b3fc-2c963f66afa7'),
('121b962e-041c-11ec-9a03-0242ac130032', 'FN002', 'a10b962e-041c-11ec-9a03-0242ac130012', '841b962e-041c-11ec-9a03-0242ac130006', '2024-09-25 18:00:00', 60.00, 'USD', '4fa85f64-5717-4562-b3fc-2c963f66afa7');

INSERT INTO seats (id, function_id, seat_number, row, section, price, status, created_by) VALUES
('211b962e-041c-11ec-9a03-0242ac130041', '111b962e-041c-11ec-9a03-0242ac130031', 'B10', 'B', 'Mezzanine', 100.00, 'AVAILABLE', '4fa85f64-5717-4562-b3fc-2c963f66afa7'),
('221b962e-041c-11ec-9a03-0242ac130042', '111b962e-041c-11ec-9a03-0242ac130031', 'C5', 'C', 'Balcony', 80.00, 'AVAILABLE', '4fa85f64-5717-4562-b3fc-2c963f66afa7'),
('231b962e-041c-11ec-9a03-0242ac130043', '121b962e-041c-11ec-9a03-0242ac130032', 'D15', 'D', 'Orchestra', 120.00, 'AVAILABLE', '4fa85f64-5717-4562-b3fc-2c963f66afa7');

INSERT INTO reservations (id, customer_id, function_id, total_price, status) VALUES
('311b962e-041c-11ec-9a03-0242ac130051', '6fa85f64-5717-4562-b3fc-2c963f66afa9', '111b962e-041c-11ec-9a03-0242ac130031', 180.00, 'PENDING'),
('321b962e-041c-11ec-9a03-0242ac130052', '5fa85f64-5717-4562-b3fc-2c963f66afa8', '121b962e-041c-11ec-9a03-0242ac130032', 120.00, 'CONFIRMED');

INSERT INTO reservation_seats (reservation_id, seat_id) VALUES
('311b962e-041c-11ec-9a03-0242ac130051', '211b962e-041c-11ec-9a03-0242ac130041'),
('311b962e-041c-11ec-9a03-0242ac130051', '221b962e-041c-11ec-9a03-0242ac130042'),
('321b962e-041c-11ec-9a03-0242ac130052', '231b962e-041c-11ec-9a03-0242ac130043');
