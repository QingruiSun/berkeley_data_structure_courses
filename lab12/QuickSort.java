import edu.princeton.cs.algs4.Queue;

public class QuickSort {
    /**
     * Returns a new queue that contains the given queues catenated together.
     *
     * The items in q2 will be catenated after all of the items in q1.
     */
    private static <Item extends Comparable> Queue<Item> catenate(Queue<Item> q1, Queue<Item> q2) {
        Queue<Item> catenated = new Queue<Item>();
        for (Item item : q1) {
            catenated.enqueue(item);
        }
        for (Item item: q2) {
            catenated.enqueue(item);
        }
        return catenated;
    }

    /** Returns a random item from the given queue. */
    private static <Item extends Comparable> Item getRandomItem(Queue<Item> items) {
        int pivotIndex = (int) (Math.random() * items.size());
        Item pivot = null;
        // Walk through the queue to find the item at the given index.
        for (Item item : items) {
            if (pivotIndex == 0) {
                pivot = item;
                break;
            }
            pivotIndex--;
        }
        return pivot;
    }

    /**
     * Partitions the given unsorted queue by pivoting on the given item.
     *
     * @param unsorted  A Queue of unsorted items
     * @param pivot     The item to pivot on
     * @param less      An empty Queue. When the function completes, this queue will contain
     *                  all of the items in unsorted that are less than the given pivot.
     * @param equal     An empty Queue. When the function completes, this queue will contain
     *                  all of the items in unsorted that are equal to the given pivot.
     * @param greater   An empty Queue. When the function completes, this queue will contain
     *                  all of the items in unsorted that are greater than the given pivot.
     */
    private static <Item extends Comparable> void partition(
            Queue<Item> unsorted, Item pivot,
            Queue<Item> less, Queue<Item> equal, Queue<Item> greater) {
        for (Item nowItem : unsorted) {
            if (nowItem.compareTo(pivot) < 0) {
                less.enqueue(nowItem);
            } else if (nowItem.compareTo(pivot) == 0) {
                equal.enqueue(nowItem);
            } else {
                greater.enqueue(nowItem);
            }
        }
    }

    /** Returns a Queue that contains the given items sorted from least to greatest. */
    public static <Item extends Comparable> Queue<Item> quickSort(
            Queue<Item> items) {
        if (items == null) {
            return items;
        }
        /* If the size of the items is less than 1, than the items needn't to be sorted */
        if (items.size() <= 1) {
            return items;
        }
        Item randomItem = getRandomItem(items);
        Queue<Item> lessQueue = new Queue<>();
        Queue<Item> greaterQueue = new Queue<>();
        Queue<Item> equalQueue = new Queue<>();
        partition(items, randomItem, lessQueue, equalQueue, greaterQueue);
        Queue<Item> sortedLessQueue = quickSort(lessQueue);
        Queue<Item> sortedGreaterQueue = quickSort(greaterQueue);
        Queue<Item> resultQueue = new Queue<>();
        for (Item nowItem : sortedLessQueue) {
            resultQueue.enqueue(nowItem);
        }
        for (Item nowItem : equalQueue) {
            resultQueue.enqueue(nowItem);
        }
        for (Item nowItem : sortedGreaterQueue) {
            resultQueue.enqueue(nowItem);
        }
        return resultQueue;
    }

    public static void main(String[] args) {
        Queue<String> queue = new Queue<>();
        queue.enqueue("Cindy");
        queue.enqueue("Dick");
        queue.enqueue("Alice");
        queue.enqueue("Bob");
        queue.enqueue("Alabama");
        Queue<String> resultQueue = quickSort(queue);
        System.out.println(queue);
        System.out.println(resultQueue);
    }
}
