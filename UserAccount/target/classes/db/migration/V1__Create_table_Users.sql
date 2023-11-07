CREATE TABLE if not exists Users (
                            id SERIAL PRIMARY KEY,
                            first_name VARCHAR(50),
                            last_name VARCHAR(50),
                            email VARCHAR(120),
                            phone VARCHAR(30)
                                          );