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

 Date: 11/05/2026 00:44:46
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
INSERT INTO `redis_sync_temp` VALUES (1, 'view', '1300');

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
) ENGINE = InnoDB AUTO_INCREMENT = 17 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '新闻主表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_news
-- ----------------------------
INSERT INTO `t_news` VALUES (1, '人工智能新突破', 'AI 在医疗领域取得重大进展', 1, '/img/ai.jpg', '详细内容...', 1, 1, 1, '2026-04-22 23:02:42', 1, 0, '2026-04-22 23:02:42', '2026-04-22 23:02:42');
INSERT INTO `t_news` VALUES (2, '区块链技术落地应用', '供应链金融案例', 3, '/img/blockchain.jpg', '详细内容...', 1, 1, 1, '2026-04-22 23:02:42', 1, 0, '2026-04-22 23:02:42', '2026-04-22 23:02:42');
INSERT INTO `t_news` VALUES (3, '云计算市场增长', '2026年份额预测', 1, '/img/cloud.jpg', '详细内容...', 1, 1, 1, '2026-04-22 23:02:42', 1, 0, '2026-04-22 23:02:42', '2026-04-22 23:02:42');
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
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '新闻分类表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_news_category
-- ----------------------------
INSERT INTO `t_news_category` VALUES (1, '科技', 0, 1, 1, 0, '2026-04-22 23:02:41', NULL);
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
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '新闻评论表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_news_comment
-- ----------------------------
INSERT INTO `t_news_comment` VALUES (1, 1, 2, '写得不错，很有深度！', 1, 0, NULL, NULL, 0, '2026-04-22 23:10:00');
INSERT INTO `t_news_comment` VALUES (2, 1, 3, '期待更多类似内容', 0, 0, NULL, NULL, 0, '2026-04-22 23:11:00');
INSERT INTO `t_news_comment` VALUES (3, 2, 4, '区块链确实在落地', 1, 0, NULL, NULL, 0, '2026-04-22 23:12:00');
INSERT INTO `t_news_comment` VALUES (4, 5, 2, '5G速度很快', 2, 0, 1, '2026-04-22 23:30:00', 0, '2026-04-22 23:13:00');
INSERT INTO `t_news_comment` VALUES (5, 8, 5, '电影好看', 0, 1, NULL, NULL, 0, '2026-04-22 23:14:00');

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
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '新闻点赞表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_news_like
-- ----------------------------
INSERT INTO `t_news_like` VALUES (1, 2, 1, 1, 0, '2026-04-22 11:02:42', '2026-04-22 11:02:42');
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
INSERT INTO `t_news_statistics` VALUES (1, 1, 1259, 3, 1, 80, '2026-04-30 22:21:26', 0, '2026-04-22 23:02:42', '2026-05-10 20:16:00');
INSERT INTO `t_news_statistics` VALUES (2, 2, 987, 2, 1, 80, '2026-04-30 22:21:26', 0, '2026-04-22 23:02:42', '2026-05-10 20:16:00');
INSERT INTO `t_news_statistics` VALUES (3, 3, 756, 1, 1, 80, '2026-04-30 22:21:26', 0, '2026-04-22 23:02:42', '2026-05-10 20:16:00');
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
) ENGINE = InnoDB AUTO_INCREMENT = 31 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '新闻标签表' ROW_FORMAT = Dynamic;

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
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '角色表' ROW_FORMAT = Dynamic;

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
INSERT INTO `t_sys_config` VALUES (1, 'site_name', '新闻资讯发布与推荐系统', 'system', '网站名称', 0, '2026-04-22 23:02:42', '2026-04-22 23:02:42');
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
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES (1, 'admin', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIUi', '超级管理员', 1, 1, 0, '2026-04-22 23:02:41', '2026-05-10 20:14:24', '2026-04-29 20:43:35', '0:0:0:0:0:0:0:1');
INSERT INTO `t_user` VALUES (2, 'user1', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIUi', '测试用户1', 2, 1, 0, '2026-04-22 23:02:41', '2026-05-10 20:21:28', '2026-04-29 21:08:00', '0:0:0:0:0:0:0:1');
INSERT INTO `t_user` VALUES (3, 'user2', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIUi', '测试用户2', 2, 0, 0, '2026-04-22 23:02:41', '2026-05-10 20:21:57', '2026-04-22 23:02:41', '127.0.0.1');
INSERT INTO `t_user` VALUES (4, 'user3', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIUi', '测试用户3', 2, 1, 1, '2026-04-22 23:02:41', '2026-05-10 20:22:38', '2026-04-22 23:02:41', '127.0.0.1');
INSERT INTO `t_user` VALUES (5, 'user4', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIUi', '测试用户4', 2, 0, 1, '2026-04-22 23:02:41', '2026-05-10 20:22:45', '2026-04-22 23:02:41', '127.0.0.1');
INSERT INTO `t_user` VALUES (8, 'user5', '$2a$10$J54LDOc5NT7g4GBvtDUqXORFKEal98Tv4poKQ4a2dBUIlKZ2H6maC', '测试账号停用', 2, 1, 0, '2026-05-10 21:12:13', NULL, NULL, '');

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
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户浏览记录表' ROW_FORMAT = Dynamic;

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
