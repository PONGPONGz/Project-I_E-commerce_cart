import java.util.Iterator;

@SuppressWarnings("unchecked")
public class ArrayList<T> implements Iterable<T> {
    private static final int DEFAULT_SIZE  = 10;    // Default allocation size if no capacity specified in the constructor.
    private static final int OVERGROW_SIZE = 3;     // Amount to preallocate when "data" reaches max capacity.

    private T[] data;
    private int size;                               // Number of exist elements in "data"

    public ArrayList(T[] raw_input)
    {
        this.data = (T[])(new Object[raw_input.length]);
        for (int i = 0; i < raw_input.length; i++)
            this.data[i] = raw_input[i];
        
        this.size = this.data.length;
    }

    public ArrayList(int capacity)
    {
        if (capacity < 0)
            throw new IllegalArgumentException("Invalid capacity: " + capacity);

        this.data = (T[])(new Object[capacity]);
    }

    public ArrayList()
    {
        this(DEFAULT_SIZE);
    }

    public T get(int index)
    {
        assertRange(index);
        return this.data[index];
    }

    public void set(int index, T element)
    {
        assertRange(index);
        this.data[index] = element;
    }

    public void add(T element)
    {
        ensureCapacity(this.size + 1);
        this.data[this.size++] = element;
    }

    public void remove(int index)
    {
        assertRange(index);
        strip(index + 1, index);
    }

    public void remove(T object)
    {
        for (int i = 0; i < this.size; i++)
        {
            if ((this.data[i] == null && object == null) || this.data[i].equals(object))
            {
                strip(i + 1, i);
                break;
            }
        }
    }

    public int size()
    {
        return this.size;
    }

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

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

    private void strip(int from, int to)
    {
        int size = this.size - 1 - to;
        System.out.println("Striping from: " + from + " to " + to + " size " + size);
        if (size > 0)
            System.arraycopy(this.data, from, this.data, to, size);

        this.data[--this.size] = null;       // Remove duplicate of last element since we shift array order
    }

    private void reallocate(int minSize)
    {
        T oldData[] = this.data;
        int newCapacity = minSize + OVERGROW_SIZE;
        this.data = (T[])(new Object[newCapacity]);
        System.arraycopy(oldData, 0, this.data, 0, this.size);
        System.out.println("ArrayList max size reallocated to: " + newCapacity);
    }

    private void ensureCapacity(int minSize)
    {
        if (minSize > this.data.length)
            reallocate(minSize);
    }

    private void assertRange(int number)
    {
        if (number > this.size || number < 0)
            throw new IndexOutOfBoundsException(number);
    }
}
