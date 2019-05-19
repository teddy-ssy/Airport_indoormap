/*
MySQL Data Transfer
Source Host: localhost
Source Database: airport
Target Host: localhost
Target Database: airport
Date: 2015/5/30 7:03:02
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for fighttable
-- ----------------------------
CREATE TABLE `fighttable` (
  `code` varchar(8) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `startcity` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `arrivalcity` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `timetakeoff` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `realtakeoff` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `timearrival` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `realarrival` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `enter` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `baggage` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Table structure for friendlisttable
-- ----------------------------
CREATE TABLE `friendlisttable` (
  `masterId` int(11) NOT NULL DEFAULT '0',
  `friendId` int(11) NOT NULL DEFAULT '0',
  `friendName` varchar(100) DEFAULT NULL,
  `friendBirthYear` smallint(6) DEFAULT NULL,
  `friendBirthMonth` smallint(6) DEFAULT NULL,
  `friendBirthDay` smallint(6) DEFAULT NULL,
  `friendGender` smallint(6) DEFAULT NULL,
  `friendAvatarId` int(11) DEFAULT NULL,
  `friendIsOnline` int(11) DEFAULT NULL,
  `friendSignupTime` varchar(100) DEFAULT NULL,
  `friendHometown` varchar(100) DEFAULT NULL,
  `friendLocation` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`masterId`,`friendId`),
  CONSTRAINT `friendlisttable_ibfk_1` FOREIGN KEY (`masterId`) REFERENCES `usertable` (`userId`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Table structure for shoptable
-- ----------------------------
CREATE TABLE `shoptable` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `label` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `chinese` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `shoptype` int(11) NOT NULL,
  `phone` int(11) DEFAULT NULL,
  `introduction` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=75 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Table structure for unsendmsgs
-- ----------------------------
CREATE TABLE `unsendmsgs` (
  `senderId` int(11) NOT NULL DEFAULT '0',
  `receiverId` int(11) NOT NULL DEFAULT '0',
  `msg` text,
  `_datetime` datetime DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  PRIMARY KEY (`senderId`,`receiverId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Table structure for usertable
-- ----------------------------
CREATE TABLE `usertable` (
  `userId` int(11) NOT NULL AUTO_INCREMENT,
  `password` varchar(100) DEFAULT NULL,
  `userName` varchar(100) DEFAULT NULL,
  `birthYear` smallint(6) DEFAULT NULL,
  `birthMonth` smallint(6) DEFAULT NULL,
  `birthDay` smallint(6) DEFAULT NULL,
  `gender` smallint(6) DEFAULT NULL,
  `avatarId` int(11) DEFAULT NULL,
  `isOnline` int(11) DEFAULT NULL,
  `signupTime` varchar(100) DEFAULT NULL,
  `hometown` varchar(100) DEFAULT NULL,
  `location` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`userId`),
  UNIQUE KEY `userName` (`userName`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records 
-- ----------------------------
INSERT INTO `fighttable` VALUES ('BC4432', '北京', '福建', '20：00', '20：00', '22：30', '22：35', 'F1E1', 'F1-B1');
INSERT INTO `fighttable` VALUES ('BC7329', '北京', '敦煌', '20：00', '20：00', '22：30', '22：35', 'F1E1', 'F1-B1');
INSERT INTO `fighttable` VALUES ('BD3485', '冰岛', '北京', '7：00', '7：00', '10：00', '10：00', 'F1E4', 'F1-B2');
INSERT INTO `fighttable` VALUES ('BL3372', '北京', '柏林', '20：00', '20：00', '22：30', '22：35', 'F1E1', 'F1-B1');
INSERT INTO `fighttable` VALUES ('BL3423', '北京', '海地', '20：00', '20：00', '22：30', '22：35', 'F1E1', 'F1-B1');
INSERT INTO `fighttable` VALUES ('BU1783', '广东', '北京', '7：00', '7：00', '10：00', '10：00', 'F1E4', 'F1-B2');
INSERT INTO `fighttable` VALUES ('BX4774', '北京', '海南', '20：00', '20：00', '22：30', '22：35', 'F1E1', 'F1-B1');
INSERT INTO `fighttable` VALUES ('CA0964', '北京', '柏林', '20：00', '20：00', '22：30', '22：35', 'F1E1', 'F1-B1');
INSERT INTO `fighttable` VALUES ('CA2254', '北京', '浙江', '20：00', '20：00', '22：30', '22：35', 'F1E1', 'F1-B1');
INSERT INTO `fighttable` VALUES ('CA2314', '北京', '上海', '20：00', '20：00', '22：30', '22：35', 'F1E1', 'F1-B1');
INSERT INTO `fighttable` VALUES ('CA2341', '北京', '新疆', '6：00', '6：00', '9：00', '9：00', 'F1E2', 'F1-B1');
INSERT INTO `fighttable` VALUES ('CA4014', '北京', '成都', '20：00', '20：00', '22：30', '22：35', 'F1E1', 'F1-B1');
INSERT INTO `fighttable` VALUES ('CA4360', '罗马', '北京', '7：00', '7：00', '10：00', '10：00', 'F1E4', 'F1-B2');
INSERT INTO `fighttable` VALUES ('CA5562', '北京', '西雅图', '20：00', '20：00', '22：30', '22：35', 'F1E1', 'F1-B1');
INSERT INTO `fighttable` VALUES ('CA6953', '北京', '重庆', '20：00', '20：00', '22：30', '22：35', 'F1E1', 'F1-B1');
INSERT INTO `fighttable` VALUES ('CA7529', '北京', '甘肃', '20：00', '20：00', '22：30', '22：35', 'F1E1', 'F1-B1');
INSERT INTO `fighttable` VALUES ('CA8442', '北京', '佛罗伦撒', '20：00', '20：00', '22：30', '22：35', 'F1E1', 'F1-B1');
INSERT INTO `fighttable` VALUES ('CA9076', '北京', '布拉格', '20：00', '20：00', '22：30', '22：35', 'F1E1', 'F1-B1');
INSERT INTO `fighttable` VALUES ('CA9782', '北京', '天津', '20：00', '20：00', '22：30', '22：35', 'F1E1', 'F1-B1');
INSERT INTO `fighttable` VALUES ('CD3419', '北京', '泉州', '20：00', '20：00', '22：30', '22：35', 'F1E1', 'F1-B1');
INSERT INTO `fighttable` VALUES ('CD5743', '北京', '成都', '20：00', '20：00', '22：30', '22：35', 'F1E1', 'F1-B1');
INSERT INTO `fighttable` VALUES ('FE2456', '海南', '北京', '7：00', '7：00', '10：00', '10：00', 'F1E4', 'F1-B2');
INSERT INTO `fighttable` VALUES ('GG4362', '黑海', '北京', '7：00', '7：00', '10：00', '10：00', 'F1E4', 'F1-B2');
INSERT INTO `fighttable` VALUES ('GT7253', '九寨沟', '北京', '7：00', '7：00', '10：00', '10：00', 'F1E4', 'F1-B2');
INSERT INTO `fighttable` VALUES ('GU1543', '休斯顿', '北京', '7：00', '7：00', '10：00', '10：00', 'F1E4', 'F1-B2');
INSERT INTO `fighttable` VALUES ('HN1234', '纽约', '北京', '7：00', '7：00', '10：00', '10：00', 'F1E4', 'F1-B2');
INSERT INTO `fighttable` VALUES ('HU3107', '天津', '北京', '7：00', '7：00', '10：00', '10：00', 'F1-E4', 'F1-B2');
INSERT INTO `fighttable` VALUES ('HU3137', '巴黎', '北京', '7：00', '7：00', '10：00', '10：00', 'F1-E4', 'F1-B2');
INSERT INTO `fighttable` VALUES ('HU4367', '浙江', '北京', '7：00', '7：00', '10：00', '10：00', 'F1-E4', 'F1-B2');
INSERT INTO `fighttable` VALUES ('HU5614', '北京', '西安', '20：00', '20：00', '22：30', '22：35', 'F1-E1', 'F1-B1');
INSERT INTO `fighttable` VALUES ('HU6206', '北京', '海南', '20：00', '20：00', '22：30', '22：35', 'F1-E1', 'F1-B1');
INSERT INTO `fighttable` VALUES ('HU8469', '北京', '莫斯科', '20：00', '20：00', '22：30', '22：35', 'F1-E1', 'F1-B1');
INSERT INTO `fighttable` VALUES ('JF1236', '济州岛', '北京', '7：00', '7：00', '10：00', '10：00', 'F1-E4', 'F1-B2');
INSERT INTO `fighttable` VALUES ('JU2344', '北京', '成都', '20：00', '20：00', '22：30', '22：35', 'F1-E1', 'F1-B1');
INSERT INTO `fighttable` VALUES ('KT4414', '北京', '东京', '20：00', '20：00', '22：30', '22：35', 'F1-E1', 'F1-B1');
INSERT INTO `fighttable` VALUES ('LJ3412', '布拉格', '北京', '7：00', '7：00', '10：00', '10：00', 'F1-E4', 'F1-B2');
INSERT INTO `fighttable` VALUES ('OF4563', '朝鲜', '北京', '7：00', '7：00', '10：00', '10：00', 'F1-E4', 'F1-B2');
INSERT INTO `fighttable` VALUES ('PD2438', '北京', '上海', '20：00', '20：00', '22：30', '22：35', 'F1-E1', 'F1-B1');
INSERT INTO `fighttable` VALUES ('PF8345', '普吉岛', '北京', '7：00', '7：00', '10：00', '10：00', 'F1-E4', 'F1-B2');
INSERT INTO `fighttable` VALUES ('PS2345', '拉萨', '北京', '7：00', '7：00', '10：00', '10：00', 'F1-E4', 'F1-B2');
INSERT INTO `fighttable` VALUES ('QE1341', '海南', '北京', '7：00', '7：00', '10：00', '10：00', 'F1-E4', 'F1-B2');
INSERT INTO `fighttable` VALUES ('RD5543', '成都', '北京', '7：00', '7：00', '10：00', '10：00', 'F1-E4', 'F1-B2');
INSERT INTO `fighttable` VALUES ('RT4265', '江苏', '北京', '7：00', '7：00', '10：00', '10：00', 'F1-E4', 'F1-B2');
INSERT INTO `fighttable` VALUES ('SG1246', '上海', '北京', '7：00', '7：00', '10：00', '10：00', 'F1-E4', 'F1-B2');
INSERT INTO `fighttable` VALUES ('SY2346', '威尼斯', '北京', '7：00', '7：00', '10：00', '10：00', 'F1-E4', 'F1-B2');
INSERT INTO `fighttable` VALUES ('SZ6354', '北京', '文莱', '20：00', '20：00', '22：30', '22：35', 'F1-E1', 'F1-B1');
INSERT INTO `fighttable` VALUES ('UD4672', '北海道', '北京', '7：00', '7：00', '10：00', '10：00', 'F1-E4', 'F1-B2');
INSERT INTO `fighttable` VALUES ('VA4523', '大连', '北京', '7：00', '7：00', '10：00', '10：00', 'F1-E4', 'F1-B2');
INSERT INTO `fighttable` VALUES ('VG4623', '重庆', '北京', '7：00', '7：00', '10：00', '10：00', 'F1-E4', 'F1-B2');
INSERT INTO `fighttable` VALUES ('WE0076', '北京', '新加坡', '20：00', '20：00', '22：30', '22：35', 'F1-E1', 'F1-B1');
INSERT INTO `fighttable` VALUES ('WE5462', '北京', '香港', '20：00', '20：00', '22：30', '22：35', 'F1-E1', 'F1-B1');
INSERT INTO `fighttable` VALUES ('XE3185', '新疆', '北京', '7：00', '7：00', '10：00', '10：00', 'F1-E4', 'F1-B2');
INSERT INTO `shoptable` VALUES ('1', 'F1S1', 'nike店', '1', '1063009', 'hurrp up！');
INSERT INTO `shoptable` VALUES ('2', 'F1S2', '照相馆', '2', '220132122', 'canon');
INSERT INTO `shoptable` VALUES ('3', 'F1S3', '好伦哥', '2', '220132122', 'canon');
INSERT INTO `shoptable` VALUES ('4', 'F1S4', 'apple', '2', '220132122', 'canon');
INSERT INTO `shoptable` VALUES ('5', 'F1S5', '博士', '2', '220132122', 'canon');
INSERT INTO `shoptable` VALUES ('6', 'F1S6', '眼镜', '2', '220132122', 'canon');
INSERT INTO `shoptable` VALUES ('7', 'F1S7', '戴尔', '2', '220132122', 'canon');
INSERT INTO `shoptable` VALUES ('8', 'F1S8', '微软', '2', '220132122', 'canon');
INSERT INTO `shoptable` VALUES ('9', 'F1S9', '大众', '2', '220132122', 'canon');
INSERT INTO `shoptable` VALUES ('10', 'F1S10', '七匹狼', '2', '220132122', 'canon');
INSERT INTO `shoptable` VALUES ('11', 'F1S11', '路易威登', '2', '220132122', 'canon');
INSERT INTO `shoptable` VALUES ('12', 'F1S12', '谷歌', '2', '220132122', 'canon');
INSERT INTO `shoptable` VALUES ('13', 'F1S13', '一杯茶', '2', '220132122', 'canon');
INSERT INTO `shoptable` VALUES ('14', 'F1S14', '电影院', '2', '220132122', 'canon');
INSERT INTO `shoptable` VALUES ('15', 'F1S15', '湘菜馆', '2', '220132122', 'canon');
INSERT INTO `shoptable` VALUES ('16', 'F1S16', '便宜坊', '2', '220132122', 'canon');
INSERT INTO `shoptable` VALUES ('17', 'F1S17', '凡客', '2', '220132122', 'canon');
INSERT INTO `shoptable` VALUES ('18', 'F1S18', '京东', '2', '220132122', 'canon');
INSERT INTO `shoptable` VALUES ('19', 'F1S19', '麦当劳', '2', '220132122', 'canon');
INSERT INTO `shoptable` VALUES ('20', 'F1S20', '肯德基', '2', '220132122', 'canon');
INSERT INTO `shoptable` VALUES ('21', 'F1S21', '华润', '2', '220132122', 'canon');
INSERT INTO `shoptable` VALUES ('22', 'F1S22', '未来人类', '2', '220132122', 'canon');
INSERT INTO `shoptable` VALUES ('23', 'F1S23', '体验馆', '2', '220132122', 'canon');
INSERT INTO `shoptable` VALUES ('24', 'F1S24', '快餐', '2', '220132122', 'canon');
INSERT INTO `shoptable` VALUES ('25', 'F1S25', '中餐', '2', '220132122', 'canon');
INSERT INTO `shoptable` VALUES ('26', 'F1S26', '西餐', '2', '220132122', 'canon');
INSERT INTO `shoptable` VALUES ('27', 'F1S27', '书店', '2', '220132122', 'canon');
INSERT INTO `shoptable` VALUES ('28', 'F1S28', '按摩', '2', '220132122', 'canon');
INSERT INTO `shoptable` VALUES ('29', 'F1S29', '咖啡', '2', '220132122', 'canon');
INSERT INTO `shoptable` VALUES ('30', 'F1S30', '旅行包', '2', '220132122', 'canon');
INSERT INTO `shoptable` VALUES ('31', 'F1S31', '内衣', '2', '220132122', 'canon');
INSERT INTO `shoptable` VALUES ('32', 'F1S32', '西装', '2', '220132122', 'canon');
INSERT INTO `shoptable` VALUES ('33', 'F1S33', '阿迪达斯', '2', '220132122', 'canon');
INSERT INTO `shoptable` VALUES ('34', 'F1S34', '李宁', '2', '220132122', 'canon');
INSERT INTO `shoptable` VALUES ('35', 'F1S35', '匹克', '2', '220132122', 'canon');
INSERT INTO `shoptable` VALUES ('36', 'F1S36', '库奇', '2', '220132122', 'canon');
INSERT INTO `shoptable` VALUES ('37', 'F1S37', '阿玛尼', '2', '220132122', 'canon');
INSERT INTO `shoptable` VALUES ('38', 'F1S38', '香奈儿', '2', '220132122', 'canon');
INSERT INTO `shoptable` VALUES ('39', 'F1S39', '奇瑞', '2', '220132122', 'canon');
INSERT INTO `shoptable` VALUES ('40', 'F1S40', '东风', '2', '220132122', 'canon');
INSERT INTO `shoptable` VALUES ('41', 'F1S41', '优酷', '2', '220132122', 'canon');
INSERT INTO `shoptable` VALUES ('42', 'F1S42', '照相馆', '2', '220132122', 'canon');
INSERT INTO `shoptable` VALUES ('43', 'F2S1', '土豆', '2', '220132122', 'canon');
INSERT INTO `shoptable` VALUES ('44', 'F2S2', '烤肉', '2', '220132122', 'canon');
INSERT INTO `shoptable` VALUES ('45', 'F2S3', '烤鱼', '2', '220132122', 'canon');
INSERT INTO `shoptable` VALUES ('46', 'F2S4', '冒菜', '2', '220132122', 'canon');
INSERT INTO `shoptable` VALUES ('47', 'F2S5', '电影院', '2', '220132122', 'canon');
INSERT INTO `shoptable` VALUES ('48', 'F2S6', '华莱士', '2', '220132122', 'canon');
INSERT INTO `shoptable` VALUES ('49', 'F2S7', '江安神鸭', '2', '220132122', 'canon');
INSERT INTO `shoptable` VALUES ('50', 'F2S8', '西苑二餐', '2', '220132122', 'canon');
INSERT INTO `shoptable` VALUES ('51', 'F2S9', '东苑三餐', '2', '220132122', 'canon');
INSERT INTO `shoptable` VALUES ('52', 'F2S10', '烧鹅', '2', '220132122', 'canon');
INSERT INTO `shoptable` VALUES ('53', 'F2S11', '滴滴', '2', '220132122', 'canon');
INSERT INTO `shoptable` VALUES ('54', 'F2S12', '健身房', '2', '220132122', 'canon');
INSERT INTO `shoptable` VALUES ('55', 'F2S13', '帆布包', '2', '220132122', 'canon');
INSERT INTO `shoptable` VALUES ('56', 'F2S14', '帆布鞋', '2', '220132122', 'canon');
INSERT INTO `shoptable` VALUES ('57', 'F2S15', '狼爪', '2', '220132122', 'canon');
INSERT INTO `shoptable` VALUES ('58', 'F2S16', '宠物店', '2', '220132122', 'canon');
INSERT INTO `shoptable` VALUES ('59', 'F2S17', '书店', '2', '220132122', 'canon');
INSERT INTO `shoptable` VALUES ('60', 'F2S18', '电影院', '2', '220132122', 'canon');
INSERT INTO `shoptable` VALUES ('61', 'F2S19', '书吧', '2', '220132122', 'canon');
INSERT INTO `shoptable` VALUES ('62', 'F2S20', '按摩', '2', '220132122', 'canon');
INSERT INTO `shoptable` VALUES ('63', 'F2S21', '西装', '2', '220132122', 'canon');
INSERT INTO `shoptable` VALUES ('64', 'F2S22', '佯装', '2', '220132122', 'canon');
INSERT INTO `shoptable` VALUES ('65', 'F2S23', '中餐', '2', '220132122', 'canon');
INSERT INTO `shoptable` VALUES ('66', 'F2S24', '快餐', '2', '220132122', 'canon');
INSERT INTO `shoptable` VALUES ('67', 'F2S25', '纪念品', '2', '220132122', 'canon');
INSERT INTO `shoptable` VALUES ('68', 'F2S26', '书店', '2', '220132122', 'canon');
INSERT INTO `shoptable` VALUES ('69', 'F2S27', '照相馆', '2', '220132122', 'canon');
INSERT INTO `shoptable` VALUES ('70', 'F2S28', '大润发', '2', '220132122', 'canon');
INSERT INTO `shoptable` VALUES ('71', 'F2S29', '麦当劳', '2', '220132122', 'canon');
INSERT INTO `shoptable` VALUES ('72', 'F2S30', '素菜', '2', '220132122', 'canon');
INSERT INTO `shoptable` VALUES ('73', 'F2S31', '湘菜馆', '2', '220132122', 'canon');
INSERT INTO `shoptable` VALUES ('74', 'F2S32', '川菜馆', '2', '220132122', 'canon');
INSERT INTO `usertable` VALUES ('1', '12345', 'admin', '1994', '8', '15', '1', '1', '0', 'xx', 'xx', 'xx');
INSERT INTO `usertable` VALUES ('2', '12345', 'teddy', '2001', '8', '15', '0', '0', '0', 'xx', 'xx', 'xx');
INSERT INTO `usertable` VALUES ('3', '11111', 'ssy', '1994', '8', '15', '1', '1', '0', 'xx', 'xx', 'xx');
