package org.jarlin;

import lombok.extern.java.Log;

@Log
public class Main {
    public static void main(String[] args) {

        final ReactiveStream<String> stringStream = new ReactiveStream<>(); //Publisher
        final ReactiveStream<Integer> intStream = new ReactiveStream<>(); //Publisher

        final String subsName1 = "Subscriber1";
        final String subsName2 = "Subscriber2";
        final String subsName3 = "Subscriber3";
        final String subsName4 = "Subscriber4";

        //Subscriber for stringStream
        final Subscriber<String> strSubs1 = new SubscriberImpl<>(
                str -> "Length" + str.length(),
                subsName1
        );

        //Subscriber for stringStream
        final Subscriber<String> strSubs2 = new SubscriberImpl<>(
                String::toUpperCase,
                subsName2
        );

        //Subscriber for intStream
        final Subscriber<Integer> intSubs1 = new SubscriberImpl<>(
                num -> "Square" + (num * num),
                subsName3
        );

        //Subscriber for intStream
        final Subscriber<Integer> intSubs2 = new SubscriberImpl<>(
                num -> "Double" + (num + num),
                subsName4
        );


    }
}