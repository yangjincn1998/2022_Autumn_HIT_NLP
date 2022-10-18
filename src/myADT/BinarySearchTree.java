package myADT;

import java.nio.BufferUnderflowException;
import java.util.Iterator;
import java.util.Stack;

public class BinarySearchTree<E extends Comparable<? super E>>implements MySet<E>{
    private static class BinaryNode<E>
    {
        //Constructors
        BinaryNode(E theElement)
        {this(theElement,null,null);}

        BinaryNode(E theElement,BinaryNode<E> lt,BinaryNode<E> rt)
        {element=theElement;left=lt;right=rt;}

        E element;
        BinaryNode<E> left;
        BinaryNode<E> right;
    }
    private BinaryNode<E> root;
    private int theSize=0;
    public BinarySearchTree()
    {root=null;}

    public void makeEmpty()
    {root=null;theSize=0;}
    public boolean isEmpty()
    {return root==null;}

    @Override
    public boolean add(E x) {
        insert(x);
        return true;
    }

    public boolean contains(E x)
    {return contains(x,root);}
    public E finMin()
    {if(isEmpty()) throw new BufferUnderflowException();
        return finMin(root).element;
    }
    public E finMax()
    {
        if(isEmpty()) throw new BufferUnderflowException();
        return finMax(root).element;
    }
    public int size()
    {return theSize;}

    public void insert(E x)
    {root=insert(x,root);theSize++;}
    public void remove(E x)
    {root=remove(x,root);theSize--;}

    @Override
    /**
     * ”√
     */
    public Iterator<E> iterator() {
        return new BinarySearchTreeIterator();
    }

    private boolean contains(E x,BinaryNode<E> t)
    {
        if(t==null)
            return false;

        int compareResult=x.compareTo(t.element);

        if(compareResult<0)
            return contains(x,t.left);
        else if(compareResult>0)
            return contains(x,t.right);
        else
            return true;
    }
    private BinaryNode<E>finMin(BinaryNode<E> t)
    {
        if(t==null)
            return null;
        else if(t.left==null)
            return t;
        return finMin(t.left);
    }

    private BinaryNode<E>finMax(BinaryNode<E> t)
    {
        if(t!=null)
            while(t.right!=null)
                t=t.right;

        return t;
    }
    private BinaryNode<E> insert(E x,BinaryNode<E> t)
    {
        if(t==null)
            return new BinaryNode<>(x,null,null);

        int compareResult=x.compareTo(t.element);

        if(compareResult<0)
            t.left=insert(x,t.left);
        else if(compareResult>0)
            t.right=insert(x,t.right);
        else
            ;
        return t;
    }
    private BinaryNode<E> remove(E x,BinaryNode<E> t)
    {
        if(t==null)
            return t;

        int compareResult=x.compareTo(t.element);

        if(compareResult<0)
            t.left=remove(x,t.left);
        else if(compareResult>0)
            t.right=remove(x,t.right);
        else if(t.left!=null&&t.right!=null)
        {
            t.element=finMin(t.right).element;
            t.right=remove(t.element,t.right);
        }
        else
            t=(t.left!=null)? t.left:t.right;
        return t;
    }
    private class BinarySearchTreeIterator<E> implements java.util.Iterator<E>
    {
        BinaryNode<E> root;
        Stack<BinaryNode<E>> st;

        public BinarySearchTreeIterator() {
            this.root=(BinaryNode<E>) BinarySearchTree.this.root;
            this.st = new Stack<>();
            while (this.root!=null)
            {
                st.push(this.root);
                this.root=this.root.left;
            }
        }

        @Override
        public boolean hasNext() {
            return !st.isEmpty();
        }

        @Override
        public E next() {
            BinaryNode<E> cur=st.peek();st.pop();
            E val=cur.element;
            cur=cur.right;
            while(cur!=null)
            {
                st.push(cur);
                cur=cur.left;
            }
            return val;
        }
    }
}
