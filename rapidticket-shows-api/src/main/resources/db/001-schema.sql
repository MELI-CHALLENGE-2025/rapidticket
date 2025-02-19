CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- ==========================
-- ENUM TYPES
-- ==========================

CREATE TYPE reservation_status AS ENUM ('PENDING', 'CONFIRMED', 'CANCELLED', 'RESERVED', 'PENDING_PAYMENT');
CREATE TYPE seat_status AS ENUM ('AVAILABLE', 'RESERVED', 'SOLD');
CREATE TYPE currency_type AS ENUM ('USD');

-- ==========================
-- TABLES
-- ==========================

-- Shows (Artistic performances)
CREATE TABLE IF NOT EXISTS shows (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    code VARCHAR(6) NOT NULL,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    created_at TIMESTAMP DEFAULT NOW()
);

-- Venues (Theaters, stadiums, etc.)
CREATE TABLE IF NOT EXISTS venues (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    code VARCHAR(6) NOT NULL,
    name VARCHAR(255) NOT NULL,
    location VARCHAR(255) NOT NULL,
    capacity INT NOT NULL,
    created_at TIMESTAMP DEFAULT NOW()
);

-- Functions (A show happening at a venue on a specific date)
CREATE TABLE IF NOT EXISTS functions (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    code VARCHAR(6) NOT NULL,
    show_id UUID NOT NULL,
    venue_id UUID NOT NULL,
    date TIMESTAMP NOT NULL,
    base_price DECIMAL(10,2) NOT NULL,
    currency currency_type NOT NULL DEFAULT 'USD',
    created_at TIMESTAMP DEFAULT NOW(),
    FOREIGN KEY (show_id) REFERENCES shows(id) ON DELETE CASCADE,
    FOREIGN KEY (venue_id) REFERENCES venues(id) ON DELETE CASCADE
);

-- Seats (Seats for a specific function)
CREATE TABLE IF NOT EXISTS seats (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    function_id UUID NOT NULL,
    section VARCHAR(50) NOT NULL,
    row VARCHAR(10) NOT NULL,
    seat_number VARCHAR(10) NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    status seat_status NOT NULL DEFAULT 'AVAILABLE',
    created_at TIMESTAMP DEFAULT NOW(),
    FOREIGN KEY (function_id) REFERENCES functions(id) ON DELETE CASCADE
);

-- Reservations (Reservations for functions)
CREATE TABLE IF NOT EXISTS reservations (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    dni VARCHAR(20) NOT NULL,
    customer_name VARCHAR(100) NOT NULL,
    function_id UUID NOT NULL,
    total_price DECIMAL(10,2) NOT NULL,
    created_at TIMESTAMP DEFAULT NOW(),
    status reservation_status NOT NULL DEFAULT 'PENDING',
    FOREIGN KEY (function_id) REFERENCES functions(id) ON DELETE CASCADE
);

-- Relationship between Reservations and Seats
CREATE TABLE IF NOT EXISTS reservation_seats (
    reservation_id UUID NOT NULL,
    seat_id UUID NOT NULL,
    PRIMARY KEY (reservation_id, seat_id),
    FOREIGN KEY (reservation_id) REFERENCES reservations(id) ON DELETE CASCADE,
    FOREIGN KEY (seat_id) REFERENCES seats(id) ON DELETE CASCADE
);

-- ==========================
-- INDEXES
-- ==========================

CREATE INDEX idx_function_date ON functions(date);
CREATE INDEX idx_seat_function ON seats(function_id);
CREATE INDEX idx_reservation_function ON reservations(function_id);
CREATE INDEX idx_reservation_dni ON reservations(dni);
CREATE INDEX idx_reservation_status ON reservations(status);

-- ==========================
-- CONSTRAINTS
-- ==========================

ALTER TABLE seats ADD CONSTRAINT unique_seat_per_function UNIQUE (function_id, section, row, seat_number);
