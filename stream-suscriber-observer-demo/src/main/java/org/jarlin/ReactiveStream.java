package org.jarlin;

import lombok.extern.java.Log;

import java.util.LinkedList;
import java.util.List;

@Log
public class ReactiveStream<T>  {

    private final List<Subscriber<T>> subscribers = new LinkedList<>();

    public ReactiveStream<T> subscribe(Subscriber<T> subscriber){
        this.subscribers.add(subscriber);
        log.info("[subscribe] " + subscriber.getName());
        return this;
    }

    public void unsubscribe(Subscriber<T> subscriber){
        this.subscribers.remove(subscriber);
        log.info("[Unsubscribe] " + subscriber.getName());
    }

    public void emit(T value){
        log.info("[emit] " + value);
        this.subscribers.forEach(subscriber -> subscriber.onNext(value));
    }
}
