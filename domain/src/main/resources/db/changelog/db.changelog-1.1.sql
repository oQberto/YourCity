--liquibase formatted sql

--changeset alex:1
ALTER TABLE users
    ADD COLUMN event_id BIGINT REFERENCES event(id);

--changeset alex:2
ALTER TABLE users
    RENAME COLUMN isverified TO is_verified;

--changeset alex:3
ALTER TABLE event
    RENAME COLUMN user_id TO owner_id