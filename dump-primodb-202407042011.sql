-- MySQL dump 10.13  Distrib 8.0.28, for Win64 (x86_64)
--
-- Host: localhost    Database: primodb
-- ------------------------------------------------------
-- Server version	8.0.28

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `access_token`
--

DROP TABLE IF EXISTS `access_token`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `access_token` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `token` tinytext NOT NULL,
  `user_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_kqodiiamededdp9947dtk9ua5` (`user_id`),
  CONSTRAINT `FKdxierwv95m6k533x9tejqmijp` FOREIGN KEY (`user_id`) REFERENCES `local_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `access_token`
--

LOCK TABLES `access_token` WRITE;
/*!40000 ALTER TABLE `access_token` DISABLE KEYS */;
/*!40000 ALTER TABLE `access_token` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `address`
--

DROP TABLE IF EXISTS `address`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `address` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `address_line_1` varchar(512) NOT NULL,
  `address_line_2` varchar(512) DEFAULT NULL,
  `city` varchar(255) NOT NULL,
  `country` varchar(75) NOT NULL,
  `user_id` bigint NOT NULL,
  `phone` varchar(100) NOT NULL,
  `CAP` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKkb7b5aavt0mlydpvdiuesa9r8` (`user_id`),
  CONSTRAINT `FKkb7b5aavt0mlydpvdiuesa9r8` FOREIGN KEY (`user_id`) REFERENCES `local_user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=237 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `address`
--

LOCK TABLES `address` WRITE;
/*!40000 ALTER TABLE `address` DISABLE KEYS */;
INSERT INTO `address` VALUES (234,'viale Kennedy 27',NULL,'soverello','calabria',1,'3519666888',88103),(235,'viale Kennedy 58','','soverello','calabria',2,'3519666888',88103);
/*!40000 ALTER TABLE `address` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `audit_log`
--

DROP TABLE IF EXISTS `audit_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `audit_log` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `method` varchar(255) DEFAULT NULL,
  `request_body` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci,
  `response_body` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci,
  `response_status` int NOT NULL,
  `timestamp` datetime(6) DEFAULT NULL,
  `uri` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `audit_log`
--

