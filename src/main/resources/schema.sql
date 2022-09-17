drop table if exists samples;

create table samples
(
    id      INT primary key AUTO_INCREMENT,
    message VARCHAR(255) not null
);
