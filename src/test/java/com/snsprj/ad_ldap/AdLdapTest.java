package com.snsprj.ad_ldap;

import com.snsprj.utils.ldap.AdLdapSyncTool;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class AdLdapTest {

    /**
     * 测试获取AD中所有用户的数据
     */
    @Test
    public void getAllUserDataTest(){

        String host = "192.168.3.88";
        int port = 389;
        String username = "SNSPRJ\\Administrator";
        String password = "UUsafe917";
        String baseDN = "DC=snsprj,DC=cn";

        String[] returnedAttributes = {};
        String searchFilter = null;

        // cn:用户姓名；
        // name：;
        // userPrincipalName:用户登录名字；
        // sAMAccountName：用户登录名（Windows2000前）
        returnedAttributes = new String[]{"distinguishedName","objectGUID","cn","name","sAMAccountName","userPrincipalName"};

        searchFilter = "(|(objectClass=person)(objectClass=user)(objectClass=organizationalPerson))";

        AdLdapSyncTool adLdapTest = new AdLdapSyncTool(username, password, host, port);

        List<Map<String, String>> recordList = adLdapTest.exportData(baseDN, returnedAttributes, searchFilter);

        log.info("====>recordList is {}", recordList);
    }

}
