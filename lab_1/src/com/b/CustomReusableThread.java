package com.b;

import javax.swing.*;
import java.util.concurrent.atomic.AtomicInteger;

public class CustomReusableThread {
    private JSlider slider;
    private boolean increment = false;
    private int minSliderValue, maxSliderValue;
    AtomicInteger semaphore;
    JButton on, off;
    Thread thread;
    boolean working = false;

    public CustomReusableThread(JSlider slider, JPanel layoutPanel, boolean increment, AtomicInteger semaphore){
        this.slider = slider;
        this.increment = increment;
        this.semaphore = semaphore;
        minSliderValue = slider.getMinimum();
        maxSliderValue = slider.getMaximum();
        on = new JButton((increment?"increment":"decrement") + " on");
        off = new JButton((increment?"increment":"decrement") + " off");
        layoutPanel.add(on);
        layoutPanel.add(off);
        on.addActionListener((actionEvent)->start());
        off.addActionListener((actionEvent) ->{
            try{
                stop();
            }
            catch (InterruptedException e){}
        });
    }

    private boolean start(){
        if (!semaphore.compareAndSet(0, 1))
            return false;
        working = true;
        thread = new Thread(()->{
            while (working) {
                if (increment) {
                    System.out.println("Inc");
                } else {
                    System.out.println("Dec");
                }
                if (increment && slider.getValue() < maxSliderValue - 10) {
                    slider.setValue(slider.getValue() + 1);
                } else if (!increment && slider.getValue() > minSliderValue + 10) {
                    slider.setValue(slider.getValue() - 1);
                }

                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.setDaemon(true);
        thread.start();
        return true;
    }

    private void stop() throws InterruptedException{
        if (working) {
            working = false;
            thread.join();
            semaphore.set(0);
        }
    }
}
