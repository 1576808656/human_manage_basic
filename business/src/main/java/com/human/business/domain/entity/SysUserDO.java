package com.human.business.domain.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 系统用户表 实体类
 * 对应数据库表：sys_user
 *
 * @author zyh
 * {@code @date} 2026/04/12
 */
@Data
@TableName("sys_user")
public class SysUserDO {

    /**
     * 自增主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 登录用户名
     */
    @TableField("username")
    private String username;

    /**
     * BCrypt加密后的登录密码
     */
    @TableField("password")
    private String password;

    /**
     * 关联的员工ID，关联employee表
     */
    @TableField("employee_id")
    private Long employeeId;

    /**
     * 绑定手机号
     */
    @TableField("phone")
    private String phone;

    /**
     * 绑定邮箱
     */
    @TableField("email")
    private String email;

    /**
     * 账号状态：1-正常，0-禁用
     */
    @TableField("status")
    private Integer status;

    /**
     * 头像
     */
    @TableField("head_photo")
    private String headPhoto;

    /**
     * 最后登录时间
     */
    @TableField("last_login_time")
    private LocalDateTime lastLoginTime;

    /**
     * 创建人
     */
    @TableField("create_by")
    private Long createBy;

    /**
     * 更新人
     */
    @TableField("update_by")
    private Long updateBy;

    /**
     * 创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @TableField("uuid")
    private String uuid;
}
