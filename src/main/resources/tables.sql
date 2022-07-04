DROP TABLE IF EXISTS users;

create TABLE users (
    id Binary(255) NOT NULL,
    username VARCHAR (45) NOT NULL,
    password VARCHAR (60) NOT NULL,
    scope VARCHAR (10) NOT NULL,

    PRIMARY KEY (id),
    CONSTRAINT uc_user_email UNIQUE (username)
);

create TABLE clients (
    id VARCHAR(60) NOT NULL,
    name VARCHAR (30) NOT NULL,
    secret VARCHAR(60) NOT NULL,

    PRIMARY KEY (id)
)
insert into users (id, username, password, scope) values ('ajsfhlsahlakrjg', 'user@user.com', '12345', 'READ');

select * from users;
select * from clients;

DELETE FROM users;
DELETE FROM clients;

