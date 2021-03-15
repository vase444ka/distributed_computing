package com.a;

public class Main {
    static Forest forest;
    static BeeGarden beeGarden;

    public static void main(String[] args) {
        forest = new Forest(50, 50);
        beeGarden = new BeeGarden(forest, 10);
        beeGarden.findWinnie();
    }
}
