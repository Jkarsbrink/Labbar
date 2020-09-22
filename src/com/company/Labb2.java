package com.company;

import java.io.File;
import java.util.Scanner;

public class Labb2 {

    public static boolean hasFoundWord = false;

    public static void main(String[] args) {
        File startingFolder = new File("C:\\GitHub\\Labbarbete\\testData");

        Scanner sc = new Scanner(System.in);
        System.out.println("Vilket ord ska vi söka på?");
        String word = sc.next();

        findFiles(startingFolder, word);
        if (hasFoundWord != true) {
            System.out.println("Can't find it! ");

        }

    }

    public static void findFiles(File file, String word) {
        if (file.isFile()) {

            printPath(file, word);
        } else if (file.isDirectory()) {
            try {
                File[] folderContents = file.listFiles();
                for (File f : folderContents) {

                    findFiles(f, word);
                }
            } catch (Exception e) {
                System.err.println(file.getAbsolutePath() + " Can't read");
            }
        }

    }

    public static void printPath(File file, String word) {
        try {
            Scanner sc = new Scanner(file);

            String token;
            while (sc.hasNext()) {

                token = (sc.next());

                if (token.contains(word)) {
                    System.out.println(file.getAbsolutePath());

                    hasFoundWord = true;
                }
            }
            sc.close();
        } catch (Exception e) {
            System.err.println(file.getAbsolutePath() + " Can't read");
        }
    }

}