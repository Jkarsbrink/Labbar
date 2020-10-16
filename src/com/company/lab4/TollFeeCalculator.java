package com.company.lab4;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Scanner;

public class TollFeeCalculator {
    public static String[] splitData(String data){
        return data.split(", ");
    }
    public static LocalDateTime[] createDatesFromDateStrings(String[] dates){
        LocalDateTime[] dateTimeArray = new LocalDateTime[dates.length];
        for (int i = 0; i < dates.length; i++) {
            dateTimeArray[i] = LocalDateTime.parse(dates[i], DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        }
        return dateTimeArray; // längd -1 och createDatesFromDateString hade bara längden inte någon sparad data

    }
    public TollFeeCalculator(String inputFile) {
        Scanner sc = null;
        try {
            sc = new Scanner(new File(inputFile));
            String[] dateStrings = splitData(sc.nextLine());
            LocalDateTime[] dates = createDatesFromDateStrings(dateStrings);
            System.out.println("Den totala kostnaden är: " + getTotalFeeCost(dates)); // saknade mellanrum
        } catch (IOException e) {
            System.err.println("Kunde inte läsa filen " + inputFile);
        } catch (StringIndexOutOfBoundsException e) {
            System.err.println("String index out of bonds ");
        }finally {
            if (sc != null) {
                sc.close(); // Skannern stängdes inte
            }
        }
    }

    public static int getTotalFeeCost(LocalDateTime[] dates) {
        int totalFee = 0;
        int totalDayFee = 0;
        int lastValue = 0;
        int currentValue;
        boolean isNewDay;
        LocalDateTime intervalStart = dates[0];

        for (LocalDateTime date : dates) { // Har testat om man åker under samma timme
            long diffInMinutes = intervalStart.until(date, ChronoUnit.MINUTES);
            isNewDay = intervalStart.getDayOfYear() != date.getDayOfYear();
            System.out.println(date.toString());
            if (getTollFeePerPassing(date) == 0) {
                System.out.println("Gratis timme/dag/månad");
            } else if (isNewDay) {
                intervalStart = date;
                System.out.println("Det är en ny dag, den nuvarande kostnaden är: " + totalDayFee);
                totalFee += Math.min(60, totalDayFee);
                System.out.println("Dagens kostnad är tilllagd på totala kostnaden och återställs");
                totalDayFee = 0;
                totalDayFee += getTollFeePerPassing(date);
                System.out.println("dagens kostnad är: " + getTollFeePerPassing(date) + " är tilllagd till den nya dagliga kostnaden");
            } else if (diffInMinutes >= 60 || intervalStart.equals(date) || totalDayFee == 60) {
                intervalStart = date;
                lastValue = getTollFeePerPassing(date);
                totalDayFee += getTollFeePerPassing(date);
                System.out.println("+ " + getTollFeePerPassing(date) + " till dagliga  kostnaden");

            } else {
                currentValue = getTollFeePerPassing(date);
                if (currentValue > lastValue) {
                    totalDayFee += currentValue - lastValue;
                    System.out.println("- " + lastValue);
                    System.out.println("+ " + currentValue);
                    System.out.println("Denna tid kostar mer än föregående tid i samma timme");
                    System.out.println("Kostnaden för denna tid kommer ersätta förra tiden");
                    lastValue = getTollFeePerPassing(date);
                } else {
                    System.out.println("Du har redan betalt för denna timme");
                }
            }
            System.out.println(" ");
        }
        totalFee += Math.min(60, totalDayFee);// Kan inte ha max utan ska ha min.
        return totalFee;
    }
    public static int getTollFeePerPassing(LocalDateTime date) {
        if (isTollFreeDate(date)) return 0;
        int hour = date.getHour();
        int minute = date.getMinute();
        if (hour == 6  && minute <= 29) return 8;
        else if (hour == 6 ) return 13;
        else if (hour == 7 ) return 18;
        else if (hour == 8 && minute <= 29) return 13;
        else if (hour >= 8 && hour < 15) return 8;
        else if (hour == 15 && minute <= 29) return 13;
        else if (hour == 15 || hour == 16) return 18;
        else if (hour == 17 ) return 13;
        else if (hour == 18 && minute <= 29) return 8;
        else return 0;
        //tog inte upp alla betaltider och räknar fel vid vissa tillfällen, även testar 14:59 och alla bytes värden exempel 07:59 och 08:00
    }

    public static boolean isTollFreeDate(LocalDateTime date) {
        return date.getDayOfWeek().getValue() == 6 || date.getDayOfWeek().getValue() == 7 || date.getMonth().getValue() == 7;
    }// har testa om juli e gratis

    public static void main(String[] args) {
        new TollFeeCalculator("testData/Lab4.txt");
    }
}
