package synthesizer;


import java.util.Iterator;

public class ArrayRingBuffer<T> extends AbstractBoundedQueue<T> {
    /* Index for the next dequeue or peek. */
    private int first;            // index for the next dequeue or peek
    /* Index for the next enqueue. */
    private int last;
    /* Array for storing the buffer data. */
    private T[] rb;

    /**
     * Create a new ArrayRingBuffer with the given capacity.
     */
    public ArrayRingBuffer(int capacity) {
        this.capacity = capacity;
        this.fillCount = 0;
        this.first = 0;
        this.last = 0;
        rb = (T[]) new Object[capacity];
    }

    /**
     * Adds x to the end of the ring buffer. If there is no room, then
     * throw new RuntimeException("Ring buffer overflow"). Exceptions
     * covered Monday.
     */
    public void enqueue(T x) {
        if (this.fillCount == this.capacity) {
            throw new RuntimeException("Ring buffer overflow");
        }
        rb[this.last] = x;
        this.last = (this.last + 1) % this.capacity;
        this.fillCount++;
    }

    /**
     * Dequeue oldest item in the ring buffer. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow"). Exceptions
     * covered Monday.
     */
    public T dequeue() {
        if (this.fillCount == 0) {
            throw new RuntimeException("Ring buffer underflow");
        }
        T result = rb[this.first];
        this.first = (this.first + 1) % this.capacity;
        this.fillCount--;
        return result;
    }

    /**
     * Return oldest item, but don't remove it.
     */
    public T peek() {
        return rb[first];
    }

    public Iterator<T> iterator() {
        return new ArrayRingBufferIterator();
    }

    private class ArrayRingBufferIterator implements Iterator<T> {
        private int index = 0;
        ArrayRingBufferIterator() {
            this.index = 0;
        }

        @Override
        public boolean hasNext() {
            if (index < fillCount) {
                return true;
            }
            return false;
        }

        @Override
        public T next() {
            int resultIndex = (first + index) % capacity;
            index++;
            return rb[resultIndex];
        }
    }
}
