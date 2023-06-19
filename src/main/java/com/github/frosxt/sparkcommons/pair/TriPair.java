package com.github.frosxt.sparkcommons.pair;

public class TriPair<A, B, C>  {

    private A first;
    private B second;
    private C third;

    public TriPair(final A first, final B second, final C third) {
        this.first = first;
        this.second = second;
        this.third = third;
    }

    public A getFirst() {
        return first;
    }

    public B getSecond() {
        return second;
    }

    public C getThird() {
        return third;
    }

    public void setFirst(final A first) {
        this.first = first;
    }

    public void setSecond(final B second) {
        this.second = second;
    }

    public void setThird(final C third) {
        this.third = third;
    }
}