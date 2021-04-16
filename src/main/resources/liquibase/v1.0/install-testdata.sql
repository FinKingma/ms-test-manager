--liquibase formatted sql
--changeset createTestData:2

CREATE TABLE testdata
(
    id bigint NOT NULL,
    testrunId bigint NOT NULL,
    projectId bigint NOT NULL,
    testname VARCHAR(256) NOT NULL,
    result VARCHAR(256) NOT NULL,
    primary key (id),
    foreign key (projectid) references project(id)
)

--rollback drop table testdata;