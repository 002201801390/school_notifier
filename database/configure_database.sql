CREATE extension IF NOT EXISTS "uuid-ossp";

CREATE type role AS ENUM ('admin', 'employee', 'student', 'responsible');
CREATE type day_of_week AS ENUM ('MONDAY', 'TUESDAY', 'WEDNESDAY', 'THURSDAY', 'FRIDAY', 'SATURDAY', 'SUNDAY');
CREATE type module AS ENUM ('mobile', 'web');

CREATE table IF NOT EXISTS users
(
    id       uuid PRIMARY KEY DEFAULT uuid_generate_v4(),
    name     text not null,
    cpf      text,
    dt_birth date,
    email    text unique,
    phone    text,
    username text not null unique,
    password text not null,
    role     role not null
);

CREATE table IF NOT EXISTS tokens
(
    token     uuid not null PRIMARY KEY DEFAULT uuid_generate_v4(),
    dt_expire date not null             DEFAULT now()::date + 30,
    user_id   uuid not null references users (id),
    module    text not null
);

CREATE view employee AS
SELECT id,
       name,
       cpf,
       dt_birth,
       email,
       phone,
       username,
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
       username,
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
       username,
       password
FROM users
WHERE role = 'responsible';

CREATE table IF NOT EXISTS responsible_student
(
    id             uuid PRIMARY KEY DEFAULT uuid_generate_v4(),
    student_id     uuid not null references users (id),
    responsible_id uuid not null references users (id)
);

CREATE OR REPLACE FUNCTION validate_responsible_student() RETURNS trigger AS
$resp_stud_stamp$
BEGIN
    if (SELECT count(*) FROM student WHERE id = NEW.student_id) <= 0 THEN
        RAISE EXCEPTION 'Student ID is not registered';
    end if;

    if (SELECT count(*) FROM responsible WHERE id = NEW.responsible_id) <= 0 THEN
        RAISE EXCEPTION 'Responsible ID is not registered';
    end if;

    RETURN new;
END;
$resp_stud_stamp$ LANGUAGE plpgsql;

CREATE TRIGGER validate_responsible_student
    BEFORE INSERT OR UPDATE
    ON responsible_student
    FOR EACH ROW
EXECUTE PROCEDURE validate_responsible_student();

CREATE TABLE IF NOT EXISTS disciplines
(
    id   uuid PRIMARY KEY DEFAULT uuid_generate_v4(),
    name text not null
);

CREATE TABLE IF NOT EXISTS classes
(
    id            uuid PRIMARY KEY DEFAULT uuid_generate_v4(),
    discipline_id uuid not null references disciplines (id),
    teacher_id    uuid not null references users (id),
    day_of_week   day_of_week[],
    time_ini      time,
    time_end      time
);

CREATE TABLE IF NOT EXISTS classes_students
(
    id         uuid PRIMARY KEY DEFAULT uuid_generate_v4(),
    class_id   uuid not null references classes (id),
    student_id uuid not null references users (id)
);

CREATE TABLE IF NOT EXISTS report_cards
(
    id              uuid PRIMARY KEY DEFAULT uuid_generate_v4(),
    student_id      uuid not null references users (id),
    class_id        uuid not null references classes (id),
    score           float,
    responsible_ack bool not null    default false
);
