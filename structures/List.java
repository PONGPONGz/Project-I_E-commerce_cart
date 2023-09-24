package structures;

public interface List<T> {
    int size();
    boolean isEmpty();
    boolean contains(T element);
    void add(T o);
    void remove(int index);
    T get(int index);
}
