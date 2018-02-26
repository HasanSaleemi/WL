/* WORD LADDER Main.java
 * EE422C Project 3 submission by
 * Replace <...> with your actual data.
 * Hasan Saleemi
 * has2375
 * Git URL: https://github.com/HasanSaleemi/WordLadder/
 * Fall 2018
 */

package assignment3;
import java.util.*;
import java.io.*;

public class Main {
	
	private static WordMap graph;
	
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

		while(true){
			ArrayList<String> words = parse(kb);
			if(words.size() == 0)
				break;
			printLadder(getWordLadderBFS(words.get(0), words.get(1)), ps);
		}
	}
	
	public static void initialize() {
		graph = new WordMap(makeDictionary());
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

	private static ArrayList<String> getBestOrder(String end, ArrayList<String> choices){
		ArrayList<String> copy = new ArrayList<>(choices);
		ArrayList<String> order = new ArrayList<>();

		while(!copy.isEmpty()){
			int minVal = Integer.MAX_VALUE;
			String theMin = null;

			for(String check : copy){
				int comp = 0;
				for(int i = 0; i < end.length(); i++){
					comp+=Math.abs((int)end.charAt(i) - (int)check.charAt(i));
				}
				if(comp < minVal){
					minVal = comp;
					theMin = check;
				}
			}
			copy.remove(theMin);
			order.add(theMin);
		}

		return order;
	}
	private static boolean DFS(String start, String end, ArrayList<String> visited, Map<String, String> path){
		visited.add(start);
		if(start.equals(end))
			return true;
		for(String edge : getBestOrder(end, graph.vertices.get(start))){
			if(!visited.contains(edge)){
				path.put(edge, start);
				if(DFS(edge, end, visited, path))
					return true;
			}
		}
		return false;
	}
	public static ArrayList<String> getWordLadderDFS(String start, String end) {
		start = start.toUpperCase(); end = end.toUpperCase();
		ArrayList<String> finalList = new ArrayList<>();

		if((graph.vertices.containsKey(start) && graph.vertices.containsKey(end)) && (start.length() == end.length())) {
			ArrayList<String> visited = new ArrayList<>();
			Map<String, String> path = new HashMap<>();

			path.put(start, null);
			DFS(start, end, visited, path);

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
	
    public static ArrayList<String> getWordLadderBFS(String start, String end) {
		start = start.toUpperCase();
		end = end.toUpperCase();
		ArrayList<String> finalList = new ArrayList<>();

		if ((graph.vertices.containsKey(start) && graph.vertices.containsKey(end)) && (start.length() == end.length())) {
			Queue<String> queue = new PriorityQueue<>();
			ArrayList<String> visited = new ArrayList<>();
			Map<String, String> path = new HashMap<>();

			path.put(start, null);
			queue.add(start);

			while (!queue.isEmpty()) {
				String parent = queue.remove();

				if (parent.equals(end))
					break;

				for (String child : graph.vertices.get(parent)) {
					if (!visited.contains(child)){
						if (!queue.contains(child)) {
							path.put(child, parent);
							queue.add(child);
						}
					}
				}
				visited.add(parent);
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
	
	private static void printLadder(ArrayList<String> ladder, PrintStream ps) {
		if(ladder.size() == 2){
			ps.println("no word ladder can be found between " + ladder.get(0) + " and " + ladder.get(1) + ".");
		} else {
			ps.println("a " + (ladder.size() - 2) + "-rung word ladder exists between " + ladder.get(0) + " and " + ladder.get(ladder.size() - 1) + ".");
			for(String rung : ladder)
				ps.println(rung);
		}
	}
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
