package structures;

import java.util.Iterator;

@SuppressWarnings("unchecked")
public class ArrayList<T> implements Iterable<T>, List<T> {
    private static final int DEFAULT_SIZE  = 10;    // Default allocation size if no capacity specified in the constructor.
    private static final int OVERGROW_SIZE = 3;     // Amount to preallocate when "data" reaches max capacity.

    private T[] data;                               // Array to store elements
    private int size;                               // Number of exist elements in "data"

    // Constructor that takes an array of elements as input
    public ArrayList(T[] raw_input)
    {
        this.data = (T[])(new Object[raw_input.length]);
        for (int i = 0; i < raw_input.length; i++)
            this.data[i] = raw_input[i];
        
        this.size = this.data.length;
    }

    // Constructor that takes a capacity as input
    public ArrayList(int capacity)
    {
        if (capacity < 0)
            throw new IllegalArgumentException("Invalid capacity: " + capacity);

        this.data = (T[])(new Object[capacity]);
    }

    // Default constructor that will call another constructor with DEFAULT_SIZE
    public ArrayList()
    {
        this(DEFAULT_SIZE);
    }

    // Check if the list contains a specific element
    public boolean contains(T element)
    {
        for (T e: this)
        {
            if (e.equals(element))
                return true;
        }
        return false;
    }

    // Get the element at index
    public T get(int index)
    {
        assertRange(index);
        return this.data[index];
    }

    // Set the element at index
    public void set(int index, T element)
    {
        assertRange(index);
        this.data[index] = element;
    }

    // Append an element to the end of the list
    public void add(T element)
    {
        ensureCapacity(this.size + 1);
        this.data[this.size++] = element;
    }

    // Remove an element at a specific index
    public void remove(int index) {
        assertRange(index);
        strip(index + 1, index);
    }

    // Remove a specific element from the list
    public void remove(T object) {
        for (int i = 0; i < this.size; i++) {
            if ((this.data[i] == null && object == null) || this.data[i].equals(object)) {
                strip(i + 1, i);
                break;
            }
        }
    }

    // Return number of elements in the list
    public int size()
    {
        return this.size;
    }

    // Return whether the list is empty
    public boolean isEmpty()
    {
        return this.size == 0;
    }

    @Override
    public String toString()
    {
        String[] strings = new String[this.size];
        for (int i = 0; i < this.size; i++)
            strings[i] = String.valueOf(this.data[i]);

        return String.format("[%s]", String.join(", ", strings));
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private int currentIndex = 0;

            @Override
            public boolean hasNext() {
                return currentIndex < size && data[currentIndex] != null;
            }

            @Override
            public T next() {
                if (!hasNext()) {
                    throw new java.util.NoSuchElementException();
                }
                T element = (T)data[currentIndex];
                currentIndex++;
                return element;
            }
        };
    }

    // Remove elements from 'from' index to 'to' index and shift the array order
    private void strip(int from, int to)
    {
        int size = this.size - 1 - to;
        if (size > 0)
            System.arraycopy(this.data, from, this.data, to, size);

        this.data[--this.size] = null;       // Remove duplicate of last element since we shift array order
    }

    // Reallocate the array to a new capacity
    private void reallocate(int minSize)
    {
        T oldData[] = this.data;
        int newCapacity = minSize + OVERGROW_SIZE;
        this.data = (T[])(new Object[newCapacity]);
        System.arraycopy(oldData, 0, this.data, 0, this.size);
    }

    // Ensure that the array has enough capacity for a specified minimum size
    private void ensureCapacity(int minSize)
    {
        if (minSize > this.data.length)
            reallocate(minSize);
    }

    // Check if a number is within the valid range of indices
    private void assertRange(int number)
    {
        if (number > this.size || number < 0)
            throw new IndexOutOfBoundsException(number);
    }
}
