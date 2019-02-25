package com.snsprj.ad_ldap;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.Control;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;
import javax.naming.ldap.PagedResultsControl;
import javax.naming.ldap.PagedResultsResponseControl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

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
     * 分页获取ad_ldap中的数据中所有符合条件的数据（可以优化成每次获取900条数据）
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

                Control[] controls = ldapContext.getResponseControls();
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
     * 获取全量同步部门数据--万科
     */
    public List<Map<String, String>> getExportDeptData(String baseDN,String searchFilter,String[] returnedAttributes) {


        log.info("====>getExportDeptData, baseDN is {}, searchFilter is {}, returnedAttributes",baseDN);

        // ldap createTimestamp 与 modifyTimestamp 为隐藏属性，无法获取其值。需要手动设置 createTimestamp 与 modifyTimestamp 为 new Date();
        List<Map<String, String>> recordList = this.getAdLdapDataByPage(baseDN, returnedAttributes, searchFilter);
        log.info("====>getExportDeptData, record size is{}", recordList.size());
        return recordList;
    }

    public static void main(String[] args) {

//        String username = "SNSPRJ\\Administrator";
//        String password = "UUsafe916";
//        int port = 389;
//        String host = "192.168.3.88";
//        String baseDN = "DC=snsprj,DC=cn";

        String username = "cn=Manager,dc=xinhua,dc=org";
        String password = "123456";
        int port = 389;
        String host = "192.168.1.42";
        String baseDN = "dc=xinhua,dc=org";

        String[] returnedAttributes = {};
        returnedAttributes = new String[]{"uid", "cn", "mobile", "mail", "not_exist_attr"};
//        String searchFilter = "(&(createTimestamp>=20180101000000Z)(objectClass=person))";
        String searchFilter = null;

//        searchFilter = "(&(userPassword=123456)(!(objectClass=organizationalUnit))(sn=002))";
//        searchFilter = "(&(userPassword=123456)(!(objectClass=person))(sn=002))";

        AdLdapSyncTool adLdapTest = new AdLdapSyncTool(username, password, host, port, baseDN, searchFilter,
            returnedAttributes);

        List<Map<String, String>> recordList = adLdapTest.getAdLdapDataByPage();

        log.info("====>recordList is {}", recordList);

//        adLdapTest.syncUserData(new Date());
    }
}
