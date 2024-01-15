create sequence task_sequence;

alter sequence task_sequence owner to postgres;

create sequence user_sequence;

alter sequence user_sequence owner to postgres;

create table usert
(
    id         bigint    not null
        primary key,
    created_at timestamp not null,
    name       text      not null
        constraint user_name_unique
            unique,
    password       text      not null
);

alter table usert
    owner to postgres;

create table task
(
    id                bigint                not null
        primary key,
    name       text   not null,
    description       text   not null,
    created_date      timestamp             not null,
    due_date          timestamp             not null,
    
    user_id           bigint                not null
        constraint task_user_fk
            references usert
);

alter table task
    owner to postgres;

insert into usert values (nextval('user_sequence'), CURRENT_TIMESTAMP, 'tester', 'password');
insert into task values (nextval('task_sequence'), 'test_task', 'this is a test', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1 );