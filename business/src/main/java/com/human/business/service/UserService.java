package com.human.business.service;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.human.business.domain.entity.SysUserDO;
import com.human.common.http.ResponseDTO;
import com.human.business.mapper.UserMapper;
import jakarta.annotation.Resource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Resource
    private UserMapper userMapper;

    public ResponseDTO login(String username, String password) {
        if(username == null || password == null) {
            return ResponseDTO.error("用户名或密码不能为空");
        }
        LambdaQueryWrapper<SysUserDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUserDO::getUsername, username);
        SysUserDO sysUserDO = userMapper.selectOne(queryWrapper);
        if(sysUserDO == null)
            return ResponseDTO.error("用户名不存在");

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if(!passwordEncoder.matches(password, sysUserDO.getPassword())) {
            return ResponseDTO.error("密码错误，请重试");
        }
        StpUtil.login(sysUserDO.getId());
        return ResponseDTO.ok("登录成功");
    }

    public ResponseDTO addUser(SysUserDO sysUserDO) throws IllegalArgumentException {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        sysUserDO.setPassword(passwordEncoder.encode(sysUserDO.getPassword()));
        int res = userMapper.insert(sysUserDO);
        if(res == 0)
            return ResponseDTO.error("用户添加失败");
        return ResponseDTO.ok("系统用户添加成功");
    }
}
