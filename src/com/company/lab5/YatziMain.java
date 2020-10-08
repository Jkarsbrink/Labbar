package com.company.lab5;

import java.util.Scanner;

public class YatziMain {
    private final Die[] die;

    public YatziMain() {
        die = new Die[5];
        for (int d = 0; d < 5; d++) {
            die[d] = new Die();
        }
        whenPlaying();
    }

    protected boolean yatzi(Die[] die) {
        for (int j = 1; j < 5; j++) {
            if (die[j].value != die[j - 1].value) {
                return false;
            }
        }
        return true;
    }

    private boolean askForYes(String message) {
        System.out.println(message);
        Scanner sc = new Scanner(System.in);
        return sc.next().equals("y");
    }

    private void rollDice() {
        for (int i = 0; i < die.length; i++) {
            die[i].rollDie();
            System.out.println(i + ": " + die[i].getString());
        }
    }

    private void whenPlaying() {
        int turn = 0;
        System.out.println("Welcome to Yatzi!");
        while (turn < 3) {
            System.out.println("Starting turn " + (turn + 1) + " of 3, rolling dice.");
            rollDice();
            if (yatzi(die)) {
                System.out.println("You got YATZI! in " + die[0].value + "'s");
                return;
            } else {
                if (turn != 2 && askForYes("Want to throw again? (y for yes, anything else for no)")) {
                    turn++;
                } else if (turn == 2 && askForYes("Game Over, Want to play again?")) {
                    turn = 0;
                } else {
                    break;
                }
            }
        }
    }


    public static void main(String[] args) {
        new YatziMain();
    }
}