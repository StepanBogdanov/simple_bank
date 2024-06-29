create table if not exists credits
(
    credit_id           uuid primary key,
    amount              decimal             not null,
    term                int                 not null,
    monthly_payment     decimal             not null,
    rate                decimal             not null,
    psk                 decimal             not null,
    payment_schedule    jsonb               not null,
    insurance_enabled   boolean             not null,
    salary_client       boolean             not null,
    credit_status       varchar(30)         not null
);