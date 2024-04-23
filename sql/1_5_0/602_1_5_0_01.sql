INSERT INTO `uaa_oauth2_client` (`client_id`, `client_id_issued_date`, `client_name`, `client_secret`, `client_secret_exp_date`, `scopes`, `auth_grant_types`, `client_authn_methods`, `redirect_uris`, `post_logout_redirect_uris`, `cs_require_proof_key`, `cs_require_auth_consent`, `ts_auth_code_validity`, `ts_access_token_validity`, `ts_reuse_refresh_tokens`, `ts_refresh_token_validity`, `create_time`, `created_by`, `update_by`, `update_time`, `version`)
VALUES ( 'zion', '2023-11-13 21:35:50', 'zion小程序工具', '{bcrypt}$2a$10$VCVHpWipe9Bi3r7cPwA9suUvnX5jvpocyzHtVa4RaBU8QrEEB5LuW', '2099-08-07 12:08:23', 'DICFREE_SI_ZION', 'client_credentials', 'client_secret_post', 'http://localhost:8081/api/sso/admin/sometest', NULL, b'0', b'0', 300, 43200, b'1', 2592000, NULL, NULL, NULL, NULL, 0);


-- ----------------------------
-- Table structure for `df_mct_member`
-- ----------------------------
DROP TABLE IF EXISTS `df_mct_member`;
CREATE TABLE `df_mct_member`
(
    `id`          bigint(20) NOT NULL AUTO_INCREMENT,
    `navigation_no`   varchar(6)  DEFAULT NULL COMMENT '海运编号',
    `aviation_no`   varchar(6)  DEFAULT NULL COMMENT '航运编号',
    `nickname`    varchar(30) DEFAULT NULL COMMENT '昵称',
    `avatar`      varchar(40) DEFAULT NULL COMMENT '头像',
    `phone`      varchar(20) DEFAULT NULL COMMENT '手机号',

    `create_time` datetime    DEFAULT NULL,
    `created_by`  varchar(30) DEFAULT NULL,
    `update_time` datetime    DEFAULT NULL,
    `update_by`   varchar(30) DEFAULT NULL,
    `version`     int(11) DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT ='前端用户信息';


-- ----------------------------
-- Table structure for `df_mct_weixin`
-- ----------------------------
DROP TABLE IF EXISTS `df_mct_weixin`;
CREATE TABLE `df_mct_weixin`
(
    `id`                     bigint(20) NOT NULL AUTO_INCREMENT,
    `member_id`              bigint(20)  DEFAULT NULL COMMENT '会员id',
    `union_id`               varchar(30)  DEFAULT NULL COMMENT '微信UID',
    `type`                   varchar(10) DEFAULT NULL COMMENT '微信类型',
    `open_id`                varchar(30) DEFAULT NULL COMMENT '微信openid',
    `access_token`           varchar(60) DEFAULT NULL COMMENT '微信accessToken有效期（2小时）',
    `access_token_exp_date`  datetime DEFAULT NULL COMMENT 'accessToken过期时间',
    `refresh_token`          varchar(60) DEFAULT NULL COMMENT '微信refreshToken有效期（30天）',
    `refresh_token_exp_date` datetime DEFAULT NULL COMMENT 'refreshToken过期时间',
    `subscribed`             bit(1)      DEFAULT NULL COMMENT '是否订阅了微信账号',

    `create_time` datetime    DEFAULT NULL,
    `created_by`  varchar(30) DEFAULT NULL,
    `update_time` datetime    DEFAULT NULL,
    `update_by`   varchar(30) DEFAULT NULL,
    `version`     int(11) DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT ='前端用户微信信息';

-- ----------------------------
-- Table structure for df_mct_member_address
-- ----------------------------
DROP TABLE IF EXISTS `df_mct_member_address`;
CREATE TABLE `df_mct_member_address`
(
    `id`          bigint(20) NOT NULL AUTO_INCREMENT,
    `member_id`    bigint(20) DEFAULT NULL COMMENT '会员id',
    `name`        varchar(60)  DEFAULT NULL COMMENT '收件人姓名',
    `phone`       varchar(30)  DEFAULT NULL COMMENT '收件人主叫电话',
    `province`    varchar(120) DEFAULT NULL COMMENT '收件人省份',
    `city`        varchar(120) DEFAULT NULL COMMENT '收件人城市',
    `address`     varchar(240) DEFAULT NULL COMMENT '收件人详细地址',
    `default_one` bit(1)       DEFAULT NULL COMMENT '是否默认地址',

    `create_time` datetime    DEFAULT NULL,
    `created_by`  varchar(30) DEFAULT NULL,
    `update_time` datetime    DEFAULT NULL,
    `update_by`   varchar(30) DEFAULT NULL,
    `version`     int(11) DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT ='前端用户地址信息';

-- ----------------------------
-- Table structure for df_cts_transport_order
-- ----------------------------
DROP TABLE IF EXISTS `df_cts_transport_order`;
CREATE TABLE `df_cts_transport_order`
(
    `id`        bigint(20) NOT NULL AUTO_INCREMENT,
    `member_id` bigint(20) DEFAULT NULL COMMENT '会员id',
    `waybill`             varchar(30)    DEFAULT NULL COMMENT '快递面单号',
    `amount`              decimal(19, 2) DEFAULT NULL COMMENT '计价金额',
    `weight`              float          DEFAULT NULL COMMENT '包裹重量',
    `length`              float          DEFAULT NULL COMMENT '包裹长度',
    `width`               float          DEFAULT NULL COMMENT '包裹宽度',
    `height`              float          DEFAULT NULL COMMENT '包裹高度',
    `transportable`       bit(1)         DEFAULT NULL COMMENT '是否可运',
    `transport_mode`      varchar(20)    DEFAULT NULL COMMENT '运单模式',
    `status`              varchar(20)    DEFAULT NULL COMMENT '运单状态',
    `first_inbound_time`  datetime       DEFAULT NULL COMMENT '头程入库时间',
    `first_delivery_time` datetime       DEFAULT NULL COMMENT '头程发货时间',
    `last_inbound_time`   datetime       DEFAULT NULL COMMENT '尾程入库时间',
    `last_delivery_time`  datetime       DEFAULT NULL COMMENT '尾程派送时间',
    `last_signing_time`   datetime       DEFAULT NULL COMMENT '尾程签收时间',

    `name`        varchar(60)  DEFAULT NULL COMMENT '收件人姓名',
    `phone`       varchar(30)  DEFAULT NULL COMMENT '收件人主叫电话',
    `province`    varchar(120) DEFAULT NULL COMMENT '收件人省份',
    `city`        varchar(120) DEFAULT NULL COMMENT '收件人城市',
    `address`     varchar(240) DEFAULT NULL COMMENT '收件人详细地址',

    `create_time` datetime    DEFAULT NULL,
    `created_by`  varchar(30) DEFAULT NULL,
    `update_time` datetime    DEFAULT NULL,
    `update_by`   varchar(30) DEFAULT NULL,
    `version`     int(11) DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT ='用户转运订单';


-- 缓存MCT_MEMBER_NAVIGATION_NO_V初始值 设置为111110
-- 缓存MCT_MEMBER_AVIATION_NO_V初始值 设置为222220
-- 创建一个DICFREE_SI_ZION的角色
-- 修改角色DICFREE_CLIENT_ALL-> DICFREE_SI_QINGFLOW
-- 修改角色SE_CLIENT_ALL-> DICFREE_SE_SUPPLIER
-- 修改角色DICFREE_PDA_ALL-> DICFREE_SI_PDA