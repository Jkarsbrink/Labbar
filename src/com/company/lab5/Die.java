package com.company.lab5;

public class Die extends BoardGameMaterial {
    public int value = 0;

    protected Die() {
    }

    public void rollDie() {
        value = (int)(Math.random()*6+1);
    }

    protected String getString() {
        return "Dice shows " + value;
    }
}
