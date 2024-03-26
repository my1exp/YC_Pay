ALTER TABLE Transaction
    RENAME COLUMN merchantid TO merchant_id;

ALTER TABLE Transaction
    RENAME COLUMN walletfrom TO wallet_from;

ALTER TABLE Transaction
    RENAME COLUMN walletto TO wallet_to;