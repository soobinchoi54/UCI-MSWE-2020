/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */
class Solution {
    public ListNode reverseList(ListNode head) {
        ListNode prev = null;
        ListNode current;
        ListNode temp; 
        
        current = head; 
        
        while (current != null) {
            temp = current.next;
            current.next = prev; 
            prev = current;
            current = temp;
        }
        
        return prev;
    }
}