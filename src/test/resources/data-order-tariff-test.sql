INSERT INTO tariff (type, interestRate)
VALUES ('CONSUMER', '5%');
INSERT INTO tariff (type, interestRate)
VALUES ('MORTGAGE', '11.9%');
INSERT INTO tariff (type, interestRate)
VALUES ('TYPE3', '5.9%');

INSERT INTO loan_order (order_id, user_id, tariff_id, credit_rating, status, time_insert)
VALUES ('37f38d66-6c55-43b5-b981-0f9fd0b448d7', 7, 1, 0.57, 'IN_PROGRESS', '2000-01-01 00:00:37');

INSERT INTO loan_order (order_id, user_id, tariff_id, credit_rating, status, time_insert)
VALUES ('37f38d66-6c55-43b5-b981-0f9fd0b448d6', 754, 2, 0.90, 'APPROVED', '2001-01-01 00:00:01');

INSERT INTO loan_order (order_id, user_id, tariff_id, credit_rating, status, time_insert)
VALUES ('37f38d66-6c55-43b5-b981-0f9fd0b448d5', 988, 2, 0.16, 'REFUSED', '2002-01-01 00:00:45');
