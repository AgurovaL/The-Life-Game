package com.agurova;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

@Data
@EqualsAndHashCode(callSuper = false)
@ToString
public class LifeFrame extends JFrame {

    private int frameWidth;
    private int frameHeight;
    private int iterations;

    private JPanel flowPanel; //main panel
    private BacteriaPanel gridPanel; //panel for the bacteria field

    private Thread myThread;

    private BacteriaColony bacteriaColony;

    public void createLifeGUI(int frameHeight, int frameWidth, int iterations) {
        this.frameHeight = frameHeight;
        this.frameWidth = frameWidth;
        this.iterations = iterations;

        //creating my colony with a new thread
        myThread = new Thread();
        myThread.setPriority(Thread.NORM_PRIORITY);
        myThread.start();
        this.bacteriaColony = new BacteriaColony(frameHeight, frameWidth);
        bacteriaColony.setZeroMatrix();

        //create panels
        this.flowPanel = new JPanel();
        this.gridPanel = new BacteriaPanel();

        //create Start, Stop and Clear buttons
        createButtons();

        //add panels to the frame
        flowPanel.add(gridPanel);
        add(flowPanel);

        //set title, size, etc
        setFrameSettings();
    }

    private void setFrameSettings() {
        setTitle("Life Game");
        setSize(new Dimension(500, 500));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void createButtons() {
        JButton bStart = createStartButton();
        JButton bStop = createStopButton();
        JButton bClear = createClearButton();

        //add buttons to the panel
        flowPanel.add(bStart);
        flowPanel.add(bStop);
        flowPanel.add(bClear);
    }

    private JButton createStartButton(){
        //start updating the colony
        JButton bStart = new JButton("START");
        bStart.setPreferredSize(new Dimension(80, 50));

        bStart.addActionListener(new ActionListener() {
            @Override
            synchronized public void actionPerformed(ActionEvent e) {
                if (myThread.isInterrupted()) {
                    myThread.notify();
                } else {
                  doLifeCircle();
                }
                myThread.interrupt();
            }
        });
        return bStart;
    }

    /**
     * Update bacteria colony several times and show the colony on the frame
     */
    private void doLifeCircle(){
        bacteriaColony.setRandomMatrix();
        for (int i = 0; i < iterations; i++) {
            gridPanel.removeAll();
            gridPanel.createButtonsField();
            gridPanel.updateUI();

            bacteriaColony.updateColonyMatrix();
            try {
                myThread.sleep(100);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

    private JButton createStopButton(){
        //stop updating the colony
        JButton bStop = new JButton("STOP");
        bStop.setPreferredSize(new Dimension(80, 50));

        bStop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                myThread.interrupt();
            }
        });
        return bStop;
    }

    private JButton createClearButton(){
        //clear the bacteria field and the matrix
        JButton bClear = new JButton("CLEAR");
        bClear.setPreferredSize(new Dimension(80, 50));

        bClear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                doClear();
            }
        });
        return bClear;
    }

    /**
     * Clear the bacteria field and set all the bacteria = 0 (dead)
     */
    private void doClear(){
        gridPanel.removeAll();
        bacteriaColony.setZeroMatrix();
        gridPanel.createButtonsField();

        gridPanel.updateUI();
    }

    //bacteria buttons table
    class BacteriaPanel extends JPanel {
        BacteriaPanel() {
            super(new BorderLayout());
            setBorder(new EmptyBorder(20, 20, 20, 20)); // margin

            createButtonsField();
        }

        //create the table with the buttons for each bacteria
        void createButtonsField() {
            GridLayout layout = new GridLayout(frameWidth, frameHeight);
            setLayout(layout);
            GridBagConstraints layConstraints = new GridBagConstraints();

            //make bacteria alive (1) and change its color
            ActionListener actionListener = new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    JButton button = (JButton) e.getSource();
                    changeButton(button);
                }
            };

            for (int heightIndex = 0; heightIndex < frameHeight; heightIndex++) {
                for (int widthIndex = 0; widthIndex < frameWidth; widthIndex++) {
                    //make bacteria button
                    JButton button = new JButton();

                    if (bacteriaColony.matrix[heightIndex][widthIndex] == 0) {
                        button.setBackground(new Color(0, 99, 0));
                    } else {
                        button.setBackground(new Color(209, 200, 100));
                    }

                    button.setPreferredSize(new Dimension(375 / frameWidth, 375 / frameHeight));
                    button.putClientProperty("heightIndex", heightIndex);
                    button.putClientProperty("widthIndex", widthIndex);
                    button.addActionListener(actionListener);

                    layConstraints.fill = GridBagConstraints.BOTH;
                    layConstraints.gridx = widthIndex; // cells' x and y
                    layConstraints.gridy = heightIndex;

                    this.add(button, layConstraints);
                }
            }
        }

        //change button's color and status in matrix
        void changeButton(JButton button) {
            int heightIndex = (int) button.getClientProperty("heightIndex");
            int widthIndex = (int) button.getClientProperty("widthIndex");

            if (bacteriaColony.matrix[heightIndex][widthIndex] == 0) {
                button.setBackground(new Color(209, 200, 100));
                bacteriaColony.matrix[heightIndex][widthIndex] = 1;
            } else {
                button.setBackground(new Color(0, 99, 0));
                bacteriaColony.matrix[heightIndex][widthIndex] = 0;
            }
        }
    }
}
