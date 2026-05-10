package com.human.business.domain;

import com.human.common.http.PageParam;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Data
public class EmployDTO extends PageParam {

    /**
     * 姓名
     */
    private String name;

    /**
     * 部门id
     */
    private Long deptId;

    /**
     * 职位id
     */
    private Long positionId;

    /**
     * 入职开始时间
     */
    private LocalDate entryStartDate;

    /**
     * 入职结束时间
     */
    private LocalDate entryEndDate;

    /**
     * 状态
     */
    private Integer status;
}
