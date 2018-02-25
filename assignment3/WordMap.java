package assignment3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class WordMap {
    public Map<String, ArrayList<String>> vertices;

    public WordMap(Set<String> dict){
        vertices = new HashMap<>();
        for(String word : dict)
            vertices.put(word, getAllOffWords(dict, word));
    }

    public void printWordGraph(){
        Set<String> keys = vertices.keySet();
        for(String key : keys){
            System.out.println(key);
            for(String word : vertices.get(key))
                System.out.println("\t" + word);
        }
    }

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
    private static ArrayList<String> getAllOffWords(Set<String> dict, String original){
        ArrayList<String> list = new ArrayList<>();
        for(String compare : dict){
            if(checkOffByOne(original, compare))
                list.add(compare);
        }
        return list;
    }
}
