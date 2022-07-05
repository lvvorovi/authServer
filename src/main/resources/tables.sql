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
    name VARCHAR (30) NOT NULL,
    secret VARCHAR(60) NOT NULL,

    PRIMARY KEY (id)
);

ALTER TABLE users MODIFY id VARCHAR(60)  NOT NULL;

CREATE TABLE oauth_access_token (
    token_id VARCHAR(60),
    token VARCHAR(60),
    authentication_id  VARCHAR(60),
    user_name  VARCHAR(60),
    client_id  VARCHAR(60),
    authentication  VARCHAR(60),
    refresh_token  VARCHAR(60),

    PRIMARY KEY(token_id)
);

DROP TABLE oauth_access_token;

CREATE TABLE oauth_refresh_token (
    token_id VARCHAR(60),
    token VARCHAR(60),
    authentication VARCHAR(60),

    PRIMARY KEY(token_id)
);

DROP TABLE oauth_refresh_token;








insert into users (id, username, password, scope) values ('ajsfhlsahlakrjg', 'user@user.com', '12345', 'READ');

INSERT INTO USERS;

select * from users;
select * from clients;

DELETE FROM users;
DELETE FROM clients;

