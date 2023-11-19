CREATE TABLE if not exists Payouts (
                            payout_id INT PRIMARY KEY,
                            currency VARCHAR(50),
                            amount FLOAT(2),
                            address_to VARCHAR(120),
                            status varchar(15),
                            created_at DATE);

