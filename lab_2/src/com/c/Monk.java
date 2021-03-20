package com.c;

import java.util.Random;

public class Monk {
    public enum Monastery{
        Guanin, Guanyan
    }
    private final Monastery monastery;
    private final int power;
    private final int MAX_POWER = 100;
    private final String name;

    private String generateName(){
        String alphabet = "abcdefghijklmnopqrstuvwxyz";
        String name = "";
        var rand =new Random();
        int len = 5;
        for(int i = 0; i < len; i++){
            name = name.concat(String.valueOf(alphabet.charAt(rand.nextInt(26))));
        }
        return name;
    }

    public Monk(){
        Random generator = new Random();
        power = generator.nextInt(MAX_POWER);
        monastery = generator.nextInt() % 2 == 0 ? Monastery.Guanin : Monastery.Guanyan;
        name = generateName();
    }

    public Monk(int power, Monastery monastery, String name){
        this.name = name;
        this.power = power;
        this.monastery = monastery;
        if (power < 0 || power > MAX_POWER){
            Random generator = new Random();
            power = generator.nextInt(MAX_POWER);
        }
    }

    public Monastery getMonastery() {
        return monastery;
    }

    public int getPower() {
        return power;
    }

    @Override
    public String toString(){
        return name + ". " + monastery + " monastery. Power = " + power;
    }
}
