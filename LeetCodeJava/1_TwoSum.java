public class TwoSum {
    public int[] twoSum(int[] nums, int target) {
        // Brute Force method
        int sum = 0; // sum of two integers from array
        int sol = 0; // count of possible number of solutions for target
        int first = 0;
        int second = 0;
        int[] solArr = new int[2];
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                sum = nums[i] + nums[j];
                if (sum == target) {
                    sol++;
                    first = i;
                    second = j;
                }
            }
        }
        if (sol == 1) {
            solArr = new int[]{first, second};
        }
        return solArr;
    }
}
