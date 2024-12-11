create table if not exists clients
(
    client_id           uuid primary key,
    first_name          varchar(30)         not null,
    last_name           varchar(30)         not null,
    middle_name         varchar(30),
    birth_date          date                not null,
    email               varchar(50)         unique not null,
    gender              varchar(30),
    marital_status      varchar(30),
    dependent_amount    int,
    passport            jsonb,
    employment          jsonb,
    account_number      varchar(20)
);