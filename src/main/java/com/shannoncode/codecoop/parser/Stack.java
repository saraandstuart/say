package com.shannoncode.codecoop.parser;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.List;

public class Stack<T> {

    private List<T> list;

    public Stack()
    {
        this(new ArrayList<>());
    }

    public Stack(List<T> list)
    {
        this.list = list;
    }

    public void push(T item) {
        list.add(item);
    }

    public T pop() {
        int size = list.size();

        T item = peek();

        list.remove(size - 1);

        return item;
    }

    public T peek() {
        int size = list.size();

        if (size == 0)
            throw new EmptyStackException();

        return list.get(size - 1);
    }

    public boolean empty() {
        return list.size() == 0;
    }

    public int search(T item) {
        int i = list.indexOf(item);

        if (i >= 0) {
            return list.size() - i;
        }

        return -1;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) return true;

        if (!(that instanceof Stack)) return false;

        Stack<?> other = (Stack<?>) that;

        return list.equals(other.list);
    }

    @Override
    public int hashCode() {
        return list.hashCode();
    }

    @Override
    public String toString() {
//        Collections.reverse(list);
        String result = "Stack{" + list + "}";
//        Collections.reverse(list);
        return result;
    }
}