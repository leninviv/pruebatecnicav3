INSERT INTO products (code, name, price, stock, active)
SELECT 'PROD-001', 'Teclado Logitech K120', 25.50, 50, 1 WHERE NOT EXISTS (SELECT 1 FROM products WHERE code = 'PROD-001');
INSERT INTO products (code, name, price, stock, active)
SELECT 'PROD-002', 'Mouse Logitech M185', 18.90, 80, 1 WHERE NOT EXISTS (SELECT 1 FROM products WHERE code = 'PROD-002');
INSERT INTO products (code, name, price, stock, active)
SELECT 'PROD-003', 'Monitor LG 24"', 185.00, 15, 1 WHERE NOT EXISTS (SELECT 1 FROM products WHERE code = 'PROD-003');
INSERT INTO products (code, name, price, stock, active)
SELECT 'PROD-004', 'Laptop Lenovo ThinkPad', 850.00, 8, 1 WHERE NOT EXISTS (SELECT 1 FROM products WHERE code = 'PROD-004');
INSERT INTO products (code, name, price, stock, active)
SELECT 'PROD-005', 'Laptop HP ProBook', 790.00, 12, 1 WHERE NOT EXISTS (SELECT 1 FROM products WHERE code = 'PROD-005');
INSERT INTO products (code, name, price, stock, active)
SELECT 'PROD-006', 'Memoria RAM 8GB DDR4', 32.50, 40, 1 WHERE NOT EXISTS (SELECT 1 FROM products WHERE code = 'PROD-006');
INSERT INTO products (code, name, price, stock, active)
SELECT 'PROD-007', 'Memoria RAM 16GB DDR4', 58.90, 25, 1 WHERE NOT EXISTS (SELECT 1 FROM products WHERE code = 'PROD-007');
INSERT INTO products (code, name, price, stock, active)
SELECT 'PROD-008', 'SSD Kingston 480GB', 45.00, 35, 1 WHERE NOT EXISTS (SELECT 1 FROM products WHERE code = 'PROD-008');
INSERT INTO products (code, name, price, stock, active)
SELECT 'PROD-009', 'SSD Kingston 1TB', 82.00, 20, 1 WHERE NOT EXISTS (SELECT 1 FROM products WHERE code = 'PROD-009');
INSERT INTO products (code, name, price, stock, active)
SELECT 'PROD-010', 'Disco Externo 1TB', 65.00, 30, 1 WHERE NOT EXISTS (SELECT 1 FROM products WHERE code = 'PROD-010');
INSERT INTO products (code, name, price, stock, active)
SELECT 'PROD-011', 'Cable HDMI 2M', 8.50, 100, 1 WHERE NOT EXISTS (SELECT 1 FROM products WHERE code = 'PROD-011');
INSERT INTO products (code, name, price, stock, active)
SELECT 'PROD-012', 'Cable USB-C', 12.90, 75, 1 WHERE NOT EXISTS (SELECT 1 FROM products WHERE code = 'PROD-012');
INSERT INTO products (code, name, price, stock, active)
SELECT 'PROD-013', 'Webcam Logitech C920', 95.00, 10, 1 WHERE NOT EXISTS (SELECT 1 FROM products WHERE code = 'PROD-013');
INSERT INTO products (code, name, price, stock, active)
SELECT 'PROD-014', 'Audifonos Logitech', 45.00, 22, 1 WHERE NOT EXISTS (SELECT 1 FROM products WHERE code = 'PROD-014');
INSERT INTO products (code, name, price, stock, active)
SELECT 'PROD-015', 'Parlante Bluetooth', 55.00, 18, 1 WHERE NOT EXISTS (SELECT 1 FROM products WHERE code = 'PROD-015');
INSERT INTO products (code, name, price, stock, active)
SELECT 'PROD-016', 'Router TP-Link', 48.00, 16, 1 WHERE NOT EXISTS (SELECT 1 FROM products WHERE code = 'PROD-016');
INSERT INTO products (code, name, price, stock, active)
SELECT 'PROD-017', 'Switch 8 Puertos', 38.00, 14, 1 WHERE NOT EXISTS (SELECT 1 FROM products WHERE code = 'PROD-017');
INSERT INTO products (code, name, price, stock, active)
SELECT 'PROD-018', 'UPS 600VA', 75.00, 9, 1 WHERE NOT EXISTS (SELECT 1 FROM products WHERE code = 'PROD-018');
INSERT INTO products (code, name, price, stock, active)
SELECT 'PROD-019', 'Impresora Multifuncion', 220.00, 6, 1 WHERE NOT EXISTS (SELECT 1 FROM products WHERE code = 'PROD-019');
INSERT INTO products (code, name, price, stock, active)
SELECT 'PROD-020', 'Regulador de Voltaje', 35.00, 20, 1 WHERE NOT EXISTS (SELECT 1 FROM products WHERE code = 'PROD-020');
INSERT INTO products (code, name, price, stock, active)
SELECT 'TEC-001', 'Teclado Mecanico', 45.90, 12, 1 WHERE NOT EXISTS (SELECT 1 FROM products WHERE code = 'TEC-001');
INSERT INTO products (code, name, price, stock, active)
SELECT 'MOU-001', 'Mouse Inalambrico', 18.50, 30, 1 WHERE NOT EXISTS (SELECT 1 FROM products WHERE code = 'MOU-001');
INSERT INTO products (code, name, price, stock, active)
SELECT 'MON-024', 'Monitor 24 Pulgadas', 149.99, 8, 1 WHERE NOT EXISTS (SELECT 1 FROM products WHERE code = 'MON-024');
INSERT INTO products (code, name, price, stock, active)
SELECT 'LAP-015', 'Laptop 15', 799.00, 4, 1 WHERE NOT EXISTS (SELECT 1 FROM products WHERE code = 'LAP-015');
INSERT INTO products (code, name, price, stock, active)
SELECT 'CAB-HDMI', 'Cable HDMI', 7.25, 50, 1 WHERE NOT EXISTS (SELECT 1 FROM products WHERE code = 'CAB-HDMI');
INSERT INTO products (code, name, price, stock, active)
SELECT 'CAM-HD', 'Webcam HD', 39.00, 15, 1 WHERE NOT EXISTS (SELECT 1 FROM products WHERE code = 'CAM-HD');
INSERT INTO products (code, name, price, stock, active)
SELECT 'AUD-BT', 'Audifonos Bluetooth', 29.90, 20, 1 WHERE NOT EXISTS (SELECT 1 FROM products WHERE code = 'AUD-BT');
INSERT INTO products (code, name, price, stock, active)
SELECT 'SIL-001', 'Silla Gamer', 189.00, 3, 0 WHERE NOT EXISTS (SELECT 1 FROM products WHERE code = 'SIL-001');
