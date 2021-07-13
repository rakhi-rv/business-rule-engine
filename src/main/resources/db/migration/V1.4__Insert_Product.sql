INSERT INTO product(product_type, parent) VALUES('physical_product', NULL);
INSERT INTO product(product_type, parent) VALUES('book', 'physical_product');
INSERT INTO product(product_type, parent) VALUES('membership', NULL);
INSERT INTO product(product_type, parent) VALUES('activate_membership', 'membership');
INSERT INTO product(product_type, parent) VALUES('upgrade_membership', 'membership');
INSERT INTO product(product_type, parent) VALUES('video', 'physical_product');
