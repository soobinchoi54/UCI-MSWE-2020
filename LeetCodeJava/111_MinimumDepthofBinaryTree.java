class Solution {
    public int minDepth(TreeNode root) {
        if (root == null) { // if the tree is empty, return 0
            return 0;
        }
    
        return findMinDepth(root); // else, call findMinDepth method
        }
    
    private int findMinDepth(TreeNode root) {
        if (root == null) { 
            return Integer.MAX_VALUE;
        }
                
        if (root.left == null && root.right == null) {
            return 1;
        }
        
        return Math.min(findMinDepth(root.left), findMinDepth(root.right)) + 1;
    }
}