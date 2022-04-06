package src.homeworks.a;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import com.google.gson.Gson;

public class Task943603 {
    public static void main(final String[] args) {
        long longValue = 0;
        String scannedLine;
        final Scanner input = new Scanner(System.in);

        scannedLine = input.nextLine().replaceAll("['’‘]", "\"");
        System.out.println(scannedLine);

        final Gson gson = new Gson();

        final TimeCounter[] tcs = gson.fromJson(scannedLine, TimeCounter[].class);

        for (final TimeCounter tc : tcs) {
            tc.convertStringsToDates();
            longValue += tc.getDurationInMinutes();
        }

        System.out.printf("%d-%02d", longValue / 60, longValue % 60);

        input.close();
    }

    private class TimeCounter {
        private static final int MS_TO_MINUTES = 60000;
        private String start;
        private String end;
        private Date startDate = null;
        private Date endDate = null;

        /**
         * @return разницу между начальной и конечной датами в минутах с округлением
         *         вниз
         */
        public long getDurationInMinutes() {
            return getDuration() / MS_TO_MINUTES;
        }

        /**
         * @return разницу между начальной и конечной датами в миллисекундах
         */
        public long getDuration() {

            return endDate.getTime() - startDate.getTime();
        }

        private void convertStringsToDates() {
            final SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy hh:mm:ss");
            try {
                this.startDate = sdf.parse(this.start);
                this.endDate = sdf.parse(this.end);
            } catch (final ParseException e) {
                System.err.println("Произошла ошибка при парсинге. Вся информация об ошибке записана в файл.");
                saveStackTraceToFile(e);
            }
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