package algo;

import org.junit.jupiter.api.Test;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.*;

public class MergeSortTest {
    @Test
    void testSort() {
        int[] a = {5, 2, 9, -1, 3};
        int[] b = a.clone();
        MergeSort.sort(a);
        Arrays.sort(b);
        assertArrayEquals(b, a);
    }
}

