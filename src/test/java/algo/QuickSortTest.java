package algo;

import org.junit.jupiter.api.Test;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.*;

public class QuickSortTest {
    @Test
    void testSort() {
        int[] a = {10, 7, 8, 9, 1, 5};
        int[] b = a.clone();
        QuickSort.sort(a);
        Arrays.sort(b);
        assertArrayEquals(b, a);
    }
}

