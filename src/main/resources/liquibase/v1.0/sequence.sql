--liquibase formatted sql
--

create sequence teams_seq
    start with 1
    increment by 1
    cache 20