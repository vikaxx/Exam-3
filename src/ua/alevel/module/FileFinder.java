package ua.alevel.module;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FileFinder {
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        System.out.println("example of queries:");
        System.out.println("for directory searching: find -d module");
        System.out.println("for file searching: " +
                "find -f 1.txt C:\\Users\\User\\IdeaProjects\\exam-3\\src");
        System.out.println("for keyword searching: find -k lalala");
        System.out.println("for size searching: find -s 0-15");
        System.out.println("\ninput search query. 'quit' to exit program.");

        ExecutorService executor = Executors.newSingleThreadExecutor();
        String query;
        while (!(query = scanner.nextLine()).equals("quit")) {
            ArrayList<String> queryWords = nextQuery(query);
            executor.submit(new QueryProcessing(queryWords));
        }
        executor.shutdown();


    }

    private static ArrayList<String> nextQuery(String query) {
        String[] words = query.split(" ");
        return new ArrayList<>(Arrays.asList(words));
    }
}
