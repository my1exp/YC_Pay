CREATE table if not exists "TransactionService".public.transactions
(id SERIAL PRIMARY KEY,
transactionId VARCHAR(50),
merchantId VARCHAR(50),
walletFrom VARCHAR(50),
walletTo VARCHAR(50),
currency VARCHAR(15),
amount numeric,
network VARCHAR(15),
createdAt timestamp,
category VARCHAR(30),
status VARCHAR(30));