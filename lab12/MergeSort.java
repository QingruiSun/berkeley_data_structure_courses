import edu.princeton.cs.algs4.Queue;

public class MergeSort {
    /**
     * Removes and returns the smallest item that is in q1 or q2.
     *
     * The method assumes that both q1 and q2 are in sorted order, with the smallest item first. At
     * most one of q1 or q2 can be empty (but both cannot be empty).
     *
     * @param   q1  A Queue in sorted order from least to greatest.
     * @param   q2  A Queue in sorted order from least to greatest.
     * @return      The smallest item that is in q1 or q2.
     */
    private static <Item extends Comparable> Item getMin(
            Queue<Item> q1, Queue<Item> q2) {
        if (q1.isEmpty()) {
            return q2.dequeue();
        } else if (q2.isEmpty()) {
            return q1.dequeue();
        } else {
            // Peek at the minimum item in each queue (which will be at the front, since the
            // queues are sorted) to determine which is smaller.
            Comparable q1Min = q1.peek();
            Comparable q2Min = q2.peek();
            if (q1Min.compareTo(q2Min) <= 0) {
                // Make sure to call dequeue, so that the minimum item gets removed.
                return q1.dequeue();
            } else {
                return q2.dequeue();
            }
        }
    }

    /** Returns a queue of queues that each contain one item from items. */
    private static <Item extends Comparable> Queue<Queue<Item>>
            makeSingleItemQueues(Queue<Item> items) {
        Queue<Queue<Item>> queues = new Queue<>();
        if (items == null) {
            return queues;
        }
        for (Item nowItem : items) {
            Queue<Item> nowQueue = new Queue<>();
            nowQueue.enqueue(nowItem);
            queues.enqueue(nowQueue);
        }
        return queues;
    }

    /**
     * Returns a new queue that contains the items in q1 and q2 in sorted order.
     *
     * This method should take time linear in the total number of items in q1 and q2.  After
     * running this method, q1 and q2 will be empty, and all of their items will be in the
     * returned queue.
     *
     * @param   q1  A Queue in sorted order from least to greatest.
     * @param   q2  A Queue in sorted order from least to greatest.
     * @return      A Queue containing all of the q1 and q2 in sorted order, from least to
     *              greatest.
     *
     */
    private static <Item extends Comparable> Queue<Item> mergeSortedQueues(
            Queue<Item> q1, Queue<Item> q2) {
        if (q1 == null) {
            return q2;
        }
        if (q2 == null) {
            return q1;
        }
        Queue<Item> resultQueue = new Queue<>();
        while ((!q1.isEmpty()) || (!q2.isEmpty())) {
            resultQueue.enqueue(getMin(q1, q2));
        }
        return resultQueue;
    }

    /** Returns a Queue that contains the given items sorted from least to greatest. */
    public static <Item extends Comparable> Queue<Item> mergeSort(
            Queue<Item> items) {
        if (items.size() == 0) {
            return null;
        }
        Queue<Queue<Item>> queues = makeSingleItemQueues(items);
        while (queues.size() > 1) {
            Queue<Item> firstQueue = queues.dequeue();
            Queue<Item> secondQueue = queues.dequeue();
            Queue<Item> partSortedQueue = mergeSortedQueues(firstQueue, secondQueue);
            queues.enqueue(partSortedQueue);
        }
        return queues.dequeue();
    }

    public static void main(String[] args) {
        Queue<String> queue = new Queue<>();
        queue.enqueue("Cindy");
        queue.enqueue("Dick");
        queue.enqueue("Alice");
        queue.enqueue("Bob");
        System.out.println(queue);
        Queue<String> resultQueue = mergeSort(queue);
        System.out.println(resultQueue);
    }
}
