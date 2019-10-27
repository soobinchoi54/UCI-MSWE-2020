import java.util.Arrays;
class Solution {
    public int solution(int[] A) {
        // write your code in Java SE 8
                // Given int array A, sort array
        Arrays.sort(A);
        // Then, loop to check if currentVal i == nextVal i + 1
            // if true, continue, else, increment distinct
        int n = A.length;
        int distinct = 1;
        
        if (n == 0) {
            distinct = 0;
        }
  
        for (int i = 0; i < n-1; i ++) {
            if (A[i] == A[i+1]) continue;
            distinct++;
        }
        // return distinct
        return distinct;
    }
}

// TAKEAWAY
// Make sure to test all cases, including when the array is empty, which means there are no distinct values and should return 0
