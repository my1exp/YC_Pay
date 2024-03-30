create table if not exists Entries (
    id INTEGER PRIMARY KEY,
    pocket_from varchar(30),
    amount float,
    currency varchar(50),
    pocket_to varchar(30),
    created_dttm timestamp
    )