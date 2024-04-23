-- ----------------------------
-- Table structure for df_wms_arrival_order
-- ----------------------------
DROP TABLE IF EXISTS `df_wms_product_arrival_order`;
CREATE TABLE `df_wms_product_arrival_order`
(
    `id`                   bigint(20) NOT NULL AUTO_INCREMENT,
    `supplier`             varchar(20) DEFAULT NULL COMMENT '客户',
    `third_order_no`       varchar(30) DEFAULT NULL COMMENT '外部订单编号',
    `container_no_virtual` varchar(5)  DEFAULT NULL COMMENT '虚拟车号',
    `container_no_real`    varchar(20) DEFAULT NULL COMMENT '真实车号',
    `inbound_status`       varchar(20) DEFAULT NULL COMMENT '入库状态',
    `inbound_count`        int(11) DEFAULT NULL COMMENT '已入库数量',
    `on_shelf_status`      varchar(20) DEFAULT NULL COMMENT '上架状态',
    `on_shelf_count`       int(11) DEFAULT NULL COMMENT '已上架数量',
    `total_count`          int(11) DEFAULT NULL COMMENT '总计数量',

    `create_time`          datetime    DEFAULT NULL,
    `created_by`           varchar(30) DEFAULT NULL,
    `update_time`          datetime    DEFAULT NULL,
    `update_by`            varchar(30) DEFAULT NULL,
    `version`              int(11) DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT ='到货单';

-- ----------------------------
-- Table structure for df_wms_arrival_order_item
-- ----------------------------
DROP TABLE IF EXISTS `df_wms_product_arrival_order_item`;
CREATE TABLE `df_wms_product_arrival_order_item`
(
    `id`                    bigint(20) NOT NULL AUTO_INCREMENT,
    `product_arrival_order_id` bigint(20) DEFAULT NULL COMMENT '到货单id',
    `product_sku_code`         varchar(50) DEFAULT NULL COMMENT '商品编码',
    `inbound_status`        varchar(20) DEFAULT NULL COMMENT '入库状态',
    `inbound_count`         int(11) DEFAULT NULL COMMENT '已入库数量',
    `on_shelf_status`       varchar(20) DEFAULT NULL COMMENT '上架状态',
    `on_shelf_count`        int(11) DEFAULT NULL COMMENT '已上架数量',
    `total_count`           int(11) DEFAULT NULL COMMENT '总计数量',

    `create_time`           datetime    DEFAULT NULL,
    `created_by`            varchar(30) DEFAULT NULL,
    `update_time`           datetime    DEFAULT NULL,
    `update_by`             varchar(30) DEFAULT NULL,
    `version`               int(11) DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT ='到货单详情';

-- ----------------------------
-- Table structure for df_wms_device_pda
-- ----------------------------
DROP TABLE IF EXISTS `df_wms_device_pda`;
CREATE TABLE `df_wms_device_pda`
(
    `id`              bigint(20) NOT NULL AUTO_INCREMENT,
    `user_id`         bigint(20) DEFAULT NULL COMMENT '绑定用户id',
    `code`            varchar(20) DEFAULT NULL COMMENT 'PDA唯一编码',
    `name`            varchar(20) DEFAULT NULL COMMENT 'PDA名称',
    `key`             varchar(20) DEFAULT NULL COMMENT 'PDA登录密钥',
    `key_sign`        varchar(80) DEFAULT NULL COMMENT 'PDA登录密钥密文',
    `shelf_area_code` varchar(100)  DEFAULT NULL COMMENT 'PDA所属区域',

    `create_time`     datetime    DEFAULT NULL,
    `created_by`      varchar(30) DEFAULT NULL,
    `update_time`     datetime    DEFAULT NULL,
    `update_by`       varchar(30) DEFAULT NULL,
    `version`         int(11) DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT ='PDA设备';

-- ----------------------------
-- Table structure for df_wms_sku
-- ----------------------------
DROP TABLE IF EXISTS `df_wms_product_sku`;
CREATE TABLE `df_wms_product_sku`
(
    `id`           bigint(20) NOT NULL AUTO_INCREMENT,
    `code`         varchar(50) DEFAULT NULL COMMENT '商品编码',
    `product_code` varchar(30) DEFAULT NULL COMMENT '产品编码',
    `supplier`     varchar(20) DEFAULT NULL COMMENT '客户',

    `create_time`  datetime    DEFAULT NULL,
    `created_by`   varchar(30) DEFAULT NULL,
    `update_time`  datetime    DEFAULT NULL,
    `update_by`    varchar(30) DEFAULT NULL,
    `version`      int(11) DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT ='电商商品';

-- ----------------------------
-- Table structure for df_wms_sn
-- ----------------------------
DROP TABLE IF EXISTS `df_wms_product_sn`;
CREATE TABLE `df_wms_product_sn`
(
    `id`                     bigint(20) NOT NULL AUTO_INCREMENT,
    `product_sku_code`          varchar(50) DEFAULT NULL COMMENT '商品编码',
    `code`                   varchar(20) DEFAULT NULL COMMENT '序列商品编码',
    `shelf_area_code`        varchar(2)  DEFAULT NULL COMMENT '货架区域',
    `shelf_no`               varchar(20) DEFAULT NULL COMMENT '货架号',
    `stock_status`           varchar(20) DEFAULT NULL COMMENT '货物库存状态',
    `inbound_time`           datetime    DEFAULT NULL COMMENT '入库时间',
    `outbound_time`          datetime    DEFAULT NULL COMMENT '出库时间',
    `shelf_status`           varchar(20) DEFAULT NULL COMMENT '货物货架状态',
    `on_shelf_time`          datetime    DEFAULT NULL COMMENT '上架时间',
    `off_shelf_time`         datetime    DEFAULT NULL COMMENT '下架时间',
    `arrival_order_item_id`  bigint(20) DEFAULT NULL COMMENT '到货单详情id',
    `delivery_order_item_id` bigint(20) DEFAULT NULL COMMENT '出货单详情id',
    `pcs`                    int(11) DEFAULT NULL COMMENT '同一批到货数量',
    `serial_no`              varchar(3)  DEFAULT NULL COMMENT '序号',

    `create_time`            datetime    DEFAULT NULL,
    `created_by`             varchar(30) DEFAULT NULL,
    `update_time`            datetime    DEFAULT NULL,
    `update_by`              varchar(30) DEFAULT NULL,
    `version`                int(11) DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT ='序列商品';

INSERT INTO `uaa_resource` (`id`, `name`, `icon`, `url`, `menu`, `root`, `leaf`, `parent_id`, `path`, `depth`, `permit_type`, `sequence`, `ms_client_id`, `create_time`, `created_by`, `update_by`, `update_time`, `version`) VALUES (603, 'Product Warehouse', '🛒', '#3', b'1', b'1', b'0', 0, '/603/', 0, 'S', 2, 'dicfree', NULL, NULL, NULL, NULL, 0);
INSERT INTO `uaa_resource` (`id`, `name`, `icon`, `url`, `menu`, `root`, `leaf`, `parent_id`, `path`, `depth`, `permit_type`, `sequence`, `ms_client_id`, `create_time`, `created_by`, `update_by`, `update_time`, `version`) VALUES (60206000, 'Product Inbounds', NULL, '/admin/product-inbounds', b'1', b'0', b'1', 603, '/603/60206000/', 1, 'S', 200, 'dicfree', NULL, NULL, NULL, NULL, 0);

INSERT INTO `uaa_oauth2_client` (`id`, `client_id`, `client_id_issued_date`, `client_name`, `client_secret`, `client_secret_exp_date`, `scopes`, `auth_grant_types`, `client_authn_methods`, `redirect_uris`, `post_logout_redirect_uris`, `cs_require_proof_key`, `cs_require_auth_consent`, `ts_auth_code_validity`, `ts_access_token_validity`, `ts_reuse_refresh_tokens`, `ts_refresh_token_validity`, `create_time`, `created_by`, `update_by`, `update_time`, `version`)
VALUES (3, 'pda_gw', '2023-08-01 17:12:42.000000', 'PDA网关', '{bcrypt}$2a$10$VCVHpWipe9Bi3r7cPwA9suUvnX5jvpocyzHtVa4RaBU8QrEEB5LuW', '2099-08-07 12:08:23.000000', 'all', 'authorization_code,refresh_token,client_credentials', 'client_secret_post,client_secret_basic,client_secret_jwt,private_key_jwt', 'http://localhost:8081/api/sso/pda/sometest', NULL, b'0', b'0', 300, 43200, b'1', 2592000, NULL, NULL, NULL, NULL, 0);

INSERT INTO `uaa_privilege` (`id`, `code`, `name`, `create_time`, `created_by`, `update_time`, `update_by`, `version`) VALUES (60200002, 'DICFREE_PDA_ALL', 'PDA-全量权限', NULL, NULL, NULL, NULL, 0);
INSERT INTO `uaa_resource` (`id`, `name`, `icon`, `url`, `menu`, `root`, `leaf`, `parent_id`, `path`, `depth`, `permit_type`, `sequence`, `ms_client_id`, `create_time`, `created_by`, `update_by`, `update_time`, `version`) VALUES (60200008, 'PDA-全匹配', NULL, '/pda/**', b'0', NULL, NULL, NULL, NULL, NULL, 'A', NULL, 'dicfree', NULL, NULL, NULL, NULL, 0);
INSERT INTO `uaa_resource_privilege` (`resource_id`, `privilege_id`) VALUES (60200008, 60200002);
