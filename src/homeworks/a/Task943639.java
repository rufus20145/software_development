package src.homeworks.a;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Scanner;

public class Task943639 {
    public static void main(String[] args) {
        Calendar startCalendar;
        Calendar calendar;
        SimpleDateFormat sdf = new SimpleDateFormat("d.M.yyyy");
        Scanner input = new Scanner(System.in);
        Date date = null;
        int numberOfWorkDay;

        String startDateString = input.nextLine();

        if (startDateString.matches("^(0?[1-9]|[12]\\d|3[01])\\.(0?[1-9]|1[012])\\.\\d{4}$")) {
            try {
                date = sdf.parse(startDateString);
            } catch (Exception e) {
                System.err.println("Что-то сломалось, вот всё, что я знаю:");
                e.printStackTrace();
                System.exit(1);
            }
        } else {
            System.err.println("Введенная строка не соответствует формату.");
            System.exit(1);
        }

        startCalendar = new GregorianCalendar(date.getYear() + 1900, date.getMonth(), date.getDate());

        calendar = new GregorianCalendar(startCalendar.get(Calendar.YEAR), startCalendar.get(Calendar.MONTH),
                startCalendar.get(Calendar.DAY_OF_MONTH));
        startCalendar.add(Calendar.MONTH, 1);

        numberOfWorkDay = 1;
        do {
            switch (numberOfWorkDay) {
                case 1:

                    if (calendar.get(Calendar.DAY_OF_WEEK) == 1) {
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
