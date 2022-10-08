package Collection.List;

public class LinkList<E> implements List<E> {
    protected Node<E> head;
    protected Node<E> tail;
    protected int size=0;

    @Override
    public String toString() {
        StringBuilder buffer = new StringBuilder();
        Node<E> flag=head;
        while(flag!=tail){
            buffer.append(flag.data);
            buffer.append(", ");
            flag=flag.next;
        }
        buffer.append(tail.data);
        return buffer.toString();
    }

    public LinkList(){
        super();
        head=null;
        tail=null;
    }

    public E head(){return head.data;}

    public E tail(){return tail.data;}

    @Override
    public int size(){return size;}

    @Override
    public boolean isEmpty(){return size==0;}

    @Override
    public void clear(){
        head=null;
        tail=null;
        size=0;
    }

    @Override
    public E get(int index){
        Node<E> node = head;
        while(index-->0){
            node=node.next;
        }
        return node.data;
    }

    @Override
    public void set(int index,E e){
        Node<E> node = head;
        while(index-->0){
            node=node.next;
        }
        node.data=e;
    }

    @Override
    public int indexOf(E e){
        int count=0;
        Node<E> node = head;
        while(node!=null){
            if(node.data.equals(e))
                return count;
            count++;
            node=node.next;
        }
        return -1;
    }

    @Override
    public boolean contains(E e){
        Node<E> node = head;
        while(node!=null){
            if(node.data.equals(e))
                return true;
            node=node.next;
        }
        return false;
    }



    @Override
    public void add(E e){
        add(new Node<E>(e));
    }

    private void add(Node<E> node){
        if(head==null){
            head=node;
        }else {
            tail.next=node;
            node.front=tail;
        }
        tail=node;
        size++;
    }

    public void insert(int pos,E data){
        insert(pos,new Node<>(data));
    }

    public void insert(int pos,Node<E> node){
        Node<E> flag=head;
        if(pos>size){
            throw new NullPointerException("插入位置超过列表长度");
        }
        if(pos==size){
            add(node);
        }
        if(pos==0){
            head.front=node;
            node.next=head;
            node.front=null;
            head=node;
        }
        while(pos-->0){
            flag=flag.next;
        }
        flag.front.next=node;
        node.front=flag.front;
        flag.front=node;
        node.next=flag;
    }

    public void remove(Node<E> node){
        Node<E> f = node.front;
        Node<E> n = node.next;
        if(f!=null){
            f.next=n;
        }else {
            head=n;
        }
        if(n!=null){
            n.front=f;
        }else {
            tail=f;
        }
        size--;
    }

    public void remove(E data){
        Node<E> flag=head;
        while(flag!=null){
            if(flag.data.equals(data)){
                remove(flag);
            }
            flag=flag.next;
        }
    }


    public void inverse(){
        Node<E> temp,flag ;
        flag=head;
        while(flag!=null){
            temp=flag.front;
            flag.front=flag.next;
            flag.next=temp;
            flag=flag.front;
        }
        temp=head;
        head=tail;
        tail=temp;
    }
}


class Node<E> {
    public E data;
    public Node<E> front;
    public Node<E> next;

    public Node(){
        super();
    }

    public Node(E data, Node<E> front, Node<E> next) {
        this.data = data;
        this.front = front;
        this.next = next;
    }

    public Node(E data){
        this();
        this.data=data;
    }

    public Node(Node<E> f1) {
        data=f1.data;
        front=f1.front;
        next=f1.next;
    }

    @Override
    public String toString() {
        return "Node{" +
                "data=" + data +
                '}';
    }
}