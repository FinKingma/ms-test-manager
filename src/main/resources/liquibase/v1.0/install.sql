--liquibase formatted sql
--

CREATE TABLE teams
(
    id bigint NOT NULL,
    name VARCHAR(256) NOT NULL,
    vision VARCHAR(256) NOT NULL,
    morale NUMERIC(3,2) NOT NULL,
    happiness NUMERIC(3,2) NOT NULL,
    number_of_updates int NOT NULL,
    created_on datetime NOT NULL,
    last_updated_on datetime NOT NULL,
    primary key (id)
)