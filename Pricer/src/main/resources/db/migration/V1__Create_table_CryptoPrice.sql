CREATE TABLE if not exists CryptoPrice (
                                        ticker varchar(15),
                                        name VARCHAR(30) not null ,
                                        price numeric not null ,
                                        update_dttm timestamp not null,
                                        PRIMARY KEY (ticker)
                                       );