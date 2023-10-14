CREATE TABLE if not exists Crypto_Wallets (
                            id SERIAL PRIMARY KEY,
                            currency_crypto VARCHAR(30),
                            network VARCHAR(30),
                            public_key VARCHAR(120),
                            private_key VARCHAR(150),
                            address VARCHAR(120),
                            create_date timestamp,
                            type VARCHAR(30)
                                          );