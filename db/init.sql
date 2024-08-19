create database simple_bank;

create user simple with encrypted password 'simple';
grant all privileges on database simple_bank to simple;

