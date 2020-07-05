INSERT INTO users(id, name, role, username, password)
VALUES ('61143bcd-2e5f-4ea0-8fb0-fa10aaf044c6'::uuid,
        'ADMIN', 'admin', 'admin',
        '5a11f031c0ec66b75db135c29adab3f00f23ee48e33170cdc3e65f1606685005c1f5922666bc6ac850b3f9f67ca72dd8a9ec3a3438f2e218565970ee04e4eb28'),
       ('61143bcd-2e5f-4ea0-8fb0-fa10aaf044c9'::uuid,
        'Laila Lúcia', 'employee', 'lalalu',
        'eb55219dd8c7d9fdd4e25da79f93712ad4947e6777072e9f9cf34aebe8a7cf450fe70805b9ffc334457d2e08becc6ba494ef9e1b81e82e08c5cc5022de35a193'),
       ('61143bcd-2e5f-4ea0-8fb0-fa10aaf044c8'::uuid,
        'Rodrigo Brossi', 'employee', 'brossi',
        'bb58ffffd1df51ddb312f5d8263abee9ee03741eeb9904d10fb374111162d64502f31d94485dc582599b872d451931db3b88646181778b9e88fb98d1b5da35f8'),
       ('61143bcd-2e5f-4ea0-8fb0-fa10aaf044c7'::uuid,
        'Eduwardo Horibe', 'student', 'edu',
        'e5607101367cbdb6cea74479fe1983004dc653ccc221bcdf47282605d7494580d81a8b8abfa97c64d080bdfe0ce5054a4e7dc7c6b1b3eb0513d4911dfdf0d5c6'),
       ('602fe963-21b1-4846-9807-1de566371316'::uuid,
        'Joana Dark', 'student', 'jdark',
        '490593d1129cdaa0b603b30125494f6b81b758ba4e2bf725c0b65a4e88abec2590a9058431bb8ca5db249563cb22d5999f9288a4e1fbab130fe8b062cd3dda1d'),
       ('82b75ec8-1e28-4ff3-9f6a-283069471a97'::uuid,
        'Brad Gibson', 'responsible', 'bson',
        '85c666c68c517fac3a0c0850cc0ad54fc71f552ada46b52a970501dc4b0f2f46aeef06b4fc02dccc9201583d51f63e58f77375e29ece6b3e56130a3c1d13fb57'),
       ('720a65c9-1d7e-40b1-bbac-5b050c17a238'::uuid,
        'Clara Kristensen', 'responsible', 'cKrisen',
        '593efc67b24179d0e58badb619ddfb8253861ac25ba98a8c442d6a6acb247005b2fc2ca0b8bde85156b157443a08b9d41cc690c3d412d914ec69f5973607a115');


INSERT INTO responsible_student(id, student_id, responsible_id)
VALUES ('b7b20b4d-4eaf-4b97-8178-fd9cbc07d8b3'::uuid, '61143bcd-2e5f-4ea0-8fb0-fa10aaf044c7'::uuid,
        '720a65c9-1d7e-40b1-bbac-5b050c17a238'::uuid),
       ('2601ccea-f934-4a41-985c-37b461395b3f'::uuid, '61143bcd-2e5f-4ea0-8fb0-fa10aaf044c7'::uuid,
        '82b75ec8-1e28-4ff3-9f6a-283069471a97'::uuid),
       ('f86e4276-7670-421b-b6eb-e55a8aa2b684'::uuid, '602fe963-21b1-4846-9807-1de566371316'::uuid,
        '82b75ec8-1e28-4ff3-9f6a-283069471a97'::uuid);


INSERT
INTO tokens(token, user_id, dt_expire, module)
VALUES ('349f78e2-5a5c-471c-b9e4-3645276bfe03'::uuid,
        '61143bcd-2e5f-4ea0-8fb0-fa10aaf044c6'::uuid,
        '01-01-2121'::date,
        'mobile'),
       ('349f78e2-5a5c-471c-b9e4-3645276bfe04'::uuid,
        '61143bcd-2e5f-4ea0-8fb0-fa10aaf044c6'::uuid,
        '01-01-2121'::date,
        'web');


