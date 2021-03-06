-- MySQL dump 10.13  Distrib 5.7.25, for Linux (x86_64)
--
-- Host: localhost    Database: sbsm
-- ------------------------------------------------------
-- Server version	5.7.25

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `tb_quartz_job`
--

DROP TABLE IF EXISTS `tb_quartz_job`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_quartz_job` (
  `id` bigint(20) NOT NULL COMMENT '非自增id',
  `job_name` varchar(20) NOT NULL COMMENT '任务名称',
  `job_group_name` varchar(45) NOT NULL COMMENT '任务组名称',
  `cron_expression` varchar(45) NOT NULL COMMENT '时间表达式',
  `quartz_class` varchar(100) NOT NULL COMMENT '任务类路径',
  `description` varchar(150) DEFAULT NULL,
  `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `index_job_name` (`job_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='定时任务';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_quartz_job`
--

LOCK TABLES `tb_quartz_job` WRITE;
/*!40000 ALTER TABLE `tb_quartz_job` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_quartz_job` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_user`
--

DROP TABLE IF EXISTS `tb_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_user` (
  `id` bigint(20) NOT NULL COMMENT '非自增id',
  `account` varchar(20) COLLATE utf8mb4_bin NOT NULL,
  `account_pinyin` varchar(100) COLLATE utf8mb4_bin NOT NULL COMMENT 'account的拼音，用于account排序。',
  `password` varchar(100) COLLATE utf8mb4_bin NOT NULL,
  `salt` varchar(50) COLLATE utf8mb4_bin NOT NULL COMMENT '盐值',
  `mobile` varchar(20) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '手机号,不可为null,默认值为'''',可用于登录',
  `email` varchar(50) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '邮箱,不可为null,默认值为'''',可用于登录',
  `user_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '用户是否删除。0：未删除；1：删除',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_account` (`account`),
  UNIQUE KEY `unique_mobile` (`mobile`),
  UNIQUE KEY `unique_email` (`email`),
  KEY `index_account_pinyin` (`account_pinyin`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_user`
--

LOCK TABLES `tb_user` WRITE;
/*!40000 ALTER TABLE `tb_user` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_user_info`
--

DROP TABLE IF EXISTS `tb_user_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_user_info` (
  `id` bigint(20) NOT NULL COMMENT '非自增id',
  `user_id` bigint(20) NOT NULL,
  `nickname` varchar(45) DEFAULT NULL,
  `avatar` varchar(300) DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `index_user_id` (`user_id`),
  KEY `index_updated_at` (`updated_at`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_user_info`
--

LOCK TABLES `tb_user_info` WRITE;
/*!40000 ALTER TABLE `tb_user_info` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_user_info` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-04-19 17:11:13
