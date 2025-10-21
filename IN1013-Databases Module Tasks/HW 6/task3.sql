-- 1

SELECT first_name, surname FROM restStaff P INNER JOIN restBill MP on P.staff_no = MP.waiter_no WHERE MP.cust_name = 'Tanya Singh';
                     
-- 2

SELECT room_date FROM restRoom_management P INNER JOIN restStaff MP on P.headwaiter = MP.staff_no WHERE P.room_date >= 160201 AND P.room_date <= 160229 AND P.room_name = 'Green' AND MP.first_name = 'Charles';

-- 3

SELECT first_name, surname FROM restStaff WHERE headwaiter = (SELECT headwaiter FROM restStaff WHERE first_name = 'Zoe' AND surname = 'Ball');

-- 4

SELECT restBill.cust_name, restBill.bill_total, restStaff.first_name, restStaff.surname FROM restBill INNER JOIN restStaff on restBill.waiter_no = restStaff.staff_no ORDER BY restBill.bill_total DESC;

-- 5

SELECT P.first_name, P.surname FROM restStaff P WHERE P.headwaiter IN (SELECT MP.headwaiter FROM restStaff MP INNER JOIN restBill MPP ON MP.staff_no = MPP.waiter_no WHERE MPP.bill_no IN (00014, 00017));

-- 6

SELECT first_name, surname FROM restStaff P INNER JOIN restRoom_management MP on P.staff_no = MP.headwaiter WHERE room_name = 'Blue' AND room_date = 160312;




