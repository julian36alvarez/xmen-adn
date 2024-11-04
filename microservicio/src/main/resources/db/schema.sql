DROP TABLE IF EXISTS adns;
DROP TABLE IF EXISTS adn_statistics;
CREATE TABLE adns (
   id serial PRIMARY KEY,
   dna text NOT NULL,
   isMutant boolean NOT NULL,
   createdAt TIMESTAMP NULL
);

CREATE TABLE adn_statistics (
    id SERIAL PRIMARY KEY,
    count_mutant_dna INT NOT NULL DEFAULT 0,
    count_human_dna INT NOT NULL DEFAULT 0
);