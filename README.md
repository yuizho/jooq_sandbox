# README
## create tables
```sql
USE test;

CREATE TABLE division
(id INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
name VARCHAR(30) NOT NULL,
PRIMARY KEY(id)
);

CREATE TABLE product(
	id INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
	division INT(10) UNSIGNED NOT NULL,
	created DATETIME NOT NULL,
	name VARCHAR(20) NOT NULL,
	PRIMARY KEY(id),
	UNIQUE KEY uk_product (division, created),
	CONSTRAINT fk_division
		FOREIGN KEY(division)
		REFERENCES division (id)
		ON DELETE NO ACTION
   ON UPDATE NO ACTION);
   
 INSERT INTO division (name) VALUES 
 ('item')
 ;
```