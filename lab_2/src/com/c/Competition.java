package com.c;

import java.util.List;

public class Competition {
    private static class Duel implements Runnable{
        private final int firstFighter;
        private final int lastFighter;
        Duel left, right;
        Monk winner;
        List <Monk> fighters;

        public Duel(int firstFighter, int lastFighter, List <Monk> fighters){
            if (firstFighter > lastFighter){
                throw new IllegalArgumentException("creating inverted duel");
            }
            if (firstFighter < 0 || firstFighter >= fighters.size() || lastFighter >= fighters.size()){
                throw new IllegalArgumentException("creating duel of nonexistent fighters");
            }
            this.firstFighter = firstFighter;
            this.lastFighter = lastFighter;
            this.fighters = fighters;
        }

        public void run(){
            if (lastFighter == firstFighter){
                try{
                    winner = this.fighters.get(firstFighter);
                }catch (IndexOutOfBoundsException e){
                    winner = null;
                }
                return;
            }

            int midFighter = (firstFighter + lastFighter)/2;
            left = new Duel(firstFighter, midFighter, this.fighters);
            right = new Duel(midFighter + 1, lastFighter, this.fighters);


            Thread rightThread = new Thread(right);
            rightThread.start();
            left.run();
            try {
                rightThread.join();
            }catch (Exception e){}

            winner = left.winner.getPower() > right.winner.getPower() ? left.winner : right.winner;
        }

        public String print(int depth){
            if (depth < 0){
                throw new IllegalArgumentException();
            }
            StringBuilder result = new StringBuilder();
            if (left != null){
                result.append(left.print(depth - 1));
            }
            result.append("_______".repeat(depth));
            if (winner != null){
                result.append(winner.toString()).append('\n');
            }
            if (right != null){
                result.append(right.print(depth - 1));
            }

            return result.toString();
        }
    }

    Duel finalBattle;
    List <Monk> fighters;
    boolean finished = false;

    public Competition(List <Monk> fighters){
        this.fighters = fighters;
        finalBattle = new Duel(0, this.fighters.size() - 1, this.fighters);
    }

    public void perform(){
        finalBattle.run();
        finished = true;
    }
    @Override
    public String toString() {
        if (finished)
            return finalBattle.print((int) (Math.ceil(Math.log(fighters.size())/Math.log(2))));
        return "Competition isn't performed yet";
    }
}
