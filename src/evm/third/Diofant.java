package evm.third;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Реализация решения диофантовых уравнений с помощью алгоритма Евклида
 */
public class Diofant {
    private long k1;
    private long k2;

    public Diofant(long k1, long k2) {
        this.k1 = k1;
        this.k2 = k2;
    }

    public void solve() {
        System.out.printf("Решаю следующее диофантово уравнение: %d * (|e|g)^-1 - %d * k = 1%n", k1, k2);
        List<Long> x = new ArrayList<>();
        List<Long> y = new ArrayList<>();
        List<Long> a = new ArrayList<>();
        int i = 1;
        x.add((long) 1);
        x.add((long) 0);
        y.add((long) 0);
        y.add((long) 1);
        do {
            long g = (a.get(i - 1) % a.get(i));
            
        } while ((a.get(i - 1) % a.get(i)) == 0);

        System.out.println("Массив иксов: \t" + Arrays.toString(x.toArray()));

        System.out.println("Массив игреков: " + Arrays.toString(y.toArray()));

        System.out.println("Массив ашек: \t" + Arrays.toString(a.toArray()));

        // for (int i = 1; i < g.length; i++) {
        // xi = x1;
        // yi = y1;
        // x1 = resX;
        // y1 = resY;
        // resX = xi - g[i] * resX;
        // resY = yi - g[i] * resY;

        // }

        // System.out.printf("X и Y равны %d, %d%n", resX, resY);
    }

    private long[] findGeshki() {
        List<Long> gList = new ArrayList<>();
        long ai = this.k1;
        long bi = this.k2;
        long zi = ai - (ai / bi) * bi;
        System.out.printf("%d = [%d / %d] * %d + %d%n", ai, ai, bi, bi, zi);
        gList.add(ai / bi);
        do {
            ai = bi;
            bi = zi;
            zi = ai - (ai / bi) * bi;
            gList.add(ai / bi);
            System.out.printf("%d = [%d / %d] * %d + %d%n", ai, ai, bi, bi, zi);
        } while (zi > 0);

        System.out.println("Гешки равны: \t" + Arrays.toString(gList.toArray()));
        return gList.stream().mapToLong(i -> i).toArray();
    }

    public String getResult() {
        return null;
    }
}
