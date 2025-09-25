package algo;

import java.util.Arrays;

public final class DeterministicSelect {
    private DeterministicSelect() {}

    public static int select(int[] a, int k) {
        if (a == null || a.length == 0) throw new IllegalArgumentException("empty array");
        if (k < 0 || k >= a.length) throw new IllegalArgumentException("k out of range");
        Metrics.reset();
        try (var t = new Metrics.Timer()) {
            return select(a, 0, a.length, k);
        }
    }

    private static int select(int[] a, int l, int r, int k) {
        while (true) {
            int n = r - l;
            if (n <= 10) {
                Arrays.sort(a, l, r);
                return a[l + k];
            }
            Metrics.current().enter();
            int pivot = medianOfMedians(a, l, r);
            int[] bounds = partitionAroundPivot(a, l, r, pivot);
            int lt = bounds[0], gt = bounds[1];
            if (l + k < lt) {
                r = lt;
            } else if (l + k >= gt) {
                k -= (gt - l);
                l = gt;
            } else {
                Metrics.current().leave();
                return pivot;
            }
            Metrics.current().leave();
        }
    }

    private static int medianOfMedians(int[] a, int l, int r) {
        int n = r - l;
        int groups = (n + 4) / 5;
        for (int i = 0; i < groups; i++) {
            int gL = l + i * 5;
            int gR = Math.min(gL + 5, r);
            Arrays.sort(a, gL, gR);
            int medianIdx = gL + (gR - gL - 1) / 2;
            Util.swap(a, l + i, medianIdx);
        }
        int mid = groups / 2;
        return select(a, l, l + groups, mid);
    }

    private static int[] partitionAroundPivot(int[] a, int l, int r, int pivot) {
        int lt = l, i = l, gt = r;
        while (i < gt) {
            Metrics.current().comparisons++; // a[i] < pivot ?
            if (a[i] < pivot)      Util.swap(a, lt++, i++);
            else {
                Metrics.current().comparisons++; // a[i] > pivot ?
                if (a[i] > pivot) Util.swap(a, --gt, i);
                else              i++;
            }
        }
        return new int[]{lt, gt};
    }
}
