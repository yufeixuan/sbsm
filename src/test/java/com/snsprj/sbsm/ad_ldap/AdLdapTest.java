package com.snsprj.sbsm.ad_ldap;

import com.snsprj.sbsm.utils.ldap.ADTreeNode;
import com.snsprj.sbsm.utils.ldap.AdLdapSyncUtil;
import com.snsprj.sbsm.utils.ldap.OrganizationUtil;
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

//        String host = "192.168.3.88";
//        int port = 389;
//        String username = "SNSPRJ\\Administrator";
//        String password = "UUsafe917";
//        String baseDN = "DC=snsprj,DC=cn";

//        String host = "192.168.3.202";
//        int port = 389;
//        String username = "cn=Manager,dc=snsprj,dc=com";
//        String password = "123456";
//        String baseDN = "DC=snsprj,DC=com";

        String host = "192.168.1.56";
        int port = 389;
        String username = "TEST\\Administrator";
        String password = "uusafe916";
        String baseDN = "OU=同福客栈,DC=test,DC=com";

        String[] returnedAttributes = {};
        String searchFilter = null;

        /**
         * cn:用户姓名；
         * userPrincipalName:用户登录名字；
         * sAMAccountName：用户登录名（Windows2000前）
         */
//        returnedAttributes = new String[]{"distinguishedName", "objectGUID", "cn", "name", "sAMAccountName", "userPrincipalName"};

//        searchFilter = "(|(objectClass=person)(objectClass=user)(objectClass=organizationalPerson))";
        searchFilter = "(objectClass=organizationalUnit)";

        AdLdapSyncUtil adLdapTest = new AdLdapSyncUtil(username, password, host, port);

        List<Map<String, String>> recordList = adLdapTest.exportData(baseDN, returnedAttributes, searchFilter);

        log.info("====>recordList size is {}", recordList.size());

        List<ADTreeNode> nodeTree = OrganizationUtil.getOrganizationTree(recordList);

        log.info("====>", nodeTree);
    }


    /**
     * 判断用户状态。false：用户被禁用；true：用户正常。 参考文档 https://support.microsoft.com/zh-cn/help/305144/how-to-use-useraccountcontrol-to-manipulate-user-account-properties
     *
     * @param userAccountControl userAccountControl
     * @return boolean
     * @author xiaohb
     */
    private boolean isUserEnable(int userAccountControl) {

        if (userAccountControl == 2) {
            return false;
        }

        return (userAccountControl <= 2) || (userAccountControl & 2) <= 0;
    }

    @Test
    public void testIsUserEnable() {
        int userAccountControl = 16777216;
//        userAccountControl = 16777218;
        userAccountControl = 514;
//        userAccountControl = 1;
//        userAccountControl = 2;

        log.info("====>userAccountControl is {}", userAccountControl);
        boolean userEnable = this.isUserEnable(userAccountControl);

        log.info("userEnable is {}", userEnable);
    }
}
