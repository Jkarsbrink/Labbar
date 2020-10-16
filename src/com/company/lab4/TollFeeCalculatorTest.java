package com.company.lab4;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
        LocalDateTime date1 = LocalDateTime.parse("2020-06-30 05:59",
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        LocalDateTime date2 = LocalDateTime.parse("2020-06-30 06:29",
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        LocalDateTime date3 = LocalDateTime.parse("2020-06-30 06:30",
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        LocalDateTime date4 = LocalDateTime.parse("2020-06-30 07:59",
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        LocalDateTime date5 = LocalDateTime.parse("2020-06-30 08:29",
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        LocalDateTime date6 = LocalDateTime.parse("2020-06-30 08:30",
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        LocalDateTime date7 = LocalDateTime.parse("2020-06-30 14:59",
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        LocalDateTime date8 = LocalDateTime.parse("2020-06-30 15:00",
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        LocalDateTime date9 = LocalDateTime.parse("2020-06-30 15:30",
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        LocalDateTime date10 = LocalDateTime.parse("2020-06-30 17:00",
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        LocalDateTime date11 = LocalDateTime.parse("2020-06-30 18:00",
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        LocalDateTime date12 = LocalDateTime.parse("2020-06-30 18:30",
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        LocalDateTime date13 = LocalDateTime.parse("2020-07-01 15:10",
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));


        assertEquals(0, TollFeeCalculator.getTollFeePerPassing(date1));
        assertEquals(8, TollFeeCalculator.getTollFeePerPassing(date2));
        assertEquals(13, TollFeeCalculator.getTollFeePerPassing(date3));
        assertEquals(18, TollFeeCalculator.getTollFeePerPassing(date4));
        assertEquals(13, TollFeeCalculator.getTollFeePerPassing(date5));
        assertEquals(8, TollFeeCalculator.getTollFeePerPassing(date6));
        assertEquals(8, TollFeeCalculator.getTollFeePerPassing(date7));
        assertEquals(13, TollFeeCalculator.getTollFeePerPassing(date8));
        assertEquals(18, TollFeeCalculator.getTollFeePerPassing(date9));
        assertEquals(13, TollFeeCalculator.getTollFeePerPassing(date10));
        assertEquals(8, TollFeeCalculator.getTollFeePerPassing(date11));
        assertEquals(0, TollFeeCalculator.getTollFeePerPassing(date12));
        assertEquals(0, TollFeeCalculator.getTollFeePerPassing(date13));
    }

    @Test
    @DisplayName("Testing if the full Date String is used")
    void isAllDatesUsed(){
    String dates = "2020-06-30 08:24, 2020-06-30 08:30";
    String[] splitedDates = TollFeeCalculator.splitData(dates);
    LocalDateTime[] twoDates = TollFeeCalculator.createDatesFromDateStrings(splitedDates);
    assertEquals(splitedDates.length, twoDates.length);
    String dates1 = "2020-06-30 08:24, 2020-06-30 08:30, 2020-06-30 09:40";
    String[] splitedDates1 = TollFeeCalculator.splitData(dates);
    LocalDateTime[] threeDates = TollFeeCalculator.createDatesFromDateStrings(splitedDates);
    assertNotEquals(splitedDates1.length, threeDates.length-1);
}

    @Test
    @DisplayName("Testing if it is just 1 payment every hour")
    void passingUnderSameHour(){
        LocalDateTime[] dates = new LocalDateTime[3];
        dates[0] = LocalDateTime.parse("2020-06-30 08:24",
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        dates[1] = LocalDateTime.parse("2020-06-30 08:30",
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        dates[2] = LocalDateTime.parse("2020-06-30 07:59",
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        assertEquals(18,TollFeeCalculator.getTotalFeeCost(dates));
    }

    @Test
    @DisplayName("Testing if the cost exceeds 60")
    void doesTheCostExceeds60(){
        String dates = "2020-09-23 05:03, 2020-09-23 06:01, 2020-09-23 06:30, 2020-09-23 07:00, 2020-09-23 07:30, 2020-09-23 08:00, 2020-09-23 08:30, 2020-09-23 13:30, 2020-09-23 15:15, 2020-09-23 15:30, 2020-09-23 17:30, 2020-09-23 18:13, 2020-09-23 18:30, 2020-09-23 21:00";
        String[] splitedDates = TollFeeCalculator.splitData(dates);
        LocalDateTime[] aLotOfDates = TollFeeCalculator.createDatesFromDateStrings(splitedDates);
        assertEquals(60, TollFeeCalculator.getTotalFeeCost(aLotOfDates));
    }

    @Test
    @DisplayName("Testing if string has space ")
    void DoesStringHaveSpace(){
        String ifItHasSpace = "Den totala kostnaden är: ";
        assertNotEquals("Den totala kostnaden är:", ifItHasSpace);
    }

    @Test@DisplayName("Testing byte array")
    void readMessageFromSystemErr(){
        ByteArrayOutputStream errContent = new ByteArrayOutputStream();
        System.setErr(new PrintStream(errContent));
        assertNotEquals("Utskrift skickat till System.err", errContent.toString().trim());
    }
}