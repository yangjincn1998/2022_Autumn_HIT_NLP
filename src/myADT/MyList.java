package myADT;

public interface MyList<E> {
    int size();
    boolean isEmpty();
    E get(int idx);

    /**
     * �滻����
     * @param idx �滻������
     * @param newVal �滻�Ķ���
     * @return ���滻�Ķ���
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
