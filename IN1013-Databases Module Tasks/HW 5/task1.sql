-- 1

SELECT bill_date, bill_total FROM restBill WHERE cust_name = 'Bob Crow';

-- 2

SELECT DISTINCT cust_name FROM restBill  WHERE cust_name like '%Smith%' ORDER BY cust_name DESC;

-- 3

SELECT DISTINCT cust_name FROM restBill  WHERE cust_name like '% C%';

-- 4

SELECT first_name, surname FROM restStaff WHERE headwaiter > 0;

-- 5

SELECT bill_no, bill_date, bill_total FROM restBill WHERE bill_date like '%1602%';

-- 6

SELECT DISTINCT bill_date FROM restBill WHERE bill_date like '15%' AND bill_total > 0 ORDER BY bill_date;
