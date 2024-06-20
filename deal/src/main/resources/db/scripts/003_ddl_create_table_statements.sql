create table if not exists statements
(
    statement_id        uuid primary key,
    client_id           uuid references clients(client_id)      not null,
    credit_id           uuid references credits(credit_id),
    status              varchar(30)                             not null,
    creation_date       timestamp                               not null,
    applied_offer       jsonb,
    sign_date           timestamp,
    ses_code            varchar(255),
    status_history      jsonb                                   not null
);