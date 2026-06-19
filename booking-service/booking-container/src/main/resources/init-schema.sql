DROP SCHEMA IF EXISTS "booking" CASCADE;

CREATE SCHEMA "booking";

CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

DROP TYPE IF EXISTS booking_status;
CREATE TYPE booking_status AS ENUM ('PENDING', 'PAID', 'APPROVED', 'CANCELLED', 'CANCELLING');

DROP TABLE IF EXISTS "booking".bookings CASCADE;

CREATE TABLE "booking".bookings
(
    id uuid NOT NULL,
    tracking_id uuid NOT NULL,
    booking_status booking_status NOT NULL,
    failure_messages character varying COLLATE pg_catalog."default",
    CONSTRAINT bookings_pkey PRIMARY KEY (id)
);

DROP TABLE IF EXISTS "booking".booking_items CASCADE;

CREATE TABLE "booking".booking_items
(
    id bigint NOT NULL,
    booking_id uuid NOT NULL,
    quantity integer NOT NULL,
    CONSTRAINT booking_items_pkey PRIMARY KEY (id, booking_id)
);

ALTER TABLE "booking".booking_items
    ADD CONSTRAINT "FK_BOOKING_ID" FOREIGN KEY (booking_id)
    REFERENCES "booking".bookings (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE CASCADE
    NOT VALID;

DROP TYPE IF EXISTS saga_status;
CREATE TYPE saga_status AS ENUM ('STARTED', 'FAILED', 'SUCCEEDED', 'PROCESSING', 'COMPENSATING', 'COMPENSATED');

DROP TYPE IF EXISTS outbox_status;
CREATE TYPE outbox_status AS ENUM ('STARTED', 'COMPLETED', 'FAILED');