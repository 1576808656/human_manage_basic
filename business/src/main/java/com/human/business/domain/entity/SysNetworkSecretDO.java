package com.human.business.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("sys_network_secret")
public class SysNetworkSecretDO {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Integer maxLoginAttempts;

    private Integer lockDuration;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}