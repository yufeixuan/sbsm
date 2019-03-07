package com.snsprj.sbsm.utils.ldap;

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

/**
 * ad/ldap获取数据工具类
 *
 * @author xiaohb
 */
@Slf4j
public class AdLdapSyncUtil {

    /**
     * ad_ldap ip
     */
    private String host;
    private int port;
    private String username;
    private String password;

    public AdLdapSyncUtil(String username, String password, String host, int port) {
        this.username = username;
        this.password = password;
        this.host = host;
        this.port = port;
    }

    /**
     * 分页获取ad_ldap中的数据中所有符合条件的数据（可以优化成每次获取900条数据）
     *
     * @param baseDN path
     * @param returnedAttributes 定制返回的属性，不指定则返回所有属性
     * @param searchFilter 过滤条件
     * @return list
     * @author xiaohb
     */
    private List<Map<String, String>> getAdLdapDataByPage(String baseDN, String[] returnedAttributes,
        String searchFilter) {

        // 定义每次获取数据的条数
        int pageSize = 900;

        // 用于保存所有同步的信息
        List<Map<String, String>> recordList = new ArrayList<>();

        byte[] cookie = null;

        LdapContext ldapContext = this.getLdapContext();

        if (ldapContext == null) {
            log.error("====>ldapContext is null!");
            return recordList;
        }

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
                    String dn = searchResult.getNameInNamespace();

                    // 得到符合条件的属性集(一条记录的所有属性的集合)
                    Attributes attributes = searchResult.getAttributes();

                    if (attributes != null) {
                        Map<String, String> row = this.getAttributeRecord(attributes);
                        row.put("dn", dn);
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

        } catch (NamingException | IOException e) {
            log.error("====>error occur,", e);
        } finally {
            try {
                // 防止程序异常没有正常close
                ldapContext.close();
            } catch (NamingException e) {
                log.error("====>error occur,", e);
            }
        }
        return recordList;
    }

    /**
     * 获取一条记录的 key/value 集合
     *
     * @param attributes 一条记录的所有属性的集合
     * @return Map
     * @author xiaohb
     */
    private Map<String, String> getAttributeRecord(Attributes attributes) {

        Map<String, String> row = new HashMap<>();

        String attributeKey = "";
        String attributeValue = "";
        try {
            NamingEnumeration ne = attributes.getAll();
            while (ne.hasMore()) {
                // 每个属性的键值对
                Attribute attribute = (Attribute) ne.next();
                attributeKey = attribute.getID();

                NamingEnumeration e = attribute.getAll();

                if (StringUtils.equalsIgnoreCase(attributeKey, "objectGUID")) {
                    byte[] objectGUID = (byte[]) e.next();
                    attributeValue = this.getGUID(objectGUID);
                    row.put("uuid", attributeValue);
                } else {
                    attributeValue = e.next().toString();
                    row.put(attributeKey, attributeValue);
                }
            }
        } catch (NamingException e) {
            e.printStackTrace();
        }

        return row;
    }

    /**
     * objectGUID byte[] 转 String
     *
     * @param objectGUID byte []
     * @return String
     * @author xiaohb
     */
    private String getGUID(byte[] objectGUID) {
        StringBuilder guid = new StringBuilder();
        for (byte b : objectGUID) {
            StringBuilder dblByte = new StringBuilder(Integer.toHexString(b & 0xff));
            if (dblByte.length() == 1) {
                guid.append("0");
            }
            guid.append(dblByte);
        }
        return guid.toString().toUpperCase();
    }

    /**
     * 获取AD/LDAP连接实例
     *
     * @return LdapContext
     */
    private LdapContext getLdapContext() {

        Hashtable<String, String> ldapEnv = new Hashtable<>();

        // LDAP/AD访问安全级别（none,simple,strong）
        ldapEnv.put(Context.SECURITY_AUTHENTICATION, "simple");
        ldapEnv.put(Context.SECURITY_PRINCIPAL, username);
        ldapEnv.put(Context.SECURITY_CREDENTIALS, password);
        // LDAP工厂类
        ldapEnv.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        // 连接超时时间3秒
        ldapEnv.put("com.sun.jndi.ldap.connect.timeout", "3000");
        // 默认端口389
        ldapEnv.put(Context.PROVIDER_URL, "ldap://" + host + ":" + port);

        // 设置16进制objectGUID
        ldapEnv.put("java.naming.ldap.attributes.binary", "objectGUID");
        LdapContext ldapContext = null;

        try {
            ldapContext = new InitialLdapContext(ldapEnv, null);
        } catch (NamingException e) {
            log.error("====>error occur,", e);
        }

        return ldapContext;
    }

    /**
     * 获取ad/ldap中的数据
     *
     * @param baseDN path
     * @param returnedAttributes 返回的属性
     * @param searchFilter 过滤条件
     * @return list
     * @author xiaohb
     */
    public List<Map<String, String>> exportData(String baseDN, String[] returnedAttributes, String searchFilter) {

        log.info("====>getExportDeptData, baseDN is {},  returnedAttributes is {}, searchFilter is {}", baseDN,
            returnedAttributes, searchFilter);

        // ldap createTimestamp 与 modifyTimestamp 为隐藏属性，无法获取其值。
        List<Map<String, String>> recordList = this.getAdLdapDataByPage(baseDN, returnedAttributes, searchFilter);
        log.info("====>exportData, record size is{}", recordList.size());
        return recordList;
    }
}
