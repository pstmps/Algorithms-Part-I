

import org.junit.jupiter.api.Test;

// import module3.src.main.java.ThreeSum;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

public class ThreeSumTest {

    @Test
    public void testThreeSum() {
        ThreeSum threeSum = new ThreeSum();

        // Test case 1: General case
        int[] nums1 = {-1, 0, 1, 2, -1, -4};
        List<List<Integer>> expected1 = Arrays.asList(
            Arrays.asList(-1, -1, 2),
            Arrays.asList(-1, 0, 1)
        );
        List<List<Integer>> result1 = threeSum.threeSum(nums1);
        assertEquals(expected1.size(), result1.size());
        assertTrue(result1.containsAll(expected1));

        // Test case 2: No triplets
        int[] nums2 = {1, 2, -2, -1};
        List<List<Integer>> expected2 = Arrays.asList();
        List<List<Integer>> result2 = threeSum.threeSum(nums2);
        assertEquals(expected2.size(), result2.size());
        assertTrue(result2.containsAll(expected2));

        // Test case 3: All zeros
        int[] nums3 = {0, 0, 0, 0};
        List<List<Integer>> expected3 = Arrays.asList(
            Arrays.asList(0, 0, 0)
        );
        List<List<Integer>> result3 = threeSum.threeSum(nums3);
        assertEquals(expected3.size(), result3.size());
        assertTrue(result3.containsAll(expected3));

        // Test case 4: Larger array
        int[] nums4 = {-4, -2, -1, 0, 1, 2, 3, 4};
        List<List<Integer>> expected4 = Arrays.asList(
            Arrays.asList(-4, 0, 4),
            Arrays.asList(-4, 1, 3),
            // Arrays.asList(-4, 2, 2),
            Arrays.asList(-2, -1, 3),
            Arrays.asList(-2, 0, 2),
            Arrays.asList(-1, 0, 1)
        );
        List<List<Integer>> result4 = threeSum.threeSum(nums4);
        assertEquals(expected4.size(), result4.size());
        assertTrue(result4.containsAll(expected4));
    }

     @Test
    public void testThreeSumPerformance() {
        ThreeSum threeSum = new ThreeSum();
        int size = 500;
        int maxSize = 64000;

        System.out.println("Profiling 3Sum algorithm runtime:");
        while (size <= maxSize) {
            int[] nums = generateRandomArray(size);
            long startTime = System.currentTimeMillis();
            threeSum.threeSum(nums);
            long endTime = System.currentTimeMillis();
            long duration = endTime - startTime;
            System.out.println("Array size: " + size + " - Time taken: " + duration + " ms");
            size *= 2;
        }
    }

    private int[] generateRandomArray(int size) {
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = (int) (Math.random() * 20001) - 10000; // Random integers between -10000 and 10000
        }
        return array;
    }
}