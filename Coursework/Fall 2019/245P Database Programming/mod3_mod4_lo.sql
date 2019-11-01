-- Murachs MySQL Learning Outcomes 
-- Module 3 & 4: Ch 6, 7, 10, 11

/* How to design a database schema */
-- A relational database system should model the real-world environment where it's used
-- The job of the designer is to analyze the real-world system and then map it onto a relational database system 
-- A table in a relational database typically represents an object, or entity, in the real world
-- Each column of a table is used to store an attribute associated with the entity, and each row represents one instance of the entity
-- Use a technique called entity-relationship ER modeling
-- If a database element contains two or more components, you should consider subdividing element into those components 
-- Group subidvided data elemtns by the entities with which they're associated
-- Each table should have a primary key that uniquely identifies each row
-- If two tables have a one-to-many relationship, you may need to add a foreign key column to the table on the "many" side 
-- If two tables have a many-to-many relationship, you'll need to define a linking table to relate them 
-- If two tables have a one-to-one relationship, they should be related by their primary keys 
-- Referential integrity means that the relationship between tables are maintained correctly
	-- Use declarative referential integrity (DRI)
-- To use DRI, you define foreign key constraints

/* What does "normalization" mean, what are its forms, and how to apply those normalizations */
-- Normalization is a formal process you can use to separate the data in a data structure into related tables
	-- reduces data redundancy, which can cause storage and maintenance problems 
-- In a normalized data structure, each table contains information about a single entity, and each piece of information is stored in exactly on place
-- In an unnormalized data structure, a table can contain information about two or more entities, repeating columns, columns, that contain repeating values, and data that's repeated in two or more rows 
-- To normalize a data structure, you apply the normal forms in sequence 
-- Each normal form assumes that the design is already in the previous normal form 
-- A database is typically considered to be normalized if it is in third normal form 

/* How to read, understand, and create an Enhanced Entity-Relationship (EER) diagram */




/* How to impliement your database design inot a concrete schema */

/* How to use, create, and drop indexes */
-- CREATE DATABASE statement 
CREATE DATABASE ap 
-- CREATE DATABASE only if ap does not exist
CREATE DATABASE IF NOT EXISTS ap 
-- DROP DATABASE
DROP DATABASE ap 
DROP DATABASE IF EXISTS ap
-- SELECT DATABASE
USE ap 
-- CREATE TABLE 
CREATE TABLE vendors 
(
	vedors_id 		INT 		NOT NULL	UNIQUE AUTO_INCREMENT,
	vendor_name 	VARCHAR(50) NOT NULL 	UNIQUE
)
-- CREATE TABLE with table-level constraints: used to enforce integrity of the data in table by enforcing rules on them 
(
	vedors_id 		INT 		NOT NULL	UNIQUE AUTO_INCREMENT,
	vendor_name 	VARCHAR(50) NOT NULL 	UNIQUE,
	CONSTRAINT 	vendors_pk 		PRIMARY KEY (vendors_id),
	CONSTRAINT 	vendor_name_uq 	PRIMARY KEY (vendor_name)
)




/* The use and power of summary queries to summarize data and perform functions within the database system */

/* Using subqueries to extend the power and complexity of queries */





/* How to code aggregate functions, including the use of the GROUP BY and HAVING clauses */
--

/* How to code aggregate window functions, frames, and named windows */

/* How to code subqueries in the WHERE clause */
-- Get vendors without invoices
SELECT vendor_id, vendor_name, vendor_state
FROM vendors 
WHERE vendor_id NOT IN 
	(SELECT DISTINCT vendor_id
	FROM invoices)
ORDER BY vendor_id
/* How to code subqueries in other clauses, such as HAVING, SELECT, and FROM */
-- Get the most recent invoice date for each vendor - SELECT clause
SELECT vendor_name,
	(SELECT MAX(invoice_date) FROM invoices 
	WHERE vendor_id = vendors.vendor_id) AS latest_inv 
FROM vendors 
ORDER BY latest_inv DESC 
-- 