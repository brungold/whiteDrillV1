CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE patient
(
    id              BIGSERIAL PRIMARY KEY,
    first_name      VARCHAR(50) NOT NULL,
    last_name       VARCHAR(50) NOT NULL,
    maiden_name     VARCHAR(50),
    language        VARCHAR(50),
    nationality     VARCHAR(50),
    patient_gender  VARCHAR(10) NOT NULL, -- Czy zmienić na typ ENUM, czy na natywne ENUM?
    birth_date      DATE NOT NULL,
    pesel           VARCHAR(11) NOT NULL,
    nip             VARCHAR(10),
    phone           VARCHAR(15) NOT NULL,
    email           VARCHAR(255) NOT NULL,
    address_id      BIGINT,
    uuid            UUID DEFAULT uuid_generate_v4() NOT NULL UNIQUE,
    created_on      TIMESTAMP(6) WITH TIME ZONE DEFAULT now(),
    version         BIGINT DEFAULT 0,
    CONSTRAINT fk_address FOREIGN KEY (address_id) REFERENCES address (id) ON DELETE SET NULL,
    CONSTRAINT unique_pesel UNIQUE (pesel),

    CONSTRAINT unique_email UNIQUE (email)
);

-- Indeksy dla szybszego wyszukiwania
CREATE INDEX idx_patient_pesel ON patient (pesel);
CREATE INDEX idx_patient_email ON patient (email);
CREATE INDEX idx_patient_phone ON patient (phone);