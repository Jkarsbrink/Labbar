package com.company.lab6;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

public class Hangman {
    public static void main(String[] args) {
        Hangman hm = new Hangman();
        hm.gameStarter();
    }

    public void gameStarter() {
        int guess = 0;
        int guessCount = 5;
        String guessedChar;
        welcomeWords();
        Scanner scan = new Scanner(System.in);
        String triedChar = "Gissat på: ";
        String secretword = null;
        try {
            secretword = randomWordFromFile("testData/Words.txt").toUpperCase();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
        String secretword_changed = secretword.toLowerCase().replaceAll(".", "_");

        System.out.print("Secret word: ");
        System.out.println(secretword_changed.replaceAll(".(?!$)", "$0 "));


        while (guess < guessCount) {


            if (!secretword_changed.contains("_")) {
                winnerWords();
                new WinOptionPaneMessage();
                break;
            }
            System.out.println(" ");
            System.out.println("Gissa en bokstav !");
            guessedChar = scan.next().trim().toUpperCase();

            if (checkingChar(guessedChar)) {
                if (secretword.contains(guessedChar)) {
                    secretword_changed = letterOnRightPlace(secretword, guessedChar, secretword_changed);
                } else if (triedChar.contains(guessedChar)) {
                    System.out.println("Sorry, du har redan gissat på den bokstaven, försök igen");
                } else {
                    System.out.println("Sorry! Du gissade FEL BOKSTAV, försök igen..");
                    System.out.println(" ");
                    triedChar = triedChar + guessedChar + " ";
                    guess = guess + 1;
                }
            }
            int guessesleft = (guessCount - guess);
            System.out.println("************************************");
            System.out.println(" ");
            System.out.print("Secret word: ");
            System.out.println(secretword_changed.replaceAll(".(?!$)", "$0 "));
            System.out.println(" ");
            System.out.println("Gissningar kvar:" + guessesleft);
            System.out.println(" ");
            System.out.println(triedChar);

            //kontrollera om du har förlorat
            if (guess >= guessCount) {
                hangdGubbeGrafik();
                new LooseOptionPaneMessage();
            }
        }

    }

    /**
     * this Method checks the users input ("guessedLetter") and checks in the Letter is in the randomly selected word,
     * if the letter is in the word the Method puts the letter on the right spot.
     *
     * @param secretWord         SecretWord is randomly selected from Words.txt
     * @param guessedChar        guessedChar is the letter the user inputs (the letter the user guesses)
     * @param secretWord_changed SecretWord_changed makes the secretword into "_____" and fills in the right guess on the right place.
     *                           Ex. (secretWord=Hej) guessedChar = e, outcome: "_e_"
     * @return the changed verison of secretWord look at exempel on line 78
     */
    public String letterOnRightPlace(String secretWord, String guessedChar, String secretWord_changed) {
        for (int i = 0; i < secretWord.length(); i++) {
            String secretword_char = Character.toString(secretWord.charAt(i));
            if (secretword_char.equals(guessedChar)) {
                char c = guessedChar.charAt(0);
                char[] secretword_blank_chars = secretWord_changed.toCharArray();
                secretword_blank_chars[i] = c;
                secretWord_changed = String.valueOf(secretword_blank_chars) + " ";
            }
        }
        return secretWord_changed;
    }

    private String[] splitData(String data) {
        return data.split(", ");
    }

    /**
     * This Method randomly select a word form our text file "Words.txt"
     *
     * @return a random word from file
     * if file can't be found an FileNotFoundException is caught
     */

    public String randomWordFromFile(String inputfile) throws Exception { //la till "String inputfile" så man kan testa den
        Scanner sc = null;
        String[] wordList = null;
        try {
            sc = new Scanner(new File(inputfile));
            wordList = splitData(sc.nextLine());
        } catch (FileNotFoundException e) {
            System.err.println("Kunde inte hitta filen");
        } catch (NullPointerException e) {
            e.printStackTrace();
        } finally {
            if (sc != null) {
                sc.close();
            }
        }
        Random rand = new Random();
        if (wordList == null) {
            throw new Exception("Kunde inte läsa i filen");
        }
        return wordList[rand.nextInt(wordList.length)];
    }

    /**
     * This method is checking if the users input is just 1 letter not more or less
     *
     * @param letter letter is the users input
     * @return if it is more then just 1 letter or a digit an error message is printed and ask the user to "input just 1 letter no numbers"
     */

    public boolean checkingChar(String letter) {
        if (letter.length() != 1 || Character.isDigit(letter.charAt(0))) {
            System.out.println("** ERROR: Endast en bokstav och Inga siffror! **");
            System.out.println(" ");
            return false;
        }
        return true;
    }


    private void welcomeWords() {
        System.out.println(" ");
        System.out.println("************************************");
        System.out.println("************************************");
        System.out.println("********* HÄNGA GUBBE SPEL *********");
        System.out.println("************************************");
        System.out.println("************************************");
        System.out.println(" ");
    }

    private void hangdGubbeGrafik() {
        System.out.println(" ");
        System.out.println("************************************");
        System.out.println("************************************");
        System.out.println("***** GAME OVER - YOU LOOSE!!! *****");
        System.out.println("*********** HÄNGDE GUBBE ***********");
        System.out.println("************************************");
        System.out.println("************************************");
        System.out.println(" ");
        System.out.println("   ----------");
        System.out.println("   |        |");
        System.out.println("   |        0");
        System.out.println("   |      --x--");
        System.out.println(" __|_      / |");
        System.out.println(" |   |");
    }

    private void winnerWords() {
        System.out.println(" ");
        System.out.println("************************************");
        System.out.println("****** GRATTIS! DU LYCKADES ********");
        System.out.println("************************************");
        System.out.println("************************************");
        System.out.println(" ");
    }

}

