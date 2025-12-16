package org.jarlin;

public interface Subscriber<T>{

    //Gard <- C,C,C,C,C,C,C,C
    void onNext(T next);
    String getName();
}
