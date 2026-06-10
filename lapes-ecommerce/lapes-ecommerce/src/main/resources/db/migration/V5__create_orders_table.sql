CREATE TABLE orders (
    id              UUID           PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id         UUID           NOT NULL REFERENCES users (id),
    status          VARCHAR(20)    NOT NULL DEFAULT 'PENDING',
    total           NUMERIC(12, 2) NOT NULL,
    discount_amount NUMERIC(12, 2) NOT NULL DEFAULT 0,
    coupon_id       UUID           REFERENCES coupons (id),
    created_at      TIMESTAMP      NOT NULL DEFAULT NOW()
);

CREATE TABLE order_items (
    id           UUID           PRIMARY KEY DEFAULT gen_random_uuid(),
    order_id     UUID           NOT NULL REFERENCES orders (id) ON DELETE CASCADE,
    product_id   UUID           NOT NULL,
    product_name VARCHAR(200)   NOT NULL,
    price        NUMERIC(12, 2) NOT NULL,
    qty          INT            NOT NULL
);

CREATE INDEX idx_orders_user_id ON orders (user_id);
CREATE INDEX idx_orders_status  ON orders (status);
