CREATE DATABASE goods_shop ENCODING 'UTF-8';

--Создаем таблицу ролей для пользователей системы

CREATE TABLE IF NOT EXISTS roles
(
    id   SERIAL PRIMARY KEY,
    role VARCHAR(5) NOT NULL
);

--Заполняем роли

INSERT INTO roles (id, role)
VALUES (DEFAULT, 'admin');
INSERT INTO roles (id, role)
VALUES (DEFAULT, 'user');

--Создаем таблицу пользователей

CREATE TABLE IF NOT EXISTS users
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(10) UNIQUE NOT NULL,
    pass VARCHAR(10) UNIQUE NOT NULL,
    role INTEGER            NOT NULL,
    FOREIGN KEY (role) REFERENCES roles (id)
);

--Вставляем новых пользователей в таблицу

INSERT INTO users (id, name, pass, role)
VALUES (DEFAULT, 'admin', '123', 1);
INSERT INTO users (id, name, pass, role)
VALUES (DEFAULT, 'user', '1234', 2);

--Создаем таблицу моделей

CREATE TABLE IF NOT EXISTS item_types
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(15) UNIQUE NOT NULL
);

--Вставляем модели в таблицу

INSERT INTO item_types (id, name)
VALUES (DEFAULT, 'food') RETURNING id;
INSERT INTO item_types (id, name)
VALUES (DEFAULT, 'electronics') RETURNING id;
INSERT INTO item_types (id, name)
VALUES (DEFAULT, 'homewares') RETURNING id;
INSERT INTO item_types (id, name)
VALUES (DEFAULT, 'stuff') RETURNING id;









--Удалить пользователя

DELETE
FROM users
WHERE id = (?)
  AND name = (?)
  AND pass = (?) RETURNING id;

--Обновить пользователя

UPDATE users
SET pass = (?)
WHERE id = (?) RETURNING id;

--Выгрузить пользователя с ролью

SELECT u.id, u.name, u.pass, r.id AS rol_id, r.role
FROM users AS u
         LEFT JOIN roles AS r ON u.role = r.id
WHERE u.name = (?);

--Удалить бд
DROP DATABASE IF EXISTS goods_shop