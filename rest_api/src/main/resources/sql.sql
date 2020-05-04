CREATE extension IF NOT EXISTS "uuid-ossp";

CREATE type role AS ENUM ('employee', 'student', 'responsible');

CREATE table IF NOT EXISTS users
(
    id       uuid PRIMARY KEY DEFAULT uuid_generate_v4(),
    name     text not null,
    cpf      text,
    dt_birth date,
    email    text unique,
    phone    text,
    login    text not null unique,
    password text not null,
    role     role not null
);

INSERT INTO users(id, name, login, password, role)
VALUES ('61143bcd-2e5f-4ea0-8fb0-fa10aaf044c6', 'ADMIN', 'admin',
        '5a11f031c0ec66b75db135c29adab3f00f23ee48e33170cdc3e65f1606685005c1f5922666bc6ac850b3f9f67ca72dd8a9ec3a3438f2e218565970ee04e4eb28',
        'employee');

INSERT INTO users(id, name, login, password, role)
VALUES ('61143bcd-2e5f-4ea0-8fb0-fa10aaf044c7', 'Eduwardo Horibe', 'edu',
        'e5607101367cbdb6cea74479fe1983004dc653ccc221bcdf47282605d7494580d81a8b8abfa97c64d080bdfe0ce5054a4e7dc7c6b1b3eb0513d4911dfdf0d5c6',
        'responsible');

CREATE table IF NOT EXISTS tokens
(
    token     uuid not null PRIMARY KEY DEFAULT uuid_generate_v4(),
    dt_expire date not null             DEFAULT now()::date + 30,
    user_id   uuid not null references users (id)
);

CREATE view employee AS
SELECT id,
       name,
       cpf,
       dt_birth,
       email,
       phone,
       login,
       password
FROM users
WHERE role = 'employee';

CREATE view student AS
SELECT id,
       name,
       cpf,
       dt_birth,
       email,
       phone,
       login,
       password
FROM users
WHERE role = 'student';

CREATE view responsible AS
SELECT id,
       name,
       cpf,
       dt_birth,
       email,
       phone,
       login,
       password
FROM users
WHERE role = 'responsible';

/*
CREATE table IF NOT EXISTS employee
(
    id       uuid PRIMARY KEY DEFAULT uuid_generate_v4(),
    name     text not null,
    cpf      text,
    dt_birth date,
    email    text not null unique,
    phone    text,
    login    text not null unique,
    password text not null
);

CREATE table IF NOT EXISTS student
(
    id       uuid PRIMARY KEY DEFAULT uuid_generate_v4(),
    name     text not null,
    cpf      text,
    dt_birth date,
    email    text not null unique,
    phone    text,
    login    text not null unique,
    password text not null
);

CREATE table IF NOT EXISTS responsible
(
    id       uuid PRIMARY KEY DEFAULT uuid_generate_v4(),
    name     text not null,
    cpf      text,
    dt_birth date,
    email    text not null unique,
    phone    text,
    login    text not null unique,
    password text not null
);
CREATE table IF NOT EXISTS responsible_student
(
    id             uuid PRIMARY KEY DEFAULT uuid_generate_v4(),
    student_id     uuid not null references student (id),
    responsible_id uuid not null references responsible (id)
);
*/

