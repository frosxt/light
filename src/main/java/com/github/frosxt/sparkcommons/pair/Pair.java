package com.github.frosxt.sparkcommons.pair;

public class Pair<A, B> {

    private A first;
    private B second;

    public Pair(final A first, final B second) {
        this.first = first;
        this.second = second;
    }

    public A getFirst() {
        return first;
    }

    public B getSecond() {
        return second;
    }

    public void setFirst(final A first) {
        this.first = first;
    }

    public void setSecond(final B second) {
        this.second = second;
    }
}
