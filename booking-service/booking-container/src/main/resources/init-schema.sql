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

DROP TABLE IF EXISTS "booking".container_movement_outbox CASCADE;

CREATE TABLE "booking".container_movement_outbox
(
    id uuid NOT NULL,
    saga_id uuid NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    processed_at TIMESTAMP WITH TIME ZONE,
    type character varying COLLATE pg_catalog."default" NOT NULL,
    payload jsonb NOT NULL,
    outbox_status outbox_status NOT NULL,
    saga_status saga_status NOT NULL,
    booking_status booking_status NOT NULL,
    version integer NOT NULL,
    CONSTRAINT container_movement_outbox_pkey PRIMARY KEY (id)
);

CREATE INDEX "container_movement_saga_status"
    ON "booking".container_movement_outbox
        (type, outbox_status, saga_status);
