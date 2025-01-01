CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE users
(
    id          BIGSERIAL   PRIMARY KEY,
    email       VARCHAR(255)NOT NULL UNIQUE,
    password    VARCHAR(255)NOT NULL,
    authorities TEXT[],
    enabled     BOOLEAN     NOT NULL,
    created_on  TIMESTAMP(6) WITH TIME ZONE DEFAULT now(),
    uuid        UUID         DEFAULT uuid_generate_v4() NOT NULL UNIQUE,
    version     BIGINT       DEFAULT 0 NOT NULL
);