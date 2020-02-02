CREATE TABLE IF NOT EXISTS user_info(
    id SERIAL PRIMARY KEY ,
    first_name VARCHAR(255) NOT NULL ,
    last_name VARCHAR(255) NOT NULL ,
    age TINYINT NOT NULL ,
    username VARCHAR(255) NOT NULL ,
    password VARCHAR(255) NOT NULL ,
    email VARCHAR(255) NOT NULL ,
    role VARCHAR(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHAR SET=utf8;
