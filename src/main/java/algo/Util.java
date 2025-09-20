package algo;

import java.util.Random;

public final class Util {
    private static final Random R = new Random();
    private Util(){}

    public static void swap(int[] a, int i, int j) {
        int t = a[i]; a[i] = a[j]; a[j] = t;
    }

    public static void shuffle(int[] a) {
        for (int i = a.length - 1; i > 0; i--) {
            int j = R.nextInt(i + 1);
            swap(a, i, j);
        }
    }

    public static int[] randomIntArray(int n, int lo, int hi) {
        int[] a = new int[n];
        for (int i = 0; i < n; i++) a[i] = lo + R.nextInt(hi - lo + 1);
        return a;
    }

    public static ClosestPair.Point[] randomPoints(int n) {
        ClosestPair.Point[] p = new ClosestPair.Point[n];
        for (int i = 0; i < n; i++) p[i] = new ClosestPair.Point(R.nextDouble(), R.nextDouble());
        return p;
    }
}

