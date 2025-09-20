package algo;

import java.util.Arrays;
import java.util.Random;

public final class Main {
    private static final int REPEATS = 3;

    public static void main(String[] args) {
        ensureHeader();

        int[] sizes = {1000, 2000, 4000, 8000, 16000};

        for (int n : sizes) {
            for (int trial = 1; trial <= REPEATS; trial++) {
                int[] base = randomIntArray(n, -1_000_000, 1_000_000);

                int[] a1 = base.clone();
                Metrics.reset();
                long ms1 = timeMs(new Runnable() {
                    @Override public void run() { MergeSort.sort(a1); }
                });
                append("mergesort", n, trial, ms1);

                int[] a2 = base.clone();
                Metrics.reset();
                long ms2 = timeMs(new Runnable() {
                    @Override public void run() { QuickSort.sort(a2); }
                });
                append("quicksort", n, trial, ms2);

                int[] a3 = base.clone();
                int k = a3.length / 2;
                Metrics.reset();
                long ms3 = timeMs(new Runnable() {
                    @Override public void run() { DeterministicSelect.select(a3, k); }
                });
                append("select", n, trial, ms3);

                final int ptsN = Math.max(1000, n / 2);
                ClosestPair.Point[] pts = randomPoints(ptsN);
                Metrics.reset();
                long ms4 = timeMs(new Runnable() {
                    @Override public void run() { ClosestPair.closest(pts); }
                });
                append("closest", ptsN, trial, ms4);
            }
        }

        int[] check = randomIntArray(20, -50, 50);
        int[] ref = check.clone(); Arrays.sort(ref);
        int[] m = check.clone(); MergeSort.sort(m);
        int[] q = check.clone(); QuickSort.sort(q);
        System.out.println("OK_MergeSort=" + Arrays.equals(m, ref));
        System.out.println("OK_QuickSort=" + Arrays.equals(q, ref));
        System.out.println("Done. Results -> metrics.csv");
    }

    private static void ensureHeader() {
        SimpleCsv.append("metrics.csv",
                "algo,n,trial,elapsed_ms,comparisons,maxDepth",
                "algo,n,trial,elapsed_ms,comparisons,maxDepth"); // при первом запуске запишется заголовок
    }

    private static void append(String algoName, int n, int trial, long elapsedMs) {
        String line = String.join(",",
                algoName,
                String.valueOf(n),
                String.valueOf(trial),
                String.valueOf(elapsedMs),
                String.valueOf(Metrics.current().comparisons),
                String.valueOf(Metrics.current().maxDepth)
        );

        SimpleCsv.append("metrics.csv",
                "algo,n,trial,elapsed_ms,comparisons,maxDepth",
                line);

        String perAlgoFile = algoName + ".csv";
        SimpleCsv.append(perAlgoFile,
                "algo,n,trial,elapsed_ms,comparisons,maxDepth",
                line);
    }


    private static long timeMs(Runnable r) {
        long start = System.nanoTime();
        r.run();
        return (System.nanoTime() - start) / 1_000_000L;
    }

    private static int[] randomIntArray(int len, int lo, int hi) {
        Random rnd = new Random(1);
        int[] a = new int[len];
        for (int i = 0; i < len; i++) a[i] = lo + rnd.nextInt(hi - lo + 1);
        return a;
    }

    private static ClosestPair.Point[] randomPoints(int n) {
        Random r = new Random(1);
        ClosestPair.Point[] p = new ClosestPair.Point[n];
        for (int i = 0; i < n; i++) p[i] = new ClosestPair.Point(r.nextDouble(), r.nextDouble());
        return p;
    }
}
