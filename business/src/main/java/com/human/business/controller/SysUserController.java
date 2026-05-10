package com.human.business.controller;

import com.human.business.domain.entity.SysUserDO;
import com.human.common.http.ResponseDTO;
import com.human.business.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sys/user")
public class SysUserController {

    @Resource
    private UserService userService;

    @PostMapping("/add")
    public ResponseDTO add(@RequestBody SysUserDO sysUserDO){

        return userService.addUser(sysUserDO);
    }

    @PostMapping("/login")
    public ResponseDTO login(@RequestBody SysUserDO sysUserDO){

        return userService.login(sysUserDO.getUsername(), sysUserDO.getPassword());
    }
}
