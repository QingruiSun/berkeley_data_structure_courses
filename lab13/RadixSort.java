import java.util.ArrayList;
import java.util.List;

/**
 * Class for doing Radix sort
 *
 * @author Akhil Batra, Alexander Hwang
 *
 */
public class RadixSort {
    /**
     * Does LSD radix sort on the passed in array with the following restrictions:
     * The array can only have ASCII Strings (sequence of 1 byte characters)
     * The sorting is stable and non-destructive
     * The Strings can be variable length (all Strings are not constrained to 1 length)
     *
     * @param asciis String[] that needs to be sorted
     *
     * @return String[] the sorted array
     */
    public static String[] sort(String[] asciis) {
        // Implement LSD Sort
        if (asciis == null) {
            return null;
        }
        int arrarLength = asciis.length;
        int maxStringLength = 0;
        for (String oneString : asciis) {
            if (oneString.length() > maxStringLength) {
                maxStringLength = oneString.length();
            }
        }
        String[] resultString = new String[arrarLength];
        /* Copy from old array to new array to maintain the non-destructive */
        for (int i = 0; i < arrarLength; ++i) {
            resultString[i] = asciis[i];
        }
        for (int i = maxStringLength - 1; i >= 0; --i) {
            sortHelperLSD(resultString, i);
        }
        return resultString;
    }

    /**
     * LSD helper method that performs a destructive counting sort the array of
     * Strings based off characters at a specific index.
     * @param asciis Input array of Strings
     * @param index The position to sort the Strings on.
     */
    private static void sortHelperLSD(String[] asciis, int index) {
        // The sort should be stable.
        // Some placeholder may exists in the asciis
        // 257 = 256 + 1. 256 is the number of ascii code. 1 represents no ascii code in
        // this index of the string.
        List<List<String>> stringLists = new ArrayList<>();
        for (int i = 0; i < 257; ++i) {
            stringLists.add(new ArrayList<>());
        }
        int arrLength = asciis.length;
        for (int i = 0; i < arrLength; ++i) {
            if (asciis[i].length() <= index) {
                stringLists.get(0).add(asciis[i]);
            } else {
                int listIndex = ((int) asciis[i].charAt(index)) + 1; // Range from 1 to 256.
                stringLists.get(listIndex).add(asciis[i]);
            }
        }
        int arrIndex = 0;
        for (int i = 0; i < 257; ++i) {
            for (int j = 0; j < stringLists.get(i).size(); ++j) {
                asciis[arrIndex++] = stringLists.get(i).get(j);
            }
        }
    }

    /**
     * MSD radix sort helper function that recursively calls itself to achieve the sorted array.
     * Destructive method that changes the passed in array, asciis.
     *
     * @param asciis String[] to be sorted
     * @param start int for where to start sorting in this method (includes String at start)
     * @param end int for where to end sorting in this method (does not include String at end)
     * @param index the index of the character the method is currently sorting on
     *
     **/
    private static void sortHelperMSD(String[] asciis, int start, int end, int index) {
        // Optional MSD helper method for optional MSD radix sort
        if (end <= start + 1) {
            return;
        }
        List<List<String>> stringLists = new ArrayList<>();
        for (int i = 0; i < 257; ++i) {
            stringLists.add(new ArrayList<>());
        }
        for (int i = start; i < end; ++i) {
            if (asciis[i].length() <= index) {
                stringLists.get(0).add(asciis[i]);
            } else {
                int listIndex = ((int) asciis[i].charAt(index)) + 1; // Range from 1 to 256.
                stringLists.get(listIndex).add(asciis[i]);
            }
        }
        int arrIndex = start;
        for (int i = 0; i < 257; ++i) {
            for (int j = 0; j < stringLists.get(i).size(); ++j) {
                asciis[arrIndex++] = stringLists.get(i).get(j);
            }
        }
    }
}
