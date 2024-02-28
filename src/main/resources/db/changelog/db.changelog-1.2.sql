--liquibase formatted sql

--changeset alex:1
CREATE TABLE IF NOT EXISTS user_event
(
    user_id  BIGINT REFERENCES users (id),
    event_id BIGINT REFERENCES event (id),
    PRIMARY KEY (user_id, event_id)
);

--changeset alex:2
CREATE TABLE IF NOT EXISTS user_chat
(
    user_id BIGINT REFERENCES users (id),
    chat_id BIGINT REFERENCES chat (id),
    PRIMARY KEY (user_id, chat_id)
);

--changeset alex:3
ALTER TABLE users
    DROP COLUMN event_id;

--changeset alex:4
ALTER TABLE chat
    ADD COLUMN owner_id BIGINT REFERENCES users (id);

--changeset alex:5
ALTER TABLE chat
    DROP COLUMN sender_id;

--changeset alex:6
ALTER TABLE chat
    DROP COLUMN recipient_id;