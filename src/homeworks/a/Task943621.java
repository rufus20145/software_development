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
import java.util.Scanner;

import com.google.gson.Gson;

//['12.09.2020', '14.09.2020-02.10.2020']
//['12.09.2020', '14.09.2020-02.10.2020']
//['14.09.2020-02.10.2020']

public class Task943621 {
    private static final String SINGLE_DATE_REGEX = ".*";

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        String bookedDatesString = input.nextLine();
        String newBookingString = input.nextLine();
        DateTimeFormatter dFormatter = DateTimeFormatter.ofPattern("");

        Gson gson = new Gson();

        String[] splitStrings = gson.fromJson(bookedDatesString, String[].class);

        for (String string : splitStrings) {
            if (string.matches(SINGLE_DATE_REGEX)) {
                BookedPeriod abc = new BookedPeriod(string);
            }
        }
    }

    private static class BookedPeriod {
        private Calendar startDate;
        private Calendar endDate;

        public BookedPeriod(String date) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
            try {
                Date tmp = sdf.parse(date);
            } catch (ParseException e) {
                saveStackTraceToFile(e);
            }
            System.out.println("Success");
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
