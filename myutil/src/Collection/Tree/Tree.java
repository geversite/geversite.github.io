package Collection.Tree;

import Collection.Collection;
import Collection.List.List;
import Collection.List.Queue;
import Collection.List.SeqList;
import Collection.List.Stack;

public class Tree<V>  {
    protected Node<V> root;

    public Tree() {}
    public Tree(Node<V> root) {
        this.root = root;
    }

    public  void add(Node<V> node){
        if(root==null){
            root=node;
        }
        Node<V> par = root;
        while (true) {
            if (node.key > par.key) {
                par.weight++;
                if(par.right==null){
                    node.parent=par;
                    par.right=node;
                    break;
                }
                par = par.right;
            } else if(node.key < par.key){
                par.weight++;
                if(par.left==null){
                    node.parent=par;
                    par.left=node;
                    break;
                }
                par = par.left;
            }else{
                par.value=node.value;
                break;
            }
        }
    }
    public void add(int key,V value){
        Node<V> node = new Node<V>(key,value);
        add (node);
    }

    public Node<V> select(int key){
        Node<V> par = root;
        while(par!=null){
            if(key < par.key){
                par=par.left;
            }else if(key > par.key){
                par=par.right;
            }else{
                return par;
            }
        }
        return null;
    }

    public Node<V> get(int no){
        Node<V> per = root;
        if(per==null)
            return null;
        if(no>root.weight)
            return null;
        while(true){
            int weightL=0;
            // TODO: 2022/4/26
            assert per!=null;
            if(per.left!=null)
                weightL=per.left.weight;
            if(no>weightL){
                no-=(weightL+1);
                if(no==0)
                    return per;
                per=per.right;
            }else{
                per=per.left;
            }
        }
    }

    @Deprecated
    public Boolean deleteValue(int key){//useless
        Node<V> node = this.select(key);
        if(node==null){
            return false;
        }
        node.value=null;
        return true;
    }

    public Boolean delete(Node<V> node){
        if(node==null){
            return false;
        }
        if(root==node&&node.left==null&&node.right==null){
            root = null;
            return true;
        }
        if(node.left==null|node.right==null){//有0或1个孩子
            /*改树高*/
            for(Node<V> par = node.parent;par!=root;par=par.parent){
                par.weight--;
            }
            root.weight--;

            if(node.right!=null){//左子节点空
                if(node.isLeft()){
                    node.parent.left=node.right;
                }else{
                    node.parent.right=node.right;
                }
                node.right.parent=node.parent;
            }else if (node.left!=null){//右子节点为空
                if(node.isLeft()){
                    node.parent.left=node.left;
                }else{
                    node.parent.right=node.left;
                }
                node.left.parent=node.parent;
            }else{//叶子
                if(node.isLeft()){
                    node.parent.left=null;
                }else{
                    node.parent.right= null;
                }
            }
        }else{//有2个儿子
            Node<V> reNode=findMin(node.right);
            node.copyContent(reNode);
            this.delete(reNode);
        }
        return true;
    }
    public  Boolean delete(int key){
        Node<V> node = this.select(key);
        return delete(node);
    }


    public Boolean deleteTree(int key){
        Node<V> node = this.select(key);
        return deleteTree(node);
    }
    public Boolean deleteTree(Node<V> node){
        if(node==null){
            return false;
        }else if(node == root){
            root=null;
        }else if(node.isLeft()){

            for(Node<V> par = node.parent;par!=root;par=par.parent){
                par.weight-=node.weight;
            }
            root.weight-=node.weight;

            node.parent.left=null;
        }else{
            node.parent.right=null;

            for(Node<V> par = node.parent;par!=root;par=par.parent){
                par.weight-=node.weight;
            }
            root.weight-=node.weight;
        }
        return true;
    }


