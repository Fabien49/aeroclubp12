-- Create user
INSERT INTO registered_user ( id , email, first_name, last_name, password, roles)
VALUES
  (
    1,
  	'fabien@gmail.com',
    'Fabien',
    'Chapeau',
    '$2a$10$jPUTDV6/9AkC7NRPPzmvXuLJlfnKoAYo.uJuZc0aeJHGum.LpxO0a',
    'USER'
  ),
  (
    2,
  	'test1@gmail.com',
    'userName',
    'userLastName',
    '$2a$10$jPUTDV6/9AkC7NRPPzmvXuLJlfnKoAYo.uJuZc0aeJHGum.LpxO0a',
    'USER'
  ),
  (
    3,
  	'test2@gmail.com',
    'userName',
    'userLastName',
    '$2a$10$jPUTDV6/9AkC7NRPPzmvXuLJlfnKoAYo.uJuZc0aeJHGum.LpxO0a',
    'USER'
  ),
  (
    4,
  	'test3@gmail.com',
    'userName',
    'userLastName',
    '$2a$10$jPUTDV6/9AkC7NRPPzmvXuLJlfnKoAYo.uJuZc0aeJHGum.LpxO0a',
    'USER'
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



    -- Create Reservation
 insert into reservations
(borrowing_date, return_date, aircraft_id, registered_user_id)
VALUES
('2023-09-26', '2023-09-26', 1, 1),
('2023-10-09', '2023-10-09', 2, 1);