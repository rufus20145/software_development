package homeworks.a;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

import com.google.gson.Gson;

//['12.09.2020', '14.09.2020-02.10.2020']
//['14.09.2020-02.10.2020']

public class Task943621 {
    /**
     * константы с кодами ошибок, используемыми в рамках данного файла
     */
    private static final int PARSING_ERROR_CODE = 1;
    private static final int INVALID_DATE_ERROR_CODE = 2;
    private static final int FORMAT_ERROR_CODE = 3;
    private static final int FILESAVE_ERROR_CODE = 4;

    /**
     * формат вводимой даты по условиям задачи и
     */
    private static final String DATE_FORMAT = "dd.MM.yyyy";
    private static final String SINGLE_DATE_REGEX = "^(0?[1-9]|[12]\\d|3[01])\\.(0?[1-9]|1[012])\\.\\d{4}$";
    private static final String DATE_PERIOD_REGEX = "^(0?[1-9]|[12]\\d|3[01])\\.(0?[1-9]|1[012])\\.\\d{4}-(0?[1-9]|[12]\\d|3[01])\\.(0?[1-9]|1[012])\\.\\d{4}$";
    private static Scanner input = new Scanner(System.in);

    public static void main(final String[] args) {
        final Gson gson = new Gson();
        List<BookingPeriod> bookedDates;
        BookingPeriod bookingRequest = null;
        final String bookedDatesString;
        final String bookingRequestString;

        bookedDatesString = getStringValue();
        final String[] jsonStrings = gson.fromJson(bookedDatesString, String[].class);
        bookedDates = new ArrayList<>();

        for (final String string : jsonStrings) {
            if (string.matches(SINGLE_DATE_REGEX)) {
                bookedDates.add(new BookingPeriod(string));
            } else if (string.matches(DATE_PERIOD_REGEX)) {
                final String[] splString = string.split("-");
                bookedDates.add(new BookingPeriod(splString[0], splString[1]));
            } else {
                System.err.println(
                        "Какой-то элемент первой строки не соответствует заданному формату. Самоликвидируюсь.");
                System.exit(FORMAT_ERROR_CODE);
            }
        }

        bookingRequestString = getStringValue();
        if (bookingRequestString.matches(SINGLE_DATE_REGEX)) {
            bookingRequest = new BookingPeriod(bookingRequestString);
        } else if (bookingRequestString.matches(DATE_PERIOD_REGEX)) {
            final String[] splString = bookingRequestString.split("-");
            bookingRequest = new BookingPeriod(splString[0], splString[1]);
        } else {
            System.err.println("Вторая строка не соответствует заданному формату. Самоликвидируюсь.");
            System.exit(FORMAT_ERROR_CODE);
        }

        System.out.println(
                BookingPeriod.checkAvailabilityForRequest(bookingRequest, bookedDates.toArray(new BookingPeriod[0])));

        input.close();
    }

    private static class BookingPeriod {
        private final Calendar startDate;
        private final Calendar endDate;
        private boolean isOneDay;

        BookingPeriod() {
            this.startDate = new GregorianCalendar();
            this.endDate = new GregorianCalendar();
        }

        BookingPeriod(final String string) {
            this();

            final Date date = parseDateFromString(string, DATE_FORMAT);
            this.startDate.setTime(date);
            this.endDate.setTime(date);
            this.isOneDay = true;
        }

        BookingPeriod(final String string1, final String string2) {
            this();

            this.startDate.setTime(parseDateFromString(string1, DATE_FORMAT));
            this.endDate.setTime(parseDateFromString(string2, DATE_FORMAT));
            this.isOneDay = false;
            if (endDate.before(startDate)) {
                System.err.println(
                        "Ошибка во вводе: дата окончания бронирования раньше даты его начала. Самоликвидируюсь.");
                System.exit(INVALID_DATE_ERROR_CODE);
            }
        }

        private Date parseDateFromString(final String string, final String format) {
            final SimpleDateFormat sdf = new SimpleDateFormat(format);
            try {
                return sdf.parse(string);
            } catch (final ParseException e) {
                saveStackTraceToFile(e);
                System.exit(PARSING_ERROR_CODE);
            }
            return null;
        }

        @Override
        public String toString() {
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
            return "BookingPeriod [endDate=" + sdf.format(endDate.getTime()) + ", startDate="
                    + sdf.format(startDate.getTime()) + ", isOneDay=" + isOneDay + "]";
        }

        public static boolean checkAvailabilityForRequest(BookingPeriod request, BookingPeriod[] bookedDates) {
            boolean availability = true;

            for (BookingPeriod bPeriod : bookedDates) {
                if ((request.startDate.after(bPeriod.startDate) && request.startDate.before(bPeriod.endDate))
                        || (request.endDate.after(bPeriod.startDate) && request.startDate.before(bPeriod.endDate))
                        || request.startDate.equals(bPeriod.startDate) || request.endDate.equals(bPeriod.endDate)
                        || request.startDate.equals(bPeriod.endDate) || request.endDate.equals(bPeriod.startDate)) {
                    availability = false;
                    break;
                }
            }
            return availability;
        }

    }

    private static void saveStackTraceToFile(final Exception e) {
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
            System.exit(FILESAVE_ERROR_CODE);
        }
        System.err.println("Что-то сломалось. Всё, что я знаю, записано в файл " + fileName);
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
