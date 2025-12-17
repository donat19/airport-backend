-- Sample data for Airport Management System

-- Airports
INSERT INTO airports (code, name, city, country, timezone) VALUES
('JFK', 'John F. Kennedy International Airport', 'New York', 'USA', 'America/New_York'),
('LAX', 'Los Angeles International Airport', 'Los Angeles', 'USA', 'America/Los_Angeles'),
('LHR', 'London Heathrow Airport', 'London', 'UK', 'Europe/London'),
('DXB', 'Dubai International Airport', 'Dubai', 'UAE', 'Asia/Dubai'),
('HND', 'Tokyo Haneda Airport', 'Tokyo', 'Japan', 'Asia/Tokyo');

-- Airlines
INSERT INTO airlines (code, name, country) VALUES
('AA', 'American Airlines', 'USA'),
('BA', 'British Airways', 'UK'),
('EK', 'Emirates', 'UAE'),
('JL', 'Japan Airlines', 'Japan'),
('DL', 'Delta Air Lines', 'USA');

-- Aircraft
INSERT INTO aircraft (registration_number, model, manufacturer, capacity) VALUES
('N12345', 'Boeing 777-300ER', 'Boeing', 396),
('N67890', 'Airbus A380-800', 'Airbus', 525),
('G-ABCD', 'Boeing 787-9', 'Boeing', 290),
('A6-XYZ', 'Airbus A350-900', 'Airbus', 325),
('JA8888', 'Boeing 737-800', 'Boeing', 189);

-- Gates (for JFK airport)
INSERT INTO gates (gate_number, terminal, status, airport_id) VALUES
('A1', 'Terminal 1', 'AVAILABLE', 1),
('A2', 'Terminal 1', 'AVAILABLE', 1),
('B5', 'Terminal 4', 'AVAILABLE', 1),
('B6', 'Terminal 4', 'OCCUPIED', 1),
('C12', 'Terminal 5', 'MAINTENANCE', 1);

-- Gates (for LAX airport)
INSERT INTO gates (gate_number, terminal, status, airport_id) VALUES
('10', 'Terminal 1', 'AVAILABLE', 2),
('45', 'Terminal 4', 'AVAILABLE', 2),
('52', 'Terminal 5', 'AVAILABLE', 2);

-- Flights
INSERT INTO flights (flight_number, departure_time, arrival_time, status, departure_airport_id, arrival_airport_id, airline_id, aircraft_id, gate_id) VALUES
('AA100', '2025-12-18 08:00:00', '2025-12-18 11:30:00', 'SCHEDULED', 1, 2, 1, 1, 1),
('BA200', '2025-12-18 14:00:00', '2025-12-18 22:00:00', 'BOARDING', 1, 3, 2, 3, 2),
('EK300', '2025-12-18 22:30:00', '2025-12-19 18:45:00', 'SCHEDULED', 1, 4, 3, 2, 3),
('JL400', '2025-12-18 18:00:00', '2025-12-19 21:00:00', 'DELAYED', 1, 5, 4, 5, 4),
('DL500', '2025-12-18 10:00:00', '2025-12-18 13:30:00', 'DEPARTED', 2, 1, 5, 1, 6);

-- Users
INSERT INTO users (username, email, password, role) VALUES
('admin', 'admin@airport.com', '$2a$10$slYQmyNdGzTn7ZLBXBChFOC9f6kFjAqPhccnP6DxlWXx2lPk1C3G6', 'ADMIN'),
('user', 'user@airport.com', '$2a$10$slYQmyNdGzTn7ZLBXBChFOC9f6kFjAqPhccnP6DxlWXx2lPk1C3G6', 'USER');
-- Note: Password is "password" hashed with BCrypt
