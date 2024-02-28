--liquibase formatted sql

--changeset alex:1
CREATE TABLE IF NOT EXISTS users
(
    id             BIGSERIAL PRIMARY KEY,
    username       VARCHAR(256)  NOT NULL UNIQUE,
    email          VARCHAR(512)  NOT NULL UNIQUE,
    password       VARCHAR(1024) NOT NULL,
    status         VARCHAR(64)   NOT NULL,
    network_status VARCHAR(64)   NOT NULL,
    isVerified     BOOLEAN DEFAULT false
);

--changeset alex:2
CREATE TABLE IF NOT EXISTS country
(
    id          BIGSERIAL PRIMARY KEY,
    name        VARCHAR(64)   NOT NULL UNIQUE,
    description VARCHAR(2048) NOT NULL
);

--changeset alex:3
CREATE TABLE IF NOT EXISTS city
(
    id          BIGSERIAL PRIMARY KEY,
    name        VARCHAR(128) NOT NULL,
    description VARCHAR(2048)
);

--changeset alex:4
CREATE TABLE IF NOT EXISTS street
(
    id   BIGSERIAL PRIMARY KEY,
    type VARCHAR(64)  NOT NULL,
    name VARCHAR(512) NOT NULL
);

--changeset alex:5
CREATE TABLE IF NOT EXISTS address
(
    id              BIGSERIAL PRIMARY KEY,
    building_number SMALLINT NOT NULL,
    room_number     SMALLINT,
    country_id      BIGINT REFERENCES country (id),
    city_id         BIGINT REFERENCES city (id),
    street_id       BIGINT REFERENCES street (id)
);

--changeset alex:6
CREATE TABLE IF NOT EXISTS place
(
    id          BIGSERIAL PRIMARY KEY,
    name        VARCHAR(128)  NOT NULL,
    category    VARCHAR(128)  NOT NULL,
    description VARCHAR(2048) NOT NULL,
    address_id  BIGINT REFERENCES address (id)
);

--changeset alex:7
CREATE TABLE IF NOT EXISTS event
(
    id          BIGSERIAL PRIMARY KEY,
    name        VARCHAR(128)             NOT NULL,
    event_time  TIMESTAMP WITH TIME ZONE NOT NULL,
    description VARCHAR(2048)            NOT NULL,
    user_id     BIGINT REFERENCES users (id),
    address_id  BIGINT REFERENCES address (id)
);

--changeset alex:8
CREATE TABLE IF NOT EXISTS chat
(
    id           BIGSERIAL PRIMARY KEY,
    status       VARCHAR(64) NOT NULL,
    sender_id    BIGINT REFERENCES users (id),
    recipient_id BIGINT REFERENCES users (id)
);

--changeset alex:9
CREATE TABLE IF NOT EXISTS chat_message
(
    id        BIGSERIAL PRIMARY KEY,
    content   VARCHAR(5048),
    status    VARCHAR(64) NOT NULL,
    send_time TIMESTAMP WITH TIME ZONE,
    chat_id   BIGINT REFERENCES chat (id),
    user_id   BIGINT REFERENCES users (id)
);

