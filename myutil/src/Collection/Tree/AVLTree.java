package Collection.Tree;

public class AVLTree<V> extends Tree<V> {
    public AVLTree() {
    }
    public AVLTree(Node<V> root){
        this.root=root;
    }


    @Override
    public void add(int key ,V value){
        add(new Node<V>(key,value));
    }
    @Override
    public void add(Node<V> node) {
        super.add(node);
        traverseDepthPlus(node);
    }

    @Override
    public Boolean delete(int key){
         return delete(select(key));
    }
    @Override
    public Boolean delete(Node<V> node){
        if(node==null){
            return false;
        }
        if(root==node&&node.left==null&&node.right==null){
            root =null;
            return true;
        }
        if(node.left==null|node.right==null){
            if(node.right!=null){//有右子树
                if(node.isLeft()){//node是左数
                    node.parent.left=node.right;
                    node.right.parent=node.parent;
                    traverseDepthMinus(node.parent.left);
                }else{//node是右数
                    node.parent.right=node.right;
                    node.right.parent=node.parent;
                    traverseDepthMinus(node.parent.right);
                }
            }else if (node.left!=null){//有左子树
                if(node.isLeft()){//是左子树
                    node.parent.left=node.left;
                    node.left.parent=node.parent;
                    traverseDepthMinus(node.parent.left);
                }else{//是右子树
                    node.parent.right=node.left;
                    node.left.parent=node.parent;
                    traverseDepthMinus(node.parent.right);
                }
                node.left.parent=node.parent;
            }else{
                if(node.isLeft()){
                    node.parent.left=null;
                }else{
                    node.parent.right= null;
                }
            }
        }else{
            Node<V> reNode=findMin(node.right);
            node.copyContent(reNode);
            this.delete(reNode);
        }
        return true;
    }


    @Override
    public Boolean deleteTree(int key){
        return deleteTree(select(key));
    }
    @Override
    public Boolean deleteTree(Node<V> node) {
        Boolean re = super.deleteTree(node);
        if(re){
            traverseDepth(node);}
        return re;
    }

    public int getDepth(Node<V> node){
        if(node==null) {
            return 0;
        }else{
            int leftDepth = this.getDepth(node.left);
            int rightDepth = this.getDepth(node.right);
            return 1 + (Math.max(leftDepth, rightDepth));
        }
    }
    public void traverseDepth(Node<V> node){
        while (node!=null){
            node.depth=this.getDepth(node);
            node.balance=getBalance(node);
            if(node.balance>1||node.balance<-1){
                rotate(node);
            }
            node=node.parent;
        }
    }
    public void changeDepth(Node<V> node){
        while (node!=null){
            node.depth=this.getDepth(node);
            node.balance=getBalance(node);
            node=node.parent;
        }
    }
    public void traverseDepthPlus(Node<V> node){
        while(node.parent!=null){
            node.depth=1;
            if(node.isLeft()) {
                node.parent.balance++;
            }else{
                node.parent.balance--;
            }
            if(node.parent.balance>1||node.parent.balance<-1) rotate(node.parent);
            if(node.parent.balance==0) break;
            node=node.parent;
        }

    }
    public void traverseDepthMinus(Node<V> node) {
    node.depth++;
        while(node.parent!=null){
            node.depth--;
            if(node.isLeft()) {
                if(node.parent.balance>0)
                node.parent.balance--;
            }else{
                if(node.parent.balance<0)
                node.parent.balance++;
            }
            if(node.parent.balance>1||node.parent.balance<-1) rotate(node.parent);
            if(node.parent.balance!=0) break;
            node=node.parent;
        }
    }
    public int getBalance(Node<V> node){
        return getDepth(node.left)-getDepth(node.right);
    }

    @Override
    public void rightRotate(Node<V> node){
        super.rightRotate(node);
        changeDepth(node);
    }
    @Override
    public void leftRotate(Node<V> node){
        super.leftRotate(node);
        changeDepth(node);
    }
    public void rotate(Node<V> node){
        if(node.balance>=2){
            if(node.left.balance>0){
                rightRotate(node);
            }else{
                leftRotate(node.left);
                rightRotate(node);
            }
        }else{
            if(node.right.balance<0){
                leftRotate(node);
            }else{
                rightRotate(node.right);
                leftRotate(node);
            }
        }
    }
}
