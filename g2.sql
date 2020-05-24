/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50725
Source Host           : localhost:3306
Source Database       : g2

Target Server Type    : MYSQL
Target Server Version : 50725
File Encoding         : 65001

Date: 2019-11-22 23:46:40
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `book`
-- ----------------------------
DROP TABLE IF EXISTS `book`;
CREATE TABLE `book` (
  `bookid` int(32) NOT NULL AUTO_INCREMENT,
  `name` varchar(64) DEFAULT NULL,
  `price` double(128,0) DEFAULT NULL,
  `keep` int(32) DEFAULT NULL,
  `zuozhe` varchar(32) DEFAULT NULL,
  `imgfile` varchar(256) DEFAULT NULL,
  `book_class` varchar(64) DEFAULT NULL,
  `ISBM` int(128) DEFAULT NULL,
  `state` varchar(32) DEFAULT NULL,
  `book_out` int(64) DEFAULT NULL,
  PRIMARY KEY (`bookid`)
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of book
-- ----------------------------
INSERT INTO `book` VALUES ('1', '数据库01', '30', '10', '作者a', '数据库.png', '22', null, '可借阅', '1');
INSERT INTO `book` VALUES ('2', 'JAVA01', '31', '110', '廖雪峰', 'javaee.png', '22', null, '可借阅', '0');
INSERT INTO `book` VALUES ('3', 'c++', '29', '91', '作者c', 'C++.png', '22', null, '可借阅', '0');
INSERT INTO `book` VALUES ('4', '数据库02', '35', '10', '作者d', '数据库.png', '22', null, '可借阅', '0');
INSERT INTO `book` VALUES ('5', '高数2', '25', '9', '作者e', '高数.png', '13', null, '可借阅', '0');
INSERT INTO `book` VALUES ('6', '大学英语', '23', '11', '作者f', '大学英语.png', '23', null, '可借阅', '0');
INSERT INTO `book` VALUES ('7', '马克思2', '30', '10', '作者1', '马克思.png', '1', null, '不可借阅', '6');
INSERT INTO `book` VALUES ('8', 'Springboot', '40', '11', '作者qq', 'SpringBoot.jpg', '22', null, '可借阅', '0');
INSERT INTO `book` VALUES ('9', '鬼谷子', '10', '6', '阿就是', '鬼谷子.png', '9', null, '可借阅', '2');
INSERT INTO `book` VALUES ('10', ' java', '10', '11', '廖雪峰', 'Java.png', '22', null, '可借阅', '0');
INSERT INTO `book` VALUES ('11', '墨菲定律', '10', '10', '廖雪峰', '墨菲定律.png', '10', null, '不可借阅', '1');
INSERT INTO `book` VALUES ('12', '大学英语', '10', '12', '廖雪峰', '大学英语.png', '23', null, '可借阅', '0');
INSERT INTO `book` VALUES ('13', 'c++', '10', '12', '廖雪峰', 'c++.png', '22', null, '可借阅', '0');
INSERT INTO `book` VALUES ('14', '高数1', '10', '12', '廖雪峰', '高数.png', '13', null, '可借阅', '0');
INSERT INTO `book` VALUES ('15', '安卓', '50', '22', '撒旦', '安卓.png', '22', null, '可借阅', '0');
INSERT INTO `book` VALUES ('16', '人间失格', '50', '10', '廖雪峰', '人间失格.png', '9', null, '可借阅', '3');
INSERT INTO `book` VALUES ('17', '女子', '50', '22', '廖雪峰', '女子.png', '9', null, '可借阅', '0');
INSERT INTO `book` VALUES ('18', '鬼谷子', '50', '9', '作者1', '鬼谷子.png', '9', null, '可借阅', '0');
INSERT INTO `book` VALUES ('19', '墨菲定律2', '19', '9', '莫非', '墨菲定律.png', '10', null, '可借阅', '0');
INSERT INTO `book` VALUES ('27', 'JAVA1', '50', '10', '作者1', 'Java.png', '22', null, '不可借阅', '0');
INSERT INTO `book` VALUES ('28', 'JAVA2', '10', '11', '廖雪峰', 'C++.png', '22', null, '可借阅', '0');
INSERT INTO `book` VALUES ('29', 'JAVA3', '10', '9', '廖雪峰', '高数.png', '13', null, '可借阅', '0');
INSERT INTO `book` VALUES ('31', '墨菲定律1', '50', '10', '22', '人间失格.png', '9', null, '可借阅', '0');
INSERT INTO `book` VALUES ('32', 'JAVAee', '10', '9', '廖雪峰', 'javaee.png', '22', null, '可借阅', '0');
INSERT INTO `book` VALUES ('33', '墨菲定律3', '29', '9', '22', '墨菲定律.png', '10', null, '可借阅', '0');
INSERT INTO `book` VALUES ('34', '墨菲定律4', '11', '10', '莫非', '墨菲定律.png', '5', null, '不可借阅', '2');
INSERT INTO `book` VALUES ('35', '明日之后', '20', '11', '小明', '1572933866(1).jpg', '4', null, '不可借阅', '1');
INSERT INTO `book` VALUES ('36', '文化教育1', '29', '11', '文化001', 'wh.png', '7', null, '可借阅', '1');
INSERT INTO `book` VALUES ('37', '文化教育2', '29', '22', '文化001', 'wh.png', '7', null, '可借阅', '0');

-- ----------------------------
-- Table structure for `book_keep`
-- ----------------------------
DROP TABLE IF EXISTS `book_keep`;
CREATE TABLE `book_keep` (
  `keep_id` int(32) NOT NULL AUTO_INCREMENT,
  `keep` int(64) DEFAULT NULL,
  `borrow_sum` int(64) DEFAULT NULL,
  PRIMARY KEY (`keep_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of book_keep
-- ----------------------------

-- ----------------------------
-- Table structure for `book_order`
-- ----------------------------
DROP TABLE IF EXISTS `book_order`;
CREATE TABLE `book_order` (
  `id` int(32) NOT NULL AUTO_INCREMENT,
  `order_name` varchar(64) DEFAULT NULL,
  `address` varchar(128) DEFAULT NULL,
  `phone` varchar(128) DEFAULT NULL,
  `date` varchar(128) DEFAULT NULL,
  `book_id` int(32) DEFAULT NULL,
  `order_id` int(128) DEFAULT NULL,
  `order_price` double(128,0) DEFAULT NULL,
  `returndate` varchar(128) DEFAULT NULL,
  `longtime` int(64) DEFAULT NULL,
  `state` varchar(64) DEFAULT NULL,
  `post` varchar(32) DEFAULT NULL,
  `order_cardid` int(32) DEFAULT NULL,
  `sum` int(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=101 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of book_order
-- ----------------------------
INSERT INTO `book_order` VALUES ('93', '小白', '广科广科广科', '1231313', '2019-11-22', null, '22201111', '283', null, null, '待收货', null, '2019005', null);
INSERT INTO `book_order` VALUES ('95', '小白', '广科广科广科', '1231313', '2019-11-22', null, '22201113', '237', null, null, '待发货', null, '2019005', null);
INSERT INTO `book_order` VALUES ('97', '小白', '广科广科广科', '1231313', '2019-11-22', '34', '22201114', '20', null, null, '待发货', null, '2019005', '1');
INSERT INTO `book_order` VALUES ('98', '小白', '广科广科广科', '1231313', '2019-11-22', '35', '12201114', '28', null, null, '交易成功', null, '2019005', '1');
INSERT INTO `book_order` VALUES ('99', 'aaa', '滴滴滴滴滴滴滴IDIDIDIDIDIDIDizhidizh', '1231313', '2019-11-22', null, '22202115', '239', null, null, '交易失败', null, '2019006', null);
INSERT INTO `book_order` VALUES ('100', 'aaa', '滴滴滴滴滴滴滴IDIDIDIDIDIDIDizhidizh', '1231313', '2019-11-22', null, '22202115', '251', null, null, '待发货', null, '2019006', null);

-- ----------------------------
-- Table structure for `book_recode`
-- ----------------------------
DROP TABLE IF EXISTS `book_recode`;
CREATE TABLE `book_recode` (
  `recode_id` int(32) NOT NULL AUTO_INCREMENT,
  `ISBM` int(64) DEFAULT NULL,
  `user_id` int(64) DEFAULT NULL,
  `book_name` varchar(64) DEFAULT NULL,
  `book_zuozhe` varchar(64) DEFAULT NULL,
  `borrow_date` date DEFAULT NULL,
  `return_date` date DEFAULT NULL,
  `fine` int(64) DEFAULT NULL,
  `overtime` date DEFAULT NULL,
  PRIMARY KEY (`recode_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of book_recode
-- ----------------------------

-- ----------------------------
-- Table structure for `card`
-- ----------------------------
DROP TABLE IF EXISTS `card`;
CREATE TABLE `card` (
  `id` int(32) NOT NULL AUTO_INCREMENT,
  `name` varchar(64) DEFAULT NULL,
  `sex` varchar(12) DEFAULT NULL,
  `address` varchar(128) DEFAULT NULL,
  `phone` varchar(64) DEFAULT NULL,
  `email` varchar(128) DEFAULT NULL,
  `age` int(64) DEFAULT NULL,
  `card_id` int(128) DEFAULT NULL,
  `card_date` varchar(128) DEFAULT NULL,
  `credit` varchar(32) DEFAULT NULL,
  `sum` int(32) DEFAULT NULL,
  `idname` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of card
-- ----------------------------
INSERT INTO `card` VALUES ('7', '小明22', '男', '广科', '1231313', '111@qq.com', '15', '2019000', '2019-11-03', '良好', '0', '1111111111111118');
INSERT INTO `card` VALUES ('9', '小明', '男', '北京', '1231313', '111@qq.com', '21', '2019001', '2019-11-10', '良好', '1', '1111111111111118X');
INSERT INTO `card` VALUES ('10', '小黑ee', null, '阿达是的', '1231313', '111@qq.com', null, '2019002', '2019-11-16', '良好', '0', null);
INSERT INTO `card` VALUES ('11', '小明', null, '广科', '10086', '111@qq.com', null, '2019003', '2019-11-16', '良好', '0', null);
INSERT INTO `card` VALUES ('12', '小黑', null, '广东省东莞市南城', '1231313', '111@qq.com', null, '2019004', '2019-11-20', '良好', '0', null);
INSERT INTO `card` VALUES ('20', '小白', null, '广科广科广科', '1231313', '111@qq.com', null, '2019005', '2019-11-22', '良好', '0', null);
INSERT INTO `card` VALUES ('21', 'aaa', null, '滴滴滴滴滴滴滴IDIDIDIDIDIDIDizhidizh', '1231313', '111@qq.com', null, '2019006', '2019-11-22', '良好', '0', null);

-- ----------------------------
-- Table structure for `cart_order`
-- ----------------------------
DROP TABLE IF EXISTS `cart_order`;
CREATE TABLE `cart_order` (
  `cart_id` int(32) NOT NULL AUTO_INCREMENT,
  `cart_book_name` varchar(64) DEFAULT NULL,
  `cart_date` varchar(128) DEFAULT NULL,
  `cart_book_id` int(32) DEFAULT NULL,
  `cart_book_order_price` double(128,0) DEFAULT NULL,
  `cart_state` varchar(64) DEFAULT NULL,
  `cart_order_id` int(32) DEFAULT NULL,
  `cart_sum` int(32) DEFAULT NULL,
  `cart_imgfile` varchar(256) DEFAULT NULL,
  `cart_cardid` int(255) DEFAULT NULL,
  `cart_one_price` double(128,0) DEFAULT NULL,
  PRIMARY KEY (`cart_id`)
) ENGINE=InnoDB AUTO_INCREMENT=111 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of cart_order
-- ----------------------------
INSERT INTO `cart_order` VALUES ('91', '明日之后', '2019-11-22', '35', '72', '已结算', '22201111', '4', '1572933866(1).jpg', '2019005', '20');
INSERT INTO `cart_order` VALUES ('92', '墨菲定律4', '2019-11-22', '34', '10', '已结算', '22201111', '1', '墨菲定律.png', '2019005', '11');
INSERT INTO `cart_order` VALUES ('93', '明日之后', '2019-11-22', '35', '18', '已结算', '22201111', '1', '1572933866(1).jpg', '2019005', '20');
INSERT INTO `cart_order` VALUES ('94', '文化教育1', '2019-11-22', '36', '52', '已结算', '22201113', '2', 'wh.png', '2019005', '29');
INSERT INTO `cart_order` VALUES ('95', '文化教育1', '2019-11-22', '36', '26', '已结算', '22201113', '1', 'wh.png', '2019005', '29');
INSERT INTO `cart_order` VALUES ('96', '明日之后', '2019-11-22', '35', '36', '已结算', '22201113', '2', '1572933866(1).jpg', '2019005', '20');
INSERT INTO `cart_order` VALUES ('97', '墨菲定律4', '2019-11-22', '34', '69', '已结算', '22201113', '7', '墨菲定律.png', '2019005', '11');
INSERT INTO `cart_order` VALUES ('98', '明日之后', '2019-11-22', '35', '18', '已结算', '22201113', '1', '1572933866(1).jpg', '2019005', '20');
INSERT INTO `cart_order` VALUES ('99', '文化教育1', '2019-11-22', '36', '26', '已结算', '22201113', '1', 'wh.png', '2019005', '29');
INSERT INTO `cart_order` VALUES ('100', '墨菲定律4', '2019-11-22', '34', '10', '已结算', '22201113', '1', '墨菲定律.png', '2019005', '11');
INSERT INTO `cart_order` VALUES ('101', '墨菲定律4', '2019-11-22', '34', '10', '已结算', '22201114', '1', '墨菲定律.png', '2019005', '11');
INSERT INTO `cart_order` VALUES ('102', '文化教育1', '2019-11-22', '36', '26', '已结算', '12201114', '1', 'wh.png', '2019005', '29');
INSERT INTO `cart_order` VALUES ('103', '文化教育2', '2019-11-22', '37', '26', '已结算', '22201114', '1', 'wh.png', '2019005', '29');
INSERT INTO `cart_order` VALUES ('104', '墨菲定律4', '2019-11-22', '34', '10', '购物车', '22201114', '1', '墨菲定律.png', '2019005', '11');
INSERT INTO `cart_order` VALUES ('105', '明日之后', '2019-11-22', '35', '18', '购物车', '12201114', '1', '1572933866(1).jpg', '2019005', '20');
INSERT INTO `cart_order` VALUES ('106', '墨菲定律4', '2019-11-22', '34', '59', '已结算', '22202115', '6', '墨菲定律.png', '2019006', '11');
INSERT INTO `cart_order` VALUES ('107', '女子', '2019-11-22', '17', '180', '已结算', '22202115', '4', '女子.png', '2019006', '50');
INSERT INTO `cart_order` VALUES ('108', '女子', '2019-11-22', '17', '180', '已结算', '22202115', '4', '女子.png', '2019006', '50');
INSERT INTO `cart_order` VALUES ('109', '鬼谷子', '2019-11-22', '18', '45', '已结算', '22202115', '1', '鬼谷子.png', '2019006', '50');
INSERT INTO `cart_order` VALUES ('110', '文化教育1', '2019-11-22', '36', '26', '已结算', '22202115', '1', 'wh.png', '2019006', '29');

-- ----------------------------
-- Table structure for `dict`
-- ----------------------------
DROP TABLE IF EXISTS `dict`;
CREATE TABLE `dict` (
  `dict_id` int(32) NOT NULL AUTO_INCREMENT,
  `dict_class` varchar(64) DEFAULT NULL,
  `class_id` int(32) DEFAULT NULL,
  PRIMARY KEY (`dict_id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dict
-- ----------------------------
INSERT INTO `dict` VALUES ('1', '马克思主义', '1');
INSERT INTO `dict` VALUES ('2', '哲学', '2');
INSERT INTO `dict` VALUES ('3', '社会科学总论', '3');
INSERT INTO `dict` VALUES ('4', '政治', '4');
INSERT INTO `dict` VALUES ('5', '军事', '5');
INSERT INTO `dict` VALUES ('6', '经济', '6');
INSERT INTO `dict` VALUES ('7', '文化科学', '7');
INSERT INTO `dict` VALUES ('8', '语言、文字', '8');
INSERT INTO `dict` VALUES ('9', '文学', '9');
INSERT INTO `dict` VALUES ('10', '艺术', '10');
INSERT INTO `dict` VALUES ('11', '历史、地理', '11');
INSERT INTO `dict` VALUES ('12', '自然科学总论', '12');
INSERT INTO `dict` VALUES ('13', ' 数理科学和化学', '13');
INSERT INTO `dict` VALUES ('14', '数理科学和化学', '14');
INSERT INTO `dict` VALUES ('15', '生物科学', '15');
INSERT INTO `dict` VALUES ('16', '医药、卫生', '16');
INSERT INTO `dict` VALUES ('17', '农业科学', '17');
INSERT INTO `dict` VALUES ('18', '工业技术', '18');
INSERT INTO `dict` VALUES ('19', '交通运输', '19');
INSERT INTO `dict` VALUES ('20', '航空、航天', '20');
INSERT INTO `dict` VALUES ('21', '其它', '21');
INSERT INTO `dict` VALUES ('22', 'IT教育', '22');
INSERT INTO `dict` VALUES ('23', '外语', '23');

-- ----------------------------
-- Table structure for `log`
-- ----------------------------
DROP TABLE IF EXISTS `log`;
CREATE TABLE `log` (
  `logid` int(32) NOT NULL AUTO_INCREMENT,
  `log_id` varchar(256) DEFAULT NULL,
  `log_name` varchar(32) DEFAULT NULL,
  `log_date` varchar(256) DEFAULT NULL,
  `log_state` varchar(32) DEFAULT NULL,
  `log` varchar(256) DEFAULT NULL,
  `log_orderid` int(128) DEFAULT NULL,
  PRIMARY KEY (`logid`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of log
-- ----------------------------
INSERT INTO `log` VALUES ('2', '1111', '顺丰快递', '2019-11-16', '运输中', null, '2020000');
INSERT INTO `log` VALUES ('3', null, null, '2019-11-16', '运输中', null, null);
INSERT INTO `log` VALUES ('4', null, null, '2019-11-16', '运输中', null, null);
INSERT INTO `log` VALUES ('5', null, null, '2019-11-16', '运输中', null, null);
INSERT INTO `log` VALUES ('6', null, null, '2019-11-16', '运输中', null, null);
INSERT INTO `log` VALUES ('7', null, null, '2019-11-16', '运输中', null, null);
INSERT INTO `log` VALUES ('8', null, null, '2019-11-16', '运输中', null, null);
INSERT INTO `log` VALUES ('9', null, null, '2019-11-16', '运输中', null, null);
INSERT INTO `log` VALUES ('10', null, null, '2019-11-16', '运输中', null, null);
INSERT INTO `log` VALUES ('11', null, null, '2019-11-16', '运输中', null, null);
INSERT INTO `log` VALUES ('12', null, null, '2019-11-16', '运输中', null, null);
INSERT INTO `log` VALUES ('13', '112345646', '顺丰快递', '2019-11-16', '运输中', null, '2020001');
INSERT INTO `log` VALUES ('14', '112345646', '顺丰快递', '2019-11-16', '运输中', null, '2020003');
INSERT INTO `log` VALUES ('15', '111111111111', '圆通快递', '2019-11-20', '运输中', null, '2020005');
INSERT INTO `log` VALUES ('16', 'asdadadadasdasd', '顺丰快递', '2019-11-22', '运输中', null, '22201111');

-- ----------------------------
-- Table structure for `tb_role`
-- ----------------------------
DROP TABLE IF EXISTS `tb_role`;
CREATE TABLE `tb_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `authority` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_role
-- ----------------------------
INSERT INTO `tb_role` VALUES ('1', 'ROLE_ADMIN');
INSERT INTO `tb_role` VALUES ('2', 'ROLE_DBA');
INSERT INTO `tb_role` VALUES ('3', 'ROLE_STUDENT');

-- ----------------------------
-- Table structure for `tb_user`
-- ----------------------------
DROP TABLE IF EXISTS `tb_user`;
CREATE TABLE `tb_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `loginName` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `role_id` int(32) DEFAULT NULL,
  `user_cardid` int(128) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=27 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_user
-- ----------------------------
INSERT INTO `tb_user` VALUES ('1', 'admin', '管理员admin', '$2a$10$GwZ6YLacTtx9nn0MKtJWue1gGdlEqrZTStwnwfd33vgPRUzIBgjSq', '1', null);
INSERT INTO `tb_user` VALUES ('2', 'fkit', 'student', '$2a$10$EfyQax73rCVgUIgf1Qdtk.jlzm0S0KW26fSqQd.Vh/0aIZgq2rsVm', '3', null);
INSERT INTO `tb_user` VALUES ('13', 'a', 'a', '$2a$10$D4itz.RmENl/VrAfrw7LBOEcrCP/Avc6pDXZZTtmbVV9oJqEmriWK', '1', null);
INSERT INTO `tb_user` VALUES ('4', '11', '11', '$2a$10$VHYgX9fdizPNETnAR6wEv.4VCiy6Ork5dXiGUauCa6RN6Qcpq7vHa', '3', '2019000');
INSERT INTO `tb_user` VALUES ('23', 'c', 'c', '$2a$10$UnqlZz77K9No1QCRs8vAJuuwTHh5mGtUKVf2B1MwFBsRjgHzgRy3G', '3', '2019003');
INSERT INTO `tb_user` VALUES ('22', 'q', 'q', '$2a$10$t5BNDeCeyc5gRnyagvlvBecMJSQzZ2DcIx1sYjQrrJeWKRim2X99W', '3', '2019002');
INSERT INTO `tb_user` VALUES ('17', '2', '3', '$2a$10$oUgkFW72hwgrxOj01xXA/OYI2wsCKUBG6wcKFFoGz/kMFinHHEzj2', '3', null);
INSERT INTO `tb_user` VALUES ('24', '22', '22', '$2a$10$.mBzINJqcMo92g4AANQVPuoEmnFnQHB0nRsqdjzVVsZazKoFUfB6C', '3', '2019005');
INSERT INTO `tb_user` VALUES ('25', 'cc', 'cc', '$2a$10$p1DNmObzeOFOHwL6WeuCLOP2BuXnFUamy.haeMyp/crluQs8QUFoq', '3', '2019004');
INSERT INTO `tb_user` VALUES ('26', 'v', 'v', '$2a$10$LXKdjA8i8bqxQePr/lvul.wAfvClds6T9B0pWPsttNZlvcC5kMceC', '3', '2019006');
