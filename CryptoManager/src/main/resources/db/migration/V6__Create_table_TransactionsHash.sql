CREATE TABLE if not exists TransactionsHash (
    crypto_wallet_id INTEGER PRIMARY KEY,
    HASH VARCHAR(128) NOT NULL,
    Payment_id INTEGER,
    received_amount float not null
    );