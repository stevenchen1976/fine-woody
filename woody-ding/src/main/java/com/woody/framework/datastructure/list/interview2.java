package com.woody.framework.datastructure.list;

import org.springframework.security.access.method.P;

public class interview2 {

    public static ListNode merge(ListNode head1, ListNode head2) {
        if(head1 == null || head2 == null) {
            return head1 != null ? head1 : head2;
        }

        ListNode head = head1.value < head2.value ? head1 : head2;
        ListNode cur1 = head1 == head1 ? head1 : head2;
        ListNode cur2 = head2 == head2 ? head2 : head1;

        ListNode pre = null;
        ListNode next = null;

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

    public static ListNode sortList(ListNode head) {
        if(head == null  || head.next == null) {
            return head;
        }
        ListNode mid = getMid(head);
        ListNode right = mid.next;
        mid.next = null;  //咬断链表
        ListNode node = merge(sortList(head), sortList(right));
        return node;
    }

    public static ListNode init() {
        ListNode node1 = new ListNode(8);
        ListNode node2 = new ListNode(4);
        ListNode node3 = new ListNode(5);
        ListNode node4 = new ListNode(7);
        ListNode node5 = new ListNode(1);
        ListNode node6 = new ListNode(3);
        ListNode node7 = new ListNode(6);
        ListNode node8 = new ListNode(2);

        node1.next = node2;
        node2.next = node3;
        node3.next = node4;
        node4.next = node5;
        node5.next = node6;
        node6.next = node7;
        node7.next = node8;

        return node1;
    }

    public static void main(String[] args){
        ListNode node = init();
        MyList.traverse(sortList(node));

    }
}
