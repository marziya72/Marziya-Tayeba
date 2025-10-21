-- 1

CREATE VIEW samsBills AS 
(SELECT first_name, surname, bill_date, cust_name, bill_total FROM restStaff P 
INNER JOIN restBill MP ON P.staff_no = MP.waiter_no WHERE first_name = 'Sam');

-- 2 

SELECT * FROM samsBills WHERE bill_total > 400;

-- 3 

CREATE VIEW roomTotals AS
(SELECT room_name, SUM(bill_total) AS total_sum
FROM restRest_table P
INNER JOIN restBill MP ON P.table_no = MP.table_no
GROUP BY P.room_name);

-- 4 

CREATE VIEW teamTotals AS
(SELECT CONCAT(MP.first_name, ' ', MP.surname) AS headwaiter_name, SUM(MPP.bill_total) AS total_sum
FROM restRoom_management P
INNER JOIN restStaff MP ON P.headwaiter = MP.staff_no
INNER JOIN restBill MPP ON P.room_name = (SELECT R.room_name FROM restRest_table R WHERE R.table_no = MPP.table_no)
GROUP BY P.headwaiter);