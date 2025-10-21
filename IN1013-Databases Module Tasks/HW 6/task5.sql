-- 1 

SELECT P.first_name, P.surname, MP.bill_date, COUNT(MP.bill_no) AS number_of_bills FROM restBill MP INNER JOIN restStaff P ON MP.waiter_no = P.staff_no GROUP BY P.first_name, P.surname, MP.bill_date HAVING COUNT(MP.bill_no) >= 2;

-- 2

SELECT room_name, COUNT(table_no) FROM restRest_table WHERE no_of_seats > 6 GROUP BY room_name;

-- 3 

SELECT P.room_name, SUM(MPP.bill_total) FROM restRoom_management P INNER JOIN restRest_table MP ON P.room_name = MP.room_name INNER JOIN restBill MPP ON MP.table_no = MPP.table_no GROUP BY P.room_name;

-- 4 

SELECT cust_name FROM restBill GROUP BY cust_name HAVING AVG(bill_total) > 400;

-- 6

SELECT P.first_name, P.surname, MP.bill_date, COUNT(MP.bill_no) AS number_of_bills FROM restBill MP INNER JOIN restStaff P ON MP.waiter_no = P.staff_no GROUP BY P.first_name, P.surname, MP.bill_date HAVING COUNT(MP.bill_no) >= 3;