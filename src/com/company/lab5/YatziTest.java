package com.company.lab5;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class YatziTest {
    @Test
    @DisplayName("Testing if it is Yatzi")
    void isYatziWhenAllDiceMatches() {
        Die[] dice = new Die[5];
        dice[0] = new Die();
        dice[1] = new Die();
        dice[2] = new Die();
        dice[3] = new Die();
        dice[4] = new Die();
        for(Die die: dice) {
            die.value = 6;
        }
        assertTrue(YatziMain.yatzi(dice));
    }

    @Test
    @DisplayName("Testing if it is NOT Yatzi")
    void isNotYatziWhenOneDieIsNotMatchingTheOther() {
        Die[] dice = new Die[5];
        dice[0] = new Die();
        dice[1] = new Die();
        dice[2] = new Die();
        dice[3] = new Die();
        dice[4] = new Die();
        for(Die die: dice) {
            die.value = 6;
        }
        dice[4].value = 1;
        assertFalse(YatziMain.yatzi(dice));
    }
}
