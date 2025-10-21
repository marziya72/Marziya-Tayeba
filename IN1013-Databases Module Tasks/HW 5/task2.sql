-- 1

SELECT SUM(bill_total) AS Income FROM restBill;

-- 2

SELECT SUM(bill_total) AS Feb_Income FROM restBill WHERE bill_date like '16%';

-- 3

SELECT AVG(bill_total) FROM restBill;

-- 4

SELECT MIN(no_of_seats) AS Min_Seats, MAX(no_of_seats) AS Max_Seats, AVG(no_of_seats) AS Avg_Seats FROM restRest_table WHERE room_name = 'Blue';

-- 5

SELECT COUNT(DISTINCT table_no) AS Tables_Served FROM restBill WHERE waiter_no IN (004, 002);
