INSERT INTO movies_actors (movie_id, actor_id)
SELECT movie_id, actor_id FROM (
    SELECT 
        (SELECT id FROM movies WHERE title = 'Inception') AS movie_id, 
        1 AS actor_id
    UNION ALL
    SELECT 
        (SELECT id FROM movies WHERE title = 'Inception'), 
        5
    UNION ALL
    SELECT 
        (SELECT id FROM movies WHERE title = 'Jurassic Park'), 
        2
    UNION ALL
    SELECT 
        (SELECT id FROM movies WHERE title = 'Pulp Fiction'), 
        4
    UNION ALL
    SELECT 
        (SELECT id FROM movies WHERE title = 'Pulp Fiction'), 
        3
) AS temp
WHERE movie_id IS NOT NULL;
