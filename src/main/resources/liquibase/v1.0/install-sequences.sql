--liquibase formatted sql
--changeset sequences:3

create sequence project_seq
    start with 1
    increment by 1
    cache 20;

create sequence testdata_seq
    start with 1
    increment by 1
    cache 20;

--rollback drop sequence project_seq
--rollback drop sequence testdata_seq