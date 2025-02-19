-- ==========================
-- INSERT DATA
-- ==========================

-- Insertar Shows
INSERT INTO shows (id, code, name, description, created_at) VALUES
  ('11111111-1111-1111-1111-111111111111', 'SH001', 'The Phantom of the Opera', 'A musical by Andrew Lloyd Webber', NOW()),
  ('22222222-2222-2222-2222-222222222222', 'SH002', 'Hamilton', 'A Broadway musical about Alexander Hamilton', NOW());

-- Insertar Venues
INSERT INTO venues (id, code, name, location, capacity, created_at) VALUES
  ('aaaaaaa1-aaaa-aaaa-aaaa-aaaaaaaaaaaa', 'VN001', 'Royal Theatre', 'New York, USA', 1200, NOW()),
  ('bbbbbbb2-bbbb-bbbb-bbbb-bbbbbbbbbbbb', 'VN002', 'Grand Opera House', 'London, UK', 1500, NOW());

-- Insertar Functions
INSERT INTO functions (id, code, show_id, venue_id, date, base_price, currency, created_at) VALUES
  ('33333333-3333-3333-3333-333333333333', 'FN001', '11111111-1111-1111-1111-111111111111', 'aaaaaaa1-aaaa-aaaa-aaaa-aaaaaaaaaaaa', '2025-03-10 19:00:00', 50.00, 'USD', NOW()),
  ('44444444-4444-4444-4444-444444444444', 'FN002', '22222222-2222-2222-2222-222222222222', 'bbbbbbb2-bbbb-bbbb-bbbb-bbbbbbbbbbbb', '2025-04-15 20:00:00', 70.00, 'USD', NOW());

-- Insertar Seats
INSERT INTO seats (id, function_id, section, row, seat_number, price, status, created_at) VALUES
  ('55555555-5555-5555-5555-555555555555', '33333333-3333-3333-3333-333333333333', 'Orchestra', 'A', '10', 60.00, 'AVAILABLE', NOW()),
  ('66666666-6666-6666-6666-666666666666', '33333333-3333-3333-3333-333333333333', 'Mezzanine', 'B', '15', 45.00, 'AVAILABLE', NOW()),
  ('77777777-7777-7777-7777-777777777777', '44444444-4444-4444-4444-444444444444', 'Balcony', 'C', '20', 35.00, 'AVAILABLE', NOW());

-- Insertar Reservations
INSERT INTO reservations (id, dni, customer_name, function_id, total_price, created_at, status) VALUES
  ('88888888-8888-8888-8888-888888888888', '12345678A', 'John Doe', '33333333-3333-3333-3333-333333333333', 60.00, NOW(), 'CONFIRMED'),
  ('99999999-9999-9999-9999-999999999999', '87654321B', 'Jane Smith', '44444444-4444-4444-4444-444444444444', 35.00, NOW(), 'PENDING');

-- Insertar Reservation Seats
INSERT INTO reservation_seats (reservation_id, seat_id) VALUES
  ('88888888-8888-8888-8888-888888888888', '55555555-5555-5555-5555-555555555555'),
  ('99999999-9999-9999-9999-999999999999', '77777777-7777-7777-7777-777777777777');
