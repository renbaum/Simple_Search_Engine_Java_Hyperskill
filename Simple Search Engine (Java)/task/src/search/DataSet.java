package search;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

abstract class SearchStrategy{
    protected Map<String, List<Integer>> map;

    public SearchStrategy(Map<String, List<Integer>> map){
        this.map = map;
    }

    abstract public Set<Integer> search(String[] patterns);
}

class SearchStrategyAny extends SearchStrategy{
    public SearchStrategyAny(Map<String, List<Integer>> map){
        super(map);
    }
    @Override
    public Set<Integer> search(String[] patterns) {
        Set<Integer> set = new HashSet<>();

        for (String pattern : patterns) {
            if(map.get(pattern.toLowerCase()) != null){
                set.addAll(map.get(pattern));
            }
        }
        if(set.size() == 0){return null;}

        return set;
    }
}

class SearchStrategyAll extends SearchStrategy{
    public SearchStrategyAll(Map<String, List<Integer>> map){
        super(map);
    }
    @Override
    public Set<Integer> search(String[] patterns) {
        Set<Integer> set = null;

        for (String pattern : patterns) {
            if(map.get(pattern.toLowerCase()) != null){
                if(set == null){
                    set = new HashSet<>(map.get(pattern));
                }else {
                    set.retainAll(map.get(pattern));

                }
            }
        }

        return set;
    }
}

class SearchStrategyNone extends SearchStrategy{
    public SearchStrategyNone(Map<String, List<Integer>> map){
        super(map);
    }
    @Override
    public Set<Integer> search(String[] patterns) {
        Set<Integer> set = new HashSet<>();
        for(List<Integer> list : map.values()){
            set.addAll(list);
        }

        for (String pattern : patterns) {
            List<Integer> list = map.get(pattern.toLowerCase());
            if(list != null){
                set.removeAll(list);
            }
        }

        if(set.size() == 0){return null;}
        return set;
    }
}


public class DataSet{
    private List<String> list;
    private Scanner sc;
    private Map<String, List<Integer>> map;

    private void Initialize(){
        sc = new Scanner(System.in);
        list = new ArrayList<>();
        map = new HashMap<>();
    }

    public DataSet(){
        Initialize();
        System.out.println("Enter the number of people:");
        int numberOfLines = Integer.parseInt(sc.nextLine());
        System.out.println("Enter all people:");
        for(int i = 1; i <= numberOfLines; i++){
            add(sc.nextLine());
        }
    }

    public DataSet(String filename){
        Initialize();
        try {
            File file = new File(filename);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                add(scanner.nextLine());
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }
    }

    public void add(String line){
        list.add(line);
        String[] words = line.split(" ");
        for (String word : words) {
            word = word.toLowerCase();
            if (map.containsKey(word)) {
                map.get(word).add(list.size() - 1);
            } else {
                List<Integer> indexes = new ArrayList<>();
                indexes.add(list.size() - 1);
                map.put(word, indexes);
            }
        }
    }

    public void printAll(){
        System.out.println("=== List of people ===");
        for (String person : list) {
            System.out.println(person);
        }
    }

    public void search(){
        System.out.println("Select a matching strategy: ALL, ANY, NONE");
        String strategy = sc.nextLine().toUpperCase();
        SearchStrategy searchStrategy;
        switch (strategy) {
            case "ALL":
                searchStrategy = new SearchStrategyAll(map);
                break;
            case "ANY":
                searchStrategy = new SearchStrategyAny(map);
                break;
            case "NONE":
                searchStrategy = new SearchStrategyNone(map);
                break;
            default:
                System.out.println("Wrong strategy");
                return;
                //searchStrategy = new SearchStrategyAny(map);
                //break;
        }
        System.out.println();
        System.out.println("Enter a name or email to search all suitable people.");
        String names = sc.nextLine().toLowerCase();

        Set<Integer> indexes = searchStrategy.search(names.split(" "));
        if (indexes != null) {
            System.out.printf("%d persons found:\n", indexes.size());
            for (int index : indexes) {
                System.out.println(list.get(index));
            }
        }else{
            System.out.println("No matching people found.");
        }

/*        for (String person : list) {
            if (person.toLowerCase().contains(name)) {
                System.out.println(person);
            }
        }*/
    }
}
