-- MySQL dump 10.13  Distrib 8.0.38, for Win64 (x86_64)
--
-- Host: localhost    Database: elearn
-- ------------------------------------------------------
-- Server version	8.0.37

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
-- Table structure for table `examination_scores`
--

DROP TABLE IF EXISTS `examination_scores`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `examination_scores` (
  `exam_id` int NOT NULL AUTO_INCREMENT,
  `user_id` int DEFAULT NULL,
  `exam_name` varchar(255) DEFAULT NULL,
  `score` float DEFAULT NULL,
  `exam_date` date DEFAULT NULL,
  PRIMARY KEY (`exam_id`),
  KEY `examination_scores_ibfk_1` (`user_id`),
  CONSTRAINT `examination_scores_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `examination_scores`
--

LOCK TABLES `examination_scores` WRITE;
/*!40000 ALTER TABLE `examination_scores` DISABLE KEYS */;
INSERT INTO `examination_scores` VALUES (3,3,'History Exam',78,'2023-08-03'),(6,3,'History Exam',78,'2023-08-03'),(7,3,'English Language',78,'2023-08-03'),(8,3,'History',40,'2023-08-03'),(9,4,'History',60,'2023-08-03'),(10,4,'English Language',80,'2023-08-03'),(11,3,'Science',30,'2023-08-03');
/*!40000 ALTER TABLE `examination_scores` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `quiz_results`
--

DROP TABLE IF EXISTS `quiz_results`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `quiz_results` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `quiz_name` varchar(255) NOT NULL,
  `score` int NOT NULL,
  `timestamp` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `quiz_results_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `quiz_results`
--

LOCK TABLES `quiz_results` WRITE;
/*!40000 ALTER TABLE `quiz_results` DISABLE KEYS */;
INSERT INTO `quiz_results` VALUES (1,3,'Science Quiz',2,'2024-08-12 00:13:29'),(2,3,'Science Quiz',0,'2024-08-13 11:45:23'),(4,3,'Science Quiz',0,'2024-08-15 16:37:31'),(5,3,'Science Quiz',0,'2024-08-16 14:08:57');
/*!40000 ALTER TABLE `quiz_results` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `resources`
--

DROP TABLE IF EXISTS `resources`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `resources` (
  `resource_id` int NOT NULL AUTO_INCREMENT,
  `resource_name` varchar(255) DEFAULT NULL,
  `resource_type` varchar(100) DEFAULT NULL,
  `content` text,
  `pdf_path` varchar(255) DEFAULT NULL,
  `related_exam` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`resource_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `resources`
--

LOCK TABLES `resources` WRITE;
/*!40000 ALTER TABLE `resources` DISABLE KEYS */;
INSERT INTO `resources` VALUES (1,'Chemistry','Science','aki ola			',NULL,'Science'),(2,'','','',NULL,'English Language'),(3,'iweiwe','weyiri','qjkeb',NULL,'English Language'),(4,'EW','FWEF','FWE',NULL,'English Language'),(5,'awdw','History','wdawd','English Language',''),(6,'wf','History','wef','English Language','');
/*!40000 ALTER TABLE `resources` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `underperforming_students`
--

DROP TABLE IF EXISTS `underperforming_students`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `underperforming_students` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int DEFAULT NULL,
  `exam_name` varchar(255) DEFAULT NULL,
  `score` float DEFAULT NULL,
  `exam_date` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `underperforming_students`
--

LOCK TABLES `underperforming_students` WRITE;
/*!40000 ALTER TABLE `underperforming_students` DISABLE KEYS */;
INSERT INTO `underperforming_students` VALUES (1,3,'Science',30,'2023-08-03');
/*!40000 ALTER TABLE `underperforming_students` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `user_id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL,
  `role` enum('teacher','student') NOT NULL,
  `email` varchar(100) DEFAULT NULL,
  `full_name` varchar(100) DEFAULT NULL,
  `gender` enum('male','female') DEFAULT NULL,
  `phone_number` varchar(15) DEFAULT NULL,
  `level` enum('100','200','300') DEFAULT NULL,
  `department` varchar(255) DEFAULT NULL,
  `registration_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (3,'Pius','0000','student','pius@gmail.com','Pumpuni Pius','male','030909090','100','Visual Arts','2024-08-06 20:02:04'),(4,'Sally','1234','student','sally@gmail.com','Salia Fati','female','0509939393','100','Science','2024-08-07 10:54:25'),(5,'Mubarick','0000','student','muba@yahoo.com','Mubarick','male','0505505050','100','Visual Arts','2024-08-09 15:33:10'),(6,'JaneDoe','password123','teacher','janedoe@example.com','Jane Doe','female','1234567890','200','Science','2024-08-10 17:40:13'),(7,'JohnSmith','securepass','teacher','johnsmith@example.com','John Smith','male','0987654321','300','Visual Arts','2024-08-10 17:40:13');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-08-27 22:19:17
