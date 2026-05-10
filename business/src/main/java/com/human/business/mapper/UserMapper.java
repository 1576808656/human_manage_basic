package com.human.business.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.human.business.domain.entity.SysUserDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<SysUserDO> {
}
