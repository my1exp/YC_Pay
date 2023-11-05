ALTER TABLE intent
    RENAME COLUMN amount TO amount_fiat;

ALTER TABLE intent
    ADD COLUMN amount_crypto numeric;