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

-- Create book
insert into book (
    author_first_name,
    author_last_name,
    pictureurl,
    publication_date,
    synopsis,
    title
  )
VALUES
  (
      'George',
      'Orwell',
      '/covers/1984.jpg',
      '1949-06-08',
      'Année 1984 en Océanie. 1984 ? C''est en tout cas ce qu''il semble à Winston, qui ne saurait toutefois en jurer. Le passé a été oblitéré et réinventé, et les événements les plus récents sont susceptibles d''être modifiés. Winston est lui-même chargé de récrire les archives qui contredisent le présent et les promesses de Big Brother. Grâce à une technologie de pointe, ce dernier sait tout, voit tout. Il n''est pas une âme dont il ne puisse connaître les pensées. On ne peut se fier à personne et les enfants sont encore les meilleurs espions qui soient. Liberté est Servitude. Ignorance est Puissance. Telles sont les devises du régime de Big Brother. La plupart des Océaniens n''y voient guère à redire, surtout les plus jeunes qui n''ont pas connu l''époque de leurs grands-parents et le sens initial du mot "libre". Winston refuse cependant de perdre espoir. Il entame une liaison secrète et hautement dangereuse avec l''insoumise Julia et tous deux vont tenter d''intégrer la Fraternité, une organisation ayant pour but de renverser Big Brother.',
      '1984'
    ),
    (
    'Philip K.',
    'Dick',
    '/covers/minority_report.jpg',
    '2005-06-09',
    'Washington, 2054. John Anderton est membre de Précrime, une unité gouvernementale utilisant les dons de prescience de trois mutants, les précogs, pour arrêter les criminels avant leur passage à l''acte. Avant même qu''ils aient imaginé de passer à l''acte. Anderton a une confiance aveugle dans les prédictions des précogs. Mais quand, chasseur devenu gibier, il se retrouvera lui-même accusé du meurtre d''un homme qu''il n''a jamais rencontré, il lui faudra découvrir les véritables rouages de Précrime pour prouver son innocence.',
    'Minority Report'
  ),
  (
    'J.R.R.',
    'Tolkien',
    '/covers/lsda_tome1.jpg',
    '2019-03-10',
    'Dans les vertes prairies de la Comté, les Hobbits, ou Semi-hommes, vivaient en paix... Jusqu''au jour fatal où l''un d''entre eux, au cours de ses voyages, entra en possession de l''Anneau Unique aux immenses pouvoirs. Pour le reconquérir, Sauron, le seigneur ténébreux, va déchaîner toutes les forces du Mal... Frodon, le Porteur de l''Anneau, Gandalf, le magicien, et leurs intrépides compagnons réussiront-t-ils à écarter la menace qui pèse sur la Terre du Milieu ?',
    'Le Seigneur des Anneaux - La communauté de l''anneau'
  ),
  (
    'Bernard',
    'Werber',
    '/covers/les_fourmis.jpeg',
    '2002-08-24',
    'Le temps que vous lisiez ces lignes, sept cents millions de fourmis seront nées sur la planète. Sept cents millions d''individus dans une communauté estimée à un milliard de milliards, et qui a ses villes, sa hiérarchie, ses colonies, son langage, sa production industrielle, ses esclaves, ses mercenaires... Ses armes aussi. Terriblement destructrices. Lorsqu''il entre dans la cave de la maison léguée par un vieil oncle entomologiste, Jonathan Wells est loin de se douter qu''il va à leur rencontre. A sa suite, nous allons découvrir le monde fabuleusement riche, monstrueux et fascinant de ces "infra terrestres", au fil d''un thriller unique en son genre, où le suspense et l''horreur reposent à chaque page sur les données scientifiques les plus rigoureuses. Voici pour la première fois un roman dont les héros sont des... fourmis.',
    'Les fourmis'
  ),
    (
      'Bernard',
      'Werber',
      '/covers/la_prophetie_des_abeilles.jpg',
      '2021-10-01',
      'Depuis la nuit des temps, les abeilles détiennent le secret du destin de l’Humanité. Ce secret est annoncé dans une prophétie écrite à Jérusalem il y 1000 ans par un chevalier Templier. Mais sa trace est perdue, et pour la retrouver, il faudra remonter dans le temps, traverser époques et continents, affronter tous les dangers. Êtes-vous prêts à payer ce prix pour sauver votre futur ?',
      'La Prophéthie des Abeilles'
    );


-- Create Library
insert into library (name)
VALUES
  ('Toussaint'),
  ('Justice'),
  ('Annie-Fratellini');

-- Create Available copie

INSERT INTO available_copie
(book_id, library_id, owned_quantity, available_quantity, book_can_be_reserved, nearest_return_date, reservation_count)
VALUES
(1, 1, 4, 0, true, '2022-06-01', 6),
(1, 2, 2, 2, true, null, 0),
(1, 3, 2, 2, true, null, 0),
(2, 1, 2, 1, true, '2022-06-11', 0),
(2, 2, 2, 2, true, null, 0),
(2, 3, 2, 2, true, null, 0),
(3, 1, 2, 0, true, '2020-06-11', 0),
(3, 2, 2, 2, true, null, 0),
(3, 3, 2, 2, true, null, 0),
(4, 1, 3, 1, true, null, 0),
(4, 2, 2, 1, true, null, 0),
(4, 3, 2, 1, true, null, 0),
(5, 1, 4, 2, true, null, 0),
(5, 2, 4, 3, true, null, 0),
(5, 3, 3, 2, true, null, 0);

-- Create Borrow
insert into borrow (
    book_returned,
    borrow_date,
    extended_duration,
    return_date,
    book_id,
    library_id,
    registered_user_id
  )
VALUES
  (
    false,
   '2022-09-01',
    false,
    '2022-10-01',
    1,
    1,
    1
  ),
  (
    false,
    '2022-05-11',
    false,
    '2022-06-11',
    3,
    1,
    2
  ),
  (
    false,
    '2022-01-17',
    false,
    '2022-02-14',
    2,
    1,
    1
  ),
  (
    false,
    '2022-03-17',
    false,
    '2022-04-14',
    3,
    1,
    1
  ),
  (
    false,
    '2022-05-11',
    false,
    '2022-06-11',
    3,
    1,
    3
  ),
    (
      false,
      '2022-04-17',
      false,
      '2022-05-14',
      4,
      2,
      2
    )
    ;

    -- Create Reservation
 insert into reservation
(avalaibility_date, notification_is_sent, book_id, library_id, registered_user_id, position)
VALUES
('2022-09-26', false, 1, 1, 3, 1),
('2022-09-26', true, 1, 1, 2, 2);