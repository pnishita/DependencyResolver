select * from feed;

select* from notifications;

Select * from feedgroup;

select * from feedconfig;

truncate table notifications;

alter table notifications drop column received_date;

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

select* from notifications
order by cob desc;
desc notifications;

update Notifications 
SET Created_Date = sysdate() where id = 1;

ALTER TABLE notifications
ALTER COLUMN updated_by SET DEFAULT 'SYSTEM';

ALTER TABLE notifications
MODIFY Updated_Date DATETIME DEFAULT CURRENT_TIMESTAMP;

drop table latestnotification;


 select * from notifications where cob= '2024-10-02' and feed_id = 1 order by Created_Date desc limit 1;



CREATE TABLE latestNotification (
    id INT AUTO_INCREMENT PRIMARY KEY,  
    notification_id INT NOT NULL,       
    feed_id INT NOT NULL,                
    msg VARCHAR(255),                   
    UNIQUE KEY unique_feed (feed_id)    
);


INSERT INTO latestNotification (notification_id, feed_id, msg)
SELECT id, feed_id, message
FROM notifications
WHERE cob = '2024-10-03'
  AND feed_id = 1
ORDER BY Created_Date DESC
LIMIT 1;

select * from latestnotification;
select * from notifications;
    
    
INSERT INTO latestNotification (notification_id, feed_id, msg)
SELECT id, feed_id, message
FROM (
    SELECT id, feed_id, message
    FROM notifications
    WHERE cob = '2024-10-03' AND feed_id = 3
    ORDER BY Created_Date DESC
    LIMIT 1
) AS new_val
ON DUPLICATE KEY UPDATE
    notification_id = new_val.id,
    msg = new_val.message;    
select * from latestnotification;


ALTER TABLE latestNotification 
drop COLUMN message;