DROP TABLE IF EXISTS users;

CREATE TABLE users (
    id VARCHAR(60) NOT NULL,
    username VARCHAR (45) NOT NULL,
    password VARCHAR (60) NOT NULL,
    scope VARCHAR (10) NOT NULL,

    PRIMARY KEY (id),
    CONSTRAINT uc_user_email UNIQUE (username)
);

CREATE TABLE clients (
    id VARCHAR(60) NOT NULL,
    name VARCHAR (30) NOT NULL,
    secret VARCHAR(60) NOT NULL,

    PRIMARY KEY (id)
);

select * from users;
select * from clients;

DELETE FROM users;
DELETE FROM clients;

UPDATE users SET scope = 'READ';