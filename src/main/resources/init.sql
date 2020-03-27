create table feed_history
(
    id         bigint auto_increment
        primary key,
    content    text         not null,
    createtime datetime(6)  null,
    name       varchar(255) null
);

create table feed_refresh
(
    id         bigint auto_increment
        primary key,
    createtime datetime(6)  null,
    freshtime  datetime(6)  null,
    name       varchar(255) null
);

create table hibernate_sequence
(
    next_val bigint null
);

