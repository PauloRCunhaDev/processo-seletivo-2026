CREATE TABLE coupons (
    id              UUID           PRIMARY KEY DEFAULT gen_random_uuid(),
    code            VARCHAR(50)    NOT NULL UNIQUE,
    type            VARCHAR(20)    NOT NULL,
    value           NUMERIC(12, 2) NOT NULL,
    min_order_value NUMERIC(12, 2) NOT NULL DEFAULT 0,
    expires_at      TIMESTAMP      NOT NULL,
    active          BOOLEAN        NOT NULL DEFAULT TRUE
);

CREATE TABLE coupon_redemptions (
    id         UUID      PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id    UUID      NOT NULL REFERENCES users (id),
    coupon_id  UUID      NOT NULL REFERENCES coupons (id),
    used_at    TIMESTAMP NOT NULL DEFAULT NOW(),
    UNIQUE (user_id, coupon_id)
);
