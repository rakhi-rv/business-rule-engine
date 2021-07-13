CREATE TABLE business_rule
(
    id           BIGSERIAL PRIMARY KEY NOT NULL,
    product_type varchar               NOT NULL,
    rule         varchar               NOT NULL
);
