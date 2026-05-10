package com.human.business.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.human.business.domain.entity.EmployeeDO;
import com.human.business.domain.vo.EmployeeVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EmployeeMapper extends BaseMapper<EmployeeDO> {

    List<EmployeeVO> page();
}
