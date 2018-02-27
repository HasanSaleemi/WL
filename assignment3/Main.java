/* WORD LADDER Main.java
 * EE422C Project 3 submission by
 * Replace <...> with your actual data.
 * Hasan Saleemi
 * has2375
 * Git URL: https://github.com/HasanSaleemi/WL/
 * Fall 2018
 */

package assignment3;
import java.util.*;
import java.io.*;

public class Main {
	/**
	 * All vertices of the dictionary graph.
	 */
	private static Map<String, ArrayList<String>> vertices;

	public static void main(String[] args) throws Exception {
		Scanner kb;
		PrintStream ps;
		if (args.length != 0) {
			kb = new Scanner(new File(args[0]));
			ps = new PrintStream(new File(args[1]));
			System.setOut(ps);
		} else {
			kb = new Scanner(System.in);
			ps = System.out;
		}
		initialize();

		int maxVert = Integer.MIN_VALUE;
		String max = "";

		Set<String> verts = vertices.keySet();
		for(String vert : verts){
			if(vertices.get(vert).size() > maxVert){
				maxVert = vertices.get(vert).size();
				max = vert;
			}
		}

		System.out.println(max + " " + maxVert);

		while(true){
			ArrayList<String> words = parse(kb);
			if(words.size() == 0)
				break;
			printLadder(getWordLadderBFS(words.get(0), words.get(1)), ps);
			printLadder(getWordLadderDFS(words.get(0), words.get(1)), ps);
		}
	}

	/**
	 * Check if a word is off by one letter from another word.
	 * @param original The original word.
	 * @param compare The word to compare to.
	 * @return Boolean whether the word is off by one or not
	 */
	private static boolean checkOffByOne(String original, String compare){
		if(original.length() != compare.length())
			return false;
		int diff = 0;

		for(int i = 0; i < original.length(); i++){
			if(original.charAt(i) != compare.charAt(i))
				diff++;
			if(diff > 1)
				return false;
		}
		return diff == 1;
	}

	/**
	 * Get an ArrayList of words from the dictionary that are one off a word.
	 * @param dict The dictionary.
	 * @param original The word to compare to.
	 * @return ArrayList of words that are one off.
	 */
	private static ArrayList<String> getAllOffWords(Set<String> dict, String original){
		ArrayList<String> list = new ArrayList<>();
		for(String compare : dict){
			if(checkOffByOne(original, compare))
				list.add(compare);
		}
		return list;
	}

	/**
	 * Creates the dictionary graph.
	 */
	public static void initialize() {
		Set<String> dict = makeDictionary();
		vertices = new HashMap<>();
		for(String word : dict)
			vertices.put(word, getAllOffWords(dict, word));
	}
	
	/**
	 * @param keyboard Scanner connected to System.in
	 * @return ArrayList of Strings containing start word and end word. 
	 * If command is /quit, return empty ArrayList. 
	 */
	public static ArrayList<String> parse(Scanner keyboard) {
		ArrayList<String> commands = new ArrayList<>();
		int numCmds = 0;

		while(keyboard.hasNext()){
			String nextCmd = keyboard.next();
			if(nextCmd.equals("/quit")){
				return new ArrayList<>();
			} else {
				commands.add(nextCmd);
				numCmds++;
			}
			if(numCmds == 2)
				break;
		}
		return commands;
	}

