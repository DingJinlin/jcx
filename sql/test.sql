/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50520
Source Host           : localhost:3306
Source Database       : test

Target Server Type    : MYSQL
Target Server Version : 50520
File Encoding         : 65001

Date: 2013-06-14 23:48:59
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `t_access`
-- ----------------------------
DROP TABLE IF EXISTS `t_access`;
CREATE TABLE `t_access` (
  `access_id` int(11) NOT NULL AUTO_INCREMENT,
  `suit` int(11) NOT NULL,
  `action` tinyint(4) NOT NULL,
  `date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `admin` int(11) NOT NULL,
  `count` int(11) NOT NULL,
  `price` float DEFAULT NULL COMMENT '售价/单价',
  PRIMARY KEY (`access_id`),
  UNIQUE KEY `FK_ID` (`access_id`),
  KEY `IND_ACTION` (`action`),
  KEY `FK_ACCESS_SUIT` (`suit`),
  KEY `FK_ACCESS_ADMIN` (`admin`),
  CONSTRAINT `FK_ACCESS_ADMIN` FOREIGN KEY (`admin`) REFERENCES `t_admin` (`id`),
  CONSTRAINT `FK_ACCESS_SUIT` FOREIGN KEY (`suit`) REFERENCES `t_suit` (`id`) ON DELETE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_access
-- ----------------------------
INSERT INTO `t_access` VALUES ('1', '5', '2', '2013-06-01 18:25:18', '1', '1', '150');
INSERT INTO `t_access` VALUES ('2', '5', '2', '2013-06-14 21:29:12', '1', '1', '150');
INSERT INTO `t_access` VALUES ('3', '6', '2', '2013-06-14 23:10:09', '1', '1', '150');

-- ----------------------------
-- Table structure for `t_admin`
-- ----------------------------
DROP TABLE IF EXISTS `t_admin`;
CREATE TABLE `t_admin` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(128) NOT NULL,
  `password` varchar(128) NOT NULL,
  `comment` varchar(1024) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UNIQUE_NAME` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_admin
-- ----------------------------
INSERT INTO `t_admin` VALUES ('1', 'fengxili', '123456', '');
INSERT INTO `t_admin` VALUES ('2', 'dingjinlin', '123456', '');

-- ----------------------------
-- Table structure for `t_depository`
-- ----------------------------
DROP TABLE IF EXISTS `t_depository`;
CREATE TABLE `t_depository` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `suit` int(11) NOT NULL,
  `amount` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `FK_ID` (`id`),
  KEY `FK_DEPOSITORY_SUIT` (`suit`),
  CONSTRAINT `FK_DEPOSITORY_SUIT` FOREIGN KEY (`suit`) REFERENCES `t_suit` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_depository
-- ----------------------------
INSERT INTO `t_depository` VALUES ('5', '5', '8');
INSERT INTO `t_depository` VALUES ('6', '6', '9');

-- ----------------------------
-- Table structure for `t_factory`
-- ----------------------------
DROP TABLE IF EXISTS `t_factory`;
CREATE TABLE `t_factory` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `abbreviation` varchar(100) NOT NULL,
  `address` varchar(500) DEFAULT NULL,
  `contactName` varchar(100) DEFAULT NULL,
  `contactPhone` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `FK_ID` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_factory
-- ----------------------------
INSERT INTO `t_factory` VALUES ('1', '香黛坊', 'XDF', '香黛坊', '香黛坊', '13800138000');
INSERT INTO `t_factory` VALUES ('2', '家宜地毯', 'JYDT', '家宜地毯', '家宜地毯', '13800138001');

-- ----------------------------
-- Table structure for `t_suit`
-- ----------------------------
DROP TABLE IF EXISTS `t_suit`;
CREATE TABLE `t_suit` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `internationalCode` bigint(20) NOT NULL,
  `type` int(11) NOT NULL,
  `name` varchar(100) NOT NULL,
  `abbreviation` varchar(100) NOT NULL,
  `factory` int(11) DEFAULT NULL,
  `standard` varchar(20) DEFAULT NULL,
  `unit` varchar(10) DEFAULT NULL,
  `purchasePrice` float DEFAULT NULL,
  `sellingPrice` float NOT NULL,
  `comment` varchar(1024) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `PK_ID` (`id`) USING BTREE,
  UNIQUE KEY `UNI_internationalCode` (`internationalCode`) USING BTREE,
  KEY `FK_FACTORY` (`factory`),
  KEY `FK_TYPE` (`type`),
  CONSTRAINT `FK_FACTORY` FOREIGN KEY (`factory`) REFERENCES `t_factory` (`id`),
  CONSTRAINT `FK_TYPE` FOREIGN KEY (`type`) REFERENCES `t_type` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_suit
-- ----------------------------
INSERT INTO `t_suit` VALUES ('5', '1365935749196', '1', '夏日风情', 'XRFQ', '1', '1', '座', '120', '150', null);
INSERT INTO `t_suit` VALUES ('6', '1365935749202', '1', '春暖花开', 'CNHK', '1', '1', '座', null, '150', null);

-- ----------------------------
-- Table structure for `t_type`
-- ----------------------------
DROP TABLE IF EXISTS `t_type`;
CREATE TABLE `t_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `abbreviation` varchar(100) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `PK_ID` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_type
-- ----------------------------
INSERT INTO `t_type` VALUES ('1', '沙发垫', 'SFD');
INSERT INTO `t_type` VALUES ('2', '地毯', 'DT');
