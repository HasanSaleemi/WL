/* WORD LADDER Main.java
 * EE422C Project 3 submission by
 * Replace <...> with your actual data.
 * Hasan Saleemi
 * has2375
 * Git URL:
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
	
	public static ArrayList<String> getWordLadderDFS(String start, String end) {
		Set<String> dict = makeDictionary();

		return null;
	}
	
    public static ArrayList<String> getWordLadderBFS(String start, String end) {
		Set<String> dict = makeDictionary();

		return null;
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
