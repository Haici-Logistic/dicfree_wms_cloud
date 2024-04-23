-- ----------------------------
-- Table structure for oss_uploaded_file
-- ----------------------------
DROP TABLE IF EXISTS `oss_uploaded_file`;
CREATE TABLE `oss_uploaded_file`
(
    `id`            bigint(20)               NOT NULL AUTO_INCREMENT,
    `key`           varchar(40) DEFAULT NULL NULL COMMENT '文件访问key',
    `filename`      varchar(60) DEFAULT NULL COMMENT '原文件名',
    `extension`     varchar(10) DEFAULT NULL NULL COMMENT '扩展名',
    `size`          bigint(10)  DEFAULT NULL NULL COMMENT '大小',
    `sign`          varchar(80) DEFAULT NULL NULL COMMENT '签名',
    `action_name`   varchar(80) DEFAULT NULL NULL COMMENT '上传文件方法名',
    `path`          varchar(30) DEFAULT NULL NULL COMMENT '上传文件相对位置',
    `upload_status` varchar(10) DEFAULT NULL NULL COMMENT '上传状态',

    `create_time`   datetime    DEFAULT NULL,
    `created_by`    varchar(30)  DEFAULT NULL,
    `update_time`   datetime    DEFAULT NULL,
    `update_by`   varchar(30)  DEFAULT NULL,
    `version`       int(11)     DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 4
  DEFAULT CHARSET = utf8mb4 COMMENT ='文件上传';
