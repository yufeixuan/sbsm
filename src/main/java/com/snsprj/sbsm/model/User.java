package com.snsprj.sbsm.model;

import java.util.Date;

public class User {
    private Long id;

    private String account;

    private String accountPinyin;

    private String password;

    private String salt;

    private String mobile;

    private String email;

    private Boolean userIsDeleted;

    private Date createdAt;

    private Date updatedAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account == null ? null : account.trim();
    }

    public String getAccountPinyin() {
        return accountPinyin;
    }

    public void setAccountPinyin(String accountPinyin) {
        this.accountPinyin = accountPinyin == null ? null : accountPinyin.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt == null ? null : salt.trim();
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public Boolean getUserIsDeleted() {
        return userIsDeleted;
    }

    public void setUserIsDeleted(Boolean userIsDeleted) {
        this.userIsDeleted = userIsDeleted;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}