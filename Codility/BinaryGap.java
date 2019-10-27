// you can also use imports, for example:
// import java.util.*;

// you can write to stdout for debugging purposes, e.g.
// System.out.println("this is a debug message");
import java.util.ArrayList;
import java.util.List;


class Solution {
    public int solution(int N) {
        // write your code in Java SE 8
        
        // FIRST
        // Convert the integer to binary string
        String binString = Integer.toBinaryString(N);

        // SECOND 
        // Iterate through each character and save the index of 1 to list listOfOnes
        // although we can store all char of the string into a list
        // it is unnecessary as we only need the indices of 1 
        List<Integer> listOfOnes = new ArrayList<Integer>();
        
        for (int i = 0; i < binString.length(); i ++) {
            if (binString.charAt(i) == '0') continue;
            else listOfOnes.add(i);
        }
        
        // THIRD
        // Iterate through listOfOnes to find the difference between (i + 1) and i (given i = 0)
        // FOURTH
        // Store the difference in tempDiff
        // compare tempDiff with maxDiff and update if tempDiff > maxDiff 
        int maxDiff = 0;
        for (int i = 0; i < listOfOnes.size() - 1; i ++) {
            int tempDiff = ((listOfOnes.get(i+1) - listOfOnes.get(i)) - 1); 
            if (tempDiff > maxDiff) {
                maxDiff = tempDiff;
            }
        }
        
        return maxDiff;
        
    }
}