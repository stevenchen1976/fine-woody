package com.woody.framework.datastructure.list;

public class MyList {

    public static void main(String[] args){
        ListNode node1 = new ListNode(1);
        ListNode node2 = new ListNode(2);
        ListNode node3 = new ListNode(3);

        node1.next = node2;
        node2.next = node3;
        node3.next = null;
        //遍历
        traverse(node1);
        //头节点插入
        ListNode newhead = new ListNode(0);
        headInsert(node1, newhead);
        traverse(newhead);
        //尾节点插入
        ListNode newtail = new ListNode(4);
        tailInsert(node3, newtail);
        traverse(newhead);
        //查找
        System.out.println(find(newhead, 3));
        //插入
        ListNode node = new ListNode(5);
        insert(node3, node);
        traverse(newhead);
        //删除
        delete(newhead, node3);
        traverse(newhead);
    }

    //头节点插入
    public static void headInsert(ListNode head, ListNode newhead ) {
        ListNode old = head;
        head = newhead;
        head.next = old;
    }

    //尾节点的插入
    public static void tailInsert(ListNode tail, ListNode newtail) {
        ListNode old = tail;
        tail = newtail;
        tail.next = null;
        old.next = tail;
    }

    //遍历
    public static void traverse(ListNode head) {
        while (head != null) {
            System.out.print(head.value + " ");
            head = head.next;
        }
        System.out.println();
    }

    //查找
    public static int find(ListNode head, int value) {
        int index = -1;
        int count = 0;
        while (head != null) {
            if(head.value == value) {
                index = count;
                return count;
            }
            count ++;
            head = head.next;
        }
        return index;
    }

    //插入
    public static void insert(ListNode p, ListNode s) {
        ListNode next = p.next;
        p.next = s;
        s.next = next;
    }

    //删除
    public static void delete(ListNode head, ListNode p) {
        if(p != null && p.next != null) {
            ListNode q = p.next;
            p.value = q.value;
            p.next = q.next;
            q = null;
        }
        //删除最后一个元素
        if(p.next == null) {
            while (head != null) {
                if(head.next != null && head.next == p) {
                    head.next = null;
                    break;
                }
                head = head.next;
            }
        }
    }
}
