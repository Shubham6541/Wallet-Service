create table usertable
(id bigint generated by default as identity,
 user_name varchar(255) not null,
 password varchar(255),
 primary key (id));