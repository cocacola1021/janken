/* userやgroupといった名前はSQLでは予約語で使えないため，userNameとしていることに注意 */
CREATE TABLE users (
    id IDENTITY PRIMARY KEY,
    name VARCHAR NOT NULL
);
CREATE TABLE matchinfo (
    id INT PRIMARY KEY AUTO_INCREMENT,
    user1 INT NOT NULL,
    user2 INT NOT NULL,
    user1Hand VARCHAR NOT NULL,
    isActive BOOLEAN NOT NULL
);
CREATE TABLE matches (
    id IDENTITY PRIMARY KEY,
    user1 VARCHAR NOT NULL,
    user2 VARCHAR NOT NULL,
    user1Hand VARCHAR NOT NULL,
    user2Hand VARCHAR NOT NULL,
    isActive BOOLEAN NOT NULL
);
