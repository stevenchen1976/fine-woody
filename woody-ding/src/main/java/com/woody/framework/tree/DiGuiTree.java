package com.woody.framework.tree;


import java.util.*;

public class DiGuiTree {


    public static void main(String[] args) {

        List<TreeNode> orignalDateTree = DataDemo.getOrignalDateTree();

//        TreeNode diGuiTree = getTree(orignalDateTree, "0");
//        TreeNode diGuiTree = getTree(orignalDateTree, "1");
//        JSONObject jsonObject = JSONObject.fromObject(diGuiTree);
//        System.out.println(jsonObject.toString());


//        List<TreeNode> resultList = new ArrayList<>();
//        getListByTree(diGuiTree, resultList);
//        for (TreeNode t : resultList) {
//            System.out.println(t.toString());
//        }

        List<TreeNode> res = new ArrayList<>();
        List<TreeNode> treeUpPartByNodeId = getTreeUpPartByNodeId("12", "6", orignalDateTree, res);
        for (TreeNode t : treeUpPartByNodeId) {
            System.out.println(t.toString());
        }
    }

    /**
     * 选择根节点，List构造成树
     *
     * @param orignalDateTree
     * @param rootId
     * @return
     */
    public static TreeNode getTree(List<TreeNode> orignalDateTree, String rootId) {

        Map<String, TreeNode> map = new HashMap<>();
        for (TreeNode node : orignalDateTree) {
            map.put(node.getId(), node);
        }

        TreeNode root = null;
        Iterator<Map.Entry<String, TreeNode>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, TreeNode> next = iterator.next();
            TreeNode treeNode = next.getValue();
            if (rootId.equals(treeNode.getId())) {
                root = treeNode;
            } else {
                TreeNode parentNode = map.get(treeNode.getParentId());
                if (parentNode != null) {
                    parentNode.getTreeNodeList().add(treeNode);
                }
            }
        }
        return root;
    }

    /**
     * 通过List，获取自节点到父节点的node
     * @param nodeId
     * @param rootId
     * @param tree
     * @param resultList
     * @return
     */
    public static List<TreeNode> getTreeUpPartByNodeId(String nodeId, String rootId, List<TreeNode> tree, List<TreeNode> resultList) {
        for (TreeNode node : tree) {
            if (nodeId.equals(node.getId())) {
                resultList.add(node);
                if (!rootId.equals(node.getId())) {
                    getTreeUpPartByNodeId(node.getParentId(), rootId, tree, resultList);
                }
            }
        }
        return resultList;
    }

    /**
     * 将树拆成List
     *
     * @param treeNode
     * @param resultList
     * @return
     */
    public static List<TreeNode> getListByTree(TreeNode treeNode, List<TreeNode> resultList) {

        if (treeNode.getTreeNodeList() != null) {
            for (TreeNode t : treeNode.getTreeNodeList()) {
                getListByTree(t, resultList);
                t.setTreeNodeList(null);
            }
        } else {
            resultList.add(treeNode);
        }
        treeNode.setTreeNodeList(null);
        resultList.add(treeNode);

        return resultList;
    }
}


