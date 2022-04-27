package evm;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Main {
    private static final int STRING_FORMAT_EXIT_CODE = 1;
    private static final String BASE_REGEX = "^([1-9]\\d{0,100} ?){1,100}$";
    private static final int POSITIONAL = 1;
    private static final int POLYODIC = 2;
    private static final int MODULAR = 3;

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        String scannedBase;
        int baseType;

        System.out.println("Выберите тип вашей системы счисления:\n1)Позиционная\n2)Полиодическая\n3)Модулярная");
        do {
            baseType = getIntValue(input);
            if (!Arrays.asList(NumberSystem.Type.allTypes).contains(baseType)) {
                System.out.println("Такого номера в списке нет. Повторите попытку.");
            }
        } while (!Arrays.asList(NumberSystem.Type.allTypes).contains(baseType));

        switch (baseType) {
            case POSITIONAL:
                System.out.println("Введите основание системы счисления");
                int a = getIntValue(input);
                break;
            case POLYODIC:
            case MODULAR:
                System.out.println(
                        "Введите строку с вашими основаниями системы счисления в формате \"1 2 3 4 5 6\" без кавычек");
                do {
                    scannedBase = input.nextLine();
                    if (!scannedBase.matches(BASE_REGEX)) {
                        System.err.println("Строка не соответствует формату ввода оснвания. Самоликвидируюсь.");
                    }
                } while (!scannedBase.matches(BASE_REGEX));
                break;
            default:
                break;
        }

    }

    private static int getIntValue(Scanner input) {
        int value = 0;
        boolean exceptionCaught = false;
        do {
            exceptionCaught = false;
            try {
                value = input.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Введенное значение не является целым числом. Повторите попытку.");
                exceptionCaught = true;
                input.nextLine();
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
        input.nextLine();
        return value;
    }

}
