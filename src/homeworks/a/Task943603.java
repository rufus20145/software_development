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
    public static void main(String[] args) {
        String scannedLine;
        Scanner input = new Scanner(System.in);

        scannedLine = input.nextLine().replaceAll("['’‘]", "\"");
        System.out.println(scannedLine);

        Gson gson = new Gson();

        TimeCounter[] tcs = gson.fromJson(scannedLine, TimeCounter[].class);

        for (TimeCounter tc : tcs) {
            System.out.println(tc.getDurationInSecs() + " duration");
        }
    }

    private class TimeCounter {
        private static final int MS_TO_SECONDS = 1000;
        String start;
        String end;

        public long getDurationInSecs() {
            return getDuration() / MS_TO_SECONDS;
        }

        public long getDuration() {
            Date startDate = null;
            Date endDate = null;
            SimpleDateFormat sdf = new SimpleDateFormat("DD.MM.YYYY hh:mm:ss");
            try {
                startDate = sdf.parse(this.start);
                endDate = sdf.parse(this.end);
            } catch (ParseException e) {
                System.err.println("Произошла ошибка при парсинге. Вся информация об ошибке записана в файл.");
                saveStackTraceToFile(e);
            }

            return endDate.getTime() - startDate.getTime();
        }
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
}