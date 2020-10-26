--liquibase formatted sql
--

CREATE TABLE teams
(
    id bigint NOT NULL,
    name VARCHAR(256) NOT NULL,
    vision VARCHAR(256) NOT NULL
)