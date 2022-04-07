package src.homeworks.a;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.NoSuchElementException;
import java.util.Scanner;

// Works in Java 18. In further versions class Date will be deleted.
public class Task943639 {

    private static final int FOUR = 4;
    private static final int THREE = 3;
    private static final int TWO = 2;
    private static final int ONE = 1;
    private static final int START_VALUE_OF_WORK_DAYS = 4;
    private static final String DATE_REGEX = "^(0?[1-9]|[12]\\d|3[01])\\.(0?[1-9]|1[012])\\.\\d{4}$";
    private static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        Calendar startCalendar;
        Calendar calendar;
        SimpleDateFormat sdf = new SimpleDateFormat("d.M.yyyy");

        Date date = null;
        int numberOfWorkDay;

        String startDateString = getStringValue();

        if (startDateString.matches(DATE_REGEX)) {
            try {
                date = sdf.parse(startDateString);
            } catch (ParseException e) {
                saveStackTraceToFile(e);
            }
        } else {
            System.err.println("Введенная строка не соответствует формату.");
            System.exit(1);
        }

        startCalendar = new GregorianCalendar();
        startCalendar.setTime(date);

        calendar = new GregorianCalendar(startCalendar.get(Calendar.YEAR), startCalendar.get(Calendar.MONTH),
                startCalendar.get(Calendar.DAY_OF_MONTH));
        startCalendar.add(Calendar.MONTH, 1);

        numberOfWorkDay = 1;
        do {
            switch (numberOfWorkDay) {
                case ONE:
                    if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
                        calendar.add(Calendar.DAY_OF_MONTH, 1);
                        System.out.println(sdf.format(calendar.getTime()));
                        calendar.add(Calendar.DAY_OF_MONTH, 1);
                    } else {
                        System.out.println(sdf.format(calendar.getTime()));
                        calendar.add(Calendar.DAY_OF_MONTH, 1);
                    }
                    numberOfWorkDay = START_VALUE_OF_WORK_DAYS;
                    break;
                case TWO:
                case THREE:
                case FOUR:
                    numberOfWorkDay--;
                    calendar.add(Calendar.DAY_OF_MONTH, 1);
                    break;
                default:
                    break;
            }
        } while (startCalendar.after(calendar));

        input.close();
    }

    private static void saveStackTraceToFile(ParseException e) {
        SimpleDateFormat timeStampPattern = new SimpleDateFormat("dd_MM_yyyy_HH_mm_ss");
        String fileName = "Log_" + timeStampPattern.format(java.time.LocalDateTime.now()) + ".txt";
        File myFile = new File(fileName);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(myFile));) {
            for (StackTraceElement elem : e.getStackTrace()) {
                writer.write(elem.toString() + "\n");
            }
        } catch (IOException ex) {
            System.out
                    .println("При записи данных о прошлой ошибке произошла какая-то ошибка. Самоликвидируюсь.");
            System.exit(1);
        }
        System.err.println("Что-то сломалось. Всё, что я знаю, записано в файл " + fileName);
        System.exit(1);
    }

    private static String getStringValue() {
        boolean exceptionCaught = false;
        String inputString = null;

        do {
            exceptionCaught = false;
            try {
                inputString = input.nextLine();
            } catch (NoSuchElementException e) {
                System.out.println("Вы не ввели ничего. Повторите попытку.");
                exceptionCaught = true;
                input.nextLine();
            } catch (IllegalStateException e) {
                System.out.println("Система ввода оказалась в некорректном состоянии. Повторите попытку.");
                exceptionCaught = true;
                input = new Scanner(System.in);
                input.nextLine();
            }
        } while (exceptionCaught);
        return inputString;
    }
}
