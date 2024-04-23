-- ----------------------------
-- Table structure for sys_admin_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_admin_user`;
CREATE TABLE `sys_admin_user`
(
    `id`          bigint(20) NOT NULL,
    `nickname`    varchar(30) DEFAULT NULL COMMENT '昵称',
    `avatar`      varchar(40) DEFAULT NULL COMMENT '头像',
    `phone`       varchar(30) DEFAULT NULL COMMENT '电话',
    `email`       varchar(30) DEFAULT NULL COMMENT '邮箱',
    `deleted`     bit(1)      DEFAULT NULL,

    `create_time` datetime    DEFAULT NULL,
    `created_by`  varchar(30)  DEFAULT NULL,
    `update_time` datetime    DEFAULT NULL,
    `update_by`   varchar(30)  DEFAULT NULL,
    `version`     int(11)     DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='后台用户';

-- ----------------------------
-- Table structure for sys_param
-- ----------------------------
DROP TABLE IF EXISTS `sys_param`;
CREATE TABLE `sys_param`
(
    `id`           bigint(20) NOT NULL AUTO_INCREMENT,
    `type`         varchar(20)  DEFAULT NULL COMMENT '参数类型',
    `code`         varchar(50)  DEFAULT NULL COMMENT '代码',
    `name`         varchar(20)  DEFAULT NULL COMMENT '展示名称',
    `parent_code`  varchar(20)  DEFAULT NULL COMMENT '上级代码',
    `value`        varchar(200) DEFAULT NULL COMMENT '值',
    `default_item` bit(1)       DEFAULT NULL COMMENT '是否默认选中',
    `sequence`     int(2)       DEFAULT NULL COMMENT '默认排序',
    `remark`       varchar(100) DEFAULT NULL COMMENT '备注',

    `create_time`  datetime     DEFAULT NULL,
    `created_by`   varchar(30)  DEFAULT NULL,
    `update_time`  datetime     DEFAULT NULL,
    `update_by`   varchar(30)  DEFAULT NULL,
    `version`      int(11)      DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY UQE_SYS_PARAM_CODE (`code`),
    INDEX IDX_SYS_PARAM_PARENT_CODE (`parent_code`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='系统参数';

INSERT INTO `sys_admin_user` (`id`, `avatar`, `email`, `nickname`, `phone`, `deleted`,`version`) VALUES (1, 'images/default-avatar.png', NULL , '系统管理员', NULL, false, 0);

INSERT INTO `sys_param`(`id`, `type`, `code`, `name`, `parent_code`, `value`, `default_item`, `sequence`, `remark`, `create_time`, `created_by`, `update_by`, `update_time`, `version`) VALUES (1, 'STRING', 'SYS_DEFAULT_AVATAR', '前后端默认头像', NULL, 'images/default-avatar.png', false, 0, '必须在images里面能访问到', NULL, NULL, NULL, NULL, 0);
INSERT INTO `sys_param`(`id`, `type`, `code`, `name`, `parent_code`, `value`, `default_item`, `sequence`, `remark`, `create_time`, `created_by`, `update_by`, `update_time`, `version`) VALUES (5, 'STRING', 'sys-admin-users-add', '用户管理用户头像上传路径', NULL, 'images', false, 0, '开头不要带\"/\"，文件会上传至该路径下，且该路径会拼接在配置文件的access-domain参数后', NULL, NULL, NULL, NULL, 0);
INSERT INTO `sys_param`(`id`, `type`, `code`, `name`, `parent_code`, `value`, `default_item`, `sequence`, `remark`, `create_time`, `created_by`, `update_by`, `update_time`, `version`) VALUES (6, 'STRING', 'sys-admin-users-edit', '用户管理用户头像上传路径', NULL, 'images', false, 0, '开头不要带\"/\"，文件会上传至该路径下，且该路径会拼接在配置文件的access-domain参数后', NULL, NULL, NULL, NULL, 0);
INSERT INTO sys_param (type, code, name, parent_code, value, default_item, sequence, remark, create_time, created_by, update_by, update_time, version) VALUES ('STRING', 'dfwms-sku-add', 'excel文件批量上传新增路径', null, 'object', false, 0, '开头不要带"/"，文件会上传至该路径下，且该路径会拼接在配置文件的access-domain参数后', null, null, null, null, 0);
INSERT INTO sys_param (type, code, name, parent_code, value, default_item, sequence, remark, create_time, created_by, update_by, update_time, version) VALUES ('STRING', 'dfwms-sku-edit', 'excel文件批量上传修改路径', null, 'object', false, 0, '开头不要带"/"，文件会上传至该路径下，且该路径会拼接在配置文件的access-domain参数后', null, null, null, null, 0);



INSERT INTO `uaa_resource` (`id`, `name`, `icon`, `url`, `menu`, `root`, `leaf`, `parent_id`, `path`, `depth`, `permit_type`, `sequence`, `ms_client_id`, `create_time`, `created_by`, `update_by`, `update_time`, `version`) VALUES ('10004000', 'Params', NULL, '/admin/params', true, false, true, '1', '/1/10004000/', '1', 'A', '400', 'sys', NULL, NULL, NULL, NULL, '0');
INSERT INTO `uaa_resource` (`id`, `name`, `icon`, `url`, `menu`, `root`, `leaf`, `parent_id`, `path`, `depth`, `permit_type`, `sequence`, `ms_client_id`, `create_time`, `created_by`, `update_by`, `update_time`, `version`) VALUES ('10004001', '参数-列表', NULL, '/ads/param/page', false, NULL, NULL, NULL, NULL, NULL, 'A', NULL, 'sys', NULL, NULL, NULL, NULL, '0');
INSERT INTO `uaa_resource` (`id`, `name`, `icon`, `url`, `menu`, `root`, `leaf`, `parent_id`, `path`, `depth`, `permit_type`, `sequence`, `ms_client_id`, `create_time`, `created_by`, `update_by`, `update_time`, `version`) VALUES ('10004002', '参数-新增', NULL, '/ads/param/add', false, NULL, NULL, NULL, NULL, NULL, 'A', NULL, 'sys', NULL, NULL, NULL, NULL, '0');
INSERT INTO `uaa_resource` (`id`, `name`, `icon`, `url`, `menu`, `root`, `leaf`, `parent_id`, `path`, `depth`, `permit_type`, `sequence`, `ms_client_id`, `create_time`, `created_by`, `update_by`, `update_time`, `version`) VALUES ('10004003', '参数-编辑', NULL, '/ads/param/edit*', false, NULL, NULL, NULL, NULL, NULL, 'A', NULL, 'sys', NULL, NULL, NULL, NULL, '0');


INSERT INTO `uaa_privilege` (`id`, `code`, `name`, `create_time`, `created_by`, `update_time`, `update_by`, `version`) VALUES ('10004001', 'SYS_PARAM_QUERY', '参数-查', NULL, NULL, NULL, NULL, '0');
INSERT INTO `uaa_privilege` (`id`, `code`, `name`, `create_time`, `created_by`, `update_time`, `update_by`, `version`) VALUES ('10004002', 'SYS_PARAM_ADD', '参数-增', NULL, NULL, NULL, NULL, '0');
INSERT INTO `uaa_privilege` (`id`, `code`, `name`, `create_time`, `created_by`, `update_time`, `update_by`, `version`) VALUES ('10004003', 'SYS_PARAM_EDIT', '参数-改', NULL, NULL, NULL, NULL, '0');
INSERT INTO `uaa_privilege` (`id`, `code`, `name`, `create_time`, `created_by`, `update_time`, `update_by`, `version`) VALUES ('10004004', 'SYS_PARAM_DEL', '参数-删', NULL, NULL, NULL, NULL, '0');
INSERT INTO `uaa_resource_privilege` (`resource_id`, `privilege_id`) VALUES ('10004000', '10004001');
INSERT INTO `uaa_resource_privilege` (`resource_id`, `privilege_id`) VALUES ('10004001', '10004001');
INSERT INTO `uaa_resource_privilege` (`resource_id`, `privilege_id`) VALUES ('10004002', '10004002');
INSERT INTO `uaa_resource_privilege` (`resource_id`, `privilege_id`) VALUES ('10004003', '10004003');

INSERT INTO `uaa_role_privilege` (`role_id`, `privilege_id`) VALUES ('1', '10004001');
INSERT INTO `uaa_role_privilege` (`role_id`, `privilege_id`) VALUES ('1', '10004002');
INSERT INTO `uaa_role_privilege` (`role_id`, `privilege_id`) VALUES ('1', '10004003');
INSERT INTO `uaa_role_privilege` (`role_id`, `privilege_id`) VALUES ('1', '10004004');
