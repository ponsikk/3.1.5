create table users.user_roles
(
    user_id bigint       not null,
    roles   varchar(255) null,
    constraint FKhfh9dx7w3ubf1co1vdev94g3f
        foreign key (user_id) references users.users (id)
);

