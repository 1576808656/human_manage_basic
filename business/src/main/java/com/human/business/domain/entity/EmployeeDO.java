package com.human.business.domain.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 员工主表 实体类
 * 对应数据库表：employee
 *
 * @author zyh
 * {@code @date} 2026/04/12
 */
@Data
@TableName("employee")
public class EmployeeDO {

    /**
     * 自增主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 员工工号，唯一
     */
    @TableField("employee_no")
    private String employeeNo;

    /**
     * 员工姓名
     */
    @TableField("name")
    private String name;

    /**
     * 身份证号，唯一
     */
    @TableField("id_card")
    private String idCard;

    /**
     * 性别：1-男，2-女
     */
    @TableField("gender")
    private Integer gender;

    /**
     * 出生日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @TableField("birthday")
    private LocalDate birthday;

    /**
     * 手机号
     */
    @TableField("phone")
    private String phone;

    /**
     * 邮箱
     */
    @TableField("email")
    private String email;

    /**
     * 联系地址
     */
    @TableField("address")
    private String address;

    /**
     * 紧急联系人
     */
    @TableField("emergency_contact")
    private String emergencyContact;

    /**
     * 紧急联系人电话
     */
    @TableField("emergency_phone")
    private String emergencyPhone;

    /**
     * 所属部门ID
     */
    @TableField("dept_id")
    private Long deptId;

    /**
     * 职位ID
     */
    @TableField("position_id")
    private Long positionId;

    /**
     * 汇报上级ID，关联employee表
     */
    @TableField("reports_to")
    private Long reportsTo;

    /**
     * 入职日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @TableField("entry_date")
    private LocalDate entryDate;

    /**
     * 员工状态：1-在职，2-离职，3-试用期
     */
    @TableField("status")
    private Integer status;

    /**
     * 离职日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @TableField("resign_date")
    private LocalDate resignDate;

    /**
     * 身份证头像面
     */
    @TableField("id_card_front_pic")
    private String idCardFrontPic;

    /**
     * 身份证国徽面
     */
    @TableField("id_card_back_pic")
    private String idCardBackPic;

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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
