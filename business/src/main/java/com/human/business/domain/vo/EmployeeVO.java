package com.human.business.domain.vo;

import lombok.Data;
import java.time.LocalDate;

@Data
public class EmployeeVO {

    private Long id;

    private String name;

    private String department;

    private String position;

    private LocalDate entryDate;

    private Integer status;
}
