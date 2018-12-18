package com.woody.framework.datastructure.tree;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Stack;

public class Traverse {

    //前序遍历（非递归）
    public static void preOrder(TreeNode head) {
        if(head != null) {
            Stack<TreeNode> stack = new Stack<>();
            stack.push(head);
            while (!stack.isEmpty()) {
                TreeNode pop = stack.pop();
                System.out.print(pop.value + " ");
                if(pop.right != null) {
                    stack.push(pop.right);
                }
                if(pop.left != null) {
                    stack.push(pop.left);
                }
            }
        }
    }

    //中序遍历（非递归）
    public static void inOrder(TreeNode head) {
        if(head != null) {
            Stack<TreeNode> stack = new Stack<>();
            while (!stack.isEmpty() || head != null) {
                if(head != null) {
                    stack.push(head);
                    head = head.left;
                } else {
                    head = stack.pop();
                    System.out.print(head.value + " ");
                    head = head.right;
                }
            }
        }
    }

    //后序遍历（非递归）
    public static void postOrder(TreeNode head) {
        if(head != null) {
            Stack<TreeNode> stack1 = new Stack<>();
            Stack<TreeNode> stack2 = new Stack<>();
            stack1.push(head);
            while (!stack1.isEmpty()) {
                TreeNode pop = stack1.pop();
                stack2.push(pop);
                if(pop.left != null) {
                    stack1.push(pop.left);
                }
                if(pop.right != null) {
                    stack1.push(pop.right);
                }
            }
            while (!stack2.isEmpty()) {
                System.out.print(stack2.pop().value + " ");
            }
        }
    }

    //层次遍历（非递归）
   public static void levelOrder(TreeNode head) {
        if(head != null) {
            Queue<TreeNode> queue = new ArrayDeque<>();
            queue.offer(head);
            while (!queue.isEmpty()) {
                //获取当前层的节点数
                int levelNum = queue.size();
                for (int i = 0; i < levelNum; i++) {
                    TreeNode poll = queue.poll();
                    System.out.print(poll.value + " ");

                    if(poll.left != null) {
                        queue.offer(poll.left);
                    }
                    if(poll.right != null) {
                        queue.offer(poll.right);
                    }
                }
            }
        }
   }

   public static TreeNode init() {
       TreeNode A = new TreeNode("A");
       TreeNode B = new TreeNode("B");
       TreeNode C = new TreeNode("C");
       TreeNode D = new TreeNode("D");
       TreeNode E = new TreeNode("E");
       TreeNode F = new TreeNode("F");
       TreeNode G = new TreeNode("G");
       TreeNode H = new TreeNode("H");
       TreeNode I = new TreeNode("I");
       TreeNode J = new TreeNode("J");
       TreeNode K = new TreeNode("K");

       A.left = B;
       A.right = C;
       B.left = D;
       B.right = E;
       D.left = H;
       D.right = I;
       E.right = J;
       C.left = F;
       C.right = G;
       F.right = K;

       return A;
   }

    public static void main(String[] args){

        TreeNode A = init();

        System.out.print("前序： ");
        preOrder(A);
        System.out.println();
        System.out.print("中序： ");
        inOrder(A);
        System.out.println();
        System.out.print("后序： ");
        postOrder(A);
        System.out.println();
        System.out.print("层次： ");
        levelOrder(A);
    }
}
