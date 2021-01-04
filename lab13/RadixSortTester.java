import org.junit.Test;

public class RadixSortTester {

    @Test
    public void testSort() {
        String[] strings = {"Alice", "Zick", "Helen", "Nick", "Marxis", "D34G", "Aadsf4-"};
        String[] resultStrings = RadixSort.sort(strings);
        for (String i : strings) {
            System.out.print(i + " ");
        }
        System.out.println();
        for (String i : resultStrings) {
            System.out.print(i + " ");
        }
    }


}
