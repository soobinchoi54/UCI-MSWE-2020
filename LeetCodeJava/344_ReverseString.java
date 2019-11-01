/*
Time complexity: O(N) to swap N/2 elements 
Space complexity: O(1) since it's a constant space solution
*/

class Solution {
    public void reverseString(char[] s) {
        // Uses two pointers: left pointer and right pointer 
        int left = 0;
        int right = s.length - 1; 
        char temp; 
        // Until the pointers meet in the middle, keep swapping
        while (left < right) {
            temp = s[left];
            s[left] = s[right];
            s[right] = temp;
            left++;
            right--;
        }
    }
}