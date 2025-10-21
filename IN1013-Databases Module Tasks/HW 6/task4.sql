-- 1

SELECT DISTINCT cust_name FROM restBill P INNER JOIN restRest_table MP on P.table_no = MP.table_no INNER JOIN restRoom_management MPP on MP.room_name = MPP.room_name INNER JOIN restStaff MPPP on MPP.headwaiter = MPPP.staff_no WHERE P.bill_total > 450 AND MPPP.first_name = 'Charles';

-- 2 

SELECT DISTINCT first_name, surname FROM restStaff P INNER JOIN restRoom_management MP on P.staff_no = MP.headwaiter INNER JOIN restRest_table MPP on MP.room_name = MPP.room_name INNER JOIN restBill MPPP on MPP.table_no = MPPP.table_no WHERE MPPP.bill_date = 160111 AND MPPP.cust_name = 'Nerida Smith';

-- 3 

SELECT cust_name, bill_total FROM restBill WHERE bill_total = (SELECT MIN(bill_total) FROM restBill);

-- 4 

SELECT first_name, surname FROM restStaff WHERE staff_no NOT IN (SELECT waiter_no FROM restBill);

-- 5 

SELECT P.cust_name, MPPP.first_name, MPPP.surname, MP.room_name FROM restBill P INNER JOIN restRest_table MP ON P.table_no = MP.table_no INNER JOIN restRoom_management MPP ON MP.room_name = MPP.room_name AND P.bill_date = MPP.room_date INNER JOIN restStaff MPPP ON MPP.headwaiter = MPPP.staff_no WHERE P.bill_total = (SELECT MAX(bill_total) FROM restBill);







