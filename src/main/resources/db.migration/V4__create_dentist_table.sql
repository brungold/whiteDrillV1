CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE dentist
(
    id              BIGSERIAL PRIMARY KEY,
    first_name      VARCHAR(50) NOT NULL,
    last_name       VARCHAR(50) NOT NULL,
    uuid            UUID DEFAULT uuid_generate_v4() NOT NULL UNIQUE,
    created_on      TIMESTAMP(6) WITH TIME ZONE DEFAULT now(),
    version         BIGINT DEFAULT 0
);