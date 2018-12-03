package com.woody.framework.datastructure.list;

public class ListAction {

    public static void main(String[] args){
        ListNode node1 = new ListNode(1);
        ListNode node2 = new ListNode(2);
        ListNode node3 = new ListNode(3);
        ListNode node4 = new ListNode(4);
        ListNode node5 = new ListNode(5);
        ListNode node6 = new ListNode(6);

        node1.next = node2;
        node2.next = node3;
        node3.next = node4;
        node4.next = node5;
        node5.next = node6;
        node6.next = null;

        MyList.traverse(node1);
        MyList.traverse(reverseList(node1));
        System.out.println(getMid(node6).value);

        ListNode node7 = new ListNode(7);
        ListNode node8 = new ListNode(8);
        ListNode node9 = new ListNode(9);
        ListNode node10 = new ListNode(10);
        ListNode node11 = new ListNode(11);
        ListNode node12 = new ListNode(12);

        node7.next = node9;
        node9.next = node11;

        node8.next = node10;
        node10.next = node12;

//        MyList.traverse(mergeTwoList(node7, node8));
        MyList.traverse(mergeTwoList2(node7, node8));
    }

    /**
     * 反转链表
     * O(n) O(1)
     */
    public static ListNode reverseList(ListNode head) {
        ListNode pre = null;   //当前节点的上一个
        ListNode next = null;  //当前节点的下一个
        while (head != null) {
            next = head.next;
            head.next = pre;
            pre = head;
            head = next;
        }
        return pre;
    }

    /**
     *取中间节点（偶数个取得中间节点是前面那个）
     */
    public static ListNode getMid(ListNode head) {
        if(head == null) {
            return head;
        }
        ListNode fast = head;
        ListNode slow = head;

        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }


    /**
     *合并两个有序链表
     * 方法一：递归
     */
    public static ListNode mergeTwoList(ListNode head1, ListNode head2) {
        if(head1 == null && head2 == null) {
            return null;
        }
        if(head1 == null) {
            return head2;
        }
        if(head2 == null) {
            return head1;
        }

        ListNode head = null;
        if(head1.value > head2.value) {
            head = head2;
            head.next = mergeTwoList(head1, head2.next);
        } else {
            head = head1;
            head.next = mergeTwoList(head1.next, head2);
        }
        return head;
    }

    /**
     * 合并两个有序列表
     * 方式二：非递归
     */
    public static ListNode mergeTwoList2(ListNode head1, ListNode head2) {
        if(head1 == null || head2 == null) {
            return head1 != null ? head1 : head2;
        }
        ListNode head = head1.value < head2.value ? head1 : head2;
        ListNode cur1 = head == head1 ? head1 : head2;
        ListNode cur2 = head == head1 ? head2 : head1;

        ListNode pre = null; //cur1前一个元素
        ListNode next = null;//cur2后一个元素
        while (cur1 != null && cur2 != null) {
            if(cur1.value <= cur2.value) {
                pre = cur1;
                cur1 = cur1.next;
            } else {
                next = cur2.next;
                pre.next = cur2;
                cur2.next = cur1;
                pre = cur2;
                cur2 = next;
            }
        }
        pre.next = cur1 == null ? cur2 : cur1;
        return head;
    }
}
