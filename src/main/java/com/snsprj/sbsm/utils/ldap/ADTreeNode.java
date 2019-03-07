package com.snsprj.sbsm.utils.ldap;

import java.util.ArrayList;
import java.util.List;

/**
 * AD Organization Tree Node
 */
public class ADTreeNode {

    /**
     * 数据库 - 主键id
     */
    private String primaryKey;

    /**
     * 数据库 - 父节点主键id
     */
    private String parentPrimaryKey;

    /**
     * AD/LDAP - distinguishedName
     */
    private String dn;

    /**
     * AD/LDAP - Organization Unit
     */
    private String ou;

    /**
     * AD/LDAP - uuid
     */
    private String uuid;

    /**
     * 当前节点拥有的子节点集合
     */
    private List<ADTreeNode> childNodeList = new ArrayList<>();

    /**
     * 组织路径-用户水印用
     */
    @Deprecated
    private String organizationWatermark;

    /**
     * 主键路径。不推荐使用，违反数据库无限级分类规则
     */
    @Deprecated
    private String treePath;

    public String getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(String primaryKey) {
        this.primaryKey = primaryKey;
    }

    public String getParentPrimaryKey() {
        return parentPrimaryKey;
    }

    public void setParentPrimaryKey(String parentPrimaryKey) {
        this.parentPrimaryKey = parentPrimaryKey;
    }

    public String getDn() {
        return dn;
    }

    public void setDn(String dn) {
        this.dn = dn;
    }

    public String getOu() {
        return ou;
    }

    public void setOu(String ou) {
        this.ou = ou;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public List<ADTreeNode> getChildNodeList() {
        return childNodeList;
    }

    public String getOrganizationWatermark() {
        return organizationWatermark;
    }

    public void setOrganizationWatermark(String organizationWatermark) {
        this.organizationWatermark = organizationWatermark;
    }

    public String getTreePath() {
        return treePath;
    }

    public void setTreePath(String treePath) {
        this.treePath = treePath;
    }
}
