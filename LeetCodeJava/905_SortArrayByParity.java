class Solution {
    public int[] sortArrayByParity(int[] A) {
        int[] newA = new int[A.length];
        int t = 0;
        for (int i = 0; i < A.length; i++) {
            if (A[i] % 2 == 0) {
                newA[t] = A[i];
                t++;
            } 
        }
        for (int i = 0; i < A.length; i++) {
            if (A[i] % 2 == 1) {
                newA[t] = A[i];
                t++;
            }
        }
        return newA;
    }
}