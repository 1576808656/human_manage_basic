package com.human.business.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.human.business.domain.ChangePasswordDTO;
import com.human.business.domain.ResetPasswordDTO;
import com.human.business.domain.entity.SysUserDO;
import com.human.business.service.UserService;
import com.human.common.http.ResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@Tag(name = "系统用户管理", description = "用户注册、登录、登出、密码管理")
@RestController
@RequestMapping("/sys/user")
public class SysUserController {

    @Resource
    private UserService userService;

    @Operation(summary = "用户注册", description = "添加新的系统用户")
    @PostMapping("/add")
    public ResponseDTO add(@Valid @RequestBody SysUserDO sysUserDO) {
        return userService.addUser(sysUserDO);
    }

    @Operation(summary = "用户登录", description = "用户名密码登录，返回Token和用户信息")
    @PostMapping("/login")
    public ResponseDTO login(@Valid @RequestBody SysUserDO sysUserDO) {
        return userService.login(sysUserDO);
    }

    @Operation(summary = "用户登出", description = "退出登录，使当前Token失效")
    @PostMapping("/logout")
    public ResponseDTO logout() {
        StpUtil.logout();
        return ResponseDTO.ok("登出成功");
    }

    @Operation(summary = "获取当前用户信息", description = "获取当前登录用户的详细信息")
    @GetMapping("/info")
    public ResponseDTO info() {
        return userService.getCurrentUserInfo();
    }

    @Operation(summary = "修改密码", description = "当前登录用户修改自己的密码（需要旧密码验证）")
    @PostMapping("/change-password")
    public ResponseDTO changePassword(@Valid @RequestBody ChangePasswordDTO dto) {
        return userService.changePassword(dto.getOldPassword(), dto.getNewPassword());
    }

    @Operation(summary = "重置密码", description = "管理员重置指定用户的密码")
    @PostMapping("/reset-password")
    public ResponseDTO resetPassword(@Valid @RequestBody ResetPasswordDTO dto) {
        return userService.resetPassword(dto.getUserId(), dto.getNewPassword());
    }
}
