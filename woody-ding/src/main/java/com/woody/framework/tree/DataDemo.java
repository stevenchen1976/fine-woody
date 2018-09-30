package com.woody.framework.tree;

import java.util.ArrayList;
import java.util.List;

public class DataDemo {

    // id, parentId
    public static List<TreeNode> resData = new ArrayList<>();

    public static List<TreeNode> getOrignalDateTree() {

        TreeNode node1 = new TreeNode("1", "0");
        TreeNode node2 = new TreeNode("2", "1");
        TreeNode node3 = new TreeNode("5", "1");
        TreeNode node4 = new TreeNode("6", "1");
        TreeNode node5 = new TreeNode("3", "2");
        TreeNode node6 = new TreeNode("4", "5");
        TreeNode node7 = new TreeNode("7", "5");
        TreeNode node8 = new TreeNode("8", "6");
        TreeNode node9 = new TreeNode("9", "3");
        TreeNode node10 = new TreeNode("10", "3");
        TreeNode node11 = new TreeNode("11", "3");
        TreeNode node12 = new TreeNode("12", "8");
        TreeNode node13 = new TreeNode("13", "8");
        TreeNode node14 = new TreeNode("14", "8");
        TreeNode node15 = new TreeNode("15", "8");
        TreeNode node16 = new TreeNode("16", "6");

        resData.add(node1);
        resData.add(node2);
        resData.add(node3);
        resData.add(node4);
        resData.add(node5);
        resData.add(node6);
        resData.add(node7);
        resData.add(node8);
        resData.add(node9);
        resData.add(node10);
        resData.add(node11);
        resData.add(node12);
        resData.add(node13);
        resData.add(node14);
        resData.add(node15);

        return resData;
    }
}
