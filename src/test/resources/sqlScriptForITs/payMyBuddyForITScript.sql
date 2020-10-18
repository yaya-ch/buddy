DROP SCHEMA IF EXISTS test_db;
CREATE SCHEMA test_db;
SET SCHEMA test_db;
DROP TABLE IF EXISTS associated_bank_account_info;
CREATE TABLE associated_bank_account_info (
    associated_bank_account_info_id INT,
    bank_account_holder_first_name VARCHAR,
    bank_account_holder_last_name VARCHAR,
    bic VARCHAR,
    iban VARCHAR,
    PRIMARY KEY (associated_bank_account_info_id),
    UNIQUE (bic),
    UNIQUE (iban)

);

INSERT INTO associated_bank_account_info(associated_bank_account_info_id, bank_account_holder_first_name, bank_account_holder_last_name, bic, iban)
VALUES (1, 'user', 'user', 'BICBIC345', 'IBANIBAN123IBAN');

CREATE TABLE buddy_account_info(
    buddy_account_info_id INT,
    actual_account_balance DOUBLE,
    previous_account_balance DOUBLE,
    associated_bank_account_info_id INT,
    PRIMARY KEY (buddy_account_info_id),
    FOREIGN KEY (associated_bank_account_info_id) REFERENCES associated_bank_account_info(associated_bank_account_info_id)
    );
INSERT INTO buddy_account_info (buddy_account_info_id, actual_account_balance, previous_account_balance, associated_bank_account_info_id)
VALUES (1, 100.0, 0.0, 1);

CREATE TABLE user(
    user_id INT,
    address VARCHAR,
    birth_date VARCHAR,
    city VARCHAR,
    civility VARCHAR,
    email VARCHAR,
    first_name VARCHAR,
    last_name VARCHAR,
    password VARCHAR,
    phone VARCHAR,
    role VARCHAR,
    zip VARCHAR,
    buddy_account_info_id INT,
    PRIMARY KEY (user_id),
    UNIQUE (email),
    FOREIGN KEY (buddy_account_info_id) REFERENCES buddy_account_info (buddy_account_info_id)
);
INSERT INTO user(user_id, address, birth_date, city, civility, email, first_name, last_name, password, phone, role, zip, buddy_account_info_id)
VALUES (1, 'any address', '1991-01-01', 'any city', 'SIR', 'user@user.com', 'user', 'user', '$2a$15$UonCA3GSGIb.IvzHCet.Z.XxemHD14EeMspbN/bCMHZeWnV237Zca', '123456789', 'ROLE_USER', '12345', 1);

CREATE TABLE contacts(
    user_id INT,
    contact_id INT,
    PRIMARY KEY (user_id, contact_id),
    FOREIGN KEY (user_id) REFERENCES user(user_id),
    FOREIGN KEY (contact_id) REFERENCES user(user_id)
);

DROP TABLE IF EXISTS transaction;
CREATE TABLE transaction(
    transaction_id INT NOT NULL AUTO_INCREMENT,
    amount DOUBLE,
    fee DOUBLE,
    recipient VARCHAR,
    sender VARCHAR,
    transaction_date VARCHAR,
    transaction_nature VARCHAR,
    transaction_status_info VARCHAR,
    buddy_account_info_id INT,
    PRIMARY KEY (transaction_id),
    FOREIGN KEY (buddy_account_info_id) REFERENCES buddy_account_info(buddy_account_info_id)
)
