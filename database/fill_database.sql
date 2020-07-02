INSERT INTO users(id, name, role, username, password)
VALUES ('61143bcd-2e5f-4ea0-8fb0-fa10aaf044c6'::uuid,
        'ADMIN', 'employee', 'admin',
        '5a11f031c0ec66b75db135c29adab3f00f23ee48e33170cdc3e65f1606685005c1f5922666bc6ac850b3f9f67ca72dd8a9ec3a3438f2e218565970ee04e4eb28'),
       ('61143bcd-2e5f-4ea0-8fb0-fa10aaf044c9'::uuid,
        'Laila Lúcia', 'employee', 'lalalu',
        'eb55219dd8c7d9fdd4e25da79f93712ad4947e6777072e9f9cf34aebe8a7cf450fe70805b9ffc334457d2e08becc6ba494ef9e1b81e82e08c5cc5022de35a193'),
       ('61143bcd-2e5f-4ea0-8fb0-fa10aaf044c7'::uuid,
        'Eduwardo Horibe', 'student', 'edu',
        'e5607101367cbdb6cea74479fe1983004dc653ccc221bcdf47282605d7494580d81a8b8abfa97c64d080bdfe0ce5054a4e7dc7c6b1b3eb0513d4911dfdf0d5c6'),
       ('602fe963-21b1-4846-9807-1de566371316'::uuid,
        'Joana Dark', 'student', 'jdark',
        '490593d1129cdaa0b603b30125494f6b81b758ba4e2bf725c0b65a4e88abec2590a9058431bb8ca5db249563cb22d5999f9288a4e1fbab130fe8b062cd3dda1d'),
       ('61143bcd-2e5f-4ea0-8fb0-fa10aaf044c8'::uuid,
        'Rodrigo Brossi', 'responsible', 'brossi',
        'bb58ffffd1df51ddb312f5d8263abee9ee03741eeb9904d10fb374111162d64502f31d94485dc582599b872d451931db3b88646181778b9e88fb98d1b5da35f8');


INSERT
INTO tokens(token, user_id, dt_expire, module)
VALUES ('349f78e2-5a5c-471c-b9e4-3645276bfe03'::uuid,
        '61143bcd-2e5f-4ea0-8fb0-fa10aaf044c6'::uuid,
        '01-01-2121'::date,
        'mobile');


INSERT
INTO disciplines(id, name)
VALUES ('eeeb136c-052c-4830-9201-0d681bf11faa'::uuid, 'Matemática'),
       ('fe67d4ec-f6b6-4964-817b-2c55b3d7deea'::uuid, 'Portugês'),
       ('73d84bf2-4cc9-45f3-9744-a6ddb54fb6a1'::uuid, 'Física');


INSERT
INTO classes(id, discipline_id, teacher_id, day_of_week, time_ini, time_end)
VALUES ('d43888d8-f5fc-4ce9-a342-b88ff290dfa9'::uuid,
        '73d84bf2-4cc9-45f3-9744-a6ddb54fb6a1'::uuid,
        '61143bcd-2e5f-4ea0-8fb0-fa10aaf044c8'::uuid,
        ARRAY ['MONDAY', 'FRIDAY']::day_of_week[],
        '08:00'::time, '18:00'::time),
       ('a8630eb6-7c8d-4d2b-ace7-c761af89378a'::uuid,
        'fe67d4ec-f6b6-4964-817b-2c55b3d7deea'::uuid,
        '61143bcd-2e5f-4ea0-8fb0-fa10aaf044c8'::uuid,
        ARRAY ['TUESDAY', 'WEDNESDAY']::day_of_week[],
        '10:00'::time, '14:00'::time);


INSERT INTO classes_students(id, class_id, student_id)
values ('42676180-e32e-4244-a0ee-cd5df91ba742'::uuid,
        'd43888d8-f5fc-4ce9-a342-b88ff290dfa9'::uuid,
        '61143bcd-2e5f-4ea0-8fb0-fa10aaf044c7'::uuid),
       ('9e5997fa-4408-40f6-9d28-fbde68333daf'::uuid,
        'd43888d8-f5fc-4ce9-a342-b88ff290dfa9'::uuid,
        '602fe963-21b1-4846-9807-1de566371316'::uuid),
       ('6e3bc96d-e432-4fd3-bb8a-ce6f58c00ac1'::uuid,
        'a8630eb6-7c8d-4d2b-ace7-c761af89378a'::uuid,
        '61143bcd-2e5f-4ea0-8fb0-fa10aaf044c7'::uuid);
