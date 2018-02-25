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

		graph.printWordGraph();
		// TODO methods to read in words, output ladder
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
		// TO DO
		return null;
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
