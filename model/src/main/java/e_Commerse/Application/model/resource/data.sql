-- Insert default Users (Owner + Customers)
INSERT INTO users (user_id, username, email, password, role)
VALUES
(9, 'owner_1', 'owner1@gmail.com', '$2a$10$7mqlJ4dbz1LJ4x3w8v9Ure8cB0sN4V8I2E8sZ3d0cQ4PwvNlQOZb2', 'OWNER'),
(10, 'customer_1', 'customer1@gmail.com', '$2a$10$7mqlJ4dbz1LJ4x3w8v9Ure8cB0sN4V8I2E8sZ3d0cQ4PwvNlQOZb2', 'CUSTOMER'),
(11, 'customer_2', 'customer2@gmail.com', '$2a$10$7mqlJ4dbz1LJ4x3w8v9Ure8cB0sN4V8I2E8sZ3d0cQ4PwvNlQOZb2', 'CUSTOMER');

-- Password is "1234" (BCrypt-encoded)

-- Insert Products (Owned by owner_1)
INSERT INTO products (product_id, name, description, price, stock, owner_id)
VALUES
(7, 'Wireless Mouse', 'Ergonomic wireless mouse with long battery life', 799.00, 50, 1),
(8, 'Mechanical Keyboard', 'RGB backlit mechanical keyboard', 2499.00, 30, 1),
(9, 'USB-C Charger', 'Fast 25W USB-C charger compatible with all phones', 999.00, 40, 1);

-- Insert Orders
INSERT INTO orders (order_id, customer_id, product_id, quantity, status, order_date)
VALUES
(8, 2, 1, 2, 'PENDING', NOW()),
(9, 3, 2, 1, 'DELIVERED', NOW() - INTERVAL '3 days');


-- Queries for Apple iPhone 15 Pro (product_id = 6)
INSERT INTO product_queries
(query_id, question, answer, created_at, answered_at, customer_id, product_id)
VALUES
(4, 'Does this iPhone come with charger?', 'Yes, it comes with charger', '2025-10-05 09:00:24', '2025-10-05 10:13:44', 1, 6),
(5, 'Does this iPhone support MagSafe?', 'Yes, it supports MagSafe charging', '2025-10-05 09:05:30', '2025-10-05 10:20:11', 2, 6),
(6, 'What is the battery capacity?', 'It has a 4500 mAh battery', '2025-10-05 09:10:00', '2025-10-05 10:30:45', 3, 6),
(7, 'Is there a warranty included?', 'Yes, 1-year manufacturer warranty', '2025-10-05 09:15:22', '2025-10-05 10:40:00', 4, 6),
(8, 'Does it support 5G?', 'Yes, it supports 5G connectivity', '2025-10-05 09:20:15', '2025-10-05 10:50:12', 5, 6);

-- Queries for Wireless Headphones (product_id = 2,3,4,5,1)
INSERT INTO product_queries
(query_id, question, answer, created_at, answered_at, customer_id, product_id)
VALUES
(9, 'Are these headphones Bluetooth 5.0?', 'Yes, they use Bluetooth 5.0', '2025-10-05 10:00:00', '2025-10-05 11:00:00', 1, 2),
(10, 'Do they have noise cancellation?', 'Yes, active noise cancellation is included', '2025-10-05 10:05:12', '2025-10-05 11:10:00', 2, 2),
(11, 'What is the battery life?', 'Up to 20 hours of continuous playback', '2025-10-05 10:10:30', '2025-10-05 11:15:20', 3, 3),
(12, 'Are they foldable?', 'Yes, foldable for easy storage', '2025-10-05 10:15:45', '2025-10-05 11:20:30', 4, 4),
(13, 'Do they come with a carrying case?', 'Yes, a carrying case is included', '2025-10-05 10:20:00', '2025-10-05 11:25:15', 5, 5);

-- Queries for Wireless Mouse (product_id = 7)
INSERT INTO product_queries
(query_id, question, answer, created_at, answered_at, customer_id, product_id)
VALUES
(14, 'Does it support Bluetooth and USB?', 'Yes, supports both Bluetooth and USB dongle', '2025-10-05 11:00:00', '2025-10-05 12:00:00', 1, 7),
(15, 'What is the battery life?', 'Battery lasts up to 12 months', '2025-10-05 11:05:00', '2025-10-05 12:05:00', 2, 7);

-- Queries for Mechanical Keyboard (product_id = 8)
INSERT INTO product_queries
(query_id, question, answer, created_at, answered_at, customer_id, product_id)
VALUES
(16, 'Are the switches tactile?', 'Yes, they are tactile Cherry MX Blue', '2025-10-05 11:10:00', '2025-10-05 12:10:00', 3, 8),
(17, 'Does it have RGB backlighting?', 'Yes, fully customizable RGB', '2025-10-05 11:15:00', '2025-10-05 12:15:00', 4, 8);

-- Queries for USB-C Charger (product_id = 9)
INSERT INTO product_queries
(query_id, question, answer, created_at, answered_at, customer_id, product_id)
VALUES
(18, 'Is this charger fast charging?', 'Yes, supports 25W fast charging', '2025-10-05 11:20:00', '2025-10-05 12:20:00', 5, 9),
(19, 'Is it compatible with all phones?', 'Compatible with all USB-C devices', '2025-10-05 11:25:00', '2025-10-05 12:25:00', 1, 9);

