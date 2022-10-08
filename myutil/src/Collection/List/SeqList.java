package Collection.List;

import Collection.List.List;

import java.util.Arrays;
public class SeqList<E> implements List<E> {

    protected Object[] data;
    protected int capacity;
    protected int size = 0;

    @Override
    public String toString() {
        StringBuilder buffer =new StringBuilder();
        for(int i=0;i<size;i++){
            buffer.append(data[i]);
            buffer.append(", ");
        }
        return buffer.toString();
    }

    public SeqList(){
        capacity=16;
        data= new Object[capacity];
    }

    public SeqList(int capacity){
        super();
        this.capacity=capacity;
        data=new Object[capacity];
    }

    public int firstIndexOf(Object val){
        for (int i=0;i<size;i++){
            if(data[i]==(val))
                return i;
        }
        return -1;
    }

    @Deprecated
    public void insert(int pos,E val){
        if(pos>size){
            throw new IndexOutOfBoundsException();
        }
        if(pos==size){
            add(val);
        }
        for(int i=size;i>pos;i--){
            data[i]=data[i-1];
        }
        data[pos]=val;
        size++;
        if(size*4>capacity*3)
            extend();
    }

    private void extend(){
        capacity*=2;
        Object[] temp =data;
        data = new Object[capacity];
        if (size >= 0) System.arraycopy(temp, 0, data, 0, size);
    }
    @Override
    public void add(E i){
        data[size]=i;
        size++;
        if(size*4>capacity*3)
            extend();
    }
    @Override
    @SuppressWarnings("unchecked")
    public E get(int index){
        if(index>=capacity)
            return null;
        return (E)data[index];
    }

    @Override
    public void set(int index, E e) {
            data[index] = e;
    }

    @Override
    public int indexOf(E o) {
        for(int i=0;i<size;i++){
            if(o.equals(data[i]))
            return i;
        }
        return -1;
    }


    @Override
    public void remove(E elem){
        Object[] temp = new Object[capacity];
        int j=0;
        for(int i=0;i<size;i++){
            if(!data[i].equals(elem)){
                temp[j]=data[i];
                j++;
            }
        }
        data=temp;
        size=j;
    }

    public void remove(int pos){
        size--;
        System.arraycopy(data,pos+1,data,pos,size-pos);
    }

    @Override
    public int size() {
        return size;
    }

    public int getCapacity(){ return capacity;}

    @Override
    public boolean contains(E o) {
        return indexOf(o)!=-1;
    }

    @Override
    public boolean isEmpty() {
        return size==0;
    }

    @Override
    public void clear() {
        size=0;
        data=new Object[capacity];
    }

    public void sort(){
        //懒得自己写排序了
        Arrays.sort(data,0,size);
    }


    public void inverse(){
        Object temp;
        for(int i=0;i<size/2;i++){
            temp=data[i];
            data[i]=data[size-1-i];
            data[size-1-i]=temp;
        }
    }





}
