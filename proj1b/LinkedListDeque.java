public class LinkedListDeque<T> implements Deque<T> {

    private InnerNode sentiB;
    private InnerNode sentiF;
    private int size = 0;


    public class InnerNode {

        private InnerNode prev;
        private InnerNode next;
        private T item;

        public InnerNode() {
            this.prev = null;
            this.next = null;
        }

        public InnerNode(T item) {
            this.prev = null;
            this.next = null;
            this.item = item;
        }
    }

    public LinkedListDeque() {
        this.sentiB = new InnerNode();
        this.sentiF = new InnerNode();
        sentiF.next = sentiB;
        sentiB.prev = sentiF;
        this.size = 0;
    }

    @Override
    public void addFirst(T item) {
        InnerNode addNode = new InnerNode(item);
        this.sentiF.next.prev = addNode;
        addNode.next = this.sentiF.next;
        addNode.prev = this.sentiF;
        this.sentiF.next = addNode;
        size++;
    }

    @Override
    public void addLast(T item) {
        InnerNode addNode = new InnerNode(item);
        this.sentiB.prev.next = addNode;
        addNode.prev = this.sentiB.prev;
        addNode.next = this.sentiB;
        this.sentiB.prev = addNode;
        size++;
    }

    @Override
    public boolean isEmpty() {
        if (size == 0) {
            return true;
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void printDeque() {
        if (size == 0) {
            return;
        }
        InnerNode ptr = this.sentiF.next;
        System.out.print(ptr.item);
        for (int i = 2; i <= size; ++i) {
            ptr = ptr.next;
            System.out.print(' ');
            System.out.println(ptr.item);
        }
    }

    @Override
    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        InnerNode firstNode = this.sentiF.next;
        this.sentiF.next.next.prev = this.sentiF;
        this.sentiF.next = this.sentiF.next.next;
        size--;
        return firstNode.item;
    }

    @Override
    public T removeLast() {
        if (size == 0) {
            return null;
        }
        InnerNode lastNode = this.sentiB.prev;
        this.sentiB.prev.prev.next = this.sentiB;
        this.sentiB.prev = this.sentiB.prev.prev;
        size--;
        return lastNode.item;
    }

    @Override
    public T get(int index) {
        if (index < 0) {
            return null;
        }
        if (index >= size) {
            return null;
        }
        InnerNode ptr = this.sentiF;
        int remain = index;
        while (index >= 0) {
            index--;
            ptr = ptr.next;
        }
        return ptr.item;
    }

    public T getRecursive(int index) {
        if (index < 0) {
            return null;
        }
        if (index >= this.size) {
            return null;
        }
        InnerNode ptr = this.sentiF.next;
        T result = getRecursiveHelper(index, ptr);
        return result;
    }

    /**
     * Help function to build getRecursive(int index).
     * @param index is the remain index from now to the item want to be got.
     * @param ptr is the pointer to now InnerNode.
     * @return item to get.
     */
    private T getRecursiveHelper(int index, InnerNode ptr) {
        if (index == 0) {
            return ptr.item;
        }
        return getRecursiveHelper(index - 1, ptr.next);
    }

}
