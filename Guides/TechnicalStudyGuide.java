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


// FILL

	// an Array
	int[] a = new int[4];
	Arrays.fill(a,0);

// SORT

	// an Array
	Arrays.sort(a, 2, 6); // sort from startIndex to endIndex
	Arrays.sort(a, 2); // sort from startIndex to end of Array

	// an ArrayList 
	ArrayList<Integer> list = new ArrayList<String>;
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
	int max = ARrays.stream(tab).max().getAsInt();

	// Retrieve Character in a String
	char c = string.charAt(i);

	// SIZE & LENGTH
	
		// Length of String 
		string.length();

		// Size of an Array 
		a.length();

		// Size of an ArrayList 
		list.size();

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

// STATEMENTS

	continue;
	// used in any of the loop control structures
	// causes loop to immediately jump to next iteration of the loop 
		// for loop: continue keyword causes control to imeediately jump to the update statement 
		// while loop: control imeediately jumps to the boolean expression

// ERRORS

	// Compiler error

		// CASE 1: Cannot find symbol
			// Solution.java:11: error: cannot find symbol
		 	// int n = a.length();
			// symbol:   method length()
		 	// location: variable a of type int[]

			// Arrays don't have a length() method, they have a length public member




