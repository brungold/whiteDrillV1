CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE schedule
(
    id          BIGSERIAL PRIMARY KEY,
    date        DATE NOT NULL,
    start_time  TIME NOT NULL,
    end_time    TIME NOT NULL,
    dentist_id  BIGINT NOT NULL,
    description TEXT,
    uuid        UUID DEFAULT uuid_generate_v4() NOT NULL UNIQUE,
    created_on  TIMESTAMP(6) WITH TIME ZONE DEFAULT now(),
    version     BIGINT DEFAULT 0,
    CONSTRAINT fk_dentist FOREIGN KEY (dentist_id) REFERENCES dentist (id)
);

CREATE TABLE schedule_booked_hours
(
    schedule_id BIGINT NOT NULL,
    booked_hour INTEGER NOT NULL,
    PRIMARY KEY (schedule_id, booked_hour),
    CONSTRAINT fk_schedule_booked_hours FOREIGN KEY (schedule_id) REFERENCES schedule (id) ON DELETE CASCADE
);

-- Dodanie indeksu dla schedule_id w tabeli schedule_booked_hours
CREATE INDEX idx_schedule_booked_hours_schedule_id ON schedule_booked_hours (schedule_id);