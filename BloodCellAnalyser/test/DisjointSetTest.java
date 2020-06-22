import static org.junit.Assert.*;

import Other.DisjointSet;
import org.junit.Test;

public class DisjointSetTest {
    int[] array1 = new int[]{0, 1, 1, 3, 2, 1, 2, 3,7,1,8};
    int[] array2 = new int[]{1,1,1,2,1,2,3,4};

    /**
     * Tests the first array
     */
    @Test
    public void findTestArray1() {
        assertEquals("Should find 1",1, DisjointSet.find(array1,5));
        assertEquals("Should find 3",3,DisjointSet.find(array1,10));
    }

    /**
     * Tests the second array
     */
    @Test
    public void findTestArray2() {
        assertEquals("", 1, DisjointSet.find(array2, 4));
        assertEquals("", 1, DisjointSet.find(array2, 7));
    }
}
