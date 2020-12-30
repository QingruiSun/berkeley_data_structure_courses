public class ArrayDeque<T> {


    private T[] items;
    private int maxLength;
    private int firstIndex;
    private int lastIndex;
    private int size;
    double usageRatio;
    public ArrayDeque() {
        maxLength = 8;
        size = 0;
        usageRatio = 0;
        items = (T[]) new Object[maxLength];
    }

    public void addFirst(T item) {
        if(size < maxLength) {
            minusFirstIndex();
        } else {
            ExpandArray();
        }
        items[firstIndex] = item;
        size++;
    }


    /**
     * Move the first index back.
     */
    private void minusFirstIndex() {
        if (firstIndex == 0) {
            firstIndex = maxLength - 1;
        } else {
            firstIndex--;
        }
    }

    public void addLast(T item) {
        if (size < maxLength) {
            addLastIndex();
        } else {
            ExpandArray();
        }
        items[lastIndex] = item;
        size++;
    }

    /**
     * Move the last index forth.
     */
    private void addLastIndex() {
        if (lastIndex == maxLength - 1) {
            lastIndex = 0;
        } else {
            lastIndex++;
        }
    }

    private void ExpandArray() {
        int prevMaxLength = maxLength;
        maxLength = maxLength * 2;
        T[] newItems = (T[]) new Object[maxLength];
        if (firstIndex <= lastIndex) {
            for (int i = firstIndex; i <= lastIndex; ++i) {
                newItems[i - firstIndex] = items[i];
            }
        } else {
            int newIndex = 0;
            for (int i = firstIndex; i <= prevMaxLength - 1; ++i) {
                newItems[newIndex++] = items[i];
            }
            for (int i = 0; i <= lastIndex; ++i) {
                newItems[newIndex++] = items[i];
            }
        }
        items = newItems;
        firstIndex = 0;
        lastIndex = size - 1;
    }

    public boolean isEmpty() {
        if (size == 0) {
            return true;
        }
        return false;
    }

    public int size() {
        return size;
    }

    public void printDeque() {
        if (size == 0) {
            return;
        }
        System.out.print(items[firstIndex]);
        if (firstIndex <= lastIndex) {
            for (int i = firstIndex + 1; i <= lastIndex; ++i) {
                System.out.print(' ');
                System.out.print(items[i]);
            }
        } else {
            for (int i = firstIndex; i <= maxLength - 1; i++) {
                System.out.print(' ');
                System.out.print(items[i]);
            }
            for (int i = 0; i < lastIndex; ++i) {
                System.out.print(' ');
                System.out.print(items[i]);
            }
        }
    }

    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        T result = items[firstIndex];
        addFirstIndexHelper();
        size--;
        usageRatio = size / maxLength;
        if ((usageRatio <= 0.25) && (maxLength > 16)) {
            ContractArray();;
        }
        return result;
    }

    /**
     * Contract the array when the usage ratio is too low.
     */
    private void ContractArray() {
        int prevMaxLength = maxLength;
        maxLength = maxLength / 2;
        T[] newItems = (T[]) new Object[maxLength];
        if (firstIndex <= lastIndex) {
            for(int i = firstIndex; i <= lastIndex; ++i){
                newItems[i - firstIndex] = items[i];
            }
        } else {
            int newIndex = 0;
            for(int i = firstIndex; i <= prevMaxLength - 1; ++i) {
                newItems[newIndex++] = items[i];
            }
            for(int i = 0; i <= lastIndex; ++i) {
                newItems[newIndex++] = items[i];
            }
        }
        items = newItems;
        firstIndex = 0;
        lastIndex = size - 1;
    }

    /**
     * Move the first index forth.
     */
    private void addFirstIndexHelper() {
        if (firstIndex == maxLength - 1) {
            firstIndex = 0;
        } else {
            firstIndex++;
        }
    }

    public T removeLast() {
        if (size == 0) {
            return null;
        }
        T result = items[lastIndex];
        minusLastIndex();
        size--;
        usageRatio = size / maxLength;
        if ((usageRatio <= 0.25) && (maxLength > 16)) {
            ContractArray();
        }
        return result;
    }

    /**
     * Move the last index back.
     */
    private void minusLastIndex() {
        if (lastIndex == 0) {
            lastIndex = maxLength - 1;
        } else {
            lastIndex--;
        }
    }

    public T get(int index) {
        return items[index];
    }

}
