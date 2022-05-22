--
-- Base de données :  `bibliop7`
--

CREATE DATABASE IF NOT EXISTS bibliop7 DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
USE bibliop7;

-- --------------------------------------------------------

--
-- Structure de la table `available_copie`
--


CREATE TABLE IF NOT EXISTS `available_copie` (
  `book_id` int(11) NOT NULL,
  `library_id` int(11) NOT NULL,
  `available_quantity` int(11) NOT NULL,
  `owned_quantity` int(11) NOT NULL,
  PRIMARY KEY (book_id, library_id)
);


-- --------------------------------------------------------

--
-- Structure de la table `book`
--

CREATE TABLE IF NOT EXISTS `book` (
  `id` int(11) NOT NULL ,
  `author_first_name` varchar(255) DEFAULT NULL,
  `author_last_name` varchar(255) DEFAULT NULL,
  `pictureurl` varchar(255) DEFAULT NULL,
  `publication_date` date NOT NULL,
  `synopsis` varchar(1000) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  PRIMARY KEY (id)
);


-- --------------------------------------------------------

--
-- Structure de la table `borrow`
--


CREATE TABLE IF NOT EXISTS `borrow` (
  `id` int(11) NOT NULL,
  `book_returned` bit(1) NOT NULL,
  `borrow_date` date NOT NULL,
  `extended_duration` bit(1) NOT NULL,
  `return_date` date NOT NULL,
  `book_id` int(11) NOT NULL,
  `library_id` int(11) NOT NULL,
  `registered_user_id` int(11) NOT NULL,
  PRIMARY KEY (id)
);


-- --------------------------------------------------------

--
-- Structure de la table `library`
--


CREATE TABLE IF NOT EXISTS `library` (
  `id` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (id),
  UNIQUE (name)
);


-- --------------------------------------------------------

--
-- Structure de la table `registered_user`
--


CREATE TABLE IF NOT EXISTS `registered_user` (
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
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `available_copie`
--
ALTER TABLE `available_copie`
  ADD CONSTRAINT `FK3cypfs2ivaoigc6eofcsu4mn5` FOREIGN KEY (`book_id`) REFERENCES `book` (`id`),
  ADD CONSTRAINT `FKps56tmxe7vcjvfdececjdqlhu` FOREIGN KEY (`library_id`) REFERENCES `library` (`id`);

--
-- Contraintes pour la table `borrow`
--
ALTER TABLE `borrow`
  ADD CONSTRAINT `FK4o1lami9uaoanv2yutyx4e5rx` FOREIGN KEY (`library_id`) REFERENCES `library` (`id`),
  ADD CONSTRAINT `FKgqh01ty3c1u7ja2rjdua05c36` FOREIGN KEY (`book_id`) REFERENCES `book` (`id`),
  ADD CONSTRAINT `FKmgvxfg4n767u28y21y5miyvm0` FOREIGN KEY (`registered_user_id`) REFERENCES `registered_user` (`id`);