LOCK TABLES `audit_log` WRITE;
/*!40000 ALTER TABLE `audit_log` DISABLE KEYS */;
INSERT INTO `audit_log` VALUES (1,'POST','unical.informatica.it.enterpriseapplicationbackend.api.model.LoginBody@40d8ae82','<200 OK OK,unical.informatica.it.enterpriseapplicationbackend.api.model.LoginResponse@14a84185,[]>',200,'2024-07-04 19:17:01.676012','/auth/login'),(2,'GET','unical.informatica.it.enterpriseapplicationbackend.model.LocalUser@1b7201c5',NULL,0,'2024-07-04 19:17:02.579065','/wishlists/public'),(3,'GET','unical.informatica.it.enterpriseapplicationbackend.model.LocalUser@71258331',NULL,0,'2024-07-04 19:17:02.579065','/wishlists/shared'),(4,'GET','unical.informatica.it.enterpriseapplicationbackend.model.LocalUser@63caefde','<200 OK OK,[unical.informatica.it.enterpriseapplicationbackend.model.Wishlist@6a8961c3],[]>',200,'2024-07-04 19:17:02.579065','/wishlists/all'),(5,'GET','',NULL,0,'2024-07-04 19:17:04.212826','/product/all'),(6,'GET','',NULL,0,'2024-07-04 19:17:34.563873','/product/all'),(7,'GET','',NULL,0,'2024-07-04 19:18:05.133419','/product/all'),(8,'GET','',NULL,0,'2024-07-04 19:18:35.527732','/product/all'),(9,'GET','','[unical.informatica.it.enterpriseapplicationbackend.model.Product@56fcaf2a, unical.informatica.it.enterpriseapplicationbackend.model.Product@579fed63, unical.informatica.it.enterpriseapplicationbackend.model.Product@5d344978, unical.informatica.it.enterpriseapplicationbackend.model.Product@5a565fdf, unical.informatica.it.enterpriseapplicationbackend.model.Product@73206c17, unical.informatica.it.enterpriseapplicationbackend.model.Product@8cfaaf, unical.informatica.it.enterpriseapplicationbackend.model.Product@4bafe948]',200,'2024-07-04 19:19:06.172450','/product/all'),(10,'GET','','[unical.informatica.it.enterpriseapplicationbackend.model.Product@68133d7, unical.informatica.it.enterpriseapplicationbackend.model.Product@57acd89b, unical.informatica.it.enterpriseapplicationbackend.model.Product@75be8d9a, unical.informatica.it.enterpriseapplicationbackend.model.Product@78685bda, unical.informatica.it.enterpriseapplicationbackend.model.Product@3df2e555, unical.informatica.it.enterpriseapplicationbackend.model.Product@41cda4f7, unical.informatica.it.enterpriseapplicationbackend.model.Product@1c43047c]',200,'2024-07-04 19:19:21.793732','/product/all'),(11,'GET','','[unical.informatica.it.enterpriseapplicationbackend.model.Product@75d9a4f9, unical.informatica.it.enterpriseapplicationbackend.model.Product@55dd3be9, unical.informatica.it.enterpriseapplicationbackend.model.Product@3cee061, unical.informatica.it.enterpriseapplicationbackend.model.Product@73e3f485, unical.informatica.it.enterpriseapplicationbackend.model.Product@306db119, unical.informatica.it.enterpriseapplicationbackend.model.Product@4a057f3a, unical.informatica.it.enterpriseapplicationbackend.model.Product@28c42955]',200,'2024-07-04 19:19:52.695833','/product/all'),(12,'POST','unical.informatica.it.enterpriseapplicationbackend.api.model.LoginBody@71cb19eb','<200 OK OK,unical.informatica.it.enterpriseapplicationbackend.api.model.LoginResponse@4368cc33,[]>',200,'2024-07-04 19:19:56.921922','/auth/login'),(13,'GET','unical.informatica.it.enterpriseapplicationbackend.model.LocalUser@387cc8ee',NULL,0,'2024-07-04 19:19:57.667324','/wishlists/public'),(14,'GET','unical.informatica.it.enterpriseapplicationbackend.model.LocalUser@4eee1b6e',NULL,0,'2024-07-04 19:19:57.667324','/wishlists/shared'),(15,'GET','unical.informatica.it.enterpriseapplicationbackend.model.LocalUser@7d029363','<200 OK OK,[unical.informatica.it.enterpriseapplicationbackend.model.dto.WishlistResponseDTO@ce7020a],[]>',200,'2024-07-04 19:19:57.667324','/wishlists/all'),(16,'GET','unical.informatica.it.enterpriseapplicationbackend.model.LocalUser@7f73d44a','<200 OK OK,[unical.informatica.it.enterpriseapplicationbackend.model.dto.WishlistSharedUserDTO@3ffd1f5a],[]>',200,'2024-07-04 19:20:00.810339','/wishlists/shared'),(17,'GET','unical.informatica.it.enterpriseapplicationbackend.model.LocalUser@2f2f7ee6',NULL,0,'2024-07-04 19:20:00.832923','/wishlists/all'),(18,'GET','unical.informatica.it.enterpriseapplicationbackend.model.LocalUser@7b33a3fa','<200 OK OK,[unical.informatica.it.enterpriseapplicationbackend.model.Wishlist@3311b5b1],[]>',200,'2024-07-04 19:20:00.842787','/wishlists/public'),(19,'GET','unical.informatica.it.enterpriseapplicationbackend.model.LocalUser@2c02da1c',NULL,0,'2024-07-04 19:20:03.899196','/wishlists/shared'),(20,'GET','unical.informatica.it.enterpriseapplicationbackend.model.LocalUser@4e003f76','<200 OK OK,[unical.informatica.it.enterpriseapplicationbackend.model.dto.WishlistSharedUserDTO@991efed],[]>',200,'2024-07-04 19:20:03.912500','/wishlists/all'),(21,'GET','unical.informatica.it.enterpriseapplicationbackend.model.LocalUser@283cb43b','<200 OK OK,[unical.informatica.it.enterpriseapplicationbackend.model.Wishlist@1f2c3238],[]>',200,'2024-07-04 19:20:03.924618','/wishlists/public'),(22,'GET','unical.informatica.it.enterpriseapplicationbackend.model.LocalUser@20a9c7d5',NULL,0,'2024-07-04 19:20:08.441736','/wishlists/public'),(23,'GET','unical.informatica.it.enterpriseapplicationbackend.model.LocalUser@46db9916','<200 OK OK,[unical.informatica.it.enterpriseapplicationbackend.model.Wishlist@79f6c0be],[]>',200,'2024-07-04 19:20:08.442737','/wishlists/shared'),(24,'GET','unical.informatica.it.enterpriseapplicationbackend.model.LocalUser@79fef16','<200 OK OK,[unical.informatica.it.enterpriseapplicationbackend.model.dto.WishlistResponseDTO@3367a325],[]>',200,'2024-07-04 19:20:08.455606','/wishlists/all'),(25,'POST','unical.informatica.it.enterpriseapplicationbackend.model.LocalUser@79aa54fd','unical.informatica.it.enterpriseapplicationbackend.model.WebOrder@664fee3f',200,'2024-07-04 19:20:10.403751','/orders/checkout'),(26,'POST','unical.informatica.it.enterpriseapplicationbackend.model.LocalUser@13b73672','unical.informatica.it.enterpriseapplicationbackend.model.WebOrder@4acf3777',200,'2024-07-04 19:20:16.665750','/orders/checkout'),(27,'GET','unical.informatica.it.enterpriseapplicationbackend.model.LocalUser@dbe7399',NULL,0,'2024-07-04 19:20:20.849072','/wishlists/shared'),(28,'GET','unical.informatica.it.enterpriseapplicationbackend.model.LocalUser@7a582c3f',NULL,0,'2024-07-04 19:20:20.849072','/wishlists/public'),(29,'GET','unical.informatica.it.enterpriseapplicationbackend.model.LocalUser@3cb1e58','<200 OK OK,[unical.informatica.it.enterpriseapplicationbackend.model.dto.WishlistSharedUserDTO@53321fae],[]>',200,'2024-07-04 19:20:20.849072','/wishlists/all'),(30,'GET','unical.informatica.it.enterpriseapplicationbackend.model.LocalUser@6db0e8a0','unical.informatica.it.enterpriseapplicationbackend.model.LocalUser@6db0e8a0',200,'2024-07-04 19:20:22.646253','/auth/me'),(31,'GET','','[unical.informatica.it.enterpriseapplicationbackend.model.Product@2d02b880, unical.informatica.it.enterpriseapplicationbackend.model.Product@24b48628, unical.informatica.it.enterpriseapplicationbackend.model.Product@190a45a, unical.informatica.it.enterpriseapplicationbackend.model.Product@6aaa7750, unical.informatica.it.enterpriseapplicationbackend.model.Product@505a6283, unical.informatica.it.enterpriseapplicationbackend.model.Product@2cefeafa, unical.informatica.it.enterpriseapplicationbackend.model.Product@372bd13e]',200,'2024-07-04 19:20:22.902712','/product/all'),(32,'GET','unical.informatica.it.enterpriseapplicationbackend.model.LocalUser@3c5f348c',NULL,0,'2024-07-04 19:20:24.734981','/wishlists/shared'),(33,'GET','unical.informatica.it.enterpriseapplicationbackend.model.LocalUser@88608d4',NULL,0,'2024-07-04 19:20:24.734981','/wishlists/all'),(34,'GET','unical.informatica.it.enterpriseapplicationbackend.model.LocalUser@75d70049','<200 OK OK,[unical.informatica.it.enterpriseapplicationbackend.model.dto.WishlistResponseDTO@69da70cb],[]>',200,'2024-07-04 19:20:24.734981','/wishlists/public'),(35,'GET','','[unical.informatica.it.enterpriseapplicationbackend.model.Product@678ec445, unical.informatica.it.enterpriseapplicationbackend.model.Product@10622305, unical.informatica.it.enterpriseapplicationbackend.model.Product@4454c4cc, unical.informatica.it.enterpriseapplicationbackend.model.Product@178824b3, unical.informatica.it.enterpriseapplicationbackend.model.Product@4c191beb, unical.informatica.it.enterpriseapplicationbackend.model.Product@e6cba07, unical.informatica.it.enterpriseapplicationbackend.model.Product@44d063cf]',200,'2024-07-04 19:20:26.728144','/product/all');
/*!40000 ALTER TABLE `audit_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `inventory`
--

DROP TABLE IF EXISTS `inventory`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `inventory` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `quantity` int NOT NULL,
  `product_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKp7gj4l80fx8v0uap3b2crjwp5` (`product_id`),
  CONSTRAINT `FKp7gj4l80fx8v0uap3b2crjwp5` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `inventory`
--

LOCK TABLES `inventory` WRITE;
/*!40000 ALTER TABLE `inventory` DISABLE KEYS */;
/*!40000 ALTER TABLE `inventory` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `local_user`
--

DROP TABLE IF EXISTS `local_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `local_user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `email` varchar(320) NOT NULL,
  `email_verified` bit(1) NOT NULL,
  `first_name` varchar(255) NOT NULL,
  `last_name` varchar(255) NOT NULL,
  `password` varchar(1000) NOT NULL,
  `username` varchar(255) NOT NULL,
  `role` bit(1) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_46f7ufu7j9nkhuyfly98to4u1` (`email`),
  UNIQUE KEY `UK_93d93k106ik2383youkc9bixl` (`username`),
  CONSTRAINT `local_user_chk_1` CHECK ((`role` between 0 and 1))
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `local_user`
--

LOCK TABLES `local_user` WRITE;
/*!40000 ALTER TABLE `local_user` DISABLE KEYS */;
INSERT INTO `local_user` VALUES (1,'prova@gmail.com',_binary '','gino','cammino','$2a$11$Q5IGF30Qx9K4/EjCjFJRrOigsw8NL.6QlTsc3CDNQa9OsRaBKpEre','Admin',_binary ''),(2,'shakksjdhsjs@gmail.com',_binary '','aziz','Abdel ','$2a$11$vB2yn36mcYKGkfGO4LMX..UX3GzdJudG17kFEp1uAzWV1gEJxSC1u','aziz',_binary '\0');
/*!40000 ALTER TABLE `local_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_quantity`
--

DROP TABLE IF EXISTS `order_quantity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order_quantity` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `quantity` int NOT NULL,
  `order_id` bigint NOT NULL,
  `product_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK21jbtplppyv8a1tkd0p5ecuab` (`order_id`),
  CONSTRAINT `FK21jbtplppyv8a1tkd0p5ecuab` FOREIGN KEY (`order_id`) REFERENCES `web_order` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=516 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_quantity`
--

LOCK TABLES `order_quantity` WRITE;
/*!40000 ALTER TABLE `order_quantity` DISABLE KEYS */;
/*!40000 ALTER TABLE `order_quantity` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `price` double NOT NULL,
  `img_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `available` bit(1) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_jmivyxk9rmgysrmsqw15lqr5b` (`title`)
) ENGINE=InnoDB AUTO_INCREMENT=243239 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` VALUES (66,'Descrizione prodotto 10','Prodotto 10',12.99,'http://192.168.1.160/img/10.jpg',_binary ''),(4354,'Descrizione prodotto 2','Prodotto 2',15.99,'http://192.168.1.160/img/2.jpg',_binary ''),(5454,'Descrizione prodotto 3','Prodotto 3',20.99,'http://192.168.1.160/img/3.jpg',_binary ''),(6576,'Descrizione prodotto 4','Prodotto 4',12.99,'http://192.168.1.160/img/4.webp',_binary '\0'),(243234,'Descrizione prodotto 1','Prodotto 1',10.99,'http://192.168.1.160/img/1.jpg',_binary ''),(243237,'Descrizione prodotto 9','Prodotto 9',15.99,'http://192.168.1.160/img/9.jpg',_binary ''),(243238,'descrizione prodotto prova ','prodotto prova ',10.99,'http://192.168.1.160/img/8.jpg',_binary '');
/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `refresh_token`
--

DROP TABLE IF EXISTS `refresh_token`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `refresh_token` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `token` tinytext NOT NULL,
  `user_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_f95ixxe7pa48ryn1awmh2evt7` (`user_id`),
  CONSTRAINT `FKs0w324tdyvb27aidxujsa81sy` FOREIGN KEY (`user_id`) REFERENCES `local_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `refresh_token`
--

LOCK TABLES `refresh_token` WRITE;
/*!40000 ALTER TABLE `refresh_token` DISABLE KEYS */;
/*!40000 ALTER TABLE `refresh_token` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `verification_token`
--

DROP TABLE IF EXISTS `verification_token`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `verification_token` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_timestamp` datetime(6) NOT NULL,
  `token` tinytext NOT NULL,
  `user_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK8tx2aau9yc0gsxb82192wkyyl` (`user_id`),
  CONSTRAINT `FK8tx2aau9yc0gsxb82192wkyyl` FOREIGN KEY (`user_id`) REFERENCES `local_user` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `verification_token`
--

LOCK TABLES `verification_token` WRITE;
/*!40000 ALTER TABLE `verification_token` DISABLE KEYS */;
/*!40000 ALTER TABLE `verification_token` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `web_order`
--

DROP TABLE IF EXISTS `web_order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `web_order` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `address_id` bigint NOT NULL,
  `user_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK65jlvhv84w95l6dimcc1p6hqr` (`address_id`),
  KEY `FK8mvneqqd44higf18x0m67bg29` (`user_id`),
  CONSTRAINT `FK65jlvhv84w95l6dimcc1p6hqr` FOREIGN KEY (`address_id`) REFERENCES `address` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK8mvneqqd44higf18x0m67bg29` FOREIGN KEY (`user_id`) REFERENCES `local_user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=255 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `web_order`
--

LOCK TABLES `web_order` WRITE;
/*!40000 ALTER TABLE `web_order` DISABLE KEYS */;
INSERT INTO `web_order` VALUES (239,235,2),(247,235,2),(248,235,2),(249,235,2),(251,235,2),(252,235,2),(253,235,2),(254,235,2);
/*!40000 ALTER TABLE `web_order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `web_order_quantities`
--

DROP TABLE IF EXISTS `web_order_quantities`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `web_order_quantities` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `quantity` int NOT NULL,
  `order_id` bigint NOT NULL,
  `product_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK654x9lb2ii9jrhsriicg518iw` (`order_id`),
  KEY `FKi7eexulg463xqvxgykc3qqx0a` (`product_id`),
  CONSTRAINT `FK654x9lb2ii9jrhsriicg518iw` FOREIGN KEY (`order_id`) REFERENCES `web_order` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FKi7eexulg463xqvxgykc3qqx0a` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=536 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `web_order_quantities`
--

LOCK TABLES `web_order_quantities` WRITE;
/*!40000 ALTER TABLE `web_order_quantities` DISABLE KEYS */;
INSERT INTO `web_order_quantities` VALUES (517,4,239,66),(523,3,247,66),(527,3,249,6576),(528,1,249,4354),(531,2,251,243237),(532,1,251,4354),(533,1,252,66),(534,2,253,66),(535,2,254,66);
/*!40000 ALTER TABLE `web_order_quantities` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `wishlist`
--

DROP TABLE IF EXISTS `wishlist`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `wishlist` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `visibility` enum('PUBLIC','PRIVATE','SHARED') NOT NULL,
  `user_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKupvkyp8adfcbydsbd2fajp8a` (`user_id`),
  CONSTRAINT `FKupvkyp8adfcbydsbd2fajp8a` FOREIGN KEY (`user_id`) REFERENCES `local_user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `wishlist`
--

LOCK TABLES `wishlist` WRITE;
/*!40000 ALTER TABLE `wishlist` DISABLE KEYS */;
INSERT INTO `wishlist` VALUES (3,'prova2','PRIVATE',2),(4,'prova3','SHARED',1),(5,'prova4','PUBLIC',1);
/*!40000 ALTER TABLE `wishlist` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `visibility_change_trigger` AFTER UPDATE ON `wishlist` FOR EACH ROW BEGIN
    IF OLD.visibility = 'SHARED' AND NEW.visibility <> 'SHARED' THEN
        DELETE FROM wishlist_shared_users WHERE wishlist_id = OLD.id;
    END IF;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `wishlist_deletion_trigger` AFTER DELETE ON `wishlist` FOR EACH ROW BEGIN
    DELETE FROM wishlist_shared_users WHERE wishlist_id = OLD.id;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `wishlist_products`
--

DROP TABLE IF EXISTS `wishlist_products`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `wishlist_products` (
  `wishlist_id` bigint NOT NULL,
  `product_id` bigint NOT NULL,
  `id` bigint NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`),
  KEY `FKfx1kub09qhl8g1w6j563ghgy0` (`product_id`),
  KEY `FKhlq0ylq5sxd70s0pembuumc1d` (`wishlist_id`),
  CONSTRAINT `FKfx1kub09qhl8g1w6j563ghgy0` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FKhlq0ylq5sxd70s0pembuumc1d` FOREIGN KEY (`wishlist_id`) REFERENCES `wishlist` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `wishlist_products`
--

LOCK TABLES `wishlist_products` WRITE;
/*!40000 ALTER TABLE `wishlist_products` DISABLE KEYS */;
INSERT INTO `wishlist_products` VALUES (5,66,9);
/*!40000 ALTER TABLE `wishlist_products` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `wishlist_shared_users`
--

DROP TABLE IF EXISTS `wishlist_shared_users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `wishlist_shared_users` (
  `wishlist_id` bigint NOT NULL,
  `user_id` bigint NOT NULL,
  `Id` bigint NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`Id`),
  KEY `FKl70rlt8m6oqnixwsgy6680t9b` (`user_id`),
  KEY `FKe3irgc7t49rcuq0f0txxbpb9o` (`wishlist_id`),
  CONSTRAINT `FKe3irgc7t49rcuq0f0txxbpb9o` FOREIGN KEY (`wishlist_id`) REFERENCES `wishlist` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FKl70rlt8m6oqnixwsgy6680t9b` FOREIGN KEY (`user_id`) REFERENCES `local_user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `wishlist_shared_users`
--

LOCK TABLES `wishlist_shared_users` WRITE;
/*!40000 ALTER TABLE `wishlist_shared_users` DISABLE KEYS */;
INSERT INTO `wishlist_shared_users` VALUES (4,2,1);
/*!40000 ALTER TABLE `wishlist_shared_users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'primodb'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-07-04 20:11:22
