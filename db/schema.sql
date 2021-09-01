CREATE TABLE post (
    id SERIAL PRIMARY KEY,
    name TEXT
);

CREATE TABLE candidate (
    id SERIAL PRIMARY KEY,
    name TEXT,
    city_id int REFERENCES city(id)
);

CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    name TEXT,
    email TEXT unique,
    password TEXT
);

CREATE TABLE city (
    id SERIAL PRIMARY KEY,
    name TEXT
);

INSERT INTO city (name) VALUES ('Москва');
INSERT INTO city (name) VALUES ('Санкт-Петербург');
INSERT INTO city (name) VALUES ('Владивосток');
