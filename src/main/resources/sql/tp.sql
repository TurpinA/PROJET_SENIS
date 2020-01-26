-- phpMyAdmin SQL Dump
-- version 4.7.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jan 06, 2020 at 01:16 AM
-- Server version: 5.7.17
-- PHP Version: 5.6.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `tp`
--

-- --------------------------------------------------------

--
-- Table structure for table `article`
--

CREATE TABLE `article` (
  `ID` int(11) NOT NULL,
  `nom` varchar(255) NOT NULL,
  `reference` varchar(255) NOT NULL,
  `prix` double NOT NULL,
  `quantite` int(11) NOT NULL,
  `description` longtext NOT NULL,
  `image` longblob,
  `rayon_ID` int(11) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `article`
--

INSERT INTO `article` (`ID`, `nom`, `reference`, `prix`, `quantite`, `description`, `image`, `rayon_ID`) VALUES
(3, 'Balle de massage', 'EUNC437CB437C', 9.9, 34, 'Description balle de massage', NULL, 2),
(1, 'Tapis', 'YVU65FD5YT', 70, 3, 'jhvivhgvhj', NULL, 2),
(2, 'Proteine', 'UONP988', 78, 43, 'jhbkj', NULL, 2),
(14, 'Raquette Pro', 'ICBENA8976', 96, 30, 'Raquette de pro !', NULL, 4),
(4, 'Bonnet de bain', 'NCU4E01247G', 15, 36, 'Description pour le bonnet de bain', NULL, 1),
(8, 'Iphone 11', 'AAAAAA854', 847.55, 3, 'DESCRIPTION TEST 2', NULL, 1);

-- --------------------------------------------------------

--
-- Table structure for table `magasin`
--

CREATE TABLE `magasin` (
  `ID` int(11) NOT NULL,
  `nom` varchar(255) NOT NULL,
  `adresse` varchar(255) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `magasin`
--

INSERT INTO `magasin` (`ID`, `nom`, `adresse`) VALUES
(1, 'Decathelon', '2 rue du cerisier');

-- --------------------------------------------------------

--
-- Table structure for table `rayon`
--

CREATE TABLE `rayon` (
  `ID` int(11) NOT NULL,
  `nom` varchar(255) NOT NULL,
  `magasin_ID` int(11) NOT NULL,
  `utilisateur_ID` int(11) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `rayon`
--

INSERT INTO `rayon` (`ID`, `nom`, `magasin_ID`, `utilisateur_ID`) VALUES
(2, 'Fitness', 1, 2),
(1, 'Natation', 1, 1),
(4, 'Tennis', 1, 20);

-- --------------------------------------------------------

--
-- Table structure for table `utilisateur`
--

CREATE TABLE `utilisateur` (
  `ID` int(11) NOT NULL,
  `nom` varchar(255) NOT NULL,
  `prenom` varchar(255) NOT NULL,
  `age` int(11) NOT NULL,
  `responsabilite` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `motdepasse` varchar(255) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `utilisateur`
--

INSERT INTO `utilisateur` (`ID`, `nom`, `prenom`, `age`, `responsabilite`, `email`, `motdepasse`) VALUES
(2, 'Turpin', 'Alexandre', 21, 'Vendeur', 'alex@mail.fr', 'password'),
(1, 'NomTest', 'PrenomTest', 99, 'Manager', 'admin', 'admin'),
(20, 'Dupas', 'Yoann', 21, 'Manager', 'yoann.dupas@outlook.fr', 'password');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `article`
--
ALTER TABLE `article`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `rayon_ID` (`rayon_ID`);

--
-- Indexes for table `magasin`
--
ALTER TABLE `magasin`
  ADD PRIMARY KEY (`ID`);

--
-- Indexes for table `rayon`
--
ALTER TABLE `rayon`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `magasin_ID` (`magasin_ID`),
  ADD KEY `utilisateur_ID` (`utilisateur_ID`);

--
-- Indexes for table `utilisateur`
--
ALTER TABLE `utilisateur`
  ADD PRIMARY KEY (`ID`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `article`
--
ALTER TABLE `article`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;
--
-- AUTO_INCREMENT for table `magasin`
--
ALTER TABLE `magasin`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT for table `rayon`
--
ALTER TABLE `rayon`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
--
-- AUTO_INCREMENT for table `utilisateur`
--
ALTER TABLE `utilisateur`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
