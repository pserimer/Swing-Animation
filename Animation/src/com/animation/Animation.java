package com.animation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Animation extends Frame implements ActionListener {

    Timer timer;

    // Location and radius for drawing a sun
    int x = 600, y = 175, dx = 100, dy = 100;

    // Solar rays
    int[] X1 = {x+35, x+50, x+65},  Y1 = {y+5, y-35, y+5};
    int[] X2 = {x+70, x+100, x+90}, Y2 = {y+15, y-15, y+30};
    int[] X3 = {x+65, x+130, x+90}, Y3 = {y+20, y+50, y+65};
    int[] X4 = {x+70, x+90, x+100}, Y4 = {y+90, y+75, y+110};
    int[] X5 = {x+35, x+50, x+65},  Y5 = {y+90, y+135, y+90};
    int[] X6 = {x+30, x+10, x},     Y6 = {y+90, y+75, y+110};
    int[] X7 = {x+5, x-30, x+2},    Y7 = {y+35, y+50, y+65};
    int[] X8 = {x+30, x, x+10},     Y8 = {y+15, y-15, y+30};

    // Variable to change color of the sun
    // And rotating the waves
    private int step = 0;

    // Location for drawing a ship
    int[] xShip = {200, 300, 350, 150}, yShip = {500, 500, 450, 450};

    // Variable to change the direction of the ship
    private double delta = 0.08;

    // Head and tail of the fish 1
    int headX = 100, headY = 700;
    int dheadx = 40, dheady = 40;
    int[] tailX = {headX, headX-15, headX-15}, tailY = {headY+20, headY, headY+40};

    // Head and tail of the fish 2
    int headX2 = 650, headY2 = 625;
    int dheadx2 = 40, dheady2 = 40;
    int[] tailX2 = {headX2+40, headX2+60, headX2+60}, tailY2 = {headY2+20, headY2, headY2+40};

    public Animation() {
        // create a timer, delay = 600
        timer = new Timer(600, this);

        // start timer
        timer.start();

        // End program when window is closed:
        addWindowListener(new MyFinishWindow());

        // Set layout:
        setLayout(null);

        // Add control panel:
        Panel panel = new Panel();
        panel.setBounds(0, 40, 800, 50);

        // Add buttons:
        Button button1 = new Button();
        button1.setLabel("Start");
        button1.addActionListener(this);

        Button button2 = new Button();
        button2.setLabel("Stop");
        button2.addActionListener(this);

        panel.add(button1);
        panel.add(button2);

        panel.setBackground(Color.WHITE);
        add(panel);
    }

    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        g2d.scale(1, 1);
        g2d.setColor(Color.WHITE);

        // Change the color of the sun,
        // And rotate the waves
        if(step == 0) {
            // Draw waves
            for(int i = -75; i<= 600; i += 75)
                g2d.drawArc(i, 500, 200, 100, 0, 110);

            // Move the solar rays
            for (int i = 0; i < X2.length; i++) {
                this.X2[i] += 10; this.Y2[i] -= 10;
                this.X4[i] += 10; this.Y4[i] += 10;
                this.X6[i] -= 10; this.Y6[i] += 10;
                this.X8[i] -= 10; this.Y8[i] -= 10;
            }

            g2d.setColor(new Color(255,255,153));
            step++;
        }
        else {
            // Draw waves
            for(int i = -75; i<= 600; i += 75)
                g2d.drawArc(i, 500, 200, 80, -80, 110);

            // Move the solar rays
            for (int i = 0; i < X2.length; i++) {
                this.X2[i] -= 10; this.Y2[i] += 10;
                this.X4[i] -= 10; this.Y4[i] -= 10;
                this.X6[i] += 10; this.Y6[i] -= 10;
                this.X8[i] += 10; this.Y8[i] += 10;
            }

            g2d.setColor(new Color(255,204,0));
            step = 0;
        }

        // Draw the sun
        g2d.fillOval(x, y, dx, dy);
        g2d.fillPolygon(X1, Y1, 3);
        g2d.fillPolygon(X2, Y2, 3);
        g2d.fillPolygon(X3, Y3, 3);
        g2d.fillPolygon(X4, Y4, 3);
        g2d.fillPolygon(X5, Y5, 3);
        g2d.fillPolygon(X6, Y6, 3);
        g2d.fillPolygon(X7, Y7, 3);
        g2d.fillPolygon(X8, Y8, 3);

        // Draw the ship
        g2d.setColor(Color.RED);
        g2d.fillPolygon(xShip, yShip, 4);

        // Draw a fishhook
        g2d.setColor(Color.BLACK);
        g2d.drawLine(xShip[0]+5, yShip[2], xShip[0]+5, yShip[2]-30);
        g2d.drawLine(xShip[0]+5, yShip[2]-30, xShip[0]-15, yShip[2]+100);

        // Move the ship and fish
        for (int i = 0; i < xShip.length; i++) {
            xShip[i] += delta * timer.getDelay();

            headX += delta * timer.getDelay() / 10;
            for (int j = 0; j < tailX.length; j++)
                tailX[j] += delta * timer.getDelay() / 10;

            headX2 -= delta * timer.getDelay() / 10;
            for (int j = 0; j < tailX2.length; j++)
                tailX2[j] -= delta * timer.getDelay() / 10;
        }

        // Rotate the ship, fish continue swimming directly
        if(xShip[2] > 700 || xShip[3] < 100) {
            this.delta *= -1;

            int tempX = headX; headX = headX2; headX2 = tempX;
            int tempY = headY; headY = headY2; headY2 = tempY;
            int[] tX = tailX; tailX = tailX2; tailX2 = tX;
            int[] tY = tailY; tailY = tailY2; tailY2 = tY;
        }

        // Spawn new 2 fish in default location
        if(headX < 0 && headY > 600) {
            headX2 = 100; headY2 = 700;
            dheadx2 = 40; dheady2 = 40;
            tailX2 = new int[]{headX2, headX2 - 15, headX2 - 15};
            tailY2 = new int[]{headY2 + 20, headY2, headY2 + 40};

            headX = 650; headY = 650;
            dheadx = 40; dheady = 40;
            tailX = new int[]{headX + 40, headX + 60, headX + 60};
            tailY = new int[]{headY + 20, headY, headY + 40};
        }

        // Draw fish 1 and fish 2
        g2d.setColor(new Color(0,0,204));
        g2d.fillOval(headX, headY, dheadx, dheady);
        g2d.fillPolygon(tailX, tailY, 3);
        g2d.fillOval(headX2, headY2, dheadx2, dheady2);
        g2d.fillPolygon(tailX2, tailY2, 3);

        // Draw cloud
        if(step == 0)
            // Scale down the cloud
            g2d.scale(0.9,0.9);

        else
            // Scale up the cloud
            g2d.scale(1.1, 1.1);

        g2d.setColor(Color.WHITE);
        g2d.fillOval(100, 175, 50, 50);
        g2d.fillOval(125, 150, 75, 75);
        g2d.fillOval(175, 175, 50, 50);
    }

    public static void main(String[] args) {
        // Initialize frame
        Animation animation = new Animation();

        // Set frame size to 800x800
        animation.setSize(800, 800);

        // Set visibility
        animation.setVisible(true);

        // Set background color
        animation.setBackground(new Color(51,153,255));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        if(command != null) {
            if (command.equals("Start"))
                timer.restart();
            else if (command.equals("Stop"))
                timer.stop();
        }

        repaint();
    }

    public static class MyFinishWindow extends WindowAdapter
    {
        public void windowClosing(WindowEvent e)
        {
            System.exit(0);
        }
    }
}