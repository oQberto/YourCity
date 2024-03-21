--liquibase formatted sql

--changeset alex:1
ALTER TABLE event
    ALTER COLUMN event_time TYPE TIMESTAMP;