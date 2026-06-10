CREATE TABLE products (
    id          UUID           PRIMARY KEY DEFAULT gen_random_uuid(),
    name        VARCHAR(200)   NOT NULL,
    description TEXT,
    price       NUMERIC(12, 2) NOT NULL,
    stock       INT            NOT NULL DEFAULT 0,
    category    VARCHAR(100)   NOT NULL,
    image_url   VARCHAR(500),
    active      BOOLEAN        NOT NULL DEFAULT TRUE,
    deleted_at  TIMESTAMP,
    created_at  TIMESTAMP      NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_products_category   ON products (category)  WHERE deleted_at IS NULL;
CREATE INDEX idx_products_active_del ON products (active, deleted_at);
