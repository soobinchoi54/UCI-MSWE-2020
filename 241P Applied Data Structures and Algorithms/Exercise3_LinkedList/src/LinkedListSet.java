public class LinkedListSet {

    LLNode temp;
    LLNode head;
    private int size = 0;

    // constructor
    public LinkedListSet() {
        head = new LLNode("dummy");
    }

    public boolean add(String value) {
        if (!contains(value)) {
            temp.next = new LLNode (value);
            size++;
            return true;
        }
        return false;
    }

    public boolean contains(String value) {
        temp = head;
        while (temp.next != null) {
            temp = temp.next;
            if (temp.value.equals(value)) {
                return true;
            }
        }
        return false;
    }

    public int size() {
        return this.size;
    }

    /*
    public void printList() {
        LLNode temp = head;
        while (temp != null) {
            System.out.println(temp.value);
            temp = temp.next;
        }
    }
    */

}
