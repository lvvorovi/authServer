DROP TABLE IF EXISTS users;

create TABLE users (
    id VARCHAR(60) NOT NULL,
    username VARCHAR (45) NOT NULL,
    password VARCHAR (60) NOT NULL,
    scope VARCHAR (10) NOT NULL,

    PRIMARY KEY (id),
    CONSTRAINT uc_user_email UNIQUE (username)
);

create TABLE clients (
    id VARCHAR(60) NOT NULL,
    client_id VARCHAR(60) NOT NULL,
    client_name VARCHAR (30) NOT NULL,
    client_secret VARCHAR(60) NOT NULL,
    redirect_uri VARCHAR(120) NOT NULL,
    client_scope VARCHAR(30) NOT NULL,
    authentication_method VARBINARY(264) NOT NULL,
    authorization_grant_type VARBINARY(264) NOT NULL,

    PRIMARY KEY (id),
    CONSTRAINT uc_clients_clientId UNIQUE (client_id)
);

DROP TABLE clients;

select * from users;
select * from clients;

delete from users;
delete from clients;

update users set scope = 'READ';
UPDATE clients SET redirect_uri = 'http://127.0.0.1:3000/authorized'