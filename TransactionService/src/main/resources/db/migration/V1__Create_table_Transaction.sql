create table if not exists Transaction (
    TransactionId varchar(50),
    MerchantId varchar(50),
    WalletFrom varchar(50),
    WalletTo varchar(50),
    Currency varchar(20),
    Amount numeric,
    Network varchar(20),
    CreateDate timestamp,
    Category varchar(20),
    Status varchar(20),
    PRIMARY KEY (TransactionId, MerchantId)
)