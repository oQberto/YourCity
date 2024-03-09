-- Test data for "country" table
INSERT INTO country (id, name, description)
VALUES (1, 'USA', 'United States of America'),
       (2, 'UK', 'United Kingdom'),
       (3, 'FR', 'France');

-- Test data for "city" table
INSERT INTO city (id, name, description)
VALUES (1, 'New York', 'The largest city in the United States'),
       (2, 'London', 'Capital city of England'),
       (3, 'Paris', 'Capital city of France');

-- Test data for "street" table
INSERT INTO street (id, type, name)
VALUES (1, 'HIGHWAY', 'Broadway'),
       (2, 'LIVING_STREET', 'Oxford Street'),
       (3, 'BOULEVARD', 'Champs-Élysées');

-- Test data for "address" table
INSERT INTO address (id, building_number, room_number, country_id, city_id, street_id)
SELECT generate_series,
       generate_series * 100,
       CASE
           WHEN generate_series % 3 = 0
               THEN generate_series * 10
           END,
       generate_series % 3 + 1,
       generate_series % 3 + 1,
       generate_series % 3 + 1
FROM generate_series(1, 30);

-- Test data for "users" table
INSERT INTO users (id, username, email, password, status, network_status, is_verified, role)
SELECT generate_series,
       'user' || generate_series,
       'user' || generate_series || '@example.com',
       'password' || generate_series,
       'active',
       CASE
           WHEN generate_series % 2 = 0
               THEN 'online'
           ELSE 'offline' END,
       generate_series % 2 = 0,
       CASE
           WHEN generate_series % 3 = 0
               THEN 'admin'
           ELSE 'user' END
FROM generate_series(1, 30);

-- Test data for "event" table
INSERT INTO event (id, name, event_time, description, owner_id, address_id)
SELECT generate_series,
       'Event' || generate_series,
       current_timestamp + INTERVAL '1' DAY * (generate_series % 3 + 1),
       'Description of Event' || generate_series,
       generate_series % 3 + 1,
       generate_series % 3 + 1
FROM generate_series(1, 90);

-- Test data for "place" table
INSERT INTO place (id, name, category, description, address_id)
SELECT generate_series,
       'Place' || generate_series,
       CASE
           WHEN generate_series % 3 = 0
               THEN 'CINEMA'
           WHEN generate_series % 3 = 1
               THEN 'CAFFE'
           ELSE 'RESTAURANT' END,
       'Description of Place' || generate_series,
       generate_series
FROM generate_series(1, 30);

-- Test data for "user_event" table
INSERT INTO user_event (user_id, event_id)
SELECT generate_series,
       floor(random() * 30) + 1
FROM generate_series(1, 30);

-- Test data for "chat" table
INSERT INTO chat (id, status, owner_id)
SELECT generate_series,
       CASE
           WHEN generate_series % 2 = 0
               THEN 'active'
           ELSE 'inactive' END,
       generate_series % 30 + 1
FROM generate_series(1, 30);

-- Test data for "user_chat" table
INSERT INTO user_chat (user_id, chat_id)
SELECT generate_series,
       floor(random() * 30) + 1
FROM generate_series(1, 30);

-- Test data for "chat_message" table
INSERT INTO chat_message (id, content, status, send_time, chat_id, user_id)
SELECT generate_series,
       'Message ' || generate_series,
       CASE
           WHEN generate_series % 2 = 0
               THEN 'sent'
           ELSE 'received' END,
       now() + INTERVAL '1' HOUR * generate_series,
       generate_series % 30 + 1,
       generate_series % 30 + 1
FROM generate_series(1, 30);
