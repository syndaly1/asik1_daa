package algo;

import java.util.Arrays;

public final class ClosestPair {
    private ClosestPair() {}

    public static class Point {
        public final double x, y;
        public Point(double x, double y) { this.x = x; this.y = y; }
    }

    public static double closest(Point[] pts) {
        if (pts == null || pts.length < 2) return Double.POSITIVE_INFINITY;
        Point[] px = pts.clone();
        Arrays.sort(px, (p, q) -> Double.compare(p.x, q.x));
        Point[] py = px.clone();
        Arrays.sort(py, (p, q) -> Double.compare(p.y, q.y));
        return rec(px, py, 0, pts.length - 1);
    }

    private static double rec(Point[] px, Point[] py, int l, int r) {
        if (r - l <= 3) return brute(px, l, r);
        int m = (l + r) / 2;
        Point mid = px[m];
        Point[] left = Arrays.copyOfRange(py, 0, m - l + 1);
        Point[] right = Arrays.copyOfRange(py, m - l + 1, r - l + 1);
        double d1 = rec(px, left, l, m);
        double d2 = rec(px, right, m + 1, r);
        double d = Math.min(d1, d2);
        return Math.min(d, strip(py, mid.x, d));
    }

    private static double brute(Point[] a, int l, int r) {
        double best = Double.POSITIVE_INFINITY;
        for (int i = l; i <= r; i++)
            for (int j = i + 1; j <= r; j++)
                best = Math.min(best, dist(a[i], a[j]));
        return best;
    }

    private static double strip(Point[] py, double midX, double d) {
        double best = d;
        for (int i = 0; i < py.length; i++) {
            for (int j = i + 1; j < py.length && (py[j].y - py[i].y) < d; j++) {
                if (Math.abs(py[j].x - midX) < d)
                    best = Math.min(best, dist(py[i], py[j]));
            }
        }
        return best;
    }

    private static double dist(Point p, Point q) {
        double dx = p.x - q.x, dy = p.y - q.y;
        return Math.sqrt(dx * dx + dy * dy);
    }
}
