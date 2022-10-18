package myADT;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.Iterator;

public class MyArrayList<E extends Comparable<? super E>> implements MyList<E>,MyStack<E>{
    private static final int DEFAULT_CAPACITY=10;

    private int theSize;
    private E[] theItems;

    public MyArrayList()
    {doClear();}

    public void clear()
    {doClear();}

    private void doClear()
    {theSize=0; ensureCapacity(DEFAULT_CAPACITY);}

    public int size()
    {return theSize;}
    public boolean isEmpty()
    {return size()==0;}

    @Override
    public boolean push(E x) {
        return add(x);
    }

    @Override
    public E pop() {
        if(isEmpty())
            throw new EmptyStackException();
        return remove(size()-1);
    }

    public void trimToSize()
    {ensureCapacity(size());}

    public E get(int idx)
    {
        if(idx<0||idx>=size())
            throw new ArrayIndexOutOfBoundsException();
        return theItems[idx];
    }

    public E set(int idx ,E newVal)
    {
        if(idx<0||idx>=size())
            throw new ArrayIndexOutOfBoundsException();
        E old =theItems[idx];
        theItems[idx]=newVal;
        return old;
    }

    public void ensureCapacity(int newCapacity)
    {
        if(newCapacity<theSize)
            return;

        E[] old =theItems;
        theItems=(E[])new Comparable[newCapacity];
        for(int i=0;i<size();i++)
            theItems[i]=old[i];
    }
    public boolean add(E x)
    {
        add(size(),x);
        return true;
    }

    public void add(int idx,E x)
    {
        if(theItems.length==size())
            ensureCapacity(size()*2+1);
        for(int i=theSize;i>idx;i--)
            theItems[i]=theItems[i-1];
        theItems[idx]=x;

        theSize++;
    }

    public E remove(int idx)
    {
        E removedItem = theItems[idx];
        for(int i=idx;i<size()-1;i++)
            theItems[i]=theItems[i+1];

        theSize--;
        return removedItem;
    }

    public java.util.Iterator<E> iterator()
    {return new ArrayListIterator();}

    @Override
    public E[] toArray() {
        E[] array= (E[]) new Object[size()];
        for(int i=0;i<size();i++)
        {
            array[i]=theItems[i];
        }
        return array;
    }

    private class ArrayListIterator implements java.util.Iterator<E>
    {
        private int current = 0;

        @Override
        public boolean hasNext() {
            return current<size();
        }
        public E next()
        {
            if(!hasNext())
                throw new java.util.NoSuchElementException();
            return theItems[current++];
        }

        public void remove()
        {
            MyArrayList.this.remove(--current);
        }

    }
    private int partition(int p,int q)
    {
        E tmp = null;
        int lo=p;
        int hi=q;
        E pivot=theItems[q];
        while(true)
        {
            while(theItems[lo].compareTo(pivot)<0)
                lo++;
            while(hi>0&&theItems[hi].compareTo(pivot)>0){
                hi--;
            }
            if(lo>=hi)
                break;
            else{
                tmp=theItems[lo];
                theItems[lo]=theItems[hi];
                theItems[hi]=tmp;
                lo++;
                hi--;
            }
        }

        tmp=theItems[lo];
        theItems[lo]=pivot;
        theItems[q]=tmp;

        return lo;
    }

    private void sort(int p,int q)
    {
        if(q-p<=0)
            return;
        int par=partition(p,q);
        sort(p,par-1);
        sort(par+1,q);
    }

    public void sort()
    {sort(0,size()-1);}
    public void removeRepeat()
    {
        if(size()<=1)
            return;
        int i=0,j=1;
        while(j<size())
        {
            while(theItems[i].equals(theItems[j]))
                j++;
            theItems[++i]=theItems[j];
        }
        theSize=i;
    }
    private boolean contains(E x,int i,int j)
    {
        if(isEmpty())
            return false;
        if(i>j)
            return false;
        int mid=i+(j-i)/2;
        if(theItems[mid].equals(x))
            return true;
        else if(theItems[mid].compareTo(x)>0)
        {
            return contains(x,i,mid-1);
        }
        else
            return contains(x,mid+1,j);
    }
    public boolean contains(E x)
    {
        return contains(x,0,size()-1);
    }
}
