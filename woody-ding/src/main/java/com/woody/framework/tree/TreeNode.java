package com.woody.framework.tree;

import java.util.ArrayList;
import java.util.List;

public class TreeNode {

    private String id;
    private String parentId;
    private List<TreeNode> treeNodeList = new ArrayList<>();

    public TreeNode() {
    }

    public TreeNode(String id, String parentId) {
        this.id = id;
        this.parentId = parentId;
    }

    public List<TreeNode> getTreeNodeList() {
        return treeNodeList;
    }

    public void setTreeNodeList(List<TreeNode> treeNodeList) {
        this.treeNodeList = treeNodeList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    @Override
    public String toString() {
        return "TreeNode{" +
                "id='" + id + '\'' +
                ", parentId='" + parentId + '\'' +
                ", treeNodeList=" + treeNodeList +
                '}';
    }
}
