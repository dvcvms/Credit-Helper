INSERT INTO roles (NAME)
VALUES ('ROLE_USER');

INSERT INTO roles (NAME)
VALUES ('ROLE_ADMIN');

INSERT INTO roles (NAME)
VALUES ('ROLE_MODERATOR');

INSERT INTO users (username, firstname, lastname, email, password, locked, enabled)
VALUES ('dvcvms', 'Max', 'Volkov', 'dvcvms@gmail.com', '1234', FALSE, FALSE);

INSERT INTO users (username, firstname, lastname, email, password, locked, enabled)
VALUES ('mts', 'fintech', 'academy', 'mts@mail.ru', '1234', FALSE, FALSE);

INSERT INTO users_roles (user_id, role_id)
VALUES (1, 1);

INSERT INTO users_roles (user_id, role_id)
VALUES (2, 2);

INSERT INTO tariff (type, interestRate)
VALUES ('CONSUMER', '5%');

INSERT INTO tariff (type, interestRate)
VALUES ('MORTGAGE', '11.9%');

INSERT INTO tariff (type, interestRate)
VALUES ('MICROLOANS', '5.9%');

INSERT INTO loan_order (order_id, user_id, tariff_id, credit_rating, status, time_insert)
VALUES ('37f38d66-6c55-43b5-b981-0f9fd0b448d7', 1, 1, 0.57, 'IN_PROGRESS', '2000-01-01 00:00:37');

INSERT INTO loan_order (order_id, user_id, tariff_id, credit_rating, status, time_insert)
VALUES ('37f38d66-6c55-43b5-b981-0f9fd0b448d6', 1, 2, 0.90, 'APPROVED', '2001-01-01 00:00:01');

INSERT INTO loan_order (order_id, user_id, tariff_id, credit_rating, status, time_insert)
VALUES ('37f38d66-6c55-43b5-b981-0f9fd0b448d5', 1, 2, 0.16, 'REFUSED', '2002-01-01 00:00:45');
