-- noinspection SqlNoDataSourceInspectionForFile

-- Create user
INSERT INTO registered_user ( id , email, first_name, last_name, password, hours, roles)
VALUES
  (
    1,
  	'fabien@gmail.com',
    'Fabien',
    'Chapeau',
    '$2a$10$jPUTDV6/9AkC7NRPPzmvXuLJlfnKoAYo.uJuZc0aeJHGum.LpxO0a',
    1875,
    'USER'
  ),
  (
    2,
  	'test1@gmail.com',
    'userName',
    'userLastName',
    '$2a$10$jPUTDV6/9AkC7NRPPzmvXuLJlfnKoAYo.uJuZc0aeJHGum.LpxO0a',
    138,
    'USER'
  ),
  (
    3,
  	'test2@gmail.com',
    'userName',
    'userLastName',
    '$2a$10$jPUTDV6/9AkC7NRPPzmvXuLJlfnKoAYo.uJuZc0aeJHGum.LpxO0a',
    459,
    'USER'
  ),
  (
    4,
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
    hours,
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
      true
    ),
    (
        'Diamond Aircraft',
        'DA 40',
        'Rotax 912 S',
        '180 ch',
        4,
        4,
        'voyage',
        378,
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
      7875,
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
    date,
    aircraft_id
)
VALUES
    (
        true,
        true,
        '',
        '2023-09-13',
        1
    ),
    (
        true,
        true,
        '',
        '2023-09-29',
        3
    ),
    (
        true,
        true,
        '',
        '2023-10-09',
        2
    ),
    (
        true,
        true,
        '',
        '2023-10-30',
        4
    );



    -- Create Reservation
 insert into reservations
(borrowing_date, return_date, aircraft_id, registered_user_id)
VALUES
('2023-09-26', '2023-09-26', 1, 1),
('2023-10-09', '2023-10-09', 2, 2),
('2023-10-10', '2023-10-10', 1, 3),
('2023-10-11', '2023-10-11', 3, 4),
('2023-12-05', '2023-12-05', 1, 1),
('2023-12-11', '2023-12-12', 3, 2);