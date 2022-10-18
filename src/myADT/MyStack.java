package myADT;

public interface MyStack<E> {
    boolean isEmpty();
    boolean push(E x);
    E pop();
}
