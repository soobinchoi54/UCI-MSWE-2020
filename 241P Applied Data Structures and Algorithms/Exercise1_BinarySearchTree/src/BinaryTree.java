public class BinaryTree {

    BTNode temp;
    BTNode root;
    BTNode parent;
    private int size;

    // constructor
    public BinaryTree() {
        root = new BTNode("dummy");
    }

    // add boolean method

    public boolean add(String value) {
        if (root.value == "dummy") {
            root.value = value;
            size++;
            return true;
        }
        if (contains(value)) {
            return false;
        } else {
            if (parent.value.compareTo(value) < 0) {
                parent.right = new BTNode(value);
            } else {
                parent.left = new BTNode(value);
            }
            size++;
        }
        return true;
    }


    public boolean contains(String value) {
        temp = root;
        while (temp != null) {
            if (temp.value.equals(value)) { // while temp is not null and if root data does equal new value
                return true;
            } else if (temp.value.compareTo(value) < 0) {
                parent = temp;
                temp = temp.right;
            } else {
                parent = temp;
                temp = temp.left;
            }
        }
        return false;
    }

    public int size() {
        return size;
    }

}
