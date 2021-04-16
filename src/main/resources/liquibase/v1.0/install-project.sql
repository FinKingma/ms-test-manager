--liquibase formatted sql
--changeset createProject:1

CREATE TABLE project
(
    id bigint NOT NULL,
    name VARCHAR(256) NOT NULL,
    primary key (id)
);

--rollback drop table project