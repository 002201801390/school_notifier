CREATE extension IF NOT EXISTS "uuid-ossp";

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
