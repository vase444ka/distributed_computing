package com.c;

import java.util.ArrayList;
import java.util.List;

public class Main {
    static final int FIGHTERS_AMOUNT = 32;

    public static void main(String[] args) {
        List <Monk> monks = new ArrayList<>();
        for (int i = 0; i < FIGHTERS_AMOUNT; i++){
            monks.add(new Monk());
        }
        Competition fight = new Competition(monks);
        fight.perform();
        System.out.println(fight);
    }
}
