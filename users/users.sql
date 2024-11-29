create table users.users
(
    id        bigint auto_increment
        primary key,
    email     varchar(255) null,
    name      varchar(255) null,
    last_name varchar(255) null,
    username  varchar(255) not null,
    password  varchar(255) not null
)
    charset = utf8mb4;

