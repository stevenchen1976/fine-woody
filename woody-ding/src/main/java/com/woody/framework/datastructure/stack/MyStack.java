package com.woody.framework.datastructure.stack;

import com.woody.framework.datastructure.list.ListNode;

public class MyStack {

    public ListNode stackTop;
    public ListNode stackBootom;

    public MyStack(ListNode stackTop, ListNode stackBootom) {
        this.stackTop = stackTop;
        this.stackBootom = stackBootom;
    }

    //进栈
    public static void pushStack(MyStack myStack, int value) {
        ListNode node = new ListNode(value);
        node.next = myStack.stackTop;
        myStack.stackTop = node;
    }

    /*遍历
     * 栈顶元素指针不指向栈底
     */
    public static void traverse(MyStack myStack) {
        ListNode stackTop = myStack.stackTop;
        while (stackTop != myStack.stackBootom) {
            System.out.print(stackTop.value + " ");
            stackTop = stackTop.next;
        }
        System.out.println();
    }

    //判断栈是否为空
    public static boolean isEmpty(MyStack myStack) {
        if (myStack.stackTop == myStack.stackBootom) {
            return true;
        } else {
            return false;
        }
    }

    //出栈
    public static void popStack(MyStack myStack) {
        if (!isEmpty(myStack)) {
            ListNode stackTop = myStack.stackTop;
            myStack.stackTop = stackTop.next;
            System.out.println(stackTop.value);
        }
    }

    //清空栈
    public static void clearStack(MyStack myStack) {
        myStack.stackTop = null;
        myStack.stackBootom = myStack.stackTop;
    }

    public static void main(String[] args) {
        MyStack myStack = new MyStack(new ListNode(0), new ListNode(0));
        myStack.stackBootom = myStack.stackTop;

        System.out.println(isEmpty(myStack));
        pushStack(myStack, 1);
        pushStack(myStack, 2);
        pushStack(myStack, 3);
        traverse(myStack);
        System.out.println(isEmpty(myStack));
        popStack(myStack);
        clearStack(myStack);
        System.out.println(isEmpty(myStack));
    }
}