INSERT
INTO disciplines(id, name)
VALUES ('eeeb136c-052c-4830-9201-0d681bf11faa'::uuid, 'Matemática'),
       ('fe67d4ec-f6b6-4964-817b-2c55b3d7deea'::uuid, 'Portugês'),
       ('73d84bf2-4cc9-45f3-9744-a6ddb54fb6a1'::uuid, 'Física'),
       ('94e7d4a1-7b04-4d82-b54c-1ea969447209'::uuid, 'História'),
       ('e05e4832-34f7-4cb9-8694-2d5be764f644'::uuid, 'Geografia'),
       ('226e1aa4-bcd7-4fad-b16b-c91cbcd61163'::uuid, 'Ciências'),
       ('4934bafb-14b1-4218-818d-d5c60aafa1c6'::uuid, 'Inglês');


INSERT
INTO classes(id, discipline_id, teacher_id, days_of_week, time_ini, time_end)
VALUES ('d43888d8-f5fc-4ce9-a342-b88ff290dfa9'::uuid,
        'eeeb136c-052c-4830-9201-0d681bf11faa'::uuid,
        '61143bcd-2e5f-4ea0-8fb0-fa10aaf044c8'::uuid,
        ARRAY ['MONDAY', 'FRIDAY']::day_of_week[],
        '08:00'::time, '12:00'::time),
       ('a8630eb6-7c8d-4d2b-ace7-c761af89378a'::uuid,
        'fe67d4ec-f6b6-4964-817b-2c55b3d7deea'::uuid,
        '61143bcd-2e5f-4ea0-8fb0-fa10aaf044c9'::uuid,
        ARRAY ['TUESDAY', 'WEDNESDAY']::day_of_week[],
        '12:00'::time, '17:00'::time);


INSERT INTO classes_students(id, class_id, student_id)
VALUES ('42676180-e32e-4244-a0ee-cd5df91ba742'::uuid,
        'd43888d8-f5fc-4ce9-a342-b88ff290dfa9'::uuid,
        '61143bcd-2e5f-4ea0-8fb0-fa10aaf044c7'::uuid),
       ('9e5997fa-4408-40f6-9d28-fbde68333daf'::uuid,
        'd43888d8-f5fc-4ce9-a342-b88ff290dfa9'::uuid,
        '602fe963-21b1-4846-9807-1de566371316'::uuid),
       ('6e3bc96d-e432-4fd3-bb8a-ce6f58c00ac1'::uuid,
        'a8630eb6-7c8d-4d2b-ace7-c761af89378a'::uuid,
        '61143bcd-2e5f-4ea0-8fb0-fa10aaf044c7'::uuid);


INSERT INTO report_cards(id, student_id, class_id, score, responsible_ack)
VALUES ('f1fd4be9-ac17-4169-a933-b24d03fe2ce3'::uuid,
        '61143bcd-2e5f-4ea0-8fb0-fa10aaf044c7'::uuid,
        'd43888d8-f5fc-4ce9-a342-b88ff290dfa9'::uuid,
        8.5, false),
       ('803aaf6e-6527-4c54-a091-91b8006ffbc4'::uuid,
        '61143bcd-2e5f-4ea0-8fb0-fa10aaf044c7'::uuid,
        'd43888d8-f5fc-4ce9-a342-b88ff290dfa9'::uuid,
        9.3, false),
       ('7994ee07-084c-44a3-99fe-75de96a33d6d'::uuid,
        '61143bcd-2e5f-4ea0-8fb0-fa10aaf044c7'::uuid,
        'a8630eb6-7c8d-4d2b-ace7-c761af89378a'::uuid,
        10.0, false),
       ('ccc5d4ee-e8e0-4331-8b83-c88e6888485a'::uuid,
        '602fe963-21b1-4846-9807-1de566371316'::uuid,
        'd43888d8-f5fc-4ce9-a342-b88ff290dfa9'::uuid,
        7.0, false),
       ('bcf00e07-50a9-4659-94ce-af5ade990163'::uuid,
        '602fe963-21b1-4846-9807-1de566371316'::uuid,
        'a8630eb6-7c8d-4d2b-ace7-c761af89378a'::uuid,
        9.0, false);