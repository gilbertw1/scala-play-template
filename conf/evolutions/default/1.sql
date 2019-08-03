# -- !Ups

CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE app_user
(
    id UUID PRIMARY KEY NOT NULL,
    username TEXT NOT NULL,
    first_name TEXT NOT NULL,
    last_name TEXT NOT NULL,
    created TIMESTAMP DEFAULT current_timestamp NOT NULL
);

CREATE INDEX app_user_username_idx ON app_user(username);

# -- !Downs

DROP TABLE app_user
