package myADT;

public interface MyList<E> {
    int size();
    boolean isEmpty();
    E get(int idx);

    /**
     * 替换操作
     * @param idx 替换的索引
     * @param newVal 替换的对象
     * @return 被替换的对象
     */
    E set(int idx,E newVal);
    boolean add(E x);
    void add(int idx,E x);
    public E remove(int idx);
    java.util.Iterator<E> iterator();
    E [] toArray();
    void sort();
    void removeRepeat();
    boolean contains(E x);

}
