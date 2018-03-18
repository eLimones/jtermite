package com.mycompany.termite.model;

import java.util.ArrayList;

public class Subject<T> {

    ArrayList<Observer<T>> list;

    Subject() {
        this.list = new ArrayList<>();
    }

    public void subscribe(Observer<T> observer) {
        this.list.add(observer);
    }

    public void next(T data) {
        list.forEach((observer) -> {
            observer.next(data);
        });
    }
}
