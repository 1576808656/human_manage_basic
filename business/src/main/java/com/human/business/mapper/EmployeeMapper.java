package com.human.business.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.human.business.domain.EmployDTO;
import com.human.business.domain.entity.EmployeeDO;
import com.human.business.domain.vo.EmployeeVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface EmployeeMapper extends BaseMapper<EmployeeDO> {

    IPage<EmployeeVO> page(@Param("page") Page<EmployeeVO> page, @Param("dto") EmployDTO dto);
}
