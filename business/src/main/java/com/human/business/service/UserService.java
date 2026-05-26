package com.human.business.service;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.human.business.domain.entity.SysNetworkSecretDO;
import com.human.business.domain.entity.SysUserDO;
import com.human.business.domain.vo.LoginResultVO;
import com.human.business.mapper.SysNetworkSecretMapper;
import com.human.business.mapper.UserMapper;
import com.human.business.util.PasswordUtils;
import com.human.common.http.ResponseDTO;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
public class UserService {

    private volatile int maxLoginAttempts = 5;
    private volatile int lockDurationMinutes = 30;

    private final Map<String, Integer> loginAttempts = new ConcurrentHashMap<>();
    private final Map<String, LocalDateTime> lockTimes = new ConcurrentHashMap<>();

    @Resource
    private UserMapper userMapper;

    @Resource
    private SysNetworkSecretMapper sysNetworkSecretMapper;

    @PostConstruct
    public void loadNetworkSecretConfig() {
        SysNetworkSecretDO config = sysNetworkSecretMapper.selectById(1L);
        if (config != null) {
            maxLoginAttempts = config.getMaxLoginAttempts();
            lockDurationMinutes = config.getLockDuration();
            log.info("加载登录安全配置：最大错误次数={}, 锁定时长={}分钟", maxLoginAttempts, lockDurationMinutes);
        } else {
            log.warn("未找到登录安全配置，使用默认值：最大错误次数=5, 锁定时长=30分钟");
        }
    }

    public ResponseDTO login(SysUserDO sysUserDO) {
        String username = sysUserDO.getUsername();
        String password = sysUserDO.getPassword();
        if (username == null || username.isBlank() || password == null || password.isBlank()) {
            return ResponseDTO.error("用户名或密码不能为空");
        }

        if (isAccountLocked(username)) {
            return ResponseDTO.error("账号已被锁定，请" + lockDurationMinutes + "分钟后再试");
        }

        LambdaQueryWrapper<SysUserDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUserDO::getUsername, username);
        SysUserDO exists = userMapper.selectOne(queryWrapper);

        if (exists == null) {
            incrementLoginAttempts(username);
            return ResponseDTO.error("用户名不存在");
        }

        if (exists.getStatus() != null && exists.getStatus() == 0) {
            return ResponseDTO.error("账号已被禁用，请联系管理员");
        }

        if (!PasswordUtils.matches(password, exists.getPassword())) {
            incrementLoginAttempts(username);
            int remaining = maxLoginAttempts - loginAttempts.getOrDefault(username, 0);
            return ResponseDTO.error("密码错误，请重试（剩余尝试次数：" + remaining + "）");
        }

        loginAttempts.remove(username);
        lockTimes.remove(username);

        exists.setLastLoginTime(LocalDateTime.now());
        userMapper.updateById(exists);

        StpUtil.login(exists.getId());
        SaTokenInfo tokenInfo = StpUtil.getTokenInfo();

        LoginResultVO result = new LoginResultVO();
        result.setToken(tokenInfo.getTokenValue());
        result.setTokenName(tokenInfo.getTokenName());
        result.setUserId(exists.getId());
        result.setUsername(exists.getUsername());
        result.setPhone(exists.getPhone());
        result.setEmail(exists.getEmail());
        result.setHeadPhoto(exists.getHeadPhoto());

        return ResponseDTO.ok(result, "登录成功");
    }

    public ResponseDTO addUser(SysUserDO sysUserDO) {
        String username = sysUserDO.getUsername();
        String password = sysUserDO.getPassword();
        if (username == null || username.isBlank()) {
            return ResponseDTO.error("用户名不能为空");
        }
        if (password == null || password.length() < 6) {
            return ResponseDTO.error("密码长度不能少于6位");
        }

        LambdaQueryWrapper<SysUserDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUserDO::getUsername, username);
        if (userMapper.selectOne(queryWrapper) != null) {
            return ResponseDTO.error("用户名已存在");
        }

        sysUserDO.setUuid(IdUtil.randomUUID());
        sysUserDO.setPassword(PasswordUtils.encode(password));
        sysUserDO.setStatus(1);
        int res = userMapper.insert(sysUserDO);
        if (res == 0) {
            return ResponseDTO.error("用户添加失败");
        }
        return ResponseDTO.ok("系统用户添加成功");
    }

    public ResponseDTO getCurrentUserInfo() {
        Long userId = StpUtil.getLoginIdAsLong();
        SysUserDO user = userMapper.selectById(userId);
        if (user == null) {
            return ResponseDTO.error("用户不存在");
        }
        user.setPassword(null);
        return ResponseDTO.ok(user);
    }

    public ResponseDTO changePassword(String oldPassword, String newPassword) {
        if (oldPassword == null || oldPassword.isBlank() || newPassword == null || newPassword.isBlank()) {
            return ResponseDTO.error("密码不能为空");
        }
        if (newPassword.length() < 6) {
            return ResponseDTO.error("新密码长度不能少于6位");
        }
        if (oldPassword.equals(newPassword)) {
            return ResponseDTO.error("新密码不能与旧密码相同");
        }

        Long userId = StpUtil.getLoginIdAsLong();
        SysUserDO user = userMapper.selectById(userId);
        if (user == null) {
            return ResponseDTO.error("用户不存在");
        }

        if (!PasswordUtils.matches(oldPassword, user.getPassword())) {
            return ResponseDTO.error("旧密码错误");
        }

        user.setPassword(PasswordUtils.encode(newPassword));
        userMapper.updateById(user);
        return ResponseDTO.ok("密码修改成功");
    }

    public ResponseDTO resetPassword(Long userId, String newPassword) {
        if (newPassword == null || newPassword.isBlank()) {
            return ResponseDTO.error("新密码不能为空");
        }
        if (newPassword.length() < 6) {
            return ResponseDTO.error("新密码长度不能少于6位");
        }

        SysUserDO user = userMapper.selectById(userId);
        if (user == null) {
            return ResponseDTO.error("用户不存在");
        }

        user.setPassword(PasswordUtils.encode(newPassword));
        userMapper.updateById(user);
        return ResponseDTO.ok("密码重置成功");
    }

    private void incrementLoginAttempts(String username) {
        int attempts = loginAttempts.merge(username, 1, Integer::sum);
        if (attempts >= maxLoginAttempts) {
            lockTimes.put(username, LocalDateTime.now());
            log.warn("账号 {} 已被锁定，登录失败次数达到 {}", username, maxLoginAttempts);
        }
    }

    private boolean isAccountLocked(String username) {
        LocalDateTime lockTime = lockTimes.get(username);
        if (lockTime == null) {
            return false;
        }
        if (lockTime.plusMinutes(lockDurationMinutes).isBefore(LocalDateTime.now())) {
            lockTimes.remove(username);
            loginAttempts.remove(username);
            return false;
        }
        return true;
    }
}
