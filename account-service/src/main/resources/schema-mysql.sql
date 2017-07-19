drop table if exists accounts;

create table accounts (
  id int NOT NULL AUTO_INCREMENT,
  name varchar(30),
  surname varchar(30),
  PRIMARY KEY (ID)
);