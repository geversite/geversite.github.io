package Collection;

public interface Collection<E> {

    void add(E e);

    void remove(E e);

    int size();

    boolean contains(E e);

    boolean isEmpty();

    void clear();
}
