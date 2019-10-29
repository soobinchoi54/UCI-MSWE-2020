-- Murachs MySQL Learning Outcomes 
-- Module 2 : S1 Ch3, Ch4, Ch5 

/* How to use the SELECT command to query specific columns, create column name aliases, code arithmetic expressions */
/* How to use the WHERE clause to specify conditions such as conditional operations, and logical operators, such as AND, OR, NOT, IN, BETWEEN, and LIKE */

-- SELECT statement that retrieves all data from a table 
SELECT * FROM invoices;
-- SELECT statement that retrieves three columns from each row, sorted in descending sequence by invoice total
SELECT 		invoice_number, invoice_date, invoice_total
FROM 		invoices
ORDER BY 	invoice_total DESC;
-- SELECT statement that retrieves two columns and a calculated value for a specific invoice
SELECT 	invoice_id, invoice_total
		credit_total + payment_total AS total_credits
FROM 	invoices
WHERE 	invoice_id = 17;
-- SELECT statement that retrieves all invoices between given dates
SELECT 		invoice_number, invoice_date, invoice_total
FROM 		invoices
WHERE 		invoice_date BETWEEN '2014-06-01' AND '2014-06-30'
ORDER BY 	invoice_date
-- SELECT statement that returns an empty result set
SELECT 	invoice_number, invoice_date, invoice_total
FROM 	invoices
WHERE 	invoice_total > 50000;
-- SELECT statement that returns full name
SELECT 	CONCAT(first_name, " ", last_name) AS full_name;
-- SELECT statement that renames the columns in the result set 
SELECT 	invoice_number AS "Invoice Number", invoice_date AS Date
FROM 	invoices;
-- Parentheses to control the sequence of arithmetic operations
SELECT 	invoice_id,
		invoice_id + 7 * 3 AS multiply_first,
		(invoice_id + 7) * 3 AS add_first
FROM 	invoices;
-- DIV and Modulo operators
SELECT 	invoice_id,
		invoice_id / 3 AS decimal_quotient,
		invoice_id DIV 3 AS integer_quotient,
		invoice_id % 3 AS remainder
FROM 	invoices; 
-- WHERE clause to retrieve vendors located in...
WHERE 	vendor_state = 'IA'
WHERE 	vendor_state = 'NJ' AND vendor_city = 'Pittsburg'
WHERE 
-- WHERE clause to retrieve invoices with a balance due 
WHERE 	invoice_total - payment_total - credit_total > 0
WHERE 	invoice_total > payment_total + credit_total
-- WHERE clause to retrieve vendors with names from A to L
WHERE 	vendor_name < 'M'
-- WHERE clause to retrieve invoices on or before and on or after a specified date
WHERE 	invoice_date <= '2014-07-31'
WHERE 	invoice_date >='2014-07-31'
WHERE 	invoice_dte BETWEEN '2014-07-31' AND '2014-08-02'
-- WHERE clause with In phrase with...
WHERE 	terms_id IN (1, 3, 4) -- with a list of numeric literals
WHERE 	vendor_state NOT IN ('CA', 'NV', 'OR') -- preceded by NOT
WHERE 	vendor_id IN -- with a subquery
		(SELECT vendor_id
		FROM invoices
		WHERE invoice_date = '2014-07-18')
-- WHERE clause with a LIKE phrase 
WHERE 	match_expression LIKE pattern
WHERE 	vendor_city LIKE 'SAN%' -- pattern contains San
WHERE 	vendor_city LIKE 'COMPU_ER%' -- pattern contains Compu er
-- WHERE clause with IS NULL clause
WHERE 	expression IS NULL
-- SELECT statement that retrieves rows with zero values
SELECT * FROM 	mull_sample
WHERE 			invoice_total = 0
-- SELECT statement that retrieves rows with non-zero values
SELECT * FROM 	null_sample
WHERE			invoice_total <> 0

/* How to order data using the ORDER BY clause */
-- ORDER BY clause that sorts by one column in ascending sequence
SELECT 	vendor_name,
	CONCAT (vendor_city, ', ', vendor_state, ' ', vendor_zip_code) AS address
