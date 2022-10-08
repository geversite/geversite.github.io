package Collection.List;

public class Stack<E> extends SeqList<E>  {



    public Stack(){
        super();
    }

    public E peek(){
        return get(size-1);
    }

    public void push(E d){
        add(d);
    }

    public E pop(){
        if(size==0){
            return null;
        }
        E obj = peek();
        remove(size-1);
        return obj;
    }


}
