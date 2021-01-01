package synthesizer;

import java.util.Iterator;

public abstract class AbstractBoundedQueue<T> implements BoundedQueue<T> {

    protected int fillCount;

    protected int capacity;

    @Override
    public int capacity() {
        return capacity;
    }

    @Override
    public int fillCount() {
        return fillCount;
    }

    public abstract Iterator<T> iterator();

    public abstract T peek();

    public abstract T dequeue();

    public abstract void enqueue(T x);
}
