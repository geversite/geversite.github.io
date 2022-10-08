package Collection.Tree;



public class Node<E> {
    protected int key;//basic
    protected E value;//basic
    protected Node<E> left=null;//basic
    protected Node<E> right=null;//basic
    protected Node<E> parent=null;//basic
    protected Color color=Color.RED;//used in RBTree
    protected int weight = 1;//used in all(number of nodes(node and its' children))
    protected int balance;//used in AVL(leftDepth - rightDepth)
    protected int depth;//used in AVL(height of node and children)



    public int getKey(){ return key; }
    public E getValue(){return value;}

    public boolean isRight() {
        return !isLeft();
    }

    public enum Color{RED,BLACK;}

    @Override
    public String toString() {
        return "Node{" +
                "value='" + value + '\'' + (color==Color.RED?"red":"black") +
                '}';
    }

    public Node<E> bro(){
        if(this.parent.left==this){
            return this.parent.right;
        }else{
            return this.parent.left;
        }
    }

    public boolean isLeft(){
        return this==this.parent.left;
    }

    public Node<E> uncle(){
        return this.parent.bro();
    }
    
    public void copyContent(Node<E> node){
        this.key=node.key;
        this.value=node.value;
    }
    public Node(int key,E value) {
        this.key = key;
        this.value = value;
    }
    public Node(){
        super();
    }


    public static <T> void swap(Node<T> n1,Node<T> n2){
        T mes=n1.value;
        int key=n1.key;
        n1.value=n2.value;
        n1.key=n2.key;
        n2.value=mes;
        n2.key=key;
    }



    public Node<E> onlyChild(){
        if((this.left == null) == (this.right == null))
            return null;
        else {
            return this.left==null ? this.right : this.left;
        }
    }

}
