package com.human.business.domain.vo;

import lombok.Data;

@Data
public class LoginResultVO {

    private String token;

    private String tokenName;

    private Long userId;

    private String username;

    private String phone;

    private String email;

    private String headPhoto;
}
