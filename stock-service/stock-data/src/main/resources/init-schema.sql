DROP SCHEMA IF EXISTS stock CASCADE;

CREATE SCHEMA stock;

CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

DROP TABLE IF EXISTS stock.stocks CASCADE;

CREATE TABLE stock.stocks
(
    id uuid NOT NULL,
    name character varying COLLATE pg_catalog."default" NOT NULL,
    active boolean NOT NULL,
    CONSTRAINT stocks_pkey PRIMARY KEY (id)
);

DROP TYPE IF EXISTS approval_status;

CREATE TYPE approval_status AS ENUM ('APPROVED', 'REJECTED');

DROP TABLE IF EXISTS stock.order_approval CASCADE;

CREATE TABLE stock.order_approval
(
    id uuid NOT NULL,
    stock_id uuid NOT NULL,
    order_id uuid NOT NULL,
    status approval_status NOT NULL,
    CONSTRAINT order_approval_pkey PRIMARY KEY (id)
);

DROP TABLE IF EXISTS stock.products CASCADE;

CREATE TABLE stock.products
(
    id uuid NOT NULL,
    name character varying COLLATE pg_catalog."default" NOT NULL,
    price numeric(10,2) NOT NULL,
    available boolean NOT NULL,
    CONSTRAINT products_pkey PRIMARY KEY (id)
);

DROP TABLE IF EXISTS stock.stock_products CASCADE;

CREATE TABLE stock.stock_products
(
    id uuid NOT NULL,
    stock_id uuid NOT NULL,
    product_id uuid NOT NULL,
    CONSTRAINT stock_products_pkey PRIMARY KEY (id)
);

ALTER TABLE stock.stock_products
    ADD CONSTRAINT "FK_STOCK_ID" FOREIGN KEY (stock_id)
    REFERENCES stock.stocks (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE RESTRICT
    NOT VALID;

ALTER TABLE stock.stock_products
    ADD CONSTRAINT "FK_PRODUCT_ID" FOREIGN KEY (product_id)
    REFERENCES stock.products (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE RESTRICT
    NOT VALID;

DROP MATERIALIZED VIEW IF EXISTS stock.order_stock_m_view;

CREATE MATERIALIZED VIEW stock.order_stock_m_view
TABLESPACE pg_default
AS
 SELECT r.id AS stock_id,
    r.name AS stock_name,
    r.active AS stock_active,
    p.id AS product_id,
    p.name AS product_name,
    p.price AS product_price,
    p.available AS product_available
   FROM stock.stocks r,
    stock.products p,
    stock.stock_products rp
  WHERE r.id = rp.stock_id AND p.id = rp.product_id
WITH DATA;

refresh materialized VIEW stock.order_stock_m_view;

DROP function IF EXISTS stock.refresh_order_stock_m_view;

CREATE OR replace function stock.refresh_order_stock_m_view()
returns trigger
AS '
BEGIN
    refresh materialized VIEW stock.order_stock_m_view;
    return null;
END;
'  LANGUAGE plpgsql;

DROP trigger IF EXISTS refresh_order_stock_m_view ON stock.stock_products;

CREATE trigger refresh_order_stock_m_view
after INSERT OR UPDATE OR DELETE OR truncate
ON stock.stock_products FOR each statement
EXECUTE PROCEDURE stock.refresh_order_stock_m_view();