select * from feed;
select* from notifications;

Select * from feedgroup;

select * from feedconfig;

SELECT g.group_name AS group_name, f.name AS feed_name
FROM feedconfig c
JOIN feed f ON c.feed_id = f.feed_id
JOIN `feedgroup` g ON c.group_id = g.group_id
WHERE c.group_id = 4;


DELIMITER //

CREATE PROCEDURE GetGroupAndFeedNames(IN GroupId INT)
BEGIN
    SELECT g.group_name AS group_name, f.name AS feed_name
    FROM feedconfig c
    JOIN feed f ON c.feed_id = f.feed_id
    JOIN feedgroup g ON c.group_id = g.group_id
    WHERE c.group_id = GroupId;
END //

DELIMITER ;


CALL GetGroupAndFeedNames(4);
