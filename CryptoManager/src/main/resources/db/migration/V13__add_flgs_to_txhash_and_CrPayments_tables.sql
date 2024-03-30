ALTER TABLE transactions_hash add COLUMN delivered_Tx_serv_flg integer;

ALTER TABLE crypto_payments add COLUMN create_dttm timestamp;