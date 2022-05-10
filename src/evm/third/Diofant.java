package evm.third;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Реализация решения диофантовых уравнений с помощью алгоритма Евклида
 */
public class Diofant {
    private long a;
    private long b;
    private long c;

    public Diofant(long a, long b, long c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    public void solve() {
        System.out.printf("Решаю следующее диофантово уравнение: %d * (|e|g)^-1 - %d * k = %d%n", a, b, c);
        long[] g = findGeshki();
        long[] x = new long[g.length];
        long[] y = new long[g.length];
        long[] v = new long[g.length];
        long[] u = new long[g.length];
        x[0] = 1;
        x[1] = 0;
        y[0] = 0;
        y[1] = 1;

        v[0] = x[0] - g[0] * x[1];
        // x[0] = g[0] * x[1] + v[0];

        v[1] = x[1] - g[1] * v[0];
        x[2] = v[0];
        // x[1] = g[1] * v[0] + v[1];

        v[2] = x[2] - g[2] * v[1];
        x[3] = v[1];
        // x[2] = g[2] * v[1] + v[2];

        v[3] = x[3] - g[3] * v[2];
        x[4] = v[2];
        // x[3] = g[3] * v[2] + v[3];

        v[4] = x[4] - g[4] * v[3];
        x[5] = v[3];
        // x[4] = g[4] * v[3] + v[4];

        v[5] = x[5] - g[5] * v[4];
        // x[5] = g[5] * v[4] + v[5];

        u[0] = y[0] - g[0] * y[1];
        // y[0] = g[0] * y[1] + u[0];

        u[1] = y[1] - g[1] * u[0];
        y[2] = u[0];
        // y[1] = g[1] * u[0] + u[1];

        u[2] = y[2] - g[2] * u[1];
        y[3] = u[1];
        // y[2] = g[2] * u[1] + u[2];

        u[3] = y[3] - g[3] * u[2];
        y[4] = u[2];
        // y[3] = g[3] * u[2] + u[3];

        u[4] = y[4] - g[4] * u[3];
        y[5] = u[3];
        // y[4] = g[4] * u[3] + u[4];

        u[5] = y[5] - g[5] * u[4];
        // y[5] = g[5] * u[4] + u[5];

        System.out.println("Массив иксов: \t" + Arrays.toString(x));
        System.out.println("Массив вешек: \t" + Arrays.toString(v));

        System.out.println("Массив игреков: " + Arrays.toString(y));
        System.out.println("Массив ушек: \t" + Arrays.toString(u));

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
        long ai = this.a;
        long bi = this.b;
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
