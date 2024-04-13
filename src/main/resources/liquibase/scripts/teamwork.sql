-- liquibase formatted sql

-- changeset karybekov:1
CREATE TABLE IF NOT EXISTS animal_shelter (
    id SERIAL PRIMARY KEY,
    address VARCHAR(255),
    file_path VARCHAR(255),
    file_size BIGINT,
    media_type VARCHAR(255),
    data BYTEA
);

CREATE TABLE IF NOT EXISTS pet_owner (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255),
    phone INT
);

CREATE TABLE IF NOT EXISTS pet (
    id SERIAL PRIMARY KEY,
    nick VARCHAR(255),
    pet_owner_id INT REFERENCES pet_owner(id)
);

CREATE TABLE IF NOT EXISTS owner_report (
    id SERIAL PRIMARY KEY,
    diet VARCHAR(255),
    well_being VARCHAR(255),
    behaviour_change VARCHAR(255),
    file_path VARCHAR(255),
    file_size BIGINT,
    media_type VARCHAR(255),
    data BYTEA,
    pet_id INT REFERENCES pet(id)
);

CREATE TABLE IF NOT EXISTS Message (
    id SERIAL PRIMARY KEY,
    chat_id BIGINT,
    message TEXT,
    notification_date_time TIMESTAMP
);

-- changeset karybekov:2
DROP TABLE IF EXISTS message;

CREATE TABLE IF NOT EXISTS bot_menu (
    id SERIAL PRIMARY KEY,
    chat_id BIGINT,
    message TEXT,
    notification_date_time TIMESTAMP
);

ALTER TABLE pet ADD COLUMN animal_shelter_id BIGINT REFERENCES animal_shelter(id);

