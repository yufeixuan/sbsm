package com.snsprj.sbsm.utils.ldap;

import com.snsprj.sbsm.utils.UUIDUtil;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * Organization Util
 *
 * @author xiaohb
 */
@Slf4j
public class OrganizationUtil {

    /**
     * 获取组织的树状结构
     *
     * @param organizationList AD/LDAP中获取的组织数据
     * @return List<ADTreeNode>
     * @author xiaohb
     */
    public static List<ADTreeNode> getOrganizationTree(List<Map<String, String>> organizationList) {

        if (organizationList == null || organizationList.size() == 0) {
            return null;
        } else {
            List<ADTreeNode> nodeList = new ArrayList<>();
            Map<String, ADTreeNode> nodeMap = new HashMap<>();

            for (Map<String, String> organizationMap : organizationList) {
                ADTreeNode currentNode = (ADTreeNode) mapToObject(organizationMap, ADTreeNode.class);

                String primaryKey = UUIDUtil.getUUID();
                currentNode.setPrimaryKey(primaryKey);

                String dn = currentNode.getDn();
                nodeMap.put(dn, currentNode);
                nodeList.add(currentNode);
            }

            // OU=财务部-澳洲分部,OU=财务部,OU=同福客栈,DC=test,DC=com
            List<ADTreeNode> nodeTree = new ArrayList<>();
            for (ADTreeNode node : nodeList) {
                String dn = node.getDn();
                String parentDn = getParentDn(dn);
                ADTreeNode parentNode = nodeMap.get(parentDn);
                if (parentNode == null) {
                    // 该节点没有父节点，加入node tree
                    nodeTree.add(node);
                } else {
                    // 该节点存在父节点，把当前节点加入父节点
                    // 设置子节点的parentPrimaryKey
                    String parentPrimaryKey = parentNode.getPrimaryKey();
                    node.setParentPrimaryKey(parentPrimaryKey);

                    parentNode.getChildNodeList().add(node);
                }
            }
            return nodeTree;
        }
    }

    /**
     * map to object
     *
     * @param map map
     * @param beanClass class
     * @return Object
     */
    private static Object mapToObject(Map<String, ?> map, Class<?> beanClass) {

        if (map == null) {
            return null;
        } else {
            try {
                Object object = beanClass.newInstance();

                BeanUtils.populate(object, map);

                return object;

            } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                log.error("====>mapToObject error", e);
                return null;
            }
        }
    }

    /**
     * 获取父的dn
     *
     * @param dn dn
     * @return parent dn
     * @author xiaohb
     */
    private static String getParentDn(String dn) {

        int index = StringUtils.indexOf(dn, ",OU=");
        if (index > 0){
            return StringUtils.substring(dn, index + 1);
        }else {
            return null;
        }
    }
}
