create table if not exists users
(
    id       varchar(36) primary key,
    name     varchar(255),
    password varchar(255)
);

create table if not exists roles
(
    id   integer primary key,
    name varchar(50) unique
);

insert into roles
values (1, 'ADMIN'),
       (2, 'USER');

CREATE TABLE IF NOT EXISTS user_roles
(
    user_id varchar(36) not null,
    role_id integer     not null,
    constraint fk_user_roles_user foreign key (user_id) references users (id),
    constraint fk_user_roles_role foreign key (role_id) references roles (id)
);

insert into users(id, name, password) values ('aa97d885-335e-43dc-96a8-76305e87088d', 'admin', '$2a$10$gj1GAfxCjzpOnCTY6SoVOupDgo0N17ldskSfCPPhrzOwhVaLvo0e6'),
                                             ('bb97d885-335e-43dc-96a8-76305e87088d', 'user', '$2a$10$m2dWQ4WewWN7ZapCY7uTw.nkzPYG/YOKzVTwKTR0vNsHFF4P5I0YG');

insert into user_roles(user_id, role_id) values ('aa97d885-335e-43dc-96a8-76305e87088d', 1),
                                                ('bb97d885-335e-43dc-96a8-76305e87088d', 2);