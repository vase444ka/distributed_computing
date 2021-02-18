package com.a;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class SliderThread extends Thread{
    private JSlider slider;
    private JSlider prioritySlider;
    private JPanel layoutPanel;
    private boolean increment = false;
    private int minSliderValue, maxSliderValue;

    public SliderThread(JSlider slider, JPanel layoutPanel, boolean increment){
        this.slider = slider;
        this.layoutPanel = layoutPanel;
        this.increment = increment;
        minSliderValue = slider.getMinimum();
        maxSliderValue = slider.getMaximum();

        JSlider prioritySlider = new JSlider(JSlider.HORIZONTAL, MIN_PRIORITY, MAX_PRIORITY, 5);
        String threadFunction = increment ? "Increment" : "Decrement";
        JLabel sliderLabel = new JLabel(threadFunction + " thread priority", JLabel.CENTER);
        layoutPanel.add(sliderLabel);
        prioritySlider.setMinorTickSpacing(1);
        prioritySlider.setMajorTickSpacing(2);
        prioritySlider.setPaintTicks(true);
        prioritySlider.setPaintLabels(true);

        prioritySlider.addChangeListener(event -> setPriority(prioritySlider.getValue()));

        this.layoutPanel.add(prioritySlider);
    }


    @Override
    public void run(){
        while (true) {
            synchronized (slider) {
                if (increment && slider.getValue() < maxSliderValue) {
                    slider.setValue(slider.getValue() + 1);
                } else if (!increment && slider.getValue() > minSliderValue) {
                    slider.setValue(slider.getValue() - 1);
                }
            }
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
