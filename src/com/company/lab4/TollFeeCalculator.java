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
        return new LocalDateTime[dates.length];
    }
    public TollFeeCalculator(String inputFile) {
        try {
            Scanner sc = new Scanner(new File(inputFile));
            String[] dateStrings = splitData(sc.nextLine());
            LocalDateTime[] dates = createDatesFromDateStrings(dateStrings);
            for (int i = 0; i < dates.length; i++) {
                dates[i] = LocalDateTime.parse(dateStrings[i], DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            }
            System.out.println("Den totala kostnaden är: " + getTotalFeeCost(dates));
        } catch (IOException e) {
            System.err.println("Kunde inte läsa filen " + inputFile);
        } catch (StringIndexOutOfBoundsException e) {
            System.err.println(e.getMessage());
        }
    }

    public static int getTotalFeeCost(LocalDateTime[] dates) {
        int totalFee = 0;
        int totalDayFee = 0;
        int lastValue = 0;
        int currentValue;
        boolean isNewDay;
        LocalDateTime intervalStart = dates[0];

        for (LocalDateTime date : dates) {
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
                    System.out.println("du har redan betalt för denna timme");
                }
            }
            System.out.println(" ");
        }
        totalFee += Math.min(60, totalDayFee);
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
        else if (hour == 8 ||  hour >= 9 && hour < 15 && minute <= 30 ) return 8;
        else if (hour == 15 && minute <= 29) return 13;
        else if (hour == 15 ||  hour == 16 && minute <=59 ) return 18;
        else if (hour == 17 ) return 13;
        else if (hour == 18 && minute <= 29) return 8;
        else return 0;
    }

    public static boolean isTollFreeDate(LocalDateTime date) {
        return date.getDayOfWeek().getValue() == 6 || date.getDayOfWeek().getValue() == 7 || date.getMonth().getValue() == 7;
    }

    public static void main(String[] args) {
        new TollFeeCalculator("testData/Lab4.txt");
    }
}
