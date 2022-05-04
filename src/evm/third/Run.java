package evm.third;

import java.util.Scanner;

public class Run {
    private static final int BASE_SIZE = 8;

    public static void main(String[] args) {

        System.out.print("Введите ваши исходные данные.\nНомер студента в списке группы: ");
        Scanner input = new Scanner(System.in);
        long studentNumber = input.nextInt();
        System.out.print(
                "Введите вашу константу рождения с учетом ведущих нулей по частям (например для 06071902 сначала 06, потом 07, потом 02): ");
        long dd = input.nextInt();
        long mm = input.nextInt();
        long gg = input.nextInt(); // ! нигде не используется

        // формируем числовые величины
        long a = (studentNumber % 10) * 10000 + dd * 100 + mm;
        long b = (studentNumber % 10) * 10000 + mm * 100 + dd;
        long v = dd * 1000 + (studentNumber % 10) * 100 + mm;
        long u = mm * 1000 + (studentNumber % 10) * 100 + dd;

        System.out.printf("Ваши числовые величины: %05d, %05d, %05d, %05d%n", a, b, v, u);

        long[] base = new long[BASE_SIZE];

        System.out.print("Введите основание вашей системы счисления из таблицы (все 8 чисел): ");
        for (int i = 0; i < BASE_SIZE; i++) {
            base[i] = input.nextInt();
        }

        System.out.println("\nЗадание 3. Выполняю переход к модулярному представлению-вектору.");

        ModularNumber aMod = new ModularNumber(base, a, "a");
        ModularNumber bMod = new ModularNumber(base, b, "b");
        ModularNumber vMod = new ModularNumber(base, v, "v");
        ModularNumber uMod = new ModularNumber(base, u, "u");

        System.out.println("\nЗадание 4. Выполняю операцию свёртки.");

        ModularNumber aXbMod = aMod.multiply(bMod, "a*b");
        ModularNumber vXuMod = vMod.multiply(uMod, "v*u");
        ModularNumber aXuMod = aMod.multiply(uMod, "a*u");
        ModularNumber sumMod = aXbMod.add(vXuMod, "sum");
        ModularNumber wModComputed = sumMod.subtract(aXuMod, "w");

        System.out.println("Задание 5. Выполняю операцию над десятичными представлениями.");
        long w = a * b + u * v - a * u;
        ModularNumber wMod = new ModularNumber(base, w, "w");

        input.close();

    }
}
