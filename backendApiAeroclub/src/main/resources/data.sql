-- noinspection SqlNoDataSourceInspectionForFile

-- Create user
INSERT INTO registered_user ( id , email, first_name, last_name, password, hours, roles)
VALUES
    (
        1,
        'admin@admin.com',
        'ad',
        'min',
        '$2a$10$jPUTDV6/9AkC7NRPPzmvXuLJlfnKoAYo.uJuZc0aeJHGum.LpxO0a',
        10000,
        'ADMIN'
    ),
    (
        2,
        'mechanic@mechanic.com',
        'mech',
        'anic',
        '$2a$10$jPUTDV6/9AkC7NRPPzmvXuLJlfnKoAYo.uJuZc0aeJHGum.LpxO0a',
        0,
        'MECHANIC'
    ),
  (
    3,
  	'fabien@gmail.com',
    'Fabien',
    'Chapeau',
    '$2a$10$jPUTDV6/9AkC7NRPPzmvXuLJlfnKoAYo.uJuZc0aeJHGum.LpxO0a',
    1875,
    'USER'
  ),
  (
    4,
  	'test1@gmail.com',
    'userName',
    'userLastName',
    '$2a$10$jPUTDV6/9AkC7NRPPzmvXuLJlfnKoAYo.uJuZc0aeJHGum.LpxO0a',
    138,
    'USER'
  ),
  (
    5,
  	'test2@gmail.com',
    'userName',
    'userLastName',
    '$2a$10$jPUTDV6/9AkC7NRPPzmvXuLJlfnKoAYo.uJuZc0aeJHGum.LpxO0a',
    459,
    'USER'
  ),
  (
    6,
  	'test3@gmail.com',
    'userName',
    'userLastName',
    '$2a$10$jPUTDV6/9AkC7NRPPzmvXuLJlfnKoAYo.uJuZc0aeJHGum.LpxO0a',
    38,
    'USER'
  );

-- Create flying club
insert into flying_club (
    oaci,
    name,
    phonenumber,
    mail,
    address,
    postalcode,
    city
)
VALUES
    (
        'LFJR',
        'Aéroclub Passion',
        '0241000000',
        'aeroclubpassion@test.fr',
        '1 rue de la paix',
        '49560',
        'Marcé'
    );

-- Create aircraft
insert into aircrafts (
    mark,
    type,
    motor,
    power,
    seats,
    autonomy,
    use,
    aircraft_hours,
    motor_hours,
    is_available
  )
VALUES
  (
      'Diamond Aircraft',
      'DA 20-100 Katana',
      'Rotax 912 S',
      '100 ch',
      2,
      4,
      'ecole',
      4589,
      4589,
      true
    ),
    (
        'Diamond Aircraft 40',
        'DA 40',
        'Rotax 912 S',
        '180 ch',
        4,
        4,
        'voyage',
        20008,
        10005,
        false
  ),
  (
      'Piper',
      'PA 28-161 Cadet',
      'Lycoming ',
      '160 ch',
      2,
      5,
      'voyage',
      7810,
      7810,
      true
  ),
  (
      'Cessna',
      'kyhawk C172 S',
      'Lycoming ',
      '180 ch',
      4,
      5,
      'voyage',
      429,
      429,
      true
  ),
    (
      'Aéro Services',
      'Super Guépard SG 10',
      'Rotax 912 ',
      '80 ch',
      2,
      4,
      'ecole et voyage',
      10011,
      10011,
      false
    );

-- Create revision
insert into revision (
    levels,
    pressure,
    bodywork,
    date,
    aircraft_id
)
VALUES
    (
        true,
        true,
        false,
        '2023-09-26',
        1
    ),
    (
        true,
        true,
        true,
        '2023-09-30',
        3
    ),
    (
        true,
        true,
        false,
        '2023-10-10',
        2
    ),
    (
        true,
        true,
        false,
        '2023-10-26',
        4
    );


-- Create workshop
insert into workshop (
    motor_change,
    helix_change,
    other,
    entry_date,
    exit_date,
    manual,
    canceled,
    aircraft_id
)
VALUES
    (
        true,
        true,
        '',
        '2023-09-13',
        '2023-09-14',
        false,
        false,
        1
    ),
    (
        true,
        true,
        '',
        '2023-09-29',
        '2023-09-30',
        false,
        false,
        3
    ),
    (
        true,
        true,
        '',
        '2023-10-09',
        '2023-10-11',
        false,
        false,
        2
    ),
    (
        false,
        false,
        '',
        '2024-01-15',
        null,
        false,
        false,
        5
    ),
    (
        false,
        false,
        '',
        '2024-01-13',
        null,
        false,
        false,
        2
    );


    -- Create Reservation
 insert into reservations
(borrowing_date, return_date,finished,canceled, aircraft_id, registered_user_id)
VALUES
('2023-09-26', '2023-09-26', true, false, 1, 3),
('2023-10-09', '2023-10-09', true, false,2, 4),
('2023-10-10', '2023-10-10', false, true,2, 4),
('2023-10-10', '2023-10-10', true, false,1, 3),
('2023-10-11', '2023-10-11', true, false,3, 6),
('2023-12-05', '2023-12-05', true, false,1, 3),
('2024-01-06', '2024-01-07', false,false,3, 2);