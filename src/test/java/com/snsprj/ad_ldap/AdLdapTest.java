package com.snsprj.ad_ldap;

import com.snsprj.utils.ldap.AdLdapSyncTool;
import java.io.IOException;
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
    public void getAllUserDataTest() throws IOException {

        Runtime runtime = Runtime.getRuntime();

        runtime.gc();

        long startUsedMemory = runtime.totalMemory()/1024/1024;
        long startFreeMemory = runtime.freeMemory()/1024/1024;
        log.info("====>startUsedMemory is {}, startFreeMemory is {}", startUsedMemory, startFreeMemory);

        String host = "192.168.3.88";
        int port = 389;
        String username = "SNSPRJ\\Administrator";
        String password = "UUsafe917";
        String baseDN = "DC=snsprj,DC=cn";

        String[] returnedAttributes = {};
        String searchFilter = null;


        /**
         * cn:用户姓名；
         * userPrincipalName:用户登录名字；
         * sAMAccountName：用户登录名（Windows2000前）
         */
//        returnedAttributes = new String[]{"distinguishedName", "objectGUID", "cn", "name", "sAMAccountName", "userPrincipalName"};
        returnedAttributes = new String[]{"distinguishedName", "objectGUID"};

//        searchFilter = "(|(objectClass=person)(objectClass=user)(objectClass=organizationalPerson))";

        AdLdapSyncTool adLdapTest = new AdLdapSyncTool(username, password, host, port);

        List<Map<String, String>> recordList = adLdapTest.exportData(baseDN, returnedAttributes, searchFilter);

        long currentUsedMemory = Runtime.getRuntime().totalMemory()/1024/1024;
        long currentFreeMemory = Runtime.getRuntime().freeMemory()/1024/1024;
        log.info("====>currentUsedMemory is {}, currentFreeMemory is {}", currentUsedMemory, currentFreeMemory);

        log.info("====>recordList size is {}", recordList.size());
    }

}
