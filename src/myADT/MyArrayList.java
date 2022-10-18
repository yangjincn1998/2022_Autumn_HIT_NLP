package myADT;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.Iterator;

public class MyArrayList<E extends Comparable<? super E>> implements MyList<E>,MyStack<E>{
    private static final int DEFAULT_CAPACITY=10;
    //0代表未知，-1代表乱序,1代表有序
    private int isInOrder = 0;
    private int theSize;
    private E[] theItems;

    public MyArrayList()
    {doClear();}

    public void clear()
    {doClear();}

    private void doClear()
    {theSize=0; ensureCapacity(DEFAULT_CAPACITY);isInOrder=1;}

    public int size()
    {return theSize;}
    public boolean isEmpty()
    {return size()==0;}

    @Override
    public boolean push(E x) {
        isInOrder=0;
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
        isInOrder=0;
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
        isInOrder=0;
        add(size(),x);
        return true;
    }

    public void add(int idx,E x)
    {
        isInOrder=0;
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
    private int partition(int i,int j,E pivot)
    {
        int low=i,high=j;
        do{
            while(low<high&&theItems[high].compareTo(pivot)>=0)
                high--;
            theItems[low]=theItems[high];
            while(low<high&&theItems[low].compareTo(pivot)<=0)
                low++;
            theItems[high]=theItems[low];
        }while(low<high);
        theItems[low]=pivot;

        return low;
    }

    private void sort(int i,int j)
    {
        if(i>=j)
            return;
        //缩小递归深度，规模较小时采用冒泡排序
        if(j-i<=10){
            //序列大小为j-i+1，所以应该冒泡j-i次
            for(int k=0;k<j-i;k++)
            {
                //判断内层循环是否发生了交换
                boolean sp=false;
                //最低端的气泡序列，第一次要冒泡i+1处，第二次冒到i+2处
                for(int btm=j;btm>i+k;btm--)
                {
                    if(theItems[btm].compareTo(theItems[btm-1])<0)
                    {
                        sp=true;
                        E tmp = theItems[btm];
                        theItems[btm]=theItems[btm-1];
                        theItems[btm-1]=tmp;
                    }
                }
                if(!sp) return;
            }
            return;
        }
        int pivotIndex = findPivot(i,j);
        if(pivotIndex==-1) return;
        if(pivotIndex != i)
        {
            E tmp =theItems[pivotIndex];
            theItems[pivotIndex]=theItems[i];
            theItems[i]=tmp;
        }

        E pivot = theItems[i];
        int p=partition(i,j,pivot);
        sort(i,p-1);
        sort(p+1,j);
    }
    private int findPivot(int i,int j)
    {
        E firstKey = theItems[i];
        int k;
        for(k=i+1;k<=j;k++)
        {
            if(theItems[k].compareTo(firstKey)>0)
                return k;
            else if(theItems[k].compareTo(firstKey)<0)
                return i;
        }
        return -1;
    }
    public void sort()
    {sort(0,size()-1);isInOrder=1;}
    public void removeRepeat()
    {
        if(!isOrdered())
        {
            System.out.println("Not In Order!");
            throw new IllegalStateException();
        }
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
    public boolean isOrdered()
    {
        if(isInOrder==-1)
            return false;
        else if(isInOrder==1){
            return true;
        }
        else if(theSize==1){isInOrder=1;return true;}
        else{
            int k=1;
            for(;k<theSize;k++)
            {
                if(theItems[k].compareTo(theItems[k-1])<0)
                {isInOrder=-1;return false;}
            }
        }
        return true;
    }
}
