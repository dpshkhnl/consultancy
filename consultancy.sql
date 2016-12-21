/*
Navicat MySQL Data Transfer

Source Server         : Local Mysql
Source Server Version : 50626
Source Host           : localhost:3306
Source Database       : consultancy

Target Server Type    : MYSQL
Target Server Version : 50626
File Encoding         : 65001

Date: 2016-12-21 10:00:45
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for consultant
-- ----------------------------
DROP TABLE IF EXISTS `consultant`;
CREATE TABLE `consultant` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `website` varchar(255) DEFAULT NULL,
  `contact_person` varchar(255) DEFAULT NULL,
  `approch_count` int(11) DEFAULT NULL,
  `latlng` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `consultant_usert_id` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of consultant
-- ----------------------------

-- ----------------------------
-- Table structure for consultant_university_record
-- ----------------------------
DROP TABLE IF EXISTS `consultant_university_record`;
CREATE TABLE `consultant_university_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `university_id` int(11) NOT NULL,
  `consultant_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `university_id` (`university_id`),
  KEY `consultant_id` (`consultant_id`),
  CONSTRAINT `consultant_users` FOREIGN KEY (`consultant_id`) REFERENCES `consultant` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `university_consultant` FOREIGN KEY (`university_id`) REFERENCES `university` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of consultant_university_record
-- ----------------------------

-- ----------------------------
-- Table structure for country
-- ----------------------------
DROP TABLE IF EXISTS `country`;
CREATE TABLE `country` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of country
-- ----------------------------

-- ----------------------------
-- Table structure for student_consultant_record
-- ----------------------------
DROP TABLE IF EXISTS `student_consultant_record`;
CREATE TABLE `student_consultant_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `student_id` int(11) NOT NULL,
  `consultant_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `student_id` (`student_id`),
  KEY `consultant_id` (`consultant_id`),
  CONSTRAINT `consulttant_uresre` FOREIGN KEY (`consultant_id`) REFERENCES `consultant` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `studnet_contrusd` FOREIGN KEY (`student_id`) REFERENCES `student_record` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of student_consultant_record
-- ----------------------------

-- ----------------------------
-- Table structure for student_record
-- ----------------------------
DROP TABLE IF EXISTS `student_record`;
CREATE TABLE `student_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `student_name` varchar(255) NOT NULL,
  `parents_name` varchar(255) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `student_id` (`user_id`),
  CONSTRAINT `studnet_id` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of student_record
-- ----------------------------

-- ----------------------------
-- Table structure for university
-- ----------------------------
DROP TABLE IF EXISTS `university`;
CREATE TABLE `university` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `country_id` int(11) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `contact` varchar(255) DEFAULT NULL,
  `website` varchar(255) DEFAULT NULL,
  `latlng` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `country_id` (`country_id`),
  CONSTRAINT `country_id_refer` FOREIGN KEY (`country_id`) REFERENCES `country` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of university
-- ----------------------------

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role` varchar(100) NOT NULL,
  `fullname` varchar(255) NOT NULL,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `contact_no` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO `users` VALUES ('19', 'Admin', 'Admin', 'a', '1011:179a4294d2ca4066468bd6a2e5fea6df46f071bfb8be4d5c:e458b0b1e5e2e519253b7c7da640950ea5325423eca76106e25e47953f8ddf29a35efada49a8d19168387318eb2e3410e4d23e2c38d8580f2f6766dcc9efa171c3293011d1352eaef4e5c073a4', 'Active', 's', 's');
INSERT INTO `users` VALUES ('27', 'Student', 'Student', 's', '1011:713e0425940b4143c0ca756b587e6590e4d4843a556ea594:7498c0d2df31bc2c3e93050cff7f816716c2acc659240abeb7bb4e9690ffee418449453377b0dc18345eadb9598533f50d8b72e313626638fd2278b6c748f16671a81876eb34508b9f38cbedb0', 'Active', '', '');
INSERT INTO `users` VALUES ('28', 'Consultancy', 'Consultancy', 'c', '1011:179a4294d2ca4066468bd6a2e5fea6df46f071bfb8be4d5c:e458b0b1e5e2e519253b7c7da640950ea5325423eca76106e25e47953f8ddf29a35efada49a8d19168387318eb2e3410e4d23e2c38d8580f2f6766dcc9efa171c3293011d1352eaef4e5c073a4', 'Active', 'dpshkhnl@gmail.com', '9849719293');
