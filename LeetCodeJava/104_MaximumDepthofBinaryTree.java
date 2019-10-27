/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
class Solution {
    public int maxDepth(TreeNode root) {
        TreeNode head;
        int depth;
        
        if (root == null) {
            return 0;
        } else {
            int depthL = maxDepth(root.left); 
            int depthR = maxDepth(root.right);
            depth = Math.max(depthL, depthR) + 1;
        }
        return depth;
    }
}