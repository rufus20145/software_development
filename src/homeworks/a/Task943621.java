package src.homeworks.a;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.TemporalAccessor;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Scanner;

import com.google.gson.Gson;

//['12.09.2020', '14.09.2020-02.10.2020']
//['12.09.2020', '14.09.2020-02.10.2020']
//['14.09.2020-02.10.2020']

public class Task943621 {
    private static final String SINGLE_DATE_REGEX = "^(0?[1-9]|[12]\\d|3[01])\\.(0?[1-9]|1[012])\\.\\d{4}$";
    private static final String DATE_PERIOD_REGEX = "^(0?[1-9]|[12]\\d|3[01])\\.(0?[1-9]|1[012])\\.\\d{4}-(0?[1-9]|[12]\\d|3[01])\\.(0?[1-9]|1[012])\\.\\d{4}$";

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        String bookedDatesString = input.nextLine();
        String newBookingString = input.nextLine();
        DateTimeFormatter dFormatter = DateTimeFormatter.ofPattern("");

        Gson gson = new Gson();

        String[] splitStrings = gson.fromJson(bookedDatesString, String[].class);

        for (String string1 : splitStrings) {
            if (string1.matches(SINGLE_DATE_REGEX)) {
                BookedPeriod abc = new BookedPeriod(string1);
            } else if (string1.matches(DATE_PERIOD_REGEX)) {
                String[] splString = string1.split("-");
                BookedPeriod abc = new BookedPeriod(splString[0], splString[1]);
            }
        }

        input.close();
    }

    private static class BookedPeriod {
        private Calendar startDate;
        private Calendar endDate;

        public BookedPeriod() {
            this.startDate = new GregorianCalendar();
            this.endDate = new GregorianCalendar();
        }

        public BookedPeriod(String string) {
            this();
            Date date = parseDateFromString(string, "dd.MM.yyyy");
            this.startDate.setTime(date);
            this.endDate.setTime(date);
            System.out.println("Success");
        }

        private Date parseDateFromString(String string, String format) {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            try {
                return sdf.parse(string);
            } catch (ParseException e) {
                saveStackTraceToFile(e);
            }
            return null;
        }

        public BookedPeriod(String string1, String string2) {
            this();

            Date tmp;
            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
            try {
                tmp = sdf.parse(string1);
                this.startDate.setTime(tmp);
                tmp = sdf.parse(string2);
                this.endDate.setTime(tmp);
            } catch (ParseException e) {
                saveStackTraceToFile(e);
            }
            System.out.println("Double success");
        }
    }

    private static void saveStackTraceToFile(final ParseException e) {
        final SimpleDateFormat timeStampPattern = new SimpleDateFormat("dd_MM_yyyy_HH_mm_ss");
        final String fileName = "Log_" + timeStampPattern.format(java.time.LocalDateTime.now()) + ".txt";
        final File myFile = new File(fileName);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(myFile));) {
            for (final StackTraceElement elem : e.getStackTrace()) {
                writer.write(elem.toString() + "\n");
            }
        } catch (final IOException ex) {
            System.out
                    .println("При записи данных о прошлой ошибке произошла какая-то ошибка. Самоликвидируюсь.");
            System.exit(1);
        }
        System.err.println("Что-то сломалось. Всё, что я знаю, записано в файл " + fileName);
        System.exit(1);
    }
}
