DROP TABLE IF EXISTS adns;

CREATE TABLE adns
(
    id        serial PRIMARY KEY,
    dna       text NOT NULL,
    isMutant  boolean      NOT NULL,
    createdAt TIMESTAMP NULL
);
