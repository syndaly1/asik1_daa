package algo;

import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.Random;
import static org.junit.jupiter.api.Assertions.*;

public class DeterministicSelectTest {
    @Test
    void testSelect() {
        Random r = new Random(1);
        int[] arr = new int[50];
        for (int i = 0; i < arr.length; i++) arr[i] = r.nextInt(100);
        int[] copy = arr.clone();
        Arrays.sort(copy);
        int k = 10;
        int expected = copy[k];
        int actual = DeterministicSelect.select(arr, k);
        assertEquals(expected, actual);
    }
}
