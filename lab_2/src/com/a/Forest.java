package com.a;

import java.util.Random;

public class Forest {
    private final int[][] regions;
    private final int WIDTH, HEIGHT;
    private final int WINNIE = 3;

    public Forest(int height, int width) throws IllegalArgumentException{
        if (width <= 0 && height <= 0){
            throw new IllegalArgumentException("Incorrect forest sizes");
        }
        this.HEIGHT = height;
        this.WIDTH = width;
        regions = new int[height][width];
        Random generator = new Random();
        regions[generator.nextInt(height)][generator.nextInt(width)] = WINNIE;
    }

    boolean isWinnieInRegion(int i, int j){
        if (i < 0 || j < 0 || i >= HEIGHT || j >= WIDTH){
            return false;
        }
        return regions[i][j] == WINNIE;
    }

    public int getWidth(){
        return WIDTH;
    }

    public int getHeight(){
        return HEIGHT;
    }
}
