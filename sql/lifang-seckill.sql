/*
 Navicat Premium Data Transfer

 Source Server         : d-mysql
 Source Server Type    : MySQL
 Source Server Version : 80027
 Source Host           : localhost:3307
 Source Schema         : lifang-seckill

 Target Server Type    : MySQL
 Target Server Version : 80027
 File Encoding         : 65001

 Date: 23/04/2026 15:17:01
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_goods
-- ----------------------------
DROP TABLE IF EXISTS `t_goods`;
CREATE TABLE `t_goods`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `goods_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '商品名称(完整，含规格)',
  `goods_img` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '商品主图(冗余字段，用于列表展示)',
  `goods_price` decimal(10, 2) NOT NULL COMMENT '商品原价',
  `status` tinyint NOT NULL DEFAULT 1 COMMENT '商品状态(0:下架 1:上架)',
  `is_deleted` tinyint NOT NULL DEFAULT 0 COMMENT '是否删除(0:未删除 1:已删除)',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_status_deleted`(`status` ASC, `is_deleted` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '商品表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_goods
-- ----------------------------

-- ----------------------------
-- Table structure for t_goods_detail
-- ----------------------------
DROP TABLE IF EXISTS `t_goods_detail`;
CREATE TABLE `t_goods_detail`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `goods_id` bigint UNSIGNED NOT NULL COMMENT '商品ID',
  `detail_content` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '商品详情(HTML/富文本)',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_goods_id`(`goods_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '商品详情表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_goods_detail
-- ----------------------------

-- ----------------------------
-- Table structure for t_goods_img
-- ----------------------------
DROP TABLE IF EXISTS `t_goods_img`;
CREATE TABLE `t_goods_img`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `goods_id` bigint UNSIGNED NOT NULL COMMENT '商品ID',
  `img_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '图片URL',
  `sort` int NOT NULL DEFAULT 0 COMMENT '排序(数字越小越靠前)',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_goods_id`(`goods_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '商品图片表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_goods_img
-- ----------------------------

-- ----------------------------
-- Table structure for t_seckill_activity
-- ----------------------------
DROP TABLE IF EXISTS `t_seckill_activity`;
CREATE TABLE `t_seckill_activity`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '活动ID',
  `activity_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '活动名称',
  `begin_time` datetime NOT NULL COMMENT '活动开始时间',
  `end_time` datetime NOT NULL COMMENT '活动结束时间',
  `status` tinyint NOT NULL DEFAULT 0 COMMENT '活动状态(0:未开始 1:进行中 2:已结束)',
  `description` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '活动描述',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_begin_time`(`begin_time` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '秒杀活动表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_seckill_activity
-- ----------------------------

-- ----------------------------
-- Table structure for t_seckill_goods
-- ----------------------------
DROP TABLE IF EXISTS `t_seckill_goods`;
CREATE TABLE `t_seckill_goods`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `activity_id` bigint UNSIGNED NOT NULL COMMENT '活动ID',
  `goods_id` bigint UNSIGNED NOT NULL COMMENT '商品ID',
  `seckill_title` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '秒杀展示名称(简短，用于列表)',
  `seckill_img` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '秒杀展示图片(用于列表)',
  `seckill_price` decimal(10, 2) NOT NULL COMMENT '秒杀价',
  `seckill_total` int NOT NULL DEFAULT 0 COMMENT '秒杀库存总量(不变)',
  `seckill_stock` int NOT NULL DEFAULT 0 COMMENT '秒杀剩余库存(扣减)',
  `seckill_limit` int NOT NULL DEFAULT 1 COMMENT '秒杀限购数量',
  `sort` int NOT NULL DEFAULT 0 COMMENT '展示排序(数字越小越靠前)',
  `version` int UNSIGNED NOT NULL DEFAULT 0 COMMENT '版本号(乐观锁)',
  `is_deleted` tinyint NOT NULL DEFAULT 0 COMMENT '是否删除(0:未删除 1:已删除)',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_activity_goods`(`activity_id` ASC, `goods_id` ASC) USING BTREE,
  INDEX `idx_activity_sort`(`activity_id` ASC, `sort` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '秒杀商品关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_seckill_goods
-- ----------------------------

-- ----------------------------
-- Table structure for t_seckill_order
-- ----------------------------
DROP TABLE IF EXISTS `t_seckill_order`;
CREATE TABLE `t_seckill_order`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `user_id` bigint UNSIGNED NOT NULL COMMENT '用户ID',
  `activity_id` bigint UNSIGNED NOT NULL COMMENT '活动ID',
  `goods_id` bigint UNSIGNED NOT NULL COMMENT '商品ID',
  `order_no` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '订单号',
  `seckill_price` decimal(10, 2) NOT NULL COMMENT '秒杀价格(冗余字段)',
  `goods_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '商品名称(冗余字段)',
  `goods_img` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '商品图片(冗余字段)',
  `status` tinyint NOT NULL DEFAULT 0 COMMENT '订单状态(0:待支付 1:待发货 2:已发货 3:已收货 4:已退款 5:已取消 6:已关闭)',
  `expire_time` datetime NOT NULL COMMENT '订单过期时间',
  `is_deleted` tinyint NOT NULL DEFAULT 0 COMMENT '是否删除(0:未删除 1:已删除)',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_user_activity_goods`(`user_id` ASC, `activity_id` ASC, `goods_id` ASC) USING BTREE,
  UNIQUE INDEX `uk_order_no`(`order_no` ASC) USING BTREE,
  INDEX `idx_user_id`(`user_id` ASC) USING BTREE,
  INDEX `idx_activity_id`(`activity_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '秒杀订单表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_seckill_order
-- ----------------------------

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `nickname` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户昵称',
  `password` varchar(72) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '密码(加密存储)',
  `mobile` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '手机号',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '头像URL',
  `status` tinyint NOT NULL DEFAULT 1 COMMENT '状态(0:禁用 1:启用)',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_mobile`(`mobile` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES (1, '李仿', '123456', '15149845307', NULL, 1, '2026-04-14 15:33:35', '2026-04-14 15:33:35');
INSERT INTO `t_user` VALUES (2, '用户530865', '$2a$10$56gd43QVB4cV0cKGAXkzUOurUMVgZBehHNqLpcZ10.kVJygx54Glm', '13394868288', NULL, 1, '2026-04-14 08:14:47', '2026-04-14 08:14:47');

SET FOREIGN_KEY_CHECKS = 1;
