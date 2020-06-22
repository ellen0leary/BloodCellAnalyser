package Other;

public class DisjointSet {
    /**
     * Find the root of the id through iteration
     * @param a the array
     * @param id the id
     * @return returns the id if it matches the array[index] or it call the method
     */
    public static int find(int[] a ,int id) {
        return a[id] == id ? id : find(a, a[id]);
    }

    /**
     * unions the two ints together
     * @param a array
     * @param p first int
     * @param q second int
     */
    public static  void union(int[] a, int p, int q){
        a[find(a,q)] =  find(a,p);
    }
}
