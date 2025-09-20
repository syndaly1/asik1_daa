package algo;

public final class MergeSort {
    private MergeSort() {}

    public static void sort(int[] a) {
        if (a == null || a.length <= 1) return;
        Metrics.reset();
        int[] buf = new int[a.length];
        try (var t = new Metrics.Timer()) {
            sort(a, 0, a.length, buf);
            long elapsed = t.elapsedNanos();
        }
    }

    private static void sort(int[] a, int l, int r, int[] buf) {
        if (r - l <= 1) return;
        Metrics.current().enter();
        int m = (l + r) / 2;
        sort(a, l, m, buf);
        sort(a, m, r, buf);
        merge(a, l, m, r, buf);
        Metrics.current().leave();
    }

    private static void merge(int[] a, int l, int m, int r, int[] buf) {
        int i = l, j = m, k = 0;
        while (i < m && j < r) {
            Metrics.current().comparisons++;
            if (a[i] <= a[j]) buf[k++] = a[i++];
            else              buf[k++] = a[j++];
        }
        while (i < m) buf[k++] = a[i++];
        while (j < r) buf[k++] = a[j++];
        System.arraycopy(buf, 0, a, l, k);
    }
}
