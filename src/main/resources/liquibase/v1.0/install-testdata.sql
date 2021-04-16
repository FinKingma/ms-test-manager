--liquibase formatted sql
--changeset createTestData:2

CREATE TABLE testdata
(
    id bigint NOT NULL,
    testrun_id bigint NOT NULL,
    project_id bigint NOT NULL,
    testname VARCHAR(256) NOT NULL,
    result VARCHAR(256) NOT NULL,
    primary key (id),
    foreign key (project_id) references project(id)
)

--rollback drop table testdata;