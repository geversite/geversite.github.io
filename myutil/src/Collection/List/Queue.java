package Collection.List;


public class Queue<E> extends LinkList<E> {

    public E peek(){
        return head();
    }

    public E dequeue(){
        E data = peek();
        remove(data);
        return data;
    }

    public void offer(E e){
        add(e);
    }

}
