CREATE TABLE carts (
    id         UUID      PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id    UUID      NOT NULL UNIQUE REFERENCES users (id),
    updated_at TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE TABLE cart_items (
    id         UUID NOT NULL PRIMARY KEY DEFAULT gen_random_uuid(),
    cart_id    UUID NOT NULL REFERENCES carts (id) ON DELETE CASCADE,
    product_id UUID NOT NULL REFERENCES products (id),
    qty        INT  NOT NULL DEFAULT 1,
    UNIQUE (cart_id, product_id)
);
