-- auto-generated definition
create table tracks
(
    id                 int auto_increment primary key,
    tag_artist         varchar(255) null,
    tag_title          varchar(255) null,
    tag_date           varchar(255) null,
    name               varchar(255) not null,
    duration           int null,
    formatted_duration varchar(10) null,
    format             varchar(10) null,
    path               varchar(255) not null,
    step_seconds       tinyint null,
    user_id            int          not null,
    created_at         datetime default CURRENT_TIMESTAMP not null,
    updated_at          datetime null,
    deleted_at         datetime null
);

-- auto-generated definition
create table track_map
(
    id         int auto_increment
        primary key,
    track_id   int not null,
    step       tinyint null,
    range_from int not null,
    range_to   int not null,
    created_at datetime default CURRENT_TIMESTAMP not null,
    updated_at datetime null,
    deleted_at  datetime null
);


