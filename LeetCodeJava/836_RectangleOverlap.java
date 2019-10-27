class Solution {
    public boolean isRectangleOverlap(int[] rec1, int[] rec2) {
        if (rec2[0] >= rec1[2] || rec1[0] >= rec2[2]) return false; // to either left or right
        if (rec2[1] >= rec1[3] || rec1[1] >= rec2[3]) return false; // to either above or below
        return true;
    }
}