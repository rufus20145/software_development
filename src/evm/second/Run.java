package evm.second;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Run {
    Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        /*
         * TODO
         * System.out.
         * print("Введите ваши исходные данные.\nНомер студента в списке группы: ");
         * long studentNumber = input.nextInt();
         * System.out.print(
         * "Введите вашу константу рождения с учетом ведущих нулей по частям (например для 06071902 сначала 06, потом 07, потом 02): "
         * );
         * long dd = input.nextInt();
         * long mm = input.nextInt();
         * long gg = input.nextInt(); // ! нигде не используется
         * 
         * System.out.printf("Ваши константы: TODO");
         */
        // B ↔ (23, 06, 02, 19, 07)
        long[] baseN = { 79, 181, 293, 421, 557, 673, 821, 953 }; // 12
        long[] baseK = { 67, 167, 277, 401, 523, 653, 767, 937 }; // 9
        System.out.println(
                "Введите число для перевода в полиадический формат. Основания жестко закодированы строчкой выше.");
        long number = input.nextLong();
        List<Long> polyNumber = new ArrayList<>();
        int i = 0;
        long[] base = baseN;
        do {
            System.out.printf("U_%d = [%d/%d]*%d + u_%d = %d*%d + u_%d = %d + u_%d => u_%d = %d%n",
                    i, number, base[i], base[i], i, number / base[i], base[i], i,
                    (number / base[i]) * base[i], i, i, number % base[i]);
            if (number == 0) {
                break;
            }
            polyNumber.add(number % base[i]);
            number /= base[i];
            i++;

        } while (true);

        System.out.println("Полиадическое представление этого числа: "
                + Arrays.toString(polyNumber.toArray()).replace("[", "(").replace("]", ")"));
    }
}
