package com.a;

import javax.swing.*;
import java.util.ArrayList;
import java.util.LinkedList;

public class Main{
    private static final int NUMBER_OF_THREADS = 2;

    public static void main(String s[]){
        JFrame frame = new JFrame();
        frame.setSize(300, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JSlider slider = new JSlider(JSlider.HORIZONTAL);
        JLabel sliderLabel = new JLabel("Manipulated Slider", JLabel.CENTER);
        JPanel layoutPanel = new JPanel();
        layoutPanel.add(sliderLabel);
        layoutPanel.add(slider);
        frame.add(layoutPanel);

        LinkedList<SliderThread> threads = new LinkedList<>();
        for (int i = 0; i < NUMBER_OF_THREADS; i++){
            boolean incrementThread = (i == 1);
            threads.add(new SliderThread(slider, layoutPanel, incrementThread));
            threads.getLast().setDaemon(true);
        }
        for (Thread th: threads){
            th.start();
        }

        frame.setVisible(true);
    }
}
