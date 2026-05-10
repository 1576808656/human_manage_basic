package com.human.business.controller;

import com.human.business.domain.EmployDTO;
import com.human.business.domain.entity.EmployeeDO;
import com.human.common.http.ResponseDTO;
import com.human.business.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "员工管理模块", description = "员工增删改查接口")
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Resource
    private EmployeeService employeeService;

    @Operation(summary = "添加员工")
    @PostMapping("/add")
    public ResponseDTO addEmployee(@RequestBody EmployeeDO employeeDO){

        return employeeService.addEmployee(employeeDO);
    }

    @Operation(summary = "查询员工")
    @PostMapping("/page")
    public ResponseDTO page(@RequestBody EmployDTO employeeDTO){
        return employeeService.page(employeeDTO);
    }
}
