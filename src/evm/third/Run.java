package evm.third;

import java.util.Arrays;
import java.util.Scanner;

public class Run {
    /**
     *
     */
    private static final int BASE_SIZE = 8;
    private static final int MODULAR_SIZE = 6;

    public static void main(String[] args) {
        System.out.print("Введите ваши исходные данные.\nНомер студента в списке группы: ");
        Scanner input = new Scanner(System.in);
        long studentNumber = input.nextInt();
        System.out.print(
                "Введите вашу константу рождения с учетом ведущих нулей по частям (например для 06071902 сначала 06, потом 07, потом 02): ");
        long dd = input.nextInt();
        long mm = input.nextInt();
        long gg = input.nextInt(); // ! нигде не используется

        long a = (studentNumber % 10) * 10000 + dd * 100 + mm;
        long b = (studentNumber % 10) * 10000 + mm * 100 + dd;
        long v = dd * 1000 + (studentNumber % 10) * 100 + mm;
        long u = mm * 1000 + (studentNumber % 10) * 100 + dd;

        System.out.printf("%d, %d, %d, %d%n", a, b, v, u);

        long[] base = new long[BASE_SIZE];

        System.out.print("Введите основание вашей системы счисления из таблицы: ");
        for (int i = 0; i < BASE_SIZE; i++) {
            base[i] = input.nextInt();
        }

        System.out.println("Задание 3. Выполняю переход к модулярному представлению-вектору.");

        long[] aModular = new long[MODULAR_SIZE];
        long[] bModular = new long[MODULAR_SIZE];
        long[] vModular = new long[MODULAR_SIZE];
        long[] uModular = new long[MODULAR_SIZE];

        for (int i = 0; i < Math.min(MODULAR_SIZE, BASE_SIZE); i++) {
            aModular[i] = a % base[i];
            bModular[i] = b % base[i];
            vModular[i] = v % base[i];
            uModular[i] = u % base[i];
        }
        // TODO: вывод промежуточных действий
        System.out.println("Модулярное представление числа " + a + " = " + Arrays.toString(aModular));
        System.out.println("Модулярное представление числа " + b + " = " + Arrays.toString(bModular));
        System.out.println("Модулярное представление числа " + v + " = " + Arrays.toString(vModular));
        System.out.println("Модулярное представление числа " + u + " = " + Arrays.toString(uModular));

        System.out.println("Задание 4. Выполняю операцию свёртки.");

        long[] wModularComputed = new long[MODULAR_SIZE];
        long[] sum = new long[MODULAR_SIZE];
        long[] aXbModular = new long[MODULAR_SIZE];
        long[] aXuModular = new long[MODULAR_SIZE];
        long[] vXuModular = new long[MODULAR_SIZE];
        for (int i = 0; i < Math.min(MODULAR_SIZE, BASE_SIZE); i++) {

            aXbModular[i] = (aModular[i] * bModular[i]) % base[i];
            vXuModular[i] = (vModular[i] * uModular[i]) % base[i];
            aXuModular[i] = (aModular[i] * uModular[i]) % base[i];
            sum[i] = (aXbModular[i] + vXuModular[i]) % base[i];
            System.out.println("Первая часть " + (aXbModular[i] + vXuModular[i] - aXuModular[i]));
            System.out.println("Вторая часть " + vichetMinus(base[i], aXbModular[i] + vXuModular[i], aXuModular[i]));

            wModularComputed[i] = aXbModular[i] + vXuModular[i] - aXuModular[i]
                    - vichetMinus(base[i], aXbModular[i] + vXuModular[i], aXuModular[i]) * base[i];
        }

        System.out.println("Модулярное представление a*b " + Arrays.toString(aXbModular));
        System.out.println("Модулярное представление v*u " + Arrays.toString(vXuModular));
        System.out.println("Модулярное представление a*u " + Arrays.toString(aXuModular));
        System.out.println("Модулярное представление суммы " + Arrays.toString(sum));
        System.out.println("Модулярное представление итога " + Arrays.toString(wModularComputed));

        System.out.println("Задание 5. Выполняю операцию над десятичными представлениями.");
        long w = a * b + u * v - a * u;
        long[] wModular = new long[MODULAR_SIZE];
        for (int i = 0; i < Math.min(MODULAR_SIZE, BASE_SIZE); i++) {
            wModular[i] = w % base[i];
        }

        System.out.println("Модулярное представление числа " + w + " = " + Arrays.toString(wModular));

        System.out.println("Задание 6");
        input.close();

    }

    private static long vichetMinus(long base, long value1, long value2) {
        if (value1 - value2 >= 0) {
            return (long) Math.floor((value1 - value2 * 1.0) / base);
        } else {
            return -1;
        }
    }
}
