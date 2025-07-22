DROP SCHEMA IF EXISTS payment CASCADE;

CREATE SCHEMA notification;

CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

DROP TYPE IF EXISTS notification_status;

CREATE TYPE notification_status AS ENUM ('SENT', 'FAILED');

DROP TABLE IF EXISTS "notification".notifications CASCADE;

CREATE TABLE "notification".notifications
(
    id uuid NOT NULL,
    customer_id uuid NOT NULL,
    order_id uuid NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    status notification_status NOT NULL,
    CONSTRAINT notifications_pkey PRIMARY KEY (id)
);
