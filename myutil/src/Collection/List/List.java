package Collection.List;

import Collection.Collection;

public interface List<E> extends Collection<E> {

    E get(int index);

    void set(int index,E e);

    void inverse();

    int indexOf(E e);

}
