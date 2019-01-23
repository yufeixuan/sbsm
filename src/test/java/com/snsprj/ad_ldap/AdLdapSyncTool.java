package com.snsprj.ad_ldap;

import java.text.SimpleDateFormat;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.naming.*;
import javax.naming.directory.*;
import javax.naming.ldap.*;
import java.io.IOException;
import java.util.*;

@Slf4j
public class AdLdapSyncTool {

    /**
     * ad_ldap ip
     */
    private String host;

    private int port;
    private String username;
    private String password;
    private String baseDN;

    /**
     * 查询过滤条件
     */
    private String searchFilter;

    /**
     * 查询需要返回的值
     */
    private String[] returnedAttributes;

    public AdLdapSyncTool(String username, String password, String host, int port, String baseDN, String searchFilter,
        String[] returnedAttributes) {
        this.username = username;
        this.password = password;
        this.host = host;
        this.port = port;
        this.baseDN = baseDN;
        this.searchFilter = searchFilter;
        this.returnedAttributes = returnedAttributes;
    }

    /**
     * 分页获取ad_ldap中的数据
     */
    private List<Map<String, String>> getAdLdapDataByPage() {

        // 定义每次获取数据的条数
        int pageSize = 900;

        // 某个属性的key
        String attributeKey = "";

        // 某个属性的value
        String attributeValue = "";

        // 用于保存所有同步的信息
        List<Map<String, String>> recordList = new ArrayList<>();

        byte[] cookie = null;

        LdapContext ldapContext = this.getLdapContext();

        try {

            // 分页控制器
            ldapContext.setRequestControls(new Control[]{new PagedResultsControl(pageSize, Control.CRITICAL)});

            SearchControls searchControl = new SearchControls();
            searchControl.setSearchScope(SearchControls.SUBTREE_SCOPE);

            // 定制返回的属性，不指定则返回所有属性
            if (returnedAttributes != null && returnedAttributes.length > 0) {
                searchControl.setReturningAttributes(returnedAttributes);
            }

            if (StringUtils.isBlank(searchFilter)) {
                // 如果没设置过滤条件则使用默认过滤
                searchFilter = "(objectClass=*)";
            }

            do {
                // 根据设置的域节点、过滤器类、搜索控制器去搜索LDAP得到结果
                NamingEnumeration answer = ldapContext.search(baseDN, searchFilter, searchControl);

                while (null != answer && answer.hasMoreElements()) {

                    SearchResult searchResult = (SearchResult) answer.next();

                    // distinguishedName
                    String dn = searchResult.getName();
                    log.info("====>distinguishedName is {}", dn);

                    // 得到符合条件的属性集(一条记录的所有属性的集合)
                    Attributes attributes = searchResult.getAttributes();

                    if (attributes != null) {

                        Map<String, String> row = new HashMap<>();

                        for (NamingEnumeration ne = attributes.getAll(); ne.hasMore(); ) {
                            // 每个属性的键值对
                            Attribute attribute = (Attribute) ne.next();
                            attributeKey = attribute.getID();

                            for (NamingEnumeration e = attribute.getAll(); e.hasMore(); ) {
                                attributeValue = e.next().toString();
                            }
                            row.put(attributeKey, attributeValue);
                        }

                        row.put("userDN", dn);
                        recordList.add(row);
                    }
                }

                Control[] controls = ldapContext.getRequestControls();
                if (controls != null) {
                    for (Control control : controls) {
                        if (control instanceof PagedResultsResponseControl) {
                            PagedResultsResponseControl prrc = (PagedResultsResponseControl) control;
                            cookie = prrc.getCookie();
                        }
                    }
                }

                ldapContext
                    .setRequestControls(new Control[]{new PagedResultsControl(pageSize, cookie, Control.CRITICAL)});
            } while (cookie != null);

            ldapContext.close();

            log.info("====>getAdLdapDataByPage, recordList size is {}", recordList.size());

        } catch (NamingException | IOException e) {
            e.printStackTrace();
        } finally {
            if (null != ldapContext) {
                try {
                    ldapContext.close();
                } catch (NamingException e) {
                    e.printStackTrace();
                }
            }

        }
        return recordList;
    }

    /**
     * 获取AD/LDAP连接实例
     *
     * @return LdapContext
     */
    private LdapContext getLdapContext() {

        Hashtable<String, String> adEnv = new Hashtable<>();

        // LDAP/AD访问安全级别（none,simple,strong）
        adEnv.put(Context.SECURITY_AUTHENTICATION, "simple");
        adEnv.put(Context.SECURITY_PRINCIPAL, username);
        adEnv.put(Context.SECURITY_CREDENTIALS, password);
        // LDAP工厂类
        adEnv.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        // 连接超时时间3秒
        adEnv.put("com.sun.jndi.ldap.connect.timeout", "3000");
        // 默认端口389
        adEnv.put(Context.PROVIDER_URL, "ldap://" + host + ":" + port);

        LdapContext ldapContext = null;

        try {
            ldapContext = new InitialLdapContext(adEnv, null);
        } catch (NamingException e) {
            e.printStackTrace();
        }

        return ldapContext;
    }

    /**
     * 导入用户-针对万科
     */
    public void exportUserData() {

        // uid:唯一标示；cn:用户姓名；smart-adaccount:AD/LDAP账号;departmentNumber:部门id;smart-orgno-fullpath:用户组织编号全路径
        returnedAttributes = new String[]{"uid", "cn", "smart-adaccount", "mobile", "mail", "departmentNumber",
            "smart-orgno-fullpath"};

        // smart-sources:1-HR,2-主数据,3-销售,4-匠心,5-SRM
        // smart-status:0-禁用,1-启用
        searchFilter = "(&(smart-adaccount=*)(smart-sources=1)(smart-status=1))";
        baseDN = "ou=People,dc=vanketest,dc=com";

        List<Map<String, String>> recordList = this.getAdLdapDataByPage();
    }

    /**
     * 同步用户-针对万科
     */
    public void syncUserData(Date startDate) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String startDateStr = sdf.format(startDate);
        startDateStr += "Z";
        log.info("====>syncUserData, startDateStr is {}", startDateStr);

    }

    public static void main(String[] args) {

        String username = "cn=Manager,dc=xinhua,dc=org";
        String password = "123456";
        int port = 389;
        String host = "192.168.1.42";
        String baseDN = "dc=xinhua,dc=org";

        String[] returnedAttributes = {};
        String searchFilter = "(&(createTimestamp>=20180101000000Z)(objectClass=person))";

        AdLdapSyncTool adLdapTest = new AdLdapSyncTool(username, password, host, port, baseDN, searchFilter,
            returnedAttributes);

//        adLdapTest.getAdLdapDataByPage();

        adLdapTest.syncUserData(new Date());
    }
}
