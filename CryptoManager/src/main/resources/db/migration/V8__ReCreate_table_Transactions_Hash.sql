DROP TABLE transactions_hash;

CREATE TABLE if not exists Transactions_Hash (
    HASH VARCHAR(128) PRIMARY KEY,
    crypto_wallet_id INTEGER NOT NULL,
    Payment_id INTEGER,
    received_amount float not null
    );