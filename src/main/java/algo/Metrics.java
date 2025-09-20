package algo;

public final class Metrics {
    private static final ThreadLocal<Metrics> LOCAL = ThreadLocal.withInitial(Metrics::new);

    public long comparisons;
    public long allocations;
    public int maxDepth;
    private int depth;

    public static Metrics current() {
        return LOCAL.get();
    }

    public static void reset() {
        LOCAL.set(new Metrics());
    }

    public void enter() {
        if (++depth > maxDepth) maxDepth = depth;
    }

    public void leave() {
        --depth;
    }

    public static final class Timer implements AutoCloseable {
        private final long start = System.nanoTime();

        public long elapsedNanos() {
            return System.nanoTime() - start;
        }

        @Override
        public void close() {}
    }
}
