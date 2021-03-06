package homeworks.a;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Task943657 {
    private static final int START_VALUE = -1;
    private static final int TWO = 2;
    private static final int THREE = 3;
    private static final int FORMAT_ERROR_CODE = 3;
    private static final int HOURS_TO_MINUTES = 60;
    private static final int MINUTES_TO_HOURS = 60;
    private static final int NUMBER_OF_MINUTES_IN_DAY = 1440;
    private static final String DOUBLE_TIME_REGEX = "^(2[0-3]|1\\d|\\d):([0-5]\\d) (2[0-3]|1\\d|\\d):([0-5]\\d)$";
    private static Scanner input = new Scanner(System.in);

    // решение тупое прямо как я сегодня (или не только сегодня)
    public static void main(String[] args) {

        List<String> scannedStrings = new ArrayList<>();
        int[] numberOfVisitors = new int[NUMBER_OF_MINUTES_IN_DAY];

        scanLines(scannedStrings);

        for (String string : scannedStrings) {
            if (string.matches(DOUBLE_TIME_REGEX)) {
                String[] splStrings = string.split("[ :]");
                int endValue = convertToMinutes(splStrings[TWO], splStrings[THREE]);
                for (int i = convertToMinutes(splStrings[0], splStrings[1]); i <= endValue; i++) {
                    numberOfVisitors[i]++;
                }
            } else {
                System.err.println("Обнаружена строка, не соответствующая формату ввода. Самоликвидируюсь. ");
                System.exit(FORMAT_ERROR_CODE);
            }
        }

        int maxVisitorsStart = START_VALUE;
        int maxVisitorsEnd = START_VALUE;
        int maxVisitors = 0;

        boolean foundMaxEnd = false;
        for (int i = 0; i < NUMBER_OF_MINUTES_IN_DAY; i++) {
            if (numberOfVisitors[i] > maxVisitors) {
                maxVisitors = numberOfVisitors[i];
                maxVisitorsStart = i;
                foundMaxEnd = false;
            }
            if (numberOfVisitors[i] < maxVisitors && !foundMaxEnd) {
                foundMaxEnd = true;
                maxVisitorsEnd = i - 1;
            }
        }

        System.out.println(convertToHoursAndMinutes(maxVisitorsStart) + " " + convertToHoursAndMinutes(maxVisitorsEnd));

        input.close();
    }

    private static void scanLines(List<String> scannedStrings) {
        while (input.hasNextLine()) {
            String buffer = input.nextLine();
            if (buffer.isEmpty()) {
                break;
            }
            scannedStrings.add(buffer);
        }
    }

    private static int convertToMinutes(String hours, String minutes) {
        return Integer.parseInt(hours) * HOURS_TO_MINUTES + Integer.parseInt(minutes);
    }

    private static String convertToHoursAndMinutes(int numOfMinutes) {
        return String.format("%02d:%02d", numOfMinutes / MINUTES_TO_HOURS,
                numOfMinutes % MINUTES_TO_HOURS);
    }
}
