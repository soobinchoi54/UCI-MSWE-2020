USE ch11;

DROP TABLE IF EXISTS members_committees;
DROP TABLE IF EXISTS members;
DROP TABLE IF EXISTS committees;

CREATE TABLE members
(
member_id	INT		PRIMARY KEY		AUTO_INCREMENT,
first_name	VARCHAR(45)		NOT NULL,
last_name	VARCHAR(45)		NOT NULL,
address		VARCHAR(45)		NOT NULL,
city		VARCHAR(45)		NOT NULL,
state		CHAR(2)		NOT NULL,
phone		VARCHAR(20)		NOT NULL
);

CREATE TABLE committees
(
committee_id	INT		PRIMARY KEY		AUTO_INCREMENT,
committee_name	VARCHAR(45)		NOT NULL
);

CREATE TABLE members_committees
(
member_id		INT		NOT NULL,
committee_id	INT		NOT NULL,
CONSTRAINT members_committees_fk_members FOREIGN KEY (member_id) REFERENCES members(member_id),
CONSTRAINT members_committees_fk_committees FOREIGN KEY (committee_id) REFERENCES committees(committee_id)
);

