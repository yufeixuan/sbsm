package com.snsprj.utils.ldap;

public class LdapAttribute {

    /**
     * 域名的部分，其格式是将完整的域名分成几部分，如域名为example.com变成dc=example,dc=com
     */
    public static final String DOMAIN_COMPONENT = "dc";

    /**
     * 用户id
     */
    public static final String USER_ID = "uid";

    /**
     * 组织单位
     */
    public static final String ORGANIZATION_UNIT = "ou";

    /**
     * 惟一辨别名
     */
    public static final String DISTINGUISHED_NAME = "dn";

    /**
     * 组织名，如“Example, Inc.”
     */
    public static final String ORGANIZATION = "o";

    /**
     * 指一个对象的名字。如果指人，需要使用其全名。
     */
    public static final String COMMON_NAME = "cn";

    /**
     * 指一个人的姓。
     */
    public static final String SURNAME = "sn";
}
