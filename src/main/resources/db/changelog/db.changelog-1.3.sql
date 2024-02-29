--liquibase formatted sql

--changeset alex:1
ALTER TABLE users
    ADD COLUMN role VARCHAR(64);