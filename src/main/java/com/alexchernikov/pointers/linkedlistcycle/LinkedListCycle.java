package com.alexchernikov.pointers.linkedlistcycle;

public class LinkedListCycle {

    public static void main(String[] args) {
        // [3,2,0,-4]
        ListNode node4 = new ListNode(-4);
        ListNode node3 = new ListNode(0, node4);
        ListNode node2 = new ListNode(2, node3);
        ListNode node1 = new ListNode(3, node2);
        node4.next = node2; // create cycle
        LinkedListCycle solution = new LinkedListCycle();
        boolean result = solution.hasCycle(node1);
        System.out.println(result); // Expected output: true

        ListNode cycle = solution.detectCycle(node1);
        System.out.println(cycle != null ? cycle.val : "No cycle"); // Expected output
    }

    static class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
            next = null;
        }

        ListNode(int x, ListNode next) {
            val = x;
            this.next = next;
        }
    }

    public boolean hasCycle(ListNode head) {
        if (head == null || head.next == null) {
            return false;
        }

        ListNode slow = head;
        ListNode fast = head.next;

        while (fast != null && fast.next != null) {
            if (slow == fast) {
                return true;
            }
            slow = slow.next;
            fast = fast.next.next;
        }

        return false;
    }

    public ListNode detectCycle(ListNode head) {
        if (head == null || head.next == null) {
            return null;
        }

        ListNode slow = head;
        ListNode fast = head;

        // Detect cycle
        do {
            if (fast == null || fast.next == null) {
                return null; // No cycle
            }
            slow = slow.next;
            fast = fast.next.next;
        } while (slow != fast);

        // Find the entry point of the cycle
        slow = head;
        while (slow != fast) {
            slow = slow.next;
            fast = fast.next;
        }

        return slow; // Entry point of the cycle
    }
}
