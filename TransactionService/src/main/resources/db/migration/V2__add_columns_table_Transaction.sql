ALTER TABLE Transaction
    ADD COLUMN currency_fiat varchar(20),
    ADD COLUMN amount_fiat numeric;

ALTER TABLE Transaction
    RENAME COLUMN Amount TO amount_crypto;
ALTER TABLE Transaction
    RENAME COLUMN currency TO currency_crypto;
ALTER TABLE Transaction
    RENAME COLUMN TransactionId to payment_id;
ALTER TABLE Transaction
    rename column CreateDate to payment_dttm;

ALTER TABLE Transaction
    DROP COLUMN status;
