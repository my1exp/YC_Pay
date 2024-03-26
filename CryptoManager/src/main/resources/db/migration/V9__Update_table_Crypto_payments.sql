ALTER TABLE crypto_payments add COLUMN delivered_Payment_Api_flg int;

ALTER TABLE crypto_payments DROP  COLUMN merchant_id;
ALTER TABLE crypto_payments DROP  COLUMN amount_fiat;
ALTER TABLE crypto_payments DROP  COLUMN currency_fiat;
