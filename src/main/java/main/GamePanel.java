package main;

import entity.Player;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable{

    //SCREEN SETTINGS
    final int ORIGINAL_TILE_SIZE = 16; // 16x16 tile
    final int SCALE = 3;

    public final int TILE_SIZE = ORIGINAL_TILE_SIZE * SCALE; // Multiply 16px size by scale 3 for a 48px (48x48) scale up size
    final int MAX_SCREEN_COL = 16;
    final int MAX_SCREEN_ROW = 12;
    final int SCREEN_WIDTH = TILE_SIZE * MAX_SCREEN_COL; // 768 pixels
    final int SCREEN_HEIGHT = TILE_SIZE * MAX_SCREEN_ROW; // 576 pixels

    //FPS
    int FPS = 60;

    KeyHandler keyHandler = new KeyHandler();
    Thread gameThread;
    Player player = new Player(this, keyHandler);

    //Set player's default position
    int playerX = 100;
    int playerY = 100;
    int playerSpeed = 4;

    public GamePanel(){

        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT)); // Sets the width and height of the JPanel Window
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandler);
        this.setFocusable(true);
    }

    //Instantiates and starts the "gameThread"
    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start(); //Automatically calls run method.
    }

    //Implements runnable requires run method
//    @Override
//    public void run() {
//
//        double drawInterval = 1000000000/FPS; //1 Billion nanoseconds (1 second) / FPS -- Frames per second  0.016666 seconds
//        double nextDrawTime = System.nanoTime() + drawInterval; // When internal system time + interval. Draw again. -- yields FPS
//
//        //As long as this "gameThread" exists
//        while(gameThread != null){
//
//
//
//            // UPDATE: update information - character positions
//            update();
//
//            // DRAW: draw screen with updated information
//            repaint();
//
//            try {
//                double remainingTime = nextDrawTime - System.nanoTime(); //returns how much time remaining until the next draw time -- Need to let the thread sleep for the remaining time.
//                remainingTime = remainingTime/1000000; // Convert nanotime (billons) to milliseconds (thousands) -- divide billion by a million.
//
//                if(remainingTime < 0){
//                    remainingTime = 0; // If update and repaint took longer than draw interval -- then no TIME is left -- the thread doesn't need to sleep -- allocated time has already been used (UNLIKELY).
//                }
//                Thread.sleep((long)remainingTime);
//
//                nextDrawTime += drawInterval;
//
//
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//
//    }

    @Override
    public void run(){

        double drawInterval = 1000000000/FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;

        while(gameThread != null) {

            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;
            timer += currentTime - lastTime;
            lastTime = currentTime;

            if(delta >= 1){
                update();
                repaint();
                delta--;
                drawCount++;
            }

            if(timer >= 1000000000){
                System.out.println("FPS:" + drawCount);
                drawCount = 0;
                timer = 0;
            }

        }
    }


    public void update(){
        player.update();
    }

    //Built in method - Use repaint to call it
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g;

        player.draw(g2);

        g2.dispose(); // Dispose this graphics context to release any system resources that it's using
    }
}
