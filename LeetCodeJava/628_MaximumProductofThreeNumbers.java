class Solution {
    public int maximumProduct(int[] nums) {
        
        // FIRST, sort int[] nums from smallest to largest 
        Arrays.sort(nums);
        
        // SECOND, find product of the last three integers 
        int n = nums.length;
        int max1 = nums[0] * nums[1] * nums[n-1];
        int max2 = nums[n-1] * nums[n-2] * nums[n-3];

        // Return product
        return Math.max(max1, max2);
        
        
    }
}