	/**
	 * Sorts an ArrayList of edges based on how close they are to the target word.
	 * @param end The target word.
	 * @param choices ArrayList of edges.
	 * @return Sorted ArrayList.
	 */
	private static ArrayList<String> getBestOrder(String end, ArrayList<String> choices){
		ArrayList<String> copy = new ArrayList<>(choices);
		ArrayList<String> order = new ArrayList<>();

		while(!copy.isEmpty()){
			int minVal = Integer.MAX_VALUE;
			String theMin = null;

			for(String check : copy){
				int comp = 0;
				int diff = 0;

				for(int i = 0; i < end.length(); i++){
					if(end.charAt(i) != check.charAt(i))
						diff++;
					comp+=Math.abs((int)end.charAt(i) - (int)check.charAt(i)); // compares all chars and gets a standardised value.
				}
				int combined = diff * 8 + comp * 2;
				if(combined < minVal){
					minVal = combined;
					theMin = check;
				}
			}
			copy.remove(theMin);
			order.add(theMin);
		}

		return order;
	}
	/**
	 * Recursive Depth First Search helper function.
	 * @param start Current parent node.
	 * @param end Target word.
	 * @param visited List of visited words.
	 * @param path Path information.
	 * @return Found the word or not.
	 */
	private static boolean DFS(String start, String end, ArrayList<String> visited, Map<String, String> path){
		visited.add(start);
		if(start.equals(end))
			return true;
		for(String edge : getBestOrder(end, vertices.get(start))){
			if(!visited.contains(edge)){
				path.put(edge, start);
				if(DFS(edge, end, visited, path))
					return true;
			}
		}
		return false;
	}
	/**
	 * Uses a smart Depth First Search algorithm to get a word ladder.
	 * @param start Beginning word.
	 * @param end Target word.
	 * @return The word ladder.
	 * If no ladder exists, will just return the start and end word in a list.
	 */
	public static ArrayList<String> getWordLadderDFS(String start, String end) {
		start = start.toUpperCase(); end = end.toUpperCase();
		ArrayList<String> finalList = new ArrayList<>();

		if((vertices.containsKey(start) && vertices.containsKey(end)) && (start.length() == end.length())) {
			ArrayList<String> visited = new ArrayList<>();
			Map<String, String> path = new HashMap<>();

			path.put(start, null);
			try{
				DFS(start, end, visited, path);
			} catch(StackOverflowError ignored){} // assume stack overflows mean no ladder found.

			while(true){
				String connect = path.get(end);
				if(connect != null){
					finalList.add(0, end.toLowerCase());
					end = connect;
				} else {
					break;
				}
			}
			if(finalList.size() == 0)
				finalList.add(end.toLowerCase());
		} else {
			finalList.add(end.toLowerCase());
		}
		finalList.add(0, start.toLowerCase());

		return finalList;
	}

	/**
	 * Uses a standard Breadth First Search algorithm to get a word ladder.
	 * @param start Beginning word.
	 * @param end Target word.
	 * @return The word ladder.
	 * If no ladder exists, will just return the start and end word in a list.
	 */
    public static ArrayList<String> getWordLadderBFS(String start, String end) {
		start = start.toUpperCase();
		end = end.toUpperCase();
		ArrayList<String> finalList = new ArrayList<>();

		if ((vertices.containsKey(start) && vertices.containsKey(end)) && (start.length() == end.length())) {
			Queue<String> queue = new LinkedList<>();
			ArrayList<String> visited = new ArrayList<>();
			Map<String, String> path = new HashMap<>();

			visited.add(start);
			path.put(start, null);
			queue.add(start);

			while (!queue.isEmpty()) {
				String parent = queue.poll();

				if (parent.equals(end))
					break;

				for (String child : vertices.get(parent)) {
					if (!visited.contains(child)){
						visited.add(child);
						path.put(child, parent);
						queue.add(child);
					}
				}
			}
			while (true) {
				String connect = path.get(end);
				if (connect != null) {
					finalList.add(0, end.toLowerCase());
					end = connect;
				} else {
					break;
				}
			}
			if (finalList.size() == 0)
				finalList.add(end.toLowerCase());
		} else {
			finalList.add(end.toLowerCase());
		}
		finalList.add(0, start.toLowerCase());

		return finalList;
	}

	/**
	 * Prints out a word ladder.
	 * @param ladder The ladder.
	 * @param ps The PrintStream to output to.
	 */
	private static void printLadder(ArrayList<String> ladder, PrintStream ps) {
		if(ladder.size() == 2){
			ps.println("no word ladder can be found between " + ladder.get(0) + " and " + ladder.get(1) + ".");
		} else {
			ps.println("a " + (ladder.size() - 2) + "-rung word ladder exists between " + ladder.get(0) + " and " + ladder.get(ladder.size() - 1) + ".");
			for(String rung : ladder)
				ps.println(rung);
		}
	}
	/**
	 * Prints out a word ladder to System.out.
	 * @param ladder The ladder.
	 */
	public static void printLadder(ArrayList<String> ladder) {
		printLadder(ladder, System.out);
	}

	/* Do not modify makeDictionary */
	public static Set<String>  makeDictionary () {
		Set<String> words = new HashSet<String>();
		Scanner infile = null;
		try {
			infile = new Scanner (new File("five_letter_words.txt"));
		} catch (FileNotFoundException e) {
			System.out.println("Dictionary File not Found!");
			e.printStackTrace();
			System.exit(1);
		}
		while (infile.hasNext()) {
			words.add(infile.next().toUpperCase());
		}
		return words;
	}
}
