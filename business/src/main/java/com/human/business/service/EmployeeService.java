package com.human.business.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.human.business.domain.EmployDTO;
import com.human.business.domain.entity.EmployeeDO;
import com.human.business.domain.vo.EmployeeVO;
import com.human.common.http.ResponseDTO;
import com.human.business.mapper.EmployeeMapper;
import jakarta.annotation.Resource;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class EmployeeService {

    @Resource
    private EmployeeMapper employeeMapper;

    public ResponseDTO addEmployee(EmployeeDO employeeDO){

        try{
            int res = employeeMapper.insert(employeeDO);
            if(res>0)
                return ResponseDTO.ok("员工添加成功");
        } catch(Exception e){
            log.error("员工添加异常",e);
            return ResponseDTO.error("员工添加失败");
        }

        return ResponseDTO.error("员工添加失败");
    }

    public ResponseDTO page(EmployDTO employeeDTO){
        Page<EmployeeVO> page = new Page<>(1,10);
        page.setRecords(employeeMapper.page());
        return ResponseDTO.ok(page);
    }
}
