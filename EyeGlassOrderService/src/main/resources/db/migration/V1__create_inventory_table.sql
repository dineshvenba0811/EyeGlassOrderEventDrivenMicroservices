CREATE TABLE inventory (
                           id SERIAL PRIMARY KEY,
                           product_id BIGINT NOT NULL,
                           product_type VARCHAR(50) NOT NULL,
                           name VARCHAR(100),
                           quantity INT NOT NULL,
                           threshold INT NOT NULL DEFAULT 5,
                           restock_level INT NOT NULL DEFAULT 10,
                           supplier_id BIGINT,
                           last_updated TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Optional index for faster lookups by product
CREATE INDEX idx_inventory_product ON inventory (product_id, product_type);