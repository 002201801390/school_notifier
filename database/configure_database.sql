CREATE extension IF NOT EXISTS "uuid-ossp";

CREATE type role AS ENUM ('admin', 'employee', 'student', 'responsible');

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
        'student');

INSERT INTO users(id, name, login, password, role)
VALUES ('61143bcd-2e5f-4ea0-8fb0-fa10aaf044c8', 'Rodrigo Brossi', 'brossi',
        'bb58ffffd1df51ddb312f5d8263abee9ee03741eeb9904d10fb374111162d64502f31d94485dc582599b872d451931db3b88646181778b9e88fb98d1b5da35f8',
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

INSERT
INTO disciplines(id, name)
VALUES ('eeeb136c-052c-4830-9201-0d681bf11faa'::uuid, 'Matemática'),
       ('fe67d4ec-f6b6-4964-817b-2c55b3d7deea'::uuid, 'Portugês'),
       ('73d84bf2-4cc9-45f3-9744-a6ddb54fb6a1'::uuid, 'Física');

CREATE TYPE DAYS_OF_WEEK AS ENUM ('MONDAY', 'TUESDAY', 'WEDNESDAY', 'THURSDAY', 'FRIDAY', 'SATURDAY', 'SUNDAY');

CREATE TABLE IF NOT EXISTS classes
(
    id            uuid PRIMARY KEY DEFAULT uuid_generate_v4(),
    discipline_id uuid not null references disciplines (id),
    teacher_id    uuid not null references users (id),
    days_of_week  DAYS_OF_WEEK[],
    time_ini      time,
    time_end      time
);

INSERT
INTO classes(id, discipline_id, teacher_id, days_of_week, time_ini, time_end)
VALUES ('d43888d8-f5fc-4ce9-a342-b88ff290dfa9'::uuid,
        '73d84bf2-4cc9-45f3-9744-a6ddb54fb6a1'::uuid,
        'abfce481-a950-48fe-a39a-08ec8dc704f7'::uuid,
        ARRAY ['MONDAY', 'FRIDAY']::DAYS_OF_WEEK[],
        '08:00'::time, '18:00'::time),
       ('a8630eb6-7c8d-4d2b-ace7-c761af89378a'::uuid,
        'fe67d4ec-f6b6-4964-817b-2c55b3d7deea'::uuid,
        'abfce481-a950-48fe-a39a-08ec8dc704f7'::uuid,
        ARRAY ['TUESDAY', 'WEDNESDAY']::DAYS_OF_WEEK[],
        '10:00'::time, '14:00'::time);

CREATE TABLE IF NOT EXISTS classes_students
(
    id         uuid PRIMARY KEY DEFAULT uuid_generate_v4(),
    class_id   uuid not null references classes (id),
    student_id uuid not null references users (id)
);

INSERT INTO classes_students(id, class_id, student_id)
values ('42676180-e32e-4244-a0ee-cd5df91ba742'::uuid,
        'd43888d8-f5fc-4ce9-a342-b88ff290dfa9'::uuid,
        'eda13326-bf9a-4e4f-8802-abc20a476a85'::uuid),
       ('9e5997fa-4408-40f6-9d28-fbde68333daf'::uuid,
        'd43888d8-f5fc-4ce9-a342-b88ff290dfa9'::uuid,
        '602fe963-21b1-4846-9807-1de566371316'::uuid),
       ('6e3bc96d-e432-4fd3-bb8a-ce6f58c00ac1'::uuid,
        'a8630eb6-7c8d-4d2b-ace7-c761af89378a'::uuid,
        'eda13326-bf9a-4e4f-8802-abc20a476a85'::uuid);

CREATE TABLE IF NOT EXISTS report_cards
(
    id              uuid PRIMARY KEY DEFAULT uuid_generate_v4(),
    student_id      uuid not null references student (id),
    class_id        uuid not null references classes (id),
    score           float,
    responsible_ack bool not null    default false
);
