CREATE TABLE if not exists Intent (
                                        request_id varchar(50),
                                        session_id varchar(50),
                                        currency varchar(30),
                                        amount numeric,
                                        network VARCHAR(30),
                                        merchant_Id varchar(50),
                                        Wallet_to varchar(50),
                                        Create_Date timestamp,
                                        Category varchar(20),
                                        Status varchar(20),
                                        PRIMARY KEY (request_id, session_id)
);