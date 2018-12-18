package com.woody.framework.datastructure.tree;

import java.util.ArrayDeque;
import java.util.Queue;

public class Depth {

    //最大深度（递归）
    public static int maxDepth(TreeNode root) {
        if(root == null) {
            return 0;
        }
        int left = maxDepth(root.left);
        int rigth = maxDepth(root.right);
        return Math.max(left, rigth) + 1;
    }

    //最大深度（非递归）
    public static int maxDepth2(TreeNode root) {
        if(root == null) {
            return 0;
        }
        Queue<TreeNode> queue = new ArrayDeque<>();
        queue.offer(root);
        int level = 0;
        while (!queue.isEmpty()) {
            level++;
            int levelNum = queue.size();
            for (int i = 0; i < levelNum; i++) {
                TreeNode node = queue.poll();
                if(node.left != null) {
                    queue.offer(node.left);
                }
                if(node.right != null) {
                    queue.offer(node.right);
                }
            }
        }
        return level;
    }

    //最小深度（递归）
    public static int minDepth(TreeNode root) {
        if(root == null) {
            return 0;
        }
        if(root.left == null && root.right == null) {
            return 1;
        }
        if(root.left == null && root.right != null) {
            return minDepth(root.right) + 1;
        }
        if(root.left != null && root.right == null) {
            return minDepth(root.left) + 1;
        }

        int left = minDepth(root.left);
        int rigth = minDepth(root.right);

        return Math.min(left, rigth) + 1;
    }

    //最小深度（非递归）
    public static int minDepth2(TreeNode root) {
        if(root == null) {
            return 0;
        }
        Queue<TreeNode> queue = new ArrayDeque<>();
        queue.offer(root);
        int level = 0;
        while (!queue.isEmpty()) {
            level++;
            int levelNum = queue.size();
            for (int i = 0; i < levelNum; i++) {
                TreeNode node = queue.poll();
                if(node.left == null && node.right == null) {
                    return level;
                }
                if(node.left != null) {
                    queue.offer(node.left);
                }
                if(node.right != null) {
                    queue.offer(node.right);
                }
            }
        }
        return 0;
    }

    public static void main(String[] args){
        TreeNode root = Traverse.init();
        System.out.println(maxDepth(root) + " ");
        System.out.println(maxDepth2(root) + " ");
        System.out.println(minDepth(root) + " ");
        System.out.println(minDepth2(root) + " ");
    }
}
