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
import java.util.Scanner;

// Works in Java 18. In further versions class Date will be deleted.
public class Task943639 {
    /**
     * константа для приведения года
     */
    private static final int EPOCH_COEFFICIENT = 1900;
    /**
     *
     */
    private static final String DATE_REGEX = "^(0?[1-9]|[12]\\d|3[01])\\.(0?[1-9]|1[012])\\.\\d{4}$";

    public static void main(String[] args) {
        Calendar startCalendar;
        Calendar calendar;
        SimpleDateFormat sdf = new SimpleDateFormat("d.M.yyyy");
        Scanner input = new Scanner(System.in);
        Date date = null;
        int numberOfWorkDay;

        String startDateString = input.nextLine();

        if (startDateString.matches(DATE_REGEX)) {
            try {
                date = sdf.parse(startDateString);
            } catch (ParseException e) {
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
        } else {
            System.err.println("Введенная строка не соответствует формату.");
            System.exit(1);
        }

        startCalendar = new GregorianCalendar(date.getYear() + EPOCH_COEFFICIENT, date.getMonth(), date.getDate());

        calendar = new GregorianCalendar(startCalendar.get(Calendar.YEAR), startCalendar.get(Calendar.MONTH),
                startCalendar.get(Calendar.DAY_OF_MONTH));
        startCalendar.add(Calendar.MONTH, 1);

        numberOfWorkDay = 1;
        do {
            switch (numberOfWorkDay) {
                case 1:

                    if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
                        calendar.add(Calendar.DAY_OF_MONTH, 1);
                        System.out.println(sdf.format(calendar.getTime()));
                        calendar.add(Calendar.DAY_OF_MONTH, 1);
                    } else {
                        System.out.println(sdf.format(calendar.getTime()));
                        calendar.add(Calendar.DAY_OF_MONTH, 1);
                    }
                    numberOfWorkDay = 4;
                    break;
                case 2:
                case 3:
                case 4:
                    numberOfWorkDay--;
                    calendar.add(Calendar.DAY_OF_MONTH, 1);
                    break;
                default:
                    break;
            }
        } while (startCalendar.after(calendar));

        input.close();
    }
}