FROM vendors 
ORDER BY vendor_name
-- ORDER BY clause that sorts by one column in descending sequence 
ORDER BY vendor_name DESC
-- ORDER BY clause that sorts by three columns
ORDER BY vendor_state, vendor_city, vendor_name
-- ORDER BY clause that uses an alias
ORDER BY address, vendor_name
-- ORDER BY clause that uses an expression
ORDER BY CONCAT(vendor_contact_last_name, vendor_contact_first_name)
-- ORDER BY clause that uses column positions
ORDER BY 2, 1

/* How to limit query results using the LIMIT clause */
-- SELECT statement with a LIMIT clause that starts with the first row
SELECT 		vendor_id, invoice_total
FROM 		invoices
ORDER BY 	invoice_total DESC
LIMIT 		5 -- row count from the start
-- SELECT statement with a LIMIT clause that starts with the third row
LIMIT 		2, 3 -- offset, row count
-- SELECT statement with a LIMIT clause that starts with the 101st row
LIMIT 		100, 1000 -- offset, row count

/* How to code and use inner joins, and when to choose them */
-- A join combines columns from two or more tables into a result set based on the join conditions you specify.
-- INNER JOIN of two tables: only those rows that satisfy the join condition are included in the result set 
SELECT 		invoice_number, vendor_name
FROM 		vendors INNER JOIN invoices -- INNER keyword is optional and is seldom used
		ON 	vendors.vendor_id = invoices.vendor_id
ORDER BY 	invoice_number
-- Tables are typically joined on the relationship between the primary key in one table and a foreign key in the other table
-- however, you can also join tables based on relationships not defined in the database - called ad hoc relationships
-- Aliases for all tables
SELECT 		invoice_number, vendor_name, invoice_due_date, invoice_total - payment_total - credit_total AS balance_due
FROM 		vendors v JOIN invoices i 
		ON 	v.vendors_id = i.vendors_id
WHERE 		invoice_total - payment_total - credit_total > 0
ORDER BY 	invoice_due_date DESC
-- Aliases for one table
SELECT 		invoice_number, line_item_amount, line_item_description
FROM 		invoices JOIN invoice_line_items li 
		ON 	invoices.invoice_id = li.invoice_id
WHERE 		account_number - 540
ORDER BY 	invoice_date
-- Joing a table in another database
SELECT 		vendor_name, customer_last_name, customer_first_name, vendor_state AS state, vendor_city AS city 
FROM 		vendors v JOIN om.customers c 
		ON 	v.vendors_zip_code = c.customer_zip
ORDER BY 	state, city 
-- INNER JOIN with two conditions
SELECT 	customer_first_name, customer_last_name
FROM 	customers c JOIN employees e 
	ON 	c.customer_first_name = e.first_name
	AND c.customer_last_name = e.last_name
-- SELF JOIN that returns vendors from cities in common with other vendors
-- A self join is a join that joins a table with itself, you must use aliases for the tables, and you must qualify each column name with alias 
SELECT DISTINCT v1.vendor_name, v1.vendor_city, v1.vendor_state
FROM 			vendors v1 JOIN vendors v2
			ON 	v1.vendor_city = v2.vendor_city
			AND v1.vendor_state = v2.vendor_state
			AND v1.vendor_name <> v2.vendor_name
ORDER BY 		v1.vendor_state, v1.vendor_city
-- Implicit syntax for an inner join using WHERE: in this case, you list the tables in the FROM clause separated by commas
SELECT 		invoice_number, vendor_name
FROM 		vendors v, invoices i
WHERE 		v.vendor_id = i.vendor_id
ORDER BY 	invoice_number

/* How to code and use outer joins, and when to choose them */
-- An outer join retrieves all rows that satisfy the join condition, plus unmatched rows in the left or right table
-- You can use outer joins to join multiple tables 
-- LEFT outer join
SELECT 		vendor_name, invoice_number, invoice_total
FROM 		vendors LEFT JOIN invoices
		ON 	vendors.vendor_id = invoices.vendor_id
ORDER BY 	vendor_name
-- RIGHT outer join
SELECT 		department_name, e.department_number, last_name
FROM 		departments d 
	RIGHT JOIN employees e
			ON d.department_number = e.department_number
