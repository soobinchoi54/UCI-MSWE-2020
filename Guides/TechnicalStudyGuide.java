// LEET CODE CHEATSHEET 

// INITIALIZATION 

	// a String 
	String string = new String();

	// an Array
	String [] a = new String {"value1", "value2", "value3"}; // with values
	String [] a = new String[4]; // with size

	// a List
	List a = new ArrayList();
	List b = new LinkedList();
	List c = new Vector();
	List d = new Stack();

	List<Integer> list = new ArrayList<Integer>();
		// to add
		list.add(i);
	List<String> list = Arrays.asList("item1", "item2", "item3"); // with values

	// Hash Set
	HashSet<Integer> set = new HashSet<Integer>();
		// to add
		set.add(i);

// FILL

	// an Array
	int[] a = new int[4];
	Arrays.fill(a,0);

// SORT

	// an Array
	Arrays.sort(a, 2, 6); // sort from startIndex to endIndex
	Arrays.sort(a, 2); // sort from startIndex to end of Array

	// an ArrayList 
	ArrayList<Integer> list = new ArrayList<String>; // import java.util.Arrays
	Collections.sort(list);

// FIND/GET/RETRIEVE

	// Element i from a list 
	list.get(i);

	// Last Element of a Linked List 
	linkedList.getLast();

	// Keys from a Map
	map.keySet();
	map.keySet().toArray();

	// Min in an Array of Primitives
	int[] a = {12, 1, 21, 8};
	int min = Arrays.stream(tab).min().getAsInt();

	// Max in an Array of Primitives
	int max = Arrays.stream(tab).max().getAsInt();

	// Retrieve Character in a String
	char c = string.charAt(i);

	// SIZE & LENGTH

		// Length of String 
		string.length();

		// Size of an Array 
		a.length();

		// Size of an ArrayList 
		list.size();

		// Size of a HashSet
		set.size();

		//

// BOOLEAN

	// Contains

		// if a set contains
		set.contains(i);
		!set.contains(i);

// REMOVE

	// Last Element from a list
	list.remove(list.size() - 1);

// CONVERT

	// List to Int[]
	int[] ints list.stream().mapToInt(Integer::intValue).toArray(); // Source : https://stackoverflow.com/questions/960431/how-to-convert-listinteger-to-int-in-java

	// int[] to List
	int[] a = {1, 2, 3};
	List<Integer> list = Arrays.stream(ints).boxed().collect(Collectors.toList());

	// Integer to Binary String
	String binaryString = Integer.toBinaryString(N);

// TREE

	// Find the number of nodes along the longest path from the root noode down the farthest leaf node; a leaf is a node with no children
	int depthLeft = maxDepth(node.left) + 1;
	int depthRight = maxDepth(node.right) + 1;

	// Find the minimum depth 
	int depthLeft = minDepth(node.left);
	int depthRight = findMinDepth(node.right);

// PRIORITY QUEUE

	PriorityQueue<Map.Entry<String, Integer>> pq = new PriorityQueue<>((a,b) -> a.getValue()==b.getValue() ? b.getKey().compareTo(a.getKey()) : a.getValue()-b.getValue());

	// Priority Queue API
	// Boolean

	// Insert specified element into pq
	offer(E e); // E is the type of elements held in this collection
	add(E e);

	// Retrieves, but doesn't remove, the head of this queue, or returns null if the queue is empty
	peek();

	// Retrieves and removes the head of this queue, or returns null if this queue is empty
	poll();

	// Removes a single instance of the specified element from the queue, if present 
	remove(Object o);

// MATH 

	// Return the GREATER of the two integer values
	Math.max(50, 500);

	// Return the power of two values
	Math.pow(2, 10); // 2^10 

	// Return the log of value N
	Math.log(N);

// STATEMENTS

	continue;
	// used in any of the loop control structures
	// causes loop to immediately jump to next iteration of the loop 
		// for loop: continue keyword causes control to imeediately jump to the update statement 
		// while loop: control imeediately jumps to the boolean expression

// EXPRESSIONS 

	// For each integer i an array called nums 
	for (int i : nums) 

// Java.lang Package classes

	// Java.lang - Integer 
		// Declaration for java.lang.Integer class
		public final class Integer extends Number
			implements Comparable<Integer>

			// Fields
			static int MAX_VALUE // constant holding the maximum value an int can have
			static int MIN_VALUE // constant holding the minimum value an int can have
			static int SIZE // number of bits used to represent an int value in two's complement binary form 
			static Class<Integer> TYPE // class instance representing the primitive type int

// ERRORS

	// Compiler error

		// CASE 1: Cannot find symbol
			// Solution.java:11: error: cannot find symbol
		 	// int n = a.length();
			// symbol:   method length()
		 	// location: variable a of type int[]

			// Arrays don't have a length() method, they have a length public member




