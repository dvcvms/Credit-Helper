INSERT INTO roles (NAME)
VALUES ('ROLE_USER');

INSERT INTO roles (NAME)
VALUES ('ROLE_ADMIN');

INSERT INTO roles (NAME)
VALUES ('ROLE_MODERATOR');

INSERT INTO users (username, firstname, lastname, email, password, locked, enabled)
VALUES ('1', '1', '1', '1', '1', FALSE, FALSE);

INSERT INTO users_roles (user_id, role_id)
VALUES (1, 1);

INSERT INTO users_roles (user_id, role_id)
VALUES (1, 2);