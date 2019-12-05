CREATE DATABASE  IF NOT EXISTS `amt` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `amt`;
-- MySQL dump 10.13  Distrib 8.0.18, for Linux (x86_64)
--
-- Host: 127.0.0.1    Database: amt
-- ------------------------------------------------------
-- Server version	8.0.18

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `match`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8 */;
CREATE TABLE IF NOT EXISTS `match` (
  `id_match` int(11) NOT NULL AUTO_INCREMENT,
  `score1` int(11) DEFAULT NULL,
  `FK_team1` int(11) NOT NULL,
  `FK_team2` int(11) NOT NULL,
  `score2` int(11) DEFAULT NULL,
  `FK_stadium` int(11) NOT NULL,
  `FK_user` int(11) NOT NULL,
  PRIMARY KEY (`id_match`,`FK_team1`,`FK_team2`),
  KEY `fk_match_team_idx` (`FK_team1`),
  KEY `fk_match_team1_idx` (`FK_team2`),
  KEY `fk_match_stadium1_idx` (`FK_stadium`),
  KEY `fk_match_user_idx` (`FK_user`),
  CONSTRAINT `fk_match_stadium1` FOREIGN KEY (`FK_stadium`) REFERENCES `stadium` (`id_stadium`) ON DELETE CASCADE,
  CONSTRAINT `fk_match_user` FOREIGN KEY (`FK_user`) REFERENCES `user` (`id_user`) ON DELETE CASCADE,
  CONSTRAINT `fk_match_team` FOREIGN KEY (`FK_team1`) REFERENCES `team` (`id_team`) ON DELETE CASCADE,
  CONSTRAINT `fk_match_team1` FOREIGN KEY (`FK_team2`) REFERENCES `team` (`id_team`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Table structure for table `stadium`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8 */;
CREATE TABLE IF NOT EXISTS `stadium` (
  `id_stadium` int(11) NOT NULL AUTO_INCREMENT,
  `stadium_name` varchar(45) DEFAULT NULL,
  `stadium_location` varchar(45) DEFAULT NULL,
  `stadium_viewer_places` int(11) DEFAULT NULL,
  PRIMARY KEY (`id_stadium`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `team`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8 */;
CREATE TABLE IF NOT EXISTS `team` (
  `id_team` int(11) NOT NULL AUTO_INCREMENT,
  `team_name` varchar(45) DEFAULT NULL,
  `team_country` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id_team`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8 */;
CREATE TABLE IF NOT EXISTS `user` (
  `id_user` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(45) NOT NULL UNIQUE,
  `first_name` varchar(45) DEFAULT NULL,
  `last_name` varchar(45) DEFAULT NULL,
  `password` varchar(70) NOT NULL,
  `email` varchar(45) NOT NULL,
  `isAdmin` boolean NOT NULL DEFAULT 0,  
PRIMARY KEY (`id_user`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

