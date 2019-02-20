
-- Log into psql

create role todouser with login password 'todopass';

create database tododb;

grant all privileges on database tododb to todouser;

create table todo (id BIGINT, description varchar(64) NOT NULL, priority INT, done BOOLEAN, primary key(id));

grant all privileges on table todo to todouser;

ALTER TABLE todo OWNER TO todouser;
