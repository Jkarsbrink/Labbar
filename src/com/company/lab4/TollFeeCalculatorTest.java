package com.company.lab4;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import static org.junit.jupiter.api.Assertions.*;

public class TollFeeCalculatorTest {


    @Test
    @DisplayName("Testing the diffrent date is free or not")
    void isItAFreeDate(){
        LocalDateTime date = LocalDateTime.parse("2020-07-01 14:00",
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")); // juli gratis
        LocalDateTime date2 = LocalDateTime.parse("2020-06-28 14:00",
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")); //helg gratis
        LocalDateTime date3 = LocalDateTime.parse("2020-07-27 14:00",
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")); // helg gratis
        assertTrue(TollFeeCalculator.isTollFreeDate(date));
        assertTrue(TollFeeCalculator.isTollFreeDate(date2));
        assertTrue(TollFeeCalculator.isTollFreeDate(date3));
    }
    @DisplayName("Testing the diffrent cost for diffrent time period")
    @Test
    void tollFeePrePassing(){
        LocalDateTime date1 = LocalDateTime.parse("2020-06-30 01:00",
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        LocalDateTime date2 = LocalDateTime.parse("2020-06-30 06:10",
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        LocalDateTime date3 = LocalDateTime.parse("2020-06-30 06:40",
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        LocalDateTime date4 = LocalDateTime.parse("2020-06-30 07:35",
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        LocalDateTime date5 = LocalDateTime.parse("2020-06-30 08:24",
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        LocalDateTime date6 = LocalDateTime.parse("2020-06-30 08:30",
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        LocalDateTime date7 = LocalDateTime.parse("2020-06-30 10:15",
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        LocalDateTime date8 = LocalDateTime.parse("2020-06-30 15:10",
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        LocalDateTime date9 = LocalDateTime.parse("2020-06-30 15:30",
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        LocalDateTime date10 = LocalDateTime.parse("2020-06-30 17:24",
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        LocalDateTime date11 = LocalDateTime.parse("2020-06-30 18:05",
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        LocalDateTime date12 = LocalDateTime.parse("2020-06-30 23:00",
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        LocalDateTime date13 = LocalDateTime.parse("2020-07-01 15:10",
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));


        assertEquals(TollFeeCalculator.getTollFeePerPassing(date1),0);
        assertEquals(TollFeeCalculator.getTollFeePerPassing(date2),8);
        assertEquals(TollFeeCalculator.getTollFeePerPassing(date3),13);
        assertEquals(TollFeeCalculator.getTollFeePerPassing(date4),18);
        assertEquals(TollFeeCalculator.getTollFeePerPassing(date5),13);
        assertEquals(TollFeeCalculator.getTollFeePerPassing(date6),8);
        assertEquals(TollFeeCalculator.getTollFeePerPassing(date7), 8);
        assertEquals(TollFeeCalculator.getTollFeePerPassing(date8),13);
        assertEquals(TollFeeCalculator.getTollFeePerPassing(date9),18);
        assertEquals(TollFeeCalculator.getTollFeePerPassing(date10),13);
        assertEquals(TollFeeCalculator.getTollFeePerPassing(date11),8);
        assertEquals(TollFeeCalculator.getTollFeePerPassing(date12),0);
        assertEquals(TollFeeCalculator.getTollFeePerPassing(date13),0);

    }
    @Test
    @DisplayName("Testing if the full Date String is used")
    void isAllDatesUsed(){
    String dates = "2020-06-30 08:24, 2020-06-30 08:30";
    String[] splitedDates = TollFeeCalculator.splitData(dates);
    LocalDateTime[] twoDates = TollFeeCalculator.createDatesFromDateStrings(splitedDates);
    assertEquals(splitedDates.length, twoDates.length);
}

    @Test
    @DisplayName("felsök för betalning")
    void passingUnderSameHour(){
        LocalDateTime[] dates = new LocalDateTime[2];
        dates[0] = LocalDateTime.parse("2020-06-30 08:24",
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        dates[1] = LocalDateTime.parse("2020-06-30 08:30",
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        assertEquals(TollFeeCalculator.getTotalFeeCost(dates), 13);

    }
    @Test
    @DisplayName("StringIndexOutOfBoundsException Test")
    void TestTollFeeCalculator(){
        TollFeeCalculator("TestIndex.txt");
    }
    void TollFeeCalculator(String inputfile){
        assertEquals("Cought a StringIndexOutOfBounds", inputfile);
    }

    @Test
    @DisplayName("Test if scanner reads all data")
    void readScanner(){

        String data = "dateStrings";
        InputStream stdin = System.in;
        System.setIn(new ByteArrayInputStream(data.getBytes()));
        Scanner scanner = new Scanner(System.in);
        System.setIn(stdin);
    }

    @Test
    void RightLengthOnDate() {
        String[] arr = new String[9];
        if (arr.length != 8) {
            throw new IllegalStateException("Array length is not expected");
                String LocalDateTime = "yyyy-MM-dd HH:mm";
                assertEquals(9, LocalDateTime.length());
        }
    }
    @Test
    @DisplayName("Testing if string has space ")
    void DoesStringHaveSpace(){
        String ifItHasSpace = "Den totala kostnaden är: ";
        assertNotEquals("Den totala kostnaden är:", ifItHasSpace);

    }
}