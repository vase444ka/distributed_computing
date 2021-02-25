package com.b;

import javax.swing.*;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicInteger;

public class Main{
    private static final int NUMBER_OF_THREADS = 2;

    public static void main(String[] s){
        AtomicInteger semaphore = new AtomicInteger(0);
        JFrame frame = new JFrame();
        JPanel layout = new JPanel();
        frame.setSize(500, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JSlider slider = new JSlider(JSlider.HORIZONTAL);
        JLabel sliderLabel = new JLabel("Manipulated Slider", JLabel.CENTER);
        JPanel mainLayout = new JPanel();
        mainLayout.add(sliderLabel);
        mainLayout.add(slider);
        layout.add(mainLayout);

        LinkedList<CustomReusableThread> threads = new LinkedList<>();
        LinkedList<JPanel> threadLayouts = new LinkedList<>();
        for (int i = 0; i < NUMBER_OF_THREADS; i++){
            boolean increment = (i == 1);
            threadLayouts.add(new JPanel());
            threads.add(new CustomReusableThread(slider, threadLayouts.getLast(), increment, semaphore));
            layout.add(threadLayouts.getLast());
        }

        threads.getLast().setCorrespondent(threads.getFirst());
        threads.getFirst().setCorrespondent(threads.getLast());

        frame.add(layout);
        frame.setVisible(true);
    }
}
