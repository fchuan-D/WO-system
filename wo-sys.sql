/*
 Navicat Premium Data Transfer

 Source Server         : 本地MySQL
 Source Server Type    : MySQL
 Source Server Version : 80030
 Source Host           : localhost:3306
 Source Schema         : bishe

 Target Server Type    : MySQL
 Target Server Version : 80030
 File Encoding         : 65001

 Date: 29/04/2024 15:25:13
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for admins
-- ----------------------------
DROP TABLE IF EXISTS `admins`;
CREATE TABLE `admins`  (
  `id` int(0) UNSIGNED NOT NULL AUTO_INCREMENT,
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '帐号',
  `pwd` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '密码',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '管理员' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of admins
-- ----------------------------
INSERT INTO `admins` VALUES (1, 'admin', 'root');
INSERT INTO `admins` VALUES (2, 'fchuan', 'password');

-- ----------------------------
-- Table structure for auditing
-- ----------------------------
DROP TABLE IF EXISTS `auditing`;
CREATE TABLE `auditing`  (
  `id` int(0) UNSIGNED NOT NULL AUTO_INCREMENT,
  `hseid` int(0) UNSIGNED NOT NULL COMMENT 'HSEid',
  `proname` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '项目名',
  `resperson` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '负责人',
  `personnel` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '执行人员',
  `operator` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '操作人',
  `auditing1` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '审核',
  `cont` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '备注',
  `addtime` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '审核时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `auditing_hseid_index`(`hseid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '审核' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of auditing
-- ----------------------------
INSERT INTO `auditing` VALUES (1, 1, 'A修井项目', '小王', '小红，小兰', 'admin', '通过', '1', '2024-04-17 14:54:21');
INSERT INTO `auditing` VALUES (2, 2, 'B修井项目', '王师傅', '小红，小赵', '111', '通过', '1', '2024-04-17 15:25:12');

-- ----------------------------
-- Table structure for completed
-- ----------------------------
DROP TABLE IF EXISTS `completed`;
CREATE TABLE `completed`  (
  `id` int(0) UNSIGNED NOT NULL AUTO_INCREMENT,
  `planid` int(0) UNSIGNED NOT NULL COMMENT '作业计划id',
  `num` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '作业编号',
  `well` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '井号',
  `type` int(0) UNSIGNED NOT NULL COMMENT '作业类型',
  `res` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '负责人',
  `pic` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '计划完成图片',
  `report` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '完成报告',
  `contents` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '完成内容',
  `cojnt` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '备注',
  `handler` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '经手人',
  `addtime` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '添加时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `completed_planid_index`(`planid`) USING BTREE,
  INDEX `completed_type_index`(`type`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '计划完成' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of completed
-- ----------------------------
INSERT INTO `completed` VALUES (1, 1, '041714372631', 'A', 1, 'XXX', '/upload/bc099933639261a535d2a2b2c73f1bfd.png', '/upload/34e0722122b94999a790b919558c0786.docx', 'XXX', 'xxx', 'admin', '2024-04-17 15:00:47');
INSERT INTO `completed` VALUES (2, 2, '041715193653', 'B', 1, '王师傅', '/upload/8fbcbe934a1524cfb1259ecfbd866f0c.png,/upload/8fbcbe934a1524cfb1259ecfbd866f0c.png', '/upload/34e0722122b94999a790b919558c0786.docx', '完成内容完成内容完成内容完成内容完成内容', '1', '111', '2024-04-17 15:22:39');

-- ----------------------------
-- Table structure for data
-- ----------------------------
DROP TABLE IF EXISTS `data`;
CREATE TABLE `data`  (
  `id` int(0) UNSIGNED NOT NULL AUTO_INCREMENT,
  `number1` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '编号',
  `wellnumber` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '井号',
  `pic` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '作业图片',
  `depth` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '井深',
  `diameter` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '井径',
  `mudproperties` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '泥浆性能',
  `bittype` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '钻头类型',
  `speed` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '钻速',
  `report` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '报告',
  `details` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '详情',
  `addtime` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '添加时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '作业数据' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of data
-- ----------------------------
INSERT INTO `data` VALUES (1, '041714501661', 'A', '/upload/dc699554926ff5fe31222825986ea1c7.png,/upload/dc699554926ff5fe31222825986ea1c7.png', '200m', '20m', '泥浆性能泥浆性能泥浆性能泥浆性能', 'A类型', '20m/s', '/upload/34e0722122b94999a790b919558c0786.docx', '详情详情详情详情详情', '2024-04-17 14:53:41');
INSERT INTO `data` VALUES (2, '041715222499', 'A', '/upload/428a7d6730f1ed588b0c1cd7e5b1f1e9.png,/upload/428a7d6730f1ed588b0c1cd7e5b1f1e9.png', '300m', '20m', 'XXX', 'A类型', '30m/s', '/upload/34e0722122b94999a790b919558c0786.docx', '详情详情详情详情详情详情详情详情详情', '2024-04-17 15:23:19');

-- ----------------------------
-- Table structure for hse
-- ----------------------------
DROP TABLE IF EXISTS `hse`;
CREATE TABLE `hse`  (
  `id` int(0) UNSIGNED NOT NULL AUTO_INCREMENT,
  `proname` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '项目名',
  `resperson` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '负责人',
  `personnel` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '执行人员',
  `timeon` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '开始日期',
  `endtime` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '结束日期',
  `objectives` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '管理目标',
  `identification` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '风险识别',
  `report` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '环境报告',
  `records` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '事故记录',
  `measures` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '改进措施',
  `compliance` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '法律法规遵守情况',
  `status` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '状态',
  `operator` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '操作人',
  `addtime` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '添加时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'HSE' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of hse
-- ----------------------------
INSERT INTO `hse` VALUES (1, 'A修井项目', '小王', '小红，小兰', '2024-04-03', '2024-04-06', 'XXX', 'xxxx', '/upload/34e0722122b94999a790b919558c0786.docx', 'xxx', 'XXX', 'XXX', '通过', 'admin', '2024-04-17 14:54:16');
INSERT INTO `hse` VALUES (2, 'B修井项目', '王师傅', '小红，小赵', '2024-04-11', '2024-04-20', 'XXX', 'XXX', '/upload/34e0722122b94999a790b919558c0786.docx', 'XXX', 'XXXX', 'XXXX', '通过', '111', '2024-04-17 15:24:41');

-- ----------------------------
-- Table structure for information
-- ----------------------------
DROP TABLE IF EXISTS `information`;
CREATE TABLE `information`  (
  `id` int(0) UNSIGNED NOT NULL AUTO_INCREMENT,
  `number1` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '设备编号',
  `device` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '设备名称',
  `type` int(0) UNSIGNED NOT NULL COMMENT '设备类型',
  `pic` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '图片',
  `spec` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '规格型号',
  `facturer` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '生产厂家',
  `supplier` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '供应商',
  `quantity` int(0) NOT NULL DEFAULT 0 COMMENT '数量',
  `status` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '状态',
  `contents` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '详情',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `information_type_index`(`type`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '设备信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of information
-- ----------------------------
INSERT INTO `information` VALUES (1, '041714242608', '中基斯顿5吨挖机', 1, '/upload/2d01dd2b7a808b5d72a468bfa15ffdc3.png', '12346', 'XX', 'xx', 5, '待使用', '<p><img src=\"https://t11.baidu.com/it/u=2138524121,3182460527&amp;fm=199&amp;app=68&amp;f=JPEG&amp;fmt=auto?w=750&amp;h=750&amp;s=02A47C2373C452F4C5B4CCDE010080E3\"></p>');
INSERT INTO `information` VALUES (2, '041714258604', '泊姆克双联三联四联宣工钻机挖机 修井机 农机', 2, '/upload/c7046031cf2cab42858fead87b0e7f5c.png', '456789', 'XXX', 'XXX', 1, '待使用', '<p><img src=\"https://t12.baidu.com/it/u=4274060631,723934506&amp;fm=199&amp;app=68&amp;f=JPEG&amp;fmt=auto?w=450&amp;h=450&amp;s=AFBE60855C1382DA4C3D7C9B03001080\"></p>');

-- ----------------------------
-- Table structure for logs
-- ----------------------------
DROP TABLE IF EXISTS `logs`;
CREATE TABLE `logs`  (
  `id` int(0) UNSIGNED NOT NULL AUTO_INCREMENT,
  `module` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '模块',
  `operationtype` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '操作类型',
  `operationcontent` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '操作内容',
  `cx` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色',
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '操作用户',
  `addtime` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '操作时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 38 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '操作日志' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of logs
-- ----------------------------
INSERT INTO `logs` VALUES (1, '设备类型', '新增', '类型名称：大型设备', '管理员', 'admin', '2024-04-17 14:22:40');
INSERT INTO `logs` VALUES (2, '设备类型', '新增', '类型名称：中型设备', '管理员', 'admin', '2024-04-17 14:22:44');
INSERT INTO `logs` VALUES (3, '设备信息', '新增', '设备编号：041714242608<br>设备名称：中基斯顿5吨挖机<br>规格型号：12346<br>生产厂家：XX<br>供应商：xx', '管理员', 'admin', '2024-04-17 14:25:07');
INSERT INTO `logs` VALUES (4, '设备信息', '新增', '设备编号：041714258604<br>设备名称：泊姆克双联三联四联宣工钻机挖机 修井机 农机<br>规格型号：456789<br>生产厂家：XXX<br>供应商：XXX', '管理员', 'admin', '2024-04-17 14:26:32');
INSERT INTO `logs` VALUES (5, '材料类型', '新增', '类型名称：小型材料', '管理员', 'admin', '2024-04-17 14:27:42');
INSERT INTO `logs` VALUES (6, '材料信息', '新增', '材料编号：041714274542<br>材料名称：36Mn2地质钻探用管<br>规格型号：132456<br>生产厂家：XX', '管理员', 'admin', '2024-04-17 14:28:32');
INSERT INTO `logs` VALUES (7, '作业类型', '新增', '类型名称：修井', '管理员', 'admin', '2024-04-17 14:36:43');
INSERT INTO `logs` VALUES (8, '作业类型', '新增', '类型名称：修复', '管理员', 'admin', '2024-04-17 14:37:33');
INSERT INTO `logs` VALUES (9, '作业计划', '新增', '作业编号：041714372631<br>井号：A<br>作业地点：XX地点<br>负责人：XXX<br>作业人员：XXX<br>作业成本：XX<br>安全措施：XX', '管理员', 'admin', '2024-04-17 14:42:31');
INSERT INTO `logs` VALUES (10, '技术材料', '新增', '材料编号：041714502959<br>备注：1<br>井况分析：井况分析井况分析井况分析井况分析井况分析井况分析井况分析井况分析<br>详情介绍：详情介绍详情介绍详情介绍详情介绍详情介绍详情介绍详情介绍详情介绍详情介绍详情介绍详情介绍详情介绍详情介绍', '管理员', 'admin', '2024-04-17 14:50:49');
INSERT INTO `logs` VALUES (11, '作业数据', '新增', '编号：041714501661<br>井号：A<br>井深：200m<br>井径：20m<br>泥浆性能：泥浆性能泥浆性能泥浆性能泥浆性能<br>钻头类型：A类型', '管理员', 'admin', '2024-04-17 14:53:41');
INSERT INTO `logs` VALUES (12, 'HSE', '新增', '项目名：A修井项目<br>负责人：小王<br>执行人员：小红，小兰<br>管理目标：XXX<br>风险识别：xxxx<br>事故记录：xxx<br>改进措施：XXX<br>法律法规遵守情况：XXX', '管理员', 'admin', '2024-04-17 14:54:16');
INSERT INTO `logs` VALUES (13, '审核', '新增', '项目名：A修井项目<br>负责人：小王<br>执行人员：小红，小兰', '管理员', 'admin', '2024-04-17 14:54:21');
INSERT INTO `logs` VALUES (14, '成本跟踪', '新增', '作业编号：041714372631<br>井号：A', '管理员', 'admin', '2024-04-17 14:54:34');
INSERT INTO `logs` VALUES (15, '计划更新', '新增', '作业编号：041714372631<br>井号：A<br>内容：内容内容内容内容内容内容内容', '管理员', 'admin', '2024-04-17 14:54:59');
INSERT INTO `logs` VALUES (16, '作业情况', '新增', '作业编号：041714372631<br>井号：A<br>井口压力：XX<br>钻进速度：XXX<br>钻井液流量：XXX<br>其他指标：XXX', '管理员', 'admin', '2024-04-17 14:55:09');
INSERT INTO `logs` VALUES (17, '计划完成', '新增', '作业编号：041714372631<br>井号：A<br>负责人：XXX<br>完成报告：/upload/34e0722122b94999a790b919558c0786.docx<br>完成内容：XXX', '管理员', 'admin', '2024-04-17 15:00:47');
INSERT INTO `logs` VALUES (18, '员工', '新增', '工号：111<br>姓名：王师傅', '管理员', 'admin', '2024-04-17 15:01:30');
INSERT INTO `logs` VALUES (19, '出库', '新增', '材料编号：041714274542<br>材料名称：36Mn2地质钻探用管<br>负责人：王师傅<br>联系方式：13798465711', '员工', '111', '2024-04-17 15:11:38');
INSERT INTO `logs` VALUES (20, '入库', '新增', '材料编号：041714274542<br>材料名称：36Mn2地质钻探用管', '员工', '111', '2024-04-17 15:11:54');
INSERT INTO `logs` VALUES (21, '材料维护', '新增', '材料编号：041714274542<br>材料名称：36Mn2地质钻探用管<br>维护内容：维护内容维护内容维护内容维护内容维护内容维护内容维护内容维护内容维护内容维护内容', '员工', '111', '2024-04-17 15:12:24');
INSERT INTO `logs` VALUES (22, '设备维护', '新增', '设备编号：041714258604<br>设备名称：泊姆克双联三联四联宣工钻机挖机 修井机 农机<br>维护内容：111', '员工', '111', '2024-04-17 15:14:33');
INSERT INTO `logs` VALUES (23, '设备维护', '新增', '设备编号：041714258604<br>设备名称：泊姆克双联三联四联宣工钻机挖机 修井机 农机<br>维护内容：维护内容维护内容维护内容维护内容', '员工', '111', '2024-04-17 15:19:10');
INSERT INTO `logs` VALUES (24, '材料维护', '新增', '材料编号：041714274542<br>材料名称：36Mn2地质钻探用管<br>维护内容：111', '员工', '111', '2024-04-17 15:19:19');
INSERT INTO `logs` VALUES (25, '出库', '新增', '材料编号：041714274542<br>材料名称：36Mn2地质钻探用管<br>负责人：王师傅<br>联系方式：13478941474', '员工', '111', '2024-04-17 15:19:41');
INSERT INTO `logs` VALUES (26, '入库', '新增', '材料编号：041714274542<br>材料名称：36Mn2地质钻探用管', '员工', '111', '2024-04-17 15:19:50');
INSERT INTO `logs` VALUES (27, '作业计划', '新增', '作业编号：041715193653<br>井号：B<br>作业地点：XXX地点<br>负责人：王师傅<br>作业人员：小赵，小红<br>作业成本：XXX<br>安全措施：XXX', '员工', '111', '2024-04-17 15:20:56');
INSERT INTO `logs` VALUES (28, '计划更新', '新增', '作业编号：041715193653<br>井号：B<br>内容：内容内容内容内容内容内容内容内容', '员工', '111', '2024-04-17 15:21:18');
INSERT INTO `logs` VALUES (29, '作业情况', '新增', '作业编号：041715193653<br>井号：B<br>井口压力：XXX<br>钻进速度：30m/s<br>钻井液流量：XXX<br>其他指标：XXX', '员工', '111', '2024-04-17 15:22:04');
INSERT INTO `logs` VALUES (30, '计划完成', '新增', '作业编号：041715193653<br>井号：B<br>负责人：王师傅<br>完成报告：/upload/34e0722122b94999a790b919558c0786.docx<br>完成内容：完成内容完成内容完成内容完成内容完成内容', '员工', '111', '2024-04-17 15:22:39');
INSERT INTO `logs` VALUES (31, '作业数据', '新增', '编号：041715222499<br>井号：A<br>井深：300m<br>井径：20m<br>泥浆性能：XXX<br>钻头类型：A类型', '员工', '111', '2024-04-17 15:23:19');
INSERT INTO `logs` VALUES (32, '技术材料', '新增', '材料编号：041715232036<br>备注：11<br>井况分析：井况分析井况分析井况分析井况分析井况分析井况分析井况分析井况分析<br>详情介绍：详情介绍详情介绍详情介绍详情介绍详情介绍详情介绍详情介绍详情介绍详情介绍', '员工', '111', '2024-04-17 15:24:08');
INSERT INTO `logs` VALUES (33, 'HSE', '新增', '项目名：B修井项目<br>负责人：王师傅<br>执行人员：小红，小赵<br>管理目标：XXX<br>风险识别：XXX<br>事故记录：XXX<br>改进措施：XXXX<br>法律法规遵守情况：XXXX', '员工', '111', '2024-04-17 15:24:41');
INSERT INTO `logs` VALUES (34, '审核', '新增', '项目名：B修井项目<br>负责人：王师傅<br>执行人员：小红，小赵', '管理员', 'admin', '2024-04-17 15:25:12');
INSERT INTO `logs` VALUES (35, '员工', '更新', '工号：111<br>姓名：王师傅', '管理员', 'fchuan', '2024-04-22 22:09:50');
INSERT INTO `logs` VALUES (36, '员工', '新增', '工号：202031061098<br>姓名：邓凯中', '管理员', 'admin', '2024-04-28 13:46:15');
INSERT INTO `logs` VALUES (37, '员工', '更新', '工号：202031061098<br>姓名：邓凯中', '管理员', 'fchuan', '2024-04-28 15:34:59');

-- ----------------------------
-- Table structure for maintenance
-- ----------------------------
DROP TABLE IF EXISTS `maintenance`;
CREATE TABLE `maintenance`  (
  `id` int(0) UNSIGNED NOT NULL AUTO_INCREMENT,
  `informationid` int(0) UNSIGNED NOT NULL COMMENT '设备信息id',
  `number1` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '设备编号',
  `device` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '设备名称',
  `type` int(0) UNSIGNED NOT NULL COMMENT '设备类型',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '维护内容',
  `oper` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '操作人',
  `addtime` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '操作时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `maintenance_informationid_index`(`informationid`) USING BTREE,
  INDEX `maintenance_type_index`(`type`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '设备维护' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of maintenance
-- ----------------------------
INSERT INTO `maintenance` VALUES (1, 2, '041714258604', '泊姆克双联三联四联宣工钻机挖机 修井机 农机', 2, '111', '111', '2024-04-17 15:14:33');
INSERT INTO `maintenance` VALUES (2, 2, '041714258604', '泊姆克双联三联四联宣工钻机挖机 修井机 农机', 2, '维护内容维护内容维护内容维护内容', '111', '2024-04-17 15:19:10');

-- ----------------------------
-- Table structure for maintenance1
-- ----------------------------
DROP TABLE IF EXISTS `maintenance1`;
CREATE TABLE `maintenance1`  (
  `id` int(0) UNSIGNED NOT NULL AUTO_INCREMENT,
  `materialid` int(0) UNSIGNED NOT NULL COMMENT '材料信息id',
  `materialnu` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '材料编号',
  `materialna` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '材料名称',
  `type` int(0) UNSIGNED NOT NULL COMMENT '材料类型',
  `contents` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '维护内容',
  `opera` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '维护人',
  `addtime` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '维护时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `maintenance1_materialid_index`(`materialid`) USING BTREE,
  INDEX `maintenance1_type_index`(`type`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '材料维护' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of maintenance1
-- ----------------------------
INSERT INTO `maintenance1` VALUES (1, 1, '041714274542', '36Mn2地质钻探用管', 1, '维护内容维护内容维护内容维护内容维护内容维护内容维护内容维护内容维护内容维护内容', '111', '2024-04-17 15:12:24');
INSERT INTO `maintenance1` VALUES (2, 1, '041714274542', '36Mn2地质钻探用管', 1, '111', '111', '2024-04-17 15:19:19');

-- ----------------------------
-- Table structure for material
-- ----------------------------
DROP TABLE IF EXISTS `material`;
CREATE TABLE `material`  (
  `id` int(0) UNSIGNED NOT NULL AUTO_INCREMENT,
  `materialnu` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '材料编号',
  `materialna` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '材料名称',
  `type` int(0) UNSIGNED NOT NULL COMMENT '材料类型',
  `specification` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '规格型号',
  `pic` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '材料图片',
  `facturer` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '生产厂家',
  `supplier` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '供应商',
  `unit` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '单位',
  `level` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '级别',
  `type1` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '接头类型',
  `quantity` int(0) NOT NULL DEFAULT 0 COMMENT '数量',
  `status` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '状态',
  `con` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '备注',
  `cont` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '材料详情',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `material_type_index`(`type`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '材料信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of material
-- ----------------------------
INSERT INTO `material` VALUES (1, '041714274542', '36Mn2地质钻探用管', 1, '132456', '/upload/033d7214c42dc203eda611b43dfa66ea.png', 'XX', 'XX', 'XX', 'A', 'A类型', 20, '待使用', '11', '材料详情材料详情材料详情材料详情材料详情材料详情材料详情材料详情材料详情材料详情材料详情材料详情材料详情材料详情材料详情材料详情材料详情');

-- ----------------------------
-- Table structure for outbound
-- ----------------------------
DROP TABLE IF EXISTS `outbound`;
CREATE TABLE `outbound`  (
  `id` int(0) UNSIGNED NOT NULL AUTO_INCREMENT,
  `materialid` int(0) UNSIGNED NOT NULL COMMENT '材料信息id',
  `materialnu` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '材料编号',
  `materialna` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '材料名称',
  `type` int(0) UNSIGNED NOT NULL COMMENT '材料类型',
  `quantity` int(0) NOT NULL DEFAULT 0 COMMENT '出库数量',
  `person` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '负责人',
  `phone` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '联系方式',
  `cont` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '备注',
  `handler` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '经手人',
  `addtime` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '出库时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `outbound_materialid_index`(`materialid`) USING BTREE,
  INDEX `outbound_type_index`(`type`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '出库' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of outbound
-- ----------------------------
INSERT INTO `outbound` VALUES (1, 1, '041714274542', '36Mn2地质钻探用管', 1, 2, '王师傅', '13798465711', '1', '111', '2024-04-17 15:11:38');
INSERT INTO `outbound` VALUES (2, 1, '041714274542', '36Mn2地质钻探用管', 1, 2, '王师傅', '13478941474', '备注备注备注备注备注备注备注备注', '111', '2024-04-17 15:19:41');

-- ----------------------------
-- Table structure for plan
-- ----------------------------
DROP TABLE IF EXISTS `plan`;
CREATE TABLE `plan`  (
  `id` int(0) UNSIGNED NOT NULL AUTO_INCREMENT,
  `num` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '作业编号',
  `well` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '井号',
  `type` int(0) UNSIGNED NOT NULL COMMENT '作业类型',
  `pic` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '图片',
  `timeon` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '开始时间',
  `endtime` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '结束时间',
  `duration` int(0) NOT NULL DEFAULT 0 COMMENT '预计总时长',
  `location` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '作业地点',
  `res` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '负责人',
  `operators` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '作业人员',
  `materials` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '使用材料',
  `information` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '使用设备',
  `status` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '作业进度',
  `measures` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '安全措施',
  `cost` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '作业成本',
  `programme` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '修井方案',
  `con` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '备注',
  `opera` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '操作人',
  `cont` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '详情',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `plan_type_index`(`type`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '作业计划' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of plan
-- ----------------------------
INSERT INTO `plan` VALUES (1, '041714372631', 'A', 1, '/upload/8456bb577f6d7ee97dcd44ac29820467.png,/upload/bb6fd98f526ace9fd4eeb90a1e321505.png', '2024-04-10', '2024-04-20', 11, 'XX地点', 'XXX', 'XXX', '1', '2,1', '已完成', 'XX', 'XX', 'XXX', 'XX', 'admin', '11');
INSERT INTO `plan` VALUES (2, '041715193653', 'B', 1, '/upload/1ff86e47fb1f5970d37f0489fb0d1f53.png,/upload/f18c3be806aa1b5b2ab21ffea63625bc.png', '2024-04-11', '2024-04-19', 9, 'XXX地点', '王师傅', '小赵，小红', '1', '2,1', '已完成', 'XXX', 'XXX', 'xx', '1', '111', '详情详情详情详情详情详情详情详情详情');

-- ----------------------------
-- Table structure for situation
-- ----------------------------
DROP TABLE IF EXISTS `situation`;
CREATE TABLE `situation`  (
  `id` int(0) UNSIGNED NOT NULL AUTO_INCREMENT,
  `planid` int(0) UNSIGNED NOT NULL COMMENT '作业计划id',
  `num` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '作业编号',
  `well` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '井号',
  `type` int(0) UNSIGNED NOT NULL COMMENT '作业类型',
  `pressure` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '井口压力',
  `speed` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '钻进速度',
  `flowrate` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '钻井液流量',
  `indicators` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '其他指标',
  `details` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '详情',
  `addpeople` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '添加人',
  `addtime` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '添加时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `situation_planid_index`(`planid`) USING BTREE,
  INDEX `situation_type_index`(`type`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '作业情况' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of situation
-- ----------------------------
INSERT INTO `situation` VALUES (1, 1, '041714372631', 'A', 1, 'XX', 'XXX', 'XXX', 'XXX', '111', 'admin', '2024-04-17 14:55:09');
INSERT INTO `situation` VALUES (2, 2, '041715193653', 'B', 1, 'XXX', '30m/s', 'XXX', 'XXX', '详情详情详情详情详情详情详情详情详情详情详情', '111', '2024-04-17 15:22:04');

-- ----------------------------
-- Table structure for stuff
-- ----------------------------
DROP TABLE IF EXISTS `stuff`;
CREATE TABLE `stuff`  (
  `id` int(0) UNSIGNED NOT NULL AUTO_INCREMENT,
  `num` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '工号',
  `password` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '密码',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '姓名',
  `sex` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '性别',
  `phone` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '手机',
  `mail` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '邮箱',
  `content` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '简介',
  `pos` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '职位',
  `years` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '工龄',
  `pic` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '头像',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '员工' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of stuff
-- ----------------------------
INSERT INTO `stuff` VALUES (1, '111', '111', '王师傅', '男', '13278946574', '1565@qq.com', 'XXX', '工人', '20', '/upload/5cbd21bc26c90a073cbeb703c533bc3b.png');
INSERT INTO `stuff` VALUES (2, '202031061098', '202031061098', '邓凯中', '男', '17882380261', '10086@qq.com', '', '', '', '/upload/ca37c03d7333f6624dceebbf2b73d56e.png');

-- ----------------------------
-- Table structure for technical
-- ----------------------------
DROP TABLE IF EXISTS `technical`;
CREATE TABLE `technical`  (
  `id` int(0) UNSIGNED NOT NULL AUTO_INCREMENT,
  `number1` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '材料编号',
  `stage` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '阶段',
  `drawing` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '图纸',
  `parametertable` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '参数表',
  `manual` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '操作手册',
  `contents` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '备注',
  `analysis` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '井况分析',
  `introduction` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '详情介绍',
  `addtime` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '添加时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '技术材料' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of technical
-- ----------------------------
INSERT INTO `technical` VALUES (1, '041714502959', '钻井', '/upload/55472ef895d50df31272a4df0088f9e7.png', '/upload/6f376474311be28c8512a9eb927cbb89.png', '/upload/34e0722122b94999a790b919558c0786.docx', '1', '井况分析井况分析井况分析井况分析井况分析井况分析井况分析井况分析', '详情介绍详情介绍详情介绍详情介绍详情介绍详情介绍详情介绍详情介绍详情介绍详情介绍详情介绍详情介绍详情介绍', '2024-04-17 14:50:49');
INSERT INTO `technical` VALUES (2, '041715232036', '完井', '/upload/7969228f417fcb543062322f150da1e2.png', '/upload/a71803a90110ada5b7a51fa952615314.png', '/upload/34e0722122b94999a790b919558c0786.docx', '11', '井况分析井况分析井况分析井况分析井况分析井况分析井况分析井况分析', '详情介绍详情介绍详情介绍详情介绍详情介绍详情介绍详情介绍详情介绍详情介绍', '2024-04-17 15:24:08');

-- ----------------------------
-- Table structure for tracking
-- ----------------------------
DROP TABLE IF EXISTS `tracking`;
CREATE TABLE `tracking`  (
  `id` int(0) UNSIGNED NOT NULL AUTO_INCREMENT,
  `planid` int(0) UNSIGNED NOT NULL COMMENT '作业计划id',
  `num` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '作业编号',
  `well` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '井号',
  `type` int(0) UNSIGNED NOT NULL COMMENT '作业类型',
  `costofuse` decimal(10, 2) NOT NULL COMMENT '使用成本',
  `cont` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '备注',
  `addtime` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '添加时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `tracking_planid_index`(`planid`) USING BTREE,
  INDEX `tracking_type_index`(`type`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '成本跟踪' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tracking
-- ----------------------------
INSERT INTO `tracking` VALUES (1, 1, '041714372631', 'A', 1, 3000000.00, '1', '2024-04-17 14:54:34');

-- ----------------------------
-- Table structure for type
-- ----------------------------
DROP TABLE IF EXISTS `type`;
CREATE TABLE `type`  (
  `id` int(0) UNSIGNED NOT NULL AUTO_INCREMENT,
  `typename` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '类型名称',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '设备类型' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of type
-- ----------------------------
INSERT INTO `type` VALUES (1, '大型设备');
INSERT INTO `type` VALUES (2, '中型设备');

-- ----------------------------
-- Table structure for type1
-- ----------------------------
DROP TABLE IF EXISTS `type1`;
CREATE TABLE `type1`  (
  `id` int(0) UNSIGNED NOT NULL AUTO_INCREMENT,
  `typename` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '类型名称',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '作业类型' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of type1
-- ----------------------------
INSERT INTO `type1` VALUES (1, '修井');
INSERT INTO `type1` VALUES (2, '修复');

-- ----------------------------
-- Table structure for type2
-- ----------------------------
DROP TABLE IF EXISTS `type2`;
CREATE TABLE `type2`  (
  `id` int(0) UNSIGNED NOT NULL AUTO_INCREMENT,
  `typename` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '类型名称',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '材料类型' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of type2
-- ----------------------------
INSERT INTO `type2` VALUES (1, '小型材料');

-- ----------------------------
-- Table structure for updates
-- ----------------------------
DROP TABLE IF EXISTS `updates`;
CREATE TABLE `updates`  (
  `id` int(0) UNSIGNED NOT NULL AUTO_INCREMENT,
  `planid` int(0) UNSIGNED NOT NULL COMMENT '作业计划id',
  `num` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '作业编号',
  `well` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '井号',
  `type` int(0) UNSIGNED NOT NULL COMMENT '作业类型',
  `pic` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '图片',
  `opera` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '操作人',
  `cont` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '内容',
  `pic1` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '照片',
  `updatesper` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '更新人',
  `addtime` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '添加时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `updates_planid_index`(`planid`) USING BTREE,
  INDEX `updates_type_index`(`type`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '计划更新' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of updates
-- ----------------------------
INSERT INTO `updates` VALUES (1, 1, '041714372631', 'A', 1, '/upload/8456bb577f6d7ee97dcd44ac29820467.png,/upload/bb6fd98f526ace9fd4eeb90a1e321505.png', 'admin', '内容内容内容内容内容内容内容', '/upload/acf9a3e517e017bea4a4fc7e6a07e81e.png', 'admin', '2024-04-17 14:54:59');
INSERT INTO `updates` VALUES (2, 2, '041715193653', 'B', 1, '/upload/1ff86e47fb1f5970d37f0489fb0d1f53.png,/upload/f18c3be806aa1b5b2ab21ffea63625bc.png', '111', '内容内容内容内容内容内容内容内容', '/upload/e607258dc4941a0d01f904302aa1f93c.png', '111', '2024-04-17 15:21:18');

-- ----------------------------
-- Table structure for warehousing
-- ----------------------------
DROP TABLE IF EXISTS `warehousing`;
CREATE TABLE `warehousing`  (
  `id` int(0) UNSIGNED NOT NULL AUTO_INCREMENT,
  `materialid` int(0) UNSIGNED NOT NULL COMMENT '材料信息id',
  `materialnu` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '材料编号',
  `materialna` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '材料名称',
  `type` int(0) UNSIGNED NOT NULL COMMENT '材料类型',
  `quantity` int(0) NOT NULL DEFAULT 0 COMMENT '入库数量',
  `cont` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '备注',
  `handler` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '经手人',
  `addtime` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '添加时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `warehousing_materialid_index`(`materialid`) USING BTREE,
  INDEX `warehousing_type_index`(`type`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '入库' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of warehousing
-- ----------------------------
INSERT INTO `warehousing` VALUES (1, 1, '041714274542', '36Mn2地质钻探用管', 1, 2, '备注备注备注备注', '111', '2024-04-17 15:11:54');
INSERT INTO `warehousing` VALUES (2, 1, '041714274542', '36Mn2地质钻探用管', 1, 2, '11', '111', '2024-04-17 15:19:50');

SET FOREIGN_KEY_CHECKS = 1;