ORDER BY department_name
-- Combine an outer and an inner join
SELECT 		department_name, last_name, project_number
FROM 		departments d
	JOIN 		employees e
	ON 			d.department_number = e.department_number
	LEFT JOIN 	projects p 
	ON 			e.employee_id = p.employee_id
ORDER BY 	department_name, last_name
-- USING keyword to join two tables 
SELECT 		invoice_number, vendor_name
FROM 		vendors
	JOIN 	invoices USING (vendor_id)
ORDER BY 	invoice_number 
/* How to code and use unions */
-- Like a join, a union cmobines data from two or more tables.
-- Instead of combining columns from base tables, a union combines rows from two or more result sets 
-- Result returns the same number of columns, and corresponding columns in each result set must have compatible data types 
-- By default eliminates duplicate rows, to include duplicates code ALL keyword 
-- UNION that combines result sets from two different tables 
SELECT 		'Active' AS source, invoice_number, invoice_date, invoice_total
FROM 		active_invoices
WHERE 		invoice_date >= '2014-06-01'
	UNION
SELECT 		'Paid' AS source, invoice_number, invoice_date, invoice_total
FROM 		paid_invoices
WHERE 		invoice_date >= '2014-06-01'
ORDER BY 	invoice_total DESC 
-- UNION that simulates a full outer join
SELECT 		department_name AS dept_name, d.department_number AS d_dept_no, e.department_number AS e_dept_no, last_name 
FROM 		departments d 
	LEFT JOIN 	employees e
	ON 			d.deparment_number = e.department_number
UNION
SELECT 		department_name AS dept_name, d.department_number AS d_dept_no, e.department_number AS e_dept_no, last_name
FROM 		departments d
	RIGHT JOIN 	employees e 
	ON 			d.deparment_number = e.department_number
ORDER BY 	dept_name 
/* How to create new tables */
-- Create a complete copy of the invoices table
CREATE TABLE 	invoice_copy AS 
SELECT *
FROM 			invoices 
-- Create a partial copy
CREATE TABLE 	invoice_copy AS 
SELECT *
FROM 			invoices 
WHERE 			invoice_total - payment_total - credit_total = 0
-- Create a table with summary rows from the invoices table
CREATE TABLE 	vendor_balances AS 
SELECT 			vendor_id, SUM(invoice_total) AS sum_of_invoices
FROM 			invoices 
WHERE 			(invoice_total - payment_total - credit_total) <> 0
GROUP BY 		vendor_id 
-- Delete a table
DROP TABLE 		old_invoices

/* How to insert new rows */
-- INSERT without using a column list
INSERT INTO invoices VALUES
	(115, 97, DEFAULT, 9, 0, NULL)
-- Multiple rows 
	(116, 29, DEFAULT, 4, 0, NULL)
-- INSERT using a column list 
INSERT INTO invoices 
	vendor_id, invoice_number, invoice_total)
VALUES
	(97, '232324', 282984)
-- INSERT using subquery: subquery is a SELECT statement that's coded within another SQL statement 
INSERT INTO 	invoice_archive
SELECT *
FROM 			invoices
WHERE 			invoice_total - pyament_total - credit_total = 0

/* How to update existing rows */
-- UPDATE two columns for a single row 
UPDATE 	invoices
SET 	payment_date = '2014-09-21', payment_total = 194729.13
WHERE 	invoice_number = '93/522'
-- UPDATE one column for multiple rows
UPDATE 	invoices 
SET 	term_id = 1
WHERE 	vendor_id = 95
-- UPDATE all invoices for a vendor 
UPDATE 	invoices
SET 	terms_id = 1
WHERE 	vendor_id = 
		(SELECT vendor_id
		FROM vendors
		WHERE vendor_name = 'Pacific Bell')

/* How to delete rows */
-- DELETE rows
DELETE FROM general_ledger_accounts
WHERE 		account_number = 42
-- DELETE multiple rows
DELETE FROM invoice_line_items 
WHERE 		invoice_id = 12 
-- DELETE one row using a compound condition
DELETE FROM invoice_line_items
WHERE 		invoice_id = 78 AND invoice_sequence = 2
-- Use subquery in a DELETE statement 
DELETE FROM invoice_line_items
WHERE 		invoice_id IN
			(SELECT invoice_id
			FROM invoices
			WHERE vendor_id = 114)













