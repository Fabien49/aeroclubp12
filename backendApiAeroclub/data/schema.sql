--
-- Base de données :  `flyingclubp12`
--

CREATE DATABASE IF NOT EXISTS flyingclubp12 DEFAULT CHARACTER SET utf8;


-- --------------------------------------------------------

--
-- Structure de la table `aircrafts`
--

CREATE TABLE IF NOT EXISTS `aircrafts` (
  `id` int(11) NOT NULL ,
  `mark` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `motor` varchar(255) DEFAULT NULL,
  `power` varchar(255) DEFAULT NULL,
  `seats` int(11) NOT NULL,
  `autonomy` int(11) NOT NULL,
  `use` varchar(255) DEFAULT NULL,
  `hours` int(11) NOT NULL,
  `isAvailable` boolean,
  PRIMARY KEY (`id`)
);


-- --------------------------------------------------------

--
-- Structure de la table `registered_user`
--


CREATE TABLE IF NOT EXISTS `registeredUser` (
  `id` int(11) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `roles` varchar(255) DEFAULT NULL,
  PRIMARY KEY (id),
  UNIQUE (email)
);


-- --------------------------------------------------------

--
-- reservation table structure
--

CREATE TABLE IF NOT EXISTS `reservation` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `borrowingDate` date DEFAULT NULL,
  `returnDate` date DEFAULT NULL,
  `aircraft_id` int(11) DEFAULT NULL,
  `registeredUser_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
  );

-- --------------------------------------------------------


  --
  -- Contraintes pour la table `reservation`
  --
  ALTER TABLE `reservation`
  ADD CONSTRAINT `FKp377u47igi9fw9amplrxsepe` FOREIGN KEY (`aircraft_id`, `registeredUser_id`) REFERENCES `aircrafts`, `registeredUser` (`id, id`),
  ADD CONSTRAINT `FK5d74ihv3dtabadl6hnk60q6ip` FOREIGN KEY (`registeredUser_id`) REFERENCES `registered_user` (`id`),