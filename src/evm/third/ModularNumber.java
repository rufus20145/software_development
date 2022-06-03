package evm.third;

import java.util.Arrays;

public class ModularNumber {
    /**
     *
     */
    private static final String DIFFERENT_BASES_WARNING = "ВНИМАНИЕ! У двух чисел разные основания систем счисления. Выходной результат может быть неправильным.";
    private static final int DEFAULT_NUMBER_OF_DIGITS = 6;
    private static final String DEFAULT_LABEL = "a";
    private long[] base;
    private long[] number;
    private String label;

    public ModularNumber(long[] base, long decimalValue) {
        this(base, decimalValue, DEFAULT_NUMBER_OF_DIGITS, DEFAULT_LABEL);
    }

    public ModularNumber(long[] base, long decimalValue, String label) {
        this(base, decimalValue, DEFAULT_NUMBER_OF_DIGITS, label);
    }

    public ModularNumber(long[] base, long decimalValue, int numberOfDigits, String label) {
        this.base = base;
        this.number = convertDecimalToModularWithComments(base, decimalValue, numberOfDigits, label);
        this.label = label;

        System.out.println(
                "Модулярное представление числа " + decimalValue + " равно " + this.label + " = " + this.toString()
                        + "\n");
    }

    public ModularNumber(long[] base, long[] number, String label) {
        this.base = base;
        this.number = number;
        this.label = label;

        System.out.println("Модулярное представление " + this.label + " = " + this.toString() + "\n");
    }

    public ModularNumber add(ModularNumber another, String newLabel) {
        System.out.println("===================================\n  Складываем два числа");

        if (!Arrays.equals(this.base, another.base)) {
            System.out.println(
                    DIFFERENT_BASES_WARNING);
        }
        long[] tmp = new long[this.number.length];
        for (int i = 0; i < tmp.length; i++) {
            tmp[i] = this.number[i] + another.number[i]
                    - (long) Math.floor((this.number[i] + another.number[i] * 1.0) / this.base[i]) * this.base[i];
            System.out.printf("%s_%d = %d + %d - [%d + %d / %d] * %d = %d%n", newLabel, i + 1, this.number[i],
                    another.number[i],
                    this.number[i], another.number[i], base[i], base[i], tmp[i]);
        }
        return new ModularNumber(this.base, tmp, newLabel);
    }

    public ModularNumber multiply(ModularNumber another, String newLabel) {
        System.out.println("===================================\n  Умножаем два числа");
        if (!Arrays.equals(this.base, another.base)) {
            System.out.println(
                    DIFFERENT_BASES_WARNING);
        }

        long[] tmp = new long[this.number.length];
        for (int i = 0; i < tmp.length; i++) {
            tmp[i] = this.number[i] * another.number[i]
                    - (long) Math.floor((this.number[i] * another.number[i] * 1.0) / this.base[i]) * this.base[i];
            System.out.printf("%s_%d = %d * %d - [%d * %d / %d] * %d = %d%n", newLabel, i + 1, this.number[i],
                    another.number[i],
                    this.number[i], another.number[i], base[i], base[i], tmp[i]);
        }

        return new ModularNumber(this.base, tmp, newLabel);

    }

    public ModularNumber subtract(ModularNumber another, String newLabel) {
        System.out.println("===================================\n  Вычитаем число из числа");

        if (!Arrays.equals(this.base, another.base)) {
            System.out.println(DIFFERENT_BASES_WARNING);
        }
        long[] tmp = new long[this.number.length];
        for (int i = 0; i < tmp.length; i++) {
            tmp[i] = this.number[i] - another.number[i]
                    - (long) Math.floor((this.number[i] - another.number[i] * 1.0) / this.base[i]) * this.base[i];
            System.out.printf("%s_%d = %d - %d - [%d - %d / %d] * %d = %d%n", newLabel, i + 1, this.number[i],
                    another.number[i],
                    this.number[i], another.number[i], base[i], base[i], tmp[i]);
        }
        return new ModularNumber(this.base, tmp, newLabel);
    }

    private long[] convertDecimalToModularWithComments(long[] base, long decimalValue, int numberOfDigits,
            String label) {
        System.out.println("===================================\n  Конвертируем число " + decimalValue);
        long[] tmp = new long[Math.min(base.length, numberOfDigits)];
        for (int i = 0; i < tmp.length; i++) {
            tmp[i] = decimalValue - (decimalValue / base[i]) * base[i];
            System.out.printf("%s_%d = %d - [%d / %d] * %d = %d%n", label, i + 1, decimalValue, decimalValue, base[i],
                    base[i], tmp[i]);
        }

        return tmp;
    }

    public String toString() {
        return Arrays.toString(number).replace("[", "(").replace("]", ")");
    }

}
