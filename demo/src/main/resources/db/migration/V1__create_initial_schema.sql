CREATE SEQUENCE asteroids_seq
    START WITH 1
    INCREMENT BY 50;

CREATE SEQUENCE comets_seq
    START WITH 1
    INCREMENT BY 50;

CREATE TABLE asteroids (
    id BIGINT NOT NULL,
    distance_from_earth DOUBLE PRECISION,
    official_name VARCHAR(255),
    nasa_id VARCHAR(255),
    potentially_hazardous BOOLEAN NOT NULL,
    risk_score INTEGER NOT NULL,

    CONSTRAINT asteroids_pkey PRIMARY KEY (id),
    CONSTRAINT asteroids_nasa_id_unique UNIQUE (nasa_id)
);

CREATE TABLE comets (
    id BIGINT NOT NULL,
    distance_from_earth DOUBLE PRECISION,
    official_name VARCHAR(255),
    tail_length VARCHAR(255),

    CONSTRAINT comets_pkey PRIMARY KEY (id)
);