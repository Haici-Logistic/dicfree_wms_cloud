-- ----------------------------
-- Table structure for sys_privilege
-- ----------------------------
DROP TABLE IF EXISTS `uaa_privilege`;
CREATE TABLE `uaa_privilege`
(
    `id`          bigint(20) NOT NULL AUTO_INCREMENT,
    `code`        varchar(30) DEFAULT NULL COMMENT '权限代码',
    `name`        varchar(60) DEFAULT NULL COMMENT '权限名称',

    `create_time` datetime    DEFAULT NULL,
    `created_by`  varchar(30)  DEFAULT NULL,
    `update_time` datetime    DEFAULT NULL,
    `update_by`   varchar(30)  DEFAULT NULL,
    `version`     int(11)     DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY UQE_UAA_PRIVILEGE_CODE (`code`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1000000000
  DEFAULT CHARSET = utf8mb4 COMMENT ='权限';

-- ----------------------------
-- Table structure for sys_resource
-- ----------------------------
DROP TABLE IF EXISTS `uaa_resource`;
CREATE TABLE `uaa_resource`
(
    `id`           bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `name`         varchar(60)  DEFAULT NULL COMMENT '名称',
    `icon`         varchar(60)  DEFAULT NULL COMMENT '图标',
    `url`          varchar(200) DEFAULT NULL COMMENT '菜单的url',
    `menu`         bit(1)       DEFAULT NULL COMMENT '是否菜单',
    `root`         bit(1)       DEFAULT NULL COMMENT '是否跟节点',
    `leaf`         bit(1)       DEFAULT NULL COMMENT '是否叶子节点',
    `parent_id`    bigint(20)   DEFAULT NULL COMMENT '父id',
    `path`         varchar(80)  DEFAULT NULL COMMENT '菜单路径',
    `depth`        int(11)      DEFAULT NULL COMMENT '菜单深度',
    `permit_type`  varchar(1)   DEFAULT NULL COMMENT '可访问类型',
    `sequence`     int(2)       DEFAULT NULL COMMENT '次序',
    `ms_client_id` varchar(20)  DEFAULT NULL COMMENT '资源所有者',

    `create_time`  datetime     DEFAULT NULL,
    `created_by`   varchar(30)   DEFAULT NULL,
    `update_time`  datetime     DEFAULT NULL,
    `update_by`    varchar(30)   DEFAULT NULL,
    `version`      int(11)      DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1000000000
  DEFAULT CHARSET = utf8mb4 COMMENT ='目录';

-- ----------------------------
-- Table structure for sys_resource_privilege
-- ----------------------------
DROP TABLE IF EXISTS `uaa_resource_privilege`;
CREATE TABLE `uaa_resource_privilege`
(
    `resource_id`  bigint(20) NOT NULL COMMENT '资源id',
    `privilege_id` bigint(20) NOT NULL COMMENT '权限id',
    PRIMARY KEY (`resource_id`, `privilege_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `uaa_role`;
CREATE TABLE `uaa_role`
(
    `id`          bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `code`        varchar(20) DEFAULT NULL COMMENT '角色代码',
    `name`        varchar(60) DEFAULT NULL COMMENT '角色名称',

    `create_time` datetime    DEFAULT NULL,
    `created_by`  varchar(30)  DEFAULT NULL,
    `update_time` datetime    DEFAULT NULL,
    `update_by`   varchar(30)  DEFAULT NULL,
    `version`     int(11)     DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY UQE_UAA_ROLE_CODE (`code`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='角色';

-- ----------------------------
-- Table structure for sys_role_privilege
-- ----------------------------
DROP TABLE IF EXISTS `uaa_role_privilege`;
CREATE TABLE `uaa_role_privilege`
(
    `role_id`      bigint(20) NOT NULL COMMENT '角色id',
    `privilege_id` bigint(20) NOT NULL COMMENT '授权id',
    PRIMARY KEY (`role_id`, `privilege_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='角色权限';

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `uaa_user`;
CREATE TABLE `uaa_user`
(
    `id`          bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `uid`         VARCHAR(30) DEFAULT NULL COMMENT '用户唯一id',
    `type`        VARCHAR(1)  DEFAULT NULL COMMENT '用户类型',
    `enabled`     bit(1)      DEFAULT NULL COMMENT '是否有效',
    `deleted`     bit(1)      DEFAULT NULL,

    `create_time` datetime    DEFAULT NULL,
    `created_by`  varchar(30)  DEFAULT NULL,
    `update_time` datetime    DEFAULT NULL,
    `update_by`   varchar(30)  DEFAULT NULL,
    `version`     int(11)     DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='用户';

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `uaa_user_role`;
CREATE TABLE `uaa_user_role`
(
    `user_id` bigint(20) NOT NULL COMMENT '用户的id',
    `role_id` bigint(20) NOT NULL COMMENT '角色的id',
    PRIMARY KEY (`user_id`, `role_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='用户角色';

-- ----------------------------
-- Table structure for uaa_user_auth
-- ----------------------------
DROP TABLE IF EXISTS `uaa_user_auth`;
CREATE TABLE `uaa_user_auth`
(
    `id`          bigint(20) NOT NULL AUTO_INCREMENT,
    `user_id`     bigint(20)  DEFAULT NULL COMMENT '用户的id',
    `type`        varchar(10) DEFAULT NULL COMMENT '账号类型',
    `username`    varchar(60) DEFAULT NULL COMMENT '用户名',
    `password`    varchar(80) DEFAULT NULL COMMENT '密码',
    `verified`    bit(1)      DEFAULT NULL COMMENT '是否已验证',
    `deleted`     bit(1)      DEFAULT NULL,

    `create_time` datetime    DEFAULT NULL,
    `created_by`  varchar(30)  DEFAULT NULL,
    `update_time` datetime    DEFAULT NULL,
    `update_by`   varchar(30)  DEFAULT NULL,
    `version`     int(11)     DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY UQE_UAA_USER_AUTH_USERNAME_TYPE (`username`, `type`),
    INDEX IDX_UAA_USER_AUTH_USER_ID (`user_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='用户登录账号';

-- ----------------------------
-- Table structure for uaa_login_record
-- ----------------------------
DROP TABLE IF EXISTS `uaa_login_record`;
CREATE TABLE `uaa_login_record`
(
    `id`          bigint(20) NOT NULL AUTO_INCREMENT,
    `user_id`     bigint(20)   DEFAULT NULL COMMENT '用户id',
    `token`       varchar(60)  DEFAULT NULL COMMENT 'token',
    `ip`          varchar(30)  DEFAULT NULL COMMENT 'ip',
    `network`     varchar(20)  DEFAULT NULL COMMENT '网络',
    `user_agent`  varchar(500) DEFAULT NULL COMMENT 'App版本，系统类型，系统版本等',
    `device_id`   varchar(60)  DEFAULT NULL COMMENT '设备id',

    `create_time` datetime     DEFAULT NULL,
    `created_by`  varchar(30)   DEFAULT NULL,
    `update_time` datetime     DEFAULT NULL,
    `update_by`   varchar(30)   DEFAULT NULL,
    `version`     int(11)      DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 21
  DEFAULT CHARSET = utf8mb4 COMMENT ='登录记录';

-- ----------------------------
-- Table structure for revinfo
-- ----------------------------
-- DROP TABLE IF EXISTS `revinfo`;
-- create table revinfo (
--   rev int(11) not null auto_increment COMMENT '全局版本号',
--   revtstmp bigint(20) DEFAULT NULL COMMENT '更新时间',
--   primary key (rev)
-- ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='全局版本控制';

-- ----------------------------
-- Table structure for sys_oA2_client
-- ----------------------------
DROP TABLE IF EXISTS `uaa_oauth2_client`;
CREATE TABLE `uaa_oauth2_client` (
     `id` bigint(20) NOT NULL AUTO_INCREMENT,
     `client_id` varchar(30) DEFAULT NULL COMMENT '客户端id',
     `client_id_issued_date` datetime DEFAULT NULL COMMENT '客户端id发出时间',
     `client_name` varchar(255) NOT NULL COMMENT '客户端名称',
     `client_secret` varchar(80) DEFAULT NULL COMMENT '客户端密码',
     `client_secret_exp_date` datetime DEFAULT NULL COMMENT '客户端密码过期时间',
     `scopes` varchar(30) DEFAULT NULL COMMENT '授权作用域',
     `auth_grant_types` varchar(70) DEFAULT NULL COMMENT '授权方式，包括authorization_code, client_credentials, refresh_token, urn:ietf:params:oauth:grant-type:jwt-bearer, urn:ietf:params:oauth:grant-type:device_code',
     `client_authn_methods` varchar(300) NOT NULL COMMENT '客户端认证的加密方式，包括client_secret_basic，client_secret_post，client_secret_jwt，private_key_jwt，none等',
     `redirect_uris` varchar(300) DEFAULT NULL COMMENT '客户端登录后重定向URI',
     `post_logout_redirect_uris` varchar(300) DEFAULT NULL COMMENT '客户端注销后重定向URI',
     `cs_require_proof_key` bit(1) DEFAULT NULL COMMENT '客户端配置：是否需要提供密钥',
     `cs_require_auth_consent` bit(1) DEFAULT NULL COMMENT '客户端配置：是否需要授权同意',
     `ts_auth_code_validity`int(11) DEFAULT NULL COMMENT 'TOKEN配置：授权CODE有效时长',
     `ts_access_token_validity`int(11) DEFAULT NULL COMMENT 'TOKEN配置：授权TOKEN有效时长',
     `ts_reuse_refresh_tokens`bit(1) DEFAULT NULL COMMENT 'TOKEN配置：刷新令牌是否重复使用',
     `ts_refresh_token_validity`int(11) DEFAULT NULL COMMENT 'TOKEN配置：刷新TOKEN有效时长',

     `create_time` datetime DEFAULT NULL,
     `created_by` varchar(30) DEFAULT NULL,
     `update_by` varchar(30) DEFAULT NULL,
     `update_time` datetime DEFAULT NULL,
     `version` int(11) DEFAULT NULL,
     PRIMARY KEY (`id`),
     UNIQUE KEY UQE_UAA_OA2_CLIENT_CLIENT_ID(`client_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

#密码3MMoCFo4nTNjRtGZ
INSERT INTO uaa_oauth2_client (id, client_id, client_id_issued_date, client_name, client_secret, client_secret_exp_date, scopes, auth_grant_types, client_authn_methods, redirect_uris, post_logout_redirect_uris, cs_require_proof_key, cs_require_auth_consent, ts_auth_code_validity, ts_access_token_validity, ts_reuse_refresh_tokens, ts_refresh_token_validity, create_time, created_by, update_by, update_time, version) VALUES (1, 'bk_gw', '2023-08-07 12:08:23.000000', '后台网关', '{bcrypt}$2a$10$VCVHpWipe9Bi3r7cPwA9suUvnX5jvpocyzHtVa4RaBU8QrEEB5LuW', '2099-08-07 12:08:23.000000', 'all', 'authorization_code,refresh_token,client_credentials', 'client_secret_post,client_secret_basic,client_secret_jwt,private_key_jwt', 'http://localhost:8081/api/sso/admin/sometest', null, false, false, 300, 43200, true, 2592000, null, null, null, null, 0);

INSERT INTO `uaa_privilege` (`id`, `code`, `name`, `create_time`, `created_by`, `update_time`, `update_by`, `version`) VALUES ('10002001', 'UAA_ROLE_QUERY', 'Query Role', NULL, NULL, NULL, NULL, '0');
INSERT INTO `uaa_privilege` (`id`, `code`, `name`, `create_time`, `created_by`, `update_time`, `update_by`, `version`) VALUES ('10002002', 'UAA_ROLE_ADD', 'Add Role', NULL, NULL, NULL, NULL, '0');
INSERT INTO `uaa_privilege` (`id`, `code`, `name`, `create_time`, `created_by`, `update_time`, `update_by`, `version`) VALUES ('10002003', 'UAA_ROLE_EDIT', 'Amend Role', NULL, NULL, NULL, NULL, '0');
INSERT INTO `uaa_privilege` (`id`, `code`, `name`, `create_time`, `created_by`, `update_time`, `update_by`, `version`) VALUES ('10002004', 'UAA_ROLE_DEL', 'Delete Role', NULL, NULL, NULL, NULL, '0');
INSERT INTO `uaa_privilege` (`id`, `code`, `name`, `create_time`, `created_by`, `update_time`, `update_by`, `version`) VALUES ('10003001', 'UAA_RESOURCE_QUERY', 'Query Resource', NULL, NULL, NULL, NULL, '0');
INSERT INTO `uaa_privilege` (`id`, `code`, `name`, `create_time`, `created_by`, `update_time`, `update_by`, `version`) VALUES ('10003002', 'UAA_RESOURCE_ADD', 'Add  Resource', NULL, NULL, NULL, NULL, '0');
INSERT INTO `uaa_privilege` (`id`, `code`, `name`, `create_time`, `created_by`, `update_time`, `update_by`, `version`) VALUES ('10003003', 'UAA_RESOURCE_EDIT', 'Amend Resource', NULL, NULL, NULL, NULL, '0');
INSERT INTO `uaa_privilege` (`id`, `code`, `name`, `create_time`, `created_by`, `update_time`, `update_by`, `version`) VALUES ('10003004', 'UAA_RESOURCE_DEL', 'Delete Resource', NULL, NULL, NULL, NULL, '0');
INSERT INTO `uaa_privilege` (`id`, `code`, `name`, `create_time`, `created_by`, `update_time`, `update_by`, `version`) VALUES (10003005, 'UAA_RESOURCE_LIST', 'List Resource', NULL, NULL, NULL, NULL, 0);

INSERT INTO `uaa_privilege` (`id`, `code`, `name`, `create_time`, `created_by`, `update_time`, `update_by`, `version`) VALUES ('10001001', 'SYS_USER_QUERY', 'Query User', NULL, NULL, NULL, NULL, '0');
INSERT INTO `uaa_privilege` (`id`, `code`, `name`, `create_time`, `created_by`, `update_time`, `update_by`, `version`) VALUES ('10001002', 'SYS_USER_ADD', 'Add User', NULL, NULL, NULL, NULL, '0');
INSERT INTO `uaa_privilege` (`id`, `code`, `name`, `create_time`, `created_by`, `update_time`, `update_by`, `version`) VALUES ('10001003', 'SYS_USER_EDIT', 'Amend User', NULL, NULL, NULL, NULL, '0');
INSERT INTO `uaa_privilege` (`id`, `code`, `name`, `create_time`, `created_by`, `update_time`, `update_by`, `version`) VALUES ('10001004', 'SYS_USER_DEL', 'Delete User', NULL, NULL, NULL, NULL, '0');
INSERT INTO `uaa_privilege` (`id`, `code`, `name`, `create_time`, `created_by`, `update_time`, `update_by`, `version`) VALUES ('10001012', 'GLOBE_COMMON', 'Global Common', NULL, NULL, NULL, NULL, '0');
INSERT INTO `uaa_privilege` (`id`, `code`, `name`, `create_time`, `created_by`, `update_time`, `update_by`, `version`) VALUES ('10001013', 'GLOBE_SELF', 'Global Self', NULL, NULL, NULL, NULL, '0');

INSERT INTO `uaa_resource` (`id`, `name`, `icon`, `url`, `menu`, `root`, `leaf`, `parent_id`, `path`, `depth`, `permit_type`, `sequence`, `ms_client_id`, `create_time`, `created_by`, `update_by`, `update_time`, `version`) VALUES ('1', 'System', '🚙 ', '#99', true, true, false, '0', '/1/', '0', 'S', '9900', 'sys', NULL, NULL, NULL, NULL, '0');
INSERT INTO `uaa_resource` (`id`, `name`, `icon`, `url`, `menu`, `root`, `leaf`, `parent_id`, `path`, `depth`, `permit_type`, `sequence`, `ms_client_id`, `create_time`, `created_by`, `update_by`, `update_time`, `version`) VALUES ('10001000', 'Users', null, '/admin/users', true, false, true, '1', '/1/10001000/', '1', 'A', '100', 'sys', NULL, NULL, NULL, NULL, '0');
INSERT INTO `uaa_resource` (`id`, `name`, `icon`, `url`, `menu`, `root`, `leaf`, `parent_id`, `path`, `depth`, `permit_type`, `sequence`, `ms_client_id`, `create_time`, `created_by`, `update_by`, `update_time`, `version`) VALUES ('10002000', 'Roles', null, '/admin/roles', true, false, true, '1', '/1/10002000/', '1', 'A', '200', 'uaa', NULL, NULL, NULL, NULL, '0');
INSERT INTO `uaa_resource` (`id`, `name`, `icon`, `url`, `menu`, `root`, `leaf`, `parent_id`, `path`, `depth`, `permit_type`, `sequence`, `ms_client_id`, `create_time`, `created_by`, `update_by`, `update_time`, `version`) VALUES ('10003000', 'Resources', null, '/admin/resources', true, false, true, '1', '/1/10003000/', '1', 'A', '300', 'uaa', NULL, NULL, NULL, NULL, '0');

INSERT INTO `uaa_resource` (`id`, `name`, `icon`, `url`, `menu`, `root`, `leaf`, `parent_id`, `path`, `depth`, `permit_type`, `sequence`, `ms_client_id`, `create_time`, `created_by`, `update_by`, `update_time`, `version`) VALUES ('10001003', '用户-列表', NULL, '/ads/adminUser/page', false, NULL, NULL, NULL, NULL, NULL, 'A', NULL, 'sys', NULL, NULL, NULL, NULL, '0');
INSERT INTO `uaa_resource` (`id`, `name`, `icon`, `url`, `menu`, `root`, `leaf`, `parent_id`, `path`, `depth`, `permit_type`, `sequence`, `ms_client_id`, `create_time`, `created_by`, `update_by`, `update_time`, `version`) VALUES ('10001004', '用户-新增', NULL, '/ads/adminUser/add', false, NULL, NULL, NULL, NULL, NULL, 'A', NULL, 'sys', NULL, NULL, NULL, NULL, '0');
INSERT INTO `uaa_resource` (`id`, `name`, `icon`, `url`, `menu`, `root`, `leaf`, `parent_id`, `path`, `depth`, `permit_type`, `sequence`, `ms_client_id`, `create_time`, `created_by`, `update_by`, `update_time`, `version`) VALUES ('10001005', '用户-编辑', NULL, '/ads/adminUser/edit*', false, NULL, NULL, NULL, NULL, NULL, 'A', NULL, 'sys', NULL, NULL, NULL, NULL, '0');
INSERT INTO `uaa_resource` (`id`, `name`, `icon`, `url`, `menu`, `root`, `leaf`, `parent_id`, `path`, `depth`, `permit_type`, `sequence`, `ms_client_id`, `create_time`, `created_by`, `update_by`, `update_time`, `version`) VALUES ('10001006', '用户-启用', NULL, '/ads/user/enable', false, NULL, NULL, NULL, NULL, NULL, 'A', NULL, 'uaa', NULL, NULL, NULL, NULL, '0');
INSERT INTO `uaa_resource` (`id`, `name`, `icon`, `url`, `menu`, `root`, `leaf`, `parent_id`, `path`, `depth`, `permit_type`, `sequence`, `ms_client_id`, `create_time`, `created_by`, `update_by`, `update_time`, `version`) VALUES ('10001007', '用户-禁用', NULL, '/ads/user/disable', false, NULL, NULL, NULL, NULL, NULL, 'A', NULL, 'uaa', NULL, NULL, NULL, NULL, '0');
INSERT INTO `uaa_resource` (`id`, `name`, `icon`, `url`, `menu`, `root`, `leaf`, `parent_id`, `path`, `depth`, `permit_type`, `sequence`, `ms_client_id`, `create_time`, `created_by`, `update_by`, `update_time`, `version`) VALUES ('10001008', '用户-重置密码', NULL, '/ads/user/password/reset', false, NULL, NULL, NULL, NULL, NULL, 'A', NULL, 'uaa', NULL, NULL, NULL, NULL, '0');
INSERT INTO `uaa_resource` (`id`, `name`, `icon`, `url`, `menu`, `root`, `leaf`, `parent_id`, `path`, `depth`, `permit_type`, `sequence`, `ms_client_id`, `create_time`, `created_by`, `update_by`, `update_time`, `version`) VALUES ('10001009', '用户-角色列表', NULL, '/ads/user/role/list', false, NULL, NULL, NULL, NULL, NULL, 'A', NULL, 'uaa', NULL, NULL, NULL, NULL, '0');
INSERT INTO `uaa_resource` (`id`, `name`, `icon`, `url`, `menu`, `root`, `leaf`, `parent_id`, `path`, `depth`, `permit_type`, `sequence`, `ms_client_id`, `create_time`, `created_by`, `update_by`, `update_time`, `version`) VALUES ('10001010', '全局-在线文档', NULL, '/swagger*/**', false, NULL, NULL, NULL, NULL, NULL, 'N', NULL, NULL, NULL, NULL, NULL, NULL, 0);
INSERT INTO `uaa_resource` (`id`, `name`, `icon`, `url`, `menu`, `root`, `leaf`, `parent_id`, `path`, `depth`, `permit_type`, `sequence`, `ms_client_id`, `create_time`, `created_by`, `update_by`, `update_time`, `version`) VALUES ('10001011', '全局-错误页面', NULL, '/error', false, NULL, NULL, NULL, NULL, NULL, 'N', NULL, NULL, NULL, NULL, NULL, NULL, '0');
INSERT INTO `uaa_resource` (`id`, `name`, `icon`, `url`, `menu`, `root`, `leaf`, `parent_id`, `path`, `depth`, `permit_type`, `sequence`, `ms_client_id`, `create_time`, `created_by`, `update_by`, `update_time`, `version`) VALUES ('10001012', '全局-通用页面', NULL, '/common/**', false, NULL, NULL, NULL, NULL, NULL, 'A', NULL, NULL, NULL, NULL, NULL, NULL, '0');
INSERT INTO `uaa_resource` (`id`, `name`, `icon`, `url`, `menu`, `root`, `leaf`, `parent_id`, `path`, `depth`, `permit_type`, `sequence`, `ms_client_id`, `create_time`, `created_by`, `update_by`, `update_time`, `version`) VALUES ('10001013', '全局-个人页面', NULL, '/self/**', false, NULL, NULL, NULL, NULL, NULL, 'A', NULL, NULL, NULL, NULL, NULL, NULL, '0');
INSERT INTO `uaa_resource` (`id`, `name`, `icon`, `url`, `menu`, `root`, `leaf`, `parent_id`, `path`, `depth`, `permit_type`, `sequence`, `ms_client_id`, `create_time`, `created_by`, `update_by`, `update_time`, `version`) VALUES ('10001014', '全局-无限制页面', NULL, '/pmall/**', false, NULL, NULL, NULL, NULL, NULL, 'N', NULL, NULL, NULL, NULL, NULL, NULL, '0');

INSERT INTO `uaa_resource` (`id`, `name`, `icon`, `url`, `menu`, `root`, `leaf`, `parent_id`, `path`, `depth`, `permit_type`, `sequence`, `ms_client_id`, `create_time`, `created_by`, `update_by`, `update_time`, `version`) VALUES ('10002001', '角色-列表', NULL, '/ads/role/page', false, NULL, NULL, NULL, NULL, NULL, 'A', NULL, 'uaa', NULL, NULL, NULL, NULL, '0');
INSERT INTO `uaa_resource` (`id`, `name`, `icon`, `url`, `menu`, `root`, `leaf`, `parent_id`, `path`, `depth`, `permit_type`, `sequence`, `ms_client_id`, `create_time`, `created_by`, `update_by`, `update_time`, `version`) VALUES ('10002002', '角色-新增', NULL, '/ads/role/add', false, NULL, NULL, NULL, NULL, NULL, 'A', NULL, 'uaa', NULL, NULL, NULL, NULL, '0');
INSERT INTO `uaa_resource` (`id`, `name`, `icon`, `url`, `menu`, `root`, `leaf`, `parent_id`, `path`, `depth`, `permit_type`, `sequence`, `ms_client_id`, `create_time`, `created_by`, `update_by`, `update_time`, `version`) VALUES ('10002003', '角色-编辑', NULL, '/ads/role/edit*', false, NULL, NULL, NULL, NULL, NULL, 'A', NULL, 'uaa', NULL, NULL, NULL, NULL, '0');
INSERT INTO `uaa_resource` (`id`, `name`, `icon`, `url`, `menu`, `root`, `leaf`, `parent_id`, `path`, `depth`, `permit_type`, `sequence`, `ms_client_id`, `create_time`, `created_by`, `update_by`, `update_time`, `version`) VALUES ('10002004', '角色-删除', NULL, '/ads/role/delete', false, NULL, NULL, NULL, NULL, NULL, 'A', NULL, 'uaa', NULL, NULL, NULL, NULL, '0');
INSERT INTO `uaa_resource` (`id`, `name`, `icon`, `url`, `menu`, `root`, `leaf`, `parent_id`, `path`, `depth`, `permit_type`, `sequence`, `ms_client_id`, `create_time`, `created_by`, `update_by`, `update_time`, `version`) VALUES ('10003001', '资源-树', NULL, '/ads/resource/tree', false, NULL, NULL, NULL, NULL, NULL, 'A', NULL, 'uaa', NULL, NULL, NULL, NULL, '0');
INSERT INTO `uaa_resource` (`id`, `name`, `icon`, `url`, `menu`, `root`, `leaf`, `parent_id`, `path`, `depth`, `permit_type`, `sequence`, `ms_client_id`, `create_time`, `created_by`, `update_by`, `update_time`, `version`) VALUES ('10003002', '资源-树-移动', NULL, '/ads/resource/tree/move', false, NULL, NULL, NULL, NULL, NULL, 'A', NULL, 'uaa', NULL, NULL, NULL, NULL, '0');
INSERT INTO `uaa_resource` (`id`, `name`, `icon`, `url`, `menu`, `root`, `leaf`, `parent_id`, `path`, `depth`, `permit_type`, `sequence`, `ms_client_id`, `create_time`, `created_by`, `update_by`, `update_time`, `version`) VALUES ('10003003', '资源-列表', NULL, '/ads/resource/page', false, NULL, NULL, NULL, NULL, NULL, 'A', NULL, 'uaa', NULL, NULL, NULL, NULL, '0');
INSERT INTO `uaa_resource` (`id`, `name`, `icon`, `url`, `menu`, `root`, `leaf`, `parent_id`, `path`, `depth`, `permit_type`, `sequence`, `ms_client_id`, `create_time`, `created_by`, `update_by`, `update_time`, `version`) VALUES ('10003004', '资源-新增', NULL, '/ads/resource/add', false, NULL, NULL, NULL, NULL, NULL, 'A', NULL, 'uaa', NULL, NULL, NULL, NULL, '0');
INSERT INTO `uaa_resource` (`id`, `name`, `icon`, `url`, `menu`, `root`, `leaf`, `parent_id`, `path`, `depth`, `permit_type`, `sequence`, `ms_client_id`, `create_time`, `created_by`, `update_by`, `update_time`, `version`) VALUES ('10003005', '资源-编辑', NULL, '/ads/resource/edit*', false, NULL, NULL, NULL, NULL, NULL, 'A', NULL, 'uaa', NULL, NULL, NULL, NULL, '0');
INSERT INTO `uaa_resource` (`id`, `name`, `icon`, `url`, `menu`, `root`, `leaf`, `parent_id`, `path`, `depth`, `permit_type`, `sequence`, `ms_client_id`, `create_time`, `created_by`, `update_by`, `update_time`, `version`) VALUES ('10003006', '资源-权限列表', NULL, '/ads/resource/privilege/list', false, NULL, NULL, NULL, NULL, NULL, 'A', NULL, 'uaa', NULL, NULL, NULL, NULL, '0');
INSERT INTO `uaa_resource` (`id`, `name`, `icon`, `url`, `menu`, `root`, `leaf`, `parent_id`, `path`, `depth`, `permit_type`, `sequence`, `ms_client_id`, `create_time`, `created_by`, `update_by`, `update_time`, `version`) VALUES ('10003007', '资源-权限新增', NULL, '/ads/resource/privilege/add', false, NULL, NULL, NULL, NULL, NULL, 'A', NULL, 'uaa', NULL, NULL, NULL, NULL, '0');

INSERT INTO `uaa_resource_privilege` (`resource_id`, `privilege_id`) VALUES ('10001000', '10001001');
INSERT INTO `uaa_resource_privilege` (`resource_id`, `privilege_id`) VALUES ('10002000', '10002001');
INSERT INTO `uaa_resource_privilege` (`resource_id`, `privilege_id`) VALUES ('10003000', '10003001');
INSERT INTO `uaa_resource_privilege` (`resource_id`, `privilege_id`) VALUES ('10001003', '10001001');
INSERT INTO `uaa_resource_privilege` (`resource_id`, `privilege_id`) VALUES ('10001004', '10001002');
INSERT INTO `uaa_resource_privilege` (`resource_id`, `privilege_id`) VALUES ('10001005', '10001003');
INSERT INTO `uaa_resource_privilege` (`resource_id`, `privilege_id`) VALUES ('10001006', '10001003');
INSERT INTO `uaa_resource_privilege` (`resource_id`, `privilege_id`) VALUES ('10001007', '10001003');
INSERT INTO `uaa_resource_privilege` (`resource_id`, `privilege_id`) VALUES ('10001008', '10001003');
INSERT INTO `uaa_resource_privilege` (`resource_id`, `privilege_id`) VALUES ('10001009', '10001001');
INSERT INTO `uaa_resource_privilege` (`resource_id`, `privilege_id`) VALUES ('10001012', '10001012');
INSERT INTO `uaa_resource_privilege` (`resource_id`, `privilege_id`) VALUES ('10001013', '10001013');

INSERT INTO `uaa_resource_privilege` (`resource_id`, `privilege_id`) VALUES ('10002001', '10002001');
INSERT INTO `uaa_resource_privilege` (`resource_id`, `privilege_id`) VALUES ('10002002', '10002002');
INSERT INTO `uaa_resource_privilege` (`resource_id`, `privilege_id`) VALUES ('10002003', '10002003');
INSERT INTO `uaa_resource_privilege` (`resource_id`, `privilege_id`) VALUES ('10002004', '10002004');
INSERT INTO `uaa_resource_privilege` (`resource_id`, `privilege_id`) VALUES ('10003001', '10003001');
INSERT INTO `uaa_resource_privilege` (`resource_id`, `privilege_id`) VALUES ('10003002', '10003003');
INSERT INTO `uaa_resource_privilege` (`resource_id`, `privilege_id`) VALUES ('10003003', '10003001');
INSERT INTO `uaa_resource_privilege` (`resource_id`, `privilege_id`) VALUES ('10003004', '10003002');
INSERT INTO `uaa_resource_privilege` (`resource_id`, `privilege_id`) VALUES ('10003005', '10003003');
INSERT INTO `uaa_resource_privilege` (`resource_id`, `privilege_id`) VALUES ('10003006', '10003001');
INSERT INTO `uaa_resource_privilege` (`resource_id`, `privilege_id`) VALUES ('10003006', '10003005');
INSERT INTO `uaa_resource_privilege` (`resource_id`, `privilege_id`) VALUES ('10003007', '10003002');
INSERT INTO `uaa_resource_privilege` (`resource_id`, `privilege_id`) VALUES ('10003007', '10003003');

INSERT INTO `uaa_role_privilege` (`role_id`, `privilege_id`) VALUES ('1', '10002001');
INSERT INTO `uaa_role_privilege` (`role_id`, `privilege_id`) VALUES ('1', '10002002');
INSERT INTO `uaa_role_privilege` (`role_id`, `privilege_id`) VALUES ('1', '10002003');
INSERT INTO `uaa_role_privilege` (`role_id`, `privilege_id`) VALUES ('1', '10002004');
INSERT INTO `uaa_role_privilege` (`role_id`, `privilege_id`) VALUES ('1', '10003005');
INSERT INTO `uaa_role_privilege` (`role_id`, `privilege_id`) VALUES ('1', '10001001');
INSERT INTO `uaa_role_privilege` (`role_id`, `privilege_id`) VALUES ('1', '10001002');
INSERT INTO `uaa_role_privilege` (`role_id`, `privilege_id`) VALUES ('1', '10001003');
INSERT INTO `uaa_role_privilege` (`role_id`, `privilege_id`) VALUES ('1', '10001004');
INSERT INTO `uaa_role_privilege` (`role_id`, `privilege_id`) VALUES ('1', '10001012');
INSERT INTO `uaa_role_privilege` (`role_id`, `privilege_id`) VALUES ('1', '10001013');
INSERT INTO `uaa_role_privilege` (`role_id`, `privilege_id`) VALUES ('4', '10001012');
INSERT INTO `uaa_role_privilege` (`role_id`, `privilege_id`) VALUES ('4', '10001013');

INSERT INTO `uaa_role` (`id`, `code`, `name`, `create_time`, `created_by`, `update_by`, `update_time`, `version`) VALUES (1, 'ADMIN_SUPER', 'Super Admin', NULL, NULL, NULL, NULL, 0);
INSERT INTO `uaa_role` (`id`, `code`, `name`, `create_time`, `created_by`, `update_by`, `update_time`, `version`) VALUES (4, 'NORMAL_USER', 'Normal User', NULL, NULL, NULL, NULL, 0);

#密码123123
INSERT INTO `uaa_user` (`id`, `uid`, `type`, `enabled`,`deleted`, `create_time`, `created_by`, `update_by`, `update_time`, `version`) VALUES (1, 'U000000000001', 'B', true, false, NULL, NULL, NULL, NULL, 0);
INSERT INTO `uaa_user_auth` (`id`, `user_id`, `type`, `username`, `password`, `verified`, `deleted`, `create_time`, `created_by`, `update_by`, `update_time`, `version`) VALUES (1, 1, 'ACCOUNT', 'admin', '{bcrypt}$2a$10$nFygHhisD39TltP3W8Cag.I/2IlqdmKwBRn8oJ11HBZfOHVT4fnn.', true, false, NULL, NULL, NULL, NULL, 0);
INSERT INTO `uaa_user_role` (`user_id`, `role_id`) VALUES (1, 1);