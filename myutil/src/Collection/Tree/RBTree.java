package Collection.Tree;



public class RBTree<V> extends Tree<V> {

    public RBTree() {
        super();
    }

    public RBTree(Node<V> root) {
        super(root);
        root.color= Node.Color.BLACK;
    }

    @Override
    public void add(Node<V> node) {
        super.add(node);
        if(node==root)
            node.color= Node.Color.BLACK;
        else{
            node.color= Node.Color.RED;
            adjust(node);
        }
    }


    @Override
    public void add(int key, V value) {
        this.add(new Node<V>(key,value));
    }

    // TODO: 2022/4/19 没想好delete之后怎么做，重点也算是了
    // TODO: 2022/4/20 差不多写完了，没测试，真是不敢测，改bug估计要一天
    // TODO: 2022/4/24 哈哈哈太棒了居然没bug
    @Override
    public Boolean delete(Node<V> node) {
        if(node==null){
            return false;
        }//null
        if(root==node&&node.left==null&&node.right==null){
            root = null;
            return true;
        }//root
        if(node.right==null&&node.left==null)//叶子
            deleteLeaf(node);
        else if(node.onlyChild()!=null){//一个孩子
            Node<V> child;
            while((child=node.onlyChild())!=null){
                Node.swap(node,child);
                node=child;
            }
            delete(node);
        }else{//两个孩子
            Node<V> min = findMin(node.right);
            Node.swap(min,node);
            delete(min);
        }
        return true;
    }

    private void deleteLeaf(Node<V> node) {
        if(node.isLeft())//先删该点
            node.parent.left=null;
        else
            node.parent.right=null;

        for(Node<V> par = node.parent;par!=root;par=par.parent){
            par.weight--;
        }
        root.weight--;

        if(node.color== Node.Color.BLACK){//如果是黑色
            Node<V> bro = node.bro();
            if(bro.color== Node.Color.RED){//如果兄弟是红色
                bro.color= Node.Color.BLACK;
                bro.parent.color= Node.Color.RED;
                if(bro.isRight())
                    leftRotate(bro.parent);
                else rightRotate(bro.parent);
            }else{//兄弟是黑色
                if(bro.left==null&&bro.right==null){
                    bro.color= Node.Color.RED;
                    Node<V> per = bro.parent;
                    while (per.color== Node.Color.BLACK&&per!=root){
                        bro=per;
                        per=per.parent;
                    }
                    bro.color= Node.Color.BLACK;
                    if(per!=root)
                        per.color= Node.Color.RED;
                }else if(bro.left!=null&&bro.right!=null){// 兄弟两个子
                    bro.color=bro.parent.color;
                    bro.parent.color= Node.Color.BLACK;
                    if(bro.isRight()){
                        bro.right.color= Node.Color.BLACK;
                        leftRotate(bro.parent);
                    }else{
                        bro.left.color= Node.Color.BLACK;
                        rightRotate(bro.parent);
                    }
                }else{//一个孩子
                    if(bro.isRight()==bro.onlyChild().isRight()){//同侧
                        bro.color=bro.parent.color;
                        bro.parent.color= Node.Color.BLACK;
                        bro.onlyChild().color= Node.Color.BLACK;
                        if(bro.isRight()) leftRotate(bro.parent);
                        else rightRotate(bro.parent);
                    }else{//两侧
                        bro.onlyChild().color=bro.parent.color;
                        bro.parent.color= Node.Color.BLACK;
                        if(bro.isRight()){
                            rightRotate(bro);
                            leftRotate(bro.parent.parent);
                        }else{
                            leftRotate(bro);
                            rightRotate(bro.parent.parent);
                        }
                    }
                }
            }
        }

    }

    @Override
    public Boolean delete(int key) {
        return this.delete(this.select(key));
    }

    // TODO: 2022/4/20 RBTree好像无法删除树 ，反正我不会
    @Override
    @Deprecated
    public Boolean deleteTree(int key){
        return false;
    }

    @Override
    @Deprecated
    public Boolean deleteTree(Node<V> node) {
        return false;
    }
    // TODO: 2022/4/19 最主要的工作量，可能会借鉴BST的rotate，希望能简化那个方法，那玩意看的我头大
    // TODO: 2022/4/20 adjust写完了，目前看没啥问题。测试不够，接下来会继续测试 rotate 写到tree里继承了
    private void adjust(Node<V> node) {
        if(node.parent==null||node.parent.color== Node.Color.BLACK){
            return;
        }
        while(node.parent.color== Node.Color.RED&&node.uncle()!=null&&node.uncle().color== Node.Color.RED){
                node.uncle().color= Node.Color.BLACK;
                node.parent.color= Node.Color.BLACK;
                node.parent.parent.color= Node.Color.RED;
                node=node.parent.parent;
                if(node==root){
                    node.color= Node.Color.BLACK;
                    return;
                }
            }
        if(node.parent.color== Node.Color.RED){
           node = rotate(node);
           reColor(node);
        }
    }

    private Node<V> rotate(Node<V> node) {
        if(node.parent.isRight()){
            if(node.isRight())
                leftRotate(node.parent.parent);
            else {
                rightRotate(node.parent);
                leftRotate(node.parent);
                node=node.right;
            }
        }else{
            if(node.isLeft()){
                rightRotate(node.parent.parent);
            }else{
                leftRotate(node.parent);
                rightRotate(node.parent);
                node=node.left;
            }
        }
        return node;
    }
    private void reColor(Node<V> node) {
        node.bro().color= Node.Color.RED;
        node.parent.color= Node.Color.BLACK;
    }


}
