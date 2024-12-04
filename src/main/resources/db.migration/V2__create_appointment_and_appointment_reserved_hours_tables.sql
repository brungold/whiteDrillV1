CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE appointment
(
    id                BIGSERIAL PRIMARY KEY,
    appointment_date  DATE NOT NULL,
    appointment_time  TIME NOT NULL,
    duration          INTEGER NOT NULL,
    price             NUMERIC(10, 2) NOT NULL,
    status            VARCHAR(10) NOT NULL DEFAULT 'SCHEDULED',
    appointment_notes TEXT,
    dentist_id        BIGINT,
    patient_id        BIGINT NOT NULL,
    schedule_id       BIGINT NOT NULL,
    uuid              UUID DEFAULT uuid_generate_v4() NOT NULL UNIQUE,
    created_on        TIMESTAMP(6) WITH TIME ZONE DEFAULT now(),
    version           BIGINT DEFAULT 0,
    CONSTRAINT fk_dentist FOREIGN KEY (dentist_id) REFERENCES dentist (id),
    CONSTRAINT fk_patient FOREIGN KEY (patient_id) REFERENCES patient (id),
    CONSTRAINT fk_schedule FOREIGN KEY (schedule_id) REFERENCES schedule (id)
);

CREATE TABLE appointment_reserved_hours (
    appointment_id BIGINT NOT NULL,
    reserved_hour  INTEGER NOT NULL,
    PRIMARY KEY (appointment_id, reserved_hour),
    CONSTRAINT fk_appointment_reserved_hours FOREIGN KEY (appointment_id) REFERENCES appointment (id) ON DELETE CASCADE
);

-- Indeksy dla szybszego wyszukiwania
CREATE INDEX idx_appointment_date_time ON appointment (appointment_date, appointment_time);
CREATE INDEX idx_appointment_schedule_id ON appointment (schedule_id);