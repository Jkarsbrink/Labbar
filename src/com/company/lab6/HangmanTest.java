package com.company.lab6;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import java.io.FileNotFoundException;


import static org.junit.jupiter.api.Assertions.*;

public class HangmanTest {

    @DisplayName("Testar om programmet läser mer en 1 bokstav")
    @Test
    void DoProgramComplyWithDigits() {
        Hangman hang = new Hangman();
        hang.checkingChar("1");
        assertFalse(hang.checkingChar("1"));
        assertTrue(hang.checkingChar("o"));
        assertFalse(hang.checkingChar("er"));

    }
    @Test
    void fileNotFoundExceptionTesting(){
        Throwable exception = assertThrows(FileNotFoundException.class, () -> {
            throw new FileNotFoundException("a message");
        });
        assertEquals("a message",exception.getMessage());
    }
    @Test
    void nullPointerExceptionTesting(){
        Throwable exception = assertThrows(NullPointerException.class, () -> {
            throw new NullPointerException("a message");
        });
        assertEquals("a message", exception.getMessage());
    }

    @DisplayName("Testing if letters is in right place")
    @Test
    void isLettersInRightPlace() {
        Hangman hang = new Hangman();
        String expectedOutcome = "_e____";
        assertEquals(expectedOutcome, hang.letterOnRightPlace("Hejsan", "e", "_e____").trim());
        assertNotEquals(expectedOutcome, hang.letterOnRightPlace("Hej", "e", "__e").trim());
    }


}
