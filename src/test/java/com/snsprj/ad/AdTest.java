package com.snsprj.ad;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.naming.*;
import javax.naming.directory.*;
import javax.naming.ldap.*;
import java.io.IOException;
import java.util.*;

@Slf4j
public class AdTest {

    /**
     * 连接AD
     *
     * @param host     ip地址
     * @param port     端口
     * @param username AD账号
     * @param password AD密码
     */
    private static void connect(String host, int port, String username, String password) {

        DirContext context = null;

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

        // 定义每次获取数据的条数
        int pageSize = 900;

        byte[] cookie = null;


        try {
            context = new InitialDirContext(adEnv);

            log.info("====>AD登录成功");


            // 域节点，域名的属性distinguishedName对应的值的值
            String domain = "DC=snsprj,DC=cn";

            // 搜索过滤器
            // 获取部门
//            String searchFilter = "objectClass=organizationalUnit";

            // user表示用户，group表示组
            String searchFilter = "(&(objectCategory=person)(objectClass=user))";

            SearchControls searchControl = new SearchControls();
            searchControl.setSearchScope(SearchControls.SUBTREE_SCOPE);

            // 设置搜索范围


            // 定制返回的属性
            String[] returnedAttributes = {"uSNCreated", "name", "instanceType", "ou"};
            searchControl.setReturningAttributes(returnedAttributes);

            // 根据设置的域节点、过滤器类、搜索控制器去搜索LDAP得到结果
            NamingEnumeration answer = context.search(domain, searchFilter, searchControl);

            while (answer.hasMoreElements()) {

                SearchResult searchResult = (SearchResult) answer.next();

                // 得到符合搜索条件的DN
                String dn = searchResult.getName();
                log.info("====>dn is {}", dn);

                // 得到符合条件的属性集
                Attributes attributes = searchResult.getAttributes();

                if (attributes != null) {

                    Map<String, String> row = new HashMap<>();


                    for (NamingEnumeration ne = attributes.getAll(); ne.hasMore(); ) {

                        Attribute attribute = (Attribute) ne.next();

                        String attributeId = attribute.getID().toString();
                        log.info("====>AttributeID=属性名：{}", attributeId);

                        // 读取属性值
                        for (NamingEnumeration e = attribute.getAll(); e.hasMore(); ) {

                            log.info("====>AttributeValues=属性值：{}", e.next().toString());

                        }
                    }
                }


            }


            context.close();

        } catch (AuthenticationException ex) {
            log.error("====>身份验证失败");
            ex.printStackTrace();
        } catch (CommunicationException ex) {
            log.error("====>AD域连接失败");
            ex.printStackTrace();
        } catch (NamingException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            log.error("====>身份验证未知异常");
            ex.printStackTrace();
        } finally {
            if (null != context) {
                try {
                    context.close();
                } catch (NamingException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static void getAdLdapDataByPage(String host, int port, String username, String password, String baseDN,
                                            String searchFilter, String[] returnedAttributes) {

        String company = "";

        // 用于保存所有同步的信息
        List<Map<String, String>> recordList = new ArrayList<>();

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

        // 定义每次获取数据的条数
        int pageSize = 900;

        // 总共获取的条数
        int total;

        byte[] cookie = null;

        try {
            LdapContext ldapContext = new InitialLdapContext(adEnv, null);

            // 分页控制器
            ldapContext.setRequestControls(new Control[]{new PagedResultsControl(pageSize, Control.CRITICAL)});

            SearchControls searchControl = new SearchControls();
            searchControl.setSearchScope(SearchControls.SUBTREE_SCOPE);

            // 定制返回的属性
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

                    // 得到符合条件的属性集
                    Attributes attributes = searchResult.getAttributes();

                    if (attributes != null) {

                        Map<String, String> row = new HashMap<>();

                        for (NamingEnumeration ne = attributes.getAll(); ne.hasMore(); ) {

                            Attribute attribute = (Attribute) ne.next();

                            for (NamingEnumeration e = attribute.getAll(); e.hasMore(); ) {

                                company = e.next().toString();

                                log.info("====>company is {}", company);
                            }

                            row.put(attribute.getID(), company);
                        }

                        row.put("userDN", dn);
                        recordList.add(row);
                    }
                }

                Control[] controls = ldapContext.getRequestControls();
                if (controls != null) {
                    for (Control control : controls) {

                        PagedResultsResponseControl prrc = (PagedResultsResponseControl) control;
                        total = prrc.getResultSize();
                        cookie = prrc.getCookie();
                    }
                }

                ldapContext.setRequestControls(new Control[]{new PagedResultsControl(pageSize, cookie, Control.CRITICAL)});
            } while (cookie != null);

            ldapContext.close();

            log.info("recordList is {}", recordList);

        } catch (NamingException | IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        String username = "SNSPRJ\\Administrator";
        String password = "UUsafe916";
        int port = 389;
        String host = "192.168.3.88";
        String baseDN = "DC=snsprj,DC=cn";
        String[] returnedAttributes = {};
        String searchFilter = null;
//        String[] returnedAttributes = {"uSNCreated", "name", "instanceType", "ou"};
//        String searchFilter = "(&(objectCategory=person)(objectClass=user))";

//        String username = "s-app@aac.com";
//        String password = "AAC#1234abcde";
//        int port = 389;
//        String host = "sz.ldap.aac.com";
//        String baseDN = "DC=aac,DC=com";
//        String[] returnedAttributes = {"uSNCreated", "name", "instanceType", "ou"};
//        String searchFilter = "(&(objectCategory=person)(objectClass=user))";

//        AdTest.connect(host, port, username, password);
        AdTest.getAdLdapDataByPage(host, port, username, password, baseDN, searchFilter, returnedAttributes);
    }
}
