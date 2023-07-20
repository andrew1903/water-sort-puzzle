package ua.andrew1903;

import java.util.Iterator;
import java.util.Stack;

public class Bottle {
    private final static int DEFAULT_BOTTLE_CAPACITY = 3;

    private final int capacity;

    private Stack<Integer> liquids;

    public Bottle() {
        this(DEFAULT_BOTTLE_CAPACITY);
    }

    public Bottle(int initialCapacity, Stack<Integer> liquids) {
        this(initialCapacity);
        this.liquids = liquids;
    }

    public Bottle(int initialCapacity) {
        this.liquids = new Stack<>();
        this.capacity = initialCapacity;
    }

    public boolean isEmpty() {
        return liquids.isEmpty();
    }

    public boolean isFull() {
        return liquids.size() == capacity;
    }

    public int push(int i) {
        if (isFull()) {
            throw new ArrayIndexOutOfBoundsException();
        }
        return this.liquids.push(i);
    }

    public int pop() {
        return this.liquids.pop();
    }

    public int peek() {
        return this.liquids.peek();
    }

    public Iterator<Integer> getLiquids() {
        return liquids.elements().asIterator();
    }

    @Override
    public String toString() {
        return liquids.toString();
    }
}
