package ua.alevel.module;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FileFinder {
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

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
