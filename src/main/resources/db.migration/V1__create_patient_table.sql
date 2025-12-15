CREATE TABLE IF NOT EXISTS patient
(
    id                 BIGSERIAL PRIMARY KEY,

    name               VARCHAR(255),

    email              VARCHAR(255) NOT NULL,

    address            VARCHAR(255),

    date_of_birth      DATE,

    registered_date    TIMESTAMP WITHOUT TIME ZONE         NOT NULL,

--     created_by         BIGINT,
--     created_at         TIMESTAMP WITHOUT TIME ZONE,
--     last_modified_by   BIGINT,
--     last_modified_date TIMESTAMP WITHOUT TIME ZONE,

    CONSTRAINT uk_patient_email UNIQUE (email)
);