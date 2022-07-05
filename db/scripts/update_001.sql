drop table if exists items;
CREATE TABLE if not exists items
(
    id   SERIAL PRIMARY KEY,
    name TEXT NOT NULL,
    description TEXT,
    created timestamp
);