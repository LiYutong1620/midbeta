/*
 Navicat Premium Dump SQL

 Source Server         : 本地
 Source Server Type    : MySQL
 Source Server Version : 80044 (8.0.44)
 Source Host           : localhost:3306
 Source Schema         : news_cms

 Target Server Type    : MySQL
 Target Server Version : 80044 (8.0.44)
 File Encoding         : 65001

 Date: 17/05/2026 20:45:23
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for redis_sync_temp
-- ----------------------------
DROP TABLE IF EXISTS `redis_sync_temp`;
CREATE TABLE `redis_sync_temp`  (
  `news_id` bigint UNSIGNED NOT NULL,
  `type` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `value` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of redis_sync_temp
-- ----------------------------

-- ----------------------------
-- Table structure for t_news
-- ----------------------------
DROP TABLE IF EXISTS `t_news`;
CREATE TABLE `t_news`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT,
  `title` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `summary` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '新闻摘要，用于列表展示',
  `category_id` bigint UNSIGNED NOT NULL,
  `cover_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `content` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `publish_status` tinyint UNSIGNED NOT NULL DEFAULT 0 COMMENT '0:草稿, 1:已发布',
  `shelf_status` tinyint UNSIGNED NOT NULL DEFAULT 1 COMMENT '0:下架, 1:上架',
  `publish_user_id` bigint UNSIGNED NOT NULL,
  `publish_time` datetime NULL DEFAULT NULL,
  `version` int UNSIGNED NOT NULL DEFAULT 1,
  `is_deleted` tinyint UNSIGNED NOT NULL DEFAULT 0,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_news_query`(`category_id` ASC, `shelf_status` ASC, `publish_status` ASC, `publish_time` DESC) USING BTREE,
  INDEX `fk_t_news_publish_user_id`(`publish_user_id` ASC) USING BTREE,
  CONSTRAINT `fk_t_news_category_id` FOREIGN KEY (`category_id`) REFERENCES `t_news_category` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_t_news_publish_user_id` FOREIGN KEY (`publish_user_id`) REFERENCES `t_user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 24 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '新闻主表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_news
-- ----------------------------
INSERT INTO `t_news` VALUES (1, '人工智能新突破', 'AI 在医疗领域取得重大进展', 1, '/upload/cc89bd4d-f8b8-4639-a602-e26a4f252340.webp', '<p>详细内容...</p>', 1, 1, 1, '2026-04-22 23:02:42', 1, 0, '2026-04-22 23:02:42', '2026-05-12 16:39:14');
INSERT INTO `t_news` VALUES (2, '区块链技术落地应用', '供应链金融案例', 3, '/upload/59359136-50f3-4e6c-82e1-bc639ed00e3c.png', '详细内容...', 1, 1, 1, '2026-04-22 23:02:42', 1, 0, '2026-04-22 23:02:42', '2026-05-12 16:39:29');
INSERT INTO `t_news` VALUES (3, '云计算市场增长', '2026年份额预测', 1, '/upload/1440f4d5-070c-490c-88a5-5ca1076f2618.png', '详细内容...', 1, 1, 1, '2026-04-22 23:02:42', 1, 0, '2026-04-22 23:02:42', '2026-05-12 16:39:42');
INSERT INTO `t_news` VALUES (4, '大数据驱动营销', '精准投放案例', 1, '/img/bigdata.jpg', '详细内容...', 1, 1, 1, '2026-04-22 23:02:42', 1, 0, '2026-04-22 23:02:42', '2026-04-22 23:02:42');
INSERT INTO `t_news` VALUES (5, '5G+智能手机体验', '新一代通信终端', 2, '/img/5g.jpg', '详细内容...', 1, 1, 1, '2026-04-22 23:02:42', 1, 0, '2026-04-22 23:02:42', '2026-04-22 23:02:42');
INSERT INTO `t_news` VALUES (6, 'A股市场持续走强', '科技股领涨', 3, '/img/stock.jpg', '详细内容...', 1, 1, 1, '2026-04-22 23:02:42', 1, 0, '2026-04-22 23:02:42', '2026-04-22 23:02:42');
INSERT INTO `t_news` VALUES (7, '欧洲杯决赛前瞻', '强强对话预测', 4, '/img/football.jpg', '详细内容...', 1, 1, 1, '2026-04-22 23:02:42', 1, 0, '2026-04-22 23:02:42', '2026-04-22 23:02:42');
INSERT INTO `t_news` VALUES (8, '新片上映票房破亿', '国产科幻崛起', 5, '/img/movie.jpg', '详细内容...', 1, 1, 1, '2026-04-22 23:02:42', 1, 0, '2026-04-22 23:02:42', '2026-04-22 23:02:42');
INSERT INTO `t_news` VALUES (9, '高考改革最新政策', '选科模式调整', 6, '/img/gaokao.jpg', '详细内容...', 1, 1, 1, '2026-04-22 23:02:42', 1, 0, '2026-04-22 23:02:42', '2026-04-22 23:02:42');
INSERT INTO `t_news` VALUES (10, '日本樱花季攻略', '东京+关西路线', 7, '/img/japan.jpg', '详细内容...', 1, 1, 1, '2026-04-22 23:02:42', 1, 0, '2026-04-22 23:02:42', '2026-04-22 23:02:42');
INSERT INTO `t_news` VALUES (11, '川菜经典回锅肉', '家常做法详解', 8, '/img/food.jpg', '详细内容...', 1, 1, 1, '2026-04-22 23:02:42', 1, 0, '2026-04-22 23:02:42', '2026-04-22 23:02:42');
INSERT INTO `t_news` VALUES (12, '新能源汽车交付量', '特斯拉VS比亚迪', 9, '/img/ev.jpg', '详细内容...', 1, 1, 1, '2026-04-22 23:02:42', 1, 0, '2026-04-22 23:02:42', '2026-04-22 23:02:42');
INSERT INTO `t_news` VALUES (13, 'AI+智能手机未来', '端侧大模型落地', 2, '/img/aiphone.jpg', '详细内容...', 1, 1, 1, '2026-04-22 23:02:42', 1, 0, '2026-04-22 23:02:42', '2026-04-22 23:02:42');
INSERT INTO `t_news` VALUES (14, '装修避坑指南', '水电隐蔽工程', 10, '/img/decor.jpg', '详细内容...', 1, 1, 1, '2026-04-22 23:02:42', 1, 0, '2026-04-22 23:02:42', '2026-04-22 23:02:42');
INSERT INTO `t_news` VALUES (15, 'RPG游戏推荐', '年度必玩清单', 12, '/img/rpg.jpg', '详细内容...', 1, 1, 1, '2026-04-22 23:02:42', 1, 0, '2026-04-22 23:02:42', '2026-04-22 23:02:42');
INSERT INTO `t_news` VALUES (16, '养生误区辟谣', '常见健康谣言', 11, '/img/health.jpg', '详细内容...', 0, 1, 1, NULL, 1, 0, '2026-04-22 23:02:42', NULL);
INSERT INTO `t_news` VALUES (22, 'NIKKI', 'NIKKI', 12, '/upload/ede9ccda-ccb1-4ff5-b503-df811f56d47b.jpg', '<p>NIKKINIKKI</p>', 1, 1, 1, NULL, 1, 0, '2026-05-11 19:54:14', NULL);
INSERT INTO `t_news` VALUES (23, 'zAA', '', 1, '', '<p>IT之家 5 月 13 日消息，中国载人航天工程办公室昨晚正式发布神舟二十三号载人飞行任务的最新版标识，<strong>相较于此前的版本在细节上进行了微调</strong>。</p><p><span style=\"background-color: rgb(242, 242, 242);\"><img src=\"https://img-s.msn.cn/tenant/amp/entityid/AA232Iu1.img?w=612&amp;h=612&amp;m=6\" alt=\"图片\"></span></p>', 1, 1, 1, NULL, 1, 0, '2026-05-13 19:32:29', '2026-05-13 19:32:41');

-- ----------------------------
-- Table structure for t_news_category
-- ----------------------------
DROP TABLE IF EXISTS `t_news_category`;
CREATE TABLE `t_news_category`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT,
  `category_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `parent_id` bigint UNSIGNED NOT NULL DEFAULT 0,
  `sort` int UNSIGNED NOT NULL DEFAULT 0,
  `status` tinyint UNSIGNED NOT NULL DEFAULT 1,
  `is_deleted` tinyint UNSIGNED NOT NULL DEFAULT 0,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_t_news_category_name`(`category_name` ASC) USING BTREE,
  INDEX `idx_t_news_category_parent_status`(`parent_id` ASC, `status` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 16 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '新闻分类表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_news_category
-- ----------------------------
INSERT INTO `t_news_category` VALUES (1, '科技', 0, 1, 1, 0, '2026-04-22 23:02:41', '2026-05-11 17:45:53');
INSERT INTO `t_news_category` VALUES (2, '数码', 0, 2, 1, 0, '2026-04-22 23:02:41', NULL);
INSERT INTO `t_news_category` VALUES (3, '财经', 0, 3, 1, 0, '2026-04-22 23:02:41', NULL);
INSERT INTO `t_news_category` VALUES (4, '体育', 0, 4, 1, 0, '2026-04-22 23:02:41', NULL);
INSERT INTO `t_news_category` VALUES (5, '娱乐', 0, 5, 1, 0, '2026-04-22 23:02:41', NULL);
INSERT INTO `t_news_category` VALUES (6, '教育', 0, 6, 1, 0, '2026-04-22 23:02:41', NULL);
INSERT INTO `t_news_category` VALUES (7, '旅游', 0, 7, 1, 0, '2026-04-22 23:02:41', NULL);
INSERT INTO `t_news_category` VALUES (8, '美食', 0, 8, 1, 0, '2026-04-22 23:02:41', NULL);
INSERT INTO `t_news_category` VALUES (9, '汽车', 0, 9, 1, 0, '2026-04-22 23:02:41', NULL);
INSERT INTO `t_news_category` VALUES (10, '房产', 0, 10, 1, 0, '2026-04-22 23:02:41', NULL);
INSERT INTO `t_news_category` VALUES (11, '健康', 0, 11, 1, 0, '2026-04-22 23:02:41', NULL);
INSERT INTO `t_news_category` VALUES (12, '游戏', 0, 12, 1, 0, '2026-04-22 23:02:41', NULL);
INSERT INTO `t_news_category` VALUES (14, '人文', 0, 13, 0, 0, '2026-05-11 16:25:24', NULL);
INSERT INTO `t_news_category` VALUES (15, '自然', 0, 14, 1, 0, '2026-05-11 16:26:09', NULL);

-- ----------------------------
-- Table structure for t_news_comment
-- ----------------------------
DROP TABLE IF EXISTS `t_news_comment`;
CREATE TABLE `t_news_comment`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT,
  `news_id` bigint UNSIGNED NOT NULL,
  `user_id` bigint UNSIGNED NOT NULL,
  `content` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `audit_status` tinyint UNSIGNED NOT NULL DEFAULT 0 COMMENT '0:待审核, 1:通过, 2:驳回',
  `is_hidden` tinyint UNSIGNED NOT NULL DEFAULT 0,
  `reviewer_id` bigint UNSIGNED NULL DEFAULT NULL,
  `review_time` datetime NULL DEFAULT NULL,
  `is_deleted` tinyint UNSIGNED NOT NULL DEFAULT 0,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_comment_news_audit`(`news_id` ASC, `audit_status` ASC, `is_hidden` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '新闻评论表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_news_comment
-- ----------------------------
INSERT INTO `t_news_comment` VALUES (1, 1, 2, '写得不错，很有深度！', 1, 0, NULL, NULL, 0, '2026-04-22 23:10:00');
INSERT INTO `t_news_comment` VALUES (2, 1, 3, '期待更多类似内容', 0, 0, NULL, NULL, 0, '2026-04-22 23:11:00');
INSERT INTO `t_news_comment` VALUES (3, 2, 4, '区块链确实在落地', 1, 0, NULL, NULL, 0, '2026-04-22 23:12:00');
INSERT INTO `t_news_comment` VALUES (4, 5, 2, '5G速度很快', 2, 0, 1, '2026-04-22 23:30:00', 0, '2026-04-22 23:13:00');
INSERT INTO `t_news_comment` VALUES (5, 8, 5, '电影好看', 2, 1, 1, '2026-05-13 19:33:20', 0, '2026-04-22 23:14:00');
INSERT INTO `t_news_comment` VALUES (9, 1, 2, '11111', 1, 1, 1, '2026-05-13 19:33:18', 0, '2026-05-12 16:11:37');

-- ----------------------------
-- Table structure for t_news_like
-- ----------------------------
DROP TABLE IF EXISTS `t_news_like`;
CREATE TABLE `t_news_like`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT,
  `user_id` bigint UNSIGNED NOT NULL,
  `news_id` bigint UNSIGNED NOT NULL,
  `like_status` tinyint UNSIGNED NOT NULL DEFAULT 1,
  `is_deleted` tinyint UNSIGNED NOT NULL DEFAULT 0,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_t_news_like_user_news`(`user_id` ASC, `news_id` ASC) USING BTREE,
  INDEX `fk_t_news_like_news_id`(`news_id` ASC) USING BTREE,
  CONSTRAINT `fk_t_news_like_news_id` FOREIGN KEY (`news_id`) REFERENCES `t_news` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `fk_t_news_like_user_id` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 20 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '新闻点赞表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_news_like
-- ----------------------------
INSERT INTO `t_news_like` VALUES (1, 2, 1, 1, 0, '2026-04-22 11:02:42', '2026-05-12 17:50:26');
INSERT INTO `t_news_like` VALUES (2, 2, 5, 1, 0, '2026-04-22 13:02:42', '2026-04-22 13:02:42');
INSERT INTO `t_news_like` VALUES (3, 2, 7, 1, 0, '2026-04-22 15:02:42', '2026-04-22 15:02:42');
INSERT INTO `t_news_like` VALUES (4, 2, 8, 1, 0, '2026-04-22 17:02:42', '2026-04-22 17:02:42');
INSERT INTO `t_news_like` VALUES (5, 3, 1, 1, 0, '2026-04-22 12:02:42', '2026-04-22 12:02:42');
INSERT INTO `t_news_like` VALUES (6, 3, 2, 1, 0, '2026-04-22 14:02:42', '2026-04-22 14:02:42');
INSERT INTO `t_news_like` VALUES (7, 3, 3, 1, 0, '2026-04-22 16:02:42', '2026-04-22 16:02:42');
INSERT INTO `t_news_like` VALUES (8, 4, 1, 1, 0, '2026-04-22 13:02:42', '2026-04-22 13:02:42');
INSERT INTO `t_news_like` VALUES (9, 4, 4, 1, 0, '2026-04-22 15:02:42', '2026-04-22 15:02:42');
INSERT INTO `t_news_like` VALUES (10, 4, 5, 1, 0, '2026-04-22 18:02:42', '2026-04-22 18:02:42');
INSERT INTO `t_news_like` VALUES (11, 4, 8, 1, 0, '2026-04-22 20:02:42', '2026-04-22 20:02:42');
INSERT INTO `t_news_like` VALUES (12, 5, 2, 1, 0, '2026-04-22 14:02:42', '2026-04-22 14:02:42');
INSERT INTO `t_news_like` VALUES (13, 5, 6, 1, 0, '2026-04-22 16:02:42', '2026-04-22 16:02:42');
INSERT INTO `t_news_like` VALUES (14, 5, 7, 1, 0, '2026-04-22 19:02:42', '2026-04-22 19:02:42');
INSERT INTO `t_news_like` VALUES (19, 1, 1, 1, 0, '2026-05-12 17:51:11', '2026-05-13 19:34:51');

-- ----------------------------
-- Table structure for t_news_statistics
-- ----------------------------
DROP TABLE IF EXISTS `t_news_statistics`;
CREATE TABLE `t_news_statistics`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT,
  `news_id` bigint UNSIGNED NOT NULL,
  `read_count` int UNSIGNED NOT NULL DEFAULT 0,
  `like_count` int UNSIGNED NOT NULL DEFAULT 0,
  `comment_count` int UNSIGNED NOT NULL DEFAULT 0,
  `sync_version` int UNSIGNED NOT NULL DEFAULT 1,
  `sync_time` datetime NULL DEFAULT NULL,
  `is_deleted` tinyint UNSIGNED NOT NULL DEFAULT 0,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_t_news_statistics_news_id`(`news_id` ASC) USING BTREE,
  CONSTRAINT `fk_t_news_statistics_news_id` FOREIGN KEY (`news_id`) REFERENCES `t_news` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 17 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '新闻统计量表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_news_statistics
-- ----------------------------
INSERT INTO `t_news_statistics` VALUES (1, 1, 1338, 4, 1, 80, '2026-04-30 22:21:26', 0, '2026-04-22 23:02:42', '2026-05-13 19:34:57');
INSERT INTO `t_news_statistics` VALUES (2, 2, 1005, 2, 1, 80, '2026-04-30 22:21:26', 0, '2026-04-22 23:02:42', '2026-05-12 18:06:18');
INSERT INTO `t_news_statistics` VALUES (3, 3, 774, 1, 1, 80, '2026-04-30 22:21:26', 0, '2026-04-22 23:02:42', '2026-05-12 18:06:18');
INSERT INTO `t_news_statistics` VALUES (4, 4, 1023, 1, 0, 80, '2026-04-30 22:21:26', 0, '2026-04-22 23:02:42', '2026-05-10 20:16:00');
INSERT INTO `t_news_statistics` VALUES (5, 5, 1568, 2, 0, 80, '2026-04-30 22:21:26', 0, '2026-04-22 23:02:42', '2026-05-10 20:16:00');
INSERT INTO `t_news_statistics` VALUES (6, 6, 654, 1, 0, 80, '2026-04-30 22:21:26', 0, '2026-04-22 23:02:42', '2026-05-10 20:16:00');
INSERT INTO `t_news_statistics` VALUES (7, 7, 2345, 2, 2, 80, '2026-04-30 22:21:26', 0, '2026-04-22 23:02:42', '2026-05-10 20:16:00');
INSERT INTO `t_news_statistics` VALUES (8, 8, 3457, 2, 0, 80, '2026-04-30 22:21:26', 0, '2026-04-22 23:02:42', '2026-05-10 20:16:00');
INSERT INTO `t_news_statistics` VALUES (9, 9, 876, 56, 1, 80, '2026-04-30 22:21:26', 0, '2026-04-22 23:02:42', '2026-04-30 22:21:26');
INSERT INTO `t_news_statistics` VALUES (10, 10, 1124, 89, 0, 80, '2026-04-30 22:21:26', 0, '2026-04-22 23:02:42', '2026-04-30 22:21:26');
INSERT INTO `t_news_statistics` VALUES (11, 11, 567, 34, 0, 80, '2026-04-30 22:21:26', 0, '2026-04-22 23:02:42', '2026-04-30 22:21:26');
INSERT INTO `t_news_statistics` VALUES (12, 12, 1890, 145, 1, 80, '2026-04-30 22:21:26', 0, '2026-04-22 23:02:42', '2026-04-30 22:21:26');
INSERT INTO `t_news_statistics` VALUES (13, 13, 934, 67, 0, 80, '2026-04-30 22:21:26', 0, '2026-04-22 23:02:42', '2026-04-30 22:21:26');
INSERT INTO `t_news_statistics` VALUES (14, 14, 789, 45, 0, 80, '2026-04-30 22:21:26', 0, '2026-04-22 23:02:42', '2026-04-30 22:21:26');
INSERT INTO `t_news_statistics` VALUES (15, 15, 1456, 98, 0, 80, '2026-04-30 22:21:26', 0, '2026-04-22 23:02:42', '2026-04-30 22:21:26');
INSERT INTO `t_news_statistics` VALUES (16, 16, 0, 0, 0, 80, '2026-04-30 22:21:26', 0, '2026-04-22 23:02:42', '2026-04-30 22:21:26');

-- ----------------------------
-- Table structure for t_news_tag
-- ----------------------------
DROP TABLE IF EXISTS `t_news_tag`;
CREATE TABLE `t_news_tag`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT,
  `tag_name` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `is_deleted` tinyint UNSIGNED NOT NULL DEFAULT 0,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_t_news_tag_name`(`tag_name` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 33 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '新闻标签表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_news_tag
-- ----------------------------
INSERT INTO `t_news_tag` VALUES (1, '人工智能', 0, '2026-04-22 23:02:41');
INSERT INTO `t_news_tag` VALUES (2, '区块链', 0, '2026-04-22 23:02:41');
INSERT INTO `t_news_tag` VALUES (3, '云计算', 0, '2026-04-22 23:02:41');
INSERT INTO `t_news_tag` VALUES (4, '大数据', 0, '2026-04-22 23:02:41');
INSERT INTO `t_news_tag` VALUES (5, '5G', 0, '2026-04-22 23:02:41');
INSERT INTO `t_news_tag` VALUES (6, '智能手机', 0, '2026-04-22 23:02:41');
INSERT INTO `t_news_tag` VALUES (7, '电动汽车', 0, '2026-04-22 23:02:41');
INSERT INTO `t_news_tag` VALUES (8, '股市', 0, '2026-04-22 23:02:41');
INSERT INTO `t_news_tag` VALUES (9, '基金', 0, '2026-04-22 23:02:41');
INSERT INTO `t_news_tag` VALUES (10, '足球', 0, '2026-04-22 23:02:41');
INSERT INTO `t_news_tag` VALUES (11, '篮球', 0, '2026-04-22 23:02:41');
INSERT INTO `t_news_tag` VALUES (12, '电影', 0, '2026-04-22 23:02:41');
INSERT INTO `t_news_tag` VALUES (13, '音乐', 0, '2026-04-22 23:02:41');
INSERT INTO `t_news_tag` VALUES (14, '明星', 0, '2026-04-22 23:02:41');
INSERT INTO `t_news_tag` VALUES (15, '高考', 0, '2026-04-22 23:02:41');
INSERT INTO `t_news_tag` VALUES (16, '考研', 0, '2026-04-22 23:02:41');
INSERT INTO `t_news_tag` VALUES (17, '留学', 0, '2026-04-22 23:02:41');
INSERT INTO `t_news_tag` VALUES (18, '日本', 0, '2026-04-22 23:02:41');
INSERT INTO `t_news_tag` VALUES (19, '泰国', 0, '2026-04-22 23:02:41');
INSERT INTO `t_news_tag` VALUES (20, '川菜', 0, '2026-04-22 23:02:41');
INSERT INTO `t_news_tag` VALUES (21, '粤菜', 0, '2026-04-22 23:02:41');
INSERT INTO `t_news_tag` VALUES (22, '新能源汽车', 0, '2026-04-22 23:02:41');
INSERT INTO `t_news_tag` VALUES (23, 'SUV', 0, '2026-04-22 23:02:41');
INSERT INTO `t_news_tag` VALUES (24, '房价', 0, '2026-04-22 23:02:41');
INSERT INTO `t_news_tag` VALUES (25, '装修', 0, '2026-04-22 23:02:41');
INSERT INTO `t_news_tag` VALUES (26, '养生', 0, '2026-04-22 23:02:41');
INSERT INTO `t_news_tag` VALUES (27, '健身', 0, '2026-04-22 23:02:41');
INSERT INTO `t_news_tag` VALUES (28, 'RPG', 0, '2026-04-22 23:02:41');
INSERT INTO `t_news_tag` VALUES (29, 'MOBA', 0, '2026-04-22 23:02:41');
INSERT INTO `t_news_tag` VALUES (30, 'FPS', 0, '2026-04-22 23:02:41');

-- ----------------------------
-- Table structure for t_news_tag_rel
-- ----------------------------
DROP TABLE IF EXISTS `t_news_tag_rel`;
CREATE TABLE `t_news_tag_rel`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT,
  `news_id` bigint UNSIGNED NOT NULL,
  `tag_id` bigint UNSIGNED NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_t_news_tag_rel_news_tag`(`news_id` ASC, `tag_id` ASC) USING BTREE,
  INDEX `fk_t_news_tag_rel_tag_id`(`tag_id` ASC) USING BTREE,
  CONSTRAINT `fk_t_news_tag_rel_news_id` FOREIGN KEY (`news_id`) REFERENCES `t_news` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `fk_t_news_tag_rel_tag_id` FOREIGN KEY (`tag_id`) REFERENCES `t_news_tag` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 26 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '新闻标签关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_news_tag_rel
-- ----------------------------
INSERT INTO `t_news_tag_rel` VALUES (1, 1, 1, '2026-04-22 23:02:42');
INSERT INTO `t_news_tag_rel` VALUES (2, 1, 4, '2026-04-22 23:02:42');
INSERT INTO `t_news_tag_rel` VALUES (3, 2, 2, '2026-04-22 23:02:42');
INSERT INTO `t_news_tag_rel` VALUES (4, 2, 8, '2026-04-22 23:02:42');
INSERT INTO `t_news_tag_rel` VALUES (5, 3, 3, '2026-04-22 23:02:42');
INSERT INTO `t_news_tag_rel` VALUES (6, 4, 4, '2026-04-22 23:02:42');
INSERT INTO `t_news_tag_rel` VALUES (7, 5, 5, '2026-04-22 23:02:42');
INSERT INTO `t_news_tag_rel` VALUES (8, 5, 6, '2026-04-22 23:02:42');
INSERT INTO `t_news_tag_rel` VALUES (9, 6, 8, '2026-04-22 23:02:42');
INSERT INTO `t_news_tag_rel` VALUES (10, 6, 9, '2026-04-22 23:02:42');
INSERT INTO `t_news_tag_rel` VALUES (11, 7, 10, '2026-04-22 23:02:42');
INSERT INTO `t_news_tag_rel` VALUES (12, 8, 12, '2026-04-22 23:02:42');
INSERT INTO `t_news_tag_rel` VALUES (13, 8, 14, '2026-04-22 23:02:42');
INSERT INTO `t_news_tag_rel` VALUES (14, 9, 15, '2026-04-22 23:02:42');
INSERT INTO `t_news_tag_rel` VALUES (15, 10, 17, '2026-04-22 23:02:42');
INSERT INTO `t_news_tag_rel` VALUES (16, 10, 18, '2026-04-22 23:02:42');
INSERT INTO `t_news_tag_rel` VALUES (17, 11, 20, '2026-04-22 23:02:42');
INSERT INTO `t_news_tag_rel` VALUES (18, 12, 7, '2026-04-22 23:02:42');
INSERT INTO `t_news_tag_rel` VALUES (19, 12, 22, '2026-04-22 23:02:42');
INSERT INTO `t_news_tag_rel` VALUES (20, 13, 1, '2026-04-22 23:02:42');
INSERT INTO `t_news_tag_rel` VALUES (21, 13, 4, '2026-04-22 23:02:42');
INSERT INTO `t_news_tag_rel` VALUES (22, 13, 6, '2026-04-22 23:02:42');
INSERT INTO `t_news_tag_rel` VALUES (23, 14, 25, '2026-04-22 23:02:42');
INSERT INTO `t_news_tag_rel` VALUES (24, 14, 26, '2026-04-22 23:02:42');
INSERT INTO `t_news_tag_rel` VALUES (25, 15, 28, '2026-04-22 23:02:42');

-- ----------------------------
-- Table structure for t_role
-- ----------------------------
DROP TABLE IF EXISTS `t_role`;
CREATE TABLE `t_role`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `role_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '角色名称',
  `role_code` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '角色编码',
  `is_deleted` tinyint UNSIGNED NOT NULL DEFAULT 0,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_t_role_role_code`(`role_code` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_role
-- ----------------------------
INSERT INTO `t_role` VALUES (1, '管理员', 'ADMIN', 0, '2026-04-22 23:02:41');
INSERT INTO `t_role` VALUES (2, '普通用户', 'USER', 0, '2026-04-22 23:02:41');

-- ----------------------------
-- Table structure for t_sys_config
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_config`;
CREATE TABLE `t_sys_config`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT,
  `config_key` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `config_value` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `config_type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT 'system',
  `remark` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `is_deleted` tinyint UNSIGNED NOT NULL DEFAULT 0,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_t_sys_config_key`(`config_key` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '系统配置表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_sys_config
-- ----------------------------
INSERT INTO `t_sys_config` VALUES (1, 'site_name', '新闻资讯发布与推荐系统', 'system', '网站名称', 0, '2026-04-22 23:02:42', '2026-05-11 18:17:45');
INSERT INTO `t_sys_config` VALUES (2, 'site_logo', '/static/logo.png', 'system', '网站Logo', 0, '2026-04-22 23:02:42', '2026-04-22 23:02:42');
INSERT INTO `t_sys_config` VALUES (3, 'site_url', 'http://localhost:8080', 'system', '网站地址', 0, '2026-04-22 23:02:42', '2026-04-22 23:02:42');
INSERT INTO `t_sys_config` VALUES (4, 'contact_email', 'admin@example.com', 'contact', '联系邮箱', 0, '2026-04-22 23:02:42', '2026-04-22 23:02:42');
INSERT INTO `t_sys_config` VALUES (5, 'contact_phone', '400-123-4567', 'contact', '联系电话', 0, '2026-04-22 23:02:42', '2026-04-22 23:02:42');
INSERT INTO `t_sys_config` VALUES (6, 'sensitive_words', '垃圾,威信,辱骂,色情,赌博', 'content', '敏感词列表（逗号分隔）', 0, '2026-04-22 23:02:42', '2026-04-22 23:02:42');
INSERT INTO `t_sys_config` VALUES (7, 'comment_audit', 'true', 'content', '评论审核开关', 0, '2026-04-22 23:02:42', '2026-04-22 23:02:42');
INSERT INTO `t_sys_config` VALUES (8, 'cache_home_list_ttl', '3600', 'cache', '首页列表缓存时间（秒）', 0, '2026-04-22 23:02:42', '2026-04-22 23:02:42');
INSERT INTO `t_sys_config` VALUES (9, 'cache_detail_ttl', '7200', 'cache', '详情页缓存时间（秒）', 0, '2026-04-22 23:02:42', '2026-04-22 23:02:42');
INSERT INTO `t_sys_config` VALUES (10, 'sync_counter_interval', '300', 'cache', '计数器同步间隔（秒）', 0, '2026-04-22 23:02:42', '2026-04-22 23:02:42');

-- ----------------------------
-- Table structure for t_sys_operation_log
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_operation_log`;
CREATE TABLE `t_sys_operation_log`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT,
  `user_id` bigint UNSIGNED NOT NULL,
  `operation_type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `operation_content` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `operation_result` tinyint UNSIGNED NOT NULL DEFAULT 1,
  `operation_ip` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `is_deleted` tinyint UNSIGNED NOT NULL DEFAULT 0,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_t_sys_operation_log_user_type`(`user_id` ASC, `operation_type` ASC) USING BTREE,
  CONSTRAINT `fk_t_sys_operation_log_user_id` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '系统操作日志表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_sys_operation_log
-- ----------------------------
INSERT INTO `t_sys_operation_log` VALUES (1, 2, 'LOGIN', '用户登录成功', 1, '0:0:0:0:0:0:0:1', 0, '2026-04-29 20:58:41');
INSERT INTO `t_sys_operation_log` VALUES (2, 2, 'LOGIN', '用户登录成功', 1, '0:0:0:0:0:0:0:1', 0, '2026-04-29 21:08:00');

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT,
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `password_hash` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `nickname` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `role_id` bigint UNSIGNED NOT NULL DEFAULT 1,
  `status` tinyint UNSIGNED NOT NULL DEFAULT 1,
  `is_deleted` tinyint UNSIGNED NOT NULL DEFAULT 0,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `last_login_at` datetime NULL DEFAULT NULL,
  `last_login_ip` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_t_user_username`(`username` ASC) USING BTREE,
  INDEX `idx_t_user_role_status`(`role_id` ASC, `status` ASC) USING BTREE,
  CONSTRAINT `fk_t_user_role_id` FOREIGN KEY (`role_id`) REFERENCES `t_role` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES (1, 'admin', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIUi', '超级管理员', 1, 1, 0, '2026-04-22 23:02:41', '2026-05-10 20:14:24', '2026-04-29 20:43:35', '0:0:0:0:0:0:0:1');
INSERT INTO `t_user` VALUES (2, 'user1', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIUi', '测试用户1', 2, 1, 0, '2026-04-22 23:02:41', '2026-05-11 18:03:23', '2026-04-29 21:08:00', '0:0:0:0:0:0:0:1');
INSERT INTO `t_user` VALUES (3, 'user2', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIUi', '测试用户2', 2, 0, 0, '2026-04-22 23:02:41', '2026-05-10 20:21:57', '2026-04-22 23:02:41', '127.0.0.1');
INSERT INTO `t_user` VALUES (4, 'user3', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIUi', '测试用户3', 2, 1, 1, '2026-04-22 23:02:41', '2026-05-10 20:22:38', '2026-04-22 23:02:41', '127.0.0.1');
INSERT INTO `t_user` VALUES (5, 'user4', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIUi', '测试用户4', 2, 0, 1, '2026-04-22 23:02:41', '2026-05-10 20:22:45', '2026-04-22 23:02:41', '127.0.0.1');
INSERT INTO `t_user` VALUES (9, 'user5', '$2a$10$LzsCQj4PpiZRgMQTeLGtQ.h0.s3YiMCbycZ8jAWxyYOhw8wIBKkxS', NULL, 2, 1, 0, '2026-05-11 11:23:15', NULL, NULL, NULL);

-- ----------------------------
-- Table structure for t_user_browse
-- ----------------------------
DROP TABLE IF EXISTS `t_user_browse`;
CREATE TABLE `t_user_browse`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT,
  `user_id` bigint UNSIGNED NOT NULL,
  `news_id` bigint UNSIGNED NOT NULL,
  `browse_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `browse_duration` int UNSIGNED NULL DEFAULT 0,
  `is_deleted` tinyint UNSIGNED NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_t_user_browse_user_time`(`user_id` ASC, `browse_time` ASC) USING BTREE,
  INDEX `fk_t_user_browse_news_id`(`news_id` ASC) USING BTREE,
  CONSTRAINT `fk_t_user_browse_news_id` FOREIGN KEY (`news_id`) REFERENCES `t_news` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `fk_t_user_browse_user_id` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 78 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户浏览记录表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_user_browse
-- ----------------------------
INSERT INTO `t_user_browse` VALUES (1, 2, 1, '2026-04-22 22:33:35', 120, 0);
INSERT INTO `t_user_browse` VALUES (2, 2, 5, '2026-04-22 22:03:35', 90, 0);
INSERT INTO `t_user_browse` VALUES (3, 2, 7, '2026-04-22 21:03:35', 180, 0);
INSERT INTO `t_user_browse` VALUES (4, 2, 8, '2026-04-22 20:03:35', 150, 0);
INSERT INTO `t_user_browse` VALUES (5, 3, 1, '2026-04-22 22:18:35', 100, 0);
INSERT INTO `t_user_browse` VALUES (6, 3, 2, '2026-04-22 21:33:35', 80, 0);
INSERT INTO `t_user_browse` VALUES (7, 3, 3, '2026-04-22 20:33:35', 200, 0);
INSERT INTO `t_user_browse` VALUES (8, 4, 1, '2026-04-22 22:43:35', 60, 0);
INSERT INTO `t_user_browse` VALUES (9, 4, 4, '2026-04-22 21:43:35', 110, 0);
INSERT INTO `t_user_browse` VALUES (10, 4, 5, '2026-04-22 20:43:35', 95, 0);
INSERT INTO `t_user_browse` VALUES (11, 5, 2, '2026-04-22 22:13:35', 130, 0);
INSERT INTO `t_user_browse` VALUES (12, 5, 6, '2026-04-22 21:13:35', 85, 0);
INSERT INTO `t_user_browse` VALUES (13, 5, 7, '2026-04-22 19:13:35', 160, 0);
INSERT INTO `t_user_browse` VALUES (14, 1, 1, '2026-05-11 20:42:34', 0, 0);
INSERT INTO `t_user_browse` VALUES (15, 1, 1, '2026-05-11 20:43:46', 0, 0);
INSERT INTO `t_user_browse` VALUES (16, 1, 1, '2026-05-11 20:59:00', 0, 0);
INSERT INTO `t_user_browse` VALUES (17, 1, 1, '2026-05-12 15:16:16', 0, 0);
INSERT INTO `t_user_browse` VALUES (18, 1, 1, '2026-05-12 15:17:16', 0, 0);
INSERT INTO `t_user_browse` VALUES (19, 1, 1, '2026-05-12 15:17:36', 0, 0);
INSERT INTO `t_user_browse` VALUES (20, 1, 1, '2026-05-12 15:19:27', 0, 0);
INSERT INTO `t_user_browse` VALUES (21, 1, 1, '2026-05-12 15:19:38', 0, 0);
INSERT INTO `t_user_browse` VALUES (22, 1, 1, '2026-05-12 15:48:42', 0, 0);
INSERT INTO `t_user_browse` VALUES (23, 1, 1, '2026-05-12 15:48:55', 0, 0);
INSERT INTO `t_user_browse` VALUES (24, 1, 1, '2026-05-12 15:49:00', 0, 0);
INSERT INTO `t_user_browse` VALUES (25, 1, 1, '2026-05-12 15:49:05', 0, 0);
INSERT INTO `t_user_browse` VALUES (26, 2, 1, '2026-05-12 16:04:16', 0, 0);
INSERT INTO `t_user_browse` VALUES (27, 2, 1, '2026-05-12 16:11:28', 0, 0);
INSERT INTO `t_user_browse` VALUES (28, 2, 1, '2026-05-12 16:11:50', 0, 0);
INSERT INTO `t_user_browse` VALUES (29, 2, 1, '2026-05-12 16:13:02', 0, 0);
INSERT INTO `t_user_browse` VALUES (30, 2, 1, '2026-05-12 16:13:18', 0, 0);
INSERT INTO `t_user_browse` VALUES (31, 1, 1, '2026-05-12 16:14:53', 0, 0);
INSERT INTO `t_user_browse` VALUES (32, 1, 1, '2026-05-12 16:15:00', 0, 0);
INSERT INTO `t_user_browse` VALUES (33, 1, 1, '2026-05-12 16:15:59', 0, 0);
INSERT INTO `t_user_browse` VALUES (34, 1, 1, '2026-05-12 16:31:14', 0, 0);
INSERT INTO `t_user_browse` VALUES (35, 1, 2, '2026-05-12 16:33:40', 0, 0);
INSERT INTO `t_user_browse` VALUES (36, 1, 3, '2026-05-12 16:34:53', 0, 0);
INSERT INTO `t_user_browse` VALUES (37, 1, 1, '2026-05-12 16:44:58', 0, 0);
INSERT INTO `t_user_browse` VALUES (38, 1, 1, '2026-05-12 16:57:15', 0, 0);
INSERT INTO `t_user_browse` VALUES (39, 1, 1, '2026-05-12 16:58:42', 0, 0);
INSERT INTO `t_user_browse` VALUES (40, 1, 1, '2026-05-12 16:58:47', 0, 0);
INSERT INTO `t_user_browse` VALUES (41, 1, 1, '2026-05-12 17:00:13', 0, 0);
INSERT INTO `t_user_browse` VALUES (42, 2, 1, '2026-05-12 17:01:06', 0, 0);
INSERT INTO `t_user_browse` VALUES (43, 2, 1, '2026-05-12 17:06:00', 0, 0);
INSERT INTO `t_user_browse` VALUES (44, 2, 1, '2026-05-12 17:18:15', 0, 0);
INSERT INTO `t_user_browse` VALUES (45, 2, 1, '2026-05-12 17:41:30', 0, 0);
INSERT INTO `t_user_browse` VALUES (46, 2, 1, '2026-05-12 17:41:48', 0, 0);
INSERT INTO `t_user_browse` VALUES (47, 2, 1, '2026-05-12 17:50:31', 0, 0);
INSERT INTO `t_user_browse` VALUES (48, 2, 1, '2026-05-12 17:50:42', 0, 0);
INSERT INTO `t_user_browse` VALUES (49, 1, 1, '2026-05-12 17:51:10', 0, 0);
INSERT INTO `t_user_browse` VALUES (50, 1, 1, '2026-05-12 17:51:19', 0, 0);
INSERT INTO `t_user_browse` VALUES (51, 1, 1, '2026-05-12 17:52:19', 0, 0);
INSERT INTO `t_user_browse` VALUES (52, 1, 1, '2026-05-12 18:05:55', 0, 0);
INSERT INTO `t_user_browse` VALUES (53, 1, 1, '2026-05-12 18:05:59', 0, 0);
INSERT INTO `t_user_browse` VALUES (54, 1, 1, '2026-05-12 18:06:03', 0, 0);
INSERT INTO `t_user_browse` VALUES (55, 1, 1, '2026-05-12 18:06:10', 0, 0);
INSERT INTO `t_user_browse` VALUES (56, 1, 1, '2026-05-12 18:06:12', 0, 0);
INSERT INTO `t_user_browse` VALUES (57, 1, 1, '2026-05-12 18:06:19', 0, 0);
INSERT INTO `t_user_browse` VALUES (58, 2, 1, '2026-05-12 18:11:12', 0, 0);
INSERT INTO `t_user_browse` VALUES (59, 2, 1, '2026-05-12 18:14:33', 0, 0);
INSERT INTO `t_user_browse` VALUES (60, 2, 1, '2026-05-12 18:14:43', 0, 0);
INSERT INTO `t_user_browse` VALUES (61, 2, 1, '2026-05-12 18:16:14', 0, 0);
INSERT INTO `t_user_browse` VALUES (62, 2, 1, '2026-05-12 18:18:54', 0, 0);
INSERT INTO `t_user_browse` VALUES (63, 2, 1, '2026-05-12 18:30:36', 0, 0);
INSERT INTO `t_user_browse` VALUES (64, 2, 1, '2026-05-12 18:30:38', 0, 0);
INSERT INTO `t_user_browse` VALUES (65, 2, 1, '2026-05-12 18:30:42', 0, 0);
INSERT INTO `t_user_browse` VALUES (66, 2, 1, '2026-05-12 18:30:44', 0, 0);
INSERT INTO `t_user_browse` VALUES (67, 2, 1, '2026-05-12 18:30:48', 0, 0);
INSERT INTO `t_user_browse` VALUES (68, 2, 1, '2026-05-12 18:30:50', 0, 0);
INSERT INTO `t_user_browse` VALUES (69, 2, 1, '2026-05-12 18:30:51', 0, 0);
INSERT INTO `t_user_browse` VALUES (70, 2, 1, '2026-05-12 18:30:53', 0, 0);
INSERT INTO `t_user_browse` VALUES (71, 2, 1, '2026-05-12 18:30:54', 0, 0);
INSERT INTO `t_user_browse` VALUES (72, 2, 1, '2026-05-12 18:30:55', 0, 0);
INSERT INTO `t_user_browse` VALUES (73, 2, 1, '2026-05-13 00:10:48', 0, 0);
INSERT INTO `t_user_browse` VALUES (74, 1, 1, '2026-05-13 19:33:35', 0, 0);
INSERT INTO `t_user_browse` VALUES (75, 1, 1, '2026-05-13 19:33:53', 0, 0);
INSERT INTO `t_user_browse` VALUES (76, 1, 1, '2026-05-13 19:34:21', 0, 0);
INSERT INTO `t_user_browse` VALUES (77, 1, 1, '2026-05-13 19:34:58', 0, 0);

-- ----------------------------
-- View structure for v_news_available
-- ----------------------------
DROP VIEW IF EXISTS `v_news_available`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `v_news_available` AS select `n`.`id` AS `id`,`n`.`title` AS `title`,`n`.`summary` AS `summary`,`n`.`category_id` AS `category_id`,`c`.`category_name` AS `category_name`,`n`.`cover_url` AS `cover_url`,`n`.`publish_time` AS `publish_time`,`u`.`nickname` AS `publish_username`,`s`.`read_count` AS `read_count`,`s`.`like_count` AS `like_count`,`s`.`comment_count` AS `comment_count` from (((`t_news` `n` left join `t_news_category` `c` on((`n`.`category_id` = `c`.`id`))) left join `t_news_statistics` `s` on((`n`.`id` = `s`.`news_id`))) left join `t_user` `u` on((`n`.`publish_user_id` = `u`.`id`))) where ((`n`.`publish_status` = 1) and (`n`.`shelf_status` = 1) and (`n`.`is_deleted` = 0) and (`c`.`is_deleted` = 0) and (`c`.`status` = 1));

-- ----------------------------
-- View structure for v_news_comment_pending
-- ----------------------------
DROP VIEW IF EXISTS `v_news_comment_pending`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `v_news_comment_pending` AS select `c`.`id` AS `id`,`c`.`news_id` AS `news_id`,`n`.`title` AS `news_title`,`c`.`user_id` AS `user_id`,`u`.`nickname` AS `nickname`,`c`.`content` AS `content`,`c`.`created_at` AS `created_at` from ((`t_news_comment` `c` left join `t_news` `n` on((`c`.`news_id` = `n`.`id`))) left join `t_user` `u` on((`c`.`user_id` = `u`.`id`))) where ((`c`.`audit_status` = 0) and (`c`.`is_deleted` = 0));

-- ----------------------------
-- Procedure structure for sync_news_statistics
-- ----------------------------
DROP PROCEDURE IF EXISTS `sync_news_statistics`;
delimiter ;;
CREATE PROCEDURE `sync_news_statistics`()
BEGIN
  DECLARE EXIT HANDLER FOR SQLEXCEPTION ROLLBACK;
  START TRANSACTION;
  
  -- 更新阅读量 (采用累加逻辑，如果你的Redis存的是增量)
  -- 如果Redis存的是全量，直接 SET s.read_count = r.value
  UPDATE t_news_statistics s
  JOIN redis_sync_temp r ON s.news_id = r.news_id AND r.type = 'view'
  SET s.read_count = r.value, s.sync_time = NOW(), s.sync_version = s.sync_version + 1;

  -- 更新点赞数
  UPDATE t_news_statistics s
  JOIN redis_sync_temp r ON s.news_id = r.news_id AND r.type = 'like'
  SET s.like_count = r.value, s.sync_time = NOW(), s.sync_version = s.sync_version + 1;

  -- 同步完后清空临时表
  TRUNCATE TABLE redis_sync_temp;
  COMMIT;
END
;;
delimiter ;

SET FOREIGN_KEY_CHECKS = 1;
