create table if not exists articles
(
    id varchar(36) primary key,
    title varchar(255),
    payload text,
    created_at timestamp not null default current_timestamp,
    created_by varchar(36),
    constraint fk_articles_user foreign key (created_by) references users (id)
);