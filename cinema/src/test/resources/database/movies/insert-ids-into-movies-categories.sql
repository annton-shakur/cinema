INSERT INTO movies_categories (movie_id, category_id)
SELECT movie_id, category_id FROM (
    SELECT
        (SELECT id FROM movies WHERE title = 'Inception') AS movie_id,
        1 AS category_id
    UNION ALL
    SELECT
        (SELECT id FROM movies WHERE title = 'Inception'),
        3
    UNION ALL
    SELECT
        (SELECT id FROM movies WHERE title = 'Jurassic Park'),
        1
    UNION ALL
    SELECT
        (SELECT id FROM movies WHERE title = 'Jurassic Park'),
        2
    UNION ALL
    SELECT
        (SELECT id FROM movies WHERE title = 'Pulp Fiction'),
        3
) AS temp
WHERE movie_id IS NOT NULL;
