
DROP TRIGGER IF EXISTS validate_responsible_student ON responsible_student;

DROP TABLE IF EXISTS responsible_student;

DROP VIEW IF EXISTS admin;
DROP VIEW IF EXISTS employee;
DROP VIEW IF EXISTS student;
DROP VIEW IF EXISTS responsible;

DROP TABLE IF EXISTS tokens;
DROP TABLE IF EXISTS users;

DROP TYPE IF EXISTS module;
DROP TYPE IF EXISTS role;

DROP TABLE IF EXISTS disciplines