    protected static <V> Node<V> findMin(Node<V> node){
        while(node.left!=null){
            node=node.left;
        }
        return node;
    }
    public void rightRotate(Node<V> node){
        Node<V> temp = node.left;
        int weightL=0,weightC=0,weightR=0;
        if(temp.left!=null)
            weightL = temp.left.weight;
        if(temp.right!=null)
            weightC=temp.right.weight;
        if(node.right!=null)
            weightR=node.right.weight;

        temp.parent=node.parent;
        if(node.parent!=null){
            if(node.isLeft())
                node.parent.left=temp;
            else node.parent.right=temp;
        }else{
            root=temp;
        }
        node.left=temp.right;
        if(node.left!=null){
            node.left.parent=node;}
        temp.right=node;
        node.parent=temp;

        node.weight=1+weightC+weightR;
        temp.weight=1+weightL+node.weight;
    }
    public void leftRotate(Node<V> node){
        Node<V> temp = node.right;
        int weightL=0,weightC=0,weightR=0;
        if(node.left!=null)
            weightL = node.left.weight;
        if(temp.left!=null)
            weightC=temp.left.weight;
        if(temp.right!=null)
            weightR=temp.right.weight;


        temp.parent=node.parent;
        if(node.parent!=null){
            if(node.isLeft())
                node.parent.left=temp;
            else node.parent.right=temp;
        }else {
            root=temp;
        }
        node.right=temp.left;
        if(node.right!=null){
            node.right.parent=node;}
        temp.left=node;
        node.parent=temp;

        node.weight=1+weightL+weightC;
        temp.weight=1+node.weight+weightR;

    }




    private void recurTraverse(Node<V> node, List<Node<V>> list, int dir){
        if(dir==1) list.add(node);
        if(node.left!=null){
            recurTraverse(node.left,list,dir);}
        if(dir==2) list.add(node);
        if(node.right!=null){
            recurTraverse(node.right,list,dir);}
        if(dir==3) list.add(node);
    }

    private void noRecurTraverse(Node<V> node,List<Node<V>> list,int dir){
        Stack<Node<V>> s = new Stack<Node<V>>();
        s.push(null);
        boolean back=false;
        do {
            if((dir==3||dir==1)&&!back) list.add(node);
            if(dir==2&&(node.left==null||back)) list.add(node);
            if ((dir!=3?node.left!=null:node.right!= null)&&!back) {
                s.push(node);
                node = dir!=3?node.left:node.right;
            } else if (dir!=3?node.right != null:node.left!=null) {
                node = dir!=3?node.right:node.left;
                back=false;
            } else {
                node = s.pop();
                back=true;
            }
        } while (!s.isEmpty());
    }


    public List<Node<V>> traverse(int dir,boolean recur){
        return traverse(dir,recur,root);
    }
    public List<Node<V>> traverse(int dir,boolean recur,Node<V> node){
        List<Node<V>> list = new SeqList<Node<V>>();
        if(recur){
            recurTraverse(node,list,dir);
        }else {
            noRecurTraverse(node,list,dir);
            if(dir==3){
                list.inverse();
            }
// TODO: 2022/9/24 非递归的不会写....菜死了
            // TODO: 2022/9/25 ohhhhhhh写出来了，感谢CSDN和我的灵光一现
        }
        return list;
    }

    public List<Node<V>> sliceTraverse(Node<V> node){
        Queue<Node<V>> queue = new Queue<Node<V>>();
        List<Node<V>> list = new SeqList<Node<V>>();
        queue.offer(node);
        Node<V> temp;
        while(queue.size()!=0){
            temp= queue.dequeue();
            list.add(temp);
            if(temp.left!=null){
                queue.offer(temp.left);
            }
            if(temp.right!=null){
                queue.offer(temp.right);
            }
        }
        return list;
    }
    public List<Node<V>> sliceTraverse() {
         return sliceTraverse(root);
    }

    public int getHeight(Node<V> node){
        return node==root?0:1+getHeight(node.parent);
    }

    public boolean isComplete(){
        return isComplete(root);
    }
    public boolean isComplete(Node<V> node){
        Queue<Node<V>> queue = new Queue<Node<V>>();
        boolean find=false;
        Node<V> temp;
        queue.offer(node);
        while(!queue.isEmpty()){
            temp = queue.dequeue();
            if(!find){
                if(temp.right!=null&&temp.left==null){
                    return false;
                }else if(temp.right==null){
                    find=true;
                }
            }else {
                if(temp.left!=null||temp.right!=null){
                    return false;
                }
            }

            if(temp.left!=null) queue.offer(temp.left);
            if(temp.right!=null) queue.offer(temp.right);
        }
        return true;
    }

    public int width(){
        if(root==null) return 0;
        List<Node<V>> list = sliceTraverse();
        int[] hei = new int[list.size()];
        int[] cnt = new int[list.size()];
        for(int i=0;i<list.size();i++){
            hei[i]=getHeight(list.get(i));
        }
        int temp=0;
        int count=1;
        int j=0;
        for(int i=1;i<hei.length;i++){
            if(hei[i]==temp){
                count+=1;
            }else {
                cnt[j++]=count;
                temp=hei[i];
                count=1;
            }
        }
        int max=0;
        for (int k : cnt) {
            if (k > max)
                max = k;
        }
        return max;
    }

}

