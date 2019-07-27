package util;

public class Counter {
    private int counter;

    public Counter() {
        counter = 0;
    }

    public synchronized int getCounter() {
        return counter;
    }

    public synchronized int setCounter(int c) {
        counter = c;

        return counter;
    }

    public synchronized int incCounter() {
        return (++counter);
    }
}
