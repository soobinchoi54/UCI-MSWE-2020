import java.util.Arrays;

public class HashTableSet {

    HashNode temp;
    HashNode head;

    private int size; // counter
    private int capacity; // total capacity
    private int pseudo;
    public HashNode[] ht;

    // create hash table with initial size of 64 (default)
    public HashTableSet() {
        capacity = 64;
        ht = new HashNode[capacity];
        Arrays.fill(ht, new HashNode("dummy"));
    }

    public int convert(String realKey) {
        char[] array = realKey.toCharArray();
        int sum = 0, pseudo;
        for (char c : array) {
            sum += (int) c;
        }
        pseudo = sum % capacity;
        return pseudo;
    }

    public boolean add(String value) {
        if (contains(value)) return false;
        else {
            head.next = new HashNode(value);
            size++;
        }
        return true;
    }

    public boolean contains(String value) {
        int index = convert(value);
        // System.out.println(index);
        head = ht[index];
        while (head.next != null) {
            head = head.next;
            if (head.value.equals(value)) return true;
        }
        return false;
    }

    public int size() {
        return this.size;
    }


}
