CREATE TABLE product
(
    id           BIGSERIAL PRIMARY KEY NOT NULL,
    product_type varchar               NOT NULL,
    parent       varchar
);
