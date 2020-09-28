package ua.alevel.module;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class QueryProcessing implements Runnable {
    private String fileName;
    private String command;
    private String filePath;
    private File file;
    private boolean isDirectory = false;
    private ArrayList<String> foundedFiles = new ArrayList<>();
    private long from;
    private long to;
    private String word;


    public QueryProcessing() {
    }

    public QueryProcessing(ArrayList<String> queryWords) {
        this.command = queryWords.get(1);
        this.fileName = queryWords.get(2);
        queryWords.remove(0);
        queryWords.remove(0);
        queryWords.remove(0);
        if (!queryWords.isEmpty()) {
            this.filePath = queryWords.get(0);
        } else
            this.filePath = "C:\\Users\\User\\IdeaProjects\\exam-3\\src";
        if (command.equals("-k")) this.word = fileName;
        if (command.equals("-s")) {
            String[] fromTo = this.fileName.split("-");
            this.from = Long.parseLong(fromTo[0]);
            this.to = Long.parseLong(fromTo[1]);
        }
    }


    @Override
    public void run() {
        this.file = new File(filePath);

        switch (command) {
            case "-f":
                this.isDirectory = false;
                foundedFile();
                if (foundedFiles.isEmpty()) System.out.println("no such file");
                else foundedFiles.forEach(System.out::println);
                break;
            case "-d":
                this.isDirectory = true;
                foundedFile();
                if (foundedFiles.isEmpty()) System.out.println("no such directory");
                else foundedFiles.forEach(System.out::println);
                break;
            case "-k":
                match();
                if (foundedFiles.isEmpty()) System.out.println("no files");
                else foundedFiles.forEach(System.out::println);
                break;
            case "-s":
                fromTo();
                if (foundedFiles.isEmpty()) System.out.println("no files");
                else foundedFiles.forEach(System.out::println);
                break;
            default:
                System.out.println("error. incorrect query. 'quit' to exit program");
                System.out.println("example of queries:");
                System.out.println("for file searching: find -d module");
                System.out.println("for directory searching: " +
                        "find -f 1.txt C:\\Users\\User\\IdeaProjects\\exam-3\\src");
                System.out.println("for keyword searching: find -k lalala");
                System.out.println("for size searching: find -s 0-15");
        }
    }

    private void foundedFile() {
        for (File currentFile : Objects.requireNonNull(this.file.listFiles())) {
            try {
                this.file = currentFile;
                foundedFile();
            } catch (NullPointerException e) {
            }
            if (currentFile.getName().equals(this.fileName)) {
                if (this.isDirectory && currentFile.isDirectory())
                    this.foundedFiles.add(currentFile.getAbsolutePath());

                if (!this.isDirectory && !currentFile.isDirectory())
                    this.foundedFiles.add(currentFile.getAbsolutePath());
            }
        }
    }

    private void match() {
        for (File currentFile : Objects.requireNonNull(this.file.listFiles())) {
            try {
                this.file = currentFile;
                match();
            } catch (NullPointerException e) {
            }
            if (currentFile.isFile()) {
                try {
                    boolean isContains = Files
                            .lines(Paths.get(currentFile.getAbsolutePath()))
                            .anyMatch(e -> e.contains(word));

                    if (isContains)
                        this.foundedFiles.add(currentFile.getAbsolutePath());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void fromTo() {
        for (File currentFile : Objects.requireNonNull(this.file.listFiles())) {
            try {
                this.file = currentFile;
                fromTo();
            } catch (NullPointerException e) {
            }
            if (currentFile.isFile()) {
                try {
                    if ((int)currentFile.length() > (int)from && (int)currentFile.length() < (int)to)
                        this.foundedFiles.add(currentFile.getAbsolutePath());
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
    }

}

