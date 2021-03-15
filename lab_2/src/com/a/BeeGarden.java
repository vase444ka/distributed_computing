package com.a;

import java.util.concurrent.atomic.AtomicInteger;

public class BeeGarden {
    private final Forest forest;
    private int flocksNumber;

    public BeeGarden(Forest forest, int flocksNumber) {
        if (forest == null || flocksNumber < 0){
            throw new IllegalArgumentException();
        }
        this.forest = forest;
        this.flocksNumber = flocksNumber;
    }

    public void setFlocksNumber(int flocksNumber) {
        this.flocksNumber = flocksNumber;
    }

    public void findWinnie(){
        AtomicInteger tasksCounter = new AtomicInteger(forest.getHeight());
        for (int i = 0; i < flocksNumber; i++){
            Thread flockThread = new Thread(()->{
                int row;
                while ((row = tasksCounter.decrementAndGet()) >= 0){
                    System.out.println(Thread.currentThread().getName() + " is checking row no." + row);
                    for (int col = 0; col < forest.getWidth(); col++){
                        if (forest.isWinnieInRegion(row, col)){
                            tasksCounter.set(0);
                            System.out.println("Winnie is executed at cell no.:" + row + " " + col);
                        }
                    }
                }

            });
            flockThread.start();
        }
    }
}
