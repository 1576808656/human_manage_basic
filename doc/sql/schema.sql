-- 基础登录服务 数据库初始化脚本
-- 适用于 MySQL 8.0+

CREATE DATABASE IF NOT EXISTS basic DEFAULT CHARACTER SET utf8mb4;

USE basic;

-- 系统用户表
CREATE TABLE IF NOT EXISTS sys_user (
    id          BIGINT        NOT NULL AUTO_INCREMENT COMMENT '用户ID',
    uuid        VARCHAR(64)   NOT NULL COMMENT 'UUID',
    username    VARCHAR(64)   NOT NULL COMMENT '用户名',
    password    VARCHAR(255)  NOT NULL COMMENT '密码（Argon2加密）',
    phone       VARCHAR(32)   DEFAULT NULL COMMENT '手机号',
    email       VARCHAR(128)  DEFAULT NULL COMMENT '邮箱',
    head_photo  VARCHAR(512)  DEFAULT NULL COMMENT '头像地址',
    status      TINYINT       NOT NULL DEFAULT 1 COMMENT '状态：1-启用 0-禁用',
    last_login_time DATETIME  DEFAULT NULL COMMENT '最后登录时间',
    create_time DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id),
    UNIQUE KEY uk_uuid (uuid),
    UNIQUE KEY uk_username (username)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='系统用户表';

-- 登录安全配置表
CREATE TABLE IF NOT EXISTS sys_network_secret (
    id                  BIGINT    NOT NULL AUTO_INCREMENT COMMENT '配置ID',
    max_login_attempts  INT       NOT NULL DEFAULT 5 COMMENT '最大登录错误次数',
    lock_duration       INT       NOT NULL DEFAULT 30 COMMENT '锁定时长（分钟）',
    create_time         DATETIME  NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time         DATETIME  NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='登录安全配置表';

INSERT INTO sys_network_secret (id, max_login_attempts, lock_duration) VALUES (1, 5, 30)

-- 部门表
CREATE TABLE IF NOT EXISTS department (
    id          BIGINT        NOT NULL AUTO_INCREMENT COMMENT '部门ID',
    dept_name   VARCHAR(128)  NOT NULL COMMENT '部门名称',
    create_time DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='部门表';

-- 职位表
CREATE TABLE IF NOT EXISTS position (
    id          BIGINT        NOT NULL AUTO_INCREMENT COMMENT '职位ID',
    dept_id     BIGINT        NOT NULL COMMENT '所属部门ID',
    pos_name    VARCHAR(128)  NOT NULL COMMENT '职位名称',
    create_time DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id),
    KEY idx_dept_id (dept_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='职位表';

-- 员工表
CREATE TABLE IF NOT EXISTS employee (
    id          BIGINT        NOT NULL AUTO_INCREMENT COMMENT '员工ID',
    name        VARCHAR(64)   NOT NULL COMMENT '员工姓名',
    dept_id     BIGINT        NOT NULL COMMENT '所属部门ID',
    position_id BIGINT        NOT NULL COMMENT '职位ID',
    entry_date  DATE          DEFAULT NULL COMMENT '入职日期',
    status      TINYINT       NOT NULL DEFAULT 1 COMMENT '状态：1-在职 0-离职',
    create_time DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id),
    KEY idx_dept_id (dept_id),
    KEY idx_position_id (position_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='员工表';