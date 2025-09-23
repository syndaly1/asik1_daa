package algo;

import java.util.Random;

public final class QuickSort {
    private static final Random R = new Random();
    private QuickSort() {}

    public static void sort(int[] a) {
        if (a == null || a.length <= 1) return;
        Metrics.reset();
        try (var t = new Metrics.Timer()) {
            sort(a, 0, a.length - 1);
            long elapsed = t.elapsedNanos();
        }
    }

    private static void sort(int[] a, int l, int r) {
        if (l >= r) return;
        Metrics.current().enter();
        int pivot = a[l + R.nextInt(r - l + 1)];
        int i = l, j = r;
        while (i <= j) {
            while (true) { Metrics.current().comparisons++; if (!(a[i] < pivot)) break; i++; }
            while (true) { Metrics.current().comparisons++; if (!(a[j] > pivot)) break; j--; }
            if (i <= j) { Util.swap(a, i, j); i++; j--; }
        }
        if (l < j) sort(a, l, j);
        if (i < r) sort(a, i, r);
        Metrics.current().leave();
    }
}
