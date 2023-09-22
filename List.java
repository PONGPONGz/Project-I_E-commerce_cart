public interface List<T> {
    int size();
    boolean isEmpty();
    void add(T o);
    void remove(int index);
    T get(int index);
}
