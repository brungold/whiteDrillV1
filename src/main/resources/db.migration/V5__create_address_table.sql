CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE address
(
    id               BIGSERIAL PRIMARY KEY,
    postal_code      INTEGER NOT NULL,
    city             VARCHAR(255),
    street           VARCHAR(255),
    house_number     INTEGER NOT NULL,
    apartment_number INTEGER DEFAULT NULL,
    uuid            UUID DEFAULT uuid_generate_v4() NOT NULL UNIQUE,
    created_on      TIMESTAMP(6) WITH TIME ZONE DEFAULT now(),
    version         BIGINT DEFAULT 0

);