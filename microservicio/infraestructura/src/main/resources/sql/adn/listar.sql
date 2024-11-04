SELECT
    COUNT(*) FILTER (WHERE ismutant = true) AS count_mutant_dna,
    COUNT(*) FILTER (WHERE ismutant = false) AS count_human_dna,
    COUNT(*) FILTER (WHERE ismutant = true) / NULLIF(COUNT(*), 0)::float AS ratio
FROM
    adns;