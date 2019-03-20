package com.snsprj.sbsm.vo;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 * 创建用户--表单
 */
@Getter
@Setter
public class UserVo {

    /**
     * 账号。不可为空。字母、数字、下划线组合，不能为纯数字，长度最小为6位，最长16位
     */
    @Size(min = 6, max = 16, message = "用户名长度6~16位")
    @Pattern(regexp = "^[a-zA-Z]+[A-Za-z0-9-_]{0,15}$", message = "用户名必须字母开头，由数字、字母、下划线组合，长度为6~16位的字符串")
    private String account;

    /**
     * 密码。长度最小为6位，最长16位
     */
    @Size(min = 6, max = 16, message = "密码长度6~16位")
    private String password;

    /**
     *
     */
    private String mobile;

    @Email
    private String email;

    /**
     * 昵称。支持emoji，最长20位
     */
    private String nickname;

    private String avatar;
}
