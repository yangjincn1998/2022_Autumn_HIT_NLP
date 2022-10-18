package myADT;

public interface MySet<E> {
    boolean isEmpty();
    boolean add(E x);
    boolean contains(E x);
    java.util.Iterator<E> iterator();
    int size();
}
