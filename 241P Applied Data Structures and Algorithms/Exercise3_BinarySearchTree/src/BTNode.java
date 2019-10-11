public class BTNode {

    String value;
    BTNode left;
    BTNode right;

    public BTNode(String value) { // constructor where we pass the data
        this.value = value;
        left = right = null;
    }

    public BTNode() { // constructor where we pass the data
        this.value = null;
        left = right = null;
    }
}
